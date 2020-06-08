package com.output;
// OutputClass for handling input file components

public class Output {

	char scanType;
	float totalTime;
	float slewTime;
	float timeOnSource;
	
	// getter and setters
	public char getScanType() {
		return scanType;
	}
	public void setScanType(char scanType) {
		this.scanType = scanType;
	}
	public float getTotalTime() {
		return totalTime;
	}
	public void setTotalTime(float totalTime) {
		this.totalTime = totalTime;
	}
	public float getSlewTime() {
		return slewTime;
	}
	public void setSlewTime(float slewTime) {
		this.slewTime = slewTime;
	}
	public float getTimeOnSource() {
		return timeOnSource;
	}
	public void setTimeOnSource(float timeOnSource) {
		this.timeOnSource = timeOnSource;
	}
	@Override
	public String toString() {
		return "Output [scanType=" + scanType + ", totalTime=" + totalTime + ", slewTime=" + slewTime
				+ ", timeOnSource=" + timeOnSource + "]";
	}
	
	
	
	
}
