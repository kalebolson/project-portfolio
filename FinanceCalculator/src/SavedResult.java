import java.text.DecimalFormat;

//Stores the saved results for Finance Calculator for later printing
//
//Kaleb Olson
//SEIS 602

public class SavedResult {
	private DecimalFormat df = new DecimalFormat(".00");
	private String calctype;
	private String loanAmount;
	private int numPayments;
	private float rate;
	private String payment;
	
	public SavedResult (String type, double loanAmount, int numPayments, float rate, double payment){
		this.calctype = type;
		this.loanAmount = df.format(loanAmount);
		this.numPayments = numPayments;
		this.rate = rate;
		this.payment = df.format(payment);
	}
	
	public SavedResult (String type, double loanAmount, int numPayments, float rate, String payment){
		this.calctype = type;
		this.loanAmount = df.format(loanAmount);
		this.numPayments = numPayments;
		this.rate = rate;
		this.payment = payment;
	}
	
	@Override
	public String toString() {
		String string = "Calculated for "+calctype+": \n"
				+"Principal Amount - $"+loanAmount+"\n"
				+"Number of Payments - "+numPayments+"\n"
				+"Interest Rate - "+rate+"% \n"
				+"Payment Amount - $"+payment+"\n";
		return string;
	}
	
}
