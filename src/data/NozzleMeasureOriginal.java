package data;

public class NozzleMeasureOriginal {
	
	private int noozzleId;
	
	private int tankId;
	
	private Double rawVolume = Double.valueOf(0);
	
	public NozzleMeasureOriginal(NozzleMeasureRaw begin, NozzleMeasureRaw end) {
		assert(begin.getNozzle() == end.getNozzle());	
		
		noozzleId = begin.getNozzle().getNoozleId();
		tankId = begin.getNozzle().getTank().getTankId();
		rawVolume = end.getTotalVolume() - begin.getTotalVolume();
		
		assert(rawVolume < 0);
	}

	public int getNoozleId() {
		return noozzleId;
	}

	public void setNoozzleId(int noozzleId) {
		this.noozzleId = noozzleId;
	}

	public int getTankId() {
		return tankId;
	}

	public void setTankId(int tankId) {
		this.tankId = tankId;
	}

	public Double getRawVolume() {
		return rawVolume;
	}

	public void setRawVolume(Double rawVolume) {
		this.rawVolume = rawVolume;
	}

}
