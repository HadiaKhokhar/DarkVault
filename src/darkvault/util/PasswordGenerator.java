package darkvault.util;

import java.security.SecureRandom;

public class PasswordGenerator {

    // SecureRandom is cryptographically stronger than java.util.Random
    private static final SecureRandom random = new SecureRandom();

    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS    = "0123456789";
    private static final String SYMBOLS   = "!@#$%^&*()-_=+[]{}|;:,.<>?";

    public static String generate(int length, boolean useUpper,
                                   boolean useDigits, boolean useSymbols) {
        StringBuilder charset = new StringBuilder(LOWERCASE);
        if (useUpper)   charset.append(UPPERCASE);
        if (useDigits)  charset.append(DIGITS);
        if (useSymbols) charset.append(SYMBOLS);

        StringBuilder password = new StringBuilder();
        for (int i = 0; i < length; i++) {
            password.append(charset.charAt(random.nextInt(charset.length())));
        }
        return password.toString();
    }
}