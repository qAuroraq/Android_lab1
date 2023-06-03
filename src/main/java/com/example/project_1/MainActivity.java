package com.example.project_1;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import java.util.List;
public class MainActivity extends AppCompatActivity {

    private ListView listView;   //ListView
    private ImageView iv_add,iv_user;
    private MyDatabase myDBHelper;
    private MyAdapter myAdapter;
    private List<Note> resultList;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //数据初始化
        init();
        Intent intent = getIntent();
        String name=intent.getStringExtra("name");
        showMsg("欢迎您，"+name);

        //编辑图片添加记录
        iv_add.setOnClickListener(v -> startActivityForResult(new Intent(MainActivity.this, RecordActivity.class),1));

        //单击进行修改查询，设置列表项的点击监听器
        listView.setOnItemClickListener((parent, view, position, id) -> {
            //当列表项被点击时，对该项的内容进行修改操作
            Note note= (Note) myAdapter.getItem(position);
            Intent intent12 = new Intent(MainActivity.this,RecordActivity.class);
            String sendId=note.getId();
            String sendContent = note.getContent();
            String sendTime = note.getNote_time();
            String sendTitle = note.getTitle();
            intent12.putExtra("id",sendId);
            intent12.putExtra("content",sendContent);
            intent12.putExtra("note_time",sendTime);
            intent12.putExtra("title",sendTitle);
            startActivityForResult(intent12,1);
        });

        //长按删除记录，列表项点击监听器，长按触发删除对应内容
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            //对话框
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                    .setIcon(R.mipmap.ic_launcher)
                    .setTitle("提示")
                    .setMessage("是否删除该条记录？")
                    .setPositiveButton("删除", (dialog, which) -> {
                        //确定要删除的记录是哪一条
                        Note note= (Note) myAdapter.getItem(position);
                        //获取需要删除记录的id
                        String deleteId=note.getId();
                        if (myDBHelper.deleteData(deleteId)){
                            init();
                            showMsg("删除成功");
                        }else {
                            showMsg("删除失败");
                        }
                    })
                    .setNegativeButton("取消", (dialog, which) -> dialog.dismiss());
            dialog = builder.create();
            dialog.show();
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
            dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
            return true;
        });
        //点击图标进入个人中心
        iv_user.setOnClickListener(v -> {
            Intent intent1 = new Intent(MainActivity.this, PersonActivity.class);
            intent1.putExtra("name",name);
            startActivity(intent1);
        });
    }

    private void init() {
        iv_add = findViewById(R.id.iv_add);
        iv_user = findViewById(R.id.iv_user);
        listView = findViewById(R.id.listview);
        if (resultList != null){
            resultList.clear();
        }
        myDBHelper = new MyDatabase(MainActivity.this,"note.db",null,1);
        resultList = myDBHelper.query();
        //适配器
        myAdapter=new MyAdapter(resultList,MainActivity.this);
        listView.setAdapter(myAdapter);
    }
    //数据回传时自动调用执行的方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println(resultCode);
        if (requestCode == 1 && resultCode == 2){
            //说明数据的添加操作时正常执行的,数据库就新增了一条记录。
            init();
        }
    }
    //Toast
    private void showMsg(String s) {
        Toast.makeText(MainActivity.this,s,Toast.LENGTH_SHORT).show();
    }
}
