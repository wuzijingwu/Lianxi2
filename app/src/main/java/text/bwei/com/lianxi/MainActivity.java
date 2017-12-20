package text.bwei.com.lianxi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import text.bwei.com.lianxi.adapter.MyAdapter;
import text.bwei.com.lianxi.api.Api;
import text.bwei.com.lianxi.bean.AdndroidBean;
import text.bwei.com.lianxi.presenter.presenter;
import text.bwei.com.lianxi.view.Iview;

public class MainActivity extends AppCompatActivity implements Iview {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_main);
        presenter presenter = new presenter(this);
        presenter.getOk(Api.URL);
    }


    @Override
    public void shouList(List<AdndroidBean.ResultsBean> list) {

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter(list, this));


    }
}
