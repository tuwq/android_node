package com.tuwq.imclient.main.layout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tuwq.imclient.R;
import com.tuwq.imclient.main.adapter.ContactAdapter;
import com.tuwq.imclient.utils.StringUtils;

import java.util.List;

public class Slidebar extends View {
    private String[] sections = {"搜","A","B","C","D","E","F","G","H","I","J","K","L","M","N",
            "O","P","Q","R","S","T","U","V","W","X","Y","Z"};
    private Paint paint;
    private int x;
    private int viewHeight;
    private RecyclerView recyclerView;
    private TextView tv_float;
    private ContactAdapter adapter;

    public Slidebar(Context context) {
        this(context,null);
    }

    public Slidebar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setColor(Color.GRAY);
        paint.setTextSize(getResources().getDimension(R.dimen.slide_text_size));
    }

    public Slidebar(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Slidebar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        x = w/2;
        viewHeight = h;
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(tv_float == null){
            //找到父容器
            ViewGroup parent = (ViewGroup) getParent();
            //在父容器中找到要操作的控件
            recyclerView = (RecyclerView) parent.findViewById(R.id.recyclerview);
            tv_float = (TextView) parent.findViewById(R.id.tv_float);
            adapter = (ContactAdapter) recyclerView.getAdapter();
        }
        String startChar;

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                setBackgroundResource(R.color.background_gray);
                tv_float.setVisibility(View.VISIBLE);
                startChar = sections[getIndex(event.getY())];
                tv_float.setText(startChar);

                scrollRecyclerView(startChar);

//                recyclerView.smoothScrollToPosition();
                break;
            case MotionEvent.ACTION_MOVE:
                startChar = sections[getIndex(event.getY())];
                tv_float.setText(startChar);
                scrollRecyclerView(startChar);
                break;
            case MotionEvent.ACTION_UP:
                setBackgroundColor(Color.TRANSPARENT);
                tv_float.setVisibility(View.GONE);
                break;
        }
        return true;
    }

    private void scrollRecyclerView(String startChar) {
        List<String> contacts = adapter.getContacts();
        if(contacts!=null && contacts.size()>0){
            for(int i = 0;i<contacts.size();i++){
                if(StringUtils.getFirstChar(contacts.get(i)).equals(startChar)){
                    recyclerView.smoothScrollToPosition(i);
                    break;
                }
            }
        }
    }

    int getIndex(float y){
        int sectionHeight = viewHeight/sections.length;
        int result = (int)y/sectionHeight;
        return result<0?0:result>sections.length-1?sections.length-1:result;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        for(int i = 0;i<sections.length;i++){
            canvas.drawText(sections[i],x,viewHeight/sections.length *(i+1),paint);
        }

    }
}
