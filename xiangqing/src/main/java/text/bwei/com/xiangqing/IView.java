package text.bwei.com.xiangqing;



public interface IView {
        void OnSuccess(Object o, String tag);
        void OnFailed(Exception e, String tag);

}
