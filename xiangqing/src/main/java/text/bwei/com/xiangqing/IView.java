package text.bwei.com.xiangqing;

/**
 * 作者：戈鹏
 * on 2017/12/19 09:19
 */

public interface IView {
        void OnSuccess(Object o, String tag);
        void OnFailed(Exception e, String tag);

}
