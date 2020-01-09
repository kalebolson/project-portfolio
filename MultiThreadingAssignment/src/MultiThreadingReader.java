import java.time.format.DateTimeFormatter;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class MultiThreadingReader implements Runnable {
	private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
	private Thread thread;
	private String threadname;
	private int startline;
	private String[][] list;
	
	public MultiThreadingReader(String name, int start,int rows, int columns) {
		this.threadname = name;
		this.startline = start;
		list = new String[rows][columns];
	}
	
	public void start() {
		System.out.println("Starting thread "+threadname+" at "+dtf.format(LocalDateTime.now()));
		if (thread==null) {
			thread = new Thread(this,threadname);
			thread.start();
			
		}
	}
	
	
	public void run() {
		try { 
			//I'm throwing the @ sign in here as a quote limiter because I need it to be a character that
			//will not appear at all in the file. I ran into an issue using the suggested single quote
			//sign that ended up bunching everything between two apostrophes into one cell in the file.
		    CSVReader csvReader = new CSVReader(new FileReader("./1000000 Sales Records.csv"), ',', '@', startline); 
		    String[] nextRecord = null;
		    
		    for(int x=0; x<list.length; x++) {
		    	nextRecord = csvReader.readNext();
		    	String cell;
		        for (int y=0; y<list[x].length; y++) { 
		        	cell = nextRecord[y];
		        	list[x][y] = cell;
		        } 
		    }
		    csvReader.close();
		} 
		catch (Exception e) { 
		    e.printStackTrace(); 
		}
		
		addTaxInfo(6.9);
		
		write();
		
		System.out.println("Ending thread "+threadname+" at "+dtf.format(LocalDateTime.now()));
	}
	
	public String[][] getlist(){
		return list;
	}
	
	public void addTaxInfo(double rate) {
		double taxamount;
		double revenue;
		
		for (String[] x : list) {
			
			revenue = Double.valueOf(x[11]);
			taxamount = revenue*(rate/100);
			taxamount = taxamount*100;
			taxamount = (int)taxamount;
			taxamount = taxamount/100;
			x[14] = rate+"%";
			x[15] = taxamount+"";
		}	
	}
	
	public synchronized void write() {
		File file = new File("./Sales Records With Tax.csv"); 
	    try { 
	        CSVWriter writer = new CSVWriter(new FileWriter(file,true)); 
	        
	        for(String[] x : list){
	        	writer.writeNext(x);
	        }
	        writer.close(); 
	    } 
	    catch (IOException e) { 
	        e.printStackTrace(); 
	    }
	}
}

