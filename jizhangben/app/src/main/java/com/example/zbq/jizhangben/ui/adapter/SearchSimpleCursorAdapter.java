package com.example.zbq.jizhangben.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zbq.jizhangben.R;

/**
 * Created by zbq on 18-2-25.
 */

public class SearchSimpleCursorAdapter extends CursorAdapter {
    private Cursor cursor;
    private Context context;
    private LayoutInflater inflater;

    public SearchSimpleCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
        this.context=context;
        this.cursor=c;
        inflater=LayoutInflater.from(context);

    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return inflater.inflate(R.layout.search_list_item,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView textView=view.findViewById(R.id.search_list_tv);
        textView.setText(cursor.getString(cursor.getColumnIndex("name")));
    }
}
