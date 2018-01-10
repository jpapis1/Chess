// Autor Jacek Papis 2018
package com.company;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import static java.lang.Math.abs;

class Board implements Serializable, Cloneable {
    int side;
    int index;
    Piece[][] board;
    ArrayList<Piece[][]> history;
    boolean backed;
    int sideWithPiece;
    Board(int sideWithPiece) {
        board = new Piece[8][8];
        history = new ArrayList<>();
        backed = false;
        index = 0;
        this.sideWithPiece = sideWithPiece;
    }
    // dodaje do planszy
    void addToBoard (int x, int y, String type, int side) {
        board[x][y] = new Piece(type,side);
    }
    // przesuwa pionek
    void movePiece(int x, int y, int x2, int y2) throws IllegalArgumentException {
        if(ableToMove(x,y,x2,y2)||ableToCapture(x,y,x2,y2)) {
            String type = board[x][y].getType();
            int side = board[x][y].getSide();
            board[x][y] = null;
            board[x2][y2] = new Piece(type, side);
            changeSide();
        }
        else{
            throw new IllegalArgumentException("ERROR: Cannot move piece");
        }
    }
    // klonuje planszę
    Piece[][] cloneBoard() {
            Board b = new Board(sideWithPiece);
            for(int i = 0; i<8; i++) {
                for(int j = 0; j<8; j++) {
                    try {
                        b.board[i][j] = this.board[i][j].getClone();
                    } catch (NullPointerException e) {
                        b.board[i][j] = null;
                    }
                }
            }

            return b.board;
    }
    // sprawdza czy jest możliwość bicia
    boolean ableToCapture(int x, int y, int x2, int y2) {
        if(board[x2][y2]!=null) {
            if (board[x][y].getSide() != board[x2][y2].getSide()) {
                switch (board[x][y].getType()) {
                    case "king":
                        if (abs(x - x2) == 1 && abs(y - y2) == 1) {
                            return true;
                        } else if (abs(x - x2) == 1 && y == y2) {
                            return true;
                        } else if (abs(y - y2) == 1 && x == x2) {
                            return true;
                        } else {
                            return false;
                        }
                    case "bishop":
                        if(isAcross(x,y,x2,y2)) {
                            if(x>x2) {
                                if(y>y2) {
                                    return cleanAcross(x,y,x2+1,y2+1);
                                }
                                else {
                                    return cleanAcross(x,y,x2+1,y2-1);
                                }
                            } else { //x<x2
                                if(y>y2) {
                                    return cleanAcross(x,y,x2-1,y2+1);
                                }
                                else {
                                    return cleanAcross(x,y,x2-1,y2-1);
                                }
                            }
                        }
                    case "pawn":
                        if(this.side==0) {
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
        }
        return false;
    }
    // sprawdza czy wygrana
    int checkWin(int sideWithPiece) {
        if(!pieceExists("pawn",0)&&sideWithPiece==0) {
            return 0;
        } else if(!pieceExists("pawn",1)&&sideWithPiece==1) {
            return 1;
        }
        for(int i = 0; i<8;i++) {
            for(int j = 0; j<8; j++) {
                if(board[i][j]!=null) {
                    if (j == 0&&board[i][j].getType().equals("pawn")&&board[i][j].getSide()==0) {
                        return 1;
                    }
                    if (j == 7&&board[i][j].getType().equals("pawn")&&board[i][j].getSide()==1) {
                        return 0;
                    }
                }
            }
        }
        return 2;
    }
    // sprawdza możliwość ruchu
    boolean ableToMove (int x, int y, int x2, int y2) {
        if(acceptableArg(x,y)&&acceptableArg(x2,y2)&&((x!=x2)||(y!=y2))&&board[x2][y2]==null) {
                switch (board[x][y].getType()) {
                    case "king":
                        if (abs(x - x2) == 1 && abs(y - y2) == 1) {
                            return true;
                        } else if (abs(x - x2) == 1 && y == y2) {
                            return true;
                        } else if (abs(y - y2) == 1 && x == x2) {
                            return true;
                        } else {
                            return false;
                        }
                    case "bishop":
                        return isAcross(x, y, x2, y2) && cleanAcross(x, y, x2, y2);
                    case "pawn":
                        if(this.side==0) {
                            return y - 1 == y2 && x == x2;
                        } else {
                            return y + 1 == y2 && x == x2;
                        }
                }
            }
        else {
            return false;
        }
        return false;
    }
    private boolean acceptableArg(int x, int y) {
        return (x>=0&&x<=7)&&(y>=0&&y<=7);
    }
    // sprawdza czy [x,y][x2,y2] są po przekątnej
    private boolean isAcross(int x, int y, int x2, int y2) {
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
    // sprawdza czy po przekątnej nie ma pionków
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
        for(int i = 0; i<8; i++) {
            System.out.print(i + "  ");
            for(int j = 0; j<8; j++) {
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
    // sprawdza czy pionek istnieje
    boolean pieceExists(String piece, int side) {
        if(side==0) {
            for(int i = 0; i<8;i++) {
                for (int j = 0; j < 8; j++) {
                    if (board[i][j] != null) {
                        if(board[i][j].getSide()==0&&board[i][j].getType().equals(piece)) {
                            return true;
                        }
                    }
                }
            }
        } else {
            for(int i = 0; i<8;i++) {
                for (int j = 0; j < 8; j++) {
                    if (board[i][j] != null) {
                        if(board[i][j].getSide()==1&&board[i][j].getType().equals(piece)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    // zapisuje grę
    void saveBoard() throws IOException{
        JFileChooser fileChooser = new JFileChooser("savegames/");
        String name = "";
        if (fileChooser.showSaveDialog(new JFrame()) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            name = file.getPath();
            // save to file
        }
                FileOutputStream fStream = new FileOutputStream(name);
                ObjectOutputStream oStream = new ObjectOutputStream(fStream);
                oStream.writeObject(this);
                fStream.close();

    }
    // pobiera grę
    static Board loadBoard() throws IOException, ClassNotFoundException {
        JFileChooser fileChooser = new JFileChooser("savegames/");
        String name = "";
        if (fileChooser.showOpenDialog(new JFrame()) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            name = file.getPath();
            // load from file
        }
        ObjectInputStream iStream = new ObjectInputStream(new FileInputStream(name));
        Board b = (Board)iStream.readObject();
        iStream.close();
        return b;

    }
    Piece getPiece (int x, int y) {
        return board[x][y];
    }
    // pobiera mapę
    void loadBoardFromTextFile(String name) throws IOException {
        String line;
        FileReader fileReader = new FileReader(name);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        int i = 0;
        int j = 0;
        int boardNum = 0;
        while ((line = bufferedReader.readLine()) != null) {
            while (j < line.length()) {
                String key = Character.toString(line.charAt(j)) + Character.toString(line.charAt(j+1));
                switch (key) {
                    case "[]":
                        break;
                    case "K0":
                        this.addToBoard(boardNum, i, "king", 0);
                        break;
                    case "K1":
                        this.addToBoard(boardNum, i, "king", 1);
                        break;
                    case "B0":
                        this.addToBoard(boardNum, i, "bishop", 0);
                        break;
                    case "B1":
                        this.addToBoard(boardNum, i, "bishop", 1);
                        break;
                    case "P0":
                        this.addToBoard(boardNum, i, "pawn", 0);
                        break;
                    case "P1":
                        this.addToBoard(boardNum, i, "pawn", 1);
                        break;
                    default:
                        throw new IOException();
                }
                j = j+2;
                boardNum++;
            }
            j=0;
            boardNum=0;
            i++;
        }
        bufferedReader.close();
    }
    // zmienia stronę konfliktu
    void changeSide() {
        if(this.side==0) {
            this.side = 1;
        } else {
            this.side = 0;
        }
    }
}
