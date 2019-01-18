package cs.frame;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.meiqia.core.callback.OnInitCallback;
import com.meiqia.meiqiasdk.util.MQConfig;
import com.meiqia.meiqiasdk.util.MQIntentBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cc.ibooker.zdialoglib.ChoosePictrueDialog;
import cc.ibooker.zdialoglib.ChoosePictrueUtil;
import cc.ibooker.zdialoglib.ZDialogConstantUtil;
import cs.cs.com.frame.R;
import cs.frame.bean.VersionBean;
import cs.frame.biz.EasyLocalTask;
import cs.frame.net.NewCallBack;
import cs.frame.net.NewNetTool;
import cs.frame.net.StaticValues;
import cs.frame.util.DeviceUtil;
import cs.frame.util.FakeX509TrustManager;
import cs.frame.util.HttpUtil;
import cs.frame.util.LogUtil;
import cs.frame.util.StringUtil;
import cs.frame.util.Utils;
import cs.frame.weight.PromptOkCancel;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private NotificationManager notificationManager;
    private Notification notification;
    RemoteViews view = null;
    private int progress;
    private TextView mTv_takePhoto;
    private ChoosePictrueDialog choosePictrueDialog;
    private Gson mGson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 初始化
        choosePictrueDialog = new ChoosePictrueDialog(this);
        updata();
        init();
    }

    private void init() {
        mTv_takePhoto = (TextView) findViewById(R.id.tv_take_photo);
        mTv_takePhoto.setOnClickListener(this);
        mGson = new Gson();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ZDialogConstantUtil.RESULT_PHOTO_CODE:
                /**
                 * 拍照，获取返回结果
                 */
                closeChoosePictrueDialog();
                Uri photoUri = ChoosePictrueUtil.photoUri;
                if (photoUri != null) {
                    /**
                     * 拍照回调，进行相应的逻辑处理即可
                     */
                    try {
                        String a =encodeBase64File(Utils.getRealFilePath(this,photoUri));
                        Log.d("MainActivity", a);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case ZDialogConstantUtil.RESULT_LOAD_CODE:
                /**
                 * 从相册中选择图片，获取返回结果
                 */
                closeChoosePictrueDialog();
                if (data == null) {
                    return;
                } else {
                    Uri uri = data.getData();// 获取图片是以content开头
                    if (uri != null) {
                        // 进行相应的逻辑操作
                        Toast.makeText(this, "我回来啦.....", Toast.LENGTH_SHORT).show();
                        try {
                           String photo = encodeBase64File(Utils.getRealFilePath(this,uri));
                           Log.d("MainActivity", photo);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
        }
    }

    // 关闭选择图片Dialog
    private void closeChoosePictrueDialog() {
        if (choosePictrueDialog != null)
            choosePictrueDialog.closeChoosePictrueDialog();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            new PromptOkCancel(MainActivity.this) {
//
//                @Override
//                protected void onOk() {
//                    if (StringUtil.isEmpty(vd.getANDROID_DOWNLOAD_LINK())) {
//                        return;
//                    }
//                    downloadApk(vd.getANDROID_DOWNLOAD_LINK());
//                }
//            }.show(MainActivity.this.getString(R.string.new_version_detected), vd.getANDROID_UPTATE_MESSAGE(),
//                    R.string.download_background, R.string.remind_me_later, vd.getANDROID_FORCE(), false);// 如果给传递值就强制下载
//        }


        switch (requestCode) {
            case ZDialogConstantUtil.PERMISSION_CAMERA_REQUEST_CODE:
                if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    choosePictrueDialog.startPhoto();
                } else {
                    Toast.makeText(this, "获取拍照权限失败", Toast.LENGTH_SHORT).show();
                }
                break;
            case ZDialogConstantUtil.PERMISSION_READ_EXTERNAL_STORAGE_REQUEST_CODE:
                if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    choosePictrueDialog.startPhoto();
                } else {
                    Toast.makeText(this, "sdcard中读取数据的权限失败", Toast.LENGTH_SHORT).show();
                }
                break;
            case ZDialogConstantUtil.PERMISSION_WRITE_EXTERNAL_STORAGE_REQUEST_CODE:
                if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    choosePictrueDialog.startPhoto();
                } else {
                    Toast.makeText(this, "写入数据到扩展存储卡(SD)权限失败", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * encodeBase64File:(将文件转成base64 字符串). <br/>
     *
     * @param path 文件路径
     * @return
     * @throws Exception
     * @author guhaizhou@126.com
     * @since JDK 1.6
     */
    public static String encodeBase64File(String path) throws Exception {
        File file = new File(path);
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int) file.length()];
        inputFile.read(buffer);
        inputFile.close();
        return Base64.encodeToString(buffer, Base64.DEFAULT);
    }

    /**
     * url 是假的。。
     */
    VersionBean vd;

    public void updata() {
        String url = StaticValues.VERSION;
        Map<String, String> map = new HashMap<>();
        NewNetTool.getInstance().startGetRequest(this, true, url, map, VersionBean.class, new NewCallBack<VersionBean>() {
            @Override
            public void onSuccess(VersionBean response) {
                if (response == null) {
                    return;
                }
                if (DeviceUtil.getVesionName(MainActivity.this).equals(response.getANDROID_VERSION())) {
                    return;
                }

                vd = response;
                if (ContextCompat.checkSelfPermission(MainActivity.this
                        , Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
                } else {
                    new PromptOkCancel(MainActivity.this) {

                        @Override
                        protected void onOk() {

                            if (StringUtil.isEmpty(vd.getANDROID_DOWNLOAD_LINK())) {
                                return;
                            }
                            downloadApk(vd.getANDROID_DOWNLOAD_LINK());
                        }
                    }.show(MainActivity.this.getString(R.string.new_version_detected), vd.getANDROID_UPTATE_MESSAGE(),
                            R.string.download_background, R.string.remind_me_later, vd.getANDROID_FORCE(), false);// 如果给传递值就强制下载

                }

            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    private void downloadApk(final String url) {
        //忽略https证书（6.0以下手机）
        FakeX509TrustManager.allowAllSSL();

        new EasyLocalTask<Void, File>() {
            @Override
            protected File doInBackground(Void... params) {
                File file = new File(MyApp.CACHE_ROOT_CACHE_DIR +
                        File.separator + StaticValues.APK_NAME);
                try {
                    LogUtil.e(url);
                    notification();
                    HttpUtil.downloadFile(url, file, new HttpUtil.IDownloadCallback() {
                        int i = 0;

                        @Override
                        public void onProgress(long currentSize, long totalSize) {

                            progress = (int) (((float) currentSize / totalSize) * 100);

                            if ((int) (progress / 10) > i) {
                                i = (int) (progress / 10);
                                // 更改进度条
                                notification.contentView.setProgressBar(R.id.progress, (int) (totalSize / 1024 / 1000),
                                        (int) (currentSize / 1024 / 1000), false);
                                // 发送消息
                                notificationManager.notify(101, notification);
                            }
                        }
                    });
                    // HttpUtil.downloadFile(url, file);
                } catch (IOException e) {
                    file = null;
                }
                return file;
            }

            @Override
            protected void onPostExecute(File result) {
                super.onPostExecute(result);
                if (result != null) {
                    notificationManager.cancel(101);// notification关闭不显示
//                    installApk(result);
                    install(MainActivity.this, result);
                }
            }
        }.execute();
    }

    /**
     * @Description 安装APK
     */
    public void install(Context context, File file) {
//		File file = new File(
//				Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
//				, JiadhConfig.APK_NAME);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        // 由于没有在Activity环境下启动Activity,设置下面的标签
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 24) { //判读版本是否在7.0以上
            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
            Uri apkUri = FileProvider.getUriForFile(context, "com.zdjr.wealth.fileprovider", file);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file),
                    "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
    }

    private void notification() {
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notification = new Notification(R.mipmap.ic_launcher, "下载新版本", System.currentTimeMillis());

        if (view == null) {
            view = new RemoteViews(getPackageName(), R.layout.notification);
            notification.contentView = view;
            notification.contentView.setProgressBar(R.id.progress, 100, 0, false);
        }
        PendingIntent contentIntent = PendingIntent.getActivity(this, R.string.app_name, new Intent(),
                PendingIntent.FLAG_UPDATE_CURRENT);
        notification.contentIntent = contentIntent;
        notification.flags |= Notification.FLAG_ONGOING_EVENT;// 滑动或者clear都不会清空
        // 获取系统当前时间
        Time t = new Time();
        t.setToNow(); // 取得系统时间。
        int hour = t.hour;
        int minute = t.minute;
        if (hour >= 12) {
            if (hour == 12) {
                notification.contentView.setTextViewText(R.id.time, "下午" + hour + ":" + minute);
            }
            notification.contentView.setTextViewText(R.id.time, "下午" + (hour - 12) + ":" + minute);
        } else {
            notification.contentView.setTextViewText(R.id.time, "上午" + hour + ":" + minute);
        }
        notificationManager.notify(101, notification);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_take_photo:
                takePhoto();
//                MQChat();
                break;
        }
    }

    private void takePhoto() {
        choosePictrueDialog.showChoosePictrueDialog();
    }

    private void MQChat() {
        MQConfig.init(this, "49c5c01e924c4ab1a0532ac7c7f7fe06", new OnInitCallback() {
            @Override
            public void onSuccess(String clientId) {
                Intent intent = new MQIntentBuilder(getBaseContext()).build();
                startActivity(intent);
                Toast.makeText(MainActivity.this, "init success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int code, String message) {
                Toast.makeText(MainActivity.this, "int failure", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
