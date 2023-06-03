package com.example.project_1;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PersonActivity extends AppCompatActivity {
    private EditText et_name,et_pwd,et_phone,et_date;
    private RadioButton rb_man,rb_woman;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        init();
        SharedPreferences sp=getSharedPreferences("data",MODE_PRIVATE);
        et_name.setText(sp.getString("name",null));
        et_pwd.setText(sp.getString("pwd",null));
        et_phone.setText(sp.getString("phone",null));
        et_date.setText(sp.getString("date",null));
        String sex = sp.getString("sex",null);
        et_pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        if (sex.equals("男")){
            rb_man.setChecked(true);
        }else {
            rb_woman.setChecked(true);
        }

    }
    //修改个人信息
    public void update(View v){
        //点击确认按钮时进行信息验证
        String name = et_name.getText().toString();
        String pwd = et_pwd.getText().toString();
        String phone = et_phone.getText().toString();
        String date=et_date.getText().toString();
        String sex = null;
        if (rb_man.isChecked()) {
            sex = "男";
        } else if (rb_woman.isChecked()){
            sex = "女";
        }
        if (sex != null) {
            SharedPreferences sp = getSharedPreferences("data", MODE_PRIVATE);
            SharedPreferences.Editor edit = sp.edit();
            edit.putString("name", name);
            edit.putString("pwd", pwd);
            edit.putString("phone", phone);
            edit.putString("date", date);
            edit.putString("sex", sex);
            edit.apply();
            showMsg("修改成功");
            finish();
        }else{
            showMsg("修改失败，有内容不合法");
        }
    }
    public void cancel(View v){
        finish();
    }
    //初始化
    private void init() {
        et_name = findViewById(R.id.et_name);
        et_pwd = findViewById(R.id.et_pwd);
        et_phone = findViewById(R.id.et_phone);
        rb_man = findViewById(R.id.rb_man);
        rb_woman = findViewById(R.id.rb_woman);
        et_date = findViewById(R.id.et_date);
    }

    private void showMsg(String s) {
        Toast.makeText(PersonActivity.this,s,Toast.LENGTH_SHORT).show();
    }
}
