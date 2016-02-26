package com.mandiriecash.ecashtoll.services.responses;

import com.mandiriecash.ecashtoll.services.models.Toll;

import java.util.List;

public class GetTollsResponse {
    private String status;
    private String message;
    private List<Toll> tolls;

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

    public List<Toll> getTolls() {
        return tolls;
    }

    public void setTolls(List<Toll> tolls) {
        this.tolls = tolls;
    }
}
