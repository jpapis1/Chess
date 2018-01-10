// Autor Jacek Papis 2018
package com.company;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartWindow extends JFrame {
    JPanel labelPanel = new JPanel();
    JPanel buttonPanel = new JPanel();
    JLabel infoLabel = new JLabel("Choose side");
    JButton blackButton = new JButton("Black with pawn");
    JButton whiteButton = new JButton("White with pawn");
    StartWindow() {
        setTitle("Bishop & King by Jacek Papis");
        Container c = getContentPane();
        c.setLayout(new GridLayout(2,1));
        c.add(labelPanel);
        c.add(buttonPanel);
        labelPanel.setLayout(new GridLayout(1,1));
        buttonPanel.setLayout(new GridLayout(1,2));
        labelPanel.add(infoLabel);
        buttonPanel.add(whiteButton);
        buttonPanel.add(blackButton);
        infoLabel.setHorizontalAlignment((int)CENTER_ALIGNMENT);
        whiteButton.addActionListener(new WhiteAction());
        blackButton.addActionListener(new BlackAction());

        setSize(400,200);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }
    class WhiteAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            setVisible(false);
            new GUI(0);
            dispose();
        }
    }
    class BlackAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            setVisible(false);
            new GUI(1);
            dispose();
        }
    }
}
