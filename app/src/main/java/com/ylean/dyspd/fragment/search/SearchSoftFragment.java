package com.ylean.dyspd.fragment.search;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ylean.dyspd.R;
import com.ylean.dyspd.activity.decorate.SearchListActivity;
import com.ylean.dyspd.adapter.decorate.SoftLoadingAdapter;
import com.zxdc.utils.library.eventbus.EventBusType;
import com.zxdc.utils.library.eventbus.EventStatus;
import com.ylean.dyspd.view.ListEmptyView;
import com.zxdc.utils.library.base.BaseFragment;
import com.zxdc.utils.library.bean.SoftLoadingList;
import com.zxdc.utils.library.http.HandlerConstant;
import com.zxdc.utils.library.http.HttpMethod;
import com.zxdc.utils.library.util.ToastUtil;
import com.zxdc.utils.library.view.MyRefreshLayout;
import com.zxdc.utils.library.view.MyRefreshLayoutListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SearchSoftFragment extends BaseFragment implements MyRefreshLayoutListener {

    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.re_list)
    MyRefreshLayout reList;
    @BindView(R.id.emptyView)
    ListEmptyView listEmptyView;
    Unbinder unbinder;
    //fragment是否可见
    private boolean isVisibleToUser=false;
    private SoftLoadingAdapter softLoadingAdapter;
    //页数
    private int page=1;
    private List<SoftLoadingList.SoftLoadingBean> listAll=new ArrayList<>();
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //注册eventBus
        EventBus.getDefault().register(this);
    }

    View view;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.listview, container, false);
        unbinder = ButterKnife.bind(this, view);
        //刷新加载
        reList.setMyRefreshLayoutListener(this);
        softLoadingAdapter=new SoftLoadingAdapter(mActivity,listAll);
        listView.setAdapter(softLoadingAdapter);
        listView.setDivider(null);
        //获取软装范本列表
        if(listAll.size()==0){
            getSoftList(HandlerConstant.GET_SOFT_LIST_SUCCESS1);
        }
        return view;
    }


    private Handler handler=new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                //下刷
                case HandlerConstant.GET_SOFT_LIST_SUCCESS1:
                    reList.refreshComplete();
                    listAll.clear();
                    refresh((SoftLoadingList) msg.obj);
                    break;
                //上拉
                case HandlerConstant.GET_SOFT_LIST_SUCCESS2:
                    reList.loadMoreComplete();
                    refresh((SoftLoadingList) msg.obj);
                    break;
                case HandlerConstant.REQUST_ERROR:
                    ToastUtil.showLong(msg.obj==null ? "异常错误信息" : msg.obj.toString());
                    break;
                default:
                    break;
            }
            return false;
        }
    });


    /**
     * 刷新界面数据
     */
    private void refresh(SoftLoadingList softLoadingList){
        if(softLoadingList==null){
            return;
        }
        if(softLoadingList.isSussess()){
            List<SoftLoadingList.SoftLoadingBean> list=softLoadingList.getData();
            listAll.addAll(list);
            softLoadingAdapter.notifyDataSetChanged();
            if(list.size()<HttpMethod.pageSize){
                reList.setIsLoadingMoreEnabled(false);
            }
            //没数据展示的视图
            if(listAll.size()>0){
                listEmptyView.setVisibility(View.GONE);
            }else{
                listEmptyView.setType(2);
                listEmptyView.setVisibility(View.VISIBLE);
            }
        }else{
            ToastUtil.showLong(softLoadingList.getDesc());
        }
    }


    @Override
    public void onRefresh(View view) {
        page=1;
        getSoftList(HandlerConstant.GET_SOFT_LIST_SUCCESS1);
    }

    @Override
    public void onLoadMore(View view) {
        page++;
        getSoftList(HandlerConstant.GET_SOFT_LIST_SUCCESS2);
    }


    /**
     * EventBus注解
     */
    @Subscribe
    public void onEvent(EventBusType eventBusType) {
        switch (eventBusType.getStatus()) {
            //搜索新的关键字
            case EventStatus.SEARCH_FRAGMENT_BY_KEYS:
                listAll.clear();
                softLoadingAdapter.notifyDataSetChanged();
                onRefresh(null);
                break;
            default:
                break;
        }
    }


    /**
     * 获取软装范本列表
     */
    private void getSoftList(int index){
        if(isVisibleToUser && view!=null){
            HttpMethod.getSoftList(null,null,null,SearchListActivity.strKey,String.valueOf(page),null,null,index,handler);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser=isVisibleToUser;
        //获取软装范本列表
        if(listAll.size()==0){
            getSoftList(HandlerConstant.GET_SOFT_LIST_SUCCESS1);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
        removeHandler(handler);
    }

}
