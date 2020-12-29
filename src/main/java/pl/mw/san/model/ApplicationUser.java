package pl.mw.san.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class ApplicationUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String userName;

    @NotNull
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_and_roles",joinColumns = {@JoinColumn(name="user_id",referencedColumnName = "id")},
    inverseJoinColumns = {@JoinColumn(name="role_id",referencedColumnName = "id")})
    private Set<UserRole> userRoles = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "applicationUser")
    private List<Item> itemList = new ArrayList<>();

    public ApplicationUser() {
    }

    public ApplicationUser(String userName, @NotNull String password) {
        this.userName = userName;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    @Override
    public String toString() {
        return "ApplicationUser{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", userRoles=" + userRoles +
                ", itemList=" + itemList +
                '}';
    }
}
