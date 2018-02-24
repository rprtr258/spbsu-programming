package com.rprtr258;

import java.util.ArrayList;
import java.util.Collections;

public class Matrix {
    private int[][] data = null;
    private int height = -1;
    private int width = -1;

   public Matrix(int[][] data) {
       this.data = data.clone();
       height = data.length;
       width = data[0].length;
   }

   public void writeTourTo(IWriter writer) {
        ArrayList<Integer> tour = spiralTour();
        for (int i = 0; i < tour.size(); i++) {
            writer.write(tour.get(i));
            if (i + 1 < tour.size())
                writer.write(' ');
        }
   }

   public ArrayList<Integer> spiralTour() {
       ArrayList<Integer> result = new ArrayList<>();
       int dist = width - 1;
       int i = 0;
       int j = 0;
       while (dist > 0) {
           for (int k = 0; k < dist; k++) {
               result.add(data[i][j]);
               j++;
           }
           for (int k = 0; k < dist; k++) {
               result.add(data[i][j]);
               i++;
           }
           for (int k = 0; k < dist; k++) {
               result.add(data[i][j]);
               j--;
           }
           for (int k = 0; k < dist; k++) {
               result.add(data[i][j]);
               i--;
           }
           i++;
           dist -= 2;
       }
       // last element in center
       result.add(data[i][j + 1]);
       Collections.reverse(result);
       return result;
   }
}
