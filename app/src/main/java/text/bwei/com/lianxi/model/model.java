package text.bwei.com.lianxi.model;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Request;
import text.bwei.com.lianxi.adapter.OkHttp;
import text.bwei.com.lianxi.bean.AdndroidBean;

/**
 * Created by dell on 2017/12/20.
 */

public class model implements Imodel {
    @Override
    public void RequestSuccess(String url, final Onselect onselect) {
        OkHttp.getAsync(url, new OkHttp.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {

            }

            @Override
            public void requestSuccess(String result) throws Exception {
                Gson gson = new Gson();
                AdndroidBean adndroidBean = gson.fromJson(result, AdndroidBean.class);
                List<AdndroidBean.ResultsBean> results = adndroidBean.getResults();
                onselect.dataSucces(results);
            }
        });


    }
}
