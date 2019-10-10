/* Final Project CS116 Spring 2018
   Jiliang Li
   Karen Weng Liang
   Xin Bai
   Jianfeng Xu
*/

public class TestTruck {
    public static void main(String[] args) {
        //test constructor
        Truck t = new Truck(2,9,1,1);
        System.out.println(t);

        //test exception
        try {
            t.setCurrentVolume(3000);
        } catch (TruckException e) {
            e.printStackTrace();
        }
    }
}
