package com.roundarch.mobilematrix;

import java.util.ArrayList;
import java.lang.NumberFormatException;

import android.app.Activity;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.text.InputType;

import android.view.View;
import android.view.View.OnClickListener;

import android.content.Intent;

import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.EditText;
import android.widget.TextView;



public class CreateMatrixActivity extends Activity implements OnClickListener
{
    class NumberFieldWatcher implements TextWatcher
    {
        public EditText edit;
        public double value = 0;

        public NumberFieldWatcher(EditText et)
        {
            edit = et;
            edit.addTextChangedListener(this);
            int type = InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED | InputType.TYPE_NUMBER_FLAG_DECIMAL;
            edit.setInputType(type);
        }

        public void afterTextChanged(Editable s)
        {
            if (s.toString() == "")
                return ;
            try
            {
                value = Double.valueOf(s.toString());
            }
            catch (NumberFormatException nfe)
            {
                value = 0;
            }

        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        {
        }
        public void onTextChanged(CharSequence  s, int start, int before, int count)
        {
        }
    }

    private ArrayList<NumberFieldWatcher> watchers;

    private int size;

    private TableLayout layout;
    private Button makeIt;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_matrix);

        layout = (TableLayout)findViewById(R.id.mtable);
        makeIt = (Button)findViewById(R.id.makeit);
        makeIt.setOnClickListener(this);
        size = -1;
    }

    public void onStart()
    {
        super.onStart();
        int foundSize = getIntent().getIntExtra("size", 4);
        if (foundSize == size)
            return ;
        else
        {
            size = foundSize;
            generateLayout();
        }
    }

    private void generateLayout()
    {
        watchers = new ArrayList<NumberFieldWatcher>(size * size);
        TableRow currentRow;
        EditText currentCell;
        NumberFieldWatcher watcher;
        for (int row = 0; row < size; row++)
        {
            currentRow = new TableRow(this);
            for (int col = 0; col < size; col++)
            {
                currentCell = new EditText(this);
                watcher = new NumberFieldWatcher(currentCell);
                watchers.add(watcher);
                currentRow.addView(currentCell);
            }
            layout.addView(currentRow);
        }
        layout.setStretchAllColumns(true);
    }

    public void onClick(View v)
    {
        double[] vals = new double[size*size];
        for (int i = 0; i < vals.length; i++)
            vals[i] = watchers.get(i).value;

        Intent goFactor = new Intent().setClass(this, FactorMatrixActivity.class).putExtra("vals", vals).putExtra("size", size);
        startActivity(goFactor);
    }

}
