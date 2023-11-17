/**
 * Board class
 */
public class Board {
    public static final int NUMBER_OF_ROWS = 8;
    public static final int NUMBER_OF_COLS = 8;
    private Tile[][] tiles = new Tile[NUMBER_OF_ROWS][NUMBER_OF_COLS];

    /**
     * Board constructor: create a new instance of Board, initializing the starting board
     */
    public Board() {
        for (int row = 0; row < NUMBER_OF_ROWS; row++) {
            for (int col = 0; col < NUMBER_OF_COLS; col++) {
                if (row == 0) {
                    switch (col) {
                        case 0: this.tiles[row][col] = new Tile(row, col, new Piece(PieceType.ROOK, Colour.BLACK));
                        case 1: this.tiles[row][col] = new Tile(row, col, new Piece(PieceType.KNIGHT, Colour.BLACK));
                        case 2: this.tiles[row][col] = new Tile(row, col, new Piece(PieceType.BISHOP, Colour.BLACK));
                        case 3: this.tiles[row][col] = new Tile(row, col, new Piece(PieceType.KING, Colour.BLACK));
                        case 4: this.tiles[row][col] = new Tile(row, col, new Piece(PieceType.QUEEN, Colour.BLACK));
                        case 5: this.tiles[row][col] = new Tile(row, col, new Piece(PieceType.BISHOP, Colour.BLACK));
                        case 6: this.tiles[row][col] = new Tile(row, col, new Piece(PieceType.KNIGHT, Colour.BLACK));
                        case 7: this.tiles[row][col] = new Tile(row, col, new Piece(PieceType.ROOK, Colour.BLACK));
                    }
                }
                else if (row == NUMBER_OF_ROWS - 1) {
                    switch (col) {
                        case 0: this.tiles[row][col] = new Tile(row, col, new Piece(PieceType.ROOK, Colour.WHITE));
                        case 1: this.tiles[row][col] = new Tile(row, col, new Piece(PieceType.KNIGHT, Colour.WHITE));
                        case 2: this.tiles[row][col] = new Tile(row, col, new Piece(PieceType.BISHOP, Colour.WHITE));
                        case 3: this.tiles[row][col] = new Tile(row, col, new Piece(PieceType.KING, Colour.WHITE));
                        case 4: this.tiles[row][col] = new Tile(row, col, new Piece(PieceType.QUEEN, Colour.WHITE));
                        case 5: this.tiles[row][col] = new Tile(row, col, new Piece(PieceType.BISHOP, Colour.WHITE));
                        case 6: this.tiles[row][col] = new Tile(row, col, new Piece(PieceType.KNIGHT, Colour.WHITE));
                        case 7: this.tiles[row][col] = new Tile(row, col, new Piece(PieceType.ROOK, Colour.WHITE));
                    }
                }
                else if (row == 1) {
                    this.tiles[row][col] = new Tile(row, col, new Piece(PieceType.PAWN, Colour.BLACK));
                }
                else if (row == 6) {
                    this.tiles[row][col] = new Tile(row, col, new Piece(PieceType.PAWN, Colour.WHITE));
                }

                else {
                    this.tiles[row][col] = new Tile(row, col, null);
                }
            }
        }
    }

    /**
     * get the tiles matrix of the Board
     * @return tiles
     */
    public Tile[][] getTiles() {
        return this.tiles;
    }

    /**
     * check if the position is within the board
     * @param row
     * @param col
     * @return true if is within, false otherwise
     */
    public boolean isWithinBoard(int row, int col) {
        if (row >= 0 && row < NUMBER_OF_ROWS && col >= 0 && col < NUMBER_OF_COLS) {
            return true;
        }
        return false;
    }



}
