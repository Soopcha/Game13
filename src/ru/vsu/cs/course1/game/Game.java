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
    private int[][] field = null; // массив - игровое поле
    /**
     * Максимальное кол-во цветов
     */
    private int colorCount = 0;

    private int[][] currentFigure = null; // массив - игровая фигура

    private int currentRow = 0;
    private int currentColumn = 0;

    private int gameScore = 0; // счёт игры

    private boolean endOfGame = false; // true - если конец игры


    public Game() {
    }

    public void newGame(int rowCount, int colCount, int colorCount) {
        // создаем поле
        field = new int[rowCount][colCount];
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                field[i][j] = 0;
            }
        }

        this.colorCount = colorCount;

        if (currentFigure == null) {
            createTheCurrentFigure();
        }

        // gameIsOn();

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

    public void gameIsOn() { //что делает наше игровое поле когда игра уже в процессе
        if (!endOfGame) {

            if (!cross(currentFigure, currentRow + 1, currentColumn)) {
                for (int i = 0; i < currentFigure.length; i++) {
                    for (int j = 0; j < currentFigure[0].length; j++) {
                        if (field[currentRow + i][currentColumn + j] == 0) {
                            if (!(currentFigure[i][j] == 0)) {
                            /*if ((field[0].length > (currentColumn + j + 1)) && (field.length > (currentRow + i + 1))) {
                                field[currentRow + i][currentColumn + j] = currentFigure[i][j];
                            }
                             */
                                field[currentRow + i][currentColumn + j] = currentFigure[i][j];
                            }
                        }
                    }
                }
                currentFigure = null;// очищаем предыдущую текущую фигуру

                for (int i = 0; i < field[0].length; i++) {
                    if (field[0][i] == 1) {
                        endOfGame = true;
                    }
                }
                createTheCurrentFigure();
            }


            // очищение целых строк
            for (int i = 0; i < field.length; i++) {
                int kol1 = 0;
                for (int j = 0; j < field[0].length; j++) {
                    if (!(field[i][j] == 0)) {
                        kol1++;
                    }
                }
                if (kol1 == field[0].length) {
                    for (int j = 0; j < field[0].length; j++) {
                        field[i][j] = 0;
                    }
                    gameScore += 100;
                }

            }
        }

    }

    public int getGameScore() { // метод возвращает счёт в игре
        return gameScore;
    }

    public boolean getEndOfGame(){ // метод возвращает true, когда игра проиграна
        return endOfGame;
    }


    private void createTheCurrentFigure() {
        int rndFigure = rnd.nextInt(7); // вернёт значения от 0 до 6 (не включая)  (выбираем рандомно фигуру)
        int rndOverturn = rnd.nextInt(4); // число от 0 до 3(выбираем как рандомно перевернуть фигуру)
        int rndColor = rnd.nextInt(8);

        switch (rndFigure) {
            case 0:
                currentFigure = new int[][]{{0, 0, 0}, {rndColor + 1, rndColor + 1, rndColor + 1}, {0, rndColor + 1, 0}};
                switch (rndOverturn) { // в зависимости от того, какая фигура и в каком положении нужно разные currentRow и  currentColumn задавать
                    case 0:
                        currentRow = -2;     //row - строки
                        currentColumn = 3; //column - столбцы   (не фигуры нашей а  располажения фигуры в массиве поля field )
                        break;
                    default:
                        currentRow = -1;
                        currentColumn = 3;
                }
                break;
            case 1:
                currentFigure = new int[][]{{rndColor + 1, rndColor + 1}, {rndColor + 1, rndColor + 1}};
                currentRow = -1;
                currentColumn = 3;
                break;
            case 2:
                currentFigure = new int[][]{{0, rndColor + 1, 0, 0}, {0, rndColor + 1, 0, 0}, {0, rndColor + 1, 0, 0}, {0, rndColor + 1, 0, 0}};
                switch (rndOverturn) {
                    case 0:
                        currentRow = -1;
                        currentColumn = 3;
                        break;
                    case 1:
                        currentRow = -2;
                        currentColumn = 2;
                        break;
                    case 2:
                        currentRow = -1;
                        currentColumn = 2;
                        break;
                    case 3:
                        currentRow = -3;
                        currentColumn = 2;
                        break;

                }
                break;
            case 3:
                currentFigure = new int[][]{{0, 0, 0}, {0, rndColor + 1, rndColor + 1}, {rndColor + 1, rndColor + 1, 0}};
                switch (rndOverturn) {
                    case 0:
                        currentRow = -2;
                        currentColumn = 3;
                        break;
                    case 3:
                        currentRow = -1;
                        currentColumn = 2;
                        break;
                    default:
                        currentRow = -1;
                        currentColumn = 3;
                }
                break;
            case 4:
                currentFigure = new int[][]{{0, 0, 0}, {rndColor + 1, rndColor + 1, 0}, {0, rndColor + 1, rndColor + 1}};
                switch (rndOverturn) {
                    case 0:
                        currentRow = -2;
                        currentColumn = 3;
                        break;
                    case 3:
                        currentRow = -1;
                        currentColumn = 2;
                        break;
                    default:
                        currentRow = -1;
                        currentColumn = 3;

                }
                break;
            case 5:
                currentFigure = new int[][]{{0, 0, 0}, {rndColor + 1, 0, 0}, {rndColor + 1, rndColor + 1, rndColor + 1}};
                switch (rndOverturn) {
                    case 0:
                        currentRow = -2;
                        currentColumn = 3;
                        break;
                    case 3:
                        currentRow = -1;
                        currentColumn = 2;
                        break;
                    default:
                        currentRow = -1;
                        currentColumn = 3;
                }
                break;
            case 6:
                currentFigure = new int[][]{{0, 0, 0}, {0, 0, rndColor + 1}, {rndColor + 1, rndColor + 1, rndColor + 1}};
                switch (rndOverturn) {
                    case 0:
                        currentRow = -2;
                        currentColumn = 3;
                        break;
                    case 3:
                        currentRow = -1;
                        currentColumn = 2;
                        break;
                    default:
                        currentRow = -1;
                        currentColumn = 3;
                }
                break;
        }
        currentFigure = turnOverAtTheBeginningOfTheGame(currentFigure, rndOverturn); //рандомное кол-во раз переворачивает фигуру
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

    private boolean cross(int[][] figure, int row, int col) { // смотрим пересекается наша ли фигура с fild; true - если всё норм
        /* мб надо было:
        int t1 = 0;
        int t2 = 0;
        if (col == -1) {
            t1 = 1;
        }
        if (col == figure[0].length) {
            t2 = -1;
        }

        for (int i = 0; i < figure.length; i++) {
            for (int j = 0 + t1; j < figure[i].length + t2; j++) {
         */
        for (int i = 0; i < figure.length; i++) {
            for (int j = 0; j < figure[i].length; j++) {

                if (row + i >= field.length || col + j >= field[0].length || row + i < 0 || col + j < 0) {
                    // проверка на то, в поле ли наша фигура вообще
                    if (figure[i][j] > 0) {
                        return false;
                    }
                } else {
                    if (figure[i][j] > 0 && field[i + row][j + col] > 0) {  //слйчай когда правой стороной текущей фигуры (дыркой) можно залезть за поле
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

    public void cleanGame() { // очистить игру
        field = null;
        currentFigure = null;
        gameScore = 0;
        endOfGame = false;
    }



   /* тестик просто
   public static void main(String[] args) {
        int[][] test = new int[][]{{0, 0, 1},{1, 1, 1},{0, 0, 0}};
        System.out.println(Arrays.deepToString(flipWithAClickUp(test)));
    }

    */
}
