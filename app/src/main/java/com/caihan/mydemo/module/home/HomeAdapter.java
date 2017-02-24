package com.caihan.mydemo.module.home;

import com.caihan.mydemo.R;
import com.caihan.mydemo.widget.recycler.adapter.MyBRVAH;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by caihan on 2017/1/23.
 */
public class HomeAdapter extends MyBRVAH<HomeItem> {

    public HomeAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    public HomeAdapter(List data) {
        super(R.layout.home_item_view, data);
    }

    @Override
    public void setNewData(List<HomeItem> data) {
        super.setNewData(data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeItem item) {
        helper.setText(R.id.text, item.getTitle());
    }
}
