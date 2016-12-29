/** File: KMeans.java **/
import java.util.ArrayList;
import java.util.Collections;

public class KMeans {
	// the collection of centroids
	private ArrayList<Centroid> centroids;
	
	// the CSV file object
	private CSV csv;
	
	// the K parameter (how many centroids)
	protected int K;
	
	// parameter to determine which distance func to use
	protected Distance.Func distanceFunction;
	
	// parameter control the maximum iteration
	private int maxIteration;
	
	// parameter to decide what centroid-init func to use
	protected Centroid.Init centroidInitFunction;
	
	// parameter to count how many iterations
	private int iteration;
	
	public KMeans(CSV csv, int K, Distance.Func distanceFunction, int maxIteration, Centroid.Init centroidInitFunction) {
		// setting up the KMeans object's properties
		this.csv = csv;
		this.K = K;
		this.distanceFunction = distanceFunction;
		this.maxIteration = maxIteration;
		this.centroidInitFunction = (centroidInitFunction == Centroid.Init.FURTHEST_FIRST ?
				Centroid.Init.FURTHEST_FIRST : Centroid.Init.RANDOM);
		reset();
	}
	
	// method to generate a random centroid from data points
	private Centroid getRandomCentroidFromDataPoints() {
		int size = csv.getRows().size();
		int idx = (int) (Math.random() * size);
		return new Centroid(csv.getRow(idx));
	}
	
	// the random centroid initialization method
	public ArrayList<Centroid> randomCentroidsInit() {
		ArrayList<Centroid> c = new ArrayList<Centroid>();
		for(int i = 0; i < K; i++) {
			Centroid chosen = getRandomCentroidFromDataPoints();
			
			if(!c.contains(chosen)) c.add(i, chosen);
			else i--;
		}
		return c;
	}
	
	// The furthest-first centroid initialization method 
	public ArrayList<Centroid> furthestFirstCentroidsInit() {
		ArrayList<Centroid> newCent = new ArrayList<Centroid>();
		// STEP 1: adding the first, random centroid
		newCent.add(getRandomCentroidFromDataPoints());
		
		// STEP 2: repeat k - 1 times: find the data point that
		//         is the farthest away from the selected centroids so far
		for(int i = 0; i < this.K - 1; i++) {
			// using the max sum of the distance of a point to all selected centroids
			double max = 0.0;
			Centroid maxDistCentroid = null;
			
			// iterating through all the data points
			// and find the data point with the max
			// distance from all the centroids
			// (not just farthest from one centroid)
			for(DataPoint d : csv.getRows()) {
				double sum = 0.0;
				for(Centroid c : newCent) {
					sum += distanceFunction.getDistance(c, d);
				}
				
				// if the current centroid is further
				// than the current, do swap
				if (sum > max) {
					maxDistCentroid = new Centroid(d);
					max = sum;
				}
			}
			
			// adding the furthest centroid
			// to the current list of centroids
			newCent.add(maxDistCentroid);
		}
		return newCent;
	}
	
	public ArrayList<DataPoint> getAllDataPoints() {
		return csv.getRows();
	}
	
	// get a centroid by its index
	public Centroid getCentroid(int idx) {
		if (idx < 0 || idx >= K) throw new IndexOutOfBoundsException("No such centroid");
		return centroids.get(idx);
	}
	// return the collection of the centroids
	public ArrayList<Centroid> getCentroids() {
		return centroids;
	}
	
	public int getK() {
		return K;
	}
	
	public int getIteration() {
		return iteration;
	}
	
	public void reset() {
		iteration = 0;
		if (this.centroidInitFunction == Centroid.Init.FURTHEST_FIRST)
			centroids  = furthestFirstCentroidsInit();
		else
			centroids = randomCentroidsInit();
	}
	
	//The main algorithm of the K-Means clustering.
	public ArrayList<Centroid> runClustering() {
		// initializing the temporary centroids and
		// other variables for comparing purposes.
		reset();
		Centroid [] initCentroids = new Centroid[this.K];
		double centroidsMovement = 0.0;
		int idx;
		ArrayList<Double> allDistance = new ArrayList<Double>();
		
		// main loop of the clustering algorithm
		do {
			
			// emptying centroid members
			for(Centroid c : getCentroids()) {
				c.deleteAllMembers();				
			}
			
			// cloning the current centroids
			// as we need initCentroids to later compare the movement
			// of all centroids
			for (int i = 0; i < this.K; i++) {
				initCentroids[i] = new Centroid(centroids.get(i));
			}
			
			// looping through all data points
			// to assign them to the closest centroid
			for (DataPoint obs : getAllDataPoints()) {	
				
				for(Centroid cent : getCentroids() ) {
					allDistance.add(distanceFunction.getDistance(obs, cent)/*calcDistance(obs, cent)*/);
				}
				
				// taking the index of the closest centroid				
				int centroidIndexDataPointAssignedTo = allDistance.indexOf(Collections.min(allDistance));

				// assigning the data point to the centroid
				getCentroid(centroidIndexDataPointAssignedTo).addMember(obs);
				
				// deleting all the distance for the next data point
				allDistance.clear();
			}
			
			// preparing for checking whether the centroids move from previous position 
			// this is where the initCentroids variable becomes handy.
			centroidsMovement = 0.0;
			idx = 0;
			
			// looping through all the centroids and do the following steps
			// (1) calculate the new position of a centroid based on the
			//     current data points that are assigned to it
			// (2) calculate the distance between the old position and 
			//     the new position and later this will be checked as
			//     one of the terminating criteria
			for(Centroid c : getCentroids()) {
				c.calculateNewPosition();
				centroidsMovement += distanceFunction.getDistance(initCentroids[idx], c);
				idx++;
			}
			
		// the terminating criterion is either: 
		// (1) the centroids no longer move OR
		// (2) the maxIteration parameter has been exceeded
		} while(centroidsMovement > 0.0 && ++this.iteration < this.maxIteration);
		return centroids;
	}	
} /** End of KMeans.java **/