package com.launcher.informsmanage.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.launcher.informsmanage.ApiManager.InformApiManager;
import com.launcher.informsmanage.Utils.ConstansUtils;
import com.launcher.informsmanage.Utils.DateUtils;
import com.launcher.informsmanage.Model.InformData;
import com.launcher.informsmanage.View.PullToRefreshView.PullToRefreshAttacher;
import com.launcher.informsmanage.main.R;

import java.util.ArrayList;
import java.util.List;

import rx.android.concurrency.AndroidSchedulers;
import rx.util.functions.Action1;


public class Inform_HomeActivity extends MainActivity implements PullToRefreshAttacher.OnRefreshListener{

    private PullToRefreshAttacher pullToRefreshAttacher;
    private ListView listView;
    private MyAdapter adapter;
    private Handler handler;
    private List<InformData> list = new ArrayList<InformData>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);



//        //自定义anctionbar
//        View viewTitleBar = getLayoutInflater().inflate(R.layout.activity_main_actionbar, null);
//        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(
//                ActionBar.LayoutParams.MATCH_PARENT,
//                ActionBar.LayoutParams.MATCH_PARENT,
//                Gravity.CENTER);
//        //添加自定义的视图
//        getActionBar().setCustomView(viewTitleBar, lp);
//        //不显示自带actionbar
//        getActionBar().setDisplayShowHomeEnabled(false);
//        //不显示标题
//        getActionBar().setDisplayShowTitleEnabled(false);
//        //显示方式
//        getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        //显示自定义视图
//        getActionBar().setDisplayShowCustomEnabled(true);

        setContentView(R.layout.inform_home);


        listView = (ListView)findViewById(R.id.listview);
        adapter = new MyAdapter(this,list);
        listView.setAdapter(adapter);
        pullToRefreshAttacher = new PullToRefreshAttacher(this);
        pullToRefreshAttacher.setRefreshableView(listView,this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putString("id",list.get(i).id);
                pushToNextActivity(bundle,Inform_DetailsActivity.class);
            }
        });

        handler = new Handler(){

            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub

                switch(msg.what)
                {
                    case ConstansUtils.MSG_SUCCESS:
                        pullToRefreshAttacher.setRefreshComplete();
                        adapter.notifyDataSetChanged();
                        break;
                }
                super.handleMessage(msg);
            }

        };

        getAsyInformsData();
    }

    //获取新闻链表
    private void getAsyInformsData()
    {
        InformApiManager.getInformsData().observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<InformData>>() {
                    @Override
                    public void call(List<InformData> informDatas) {
                        list.clear();
                        list.addAll(informDatas);

                        handler.obtainMessage(ConstansUtils.MSG_SUCCESS)
                                .sendToTarget();
                    }
                });



//        Observable.from(new informData()).mapMany(new Func1<informData, Observable<?>>() {
//            @Override
//            public Observable<?> call(informData informsData) {
//                return InformNetWorkModel.getInformsData();
//            }
//        }).subscribeOn(Schedulers.threadPoolForIO())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<Object>() {
//                    @Override
//                    public void call(Object o) {
//
//                    }
//                });
    }

    @Override
    public void onRefreshStarted(View view) {
        getAsyInformsData();
    }

    //	内部类实现BaseAdapter  ，自定义适配器
    class MyAdapter extends BaseAdapter{

        private Context context;
        List<InformData> list;

        public MyAdapter(Context context, List<InformData> list)
        {
            this.context = context;
            this.list = list;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return list.size();
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return arg0;
        }

        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return arg0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            ViewHolder holder = null;
            // TODO Auto-generated method stub
            if(holder == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.inform_home_item, null);
                holder = new ViewHolder();
                holder.informs_item_title = (TextView)convertView.findViewById(R.id.informs_item_title);
                holder.informs_item_time = (TextView)convertView.findViewById(R.id.informs_item_time);
                holder.informs_item_details = (TextView)convertView.findViewById(R.id.informs_item_details);
                convertView.setTag(holder);
            }
            else
            {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.informs_item_title.setText(list.get(position).title);
            holder.informs_item_details.setText(list.get(position).brief);
            holder.informs_item_time.setText(DateUtils.StimestampToDeatil(list.get(position).updated_at));


            return convertView;
        }
    }
    //此类为上面getview里面view的引用，方便快速滑动
    class ViewHolder{
        TextView informs_item_title;
        TextView informs_item_time;
        TextView informs_item_details;
    }

}
