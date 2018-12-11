package com.lulu.todayinhistory.utils.dialog;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.lulu.todayinhistory.R;

/**
 * Created by fyl on 2018/11/23 0023.
 */

public class MyDialog {

    private Context context;
    private AlertDialog alertDialog;

    private TextView title;
    private TextView content;
    private Button btnRight;
    private Button btnCenter;
    private Button btnLeft;

    private String txtTitle;
    private String txtContent;
    private String txtBtnRight;
    private String txtBtnLeft;
    private String txtBtnCenter;

    private boolean cancelable;

    private OnButtonClickListener onButtonClickListener;

    /**
     * button类型
     */
    public enum Type {
        TYPT_ONE, TYPE_TWO, TYPE_THREE
    }
    /**
     * 位置
     */
    public enum Gravitys{
       TOP,LEFT,CENTER,RIGHT,BOTTOM
    }

    public MyDialog(Context context) {
        this.context = context;
    }

    private void createDialog(Type type,Gravitys gravity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = View.inflate(context, R.layout.dialog_my, null);
        builder.setView(view);
        builder.setCancelable(cancelable);
        initView(view);
        setView();
        switch (type) {
            case TYPT_ONE:
                btnLeft.setVisibility(View.GONE);
                btnCenter.setVisibility(View.GONE);
                btnRight.setVisibility(View.VISIBLE);
                break;
            case TYPE_TWO:
                btnLeft.setVisibility(View.VISIBLE);
                btnCenter.setVisibility(View.GONE);
                btnRight.setVisibility(View.VISIBLE);
                break;
            case TYPE_THREE:
                btnLeft.setVisibility(View.VISIBLE);
                btnCenter.setVisibility(View.VISIBLE);
                btnRight.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }

        alertDialog = builder.create();
        //设置dialog显示位置及动画
        setWindow(gravity);
        alertDialog.show();
    }

    private void initView(View view) {
        //标题
        title = (TextView) view.findViewById(R.id.title);
        //内容
        content = (TextView) view.findViewById(R.id.content);
        //最右侧按钮
        btnRight = (Button) view.findViewById(R.id.btn_right);
        //中间按钮
        btnCenter = (Button) view.findViewById(R.id.btn_center);
        //最左侧按钮
        btnLeft = (Button) view.findViewById(R.id.btn_left);
    }

    private void setView() {
        title.setText(txtTitle);
        content.setText(txtContent);
        btnLeft.setText(txtBtnLeft);
        btnCenter.setText(txtBtnCenter);
        btnRight.setText(txtBtnRight);
        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onButtonClickListener!=null){
                    onButtonClickListener.onLeftClick();
                }
            }
        });
        btnCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onButtonClickListener!=null){
                    onButtonClickListener.onCenterClick();
                }
            }
        });

        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onButtonClickListener!=null){
                    onButtonClickListener.onRightClick();
                }
            }
        });
    }

    private void setWindow(Gravitys gravity) {
        Window window=alertDialog.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        DisplayMetrics dm = new DisplayMetrics();
        window.getWindowManager().getDefaultDisplay().getMetrics(dm);

        switch (gravity) {
            case TOP:
                window.setGravity(Gravity.TOP);//设置dialog的显示位置TOP
                break;
            case LEFT:
                window.setGravity(Gravity.LEFT);//设置dialog的显示位置LEFT
                break;
            case CENTER:
                window.setGravity(Gravity.CENTER);//设置dialog的显示位置CENTER
                break;
            case RIGHT:
                window.setGravity(Gravity.RIGHT);//设置dialog的显示位置RIGHT
                break;
            case BOTTOM:
                window.setGravity(Gravity.BOTTOM);//设置dialog的显示位置BOTTOM
                break;
            default:
                break;
        }

        window.setWindowAnimations(R.style.dialogStyle);//添加动画
      //  window.getDecorView().setPadding(0, 0, 0, 0);

//        layoutParams.width = window.getWindowManager().getDefaultDisplay().getWidth()/3;
//        layoutParams.height= window.getWindowManager().getDefaultDisplay().getHeight()/3;
        layoutParams.width = dm.widthPixels;
        layoutParams.height = dm.heightPixels/2;
        window.setAttributes(layoutParams);
    }

    /**
     * 设置标题
     *
     * @param txtTitle
     */
    public MyDialog setTxtTitle(String txtTitle) {
        this.txtTitle = txtTitle;
        return this;
    }

    /**
     * 设置内容
     *
     * @param txtContent
     */
    public MyDialog setTxtContent(String txtContent) {
        this.txtContent = txtContent;
        return this;
    }

    /**
     * 最右侧按钮名称
     *
     * @param txtBtnRight
     */
    public MyDialog setTxtBtnRight(String txtBtnRight) {
        this.txtBtnRight = txtBtnRight;
        return this;
    }

    /**
     * 最左侧按钮名称
     *
     * @param txtBtnLeft
     */
    public MyDialog setTxtBtnLeft(String txtBtnLeft) {
        this.txtBtnLeft = txtBtnLeft;
        return this;
    }

    /**
     * 中间按钮名称
     *
     * @param txtBtnCenter
     */
    public MyDialog setTxtBtnCenter(String txtBtnCenter) {
        this.txtBtnCenter = txtBtnCenter;
        return this;
    }

    public MyDialog setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
        return this;
    }

    public MyDialog setOnButtonClickListener(OnButtonClickListener onButtonClickListener) {
        this.onButtonClickListener = onButtonClickListener;
        return this;
    }

    /**
     * 显示弹出框
     */
    public MyDialog show(Type type,Gravitys gravity) {
        createDialog(type,gravity);
        return this;
    }

    /**
     * 取消弹出框
     */
    public void dismiss() {
        alertDialog.dismiss();
    }

    /**
     * 按钮监听
     */
    public interface OnButtonClickListener {
        //左侧
        void onLeftClick();
        //中间
        void onCenterClick();
        //右侧
        void onRightClick();
    }


}
