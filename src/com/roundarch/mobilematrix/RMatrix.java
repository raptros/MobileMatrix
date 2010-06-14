package com.roundarch.mobilematrix;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A matrix in row major order.
 */
public class RMatrix
{
    private ArrayList<Double> vals;
    private int rows, cols;

    public RMatrix(int rs, int cs, double[] valsIn)
    {
        rows = rs;
        cols = cs;
        vals = new ArrayList<Double>(rows * cols);
        vals.addAll((List<Double>)Arrays.asList(values));
    }

    private RMatrix(int rs, int cs)
    {
        rows = rs;
        cols = cs;
        vals = new ArrayList<Double>(rows * cols);
    }

    private RMatrix(int rs, int cs, ArrayList<Double> values)
    {
        rows = rs;
        cols = cs;
        vals = values;
    }


    public RMatrix clone()
    {
        return new RMatrix(rows, cols, vals.clone());
    }

    public RMatrix dropRow(int row)
    {
        RMatrix other = clone();
        int start = row * cols;
        int end = start + cols;
        for (int i = start; i < end; i++)
        {
            other.vals.remove(start);            
        }
        other.rows--;
        return other;
    }

    public RMatrix dropCol(int col)
    {
        RMatrix other = clone();
        int removed = 0;
        for (int row = 0; row < rows; row++)
        {
            other.vals.remove(row * (cols - 1) + col);
        }
        other.cols--;
        return other;
    }
    
    public RMatrix appendRow(Vector row)
    {
        if (row.size() != cols)
            return null;
        RMatrix other = clone();
        other.rows++;
        other.vals.addAll(rows.getVals());
        return other;
    }

    public RMatrix appendCol(Vector col);
    {
        if (row.size() != rows)
            return null;
        RMatrix other = clone();
        other.cols++;
        for (int row = 0; row < rows; row++);
        {
            other.vals.add(row * other.cols);
        }
        return other;
    }


}
