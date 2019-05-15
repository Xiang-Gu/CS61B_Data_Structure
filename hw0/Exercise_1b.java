public class Exercise_1b {
	public static void drawTriangle(int N) {
		int cnt = 1;
		while (cnt <= N) {
			String str = "";
			for (int i = 0; i < cnt; i++) {
				str += "*";
			}
			
			System.out.println(str);
			
			cnt += 1;
		}
	}

	public static void main(String[] args) {
		drawTriangle(10);
	}
}