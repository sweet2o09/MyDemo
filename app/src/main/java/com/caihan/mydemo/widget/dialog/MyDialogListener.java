package com.caihan.mydemo.widget.dialog;

import android.view.View;

import com.flyco.dialog.widget.base.BaseDialog;

/**
 * Created by caihan on 2016/12/23.
 */

public interface MyDialogListener {
    void cancel(BaseDialog dialog, View view);
    void confirm(BaseDialog dialog, View view);
}
