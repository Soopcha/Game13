package ru.vsu.cs.course1.game;

import java.awt.*;
import java.util.Arrays;
import java.util.Random;

/**
 * Класс, реализующий логику игры
 */
public class Game {

    public enum Action { // перечисления наши
        LEFT,
        RIGHT,
        TURNOVER,
        DOWN
    }

    /**
     * объект Random для генерации случайных чисел
     * (можно было бы объявить как static)
     */
    private final Random rnd = new Random();

    /**
     * двумерный массив для хранения игрового поля
     * (в данном случае цветов, 0 - пусто; создается / пересоздается при старте игры)
     */
    private int[][] field = null;
    /**
     * Максимальное кол-во цветов
     */
    private int colorCount = 0;

    private int[][] currentFigure = null;

    private int currentRow = 0;
    private int currentColumn = 0;


    public Game() {
    }

    public void newGame(int rowCount, int colCount, int colorCount) {
        // создаем поле
        field = new int[rowCount][colCount];
        this.colorCount = colorCount;

        if (currentFigure == null) {
            createTheCurrentFigure();
        }

        /*boolean gameIsOn = true;
        while (gameIsOn) {
            if (currentRow + currentFigure.length == field.length) {
                for (int i = 0; i < currentFigure.length; i++) {
                    for (int j = 0; j < currentFigure[0].length; j++) {
                        field[currentRow + i][currentColumn + j] = currentFigure[i][j];
                    }
                }
                createTheCurrentFigure();
            }
        }*/
        //testInit();
    }

    private void testInit() {
        currentFigure = new int[][]{{0, 1, 0}, {1, 1, 0}, {0, 1, 0}};
        // currentFigure = new int[field.length - currentRow][field[0] - currentColumn];
        currentRow = 3;     //row - строки
        currentColumn = 3;  //column - столбцы   (не фигуры нашей а  располажения фигуры в массиве поля field )


    }
    public void gameIsOn() {
        if (currentRow + currentFigure.length == field.length) {
            for (int i = 0; i < currentFigure.length; i++) {
                for (int j = 0; j < currentFigure[0].length; j++) {
                    field[currentRow + i][currentColumn + j] = currentFigure[i][j];
                }
            }
            createTheCurrentFigure();
        }
    }


    private void createTheCurrentFigure() {
        int rndFigure = rnd.nextInt(7); // вернёт значения от 0 до 6 (не включая)
        int rndOverturn = rnd.nextInt(4); //

        switch (rndFigure) {
            case 0:
                currentFigure = new int[][]{{0, 0, 0}, {1, 1, 1}, {0, 1, 0}};
                break;
            case 1:
                currentFigure = new int[][]{{1, 1}, {1, 1}};
                break;
            case 2:
                currentFigure = new int[][]{{0, 1, 0, 0}, {0, 1, 0, 0}, {0, 1, 0, 0}, {0, 1, 0, 0}};
                break;
            case 3:
                currentFigure = new int[][]{{0, 0, 0}, {0, 1, 1}, {1, 1, 0}};
                break;
            case 4:
                currentFigure = new int[][]{{0, 0, 0}, {1, 1, 0}, {0, 1, 1}};
                break;
            case 5:
                currentFigure = new int[][]{{0, 0, 0}, {1, 0, 0}, {1, 1, 1}};
                break;
            case 6:
                currentFigure = new int[][]{{0, 0, 0}, {0, 0, 1}, {1, 1, 1}};
                break;
        }
        currentFigure = turnOverAtTheBeginningOfTheGame(currentFigure, rndOverturn); //рандомное кол-во раз переворачивает фигуру

        currentRow = 0;     //row - строки
        currentColumn = 3;  //column - столбцы   (не фигуры нашей а  располажения фигуры в массиве поля field )
    }

    private int[][] turnOverAtTheBeginningOfTheGame(int arr[][], int numOfOverturn) {
        for (int i = 0; i < numOfOverturn; i++) {
            arr = turnOver(arr);
        }
        return arr;
    }

