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


import android.widget.TextView;
import android.widget.TableLayout;
import android.widget.TableRow;

public class MatrixView extends TextView
{
    private static final String TAG = "MatrixView";
    private RMatrix mat;
    private int precision = 3;
    public static final int GUESS_DIGITS = 5; //how many char a num will have (to left) at min.
    public static final int COL_EXTRA = 2; // space, dot
    public static final int ROW_SPACE = 1; //leading char, ending newline

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
        Log.d(TAG, formatString);
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
    }

}
