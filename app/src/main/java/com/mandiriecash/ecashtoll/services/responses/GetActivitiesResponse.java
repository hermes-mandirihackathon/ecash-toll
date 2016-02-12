package com.mandiriecash.ecashtoll.services.responses;

import com.mandiriecash.ecashtoll.services.models.Activity;

import java.util.List;

public class GetActivitiesResponse {
    String status;
    String message;
    List<Activity> activities;

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

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }
}
