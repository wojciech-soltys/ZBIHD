package data;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TankMeasureOriginal {
	
	private int tankId;
	
	private Calendar timeStamp;
	
	private Double grossVolume;
	
	private Double waterVolume;
	
	private Double temperature;

	public TankMeasureOriginal(){
	}
	
	public TankMeasureOriginal(Tank tank){
		this.tankId = tank.getTankId();
		this.timeStamp = Calendar.getInstance();
		this.grossVolume = tank.getGrossVolume();
		this.waterVolume = tank.getWaterVolume();
		this.temperature = tank.getTemperature();
	}
	
	public int getTankId() {
		return tankId;
	}

	public void setTankId(int tankId) {
		this.tankId = tankId;
	}

	public Calendar getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Calendar timeStamp) {
		this.timeStamp = timeStamp;
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

	public Double getTemperature() {
		return temperature;
	}

	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}
	
    @Override
    public String toString() {
    	SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        return tankId + ";" + sdf.format(timeStamp.getTime()) + ";"
        		+ grossVolume.toString() + ";" + waterVolume.toString() + ";"
        		+ temperature.toString();
    }
	
}
