package com.component.core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;

import com.input.Input;
import com.output.Output;

public class TimeCalculator {

	// Method to read filename provided
	public ArrayList<Input> readFile(String fileName) {
		ArrayList<Input> inputArrayList= new ArrayList<>();
		File file = new File(fileName);
		String st; 
		int counter = 0;
		boolean errorflag=false;
		
		//open file with resources
		try(BufferedReader br = new BufferedReader(new FileReader(file))) {
			while ((st = br.readLine()) != null){
				String[] array=st.split("\\s+"); // splits the line with single or multiple spaces
				Input input=new Input(); 		// Create Object of Input Type
				input.setScanType(array[0].charAt(0));
				input.setAzimuth(Float.valueOf(array[1]));
				input.setElevation(Float.valueOf(array[2]));
				input.setFluxDensity(Float.valueOf(array[3]));
				// check validate input 
				int validated=validateInput(input);
				counter++; //increment the line counter
				//4 means all correct
				if(validated==4) 
					inputArrayList.add(input);
				else {
					System.out.println("#"+counter+" Value:"+array[validated]+": The input is out of range at this line! Please correct the file input.");
					errorflag=true;
				}
			}
		} catch (IOException e) {
			System.err.println("Specified file is not present");
			//e.printStackTrace();
		}
		//if found error, exit
		if(errorflag==true)         
			System.exit(0);
		
		return inputArrayList; 
	}
	
	//Method to validate the input
	private int validateInput(Input input) {
		//check scan type
		HashSet<Character> acceptableScanType= new HashSet<Character>();
		acceptableScanType.add('C');
		acceptableScanType.add('T');
		acceptableScanType.add('V');
		if(!acceptableScanType.contains(input.getScanType()))
				return 0;
		
		//check azimuth
		if(input.getAzimuth()>360 || input.getAzimuth()<0)
			return 1;
	
		//check elevation
		if(input.getElevation()>90 || input.getElevation()<0)
			return 2;
		
		//check flux density
		if(input.getFluxDensity()>10 || input.getFluxDensity()<0.5)
			return 3;
		
		//return 4 if all input are correct
		return 4;
	}

	//Method to calculate the output array
	public ArrayList<Output> calculateArray(ArrayList<Input> inputArray) {
		//Save previous input
		Input previousInput=new Input();
		previousInput.setAzimuth(0);
		previousInput.setElevation(0);
		
		//Crate output Array
		ArrayList<Output> outputArray= new ArrayList<Output>();
		
		//Iterate over the input array 
		for(Input input:inputArray) {
			//Crate Output object
			Output output= new Output();
			output.setScanType(input.getScanType());
			
			//Calculate the max and minimum of azimuth and elevation
			float maxAzimuth= (input.getAzimuth() > previousInput.getAzimuth()) ? input.getAzimuth() : previousInput.getAzimuth();  
			float minAzimuth=(input.getAzimuth() < previousInput.getAzimuth()) ? input.getAzimuth() : previousInput.getAzimuth();  
			float maxElevation= (input.getElevation() > previousInput.getElevation()) ? input.getElevation() : previousInput.getElevation();  
			float minElevation=(input.getElevation() < previousInput.getElevation()) ? input.getElevation() : previousInput.getElevation();  
			
			//calculate difference between azimuth and elevation
			float differenceAzimuth=maxAzimuth-minAzimuth;
			float differenceElevation=maxElevation-minElevation;
			
			//check if difference between azimuths is greater than 180
			if(input.getAzimuth()-previousInput.getAzimuth()>=180) {
				output.setSlewTime(Math.max((360-differenceAzimuth)/40, (differenceElevation)/20));//make 40-20 constant
			}
			else {
				output.setSlewTime(Math.max((differenceAzimuth)/40, (differenceElevation)/20));//make 40-20 constant
			}
			
			output.setTimeOnSource(TimeOnSource(input.getScanType(),input.getFluxDensity()));
			output.setTotalTime(output.getSlewTime()+output.getTimeOnSource());
			
			//add to output Array
			outputArray.add(output);
			
			//set current to previous
			previousInput=input;
		}
		return outputArray;
	}
	
	//Method to return Time on Source based on flux density and scan type
	private float TimeOnSource(char scanType, float fluxDensity) {
		switch(scanType) {
			//return based on flux density and scan type
			case 'T': return 5.0f;
			case 'C': return 2.0f;
			case 'V': return (float) (20.5-(2*fluxDensity));
			default : return 0;
		}
			
	}
	
	//Method to write the Output Array to observation.log file
	public void writesFile(File file, ArrayList<Output> outputArray) {
		//set counter for number of instances
		int counter=0;
		
		//initialize the total values
		float totalTime=0.0f;
		float totalSlewTime=0.0f;
		float totalTimeOnSources=0.0f;
		
		//DeceimalFormat for 3 decimal print
		DecimalFormat df = new DecimalFormat("#.###");
	 	try (BufferedWriter br = new BufferedWriter(new FileWriter(file))){//add ,true to append
	 		for(Output output:outputArray) {
	 			//increment the value of totals 
	 			totalTime +=Float.valueOf(df.format(output.getTotalTime()));
	 			totalSlewTime +=Float.valueOf(df.format(output.getSlewTime()));
	 			totalTimeOnSources +=Float.valueOf(df.format(output.getTimeOnSource()));
	 			br.write("#"+(++counter)+": "+output.getScanType()+" "+String.format("%.3f", output.getTotalTime())+" "+String.format("%.3f", output.getSlewTime())+" "+String.format("%.3f", output.getTimeOnSource()));
	 			br.newLine();
	     	}
	 		// add the output to file 
			br.write(String.format("%.3f", totalTime)+" "+String.format("%.3f", totalSlewTime)+" "+String.format("%.3f", totalTimeOnSources));
	 	}
	 	catch (Exception e)
	 	{
			System.err.println("Error Occured");
	 		//e.printStackTrace();
	 	}
	 	
	}


}
