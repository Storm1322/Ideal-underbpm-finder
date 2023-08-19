package com.mycompany.idealbpmtester;

import static java.lang.Math.floor;

public class IdealBpmTester {
    static int i = 0;

    public static void main(String[] args) {
        idealBpm(220, 99.5,205,0);
    }
    
    public static void idealBpm(double song_bpm, double ODLCT, double IUBPM_guess, double firstHitLatency){
        double surplusLatency = (ODLCT / (15000 / IUBPM_guess - 15000 / song_bpm) - floor(ODLCT / (15000 / IUBPM_guess - 15000 / song_bpm))) * ODLCT - 15000 / song_bpm;
        while(i<200){
            i++;
            firstHitLatency += surplusLatency; 
            if(firstHitLatency >= 0){
                if(firstHitLatency > ODLCT){
                    firstHitLatency -= ODLCT;
                }
                System.out.println(firstHitLatency);
                idealBpm(song_bpm, ODLCT, IUBPM_guess, firstHitLatency);
            }else{
                System.out.println("This bpm is no go.");
            }
        }
    }
}
