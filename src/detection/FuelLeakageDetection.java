package detection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import simulator.SimulatorConfig;
import data.Distributor;
import data.Nozzle;
import data.NozzleMeasureProcessed;
import data.NozzleMeasureRaw;
import data.PetrolStation;
import data.Tank;
import data.TankMeasureInterval;
import data.TankMeasureProcessed;

public class FuelLeakageDetection extends Thread {
	
	public FuelLeakageDetection(PetrolStation petrolStation) {
		this.petrolStation = petrolStation;
	}

	private PetrolStation petrolStation;
	
	private List<NozzleMeasureRaw> nozzleMeasureList = new ArrayList<NozzleMeasureRaw>();
	private List<NozzleMeasureProcessed> nozzleMeasureProcessedList = new ArrayList<NozzleMeasureProcessed>();
	private List<TankMeasureProcessed> tankMeasureList = new ArrayList<TankMeasureProcessed>();
	private List<TankMeasureInterval> tankMeasureIntervalList = new ArrayList<TankMeasureInterval>();
	
	private NozzleMeasureRaw findNozzleMeasure(Nozzle nozzle) {
		Iterator<NozzleMeasureRaw> it = nozzleMeasureList.iterator();
		while(it.hasNext()) {
			NozzleMeasureRaw measure = it.next();
			if(measure.getNozzle() == nozzle)
				return measure;
		}
		return null;
	}
	
	private TankMeasureProcessed findTankMeasureProcessed(Tank tank) {
		Iterator<TankMeasureProcessed> it = tankMeasureList.iterator();
		while(it.hasNext()) {
			TankMeasureProcessed measure = it.next();
			if(measure.getTankId() == tank.getTankId())
				return measure;
		}
		return null;
	}
	
	private void addNozzlesMeasurments(TankMeasureInterval tankMeasureInterval) {
		Iterator<NozzleMeasureProcessed> it = nozzleMeasureProcessedList.iterator();
		while(it.hasNext()) {
			NozzleMeasureProcessed nozzleMeasureProcessed = it.next();
			if(tankMeasureInterval.getTankId() == tankMeasureInterval.getTankId()) {
				tankMeasureInterval.addNozzleVolume(nozzleMeasureProcessed);
				it.remove();
			}
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				
				Iterator<Distributor> distr_it = petrolStation.getDistributorList().iterator();
				while(distr_it.hasNext()) {
					Distributor distr = distr_it.next();
					Iterator<Nozzle> nozz_it = distr.getNoozleList().iterator();
					while(nozz_it.hasNext()) {
						Nozzle nozzle = nozz_it.next();
						NozzleMeasureRaw nozzleMeasureOld = findNozzleMeasure(nozzle);
						NozzleMeasureRaw nozzleMeasureNew = new NozzleMeasureRaw(nozzle);

						if(nozzleMeasureOld != null) {
							NozzleMeasureProcessed nozzleMeasureProcessed = new NozzleMeasureProcessed(nozzleMeasureOld, nozzleMeasureNew);
							nozzleMeasureProcessedList.add(nozzleMeasureProcessed);
							nozzleMeasureList.remove(nozzleMeasureOld);
						}
						nozzleMeasureList.add(nozzleMeasureNew);
					}
				}
				
				Iterator<Tank> tank_it = petrolStation.getTankList().iterator();
				while(tank_it.hasNext()) {
					Tank tank = tank_it.next();
					TankMeasureProcessed tankMeasureOld = findTankMeasureProcessed(tank);
					TankMeasureProcessed tankMeasureNew = new TankMeasureProcessed(tank);
					
					if(tankMeasureOld != null) {
						TankMeasureInterval tankMeasureInterval = new TankMeasureInterval(tankMeasureOld,tankMeasureNew);
						addNozzlesMeasurments(tankMeasureInterval);
						//TODDO checking fuel leakage
						tankMeasureList.remove(tankMeasureOld);
					}
					tankMeasureList.add(tankMeasureNew);
				}
				
				sleep(SimulatorConfig.detectionFuelLeakagePeriod);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
