package com.smrecki.common.utils;

import android.app.Activity;
import android.app.Dialog;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.smrecki.payconiqtest.R;

/**
 * Created by tomislav on 27/02/17.
 */
public class DialogUtil {

    public static Dialog buildInfoDialog(
            Activity activity,
            @Nullable String title,
            @Nullable String description,
            @Nullable String buttonText) {
        return buildInfoDialog(activity, title, description, buttonText, null);
    }

    public static Dialog buildInfoDialog(
            Activity activity,
            @Nullable String title,
            @Nullable String description,
            @Nullable String buttonText,
            @Nullable final View.OnClickListener onConfirmClickListener) {
        final Dialog dialog = new Dialog(activity, R.style.Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_info_layout);

        TextView titleV = (TextView) dialog.findViewById(R.id.dialog_title);
        TextView descriptionV = (TextView) dialog.findViewById(R.id.dialog_description);

        if (title != null) {
            titleV.setText(title);
        } else {
            titleV.setVisibility(View.GONE);
        }

        if (description != null) {
            descriptionV.setText(description);
        } else {
            descriptionV.setVisibility(View.GONE);
        }

        Button ok = (Button) dialog.findViewById(R.id.dialog_send);
        if (buttonText != null) {
            ok.setText(buttonText);
        }

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (onConfirmClickListener != null) {
                    onConfirmClickListener.onClick(v);
                }
            }
        });

        return dialog;
    }

}
