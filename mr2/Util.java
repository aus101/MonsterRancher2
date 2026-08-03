package mr2;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Util {
	private static int COUNT = 5_000_000;
	public static final int STAGES = 4;
	
	public final static int NANOSECONDS_IN_1_SECOND = 1_000_000_000;
	public static BigDecimal COUNT_BD = new BigDecimal(COUNT);
	public static final BigDecimal ONE_HUNDRED_BD = new BigDecimal("100");
	public static final String newLine = System.lineSeparator();
	public static final String doubleNewLine = System.lineSeparator() + System.lineSeparator();
	
	public static void printCompare(int successes, String ratePercent) {
		BigDecimal ratio = new BigDecimal(successes).divide(COUNT_BD, 8, RoundingMode.HALF_UP);
		String answer = ratio.multiply(ONE_HUNDRED_BD).toString();
		int index = answer.indexOf(".");
		answer = answer.substring(0, index+3);
		System.out.println("Observed Learn Rate of " + answer + "%, Compared to Stage Learn Rate of " + ratePercent);	
	}
	
	public static int printEachRow(int[] attackRecord) {
		int successes = 0;
		for (int i = 0; i < attackRecord.length; i++) {
			successes += attackRecord[i];
			BigDecimal ratio = new BigDecimal(attackRecord[i]).divide(COUNT_BD, 8, RoundingMode.HALF_UP);
			String answer = ratio.multiply(Util.ONE_HUNDRED_BD).toString();
			if (answer.startsWith("0E")) {
				answer = "0";
			} else {
				int index = answer.indexOf(".");
				answer = answer.substring(0, index + 3);
			}
			System.out.println("Stage " + (i + 1) + ": " + attackRecord[i] + " = " + answer + "%");
		}
		return successes;
	}
	
	public static int getCount() {
		return COUNT;
	}
	
	public static void setCount(int count) {
		if (count > 0) {
			COUNT = count;
			COUNT_BD = new BigDecimal(COUNT);
		} else {
			System.err.println("Count must be greater than 0. Unchanged.");
		}
	}
	
	/**
	 * @param nanoTime the time as a long in nanoseconds, such as the difference with 2 System.nanoTime() calls
	 * @return String in human readable minutes and seconds with < 1 second as "less than 1 second"
	 */
	public static String nanoToMinutesSeconds(long nanoTime) {
		long seconds = nanoTime / NANOSECONDS_IN_1_SECOND;
		StringBuilder sb = new StringBuilder();
		if (seconds < 60) {
			if (seconds < 1) {
				sb.append("less than 1 second");
			} else if (seconds > 1) {
				sb.append(seconds + " seconds");
			} else {//== 1
				sb.append("1 second");
			} 
		} else {
			long minutes = seconds / 60;
			long remainder = seconds % 60;
			if (minutes > 1) {
				sb.append(minutes).append(" minutes");
			} else {
				sb.append(minutes).append(" minute");
			}
			if (remainder > 0) {
				if (remainder > 1) {
					sb.append(", ").append(remainder).append(" seconds");
				} else {//== 1
					sb.append(", 1 second");
				}
			}
		}
		return sb.toString();
	}
	/*
	 * 43064913176100 717 minutes, 44 seconds
	 * 43141650708600 719 minutes, 1 second
	 * 42960766596100 716 minutes
	 */
}
