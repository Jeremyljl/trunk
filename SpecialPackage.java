/* Final Project CS116 Spring 2018
   Jiliang Li
   Karen Weng Liang
   Xin Bai
   Jianfeng Xu
*/

public class SpecialPackage extends Package implements Comparable {
	
	private int time; // limit time for special package

	public SpecialPackage(String receiver, String zone, Date date, int weight, int volume, int time) {
		super(receiver, zone, date, weight, volume);
		try {
			setTime(time);
		} catch (SpecialPackageException e) {
			e.printStackTrace();
		}
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) throws SpecialPackageException {
		if (time >= 9 && time <= 16)
			this.time = time;
		else
			throw new SpecialPackageException("Time should be between 9 and 16");
	}

	public String toString() {
		return super.toString() + " time:" + time + " specialPackage";
	}

	public int compareTo(Object sp) {
		SpecialPackage temp = (SpecialPackage) sp;
		if (time > temp.time) {
			return 1;
		} else if (time == temp.time) {
			return 0;
		} else {
			return -1;
		}
	}
}
