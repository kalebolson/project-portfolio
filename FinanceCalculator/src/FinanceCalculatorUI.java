//PRIMARY USER INTERFACE FOR THE FINANCE CALCULATOR
//
//KALEB OLSON
//SEIS 602

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.text.DecimalFormat;

public class FinanceCalculatorUI {
	static ArrayList<SavedResult> saved_results = new ArrayList<SavedResult>(0);
	static FinanceCalculator calc = new FinanceCalculator();
	static DecimalFormat df = new DecimalFormat(".00");
	
	public static void main(String args[]) throws FileNotFoundException {
		
		Boolean running = true;
		Scanner input = new Scanner(System.in);
		String entry;

		//simply instantiating all menu strings here to avoid making all
		//expressions throw FileNotFoundException
		String mainmenu = string_from_file(new File("./src/mainmenu"));
		String aprmenu = string_from_file(new File("./src/aprmenu"));
		String monthlyamountmenu = string_from_file(new File("./src/monthlyamountmenu"));
		String numpaymentsmenu = string_from_file(new File("./src/numpaymentsmenu"));
		String principalamountmenu = string_from_file(new File("./src/principalamountmenu"));

		while (running) {
			System.out.println(mainmenu);
			entry = input.nextLine();
			
			if (entry.equals("1")) {
				calc_monthlyamount(monthlyamountmenu);
			} else if (entry.equals("2")) {
				calc_apr(aprmenu);
			} else if (entry.equals("3")) {
				calc_numpayments(numpaymentsmenu);
			} else if (entry.equals("4")) {
				calc_principal(principalamountmenu);
			} else if (entry.equals("5")) {
				print_saved();
			} else if (entry.equals("x") || entry.equals("X")) {
				running = false;
			} else System.out.println("Invalid entry " + entry);
		}
	}
	
	private static void print_saved() {
		System.out.println("\n\n\n");
		if (saved_results.size() != 0) {
			for (int i=0; i<saved_results.size(); i++) {
				System.out.println("Query number "+(i+1)+"\n"+saved_results.get(i)+"\n\n");
			}
		} else System.out.println("No saved results to show");
	}

	private static void calc_principal(String menu) {
		double loanAmount=0.0;
		int numPayments=0;
		float rate=0;
		double payment=0.0;
		Boolean in_menu = true;
		Scanner input = new Scanner(System.in);
		Scanner input2 = new Scanner(System.in);
		String entry;
		Boolean can_save = false;
		
		while (in_menu) {
			System.out.println(menu);
			entry = input.nextLine();

			if (entry.equals("1")) {
				System.out.println("Enter monthly amount");
				try {
				  payment = input2.nextDouble();
				} catch (InputMismatchException e) {
					System.out.println("Invalid input");
					input2.next(); // this line is here to move the pointer in the scanner forward
				}
			} 
			else if (entry.equals("2")) {
				System.out.println("Enter APR");
				try {
					rate = input2.nextFloat();
				} catch (InputMismatchException e) {
					System.out.println("Invalid input");
					input2.next();
				}
			} 
			else if (entry.equals("3")) {
				System.out.println("Enter number of months");
				try {
					numPayments = input2.nextInt();
				} catch (InputMismatchException e) {
					System.out.println("Invalid input");
					input2.next();
				}
			} 
			else if (entry.equals("4")) {
				if (input_valid(1,numPayments,rate,payment)) {
					loanAmount = calc.Calculate(numPayments, rate, payment);
					System.out.println("$"+df.format(loanAmount));
					can_save=true;
				} else System.out.println("Requirements to calculate not satisfied");
			} 
			else if (entry.equals("5")) {
				if (can_save) {
					saved_results.add(new SavedResult("Principal Amount" , loanAmount, numPayments, rate, payment));
				} else System.out.println("Unable to save, no calculation has been done.");
			} 
			else if (entry.equals("x") || entry.equals("X")) {
				in_menu = false;
			} 
			else System.out.println("Invalid entry");
		}
	}

	private static void calc_numpayments(String menu) {
		double loanAmount=0.0;
		int numPayments=0;
		float rate=0;
		double payment=0.0;
		Boolean in_menu = true;
		Scanner input = new Scanner(System.in);
		Scanner input2 = new Scanner(System.in);
		String entry;
		Boolean can_save=false;
		
		while (in_menu) {
			System.out.println(menu);
			entry = input.nextLine();
			if (entry.equals("1")) {
				System.out.println("Enter Monthly Amount");
				try {
					payment = input2.nextDouble();
				} catch (InputMismatchException e) {
					System.out.println("Invalid input");
					input2.next();
				}
			} 
			else if (entry.equals("2")) {
				System.out.println("Enter APR");
				try {
					rate = input2.nextFloat();
				} catch (InputMismatchException e) {
					System.out.println("Invalid input");
					input2.next();
				}
			} 
			else if (entry.equals("3")) {
				System.out.println("Enter Principal Amount");
				try {
					loanAmount = input2.nextDouble();
				} catch (InputMismatchException e) {
					System.out.println("Invalid input");
					input2.next();
				}
			} 
			else if (entry.equals("4")) {
				if (input_valid(loanAmount,2,rate,payment)) {
					numPayments = calc.Calculate(loanAmount, rate, payment);
					System.out.println(numPayments);
					can_save=true;
				} else System.out.println("Requirements to calculate not satisfied.");
			} 
			else if (entry.equals("5")) {
				if (can_save) {
					saved_results.add(new SavedResult("Number of Payments" , loanAmount, numPayments, rate, payment));
				} else System.out.println("Unable to save, no calculation has been done");
			} 
			else if (entry.equals("x") || entry.equals("X")) {
				in_menu = false;
			} 
			else System.out.println("Invalid entry");
		}
	}

