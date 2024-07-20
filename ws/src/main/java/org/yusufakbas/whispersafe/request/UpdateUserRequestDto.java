package org.yusufakbas.whispersafe.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UpdateUserRequestDto(String username, @JsonProperty("profile_image") String profileImage) {
    // You can add validation or custom methods here if needed
}