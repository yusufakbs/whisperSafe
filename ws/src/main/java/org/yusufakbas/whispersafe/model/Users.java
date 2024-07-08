package org.yusufakbas.whispersafe.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fullName;
    private String email;
    private String profileImage;
    private String password;

    //TODO : Look at the jwt conversation, review the notes
    public Users() {
    }

    public Users(Long id, String fullName, String email, String profileImage, String password) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.profileImage = profileImage;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Users users = (Users) object;
        return Objects.equals(id, users.id) && Objects.equals(fullName, users.fullName) && Objects.equals(email, users.email) && Objects.equals(profileImage, users.profileImage) && Objects.equals(password, users.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName, email, profileImage, password);
    }
}