package text.bwei.com.xiangqing.presenter;


import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import text.bwei.com.xiangqing.IView;
import text.bwei.com.xiangqing.entity.CartBean;
import text.bwei.com.xiangqing.entity.GoodsShowBean;
import text.bwei.com.xiangqing.entity.MessageBean;
import text.bwei.com.xiangqing.model.Model;



public class NewsPresenter implements IPresenter{

    private IView iView;
    private DisposableSubscriber<MessageBean<GoodsShowBean>> subscriber;
    private DisposableSubscriber<MessageBean<List<CartBean>>> subscriber2;

    public void attachView(IView iView){
        this.iView=iView;
    }


    @Override
    public void getData(Map<String, String> map, String tag) {
        Model model = new Model(this);
        model.getData(map,tag);
    }

    public void detachView(){

        if(iView!=null){
            iView=null;
        }

        if(subscriber!=null){
            //如果该资源已被处理，则可一次性返回true。如果没有被处理返回false
            if(!subscriber.isDisposed()){
                subscriber.dispose();
            }
        }

        if(subscriber2!=null){
            //如果该资源已被处理，则可一次性返回true。如果没有被处理返回false
            if(!subscriber2.isDisposed()){
                subscriber2.dispose();
            }
        }

    }


    public void get(Flowable<MessageBean<GoodsShowBean>> flowable , final String tag) {
        subscriber = flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<MessageBean<GoodsShowBean>>() {
                    @Override
                    public void onNext(MessageBean<GoodsShowBean> listMessageBean) {
                        if (listMessageBean != null) {
                            GoodsShowBean data = listMessageBean.getData();
                            iView.OnSuccess(data,tag);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        iView.OnFailed(new Exception(t),tag);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    public void get2(Flowable<MessageBean<List<CartBean>>> flowable, final String tag) {
        subscriber2 = flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<MessageBean<List<CartBean>>>() {
                    @Override
                    public void onNext(MessageBean<List<CartBean>> list) {
                        if (list != null) {
                            List<CartBean> data = list.getData();
                            if(data!=null){
                                iView.OnSuccess(data,tag);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        iView.OnFailed(new Exception(t),tag);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


}
