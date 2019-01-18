package cs.frame.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by yangshenglong on 16/11/22.
 */

public abstract class BaseFragment extends Fragment {
    public View rootView;

    private Context context;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {

        if (rootView == null) {
            rootView=inflater.inflate(setLayout(),container,false);
            ButterKnife.bind(this, rootView);
            initView(rootView);
            initData();
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        //必须执行的代码
        initMustData();
        return rootView;
    }

    public void initMustData(){

    }

    //绑定布局
    public abstract int setLayout();
    //初始化
    public abstract void initView(View view);
    //逻辑代码
    public abstract void initData();


}
