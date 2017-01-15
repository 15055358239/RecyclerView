package com.bawei.wangchuanting.recyclerviewdemos.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.bawei.wangchuanting.recyclerviewdemos.R;
import com.bawei.wangchuanting.recyclerviewdemos.beans.Goods;
import com.bawei.wangchuanting.recyclerviewdemos.fragment.GoodsListFragment;
import com.bawei.wangchuanting.recyclerviewdemos.fragment.GoodsTypeFragment;
import com.bawei.wangchuanting.recyclerviewdemos.net.HttpUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements GoodsTypeFragment.CallBackListener{

    private FragmentManager mFm;
    private List<Goods.RsBean.ChildrenBeanX> childrenBeanXList = new ArrayList<>();

    public List<Goods.RsBean.ChildrenBeanX> getChildrenBeanXList() {
        return childrenBeanXList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFm = getSupportFragmentManager();
        mFm.beginTransaction().add(R.id.goodType, new GoodsTypeFragment()).commit();
    }
    @Override
    public void callBack(List<Goods.RsBean.ChildrenBeanX> childrenList) {
        childrenBeanXList = childrenList;
        mFm.beginTransaction().add(R.id.goodList,new GoodsListFragment()).commit();
    }
}
