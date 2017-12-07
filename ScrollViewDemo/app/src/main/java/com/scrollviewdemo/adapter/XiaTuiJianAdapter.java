package com.scrollviewdemo.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.scrollviewdemo.R;
import com.scrollviewdemo.hoder.HolderImageLoader;
import com.scrollviewdemo.hoder.MulitiTypeSupport;
import com.scrollviewdemo.hoder.RecyclerCommonAdapter;
import com.scrollviewdemo.hoder.ViewHolders;
import com.scrollviewdemo.model.GankGay;

import java.util.List;

/**
 * Created by weijinran ，Email 994425089@qq.com，on 2017/10/24.
 * Describe:
 * PS: Not easy to write code, please indicate.
 */

public class XiaTuiJianAdapter extends RecyclerCommonAdapter<GankGay.ResultsBean> {
    Context mContext;

    public XiaTuiJianAdapter(Context context, List<GankGay.ResultsBean> mDatas, MulitiTypeSupport<GankGay.ResultsBean> typeSupport) {
        super(context, mDatas, typeSupport);
        this.mContext = context;

    }

    @Override
    protected void convert(ViewHolders holder, final GankGay.ResultsBean item, final int position) {
        holder.setText(R.id.tvTitle, item.getType());
        holder.setText(R.id.tvdea, item.getDesc() + "");

    }

}
