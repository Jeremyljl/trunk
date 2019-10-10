import java.util.ArrayList;

public class Truck {
    private static int pCounter = 1;       //to generate unique id
    private int truckID;                   //truck id
    private int tWeight;                   //limit weight
    private int tVolume;                   //limit volume
    private int type;                      //truck type 1:small 2:medium 3:large
    private int currentWeight;             //current weight
    private int currentVolume;             //current volume
    private int currentRowIndex;
    private int currentColIndex;
    private int startTime;
    private int[] unusedCount;
    private ArrayList<Package> packageArrayList;               //a list of packages loaded by truck
    private ArrayList<String> currentDeliveryPosition;

    public Truck(int type, int time, int currentRowIndex, int currentColIndex) {
        try {
            this.truckID = pCounter;
            unusedCount = new int[9];
            for (int i = 0; i < 9; i++) {
                unusedCount[i] = 5;
            }
            setCurrentRowIndex(currentRowIndex);
            setCurrentColIndex(currentColIndex);
            startTime = time;
            setType(type);
            switch (type) {
                case 1:                  //construct small truck
                    settWeight(2000);
                    settVolume(1000);
                    break;
                case 2:                  //construct medium truck
                    settWeight(4000);
                    settVolume(2000);
                    break;
                case 3:                  //construct large truck
                    settWeight(8000);
                    settVolume(4000);
                    break;
                default:
                    break;
            }
            currentWeight = 0;
            currentVolume = 0;
            packageArrayList = new ArrayList<Package>();
            currentDeliveryPosition = new ArrayList<String>();
            pCounter++;
        } catch (TruckException e) {
            e.printStackTrace();
        }
    }

    public int getTruckID() {
        return truckID;
    }

    public int gettWeight() {
        return tWeight;
    }

    public void settWeight(int tWeight) {
        this.tWeight = tWeight;
    }

    public int gettVolume() {
        return tVolume;
    }

    public void settVolume(int tVolume) {
        this.tVolume = tVolume;
    }

    public int getType() {
        return type;
    }

   
    public void setType(int type) throws TruckException {
        if (type == 1 || type == 2 || type == 3)
            this.type = type;
        else throw new TruckException("truck type is illegal!");
    }

    public int getCurrentWeight() {
        return currentWeight;
    }

   
    public void setCurrentWeight(int currentWeight) throws TruckException {
        if (currentWeight >= 0 && gettWeight() >= currentWeight)
            this.currentWeight = currentWeight;
        else throw new TruckException("currentWeight is illegal!");
    }

    public int getCurrentVolume() {
        return currentVolume;
    }

    
    public void setCurrentVolume(int currentVolume) throws TruckException {
        if (currentVolume >= 0 && gettVolume() >= currentVolume)
            this.currentVolume = currentVolume;
        else throw new TruckException("currentVolume is illegal!");
    }

    public int getCurrentRowIndex() {
        return currentRowIndex;
    }

    public void setCurrentRowIndex(int endRowIndex) {
        this.currentRowIndex = endRowIndex;
    }

    public int getCurrentColIndex() {
        return currentColIndex;
    }

    public void setCurrentColIndex(int endColIndex) {
        this.currentColIndex = endColIndex;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }
    public ArrayList<String> getCurrentDeliveryPosition() {
        return currentDeliveryPosition;
    }
    public ArrayList<Package> getPackageArrayList() {
        return packageArrayList;
    }

    public void setPackageArrayList(ArrayList<Package> packageArrayList) {
        this.packageArrayList = packageArrayList;
    }

   
    public String toString() {
        String s = "";
        switch (type) {
            case 1:
                s = "small";
                break;
            case 2:
                s = "medium";
                break;
            case 3:
                s = "large";
                break;
            default:
                break;
        }
        return "Truck ID:" + truckID + " " + "type:" + s + " " + "currentWeight:" + currentWeight + " " + "currentVolume:" + currentVolume;
    }


    public boolean loadPackage(Package p) {
        if ((getCurrentVolume() + p.getVolume()) <= gettVolume() && (getCurrentWeight() + p.getWeight()) <= gettWeight()) {
            try {
                int i = 0;
                if (currentDeliveryPosition.contains(p.getReceiver())) {
                    setCurrentWeight(getCurrentWeight() + p.getWeight());
                    setCurrentVolume(getCurrentVolume() + p.getVolume());
                    packageArrayList.add(p);
                    return true;
                }
                while (unusedCount[i] <= 0)
                    i++;
                if (i < 9) {
                    unusedCount[i] -= 1;
                } else {
                    return false;
                }
                currentDeliveryPosition.add(p.getReceiver());
                setCurrentWeight(getCurrentWeight() + p.getWeight());
                setCurrentVolume(getCurrentVolume() + p.getVolume());
            } catch (TruckException e) {
                e.printStackTrace();
            }
            packageArrayList.add(p);
            return true;
        } else {
            return false;
        }
    }

    
    public boolean loadSpecialPackage(SpecialPackage p, int time) {
        if ((getCurrentVolume() + p.getVolume()) <= gettVolume() && (getCurrentWeight() + p.getWeight()) <= gettWeight()) {
            try {
                if (currentDeliveryPosition.contains(p.getReceiver())) {
                    setCurrentWeight(getCurrentWeight() + p.getWeight());
                    setCurrentVolume(getCurrentVolume() + p.getVolume());
                    packageArrayList.add(p);
                    return true;
                }
                unusedCount[time - 9] -= 1;
                currentDeliveryPosition.add(p.getReceiver());
                setCurrentWeight(getCurrentWeight() + p.getWeight());
                setCurrentVolume(getCurrentVolume() + p.getVolume());
            } catch (TruckException e) {
                e.printStackTrace();
            }
            packageArrayList.add(p);
            return true;
        } else {
            return false;
        }
    }

    
    public boolean moveTo(int row, int col, int time) {
        this.currentRowIndex = row;
        this.currentColIndex = col;
        currentDeliveryPosition.clear();
        if (time >= 0) {
            unusedCount[time - 9] -= 1;
            return true;
        }
        int i = 0;
        while (unusedCount[i] == 0)
            i++;
        if (i < 9) {
            unusedCount[i] -= 1;
            return true;
        } else {
            return false;
        }
    }

 
    public int getAvailableStops(int time) {
        return unusedCount[time - 9];
    }

    public boolean hastruck(int time, int row, int col) {
        return time == this.startTime && this.currentRowIndex == row && this.currentColIndex == col;
    }
}
