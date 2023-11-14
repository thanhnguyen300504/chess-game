package side;

import side.Colour;

public class Piece {

    private Colour pieceColour;
    private PieceType pieceType;
    Piece(PieceType pieceType, Colour pieceColour) {
        this.pieceType = pieceType;
        this.pieceColour = pieceColour;
    }
}
