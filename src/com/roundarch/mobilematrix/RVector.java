package com.roundarch.mobilematrix;
import java.lang.Math;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
/**
 * Represents a vector. Has most of those vector operations.
 */
public class RVector
{
    private ArrayList<Double> vals;
    
    /**
     * Returns a vector of zeroes.
     */
    public static RVector zeroVector(int len)
    {
        ArrayList<Double> someVals = new ArrayList<Double>(len);
        for (int i = 0; i < len; i++)
            someVals.add((Double)0.0);
        return new RVector(someVals);
    }
    
    /**
     * Returns a vector of all zeroes, except at n, which is 1
     */
    public static RVector unitVector(int n, int len)
    {
        if (n >= len)
            return null;
        RVector unit = RVector.zeroVector(len);
        unit.set(n, 1.0);
        return unit;
    }

    /**
     * Returns a vector constructed from an array.
     */
    public RVector(double[] values)
    {
        vals = new ArrayList<Double>(values.length);
        //add all.
        for (int i = 0; i < values.length; i++)
            vals.add(values[i]);
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
        vals.set(i, val);
    }

    public int size()
    {
        return vals.size();
    }

    public boolean equals(Object other)
    {
        return other instanceof RVector && vals.equals(((RVector)other).vals);
    }

    public RVector clone()
    {
        return new RVector((ArrayList<Double>)vals.clone());
    }

    /**
     * Returns a vector by multiplying each member
     * of this vector by amount.
     */
    public RVector scale(double amount)
    {
        RVector other = clone();
        for (int i = 0; i < size(); i++)
            other.set(i, amount * get(i));
        return other;
    }

    /**
     * Adds another vector to this vector 
     * and returns the result.
     */
    public RVector add(RVector other)
    {
        if (size() != other.size())
            return null;

        RVector sum = clone();
        for (int i = 0; i < size(); i++)
            sum.set(i, get(i) + other.get(i));

        return sum;
    }

    /**
     * Where this vector is x, scalar a, and vector y,
     * returns vector z such that z=ax+y
     */
    public RVector axpy(double a, RVector y)
    {
        if (size() != y.size())
            return null;

        RVector z = clone();
        for (int i = 0; i < size(); i++)
            z.set(i, a * get(i) + y.get(i));

        return z;
    }

    /**
     * Returns the inner, or dot, product of this
     * vector with another vector y. The return type
     * of this product is a scalar.
     */
    public double dot(RVector y)
    {
        if (size() != y.size())
            return 0;

        double dotProd = 0;
        for (int i = 0; i < size(); i++)
            dotProd += get(i) * y.get(i);
        return dotProd;
    }

    /**
     * returns the length of this vector, as in the 
     * norm2 length, using the pythagorean thing.
     */
    public double norm2()
    {
        return Math.sqrt(dot(this));
    }

    /**
     * returns a vector by dropping a value from 
     * this one.
     */
    public RVector drop(int dropWhich)
    {
        RVector other = clone();
        other.vals.remove(dropWhich);
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
    
    /**
     * inserts a value into the vector.
     */
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
        return insert(size(), value);
    }   

    public String toString()
    {
        return vals.toString();
    }
}
