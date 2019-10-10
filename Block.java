
/* Final Project CS116 Spring 2018
   Jiliang Li
   Karen Weng Liang
   Xin Bai
   Jianfeng Xu

	We created an additional class to arrange our packages to the trucks
*/

import java.util.ArrayList;

public class Block {
	public ArrayList<Package> packages; 
	public ArrayList<Boolean> arranged; // to determine if packages have been arranged
	public ArrayList<Truck> trucks;

	public int indexSP;

	public Block(int startRow, int startCol) {
		trucks = new ArrayList<Truck>();
		packages = new ArrayList<Package>();
		arranged = new ArrayList<Boolean>(); 
		indexSP = 0;
	}

	public Block() {
		trucks = new ArrayList<Truck>();
		packages = new ArrayList<Package>();
		arranged = new ArrayList<Boolean>();
		indexSP = 0;
	}

	public int getNormalPackageSize() {
		return indexSP;
	}

	public Package getNormalPackageAt(int i) {
		return packages.get(i);
	}

	public int getSpecialPackageSize() {
		return packages.size() - indexSP;
	}

	public SpecialPackage getSpecialPackageAt(int i) {
		return (SpecialPackage) packages.get(i + indexSP);
	}
	
	//For packages that have not been arranged, check if they are assigned to same zone as truck where truck is
	public void SameArea(Truck truck) {
		Package p1;
		for (int i = 0; i < getNormalPackageSize(); i++) {
			if (!arranged.get(i)) {
				p1 = getNormalPackageAt(i);
				if (truck.getCurrentColIndex() == p1.getColIndex()
						&& truck.getCurrentRowIndex() == p1.getRowIndex()) {
					arranged.remove(i);
					arranged.add(i, new Boolean(true));
					truck.loadPackage(p1);
				}
			}
		}
	}

	//Arranges special packages into trucks
	public void ArrangeSpecial() {
		SpecialPackage temp;
		for (int index = 0; index < getSpecialPackageSize(); index++) {
			temp = getSpecialPackageAt(index);
			Truck t = findTruckInTimeAndZone(temp);
			if (t == null) {
				Truck addTruck;
				int totalWeight = ZoneWeight(temp.getRowIndex(), temp.getColIndex(), index);
				int totalVolume = ZoneVolume(temp.getRowIndex(), temp.getColIndex(), index);
				//creating truck objects according to weight/volume
				if (totalVolume <= 1000 && totalWeight <= 2000) {
					addTruck = new Truck(1, temp.getTime(), temp.getRowIndex(), temp.getColIndex());
				} else if (totalVolume <= 2000 && totalWeight <= 4000) {
					addTruck = new Truck(2, temp.getTime(), temp.getRowIndex(), temp.getColIndex());
				} else {
					addTruck = new Truck(3, temp.getTime(), temp.getRowIndex(), temp.getColIndex());
				}
				addTruck.loadSpecialPackage(temp, temp.getTime()); 
				trucks.add(addTruck);
				SameArea(addTruck);
			} else {
				t.loadSpecialPackage(temp, temp.getTime());
			}
			index++;
		}
	}

	public void ArrangeRegular() {
		Package temp;
		for (int i = 0; i < getNormalPackageSize(); i++) {
			if (!arranged.get(i)) {
				temp = getNormalPackageAt(i);
				Truck t = findZone(temp);
				if (t != null) {
					boolean done = t.loadPackage(temp);
					if (done == true) {
						arranged.remove(i);
						arranged.add(i, new Boolean(true));
						continue;
					}
				}
				t = Adjacent(temp);
				if (t != null) {
					boolean done = t.loadPackage(temp);
					if (done == true) {
						arranged.remove(i);
						arranged.add(i, new Boolean(true));
						continue;
					}
				}
				Truck addTruck;
				int totalWeight = ZoneWeight(temp.getRowIndex(), temp.getColIndex(), -1);
				int totalVolume = ZoneVolume(temp.getRowIndex(), temp.getColIndex(), -1);
				if (totalVolume <= 1000 && totalWeight <= 2000) {
					addTruck = new Truck(1, 9, temp.getRowIndex(), temp.getColIndex());
				} else if (totalVolume <= 2000 && totalWeight <= 4000) {
					addTruck = new Truck(2, 9, temp.getRowIndex(), temp.getColIndex());
				} else {
					addTruck = new Truck(3, 9, temp.getRowIndex(), temp.getColIndex());
				}
				addTruck.loadPackage(temp);
				arranged.remove(i);
				arranged.add(i, new Boolean(true));
				trucks.add(addTruck);
			}
		}
	}

