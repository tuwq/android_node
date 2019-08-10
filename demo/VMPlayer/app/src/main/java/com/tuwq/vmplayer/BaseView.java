package com.tuwq.vmplayer;

public interface BaseView<T> {
    /**
     * 给View传递Presenter实例
     * @param presenter
     */
    void setPresenter(T presenter);

}
