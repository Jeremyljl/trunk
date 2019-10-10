/* Final Project CS116 Spring 2018
   Jiliang Li
   Karen Weng Liang
   Xin Bai
   Jianfeng Xu
*/

public class PackageException extends Exception {

    public PackageException() {
        super("There is something wrong with your package!");
    }

    public PackageException(String message) {
        super(message);
    }

}