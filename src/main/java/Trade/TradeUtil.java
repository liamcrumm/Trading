package Trade;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class TradeUtil {

    public static String generateHash(String password) {
        try {
            byte[] salt = getSalt();
            PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray(), salt, 1000, 512);
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = secretKeyFactory.generateSecret(pbeKeySpec).getEncoded();
            return Base64.encode(salt) + " " + Base64.encode(hash);
        } catch (Exception e) {
            System.err.println("An error has occurred during password generation");
            e.printStackTrace();
            return "";
        }
    }

    public static boolean checkPass(String password, String hash) {
        try {
            String[] splitPass = hash.split(" ");
            byte[] salt = Base64.decode(splitPass[0]);
            byte[] dHash = Base64.decode(splitPass[1]);
            PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray(), salt, 1000, 512);
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] newHash = secretKeyFactory.generateSecret(pbeKeySpec).getEncoded();
            if (newHash.length != dHash.length) {
                return false;
            } else {
                for (int i = 0; i < newHash.length; i++) {
                    if (newHash[i] != dHash[i]) {
                        return false;
                    }
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    private static byte[] getSalt() {
        try {
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            byte[] salt = new byte[16];
            secureRandom.nextBytes(salt);
            return salt;
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Salt generation could not find specified algorithm");
            e.printStackTrace();
            return null;
        }
    }
}
