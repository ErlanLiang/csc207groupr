package model;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;
import java.io.Serializable;
import java.util.*;

public class SingleObjectBoom {

    private TetrisPiece piece = new TetrisPiece("0 0");//Set the boom to a 1X1 Piece

    private static SingleObjectBoom instance = new SingleObjectBoom();


    public static SingleObjectBoom getInstance(){
        return instance;
    }

    public TetrisPiece getPiece(){
        return piece;
    }

}
