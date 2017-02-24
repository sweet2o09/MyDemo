package com.caihan.mydemo.widget.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.caihan.mydemo.R;
import com.caihan.mydemo.widget.CircularProgress;
import com.flyco.dialog.widget.base.BaseDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by caihan on 2017/1/24.
 */
public class NetWorkLoadingDialog extends BaseDialog<NetWorkLoadingDialog> {
    private static final String TAG = "NetWorkLoadingDialog";

    @BindView(R.id.loading_progressBar)
    CircularProgress mLoadingProgressBar;
    @BindView(R.id.text_progress)
    TextView mTextProgress;

    private CharSequence mMsgString = "";

    public NetWorkLoadingDialog(Context context) {
        super(context);
    }

    @Override
    public View onCreateView() {
        heightScale(0);
        widthScale(0);
        View inflate = View.inflate(mContext, R.layout.progress_layout, null);
        ButterKnife.bind(this, inflate);
        if (!TextUtils.isEmpty(mMsgString)) mTextProgress.setText(mMsgString);
        return inflate;
    }

    @Override
    public void setUiBeforShow() {

    }

    public NetWorkLoadingDialog setDialogMsg(CharSequence text) {
        mMsgString = text;
        return this;
    }

    @Override
    public void show() {
        setCanceledOnTouchOutside(false);
        super.show();
    }
}
