package com.rprtr258;

enum GameState {
    CROSS_WIN,
    ZERO_WIN,
    DRAW,
    IN_PROCESS
}

enum CellState {
    ZERO_CELL,
    CROSS_CELL,
    EMPTY_CELL
}

public class TicTacToe {
    private final int EMPTY_MARK = 0;
    private final int CROSS_MARK = 1;
    private final int ZERO_MARK = -1;
    private int[][] field = new int[3][3];
    private final int CROSS_PLAYER = CROSS_MARK;
    private final int ZERO_PLAYER = ZERO_MARK;
    private int currentPlayer = CROSS_PLAYER;

    public TicTacToe() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                field[i][j] = EMPTY_MARK;
    }

    public boolean makeTurn(int row, int column) {
        if (getState() != GameState.IN_PROCESS)
            return false;
        if (field[row][column] != EMPTY_MARK)
            return false;
        field[row][column] = currentPlayer;
        changePlayer();
        return true;
    }

    public CellState getCellState(int i, int j) {
        int cell = field[i][j];
        return (cell == 0 ? CellState.EMPTY_CELL : (cell == 1 ? CellState.CROSS_CELL : CellState.ZERO_CELL));
    }

    public GameState getState() {
        int winState = checkWin();
        if (winState != 0)
            return (winState == 1 ? GameState.CROSS_WIN : GameState.ZERO_WIN);
        if (countEmptyCells() == 0)
            return GameState.DRAW;
        return GameState.IN_PROCESS;
    }

    private int checkWin() {
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
        int mainDiagSum = 0;
        for (int i = 0; i < 3; i++)
            mainDiagSum += field[i][i];
        if (mainDiagSum == 3 || mainDiagSum == -3)
            return mainDiagSum / 3;
        int secondDiagSum = 0;
        for (int i = 0; i < 3; i++)
            secondDiagSum += field[i][2 - i];
        if (secondDiagSum == 3 || secondDiagSum == -3)
            return secondDiagSum / 3;
        return 0;
    }

    private int countEmptyCells() {
        int result = 0;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (field[i][j] == EMPTY_MARK)
                    result++;
        return result;
    }

    private void changePlayer() {
        currentPlayer *= -1;
    }
}
