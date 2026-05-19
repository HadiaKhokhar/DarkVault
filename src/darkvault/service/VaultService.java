package darkvault.service;

import darkvault.database.DatabaseManager;
import darkvault.model.Credential;
import darkvault.util.EncryptionService;
import java.sql.*;
import java.util.*;

public class VaultService {

    public boolean addCredential(int userId, String siteName,
                                  String username, String plainPassword, String notes) {
        try {
            String encrypted = EncryptionService.encrypt(plainPassword);
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO credentials (user_id, site_name, username, encrypted_password, notes) " +
                "VALUES (?, ?, ?, ?, ?)");
            ps.setInt(1, userId);
            ps.setString(2, siteName);
            ps.setString(3, username);
            ps.setString(4, encrypted);
            ps.setString(5, notes);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Add error: " + e.getMessage());
            return false;
        }
    }

    public List<Credential> getCredentials(int userId) {
        List<Credential> list = new ArrayList<>();
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM credentials WHERE user_id = ? ORDER BY site_name");
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Credential c = new Credential();
                c.setId(rs.getInt("id"));
                c.setUserId(rs.getInt("user_id"));
                c.setSiteName(rs.getString("site_name"));
                c.setUsername(rs.getString("username"));
                c.setEncryptedPassword(rs.getString("encrypted_password"));
                c.setNotes(rs.getString("notes"));
                list.add(c);
            }
        } catch (SQLException e) {
            System.err.println("Get error: " + e.getMessage());
        }
        return list;
    }

    public String decryptPassword(String encryptedPassword) {
        return EncryptionService.decrypt(encryptedPassword);
    }

    public boolean deleteCredential(int credentialId) {
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "DELETE FROM credentials WHERE id = ?");
            ps.setInt(1, credentialId);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Delete error: " + e.getMessage());
            return false;
        }
    }

    public List<Credential> searchCredentials(int userId, String query) {
        List<Credential> list = new ArrayList<>();
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM credentials WHERE user_id = ? " +
                "AND (site_name LIKE ? OR username LIKE ?)");
            ps.setInt(1, userId);
            ps.setString(2, "%" + query + "%");
            ps.setString(3, "%" + query + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Credential c = new Credential();
                c.setId(rs.getInt("id"));
                c.setUserId(rs.getInt("user_id"));
                c.setSiteName(rs.getString("site_name"));
                c.setUsername(rs.getString("username"));
                c.setEncryptedPassword(rs.getString("encrypted_password"));
                c.setNotes(rs.getString("notes"));
                list.add(c);
            }
        } catch (SQLException e) {
            System.err.println("Search error: " + e.getMessage());
        }
        return list;
    }
}