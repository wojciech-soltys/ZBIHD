package simulator;

import java.util.Iterator;
import java.util.Random;

import data.Distributor;
import data.Nozzle;
import data.PetrolStation;
import data.Tank;

public class SimulateAction extends Thread {

	SimulateAction(PetrolStation petrolStation) {
		this.petrolStation = petrolStation;
	}

	private PetrolStation petrolStation;

	@Override
	public void run() {
		while (true) {
			try {
				// generating petrol supplies
				Iterator<Tank> it = petrolStation.getTankList().iterator();
				Random rnd = new Random();
				while (it.hasNext()) {
					Tank tank = it.next();
					if (tank.getGrossVolume() <= tank.getMinimalVolume()) {
						tank.isRefuel = true;
					}
					if (tank.isRefuel == true) {
						tank.addVolume(SimulatorConfig.addVolumeCoefficient * tank.getMaximalVolume());
						if (tank.getGrossVolume() >= tank.getMaximalVolume()) {
							tank.isRefuel = false;
						}
					}
					if(rnd.nextDouble() <= SimulatorConfig.addTemperatureProbability) {
						tank.addTemperature(rnd.nextDouble()*SimulatorConfig.addTemperatureCoefficient);
					} else {
						tank.minusTemperature(rnd.nextDouble()*SimulatorConfig.minusTemperatureCoefficient);
					}
					if(rnd.nextDouble() <= SimulatorConfig.addWaterProbability) {
						tank.addWaterVolume(SimulatorConfig.addWaterVolumeValue);
					}
					if(SimulatorConfig.fuelLeakageSimulation) {
						tank.minusVolume(SimulatorConfig.fuelLeakageVolume);
					}
				}

				Iterator<Distributor> it2 = petrolStation.getDistributorList().iterator();
				while (it2.hasNext()) {
					if (rnd.nextDouble() <= SimulatorConfig.distributorRefuelProbability) {
						Distributor dist = it2.next();	
						Iterator<Nozzle> it3 = dist.getNoozleList().iterator();
						while (it3.hasNext()) {
							Nozzle nozzle = it3.next();
							Double randomVolume = rnd.nextDouble(); 
							if(randomVolume <= SimulatorConfig.nozzleRefuelProbability) {
								randomVolume = randomVolume * SimulatorConfig.refuelVolumeCoefficient * nozzle.getTank().getMaximalVolume();
								nozzle.addToTotalVolume(randomVolume);
								nozzle.getTank().minusVolume(randomVolume);
							}
						}
					}
				}
				sleep(SimulatorConfig.actionPeriod);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
