/**
 * Name: Ertugrul Eryildiz
 * ID Number: 112495660
 */

import java.math.BigInteger;

public abstract class KeyRSA {
    private BigInteger n;

    KeyRSA (BigInteger InitN)
    {
        n = InitN;
    }

    public BigInteger getN()
    {
        return n;
    }
}
