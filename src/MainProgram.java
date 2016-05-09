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
		
		// KMeans initialization
		KMeans km = new KMeans(csv /* the CSV file */, 
								4 /* number of centroids */, 
								Distance.EUCLIDEAN /* distance function*/, 
								100 /* max iteration */, 
								Centroid.FURTHEST_FIRST_CENTROID_INIT/* centroid initialization function */);
		
		System.out.println("Chosen centroids:");
		for(Centroid c : km.getCentroids()) {
			System.out.println(c.getFeatures() + " (Members: " + c.getAllMembers().size() + ")");
		}
		
		km.runClustering();
		
		System.out.println("Final centroids");
		for(Centroid c : km.getCentroids()) {
			System.out.println(c.getFeatures() + " (Members: " + c.getAllMembers().size() + ")");
		}
		System.out.println("Iteration: " + km.getIteration());
		
		
		
	}

}
