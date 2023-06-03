package com.example.project_1;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class RecordActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView iv_backHome,iv_delete,iv_save,iv_t;
    private TextView tv_title;
    private EditText et_content,et_title;
    private MyDatabase myDBHelper;
    private String sendId;
    private int[] textSizeArr={10,20,30,40,45};  //存储字体大小
    int textSize=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        init();
        myDBHelper = new MyDatabase(RecordActivity.this, "note.db", null, 1);
        //根据sendId判断用户进行的是添加还是修改
        Intent intent = this.getIntent();
        //如果用户进行的是添加数据，则sendId为空
        sendId = intent.getStringExtra("id");
        if (sendId == null){
            tv_title.setText("添加记录");
        }else {
            tv_title.setText("修改记录");
            String content = intent.getStringExtra("content");
            String title = intent.getStringExtra("title");
            et_content.setText(content);
            et_title.setText(title);
        }
    }
    //点击事件
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_save:
                //判断用户进行的是添加数据还是修改数据
                String content = et_content.getText().toString();
                String title=et_title.getText().toString();
                if (sendId == null) {
                    //获取编辑框中输入的内容
                    boolean flag = MyDatabase.insertData(content,title);
                    if (flag) {
                        //如果添加成功，将数据回传的结果码设置为2
                        setResult(2);
                        showMsg("添加成功");
                        finish();
                    } else {
                        showMsg("添加失败");
                    }
                }else {
                    if (myDBHelper.update(sendId,content,title)) {
                        //修改成功，将数据回传的结果码设置为2
                        setResult(2);
                        showMsg("修改成功");
                        finish();
                    } else {
                        showMsg("修改失败");
                    }
                }
                break;
            case R.id.iv_delete:
                //清空界面
                et_content.setText("");
                break;
            case R.id.iv_backHome:
                //关闭界面
                finish();
                break;
            case R.id.iv_t:
                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setTitle("设置字体大小")
                        .setIcon(R.mipmap.ic_launcher)
                        //设置单选列表，并为该列表设置监听事件
                        .setSingleChoiceItems(new String[]{"小号","默认","中号","大号","超大"},textSize, (dialog, which) -> {
                            textSize=which;
                        })
                        .setPositiveButton("确定", (dialog, which) -> {
                            et_content.setTextSize(textSizeArr[textSize]);
                            et_title.setTextSize(textSizeArr[textSize]);
                            //根据序号获取数据，通过setTextSize方法设置控件的文本大小
                        })
                        .setNegativeButton("取消", (dialog, which) -> {
                            dialog.dismiss();  //关闭对话框
                        });
                AlertDialog ad=builder.create();
                ad.show();//显示对话框
                ad.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
                ad.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
                break;
        }
    }
    private void init() {
        //获取控件对象
        iv_backHome = findViewById(R.id.iv_backHome);
        iv_delete = findViewById(R.id.iv_delete);
        iv_save = findViewById(R.id.iv_save);
        iv_t = findViewById(R.id.iv_t);
        tv_title = findViewById(R.id.tv_title);
        et_content = findViewById(R.id.et_content);
        et_title = findViewById(R.id.et_title);
        //为控件设监听器
        iv_backHome.setOnClickListener(this);
        iv_save.setOnClickListener(this);
        iv_delete.setOnClickListener(this);
        iv_t.setOnClickListener(this);
    }
    public void showMsg(String s){
        Toast.makeText(RecordActivity.this,s,Toast.LENGTH_SHORT).show();
    }
}

