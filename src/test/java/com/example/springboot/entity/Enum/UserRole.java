package com.example.springboot.entity.Enum;

public enum UserRole {
    ADMIN,
    CUSTOMER,
    EXPERT;
    public static UserRole getFromString(String name) {
        for (UserRole value : UserRole.values()) {
            if (value.toString().equalsIgnoreCase(name)) {
                return value;
            }
        }
        return null;
    }
    
}
