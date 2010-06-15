package com.roundarch.mobilematrix;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;
import java.lang.IndexOutOfBoundsException;

public class LUMatrixFactor
{
    public RMatrix a, l, u, p;

    private PartMatrix2x2 a2x2, l2x2, u2x2, p2x2;
    private PartMatrix3x3 aPart, lPart, uPart, pPart;


    public LUMatrixFactor(RMatrix a)
    {
        this.a = a;
        a2x2 = new PartMatrix2x2(a);
        aPart = new PartMatrix3x3(a2x2);

        l = RMatrix.unitMatrix(a.getRows(), a.getCols());
        l2x2 = new PartMatrix2x2(l);
        lPart = new PartMatrix3x3(l2x2);

       // u = RMatrix.zeroMatrix(a.getRows(), a.getCols());
        p = RMatrix.unitMatrix(a.getRows(), a.getCols());
        p2x2 = new PartMatrix2x2(p);
        pPart = new PartMatrix3x3(p2x2);
    }
    
    /*
     * loop through partitioning l, u, p, a.
     * P_BL, etc is generate pivot matrix for whatever is left of a by examining a11 and a21
     *   to gen pivot matrix, look for first index of a21 that is not zero.
     *   use that in call to genPivot.
     *   A_BR and L_BL need to be updated this way.
     * l21 = a21 / a11
     * A22 = A22 - l21 * a12 (outer product - rank 1 update)
     * continue on
     * A effectively becomes U.
     *
     * Also, notify watchers each step of the way.
     */
    public void factor()
    {
        while (a2x2.tl.diaLen()< a.diaLen())
        {
            //Partition\
            aPart.repartRightDown();
            lPart.repartRightDown();
            pPart.repartRightDown();
            
            //compute pivot, and perform pivot
            RMatrix piv = genPivot( calcPivot(aPart.sa11, aPart.va21),  a2x2.br.getRows());

            a2x2.br = piv.mmMult(a2x2.br);
            l2x2.bl = piv.mmMult(l2x2.bl); //this is missing from rvdg's algorithm.
            p2x2.br = piv.mmMult(p2x2.br);

            //repartition
            aPart.repartRightDown();
            lPart.repartRightDown();
            pPart.repartRightDown();

            //perform calculations.
            lPart.va21 = aPart.va21.scale(1.0/aPart.sa11);

            aPart.va21 = RVector.zeroVector(aPart.va21.size());
            aPart.mA22 = aPart.mA22.rank1(-1.0, lPart.va21, aPart.va12);

            //continue with
            aPart.continueLeftUp();
            lPart.continueLeftUp();
            pPart.continueLeftUp();
        }
        a = a2x2.tl;
        l = l2x2.tl;
        p = p2x2.tl;
    }

    public static int calcPivot(double alpha, RVector edge)
    {
        int pos = 0;
        if (alpha != 0)
            return pos;
        pos++;
        while (pos - 1 < edge.size())
        {
            if (edge.get(pos - 1) != 0)
                return pos;
        }
        return -1; //doesn't this mean matrix is singular or something?
        //probably needs an error.
    }

    public static RMatrix genPivot(int piv, int rows)
    {
        RMatrix pivot = new RMatrix(0, rows);
        pivot = pivot.appendRow(RVector.unitVector(piv, rows));
        for (int i = 0; i < rows; i++)
        {
            if (i != piv)
                pivot = pivot.appendRow(RVector.unitVector(i, rows));
        }
        return pivot;
    }
}
