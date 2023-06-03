package com.example.project_1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    private EditText et_name, et_pwd1, et_pwd2, et_phone,et_date;
    private MyDatabase MyDBHelper;
    private RadioButton rb_man,rb_woman;
    boolean result = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        MyDBHelper = new MyDatabase(RegisterActivity.this, "user.db", null, 1);
    }
    //注册按钮时信息验证
    public void register(View v) {
        String name = et_name.getText().toString().trim();
        String pwd = et_pwd1.getText().toString();
        String pwd2 = et_pwd2.getText().toString();
        String phone = et_phone.getText().toString();
        String date = et_date.getText().toString();
        String sex = null;
        if (rb_man.isChecked()) {
            sex = "男";
        } else if (rb_woman.isChecked()){
            sex = "女";
        }
        if (sex != null) {
            if (!pwd.equals(pwd2)){
                showMsg("两次输入密码不相同");
            }else {
                SharedPreferences sp = getSharedPreferences("data", MODE_PRIVATE);
                SharedPreferences.Editor edit = sp.edit();
                edit.putString("name", name);
                edit.putString("pwd", pwd);
                edit.putString("phone", phone);
                edit.putString("date", date);
                edit.putString("sex", sex);
                result = edit.commit();
                showMsg("注册成功");
                startActivity(new Intent(this, LoginActivity.class));
            }
        }else{
            showMsg("注册失败，有内容不合法");
        }
    }

    public void back(View v){
        finish();
    }
    private void showMsg(String s) {
        Toast.makeText(RegisterActivity.this,s,Toast.LENGTH_SHORT).show();
    }

    private void init() {
        et_name = findViewById(R.id.et_name);
        et_pwd1 = findViewById(R.id.et_pwd1);
        et_pwd2 = findViewById(R.id.et_pwd2);
        et_phone = findViewById(R.id.et_phone);
        rb_woman = findViewById(R.id.rb_woman);
        rb_man = findViewById(R.id.rb_man);
        et_date = findViewById(R.id.et_date);
    }
}

