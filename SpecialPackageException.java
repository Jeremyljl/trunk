/* Final Project CS116 Spring 2018
   Jiliang Li
   Karen Weng Liang
   Xin Bai
   Jianfeng Xu
*/

public class SpecialPackageException extends Exception {

    public SpecialPackageException() {
        super("There is something wrong with your special package!");
    }

    public SpecialPackageException(String message) {
        super(message);
    }

}