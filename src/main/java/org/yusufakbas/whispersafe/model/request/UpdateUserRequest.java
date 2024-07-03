package org.yusufakbas.whispersafe.model.request;

public class UpdateUserRequest {

    private String fullName;
    private String profileImage;

    public UpdateUserRequest() {
    }

    public UpdateUserRequest(String fullName, String profileImage) {
        this.fullName = fullName;
        this.profileImage = profileImage;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

}
