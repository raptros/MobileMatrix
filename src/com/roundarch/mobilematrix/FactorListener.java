package com.roundarch.mobilematrix;
public interface FactorListener
{
    public void onInitialPartition();

    public void onRepartition1();

    public void onRepartition2();

    public void onPermute();

    public void onCalculateComplete();

    public void onContinuing();

    public void onFactorComplete();
}
