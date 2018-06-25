package com.zxn.xutils3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

@ContentView(R.layout.activity_db)
public class DbActivity extends AppCompatActivity {
    @ViewInject(R.id.rv_db)
    RecyclerView recyclerView;
    private DbManager db;
    private DbRvAadapter adapter;
    private List<NewsEntity> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        recyclerView
                .setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        initData();

        //给View注册上下文菜单
//        this.registerForContextMenu(recyclerView);
    }

    private void initData() {
        MyApplication application = (MyApplication) getApplication();
        db = application.getDb();
        try {
            list = db.findAll(NewsEntity.class);
            adapter = new DbRvAadapter(list);
            recyclerView.setAdapter(adapter);
        } catch (DbException e) {
            Toast.makeText(this, "查询数据失败了!哦", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    //重写Activity或者Fragment中的onCreateContextMenu方法,创建
//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        super.onCreateContextMenu(menu, v, menuInfo);
//        menu.setHeaderTitle("你想干啥？");
//        menu.add(0, 0, Menu.NONE, "删除");
//        menu.add(0, 1, Menu.NONE, "修改");
//    }

    ////重写Activity或者Fragment中的onContextItemSelected方法，实现菜单事件监听
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        CharSequence title = item.getTitle();
        int position = adapter.getmPosition();
        NewsEntity entity = list.get(position);
        switch (item.getItemId()) {
            case 0:
                Toast.makeText(this, title, Toast.LENGTH_SHORT).show();
                try {
                    db.delete(entity);
                    list = db.findAll(NewsEntity.class);
                    adapter = new DbRvAadapter(list);
                    recyclerView.setAdapter(adapter);
                    //adapter.notifyDataSetChanged();
                } catch (DbException e) {
                    Toast.makeText(this, "删除失败了", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                break;
            case 1:
                //Toast.makeText(this, title, Toast.LENGTH_SHORT).show();
                entity.setSubject("简单粗暴!");
                try {
                    db.update(entity,"subject");
                    list = db.findAll(NewsEntity.class);
                    adapter = new DbRvAadapter(list);
                    recyclerView.setAdapter(adapter);
                } catch (DbException e) {
                    Toast.makeText(this, "修改失败了", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                break;
            default:
                return super.onContextItemSelected(item);
        }
        return true;
    }
}
