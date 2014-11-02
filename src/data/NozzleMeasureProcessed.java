package data;

import java.text.SimpleDateFormat;
import java.util.Date;

import simulator.SimulatorConfig;

public class NozzleMeasureProcessed extends NozzleMeasureOriginal {
	
	private Double grossVolume;
	private Double nettoVolume;
	private Double temperature;
	private Date beginTimeStamp;
	private Date endTimeStamp;
	private static Double referenceTemperature = Double.valueOf(15);
	private static Double nozzleCalibrationCoefficient = 0.999;
	
	public NozzleMeasureProcessed(NozzleMeasureRaw begin, NozzleMeasureRaw end) {
		super(begin, end);
		temperature = begin.getNozzle().getTank().getTemperature();
		beginTimeStamp = begin.getTimeStamp();
		endTimeStamp = end.getTimeStamp();
		countGrossVolume();
		countNettoVolume();
	}
	
	public void countGrossVolume() {
		grossVolume = getRawVolume() * nozzleCalibrationCoefficient;
	}
	
	public boolean countNettoVolume(){
		if(getTemperature() == null){
			return false;
		}else{
			nettoVolume = countNettoVolume(grossVolume);	
		}
		return true;
	}
	
	public Double getGrossVolume() {
		return grossVolume;
	}
	public void setGrossVolume(Double grossVolume) {
		this.grossVolume = grossVolume;
	}
	public Double getNettoVolume() {
		return nettoVolume;
	}
	public void setNettoVolume(Double nettoVolume) {
		this.nettoVolume = nettoVolume;
	}
	public Double getTemperature() {
		return temperature;
	}
	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}
	public static Double getReferenceTemperature() {
		return referenceTemperature;
	}
	
	public Date getBeginTimeStamp() {
		return beginTimeStamp;
	}

	public void setBeginTimeStamp(Date beginTimeStamp) {
		this.beginTimeStamp = beginTimeStamp;
	}

	public Date getEndTimeStamp() {
		return endTimeStamp;
	}

	public void setEndTimeStamp(Date endTimeStamp) {
		this.endTimeStamp = endTimeStamp;
	}

    @Override
    public String toString() {
    	SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
    	String str = new String();
    	
    	switch(SimulatorConfig.streamType)
    	{
		case CSV:
		case CSV_LABELS:
	    	str += getNoozleId() + ";";
	    	str += getTankId() + ";";
	    	str += sdf.format(beginTimeStamp) + ";";
	    	str += sdf.format(endTimeStamp) + ";";
	    	str += getRawVolume() + ";";
	    	str += grossVolume + ";";
	    	str += nettoVolume + ";";
	    	str += temperature + "\n";
			break;
		case LIST:
	    	str += "Nozzle id: " + getNoozleId() + "\n";
	    	str += "Tank id: " + getTankId() + "\n";
	    	str += "Begin timestamp: " + sdf.format(beginTimeStamp) + "\n";
	    	str += "End timestamp: " + sdf.format(endTimeStamp) + "\n";
	    	str += "Raw volume: " + getRawVolume() + "\n";
	    	str += "Gross volume: " + grossVolume + "\n";
	    	str += "Netto volume: " + nettoVolume + "\n";
	    	str += "Temperature: " + temperature + "\n";
			break;
		default:
			assert(false);
			break;
    	}

        return str;
    }
	
	public static void setReferenceTemperature(Double referenceTemperature) {
		NozzleMeasureProcessed.referenceTemperature = referenceTemperature;
	}

	public static void setNozzleCalibrationCoefficient(
			Double nozzleCalibrationCoefficient) {
		NozzleMeasureProcessed.nozzleCalibrationCoefficient = nozzleCalibrationCoefficient;
	}
	
	private Double countNettoVolume(Double grossVolume) {
		return grossVolume * (1 + (getTemperature()-referenceTemperature)/referenceTemperature);
	}
	
	public static String getLabelsCSV() {
    	return "Nozzle id;Tank id;Begin timestamp;End timestamp;Raw volume;Gross volume;Netto volume;Temperature\n";
	}
	
}
