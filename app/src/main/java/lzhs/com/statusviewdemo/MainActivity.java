package lzhs.com.statusviewdemo;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import java.util.Arrays;

import lzhs.com.StatusView;

public class MainActivity extends AppCompatActivity {

    Toolbar mToolbar = null;

    StatusView mStatusView = null;

    TabLayout mTabLayout = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = findViewById(R.id.mToolbar);
        setSupportActionBar(mToolbar);
        mStatusView = StatusView.wrap(findViewById(R.id.mViewContent));
        mToolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_01:

                    break;
                case R.id.action_02:

                    break;
            }
            return true;
        });
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


}
