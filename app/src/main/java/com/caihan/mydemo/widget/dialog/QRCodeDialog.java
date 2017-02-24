package com.caihan.mydemo.widget.dialog;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.caihan.mydemo.R;
import com.caihan.mydemo.model.qrcode.MyQRCode;
import com.flyco.dialog.widget.base.BaseDialog;

/**
 * Created by caihan on 2016/12/24.
 */
public class QRCodeDialog extends BaseDialog<QRCodeDialog> {
    private static final String TAG = "QRCodeDialog";

    ImageView mV2DialogQrcodeImage;
    String mShareUrl;

    public QRCodeDialog(Context context) {
        super(context);
    }

    public QRCodeDialog(Context context, String shareUrl) {
        super(context);
        mShareUrl = shareUrl;
    }

    @Override
    public View onCreateView() {
        widthScale(0);
//        showAnim(new Swing());

        // dismissAnim(this, new ZoomOutExit());
        View inflate = View.inflate(mContext, R.layout.qrcode_dialog_layout, null);
        mV2DialogQrcodeImage = (ImageView) inflate.findViewById(R.id.dialog_qrcode_image);
        MyQRCode qrCode = new MyQRCode(mContext, mV2DialogQrcodeImage, mShareUrl);
        return inflate;
    }

    @Override
    public void setUiBeforShow() {

    }

    @Override
    public void show() {
        setCanceledOnTouchOutside(true);
        super.show();
    }
}
