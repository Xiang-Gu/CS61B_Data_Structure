/* The bug is that we pass int variable to the method
Flik.isSameNumber() which has Integer as its input argument type.
But I really don't know how to explain the behavior -- why this
function returns true for i,j's that are less than 128 but false
when they are 128. Also I don't know what happened when you pass
variables of different type, as required in a function signature,
to this function.
 */


public class HorribleSteve {
    public static void main(String [] args) throws Exception {
        int i = 0;
        for (int j = 0; i < 500; ++i, ++j) {
            if (!Flik.isSameNumber(i, j)) {
                throw new Exception(
                        String.format("i:%d not same as j:%d ??", i, j));
            }
        }
        System.out.println("i is " + i);
    }
}
