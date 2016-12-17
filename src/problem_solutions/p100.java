package problem_solutions;

import java.math.BigInteger;

public final class p100 implements EulerSolution
{
	
	public static void main(String[] args) {
		System.out.println(new p100().solve());
	}
	
	
	/**
	 * Intention about the Solution:
	 * =============================
	 *
	 * Suppose the box has b blue discs and r red discs.
	 * The probability of taking 2 blue discs is [b / (b + r)] * [(b - 1) / (b + r - 1)],
	 * which we want to be equal to 1/2. Rearrange the equation:
	 *   [b(b - 1)] / [(b + r)(b + r - 1)] = 1 / 2.
	 *   2b(b - 1) = (b + r)(b + r - 1).
	 *   2b^2 - 2b = b^2 + br - b + br + r^2 - r.
	 *   b^2 - b = r^2 + 2br - r.
	 *   b^2 - (2r + 1)b + (r - r^2) = 0.
	 * Apply the quadratic equation to solve for b:
	 *   b = [(2r + 1) +/- sqrt((2r + 1)^2 - 4(r - r^2))] / 2
	 *     = r + [1 +/- sqrt(8r^2 + 1)]/2
	 *     = r + [sqrt(8r^2 + 1) + 1]/2.  (Discard the minus solution because it would make b < r)
	 * 
	 * For b to be an integer, we need sqrt(8r^2 + 1) to be odd, and also 8r^2 + 1 be a perfect square.
	 * Assume 8y^2 + 1 = x^2 for some integer x > 0.
	 * We can see this is in fact a Pell's equation: x^2 - 8y^2 = 1.
	 * 
	 * Suppose we have the solution (x0, y0) such that x0 > 0 and x0 is as small as possible.
	 * This is called the fundamental solution, and all other solutions be derived from it (proven elsewhere).
	 * Suppose (x0, y0) and (x1, y1) are solutions. Then we have:
	 *   x0^2 - 8*y0^2 = 1.
	 *   (x0 + y0*sqrt(8))(x0 - y0*sqrt(8)) = 1.
	 *   (x1 + y1*sqrt(8))(x1 - y1*sqrt(8)) = 1.  (Similarly)
	 * Multiply them together:
	 *   [(x0 + y0*sqrt(8))(x0 - y0*sqrt(8))][(x1 + y1*sqrt(8))(x1 - y1*sqrt(8))] = 1 * 1.
	 *   [(x0 + y0*sqrt(8))(x1 + y1*sqrt(8))][(x0 - y0*sqrt(8))(x1 - y1*sqrt(8))] = 1.
	 *   [x0*x1 + x0*y1*sqrt(8) + x1*y0*sqrt(8) + 8y0*y1][x0*x1 - x0*y1*sqrt(8) - x1*y0*sqrt(8) + 8y0*y1] = 1.
	 *   [(x0*x1 + 8y0*y1) + (x0*y1 + x1*y0)*sqrt(8)][(x0*x1 + 8y0*y1) - (x0*y1 + x1*y0)*sqrt(8)] = 1.
	 *   (x0*x1 + 8y0*y1)^2 - 8*(x0*y1 + x1*y0)^2 = 1.
	 * Therefore (x0*x1 + 8y0*y1, x0*y1 + x1*y0) is also a solution.
	 * By inspection, the fundamental solution is (3, 1).
	 */
	public String solve()
	{
		// Fundamental solution
		BigInteger x0 = BigInteger.valueOf(3);
		BigInteger y0 = BigInteger.valueOf(1);

		// Current solution
		BigInteger x = BigInteger.valueOf(3);
		BigInteger y = BigInteger.valueOf(1);  // An alias for the number of red discs

		while (true)
		{
			// Check if this solution is acceptable
			BigInteger sqrt = sqrt(y.multiply(y).multiply(BigInteger.valueOf(8)).add(BigInteger.ONE));

			// Check highest one-bit if it's odd
			if (sqrt.testBit(0))
			{
				BigInteger blue = sqrt.add(BigInteger.ONE).divide(BigInteger.valueOf(2)).add(y);

				if (blue.add(y).compareTo(BigInteger.TEN.pow(12)) > 0)
					return blue.toString();
			}

			// Create the next bigger solution
			BigInteger nextx = x.multiply(x0).add(y.multiply(y0).multiply(BigInteger.valueOf(8)));
			BigInteger nexty = x.multiply(y0).add(y.multiply(x0));

			x = nextx;
			y = nexty;
		}
	}

	/* This Method is to unimportant to be @ProjectEulerLibrary.java, don't move it! */
	private static BigInteger sqrt(BigInteger x)
	{
		BigInteger y = BigInteger.ZERO;

		for (int i = (x.bitLength() - 1) / 2; i >= 0; i--)
		{
			y = y.setBit(i);

			if (y.multiply(y).compareTo(x) > 0)
				y = y.clearBit(i);
		}
		return y;
	}
}
