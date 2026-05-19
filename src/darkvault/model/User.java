package darkvault.model;

public class User {
    private int id;
    private String username;
    private String masterPasswordHash;

    public User() {}

    public User(int id, String username, String masterPasswordHash) {
        this.id = id;
        this.username = username;
        this.masterPasswordHash = masterPasswordHash;
    }

    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getMasterPasswordHash() { return masterPasswordHash; }

    public void setId(int id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setMasterPasswordHash(String hash) { this.masterPasswordHash = hash; }
}