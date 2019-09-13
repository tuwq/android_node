package com.tuwq.googleplay95.adapter;

import android.support.v4.view.ViewCompat;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public abstract class MyBaseAdapter<T> extends BaseAdapter{
    ArrayList<T> list;

    public MyBaseAdapter(ArrayList<T> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //抽取原则：共同的操作留下，动态变化的可以用方法来获取
        Object holder;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), getItemLayoutId(position), null);
            holder = createViewHolder(convertView,position);//通过方法来获取，子类重写方法实现不一样的结果

            //设置tag
            convertView.setTag(holder);
        } else {
            holder =  convertView.getTag();
        }

        //绑定数据
        T t = list.get(position);

        bindViewHolder(t,holder,position);

        //给convertView添加动画效果
        animateConvertView(convertView);

        return convertView;
    }

    /**
     * 给convertView添加动画效果
     * @param convertView
     */
    protected void animateConvertView(View convertView){
        //1.先让convertView缩小
        scaleAnim(convertView);

        //平移动画
//        translateAnim(convertView);

        //明星程序员，高产的程序员
//        NineOldAndroid.jar => JackWarthon
//        ViewPagerIndicator=JackWarthon
//        Refrofit => JackWarthon
//        OkHttp => JackWarthon
//        Picasso => JackWarthon
//        RxAndroid => JackWarthon
//        ButterKnife=>JackWarthon
//        LeakCanary=>JackWarthon
//        Mushi=>JackWarthon
//        Otto=>JackWarthon
    }

    /**
     * 平移动画
     * @param convertView
     */
    protected void translateAnim(View convertView){
        convertView.setTranslationY(500f);
        ViewCompat.animate(convertView)
                .translationY(0)
                .setInterpolator(new OvershootInterpolator())
                .setDuration(1500)
                .start();
    }

    /**
     * 缩放动画
     * @param convertView
     */
    private void scaleAnim(View convertView) {
        convertView.setScaleX(0.5f);
        convertView.setScaleY(0.5f);

//        convertView.setTranslationX();
//        convertView.setAlpha();
//        convertView.setRotationY(180);


        //2.绽放开来，执行放大的动画
//        ObjectAnimator animator = ObjectAnimator.ofFloat(convertView,"scaleX",1f);
//        animator.setDuration(500).start();
//        ObjectAnimator animator2 = ObjectAnimator.ofFloat(convertView,"scaleY",1f);
//        animator2.setDuration(500).start();

        ViewCompat.animate(convertView)
                .rotationXBy(360)
                .scaleX(1f)
                .scaleY(1f)
                .setInterpolator(new OvershootInterpolator())//会让运动轨迹超过一点再回来
                .setDuration(1000)
                .start();
    }


    /**
     * 返回adapter的布局
     * @return
     */
    public abstract int getItemLayoutId(int position);

    /**
     * 子类实现该方法，返回自己的holder对象
     * @param convertView
     * @param position
     * @return
     */
    protected abstract Object createViewHolder(View convertView, int position);

    /**
     * 子类来实现绑定数据的
     * @param t
     * @param holder
     * @param position
     */
    protected abstract void bindViewHolder(T t, Object holder, int position);
}

