package com.example.administrator.tabhidetest;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.hardware.display.DisplayManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.administrator.tabhidetest.adapter.ViewHolderAdapter;
import com.example.administrator.tabhidetest.bean.NewsItem;
import com.example.administrator.tabhidetest.view.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luore on 2016/9/3.
 */
public class MessageActivity extends Activity {

    private ListView mlistView;
    private LinearLayout mBottom_bar;
    private LinearLayout mHead_bar;
    private ViewHolderAdapter mAdapter;
    private RefreshLayout mSr_refresh;
    private Button mEmptyFresh;
    private boolean isRunning = false;


    private ObjectAnimator mHeaderAnimator;
    private ObjectAnimator mBottomAnimator;
    private ImageButton mIb_explore;
    private ArrayList<Integer> imgs = new ArrayList<>();
    private List<NewsItem> mDates;
    private View header;
    private View footview;
    private TextView foot_tv_msg;
    private TextView head_tv_msg;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        initData();
        initView();
        bindEvents();
    }


    private void initView() {

        mSr_refresh = (RefreshLayout) findViewById(R.id.sr_refresh);
        mlistView = (ListView) findViewById(R.id.listView);
        //底部导航栏
        mBottom_bar = (LinearLayout) getParent().findViewById(R.id.bottom);
        //顶部搜索栏
        mHead_bar = (LinearLayout) findViewById(R.id.head_bar);

        mEmptyFresh = (Button) findViewById(R.id.empty_fresh);

        header = LayoutInflater.from(getApplicationContext()).inflate(R.layout.lv_headerview, mlistView, false);
        head_tv_msg = (TextView) header.findViewById(R.id.message_tv_msg);
        head_tv_msg.setText("测试新闻");
        mlistView.addHeaderView(header);

//        footview = LayoutInflater.from(getApplicationContext()).inflate(R.layout.lv_footview, mlistView, false);
//        foot_tv_msg = (TextView) footview.findViewById(R.id.tv_loadmore);
//        mlistView.addFooterView(footview);
        mAdapter = new ViewHolderAdapter(getApplicationContext(), mDates);
        mlistView.setAdapter(mAdapter);

        //设置下拉刷新加载圈的颜色
        mSr_refresh.setColorSchemeColors(getResources().getColor(R.color.refresh_circle));
        //设置下拉加载圈出现距离顶部的位置
        mSr_refresh.setDistanceToTriggerSync(getResources().getDimensionPixelOffset(R.dimen.swipe_progress_appear_offset));
        //设置下拉加载圈转动时距离顶部的位置
        mSr_refresh.setProgressViewEndTarget(true, getResources().getDimensionPixelOffset(R.dimen.swipe_progress_to_top));

    }

    private void initData() {
        imgs.add(R.drawable.img1);
        imgs.add(R.drawable.img2);
        imgs.add(R.drawable.img3);
        imgs.add(R.drawable.img4);
        imgs.add(R.drawable.img5);
        imgs.add(R.drawable.img6);
        imgs.add(R.drawable.img7);
        imgs.add(R.drawable.img8);
        imgs.add(R.drawable.img9);
        imgs.add(R.drawable.img10);
        mDates = refreshDate();
    }

    private void bindEvents() {
        mSr_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSr_refresh.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        List<NewsItem> newsItems = refreshDate();
                        mAdapter.addDate(newsItems, true);
                        mAdapter.notifyDataSetChanged();
                        //调用该方法结束刷新，否则加载圈会一直在
                        mSr_refresh.setRefreshing(false);
                    }
                }, 1000);
            }
        });
        mlistView.setOnTouchListener(new View.OnTouchListener() {
            private float mEndY;
            private float mStartY;
            private int direction;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mStartY = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mEndY = event.getY();
                        float v1 = mEndY - mStartY;
                        if (v1 > 3 && !isRunning && direction == 1) {
                            direction = 0;
                            showBar();
                            mStartY = mEndY;
                            return false;
                        } else if (v1 < -3 && !isRunning && direction == 0) {
                            direction = 1;
                            hideBar();
                            mStartY = mEndY;
                            return false;
                        }
                        mStartY = mEndY;

                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return false;
            }
        });

        // 加载上拉加载更多监听器
        mSr_refresh.setOnLoadListener(new RefreshLayout.OnLoadListener() {

            @Override
            public void onLoad() {


                mSr_refresh.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        SystemClock.sleep(1500);
                        List<NewsItem> loadItems = refreshDate();
                        mAdapter.addDate(loadItems, false);
                        mAdapter.notifyDataSetChanged();
                        // 加载完后调用该方法
                        mSr_refresh.setLoading(false);
                    }
                }, 1500);

            }
        });
    }

    private int obtainScreenHight() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    private int obtainViewHight(View view) {
        int width = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);

        int height = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);

        view.measure(width, height);


        return view.getMeasuredHeight();
    }

    public void hideBar() {
        mHeaderAnimator = ObjectAnimator.ofFloat(mHead_bar, "translationY", -mHead_bar.getHeight());
        mBottomAnimator = ObjectAnimator.ofFloat(mBottom_bar, "translationY", mBottom_bar.getHeight());
        mHeaderAnimator.setDuration(300).start();
        mBottomAnimator.setDuration(300).start();
        mHeaderAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                isRunning = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isRunning = false;
            }
        });
    }

    public void showBar() {
        mHeaderAnimator = ObjectAnimator.ofFloat(mHead_bar, "translationY", 0);
        mBottomAnimator = ObjectAnimator.ofFloat(mBottom_bar, "translationY", 0);

        mHeaderAnimator.setDuration(300).start();
        mBottomAnimator.setDuration(300).start();
    }

    //模拟拉取数据
    private List<NewsItem> refreshDate() {
        List<NewsItem> newsItems = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            NewsItem newsItem = new NewsItem(imgs.get((int) (Math.random() * 10)), "Title" + i, (int) (Math.random() * 100), "xianyu" + i, System.currentTimeMillis(), true);
            newsItems.add(newsItem);
        }
        return newsItems;
    }
}
