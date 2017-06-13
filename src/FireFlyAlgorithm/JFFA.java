package FireFlyAlgorithm;

/**
 *
 * @author erigha eseoghene dan
 * @author Joël Schmid, joel.schmid@students.fhnw.ch - enhanced output and restructuring
 */

public class JFFA {

	private FFA ffa;

	private void init_ffa(int verbose) {

		int n = 10; // JS: default 10 (number of fireflies)
		int maxGen = 10; // JS: default 10
		double alpha = 0.25; // JS: default 0.25
		double betamin = 0.2; // JS: default 0.2 //β0 is the attractiveness at r
		double gamma = 1.0; // JS: default 1.0
		int dimension = 6; // JS: default 2 // Equals number of n jobs

		ffa = new FFA(n, maxGen, alpha, betamin, gamma, dimension, verbose);
	}

	/**
	 * @param args
	 * the command line arguments
	 */
	public static void main(String[] args) {

		// JS: Verbosity
		// 1 = only makespan of best ff and time
		// 2 = more detailed (all ffs)
		// 3 = very detailed (ffs plus within iteration information)
		int verbose = 1;

		for (int i = 0; 100 > i; i++) {
			// Measure Runtime
			long startTime = System.currentTimeMillis();
			
			// System.out.println(startTime);
			new JFFA().init_ffa(verbose);

			// Measure Runtime
			long endTime = System.currentTimeMillis();
			long totalTime = endTime - startTime;
			System.out.println("\t" + totalTime);
		}
	}
}
