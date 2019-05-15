public class Exercise_4 {
  public static void windowPosSum(int[] a, int n) {
    for (int i = 0; i < a.length; i++) {
      // Only rewrite elements when it is positive.
      if (a[i] > 0) {
        int sum = a[i];
        for (int j = i + 1; j < a.length && j <= i + n; j++) {
          sum += a[j];
        }
        
        a[i] = sum;
      }
    }
  }

  public static void main(String[] args) {
    int[] a = {1, 2, -3, 4, 5, 4};
    int n = 3;
    windowPosSum(a, n);

    // Should print 4, 8, -3, 13, 9, 4
    System.out.println(java.util.Arrays.toString(a));
  }
}