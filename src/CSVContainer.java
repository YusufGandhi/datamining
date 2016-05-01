import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class CSVContainer {
	private ArrayList<Observation> rows;
	
	public CSVContainer(String fileName) {
		rows = new ArrayList<Observation>();
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ArrayList<Observation> getRows() {
		return rows;
	}
	
	public static class Observation {
		private ArrayList<Object> features;
		private int numOfFeatures;
		
		public Observation(String row) {
			String [] f = row.split(",");
			numOfFeatures = f.length;
			features = new ArrayList<Object>();
			for(int i = 0; i < numOfFeatures; i++) {
				if(isNumeric(f[i])) features.add(i, Double.parseDouble(f[i]));
				else features.add(i, f[i]);
			}
		}
		
		public void addFeatureValue(int idx, Object val) {
			if (idx < 0 || idx >= numOfFeatures) throw new IndexOutOfBoundsException("No such element");	
			features.add(idx, val);
		}
		
		public Object getFeatureValue(int idx) {
			if (idx < 0 || idx >= numOfFeatures) throw new IndexOutOfBoundsException("No such element");
			return features.get(idx);
		}
		
		// Inspired by: stackoverflow.com/questions/1102891/how-to-check-if-a-string-is-numeric-in-java
		private boolean isNumeric(String str) {
			return str.matches("-?\\d+(\\.\\d+)?");
		}
		
		public int getNumFeatures() {
			return numOfFeatures;
		}
		
	}
	
}
