package text.bwei.com.xiangqing;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import text.bwei.com.xiangqing.adapter.ExpandableAdapter;
import text.bwei.com.xiangqing.entity.CartBean;
import text.bwei.com.xiangqing.entity.ChildBean;
import text.bwei.com.xiangqing.entity.GroupBean;
import text.bwei.com.xiangqing.presenter.NewsPresenter;

public class Main2Activity extends AppCompatActivity implements IView {

    @BindView(R.id.exListView)
    ExpandableListView exListView;
    @BindView(R.id.all_chekbox)
    public CheckBox allCheckbox;
    @BindView(R.id.total_price)
    TextView totalPrice;
    @BindView(R.id.total_number)
    TextView totalnumber;

    private ExpandableAdapter expandableAdapter;

    private boolean flagedit = true;


    private NewsPresenter presenter;

    List<GroupBean> groupBeen = new ArrayList<>();
    List<List<ChildBean>> childBeen = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);

        //获取二级列表适配器
        expandableAdapter = new ExpandableAdapter(Main2Activity.this, groupBeen, childBeen);
        exListView.setAdapter(expandableAdapter);

        exListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                return true;
            }
        });


        presenter = new NewsPresenter();
        presenter.attachView(this);
        //http://120.27.23.105/product/getCarts?uid=3802&source=android
        Map<String, String> map = new HashMap<>();
        map.put("uid", "3802");
        presenter.getData(map, "cart");


    }


    @Override
    public void OnSuccess(Object o, String tag) {
        if (tag.equals("cart")) {
            if (o != null) {
                List<CartBean> data = (List<CartBean>) o;
                //Log.e("Gp", "OnSuccess: " + data.toString());
                for (CartBean i : data) {
                    GroupBean groupBean = new GroupBean(i.getSellerName(), false);
                    this.groupBeen.add(groupBean);
                    List<CartBean.ListBean> list = i.getList();
                    List<ChildBean> ls = new ArrayList<>();
                    for (CartBean.ListBean w : list) {
                        String[] split = w.getImages().split("\\|");
                        ChildBean childBean = new ChildBean(w.getTitle(), split[0], w.getPrice(), 1, false, false);
                        ls.add(childBean);
                    }
                    this.childBeen.add(ls);

                }
                for(int i = 0; i < expandableAdapter.getGroupCount(); i++){
                    exListView.expandGroup(i);
                }
                expandableAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void OnFailed(Exception e, String tag) {

    }

    @OnClick({R.id.back, R.id.edit,R.id.all_chekbox})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.edit:
                for (List<ChildBean> i1:childBeen){
                    for(int r=0;r<i1.size();r++) {
                        i1.get(r).setBtn(flagedit);
                    }
                }
                flagedit=!flagedit;
                expandableAdapter.notifyDataSetChanged();
                break;
            case R.id.all_chekbox:
                boolean checked = allCheckbox.isChecked();
                //改变一级item复选框
                for (GroupBean i:groupBeen){
                    i.setGropuCb(checked);
                }
                //改变二级item复选框
                for (List<ChildBean> i1:childBeen){
                    for(int r=0;r<i1.size();r++) {
                        i1.get(r).setChildCb(checked);
                    }
                }
                expandableAdapter.notifyDataSetChanged();
                changesum(childBeen);
                break;
        }
    }

    //计算和数量总价
    public void changesum(List<List<ChildBean>> childBeen){
        int count=0;
        double sum=0;
        for (List<ChildBean> i1:childBeen){
            for(int r=0;r<i1.size();r++) {
                boolean childCb1 = i1.get(r).isChildCb();
                if(childCb1){
                    double price = i1.get(r).getPrice();
                    int num = i1.get(r).getNum();
                    sum+=price*num;
                    count++;
                }
            }
        }
        totalPrice.setText("￥"+sum);
        totalnumber.setText("共有商品:"+count+"件");
    }

}
