package com.example.project_1;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyDatabase extends SQLiteOpenHelper {
    private static SQLiteDatabase db;
    //创建数据库
    public MyDatabase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, null, version);
        db = this.getWritableDatabase();
    }

    @Override
    //创建表note,表中字段：id，content，note_time。
    public void onCreate(SQLiteDatabase db) {
        //note表，用于存储记事本内容
        db.execSQL("create table note(id integer primary key autoincrement,content text,note_time text,title text)");
    }

    //对note增删改查
    //添加数据
    public static boolean insertData(String content, String title) {
        //设置日期格式
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        //获取系统的当前时间
        Date date = new Date(System.currentTimeMillis());
        //格式化日期
        String finalDate = sdf.format(date);
        ContentValues contentValues = new ContentValues();
        contentValues.put("content", content);
        contentValues.put("note_time", finalDate);
        contentValues.put("title",title);
        long result = db.insert("note", null, contentValues);
        return result > 0;
    }
    //删除数据,根据id进行删除
    public boolean deleteData(String id){
        int result = db.delete("note","id=?",new String[]{id});
        return result > 0;
    }

    //修改数据，根据id进行修改
    public boolean update(String id,String content,String title){
        //设置日期格式
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String finalDate = sdf.format(date);        //格式化日期
        ContentValues contentValues = new ContentValues();  //将需要更新的内容存入contentValues
        contentValues.put("content",content);
        contentValues.put("note_time",finalDate);
        contentValues.put("title",title);
        int result = db.update("note",contentValues,"id=?",new String[]{id});
        return result > 0;
    }
    //查询数据,将查询的内容用note的对象属性存储。
    public List<Note> query(){
        List<Note> list = new ArrayList<>();
        Cursor result = db.query("note",null,null,null,
                null,null,null,null);
        if (result!=null){
            while (result.moveToNext()) {
                Note note = new Note();
                note.setId(String.valueOf(result.getInt(0)));  //指id
                note.setContent(result.getString(1));   //指内容
                note.setNote_time(result.getString(2));  //指时间
                note.setTitle(result.getString(3));  //指标题
                list.add(note);
            }result.close();
        }return list;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    };
}

