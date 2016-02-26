package com.mandiriecash.ecashtoll.services.responses;

/**
 * Created by yafi on 27-Feb-16.
 */
public class CreatePlanResponse {
    String status;
    String message;
    int id;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
