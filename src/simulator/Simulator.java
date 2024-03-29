package simulator;

import java.io.File;

import data.PetrolStation;
import detection.FuelLeakageDetection;

public class Simulator {

	private PetrolStation petrolStation;

	public Simulator() {
		PetrolStation petrolStation = new PetrolStation();
		petrolStation.addTank(SimulatorConfig.tankMinimalVolume, SimulatorConfig.tankMaximalVolume);
		petrolStation.addTank(SimulatorConfig.tankMinimalVolume, SimulatorConfig.tankMaximalVolume);
		petrolStation.addTank(SimulatorConfig.tankMinimalVolume, SimulatorConfig.tankMaximalVolume);
		petrolStation.addDistributor();
		petrolStation.addDistributor();
		petrolStation.addDistributor();
		petrolStation.addDistributor();
		this.petrolStation = petrolStation;
	}

	public void startSimulation() {
		if(SimulatorConfig.streamType == StreamType.CSV) {
			File file = new File(SimulatorConfig.nozzleMeasureFilePath);
			file.delete();
			file = new File(SimulatorConfig.tankMeasureFilePath);
			file.delete();
		}
		GenerateMeasure gm = new GenerateMeasure(petrolStation);
		SimulateAction sa = new SimulateAction(petrolStation);
		FuelLeakageDetection fla = new FuelLeakageDetection(petrolStation);
		gm.start();
		sa.start();
		fla.start();
	}

	public static void main(String[] args) {
		Simulator simulator = new Simulator();
		simulator.startSimulation();
	}

}
