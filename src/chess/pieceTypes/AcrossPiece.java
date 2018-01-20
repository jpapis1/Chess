package chess.pieceTypes;

import chess.mainComponents.Board;
import chess.mainComponents.Piece;

public interface AcrossPiece {
    public boolean isAcross(int x, int y, int x2, int y2);
    public boolean cleanAcross(int x, int y, int x2, int y2, Board board);
}
