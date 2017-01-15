package com.bawei.wangchuanting.recyclerviewdemos.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bawei.wangchuanting.recyclerviewdemos.R;
import com.bawei.wangchuanting.recyclerviewdemos.activity.MainActivity;
import com.bawei.wangchuanting.recyclerviewdemos.adapter.MyAdapter;
import com.bawei.wangchuanting.recyclerviewdemos.adapter.ViewHolder;
import com.bawei.wangchuanting.recyclerviewdemos.beans.Goods;
import com.bawei.wangchuanting.recyclerviewdemos.net.HttpUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：${王传亭} on 2017/1/9 17:56
 */

public class GoodsTypeFragment extends Fragment {
    private final String url = "http://mock.eoapi.cn/success/4q69ckcRaBdxhdHySqp2Mnxdju5Z8Yr4";
    private MainActivity mContext;
    private List<Goods.RsBean> goodsList;
    private CallBackListener callBackListener;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            goodsList = new ArrayList<Goods.RsBean>();
            goodsList = (List<Goods.RsBean>) msg.obj;
            LinearLayoutManager manager = new LinearLayoutManager(mContext);
            goodsType.setLayoutManager(manager);
            MyAdapter adapter = new MyAdapter<Goods.RsBean>(mContext, R.layout.goodstype_item, goodsList) {

                @Override
                public void conver(ViewHolder viewHodler, Goods.RsBean o) {
                    viewHodler.setText(R.id.typeName, o.getDirName());
                    viewHodler.setOnItemClickLisenter(new ViewHolder.OnItemClickLisenter() {
                        @Override
                        public void onItemClickLisenter(View view, int position) {
                            List<Goods.RsBean.ChildrenBeanX> childrenList = goodsList.get(position).getChildren();
                            callBackListener.callBack(childrenList);
                        }
                    });
                }
            };
            goodsType.setAdapter(adapter);
        }
    };
    private RecyclerView goodsType;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = (MainActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_goodstype, container, false);
//        goodsList = mContext.getGoodsList();

        goodsType = (RecyclerView) view.findViewById(R.id.rv_goodsType);

        initData(url);
        return view;
    }

    public void initData(String url) {
        HttpUtils httpUtils = HttpUtils.getHttpUtils();
        httpUtils.loadDataFromNet(url, Goods.class, new HttpUtils.OnCallBack<Goods>() {
            @Override
            public void onSuccess(Goods result) {
                List<Goods.RsBean> rsList = result.getRs();
                Message msg = mHandler.obtainMessage();
                msg.obj = rsList;
                mHandler.sendMessage(msg);
            }

            @Override
            public void onFail() {

            }
        });
    }

    public void getListener(CallBackListener callBackListener) {
        this.callBackListener = callBackListener;
    }

    public interface CallBackListener {
        void callBack(List<Goods.RsBean.ChildrenBeanX> childrenList);
    }
}
