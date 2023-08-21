package com.mycompany.idealbpmtester;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.floor;

public class IdealBpmTester {
    static List<List<String>> listOfLists;
    static String[][] array;
    static String[] tableCategories = {"BPM", "Offset Per Cycle", "Clicks Until Uiss"};

    static Map<Double,Double> idealBPMList = new HashMap<>();
    
    public IdealBpmTester(double bpm, double od){
        double ODLCT = odlctFinder(od);
        listOfLists = new ArrayList<>();
        
        for(double i = bpm; i > 2 * bpm / 3; i = i - 1.0) {
            idealBPMList.put(i,idealBpm(bpm, ODLCT, i, 0));
            double clicksUntilMiss = floor(ODLCT / ((15000 / i) - (15000 / bpm)));
            
            if(idealBpm(bpm, ODLCT, i, 0) >= 0 && idealBpm(bpm, ODLCT, i, 0) < 80 - 6 * od){
                List<String> list = new ArrayList<>();
                list.add(String.valueOf(i));
                list.add(String.valueOf(idealBpm(bpm, ODLCT, i, 0)));
                list.add(String.valueOf(clicksUntilMiss));
                System.out.println(list);
                listOfLists.add(list);
            }
        }
        
        array = new String[listOfLists.size()][3];
        int j = 0;
        for(List<String> entry: listOfLists){
            array[j++] = entry.toArray(new String[1]);
        }
        
        BpmTable.main(null);
    }
    
    public static void main(String[] args) {
        double bpm = Double.parseDouble(GUI.jTextField2.getText());
        double ODLCT = odlctFinder(Double.parseDouble(GUI.jTextField3.getText()));
        for(double i = bpm; i > 0; i = i - 1.0) {
            idealBPMList.put(i,idealBpm(bpm, ODLCT, i, 0));
        }

        double min = Double.MAX_VALUE;
        double minval = 0;

        for (Map.Entry<Double,Double> entry : idealBPMList.entrySet()) {
            Double key = entry.getKey();
            Double value = entry.getValue();
            if(value>=0){
               min = key;
               minval = value;
               System.out.println(min + "," + minval);
            }
        }
    }
    
    public static double idealBpm(double song_bpm, double ODLCT, double IUBPM_guess, double firstHitLatency){
        double surplusLatency = (ODLCT / ((15000 / IUBPM_guess) - (15000 / song_bpm)) -
                floor(ODLCT / ((15000 / IUBPM_guess) - (15000 / song_bpm)))) * ODLCT - (15000 / song_bpm);

            firstHitLatency += surplusLatency;

            return firstHitLatency >= 0 ? firstHitLatency : -1.0;
    }

    public static double odlctFinder(double od){
        int base = 200;
        return base - 10 * od - 0.5;
    }
}
