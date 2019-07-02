package com.tuwq.pressurediagram;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class MyPressureView extends View {
    private int pressure = 0;

    public MyPressureView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MyPressureView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyPressureView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        if(pressure>80){
            paint.setColor(Color.RED);
        }else if(pressure>50){
            paint.setColor(Color.YELLOW);
        }else{
            paint.setColor(Color.GREEN);
        }
        paint.setTextSize(30);
        canvas.drawText(""+pressure,30,30,paint);
        canvas.drawRect(30, 200-pressure, 80, 200, paint);
    }


    /**
     *
     * @param pressure
     */
    public void setPressure(int pressure) {
        this.pressure = pressure;
        // 重新绘制,子线程调用postInvalidate,主线程调用Invalidate
        postInvalidate();
    }

}
