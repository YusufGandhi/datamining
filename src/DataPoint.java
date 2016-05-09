/**
 * (c) 2016 - MIT license
 * DataPoint class is the class which contains each defined data point
 */
import java.util.ArrayList;

public class DataPoint {
	// the features of the data point
	private ArrayList<Object> features;
	
	// the number of features
	private int numOfFeatures;
	
	// This index is used to save numeric index
	// useful for iterating just the numeric fields for calculating distance 
	private ArrayList<Integer> numIndex;
	
	public DataPoint() {
		features = new ArrayList<Object>();
	}
	
	public DataPoint(String row) {
		String [] f = row.split(",");
		numOfFeatures = f.length;
		features = new ArrayList<Object>(numOfFeatures);
		numIndex = new ArrayList<Integer>();
		
		// adding the features to the DataPoint object 
		// and casting it to either String or Double class
		for(int i = 0; i < numOfFeatures; i++) {
			if(isNumeric(f[i])) {
				features.add(i, Double.parseDouble(f[i]));
				numIndex.add(i);
			}
			else features.add(i, f[i]);
		}
	}
	
	/**
	 * Constructor to copy another DataPoint object 
	 * @param copy the DataPoint object that wants to be copied 
	 */
	public DataPoint(DataPoint copy) {
		this.features = new ArrayList<Object>(copy.features);
		this.numOfFeatures = this.features.size();
		this.numIndex = new ArrayList<Integer>(copy.numIndex);
	}
	
	/**
	 * Method to set a feature value. Each value is saved to either String
	 * or Double object and represented by an index.
	 * @param idx the index of the feature
	 * @param val the value that is going to be inserted into the index
	 */
	public void setFeatureValue(int idx, Object val) {
		if (idx < 0 || idx >= numOfFeatures) throw new IndexOutOfBoundsException("No such element");	
		features.set(idx, val);
	}
	
	/**
	 * Method to retrieve the feature. Returns an Object and 
	 * needs to be cast to either String or Double.
	 * @param idx the index of the feature that wants to be retrieved
	 * @return    the object containing the feature
	 */
	public Object getFeatureValue(int idx) {
		if (idx < 0 || idx >= numOfFeatures) throw new IndexOutOfBoundsException("No such element");
		return features.get(idx);
	}
	
	/**
	 * Method to check whether a string is numeric or not.
	 * @param str the string that wants to be checked
	 * @return    true if str is numeric; false otherwise
	 */
	private boolean isNumeric(String str) {
		return str.trim().matches("-?\\d+(\\.\\d+)?");
	}
	
	/**
	 * Method to retrieve all features.
	 * @return all features in the for of ArrayList of Object.
	 */
	public ArrayList<Object> getFeatures() {
		return features;
	}
	
	/**
	 * Function to retrieve the length of the features.
	 * @return length of the features
	 */
	public int featureSize() {
		return numOfFeatures;
	}
	
	/**
	 * Function to retrieve the index of numeric only fields.
	 * @return the ArrayList of Integer of the numeric fields index
	 */
	public ArrayList<Integer> getNumIndex() {
		return numIndex;
	}
	
	/**
	 * Function to calculate the magnitude of a DataPoint (as the way to calculate
	 * magnitude of a vector). 
	 * @return the magnitude of the vector DataPoint
	 */
	public double getMagnitude() {
		double magSquared = 0.0;
		for(Integer i : numIndex) {
			magSquared += (Double) features.get(i) * (Double) features.get(i);
		}
		return Math.sqrt(magSquared);
	}
	
}