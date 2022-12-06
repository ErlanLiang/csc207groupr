package iterator;

import model.TetrisPiece;

public class IteratorPattern {
    public static void main(TetrisPiece[] args) {
        PieceRepository pieceRepository = new PieceRepository();
        for (Iterator iter = pieceRepository.getIterator(); iter.hasNext();) {
            TetrisPiece piece = (TetrisPiece) iter.next();
        }
    }
}
