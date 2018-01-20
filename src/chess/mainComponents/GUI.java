// Autor Jacek Papis 2018
package chess.mainComponents;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
class GUI extends JFrame {
    Board board;
    JButton buttons[][] = new JButton[8][8];
    JPanel controlPanel = new JPanel();
    JPanel jBoard = new JPanel();
    JButton helpButton = new JButton("Help");
    JButton undoButton = new JButton("Undo");
    JButton redoButton = new JButton("Redo");
    JButton saveButton = new JButton("Save");
    JButton loadButton = new JButton("Load");
    JLabel infoLabel = new JLabel("Info Label");
    boolean clicked = false;
    boolean created = false;
    static int sideWithPiece;
    GUI(int side) {
        board = new Board(sideWithPiece);
        sideWithPiece = side;
        board.side = side;
        setTitle("Bishop & King by Jacek Papis");
        /*
        board.addToBoard(2,2,"king",0);
        board.addToBoard(3,2,"pawn",0);
        board.addToBoard(7,3,"bishop",1);
        board.addToBoard(7,6,"king",1);
        */
        try {
            String fileName;
            if(side==0) {
                fileName = "maps/whiteMap.txt";
            } else {
                fileName = "maps/blackMap.txt";
            }
            board.loadBoardFromTextFile(fileName);
            infoLabel.setText("Board has been loaded");
        } catch (IOException e) {
            infoLabel.setText("Cannot load the board!");
        }
        board.history.add(board.cloneBoard()); // added start position to history
        Container container = getContentPane();
        container.setLayout(new GridLayout(1,2));
        container.add(jBoard);
        container.add(controlPanel);
        infoLabel.setHorizontalAlignment((int)CENTER_ALIGNMENT);
        controlPanel.setLayout(new GridLayout(8,1));
        helpButton.addActionListener(new HintAction());
        redoButton.addActionListener(new RedoAction());
        undoButton.addActionListener(new UndoAction());
        saveButton.addActionListener(new SaveAction());
        loadButton.addActionListener(new LoadAction());
        controlPanel.add(infoLabel);
        controlPanel.add(undoButton);
        controlPanel.add(redoButton);
        controlPanel.add(saveButton);
        controlPanel.add(loadButton);
        controlPanel.add(helpButton);
        jBoard.setLayout(new GridLayout(8,8));

        undoButton.setEnabled(false);
        redoButton.setEnabled(false);
        saveButton.setEnabled(false);
        updateButtons();
        setSize(1000,600);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }
    // uaktualnia przyciski
    void updateButtons() {
        //jBoard = new JPanel();
        for(int i = 0; i<8; i++) {
            for(int j = 0; j<8; j++) {
                if(board.getPiece(j,i) == null) {
                    if(!created) {
                        buttons[j][i] = new JButton();
                        buttons[j][i].addActionListener(new BoardAction(j, i));
                    }
                    buttons[j][i].setIcon(null);
                } else {
                    if(!created) {
                        buttons[j][i] = new JButton();
                        buttons[j][i].addActionListener(new BoardAction(j, i));
                    }
                    switch (board.getPiece(j,i).getType()) {
                        case "king":
                            if(board.getPiece(j,i).getSide()==1) {
                                buttons[j][i].setIcon(new ImageIcon("images/kingBlack.png"));
                            } else {
                                buttons[j][i].setIcon(new ImageIcon("images/kingWhite.png"));
                            }

                            break;
                        case "pawn":
                            if(board.getPiece(j,i).getSide()==1) {
                                buttons[j][i].setIcon(new ImageIcon("images/pawnBlack.png"));
                            } else {
                                buttons[j][i].setIcon(new ImageIcon("images/pawnWhite.png"));
                            }
                            break;
                        case "bishop":
                            if(board.getPiece(j,i).getSide()==1) {
                                buttons[j][i].setIcon(new ImageIcon("images/bishopBlack.png"));
                            } else {
                                buttons[j][i].setIcon(new ImageIcon("images/bishopWhite.png"));
                            }
                            break;
                        default:
                            buttons[j][i].setIcon(null);
                            break;
                    }

                }
                if(!created) {
                    jBoard.add(buttons[j][i]);
                }


            }
        }
        created = true;
    }
    String getSideName() {
        if(board.side==0) {
            return "White";
        } else {
            return "Black";
        }
    }
    class BoardAction implements ActionListener {
        int x, y;
        BoardAction(int x, int y) {
            this.x = x;
            this.y = y;

        }
        // kiedy przycisk pionkowy wciśnięty
        public void actionPerformed(ActionEvent e) {
            if(buttons[x][y].getBackground() != Color.GREEN) {
                if (clicked == false) {
                    if (buttons[x][y].getIcon() != null&&board.board[x][y].getSide()==board.side) {
                        buttons[x][y].setOpaque(true);
                        buttons[x][y].setBackground(Color.GREEN);
                        clicked = true;
                    }
                } else {
                    int tempX = 0;
                    int tempY = 0;
                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            if (buttons[i][j].getBackground() == Color.GREEN) {
                                tempX = i;
                                tempY = j;
                            }
                        }
                    }
                    if (board.board[tempX][tempY].ableToMove(tempX, tempY, x, y,board)||board.board[tempX][tempY].ableToCapture(tempX,tempY,x,y,board)) {
                        buttons[tempX][tempY].setBackground(null);
                        buttons[tempX][tempY].setIcon(null);
                        for(int i = board.history.size()-1; i>board.index; i--) {
                            board.history.remove(i);
                        }
                        board.index++;
                        board.movePiece(tempX, tempY, x, y);
                        board.history.add(board.cloneBoard()); // adding board to history
                        saveButton.setEnabled(true);
                        if(board.history.size()>board.index) {
                            undoButton.setEnabled(true);
                        }
                        if(board.history.size()-1==board.index) {
                            redoButton.setEnabled(false);
                        }
                        updateButtons();
                        infoLabel.setText("Move/Capture successful! " + getSideName() + " turn.");
                        cleanBackground();
                        checkWin();


                    }
                    // else if ableToCapture
                    else {
                        infoLabel.setText("Unable to move to this location!");
                    }
                }
            } else {
                infoLabel.setText("Unclick successful! " + getSideName() + " turn.");
                cleanBackground();
            }
        }

    }
    // sprawdza wygraną
    void checkWin() {
        if(board.checkWin(sideWithPiece)==0) {
            infoLabel.setText("Black wins!");
            blockButtons();
        } else if(board.checkWin(sideWithPiece)==1) {
            infoLabel.setText("White wins!");
            blockButtons();
        } else {
            unblockButtons();
        }
    }
    // przycisk help
    class HintAction implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            JFrame hintWindow = new JFrame("Help");
            hintWindow.setLayout(new GridLayout(1,1));
            JLabel label = new JLabel("<html><h1>Król i Goniec</h1><h2>Autor: Jacek Papis 2018</h2><p>" +
                    "Zadaniem strony z pionkiem jest dotarcie do linii przejścia. Zadaniem strony przeciwnej - zbicie wszystkich pionków przeciwnika." +
                    "</p><h3>Konstruowanie map(białe whiteMap.txt, czarne blackMap.txt)</h3><h4>UWAGA: Każda mapa musi mieć co najmniej jeden pionek z podanej strony (whiteMap.txt co najmniej 1 pionek biały, blackMap.txt co najmniej 1 pionek czarny).</h4><p>[] - puste pole</p><p>P0 - pionek biały</p><p>P1 - pionek czarny</p>" +
                    "<p>K0 - król biały</p><p>K1 - król czarny</p><p>B0 - goniec biały</p><p>B1 - goniec czarny</p><p></p><p>Można zapisać/wczytać grę za pomocą przycisku save/load.</p></html>");
            label.setVerticalAlignment(SwingConstants.CENTER);
            hintWindow.setSize(530,400);
            hintWindow.add(label);
            hintWindow.setVisible(true);

        }
    }
    // przycisk zapisywania
    class SaveAction implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            try {
                board.saveBoard();
                saveButton.setEnabled(true);
                infoLabel.setText("Board has been saved!");
            } catch (IOException e1) {
                infoLabel.setText("ERROR: Cannot save board!");
            }
            cleanBackground();
        }
    }
    // przycisk wczytywanie
    class LoadAction implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            try {
                //int tempSide = Board.side;
                board = Board.loadBoard();
                GUI.sideWithPiece = board.sideWithPiece;
                //Board.side = tempSide;
                updateButtons();
                cleanBackground();
                infoLabel.setText("Board has been loaded! " + getSideName() + " turn.");
                if(board.history.size()!=0&&board.index!=0) {
                    undoButton.setEnabled(true);
                } else {
                    undoButton.setEnabled(false);
                }

                if(board.history.size()-1>board.index) {
                    redoButton.setEnabled(true);
                } else {
                    redoButton.setEnabled(false);
                }
                checkWin();
            } catch (IOException | ClassNotFoundException e1) {
                infoLabel.setText("ERROR: Cannot load board!");
            }
        }
    }
    // przycisk cofania
    class UndoAction implements ActionListener{
        public void actionPerformed(ActionEvent e) {

            if(board.index>=1) {
                board.index--;
                //board.board = board.history.get(board.index);
                Board b = new Board(sideWithPiece);
                b.board = board.history.get(board.index);
                board.board = b.cloneBoard();
                updateButtons();
            } if(board.index==0) {
                undoButton.setEnabled(false);
            }
            clicked = false;
            if(board.index<board.history.size()) {
                redoButton.setEnabled(true);
            }

            board.changeSide();
            infoLabel.setText("Undo successful! " + getSideName() + " turn.");
            cleanBackground();
            checkWin();
        }
    }
    // przycisk przywracania
    class RedoAction implements ActionListener{
        public void actionPerformed(ActionEvent e) {

                board.index++;
                //board.board = board.history.get(board.index);

            Board b = new Board(sideWithPiece);
            b.board = board.history.get(board.index);
            board.board = b.cloneBoard();

                updateButtons();
                clicked = false;
                undoButton.setEnabled(true);
            if(board.history.size()-1==board.index) {
                redoButton.setEnabled(false);
            }
            board.changeSide();
            infoLabel.setText("Redo successful! " + getSideName() + " turn.");
            cleanBackground();
            checkWin();
        }
    }
    // blokuje przyciski
    void blockButtons(){
        for(JButton[] b : buttons) {
            for(JButton but : b) {
                but.setEnabled(false);
            }
        }
    }
    // odblokowuje przyciski
    void unblockButtons(){
        for(JButton[] b : buttons) {
            for(JButton but : b) {
                but.setEnabled(true);
            }
        }
    }
    // czyści planszę z kolorów
    void cleanBackground() {
        for(JButton[] b : buttons) {
            for(JButton but : b) {
                but.setBackground(null);
            }
        }
        clicked = false;
    }
}
