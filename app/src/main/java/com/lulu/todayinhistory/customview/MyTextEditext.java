package com.lulu.todayinhistory.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by fyl on 2018/12/3 0003.
 */

public class MyTextEditext extends AppCompatEditText {

    public static final int CATCH = 0;
    public static final int FILES = 1;

    private Paint paint = null;

    public MyTextEditext(Context context) {
        super(context);
        init();
    }

    public MyTextEditext(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyTextEditext(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //画编辑的内容就是super.onDraw
        super.onDraw(canvas);
        //每一行的下划线
        //在editext中可以使用getLineCount这个方法获取内容一共有多少行
        //当你只写了一行时，getLineCount的返回值就是1
        //但是此时你要在整个记事本中全部画满下划线
        //矛盾的是，你画满需要计算一共要画多少条，你只是把view高度除以行高度=n。
        //画多少条是固定的。
        //当你写的内容超过了屏幕的范围，getLineCount>n。再写的内容就没有下划线了。
        //所以，你需要去判断写的内容的行数，和屏幕能容纳的行数的大小关系，取较大的一个。
        //获取行的高度在editext里用getLineHight

        //获取一个屏幕能容纳的最多的行数
        int lineCount = (getHeight() - getPaddingTop() - getPaddingBottom()) / getLineHeight();
        int maxLineCount = lineCount > getLineCount() ? lineCount : getLineCount();
        //绘制下划线
        for (int i = 0; i < maxLineCount; i++) {
            canvas.drawLine(getPaddingLeft(), getPaddingTop() + (i + 1) * getLineHeight(), getWidth() - getPaddingRight(), getPaddingTop() + (i + 1) * getLineHeight(), paint);
        }
    }

    //文件保存
    public static void writer(String fileName, byte[] content, Context context, int type) {
        //判断文件名
        if (fileName == null || "".equals(fileName)) {
            return;
        }
        FileOutputStream outputStream = null;
        File file = null;
        switch (type) {
            case CATCH:
                file = new File(context.getCacheDir(), fileName);
                break;
            case FILES:
                file = new File(context.getFilesDir(), fileName);
                break;
            default:
                break;
        }

        //对文件进行操作
        try {
            outputStream = new FileOutputStream(file);
            outputStream.write(content);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //读取内部存储中的文件的方法
    public static byte[] reader(String fileName, Context context, int type) {
        //判断文件名
        if (fileName == null || "".equals(fileName)) {
            return null;
        }

        FileInputStream inputStream = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        File file = null;
        switch (type) {
            case CATCH:
                file = new File(context.getCacheDir(), fileName);
                break;
            case FILES:
                file = new File(context.getFilesDir(), fileName);
                break;
            default:
                break;
        }

        if (!file.exists()) {
            return "".getBytes();
        }

        //操作文件
        try {
            inputStream = new FileInputStream(file);
            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = inputStream.read(buffer)) != -1) {
                baos.write(buffer,0,len);
                baos.flush();
            }
            return baos.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭流操作
            if (inputStream != null) {
                try {
                    inputStream.close();
                    if (baos != null) {
                        baos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public void save(String fileName, Context context, int type) {
        writer(fileName, (this.getText() + "").getBytes(), this.getContext(), type);
    }

    public void open(String fileName, Context context, int type) {
        byte[] data = reader(fileName, this.getContext(), type);
        String str = new String(data);
        this.setText(str);
    }
}
