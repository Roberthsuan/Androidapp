package com.example.pretest0813;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputLayout;

public class TodoActivity extends AppCompatActivity {
    private String title, content, action, imgName;
    private int number, index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_todo);

        // 設定 WindowInsets 來適應系統欄
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 從 Intent 中獲取資料
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            action = bundle.getString("ACTION");

            if ("edit".equals(action)) {
                title = bundle.getString("TITLE");
                number = bundle.getInt("NUMBER");
                imgName = bundle.getString("IMGNAME");
                content = bundle.getString("CONTENT");
                index = bundle.getInt("INDEX");

                // 將資料設置到 UI 元素中
                ((EditText) findViewById(R.id.newTodoTitle)).setText(title);
                ((EditText) findViewById(R.id.newTodoImgName)).setText(imgName);
                ((EditText) findViewById(R.id.newTodoNumber)).setText(String.valueOf(number));
                ((TextInputLayout) findViewById(R.id.contentTextInputLayout)).getEditText().setText(content);
            }
        }
    }

    public void saveButtonClick(View view) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        String titleText = "確定新增？";
        String contentText = "確定內容並儲存？";

        if ("edit".equals(action)) {
            titleText = "確定修改？";
            contentText = "確定修改的內容並儲存？";
        }

        dialog.setTitle(titleText);
        dialog.setMessage(contentText);
        dialog.setCancelable(true);
        dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // 從 UI 元素中讀取資料
                title = ((EditText) findViewById(R.id.newTodoTitle)).getText().toString();
                imgName = ((EditText) findViewById(R.id.newTodoImgName)).getText().toString();
                number = Integer.parseInt(((EditText) findViewById(R.id.newTodoNumber)).getText().toString());
                content = ((TextInputLayout) findViewById(R.id.contentTextInputLayout)).getEditText().getText().toString();

                // 準備返回資料
                Intent intent = new Intent();
                intent.putExtra("TITLE", title);
                intent.putExtra("NUMBER", number);
                intent.putExtra("IMGNAME", imgName);
                intent.putExtra("CONTENT", content);
                intent.putExtra("ACTION", action);

                if ("edit".equals(action)) {
                    intent.putExtra("INDEX", index);
                }

                // 傳回結果並結束 Activity
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
        dialog.setNeutralButton("取消", null);
        dialog.show();
    }

    public void giveUpButtonClick(View view) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("確定放棄？");
        dialog.setMessage("確定放棄並返回主頁面？");
        dialog.setCancelable(true);
        dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        dialog.setNeutralButton("取消", null);
        dialog.show();
    }
}
