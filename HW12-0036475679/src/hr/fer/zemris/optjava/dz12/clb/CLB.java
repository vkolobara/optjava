package hr.fer.zemris.optjava.dz12.clb;

import java.util.Arrays;

public class CLB {

	
	public static boolean calculate(boolean[] input, boolean[] table) {
		
		String bin = "";
				
		for (int i=0; i<input.length; i++) {
			bin += input[i] ? "1" : "0";
		}
		
		return table[Integer.parseInt(bin, 2)];
	}
}
