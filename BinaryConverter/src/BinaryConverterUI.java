/*Binary Converter assignment - SEIS 602
Student: Kaleb Olson
This program will convert numbers or strings to binary,
it will also convert binary input into numbers. The second
equation gets a bit more complicated in order to accomodate
for the examples provided, in which binary values such as 110.101
may be converted as well*/


import java.util.Scanner;
public class BinaryConverterUI {
	public static void main(String args[]) {
		char type;
		String string;
		String number;
		String binary;
		Scanner scan = new Scanner(System.in);
		BinaryConverter conv = new BinaryConverter();
		
		do {
			System.out.println("Binary Converter:\n"
					+ "Enter 's' to convert a string\n"
					+ "Enter 'n' to convert a number\n"
					+ "Enter 'b' to convert from binary\n");
			type = scan.next().charAt(0);
		} while (type!='s' && type!='n' && type!='b');
		
		if (type=='b') {
			System.out.println("Enter your binary value:");
			scan.nextLine();
			binary = scan.nextLine();
			float result = conv.from_binary(binary);
			System.out.println("Result: "+result);
		}
		
		if (type=='n') {
			System.out.println("Enter your numerical value:");
			scan.nextLine();
			number = scan.nextLine();
			double result = conv.from_number(number);
			System.out.println("Result: "+result);
		}
		
		if (type=='s') {
			System.out.println("Enter your string value:");
			scan.nextLine();
			string = scan.nextLine();
			String result = conv.from_string(string);
			System.out.println("Result: "+result);
		}
		
	}
	
}
