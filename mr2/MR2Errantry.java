package mr2;

import java.security.SecureRandom;
import java.util.Random;

public class MR2Errantry {
	final boolean truncate = true;
	final boolean doNotTruncate = false;
	Tech phantomClaw = new Tech("Phantom Claw", false, "0.40", true);//40%
	Tech twisterClaw = new Tech("Twister Claw", false, "0.20", true);//20%
	int[] attack1Record = new int[Util.STAGES];
	int[] attack2Record = new int[Util.STAGES];

	public static void main(String[] args) {
		MR2Errantry e = new MR2Errantry();
		Random r = new SecureRandom();
		
		long start = System.nanoTime();
		
		for (int i = 0; i<Util.getCount(); i++) {
			e.phantomClaw.unlearn();
			e.twisterClaw.unlearn();
			
			for (int stage=1; stage <= Util.STAGES; stage++) {
				if (e.phantomClaw.tryOneStageRNGBias(r)) {
					e.attack1Record[stage - 1]++;
					break;
				} else if (e.twisterClaw.tryOneStageRNGBias(r)) {
					e.attack2Record[stage - 1]++;
					break;
				}
			}
		}
		long end = System.nanoTime();
		String iterations = String.format("%,d", Util.getCount()) + " iterations";
		System.out.println(Util.nanoToMinutesSeconds(end - start) + " to execute for " + iterations);
		System.out.print(Util.newLine);
		
		System.out.println(e.phantomClaw.name);
		int successes1 = Util.printEachRow(e.attack1Record);
		Util.printCompare(successes1, e.phantomClaw.getRatePercent());
		
		System.out.print(Util.newLine);

		System.out.println(e.twisterClaw.name);
		int successes2 = Util.printEachRow(e.attack2Record);
		Util.printCompare(successes2, e.twisterClaw.getRatePercent());
	}
}

/*
----

40-20 with Auto-Learn and RNG Bias

3 minutes, 8 seconds to execute for 50,000,000 iterations

Phantom Claw
Stage 1: 21052146 = 42.10%
Stage 2: 9404706 = 18.80%
Stage 3: 4205405 = 8.41%
Stage 4: 1879440 = 3.75%
Observed Learn Rate of 73.08%, Compared to Stage Learn Rate of 40.00%

Twister Claw
Stage 1: 6602575 = 13.20%
Stage 2: 2952068 = 5.90%
Stage 3: 1318097 = 2.63%
Stage 4: 589978 = 1.17%
Observed Learn Rate of 22.92%, Compared to Stage Learn Rate of 20.00%

----

40-20 with Auto-Learn

6 minutes, 8 seconds to execute for 50,000,000 iterations

Phantom Claw
Stage 1: 20902289 = 41.80%
Stage 2: 9438313 = 18.87%
Stage 3: 4265201 = 8.53%
Stage 4: 1924484 = 3.84%
Observed Learn Rate of 73.06%, Compared to Stage Learn Rate of 40.00%

Twister Claw
Stage 1: 6517076 = 13.03%
Stage 2: 2941653 = 5.88%
Stage 3: 1329351 = 2.65%
Stage 4: 600233 = 1.20%
Observed Learn Rate of 22.77%, Compared to Stage Learn Rate of 20.00%

----

4 minutes, 12 seconds to execute for 25,000,000 iterations
30-15 with Auto-Learn

Phantom Claw
Stage 1: 8021796 = 32.08%
Stage 2: 4491600 = 17.96%
Stage 3: 2517779 = 10.07%
Stage 4: 1408356 = 5.63%
Observed Learn Rate of 65.75%, Compared to Stage Learn Rate of 30.00%

Twister Claw
Stage 1: 2976789 = 11.90%
Stage 2: 1668395 = 6.67%
Stage 3: 934915 = 3.73%
Stage 4: 523302 = 2.09%
Observed Learn Rate of 24.41%, Compared to Stage Learn Rate of 15.00%

----

250,00,000 iterations
45-25 with no Auto-Learn

Phantom Claw
Stage 1: 11249422 = 44.9976%
Stage 2: 4642389 = 18.5695%
Stage 3: 1912657 = 7.6506%
Stage 4: 790973 = 3.1638%

Observed Learn Rate of 74.38%, Compared to Stage Learn Rate of 45.00%

Twister Claw
Stage 1: 3435531 = 13.7421%
Stage 2: 1418398 = 5.6735%
Stage 3: 584933 = 2.3397%
Stage 4: 241136 = 0.9645%

Observed Learn Rate of 22.71%, Compared to Stage Learn Rate of 25.00%

----

100,000,000 iterations
45-25 with 5% Auto-Learn

Phantom Claw
Stage 1: 47753190 = 47.7531%
Stage 2: 17775100 = 17.7751%
Stage 3: 6617622 = 6.6176%
Stage 4: 2460937 = 2.4609%

Observed Learn Rate of 74.60%, Compared to Stage Learn Rate of 45.00%

Twister Claw
Stage 1: 15024222 = 15.0242%
Stage 2: 5593797 = 5.5937%
Stage 3: 2080646 = 2.0806%
Stage 4: 774310 = 0.7743%

Observed Learn Rate of 23.47%, Compared to Stage Learn Rate of 25.00%

----

50,000,000 iterations
 45-25 no Auto-Learn

Phantom Claw
Stage 1: 22501457 = 45.0029%
Stage 2: 9280964 = 18.5619%
Stage 3: 3826378 = 7.6527%
Stage 4: 1579378 = 3.1587%

Observed Learn Rate of 74.37%, Compared to Stage Learn Rate of 45.00%

Twister Claw
Stage 1: 6876955 = 13.7539%
Stage 2: 2832771 = 5.6655%
Stage 3: 1169657 = 2.3393%
Stage 4: 482810 = 0.9656%

Observed Learn Rate of 22.72%, Compared to Stage Learn Rate of 25.00%

----

200,000,000 iterations
45-25 with Auto-Learn

Phantom Claw
Stage 1: 93304385 = 46.6521%
Stage 2: 36211782 = 18.1058%
Stage 3: 14053667 = 7.0268%
Stage 4: 5455229 = 2.7276%

Observed Learn Rate of 74.51%, Compared to Stage Learn Rate of 45.00%

Twister Claw
Stage 1: 29072626 = 14.5363%
Stage 2: 11286949 = 5.6434%
Stage 3: 4378219 = 2.1891%
Stage 4: 1699847 = 0.8499%

Observed Learn Rate of 23.21%, Compared to Stage Learn Rate of 25.00%

----
*/
