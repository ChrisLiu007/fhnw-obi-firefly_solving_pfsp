package FireFlyAlgorithm;

import java.text.DecimalFormat;

import FireFlyAlgorithm.FireFly;
//import net.sf.javaml.core.Dataset;
//import weka.core.Instances;
import FlowShopProblem.FlowShop;
import FlowShopProblem.ProblemDefinitionArray;

/**
 *
 * @author erigha eseoghene dan
 * @author Joël Schmid, joel.schmid@students.fhnw.ch - enhanced output and restructuring
 */

public class FireFlyFitnessFunction {

	public static double makespan;

	public double getFitness(Object o, int[] ranks, FireFly[] aFireFlies, int verbose) {
		FireFly aFireFly = (FireFly) o;

		// JS: Define Problem in ProblemDefinitionArray Class
		int arrJobsTimes [][] = ProblemDefinitionArray.arrJobsTimes;
		
		int arrPermutation[] = ranks;

		// JS: Calculate Makespan with current Order
		FlowShop fs = new FlowShop(arrJobsTimes[0].length, arrJobsTimes);
		makespan = FlowShop.computeMakespan(fs, arrPermutation);
		
		/////////////////////////////////////////////////////
		// JS: Fitness Functions
		////////////////////////////////////////////////////
		double fitnessValue;
		// Variant 1: 
		fitnessValue =  aFireFly.getLightIntensity()-(makespan*100);
		
		// Variant 2:
		// fitnessValue =  aFireFly.getLightIntensity()/makespan;
		
		// Variant 3: Penalty Strategy
		//fitnessValue =  aFireFly.getLightIntensity()-(makespan*100);
		
		// Penalty Strategy Testing
		/*double currMakespanFF;
		double maxMakespanFF = aFireFly.getCurrMakespan();
		for(int i = 0; i < aFireFlies.length; i++){
			currMakespanFF = aFireFlies[i].getCurrMakespan();
			if(maxMakespanFF <= currMakespanFF){
				maxMakespanFF = currMakespanFF;
				//System.out.println("New max makespan!");
			}
		}
		if(verbose > 1){
		System.out.println("HIGHEST Makespan" + maxMakespanFF + " " + aFireFly.getCurrMakespan());
		}
		
		// Apply Penalty!
		if(maxMakespanFF == aFireFly.getCurrMakespan()){
			 fitnessValue -=  3000;
			if(verbose > 1){
				System.out.println("Penalty for this guy!!" + maxMakespanFF);
			}
		}*/
		
		aFireFly.setCurrMakespan(makespan);
		return fitnessValue;
	}
	
	// Format number
	private double formatNumber(double num) {
		DecimalFormat myFormat = new DecimalFormat("##.########");
		String outVar = myFormat.format(num);
		return Double.parseDouble(outVar);
	}

	/*
	 * @Override public double getFitness(Object o, Instances train, Instances
	 * test) { throw new UnsupportedOperationException("Not supported yet.");
	 * //To change body of generated methods, choose Tools | Templates. }
	 * 
	 * @Override public double getFitness(Object o, Dataset train, Dataset test)
	 * { throw new UnsupportedOperationException("Not supported yet."); //To
	 * change body of generated methods, choose Tools | Templates. }
	 */
}
