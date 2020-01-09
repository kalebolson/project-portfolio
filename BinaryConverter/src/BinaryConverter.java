public class BinaryConverter {
	
	public float from_binary(String input) {
		//splitting input number to be handled separately before and after decimal
		String[] split_number = input.split("\\.");
		
		float result1=0;
		char c;
		for (int i=1;i<=split_number[0].length();i++) {
			c = split_number[0].charAt(split_number[0].length()-i);
			if (c=='1')
				result1 += (float) Math.pow(2, i-1);
		}
		

		float result2=0;
		String after_dec;
		try {
			after_dec = split_number[1];
		} catch (Exception e) {
			after_dec = "0";
		}
		for (int i=0;Math.abs(i)<after_dec.length();i++) {
			c = after_dec.charAt(i);
			if (c=='1')
				result2 += (float) Math.pow(2, -(i+1));
		}
		
		return result1+result2;
	}
	
	public double from_number(String input) {
		//first convert the number to two values before and after decimal
		//the first number we can get with valueOf()
		String[] split_number = input.split("\\.");
		int before_dec;
		try {
			before_dec = Integer.valueOf(split_number[0]);
		} catch (NumberFormatException e) {
			before_dec = 0;
		}
		//the second number I have to divide by 10^(length of number)
		//this will put a decimal point back at the beginning of the number
		double after_dec;
		try {
			double x = Math.pow(10, split_number[1].length());
			after_dec = Integer.valueOf(split_number[1])/x;
		} catch (ArrayIndexOutOfBoundsException e) {
			after_dec = 0.0;
		}
		
		//here is the calculation for before_decimal into binary
		double binary_1=0;
		double binary_temp=1;
		double curr_val=0;
		double next_val=1;
		int i=0;
		while (before_dec > 0) {
			while (next_val <= before_dec) {
					curr_val = Math.pow(2, i);
					next_val = Math.pow(2, i+1);
					binary_temp = Math.pow(10, i);
					i+=1;
					
			}
			before_dec-=curr_val;
			i=0;
			binary_1+=binary_temp;
			binary_temp=1;
			curr_val=0;
			next_val=1;
		}

		//here's the calculation for after_decimal
		double binary_2=0;
		double guess_value_actual=0;
		double guess_value_try=0;
		String guess_binary="0.";
		i=-1;
		while (after_dec != guess_value_actual) {
			guess_value_try += Math.pow(2, i);
			
			if (guess_value_try <= after_dec) {
				guess_binary+="1";
			} else {
				guess_binary+="0";
			}
			
			i-=1;
			guess_value_actual = from_binary(guess_binary);
			guess_value_try = guess_value_actual;
		}
		//converting the post-decimal binary value to a double
		try {
			binary_2 = Integer.valueOf(guess_binary.substring(2)); //eliminating decimal point to avoid number format error
			double x = Math.pow(10, guess_binary.substring(2).length());
			binary_2 = binary_2/x;
		} catch (NumberFormatException e) {
			binary_2 = 0.0;
		}
		return binary_1+binary_2;
	}
	
	public String from_string(String input) {
		String result="";
		int ascii;
		int binary;
		for (int i=0; i<input.length(); i++) {
			ascii = (int)input.charAt(i);
			binary = (int)from_number(ascii+"");
			result+=binary+" ";
		}
		return result;
	}
}

