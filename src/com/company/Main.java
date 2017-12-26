package com.company;

import javax.swing.*;

class Main {
    public static void main(String[] args) {
        //new GUI();
        Board board = new Board();
        board.addToBoard(8,4,"knight",0);
       // board.addToBoard(7,5,"bishop",0);
        //board.addToBoard(5,2,"bishop",0);
        board.print();
        System.out.println(board.ableToMove(8,4,9,5));
    }
}

