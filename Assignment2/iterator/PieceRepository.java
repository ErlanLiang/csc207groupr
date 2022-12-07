package iterator;

import model.TetrisPiece;

public class PieceRepository {
    private TetrisPiece l1 = new TetrisPiece(TetrisPiece.L1_STR);
    private TetrisPiece l2 = new TetrisPiece(TetrisPiece.L2_STR);
    private TetrisPiece s1 = new TetrisPiece(TetrisPiece.S1_STR);
    private TetrisPiece s2 = new TetrisPiece(TetrisPiece.S2_STR);
    private  TetrisPiece square = new TetrisPiece(TetrisPiece.SQUARE_STR);
    private TetrisPiece pyramid = new TetrisPiece(TetrisPiece.PYRAMID_STR);
    public TetrisPiece[] pieces = {l1, l2, s1, s2, square, pyramid};

    public Iterator getIterator() {
        return new PieceIterator();
    }

    private class PieceIterator implements Iterator {

        int index;

        @Override
        public boolean hasNext() {
            if (index < pieces.length) {
                return true;
            }
            return false;
        }

        @Override
        public Object next() {
            if (this.hasNext()) {
                return pieces[index++];
            }
            return null;
        }
    }
}
