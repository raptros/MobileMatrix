package com.roundarch.mobilematrix;
import android.test.ActivityInstrumentationTestCase2;


public class MatrixFactorTester extends ActivityInstrumentationTestCase2<MobileMatrixActivity> {
    public MatrixFactorTester() {
        super("com.roundarch.mobilematrix", MobileMatrixActivity.class);
    }

    public void testGenPivot()
    {
        RMatrix piv = LUMatrixFactor.genPivot(2, 3);
        double[] ex = {0, 0, 1, 1, 0, 0, 0, 1, 0};
        RMatrix expected = new RMatrix(3, 3, ex);
        assertEquals(expected, piv);
    }

    public void testLUFactor()
    {
        double[] m4 = { 1.0, 2.0, 7.0, 3.0, 
                        4.0, 8.0, 10.0, 6.0,
                        9.0, 7.0, 2.0, 15.0,
                        14.0, 1.0, 0.0, 5.0};
        RMatrix mat = new RMatrix(4,4, m4);
        LUMatrixFactor lu = new LUMatrixFactor(mat);
        lu.factor();

        String mats = "mat, a, l, p \n" + mat + "\n"+ lu.a + "\n" + lu.l + "\n" + lu.p;
        RMatrix reMult = lu.p.mmMult(lu.l.mmMult(lu.a));
        String mults = "mat vs l*u (where u is lu.a)\n" + mat + "\n" + reMult;
        //fail(mats + "\n" + mults);
        assertEquals(mults, mat, reMult);


    }



}
