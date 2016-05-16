/** File: Distance.java **/
import java.math.BigDecimal;

public class Distance {
	public enum Func {
		// the Euclidean distance representation and distance calculation function
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
		},
		// the Manhattan distance representation and the distance calculation function
		MANHATTAN {
			public double getDistance(DataPoint obs1, DataPoint obs2) {
				if(!IsCompatible(obs1,obs2)) throw new UnsupportedOperationException("Observations don't match");
				double distance = 0.0;
				
				for (Integer i : obs1.getNumIndex()) {
					distance += Math.abs((Double) obs1.getFeatures().get(i) - (Double) obs2.getFeatures().get(i));
				}
				return distance;
			}
		}, 
		// the Cosine distance representation and the distance calculation
		COSINE {
			public double getDistance(DataPoint obs1, DataPoint obs2) {
				if(!IsCompatible(obs1,obs2)) throw new UnsupportedOperationException("Observations don't match");
				double result = dotProduct(obs1, obs2) / 
						(obs1.getMagnitude() * obs2.getMagnitude());
				// error handling: return 1.0 when it gets infinite result
				if(Double.isInfinite(result) || Double.isNaN(result)) return 1.0;
				return new BigDecimal(1.0 - result).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
			}
		};
		
		abstract double getDistance(DataPoint obs1, DataPoint obs2);
	}
	
	// function to calculate the dot product of two vectors
	private final static double dotProduct(DataPoint obs1, DataPoint obs2) {
		if(!IsCompatible(obs1,obs2)) throw new UnsupportedOperationException("Observations don't match");
		
		double sum = 0.0;
		for(Integer i : obs1.getNumIndex()) {
			sum += (Double) obs1.getFeatures().get(i) * (Double) obs2.getFeatures().get(i); 
		}
		return sum;
	}
	
	// function to check whether the two data points are compatible
	private static boolean IsCompatible(DataPoint o1, DataPoint o2) {
		if(o1.featureSize() != o2.featureSize()) return false;

		for(int i = 0; i < o1.featureSize(); i++) {
			if(!o1.getFeatures().get(i).getClass().toString().equals(
					o2.getFeatures().get(i).getClass().toString()))
				return false;
		}
		return true;
	}	
} /** end of Distance.java **/