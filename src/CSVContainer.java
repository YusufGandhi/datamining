import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class CSVContainer {
	private ArrayList<Observation> rows;
	private int observationSize;
	
	public CSVContainer(String fileName) {
		rows = new ArrayList<Observation>();
		observationSize = rows.size();
		extractCSV(fileName);
	}
	
	private void extractCSV(String fileName)  {
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String strLine;
			while((strLine = br.readLine()) != null) {
				rows.add(new Observation(strLine));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Observation> getRows() {
		return rows;
	}
	
	public Observation getRow(int idx) {
		return rows.get(idx);
	}
	
	public static class Observation {
		private ArrayList<Object> features;
		private int numOfFeatures;
		
		// This index is used to save numeric index
		// useful for iterating just the numeric fields for calculating distance 
		private ArrayList<Integer> numIndex;
		
		public Observation() {
			features = new ArrayList<Object>();
		}
		
		public Observation(String row) {
			String [] f = row.split(",");
			numOfFeatures = f.length;
			features = new ArrayList<Object>(numOfFeatures);
			numIndex = new ArrayList<Integer>();
			
			// adding the features and casting it to
			// either String or Double class
			for(int i = 0; i < numOfFeatures; i++) {
				if(isNumeric(f[i])) {
					features.add(i, Double.parseDouble(f[i]));
					numIndex.add(i);
				}
				else features.add(i, f[i]);
			}
		}
		
		public Observation(Observation copy) {
			this.features = new ArrayList<Object>(copy.features);
			this.numOfFeatures = this.features.size();
			this.numIndex = new ArrayList<Integer>(copy.numIndex);
		}
		
		public void setFeatureValue(int idx, Object val) {
			if (idx < 0 || idx >= numOfFeatures) throw new IndexOutOfBoundsException("No such element");	
			features.set(idx, val);
		}
		
		public Object getFeatureValue(int idx) {
			if (idx < 0 || idx >= numOfFeatures) throw new IndexOutOfBoundsException("No such element");
			return features.get(idx);
		}
		
		// Inspired by: stackoverflow.com/questions/1102891/how-to-check-if-a-string-is-numeric-in-java
		// this can check only up to decimal number system (it will fail to check hexadecimal)
		private boolean isNumeric(String str) {
			return str.trim().matches("-?\\d+(\\.\\d+)?");
		}
		
		public ArrayList<Object> getFeatures() {
			return features;
		}
		
		public int featureSize() {
			return numOfFeatures;
		}
		
		public ArrayList<Integer> getNumIndex() {
			return numIndex;
		}
		
		public double getMagnitude() {
			double magSquared = 0.0;
			for(Integer i : numIndex) {
				magSquared += (Double) features.get(i) * (Double) features.get(i);
			}
			return Math.sqrt(magSquared);
		}
		
	}
}
