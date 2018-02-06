package lzhs.com;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

/**
 * 多状态控件<br/>
 * 1、加载中、无数据、出错、三种种情况<br/>
 * 作者：LZHS<br/>
 * 时间： 2016/1/2 15:55<br/>
 * 邮箱：1050629507@qq.com
 */
public class StatusView extends FrameLayout {

    Map<Integer, View> mChildView = new HashMap<>();
    int mContentId = R.id.StatusView_Contnet;
    Context mContext;
    LayoutInflater mInflater;
    Config mConfig = new Config();

    public StatusView(@NonNull Context context) {
        this(context, null, R.attr.StatusViewAttr);
    }

    public StatusView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, R.attr.StatusViewAttr);
    }

    public StatusView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initAttr(attrs, defStyleAttr);
    }

    private void initAttr(AttributeSet attrs, int defStyleAttr) {
        mInflater = LayoutInflater.from(mContext);
        TypedArray mTypeArr = mContext.obtainStyledAttributes(attrs, R.styleable.StatusView, defStyleAttr, R.style.StatusView_Style);
        mConfig.mStatusEmptyImage = mTypeArr.getResourceId(R.styleable.StatusView_StatusEmptyImage, mConfig.mStatusEmptyImage);
        mConfig.mStatusEmptyText = mTypeArr.getString(R.styleable.StatusView_StatusEmptyText);

        mConfig.mStatusErrorImage = mTypeArr.getResourceId(R.styleable.StatusView_StatusErrorImage, mConfig.mStatusErrorImage);
        mConfig.mStatusErrorText = mTypeArr.getString(R.styleable.StatusView_StatusErrorText);
        mConfig.mStatusRetryText = mTypeArr.getString(R.styleable.StatusView_StatusRetryText);

        mConfig.mStatusTextColor = mTypeArr.getColor(R.styleable.StatusView_StatusTextColor, mConfig.mStatusTextColor);
        mConfig.mStatusTextSize = mTypeArr.getDimensionPixelSize(R.styleable.StatusView_StatusTextSize, mConfig.mStatusTextSize);

        mConfig.mStatusButtonTextColor = mTypeArr.getColor(R.styleable.StatusView_StatusButtonTextColor, mConfig.mStatusButtonTextColor);
        mConfig.mStatusButtonTextSize = mTypeArr.getDimensionPixelSize(R.styleable.StatusView_StatusButtonTextSize, mConfig.mStatusButtonTextSize);
        mConfig.mStatusButtonBackground = mTypeArr.getDrawable(R.styleable.StatusView_StatusButtonBackground);


        mConfig.mStatusLoadingText = mTypeArr.getString(R.styleable.StatusView_StatusLoadingText);


        mConfig.mStatusEmptyResId = mTypeArr.getResourceId(R.styleable.StatusView_StatusEmptyResId, mConfig.mStatusEmptyResId);
        mConfig.mStatusErrorResId = mTypeArr.getResourceId(R.styleable.StatusView_StatusErrorResId, mConfig.mStatusErrorResId);
        mConfig.mStatusLoadingResId = mTypeArr.getResourceId(R.styleable.StatusView_StatusLoadingResId, mConfig.mStatusLoadingResId);
        mTypeArr.recycle();
    }

    public static StatusView wrap_v4(android.support.v4.app.Fragment mFragment) {
        return wrap(mFragment.getView());
    }

    public static StatusView wrap(Fragment mFragment) {
        return wrap(mFragment.getView());
    }

    public static StatusView wrap(Activity mActivity) {
        return wrap(((ViewGroup) mActivity.findViewById(android.R.id.content)).getChildAt(0));
    }

    public static StatusView wrap(View mView) {
        if (mView == null) throw new RuntimeException("StatusView 将要包裹的Vie不能为空");
        ViewGroup mParent = (ViewGroup) mView.getParent();
        if (mParent == null) throw new RuntimeException("StatusView 将要依附的父控件不能为空");
        ViewGroup.LayoutParams mLayoutPara = mView.getLayoutParams();
        int index = mParent.indexOfChild(mView);
        mParent.removeView(mView);
        StatusView mStatusView = new StatusView(mView.getContext());
        mParent.addView(mStatusView, index, mLayoutPara);
        mView.setLayoutParams(new LayoutParams(GridLayout.LayoutParams.MATCH_PARENT
                , GridLayout.LayoutParams.MATCH_PARENT));
        mStatusView.addView(mView);
        mStatusView.setContextView(mView);
        return mStatusView;
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() == 0) return;
        if (getChildCount() > 1) removeViews(1, getChildCount() - 1);
        View mView = getChildAt(0);
        setContextView(mView);
        showLoading();
    }

    /**
     * 设置加载视图
     *
     * @param id
     * @return
     */
    public StatusView setLoading(@LayoutRes int id) {
        if (mConfig.mStatusLoadingResId != id) {
            remove(mConfig.mStatusLoadingResId);
            mConfig.mStatusLoadingResId = id;
        }
        return this;
    }

    /**
     * 设置空视图
     *
     * @param id
     * @return
     */
    public StatusView setEmpty(@LayoutRes int id) {
        if (mConfig.mStatusEmptyResId != id) {
            remove(mConfig.mStatusEmptyResId);
            mConfig.mStatusEmptyResId = id;
        }
        return this;
    }

    /**
     * 设置错误视图
     *
     * @param id
     * @return
     */
    public StatusView setError(@LayoutRes int id) {
        if (mConfig.mStatusErrorResId != id) {
            remove(mConfig.mStatusErrorResId);
            mConfig.mStatusErrorResId = id;
        }
        return this;
    }

    /**
     * 设置重试请求点击事件
     *
     * @param mRetryListenet
     */
    public StatusView setRetryListenet(OnClickListener mRetryListenet) {
        mConfig.mRetryListenet = mRetryListenet;
        return this;
    }

    /**
     * 设置空视图点击事件
     *
     * @param mEmptyListenet
     */
    public StatusView setEmptyListenet(OnClickListener mEmptyListenet) {
        mConfig.mEmptyListenet = mEmptyListenet;
        return this;
    }

    /**
     * 显示加载中视图
     */
    public void showLoading() {
        show(mConfig.mStatusLoadingResId);
    }

    /**
     * 显示空视图
     */
    public void showEmpty() {
        show(mConfig.mStatusEmptyResId);
    }

    /**
     * 显示错误视图
     */
    public void showError() {
        show(mConfig.mStatusErrorResId);
    }

    /**
     * 显示内容视图
     */
    public void showContent() {
        show(mContentId);
    }

    /**
     * 设置空视图图片
     *
     * @param resId
     * @return
     */
    public StatusView setEmptyImage(@DrawableRes int resId) {
        mConfig.mStatusEmptyImage = resId;
        return this;
    }

    /**
     * 设置空视图文字
     *
     * @param value
     * @return
     */
    public StatusView setEmptyText(String value) {
        mConfig.mStatusErrorText = value;
        return this;
    }

    /**
     * 设置错误视图图片
     *
     * @param resId
     * @return
     */
    public StatusView setErrorImage(@DrawableRes int resId) {
        mConfig.mStatusErrorImage = resId;
        return this;
    }

    /**
     * 设置错误视图文字
     *
     * @param value
     * @return
     */
    public StatusView setErrorText(String value) {
        mConfig.mStatusErrorText = value;
        return this;
    }

    /**
     * 设置点击重试文字
     *
     * @param text
     * @return
     */
    public StatusView setRetryText(String text) {
        mConfig.mStatusRetryText = text;
        return this;
    }

    /**
     * 设置正在加载文字
     *
     * @param text
     * @return
     */
    public StatusView setLoadingText(String text) {
        mConfig.mStatusLoadingText = text;
        return this;
    }

    private void show(int layoutId) {
        for (View view : mChildView.values())
            view.setVisibility(View.GONE);
        View mView = layout(layoutId);
        mView.setVisibility(View.VISIBLE);

        for (View view : mChildView.values())
            System.out.println(view.getVisibility() + "" + view.getId());

    }

    protected View layout(int layoutId) {
        if (mChildView.containsKey(layoutId))
            return mChildView.get(layoutId);
        View mLayoutView = mInflater.inflate(layoutId, this, false);
        addView(mLayoutView);
        mChildView.put(layoutId, mLayoutView);
        if (layoutId == R.layout.status_layout_error)
            createErrorLayout(mLayoutView);
        else if (layoutId == R.layout.status_layout_empty)
            createEmptyLayout(mLayoutView);
        else if (layoutId == R.layout.status_layout_loading)
            createLoadingLayout(mLayoutView);
        return mLayoutView;
    }

    private void setContextView(View mView) {
        mContentId = mView.getId();
        mChildView.put(mContentId, mView);
        showContent();
    }

    private void createLoadingLayout(View mLayoutView) {
        TextView mText = mLayoutView.findViewById(R.id.mTextStatusLoading);
        mText.setText(mConfig.mStatusEmptyText);
        mText.setTextColor(mConfig.mStatusTextColor);
        mText.setTextSize(TypedValue.COMPLEX_UNIT_PX, mConfig.mStatusTextSize);
    }

    private void createEmptyLayout(View mLayoutView) {
        ImageView mImage = mLayoutView.findViewById(R.id.mImageStatusEmpty);
        mImage.setImageResource(mConfig.mStatusEmptyImage);
        TextView mText = mLayoutView.findViewById(R.id.mTextStatusEmpty);
        mText.setText(mConfig.mStatusEmptyText);
        mText.setTextColor(mConfig.mStatusTextColor);
        mText.setTextSize(TypedValue.COMPLEX_UNIT_PX, mConfig.mStatusTextSize);
        if (mConfig.mEmptyListenet != null) mLayoutView.setOnClickListener(mConfig.mEmptyListenet);
    }

    private void createErrorLayout(View mLayoutView) {
        ImageView mImage = mLayoutView.findViewById(R.id.mImageStatusError);
        mImage.setImageResource(mConfig.mStatusErrorImage);
        TextView mText = mLayoutView.findViewById(R.id.mTextStatusError);
        mText.setTextColor(mConfig.mStatusTextColor);
        mText.setTextSize(TypedValue.COMPLEX_UNIT_PX, mConfig.mStatusTextSize);
        mText.setText(mConfig.mStatusErrorText);

        TextView mTextBtn = mLayoutView.findViewById(R.id.mTextStatusRetryButton);
        mTextBtn.setTextColor(mConfig.mStatusButtonTextColor);
        mTextBtn.setTextSize(TypedValue.COMPLEX_UNIT_PX, mConfig.mStatusButtonTextSize);
        mTextBtn.setText(mConfig.mStatusRetryText);
        mTextBtn.setBackgroundDrawable(mConfig.mStatusButtonBackground);
        if (mConfig.mRetryListenet != null) mTextBtn.setOnClickListener(mConfig.mRetryListenet);
    }


    private void remove(int layoutId) {
        if (mChildView.containsKey(layoutId))
            removeView(mChildView.remove(layoutId));
    }


    class Config {
        int textSize = (int) (getResources().getDisplayMetrics().density * 13);
        int mStatusTextColor = Color.parseColor("#999999");
        int mStatusTextSize = textSize;


        int mStatusEmptyImage = R.mipmap.empty;
        String mStatusEmptyText = "暂无数据...";
        int mStatusErrorImage = R.mipmap.error;
        String mStatusErrorText = "无网络连接，请检查您的网络...";
        String mStatusRetryText = "加载失败，点击重试~~";
        int mStatusButtonTextColor = Color.parseColor("#999999");
        String mStatusLoadingText = "正在加载...";
        int mStatusButtonTextSize = (int) (getResources().getDisplayMetrics().density * 13);
        Drawable mStatusButtonBackground = getResources().getDrawable(R.drawable.status_loading_layout_button);

        int mStatusEmptyResId = R.layout.status_layout_empty;
        int mStatusErrorResId = R.layout.status_layout_error;
        int mStatusLoadingResId = R.layout.status_layout_loading;

        View.OnClickListener mRetryListenet = null;

        View.OnClickListener mEmptyListenet = null;


    }


}
