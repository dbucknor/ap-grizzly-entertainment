package com.grizzly.application.models;

public class Power extends Equipment {
    private String outputPower;
    private String phase;
    private String outputVoltage;
    private String fuelSource;

    public Power() {
        super(); // Call to the superclass constructor
        outputPower = "";
        phase = "";
        outputVoltage = "";
        fuelSource = "";
    }

    public Power(String id, String name, String description, String category, float price, 
                 ArrayList<MaintainanceLog> maintainaceLogs, String type, Date nextAvaliableDate,
                 String outputPower, String phase, String outputVoltage, String fuelSource) {
        super(id, name, description, category, price, maintainaceLogs, type, nextAvaliableDate);
        this.outputPower = outputPower;
        this.phase = phase;
        this.outputVoltage = outputVoltage;
        this.fuelSource = fuelSource;
    }

    
 // Copy constructor
    public Power(Power power) {
        super(power); // Calling the superclass's copy constructor
        this.outputPower = power.outputPower;
        this.phase = power.phase;
        this.outputVoltage = power.outputVoltage;
        this.fuelSource = power.fuelSource;
    }
    
    public String getOutputPower() {
        return outputPower;
    }

    public void setOutputPower(String outputPower) {
        this.outputPower = outputPower;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public String getOutputVoltage() {
        return outputVoltage;
    }

    public void setOutputVoltage(String outputVoltage) {
        this.outputVoltage = outputVoltage;
    }

    public String getFuelSource() {
        return fuelSource;
    }

    public void setFuelSource(String fuelSource) {
        this.fuelSource = fuelSource;
    }

	@Override
	public String toString() {
		return "Power [outputPower=" + outputPower + ", phase=" + phase + ", outputVoltage=" + outputVoltage
				+ ", fuelSource=" + fuelSource + ", toString()=" + super.toString() + "]";
	}
    
    
}
