package com.example.a0731event;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,View.OnLongClickListener,View.OnTouchListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button button =(Button) findViewById(R.id.button);
        button.setOnClickListener(this);


        Button a =(Button) findViewById(R.id.big);
        a.setOnClickListener(this);
        Button b =(Button) findViewById(R.id.small);
        b.setOnClickListener(this);

        button.setOnLongClickListener(this);

        ConstraintLayout main=(ConstraintLayout)findViewById(R.id.main);
        main.setOnTouchListener (this);
    }




    @Override
    public void onClick(View view) {

        TextView text=(TextView) findViewById(R.id.text);
        TextView sizeText=(TextView) findViewById(R.id.sizeText);

        float currentsize= text.getTextSize()/getResources().getDisplayMetrics().scaledDensity;
        text.setTextSize(currentsize+5);
        sizeText.setText(String.valueOf(currentsize+5));

        if(view.getId()==R.id.big){
            text.setTextSize(currentsize+5);
            sizeText.setText(String.valueOf(currentsize+5));
        } else if (view.getId()==R.id.small){
            text.setTextSize(currentsize-5);
            sizeText.setText(String.valueOf(currentsize-5));
        }


    }

    @Override
    public boolean onLongClick(View view) {

        TextView text=(TextView) findViewById(R.id.text);
        TextView sizeText=(TextView) findViewById(R.id.sizeText);


        text.setTextSize(30);
        sizeText.setText(String.valueOf(30));
        return true;//false 就不會持續維持這個長按的功能
    }


    public boolean onTouch(View view, MotionEvent motionEvent){
        TextView action=(TextView) findViewById(R.id.action);
        TextView position=(TextView) findViewById(R.id.positon);

        int act = motionEvent.getAction();

        switch (act){
            case MotionEvent.ACTION_DOWN:
                action.setText("Action down");
                action.setTextColor(Color.parseColor("yellow"));
                break;



            case MotionEvent.ACTION_MOVE:
                action.setText("Action move");
                action.setTextColor(Color.parseColor("#928987"));
                break;

            case MotionEvent.ACTION_UP:
                action.setText("Action up");
                action.setTextColor(Color.parseColor("#928988"));
                break;

        }

        position.setText("x"+motionEvent.getX()+"\n"+"Y:"+motionEvent.getY());
        return true;
    }
}