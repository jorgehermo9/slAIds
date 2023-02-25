package es.hackUDC.slAIds.model.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

@Entity
public class ModelUser {

    public enum RoleType {
        USER
    };

    private Long id;
    private String userName;
    private String password;
    private String email;
    private RoleType role;

    // @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    // @JoinColumn(name = "user_id")
    // private List<Presentation> presentations;

    // public void setPresentations(List<Presentation> presentations) {
    // this.presentations = presentations;
    // }

    public ModelUser() {
    }

    public ModelUser(String userName, String password, String email) {

        this.userName = userName;
        this.password = password;
        this.email = email;

    }

    // public List<Presentation> getPresentations() {
    // return presentations;
    // }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RoleType getRole() {
        return role;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }

}
