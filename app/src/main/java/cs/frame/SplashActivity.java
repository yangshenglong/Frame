package cs.frame;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;



/**
 * 欢迎页
 *
 * @author lenovo
 */
public class SplashActivity extends FragmentActivity {

	private static final String TAG = "SplashActivity";
	private boolean finished = false;
	private Uri uridata;
	private boolean GotoWhere;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		MobclickAgent.setDebugMode(true);
		uridata = this.getIntent().getData();
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				if (finished) {
					return;
				}
				if (uridata != null) {
					startPointActivity();
				} else {
//					getToken();
				}
			}
		}, 2000);
	}

	@Override
	public void onBackPressed() {
		finished = true;
		super.onBackPressed();
	}

	private void startPointActivity() {
		Intent intent = new Intent();
		intent.putExtra("type", 4);
		if (uridata != null) {
			String action = uridata.getQueryParameter("action");
			String mydata = uridata.getQueryParameter("data");
			if ("register".equals(action)) {
				//注册
//				intent.setClass(SplashActivity.this, RegistActivity.class);
			} else {
				//投资详情
//				intent.setClass(SplashActivity.this, InvestDetailActivity.class);
//				intent.putExtra(ExtraConfig.IntentExtraKey.PRODUCT_ID, mydata);
			}
		}
		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		finish();
	}

//	private void getToken() {
//		new TokenController().reqCheckToken(PreferenceCache.getToken(), new RequestCallback<String>() {
//			@Override
//			public void onSuccess(String s) {
//				//返回0(token为空) 返回1(token过期或其他原因)返回2(token正确)
//				if (s.equals("2")) {
//					if (PreferenceCache.getGestureFlag()) {
//						Intent intent = new Intent(SplashActivity.this, GestureTestActivity.class);
//						intent.putExtra("gestureFlg", 3);
//						intent.putExtra("flag", 98);
//						startActivity(intent);
//						finish();
//					} else {
//						startActivity(new Intent(SplashActivity.this, MainActivity.class));
//						finish();
//					}
////					GotoWhere = PreferenceCache.getGuidePage();
////					if (GotoWhere){
////						//引导页
////						PreferenceCache.putGuidePage(false);
////						startActivity(new Intent(SplashActivity.this, GuideActivity.class));
////						finish();
////					}else{
////						if (PreferenceCache.getGestureFlag()) {
////							Intent intent = new Intent(SplashActivity.this, GestureTestActivity.class);
////							intent.putExtra("gestureFlg", 3);
////							intent.putExtra("flag", 98);
////							startActivity(intent);
////							finish();
////						} else {
////							startActivity(new Intent(SplashActivity.this, MainActivity.class));
////							finish();
////						}
////					}
//				} else {
//					startActivity(new Intent(SplashActivity.this, MainActivity.class));
//					finish();
////					GotoWhere = PreferenceCache.getGuidePage();
////					if (GotoWhere){
////						//引导页
////						PreferenceCache.putGuidePage(false);
////						startActivity(new Intent(SplashActivity.this, GuideActivity.class));
////						finish();
////					}else{
////						startActivity(new Intent(SplashActivity.this, MainActivity.class));
////						finish();
////					}
//				}
//			}
//
//			@Override
//			public void onFailure(String error) {
//				if (!StringUtil.isEmpty(error)) {
//					AlertUtil.t(SplashActivity.this, error);
//				}
//			}
//		});
//	}

	public String getVersion1() {
		String version1 = "";
		PackageManager manager = this.getPackageManager();
		PackageInfo info;
		try {
			info = manager.getPackageInfo(getPackageName(), 0);
			version1 = info.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return version1;

	}
}
