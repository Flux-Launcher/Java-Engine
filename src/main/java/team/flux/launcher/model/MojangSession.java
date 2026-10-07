package team.flux.launcher.model;

public class MojangSession {

    private String sessionToken;
    private String Username;
    private String UUID;

    public MojangSession(String sessionToken, String Username, String UUID) {
        this.sessionToken = sessionToken;
        this.Username = Username;
        this.UUID = UUID;
    }

    public String getSessionToken() { return sessionToken; }
    public void setSessionToken(String sessionToken) { this.sessionToken = sessionToken; }

    public String getUsername() { return Username; }
    public void setUsername(String Username) { this.Username = Username; }

    public String getUUID() { return UUID; }
    public void setUUID(String UUID) { this.UUID = UUID; }
}
