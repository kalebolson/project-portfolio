import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.opencsv.CSVWriter;

public class Main {
	public static void main(String args[]) {
		MultiThreadingReader halfwaydown = new MultiThreadingReader("middledown",500000,500000,16);
		MultiThreadingReader topdown = new MultiThreadingReader("topdown",1,500000,16);
		
		File file = new File("./Sales Records With Tax.csv");
		CSVWriter writer;
		try {
			writer = new CSVWriter(new FileWriter(file));
			String[] header = { "Region", "Country", "Item Type","Sales Channel","Order Priority","Order Date",
	        		"Order ID", "Ship Date","Units Sold", "Unit Price", "Unit Cost", "Total Revenue","Total Cost",
	        		"Total Profit","Tax Rate","Total Tax Paid"}; 
	        writer.writeNext(header); 
	        writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		
		topdown.start();
		halfwaydown.start();
	}

}
