package com.iven.searchview.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iven.searchview.R;

/**
 * @author Iven
 * @date 2016/9/29 10:57
 * @Description 自定义搜索框
 */
public class SearchView extends LinearLayout implements View.OnClickListener {
    /**
     * 全局TAG
     */
    private final String TAG = "IVEN";
    /**
     * 输入框
     */
    private EditText et_search;
    /**
     * 删除按钮(图片)
     */
    private ImageView iv_delete;
    /**
     * 返回按钮
     */
    private TextView tv_back;
    /**
     * 上下文
     */
    private Context mContext;

    /**
     * 回调接口
     */
    private SearchViewListener mListener;

    /**
     * set interface
     */
    public void setSearchViewListener(SearchViewListener listener) {
        mListener = listener;
    }

    public SearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater.from(mContext).inflate(R.layout.layout_search, this);
        initViews();
    }

    /**
     * 初始化
     */
    private void initViews() {
        et_search = (EditText) findViewById(R.id.et_search);
        iv_delete = (ImageView) findViewById(R.id.iv_delete);
        tv_back = (TextView) findViewById(R.id.tv_back);

        iv_delete.setOnClickListener(this);
        tv_back.setOnClickListener(this);
        //直接显示软键盘
//        showSoftInput(et_search);
        et_search.addTextChangedListener(new EditChangedListener());
        et_search.setOnFocusChangeListener(new EditOnFouseChangeListener());


        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (mListener != null) {
                        mListener.onRefreshComplete(et_search.getText().toString());
                    }
                }
                return true;
            }
        });
    }

    /**
     * 监听实时变化文本
     */
    private class EditChangedListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        //文本改变时，实时将当前的文本传递
        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            if (!TextUtils.isEmpty(charSequence.toString())) {
                //显示删除图片按钮
                iv_delete.setVisibility(VISIBLE);
                //传递数据
                if (mListener != null) {
                    mListener.onRefreshComplete(charSequence.toString());
                }
            } else {
                iv_delete.setVisibility(GONE);//禁止占位
                if (mListener != null) {
                    mListener.onRefreshComplete(charSequence.toString());
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    /**
     * 监听焦点变化
     */
    private class EditOnFouseChangeListener implements OnFocusChangeListener {

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                // TODO: 2016/9/29 获得焦点，需要进行什么操作---开启软键盘？
                mListener.onGetFouse();
                Log.e(TAG, "onFocusChange. = " + hasFocus);
            } else {
                // TODO: 2016/9/30 失去焦点，关闭软键盘
                Log.e(TAG, "onFocusChange. = " + hasFocus);
            }
        }
    }

    /**
     * 点击事件处理
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // TODO: 2016/9/29 有必要监听这个吗？？EditText 的？？？？
            case R.id.et_search:
                if (mListener != null) {
                    mListener.onGetFouse();
                }
                break;
            case R.id.iv_delete:
                et_search.setText("");//清空EditText
                iv_delete.setVisibility(GONE);//隐藏×
                if (mListener != null) {
                    mListener.onClear();
                }
                break;
            case R.id.tv_back:
                // TODO: 2016/9/29 处理一下，再 ，其实取消按钮，应该是用StartActivityForResult来实现的
                if (mListener != null) {
                    mListener.onBack();
                }
                break;

        }
    }


    /**
     * 回调方法
     */
    public interface SearchViewListener {
        /**
         * 实时更新内容
         *
         * @param text
         */
        void onRefreshComplete(String text);

        /**
         * 获得焦点
         */
        void onGetFouse();


        /**
         * 清空键
         */
        void onClear();

        /**
         * 返回键
         */
        void onBack();
    }

    /**
     * 获得EditText的内容
     * @return EditText.toString()
     */
    public String getEditText() {
        return et_search.getText().toString();
    }

    /**
     * 直接弹出软键盘
     * todo 提取公共方法 可
     *
     * @param editText EditText
     */
    private void showSoftInput(final EditText editText) {
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        InputMethodManager inputManager = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(editText, 0);
        /**
         * 页面未加载完毕，延迟弹出软键盘，
         */
        /*Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            public void run() {
                InputMethodManager inputManager = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(editText, 0);
            }

        }, 500);*/
    }
}
