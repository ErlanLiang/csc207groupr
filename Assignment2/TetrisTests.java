import model.TetrisPiece;
import model.TetrisBoard;

import model.TetrisPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class TetrisTests {
    TetrisBoard b;

    private TetrisPiece pyr1, pyr2, pyr3, pyr4 ;
    private TetrisPiece square1,square2,square3;
    private TetrisPiece stick1, stick2, stick3;
    private TetrisPiece L21,L22,L24;
    private TetrisPiece s, sRotated;
    private TetrisPiece[] pieces;

    //Piece tests
    @Test
    void testPiece() {
        TetrisPiece piece = new TetrisPiece("1 0  1 1  1 2  0 1");
        int [] output = piece.getLowestYVals();
        int [] target = {1,0};
        for (int i =0; i< output.length; i++) {
            assertEquals(output[i], target[i], "Error when testing lowest Y values");
        }
    }

    @Test
    void testMakeFastRotations() {
        TetrisPiece piece = new TetrisPiece(TetrisPiece.S2_STR);
        piece = TetrisPiece.makeFastRotations(piece);
        String[] target = {"0 0 0 1 1 1 1 2", "0 1 1 0 1 1 2 0", "0 0 0 1 1 1 1 2", "0 1 1 0 1 1 2 0"};
        int counter = 0;
        while(counter < 4){
            TetrisPiece np = new TetrisPiece(target[counter]);
            piece = piece.fastRotation();
            assertTrue(np.equals(piece), "Error when testing piece equality");
            counter++;
        }

    }

    @Test
    void testEquals() {

        TetrisPiece pieceA = new TetrisPiece("1 0  1 1  1 2  0 1");
        TetrisPiece pieceB = new TetrisPiece("1 0  1 1  1 2  0 1");
        assertTrue(pieceB.equals(pieceA), "Error when testing piece equality");
        assertTrue(pieceA.equals(pieceB), "Error when testing piece equality");

    }

    @Test
    void testPlacePiece() {

        TetrisBoard board = new TetrisBoard(10,24);
        TetrisPiece pieceA = new TetrisPiece(TetrisPiece.SQUARE_STR);

        board.commit();
        int retval = board.placePiece(pieceA, 0,0);
        assertEquals(TetrisBoard.ADD_OK,retval);

        board.commit();
        retval = board.placePiece(pieceA, 12,12); //out of bounds
        assertEquals(TetrisBoard.ADD_OUT_BOUNDS,retval);

        board.commit();
        retval = board.placePiece(pieceA, 0,0);
        assertEquals(TetrisBoard.ADD_BAD, retval);

        //fill the entire row
        retval = board.placePiece(pieceA, 2,0); board.commit();
        retval = board.placePiece(pieceA, 4,0); board.commit();
        retval = board.placePiece(pieceA, 6,0); board.commit();
        retval = board.placePiece(pieceA, 8,0);
        assertEquals(TetrisBoard.ADD_ROW_FILLED, retval);

        for (int i = 0; i < board.getWidth(); i++) {
            assertEquals(board.getGrid(i,0), true);
            assertEquals(board.getGrid(i,1), true);
            assertEquals(board.getGrid(i,2), false);
        }

    }

    @Test
    void testPlacementHeight() {
        TetrisPiece pieceA = new TetrisPiece(TetrisPiece.SQUARE_STR);
        TetrisBoard board = new TetrisBoard(10,24); board.commit();
        int retval = board.placePiece(pieceA, 0,0); board.commit();
        int x = board.placementHeight(pieceA, 0);
        assertEquals(2,x);
        retval = board.placePiece(pieceA, 0,2); board.commit();
        x = board.placementHeight(pieceA, 0);
        assertEquals(4,x);

    }

    @Test
    void testClearRows() {
        TetrisBoard board = new TetrisBoard(10,24); board.commit();
        TetrisPiece pieceA = new TetrisPiece(TetrisPiece.SQUARE_STR);

        //fill two rows completely
        int retval = board.placePiece(pieceA, 0,0); board.commit();
        retval = board.placePiece(pieceA, 2,0); board.commit();
        retval = board.placePiece(pieceA, 4,0); board.commit();
        retval = board.placePiece(pieceA, 6,0); board.commit();
        retval = board.placePiece(pieceA, 8,0);

        int rcleared = board.clearRows();
        assertEquals(2, rcleared);
    }

    @BeforeEach
    public void setUp1() throws Exception {

        pyr1 = new TetrisPiece(TetrisPiece.PYRAMID_STR);
        square1 = new TetrisPiece(TetrisPiece.SQUARE_STR);
        stick1 = new TetrisPiece(TetrisPiece.STICK_STR);
        L21 = new TetrisPiece(TetrisPiece.L2_STR);
        square2 = square1.computeNextRotation();
        square3 = square2.computeNextRotation();
        stick2 = stick1.computeNextRotation();
        stick3 = stick2.computeNextRotation();
        L22 = L21.computeNextRotation();
        L24 = L22.computeNextRotation().computeNextRotation().computeNextRotation();
        pyr2 = pyr1.computeNextRotation();
        pyr3 = pyr2.computeNextRotation();
        pyr4 = pyr3.computeNextRotation();
        s = new TetrisPiece(TetrisPiece.S1_STR);
        sRotated = s.computeNextRotation();
        pieces = TetrisPiece.getPieces();
    }

    //Sample tests (provided)

    @Test
    public void testSampleSize() {
        // Check size of pyr piece
        assertEquals(3, pyr1.getWidth());
        assertEquals(2, pyr1.getHeight());

        // Now try after rotation
        // Effectively we're testing size and rotation code here
        assertEquals(2, pyr2.getWidth());
        assertEquals(3, pyr2.getHeight());

        // Now try with some other piece, made a different way
        assertEquals(1, stick1.getWidth());
        assertEquals(4, stick1.getHeight());
        assertEquals(4, stick2.getWidth());
        assertEquals(1, stick2.getHeight());

        //square
        assertEquals(2,square1.getHeight());
        assertEquals(2,square3.getHeight());

        //L and s
        assertEquals(3,L22.getWidth());
        assertEquals(3,sRotated.getHeight());
    }


    // Test the skirt returned by a few pieces
    @Test
    public void testSampleSkirt() {
        // Note must use assertTrue(Arrays.equals(... as plain .equals does not work
        // right for arrays.
        assertTrue(Arrays.equals(new int[] {0, 0, 0}, pyr1.getLowestYVals()));
        assertTrue(Arrays.equals(new int[] {1, 0, 1}, pyr3.getLowestYVals()));

        assertTrue(Arrays.equals(new int[] {0, 0, 1}, s.getLowestYVals()));
        assertTrue(Arrays.equals(new int[] {1, 0}, sRotated.getLowestYVals()));

        assertTrue(Arrays.equals(new int[] {0, 0, 0, 0}, stick2.getLowestYVals()));

        assertTrue(Arrays.equals(new int[] {1, 1, 0}, L22.getLowestYVals()));
    }

    // Test the equals on various pieces
    @Test
    public void testEquals1() {
        //obvious fail
        assertTrue(!sRotated.equals(pyr3));
        assertTrue(!L22.equals(stick3));

        //check equals using a square
        assertTrue(square1.equals(square2));
        assertTrue(square1.equals(square3));
        assertTrue(!square1.equals(new TetrisPiece("1 0 0 0 2 0 1 1")));
        assertTrue(square1.equals(new TetrisPiece("1 0 0 0 1 1 0 1")));

        //check equals using stick and s
        assertTrue(!stick1.equals(stick2));
        assertTrue(stick1.equals(stick3));
        assertTrue(sRotated.equals(new TetrisPiece("0 1 1 1 0 2 1 0")));

        //have fun with L
        assertTrue(L21.equals(L24));
        assertTrue(!L21.equals(new TetrisPiece("0 0 1 0 0 1 0 2")));

        //check pyramid
        assertTrue(!pyr1.equals(pyr3));
        for (TetrisPoint p: pyr2.getBody()) {
            System.out.println(p);
        }
        assertTrue(pyr2.equals(new TetrisPiece("1 1 1 0 0 1 1 2")));
        assertTrue(pyr3.equals(new TetrisPiece("0 1 1 1 2 1 1 0")));
        assertTrue(pyr4.equals(new TetrisPiece("1 1 0 2 0 1 0 0")));
    }

}
