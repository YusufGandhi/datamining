import java.util.ArrayList;
import java.util.Collections;


public class KMeans {
	private ArrayList<Centroid> centroids;
	private CSV csv;
	private int K;
	private int distanceFunction;
	private int maxIteration;
	private int centroidInitFunction;
	private int iteration;
	
	
	public KMeans(CSV csv, int K, int distanceFunction, int maxIteration, int centroidInitFunction) {
		this.csv = csv;
		this.K = K;
		this.distanceFunction = distanceFunction;
		this.maxIteration = maxIteration;
		this.centroidInitFunction = (centroidInitFunction == Centroid.FURTHEST_FIRST_CENTROID_INIT ?
				Centroid.FURTHEST_FIRST_CENTROID_INIT : Centroid.RANDOM_CENTROID_INIT);
		centroids = new ArrayList<Centroid>();
		
		// initalizing centroids initialization function
		// either furthest-first or random 
		if (this.centroidInitFunction == Centroid.FURTHEST_FIRST_CENTROID_INIT)
			furthestFirstCentroidsInit();
		else
			randomCentroidsInit();
		
		this.iteration = 0;
			
	}
	
	private Centroid initRandomCentroid() {
		int size = csv.getRows().size();
		int idx = (int) (Math.random() * size);
		return new Centroid(csv.getRow(idx));
	}
	
	public void randomCentroidsInit() {		
		for(int i = 0; i < K; i++) {
			Centroid chosen = initRandomCentroid();
			
			if(!centroids.contains(chosen))
				centroids.add(i, chosen);
			else 
				i--;
		}
	}
	
	public void furthestFirstCentroidsInit() {
		// TODO: farthest-first initialization
		
		// STEP 1: adding the first, random centroid
		centroids.add(initRandomCentroid());
		
		// STEP 2: repeat k - 1 times
		for(int i = 0; i < this.K - 1; i++) {
			// using the max sum of the distance of a point to all selected centroids
			double max = 0.0;
			Centroid maxDistCentroid = null;
			for(DataPoint d : csv.getRows()) {
				double sum = 0.0;
				for(Centroid c : centroids) {
					sum += calcDistance(c, d);
				}
				if (sum > max) {
					maxDistCentroid = new Centroid(d);
					max = sum;
				}
			}
			centroids.add(maxDistCentroid);
		}
	}
	
	private double calcDistance(DataPoint d1, DataPoint d2) {
		if (distanceFunction == Distance.MANHATTAN)
			return Distance.ManhattanDist(d1, d2);
		if(distanceFunction == Distance.COSINE)
			return Distance.CosineDist(d1, d2);
		
		return Distance.EuclideanDist(d1, d2);	
	}
	
	public ArrayList<DataPoint> getAllDataPoints() {
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
	
	public int getIteration() {
		return iteration;
	}
	
	public void runClustering() {
		// initializing the temporary centroids and
		// other variables for comparing purposes.
		Centroid [] initCentroids = new Centroid[this.K];
		double centroidsMovement = 0.0;
		int idx;
		ArrayList<Double> allDistance = new ArrayList<Double>();
		
		

		do {
			System.out.println("Iteration " + iteration + ":");
			
			// emptying centroids
			for(Centroid c : getCentroids()) {
				c.deleteAllMembers();				
			}
			
			// initCentroids can be instance of Observation
			// copying the current centroids
			// as we need initCentroids to later compare the movement
			// of all centroids
			for (int i = 0; i < this.K; i++) {
				initCentroids[i] = new Centroid(centroids.get(i));
			}
			
			// assigning all observations to the closest centroid
			for (DataPoint obs : getAllDataPoints()) {
				
				
				for(Centroid cent : getCentroids() ) {
					allDistance.add(Distance.EuclideanDist(obs, cent));
				}
				
				int centIndexObsAssignedTo = allDistance.indexOf(Collections.min(allDistance));
				allDistance.clear();
				
				getCentroid(centIndexObsAssignedTo).addMember(obs);
			}
			
			idx = 0;
			centroidsMovement = 0.0;
			for(Centroid c : getCentroids()) {
				System.out.println("Centroid #" + idx + " members: " + c.getAllMembers().size());
				c.printAllMembers();
				c.calculateNewPosition();
				centroidsMovement += Distance.ManhattanDist(initCentroids[idx], c);
				idx++;
			}
			
			System.out.println();
		} while(centroidsMovement > 0.0 && ++this.iteration < this.maxIteration);
	}
	
	
}
