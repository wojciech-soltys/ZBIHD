package data;

public class Tank {
	
	private int tankId;
	public boolean isRefuel = false;
	private Double grossVolume;
	private Double waterVolume;
	private Double temperature;
	private Double minimalVolume;
	private Double maximalVolume;
	
	public Tank(int tankId, Double minimalVolume, Double maximalVolume){
		this.tankId = tankId;
		this.minimalVolume = minimalVolume;
		this.maximalVolume = maximalVolume;
		this.temperature = Double.valueOf(15);
		this.grossVolume = Double.valueOf((minimalVolume + maximalVolume)/2);
		this.waterVolume = Double.valueOf(minimalVolume/10000);		
	}

	public int getTankId() {
		return tankId;
	}

	public void setTankId(int tankId) {
		this.tankId = tankId;
	}

	public Double getGrossVolume() {
		return grossVolume;
	}

	public void setGrossVolume(Double grossVolume) {
		this.grossVolume = grossVolume;
	}

	public Double getWaterVolume() {
		return waterVolume;
	}

	public void setWaterVolume(Double waterVolume) {
		this.waterVolume = waterVolume;
	}
	
	public void addWaterVolume(Double waterVolume) {
		this.waterVolume += waterVolume;
	}

	public Double getTemperature() {
		return temperature;
	}

	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}
	
	public void addTemperature(Double temperature) {
		this.temperature += temperature;
	}
	
	public void minusTemperature(Double temperature) {
		this.temperature -= temperature;
	}
	
	public Double getMinimalVolume() {
		return minimalVolume;
	}

	public void setMinimalVolume(Double minimalVolume) {
		this.minimalVolume = minimalVolume;
	}

	public Double getMaximalVolume() {
		return maximalVolume;
	}

	public void setMaximalVolume(Double maximalVolume) {
		this.maximalVolume = maximalVolume;
	}
	
	public void addVolume(Double volume) {
		this.grossVolume += volume;
	}
	
	public void minusVolume(Double volume) {
		this.grossVolume -= volume;
	}
}
