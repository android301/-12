package com.example.zbq.jizhangben.ui.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zbq.jizhangben.R;
import com.example.zbq.jizhangben.ui.activity.BeiFenActivity;
import com.example.zbq.jizhangben.ui.activity.TouXiangPopup;
import com.example.zbq.jizhangben.ui.activity.ZiJinGuanLiActivity;

import java.io.File;

import butterknife.BindView;

import static android.app.Activity.RESULT_CANCELED;

/**
 * Created by zbq on 18-1-14.
 */

public class MenuMyFragment extends BaseFragment implements View.OnClickListener {


    @BindView(R.id.iv_touxiang)
    ImageView ivTouxiang;
    @BindView(R.id.ll_daochu)
    LinearLayout llDaochu;
    @BindView(R.id.logout)
    TextView logout;
    @BindView(R.id.ll_zijin)
    LinearLayout llZijin;

    /* 请求识别码 */
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;

    private static int output_X = 150;
    private static int output_Y = 150;

    /* 头像文件 */
    private static final String IMAGE_FILE_NAME = "temp_head_image.jpg";

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_menu_my;
    }

    @Override
    protected void initEventAndData() {
        ivTouxiang.setOnClickListener(this);
        logout.setOnClickListener(this);
        llDaochu.setOnClickListener(this);
        llZijin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_daochu:
                Intent intent = new Intent(getActivity(), BeiFenActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_zijin:
                Intent zijin=new Intent(getActivity(), ZiJinGuanLiActivity.class);
                startActivity(zijin);
                break;
            case R.id.logout:
                getActivity().finish();
                break;
            case R.id.iv_touxiang:
                TouXiangPopup fp = new TouXiangPopup(getActivity(),ivTouxiang);
                fp.showAtLocation(LayoutInflater.from(getActivity()).
                                inflate(R.layout.touxiangshezhi_popup, null),
                        Gravity.BOTTOM, 0, 0);
                break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        // 用户没有进行有效的设置操作，返回

        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(getActivity(), "取消", Toast.LENGTH_LONG).show();
            return;
        }
        switch (requestCode) {
            case CODE_GALLERY_REQUEST:
                cropRawPhoto(intent.getData());
                break;

            case CODE_CAMERA_REQUEST:
                if (hasSdcard()) {
                    File apkFile = new File(Environment.getExternalStorageDirectory().getPath(), "pictures/" + System.currentTimeMillis() + ".jpg");
                    cropRawPhoto(Uri.fromFile(apkFile));
                } else {
                    Toast.makeText(getActivity(), "没有SDCard!", Toast.LENGTH_LONG)
                            .show();
                }

                break;

            case CODE_RESULT_REQUEST:
                if (intent != null) {
                    setImageToHeadView(intent);
                }

                break;
        }
        super.onActivityResult(requestCode, resultCode, intent);
    }

    /**
     * 裁剪原始的图片
     */
    public void cropRawPhoto(Uri uri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");

        // 设置裁剪
        intent.putExtra("crop", "true");

        // aspectX , aspectY :宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX , outputY : 裁剪图片宽高
        intent.putExtra("outputX", output_X);
        intent.putExtra("outputY", output_Y);
        intent.putExtra("return-data", true);

        startActivityForResult(intent, CODE_RESULT_REQUEST);
    }

    /**
     * 提取保存裁剪之后的图片数据，并设置头像部分的View
     */
    private void setImageToHeadView(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            ivTouxiang.setImageBitmap(photo);
        }
    }

    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            // 有存储的SDCard
            return true;
        } else {
            return false;
        }

    }

}
