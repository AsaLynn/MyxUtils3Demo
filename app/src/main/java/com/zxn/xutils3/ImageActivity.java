package com.zxn.xutils3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;

@ContentView(R.layout.activity_image)
public class ImageActivity extends AppCompatActivity {

    @ViewInject(R.id.iv_image)
    ImageView imageView;
    private ImageOptions imageOptions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        imageOptions = new ImageOptions.Builder()
                .setUseMemCache(true)
                .setIgnoreGif(false)//true:gif视为普通,false动图.
                .build();
    }

    @Event(type = View.OnClickListener.class,
            value = {R.id.btn_assets,
                    R.id.btn_file,
                    R.id.btn_sdcard1,
                    R.id.btn_sdcard2,
                    R.id.btn_sdcard3})
    private void test(View view) {
        switch (view.getId()) {
            case R.id.btn_assets:
                //加载资产图片
                x.image().bind(imageView, "assets://assets.gif", imageOptions);
                break;
            case R.id.btn_file:
                //加载本地图片
                //参数1:imageview,参数2:file的uri,参数3:配置参数
                x.image().bind(imageView, new File("/sdcard/file.gif").toURI().toString(), imageOptions);
                break;
            case R.id.btn_sdcard1:
                x.image().bind(imageView, "/sdcard/sdcard1.gif", imageOptions);
                break;
            case R.id.btn_sdcard2:
                x.image().bind(imageView, "file:///sdcard/sdcard2.gif", imageOptions);
                break;
            case R.id.btn_sdcard3:
                x.image().bind(imageView, "file:/sdcard/sdcard3.gif", imageOptions);
                break;
        }
    }
}
