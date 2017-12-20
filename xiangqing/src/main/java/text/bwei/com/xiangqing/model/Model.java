package text.bwei.com.xiangqing.model;


import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import text.bwei.com.xiangqing.entity.CartBean;
import text.bwei.com.xiangqing.entity.GoodsShowBean;
import text.bwei.com.xiangqing.entity.MessageBean;
import text.bwei.com.xiangqing.http.RetrofitUtils;
import text.bwei.com.xiangqing.presenter.NewsPresenter;

public class Model implements IModel{

    private NewsPresenter presenter;
    public Model(NewsPresenter presenter) {
        this.presenter = presenter;
    }

    /*@Override
    public void getData(Map<String, String> map) {
        Flowable<MessageBean<List<Newslist>>> flowable = RetrofitUtils.getInstance().getApiService().getNews(map);
        presenter.get(flowable);
    }*/

    @Override
    public void getData(Map<String, String> map, String tag) {
        if(tag.equals("cart")){
            Flowable<MessageBean<List<CartBean>>> flowable = RetrofitUtils.getInstance().getApiService().getCart(map);
            presenter.get2(flowable,tag);
        }else if(tag.equals("goods")){
            Flowable<MessageBean<GoodsShowBean>> flowable = RetrofitUtils.getInstance().getApiService().getNews(map);
            presenter.get(flowable,tag);
        }

    }
}
