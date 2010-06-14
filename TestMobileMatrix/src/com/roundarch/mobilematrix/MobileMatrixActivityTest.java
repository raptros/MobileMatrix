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
public class MobileMatrixActivityTest extends ActivityInstrumentationTestCase2<MobileMatrixActivity> {

    public MobileMatrixActivityTest() {
        super("com.roundarch.mobilematrix", MobileMatrixActivity.class);
    }

}
