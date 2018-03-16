package com.example.zbq.jizhangben.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zbq.jizhangben.R;
import com.example.zbq.jizhangben.ui.fragment.FaXianFragment;
import com.example.zbq.jizhangben.ui.fragment.MenuFirstFragment;
import com.example.zbq.jizhangben.ui.fragment.MenuMyFragment;
import com.example.zbq.jizhangben.ui.fragment.MenuTypeFragment;
import com.example.zbq.jizhangben.ui.fragment.MenuWriteFragment;
import com.example.zbq.jizhangben.ui.view.BaseBottomBar;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements BaseBottomBar.OnBottomBarListener{

    @BindView(R.id.Container)
    FrameLayout Container;
    @BindView(R.id.main_bottom_bar)
    BaseBottomBar mBottomBar;
    // 当前选中的tab位置
    private int index = 0;
    private FragmentManager mFragmentManager;

    private static final int MSG_CLICK = 20180120;
    private Boolean mCanClickBoolean = true;
    private int MSG_CLICK_DURATION = 200;//能够响应点击事件的时间间隔为0.2s

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (mHandler == null) {
                return;
            }

            int msgNum = msg.what;
            switch (msgNum) {
                case MSG_CLICK:
                    mCanClickBoolean = true;
                    break;
            }
        }
    };


    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }



    @Override
    protected void initEventAndData() {
        mFragmentManager=getSupportFragmentManager();
        mBottomBar.setOnBottomBarListener(this);
        mBottomBar.showTab(0);
    }


    @Override
    public void showFragment(int index) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(String.valueOf(index));
        if (fragment == null) {
            if (index == 0) {
                fragment = new MenuFirstFragment();
            } else if (index == 1) {
                fragment = new MenuTypeFragment();
            } else if (index == 2) {
                fragment = new MenuWriteFragment();
            }else if (index==3){
                fragment=new FaXianFragment();
            }else if (index==4){
                fragment = new MenuMyFragment();
            }

            if (!fragment.isAdded()) {
                obtainFragmentTransaction(index).add(R.id.Container, fragment, String.valueOf(index)).commitAllowingStateLoss();
            }
        }
        if (fragment != null) {
            obtainFragmentTransaction(index).show(fragment).commitAllowingStateLoss();
        }
        this.index = index;

    }

    @Override
    public void hideFragment(int lastIndex, int curIndex) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(String.valueOf(index));
        if (fragment != null) {
            obtainFragmentTransaction(curIndex).hide(fragment).commitAllowingStateLoss();
        }
    }

    @Override
    public void refreshView(int index) {
        if (index == 0) {
        } else if (index == 1) {

        } else if (index == 2) {

        }else  if (index == 3) {

        }
    }

    @Override
    public void onTabClick(View view) {
        if (!mCanClickBoolean) {
            return;
        }
        mCanClickBoolean = false;
        mHandler.removeMessages(MSG_CLICK);
        mHandler.sendEmptyMessageDelayed(MSG_CLICK,
                MSG_CLICK_DURATION);

        mBottomBar.setCurrentView(view);


    }
    


    /**
     * 获取一个带动画的FragmentTransaction
     *
     * @param index
     * @return
     */
    private FragmentTransaction obtainFragmentTransaction(int index) {
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        // 设置切换动画
        if (index > this.index) {
            ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
        } else {
            ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right);
        }
        return ft;
    }


}
