 package detection;

import graph.CumulativeVarianceGraph;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jfree.data.time.Millisecond;

import simulator.SimulatorConfig;
import simulator.StreamType;
import data.Distributor;
import data.Nozzle;
import data.NozzleMeasureProcessed;
import data.NozzleMeasureRaw;
import data.PetrolStation;
import data.Tank;
import data.TankMeasureInterval;
import data.TankMeasureProcessed;
import database.MySQLAccess;

public class FuelLeakageDetection extends Thread {
	
	public FuelLeakageDetection(PetrolStation petrolStation) {
		this.petrolStation = petrolStation;
	}

	private PetrolStation petrolStation;
	
	private CumulativeVarianceGraph cumulativeVarianceGraph = new CumulativeVarianceGraph("Cumulative Variance Graph");
	
	private List<NozzleMeasureRaw> nozzleMeasureList = new ArrayList<NozzleMeasureRaw>();
	private List<NozzleMeasureProcessed> nozzleMeasureProcessedList = new ArrayList<NozzleMeasureProcessed>();
	private List<TankMeasureProcessed> tankMeasureList = new ArrayList<TankMeasureProcessed>();
	private Map<Integer,List<TankMeasureInterval>> tankMeasureIntervalMap = new HashMap<Integer,List<TankMeasureInterval>>();
	
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
			if(tankMeasureInterval.getTankId() == nozzleMeasureProcessed.getTankId()) {
				tankMeasureInterval.addNozzleVolume(nozzleMeasureProcessed);
				it.remove();
			}
		}
	}
	
	
	private void checkFuelLeakageForTank(Integer tankId, Millisecond now) {
		List<TankMeasureInterval> listOfTankMesaurments = tankMeasureIntervalMap.get(tankId);
		int numberOfDetected;
		if(listOfTankMesaurments != null){
			for (int i = listOfTankMesaurments.size() - 1; i >= 0; i--) {
				TankMeasureInterval lastDetected = null;
				TankMeasureInterval firstDetected = null;
				TankMeasureInterval tankMeasure = listOfTankMesaurments.get(i);
				numberOfDetected = 1;
/*				if(tankId == 0) {
					cumulativeVarianceGraph.addVariance(tankMeasure.getDifferentialGrossVolume().doubleValue(),tankId, now);
				}*/
				
				if(tankMeasure.isDetectedFuelLeakage()) {
					lastDetected = tankMeasure;
					
					if(numberOfDetected == 1) {
						System.out.println(tankMeasure.getTimeStamps() + " - single fuel leakage detected in tank " + tankMeasure.getTankId() + " of volume: " 
								+ tankMeasure.getDifferentialGrossVolume());
					}
					System.out.println("---------------------------------------------------------------------------------------------------------------------");
				}
				
				if(listOfTankMesaurments.size() < SimulatorConfig.minNumbersOfPeriodsToDetectContinuousFuelLeakage) {
					break;
				} else {
					
					for (int j = listOfTankMesaurments.size() - 2; j >= 0; j--) {
						TankMeasureInterval tankMeasureInterval = listOfTankMesaurments.get(j);
						if(tankMeasureInterval.isDetectedFuelLeakage()) {
							numberOfDetected++;
						}
					}
					if(numberOfDetected >= SimulatorConfig.minNumbersOfPeriodsToDetectContinuousFuelLeakage) {
						firstDetected = listOfTankMesaurments.get(listOfTankMesaurments.size() - numberOfDetected);
						System.out.println("CONTINUOUS FUEL LEAKAGE DETECTED IN TANK NUMBER " +
								firstDetected.getTankId() + " DURING " + numberOfDetected + " PERIODS FROM  " + firstDetected.getTimeBeginTimeStamp() +
								" TO " + lastDetected.getTimeEndTimeStamp());
						System.out.println("---------------------------------------------------------------------------------------------------------------------");
					}
					
					if(tankId == 0) {
						Double differentialIntervalGrossVolumeTank0 = Double.valueOf(0);
						for(int k = 0; k < listOfTankMesaurments.size(); k++) {
							differentialIntervalGrossVolumeTank0 += listOfTankMesaurments.get(k).getDifferentialGrossVolume();
						}
						cumulativeVarianceGraph.addVariance(differentialIntervalGrossVolumeTank0.doubleValue(), tankId, now);
					}
					if(tankId == 1) {
						Double differentialIntervalGrossVolumeTank1 = Double.valueOf(0);
						for(int k = 0; k < listOfTankMesaurments.size(); k++) {
							differentialIntervalGrossVolumeTank1 += listOfTankMesaurments.get(k).getDifferentialGrossVolume();
						}
						cumulativeVarianceGraph.addVariance(differentialIntervalGrossVolumeTank1.doubleValue(), tankId, now);
					}
					if(tankId == 2) {
						Double differentialIntervalGrossVolumeTank2 = Double.valueOf(0);
						for(int k = 0; k < listOfTankMesaurments.size(); k++) {
							differentialIntervalGrossVolumeTank2 += listOfTankMesaurments.get(k).getDifferentialGrossVolume();
						}
						cumulativeVarianceGraph.addVariance(differentialIntervalGrossVolumeTank2.doubleValue(), tankId, now);
					}
					listOfTankMesaurments.clear();
					numberOfDetected = 0;	
					break;
				}
					
				
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
				final Millisecond now = new Millisecond();
				while(tank_it.hasNext()) {
					Tank tank = tank_it.next();
					if(tank.isRefuel) {
						break;
					}
					TankMeasureProcessed tankMeasureOld = findTankMeasureProcessed(tank);
					TankMeasureProcessed tankMeasureNew = new TankMeasureProcessed(tank);
					Integer tankId = Integer.valueOf(tankMeasureNew.getTankId());
					if(tankMeasureOld != null) {
						TankMeasureInterval tankMeasureInterval = new TankMeasureInterval(tankMeasureOld,tankMeasureNew);
						addNozzlesMeasurments(tankMeasureInterval);
						tankMeasureInterval.countDifferentialVolume();
						if(tankMeasureIntervalMap.get(tankId) == null) {
							List<TankMeasureInterval> tankMeasureIntervalList = new ArrayList<TankMeasureInterval>();
							tankMeasureIntervalList.add(tankMeasureInterval);
							tankMeasureIntervalMap.put(tankId, tankMeasureIntervalList);
						} else {
							List<TankMeasureInterval> tankMeasureIntervalList = tankMeasureIntervalMap.get(tankId);
							tankMeasureIntervalList.add(tankMeasureInterval);
						}
						tankMeasureList.remove(tankMeasureOld);
						
						if(SimulatorConfig.streamType == StreamType.DATABASE) {
							MySQLAccess mySQLAccess = new MySQLAccess();
							mySQLAccess.writeTankMeasureIntervalToDatabase(tankMeasureInterval);
						}
						if(SimulatorConfig.streamType == StreamType.CSV_LABELS) {
							try {
								FileWriter fw = new FileWriter(SimulatorConfig.tankIntervalMeasureFilePath, true);
								fw.write(tankMeasureInterval.toString());
								fw.close();
							}
							catch(IOException e) {
								assert(false);
							}
						}
					}
					tankMeasureList.add(tankMeasureNew);
					checkFuelLeakageForTank(tankId, now);
				}
				sleep(SimulatorConfig.detectionFuelLeakagePeriod);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
