package text.bwei.com.lianxi.presenter;

import java.util.List;

import text.bwei.com.lianxi.bean.AdndroidBean;
import text.bwei.com.lianxi.model.Imodel;
import text.bwei.com.lianxi.model.Onselect;
import text.bwei.com.lianxi.model.model;
import text.bwei.com.lianxi.view.Iview;

/**
 * Created by dell on 2017/12/20.
 */

public class presenter {
    Imodel imodel;
    Iview iview;

    public presenter(Iview iview) {
        this.iview = iview;
        imodel = new model();
    }

    public void getOk(String url) {
        imodel.RequestSuccess(url, new Onselect() {
            @Override
            public void dataSucces(List<AdndroidBean.ResultsBean> list) {
                iview.shouList(list);
            }
        });


    }


}
