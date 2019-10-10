/* Final Project CS116 Spring 2018
   Jiliang Li
   Karen Weng Liang
   Xin Bai
   Jianfeng Xu
*/

public class Package {
    private static int pCounter = 1;    //to generate unique id
    private int packageID;              //package id
    private String receiver;            //receiver company
    private String zone;                //delivery zone
    private Date deliveryDate;          //delivery date
    private int weight, volume;         //package weight, volume

    public Package(String receiver, String zone, Date date, int weight, int volume) {
        try {
            packageID = pCounter;
            setDeliveryDate(date);
            setReceiver(receiver);
            setZone(zone);
            setWeight(weight);
            setVolume(volume);
            pCounter++;
        } catch (PackageException e) {
            e.printStackTrace();
        }
    }

    //Gets the column and the row of the zone
    public int getRowIndex() {
        return zone.charAt(0) - 'A';
    }

    public int getColIndex() {
        return zone.charAt(1) - '1';
    }

    public Package(Package oldPackage) {
        try {
            packageID = oldPackage.getPackageID();
            setReceiver(oldPackage.getReceiver());
            setDeliveryDate(oldPackage.getDeliveryDate());
            setZone(oldPackage.getZone());
            setWeight(oldPackage.getWeight());
            setVolume(oldPackage.getVolume());
        } catch (PackageException e) {
            e.printStackTrace();
        }
    }

    public int getPackageID() {
        return packageID;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) throws PackageException {
        if (zone.charAt(0) >= 'A' && zone.charAt(0) <= 'Z' &&
                zone.charAt(1) >= '1' && zone.charAt(1) <= '9')
            this.zone = zone;
        else throw new PackageException("your zone is illegal!");
    }

    public int getWeight() {
        return weight;
    }
    
    public void setWeight(int weight) throws PackageException {
        if (weight > 0) this.weight = weight;
        else throw new PackageException("weight can not be less than zero!");
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) throws PackageException {
        if (volume > 0) this.volume = volume;
        else throw new PackageException("volume can not be less than zero!");
    }

    public String toString() {
        return "Package ID:" + packageID + " " + "receiver:" + receiver + " " + "area:" + getZone() + " " + "Weight:" + weight + " " + "Volume:" + volume;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
}
