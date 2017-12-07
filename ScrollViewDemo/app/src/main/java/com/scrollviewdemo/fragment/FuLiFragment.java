package com.scrollviewdemo.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.photolibrary.util.MeiUtilPhotoUtil;
import com.scrollviewdemo.R;
import com.scrollviewdemo.adapter.YouAdapter;
import com.scrollviewdemo.base.BaseFragment;
import com.scrollviewdemo.hoder.ItemClickListener;
import com.scrollviewdemo.hoder.MulitiTypeSupport;
import com.scrollviewdemo.impl.ActionBarClickListener;
import com.scrollviewdemo.model.GankGay;
import com.scrollviewdemo.net.DefaultObserver;
import com.scrollviewdemo.net.IdeaApi;
import com.scrollviewdemo.util.Utils;
import com.scrollviewdemo.weight.CommomDialog;
import com.scrollviewdemo.weight.DividerGridItemDecoration;
import com.scrollviewdemo.weight.TranslucentActionBar;
import com.scrollviewdemo.weight.TranslucentScrollView;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FuLiFragment extends BaseFragment implements MulitiTypeSupport<GankGay.ResultsBean>, XRecyclerView.LoadingListener {
    private XRecyclerView recyclerView;
    private int page = 1;
    private int pageSize = 20;
    private List<GankGay.ResultsBean> gankGays = new ArrayList<>();
    private YouAdapter youAdapter;
    ArrayList<String> banmer = new ArrayList<>();
    Banner banner_home;

    public FuLiFragment() {
    }

    public static FuLiFragment newInstance() {
        FuLiFragment fragment = new FuLiFragment();

        return fragment;
    }

    @Override
    public int initView() {
        return R.layout.fragment_fuli;
    }

    @Override
    public void onMyActivityCreated() {
        getinit();
    }

    public void getinit() {
        getMyInconme(pageSize, page);
        View header = LayoutInflater.from(getActivity()).inflate(R.layout.include_banner, null);
        banner_home = (Banner) header.findViewById(R.id.banner_home);
        recyclerView.addHeaderView(header);
        recyclerView.setLimitNumberToCallLoadMore(2);
        recyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        recyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        recyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
        recyclerView.setLoadingListener(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerGridItemDecoration(getActivity(), R.drawable.divider_love, null));
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        youAdapter = new YouAdapter(getActivity(), gankGays, this);
        recyclerView.setAdapter(youAdapter);
        youAdapter.setmItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                MeiUtilPhotoUtil.onMeizi(getActivity(), gankGays.get(position).getUrl());
            }

            @Override
            public boolean onItemLongClick(View v, int position) {
                getAlt(gankGays.get(position).getUrl() + "");

                return false;
            }
        });
        getBanner();
    }

    public void getInitBanner(final List<GankGay.ResultsBean> list) {
        banmer.clear();
        for (int i = 0; i < list.size(); i++) {
            banmer.add(list.get(i).getUrl() + "");
        }
        banner_home.setBannerStyle(Banner.CIRCLE_INDICATOR_TITLE);
        banner_home.setIndicatorGravity(Banner.CENTER);
        banner_home.isAutoPlay(true);
        //设置轮播图片间隔时间（不设置默认为2000）
        banner_home.setDelayTime(2000);
        banner_home.setImages(banmer, new Banner.OnLoadImageListener() {
            @Override
            public void OnLoadImage(ImageView view, Object url) {
                Glide.with(getActivity()).load(url).into(view);
            }
        });
        //设置点击事件，下标是从1开始
        banner_home.setOnBannerClickListener(new Banner.OnBannerClickListener() {//设置点击事件
            @Override
            public void OnBannerClick(View view, int position) {
                MeiUtilPhotoUtil.setNetWorkBigPicture(getActivity(), banmer, position);
            }
        });
    }

    public void getMyInconme(final int pageSize, final int pagenum) {
        IdeaApi.getApiService().getContent("福利", pageSize + "", pagenum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<GankGay>(this, false) {
                    @Override
                    public void onSuccess(GankGay bean) {
                        if (pagenum == 1) {
                            gankGays.clear();
                        }
                        recyclerView.loadMoreComplete();
                        recyclerView.refreshComplete();

                        gankGays.addAll(bean.getResults());
                        youAdapter.notifyDataSetChanged();
                    }
                });
    }

    public void getBanner() {
        IdeaApi.getApiService().getMyincome("福利", "6", 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<GankGay>(this, false) {
                    @Override
                    public void onSuccess(GankGay bean) {
                        getInitBanner(bean.getResults());
                    }
                });
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean addRxDestroy(Disposable disposable) {
        return false;
    }

    @Override
    public int getLayoutId(GankGay.ResultsBean item) {
        return R.layout.recycle_item;
    }

    @Override
    public void onRefresh() {
        page = 1;
        getMyInconme(pageSize, page);
        recyclerView.refreshComplete();
        youAdapter.notifyDataSetChanged();

    }

    @Override
    public void onLoadMore() {
        page++;
        getMyInconme(pageSize, page);
        recyclerView.loadMoreComplete();
        youAdapter.notifyDataSetChanged();
    }

    public void getAlt(final String url) {
        new CommomDialog(getActivity(), R.style.dialog, "是否保存到本地", "确定", new CommomDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm) {
                if (confirm) {
                    Utils.SavePicture(System.currentTimeMillis() + "", url);
                    dialog.dismiss();
                } else if (confirm == false) {

                    dialog.dismiss();
                }

            }
        })
                .setTitle("提示").show();
    }
}
