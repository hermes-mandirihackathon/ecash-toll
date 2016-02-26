package com.mandiriecash.ecashtoll.services.responses;

import com.mandiriecash.ecashtoll.services.models.History;

import java.util.List;

/**
 * Created by yafi on 27-Feb-16.
 */
public class GetHistoryResponse {
    String status;
    String message;
    List<History> histories;

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

    public List<History> getHistories() {
        return histories;
    }

    public void setHistories(List<History> histories) {
        this.histories = histories;
    }
}
