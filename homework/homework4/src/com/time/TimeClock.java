package com.time;

/**
 * Diem Pham dtp160130
 * CS 2336.002
 */
public class TimeClock extends MilTime {
    private int milHours;
    private int seconds;

    public TimeClock(int startMilHours, int startSeconds, int endMilHours, int endSeconds){
        super(startMilHours, startSeconds);
        this.milHours = endMilHours;
        this.seconds = endSeconds;

    }

    public int getEndMilHours(){
        return milHours;
    }

    public void setEndMilHours(int milHours){
        this.milHours = milHours;
    }

    public int getEndSeconds(){
        return seconds;
    }

    public void setEndSeconds(int seconds){
        this.seconds = seconds;
    }

    public int getStartMilHours(){
        return getMilHours();
    }

    public void setStartMilHours(int milHours){
        super.setMilHours(milHours);
    }

    public int getStartSeconds(){
        return getSeconds();
    }

    public void setStartSeconds(int seconds){
        super.setSeconds(seconds);
    }

    public int timeDifference(){
        return (getEndMilHours() - getStartMilHours())/100;
    }

}