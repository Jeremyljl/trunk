/* Final Project CS116 Spring 2018
   Jiliang Li
   Karen Weng Liang
   Xin Bai
   Jianfeng Xu
*/

public class ZoneException extends Exception {

    public ZoneException() {
        super("There is something wrong with your zone!");
    }

    public ZoneException(String message) {
        super(message);
    }
}
