//Calculates loan payment information based off 3 of 4 specified values
//
//Kaleb Olson
//SEIS 602

import java.lang.Math;
import java.text.DecimalFormat;

public class FinanceCalculator {
	private double loanAmount;
	private int numPayments;
	private float rate;
	private double payment;
	static DecimalFormat df = new DecimalFormat(".00");
	
	public FinanceCalculator() {
	}
	
	// Formula Source: https://www.calculatorsoup.com/calculators/financial/loan-calculator.php
	
	// calculate loan amount
	public double Calculate(int numPayments, float rate, double payment) {
		if (rate == 0) {
			this.loanAmount = numPayments * payment;
		} else {
		// dividing interest rate by 12 first
		float i = (float) ((rate * .01) / 12.0);
		this.loanAmount = (payment / i) * (1 - (1 / Math.pow((1 + i), numPayments)));
		}
		return loanAmount;
	}


    //calculate number of payments 
	public int Calculate(double loanAmount, float rate, double payment) { 
		if (rate == 0) {
			this.numPayments = (int) (loanAmount / payment);
		} else {
		// dividing interest rate by 12 first
		float i = (float) ((rate * .01) / 12.0);
		// payment/interest is used twice in this formula, so getting that first as well
		float x = (float)payment/i;
		
		//officially casting this to int. In the future, for practical purposes,
		//it might be a good idea to add 1 to the result, in the instance that
		//there is a remainder that would otherwise be dropped in casting.
		this.numPayments = (int) (Math.log(x/(x-loanAmount))/Math.log(1+i));
		}
		return numPayments;
	}
  
    //calculate payment amount 
	public String Calculate(double loanAmount, int numPayments, float rate) { 
		String stringPayment;
		if (rate == 0) {
			this.payment = loanAmount / numPayments;
			//check if the payment amount*number of payments is less than loan amount
			//converting to int value of cents to simplify math.
			int paymentParse =(int)(this.payment*100);
			int allPaymentCents = (int)(paymentParse*numPayments);
			if (allPaymentCents<(int)(loanAmount*100)) {
				int lastPaymentCents = paymentParse;
				while ((paymentParse*(numPayments-1))+lastPaymentCents<(int)(loanAmount*100)) {
					lastPaymentCents++;
				}
				String lastPaymentDollars = (lastPaymentCents/100)+"."+(lastPaymentCents-(lastPaymentCents/100*100));
				stringPayment = (numPayments-1)+" payments of $"+df.format(payment)+" and 1 payment of $"+lastPaymentDollars;
				
			} else {
				stringPayment = df.format(payment);
			}
		} else {
		// dividing interest rate by 12 first
		float i = (float) ((rate * .01) / 12.0);
		// (1+i)^numPayments is used twice in this formula, so getting that first as well
		float x = (float)Math.pow((1+i),numPayments);
		this.payment = (loanAmount*i*x)/(x-1);
		stringPayment = df.format(payment);
		}
		return stringPayment;
	} 
	
	  
    //calculate interest rate 
	public float Calculate(double loanAmount, int numPayments, double payment) { 
		float guess = (float) ((2*((numPayments*payment)-loanAmount))/(numPayments*loanAmount));
		final double DIFF_CONST = .000000001;
		double x = init_function(guess,loanAmount,numPayments,payment) / deriv_function(guess,loanAmount,numPayments,payment);
		while (Math.abs(x) >= DIFF_CONST) {
			x = init_function(guess,loanAmount,numPayments,payment) / deriv_function(guess,loanAmount,numPayments,payment);
			guess=(float) (guess-x);
		}
		guess = (float) (Math.round(guess*100000.0)/100000.0);
		return (float) (guess*1200.0);
	}
	
	
	//additional equations to assist with interest rate calculation
	//We will need to utilize the Newton/Raphson method here
	//using functions from https://math.stackexchange.com/questions/622643/how-to-calculate-apr-using-newton-raphson
	//ignoring fee, as it is not a part of this assignment
	private static double init_function(float guess, double loanAmount, int numPayments, double payment) {
		return loanAmount*guess*(1+guess)-payment*(1+guess-Math.pow((1+guess),(1-numPayments)));
	}
	
	private static double deriv_function(float guess, double loanAmount, int numPayments, double payment) {
		return loanAmount*(1+2*guess)-payment*(1-(numPayments-1)*Math.pow((1+guess),-numPayments));
	}
	
	//experimenting with alternative equations, they don't seem to work as well
//	private static double init_function(float guess, double loanAmount, int numPayments, double payment) {
//		double result;
//		result = ((loanAmount*guess*Math.pow((1+guess), numPayments)) / (Math.pow((1+guess), numPayments)-1))-payment;
//		return result;
//	}
//	
//	private static double deriv_function(float guess, double loanAmount, int numPayments, double payment) {
//		double result;
//		result = (loanAmount*Math.pow((guess+1), (numPayments-1))*(guess*Math.pow(guess+1, numPayments)+Math.pow(guess+1, numPayments)-(numPayments*guess)-guess-1)) / Math.pow((Math.pow(guess+1, numPayments)-1), 2);
//		return result;
//	}
	
}
