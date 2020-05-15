package unswstudyclub.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Admin {

    private final UUID id;
    private String email;
    private String password;

    public Admin(@JsonProperty("id") UUID id,
                 @JsonProperty("email") String email,
                 @JsonProperty("password") String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public UUID getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
