package com.example.zhulie;

public class projectModel {

    private String projectName, date_creation;

    public projectModel(String projectName, String date_creation) {
        this.projectName = projectName;
        this.date_creation = date_creation;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(String date_creation) {
        this.date_creation = date_creation;
    }
}
