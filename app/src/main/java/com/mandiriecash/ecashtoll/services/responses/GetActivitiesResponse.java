package com.mandiriecash.ecashtoll.services.responses;

import com.mandiriecash.ecashtoll.services.models.Activity;
import com.mandiriecash.ecashtoll.services.models.LogActivity;

import java.util.List;

public class GetActivitiesResponse {
    String status;
    String message;
    List<LogActivity> activities;

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

    public List<LogActivity> getActivities() {
        return activities;
    }

    public void setActivities(List<LogActivity> activities) {
        this.activities = activities;
    }


}
