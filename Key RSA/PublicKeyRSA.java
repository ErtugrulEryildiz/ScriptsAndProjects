/**
 * Name: Ertugrul Eryildiz
 * ID Number: 112495660
 */

import java.math.BigInteger;

public class PublicKeyRSA extends KeyRSA {
    private BigInteger e;

    PublicKeyRSA(BigInteger InitE, BigInteger InitN) {
        super(InitN);
        e = InitE;
    }

    public BigInteger encrypt(String plainText) {
        BigInteger numericPlainText = new BigInteger(plainText.getBytes());

        // Size of the plain text must be less than N, which is p * q.
        int compareSizeOfText = getN().compareTo(numericPlainText);
        if (compareSizeOfText == 1) {
            return numericPlainText.modPow(e, getN());
        } else {
            throw new IllegalArgumentException("Plain text is too large. Either enlarge the key size when generating key pairs or use smaller text.");
        }
    }

    public String toString()
    {
        return "KU: {" + e + ", " + getN() + "}";
    }
}