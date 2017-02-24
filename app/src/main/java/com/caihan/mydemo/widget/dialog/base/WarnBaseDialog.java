package com.caihan.mydemo.widget.dialog.base;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.caihan.mydemo.R;
import com.caihan.mydemo.widget.dialog.MyDialogListener;
import com.flyco.dialog.utils.CornerUtils;
import com.flyco.dialog.widget.base.BaseDialog;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by caihan on 2016/12/23.
 * 警告提示型Dialog
 */
public class WarnBaseDialog extends BaseDialog<WarnBaseDialog> implements View.OnClickListener {
    private static final String TAG = "CustomBaseDialog";

    @BindView(R.id.v2_dialog_title)
    TextView mV2DialogTitle;
    @BindView(R.id.v2_dialog_msg)
    TextView mV2DialogMsg;
    @BindView(R.id.v2_dialog_cancel)
    TextView mV2DialogCancel;
    @BindView(R.id.v2_dialog_confirm)
    TextView mV2DialogConfirm;

    private CharSequence mTitleString = "";
    private CharSequence mMsgString = "";
    private CharSequence mCancelString = "";
    private CharSequence mConfirmString = "";
    private int mCancelColor = 0;
    private int mConfirmColor = 0;

    private MyDialogListener mDialogListener;

    public WarnBaseDialog(Context context) {
        super(context);
    }

    public WarnBaseDialog(Context context, boolean isPopupStyle) {
        super(context, isPopupStyle);
    }

    @Override
    public View onCreateView() {
        widthScale(0.85f);
//        showAnim(new Swing());

        // dismissAnim(this, new ZoomOutExit());
        View inflate = View.inflate(mContext, R.layout.warn_dialog_two_btn, null);
        ButterKnife.bind(this, inflate);
        inflate.setBackgroundDrawable(
                CornerUtils.cornerDrawable(Color.parseColor("#ffffff"), dp2px(5)));

        if (!TextUtils.isEmpty(mTitleString)) mV2DialogTitle.setText(mTitleString);
        if (!TextUtils.isEmpty(mMsgString)) mV2DialogMsg.setText(mMsgString);
        if (!TextUtils.isEmpty(mCancelString)) mV2DialogCancel.setText(mCancelString);
        if (!TextUtils.isEmpty(mConfirmString)) mV2DialogConfirm.setText(mConfirmString);

        if (mCancelColor != 0) mV2DialogCancel.setTextColor(
                mContext.getResources().getColor(mCancelColor));
        if (mConfirmColor != 0) mV2DialogConfirm.setTextColor(
                mContext.getResources().getColor(mConfirmColor));

        return inflate;
    }

    @Override
    public void setUiBeforShow() {
        mV2DialogCancel.setOnClickListener(this);

        mV2DialogConfirm.setOnClickListener(this);
    }

    public WarnBaseDialog setDialogTitle(CharSequence text) {
        mTitleString = text;
        return this;
    }

    public WarnBaseDialog setDialogMsg(CharSequence text) {
        mMsgString = text;
        return this;
    }

    public WarnBaseDialog setDialogCancel(CharSequence text) {
        mCancelString = text;
        return this;
    }

    public WarnBaseDialog setDialogConfirm(CharSequence text) {
        mConfirmString = text;
        return this;
    }

    public WarnBaseDialog setOnTouchOutside(boolean cancel) {
        setCanceledOnTouchOutside(cancel);
        return this;
    }

    public WarnBaseDialog setCancelListener(boolean cancel) {
        setCanceledOnTouchOutside(cancel);
        return this;
    }

    public WarnBaseDialog setConfirmListener(boolean cancel) {
        setCanceledOnTouchOutside(cancel);
        return this;
    }

    public WarnBaseDialog setMyDialogListener(MyDialogListener listener) {
        mDialogListener = listener;
        return this;
    }

    public WarnBaseDialog setCancelTextColor(@ColorRes int _id) {
        mCancelColor = _id;
        return this;
    }

    public WarnBaseDialog setConfirmTextColor(@ColorRes int _id) {
        mConfirmColor = _id;
        return this;
    }

    @Override
    public void onClick(View v) {
        dismiss();
        switch (v.getId()) {
            case R.id.v2_dialog_cancel:
                if (mDialogListener != null) {
                    mDialogListener.cancel(this, v);
                }
                break;
            case R.id.v2_dialog_confirm:
                if (mDialogListener != null) {
                    mDialogListener.confirm(this, v);
                }
                break;
            default:
                break;
        }
    }
}
