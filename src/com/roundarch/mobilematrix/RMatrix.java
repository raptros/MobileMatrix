package com.roundarch.mobilematrix;
import java.lang.Math;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

/**
 * A matrix in row major order.
 */
public class RMatrix
{
    private ArrayList<Double> vals;
    private int rows, cols;

    public static RMatrix nullMatrix()
    {
        return new RMatrix(0,0);
    }

    public static RMatrix zeroMatrix(int rs, int cs)
    {
        RMatrix zeroes = new RMatrix (rs, cs);
        for (int i = 0; i < rs * cs; i++)
            zeroes.vals.add((Double)0.0);
        return zeroes;
    }

    public static RMatrix unitMatrix(int rs, int cs)
    {
        RMatrix unit = RMatrix.zeroMatrix(rs, cs);
        for (int i = 0; i < unit.diaLen(); i++)
            unit.set(i, i, 1.0);
        return unit;
    }

    public RMatrix(int rs, int cs, double[] valsIn)
    {
        rows = rs;
        cols = cs;
        vals = new ArrayList<Double>(rows * cols);
        for (int i = 0; i < valsIn.length; i++)
            vals.add(valsIn[i]);
    }

    public RMatrix(int rs, int cs)
    {
        rows = rs;
        cols = cs;
        vals = new ArrayList<Double>(rows * cols);
    }

    private double get(int r, int c)
    {
        return vals.get(r*cols+c);
    }

    private void set(int r, int c, double v)
    {
        vals.set(r*cols+c, v);
    }

    private RMatrix(int rs, int cs, ArrayList<Double> values)
    {
        rows = rs;
        cols = cs;
        vals = values;
    }

    public int diaLen()
    {
        return (rows < cols) ? rows : cols;
    }

    public int getRows()
    {
        return rows;
    }

    public int getCols()
    {
        return cols;
    }

    public RMatrix clone()
    {
        return new RMatrix(rows, cols, (ArrayList<Double>)vals.clone());
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
    
    public RMatrix appendRow(RVector row)
    {
        if (row.size() != cols)
            return null;
        RMatrix other = clone();
        other.rows++;
        other.vals.addAll(row.getVals());
        return other;
    }

    public boolean equals(Object other)
    {
        return other instanceof RMatrix && rows == ((RMatrix)other).rows &&
            cols == ((RMatrix)other).cols && vals.equals(((RMatrix)other).vals);
    }

    public String toString()
    {
        return "["+rows+"x"+cols+"] " + vals.toString();
    }

    public RMatrix appendCol(RVector col)
    {
        if (col.size() != rows)
            return null;
        RMatrix other = clone();
        other.cols++;
        for (int row = 0; row < rows; row++)
            other.vals.add(row * other.cols + other.cols - 1, col.get(row));
        return other;
    }

    public RVector getRow(int row)
    {
        ArrayList<Double> rowList = new ArrayList<Double>(cols);
        int start = row*cols, end = start + cols;
        for (int i = start; i < end; i++)
            rowList.add(vals.get(i));
        return new RVector(rowList);
    }

    public RVector getCol(int col)
    {
        ArrayList<Double> colList = new ArrayList<Double>(rows);
        for (int row = 0; row < rows; row++)
            colList.add(vals.get(row*cols+col));
        return new RVector(colList);
    }

    public RVector mvMult(RVector v)
    {
        if (v.size() != cols)
            return null;
        ArrayList<Double> results = new ArrayList<Double>(rows);
        for (int row = 0; row < rows; row++)
            results.add(getRow(row).dot(v));
        return new RVector(results);
    }

    public RVector vmMult(RVector v)
    {
        if (v.size() != rows)
            return null;
        ArrayList<Double> results = new ArrayList<Double>(cols);
        for (int col = 0; col < cols; col++)
            results.add(getCol(col).dot(v));
        return new RVector(results);

    }

    public RMatrix mmMult(RMatrix m)
    {
        if (cols != m.rows)
            return null;
        RMatrix out = new RMatrix(0, m.cols);
        for (int r = 0; r < rows; r++)
        {
            out.rows++;
            out.vals.addAll(m.vmMult(getRow(r)).getVals());
       }
        return out;
    }

    //Z = A + scale * y * x^T
    public RMatrix rank1(double scale, RVector x, RVector y)
    {
        if (x.size() != cols || y.size() != rows)
            return null;
        RMatrix z = new RMatrix(rows, 0);
        RVector temp = null;
        for (int col = 0; col < cols; col++)
        {
            temp = y.axpy(scale * x.get(col), getCol(col));
            z = z.appendCol(temp);
        }
        return z;
    }
}
