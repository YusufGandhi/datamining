import java.util.ArrayList;
import java.util.Collections;


public class MainProgram {
	public static void main(String[] args) {

		CSVContainer csv = new CSVContainer("test.csv");
		
		KMeans km = new KMeans(2 /* number of centroids */, csv);
		System.out.println("Chosen centroids:");
		System.out.println(km.getCentroid(0).getFeatures());
		System.out.println(km.getCentroid(1).getFeatures());
		
		System.out.println();
		
		for(CSVContainer.Observation o : csv.getRows()) {
			
			for(int i = 0; i < o.featureSize(); i++)
				System.out.print(o.getFeatureValue(i) + "(" + o.getFeatureValue(i).getClass().toString() + ") " );
			System.out.println();
		}
		
		System.out.println();
		System.out.println("\nDistance between 2 and 3:");
		System.out.println("Euclid: " + Distance.EuclideanDist(csv.getRow(2), csv.getRow(3)));
		System.out.println("Manh  : " + Distance.ManhattanDist(csv.getRow(2), csv.getRow(3)));

		
		KMeans.Centroid [] initCentroids = new KMeans.Centroid[km.getK()];
		boolean centroidsMoved = true;
		double centroidsMovement = 0.0;
		int idx;
		ArrayList<Double> allDistance = new ArrayList<Double>();
		int iteration = 1;
		do {
			System.out.println("Iteration " + iteration++ + ":");
			
			// copying the current centroids
			// initCentroids can be instance of Observation
			// as we need initCentroids to later compare the movement
			// of any centroid
			for (int i = 0; i < km.getK(); i++) {
				initCentroids[i] = new KMeans.Centroid(km.getCentroid(i));
			}
			
			// assigning all the observation to the closest centroid
			for (CSVContainer.Observation obs : km.getObservations()) {
				
				
				idx = 0;
				for(KMeans.Centroid cent : km.getCentroids() ) {
					allDistance.add(Distance.EuclideanDist(obs, cent));
					idx++;
				}
				
				int centIndexObsAssignedTo = allDistance.indexOf(Collections.min(allDistance));
				allDistance.clear();
				
				km.getCentroid(centIndexObsAssignedTo).addMember(obs);
			}
			
			idx = 0;
			centroidsMovement = 0.0;
			for(KMeans.Centroid c : km.getCentroids()) {
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
