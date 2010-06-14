package com.roundarch.mobilematrix;

import android.test.ActivityInstrumentationTestCase2;

public class MatrixPartitionTester extends ActivityInstrumentationTestCase2<MobileMatrixActivity> {

    public MatrixPartitionTester() {
        super("com.roundarch.mobilematrix", MobileMatrixActivity.class);
    }

    public void testRunThrough()
    {
        double[] m = { 2, 3, 1, 7,
                       4, 5, 0, 9,
                       3, 7, 7, 6,
                       8, 4, 2, 6 };
        double d1, d2, d3, d4;
        RMatrix mat = new RMatrix(4, 4, m);
        PartMatrix2x2 part22 = new PartMatrix2x2(mat);
        PartMatrix3x3 part33 = new PartMatrix3x3(part22);
        part33.repartRightDown();
        d1 = part33.sa11;
/*        fail("parts: " + part33.mA00 + "\n" +
                part33.va01 + "\n" + part33.mA02 + "\n" +
                part33.va10 + "\n" + part33.sa11 + "\n" + part33.va12+
                "\n" + part33.mA20 + "\n" + part33.va21 + "\n" +
                part33.mA22);*/


        part33.continueLeftUp();

        assertNotNull(" " + part22.tl, part22.tl);
        assertNotNull(" " + part22.tr, part22.tr);
        assertNotNull("parts tl, tr, bl, br: \n" + part22.tl + "\n" + part22.tr + "\n" + part22.bl + "\n" + part22.br, part22.bl);
        assertNotNull(" " + part22.br, part22.br);

        part33.repartRightDown();
        d2 = part33.sa11;
        part33.continueLeftUp();

        part33.repartRightDown();
        d3 = part33.sa11;
        part33.continueLeftUp();
        
        part33.repartRightDown();
        d4 = part33.sa11;
        part33.continueLeftUp();

        assertEquals(2.0, d1);
        assertEquals(5.0, d2);
        assertEquals(7.0, d3);
        assertEquals(6.0, d4);
        assertEquals(mat, part22.tl);

    }
}
