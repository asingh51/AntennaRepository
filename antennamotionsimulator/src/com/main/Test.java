package com.main;

import java.io.File;
import java.util.ArrayList;
import com.component.core.TimeCalculator;
import com.input.Input;
import com.output.Output;
//main class
public class Test {
	
	//main class to run
	public static void main(String[] args) {
		
		TimeCalculator t=new TimeCalculator();
		//read file
		ArrayList<Input> inputArray=t.readFile(args[0]);
		
		//calculate the output
		ArrayList<Output> outputArray=t.calculateArray(inputArray);
		
		//Save the output to the file 
		t.writesFile(new File("observation.log"),outputArray);
	}

}
