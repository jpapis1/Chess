package com.company;

import javax.swing.*;
import java.awt.*;

class GUI extends JFrame {
    Board board = new Board();
    JButton buttons[][] = new JButton[10][10];
    JPanel controlPanel = new JPanel();
    JPanel jBoard = new JPanel();
    GUI() {
        Container container = getContentPane();
        container.setLayout(new GridLayout(1,2));
        container.add(controlPanel);
        container.add(jBoard);
        controlPanel.setLayout(new GridLayout(8,1));
        jBoard.setLayout(new GridLayout(10,10));
        updateButtons();
        setSize(600,400);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }
    void updateButtons() {
        for(int i = 0; i<10; i++) {
            for(int j = 0; j<10; j++) {
                if(board.getPiece(i,j) == null) {
                    buttons[i][j] = new JButton(" ");
                } else {
                    buttons[i][j] = new JButton(Piece.getShortNameFromPieceName(board.getPiece(i,j).getType()));
                }

            }
        }
    }
}
