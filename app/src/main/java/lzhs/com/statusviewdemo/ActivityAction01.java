package lzhs.com.statusviewdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import java.util.Arrays;

import lzhs.com.StatusView;

/**
 * <br/>
 * 作者：LZHS<br/>
 * 时间： 2018/2/6 16:16<br/>
 * 邮箱：1050629507@qq.com
 */
public class ActivityAction01 extends AppCompatActivity {
    public static void start(Context mContext) {
        Intent mIntent = new Intent();
        mIntent.setClass(mContext, ActivityAction01.class);
        mContext.startActivity(mIntent);
    }

    StatusView mStatusView;
    TabLayout mTabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action01);
        mStatusView = findViewById(R.id.mStatusView);
        mStatusView.showContent();
        mTabLayout = findViewById(R.id.mTabLayout);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        mStatusView.showContent();
                        break;
                    case 1:
                        mStatusView.showLoading();
                        break;
                    case 2:
                        mStatusView.showError();
                        break;
                    case 3:
                        mStatusView.showEmpty();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        for (String s : Arrays.asList(new String[]{"内容视图", "加载视图", "错误视图", "空视图"}))
            mTabLayout.addTab(mTabLayout.newTab().setText(s));

    }
}
