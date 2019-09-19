package com.example.zhulie;

public class teamModel {

    private int teamImage;
    private String teamName;
    private String teamId;

    public teamModel(int teamImage, String teamName, String teamId) {
        this.teamImage = teamImage;
        this.teamName = teamName;
        this.teamId = teamId;
    }

    public int getTeamImage() {
        return teamImage;
    }

    public void setTeamImage(int teamImage) {
        this.teamImage = teamImage;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }
}
