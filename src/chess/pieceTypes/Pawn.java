package chess.pieceTypes;
import chess.mainComponents.Board;
import chess.mainComponents.Piece;

public class Pawn extends Piece{
    public Pawn(int side) {
        super(side);
    }

    @Override
    public String getType() {
        return "pawn";
    }
    @Override
    public boolean ableToCapture(int x, int y, int x2, int y2, Board board) {
        if(board.board[x2][y2]!=null) {
            if (board.board[x][y].getSide() != board.board[x2][y2].getSide()) {
                if(board.side==0) {
                    if(y-1==y2&&x-1==x2) {
                        return true;
                    } else if(y-1==y2&&x+1==x2) {
                        return true;
                    }
                } else {
                    if(y+1==y2&&x-1==x2) {
                        return true;
                    } else if(y+1==y2&&x+1==x2) {
                        return true;
                    }
                }
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean ableToMove(int x, int y, int x2, int y2, Board board) {
        if (((x != x2) || (y != y2)) && board.board[x2][y2] == null) {
            if(board.side==0) {
                return y - 1 == y2 && x == x2;
            } else {
                return y + 1 == y2 && x == x2;
            }
        }
        return false;
    }
}
