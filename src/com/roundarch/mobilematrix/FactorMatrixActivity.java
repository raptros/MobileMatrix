package com.roundarch.mobilematrix;

import java.util.ArrayList;
import java.lang.NumberFormatException;
import java.lang.Thread;
import java.lang.InterruptedException;

import android.app.Activity;
import android.os.Bundle;

import android.view.View;
import android.view.View.OnClickListener;

import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.EditText;
import android.widget.TextView;

public class FactorMatrixActivity extends Activity implements FactorListener
{
    LUMatrixFactor lu;
    MatrixView mv0, mv1, mv2, mv3;
               //luAtl, luAtr, luAbl, luAbr;
    boolean hasRun;

    RMatrix mat;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.factor_matrix);
        mv0 = (MatrixView)findViewById(R.id.mat0);
        mv1 = (MatrixView)findViewById(R.id.mat1);
        mv2 = (MatrixView)findViewById(R.id.mat2);
        mv3 = (MatrixView)findViewById(R.id.mat3);
        /*luAtl = (MatrixView)findViewById(R.id.lua_tl);
        luAtr = (MatrixView)findViewById(R.id.lua_tr);
        luAbl = (MatrixView)findViewById(R.id.lua_bl);
        luAbr = (MatrixView)findViewById(R.id.lua_br);
        */
        hasRun = false;
    }

    public void onStart()
    {
        super.onStart();
        //if (hasRun)
        //    return ;
        
        double[] vals = getIntent().getDoubleArrayExtra("vals");
        int size = getIntent().getIntExtra("size", 4);
        mat = new RMatrix(size, size, vals);
        mv0.setMatrix(mat);

    }

    public void onResume()
    {
        super.onResume();
        runFactor();
        hasRun = true;
    }

    public void runFactor()
    {
        int size = getIntent().getIntExtra("size", 4);
        lu = new LUMatrixFactor(mat);
        lu.factor(this);

        mv0.setMatrix(lu.mat);
        mv1.setMatrix(lu.l);
        mv2.setMatrix(lu.a);
        mv3.setMatrix(lu.p);
    }

    private RMatrix vectorToColumnMatrix(RVector v)
    {
        RMatrix tmp = new RMatrix(0, v.size());
        return tmp.appendCol(v);
    }

    private RMatrix vectorToRowMatrix(RVector v)
    {
        RMatrix tmp = new RMatrix(v.size(), 0);
        return tmp.appendCol(v);
    }


    public void onInitialPartition()
    {
/*        luAtl.setMatrix(lu.a2x2.tl);
        luAtr.setMatrix(lu.a2x2.tr);
        luAbl.setMatrix(lu.a2x2.bl);
        luAbr.setMatrix(lu.a2x2.br);*/
    }

    public void onRepartition1()
    {
    }

    public void onRepartition2()
    {
    }

    public void onPermute()
    {
    }

    public void onCalculateComplete()
    {
    }

    public void onContinuing()
    {
/*        luAtl.setMatrix(lu.a2x2.tl);
        luAtr.setMatrix(lu.a2x2.tr);
        luAbl.setMatrix(lu.a2x2.bl);
        luAbr.setMatrix(lu.a2x2.br);*/
    }

    public void onFactorComplete()
    {
    }
}
