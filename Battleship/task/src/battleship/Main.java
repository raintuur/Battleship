package battleship;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import static battleship.GameBoard.VERTICAL_LETTERS;
import static battleship.Main.INPUT_PREFIX;

public class Main {

    public static final String INPUT_PREFIX = "\n> ";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // COMPLETED: Stage 1/5: Take position!
        GameSetup gameSetup = new GameSetup(scanner);
        // TODO: Stage 2/5: The first shot
        // TODO: Stage 3/5: Fog of war
        // TODO: Stage 4/5: The end of the war
        // TODO: Stage 5/5: Friend or foe
    }
}

class GameSetup {
    public static final String LEFT = "left";
    public static final String RIGHT = "right";
    public static final String TOP = "top";
    public static final String BOTTOM = "bottom";
    private GameBoard boardPlayer1;
    private GameBoard boardPlayer2;

    public GameSetup(Scanner scanner) {
        // players 1 and 2 are required at later stage (stage 5/5)
        this.boardPlayer1 = createPlayerGameBoard(scanner, "Player 1");
    }

    /*  Note! By accepted Java code style convention all methods in class
    should be ordered top to bottom as 'public -> private'.
    However, for better readability to study the code I have not followed this rule.
    All methods are sorted in the flow of the logic.
    Any standard Getters and Setters are at the bottom section of the class.
    */

    private GameBoard createPlayerGameBoard(Scanner scanner, String playerName) {
        GameBoard board = new GameBoard(playerName);
        board.printOwnBoard();
        addAllShipsToGameBoard(scanner, board);
        return board;
    }

    private void addAllShipsToGameBoard(Scanner scanner, GameBoard board) {
        while (!board.areAllShipsPlaced()) {
            askShipCoordinates(board.getNextShip());
            addNextShipToGameBoard(scanner, board);
        }
    }

    private void askShipCoordinates(Ship ship) {
        System.out.printf("\nEnter the coordinates of the %s (%s cells):", ship.getName(), ship.getSize());
    }

