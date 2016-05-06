import java.util.ArrayList;
import java.util.Collections;


/**
 * NOTE MAY 3, 2016:
 * For categorical data, there's no right or wrong.
 * So for the assignment purpose, just use 0 if there's no difference and 1 if there's a difference
 * between the compared categorical data.
 * [NOT IMPLEMENTED]
 * 
 * @author yusufgandhi
 *
 */

public class MainProgram {
	public static void main(String[] args) {

		CSV csv = new CSV("test.csv");
		
		KMeans km = new KMeans(csv, 2 /* number of centroids */, 0, 1, 0);
		System.out.println("Chosen centroids:");
		System.out.println(km.getCentroid(0).getFeatures());
		System.out.println(km.getCentroid(1).getFeatures());
		
		System.out.println();
		
		for(DataPoint o : csv.getRows()) {
			
			for(int i = 0; i < o.featureSize(); i++)
				System.out.print(o.getFeatureValue(i) + "(" + o.getFeatureValue(i).getClass().toString() + ") " );
			System.out.println();
		}
		
		System.out.println();
		System.out.println("\nDistance between 2 and 3:");
		System.out.println("Euclid: " + Distance.EuclideanDist(csv.getRow(2), csv.getRow(3)));
		System.out.println("Manh  : " + Distance.ManhattanDist(csv.getRow(2), csv.getRow(3)));

		
		Centroid [] initCentroids = new Centroid[km.getK()];
		boolean centroidsMoved = true;
		double centroidsMovement = 0.0;
		int idx;
		ArrayList<Double> allDistance = new ArrayList<Double>();
		int iteration = 1;
		do {
			System.out.println("Iteration " + iteration++ + ":");
			
			// initCentroids can be instance of Observation
			// copying the current centroids
			// as we need initCentroids to later compare the movement
			// of all centroids
			for (int i = 0; i < km.getK(); i++) {
				initCentroids[i] = new Centroid(km.getCentroid(i));
			}
			
			// assigning all observations to the closest centroid
			for (DataPoint obs : km.getObservations()) {
				
				
				for(Centroid cent : km.getCentroids() ) {
					allDistance.add(Distance.EuclideanDist(obs, cent));
				}
				
				int centIndexObsAssignedTo = allDistance.indexOf(Collections.min(allDistance));
				allDistance.clear();
				
				km.getCentroid(centIndexObsAssignedTo).addMember(obs);
			}
			
			idx = 0;
			centroidsMovement = 0.0;
			for(Centroid c : km.getCentroids()) {
				System.out.println("Centroid #" + idx + " members: " + c.getAllMembers().size());
				c.printAllMembers();
				c.calculateNewPosition();
				centroidsMovement += Distance.ManhattanDist(initCentroids[idx], c);
				c.deleteAllMembers();
				idx++;
			}
			
			System.out.println();
		} while(centroidsMovement > 0.0);
		
		
		
		
		
	}

}
