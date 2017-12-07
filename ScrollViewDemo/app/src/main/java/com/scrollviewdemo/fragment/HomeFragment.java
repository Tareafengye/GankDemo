package com.scrollviewdemo.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.photolibrary.util.MeiUtilPhotoUtil;
import com.scrollviewdemo.R;
import com.scrollviewdemo.adapter.YouAdapter;
import com.scrollviewdemo.base.BaseFragment;
import com.scrollviewdemo.hoder.ItemClickListener;
import com.scrollviewdemo.hoder.MulitiTypeSupport;
import com.scrollviewdemo.model.GankGay;
import com.scrollviewdemo.net.DefaultObserver;
import com.scrollviewdemo.net.IdeaApi;
import com.scrollviewdemo.util.RxToast;
import com.scrollviewdemo.util.Utildip;
import com.scrollviewdemo.util.Utils;
import com.scrollviewdemo.weight.CommomDialog;
import com.scrollviewdemo.weight.DividerGridItemDecoration;
import com.scrollviewdemo.weight.ItHqAddTab;
import com.scrollviewdemo.weight.ScrollLinearLayoutManager;
import com.scrollviewdemo.weight.SpaceItemDecoration;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomeFragment extends BaseFragment {

    private List<Fragment> mFragment = new ArrayList<>();
    private String title[] = {"福利", "瞎推荐"};
    private List<String> mTitle = new ArrayList<>();
    TabLayout tabMyOrder;
    ViewPager viewOrdre;

    @Override
    public int initView() {
        return R.layout.fragment_home;
    }

    @Override
    public void onMyActivityCreated() {
        getTItleAdd(title);

    }

    public void getTItleAdd(String title[]) {
        for (int i = 0; i < title.length; i++) {
            mTitle.add(title[i] + "");

        }
        mFragment.add(FuLiFragment.newInstance());
        mFragment.add(XiatuijianFragment.newInstance());
        ItHqAddTab.addTab(tabMyOrder, viewOrdre, mFragment, mTitle, getChildFragmentManager());
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean addRxDestroy(Disposable disposable) {
        return false;
    }
}
