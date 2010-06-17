package com.roundarch.mobilematrix;

import java.util.ArrayList;
import java.lang.NumberFormatException;
import java.lang.Thread;
import java.lang.InterruptedException;
import java.lang.Runnable;

import android.app.Activity;
import android.os.Bundle;

import android.view.View;
import android.view.View.OnClickListener;

import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.EditText;
import android.widget.TextView;
import android.util.Log;

public class FactorMatrixActivity extends Activity implements FactorListener
{
    LUMatrixFactor lu;
    MatrixView mv0;
    PartitionGroup ml, mu, mp;
    boolean hasRun;

    public static final String TAG = "FactorMatrixActivity";
    RMatrix mat;


    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.factor_matrix);
        mv0 = (MatrixView)findViewById(R.id.mat0);
        /*mv1 = (MatrixView)findViewById(R.id.mat1);
        mv2 = (MatrixView)findViewById(R.id.mat2);
        mv3 = (MatrixView)findViewById(R.id.mat3);*/
        ml = (PartitionGroup)findViewById(R.id.pg_ml);
        mu = (PartitionGroup)findViewById(R.id.pg_mu);
        mp = (PartitionGroup)findViewById(R.id.pg_mp);
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
        final FactorListener luListen = this;
        Thread luThread = new Thread() 
        {
            public void run()
            {
                lu.factor(luListen);
            }
            
        };

        luThread.start();
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
        runOnUiThread(
            new Runnable() 
            {
                public void run() 
                {
                    ml.updatePartitioning(lu.l2x2);
                    mu.updatePartitioning(lu.a2x2);
                    mp.updatePartitioning(lu.p2x2);
                }
            }
        );
        try
        {
            Thread.sleep(1500);
        }
        catch (InterruptedException inter)
        {
        }
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
        runOnUiThread(
            new Runnable() 
            {
                public void run() 
                {
                    ml.updatePartitioning(lu.l2x2);
                    mu.updatePartitioning(lu.a2x2);
                    mp.updatePartitioning(lu.p2x2);
                }
            }
        );
        try
        {
            Thread.sleep(1500);
        }
        catch (InterruptedException inter)
        {
        }
    }

    public void onFactorComplete()
    {
    }
}
