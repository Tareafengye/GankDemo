package com.scrollviewdemo.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.photolibrary.util.MeiUtilPhotoUtil;
import com.scrollviewdemo.R;
import com.scrollviewdemo.adapter.XiaTuiJianAdapter;
import com.scrollviewdemo.adapter.YouAdapter;
import com.scrollviewdemo.base.BaseFragment;
import com.scrollviewdemo.hoder.ItemClickListener;
import com.scrollviewdemo.hoder.MulitiTypeSupport;
import com.scrollviewdemo.model.GankGay;
import com.scrollviewdemo.net.DefaultObserver;
import com.scrollviewdemo.net.IdeaApi;
import com.scrollviewdemo.ui.WebActivity;
import com.scrollviewdemo.util.RxToast;
import com.scrollviewdemo.util.Utildip;
import com.scrollviewdemo.util.Utils;
import com.scrollviewdemo.weight.CommomDialog;
import com.scrollviewdemo.weight.DividerGridItemDecoration;
import com.scrollviewdemo.weight.ScrollLinearLayoutManager;
import com.scrollviewdemo.weight.SpaceItemDecoration;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class XiatuijianFragment extends BaseFragment implements MulitiTypeSupport<GankGay.ResultsBean>, XRecyclerView.LoadingListener {
    private XRecyclerView recyclerView;
    private int page = 1;
    private int pageSize = 20;
    private List<GankGay.ResultsBean> gankGays = new ArrayList<>();
    private XiaTuiJianAdapter adapter;

    public XiatuijianFragment() {
    }

    public static XiatuijianFragment newInstance() {
        XiatuijianFragment fragment = new XiatuijianFragment();

        return fragment;
    }

    @Override
    public int initView() {
        return R.layout.fragment_tuijian;
    }

    @Override
    public void onMyActivityCreated() {
        getinit();
    }

    public void getinit() {
        getMyInconme(pageSize, page);
        recyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        recyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        recyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
        recyclerView.setLoadingListener(this);
        recyclerView.setHasFixedSize(true);
        ScrollLinearLayoutManager scrollLinearLayoutManager = new ScrollLinearLayoutManager(getActivity());
        scrollLinearLayoutManager.setScrollEnabled(true);
        recyclerView.setLayoutManager(scrollLinearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new SpaceItemDecoration(getActivity(), SpaceItemDecoration.VERTICAL_LIST, R.drawable.item_love_panding, Utildip.dip2px(getActivity(), 0)));

        adapter = new XiaTuiJianAdapter(getActivity(), gankGays, this);
        recyclerView.setAdapter(adapter);
        adapter.setmItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("url", gankGays.get(position).getUrl() + "");
                intent.putExtra("title", "瞎推荐");
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View v, int position) {
                getAlt(gankGays.get(position).getUrl() + "");

                return false;
            }
        });
    }


    public void getMyInconme(final int pageSize, final int pagenum) {
        IdeaApi.getApiService().getHomeTuiJian("瞎推荐", pageSize + "", pagenum)
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
                        adapter.notifyDataSetChanged();
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
        return R.layout.recycle_item_tuijian;
    }

    @Override
    public void onRefresh() {
        page = 1;
        getMyInconme(pageSize, page);
        recyclerView.refreshComplete();
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onLoadMore() {
        page++;
        getMyInconme(pageSize, page);
        recyclerView.loadMoreComplete();
        adapter.notifyDataSetChanged();
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
