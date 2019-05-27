public class TestBody {
	public static void main(String[] args) {
		Body bodyA = new Body(0, 0, 1, -1, 3, " ");
		Body bodyB = new Body(4, 8, -2, 1.2, 2, " ");
		Body bodyC = new Body(-1, -7, 1.2, 3.4, 1, " ");
		Body bodyD = new Body(bodyC);

		System.out.println("Pairwise force on A exerted by B = " + bodyA.calcForceExertedBy(bodyB));
		System.out.println("Pairwise force on B exerted by A = " + bodyB.calcForceExertedBy(bodyA));

		System.out.println("Pairwise force on C exerted by D = " + bodyC.calcForceExertedBy(bodyD));
		System.out.println("Pairwise force on D exerted by D = " + bodyD.calcForceExertedBy(bodyD));

	}
}