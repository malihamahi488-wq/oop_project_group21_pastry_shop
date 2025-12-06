package com.example.com_oop_project_group21_pastryshop.Nabiha.Nabiha;

public class policies {
    private String policy;

    public policies() {
    }

    public policies(String policy) {
        this.policy = policy;
    }

    public String getPolicy() {
        return policy;
    }

    @Override
    public String toString() {
        return "policy{" +
                "policy='" + policy + '\'' +
                '}';
    }
}
