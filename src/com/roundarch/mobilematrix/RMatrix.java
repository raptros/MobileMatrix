package com.roundarch.mobilematrix;
import java.lang.Math;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.lang.StringBuilder;

/**
 * A matrix in row major order. Contains the basic matrix arithmetic
 * operations, plus operations to cut apart and put bac together matrices.
 * I don't like having to deal with modifiable matrices, so most
 * of the operations here return new matrices. Sort of a function programming
 * thing. I don't really want to talk about memory considerations.
 */
public class RMatrix
{
    private ArrayList<Double> vals;
    private int rows, cols;

    //Convenience method for a fake matrix
    public static RMatrix nullMatrix()
    {
        return new RMatrix(0,0);
    }

    //Returns a matrix of all zeroes at specified size
    public static RMatrix zeroMatrix(int rs, int cs)
    {
        RMatrix zeroes = new RMatrix (rs, cs);
        for (int i = 0; i < rs * cs; i++)
            zeroes.vals.add((Double)0.0);
        return zeroes;
    }

    /**
     * Misnamed function. What it creates is actually 
     * a matrix with ones on the diagonal, like the 
     * Identity matrix.
     */
    public static RMatrix unitMatrix(int rs, int cs)
    {
        RMatrix unit = RMatrix.zeroMatrix(rs, cs);
        for (int i = 0; i < unit.diaLen(); i++)
            unit.set(i, i, 1.0);
        return unit;
    }

    /**
     * construct a matrix.
     */
    public RMatrix(int rs, int cs, double[] valsIn)
    {
        rows = rs;
        cols = cs;
        vals = new ArrayList<Double>(rows * cols);
        for (int i = 0; i < valsIn.length; i++)
            vals.add(valsIn[i]);
    }
    
    /**
     * An empty matrix, with space allocated for 
     * every index.
     */
    public RMatrix(int rs, int cs)
    {
        rows = rs;
        cols = cs;
        vals = new ArrayList<Double>(rows * cols);
    }

    /**
     * While indexing is harder to use, this can be convenient.
     */
    public double get(int r, int c)
    {
        return vals.get(r*cols+c);
    }

    /**
     * This is used only for creating new matrices.
     */
    private void set(int r, int c, double v)
    {
        vals.set(r*cols+c, v);
    }

    /**
     * construct a matrix from an already made values list.
     * Private because I don't want non-matrices to be able to
     * modify matrices.
     */
    private RMatrix(int rs, int cs, ArrayList<Double> values)
    {
        rows = rs;
        cols = cs;
        vals = values;
    }

    /**
     * Returns the number of items on the diagonal.
     */
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

    /**
     * clones a matrix with its values.
     */
    public RMatrix clone()
    {
        return new RMatrix(rows, cols, (ArrayList<Double>)vals.clone());
    }

    /**
     * Creates a new matrix that is this matrix with a row 
     * removed.
     */
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

    /**
     * Creates a new matrix that is this matrix with a column 
     * removed.
     */
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
    
    /**
     * Creates a new matrix by appending a vector to
     * this matrix as a row.
     */
    public RMatrix appendRow(RVector row)
    {
        if (row.size() != cols)
            return null;
        RMatrix other = clone();
        other.rows++;
        other.vals.addAll(row.getVals());
        return other;
    }

    /**
     * Creates a new matrix by appending a vector to
     * this matrix as a column.
     */
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

    public boolean equals(Object other)
    {
        return other instanceof RMatrix && rows == ((RMatrix)other).rows &&
            cols == ((RMatrix)other).cols && vals.equals(((RMatrix)other).vals);
    }

    public String toString()
    {
        StringBuilder niceArray = new StringBuilder();
        niceArray.append("[");
        for (int row = 0; row < rows; row++)
        {
            if (row != 0)
                niceArray.append("\n").append(" ");
            for (int col = 0; col < cols; col++)
                niceArray.append(get(row, col)).append(" ");
        }
        niceArray.append("]");
        return "["+rows+"x"+cols+"]\n" + niceArray.toString();
    }

    /**
     * Returns a row of this matrix as a vector.
     */
    public RVector getRow(int row)
    {
        ArrayList<Double> rowList = new ArrayList<Double>(cols);
        int start = row*cols, end = start + cols;
        for (int i = start; i < end; i++)
            rowList.add(vals.get(i));
        return new RVector(rowList);
    }

    /**
     * Returns a column of this matrix as a vector.
     */
    public RVector getCol(int col)
    {
        ArrayList<Double> colList = new ArrayList<Double>(rows);
        for (int row = 0; row < rows; row++)
            colList.add(vals.get(row*cols+col));
        return new RVector(colList);
    }

    /**
     * Matrix vector multiplication. Returns matrix Z,
     * where for this matrix M, and passed in vector V,
     * Z=M*v
     */
    public RVector mvMult(RVector v)
    {
        if (v.size() != cols)
            return null;
        ArrayList<Double> results = new ArrayList<Double>(rows);
        for (int row = 0; row < rows; row++)
            results.add(getRow(row).dot(v));
        return new RVector(results);
    }

    /**
     * Vector-Matrix multiplication.  Returns matrix Z,
     * where for this matrix M, and passed in vector V,
     * Z=v^T*M 
     */
    public RVector vmMult(RVector v)
    {
        if (v.size() != rows)
            return null;
        ArrayList<Double> results = new ArrayList<Double>(cols);
        for (int col = 0; col < cols; col++)
            results.add(getCol(col).dot(v));
        return new RVector(results);

    }

    /**
     * Matrix-Matrix multiplication. Returns matrix C, where,
     * for this matrix as A and passed in matrix as B,
     * C=AB
     */
    public RMatrix mmMult(RMatrix m)
    {
        if (cols != m.rows)
            return null;
        RMatrix out = new RMatrix(0, m.cols);
        for (int r = 0; r < rows; r++)
        {
            out.rows++;
            //this line means that I don't have to 
            //keep generating new matrices to complete
            //the multiplication.
            out.vals.addAll(m.vmMult(getRow(r)).getVals());
       }
        return out;
    }

    /**
     * Performs a rank 1 update. Returns matrix Z, where, for
     * this matrix as A, passed in double as scale, and passed
     * in vectors as y and x,
     * Z = A + scale * y * x^T
     */
    public RMatrix rank1(double scale, RVector y, RVector x)
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

    /**
     * Returns the transpose of this matrix.
     */
    public RMatrix transpose()
    {
        RMatrix trans = new RMatrix(cols, rows);
        for (int col = 0; col < cols; col++)
            trans = trans.appendRow(getCol(col));
        return trans;
    }
}