	private static void calc_apr(String menu) {
		double loanAmount=0.0;
		int numPayments=0;
		float rate=0;
		double payment=0.0;
		Boolean in_menu = true;
		Scanner input = new Scanner(System.in);
		Scanner input2 = new Scanner(System.in);
		String entry;
		Boolean can_save=false;
		
		while (in_menu) {
			System.out.println(menu);
			entry = input.nextLine();
			if (entry.equals("1")) {
				System.out.println("Enter Monthly Amount");
				try {
					payment = input2.nextDouble();
				} catch (InputMismatchException e) {
					System.out.println("Invalid input");
					input2.next();
				}
			} 
			else if (entry.equals("2")) {
				System.out.println("Enter Number of Months");
				try {
					numPayments = input2.nextInt();
				} catch (InputMismatchException e) {
					System.out.println("Invalid input");
					input2.next();
				}
			} 
			else if (entry.equals("3")) {
				System.out.println("Enter Principal Amount");
				try {
					loanAmount = input2.nextDouble();
				} catch (InputMismatchException e) {
					System.out.println("Invalid input");
					input2.next();
				}
			} 
			else if (entry.equals("4")) {
				if (input_valid(loanAmount,numPayments,1,payment)) {
					rate = calc.Calculate(loanAmount, numPayments, payment);
					System.out.println(rate);
					can_save=true;
				} else System.out.println("Requirements to calculate not satisfied.");
			} 
			else if (entry.equals("5")) {
				if (can_save) {
					saved_results.add(new SavedResult("APR" , loanAmount, numPayments, rate, payment));
				} else System.out.println("Unable to save, no calculation has been done");
			} 
			else if (entry.equals("x") || entry.equals("X")) {
				in_menu = false;
			} 
			else System.out.println("Invalid entry");
		}
	}

	private static void calc_monthlyamount(String menu) {
		double loanAmount=0.0;
		int numPayments=0;
		float rate=0;
		String payment="";
		Boolean in_menu = true;
		Scanner input = new Scanner(System.in);
		Scanner input2 = new Scanner(System.in);
		String entry;
		Boolean can_save=false;
		
		while (in_menu) {
			System.out.println(menu);
			entry = input.nextLine();
			
			if (entry.equals("1")) {
				System.out.println("Enter APR");
				try {
					rate = input2.nextFloat();
				} catch (InputMismatchException e) {
					System.out.println("Invalid input");
					input2.next();
				}	
			} 
			else if (entry.equals("2")) {
				System.out.println("Enter Number of Months");
				try {
					numPayments = input2.nextInt();
				} catch (InputMismatchException e) {
					System.out.println("Invalid input");
					input2.next();
				}
			} 
			else if (entry.equals("3")) {
				System.out.println("Enter Principal Amount");
				try {
					loanAmount = input2.nextDouble();
				} catch (InputMismatchException e) {
					System.out.println("Invalid input");
					input2.next();
				}
			} 
			else if (entry.equals("4")) {
				if (input_valid(loanAmount,numPayments,rate,1)) {
					payment = calc.Calculate(loanAmount, numPayments, rate);
					System.out.println(payment);
					can_save=true;
				} else System.out.println("Requirements to calculate not satisfied.");
			} 
			else if (entry.equals("5")) {
				if (can_save) {
					saved_results.add(new SavedResult("Monthly Payment" , loanAmount, numPayments, rate, payment));
				} else System.out.println("Unable to save, no calculation has been done");
			} 
			else if (entry.equals("x") || entry.equals("X")) {
				in_menu = false;
			} 
			else System.out.println("Invalid entry");
		}
	}

	private static String string_from_file(File file) throws FileNotFoundException {
		Scanner scan = new Scanner(file);
		String string = "";
		while (scan.hasNextLine()) {
			string += scan.nextLine()+"\n";
		}
		return string;
	}
	
	private static Boolean input_valid(double loanAmount, int numPayments, float rate, double payment) {
		//At the stage that this is called, one of the four values above will not have been calculated yet
		//The workaround I'm using is to simply send a value that will not trigger its respective if statement
		Boolean valid = true;
		if (loanAmount<=0) {
			System.out.println("Principal Amount cannot be 0 or negative.");
			valid = false;
		}
		if (numPayments<2 || numPayments>72) {
			System.out.println("Payments must be between 2 and 72");
			valid = false;
		}
		if (rate<0) {
			System.out.println("APR cannot be negative");
			valid = false;
		}
		if (payment<=0) {
			System.out.println("Payment amount cannot be 0 or negative");
			valid = false;
		}
		return valid;
	}
}