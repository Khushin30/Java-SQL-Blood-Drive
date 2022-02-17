// authors: Khushin Patel, Zaid Iqbal, Aleexis Sahagun

import java.util.Scanner;

public class User {
    private String username;
    private String password;
    private String privilege;

    public User(String username, String password, String privilege) {
        setUsername(username);
        setPassword(password);
        setPrivilege(privilege);
    }

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
    public String getPrivilege() {
        return privilege;
    }
    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }
}
