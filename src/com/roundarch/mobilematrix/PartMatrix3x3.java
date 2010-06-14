package com.roundarch.mobilematrix;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;

public class PartMatrix3x3
{
    public RMatrix mA00, mA02, mA20, mA22;
    public RVector va01, va10, va12, va21;
    public double sa11;
    /* Three by three matrix!
     *
     *  A00 a01 A02
     *  a10 a11 a12
     *  A20 a21 A22 
     */

    public PartMatrix2x2 part;
    
    public PartMatrix3x3(PartMatrix2x2 pm22)
    {
        part = pm22;
    }

    /*
      TL | TR     A00 | a01 A02
      ---+---  -> ----+--------
      BL | BR     a10 | a11 a12
                  A20 | a21 A22
     */
    public void repartRightDown()
    {
        mA00 = part.tl;
        
        va10 = part.bl.getRow(0);
        mA20 = part.bl.dropRow(0);

        va01 = part.tr.getCol(0);
        mA02 = part.tr.dropCol(0);

        RVector vScratch = part.br.getRow(0);
        RMatrix mScratch = part.br.dropRow(0);
        sa11 = vScratch.get(0);
        va12 = vScratch.dropFirst();
        
        va21 = mScratch.getCol(0);
        mA22 = mScratch.dropCol(0);
    }

    /*
       A00 a01 | A02    TL | TR
       a10 a11 | a12 -> ---+---
       --------+----    BL | BR
       A20 a21 | A22
     */
      
    public void continueLeftUp()
    {
        part.tl = mA00.appendCol(va01).appendRow(va10.append(sa11));
        part.tr = mA02.appendRow(va12);
        
        part.bl = mA20.appendCol(va21);
        part.br = mA22;
    }
}
