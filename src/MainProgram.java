/** File: MainProgram.java **/
import java.util.ArrayList;

public class MainProgram {
	public static void main(String[] args) {
		CSV csv = new CSV("forestfires.normalized.final.csv"); // the CSV file
		// KMeans initialization
		KMeans km = new KMeans(csv /* the CSV file */, 
								6  /* number of centroids */, 
								Distance.Func.EUCLIDEAN /* distance function*/, 
								100 /* max iteration */, 
								Centroid.Init.RANDOM/* centroid initialization function */);
		
		ArrayList<Centroid> centroids = km.runClustering();
		int i = 1;
		for(Centroid c : centroids) {
			System.out.println("Centroid #" + i++ + ": " +c);
		}
		System.out.println("Iteration: " + km.getIteration());
	}
} /** End of MainProgram.java **/