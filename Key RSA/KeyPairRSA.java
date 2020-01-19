/**
 * Name: Ertugrul Eryildiz
 * ID Number: 112495660
 */

import java.math.BigInteger;

public class KeyPairRSA {
    private PublicKeyRSA publicKey;
    private PrivateKeyRSA privateKey;

    KeyPairRSA() {}

    KeyPairRSA(BigInteger d, BigInteger e, BigInteger n) {
        privateKey = new PrivateKeyRSA(d, n);
        publicKey = new PublicKeyRSA(e, n);
    }
    public PrivateKeyRSA getPrivateKey()
    {
        return privateKey;
    }

    public PublicKeyRSA getPublicKey()
    {
        return publicKey;
    }

    public String toString()
    {
        return publicKey.toString() + ", " + privateKey.toString();
    }
}
