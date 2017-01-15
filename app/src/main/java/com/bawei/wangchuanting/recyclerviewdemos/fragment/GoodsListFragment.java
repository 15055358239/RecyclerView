package com.bawei.wangchuanting.recyclerviewdemos.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bawei.wangchuanting.recyclerviewdemos.R;
import com.bawei.wangchuanting.recyclerviewdemos.activity.MainActivity;
import com.bawei.wangchuanting.recyclerviewdemos.adapter.MyAdapter;
import com.bawei.wangchuanting.recyclerviewdemos.adapter.ViewHolder;
import com.bawei.wangchuanting.recyclerviewdemos.beans.Goods;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：${王传亭} on 2017/1/9 17:54
 */

public class GoodsListFragment extends Fragment {
    private MainActivity mContext;
    private ArrayList<Goods.RsBean.ChildrenBeanX> childrenBeanXList = new ArrayList<>();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = (MainActivity) context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fargment_goodslist, container, false);
        List<Goods.RsBean.ChildrenBeanX> childrenBeanXList = mContext.getChildrenBeanXList();
        RecyclerView goodsNameList = (RecyclerView) view.findViewById(R.id.goodList);
        GridLayoutManager manager = new GridLayoutManager(mContext,3);
        goodsNameList.setLayoutManager(manager);
        MyAdapter adapter = new MyAdapter<Goods.RsBean.ChildrenBeanX>(mContext,R.layout.goodslist_item,childrenBeanXList) {

            @Override
            public void conver(ViewHolder viewHodler, Goods.RsBean.ChildrenBeanX o) {
                viewHodler.setText(R.id.goodsName,o.getDirName());
//                viewHodler.setRecycleview(R.id.goodsNameList,mContext,o.getChildren());
            }
        };
        return view;
    }
}
