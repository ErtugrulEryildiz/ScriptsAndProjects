/**
 * Name: Ertugrul Eryildiz
 * ID Number: 112495660
 */

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

public class KeyPairGeneratorRSA extends KeyPairRSA {
    // KeySizeInBits variable Will determine the bit length of randomly generated number.
    private int keySizeInBits;

    KeyPairGeneratorRSA (int keySize) { this.keySizeInBits = keySize; }


    public KeyPairRSA generateKeyPair() {
        Random random = new SecureRandom();
        BigInteger p =  BigInteger.probablePrime(keySizeInBits, random);
        BigInteger q =  BigInteger.probablePrime(keySizeInBits, random);

        BigInteger n = p.multiply(q);
        BigInteger ONE = new BigInteger("1");
        BigInteger phi = (p.subtract(ONE).multiply(q.subtract(ONE)));

        // For our 'e' value, we just pick another random number for it, rather than finding
        // greatest common divisor with phi.
        BigInteger e = BigInteger.probablePrime(keySizeInBits, random);
        BigInteger d = e.modInverse(phi);

        return new KeyPairRSA(d, e, n);
    }
}
