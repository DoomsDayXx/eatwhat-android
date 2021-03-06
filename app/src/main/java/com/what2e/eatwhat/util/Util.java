package com.what2e.eatwhat.util;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;


import com.what2e.eatwhat.tools.NetworkTools;


public class Util {
    private static AlertDialog mAlertDialog;
    public static String Url = "http://www.betayao.tech/";
    private static Toast toast;
    private static boolean IsConnect = true;

    public static void showToast(Context context, String text) {
        try {
            if (toast == null) {
                toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.show();
            } else {
                toast.setText(text);
            }
            toast = null;
        } catch (Exception e) {
            Looper.prepare();
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
            Looper.loop();
        }
    }

    public static boolean checkNetwork(Context context) {
        IsConnect = true;
        if (NetworkTools.getNetState(context)== NetworkTools.NET_NONE) {
            IsConnect = false;
            Util.showToast(context, "网络连接失败,请检查网络设置");
            return IsConnect;
        }
        return IsConnect;
    }



    /**
     * 显示用户自定义的对话框
     *
     * @param context
     * @param message
     * @param listener
     */
    public static void showDialog(Context context, String title, String message, final DialogButtonListener listener) {
        // 在创建Dialog的时候设置样式为透明的，并且要求api最低为11
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setTitle(title);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (mAlertDialog != null) { // 关闭对话框：判断对话框是否为空
                    mAlertDialog.cancel();
                }
                if (listener != null) {
                    // 设置回调，OnClick（）就是IAlertDialogButtonListener接口中的方法
                    listener.onDialogOkButtonClick();// 执行接口的确定方法
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (mAlertDialog != null) {
                    mAlertDialog.cancel();
                }

                if (listener != null) {
                    // 设置回调，OnClick（）就是IAlertDialogButtonListener接口中的方法
                    listener.onDialogCancelButtonClick();// 执行接口的取消方法
                }
            }
        });
        // 创建对话
        mAlertDialog = builder.create();
        // 显示对话框
        mAlertDialog.show();
    }

}
