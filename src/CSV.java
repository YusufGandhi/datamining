/** File: CSV.java **/
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CSV {
	// variable in which the data set is saved
	private ArrayList<DataPoint> rows;
	
	public CSV(String fileName) {
		rows = new ArrayList<DataPoint>();
		extractCSV(fileName);
	}
	
	// The main algorithm to extract the data points in the CSV.
	private void extractCSV(String fileName)  {
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String strLine;
			while((strLine = br.readLine()) != null) {
				rows.add(new DataPoint(strLine));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// method to retrieve all rows of the dataset
	public ArrayList<DataPoint> getRows() {
		return rows;
	}
	// method to retrieve a certain row (idx)
	public DataPoint getRow(int idx) {
		return rows.get(idx);
	}
}
