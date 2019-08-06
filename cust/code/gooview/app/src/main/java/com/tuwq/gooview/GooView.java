package com.tuwq.gooview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.OvershootInterpolator;

public class GooView extends View {

    private Paint paint;

    public GooView(Context context) {
        this(context, null);
    }

    public GooView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GooView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
    }


    private PointF dragCenter = new PointF(400, 200);//drag圆的圆心
    private float dragRadius = 20;//drag圆的半径
    private PointF stickyCenter = new PointF(400, 200);//sticky圆的圆心
    private float stickyRadius = 20;//drag圆的半径

    private PointF controlPoint = new PointF(300, 200);//控制点
    private PointF[] stickyPoints = {new PointF(400, 180), new PointF(400, 220)};
    private PointF[] dragPoints = {new PointF(200, 180), new PointF(200, 220)};

    private double lineK;//斜率或则正切值
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //动态根据圆心距离计算sticky圆的半径
        stickyRadius = calculateStickyRadius();

        //a.计算角的斜率，也就是正切值
        float yOffset = dragCenter.y - stickyCenter.y;
        float xOffset = dragCenter.x - stickyCenter.x;
        if(xOffset!=0){
            lineK = yOffset/xOffset;
        }
        //b.根据lineK就可以计算出4个点的坐标
        dragPoints = GeometryUtil.getIntersectionPoints(dragCenter,dragRadius,lineK);
        stickyPoints = GeometryUtil.getIntersectionPoints(stickyCenter,stickyRadius,lineK);

        //c.计算控制点
        controlPoint = GeometryUtil.getPointByPercent(dragCenter,stickyCenter,0.618f);


        //1.绘制2个圆,drag圆和sticky圆
        canvas.drawCircle(dragCenter.x, dragCenter.y, dragRadius, paint);
        if(!isOutOfRange){
            canvas.drawCircle(stickyCenter.x, stickyCenter.y, stickyRadius, paint);

            //2.使用贝塞尔曲线来绘制2圆链接的部分
            Path path = new Path();
            path.moveTo(stickyPoints[0].x, stickyPoints[0].y);//指定曲线的起点
            //绘制第一条2段贝塞尔曲线
            path.quadTo(controlPoint.x, controlPoint.y, dragPoints[0].x, dragPoints[0].y);
            //连线到第二条贝塞尔曲线的起点
            path.lineTo(dragPoints[1].x, dragPoints[1].y);//连直线
            //绘制第二条贝塞尔曲线
            path.quadTo(controlPoint.x, controlPoint.y, stickyPoints[1].x, stickyPoints[1].y);

            path.close();//path会自动闭合
            canvas.drawPath(path, paint);
        }


        //在sticky圆的周围画上一个保护圈
        paint.setStyle(Paint.Style.STROKE);//空心圆
        canvas.drawCircle(stickyCenter.x,stickyCenter.y,maxDistance,paint);
        paint.setStyle(Paint.Style.FILL);//实心圆
    }


    private float maxDistance = 160;
    /**
     * 计算sitkcy圆的半径
     * @return
     */
    private float calculateStickyRadius() {
        //1.计算2圆圆心距离
        float distance = GeometryUtil.getDistanceBetween2Points(dragCenter,stickyCenter);
        float fraction = distance/maxDistance;
        //根据拖拽百分比计算对应的半径
        return GeometryUtil.evaluateValue(fraction,20,3);
    }

    boolean isOutOfRange = false;//是否超出最大范围
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                dragCenter.set(event.getX(),event.getY());

                //计算2圆圆心距离
                float distance = GeometryUtil.getDistanceBetween2Points(dragCenter,stickyCenter);
                isOutOfRange = distance>maxDistance;

                break;
            case MotionEvent.ACTION_UP:
                //抬起的时候是否超出范围
                if(!isOutOfRange){
                    //让drag圆弹回去
                    final PointF start = new PointF(dragCenter.x,dragCenter.y);
                    ValueAnimator animator = ValueAnimator.ofFloat(0,2);
                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            //获取动画执行的百分比进度
                            float fraction = animation.getAnimatedFraction();
                            //根据百分比获取中间的点
                            PointF pointF = GeometryUtil.getPointByPercent(start, stickyCenter, fraction);
                            dragCenter.set(pointF);
                            //刷新
                            invalidate();
                        }
                    });
                    animator.setInterpolator(new OvershootInterpolator(4));
                    animator.setDuration(600);
                    animator.start();

                }else {
                    //播放爆炸动画...
                }

                break;
        }
        //重绘
        invalidate();

        return true;
    }
}
