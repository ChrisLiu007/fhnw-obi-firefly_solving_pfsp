package FireFlyAlgorithm;

import FireFlyAlgorithm.FireFlyFitnessFunction;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.TreeMap;

/**
 *
 * @author erigha eseoghene dan
 * @author Joël Schmid, joel.schmid@students.fhnw.ch - enhanced output and restructuring
 */

public class FFA {

	private int verbose;
	private final Random rand; // Random number generator
	private final int n; // number of fireflies
	private final int maxGen; // number of generations
	private double alpha; // alpha
	private final double betamin; // betamin
	private final double gamma; // Absorption coefficent
	private final int dimension; // problem dimension
	private final double[] lb; // Array of upper bound
	private final double[] ub; // Array of lower bound
	private final double[] u0;
	private double fbest; // best fitness value at current generation (Light
							// Intensity)
	private FireFly bestFireFly; // best firefly//Solution

	protected FireFly[] aFireFlies;

	public FFA(int n, int maxGen, double alpha, double betamin, double gamma,
			int dimension, int verbose) {
		this.verbose = verbose;
		this.n = n;
		this.maxGen = maxGen;
		this.betamin = betamin;
		this.gamma = gamma;
		this.alpha = alpha;
		this.dimension = dimension;
		aFireFlies = new FireFly[n];
		lb = new double[dimension];
		ub = new double[dimension];
		u0 = new double[dimension];
		rand = new Random();
		executeFFA(); // executes the firefly algorithm
	}

	// Set Limits
	private void findLimits(int i) {
		int k;

		for (k = 0; k < dimension; k++) {

			if (aFireFlies[i].getValue(k) < lb[k]) {
				// aFireFlies[i].setValue(k, lb[k]);

			}
			if (aFireFlies[i].getValue(k) > ub[k]) {
				// aFireFlies[i].setValue(k, ub[k]);
			}
		}
	}

	// Update alpha
	private void alpha_new(double aAlpha, int NGen) {
		double delta; // delta parameter
		delta = 1.0 - Math.pow((Math.pow(10.0, -4) / 0.9), (1 / (double) NGen));
		alpha = (1 - delta) * aAlpha;
		// System.out.println("this is alpha:" + delta);
	}

	// Initialize upper and lower bounds
	private void init_bounds() {

		for (int i = 0; i < dimension; i++) {
			lb[i] = -500.0;
			ub[i] = 500.0;
		}
	}

	// Format Number
	private double formatNumber(double num) {
		DecimalFormat myFormat = new DecimalFormat("##.#####");
		String outVar = myFormat.format(num);
		return Double.parseDouble(outVar);
	}

	// Sort fireflies according to fitness
	private void sort_ffa() {
		// Arrays.sort(aFireFlies,Collections.reverseOrder());
		Arrays.sort(aFireFlies); // Sort by LightIntensity!
	}

	// Initial Random guess
	private void initialRandomGuess() {
		for (int i = 0; i < dimension; i++) {
			double rNum = rand.nextDouble();
			u0[i] = formatNumber(lb[i] + (ub[i] - lb[i]) * rNum);
			// System.out.println("This is u0:" + u0[i]);
		}
	}

	// Display results for each generation
	private void displayResults(int numGen, FireFly aFireFly) {
		System.out.println("\nGENERATION: " + numGen + "\t" + "Best Fitness: "
				+ aFireFly.getLightIntensity());
		System.out.print("FireFly position: ");
		for (int i = 0; i < dimension; i++) {

			System.out.print(aFireFly.getValue(i) + ", ");
		}
		System.out.println();
	}

	// Initialize firefly
	private void init_ffa() {
		int i, j;
		double r;

		// initialize upper and lower bounds
		init_bounds();

		// initial Random guess
		initialRandomGuess();

		// Initial location of fireflies
		for (i = 0; i < n; i++) {
			aFireFlies[i] = new FireFly(dimension);

			for (j = 0; j < dimension; j++) {

				r = formatNumber(rand.nextDouble());

				double value = formatNumber(r * (ub[j] - lb[j]) + lb[j]);
				// System.out.println("This is the dimension: " + value);
				aFireFlies[i].setValue(j, value); // setValues for each firefly
													// agent's dimension
			}
			aFireFlies[i].setLightIntensity(1.0); // initialize each firefly's
													// light intensity to 1.0
		}

	}

