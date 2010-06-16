package com.roundarch.mobilematrix;

import java.util.ArrayList;
import java.lang.NumberFormatException;

import android.app.Activity;
import android.os.Bundle;

import android.view.View;
import android.view.View.OnClickListener;

import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.EditText;
import android.widget.TextView;

public class FactorMatrixActivity extends Activity 
{
    LUMatrixFactor lu;
    TextView tv;
    boolean hasRun;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.factor_matrix);
        tv = (TextView)findViewById(R.id.display);
        hasRun = false;
    }

    public void onStart()
    {
        super.onStart();
        if (hasRun)
            return ;
        runFactor();
        hasRun = true;
    }

    public void runFactor()
    {
        double[] vals = getIntent().getDoubleArrayExtra("vals");
        int size = getIntent().getIntExtra("size", 4);
        RMatrix mat = new RMatrix(size, size, vals);
        lu = new LUMatrixFactor(mat);
        tv.setText(mat.toString());
        lu.factor();

        String mats = "orig: " +
            lu.mat + "\nL: " +
            lu.l + "\nU: " +
            lu.a + "\nP: " +
            lu.p;
        tv.setText(mats);
    }
}
