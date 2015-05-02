package database;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import simulator.SimulatorConfig;
import data.NozzleMeasureProcessed;
import data.TankMeasureInterval;
import data.TankMeasureProcessed;

public class MySQLAccess {

	private SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

	public void writeTankMeasureProcessedToDatabase(
			TankMeasureProcessed tankMeasureProcessed) {
		// PreparedStatements can use variables and are more efficient
		Connection connect = null;
		PreparedStatement preparedStatement = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection(
					"jdbc:mysql://"
					+ SimulatorConfig.databaseUrl
					+ "/"
					+ SimulatorConfig.databaseName, 
					SimulatorConfig.databaseUser, 
					SimulatorConfig.databasePassword);

			preparedStatement = connect
					.prepareStatement("insert into  zbdihd.tank_measures values (default, ?, ?, ?, ? , ?, ?)");
			// Parameters start with 1

			BigDecimal grossVolume = new BigDecimal(tankMeasureProcessed
					.getGrossVolume().toString()).setScale(15,
					BigDecimal.ROUND_HALF_EVEN);
			BigDecimal nettoVolume = new BigDecimal(
					tankMeasureProcessed.getNettoVolume()).setScale(15,
					BigDecimal.ROUND_HALF_EVEN);
			BigDecimal waterVolume = new BigDecimal(tankMeasureProcessed
					.getWaterVolume().toString()).setScale(15,
					BigDecimal.ROUND_HALF_EVEN);
			BigDecimal temperature = new BigDecimal(tankMeasureProcessed
					.getTemperature().toString()).setScale(15,
					BigDecimal.ROUND_HALF_EVEN);

			preparedStatement.setInt(1, tankMeasureProcessed.getTankId());
			preparedStatement.setString(2,
					sdf.format(tankMeasureProcessed.getTimeStamp().getTime()));
			preparedStatement.setBigDecimal(3, grossVolume);
			preparedStatement.setBigDecimal(4, nettoVolume);
			preparedStatement.setBigDecimal(5, waterVolume);
			preparedStatement.setBigDecimal(6, temperature);
			preparedStatement.executeUpdate();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connect.close();
				preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public void writeNozzleMeasureProcessedToDatabase(
			NozzleMeasureProcessed nozzleMeasureProcessed) {
		// PreparedStatements can use variables and are more efficient
		Connection connect = null;
		PreparedStatement preparedStatement = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection(
					"jdbc:mysql://"
					+ SimulatorConfig.databaseUrl
					+ "/"
					+ SimulatorConfig.databaseName, 
					SimulatorConfig.databaseUser, 
					SimulatorConfig.databasePassword);

			preparedStatement = connect
					.prepareStatement("insert into  zbdihd.nozzles_measures values (default, ?, ?, ?, ?, ?, ?, ?, ?)");
			// Parameters start with 1
			BigDecimal rawVolume = new BigDecimal(nozzleMeasureProcessed
					.getRawVolume().toString()).setScale(15,
					BigDecimal.ROUND_HALF_EVEN);
			BigDecimal nettoVolume = new BigDecimal(
					nozzleMeasureProcessed.getNettoVolume()).setScale(15,
					BigDecimal.ROUND_HALF_EVEN);
			BigDecimal grossVolume = new BigDecimal(
					nozzleMeasureProcessed.getNettoVolume()).setScale(15,
					BigDecimal.ROUND_HALF_EVEN);
			BigDecimal temperature = new BigDecimal(nozzleMeasureProcessed
					.getTemperature().toString()).setScale(15,
					BigDecimal.ROUND_HALF_EVEN);

			preparedStatement.setInt(1, nozzleMeasureProcessed.getNoozleId());
			preparedStatement.setInt(2, nozzleMeasureProcessed.getTankId());
			preparedStatement.setString(3, sdf.format(nozzleMeasureProcessed
					.getBeginTimeStamp().getTime()));
			preparedStatement.setString(4, sdf.format(nozzleMeasureProcessed
					.getEndTimeStamp().getTime()));
			preparedStatement.setBigDecimal(5, rawVolume);
			preparedStatement.setBigDecimal(6, grossVolume);
			preparedStatement.setBigDecimal(7, nettoVolume);
			preparedStatement.setBigDecimal(8, temperature);
			preparedStatement.executeUpdate();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connect.close();
				preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
	
	public void writeTankMeasureIntervalToDatabase(
			TankMeasureInterval tankMeasureInterval) {
		// PreparedStatements can use variables and are more efficient
		Connection connect = null;
		PreparedStatement preparedStatement = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection(
					"jdbc:mysql://"
					+ SimulatorConfig.databaseUrl
					+ "/"
					+ SimulatorConfig.databaseName, 
					SimulatorConfig.databaseUser, 
					SimulatorConfig.databasePassword);

			preparedStatement = connect
					.prepareStatement("insert into  zbdihd.fuel_leakage_detections values (default, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			// Parameters start with 1
			BigDecimal tankIntervalNettoVolume = new BigDecimal(tankMeasureInterval.getTankIntervalNettoVolume().toString()).setScale(15,BigDecimal.ROUND_HALF_EVEN);
			BigDecimal tankIntervalGrossVolume = new BigDecimal(tankMeasureInterval.getTankIntervalGrossVolume()).setScale(15,BigDecimal.ROUND_HALF_EVEN);
			BigDecimal nozzlesIntervalNettoVolume = new BigDecimal(tankMeasureInterval.getNozzlesIntervalNettoVolume()).setScale(15,BigDecimal.ROUND_HALF_EVEN);
			BigDecimal nozzlesIntervalGrossVolume = new BigDecimal(tankMeasureInterval.getNozzlesIntervalGrossVolume().toString()).setScale(15,BigDecimal.ROUND_HALF_EVEN);
			BigDecimal differentialNettoVolume = new BigDecimal(tankMeasureInterval.getDifferentialNettoVolume().toString()).setScale(15,BigDecimal.ROUND_HALF_EVEN);
			BigDecimal differentialGrossVolume = new BigDecimal(tankMeasureInterval.getDifferentialGrossVolume().toString()).setScale(15,BigDecimal.ROUND_HALF_EVEN);
			
			preparedStatement.setInt(1, tankMeasureInterval.getTankId());
			preparedStatement.setString(2, sdf.format(tankMeasureInterval
					.getBeginTimeStamp().getTime()));
			preparedStatement.setString(3, sdf.format(tankMeasureInterval
					.getEndTimeStamp().getTime()));
			preparedStatement.setBigDecimal(4, tankIntervalNettoVolume);
			preparedStatement.setBigDecimal(5, tankIntervalGrossVolume);
			preparedStatement.setBigDecimal(6, nozzlesIntervalNettoVolume);
			preparedStatement.setBigDecimal(7, nozzlesIntervalGrossVolume);
			preparedStatement.setBigDecimal(8, differentialNettoVolume);
			preparedStatement.setBigDecimal(9, differentialGrossVolume);
			preparedStatement.setBoolean(10, tankMeasureInterval.isDetectedFuelLeakage());
			
			preparedStatement.executeUpdate();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connect.close();
				preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

}
