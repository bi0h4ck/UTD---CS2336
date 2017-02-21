/**
 * Diem Pham dtp160130
 * CS 2336.002
 */

import com.time.MilTime;
import com.time.TimeClock;

import java.util.Scanner;

public class TimeValidator {
    public static int getHours(){
         int hours;
         Scanner scanner = new Scanner(System.in);
         System.out.print("Enter military time: ");
         hours = scanner.nextInt();
         if (hours < 0)
             hours = 0;
         else if(hours > 2359)
             hours = 2359;
         return hours;
        }

    public static int getSeconds(){
        int seconds;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the seconds: ");
        seconds = scanner.nextInt();
        if (seconds < 0)
            seconds = 0;
        else if (seconds > 59)
            seconds = 59;
        return seconds;
    }

    public static void main (String args[]){
        MilTime milTime;
        TimeClock clock;
        int hours, seconds, startMilHours, startSeconds, endMilHours, endSeconds;
        hours = getHours();
        seconds = getSeconds();
        milTime = new MilTime(hours, seconds);

        System.out.println("Military time: " + milTime.getMilHours());
        System.out.println("Hours: " + milTime.getStandhr());
        System.out.println("Minutes: " + milTime.getMinutes());
        System.out.println("Seconds: " + milTime.getSeconds());

        System.out.println("Enter start time");
        startMilHours = getHours();
        startSeconds = getSeconds();
        System.out.println("Enter end time");
        endMilHours = getHours();
        endSeconds = getSeconds();

        clock = new TimeClock(startMilHours, startSeconds, endMilHours, endSeconds);
        System.out.println("Start time is: " + clock.getStartMilHours());
        System.out.println("End time is: " + clock.getEndMilHours());
        System.out.println("Time difference is: " + clock.timeDifference());
    }
}