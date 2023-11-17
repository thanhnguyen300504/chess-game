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
        Tile[][] tiles = board.getTiles();
        Tile currentTile = tiles[row][col];
        if (!currentTile.isEmpty()) {
            PieceType currentPieceType = currentTile.getTopPiece().getPieceType();
            switch (currentPieceType) {
                case PAWN: return getMovesForPawn(row, col);
                case ROOK: return getMovesForRook(row, col);
                case KNIGHT: return getMovesForKnight(row, col);
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
    private Set<Tile> getMovesForPawn(int row, int col) {
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
                    Colour targetTileColour = targetTile.getTopPiece().getPieceColour();
                    if ((i == 0 || i == 1) && targetTileColour != currentPieceColour) {
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
    private Set<Tile> getMovesForRook(int row, int col) {

        Tile currentTile = this.getBoard().getTiles()[row][col];
        Piece currentPiece = currentTile.getTopPiece();
        PieceType currentPieceType = currentPiece.getPieceType();

        if (currentPieceType != PieceType.ROOK) return new HashSet<>();

        Set<Tile> allLegalMoves = new HashSet<>();
        boolean firstRowCapture = false;
        boolean firstColCapture = false;

        for (int j = - Board.NUMBER_OF_COLS + 1; j < Board.NUMBER_OF_COLS; j++) {
            int newCol =  col + j;
            if (board.isWithinBoard(row, newCol)) {
                Tile targetTile = board.getTiles()[row][newCol];
                if (targetTile.isEmpty()) {
                    allLegalMoves.add(targetTile);
                }
                else if (currentTile.isDifferentColour(targetTile) && !firstColCapture) {
                    allLegalMoves.add(targetTile);
                    firstColCapture = true;
                }
            }
        }

        for (int i = - Board.NUMBER_OF_ROWS + 1; i < Board.NUMBER_OF_ROWS; i++) {
            int newRow = row + i;
            if (board.isWithinBoard(newRow, col)) {
                Tile targetTile = board.getTiles()[newRow][col];
                if (targetTile.isEmpty()) {
                    allLegalMoves.add(targetTile);
                }
                else if (currentTile.isDifferentColour(targetTile) && !firstRowCapture) {
                    allLegalMoves.add(targetTile);
                    firstRowCapture = true;
                }
            }
        }
        return allLegalMoves;

    }

    private Set<Tile> getMovesForKnight(int row, int col) {
        Tile currentTile = this.getBoard().getTiles()[row][col];
        Piece currentPiece = currentTile.getTopPiece();
        PieceType currentPieceType = currentPiece.getPieceType();
        if (currentPieceType != PieceType.KNIGHT) return new HashSet<>();
        Set<Tile> allLegalMoves = new HashSet<>();

        for (int i = - Board.NUMBER_OF_ROWS + 1; i < Board.NUMBER_OF_ROWS; i++) {
            for (int j = - Board.NUMBER_OF_COLS + 1; j < Board.NUMBER_OF_COLS; j++) {
                int newRow = row + i;
                int newCol = col + j;
                Tile[][] tiles = this.getBoard().getTiles();

                Tile targetTile = tiles[newRow][newCol];
                if (board.isWithinBoard(newRow, newCol) && board.isWithinBoard(row, col)) {
                    int dRow = Math.abs(newRow - row);
                    int dCol = Math.abs(newCol - col);
                    if (dRow * dCol == 2) {
                        if (targetTile.isEmpty()) {
                            allLegalMoves.add(targetTile);
                        }
                        else if (currentTile.isDifferentColour(targetTile)) {
                            allLegalMoves.add(targetTile);
                        }
                    }
                }
            }
        }

        return allLegalMoves;
    }

    private Set<Tile> getMovesForBishop(int row, int col) {
        Tile currentTile = this.getBoard().getTiles()[row][col];
        Piece currentPiece = currentTile.getTopPiece();
        PieceType currentPieceType = currentPiece.getPieceType();
        if (currentPieceType != PieceType.BISHOP) return new HashSet<>();
        Set<Tile> allLegalMoves = new HashSet<>();






        return allLegalMoves;
    }

}
