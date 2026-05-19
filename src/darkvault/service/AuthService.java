package darkvault.service;

import darkvault.database.DatabaseManager;
import darkvault.model.User;
import darkvault.util.EncryptionService;
import java.sql.*;

public class AuthService {

    public User login(String username, String masterPassword) {
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM users WHERE username = ?");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String storedHash = rs.getString("master_password_hash");
                if (EncryptionService.verifyPassword(masterPassword, storedHash)) {
                    return new User(rs.getInt("id"), rs.getString("username"), storedHash);
                }
            }
        } catch (SQLException e) {
            System.err.println("Login error: " + e.getMessage());
        }
        return null;
    }

    public boolean register(String username, String masterPassword) {
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement check = conn.prepareStatement(
                "SELECT id FROM users WHERE username = ?");
            check.setString(1, username);
            if (check.executeQuery().next()) return false;

            String hashedPassword = EncryptionService.hashPassword(masterPassword);
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO users (username, master_password_hash) VALUES (?, ?)");
            ps.setString(1, username);
            ps.setString(2, hashedPassword);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Registration error: " + e.getMessage());
            return false;
        }
    }
}