package com.caihan.mydemo.widget.recycler.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by caihan on 2017/1/23.
 */
public abstract class MyBRVAH<T> extends BaseQuickAdapter<T, BaseViewHolder> {
    private static final String TAG = "MyBRVAH";

    public MyBRVAH(int layoutResId, List<T> data) {
        super(layoutResId, data);
    }

    public MyBRVAH(List<T> data) {
        super(data);
    }

    @Override
    public void setNewData(List<T> data) {
        super.setNewData(data);
    }

    @Override
    public void addData(T data) {
        super.addData(data);
    }

    @Override
    public void addData(List<T> newData) {
        super.addData(newData);
    }
}
