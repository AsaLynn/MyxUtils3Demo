package com.zxn.xutils3;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;

@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @ViewInject(R.id.tv)
    private TextView textView;
    private String tag = this.getClass().getSimpleName();//MainActivity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        x.view().inject(this);
        if (null != textView) {
            textView.setText("this text content!");
        }
    }

//    方法必须私有限定,
//    方法参数形式必须和type对应的Listener接口一致.
//    注解参数value支持数组: value={id1, id2, id3}

    @Event(type = View.OnClickListener.class, value = {R.id.btn, R.id.btn_upload, R.id.btn_download})
    private void test(View view) {
        switch (view.getId()) {
            case R.id.btn_download:
                //下载
                download();
                break;
            case R.id.btn:
                Toast.makeText(this, "按钮被点击了!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_upload:
                //上传
                upload();
                break;
        }
    }

    private void download() {
        String url = "http://169.254.38.24/app2.0.apk";
        RequestParams params = new RequestParams(url);
        //自定义保存路径，Environment.getExternalStorageDirectory()：SD卡的根目录
        params.setSaveFilePath(Environment.getExternalStorageDirectory() + "/myapp/");
        //自动为文件命名
        params.setAutoRename(true);
        x.http().post(params, new Callback.ProgressCallback<File>() {
            @Override
            public void onSuccess(File result) {
                //apk下载完成后，调用系统的安装方法
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(result), "application/vnd.android.package-archive");
                startActivity(intent);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }

            //网络请求之前回调
            @Override
            public void onWaiting() {
            }

            //网络请求开始的时候回调
            @Override
            public void onStarted() {
            }

            //下载的时候不断回调的方法
            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                //当前进度和文件总大小
                Log.i("JAVA", "current：" + current + "，total：" + total);
            }
        });
    }

    private void upload() {
        String url = "http://169.254.38.24/MyUploadServer/servlet/MyUploadServlet";
        Toast.makeText(this, url, Toast.LENGTH_SHORT).show();
        String path1 = "/mnt/sdcard/1.jpg";
        String path2 = "/mnt/sdcard/2.jpg";
        //创建请求参数
        RequestParams params = new RequestParams(url);
        params.setMultipart(true);
        params.addBodyParameter("file", new File(path1));
        params.addBodyParameter("file", new File(path2));
        params.addBodyParameter("name", "zhang101");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }
        });
    }

//    @Event(type = View.OnClickListener.class, value = {R.id.btn})
//    private void upload(View view){
//        String url = "http://169.254.38.24/MyUploadServer/servlet/MyUploadServlet";
//    }

    /**
     * 长按事件
     */
    @Event(type = View.OnLongClickListener.class, value = R.id.btn)
    private boolean testOnLongClickListener(View v) {
        Toast.makeText(this, "按钮被长按了!", Toast.LENGTH_SHORT).show();
        return true;
    }

}
