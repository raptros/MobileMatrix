package com.roundarch.mobilematrix;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;
public class RVector
{
    private ArrayList<Double> vals;
    
    public RVector(double[] values)
    {
        vals = new ArrayList<Double>(values.length);
        //add all.
        vals.addAll((List<Double>)Arrays.asList(values));
    }

    public RVector(ArrayList<Double> values)
    {
        vals = values;
    }
    
    public double get(int i)
    {
        return vals.get(i);
    }

    public ArrayList<Double> getVals()
    {
        return vals;
    }

    private void set(int i, double val)
    {
        vals.set(i, value);
    }

    public int size()
    {
        return vals.size();
    }

    public RVector clone()
    {
        return new RVector(vals.clone());
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

    public RVector drop(int dropWhich)
    {
        RVector other = clone();
        other.remove(dropWhich);
        return other;
    }
    
    public RVector dropFirst()
    {
        return drop(0);
    }

    public RVector dropLast()
    {
        return drop(size() - 1);
    }
    
    public RVector insert(int i, double value)
    {
        RVector other = clone();
        other.vals.add(i, value);
        return other;
    }

    public RVector prepend(double value)
    {
        return insert(0, value);
    }

    public RVector append(double value)
    {
        return insert(size() - 1, value);
    }   
}
