package data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PetrolStation {
	
	private int petrolStationId;
	private static int nextDistributorId = 0;
	private static int nextTankId = 0;
	private static int nextNoozleId = 0;
	
	private List<Distributor> distributorList = new ArrayList<Distributor>();
	private List<Tank> tankList = new ArrayList<Tank>();
	
	public void addTank(Double minimalVolume, Double maximalVolume){
		Tank tank = new Tank(nextTankId, minimalVolume, maximalVolume);
		tankList.add(tank);
		nextTankId++;
	}
	
	public void addDistributor(){
		Distributor distributor = new Distributor();
		distributor.setDistributroId(nextDistributorId);
		nextDistributorId++;
		
		Iterator<Tank> it = tankList.iterator();
		while(it.hasNext()){
			Nozzle noozle = new Nozzle();
			noozle.setNoozleId(nextNoozleId);
			nextNoozleId++;
			noozle.setTank(it.next());
			distributor.getNoozleList().add(noozle);
		}
		distributorList.add(distributor);
	}
	
	public int getPetrolStationId() {
		return petrolStationId;
	}
	public void setPetrolStationId(int petrolStationId) {
		this.petrolStationId = petrolStationId;
	}
	public List<Distributor> getDistributorList() {
		return distributorList;
	}
	public void setDistributorList(List<Distributor> distributorList) {
		this.distributorList = distributorList;
	}
	public List<Tank> getTankList() {
		return tankList;
	}
	public void setTankList(List<Tank> tankList) {
		this.tankList = tankList;
	}
}
