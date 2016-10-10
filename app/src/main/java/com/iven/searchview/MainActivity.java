package com.iven.searchview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import com.iven.searchview.view.SearchView;

/**
 * 自定义SearchView
 * 回调接口功能
 */

/**
 * 尺寸设置用的px像素，需要结合鸿洋大神的AutoLayout使用
 */
public class MainActivity extends AppCompatActivity implements SearchView.SearchViewListener {

    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        searchView = (SearchView) findViewById(R.id.searchview);
        searchView.setSearchViewListener(this);
    }

    @Override
    public void onRefreshComplete(String text) {
        Log.e("IVEN", "onRefreshComplete: " + text);
    }

    @Override
    public void onGetFouse() {
        Log.e("IVEN", "onGetFouse: " + "获得焦点");
    }

    @Override
    public void onClear() {
        Log.e("IVEN", "onClear: " + "点击删除键");
    }

    @Override
    public void onBack() {
        Log.e("IVEN", "onBack: " + "点击返回键");
    }
}
