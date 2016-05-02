import java.util.ArrayList;


public class KMeans {
	private ArrayList<Centroid> centroids;
	private CSVContainer csv;
	private int K;
	
	public KMeans(int K, CSVContainer csv) {
		this.K = K;
		this.csv = csv;
		centroids = new ArrayList<Centroid>();
		randomCentroidsInit();
	}
	
	public void randomCentroidsInit() {
		// TODO random initialization
		int len = csv.getRows().size();
		System.out.println("Randomize: " + len);
		// setting random centroids from the observations
		for(int i = 0; i < K; i++) {
			int idx = (int) (Math.random() * len);
			Centroid chosen = new Centroid(csv.getRows().get(idx));
			centroids.add(i, chosen);
		}
	}
	
	public void farthestFirstCentroidsInit() {
		// TODO: farthest-first initialization
	}
	
	public ArrayList<CSVContainer.Observation> getObservations() {
		return csv.getRows();
	}
	
	public Centroid getCentroid(int idx) {
		if (idx < 0 || idx >= K) throw new IndexOutOfBoundsException("No such centroid");
		return centroids.get(idx);
	}
	
	public ArrayList<Centroid> getCentroids() {
		return centroids;
	}
	
	public int getK() {
		return K;
	}
	
	public static class Centroid extends CSVContainer.Observation {
		private ArrayList<CSVContainer.Observation> members;
		
		public Centroid() {
			super();
			members = new ArrayList<CSVContainer.Observation>();
		}
		
		public Centroid(CSVContainer.Observation copy) {
			super(copy);
			members = new ArrayList<CSVContainer.Observation>();
		}
		
		public void addMember(CSVContainer.Observation m) {
			members.add(m);
		}
		
		public ArrayList<CSVContainer.Observation> getAllMembers() {
			return members;
		}
		
		public void deleteAllMembers() {
			members.clear();
		}
		
		public void calculateNewPosition() {
			for(int i = 0; i < featureSize(); i++) {
				
				if (getFeatureValue(i) instanceof String) continue;
				
				double sum = 0;
				for(CSVContainer.Observation m : members) {
					sum += (Double) m.getFeatureValue(i);
				}
				
				setFeatureValue(i, sum / members.size());
			}
		}
		
		public void printAllMembers() {
			for(CSVContainer.Observation m : members) {
				System.out.println(m.getFeatures());
			}
		}
		
		
	}
	
	
}
