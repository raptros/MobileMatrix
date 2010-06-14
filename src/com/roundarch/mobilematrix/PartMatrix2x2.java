package com.roundarch.mobilematrix;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;

public class PartMatrix2x2
{
    public RMatrix tl, tr, bl, br;
    /* two by two matrix!
     *
     * tl tr
     * bl br
     */

    public PartMatrix2x2(RMatrix orig)
    {
        tl = RMatrix.nullMatrix();
        tr = new RMatrix(0, orig.getCols());
        bl = new RMatrix(orig.getRows(), 0);
        br = orig;
    }


}
