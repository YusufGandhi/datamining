import java.util.ArrayList;


public class Centroid extends DataPoint {
	public static final int FURTHEST_FIRST_CENTROID_INIT = 1;
	public static final int RANDOM_CENTROID_INIT = 0;
	
	private ArrayList<DataPoint> members;
	
	public Centroid() {
		super();
		members = new ArrayList<DataPoint>();
	}
	
	public Centroid(DataPoint copy) {
		super(copy);
		members = new ArrayList<DataPoint>();
	}
	
	public void addMember(DataPoint m) {
		members.add(m);
	}
	
	public ArrayList<DataPoint> getAllMembers() {
		return members;
	}
	
	public void deleteAllMembers() {
		members.clear();
	}
	
	public void calculateNewPosition() {
		for(int i = 0; i < featureSize(); i++) {
			
			if (!(getFeatureValue(i) instanceof Double)) continue;
			
			double sum = 0;
			for(DataPoint m : members) {
				sum += (Double) m.getFeatureValue(i);
			}
			
			setFeatureValue(i, sum / members.size());
		}
	}
	
	public void printAllMembers() {
		for(DataPoint m : members) {
			System.out.println(m.getFeatures());
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		int i = 0;
		for(Object o : getFeatures()) {
			if (i++ != 0) sb.append(", ");
			sb.append(o.toString());
		}
		return sb.toString();
	}
	
	
}