package com.example.memorycardgame;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;

public class User {

    private String easyLevelTopScore;
    private Duration easyLevelTopTime;
    private String mediumLevelTopScore;
    private Duration mediumLevelTopTime;
    private String hardLevelTopScore;
    private Duration hardLevelTopTime;

    public void setEasyLevelTopScore(String easyLevelTopScore, Duration easyLevelTopTime) {
        this.easyLevelTopScore = easyLevelTopScore;
        this.easyLevelTopTime = easyLevelTopTime;
    }

    public void setMediumLevelTopScore(String mediumLevelTopScore, Duration mediumLevelTopTime) {
        this.mediumLevelTopScore = mediumLevelTopScore;
        this.mediumLevelTopTime = mediumLevelTopTime;
    }

    public void setHardLevelTopScore(String hardLevelTopScore, Duration hardLevelTopTime) {
        this.hardLevelTopScore = hardLevelTopScore;
        this.hardLevelTopTime = hardLevelTopTime;
    }

    public Duration getEasyLevelTopTime() {
        return easyLevelTopTime;
    }

    public Duration getHardLevelTopTime() {
        return hardLevelTopTime;
    }

    public Duration getMediumLevelTopTime() {
        return mediumLevelTopTime;
    }

    public String getEasyLevelTopScore() {
        return easyLevelTopScore;
    }

    public String getHardLevelTopScore() {
        return hardLevelTopScore;
    }

    public String getMediumLevelTopScore() {
        return mediumLevelTopScore;
    }

}
