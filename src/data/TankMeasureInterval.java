package data;

import java.util.Calendar;

public class TankMeasureInterval {
	
	private int tankId;
	private Calendar beginTimeStamp;
	private Calendar endTimeStamp;
	private Double tankIntervalNettoVolume = Double.valueOf(0);
	private Double tankIntervalGrossVolume = Double.valueOf(0);
	private Double nozzlesIntervalNettoVolume = Double.valueOf(0);
	private Double nozzlesIntervalGrossVolume = Double.valueOf(0);

	public TankMeasureInterval(TankMeasureProcessed begin,TankMeasureProcessed end) {
		tankId = begin.getTankId();
		beginTimeStamp = begin.getTimeStamp();
		endTimeStamp = end.getTimeStamp();
		tankIntervalNettoVolume = begin.getNettoVolume() - end.getNettoVolume();
		tankIntervalGrossVolume = begin.getGrossVolume() - end.getGrossVolume();
	}
	
	public void addNozzleVolume(NozzleMeasureProcessed nozzleMeasure) {
		
	}

	public int getTankId() {
		return tankId;
	}

	public void setTankId(int tankId) {
		this.tankId = tankId;
	}

	public Calendar getBeginTimeStamp() {
		return beginTimeStamp;
	}

	public void setBeginTimeStamp(Calendar beginTimeStamp) {
		this.beginTimeStamp = beginTimeStamp;
	}

	public Calendar getEndTimeStamp() {
		return endTimeStamp;
	}

	public void setEndTimeStamp(Calendar endTimeStamp) {
		this.endTimeStamp = endTimeStamp;
	}

	public Double getTankIntervalNettoVolume() {
		return tankIntervalNettoVolume;
	}

	public void setTankIntervalNettoVolume(Double tankIntervalNettoVolume) {
		this.tankIntervalNettoVolume = tankIntervalNettoVolume;
	}

	public Double getTankIntervalGrossVolume() {
		return tankIntervalGrossVolume;
	}

	public void setTankIntervalGrossVolume(Double tankIntervalGrossVolume) {
		this.tankIntervalGrossVolume = tankIntervalGrossVolume;
	}

	public Double getNozzlesIntervalNettoVolume() {
		return nozzlesIntervalNettoVolume;
	}

	public void setNozzlesIntervalNettoVolume(Double nozzlesIntervalNettoVolume) {
		this.nozzlesIntervalNettoVolume = nozzlesIntervalNettoVolume;
	}

	public Double getNozzlesIntervalGrossVolume() {
		return nozzlesIntervalGrossVolume;
	}

	public void setNozzlesIntervalGrossVolume(Double nozzlesIntervalGrossVolume) {
		this.nozzlesIntervalGrossVolume = nozzlesIntervalGrossVolume;
	}
}
