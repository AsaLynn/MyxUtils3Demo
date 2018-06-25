package com.zxn.xutils3;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.xutils.common.Callback;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.File;
import java.util.List;

class DbRvAadapter extends RecyclerView.Adapter<MyViewHolder> {

    private List<NewsEntity> mFeedsBeanList;
    private ImageOptions imageOptions;
    private String TAG = "MyRvAadapter";
    private int mPosition;

    public int getmPosition() {
        return mPosition;
    }

    public DbRvAadapter(List<NewsEntity> feedsBeanList) {
        mFeedsBeanList = feedsBeanList;
        //通过ImageOptions.Builder().set方法设置图片的属性

        ImageOptions.Builder builder = new ImageOptions.Builder();
        builder.setRadius(20);//设置拐角弧度
        imageOptions = builder
                .setFadeIn(true)//淡入效果
//                .setCircular(true) //设置图片显示为圆形
                .setSquare(true) //设置图片显示为正方形
//                .setCrop(true).setSize(200, 200) //设置大小
//                .setAnimation(animation) //设置动画
//                .setFailureDrawable(Drawable failureDrawable) //设置加载失败的动画
//                .setFailureDrawableId(R.mipmap.ic_launcher_round) //以资源id设置加载失败的动画
//                    .setLoadingDrawable(Drawable loadingDrawable) //设置加载中的动画
//                    .setLoadingDrawableId( int loadingDrawable) //以资源id设置加载中的动画
                .setIgnoreGif(false) //忽略Gif图片
//                .setParamsBuilder(ParamsBuilder paramsBuilder) //在网络请求中添加一些参数
                .setUseMemCache(true) //设置使用MemCache，默认true
                .build();//生成ImageOptions
    }

    //创建viewholder
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv, null));
    }

    //绑定viewholer,绑定数据
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        //mPosition = position;
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mPosition = holder.getAdapterPosition();
                return false;
            }
        });
        NewsEntity bean = mFeedsBeanList.get(position);
        holder.tv_title.setText(bean.getSubject());
        holder.tv_content.setText(bean.getSummary());
        String url = "http://litchiapi.jstv.com".concat(bean.getCover());
        //加载网络图片
        //loadImage(url,holder);
        //有缓存则读取缓存
//        loadImage2(url, holder);
        loadImage3(url, holder);
    }

    private void loadImage3(String url, MyViewHolder holder) {
        x.image().bind(holder.imageView, url, imageOptions, new Callback.CommonCallback<Drawable>() {
            @Override
            public void onSuccess(Drawable result) {
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

    private void loadImage2(String url, final MyViewHolder holder) {
        x.image().loadFile(url, imageOptions, new Callback.CacheCallback<File>() {
            @Override
            public boolean onCache(File result) {
                //在这里可以做图片另存为等操作
                Log.i("JAVA", "file：" + result.getPath() + result.getName());
                holder.imageView.setImageURI(Uri.fromFile(result));
                return true; //相信本地缓存返回true
            }

            @Override
            public void onSuccess(File result) {
                Log.i(TAG, "onSuccess: ".concat(result.getAbsolutePath()));
                holder.imageView.setImageURI(Uri.fromFile(result));
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

    private void loadImage(String url, final MyViewHolder holder) {
        x.image().loadDrawable(url, imageOptions, new Callback.CommonCallback<Drawable>() {
            @Override
            public void onSuccess(Drawable result) {
                holder.imageView.setImageDrawable(result);
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

    //获取条目个数
    @Override
    public int getItemCount() {
        return mFeedsBeanList == null ? 0 : mFeedsBeanList.size();
    }
}
