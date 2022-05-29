package com.example.ignition;

import java.util.Timer;
import java.util.TimerTask;
public class TestDrive {
    Timer timer;
    int [] time;
    int finalDistance = 150, currentDistance, tripProgress;
    double speed = 0;
    double fuelConsumption = 0.0, fuelConsumptionRate = 0.0;
    public void accelerate()
    {
        speed = speed+1;
    }

    public void fuelConsumptionCalculator() {
        if (fuelConsumptionRate < 0)
            fuelConsumptionRate = 0;
    }
    void resetData()
    {
        currentDistance = 0;
        speed = 0;
        fuelConsumption = 0;
        fuelConsumptionRate = 0;
        tripProgress = 0;
    }

 /*   public int[] timeRemaining(int currentDistance, int finalDistance)
    {
        int[] values = new int[3];
        int time = (int)(finalDistance - currentDistance) / speed;
        int hours = 0, minutes = 0, seconds = 0;
        if (time > 3600)
        {
            hours = time % 3600;
            minutes = time/3600%60;
            seconds = time/3600/60;
        }
        else if (time > 60)
        {
            minutes = time%60;
            seconds = time%60;
        }
        else seconds = time;

        values[0] = hours;
        values[1] = minutes;
        values[2] = seconds;

        return values;
    }
*/

    public TestDrive() {
        timer = new Timer();
        timer.schedule(new RemindTask(),
                0,        //initial delay
                1000);  //subsequent rate

    }

    class RemindTask extends TimerTask {
        public void run() {
            if (finalDistance > currentDistance) {
                currentDistance += speed;
                tripProgress = currentDistance*100/finalDistance;
                fuelConsumptionCalculator();
                fuelConsumption += fuelConsumptionRate;
                fuelConsumption = (double) (Math.round(fuelConsumption*1000.0)/1000.0);
            }
        }
    }
}
