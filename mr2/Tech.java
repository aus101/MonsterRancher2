package mr2;

import java.math.BigDecimal;
import java.util.Random;

public class Tech {
	final String name;
	final int STAGES;
	boolean hasLearned = false;
	BigDecimal oddsToLearn = new BigDecimal("0.00");//0% default but may have Auto-Learn
	boolean autoLearn = true;
	private double autoLearnOdds = 0.03;//3% Auto-Learn rate but not <code>final</code> in case a mod would change
	private double workingOdds = Double.parseDouble(oddsToLearn.toString());
	final private static BigDecimal ONE_HUNDRED = new BigDecimal("100");
	final static private String GENERIC = "Generic Attack";

	public Tech() {
		name = GENERIC;
		STAGES = Util.STAGES;
	}

	public Tech(double oddsToLearn) {
		this(GENERIC, Util.STAGES, false, oddsToLearn, true);
	}
	
	public Tech(String name, boolean hasLearned, BigDecimal oddsToLearn, boolean autoLearn) {
		this(name, Util.STAGES, hasLearned, oddsToLearn, autoLearn);
	}
	
	public Tech(String name, boolean hasLearned, String oddsToLearn, boolean autoLearn) {
		this(name, Util.STAGES, hasLearned, new BigDecimal(oddsToLearn), autoLearn);
	}

	public Tech(String name, boolean hasLearned, double oddsToLearn, boolean autoLearn) {
		this(name, Util.STAGES, hasLearned, oddsToLearn, autoLearn);
	}

	public Tech(String name, int STAGES, boolean hasLearned, double oddsToLearn, boolean autoLearn) {
		this(name, STAGES, hasLearned, new BigDecimal(oddsToLearn), autoLearn);
	}
	
	public Tech(String name, int STAGES, boolean hasLearned, BigDecimal oddsToLearn, boolean autoLearn) {
		if (name != null) {
			this.name = name;
		} else {
			this.name = GENERIC;
		}
		this.STAGES = STAGES;
		this.hasLearned = hasLearned;
		this.oddsToLearn = oddsToLearn;//immutable class
		workingOdds = Double.parseDouble(oddsToLearn.toString());
		this.autoLearn = autoLearn;
	}

	/**
	 * Try to learn the tech for one stage. Auto-Learn run if available after the stats roll.
	 * @param r The Random Number Generator, <code>SecureRandom</code> used in {@link MR2Errantry}
	 * @return <code>true</code> if learned, <code>false</code> if not
	 */
	public boolean tryOneStage(Random r) {
		if (!hasLearned) {
			tryToLearn(r);
		}
		if (!hasLearned) {
			tryToAutoLearn(r);
		}
		return hasLearned;
	}
	
	/**
	 * Small performance improvement by not checking for Auto-Learn possibility
	 * @param r The Random Number Generator, SecureRandom used in {@link MR2Errantry}
	 * @return <code>true</code> if learned, <code>false</code> if not
	 */
	public boolean tryOneStageNoAutoLearnCheck(Random r) {
		if (!hasLearned) {
			tryToLearn(r);
		}
		return hasLearned;
	}
	
	/**
	 * Try to learn the tech for one stage.<br>
	 * Not <code>public<code> because no check for having already learned and no Auto-Learn roll. Could be misused.
	 * @param r The Random Number Generator, SecureRandom used in {@link MR2Errantry}
	 * @return <code>true</code> if learned, <code>false</code> if not
	 */
	protected boolean tryToLearn(Random r) {
		if (workingOdds > r.nextDouble())
			hasLearned = true;
		return hasLearned;
	}
	
	/**
	 * Try to learn the tech for one stage.<br>
	 * Not <code>public<code> because no check for having already learned and no stats roll. Could be misused.
	 * @param r The Random Number Generator, SecureRandom used in {@link MR2Errantry}
	 * @return <code>true</code> if learned, <code>false</code> if not
	 */
	protected boolean tryToAutoLearn(Random r) {
		if (autoLearn) {
			if (autoLearnOdds > r.nextDouble()) {
				hasLearned = true;
			}
		}
		return hasLearned;
	}
	
	public void unlearn() {
		hasLearned = false;
	}
	
	public double getRateDecimal() {
		return workingOdds;
	}
	
	public String getRatePercent() {
		return oddsToLearn.multiply(ONE_HUNDRED).toString() + "%";
	}
}
