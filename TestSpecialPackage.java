/* Final Project CS116 Spring 2018
   Jiliang Li
   Karen Weng Liang
   Xin Bai
   Jianfeng Xu
*/

public class TestSpecialPackage {
    public static void main(String[] args) {
        //test constructor
        Date date1 = new Date(3, 5, 2018);
        SpecialPackage sp1 = new SpecialPackage("KDJ Industries", "Z2", date1, 60, 3, 13);
        System.out.println(sp1);

        //test exception
        SpecialPackage sp2 = new SpecialPackage("KDJ Industries", "Z2", date1, -10, 3, 13);
        System.out.println(sp2);
    }
}
