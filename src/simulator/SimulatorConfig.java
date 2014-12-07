package simulator;

public class SimulatorConfig {
	public static StreamType streamType = StreamType.CSV;
	
	public static String tankMeasureFilePath = "TankMeasures.csv";
	public static String nozzleMeasureFilePath = "NozzleMeasures.csv";
	
	public static Double nozzleCalibrationCoefficient = 0.999;
	
	public static Double tankMinimalVolume = Double.valueOf(10);
	public static Double tankMaximalVolume = Double.valueOf(100);
	
	public static Integer measurePeriod = 3000;
	public static Integer actionPeriod = 300;
	
	public static Double addVolumeCoefficient = 0.01;
	public static Double addTemperatureCoefficient = 0.01;
	public static Double minusTemperatureCoefficient = 0.01;
	public static Double refuelVolumeCoefficient = 0.005;
	
	public static Double addTemperatureProbability = 0.5;
	public static Double addWaterProbability = 0.3;
	
	public static Double distributorRefuelProbability = 0.2;
	public static Double nozzleRefuelProbability = 0.2;
	
	public static Double addWaterVolumeValue = 0.001;
}
