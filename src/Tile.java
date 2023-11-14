/**
 * Tile class contains properties of a tile:
 * - row: the row of the Board that Tile is currently at
 * - col: the col of the Board that Tile is currently at
 * - topPiece: the Piece occupying the Tile (null if the Tile is empty)
 */
public class Tile {
    private int row;
    private int col;
    private Piece topPiece;

    /**
     * Tile constructor: create a new instance of Tile class
     * @param row
     * @param col
     * @param topPiece
     */
    public Tile(int row, int col, Piece topPiece) {
        this.row = row;
        this.col = col;
        this.topPiece = topPiece;
    }

    /**
     * getter method for the row coordinate of Tile
     * @return row
     */
    public int getRow() {
        return this.row;
    }

    /**
     * getter method for the column coordinate of Tile
     * @return col
     */
    public int getCol() {
        return this.col;
    }

    /**
     * getter method for the Piece occupying the Tile
     * @return
     */
    public Piece getTopPiece() {
        return this.topPiece;
    }

    /**
     * check if the Tile is currently empty
     * @return true if the tile is empty, false otherwise
     */
    public boolean isEmpty() {
        if (this.getTopPiece() == null) {
            return true;
        }
        return false;
    }

    /**
     * check if two Tile have different colours
     * @param otherTile
     * @return true if different, false otherwise
     */
    public boolean isDifferentColour(Tile otherTile) {
        if (!this.isEmpty() && !otherTile.isEmpty()) {
            Colour currentPieceColour = this.getTopPiece().getPieceColour();
            Colour otherPiececolour = otherTile.getTopPiece().getPieceColour();
            if (currentPieceColour != otherPiececolour) {
                return true;
            }
            return false;
        }
        return false;
    }






}
