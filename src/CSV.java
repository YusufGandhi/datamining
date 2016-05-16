/** File: CSV.java **/
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CSV {
	
	private ArrayList<DataPoint> rows;
	
	public CSV(String fileName) {
		rows = new ArrayList<DataPoint>();
		extractCSV(fileName);
	}
	/**
	 * The main algorithm to extract the data points in the CSV.
	 * Each line of the CSV represents one data point. Thus, each row
	 * is saved as one DataPoint object and added to the rows parameter.
	 * @param fileName the CSV filename in which the data points are defined
	 */
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
	
	/**
	 * Returns the collection of data points.
	 * @return all the data points
	 */
	public ArrayList<DataPoint> getRows() {
		return rows;
	}
	
	/**
	 * Returns the data point in the defined index
	 * @param idx the index of which the data point is going to be retrieved
	 * @return    the data point of the defined index
	 */
	public DataPoint getRow(int idx) {
		return rows.get(idx);
	}
}