	// Execute the algorithm
	private void executeFFA() {
		int t = 0;
		init_ffa();

		while (t < maxGen) {

			// This line of reducing alpha gradually is optional
			alpha_new(alpha, maxGen);

			int ranks[] = new int[aFireFlies.length];

			// JS: Get Current Firefly Ranks (Each locations represents a
			// dimension)
			/*
			 * double newArray[] = new double[aFireFlies.length]; for (int i =
			 * 0; i < aFireFlies.length; i++) { //
			 * System.out.println(aFireFlies[i].getValue(1)); newArray[i] =
			 * aFireFlies[i].getValue(1); //System.out.println(); } ranks =
			 * getRanksArray(newArray);
			 */

			// Evaluate new solutions
			for (int i = 0; i < n; i++) {

				// JS: Get Dimensions of firefly in array
				double arrLocations[] = new double[dimension];

				for (int j = 0; j < dimension; j++) {
					// System.out.print("Dimension Location: " +
					// aFireFlies[i].getValue(j));
					arrLocations[j] = aFireFlies[i].getValue(j);
				}

				// Output Dimensions
				/*
				 * for (int j = 0; j < dimension; j++) {
				 * System.out.print(arrLocations[j] + "/ ");
				 * //System.out.println(); }
				 */

				// JS: Get Order/Ranks depending on Location
				ranks = getRanksArray(arrLocations);

				// JS Output Ranks:
				if (verbose > 2) {
					System.out.print(i + ". "
							+ aFireFlies[i].getLightIntensity() + " "
							+ aFireFlies[i].getCurrMakespan()
							+ " Firefly Ranks: ");

					for (int j = 0; j < ranks.length; j++) {
						System.out.print(ranks[j] + " ");
					}
					System.out.println("");
				}
				double fitness = new FireFlyFitnessFunction().getFitness(
						aFireFlies[i], ranks, aFireFlies, verbose);

				aFireFlies[i].setLightIntensity(fitness); // objective function
															// intensity

			}

			// Rank Fireflies based on light intensity; the last is the best
			sort_ffa();

			// Select current best firefly
			bestFireFly = aFireFlies[aFireFlies.length - 1]; // the first
																// firefly
			// System.out.println("Best Firefly: " + bestFireFly.toString());
			/*
			 * NOTE: OPTIMIZATION TASKS 1. Maximization problems: The last
			 * firefly after sorting is the current best firefly 2. Minimization
			 * problems: The first firefly after sorting is the current best
			 * firefly
			 */

			// display intermediate results
			if (verbose > 1) {
				displayResults(t + 1, bestFireFly);
			}
			// move all fireflies to the better locations
			move_ffa();

			// JS Customized Output
			if (verbose > 1) {
				System.out.print("#\t MS \t LightInt. \t\t ");
				for (int i = 0; i < dimension; i++) {

					System.out.print("\t" + i + ". Pos\t");
				}
				System.out.println("");
				// System.out.println("\t\t ROV");
				for (int i = 0; i < aFireFlies.length; i++) {
					System.out.print(i + "\t" + aFireFlies[i].getCurrMakespan()
							+ ". \t " + aFireFlies[i].toString(dimension));
					System.out.println("");
				}
				for (int i = 0; i < aFireFlies.length; i++) {
					// System.out.println(aFireFlies[i].getCurrMakespan());
				}
			}

			t++; // move to nextGeneration

		}
		if (verbose == 1) {
			System.out.print(bestFireFly.getCurrMakespan());
		}
	}

	// JS: Get Array Ranks (ROV)
	public static int[] getRanksArray(double[] newArray) {
		int[] result = new int[newArray.length];
		// System.out.print("Ranks/Permutation:");
		for (int i = 0; i < newArray.length; i++) {
			int count = 0;
			for (int j = 0; j < newArray.length; j++) {
				if (newArray[j] > newArray[i]) {
					count++;
				}
			}
			result[i] = count;
			// System.out.print(newArray[i] + " <---" + result[i] + " |||| ");
		}
		return result;
	}

	// Move fireflies to new locations
	private void move_ffa() {
		int i, j, k;
		double scale;
		double r, beta;

		// create a copy of the original firefly array
		FireFly[] copyFireFlies = new FireFly[aFireFlies.length];
		System.arraycopy(aFireFlies, 0, copyFireFlies, 0, aFireFlies.length); 


		for (i = 0; i < n; i++) {

			for (j = 0; j < n; j++) {

				r = 0.0;

				for (k = 0; k < dimension; k++) {
					// Euclidean distance computation
					r += (aFireFlies[i].getValue(k) - aFireFlies[j].getValue(k))
							* (aFireFlies[i].getValue(k) - aFireFlies[j].getValue(k));
				}// end for k

				// Square root to determine the euclidean distance
				r = Math.sqrt(r); 

				// update firefly movement
				if (aFireFlies[j].getLightIntensity() > copyFireFlies[i]
						.getLightIntensity()) { // Brighter and more attractive
					double beta0 = 1;
					beta = (beta0 - betamin)
							* Math.exp((-gamma) * Math.pow(r, 2.0)) + betamin;

					for (k = 0; k < dimension; k++) {
						// System.out.println(i + ". BEFORE: " +  aFireFlies[i].getValue(k));

						scale = Math.abs(ub[k] - lb[k]);
						double rNum = rand.nextDouble();
						double tmpf = alpha * (rNum - 0.5) * scale;
						double value = formatNumber(aFireFlies[i].getValue(k)
								* (1.0 - beta) + copyFireFlies[j].getValue(k)
								* beta + tmpf);
						aFireFlies[i].setValue(k, value); // update value
						// System.out.println("k value:" + aFireFlies[i].getValue(k));
						// System.out.println(i + ". AFTER: " + aFireFlies[i].getValue(k));

					}// end for k

				}// end if

			} // end for j

			findLimits(i);
		} // end for i

	} // end move_ffa

}
