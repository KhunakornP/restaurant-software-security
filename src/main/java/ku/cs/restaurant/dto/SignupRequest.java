package ku.cs.restaurant.dto;


import jakarta.validation.constraints.Size;

public class SignupRequest {
    @Size(min = 4, message = "Username must contain at least 4 characters")
    private String username;
    @Size(min = 8, message = "Password must contain at least 8 characters")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
}

