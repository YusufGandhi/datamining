import java.math.BigDecimal;


public class Distance {
	public enum Func {
		EUCLIDEAN {
			public double getDistance(DataPoint obs1, DataPoint obs2) {
				if(!IsCompatible(obs1,obs2)) throw new UnsupportedOperationException("Observations don't match");
				double distance = 0.0;
				for (Integer i : obs1.getNumIndex()) {
					distance += ((Double) obs1.getFeatures().get(i) - (Double) obs2.getFeatures().get(i)) *
							((Double) obs1.getFeatures().get(i) - (Double) obs2.getFeatures().get(i));
				}
				return new BigDecimal(Math.sqrt(distance)).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
			}
		}, MANHATTAN {
			public double getDistance(DataPoint obs1, DataPoint obs2) {
				if(!IsCompatible(obs1,obs2)) throw new UnsupportedOperationException("Observations don't match");
				double distance = 0.0;
				
				// only iterating through the numeric index
				for (Integer i : obs1.getNumIndex()) {
					distance += Math.abs((Double) obs1.getFeatures().get(i) - (Double) obs2.getFeatures().get(i));
				}
				return distance;
			}
		}, COSINE {
			public double getDistance(DataPoint obs1, DataPoint obs2) {
				if(!IsCompatible(obs1,obs2)) throw new UnsupportedOperationException("Observations don't match");
				BigDecimal result = new BigDecimal(1.0 - (dotProduct(obs1, obs2) / (obs1.getMagnitude() * obs2.getMagnitude())));
				return result.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
			}
		};
		
		abstract double getDistance(DataPoint obs1, DataPoint obs2);
	}
//	public static final int EUCLIDEAN = 0;
//	public static final int MANHATTAN = 1;
//	public static final int COSINE = 2;
	
	/**
	 * The function to calculate Manhattan distance between two DataPoint objects.
	 * @param obs1 the first DataPoint object
	 * @param obs2 the second DataPoint object
	 * @return     Manhattan distance between the first and second DataPoint
	 */
	public final static double ManhattanDist(DataPoint obs1, DataPoint obs2) {
		if(!IsCompatible(obs1,obs2)) throw new UnsupportedOperationException("Observations don't match");
		double distance = 0.0;
		
		// only iterating through the numeric index
		for (Integer i : obs1.getNumIndex()) {
			distance += Math.abs((Double) obs1.getFeatures().get(i) - (Double) obs2.getFeatures().get(i));
		}
		return distance;
	}
	
	/**
	 * The function to calculate Euclidean distance of two DataPoint objects
	 * @param obs1 the first DataPoint object
	 * @param obs2 the second DataPoint object
	 * @return     Euclidean distance of the first and second DataPoint
	 */
	public final static double EuclideanDist(DataPoint obs1, DataPoint obs2) {
		if(!IsCompatible(obs1,obs2)) throw new UnsupportedOperationException("Observations don't match");
		double distance = 0.0;
		for (Integer i : obs1.getNumIndex()) {
			distance += ((Double) obs1.getFeatures().get(i) - (Double) obs2.getFeatures().get(i)) *
					((Double) obs1.getFeatures().get(i) - (Double) obs2.getFeatures().get(i));
		}
		return new BigDecimal(Math.sqrt(distance)).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	/**
	 * Cosine Similarity is derived from this reference: https://en.wikipedia.org/wiki/Cosine_similarity
	 * The function returns the cosine similarity of two DataPoint objects
	 * @param obs1 the first DataPoint object
	 * @param obs2 the second DataPoint object
	 * @return     Cosine similarity (distance) of the first and second DataPoint
	 */
	public final static double CosineDist(DataPoint obs1, DataPoint obs2) {
		if(!IsCompatible(obs1,obs2)) throw new UnsupportedOperationException("Observations don't match");
		BigDecimal result = new BigDecimal(1.0 - (dotProduct(obs1, obs2) / (obs1.getMagnitude() * obs2.getMagnitude())));
		return result.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	/**
	 * Dot product of a two DataPoint is derived from this reference:
	 * https://en.wikipedia.org/wiki/Euclidean_vector#Dot_product
	 * The function returns the dot product of two DataPoint objects
	 * @param obs1 the first DataPoint object
	 * @param obs2 the second DataPoint object
	 * @return     the dot product of the first and second DataPoint objects
	 */
	private final static double dotProduct(DataPoint obs1, DataPoint obs2) {
		if(!IsCompatible(obs1,obs2)) throw new UnsupportedOperationException("Observations don't match");
		
		double sum = 0.0;
		for(Integer i : obs1.getNumIndex()) {
			sum += (Double) obs1.getFeatures().get(i) * (Double) obs2.getFeatures().get(i); 
		}
		return sum;
	}
	
	/**
	 * The function checks whether two DataPoiint objects are compatible
	 * (read: have the dimension and the same type of features for each dimension)
	 * @param o1 the first DataPoint object
	 * @param o2 the second DataPoint object
	 * @return   true if both DataPoints have the same features (length and type); false otherwise
	 */
	private static boolean IsCompatible(DataPoint o1, DataPoint o2) {
		if(o1.featureSize() != o2.featureSize()) return false;

		for(int i = 0; i < o1.featureSize(); i++) {
			if(!o1.getFeatures().get(i).getClass().toString().equals(
					o2.getFeatures().get(i).getClass().toString()))
				return false;
		}
		return true;
	}
	
}
