package chess.pieceTypes;
import chess.mainComponents.Board;
import chess.mainComponents.Piece;

public class Bishop extends Piece implements AcrossPiece {
    public Bishop(int side) { super(side); }

    @Override
    public String getType() {
        return "bishop";
    }

    @Override
    public boolean ableToCapture(int x, int y, int x2, int y2, Board board) {
        if(board.board[x2][y2]!=null) {
            if (board.board[x][y].getSide() != board.board[x2][y2].getSide()) {
                if(isAcross(x,y,x2,y2)) {
                    if(x>x2) {
                        if(y>y2) {
                            return cleanAcross(x,y,x2+1,y2+1, board);
                        }
                        else {
                            return cleanAcross(x,y,x2+1,y2-1, board);
                        }
                    } else { //x<x2
                        if(y>y2) {
                            return cleanAcross(x,y,x2-1,y2+1, board);
                        }
                        else {
                            return cleanAcross(x,y,x2-1,y2-1, board);
                        }
                    }
                }
            }
        }

        return false;
    }

    @Override
    public boolean ableToMove(int x, int y, int x2, int y2, Board board) {
        return isAcross(x, y, x2, y2) && cleanAcross(x, y, x2, y2, board);
    }


    public boolean isAcross(int x, int y, int x2, int y2) {
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

    public boolean cleanAcross(int x, int y, int x2, int y2, Board board) {
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
