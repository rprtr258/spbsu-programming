package com.rprtr258.game;

import static java.lang.Math.abs;

public class TicTacToeField {
    private final int EMPTY_MARK = 0;
    private final int[][] field = new int[3][3];

    public TicTacToeField() {
        clear();
    }

    public void clear() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                field[i][j] = EMPTY_MARK;
    }

    public CellState getCellState(int i, int j) {
        int cell = field[i][j];
        return (cell == 0 ? CellState.EMPTY_CELL : (cell == 1 ? CellState.CROSS_CELL : CellState.ZERO_CELL));
    }

    public void setCellState(int i, int j, CellState value) {
        switch (value) {
            case ZERO_CELL: {
                int ZERO_MARK = -1;
                field[i][j] = ZERO_MARK;
                break;
            }
            case CROSS_CELL: {
                int CROSS_MARK = 1;
                field[i][j] = CROSS_MARK;
                break;
            }
            case EMPTY_CELL: {
                field[i][j] = EMPTY_MARK;
                break;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int cell = field[i][j];
                result.append(cell == 0 ? "_" : (cell == 1 ? "X" : "O")).append(" ");
            }
            result.append("\n");
        }
        return result.toString();
    }

    public int countEmptyCells() {
        int result = 0;
        for (int[] row : field)
            for (int cell : row)
                if (cell == EMPTY_MARK)
                    result++;
        return result;
    }

    public int getMaxLineSum() {
        int result = 0;
        int maxRowSum = getMaxRowSum();
        int maxColumnSum = getMaxColumnSum();
        int maxDiagonalSum = getMaxDiagonalSum();
        if (abs(maxRowSum) > abs(result))
            result = maxRowSum;
        if (abs(maxColumnSum) > abs(result))
            result = maxColumnSum;
        if (abs(maxDiagonalSum) > abs(result))
            result = maxDiagonalSum;
        return result;
    }

    private int getMaxRowSum() {
        int result = 0;
        for (int i = 0; i < 3; i++) {
            int rowSum = field[i][0] + field[i][1] + field[i][2];
            if (abs(rowSum) > abs(result))
                result = rowSum;
        }
        return result;
    }

    private int getMaxColumnSum() {
        int result = 0;
        for (int i = 0; i < 3; i++) {
            int rowSum = field[0][i] + field[1][i] + field[2][i];
            if (abs(rowSum) > abs(result))
                result = rowSum;
        }
        return result;
    }

    private int getMaxDiagonalSum() {
        int mainDiagonalSum = 0;
        for (int i = 0; i < 3; i++)
            mainDiagonalSum += field[i][i];
        int secondDiagonalSum = 0;
        for (int i = 0; i < 3; i++)
            secondDiagonalSum += field[i][2 - i];
        return (abs(mainDiagonalSum) > abs(secondDiagonalSum) ? mainDiagonalSum : secondDiagonalSum);
    }

    public int getSize() {
        return field.length;
    }
}