	public Truck findZone(Package p) {
		for (int i = 0; i < trucks.size(); i++) {
			Truck t = trucks.get(i);
			if (t.getCurrentRowIndex() == p.getRowIndex()
					&& t.getCurrentColIndex() == p.getColIndex()) {
				if (p.getVolume() + t.getCurrentVolume() <= t.gettVolume()
						&& p.getWeight() + t.getCurrentWeight() <= t.gettWeight()) {
					return t;
				}
			}
		}
		return null;
	}

	public Truck findTruckInTimeAndZone(SpecialPackage sp1) {
		for (int i = 0; i < trucks.size(); i++) {
			Truck t = trucks.get(i);
			if (t.hastruck(sp1.getTime(), sp1.getRowIndex(), sp1.getColIndex())) {
				if (t.getAvailableStops(sp1.getTime()) > 0) {
					if (sp1.getVolume() + t.getCurrentVolume() <= t.gettVolume()
							&& sp1.getWeight() + t.getCurrentWeight() <= t.gettWeight()) {
						return t;
					}
				}
			}
		}
		return null;
	}

	// 
	public Truck Adjacent(Package p) {
		for (int i = 0; i < trucks.size(); i++) {
			Truck t = trucks.get(i);
			if (p.getVolume() + t.getCurrentVolume() <= t.gettVolume()
					&& p.getWeight() + t.getCurrentWeight() <= t.gettWeight()) {
				if ((t.getCurrentColIndex() == p.getColIndex() - 1
						&& t.getCurrentRowIndex() == p.getRowIndex())
						|| t.getCurrentColIndex() == p.getColIndex() + 1
								&& t.getCurrentRowIndex() == p.getRowIndex()
						|| t.getCurrentColIndex() == p.getColIndex()
								&& t.getCurrentRowIndex() == p.getRowIndex() + 1
						|| t.getCurrentColIndex() == p.getColIndex()
								&& t.getCurrentRowIndex() == p.getRowIndex() - 1) {
					boolean done = t.moveTo(p.getRowIndex(), p.getColIndex(), -1);
					if (done == false) {
						return null;
					} else {
						return t;
					}
				}
			}
		}
		return null;
	}

	// Get the total volume in zone
	public int ZoneVolume(int row, int col, int index) {
		int volume = 0;
		for (int i = 0; i < indexSP; i++) {
			if (packages.get(i).getRowIndex() == row && packages.get(i).getColIndex() == col
					&& arranged.get(i) == false) {
				volume += packages.get(i).getVolume();
			}
		}

		int i;
		if (index < 0) {
			i = packages.size();
		} else {
			i = index + indexSP;
		}

		for (; i < packages.size(); i++) {
			volume += packages.get(i).getVolume();
		}
		return volume;
	}

	//Get the total weight in zone
	public int ZoneWeight(int row, int col, int index) {
		int weight = 0;
		for (int i = 0; i < indexSP; i++) {
			if (packages.get(i).getRowIndex() == row && packages.get(i).getColIndex() == col
					&& arranged.get(i) == false) {
				weight += packages.get(i).getVolume();
			}
		}
		
		int i;
		if (index < 0) {
			i = packages.size();
		} else {
			i = index + indexSP;
		}
		
		for (; i < packages.size(); i++) {
			weight += packages.get(i).getVolume();
		}
		return weight;
	}
}
