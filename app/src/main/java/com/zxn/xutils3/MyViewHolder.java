package com.zxn.xutils3;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

    @ViewInject(R.id.iv)
    ImageView imageView;

    @ViewInject(R.id.tv_title)
    TextView tv_title;

    @ViewInject(R.id.tv_content)
    TextView tv_content;

    public MyViewHolder(View itemView) {
        super(itemView);
//        x.view().inject(this.itemView);
        x.view().inject(this, this.itemView);
        itemView.setOnCreateContextMenuListener(this);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("你想干啥？");
        menu.add(0, 0, Menu.NONE, "删除");
        menu.add(0, 1, Menu.NONE, "修改");
    }

}
