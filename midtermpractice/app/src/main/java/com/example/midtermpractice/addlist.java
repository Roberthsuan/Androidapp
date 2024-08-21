package com.example.midtermpractice;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

public class addlist extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_addlist);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public  void gobackmainpage(View view){
        Intent intent= new Intent(this,MainActivity.class);
        startActivity(intent);
    }



    @Override
    public void onClick(View view) {
        String title = ((EditText) findViewById(R.id.title)).getText().toString();
        String content = ((TextInputEditText) findViewById(R.id.newTodoContent)).getText().toString();

        new AlertDialog.Builder(addlist.this)
                .setTitle("確認儲存")
                .setMessage("你確定要儲存這個代辦事項嗎？")
                .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // 儲存資料的操作
                        Intent intent = new Intent(addlist.this, MainActivity.class);
                        intent.putExtra("title", title);
                        intent.putExtra("content", content);
                        startActivity(intent);
                        finish(); // 返回主頁面並結束當前頁面
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    };
}