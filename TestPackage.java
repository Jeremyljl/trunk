/* Final Project CS116 Spring 2018
   Jiliang Li
   Karen Weng Liang
   Xin Bai
   Jianfeng Xu
*/

public class TestPackage {
    public static void main(String[] args) {
        //test constructor
        Date date1 = new Date(10, 13, 2007);
        Package p1 = new Package("HAL Industries", "A2", date1, 10, 2);
        System.out.println(p1);

        //test copy constructor
        Package p2 = new Package(p1);
        System.out.println(p2);

        //test exception
        Package p3 = new Package("HAL Industries", "11", date1, 10, 2);
        System.out.println(p3);
    }
}
