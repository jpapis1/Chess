// Autor Jacek Papis 2018
package com.company;
import java.io.Serializable;
class Piece implements Serializable, Cloneable {
    private String type;
    private int side;
    Piece() { }
    Piece(String type, int side) { //type = king, bishop, knight
        if(!(type.equals("king"))&&!(type.equals("bishop"))&&!(type.equals("pawn"))) {
            throw new IllegalArgumentException(type + " is a wrong piece type!");
        }
        if(side!=0&&side!=1) {
            throw new IllegalArgumentException("There's no" + side + " side!");
        }
            this.type = type;
            this.side = side;
    }
    Piece getClone() {
        try{
            return (Piece) super.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println("ERROR");
        }
        return null;
    }
    public String getType() {
        return type;
    }

    public int getSide() {
        return side;
    }
    static String getShortNameFromPieceName (String name) {
        String c = null;
        switch (name) {
            case "king":
                c = "X";
                break;
            case "bishop":
                c = "B";
                break;
            case "pawn":
                c = "C";
                break;
        }
        return c;
    }
}
