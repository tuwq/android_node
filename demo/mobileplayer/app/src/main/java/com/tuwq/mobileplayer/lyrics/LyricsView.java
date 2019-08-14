package com.tuwq.mobileplayer.lyrics;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

import com.tuwq.mobileplayer.R;

import java.io.File;
import java.util.ArrayList;

public class LyricsView extends TextView {

    private Paint paint;
    private int mHighlightColor;
    private int mNormalColor;
    private int mHighlightSize;
    private int mNormalSize;
    private int mWidth;
    private int mHeight;
    private ArrayList<Lyric> lyrics;
    private int centerIndex;
    private int mLineH;
    private int mDuration;
    private int mPosition;

    public LyricsView(Context context) {
        super(context);
        initView();
    }

    public LyricsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public LyricsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    /**
     * 初始化view
     * 初始化文字的画笔
     */
    private void initView() {
        mHighlightSize = getResources().getDimensionPixelSize(R.dimen.highlight_size);
        mNormalSize = getResources().getDimensionPixelSize(R.dimen.normal_size);
        mLineH = getResources().getDimensionPixelSize(R.dimen.line_size);

        mHighlightColor = 0xffffcd2e;
        mNormalColor = 0xfff8f8f8;

        paint = new Paint();
        paint.setColor(mHighlightColor);
        paint.setTextSize(mHighlightSize);
        paint.setAntiAlias(true);// 抗锯齿
    }

    /**
     * 大小改变时
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    /**
     * 绘制文本
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        if (lyrics==null||lyrics.size()==0){
            drawSingleText(canvas);
        }else {
            drawMuliteText(canvas);
        }
    }

    /**
     * 绘制单行 正中央
     *   [01:09.22]也在这里死去 centerY
     *      x = View宽度的一半 - 文字宽度的一半
     *      y = View高度的一半 + 文字宽度的一半
     * @param canvas
     */
    private void drawSingleText(Canvas canvas) {
        //  X = View宽度的一半 - 文字宽度的一半
        //  Y = View高度的一半 + 文字高度的一半
        // 这样正好文字在正中央
        String text = "正在加载歌词...";
        // 获取文本大小
        Rect bounds = new Rect();
        paint.getTextBounds(text,0,text.length(),bounds);
        // 计算文本绘制坐标
        float drawX = mWidth / 2 - bounds.width() / 2;
        float drawY = mHeight / 2 + bounds.height() / 2;
        canvas.drawText(text,drawX,drawY,paint);
    }

    /**
     * 在屏幕水平居中绘制多行文本
     *  示范
     *      [00:59.73]我在这里欢笑 centerY - lineH * 3
     *      [01:03.35]我在这里哭泣 centerY - lineH * 2
     *      [01:06.99]我在这里活着 centerY - lineH * 1
     *      [01:09.22]也在这里死去 centerY
     *      [01:14.15]我在这里祈祷 centerY + lineH * 1
     *      [01:17.63]我在这里迷惘 centerY + lineH * 2
     *      [01:21.35]我在这里寻找 centerY + lineH * 3
     *
     *      x = View宽度的一半 - 文字宽度的一半
     *      y = 居中行Y + 行高 * (绘制行 - 居中行)
     *      偏移的Y = 已消耗时间百分比 * 行高
     *      已消耗时间百分比 = 已消耗时间 / 行可用时间
     *      已消耗时间 = 当前播放进度 - 行起始时间
     *      行可用时间 = 下一行起始时间 - 行起始时间
     * @param canvas
     */
    private void drawMuliteText(Canvas canvas) {
        // 获取居中行数据
        Lyric lyric = lyrics.get(centerIndex);
        // 下一行起始时间
        int nextStartPoint;
        if (centerIndex != lyrics.size()-1){
            // 非最后一行
            Lyric nextLyric = lyrics.get(centerIndex + 1);
            nextStartPoint = nextLyric.getStartPoint();
        }else{
            nextStartPoint = mDuration;
        }
        // 行可用时间 = 下一行起始时间 - 行起始时间
        int lineTime = nextStartPoint - lyric.getStartPoint();
        // 已消耗时间 = 当前播放进度 - 行起始时间
        int pastTime = mPosition - lyric.getStartPoint();
        // 已消耗时间百分比 = 已消耗时间 / 行可用时间
        float pastPercent = (float)pastTime / lineTime;
        // 偏移的Y = 已消耗时间百分比 * 行高
        int offsetY = (int) (pastPercent * mLineH);
        // 获取居中行高度
        Rect bounds = new Rect();
        paint.getTextBounds(lyric.getContent(),0,lyric.content.length(),bounds);
        // 计算文本绘制坐标
        float centerY = mHeight / 2 + bounds.height() / 2 - offsetY;
        // 文本高亮
        for (int i = 0; i < lyrics.size(); i++) {
            if (i == centerIndex){
                // 居中行需要高亮
                paint.setColor(mHighlightColor);
                paint.setTextSize(mHighlightSize);
            }else if( i == centerIndex -1 || i ==centerIndex + 1 ){
                paint.setColor(getResources().getColor(R.color.hightLightColor1));
                paint.setTextSize(mNormalSize);
            }else if(i==centerIndex-2||i==centerIndex+2){
                paint.setColor(getResources().getColor(R.color.hightLightColor2));
                paint.setTextSize(mNormalSize);
            } else{
                // 普通行变暗
                paint.setColor(mNormalColor);
                paint.setTextSize(mNormalSize);
            }
            // Y = 居中行Y + 行高 * (绘制行 - 居中行)
            float drawY = centerY + mLineH * (i - centerIndex);
            drawHorizontalText(canvas, lyrics.get(i).getContent(), drawY);
        }
    }

    /**
     * 水平居中绘制一行文本
     * @param canvas
     * @param text
     * @param drawY
     */
    private void drawHorizontalText(Canvas canvas, String text, float drawY) {
        // 获取文本大小
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        // 计算文本绘制坐标
        float drawX = mWidth / 2 - bounds.width() / 2;
        canvas.drawText(text, drawX, drawY, paint);
    }

    /**
     * 根据当前歌曲播放进度，计算据中行的所在行数
     * @param position
     * @param duration
     */
    public void computeCenterIndex(int position,int duration){
        mDuration = duration;
        mPosition = position;
        // 当前歌曲播放进度 >= 当前行起始时间 && 当前播放进度 < 下一行起始时间
        for (int i = 0; i < lyrics.size(); i++) {
            int startPoint = lyrics.get(i).getStartPoint();
            int nextStartPoint;
            if (i != lyrics.size()) {
                // 非最后一行
                Lyric nextLyric = lyrics.get(i + 1);
                nextStartPoint = nextLyric.getStartPoint();
            } else {
                // 最后一行
                nextStartPoint = duration;
            }
            if (position >= startPoint && position < nextStartPoint) {
                centerIndex = i;
                break;
            }
        }
        // 使用新的居中行刷新界面
        invalidate();
    }

    /**
     * 接收歌词文件，生成歌词列表
     * @param file
     */
    public void setLyricFile(File file){
        lyrics = LyricsParser.parseFile(file);
        centerIndex = 0;
    }
}
