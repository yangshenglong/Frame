package cs.frame.welcome;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.widget.ImageView;


import butterknife.BindView;

import cs.cs.com.frame.R;
import cs.frame.MainActivity;
import cs.frame.util.SharedPreferencesUtils;

public class WelcomeActivity extends Activity {

    @BindView(R.id.iv_welcome)
    public ImageView ivWelcomeImg;

    private timeCount countTime;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //        sp = getSharedPreferences("Launcher", MODE_PRIVATE);
//        boolean isFirst = sp.getBoolean("isFirst", true);
//        //判断
//        if (isFirst) {
//            Intent intent = new Intent(this, GuideActivity.class);
//            startActivity(intent);
//            finish();
//        }
        setContentView(R.layout.activity_welcome);

        countTime = new timeCount(3000, 1000);
        //   boolean isFirst = sp.getBoolean("isFirst", true);
        //   if (!isFirst) {
        countTime.start();
        //    }
    }






    class timeCount extends CountDownTimer {

        public timeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {

        }

        @Override
        public void onFinish() {
            String token = SharedPreferencesUtils.getToken(WelcomeActivity.this);

            if (TextUtils.isEmpty(token)) {
//                Intent intent = new Intent(WelcomeActivity.this, LoginAty.class);
//                startActivity(intent);
            } else {
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);

                startActivity(intent);
            }
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
