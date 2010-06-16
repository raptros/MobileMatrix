package com.roundarch.mobilematrix;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;

/*
 * This class partitions a matrix into 4 submatrices, as if it 
 * was itself a matrix. The purpose of matrix partitioning 
 * is to give access to parts of the matrix without having to
 * worry about index problems. This idea comes from
 * Dr. Robert van de Geijn, at the University of Texas;
 * I learned this in his class, SSC329C Practical Linear Algebra 1.
 * 
 * This class on its own does nothing to actually take apart
 * a matrix - it just provides references.
 */
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

    public String toString()
    {
        return tl + " " + tr + "\n"
             + bl + " " + br;
    }

}
