package data;

import java.text.SimpleDateFormat;

import simulator.SimulatorConfig;

public class TankMeasureProcessed extends TankMeasureOriginal {

	private Double nettoVolume;

	private static Double referenceTemperature = Double.valueOf(15);

	public TankMeasureProcessed() {
	}

	public TankMeasureProcessed(Tank tank) {
		super(tank);
		countNettoVolume();
	}

	public boolean countNettoVolume(){
			if(getTemperature() == null){
				return false;
			}else{
				nettoVolume = countNettoVolume(getGrossVolume());
			}
			return true;
		}

	public double getNettoVolume() {
		return nettoVolume;
	}

	public void setNettoVolume(Double nettoVolume) {
		this.nettoVolume = nettoVolume;
	}

	public static Double getReferenceTemperature() {
		return referenceTemperature;
	}

	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		String str = new String();
		
		switch(SimulatorConfig.streamType)
		{
		case CSV:
		case CSV_LABELS:
			str += getTankId() + ";";
			str += sdf.format(getTimeStamp().getTime()) + ";";
			str += getGrossVolume() + ";";
			str += nettoVolume + ";";
			str += getWaterVolume() + ";";
			str += getTemperature() + "\n";
			break;
		case LIST:
			str += "Id: " + getTankId() + "\n";
			str += "Timestamp: " + sdf.format(getTimeStamp().getTime()) + "\n";
			str += "Gross volume: " + getGrossVolume() + "\n";
			str += "Netto volume: " + nettoVolume + "\n";
			str += "Water volume: " + getWaterVolume() + "\n";
			str += "Temperature: " + getTemperature() + "\n";
			break;
		default:
			assert(false);
			break;
		
		}
		
		return str;
	}

	public static void setReferenceTemperature(Double referenceTemperature) {
		TankMeasureProcessed.referenceTemperature = referenceTemperature;
	}

	private Double countNettoVolume(Double grossVolume) {
		return grossVolume * (1 + (getTemperature()-referenceTemperature)/referenceTemperature);
	}
	
	public static String getLabelsCSV() {
    	return "Id;Timestamp;Gross volume;Netto volume;Water volume;Temperature\n";
	}
}
