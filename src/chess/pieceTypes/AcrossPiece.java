// Autor Jacek Papis 2018
package chess.pieceTypes;
import chess.mainComponents.Board;

public interface AcrossPiece {
    default boolean isAcross(int x, int y, int x2, int y2) {
        int tempx = x;
        int tempy = y;
        while (tempx!=8&&tempy!=8) {
            if(tempx==x2&&tempy==y2) {
                return true;
            }
            tempy++;
            tempx++;
        }
        tempx = x;
        tempy = y;
        while (tempx!=8&&tempy!=-1) {
            if(tempx==x2&&tempy==y2) {
                return true;
            }
            tempy--;
            tempx++;
        }
        tempx = x;
        tempy = y;
        while (tempx!=-1&&tempy!=-1) {
            if(tempx==x2&&tempy==y2) {
                return true;
            }
            tempy--;
            tempx--;
        }
        tempx = x;
        tempy = y;
        while (tempx!=-1&&tempy!=8) {
            if(tempx==x2&&tempy==y2) {
                return true;
            }
            tempy++;
            tempx--;
        }
        return false;
    }

    default boolean cleanAcross(int x, int y, int x2, int y2, Board board) {
        if(x>x2) { //left
            if(y>y2) { //down
                while (x!=x2&&y!=y2) {
                    x--;
                    y--;
                    if(board.board[x][y]!=null) {
                        return false;
                    }
                }
            } else { //up
                while (x!=x2&&y!=y2) {
                    x--;
                    y++;
                    if(board.board[x][y]!=null) {
                        return false;
                    }
                }
            }
        } else { // right
            if(y>y2) { //down
                while (x!=x2&&y!=y2) {
                    x++;
                    y--;
                    if(board.board[x][y]!=null) {
                        return false;
                    }
                }
            } else { //up
                while (x!=x2&&y!=y2) {
                    x++;
                    y++;
                    if(board.board[x][y]!=null) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
