package chess.pieceTypes;
import chess.mainComponents.Piece;
import chess.mainComponents.Board;
import static java.lang.Math.abs;

public class King extends Piece {
    public King(int side) {
        super(side);
    }

    @Override
    public String getType() {
        return "king";
    }
   @Override
   public boolean ableToCapture(int x, int y, int x2, int y2, Board board) {
       if(board.board[x2][y2]!=null) {
           if (board.board[x][y].getSide() != board.board[x2][y2].getSide()) {
               return checkVars(x,y,x2,y2);
           }
       }
       return false;
    }

    @Override
    public boolean ableToMove(int x, int y, int x2, int y2, Board board) {
        if (((x != x2) || (y != y2)) && board.board[x2][y2] == null) {
            return checkVars(x,y,x2,y2);
        }
        return false;
    }
    static boolean checkVars(int x, int y, int x2, int y2){
        if (abs(x - x2) == 1 && abs(y - y2) == 1) {
            return true;
        } else if (abs(x - x2) == 1 && y == y2) {
            return true;
        } else if (abs(y - y2) == 1 && x == x2) {
            return true;
        } else {
            return false;
        }
    }
}
