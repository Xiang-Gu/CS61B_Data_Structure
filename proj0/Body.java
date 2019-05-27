public class Body {
	public double xxPos, yyPos, xxVel, yyVel, mass;
	public String imgFileName;
	public static final double gravAcc = 6.67e-11;

	public Body(double xP, double yP, double xV, 
				double yV, double m, String img) {
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}

	public Body(Body b) {
		xxPos = b.xxPos;
		yyPos = b.yyPos;
		xxVel = b.xxVel;
		yyVel = b.yyVel;
		mass = b.mass;
		imgFileName = b.imgFileName;
	}

	public double calcDistance(Body b) {
		return Math.sqrt(Math.pow(xxPos - b.xxPos, 2) + Math.pow(yyPos - b.yyPos, 2));
	}
	
	public double calcForceExertedBy(Body b) {
		double distance = calcDistance(b);
		double force = gravAcc * (mass * b.mass) / Math.pow(distance, 2);
		return force;
	}

	public double calcForceExertedByX(Body b) {
		double force = calcForceExertedBy(b);
		double forceByX = force * (b.xxPos - xxPos) / calcDistance(b);
		return forceByX;
	}

	public double calcForceExertedByY(Body b) {
		double force = calcForceExertedBy(b);
		double forceByY = force * (b.yyPos - yyPos) / calcDistance(b);
		return forceByY;
	}

	public double calcNetForceExertedByX(Body[] allBodys) {
		double netForceX = 0.;
		for (Body b : allBodys) {
			// calculate force exerted by b only if b is not equal
			// to this body. Bodys cannot exert forces on themselves.
			if (this.equals(b) == false) {
				netForceX += calcForceExertedByX(b);
			}
		}
		return netForceX;
	}

	public double calcNetForceExertedByY(Body[] allBodys) {
		double netForceY = 0.;
		for (Body b : allBodys) {
			// calculate force exerted by b only if b is not equal
			// to this body. Bodys cannot exert forces on themselves.
			if (this.equals(b) == false) {
				netForceY += calcForceExertedByY(b);
			}
		}
		return netForceY;
	}

	public void update(double dt, double fX, double fY) {
		// Compute accelerations in both directions.
		double xxAcc = fX / mass;
		double yyAcc = fY / mass;

		// Update velocity. 
		xxVel += dt * xxAcc;
		yyVel += dt * yyAcc;

		// Update position.
		xxPos += dt * xxVel;
		yyPos += dt * yyVel;
	}

	public void draw() {
		StdDraw.picture(xxPos, yyPos, imgFileName);
	}


}