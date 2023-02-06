package com.barry.flutter_app_intellij_plugin.action;

public class BusinessDataModel {
    final String businessName;
    final BusinessCategory businessCategory;
    final StateManagement stateManagement;

    public BusinessDataModel(String businessName, BusinessCategory businessCategory, StateManagement stateManagement) {
        this.businessName = businessName;
        this.businessCategory = businessCategory;
        this.stateManagement = stateManagement;
    }

    public String getBusinessName() {
        return businessName;
    }

    public BusinessCategory getBusinessCategory() {
        return businessCategory;
    }

    public StateManagement getStateManagement() {
        return stateManagement;
    }

    @Override
    public String toString() {
        return "businessName: " + businessName + " businessCategory: " + businessCategory.toString() + " blocStyle: " + stateManagement.toString();
    }
}