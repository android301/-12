package com.example.zbq.jizhangben.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.zbq.jizhangben.BuildConfig;
import com.example.zbq.jizhangben.R;
import com.example.zbq.jizhangben.ui.utils.PictureHelperUtils;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import butterknife.BindView;
import butterknife.ButterKnife;



/**
 * Created by zbq on 18-3-2.
 */

public class TouXiangPopup extends PopupWindow implements View.OnClickListener {
    @BindView(R.id.tv_drugway_1)
    TextView tvDrugway1;
    @BindView(R.id.tv_drugway_2)
    TextView tvDrugway2;

    private ImageView imageView;
    private Context context;

    /* 头像文件 */
    private static final String IMAGE_FILE_NAME = "temp_head_image.jpg";

    /* 请求识别码 */
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;

    // 裁剪后图片的宽(X)和高(Y),150 X 150的正方形。
    private static int output_X = 150;
    private static int output_Y = 150;


    public TouXiangPopup(Context context, ImageView imageView) {
        this.context = context;
        this.imageView=imageView;
        View view = LayoutInflater.from(context).inflate(R.layout.touxiangshezhi_popup, null);
        ButterKnife.bind(this, view);
        setContentView(view);
        setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        //设置弹出窗体可点击
        setFocusable(true);
        //实例化一个ColorDrawable颜色不透明
        ColorDrawable colorDrawable = new ColorDrawable(Color.WHITE);
        //设置弹出窗体背景
        setBackgroundDrawable(colorDrawable);
        setOutsideTouchable(true);


        tvDrugway1 = view.findViewById(R.id.tv_drugway_1);
        tvDrugway2 = view.findViewById(R.id.tv_drugway_2);
        tvDrugway1.setOnClickListener(this);
        tvDrugway2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_drugway_1://拍照
                dismiss();
                choseHeadImageFromCameraCapture();
                break;
            case R.id.tv_drugway_2:
                dismiss();
                choseHeadImageFromGallery();
                break;
        }
    }


    // 从本地相册选取图片作为头像
    private void choseHeadImageFromGallery() {
        Intent intentFromGallery = new Intent();
        // 设置文件类型
        intentFromGallery.setType("image/*");
        intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
        ((Activity)context).startActivityForResult(intentFromGallery, CODE_GALLERY_REQUEST);
    }

    //定义一个保存图片的File变量
    private File currentImageFile = null;

    // 启动手机相机拍摄照片作为头像
    private void choseHeadImageFromCameraCapture() {
        Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //存储在mnt/sdcard/下
        File dir = new File(Environment.getExternalStorageDirectory(),"pictures");
        if(dir.exists()){
            dir.mkdirs();
        }
        currentImageFile = new File(dir,System.currentTimeMillis() + ".jpg");
        if(!currentImageFile.exists()){
            try {
                currentImageFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Log.v("TAG",currentImageFile.getPath());

        //判断是否是AndroidN以及更高的版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intentFromCapture.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, "com.example.zbq.jizhangben.fileProvider", currentImageFile);
            intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);

           }
        else {
            // 告诉相机拍摄完毕输出图片到指定的Uri
            intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(currentImageFile));

        }

        ((Activity)context).startActivityForResult(intentFromCapture, CODE_CAMERA_REQUEST);

    }

}
