package data;

import java.util.ArrayList;
import java.util.List;

public class Distributor {
	 
	private	int distributorId;
	
	private List<Nozzle> noozleList = new ArrayList<Nozzle>();

	public int getDistributroId() {
		return distributorId;
	}

	public void setDistributroId(int distributorId) {
		this.distributorId = distributorId;
	}

	public List<Nozzle> getNoozleList() {
		return noozleList;
	}

	public void setNoozleList(List<Nozzle> noozleList) {
		this.noozleList = noozleList;
	}
	
}
