package FireFlyAlgorithm;

import java.io.Serializable;
import java.text.DecimalFormat;

/**
 *
 * @author erigha eseoghene dan
 * @author Joël Schmid, joel.schmid@students.fhnw.ch - enhanced output and
 *         restructuring
 */

public class FireFly implements Comparable<FireFly>, Serializable {
	protected double[] attributes;

	protected double aLightIntensity;
	public double currMakespan;

	public FireFly(double[] aAttributes) {
		attributes = aAttributes;
	}

	public FireFly(int dimension) {
		attributes = new double[dimension];
	}

	@Override
	public int compareTo(FireFly other) {
		if (aLightIntensity < other.aLightIntensity) {
			return -1;
		} else if (aLightIntensity > other.aLightIntensity) {
			return +1;
		}
		return 0;
	}

	public double getLightIntensity() {
		return aLightIntensity;
	}

	public void setLightIntensity(double value) {
		aLightIntensity = value;
	}

	public double getCurrMakespan() {
		return currMakespan;
	}

	public void setCurrMakespan(double value) {
		currMakespan = value;
	}

	public void setValue(int key, double value) {
		attributes[key] = value;
	}

	public double getValue(int pos) {
		return attributes[pos];
	}

	public int noAttributes() {
		return attributes.length;
	}

	public double[] getAttributes() {
		// double [] attribs = new double [attributes.length];
		// System.arraycopy(attributes,0,attribs,0,attributes.length);
		return attributes;
	}

	public String toString(int dimension) {
		String light = getLightIntensity() + " ";
		for (int i = 0; i < dimension; i++) {
			light += "\t" + getValue(i) + "\t";
		}

		return light;

	}
}
