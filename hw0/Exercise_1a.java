public class Exercise_1a {
	public static void main(String[] args) {
		int cnt = 5;
		while (cnt > 0) {
			String str = "";
			for (int i = 0; i < cnt; i++) {
				str += "*";
			}
			System.out.println(str);
			cnt = cnt - 1;
		}
	}
}