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
    private Player currentPlayer = new Player(Colour.WHITE);
    boolean whitePawnMovesTwo = false;
    boolean blackPawnMovesTwo = false;
    boolean blackKingIsChecked = false;
    boolean whiteKingIsChecked = false;

    /**
     * getter method for board
     *
     * @return board
     */
    public Board getBoard() {
        return this.board;
    }

    /**
     * getter method for currentPlayer
     *
     * @return currentPlayer
     */
    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    /**
     * check if the game is over
     * @return true if any King is currently checked and has no available
     * legal moves to escape
     */
    public boolean isGameOver() {
        boolean availableWhiteMoves = false;
        boolean availableBlackMoves = false;
        for (int row = 0; row < Board.NUMBER_OF_ROWS; row++) {
            for (int col = 0; col < Board.NUMBER_OF_COLS; col++) {
                if (blackKingIsChecked) {
                    if (!this.getAllValidMoves(row, col).isEmpty()) {
                        availableBlackMoves = true;
                    }
                }
                else if (whiteKingIsChecked) {
                    if (!this.getAllValidMoves(row, col).isEmpty()) {
                        availableWhiteMoves = true;
                    }
                }
            }
        }
        if (!availableBlackMoves || !availableWhiteMoves) return true;
        return false;
    }

    /**
     * set Black KING or White King to being checked if the case happens
     */
    public void setCheckKing() {
        for (int row = 0; row < Board.NUMBER_OF_ROWS; row++) {
            for (int col = 0; col < Board.NUMBER_OF_COLS; col++) {
                Set<Tile> allLegalMoves = this.getAllValidMoves(row, col);
                for (Tile legalMove : allLegalMoves) {
                    if (!legalMove.isEmpty()) {
                        Piece currentPiece = legalMove.getTopPiece();
                        PieceType currentPieceType = currentPiece.getPieceType();
                        if (currentPieceType == PieceType.KING) {
                            switch (currentPiece.getPieceColour()) {
                                case BLACK: blackKingIsChecked = true;
                                case WHITE: whiteKingIsChecked = true;
                            }
                        }
                    }
                }
            }
        }
    }

    public void makeMove(int initRow, int initCol, int finRow, int finCol) {
        if (board.isWithinBoard(initRow, initCol)
                && board.isWithinBoard(finRow, finCol)) {
            Tile[][] tiles = board.getTiles();
            Tile targetTile = tiles[finRow][finCol];
            Tile currentTile = tiles[initRow][initCol];
            if (this.getAllValidMoves(initRow, initCol).contains(targetTile)) {
                Piece currentPiece = currentTile.getTopPiece();
                PieceType currentPieceType = currentPiece.getPieceType();
                if (currentPieceType == PieceType.PAWN && Math.abs(finCol - initCol) == 2) {
                    switch (currentPiece.getPieceColour()) {
                        case BLACK: blackPawnMovesTwo = true;
                        case WHITE: whitePawnMovesTwo = true;
                    }
                }
                tiles[finRow][finCol] = new Tile(finRow, finCol, currentTile.getTopPiece());
                tiles[initRow][initCol] = new Tile(initRow, initCol, null);
            }
        }
    }
    

    /**
     * return the set of legal moves that the current piece can make
     *
     * @param row
     * @param col
     * @return set of Tile that PIECE can move to
     * @return empty set if there's no moves available or the Tile is empty
     */
    public Set<Tile> getAllValidMoves(int row, int col) {
        Tile[][] tiles = board.getTiles();
        Tile currentTile = tiles[row][col];
        if (!currentTile.isEmpty()) {
            PieceType currentPieceType = currentTile.getTopPiece().getPieceType();
            switch (currentPieceType) {
                case PAWN:
                    return getMovesForPawn(row, col);
                case ROOK:
                    return getMovesForRook(row, col);
                case KNIGHT:
                    return getMovesForKnight(row, col);
                case BISHOP:
                    return getMovesForBishop(row, col);
                case QUEEN:
                    return getMovesForQueen(row, col);
                case KING:
                    return getMovesForKing(row, col);
                default:
                    new HashSet<>();
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
        if (!board.isWithinBoard(row, col)) return new HashSet<>();

        // Tile properties from the current coordinates (row, col)
        Tile currentTile = this.getBoard().getTiles()[row][col];
        Piece currentPiece = currentTile.getTopPiece();
        PieceType currentPieceType = currentPiece.getPieceType();

        Set<Tile> allLegalMoves = new HashSet<>();
        // check if Tile is occupied by PAWN
        if (currentPieceType == PieceType.PAWN) {
            Colour currentPieceColour = currentPiece.getPieceColour();
            Tile[][] tiles = board.getTiles();

            /**
             * all directions that a PAWN could move
             * - forward-diagonal to capture piece
             * - one-step vertical to move
             */
            int[][] directions = {{-1, -1}, {1, -1}, {0, -1}, {0, -2}};

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

                Tile targetTile = tiles[newRow][newCol];

                // check if capturing is allowed
                if (!targetTile.isEmpty()) {
                    if ((i == 0 || i == 1) && currentTile.isDifferentColour(targetTile)) {
                        allLegalMoves.add(targetTile);
                    }
                }

                // check if one-step vertical movement is allowed
                if (targetTile.isEmpty() && i == 2) {
                    allLegalMoves.add(targetTile);
                }

                if (targetTile.isEmpty() && i == 3) {
                    if (currentPieceColour == Colour.WHITE && !whitePawnMovesTwo) {
                        allLegalMoves.add(targetTile);
                    }
                    else if (currentPieceColour == Colour.BLACK && !blackPawnMovesTwo) {
                        allLegalMoves.add(targetTile);
                    }
                }

            }
        }
        return allLegalMoves;
    }

    /**
     * return the set of Tile that the current Knight can make
     * @param row
     * @param col
     * @return set of Tile that KNIGHT can move to
     */
    private Set<Tile> getMovesForKnight(int row, int col) {
        if (!board.isWithinBoard(row, col)) return new HashSet<>();

        Tile currentTile = board.getTiles()[row][col];
        if (currentTile.isEmpty()) return new HashSet<>();

        Piece currentPiece = currentTile.getTopPiece();
        PieceType currentPieceType = currentPiece.getPieceType();

        if (currentPieceType != PieceType.KNIGHT) return new HashSet<>();

        Tile[][] tiles = board.getTiles();
        Set<Tile> allLegalMoves = new HashSet<>();

        for (int i = - Board.NUMBER_OF_ROWS + 1; i < Board.NUMBER_OF_ROWS; i++) {
            for (int j = - Board.NUMBER_OF_COLS + 1; j < Board.NUMBER_OF_COLS; j++) {
                int newRow = row + i;
                int newCol = col + j;

                Tile targetTile = tiles[newRow][newCol];
                if (board.isWithinBoard(newRow, newCol)) {
                    int dRow = Math.abs(newRow - row);
                    int dCol = Math.abs(newCol - col);
                    if (dRow * dCol == 2) {
                        if (targetTile.isEmpty() || currentTile.isDifferentColour(targetTile)) {
                            allLegalMoves.add(targetTile);
                        }
                    }
                }
            }
        }

        return allLegalMoves;
    }

    /**
     * return the set of Tile that the current BISHOP can make
     * @param row
     * @param col
     * @return set of Tile that BISHOP can move to
     */
    private Set<Tile> getMovesForBishop(int row, int col) {
        if (!board.isWithinBoard(row, col)) return new HashSet<>();

        Tile currentTile = this.getBoard().getTiles()[row][col];

        if (currentTile.isEmpty()) return new HashSet<>();

        Piece currentPiece = currentTile.getTopPiece();
        PieceType currentPieceType = currentPiece.getPieceType();
        Tile[][] tiles = board.getTiles();


        Set<Tile> allLegalMoves = new HashSet<>();

        if (currentPieceType == PieceType.BISHOP || currentPieceType == PieceType.QUEEN) {
            for (int dRow = -1; dRow > -Board.NUMBER_OF_ROWS; dRow--) {
                for (int dCol = -1; dCol > -Board.NUMBER_OF_COLS; dCol--) {
                    int newRow = row + dRow;
                    int newCol = col + dCol;
                    if (board.isWithinBoard(newRow, newCol) && (dRow == dCol)) {
                        Tile targetTile = tiles[newRow][newCol];
                        if (targetTile.isEmpty()) {
                            allLegalMoves.add(targetTile);
                        } else if (targetTile.isDifferentColour(currentTile)) {
                            allLegalMoves.add(targetTile);
                            break;
                        } else {
                            break;
                        }
                    }
                }
            }

            for (int dRow = 1; dRow < Board.NUMBER_OF_ROWS; dRow++) {
                for (int dCol = 1; dCol < Board.NUMBER_OF_COLS; dCol++) {
                    int newRow = row + dRow;
                    int newCol = col + dCol;
                    if (board.isWithinBoard(newRow, newCol) && (dRow == dCol)) {
                        Tile targetTile = tiles[newRow][newCol];
                        if (targetTile.isEmpty()) {
                            allLegalMoves.add(targetTile);
                        } else if (targetTile.isDifferentColour(currentTile)) {
                            allLegalMoves.add(targetTile);
                            break;
                        } else {
                            break;
                        }
                    }
                }

            }
        }
        return allLegalMoves;
    }

    /**
     * return the set of Tile that the current ROOK can make
     * @param row
     * @param col
     * @return set of Tile that ROOK can move to
     */
    private Set<Tile> getMovesForRook(int row, int col) {
        if (!board.isWithinBoard(row, col)) return new HashSet<>();

        Tile currentTile = board.getTiles()[row][col];

        if (currentTile.isEmpty()) return new HashSet<>();

        Piece currentPiece = currentTile.getTopPiece();
        PieceType currentPieceType = currentPiece.getPieceType();
        Tile[][] tiles = board.getTiles();


        Set<Tile> allLegalMoves = new HashSet<>();

        if (currentPieceType == PieceType.ROOK || currentPieceType == PieceType.QUEEN) {
            for (int dCol = -Board.NUMBER_OF_COLS + 1; dCol < Board.NUMBER_OF_COLS; dCol++) {
                int newCol = col + dCol;
                if (board.isWithinBoard(row, newCol)) {
                    Tile targetTile = tiles[row][newCol];
                    if (targetTile.isEmpty()) {
                        allLegalMoves.add(targetTile);
                    } else if (currentTile.isDifferentColour(targetTile)) {
                        allLegalMoves.add(targetTile);
                        break;
                    } else {
                        break;
                    }
                }
            }

            for (int dRow = -Board.NUMBER_OF_ROWS + 1; dRow < Board.NUMBER_OF_ROWS; dRow++) {
                int newRow = row + dRow;
                if (board.isWithinBoard(newRow, col)) {
                    Tile targetTile = tiles[newRow][col];
                    if (targetTile.isEmpty()) {
                        allLegalMoves.add(targetTile);
                    } else if (currentTile.isDifferentColour(targetTile)) {
                        allLegalMoves.add(targetTile);
                        break;
                    } else {
                        break;
                    }
                }
            }
        }

        return allLegalMoves;
    }

    /**
     * return the set of Tile that the current QUEEN can make
     * @param row
     * @param col
     * @return set of Tile that QUEEN can move to
     */
    private Set<Tile> getMovesForQueen(int row, int col) {
        if (!board.isWithinBoard(row, col)) return new HashSet<>();

        Tile currentTile = board.getTiles()[row][col];

        if (currentTile.isEmpty()) return new HashSet<>();

        Piece currentPiece = currentTile.getTopPiece();
        PieceType currentPieceType = currentPiece.getPieceType();



        Set<Tile> allLegalMoves = new HashSet<>();

        if (currentPieceType == PieceType.QUEEN) {
            allLegalMoves.addAll(this.getMovesForBishop(row, col));
            allLegalMoves.addAll(this.getMovesForRook(row, col));
        }

        return allLegalMoves;

    }

    /**
     * return the set of Tile that the current KING can make
     * @param row
     * @param col
     * @return set of Tile that KING can move to
     */
    private Set<Tile> getMovesForKing(int row, int col) {
        if (!board.isWithinBoard(row, col)) return new HashSet<>();

        Tile currentTile = board.getTiles()[row][col];

        if (currentTile.isEmpty()) return new HashSet<>();

        Piece currentPiece = currentTile.getTopPiece();
        PieceType currentPieceType = currentPiece.getPieceType();
        Tile[][] tiles = board.getTiles();


        Set<Tile> allLegalMoves = new HashSet<>();

        if (currentPieceType == PieceType.KING) {
            for (int dRow = -1; dRow <= 1; dRow++) {
                for (int dCol = -1; dCol <= 1; dCol++) {
                    int newRow = row + dRow;
                    int newCol = col + dCol;
                    if (!(dRow == 0 && dCol == 0) && board.isWithinBoard(newRow, newCol)) {
                        Tile targetTile = tiles[newRow][newCol];
                        if (targetTile.isEmpty() || currentTile.isDifferentColour(targetTile)) {
                            allLegalMoves.add(targetTile);
                        }

                    }
                }
            }
        }

        return allLegalMoves;
    }




}



