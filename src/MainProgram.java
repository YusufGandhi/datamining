/** File: MainProgram.java **/

public class MainProgram {
	public static void main(String[] args) {
		CSV csv = new CSV("forestfires.normalized.final.csv"); // the CSV file
		// KMeans initialization
		KMeans km = new KMeans(csv /* the CSV file */, 
								2  /* number of centroids */, 
								Distance.Func.EUCLIDEAN /* distance function*/, 
								100 /* max iteration */, 
								Centroid.Init.RANDOM/* centroid initialization function */);
		
		km.runClustering();
	}
} /** End of MainProgram.java **/