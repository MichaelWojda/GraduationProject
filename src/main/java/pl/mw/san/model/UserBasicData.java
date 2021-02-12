package pl.mw.san.model;

import java.util.List;

public class UserBasicData {


    private Long id;
    private String userName;
    private String role;

    public UserBasicData(Long id, String userName, String role) {
        this.id = id;
        this.userName = userName;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
