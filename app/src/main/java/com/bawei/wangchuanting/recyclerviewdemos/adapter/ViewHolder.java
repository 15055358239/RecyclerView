package com.bawei.wangchuanting.recyclerviewdemos.adapter;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;


/**
 * 自定义ViewHolder
 *
 * @作者 : 王传亭
 * @日期 : 2017/1/7 16:54
 */
public class ViewHolder extends RecyclerView.ViewHolder {
    //使用集合来存储item上的控件
    private SparseArray<View> mViewList;
    //加载item的布局
    private View mConvertView;
    //记录改变背景的ViewID
    private int mViewID;
    //背景颜色的资源ID
    private int mResID;
    //当前ViewHolder所关联的Adapter
    private MyAdapter mAdapter;
    //记录是否 进行背景颜色更改的监听
    boolean isChange = false;

    // 初始id ==@param itemView
    private ViewHolder(View itemView) {
        super(itemView);
        this.mConvertView = itemView;
        mViewList = new SparseArray<View>();
    }

//    private List<CategoryRecycleViewBean.DatasBean.ClassListBean> subItemListDatas;

    /**
     * 获取ViewHolder的方法
     *
     * @param context  上下文
     * @param layoutID 布局ID
     * @param parent   父控件
     * @return 返回当前的布局ID所对应的ViewHolder的实例
     */
    public static ViewHolder getViewHolder(Context context, int layoutID, ViewGroup parent) {
        //将布局ID转化为视图
        View itemView = LayoutInflater.from(context).inflate(layoutID, parent, false);
        //实例化当前ViewHolder
        ViewHolder viewHolder = new ViewHolder(itemView);
        //返回
        return viewHolder;
    }

    /**
     * 通过ID取控件的方法 对ItemView的重用已经进行了缓存
     *
     * @param viewID 控件的ID
     * @return 返回id所对应的控件
     */
    public <T extends View> T getView(int viewID) {
        //从集合中取控件
        View view = mViewList.get(viewID);
        //如果没有就通过findViewById创建一个,并缓存到集合中
        if (view == null) {
            view = mConvertView.findViewById(viewID);
            mViewList.put(viewID, view);
        }
        return (T) view;
    }

    /**
     * 辅助方法
     * 给TextView控件赋值
     */
    public ViewHolder setText(int viewID, String string) {
        TextView textView = getView(viewID);
        textView.setText(string);
        return this;
    }

    //给Button控件赋值
    public ViewHolder setButtons(int viewID, String string) {
        Button bt_name = getView(viewID);
        bt_name.setText(string);
        return this;
    }

    //给ImageView控件赋值
    public ViewHolder setimageResouse(int viewID, int resID) {
        ImageView imageView = getView(viewID);
        imageView.setImageResource(resID);
        return this;
    }

    /**
     * 给SimpleDraweeView赋值(当使用Fresco加载图片的时候必须使用的控件)
     *
     * @param viewID 传过来的控件Id
     * @param uri    传过来加载图片的网址uri
     * @return 图片赋值后返回
     */
    public ViewHolder setSimpleDraweeView(int viewID, String uri) {
        Uri uri1 = Uri.parse(uri);
        SimpleDraweeView imageView = getView(viewID);
        imageView.setImageURI(uri1);
        return this;
    }

//    /**
//     * 给RecyclerView赋值
//     *
//     * @param viewID  控件Id
//     * @param context 上下文
//     * @param ID      参数 :gc_id
//     * @return
//     */
//    public ViewHolder setRecycleview(int viewID, final Context context, String ID) {
////        Log.e("gc_id", ID + "");
//        //获得RecyclerView控件初始化
//        final RecyclerView recyclerView = getView(viewID);
//        //设置RecyclerView展示方式(布局管理器)
//        recyclerView.setLayoutManager(new GridLayoutManager(context, 3));
//        //调用Presenter请求数据
//        CategoryRecycle_ChildPresenter childPresenter = new CategoryRecycle_ChildPresenter();
//        //获得请求数据的网址
//        String url = String.format(Constant.category_RecycleViewUrl, ID);
//        //调用请求数据的方法
//        childPresenter.loaddataFromNet(url);
//        childPresenter.setResult(new CategoryChildRecycleView() {
//            //请求数据成功返回的Bean
//            @Override
//            public void onSuccess(CategoryRecycleViewBean recycleViewBean) {
//                subItemListDatas = recycleViewBean.getDatas().getClass_list();
//                //绑定自定义适配器
//                recyclerView.setAdapter(new MyAdapter<CategoryRecycleViewBean.DatasBean.ClassListBean>(context, R.layout.category_child_item, subItemListDatas) {
//                    //用户的具体逻辑操作
//                    @Override
//                    public void conver(ViewHolder viewHodler, final CategoryRecycleViewBean.DatasBean.ClassListBean o) {
//                        viewHodler.setButtons(R.id.bt_name, o.getGc_name());
//                        viewHodler.setOnClickLisenter(R.id.bt_name, new View.OnClickListener() {
//                            //recyclerView的条目点击事件
//                            @Override
//                            public void onClick(View v) {
//                                String url = String.format(Constant.category_GoodsDetailsUrl, o.getGc_id());
//                                //跳转到s商品列表页面
//                                Intent intent = new Intent(mContext, GoodsListActivity.class);
//                                intent.putExtra("url", url);
//                                mContext.startActivity(intent);
//                            }
//                        });
//                    }
//                });
//            }
//        });
//
//
//        return this;
//    }

    //条目点击事件
    public ViewHolder setOnItemClickLisenter(final OnItemClickLisenter listener) {
        this.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isChange) {
                    mAdapter.changeItemBackground(mViewID, mResID, getLayoutPosition());
                    mAdapter.notifyDataSetChanged();
                }
                listener.onItemClickLisenter(itemView, getLayoutPosition());
            }
        });
        return this;
    }

    //控件点击事件
    public ViewHolder setOnClickLisenter(int viewID, View.OnClickListener listener) {
        View view = getView(viewID);
        view.setOnClickListener(listener);
        return this;
    }

    public interface OnItemClickLisenter {
        void onItemClickLisenter(View view, int position);
    }

    public ViewHolder setTextViewClickColor(final int viewID, final int colorID) {
        isChange = true;
        this.mViewID = viewID;
        this.mResID = colorID;
        return this;
    }

    public void regist(MyAdapter adapter) {
        this.mAdapter = adapter;
    }
}
