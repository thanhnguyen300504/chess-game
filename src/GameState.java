import java.util.HashSet;
import java.util.Set;

/**
 * GameState class contains properties of current game state:
 * - Board: the current Board
 * - currentPlayer: the current player's turn
 * -
 */
public class GameState {
    private Board board;
    private Player currentPlayer;

    /**
     * getter method for board
     * @return board
     */
    public Board getBoard() {
         return this.board;
    }

    /**
     * getter method for currentPlayer
     * @return currentPlayer
     */
    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    /**
     * return the set of legal moves that the current piece can make
     * @param row
     * @param col
     * @return set of Tile that PIECE can move to
     * @return empty set if there's no moves available or the Tile is empty
     */
    public Set<Tile> allLegalMoves(int row, int col) {
        Tile[][] tiles = this.getBoard().getTiles();
        Tile currentTile = tiles[row][col];
        if (!currentTile.isEmpty()) {
            PieceType currentPieceType = currentTile.getTopPiece().getPieceType();
            switch (currentPieceType) {
                case PAWN: return possibleMovesForPawns(row, col);
                default: new HashSet<>();
            }
        }
        return new HashSet<>();
    }

    /**
     * return the set of legal moves that the current PAWN can make
     * @param row
     * @param col
     * @return set of Tile that PAWN can move to
     */
    private Set<Tile> possibleMovesForPawns(int row, int col) {
        // Tile properties from the current coordinates (row, col)
        Tile currentTile = this.getBoard().getTiles()[row][col];
        Piece currentPiece = currentTile.getTopPiece();
        PieceType currentPieceType = currentPiece.getPieceType();

        // check if Tile is occupied by PAWN
        if (currentPieceType == PieceType.PAWN) {
            Colour currentPieceColour = currentPiece.getPieceColour();
            Set<Tile> allLegalMoves = new HashSet<>();

            /**
             * all directions that a PAWN could move
             * - forward-diagonal to capture piece
             * - one-step vertical to move
             */
            int[][] directions = {{-1, -1}, {1, -1}, {0, -1}};

            for (int i = 0; i < directions.length; i++) {
                int[] move = directions[i];
                int newRow;
                int newCol;

                // movement of WHITE Piece
                if (currentPiece.getPieceColour() == Colour.WHITE) {
                    newRow = row + move[0];
                    newCol = col + move[1];
                }

                // movement of BLACK Piece
                else {
                    newRow = row - move[0];
                    newCol = col - move[1];
                }
                Board currentBoard = this.getBoard();
                Tile[][] tiles = currentBoard.getTiles();
                Tile targetTile = tiles[newRow][newCol];

                // check if capturing is allowed
                if (!targetTile.isEmpty()) {
                    if ((i == 0 || i == 1) && targetTile.getTopPiece().getPieceColour() != currentPieceColour) {
                        allLegalMoves.add(targetTile);
                    }
                }

                // check if one-step vertical movement is allowed
                else if (targetTile.isEmpty() && i == 2) {
                    allLegalMoves.add(targetTile);
                }

            }
            return allLegalMoves;
        }
        return new HashSet<>();
    }

    /**
     * return the set of Tile that the current ROOK can make
     * @param row
     * @param col
     * @return set of Tile that ROOK can move to
     */
    private Set<Tile> possibleMovesForRook(int row, int col) {
        Tile currentTile = this.getBoard().getTiles()[row][col];
        Piece currentPiece = currentTile.getTopPiece();
        PieceType currentPieceType = currentPiece.getPieceType();
        if (currentPieceType != PieceType.ROOK) return new HashSet<>();
        Set<Tile> allLegalMoves = new HashSet<>();
        for (int i = 0; i < Board.NUMBER_OF_ROWS; i++) {
            for (int j = 0; j < Board.NUMBER_OF_COLS; j++) {
                if (i == row || j == col) {

                }
            }
        }
        return allLegalMoves;

    }
}
