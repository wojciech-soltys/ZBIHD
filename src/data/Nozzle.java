package data;

public class Nozzle {

	private int noozleId;
	
	private Tank tank;
	
	private Double totalVolume = Double.valueOf(0);
	
	public int getNoozleId() {
		return noozleId;
	}

	public void setNoozleId(int noozleId) {
		this.noozleId = noozleId;
	}

	public Double getTotalVolume() {
		return totalVolume;
	}

	public void setTotalVolume(Double totalVolume) {
		this.totalVolume = totalVolume;
	}

	public Tank getTank() {
		return tank;
	}

	public void setTank(Tank tank) {
		this.tank = tank;
	}
	
	public void addToTotalVolume(Double volume) {
		this.totalVolume += volume;
	}
	
}
