/**
 * Piece class contains properties of pieces:
 * - pieceColour: colour of piece (BLACK or WHITE)
 * - pieceType: type of piece (ROOK, PAWN, BISHOP, QUEEN, KING, KNIGHT)
 */
public class Piece {
    private final Colour pieceColour;
    private final PieceType pieceType;

    /**
     * Piece constructor: create a new instance of Piece class
     * @param pieceType
     * @param pieceColour
     */
    public Piece(PieceType pieceType, Colour pieceColour) {
        this.pieceType = pieceType;
        this.pieceColour = pieceColour;
    }

    /**
     * getter method for pieceColour
     * @return pieceColour
     */
    public Colour getPieceColour() {
        return this.pieceColour;
    }

    /**
     * getter method for pieceType
     * @return pieceType
     */
    public PieceType getPieceType() {
        return this.pieceType;
    }

    /**
     * convert each piece to corresponding strength value (according to chess.com)
     * @return
     */
    public int getPieceValue() {
        switch (this.pieceType) {
            case PAWN: return 1;
            case KNIGHT, BISHOP: return 3;
            case ROOK: return 5;
            case QUEEN: return 9;
            default: return 0;
        }
    }


}
