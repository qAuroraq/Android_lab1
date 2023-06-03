package com.example.project_1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends BaseAdapter{
    private LayoutInflater layoutInflater;
    //list存储数据库中note表所有记录。
    private List<Note> list;
    //用于将某个布局转换为view的对象。

    public MyAdapter(List<Note> list, Context context){
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();  //返回item的元素个数
    }

    @Override
    public Object getItem(int position) {
        //获取Note对象，Note对象对应这表中的某条记录。
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        //id和position对应
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.list_record,null,false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder)convertView.getTag();
        }
        //将数据库中的内容加载到对应的控件上
        Note note=(Note) getItem(position);
        viewHolder.tv_content.setText(note.getContent());
        viewHolder.tv_time.setText(note.getNote_time());
        viewHolder.tv_title.setText(note.getTitle());
        return convertView;
    }
    //用于加载数据内容。
    static class ViewHolder{
        TextView tv_content,tv_time,tv_title;
        public ViewHolder(View view){
            tv_content = view.findViewById(R.id.tv_content);
            tv_time = view.findViewById(R.id.tv_time);
            tv_title = view.findViewById(R.id.tv_title);
        }
    }
}


