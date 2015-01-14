package data;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import simulator.SimulatorConfig;

public class TankMeasureInterval {
	
	private int tankId;
	private Calendar beginTimeStamp;
	private Calendar endTimeStamp;
	private Double tankIntervalNettoVolume = Double.valueOf(0);
	private Double tankIntervalGrossVolume = Double.valueOf(0);
	private Double nozzlesIntervalNettoVolume = Double.valueOf(0);
	private Double nozzlesIntervalGrossVolume = Double.valueOf(0);
	private Double differentialNettoVolume = Double.valueOf(0);
	private Double differentialGrossVolume = Double.valueOf(0);
	private boolean detectedFuelLeakage = false;

	public TankMeasureInterval(TankMeasureProcessed begin,TankMeasureProcessed end) {
		tankId = begin.getTankId();
		beginTimeStamp = begin.getTimeStamp();
		endTimeStamp = end.getTimeStamp();
		tankIntervalNettoVolume = begin.getNettoVolume() - end.getNettoVolume();
		tankIntervalGrossVolume = begin.getGrossVolume() - end.getGrossVolume();
	}
	
	public void addNozzleVolume(NozzleMeasureProcessed nozzleMeasure) {
		nozzlesIntervalNettoVolume += nozzleMeasure.getNettoVolume();
		nozzlesIntervalGrossVolume += nozzleMeasure.getGrossVolume();
	}
	
	public void countDifferentialVolume() {
		differentialNettoVolume = tankIntervalNettoVolume - nozzlesIntervalNettoVolume;
		differentialGrossVolume = tankIntervalGrossVolume - nozzlesIntervalGrossVolume;
		if(differentialGrossVolume > SimulatorConfig.fuelDetectionLeakageVolume) {
			detectedFuelLeakage = true;
		}
	}
	
	public String getTimeStamps() {
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		return sdf.format(beginTimeStamp.getTime()) + " : " + sdf.format(endTimeStamp.getTime());
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

	public Double getDifferentialNettoVolume() {
		return differentialNettoVolume;
	}

	public void setDifferentialNettoVolume(Double differentialNettoVolume) {
		this.differentialNettoVolume = differentialNettoVolume;
	}

	public Double getDifferentialGrossVolume() {
		return differentialGrossVolume;
	}

	public void setDifferentialGrossVolume(Double differentialGrossVolume) {
		this.differentialGrossVolume = differentialGrossVolume;
	}

	public boolean isDetectedFuelLeakage() {
		return detectedFuelLeakage;
	}

	public void setDetectedFuelLeakage(boolean detectedFuelLeakage) {
		this.detectedFuelLeakage = detectedFuelLeakage;
	}
}
