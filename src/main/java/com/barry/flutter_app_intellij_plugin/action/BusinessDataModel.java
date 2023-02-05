package com.barry.flutter_app_intellij_plugin.action;

enum BusinessCategory {
    SCREEN,
    WIDGET
}

enum StateManagement {
    BLOC,
    CUBE
}

public class BusinessDataModel {
    final String businessName;
    final BusinessCategory businessCategory;
    final StateManagement stateManagement;

    public BusinessDataModel(String businessName, BusinessCategory businessCategory, StateManagement stateManagement) {
        this.businessName = businessName;
        this.businessCategory = businessCategory;
        this.stateManagement = stateManagement;
    }

    @Override
    public String toString() {
        return "businessName: " + businessName + " businessCategory: " + businessCategory.toString() + " blocStyle: " + stateManagement.toString();
    }
}