package com.lqcode.lucytv.fragment;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.liqiong.lucy.module.impl.LucyController;
import com.lqcode.lucytv.R;
import com.lqcode.lucytv.activity.DetilsActivity;
import com.lqcode.lucytv.entity.Entity;
import com.lqcode.lucytv.entity.TVItem;
import com.lqcode.lucytv.network.LiveRequest;
import com.lqcode.lucytv.ui.OnRecyclerItemClick;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/4.
 */
public class LiveFragment extends BaseFragment implements OnRecyclerItemClick {
    private TVListAdapter mAdapter;
    private List<TVItem> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.fragment_list_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        LiveDataRequetNet();
        mAdapter = new TVListAdapter(list, this);
        recyclerView.setAdapter(mAdapter);
        return view;
    }

    /**
     *
     */
    private void LiveDataRequetNet() {
        new LiveRequest() {
            @Override
            public void _onSuccess(final String result) {
                LucyController.uiHelp.post(new Runnable() {
                    @Override
                    public void run() {
                        list.addAll(JSON.parseArray(result, TVItem.class));
                        mAdapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void _onFail(String result) {
                LucyController.uiHelp.toast("fail!");
            }
        };
    }

    @Override
    public void onItemClick(View view, Entity data) {
        TextView liveNameText = (TextView) view.findViewById(R.id.listitem_name);
        liveNameText.setTransitionName("CCTVTextView");
        TVItem item = (TVItem) data;
        Intent mIntent = new Intent(getContext(), DetilsActivity.class);
        mIntent.putExtra("LiveName", item.getLiveName());
        startActivity(mIntent, ActivityOptions.makeSceneTransitionAnimation((Activity) getContext(), liveNameText, liveNameText.getTransitionName()).toBundle());

    }
}