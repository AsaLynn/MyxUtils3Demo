package com.zxn.xutils3;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zxn.xutils3.model.NewsInfo;

import org.xutils.DbManager;
import org.xutils.common.Callback;
import org.xutils.ex.DbException;
import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.fragment_contet)
public class MyFragment extends Fragment {

    private String tag = this.getClass().getSimpleName();
    private String TAG = this.getClass().getSimpleName();

    @ViewInject(R.id.tv_frag)
    private TextView textView;

    @ViewInject(R.id.rv)
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

//        return inflater.inflate(R.layout.fragment_contet, null);

        //参数1:Fragment,参数2:LayoutInflater,参数3:ViewGroup
        //inject(Object fragment, LayoutInflater inflater, ViewGroup container)
        return x.view().inject(this, inflater, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (null != textView) {
            textView.setText("this is text in fragment!");
        }
        //条目大小固定
        recyclerView.setHasFixedSize(true);
        //设置布局管理器
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        //增加条目分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));


        sendGet();
//        sendGet2();
//        sendPost();
    }

    //发送post请求
    private void sendPost() {
        String url = "http://litchiapi.jstv.com/api/GetFeeds?";
        //        String baseUrl = "http://v5.pc.duomi.com/search-ajaxsearch-searchall?kw=liedehua&pi=1&pz=10";
//        String baseUrl = "http://v5.pc.duomi.com/search-ajaxsearch-searchall?";
        RequestParams requestParams = new RequestParams(url);
//        params.addHeader("head","android"); //为当前请求添加一个头
//        requestParams.addBodyParameter("kw", "liedehua");
//        requestParams.addBodyParameter("pi", "1");
//        requestParams.addBodyParameter("pz", "15");
        requestParams.addBodyParameter("column", "4");
        requestParams.addBodyParameter("PageSize", "20");
        requestParams.addBodyParameter("pageIndex", "1");
        requestParams.addBodyParameter("val", "100511D3BE5301280E0992C73A9DEC41");
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //解析result
                Log.i(TAG, "onSuccess: ");
                Log.i(TAG, result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i(TAG, "onError: ");
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
                Log.i(TAG, "onFinished: ");
            }
        });
    }

    //发送get请求带缓存
    private void sendGet2() {
        String url = "http://litchiapi.jstv.com/api/GetFeeds?";
        RequestParams requestParams = new RequestParams(url);
        //2,添加参数
        requestParams.addParameter("column", "4");
        requestParams.addParameter("PageSize", "20");
        requestParams.addParameter("pageIndex", "1");
        requestParams.addParameter("val", "100511D3BE5301280E0992C73A9DEC41");
// 默认缓存存活时间, 单位:毫秒（如果服务器没有返回有效的max-age或Expires则参考）
        requestParams.setCacheMaxAge(1000 * 60);

        x.http().get(requestParams, new Callback.CacheCallback<String>() {
            private boolean hasError = false;
            private String result = null;

            @Override
            public boolean onCache(String result) {
                //得到缓存数据, 缓存过期后不会进入
                this.result = result;
                Log.i(TAG, "onCache");
                Log.i(TAG, result);
                //true: 信任缓存数据, 不再发起网络请求; false不信任缓存数据
                return true;
            }

            @Override
            public void onSuccess(String result) {
                //如果服务返回304或onCache选择了信任缓存,这时result为null
                Log.i("JAVA", "开始请求");
                if (result != null) {
                    this.result = result;
                    Log.i(TAG, "onSuccess");
                    Log.i(tag, result);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                hasError = true;
                Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
                if (ex instanceof HttpException) { //网络错误
                    HttpException httpEx = (HttpException) ex;
                    int responseCode = httpEx.getCode();
                    String responseMsg = httpEx.getMessage();
                    String errorResult = httpEx.getResult();
                    //...
                } else { //其他错误
                    //...
                }
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                if (!hasError && result != null) {
                    //成功获取数据
                    Toast.makeText(x.app(), result, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    //发送get请求
    private void sendGet() {
        //1,封装请求参数,
        //http://litchiapi.jstv.com/api/GetFeeds?column=4&PageSize=20&pageIndex=1&val=100511D3BE5301280E0992C73A9DEC41
        //column=4&PageSize=20&pageIndex=1&val=100511D3BE5301280E0992C73A9DEC41
        String url = "http://litchiapi.jstv.com/api/GetFeeds?";
        RequestParams requestParams = new RequestParams(url);
        //2,添加参数
        requestParams.addParameter("column", "4");
        requestParams.addParameter("PageSize", "20");
        requestParams.addParameter("pageIndex", "1");
        requestParams.addParameter("val", "100511D3BE5301280E0992C73A9DEC41");
        //3,发送网络请求
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //请求成功回调
                Log.i(tag, "onSuccess: currentThread--->" + Thread.currentThread().getName());
                Log.i(tag, result);
                Gson gson = new Gson();
                NewsInfo info = gson.fromJson(result, NewsInfo.class);
                List<NewsInfo.ParamzBean.FeedsBean> feedsBeanList = info.getParamz().getFeeds();
                MyRvAadapter myRvAadapter = new MyRvAadapter(feedsBeanList);
                recyclerView.setAdapter(myRvAadapter);
                saveDB(feedsBeanList);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                //请求失败回调
                Log.i(tag, "onError: ");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                //请求取消回调
                Log.i(tag, "onCancelled: ");
            }

            @Override
            public void onFinished() {
                //任务完成
                Log.i(tag, "onFinished: ");
            }
        });
    }

    private void saveDB(List<NewsInfo.ParamzBean.FeedsBean> feedsBeanList) {
        MyApplication application = (MyApplication) getActivity().getApplication();
        DbManager db = application.getDb();
        try {
            List<NewsEntity> entityList = db.findAll(NewsEntity.class);
            if (entityList == null || entityList.size() == 0) {
                ArrayList<NewsEntity> list = new ArrayList<>();
                for (int i = 0; i < feedsBeanList.size(); i++) {
                    NewsInfo.ParamzBean.FeedsBean.DataBean data = feedsBeanList.get(i).getData();
                    NewsEntity entity = new NewsEntity();
                    entity.setSubject(data.getSubject());
                    entity.setCover(data.getCover());
                    entity.setSummary(data.getSummary());
                    list.add(entity);
                }
                //保存一个集合的数据.
                db.save(list);
            } else {
                Toast.makeText(getContext(), "data 已经存在", Toast.LENGTH_LONG).show();
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

}
