package utils;

import java.math.BigInteger;

public final class ProjectEulerLibrary {

    /**
     * Returns the reverse of the given string.
     */
    public static String reverse(String s) {
        return new StringBuilder(s).reverse().toString();
    }


    /**
     * Tests whether the given string is a palindrome.
     *
     * @param s String that shall be tested
     * @return Returns true if the tested String is a Palindrome
     */
    public static boolean isPalindrome(String s) {
        return s.equals(reverse(s));
    }


    /**
     * Tests whether the given integer is a palindrome.
     *
     * @param x integer that shall be tested
     * @return Returns true if the tested String is a Palindrome
     */
    public static boolean isPalindrome(int x) {
        return isPalindrome(Integer.toString(x));
    }

    /**
     * Checks if the passed digit is pandigitl
     *
     * @param s Digit to test
     * @return returns true if the digit is pandigital else false
     */
    public static boolean isPandigital(String s) {
        /*http://de.wikipedia.org/wiki/Pandigitale_Zahl*/
        return (getCrossfoot(s) == 45) && (Integer.parseInt(s) % 9 == 0);

    }

    /**
     * Get's the crossfoot of a given String back
     *
     * @param s String to calculate the croosfoot
     * @return Returns the crossfoot as integer
     */
    public static int getCrossfoot(String s) {
        int digit = new Integer(s);
        int crossfoot = 0;

        while (digit > 0) {
            crossfoot += digit % 10;
            digit /= 10;
        }

        return crossfoot;
    }

    /**
     * Returns floor(sqrt(x)) => See http://www.codecodex.com/wiki/Calculate_an_integer_square_root
     * for more details
     *
     * @param x The given Integer to extract the root
     * @return Returns the square root as integer
     */
    public static int sqrt(int x) {
        if (x < 0)
            throw new IllegalArgumentException("Square root of negative number");

        int y = 0;

        for (int i = 32768; i != 0; i >>>= 1) {
            y |= i;
            if (y > 46340 || y * y > x)
                y ^= i;
        }
        return y;
    }


    /**
     * Returns floor(sqrt(x)) => See http://www.codecodex.com/wiki/Calculate_an_integer_square_root
     * for more details
     *
     * @param x The given long to extract the root
     * @return Returns the square root as long
     */
    public static long sqrt(long x) {
        if (x < 0)
            throw new IllegalArgumentException("Square root of negative number");

        long y = 0;

        for (long i = 1L << 31; i != 0; i >>>= 1) {
            y |= i;
            if (y > 3037000499L || y * y > x)
                y ^= i;
        }

        return y;
    }


    /**
     * Tests whether x is a perfect square
     * <p>
     * A number made by squaring a whole number.
     * 16 is a perfect square because 4^2 = 16
     */
    public static boolean isSquare(int x) {
        if (x < 0)
            return false;

        int sqrt = ProjectEulerLibrary.sqrt(x);

        return (sqrt * sqrt == x);
    }


    /**
     * Returns x to the power of y.
     *
     * @param x the base
     * @param y the exponent
     */
    public static int pow(int x, int y) {
        if (y < 0)
            throw new IllegalArgumentException("Negative exponent");

        int z = 1;

        for (int i = 0; i < y; i++) {
            if (Integer.MAX_VALUE / z < x)
                throw new ArithmeticException("Overflow");
            z *= x;
        }

        return z;
    }


    /**
     * Returns x^y mod m.
     *
     * @param x the base
     * @param y the exponent
     * @param m the digit for the modulo operation
     */
    public static int powMod(int x, int y, int m) {
        if (x < 0)
            throw new IllegalArgumentException("Negative base not handled");
        if (y < 0)
            throw new IllegalArgumentException("Reciprocal not handled");
        if (m <= 0)
            throw new IllegalArgumentException("Invalid modulus");
        if (m == 1)
            return 0;

        // Exponentiation by squaring
        int z = 1;

        while (y != 0) {
            if ((y & 1) != 0)
                z = (int) ((long) z * x % m);

            x = (int) ((long) x * x % m);
            y >>>= 1;
        }

        return z;
    }


