/**
 * (c) 2016 - MIT License
 * The Centroid class is extended from the DataPoint class.
 * This is where the centroids are defined.
 */
import java.util.ArrayList;

public class Centroid extends DataPoint {
	public enum Init {
		RANDOM, FURTHEST_FIRST;
	}
	
	// the collection to which data points are assigned
	private ArrayList<DataPoint> members;
	
	public Centroid() {
		super();
		members = new ArrayList<DataPoint>();
	}
	
	public Centroid(DataPoint copy) {
		super(copy);
		members = new ArrayList<DataPoint>();
	}
	/**
	 * Method to add a data point to the members collection.
	 * @param m the DataPoint object that is going to be added into the collection.
	 */
	public void addMember(DataPoint m) {
		members.add(m);
	}
	
	/**
	 * Returns the collection of data points which are assigned to
	 * the particular centroid. 
	 * @return the members parameter (ArrayList of DataPoint)
	 */
	public ArrayList<DataPoint> getAllMembers() {
		return members;
	}
	
	/**
	 * Procedure to empty the collection of the member data points
	 */
	public void deleteAllMembers() {
		members.clear();
	}
	
	/**
	 * Method to calculate the new position of the centroids based on
	 * the collection of member data points. The new value of each feature
	 * is the average of the member data points's feature.
	 */
	public void calculateNewPosition() {
		// iterating through the features
		for(int i = 0; i < featureSize(); i++) {
			
			// non-numerical data is skipped
			if (!(getFeatureValue(i) instanceof Double)) continue;
			
			// getting the sum of the feature of all data points
			double sum = 0;
			for(DataPoint m : members) {
				sum += (Double) m.getFeatureValue(i);
			}
			
			// setting the value by the average of the calculated feature
			setFeatureValue(i, sum / members.size());
		}
	}
	
	/**
	 * A procedure to print all the members and their respective features.
	 */
	public void printAllMembers() {
		for(DataPoint m : members) {
			System.out.println(m.getFeatures());
		}
	}
	
	@Override
	public String toString() {
		return getFeatures().toString();
	}
	
	
}