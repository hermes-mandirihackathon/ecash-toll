package com.mandiriecash.ecashtoll.services.responses;

import com.mandiriecash.ecashtoll.services.models.Plan;

import java.util.List;

public class GetPlansResponse {
    String status;
    String message;
    List<Plan> plans;

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

    public List<Plan> getPlans() {
        return plans;
    }

    public void setPlans(List<Plan> plans) {
        this.plans = plans;
    }
}
