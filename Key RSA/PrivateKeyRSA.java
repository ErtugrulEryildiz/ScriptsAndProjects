/**
 * Name: Ertugrul Eryildiz
 * ID Number: 112495660
 */

import java.math.BigInteger;

public class PrivateKeyRSA extends KeyRSA {
    private BigInteger d;

    PrivateKeyRSA(BigInteger InitD, BigInteger InitN) {
        super(InitN);
        d = InitD;
    }

    public String decrypt(BigInteger cipherText) {
        BigInteger plainText = cipherText.modPow(d, getN());
        return new String(plainText.toByteArray());
    }

    public String toString()
    {
        return "KR: {" + d + ", " + getN() + "}";
    }
}
