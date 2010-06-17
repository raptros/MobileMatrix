package com.roundarch.mobilematrix;
import android.view.View;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import android.widget.RelativeLayout;
import java.lang.Math;
import android.widget.RelativeLayout.LayoutParams;

/**
 * This helps deal with a 2x2 partitioning for display. 
 * look at the res/layout/factor_matrix.xml file 
 * to see what it is for.
 */
public class PartitionGroup extends RelativeLayout
{
    MatrixView tl, tr, bl, br;
    View vsep, hsep;

    public PartitionGroup(Context context)
    {
        super(context);
    }

    public PartitionGroup(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    /**
     * find the views being layed out.
     */
    public void onFinishInflate()
    {
        super.onFinishInflate();
        tl = (MatrixView)findViewWithTag("TL");
        tr = (MatrixView)findViewWithTag("TR");
        bl = (MatrixView)findViewWithTag("BL");
        br = (MatrixView)findViewWithTag("BR");
        vsep = findViewWithTag("VSEP");
        hsep = findViewWithTag("HSEP");
        
    }

    /**
     * update the matrices and the sizes of the partitioners.
     */
    public void updatePartitioning(PartMatrix2x2 part)
    {
        tl.setMatrix(part.tl);
        tr.setMatrix(part.tr);
        bl.setMatrix(part.bl);
        br.setMatrix(part.br);
        LayoutParams vlp = (LayoutParams)vsep.getLayoutParams();
        LayoutParams hlp = (LayoutParams)hsep.getLayoutParams();
        int upperHeight = Math.max(tl.getHeight(), tr.getHeight());
        int lowerHeight = Math.max(bl.getHeight(), br.getHeight());
        int leftWidth = Math.max(tl.getWidth(), bl.getWidth());
        int rightWidth = Math.max(tr.getWidth(), br.getWidth());
        vlp.height = upperHeight + lowerHeight + hsep.getHeight();
        hlp.width = leftWidth + rightWidth + vsep.getWidth();
        updateViewLayout(vsep, vlp);
        updateViewLayout(hsep, hlp);
        vsep.invalidate();
        hsep.invalidate();
    }
}
