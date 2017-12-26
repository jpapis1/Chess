package com.company;
import static java.lang.Math.abs;

class Board {
    private Piece[][] board;
    Board() {
        board = new Piece[10][10];
    }
    void addToBoard (int x, int y, String type, int side) {
        board[x][y] = new Piece(type,side);
    }
    boolean ableToMove (int x, int y, int x2, int y2) {
        if(acceptableArg(x,y)&&acceptableArg(x2,y2)) {
            switch (board[x][y].getType()) {
                case "king":
                    if ((abs(x - x2) == 1) && (y == y2) && board[x2][y2] == null) { //vertically
                        return true;
                    } else if ((abs(y - y2) == 1) && (x == x2) && board[x2][y2] == null) { //horizontally
                        return true;
                    } else {
                        return false;
                    }
                case "bishop":
                    return isAcross(x, y, x2, y2) && cleanAcross(x, y, x2, y2);
                case "knight":
                    if(board[x2][y2] == null) {
                        if(y - 2 ==y2) {
                            if (x + 1 == x2 || x - 1 == x2) {
                                return true;
                            }
                            return false;
                        } else if (y + 2 == y2) {
                            if (x + 1 == x2 || x - 1 == x2) {
                                return true;
                            }
                            return false;
                        } else if (y - 1 == y2) {
                            if (x - 2 == x2 || x + 2 == x2) {
                                return true;
                            }
                            return false;

                        } else if (y + 1 == y2) {
                            if (x - 2 == x2 || x + 2 == x2) {
                                return true;
                            }
                            return false;
                        } else {
                            return false;
                        }
                    }
                    break;
            }
        } else {
            throw new IllegalArgumentException("Wrong arguments!");
        }
        return false;
    }
    private boolean acceptableArg(int x, int y) {
        return x>0&&x<=9&&y>0&&y<=9;
    }
    private boolean isAcross(int x, int y, int x2, int y2) {
        int tempx = x;
        int tempy = y;
        while (tempx!=9&&tempy!=9) {
            if(tempx==x2&&tempy==y2) {
                return true;
            }
            tempy++;
            tempx++;
        }
        tempx = x;
        tempy = y;
        while (tempx!=9&&tempy!=0) {
            if(tempx==x2&&tempy==y2) {
                return true;
            }
            tempy--;
            tempx++;
        }
        tempx = x;
        tempy = y;
        while (tempx!=0&&tempy!=0) {
            if(tempx==x2&&tempy==y2) {
                return true;
            }
            tempy--;
            tempx--;
        }
        tempx = x;
        tempy = y;
        while (tempx!=0&&tempy!=9) {
            if(tempx==x2&&tempy==y2) {
                return true;
            }
            tempy++;
            tempx--;
        }
        return false;
    }
    private boolean cleanAcross(int x, int y, int x2, int y2) {
        if(x>x2) { //left
            if(y>y2) { //down
                while (x!=x2&&y!=y2) {
                    x--;
                    y--;
                    if(this.board[x][y]!=null) {
                        return false;
                    }
                }
            } else { //up
                while (x!=x2&&y!=y2) {
                    x--;
                    y++;
                    if(this.board[x][y]!=null) {
                        return false;
                    }
                }
            }
        } else { // right
            if(y>y2) { //down
                while (x!=x2&&y!=y2) {
                    x++;
                    y--;
                    if(this.board[x][y]!=null) {
                        return false;
                    }
                }
            } else { //up
                while (x!=x2&&y!=y2) {
                    x++;
                    y++;
                    if(this.board[x][y]!=null) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    void print() {
        System.out.println("    0   1   2   3   4   5   6   7   8   9  ");
        for(int i = 0; i<10; i++) {
            System.out.print(i + "  ");
            for(int j = 0; j<10; j++) {
                String c;
                try {
                    c = Piece.getShortNameFromPieceName(board[j][i].getType());
                    System.out.print("[" + c + "] ");
                    } catch (NullPointerException e) {
                        System.out.print("[ ] ");
                    }
            }
            System.out.println();
        }
    }
    Piece getPiece (int x, int y) {
        return board[x][y];
    }

}
