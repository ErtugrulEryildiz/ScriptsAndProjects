/**
 * Name: Ertugrul Eryildiz
 * ID Number: 112495660
 */

import java.math.BigInteger;
import java.util.Scanner;

public class RSADriver {
    public static void main(String[] args) {
        // Default key size set to be 64, meaning longest plain text can be 16 character long.
        KeyPairGeneratorRSA generator = new KeyPairGeneratorRSA(64);
        KeyPairRSA keys = generator.generateKeyPair();

        // Show generated key pairs.
        System.out.println(keys.toString());

        String plain, cipher, decrypted;

        do {
            // Request plaintext message from user.
            System.out.print("\nEnter a Plaintext Message: ");
            plain = new Scanner(System.in).nextLine();

            // Encrypt plaintext message into cipher text number and then text
            try {
                BigInteger cipherBigInt = keys.getPublicKey().encrypt(plain);
                cipher = new String(cipherBigInt.toByteArray());

                // DECRYPT ciphertext NUMBER BACK INTO plaintext MESSAGE
                decrypted = keys.getPrivateKey().decrypt(cipherBigInt);

                // PRINT SUMMARY INFORMATION
                System.out.println("plain:     " + plain);
                System.out.println("cipher:    " + cipher);
                System.out.println("decrypted: " + decrypted);
            } catch (IllegalArgumentException iae) {
                System.out.println(iae.getMessage());
            }
        }while (! plain.toLowerCase().equals("quit") );
    }
}