    /**
     * Returns x^-1 mod m. Note that x * x^-1 mod m = x^-1 * x mod m = 1.
     */
    public static int reciprocalMod(int x, int m) {
        if (m < 0 || x < 0 || x >= m)
            throw new IllegalArgumentException();

        // Based on a simplification of the extended Euclidean algorithm
        int y = x;
        int a = 0;
        int b = 1;
        x = m;

        while (y != 0) {
            int z = x % y;
            int c = a - x / y * b;

            x = y;
            y = z;
            a = b;
            b = c;
        }

        if (x == 1)
            return (a + m) % m;
        else
            throw new IllegalArgumentException("Reciprocal does not exist");
    }


    /**
     * Returns n!
     *
     * @param n Digit to factorize. n needs to be >= 0
     */
    public static BigInteger factorial(int n) {
        if (n < 0)
            throw new IllegalArgumentException("Factorial of negative number");

        BigInteger prod = BigInteger.ONE;

        //Expensive but well who cares?
        for (int i = 2; i <= n; i++)
            prod = prod.multiply(BigInteger.valueOf(i));

        return prod;
    }


    /**
     * Returns n choose k.
     * <p>
     * See http://matheguru.com/stochastik/131-binomialkoeffizient.html
     * for more details
     */
    public static BigInteger binomial(int n, int k) {
        return factorial(n).divide(factorial(n - k).multiply(factorial(k)));
    }


    /**
     * Returns the largest non-negative integer that divides both, x and y
     *
     * @param x
     * @param y
     */
    public static int gcd(int x, int y) {
        while (y != 0) {
            int z = x % y;
            x = y;
            y = z;
        }

        if (x >= 0)
            return x;

        throw new ArithmeticException("Something got fucked up and a non-negative integer shall be returned ...");
    }


    /**
     * Tests whether the given integer is prime.
     *
     * @param x The digit that should be tested
     */
    public static boolean isPrime(int x) {
        if (x < 0)
            throw new IllegalArgumentException("Negative number");

        if (x == 0 || x == 1)
            return false;
        else if (x == 2)
            return true;
        else {
            if (x % 2 == 0)
                return false;
            for (int i = 3, end = sqrt(x); i <= end; i += 2) {
                if (x % i == 0)
                    return false;
            }

            return true;
        }
    }

    /**
     * Returns a Boolean array 'isPrime' where isPrime[i] indicates whether i is prime, for 0 <= i <= n.
     * For a large batch of queries, this is faster than calling isPrime() for each integer.
     * For example: listPrimality(100) = {false, false, true, true, false, true, false, true, false, false, ...}.
     */
    public static boolean[] listPrimality(int n) {
        if (n < 0)
            throw new IllegalArgumentException("Negative size");

        boolean[] prime = new boolean[n + 1];

        if (n >= 2)
            prime[2] = true;

        for (int i = 3; i <= n; i += 2)
            prime[i] = true;

        // Sieve of Eratosthenes
        for (int i = 3, end = sqrt(n); i <= end; i += 2)
            if (prime[i])
                for (int j = i * i; j <= n; j += i << 1)
                    prime[j] = false;

        return prime;
    }


    /**
     * Returns all the prime numbers less than or equal to n, in ascending order.
     * For example: listPrimes(100) = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, ..., 83, 89, 97}.
     */
    public static int[] listPrimes(int n) {
        if (n < 0)
            throw new IllegalArgumentException("Negative size");

        boolean[] isPrime = listPrimality(n);

        int count = 0;

        for (boolean b : isPrime)
            if (b)
                count++;

        int[] primes = new int[count];

        for (int i = 0, j = 0; i < isPrime.length; i++) {
            if (isPrime[i]) {
                primes[j] = i;
                j++;
            }
        }

        return primes;
    }


