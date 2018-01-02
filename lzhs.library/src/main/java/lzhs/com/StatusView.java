package lzhs.com;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * 多状态控件<br/>
 * 1、加载中<br/>无网络<br/>无数据<br/>出错<br/>四种情况
 * 作者：LZHS<br/>
 * 时间： 2018/1/2 15:55<br/>
 * 邮箱：1050629507@qq.com
 */
public class StatusView extends FrameLayout {
    public StatusView(@NonNull Context context) {
        super(context);
    }

    public StatusView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public StatusView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public StatusView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
