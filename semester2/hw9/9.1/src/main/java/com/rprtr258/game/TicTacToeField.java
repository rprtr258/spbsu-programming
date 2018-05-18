package com.rprtr258.game;

public class TicTacToeField {
    private final int EMPTY_MARK = 0;
    private final int CROSS_MARK = 1;
    private final int ZERO_MARK = -1;
    private int[][] field = new int[3][3];

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
                field[i][j] = ZERO_MARK;
                break;
            }
            case CROSS_CELL: {
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

    public int checkWin() {
        for (int i = 0; i < 3; i++) {
            int rowSum = field[i][0] + field[i][1] + field[i][2];
            if (rowSum == 3 || rowSum == -3)
                return rowSum / 3;
        }
        for (int i = 0; i < 3; i++) {
            int columnSum = field[0][i] + field[1][i] + field[2][i];
            if (columnSum == 3 || columnSum == -3)
                return columnSum / 3;
        }
        int mainDiagonalSum = 0;
        for (int i = 0; i < 3; i++)
            mainDiagonalSum += field[i][i];
        if (mainDiagonalSum == 3 || mainDiagonalSum == -3)
            return mainDiagonalSum / 3;
        int secondDiagonalSum = 0;
        for (int i = 0; i < 3; i++)
            secondDiagonalSum += field[i][2 - i];
        if (secondDiagonalSum == 3 || secondDiagonalSum == -3)
            return secondDiagonalSum / 3;
        return 0;
    }

    public int countEmptyCells() {
        int result = 0;
        for (int[] row : field)
            for (int cell : row)
                if (cell == EMPTY_MARK)
                    result++;
        return result;
    }
}
