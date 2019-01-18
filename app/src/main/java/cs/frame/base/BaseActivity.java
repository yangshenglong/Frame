package cs.frame.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import butterknife.BindView;
import butterknife.ButterKnife;
import cs.cs.com.frame.R;

/**
 * Created by yangshenglong on 16/11/22.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Nullable
    @BindView(R.id.rl_back_renwu)
    public RelativeLayout backLayout;
    @Nullable
    @BindView(R.id.title_bar_tv)
    public TextView titleTv;
    @Nullable
    @BindView(R.id.menu_icon_img)
    public ImageView menuIconmg;
    @Nullable
    @BindView(R.id.title_bar_img)
    public ImageView barImg;
    @Nullable
    @BindView(R.id.son_esc_layout)
    public RelativeLayout rightMenulayout;
    //webview的X
    @Nullable
    @BindView(R.id.title_webview_canacle)
    public RelativeLayout cancleLayout;
    //是否显示title图片
    public boolean isShowTitleImg;
    //是否显示左侧返回键
    public boolean isShowFinish=true;
    //是否显示右侧menu图标
    public boolean isRightMenu=false;
    //是否显示webview 的X
    public boolean isShowCancle=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //禁止横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(setLayout());
        ButterKnife.bind(this);
        if(cancleLayout!=null){
            cancleLayout.setVisibility(View.GONE);
            cancleLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    doWebCanaleClick();
                }
            });
        }
        initView();
        initData();
        initTitle();
        if(isShowFinish){
            if(backLayout!=null){
                backLayout.setVisibility(View.VISIBLE);
            }
        }else{
            if(backLayout!=null) {
                backLayout.setVisibility(View.GONE);
            }
        }
        if(rightMenulayout!=null) {
            rightMenulayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    doMenuClick();
                }
            });
        }



    }

    //初始化组件
    public abstract void initView();
    //初始化数据的方法
    public abstract void initData();
    //设置布局的
    public abstract int setLayout();


    //设置title文字
    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        if(titleTv!=null){
            titleTv.setText(title);
            if(barImg!=null){
                if(isShowTitleImg){
                    barImg.setVisibility(View.VISIBLE);
                    titleTv.setVisibility(View.GONE);
                }else{

                    barImg.setVisibility(View.GONE);
                    titleTv.setVisibility(View.VISIBLE);
                }
            }

        }
        if(isRightMenu){
            if(rightMenulayout!=null){
                rightMenulayout.setVisibility(View.VISIBLE);
            }
        }else{
            if(rightMenulayout!=null){
                rightMenulayout.setVisibility(View.GONE);
            }
        }
    }

    public void initMenuDrawable(@DrawableRes int drawable){
        if(menuIconmg!=null){
            if(drawable!=0){
                menuIconmg.setVisibility(View.VISIBLE);
                menuIconmg.setImageDrawable(getResources().getDrawable(drawable));
            }

        }

    }
    public void doMenuClick(){

    }

    public <T extends View>  T bindView(int id){
        return (T)findViewById(id);
    }

    //初始化title
    public void initTitle(){
        if(backLayout!=null){
            backLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    public void isShowWebViewCancle(boolean isShowCancle){
        if(isShowCancle){
            if(cancleLayout!=null){
                cancleLayout.setVisibility(View.VISIBLE);
            }else{
                if(cancleLayout!=null){
                    cancleLayout.setVisibility(View.GONE);
                }
            }
        }
    }
    //webview　上Ｘ的点击事件　
    public void doWebCanaleClick(){

    }
}
