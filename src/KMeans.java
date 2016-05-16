import java.util.ArrayList;
import java.util.Collections;


public class KMeans {
	// the collection of centroids
	private ArrayList<Centroid> centroids;
	
	// the CSV file object
	private CSV csv;
	
	// the K parameter (how many centroids)
	protected int K;
	
	// parameter to determine which distance
	// function to use 
	protected Distance.Func distanceFunction;
	
	// parameter control the maximum iteration
	private int maxIteration;
	
	// parameter to decide what centroid-initialization
	// function to use
	protected Centroid.Init centroidInitFunction;
	
	// parameter to count how many iterations
	// used in the clustering process
	private int iteration;
	
//	private ArrayList<Integer> initialCentroidIdx;
	
	/**
	 * The main constructor of KMeans object. It accepts CSV object, the K,
	 * distance function parameter, maximum iteration, and centroid initialization
	 * procedure
	 * @param csv
	 * @param K
	 * @param distanceFunction
	 * @param maxIteration
	 * @param centroidInitFunction
	 */
	public KMeans(CSV csv, int K, Distance.Func distanceFunction, int maxIteration, Centroid.Init centroidInitFunction) {
		// setting up the KMeans object's properties
		this.csv = csv;
		this.K = K;
		this.distanceFunction = distanceFunction;
		this.maxIteration = maxIteration;

		
		// choosing the centroid initialization function:
		// either fur
		this.centroidInitFunction = (centroidInitFunction == Centroid.Init.FURTHEST_FIRST ?
				Centroid.Init.FURTHEST_FIRST : Centroid.Init.RANDOM);
		
		reset();
			
	}
	
	/**
	 * Method to generate one random centroids.
	 * @return random centroid from one of the available data points
	 */
	private Centroid getRandomCentroidFromDataPoints() {
		int size = csv.getRows().size();
		int idx = (int) (Math.random() * size);
		return new Centroid(csv.getRow(idx));
	}
	
	/**
	 * The random centroid initialization method
	 */
	public void randomCentroidsInit() {		
		for(int i = 0; i < K; i++) {
			Centroid chosen = getRandomCentroidFromDataPoints();
			
			if(!centroids.contains(chosen))
				centroids.add(i, chosen);
			else 
				i--;
		}
	}
	
	/**
	 * The furthest-first centroid initialization method 
	 */
	public void furthestFirstCentroidsInit() {
		// STEP 1: adding the first, random centroid
		centroids.add(getRandomCentroidFromDataPoints());
		
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
				for(Centroid c : centroids) {
					sum += distanceFunction.getDistance(c, d);//calcDistance(c, d);
				}
				
				if (sum > max) {
					maxDistCentroid = new Centroid(d);
					max = sum;
				}
			}
			
			// adding the furhtest centroid
			// to the current list of centroids
			centroids.add(maxDistCentroid);
		}
	}
	
	/**
	 * This method is to calculate the distance of two data points.
	 * The distance function is chosen based on the selected function
	 * in the initial construction of the object.
	 * 
	 * @param d1 the first data point to compare 
	 * @param d2 the second data point to compare
	 * @return   the distance between the two data points
	 */
//	private double calcDistance(DataPoint d1, DataPoint d2) {
//		if (distanceFunction == Distance.Func.MANHATTAN)
//			return Distance.ManhattanDist(d1, d2);
//		if(distanceFunction == Distance.Func.COSINE)
//			return Distance.CosineDist(d1, d2);
//		
//		return Distance.EuclideanDist(d1, d2);	
//	}
	
	/**
	 * Returns all the data points contained in the CSV file
	 * @return all the data points in csv variable
	 */
	public ArrayList<DataPoint> getAllDataPoints() {
		return csv.getRows();
	}
	
	/**
	 * Returns the centroid of the defined index
	 * @param idx the index of the centroid within the collection
	 * @return    the centroid within the index
	 */
	public Centroid getCentroid(int idx) {
		if (idx < 0 || idx >= K) throw new IndexOutOfBoundsException("No such centroid");
		return centroids.get(idx);
	}
	/**
	 * Returns the list of the centroids.
	 * @return list of centroids (ArrayList data-type)
	 */
	public ArrayList<Centroid> getCentroids() {
		return centroids;
	}
	
	/**
	 * Returns the amount of K defined during the initialization process. 
	 * @return the K parameter of the object
	 */
	public int getK() {
		return K;
	}
	
	/**
	 * Returns the iteration parameter. The iteration is 
	 * incremented during the execution of runClustering().
	 * @return the iteration parameter of the KMeans object
	 */
	public int getIteration() {
		return iteration;
	}
	
	/**
	 * The main algorithm of the K-Means clustering.
	 */
	public void runClustering() {
		// initializing the temporary centroids and
		// other variables for comparing purposes.
		reset();
		Centroid [] initCentroids = new Centroid[this.K];
		double centroidsMovement = 0.0;
		int idx;
		ArrayList<Double> allDistance = new ArrayList<Double>();
		
		// main loop of the clustering algorithm
		do {
			/** debugging message **/
//			System.out.println("Iteration " + iteration + ":");
			/** end of debugging message **/
			
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
				centroidsMovement += distanceFunction.getDistance(initCentroids[idx], c);//calcDistance(initCentroids[idx], c);
				/** Debugging message **/
//				System.out.println("Distance " + idx + "=" + calcDistance(initCentroids[idx], c));
//				System.out.println(initCentroids[idx].getFeatures());
//				System.out.println(c.getFeatures());
				/** end of debugging message**/
				idx++;
			}
			
//			System.out.println();
		
		// the terminating criteria either: 
		// (1) the centroids no longer move OR
		// (2) the maxIteration parameter has been exceeded
		} while(centroidsMovement > 0.0 && ++this.iteration < this.maxIteration);
	}

	public void reset() {
		iteration = 0;
		centroids = new ArrayList<Centroid>();
		
		// initalizing centroids initialization function
		// either furthest-first or random 
		if (this.centroidInitFunction == Centroid.Init.FURTHEST_FIRST)
			furthestFirstCentroidsInit();
		else
			randomCentroidsInit();
	}
	
}
