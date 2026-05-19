package darkvault.model;

public class Credential {
    private int id;
    private int userId;
    private String siteName;
    private String username;
    private String encryptedPassword;
    private String notes;

    public Credential() {}

    public Credential(int userId, String siteName, String username,
                      String encryptedPassword, String notes) {
        this.userId = userId;
        this.siteName = siteName;
        this.username = username;
        this.encryptedPassword = encryptedPassword;
        this.notes = notes;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getSiteName() { return siteName; }
    public void setSiteName(String siteName) { this.siteName = siteName; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEncryptedPassword() { return encryptedPassword; }
    public void setEncryptedPassword(String p) { this.encryptedPassword = p; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}