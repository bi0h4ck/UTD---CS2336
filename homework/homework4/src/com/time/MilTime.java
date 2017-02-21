package com.time;

/**
 * Diem Pham dtp160130
 * CS 2336.002
 */
public class MilTime extends Time {
    private int milHours;

    public MilTime(int milHours, int seconds){
        super(0,0, seconds);
        this.milHours = milHours;
        int hours;
        int minutes;
        if(milHours >= 1200)
            hours = ((milHours - 1200)/100);
        else
            hours = (milHours/100);
        minutes = milHours % 100;

        if(hours == 0)
            hours = 12;
        setHours(hours);
        setMinutes(minutes);
    }

    public int getMilHours(){
        return this.milHours;
    }

    public void setMilHours(int milHours){
        this.milHours = milHours;
        int hours;
        int minutes = 0;
        if(milHours >= 1200)
            hours = ((milHours - 1200)/100);
        else
            hours = (milHours/100);

        if(hours == 0)
            hours = 12;
        setHours(hours);
        setMinutes(minutes);
    }

    public int getStandhr(){
        return getHours();
    }
}