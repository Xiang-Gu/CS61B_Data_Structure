public class HelloNumbers {
	public static void main(String[] args) {
		int x = 0;
		int current_sum = 0
		while (x < 10) {
			current_sum = current_sum + x;
			System.out.println(current_sum);
			x = x + 1;
		}
	}
}
