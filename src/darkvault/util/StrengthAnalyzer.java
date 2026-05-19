package darkvault.util;

public class StrengthAnalyzer {

    public enum Strength { WEAK, MEDIUM, STRONG }

    public static Strength analyze(String password) {
        if (password == null || password.isEmpty()) return Strength.WEAK;

        int score = 0;

        if (password.length() >= 8)  score++;
        if (password.length() >= 12) score++;

        if (password.matches(".*[A-Z].*")) score++;      // has uppercase
        if (password.matches(".*[a-z].*")) score++;      // has lowercase
        if (password.matches(".*[0-9].*")) score++;      // has digit
        if (password.matches(".*[!@#$%^&*()\\-_=+\\[\\]{}|;:,.<>?].*")) score++; // has symbol

        if (score <= 2) return Strength.WEAK;
        if (score <= 4) return Strength.MEDIUM;
        return Strength.STRONG;
    }

    public static String getFeedback(String password) {
        StringBuilder feedback = new StringBuilder();
        if (password.length() < 8)  feedback.append("• At least 8 characters\n");
        if (password.length() < 12) feedback.append("• 12+ characters recommended\n");
        if (!password.matches(".*[A-Z].*")) feedback.append("• Add uppercase letters\n");
        if (!password.matches(".*[0-9].*")) feedback.append("• Add numbers\n");
        if (!password.matches(".*[!@#$%^&*()\\-_=+].*")) feedback.append("• Add symbols\n");
        return feedback.length() == 0 ? "Strong password!" : feedback.toString().trim();
    }
}