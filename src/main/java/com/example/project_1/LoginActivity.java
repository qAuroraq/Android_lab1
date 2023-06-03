package com.example.project_1;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText et_name,et_pwd;
    private String inName,inPwd,findName,findPwd,findName1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }
    //点击注册按钮
    public void register(View v) {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }
    //点击登录按钮
    public void login(View v){
        SharedPreferences sp=getSharedPreferences("data",MODE_PRIVATE);  //data：注册信息
        SharedPreferences sp1=getSharedPreferences("data1",MODE_PRIVATE);//data1：登录信息
        findName = sp.getString("name",null);  //注册信息data中的用户名
        findPwd = sp.getString("pwd",null);//注册信息data中的密码
        inName = et_name.getText().toString();//输入框中的用户名
        inPwd = et_pwd.getText().toString();//输入框中的密码
        if (inName.equals(findName)&&inPwd.equals(findPwd)){
                success();
        }else {
            showMsg("登录失败，用户名或密码错误");
        }
    }

    //Toast
    private void showMsg(String s) {
        Toast.makeText(LoginActivity.this,s,Toast.LENGTH_SHORT).show();
    }
    //登录跳转至主界面
    public void success(){
        showMsg("登录成功");
        Intent intent=new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("name",inName);
        startActivity(intent);
    }
    private void init() {
        et_name = findViewById(R.id.et_name);
        et_pwd = findViewById(R.id.et_pwd);
        et_pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }
}