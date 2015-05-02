package simulator;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import data.Distributor;
import data.Nozzle;
import data.NozzleMeasureProcessed;
import data.NozzleMeasureRaw;
import data.PetrolStation;
import data.Tank;
import data.TankMeasureProcessed;
import database.MySQLAccess;

public class GenerateMeasure extends Thread {

	private PetrolStation petrolStation;
	
	private List<NozzleMeasureRaw> nozzleMeasureList = new ArrayList<NozzleMeasureRaw>();
	
	private int measurementCounter = 4;
	
	private NozzleMeasureRaw findNozzleMeasure(Nozzle nozzle) {
		Iterator<NozzleMeasureRaw> it = nozzleMeasureList.iterator();
		while(it.hasNext()) {
			NozzleMeasureRaw measure = it.next();
			if(measure.getNozzle() == nozzle)
				return measure;
		}
		return null;
	}

	GenerateMeasure(PetrolStation petrolStation) {
		this.petrolStation = petrolStation;
	}

	@Override
	public void run() {
		MySQLAccess mySQLAccess = new MySQLAccess();
		NozzleMeasureProcessed.setNozzleCalibrationCoefficient(SimulatorConfig.nozzleCalibrationCoefficient);
		if(SimulatorConfig.streamType == StreamType.CSV_LABELS) {
			try {
				FileWriter fw = new FileWriter(SimulatorConfig.tankMeasureFilePath);
				fw.write(TankMeasureProcessed.getLabelsCSV());
				fw.close();
				fw = new FileWriter(SimulatorConfig.nozzleMeasureFilePath);
				fw.write(NozzleMeasureProcessed.getLabelsCSV());
				fw.close();
			}
			catch(IOException e) {
				assert(false);
			}
		}
		while (true) {
			try {
				Iterator<Tank> it = petrolStation.getTankList().iterator();
				while(it.hasNext()){
					TankMeasureProcessed tankMeasureProcessed = new TankMeasureProcessed(it.next());
					switch(SimulatorConfig.streamType)
					{
					case CSV:
					case CSV_LABELS:
						try
						{
							FileWriter fw = new FileWriter(SimulatorConfig.tankMeasureFilePath, true);
							fw.write(tankMeasureProcessed.toString());
							fw.close();
						}
						catch(IOException e)
						{
							assert(false);
						}
						break;
					case LIST:
						System.out.println(tankMeasureProcessed);
						break;
					case DATABASE:
						mySQLAccess.writeTankMeasureProcessedToDatabase(tankMeasureProcessed);
						break;
					default:
						assert(false);
						break;
					}
				}
				
				if(++measurementCounter == 5) {
					measurementCounter = 0;
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
								switch(SimulatorConfig.streamType)
								{
								case CSV:
								case CSV_LABELS:
									try
									{
										FileWriter fw = new FileWriter(SimulatorConfig.nozzleMeasureFilePath, true);
										fw.write(nozzleMeasureProcessed.toString());
										fw.close();
									}
									catch(IOException e)
									{
										assert(false);
									}
									break;
								case LIST:
									System.out.println(nozzleMeasureProcessed);
									break;
								case DATABASE:
									mySQLAccess.writeNozzleMeasureProcessedToDatabase(nozzleMeasureProcessed);
									break;
								default:
									assert(false);
									break;
								}
								nozzleMeasureList.remove(nozzleMeasureOld);
							}
							nozzleMeasureList.add(nozzleMeasureNew);
						}
					}
				}
				
				sleep(SimulatorConfig.measurePeriod);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
