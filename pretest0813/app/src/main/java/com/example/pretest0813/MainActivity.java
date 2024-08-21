package com.example.pretest0813;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ArrayList<Todo> todoArrayList = new ArrayList<Todo>();
    private ActivityResultLauncher<Intent> intentActivityResultLanucher;
    private Context context;

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

        context = this; // 初始化 context

        intentActivityResultLanucher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult o) {
                        if(o.getData() != null && o.getResultCode() == Activity.RESULT_OK){
                            String action = o.getData().getStringExtra("ACTION");

                            if(action.equals("new")){
                                String newTitle = o.getData().getStringExtra("TITLE");
                                int newNumber = o.getData().getIntExtra("NUMBER",0);
                                String newimgName = o.getData().getStringExtra("newimgName");
                                String newContent = o.getData().getStringExtra("CONTENT");
                                Todo newData = new Todo(newTitle,newNumber,newimgName, newContent);
                                todoArrayList.add(newData);
                            } else if (action.equals("edit")) {
                                String index = o.getData().getStringExtra("INDEX");
                                String newTitle = o.getData().getStringExtra("TITLE");
                                int newNumber = o.getData().getIntExtra("NUMBER",0);
                                String newimgName = o.getData().getStringExtra("IMGNAME");
                                String newContent = o.getData().getStringExtra("CONTENT");

                                assert index != null;
                                todoArrayList.get(Integer.parseInt(index)).setTitle(newTitle);
                                todoArrayList.get(Integer.parseInt(index)).setNumber(newNumber);
                                todoArrayList.get(Integer.parseInt(index)).setImgName(newimgName);
                                todoArrayList.get(Integer.parseInt(index)).setContent(newContent);
                            } else{
                                int removeIndex = o.getData().getIntExtra("INDEX", 0);
                                todoArrayList.remove(removeIndex);
                            }
                        }
                    }
                }
        );

        ListView todoListView = findViewById(R.id.todoListView);

        if(todoArrayList.isEmpty()){
            Log.d("Test", "todoArrayList empty.");
            ArrayList<String> empty = new ArrayList<String>();
            empty.add("目前無任何料件!");
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, empty);
            todoListView.setAdapter(adapter);
        }else{
            TodoAdapter adapter = new TodoAdapter(this, todoArrayList);
            todoListView.setAdapter(adapter);
            todoListView.setOnItemClickListener(this);
        }

        // 設定長按事件監聽器，實現刪除功能
        todoListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int itemIndex, long l) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                String titleText = "確定刪除？";
                String contentText = "確定刪除這個資料件？";

                dialog.setTitle(titleText);
                dialog.setMessage(contentText);
                dialog.setCancelable(true);

                dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        todoArrayList.remove(itemIndex);  // 移除被長按的項目
                        refreshTodoListView();            // 刷新列表視圖
                    }
                });

                dialog.setNeutralButton("取消", null);
                dialog.show();

                return true;
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        ListView todoListView = findViewById(R.id.todoListView);
        if(todoArrayList.isEmpty()){
            Log.d("Test", "todoArrayList empty.");
            ArrayList<String> empty = new ArrayList<String>();
            empty.add("還沒有待辦事項，趕緊去新增！");
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, empty);
            todoListView.setAdapter(adapter);
        }else{
            TodoAdapter adapter = new TodoAdapter(this, todoArrayList);
            todoListView.setAdapter(adapter);
            todoListView.setOnItemClickListener(this);
        }
    }

    public void newButtonClick(View view){
        Intent intent = new Intent(this, TodoActivity.class);
        // 設定一個bundle來放資料
        Bundle bundle = new Bundle();
        bundle.putString("ACTION", "new");

        // 利用intent攜帶bundle的資料
        intent.putExtras(bundle);
        intentActivityResultLanucher.launch(intent);
    }

    // 用來更新 ListView 的方法
    private void refreshTodoListView() {
        ListView todoListView = findViewById(R.id.todoListView);
        TodoAdapter adapter = new TodoAdapter(this, todoArrayList);
        todoListView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Todo item = todoArrayList.get(i);
        String title = item.getTitle();
        int number=item.getNumber() ;
        String content = item.getContent();

        Intent intent = new Intent(this, TodoActivity.class);
        // 設定一個bundle來放資料
        Bundle bundle = new Bundle();
        bundle.putString("ACTION", "edit");
        bundle.putString("TITLE", title);
        bundle.putInt("NUMBER", number);
        bundle.putString("IMGNAME", item.getImgName());
        bundle.putString("CONTENT", content);
        bundle.putInt("INDEX", i);

        // 利用intent攜帶bundle的資料
        intent.putExtras(bundle);
        intentActivityResultLanucher.launch(intent);
    }
}
