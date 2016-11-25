package com.example.administrator.tabhidetest;


import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

public class MainActivity extends TabActivity implements OnClickListener {
    private TabHost tabhost;
    public static final String TAB_NEAR = "tabNearby";
    public static final String TAB_MESSAGE = "tabMessage";
    public static final String TAB_HOME = "tabHome";
    public static final String TAB_FIND = "tabFind";
    public static final String TAB_USER = "tabUser";
    private LinearLayout li_tab_nearby, li_tab_message, li_tab_home,
            li_tab_find, li_tab_user;
    private ImageView img_nearby, img_message, img_home, img_find, img_user;
    private TextView tv_nearby, tv_message, tv_home, tv_find, tv_user;
    private Activity ac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ac = this;
        initView();
        initEvent();
        //重置底部导航拦
        resetTab();
        //默认选中第一个选项卡
        selectTab(1);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    /**
     * 重置底部导航栏，由于每次点击效果都要改变，先重置成默认的，然后把点击的特殊处理
     */
    private void resetTab() {
        // TODO Auto-generated method stub
        img_nearby.setBackgroundResource(R.drawable.nearby_no);
        img_message.setBackgroundResource(R.drawable.message_no);
        img_home.setBackgroundResource(R.drawable.home_no);
        img_find.setBackgroundResource(R.drawable.find_no);
        img_user.setBackgroundResource(R.drawable.user_no);

        tv_nearby.setTextColor(getResources().getColor(R.color.text30));
        tv_message.setTextColor(getResources().getColor(R.color.text30));
        tv_home.setTextColor(getResources().getColor(R.color.text30));
        tv_find.setTextColor(getResources().getColor(R.color.text30));
        tv_user.setTextColor(getResources().getColor(R.color.text30));

    }

    private void initEvent() {
        // TODO Auto-generated method stub
        li_tab_nearby.setOnClickListener(this);
        li_tab_message.setOnClickListener(this);
        li_tab_home.setOnClickListener(this);
        li_tab_find.setOnClickListener(this);
        li_tab_user.setOnClickListener(this);
    }

    private void initView() {
        // TODO Auto-generated method stub
        li_tab_nearby = (LinearLayout) findViewById(R.id.tab_nearby);
        li_tab_message = (LinearLayout) findViewById(R.id.tab_message);
        li_tab_home = (LinearLayout) findViewById(R.id.tab_home);
        li_tab_find = (LinearLayout) findViewById(R.id.tab_find);
        li_tab_user = (LinearLayout) findViewById(R.id.tab_user);

        img_nearby = (ImageView) findViewById(R.id.iv_nearby);
        img_message = (ImageView) findViewById(R.id.iv_message);
        img_home = (ImageView) findViewById(R.id.iv_home);
        img_find = (ImageView) findViewById(R.id.iv_find);
        img_user = (ImageView) findViewById(R.id.iv_user);

        tv_nearby = (TextView) findViewById(R.id.tv_nearby);
        tv_message = (TextView) findViewById(R.id.tv_message);
        tv_home = (TextView) findViewById(R.id.tv_home);
        tv_find = (TextView) findViewById(R.id.tv_find);
        tv_user = (TextView) findViewById(R.id.tv_user);

        tabhost = getTabHost();
        tabhost.addTab(tabhost.newTabSpec(TAB_NEAR).setIndicator(TAB_NEAR)
                .setContent(new Intent(this, NearbyActivity.class)));
        tabhost.addTab(tabhost.newTabSpec(TAB_MESSAGE)
                .setIndicator(TAB_MESSAGE)
                .setContent(new Intent(this, MessageActivity.class)));
        tabhost.addTab(tabhost.newTabSpec(TAB_HOME).setIndicator(TAB_HOME)
                .setContent(new Intent(this, HomeActivity.class)));
        tabhost.addTab(tabhost.newTabSpec(TAB_FIND).setIndicator(TAB_FIND)
                .setContent(new Intent(this, FindActivity.class)));
        tabhost.addTab(tabhost.newTabSpec(TAB_USER).setIndicator(TAB_USER)
                .setContent(new Intent(this, UserActivity.class)));
    }

    /**
     * 选项卡的选择，同时把选中的选项卡对应的导航栏做特殊处理
     */
    private void selectTab(int i) {
        switch (i) {
            case 1:
                resetTab();
                tabhost.setCurrentTabByTag(TAB_NEAR);
                img_nearby.setBackgroundResource(R.drawable.nearby_select);
                tv_nearby
                        .setTextColor(getResources().getColor(R.color.user_heart1));
                break;
            case 2:
                resetTab();
                tabhost.setCurrentTabByTag(TAB_MESSAGE);
                img_message.setBackgroundResource(R.drawable.message_select);
                tv_message.setTextColor(getResources()
                        .getColor(R.color.user_heart1));
                break;
            case 3:
                resetTab();
                tabhost.setCurrentTabByTag(TAB_HOME);
                img_home.setBackgroundResource(R.drawable.home_select);
                tv_home.setTextColor(getResources().getColor(R.color.user_heart1));
                break;
            case 4:
                resetTab();
                tabhost.setCurrentTabByTag(TAB_FIND);
                img_find.setBackgroundResource(R.drawable.find_select);
                tv_find.setTextColor(getResources().getColor(R.color.user_heart1));
                break;
            case 5:
                resetTab();
                tabhost.setCurrentTabByTag(TAB_USER);
                img_user.setBackgroundResource(R.drawable.user_select);
                tv_user.setTextColor(getResources().getColor(R.color.user_heart1));
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.tab_nearby:
                selectTab(1);
                break;
            case R.id.tab_message:
                selectTab(2);
                break;
            case R.id.tab_home:
                selectTab(3);
                break;
            case R.id.tab_find:
                selectTab(4);
                break;
            case R.id.tab_user:
                selectTab(5);
                break;
        }
    }

}
