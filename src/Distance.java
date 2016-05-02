
public class Distance {
	public final static double ManhattanDist(CSVContainer.Observation obs1, CSVContainer.Observation obs2) {
		if(!IsCompatible(obs1,obs2)) throw new UnsupportedOperationException("Observations don't match");
		double distance = 0.0;
		for (Integer i : obs1.getNumIndex()) {
			distance += Math.abs((Double) obs1.getFeatures().get(i) - (Double) obs2.getFeatures().get(i));
		}
		return distance;
	}
	
	public final static double EuclideanDist(CSVContainer.Observation obs1, CSVContainer.Observation obs2) {
		if(!IsCompatible(obs1,obs2)) throw new UnsupportedOperationException("Observations don't match");
		double distance = 0.0;
		for (Integer i : obs1.getNumIndex()) {
			distance += ((Double) obs1.getFeatures().get(i) - (Double) obs2.getFeatures().get(i)) *
					((Double) obs1.getFeatures().get(i) - (Double) obs2.getFeatures().get(i));
		}
		return Math.sqrt(distance);
	}
	
	public final static double CosineDist(CSVContainer.Observation obs1, CSVContainer.Observation obs2) {
		return 0.0;
	}
	
	private static boolean IsCompatible(CSVContainer.Observation o1, CSVContainer.Observation o2) {
		if(o1.featureSize() != o2.featureSize()) return false;

		for(int i = 0; i < o1.featureSize(); i++) {
			if(!o1.getFeatures().get(i).getClass().toString().equals(
					o2.getFeatures().get(i).getClass().toString()))
				return false;
		}
		return true;
	}
	
}
