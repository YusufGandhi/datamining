import java.util.ArrayList;


public class KMeans {
	private ArrayList<Centroid> centroids;
	private CSV csv;
	private int K;
	private int distanceFunction;
	private int maxIteration;
	private int centroidInitFunction;
	
	public KMeans(CSV csv, int K, int distanceFunction, int maxIteration, int centroidInitFunction) {
		this.csv = csv;
		this.K = K;
		this.distanceFunction = distanceFunction;
		this.maxIteration = maxIteration;
		this.centroidInitFunction = centroidInitFunction;
		centroids = new ArrayList<Centroid>();
		randomCentroidsInit();
	}
	
	public void randomCentroidsInit() {
//		int len = csv.getRows().size();
//		System.out.println("Randomize: " + len);
//		
		// setting random centroids from the observations
		for(int i = 0; i < K; i++) {
//			int idx = (int) (Math.random() * len);
			Centroid chosen = initRandomCentroid();
			
			/*
			 * check whether the chosen centroid is already in the list
			 * if not, insert it in the centroid collection; repeat otherwise 
			 */
			if(!centroids.contains(chosen))
				centroids.add(i, chosen);
			else 
				i--;
		}
	}
	
	public Centroid initRandomCentroid() {
		int size = csv.getRows().size();
		int idx = (int) (Math.random() * size);
		return new Centroid(csv.getRow(idx));
	}
	
	public void farthestFirstCentroidsInit() {
//		 TODO: farthest-first initialization
		
	}
	
	public ArrayList<DataPoint> getObservations() {
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
	
	
}
