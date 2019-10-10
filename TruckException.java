/* Final Project CS116 Spring 2018
   Jiliang Li
   Karen Weng Liang
   Xin Bai
   Jianfeng Xu
*/

public class TruckException extends Exception {

    public TruckException() {
        super("There is something wrong with your truck!");
    }

    public TruckException(String message) {
        super(message);
    }

}