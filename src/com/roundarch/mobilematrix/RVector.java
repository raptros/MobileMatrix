package com.roundarch.mobilematrix;
import java.lang.Math;
public class RVector
{
    private double[] vals;
    
    public RVector(double[] values)
    {
        vals = values.clone();
    }

    private RVector()
    {

    }
    
    private void setValues(double[] values)
    {
        vals = values;
    }

    public double get(int i)
    {
        return vals[i];
    }

    private void set(int i, double val)
    {
        vals[i] = val;
    }

    public int size()
    {
        return vals.length;
    }

    public RVector clone()
    {
        return new RVector(vals);
    }

    public RVector scale(double amount)
    {
        RVector other = clone();
        for (int i = 0; i < size(); i++)
            other.set(i, amount * get(i));
        return other;
    }

    public RVector add(RVector other)
    {
        if (size() != other.size())
            return null;

        RVector sum = clone();
        for (int i = 0; i < size(); i++)
            sum.set(i, get(i) + other.get(i));

        return sum;
    }

    public RVector axpy(double a, RVector y)
    {
        if (size() != y.size())
            return null;

        RVector z = clone();
        for (int i = 0; i < size(); i++)
            z.set(i, a * get(i) + y.get(i));

        return z;
    }

    public double dot(RVector y)
    {
        if (size() != y.size())
            return 0;

        double dotProd = 0;
        for (int i = 0; i < size(); i++)
            dotProd += get(i) * y.get(i);
        return dotProd;
    }

    public double norm2()
    {
        return Math.sqrt(dot(this));
    }
        
}
