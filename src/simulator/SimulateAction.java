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
	
	private int runCounter =0;

	@Override
	public void run() {		
		while (true) {
			try {
				// generating petrol supplies
				Iterator<Tank> it = petrolStation.getTankList().iterator();
				Random rnd = new Random();	
				runCounter+=1;				
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
						if(runCounter >= 96) {
							runCounter=96;
						}
						
						if(tank.getTankId() == 2 && ((runCounter >=24 && runCounter<=39) || (runCounter >=56 && runCounter<=63) || runCounter>=80)) {
							tank.minusVolume(SimulatorConfig.fuelLeakageVolume*(rnd.nextInt(30)+85)/100);
						} else if (tank.getTankId() == 1 && runCounter >=32) {
							tank.minusVolume(SimulatorConfig.fuelLeakageVolume*(rnd.nextInt(30)+85)/100);
						} else if (tank.getTankId() == 0 && runCounter >= 32) {
							tank.minusVolume(SimulatorConfig.fuelLeakageVolume*(rnd.nextInt(30)+85)/100);
						}
						
						//tank.minusVolume(SimulatorConfig.fuelLeakageVolume);
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
