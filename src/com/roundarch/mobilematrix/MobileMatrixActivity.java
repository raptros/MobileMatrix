package com.roundarch.mobilematrix;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MobileMatrixActivity extends Activity implements OnClickListener
{
    LUMatrixFactor lu;
    EditText size;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button createButton = (Button)findViewById(R.id.create);
        size = (EditText)findViewById(R.id.size);
        createButton.setOnClickListener(this);
    }

    public void onClick(View v)
    {
        int theSize = Integer.decode(size.getText().toString());
        Intent goToCreator = new Intent().setClass(this, CreateMatrixActivity.class).putExtra("size", theSize);
        startActivity(goToCreator);
    }
    
}
