import java.text.DecimalFormat;


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
		long start = System.currentTimeMillis();
		Runtime runtime = Runtime.getRuntime();
		long usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();
		System.out.println("Used memory before: " + usedMemoryBefore);
		
		CSV csv = new CSV("test.csv");
//		CSV csv = new CSV("abalone.data.norm.csv");
//		CSV csv = new CSV("forestfires.normalized.final.csv");
		
		// KMeans initialization
		KMeans km = new KMeans(csv /* the CSV file */, 
								3 /* number of centroids */, 
								Distance.Func.MANHATTAN /* distance function*/, 
								100 /* max iteration */, 
								Centroid.Init.FURTHEST_FIRST/* centroid initialization function */);
		
		System.out.println("Chosen centroids:");
		for(Centroid c : km.getCentroids()) {
			System.out.println(c.getFeatures() + " (Members: " + c.getAllMembers().size() + ")");
		}
		
		
		// let's execute this several times
		
		km.runClustering();
		
		//
		System.out.println("Final centroids");
		for(Centroid c : km.getCentroids()) {
			System.out.println(c.getFeatures() + " (Members: " + c.getAllMembers().size() + ")");
		}
		System.out.println("Iteration: " + km.getIteration());
		long usedMemoryAfter = runtime.totalMemory() - runtime.freeMemory();
		long end = System.currentTimeMillis();
		System.out.println("Memory increased: " + new DecimalFormat("###,###").format(usedMemoryAfter - usedMemoryBefore));
		System.out.println("Exeucation time: " + (end - start) + " ms");
		
		
	}

}
