package com.caihan.mydemo.module.demo;

import com.caihan.mydemo.R;
import com.caihan.mydemo.module.home.HomeItem;
import com.caihan.mydemo.widget.recycler.adapter.MyBRVAH;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by caihan on 2017/1/25.
 */
public class RecyclerViewAdapter extends MyBRVAH<HomeItem> {
    private static final String TAG = "RecyclerViewAdapter";

    public RecyclerViewAdapter(List data) {
        super(R.layout.rv_item_view, data);
    }

    public RecyclerViewAdapter(int layoutResId, List<HomeItem> data) {
        super(layoutResId, data);
    }

    @Override
    public void setNewData(List<HomeItem> data) {
        super.setNewData(data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeItem item) {
        helper.setText(R.id.rv_item_text, item.getTitle());
    }
}
