package com.application.base.utils.common;

import java.math.BigDecimal;

/**
 * @desc 内部收益率的问题
 *
 * @author 孤狼
 */
public class IrrUtils {

	public static void main(String[] args) throws Exception {
		System.out.println(countInsideRate(new BigDecimal("12"), new BigDecimal("1"), new BigDecimal("3")));
	}

	/**
	 * 计算内部收益率
	 * 
	 * @param headerRate:商户砍头息.
	 * @param userRate:用户利率.
	 * @param partCount:分期期数.
	 * @return
	 */
	public static BigDecimal countInsideRate(BigDecimal headerRate, BigDecimal userRate, BigDecimal partCount)
			throws Exception {
		// 砍头息计算值.
		BigDecimal shopHeader = new BigDecimal(10000).multiply(headerRate.divide(new BigDecimal(100), 4, BigDecimal.ROUND_HALF_UP).subtract(new BigDecimal(1)));
		// 客户利率计算值.
		double customerRate = (((new BigDecimal(10000).multiply(userRate).divide(new BigDecimal(100), 4,BigDecimal.ROUND_HALF_UP))).divide(new BigDecimal(12), 4, BigDecimal.ROUND_HALF_UP)).add(new BigDecimal(10000).divide(partCount, 4, BigDecimal.ROUND_HALF_UP)).doubleValue();
		double[] array = new double[partCount.intValue() + 1];
		array[0] = shopHeader.doubleValue();
		for (int i = 1; i < partCount.intValue() + 1; i++) {
			array[i] = customerRate;
		}
		double result = countIRR(array) * 12 * 100;
		BigDecimal tmpDecimal = new BigDecimal(result);
		return tmpDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public static double countIRR(double[] income) {
		return getIRR(income, 0.1D);
	}

	public static double getIRR(final double[] cashFlows, final double estimatedResult) {
		double result = Double.NaN;
		if (cashFlows != null && cashFlows.length > 0) {
			// check if business startup costs is not zero:
			if (cashFlows[0] != 0.0) {
				final double noOfCashFlows = cashFlows.length;
				double sumCashFlows = 0.0;
				// check if at least 1 positive and 1 negative cash flow exists:
				int noOfNegativeCashFlows = 0;
				int noOfPositiveCashFlows = 0;
				for (int i = 0; i < noOfCashFlows; i++) {
					sumCashFlows += cashFlows[i];
					if (cashFlows[i] > 0) {
						noOfPositiveCashFlows++;
					}
					else if (cashFlows[i] < 0) {
						noOfNegativeCashFlows++;
					}
				}
				// at least 1 negative and 1 positive cash flow available?
				if (noOfNegativeCashFlows > 0 && noOfPositiveCashFlows > 0) {
					// set estimated result:
					// default: 10%
					double irrGuess = 0.1;
					if (!Double.isNaN(estimatedResult)) {
						irrGuess = estimatedResult;
						if (irrGuess <= 0.0) {
							irrGuess = 0.5;
						}
					}
					// initialize first IRR with estimated result:
					double irr = 0.0;
					// sum of cash flows negative?
					if (sumCashFlows < 0) {
						irr = -irrGuess;
					}
					else { // sum of cash flows not negative
						irr = irrGuess;
					}
					// iteration:
					// the smaller the distance, the smaller the interpolation
					// error
					final double minDistance = 1E-15;
					// business startup costs
					final double cashFlowStart = cashFlows[0];
					final int maxIteration = 100;
					boolean wasHi = false;
					double cashValue = 0.0;
					for (int i = 0; i <= maxIteration; i++) {
						// calculate cash value with current irr:
						// init with startup costs
						cashValue = cashFlowStart;
						// for each cash flow
						for (int j = 1; j < noOfCashFlows; j++) {
							cashValue += cashFlows[j] / Math.pow(1.0 + irr, j);
						}
						// cash value is nearly zero
						if (Math.abs(cashValue) < 0.01) {
							result = irr;
							break;
						}
						// adjust irr for next iteration:
						// cash value > 0 => next irr > current irr
						if (cashValue > 0.0) {
							if (wasHi) {
								irrGuess /= 2;
							}
							irr += irrGuess;
							if (wasHi) {
								irrGuess -= minDistance;
								wasHi = false;
							}
						}
						else {
							// cash value < 0 => next irr < current irr
							irrGuess /= 2;
							irr -= irrGuess;
							wasHi = true;
						}
						// estimated result too small to continue => end
						// calculation
						if (irrGuess <= minDistance) {
							result = irr;
							break;
						}
					}
				}
			}
		}
		return result;
	}

}
