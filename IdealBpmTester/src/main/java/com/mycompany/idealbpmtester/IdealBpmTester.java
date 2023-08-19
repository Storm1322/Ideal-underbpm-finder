package com.mycompany.idealbpmtester;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.floor;

public class IdealBpmTester {
    static int i = 0;

    static Map<Double,Double> idealBPMList = new HashMap<>();
    public static void main(String[] args) {
        int bpm = 220;
        for(double i = bpm; i > 0; i = i - 0.1) {
            idealBPMList.put(i,idealBpm(bpm, 99.5, i, 0));
        }

        double min = Double.MAX_VALUE;
        double minval = 0;

        for (Map.Entry<Double,Double> entry : idealBPMList.entrySet()) {
            Double key = entry.getKey();
            Double value = entry.getValue();
            if(value>=0 && key <= min){
               min = key;
               minval = value;
            }
        }
        System.out.println(min + "," + minval);

    }
    
    public static double idealBpm(double song_bpm, double ODLCT, double IUBPM_guess, double firstHitLatency){
        double surplusLatency = (ODLCT / ((15000 / IUBPM_guess) - (15000 / song_bpm)) -
                floor(ODLCT / ((15000 / IUBPM_guess) - (15000 / song_bpm)))) * ODLCT - (15000 / song_bpm);

            firstHitLatency += surplusLatency;

            return firstHitLatency >= 0 ? firstHitLatency : -1.0;
    }

    public static double odFinder(double od){
        int base = 200;
        return base - 10 * od - 0.5;
    }
}
