package com.input;

// Input Class for handling input file components
public class Input {

	char scanType;
	float azimuth;
	float elevation;
	float fluxDensity;

	// getter and setters
	public void setAzimuth(float azimuth) {
		this.azimuth = azimuth;
	}
	public void setElevation(float elevation) {
		this.elevation = elevation;
	}
	public void setFluxDensity(float fluxDensity) {
		this.fluxDensity = fluxDensity;
	}
	public void setScanType(char scanType) {
		this.scanType = scanType;
	}
	
	public float getAzimuth() {
		return azimuth;
	}
	public float getElevation() {
		return elevation;
	}
	public float getFluxDensity() {
		return fluxDensity;
	}
	public char getScanType() {
		return scanType;
	}
	@Override
	public String toString() {
		return "Input [scanType=" + scanType + ", azimuth=" + azimuth + ", elevation=" + elevation + ", fluxDensity="
				+ fluxDensity + "]";
	}
	
	
}
