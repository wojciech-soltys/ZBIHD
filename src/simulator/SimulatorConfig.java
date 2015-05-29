package simulator;

public class SimulatorConfig {
//	public static StreamType streamType = StreamType.DATABASE;
	public static StreamType streamType = StreamType.CSV_LABELS;
	
	public static String tankMeasureFilePath = "TankMeasures.csv";
	public static String nozzleMeasureFilePath = "NozzleMeasures.csv";
	//TODO - stworzylem juz nazwe pliku :P
	public static String tankIntervalMeasureFilePath = "TankIntervalMeasures.csv";
	//TODO - zaburzanie pomiarow z pistoletow 
	public static Double nozzleCalibrationCoefficient = 0.999;
	
	public static Double tankMinimalVolume = Double.valueOf(10);
	public static Double tankMaximalVolume = Double.valueOf(100);
	
	// intervals of actions in millisecond
	public static Integer measurePeriod = 3000;
	public static Integer actionPeriod = 300;
	public static Integer detectionFuelLeakagePeriod = 3000;
	
	public static Double addVolumeCoefficient = 0.01;
	public static Double addTemperatureCoefficient = 0.01;
	public static Double minusTemperatureCoefficient = 0.01;
	public static Double refuelVolumeCoefficient = 0.005;
	
	public static Double addTemperatureProbability = 0.5;
	public static Double addWaterProbability = 0.3;
	
	public static Double distributorRefuelProbability = 0.2;
	public static Double nozzleRefuelProbability = 0.2;
	
	public static Double addWaterVolumeValue = 0.001;
	
	public static boolean fuelLeakageSimulation = true;
	//TODO - stworzenie randomowego wycieku
	public static Double fuelLeakageVolume = 0.002;
	public static Double fuelDetectionLeakageVolume = 0.001;
	public static int 	minNumbersOfPeriodsToDetectContinuousFuelLeakage = 8;
	
	
	/***
	 * Database settings
	 */
	
	public static final String databaseUrl = "localhost";
	public static final String databaseUser = "root";
	public static final String databasePassword = "root";
	public static final String databaseName = "zbdihd";
}
