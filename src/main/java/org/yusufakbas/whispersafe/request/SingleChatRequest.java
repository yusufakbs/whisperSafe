package org.yusufakbas.whispersafe.request;

public class SingleChatRequest {

    private Long userId;

    public SingleChatRequest() {

    }

    public SingleChatRequest(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
