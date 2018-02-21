package com.rprtr258;

public class Matrix {
    private int[][] data = null;
    private int height = -1;
    private int width = -1;

   public Matrix(int[][] data) {
       this.data = data.clone();
       height = data.length;
       width = data[0].length;
   }

   public void writeTo(IWriter writer) {
       for (int i = 0; i < height; i++) {
           for (int j = 0; j < width; j++) {
               writer.write(data[i][j]);
               writer.write(' ');
           }
           writer.write('\n');
       }
   }
}
