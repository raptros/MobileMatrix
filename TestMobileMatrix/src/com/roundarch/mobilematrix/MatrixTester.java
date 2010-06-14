package com.roundarch.mobilematrix;

import android.test.ActivityInstrumentationTestCase2;

/**
 * This is a simple framework for a test of an Application.  See
 * {@link android.test.ApplicationTestCase ApplicationTestCase} for more information on
 * how to write and extend Application tests.
 * <p/>
 * To run this test, you can type:
 * adb shell am instrument -w \
 * -e class com.roundarch.mobilematrix.MobileMatrixActivityTest \
 * com.roundarch.mobilematrix.tests/android.test.InstrumentationTestRunner
 */
public class MatrixTester extends ActivityInstrumentationTestCase2<MobileMatrixActivity> {

    public MatrixTester() {
        super("com.roundarch.mobilematrix", MobileMatrixActivity.class);
    }

    public void testAppendColumn()
    {
        double[] m1 = { 1.0, 2.0,
                      3.0, 4.0,
                      5.0, 6.0 };
        double[] m2 = { 1.0, 2.0, 7.0, 3.0, 4.0, 8.0, 5.0, 6.0, 9.0};
        double[] v1 = {7.0, 8.0, 9.0};

        RMatrix mat1 = new RMatrix(3, 2, m1);
        RMatrix mat2 = new RMatrix(3, 3, m2);
        RVector vec1 = new RVector(v1);
        assertEquals(mat1.appendCol(vec1) + " vs. " + mat2, mat2, mat1.appendCol(vec1));
    }

    public void testAppendRow()
    {
        double[] m1 = { 1.0, 2.0, 5.0,
                      3.0, 4.0, 6.0};
        double[] m2 = { 1.0, 2.0, 5.0, 3.0, 4.0, 6.0, 7.0, 8.0, 9.0};
        double[] v1 = {7.0, 8.0, 9.0};

        RMatrix mat1 = new RMatrix(2, 3, m1);
        RMatrix mat2 = new RMatrix(3, 3, m2);
        RVector vec1 = new RVector(v1);
        assertEquals(mat1.appendRow(vec1) + " vs. " + mat2, mat2, mat1.appendRow(vec1));
    }

    public void testRemoveColumn()
    {
        double[] m3 = { 1.0, 2.0, 7.0, 3.0, 4.0, 8.0, 5.0, 6.0, 9.0};
        double[] m2 = { 1.0, 2.0, 3.0, 4.0, 5.0, 6.0 };
        double[] m1 = { 1.0, 7.0, 3.0, 8.0, 5.0, 9.0};
        double[] m0 = { 2.0, 7.0, 4.0, 8.0, 6.0, 9.0};
        double[] v1 = {7.0, 8.0, 9.0};
        RMatrix mat3 = new RMatrix(3, 3, m3);
        RMatrix mat2 = new RMatrix(3, 2, m2);
        RMatrix mat1 = new RMatrix(3, 2, m1);
        RMatrix mat0 = new RMatrix(3, 2, m0);
        RVector vec1 = new RVector(v1);
        
        assertEquals(mat3.dropCol(2) + " vs. " + mat2, mat2, mat3.dropCol(2));
        assertEquals(mat3.dropCol(1) + " vs. " + mat1, mat1, mat3.dropCol(1));
        assertEquals(mat3.dropCol(0) + " vs. " + mat0, mat0, mat3.dropCol(0));
    }

    public void testRemoveRow()
    {
        double[] m3 = { 1.0, 2.0, 7.0, 3.0, 4.0, 8.0, 5.0, 6.0, 9.0};
        double[] m2 = { 1.0, 2.0, 7.0, 3.0, 4.0, 8.0};
        double[] m1 = { 1.0, 2.0, 7.0, 5.0, 6.0, 9.0};
        double[] m0 = { 3.0, 4.0, 8.0, 5.0, 6.0, 9.0};
        RMatrix mat3 = new RMatrix(3, 3, m3);
        RMatrix mat2 = new RMatrix(2, 3, m2);
        RMatrix mat1 = new RMatrix(2, 3, m1);
        RMatrix mat0 = new RMatrix(2, 3, m0);
        
        assertEquals(mat3.dropRow(2) + " vs. " + mat2, mat2, mat3.dropRow(2));
        assertEquals(mat3.dropRow(1) + " vs. " + mat1, mat1, mat3.dropRow(1));
        assertEquals(mat3.dropRow(0) + " vs. " + mat0, mat0, mat3.dropRow(0));
    }

    public void testGetRow()
    {
        double[] m = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        double[] v0 = {1, 2, 3};
        double[] v1 = {4, 5, 6};
        double[] v2 = {7, 8, 9};
        RMatrix mat = new RMatrix(3,3,m);
        RVector vec0 = new RVector(v0);
        RVector vec1 = new RVector(v1);
        RVector vec2 = new RVector(v2);

        assertEquals(mat.getRow(0) + " vs. " + vec0, vec0, mat.getRow(0));
        assertEquals(mat.getRow(1) + " vs. " + vec1, vec1, mat.getRow(1));
        assertEquals(mat.getRow(2) + " vs. " + vec2, vec2, mat.getRow(2));
    }

    public void testMVMult()
    {

    }
}
