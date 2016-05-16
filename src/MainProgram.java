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
		
		Runtime runtime = Runtime.getRuntime();
		
		
		
//		CSV csv = new CSV("test.csv");
//		CSV csv = new CSV("abalone.data.norm.csv");
		CSV csv = new CSV("forestfires.normalized.final.csv");
		
		// KMeans initialization
		KMeans km = new KMeans(csv /* the CSV file */, 
								2  /* number of centroids */, 
								Distance.Func.EUCLIDEAN /* distance function*/, 
								100 /* max iteration */, 
								Centroid.Init.RANDOM/* centroid initialization function */);
		
		System.out.println("Chosen centroids:");
		for(Centroid c : km.getCentroids()) {
			System.out.println(c.getFeatures() + " (Members: " + c.getAllMembers().size() + ")");
		}
		
		long usedMemoryBefore;
		long usedMemoryAfter;
		long start;
		long end;
		DecimalFormat df = new DecimalFormat("###,###");
		
		/** let's execute this several times **/
		for(Centroid.Init c : Centroid.Init.values()) {
			
			km.centroidInitFunction = c;
				
			for(Distance.Func d : Distance.Func.values()) {
				
				for(int j = 0; j < 3; j++) {
					
					km.distanceFunction = d;
					km.reset();
					runtime.gc();
//						System.out.println("Used memory before: " + usedMemoryBefore);
					
					
					start = System.currentTimeMillis();
					usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();
					
					km.runClustering();
					
					usedMemoryAfter = runtime.totalMemory() - runtime.freeMemory();
					end = System.currentTimeMillis();
					runtime.gc();
					
					System.out.print(km.getIteration() + "," + km.centroidInitFunction + "," + km.distanceFunction + "," +
							/*df.format*/(usedMemoryAfter - usedMemoryBefore) + /*" bytes,"*/"," + (end-start) /* +"ms"*/);
					for(Centroid cent : km.getCentroids()) {
						System.out.print("," + cent.getAllMembers().size());
					}
					System.out.println();
//						System.out.println("Memory increased: " + new DecimalFormat("###,###").format(usedMemoryAfter - usedMemoryBefore));
//						System.out.println("Exeucation time: " + (end - start) + " ms");
				}
			}
		}
		
		
		/** end of multi execution **/
		System.out.println("Final centroids");
		for(Centroid c : km.getCentroids()) {
			System.out.println(c.getFeatures() + " (Members: " + c.getAllMembers().size() + ")");
		}
		System.out.println("Iteration: " + km.getIteration());
		
		
		
		
	}

}
