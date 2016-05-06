import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class CSV {
	private ArrayList<DataPoint> rows;
	private int observationSize;
	
	public CSV(String fileName) {
		rows = new ArrayList<DataPoint>();
		observationSize = rows.size();
		extractCSV(fileName);
	}
	
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
	
	public ArrayList<DataPoint> getRows() {
		return rows;
	}
	
	public DataPoint getRow(int idx) {
		return rows.get(idx);
	}
}
