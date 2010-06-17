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

/**
 * displays the factoring process.
 */
public class FactorMatrixActivity extends Activity implements FactorListener
{
    LUMatrixFactor lu; //the factor object
    MatrixView mv0;  //display of the input matrix
    PartitionGroup ml, mu, mp; //display the changing of the output matrices
    boolean hasRun;

    public static final String TAG = "FactorMatrixActivity";
    RMatrix mat;

    /**
     * find the view objects needed to make this work
     */
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.factor_matrix);
        mv0 = (MatrixView)findViewById(R.id.mat0);
        ml = (PartitionGroup)findViewById(R.id.pg_ml);
        mu = (PartitionGroup)findViewById(R.id.pg_mu);
        mp = (PartitionGroup)findViewById(R.id.pg_mp);
        hasRun = false;
    }

    /**
     * get the input matrix, display it
     */
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

    /**
     * activity is in view, so run the factoring process.
     */
    public void onResume()
    {
        super.onResume();
        runFactor();
        hasRun = true;
    }

    /**
     * start a thread to run the factoring.
     */
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

    /**
     * not used.
     */
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


    /**
     * matrix has been parted into a 2x2 matrix, so show that.
     * then wait 1.5 seconds.
     */
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

    /**
     * the 2x2 partitioning has been moved down and left.
     * the calculation has completed at this point, and the
     * next will begin soon.
     */
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