    /**
     * Returns an array spf where spf[k] is the smallest prime factor of k, valid for 0 <= k <= n.
     * For example: listSmallestPrimeFactors(10) = {0, 0, 2, 3, 2, 5, 2, 7, 2, 3, 2}.
     */
    public static int[] listSmallestPrimeFactors(int n) {
        int[] result = new int[n + 1];
        for (int i = 2; i < result.length; i++) {
            if (result[i] == 0) {
                result[i] = i;
                if ((long) i * i <= n) {
                    for (int j = i * i; j <= n; j += i) {
                        if (result[j] == 0)
                            result[j] = i;
                    }
                }
            }
        }
        return result;
    }


    /**
     * Returns the number of integers in the range [1, n] that are coprime with n.
     * For example, totient(12) = 4 because these integers are coprime with 12: 1, 5, 7, 11.
     *
     * https://en.wikipedia.org/wiki/Euler's_totient_function
     */
    public static int totient(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("Totient of non-positive integer");

        int p = 1;

        for (int i = 2, end = ProjectEulerLibrary.sqrt(n); i <= end; i++) {
            // Trial division
            if (n % i == 0) {
                // Found a factor
                p *= i - 1;
                n /= i;

                while (n % i == 0) {
                    p *= i;
                    n /= i;
                }

                end = ProjectEulerLibrary.sqrt(n);
            }
        }

        if (n != 1)
            p *= n - 1;

        return p;
    }


    /**
     * Returns an array 'totients' where totients[i] == totient(i), for 0 <= i <= n.
     * For a large batch of queries, this is faster than calling totient() for each integer.
     */
    public static int[] listTotients(int n) {
        if (n < 0)
            throw new IllegalArgumentException("Negative size");

        int[] totients = new int[n + 1];

        for (int i = 0; i <= n; i++)
            totients[i] = i;

        for (int i = 2; i <= n; i++) {
            if (totients[i] == i) {
                // i is prime
                for (int j = i; j <= n; j += i)
                    totients[j] = totients[j] / i * (i - 1);
            }
        }
        return totients;
    }


    /**
     * Returns the same result as x.multiply(y), but is faster for large integers.
     */
    public static BigInteger multiply(BigInteger x, BigInteger y) {
        final int CUTOFF = 1536;
        if (x.bitLength() <= CUTOFF || y.bitLength() <= CUTOFF) {  // Base case
            return x.multiply(y);

        } else {
            // Karatsuba fast multiplication https://de.wikipedia.org/wiki/Karazuba-Algorithmus
            int n = Math.max(x.bitLength(), y.bitLength());
            int half = (n + 32) / 64 * 32;

            BigInteger mask = BigInteger.ONE.shiftLeft(half).subtract(BigInteger.ONE);
            BigInteger xlow = x.and(mask);
            BigInteger ylow = y.and(mask);
            BigInteger xhigh = x.shiftRight(half);
            BigInteger yhigh = y.shiftRight(half);

            BigInteger a = multiply(xhigh, yhigh);
            BigInteger b = multiply(xlow.add(xhigh), ylow.add(yhigh));
            BigInteger c = multiply(xlow, ylow);
            BigInteger d = b.subtract(a).subtract(c);

            return a.shiftLeft(half).add(d).shiftLeft(half).add(c);
        }
    }


    /**
     * Advances the given sequence to the next permutation and returns whether a permutation was performed.
     * If no permutation was performed, then the input state was already the last possible permutation (a non-ascending sequence).
     * <p>
     * For example:
     * - nextPermutation({0,0,1}) changes the argument array to {0,1,0} and returns true.
     * - nextPermutation({1,0,0}) leaves the argument array unchanged and returns false.
     */
    public static boolean nextPermutation(int[] a) {
        int i, n = a.length;

        for (i = n - 2; ; i--) {
            if (i < 0)
                return false;

            if (a[i] < a[i + 1])
                break;
        }

        for (int j = 1; i + j < n - j; j++) {
            int tp = a[i + j];
            a[i + j] = a[n - j];
            a[n - j] = tp;
        }

        int j;

        for (j = i + 1; a[j] <= a[i]; j++)
            ;

        int tp = a[i];

        a[i] = a[j];
        a[j] = tp;

        return true;
    }
}

