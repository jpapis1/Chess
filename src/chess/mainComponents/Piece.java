// Autor Jacek Papis 2018
package chess.mainComponents;
import java.io.Serializable;
abstract public class Piece implements Serializable, Cloneable {
    private int side;
    protected Piece(int side) { //type = king, bishop, knight
        if(side!=0&&side!=1) {
            throw new IllegalArgumentException("There's no" + side + " side!");
        }
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
    public int getSide() {
        return side;
    }
    abstract public String getType();
    abstract public boolean ableToCapture(int x, int y, int x2, int y2, Board board);
    abstract public boolean ableToMove(int x, int y, int x2, int y2, Board board);
}
