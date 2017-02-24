package com.caihan.mydemo.model.qrcode;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.blankj.utilcode.utils.EncodeUtils;
import com.blankj.utilcode.utils.LogUtils;
import com.caihan.mydemo.R;
import com.google.zxing.WriterException;

import java.security.SecureRandom;


/**
 * Created by caihan on 2016/12/24.
 * 生成二维码
 */
public class MyQRCode {
    private static final String TAG = "QRCode";

    private ImageView mQrcodeImage;
    private Bitmap mQrCodeBitmap;
    private static int width, height;
    private String mQRCodeUri;
    private Context mContext;
    /**
     * 推广地址
     */
    String SHARE_URL_TYPE = "https://www.baidu.com/";

    public MyQRCode(Context context, ImageView imageView) {
        mContext = context;
        mQrcodeImage = imageView;
        getQRcodeUri();
        calculateView();
    }

    public MyQRCode(Context context, ImageView imageView, String codeUri) {
        mContext = context;
        mQrcodeImage = imageView;
        mQRCodeUri = codeUri;
        LogUtils.d(TAG, mQRCodeUri);
        calculateView();
    }

    /**
     * 拼接分享网络地址
     */
    private void getQRcodeUri() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[16];
        secureRandom.nextBytes(randomBytes);
        String phone = "reg_invite" + "123456";
        String phone64 = EncodeUtils.base64Encode2String(EncodeUtils.urlEncode(phone).getBytes());
        StringBuilder sb = new StringBuilder();
        sb.append(SHARE_URL_TYPE);
        sb.append(phone64);
        mQRCodeUri = sb.toString();
        LogUtils.d(TAG, mQRCodeUri);
    }

    /**
     * 计算控件的宽高
     */
    private void calculateView() {
        final ViewTreeObserver vto = mQrcodeImage.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                if (vto.isAlive()) {
                    vto.removeOnPreDrawListener(this);
                }
                height = mQrcodeImage.getMeasuredHeight();
                width = mQrcodeImage.getMeasuredWidth();
                Bitmap logo = MakeQRCodeUtil.gainBitmap(mContext, R.mipmap.ic_launcher);
//                Bitmap background = MakeQRCodeUtil.gainBitmap(mContext, R.drawable.qrcode_bg);
//                Bitmap markBMP = MakeQRCodeUtil.gainBitmap(mContext, R.drawable.qrcode_water);
                try {
                    //获得二维码图片
                    mQrCodeBitmap = MakeQRCodeUtil.makeQRImage(logo, mQRCodeUri, width, height);
                    //给二维码加背景
//                    mQrCodeBitmap = MakeQRCodeUtil.addBackground(mQrCodeBitmap, background);
                    //加水印
//                    mQrCodeBitmap = MakeQRCodeUtil.composeWatermark(mQrCodeBitmap, markBMP);
                    //设置二维码图片
                    mQrcodeImage.setImageBitmap(mQrCodeBitmap);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
                return true;
            }
        });
    }
}
