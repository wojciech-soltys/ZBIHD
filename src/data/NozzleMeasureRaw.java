package data;

import java.util.Calendar;
import java.util.Date;

public class NozzleMeasureRaw {

	private Nozzle nozzle;

	private double totalVolume = 0;

	private Date timeStamp;

	public NozzleMeasureRaw(Nozzle nozzle) {
		this.nozzle = nozzle;
		totalVolume = nozzle.getTotalVolume();
		timeStamp = Calendar.getInstance().getTime();
	}

	public Nozzle getNozzle() {
		return nozzle;
	}

	public void setNozzle(Nozzle nozzle) {
		this.nozzle = nozzle;
	}

	public double getTotalVolume() {
		return totalVolume;
	}

	public void setTotalVolume(double totalVolume) {
		this.totalVolume = totalVolume;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

}