    private void addNextShipToGameBoard(Scanner scanner, GameBoard board) {
        boolean placementIsOk = false;
        while (!placementIsOk) {
            Coordinates coordinates = getCoordinatesInfo(scanner);
            board.setCoordinates(coordinates);
            try {
                List<Cell> cellsToPlace = createCellsToPlace(coordinates);
                validateOkToPlaceShipToGameBoard(cellsToPlace, board);
                completeAddingShipToGameBoard(cellsToPlace, board);
                board.printOwnBoard();
                placementIsOk = true;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static Coordinates getCoordinatesInfo(Scanner scanner) {
        System.out.print(INPUT_PREFIX);
        String startPos = scanner.next();
        String endPos = scanner.next();
        return new Coordinates(startPos, endPos);
    }

    private List<Cell> createCellsToPlace(Coordinates coordinates) {
        if (coordinates.isHorizontalPlacement()) {
            return createHorizontalPlacementCells(coordinates);
        } else {
            return createVerticalPlacementCells(coordinates);
        }
    }

    private List<Cell> createHorizontalPlacementCells(Coordinates coordinates) {
        List<Cell> placedCells = new ArrayList<>();
        for (int i = coordinates.getStartPosColumn(); i <= coordinates.getEndPosColumn(); i++) {
            placedCells.add(new Cell(coordinates.getStartPosRow(), i));
        }
        return placedCells;
    }

    private List<Cell> createVerticalPlacementCells(Coordinates coordinates) {
        List<Cell> placedCells = new ArrayList<>();
        for (int i = coordinates.getStartPosRow(); i <= coordinates.getEndPosRow(); i++) {
            placedCells.add(new Cell(i, coordinates.getStartPosColumn()));
        }
        return placedCells;
    }

    private void validateOkToPlaceShipToGameBoard(List<Cell> cellsToPlace, GameBoard board) throws Exception {
        validateShipLengthIsOK(board);
        validateShipLocationIsOk(board.getCoordinates());
        validateCellsToPlaceAreNotCloseToShips(cellsToPlace, board);
    }

    private static void validateShipLengthIsOK(GameBoard board) throws Exception {
        Coordinates coordinates = board.getCoordinates();
        Ship ship = board.getNextShip();


        if (coordinates.isHorizontalPlacement() &&
                !isWithinAllowedSize(coordinates.getStartPosColumn(), coordinates.getEndPosColumn(), ship.getSize())) {
            throw new Exception("\nError! Wrong length of the " + ship.getName() + "! Try again:");
        }
        if (coordinates.isVerticalPlacement() &&
                !isWithinAllowedSize(coordinates.getStartPosRow(), coordinates.getEndPosRow(), ship.getSize())) {
            throw new Exception("Error! Wrong length of the " + ship.getName() + "! Try again:");
        }
    }

    private static boolean isWithinAllowedSize(int position1, int position2, int shipSize) {
        return Math.abs(position1 - position2) + 1 == shipSize;
    }

    private static void validateShipLocationIsOk(Coordinates coordinates) throws Exception {
        if (!coordinates.isHorizontalPlacement() && !coordinates.isVerticalPlacement()) {
            throw new Exception("\nError! Wrong ship location! Try again:");
        }
    }

    private void validateCellsToPlaceAreNotCloseToShips(List<Cell> cellsToPlace, GameBoard board) throws Exception {
        boolean isHorizontal = board.getCoordinates().isHorizontalPlacement();
        List<Cell> adjacentCells = createAdjacentCells(cellsToPlace, isHorizontal);
        validateAdjacentCellsNotCloseToShips(adjacentCells, board.getShips());
    }

    private List<Cell> createAdjacentCells(List<Cell> cellsToPlace, boolean isHorizontalPlacement) {
        List<Cell> adjacentCells = new ArrayList<>();
        if (isHorizontalPlacement) {
            createHorizontalAdjacentCells(cellsToPlace, adjacentCells);
        } else {
            createVerticalAdjacentCells(cellsToPlace, adjacentCells);
        }
        return adjacentCells;
    }

    private void createHorizontalAdjacentCells(List<Cell> cellsToPlace, List<Cell> adjacentCells) {
        Cell firstCell = cellsToPlace.get(0);
        Cell lastCell = cellsToPlace.get(cellsToPlace.size() - 1);
        adjacentCells.add(createAdjacentCell(firstCell, LEFT));
        adjacentCells.add(createAdjacentCell(lastCell, RIGHT));

        for (Cell cell : cellsToPlace) {
            adjacentCells.add(createAdjacentCell(cell, TOP));
            adjacentCells.add(createAdjacentCell(cell, BOTTOM));
        }
    }
    private void validateAdjacentCellsNotCloseToShips(List<Cell> adjacentCells, List<Ship> ships) throws Exception {
        for (Ship ship : ships) {
            for (Cell placedCell : ship.getPlacedCells()) {
                if (adjacentCells.contains(placedCell)) {
                    throw new Exception("Error! You placed it too close to another one. Try again:");
                }
            }
        }
    }

    private void createVerticalAdjacentCells(List<Cell> cellsToPlace, List<Cell> adjacentCells) {
        for (Cell cell : cellsToPlace) {
            adjacentCells.add(createAdjacentCell(cell, LEFT));
            adjacentCells.add(createAdjacentCell(cell, RIGHT));
        }

        Cell firstCell = cellsToPlace.get(0);
        Cell lastCell = cellsToPlace.get(cellsToPlace.size() - 1);
        adjacentCells.add(createAdjacentCell(firstCell, TOP));
        adjacentCells.add(createAdjacentCell(lastCell, BOTTOM));
    }

    private Cell createAdjacentCell(Cell cell, String direction) {
        switch (direction) {
            case LEFT:
                return new Cell(cell.getRow(), cell.getColumn() - 1);
            case RIGHT:
                return new Cell(cell.getRow(), cell.getColumn() + 1);
            case TOP:
                return new Cell(cell.getRow() - 1, cell.getColumn());
            case BOTTOM:
                return new Cell(cell.getRow() + 1, cell.getColumn());
            default:
                throw new IllegalArgumentException("Error! Cannot create adjacent cell, unknown 'direction'");
        }
    }

    private void completeAddingShipToGameBoard(List<Cell> cellsToPlace, GameBoard board) {
        Ship ship = board.getNextShip();
        ship.setPlacedCells(cellsToPlace);
        ship.setShipIsPlaced();
        board.addPlacedCellsToOwnBoard(cellsToPlace);
        board.findAndSetNextShip();
    }
}

class GameBoard {

    public static final String[] VERTICAL_LETTERS = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};

    // player name required at later stage (stage 5/5)
    private String playerName;
    private char[][] ownBoard;
    private List<Ship> ships;
    private Ship nextShip;
    private Coordinates coordinates;

    public GameBoard(String playerName) {
        this.playerName = playerName;
        ownBoard = createBlankGameBoard();
        ships = createShips();
        findAndSetNextShip();
    }

    /*  Note! By accepted Java code style convention all methods in class
        should be ordered top to bottom as 'public -> private'.
        However, for better readability to study the code I have not followed this rule.
        All methods are sorted in the flow of the logic.
        Any standard Getters and Setters are at the bottom section of the class.
    */

    private static char[][] createBlankGameBoard() {
        char[][] matrix = new char[10][11];
        for (char[] row : matrix) {
            for (int i = 1; i < row.length; i++) {
                row[i] = '~';
            }
        }
        return matrix;
    }

    private List<Ship> createShips() {
        List<Ship> ships = new ArrayList<>();
        ships.add(new Ship("Aircraft Carrier", 5));
        ships.add(new Ship("Battleship", 4));
        ships.add(new Ship("Submarine", 3));
        ships.add(new Ship("Cruiser", 3));
        ships.add(new Ship("Destroyer", 2));
        return ships;
    }

    public void findAndSetNextShip() {
        if (!areAllShipsPlaced()) {
            for (Ship ship : ships) {
                if (!ship.isShipPlaced()) {
                    nextShip = ship;
                    break;
                }
            }
        }
    }

    public boolean areAllShipsPlaced() {
        for (Ship ship : ships) {
            if (!ship.isShipPlaced()) {
                return false;
            }
        }
        return true;
    }

    public void printOwnBoard() {
        String header = "\n  1 2 3 4 5 6 7 8 9 10\n";

        StringBuilder sb = new StringBuilder();
        sb.append(header);
        for (int row = 0; row < ownBoard.length; row++) {
            sb.append(VERTICAL_LETTERS[row]);
            for (int column = 1; column < VERTICAL_LETTERS.length + 1; column++) {
                sb.append(" " + ownBoard[row][column]);
            }
            if (row != ownBoard.length - 1) {
                sb.append(System.lineSeparator());
            }
        }
        System.out.println(sb);
    }

    public void addPlacedCellsToOwnBoard(List<Cell> placedCells) {
        for (Cell cell : placedCells) {
            ownBoard[cell.getRow()][cell.getColumn()] = 'O';
        }
    }

    // Getters, setters
    public List<Ship> getShips() {
        return ships;
    }

    public Ship getNextShip() {
        return nextShip;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

}

class Coordinates {
    private int startPosRow;
    private int startPosColumn;
    private int endPosRow;
    private int endPosColumn;
    private boolean isHorizontalPlacement;
    private boolean isVerticalPlacement;

    public Coordinates(String startPos, String endPos) {
        this.startPosRow = Math.min(getRowNumber(startPos), getRowNumber(endPos));
        this.endPosRow = Math.max(getRowNumber(startPos), getRowNumber(endPos));
        this.startPosColumn = Math.min(getColumnNumber(startPos), getColumnNumber(endPos));
        this.endPosColumn = Math.max(getColumnNumber(startPos), getColumnNumber(endPos));
        this.isHorizontalPlacement = this.startPosRow == this.endPosRow;
        this.isVerticalPlacement = this.startPosColumn == this.endPosColumn;
    }

    /*  Note! By accepted Java code style convention all methods in class
    should be ordered top to bottom as 'public -> private'.
    However, for better readability to study the code I have not followed this rule.
    All methods are sorted in the flow of the logic.
    Any standard Getters and Setters are at the bottom section of the class.
    */

    private int getRowNumber(String coordinate) {
        for (int i = 0; i < VERTICAL_LETTERS.length; i++) {
            if (coordinate.contains(VERTICAL_LETTERS[i])) {
                return i;
            }
        }
        return 0;
    }

    private int getColumnNumber(String coordinate) {
        return Integer.parseInt(coordinate.substring(1));
    }

    // Getters, setters
    public int getStartPosRow() {
        return startPosRow;
    }

    public int getStartPosColumn() {
        return startPosColumn;
    }

    public int getEndPosRow() {
        return endPosRow;
    }

    public int getEndPosColumn() {
        return endPosColumn;
    }

    public boolean isHorizontalPlacement() {
        return isHorizontalPlacement;
    }

    public boolean isVerticalPlacement() {
        return isVerticalPlacement;
    }

}

class Ship {
    private String name;
    private int size;
    private List<Cell> placedCells = new ArrayList<>();
    private boolean isShipPlaced = false;
    private boolean isSunk = false;

    public Ship(String name, int size) {
        this.name = name;
        this.size = size;
    }

    // Getters, setters
    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public boolean isShipPlaced() {
        return isShipPlaced;
    }

    public void setShipIsPlaced() {
        isShipPlaced = true;
    }

    public List<Cell> getPlacedCells() {
        return placedCells;
    }

    public void setPlacedCells(List<Cell> placedCells) {
        this.placedCells = placedCells;
    }

    @Override
    public String toString() {
        return "name='" + name +
                ", isShipPlaced=" + isShipPlaced +
                ", isSunk=" + isSunk;
    }
}

class Cell {
    private int row;
    private int column;
    private boolean isHit = false;

    public Cell(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public String toString() {
        return "r" + (row + 1) + "c" + column;
    }

    // These I Generated with IntelliJ.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return row == cell.row && column == cell.column && isHit == cell.isHit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column, isHit);
    }
}