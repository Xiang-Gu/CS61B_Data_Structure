public class Exercise_2 {
    /** Returns the maximum value from m. */
    public static int max(int[] m) {
        if (m.length == 0) {
        	System.out.println("Error: input array must have at least one element.");
        	return 0;
        }
        else {
        	int result = m[0];
        	for (int i = 1; i < m.length; i++) {
        		if (m[i] > result) {
        			result = m[i];
        		}
        	}
        	return result;
        }
    }

    public static void main(String[] args) {
       int[] numbers = new int[]{9, 2, 15, 2, 22, 10, 6};   
       System.out.println(max(numbers));   
    }
}