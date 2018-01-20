// Autor Jacek Papis 2018
package chess.mainComponents;
import chess.pieceTypes.Bishop;
import chess.pieceTypes.King;
import chess.pieceTypes.Pawn;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import static java.lang.Math.abs;

public class Board implements Serializable, Cloneable {
    public int side;
    int index;
    public Piece[][] board;
    ArrayList<Piece[][]> history;
    int sideWithPiece;
    public Board(int sideWithPiece) {
        board = new Piece[8][8];
        history = new ArrayList<>();
        index = 0;
        this.sideWithPiece = sideWithPiece;
    }
    // dodaje do planszy
    // updated
    void addToBoard (int x, int y, String type, int side) {
        switch (type) {
            case "king":
                board[x][y] = new King(side);
                break;
            case "bishop":
                board[x][y] = new Bishop(side);
                break;
            case "pawn":
                board[x][y] = new Pawn(side);
                break;
        }
    }
    // przesuwa pionek
    // updated
    void movePiece(int x, int y, int x2, int y2) throws IllegalArgumentException {
        if(board[x][y].ableToMove(x,y,x2,y2,this)||board[x][y].ableToCapture(x,y,x2,y2,this)) {
            String type = board[x][y].getType();
            int side = board[x][y].getSide();
            board[x][y] = null;
            addToBoard(x2,y2,type,side);
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
