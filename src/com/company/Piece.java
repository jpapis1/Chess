package com.company;

class Piece {
    private String type;
    private int side;
    Piece() { }
    Piece(String type, int side) { //type = king, bishop, knight
        if(!(type.equals("king"))&&!(type.equals("bishop"))&&!(type.equals("knight"))) {
            throw new IllegalArgumentException(type + " is a wrong piece type!");
        }
        if(side!=0&&side!=1) {
            throw new IllegalArgumentException("There's no" + side + " side!");
        }
            this.type = type;
            this.side = side;
    }

    public String getType() {
        return type;
    }

    public int getSide() {
        return side;
    }
}
