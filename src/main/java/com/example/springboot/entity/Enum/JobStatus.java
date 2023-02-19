package com.example.springboot.entity.Enum;

public enum JobStatus {
    WAITING_FOR_SUGGESTION,
    WAITING_FOR_EXPERT,
    EXPERT_ON_WAY,
    STARTED,
    FINISHED,
    PAYED;

    public static JobStatus getFromString(String name) {
        for (JobStatus value : JobStatus.values()) {
            if (value.toString().equalsIgnoreCase(name)) {
                return value;
            }
        }
        return null;
    }

}