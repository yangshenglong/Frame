package cs.frame.welcome.guide;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;



import java.util.ArrayList;

import cs.cs.com.frame.R;
import cs.frame.base.BaseActivity;
import cs.frame.welcome.WelcomeActivity;


public class GuideActivity extends BaseActivity {

    private ArrayList<View> data;
    private ViewPager viewPager;
    private ImageView ivOver,ivOverOne,ivOvertwo;

    @Override
    public int setLayout() {
        return R.layout.activity_guide;
    }

    @Override
    public void initView() {
        viewPager = (ViewPager) findViewById(R.id.vp_guide);
    }

    @Override
    public void initData() {

        //判断 是不是 第一次 启动
        SharedPreferences.Editor editor = getSharedPreferences("Launcher",MODE_PRIVATE).edit();
        //存储
        editor.putBoolean("isFirst",false);
        editor.commit();

        data = new ArrayList<>();
        View viewOne = getLayoutInflater().inflate(R.layout.view_guideone,null);
        View viewTwo = getLayoutInflater().inflate(R.layout.view_guidetwo,null);
        View viewThree = getLayoutInflater().inflate(R.layout.view_guidethree,null);

        data.add(viewOne);
        data.add(viewTwo);
        data.add(viewThree);

        GuideAdapter adapter = new GuideAdapter(data);
        viewPager.setAdapter(adapter);

        ivOverOne = (ImageView) viewOne.findViewById(R.id.iv_over_one);
        ivOvertwo = (ImageView)viewTwo.findViewById(R.id.iv_two);
        ivOver = (ImageView)viewThree.findViewById(R.id.iv_guide_over);

        ivOverOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GuideActivity.this,WelcomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        ivOvertwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GuideActivity.this,WelcomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        ivOver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GuideActivity.this,WelcomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