    public void leftMouseClick(int row, int col) {
        int rowCount = getRowCount(), colCount = getColCount();
        if (row < 0 || row >= rowCount || col < 0 || col >= colCount) {
            return;
        }

        field[row][col] = rnd.nextInt(getColorCount()) + 1;
    }

    public void rightMouseClick(int row, int col) {
        int rowCount = getRowCount(), colCount = getColCount();
        if (row < 0 || row >= rowCount || col < 0 || col >= colCount) {
            return;
        }

        field[row][col] = 0;
    }

    public int getRowCount() {
        return field == null ? 0 : field.length;
    }

    public int getColCount() {
        return field == null ? 0 : field[0].length;
        //[первый операнд - условие] ? [второй операнд] : [третий операнд]. Таким образом, в этой операции участвуют сразу
        // три операнда. В зависимости от условия тернарная операция возвращает второй или третий операнд: если условие равно true,
        // то возвращается второй операнд; если условие равно false, то третий.
    }

    public int getColorCount() {
        return colorCount;
    }

    public int getCell(int row, int col) { //проверяет есть ли фигура на поле в данной точке
        // если выводит -1 => нет точки      если 0 или 1 то есть или нет фигуры там
        // return (row < 0 || row >= getRowCount() || col < 0 || col >= getColCount()) ? 0 : field[row][col];
        if ((row < 0 || row >= getRowCount() || col < 0 || col >= getColCount())) {
            return 0;
        }
        if (field[row][col] > 0) {
            return field[row][col];
        }
        int row2 = row - currentRow;
        int col2 = col - currentColumn;

        if ((0 <= row2 && row2 < currentFigure.length && col2 >= 0 && col2 < currentFigure[0].length)) {
            return currentFigure[row2][col2];
        }
        return 0;
    }

    private static int[][] turnOver(int[][] figure) { //метод, что переворачивает фигуру , после клика вверх (или на лево)
        // (в зависимости будет ли фигура переворачиваться основываясь на двух кнопках или на 1 )
        int[][] newFigure = new int[figure.length][figure[0].length];

        for (int i = 0; i < figure.length; i++) {
            for (int j = 0; j < figure[0].length; j++) {
                newFigure[i][j] = figure[figure.length - 1 - j][i];
            }
        }
        return newFigure;
    }

    private boolean cross(int[][] figure, int row, int col) { // смотрим пересекается наша ли фигура с fild;
        for (int i = 0; i < figure.length; i++) {
            for (int j = 0; j < figure[0].length; j++) {

                if (row + i >= field.length || col + j >= field[0].length || row + i < 0 || col + j < 0) {
                    // проверка на то, в поле ли наша фигура вообще
                    if (figure[i][j] > 0) {
                        return false;
                    }
                } else {
                    if (figure[i][j] > 0 && field[i + row][j + col] > 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean canAction(Action action) {
        if (action == Action.RIGHT) {
            return (cross(currentFigure, currentRow, currentColumn + 1));
        }
        if (action == Action.LEFT) {
            return (cross(currentFigure, currentRow, currentColumn - 1));
        }
        if (action == Action.DOWN) {
            return (cross(currentFigure, currentRow + 1, currentColumn));
        }
        if (action == Action.TURNOVER) {
            return (cross(turnOver(currentFigure), currentRow, currentColumn));
        }
        return false;
    }

    public void action(Action action) {
        if (canAction(action)) {
            if (action == Action.RIGHT) {
                currentColumn++;
            }
            if (action == Action.LEFT) {
                currentColumn--;
            }
            if (action == Action.DOWN) {
                currentRow++;
            }
            if (action == Action.TURNOVER) {
                currentFigure = turnOver(currentFigure);
            }
        }

    }



   /* тестик просто
   public static void main(String[] args) {
        int[][] test = new int[][]{{0, 0, 1},{1, 1, 1},{0, 0, 0}};
        System.out.println(Arrays.deepToString(flipWithAClickUp(test)));
    }

    */
}
