public class NBody {
	public static int readN(String fname) {
		In in = new In(fname);
		int N = in.readInt();
		return N;
	}

	public static double readRadius(String fname) {
		In in = new In(fname);

		// Read the first integer number in the file (./data/planets.txt).
		// This method won't need this number.
		int N = in.readInt();
		// Read the second double number in the file which is
		// the raidus of the universe.
		double radius = in.readDouble();
		return radius;
	}

	public static Body[] readBodies(String fname) {
		// Suffix of path to the images.
		// E.g., all my images are stored in the images directory
		// so the path of an typical image is something like "images/earth.jpg"
		String imgSuffix = "images/";

		In in = new In(fname);

		int N = in.readInt();
		double radius = in.readDouble();

		// Read attributes of N bodies from the file;
		// Instantiate bodies and store them in allBodies.
		Body[] allBodies = new Body[N];
		for(int i = 0; i < N; i++) {
			double xxPos = in.readDouble();
			double yyPos = in.readDouble();
			double xxVel = in.readDouble();
			double yyVel = in.readDouble();
			double mass = in.readDouble();
			String img = imgSuffix + in.readString();
			allBodies[i] = new Body(xxPos, yyPos, xxVel, yyVel, mass, img);
		}

		return allBodies;
	}

	public static void main(String[] args) {
		StdAudio.play("audio/2001.mid");

		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String fname = args[2];

		int N = readN(fname);
		double radius = readRadius(fname);
		Body[] allBodies = readBodies(fname);

		String backgroundImg = "images/starfield.jpg";
		// Enable double buffering. Copied from the 
		// demo java file although I don't quite understand
		// why I need it.
		StdDraw.enableDoubleBuffering();

		// Set up the scale of the universe.
		StdDraw.setScale(-radius, radius);

		// Clear the drawing window.
		StdDraw.clear();

		// We discretize time so that every dt we update all the
		// bodies in allBodies and draw them again in the window.
		double t = 0.;
		while (t < T) {
			// Calculate and update each and every
			// body's position and velocity
			double[] xForces = new double[N];
			double[] yForces = new double[N];

			for (int i = 0; i < N; i++) {
				Body b = allBodies[i];
				// Calulate the net force (in both x- and y-directions)
				// for current body b.
				xForces[i] = b.calcNetForceExertedByX(allBodies);
				yForces[i] = b.calcNetForceExertedByY(allBodies);
			}

			for (int i = 0; i < N; i++) {
				Body b = allBodies[i];
				// Update  b's position and velocity using the net force on b.
				b.update(dt, xForces[i], yForces[i]);
			}

			// Draw the background image.
			StdDraw.picture(0, 0, backgroundImg);

			// Draw each and every body
			for (Body b : allBodies) {
				b.draw();
			}

			// Show the offscreen buffer.
			StdDraw.show();

			// Pause the image for 10 milliseconds
			StdDraw.pause(10);

			// Increment time t by interval dt.
			t += dt;
		}

		StdOut.printf("%d\n", N);
		StdOut.printf("%.2e\n", radius);
		for (int i = 0; i < N; i++) {
		    StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
		                  allBodies[i].xxPos, allBodies[i].yyPos, allBodies[i].xxVel,
		                  allBodies[i].yyVel, allBodies[i].mass, allBodies[i].imgFileName);   
}


	}
}