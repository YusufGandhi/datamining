
public class MainProgram {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CSVContainer csv = new CSVContainer("test.csv");
		for(CSVContainer.Observation o : csv.getRows()) {
//			if(o.getFeatureValue(0) instanceof Double) System.out.println("dobel");
//			else if(o.getFeatureValue(0) instanceof String) System.out.println("cetring");
			for(int i = 0; i < o.getNumFeatures(); i++)
				System.out.print(o.getFeatureValue(i) + "(" + o.getFeatureValue(i).getClass().toString() + ") " );
			System.out.println();
		}
		
	}

}
