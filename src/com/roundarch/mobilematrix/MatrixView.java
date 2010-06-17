package com.roundarch.mobilematrix;

import java.util.ArrayList;
import java.lang.StringBuilder;
import java.util.Formatter;
import java.util.Locale;

import android.graphics.Typeface;
import android.app.Activity;
import android.os.Bundle;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import android.view.View;
import android.view.View.OnClickListener;
import android.graphics.Canvas;

import android.widget.TextView;
import android.widget.TableLayout;
import android.widget.TableRow;

/**
 * Displays a matrix in a textview. Has a function for formatting a string 
 * to present the matrix in as close to aligned columns as possible.
 *
 */
public class MatrixView extends TextView
{
    private static final String TAG = "MatrixView";
    private RMatrix mat;
    private int precision = 1;
    public static final int GUESS_DIGITS = 5; //how many char a num will have (to left) at min.
    public static final int COL_EXTRA = 2; // space, dot
    public static final int ROW_SPACE = 1; //leading char, ending newline
    private boolean matrixModded = false;

    /**
     * Constructors follow.
     */
    public MatrixView(Context context)
    {
        super(context);
    }

 	public MatrixView (Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }
	
    public MatrixView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }
    

    /**
     * set the matrix, and update the string.
     */
    public void setMatrix(RMatrix mat)
    {
        this.mat = mat;
        update();
    }

    public void setPrecision(int precision)
    {
        this.precision = precision;
        update();
    }

    /**
     * I wouldn't use this.
     */
    public void setMatrixOutsideThread(RMatrix mat)
    {
        this.mat = mat;
        matrixModded = true;
        postInvalidate();
    }

    /**
     * yeah, i don't know.
     */
    public void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        if (matrixModded)
        {
            Log.d(TAG, "redrawing matrices");
            update();
        }
    }
    
    //Rebuild the text string.
    public void update()
    {
        setTypeface(Typeface.MONOSPACE);
        if (mat == null)
            return;
        int rows = mat.getRows(), cols = mat.getCols();
        StringBuilder bld = new StringBuilder(rows*(cols*(precision+GUESS_DIGITS+COL_EXTRA)+ROW_SPACE));
        Formatter fromat = new Formatter(bld, Locale.US);
        String formatString = String.format("%%%d.%df", GUESS_DIGITS+precision, precision);
        double curr = 0;
        for (int row = 0; row < rows; row++)
        {
            for (int col = 0; col < cols; col++)
            {
                curr = mat.get(row, col);
                fromat.format(formatString, curr);
                bld.append(" ");
            }
            if (row + 1 != rows)
                bld.append("\n");
        }
        setText(bld.toString());
        matrixModded = false;
    }

}
