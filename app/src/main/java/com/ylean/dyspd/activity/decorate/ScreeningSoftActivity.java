package com.ylean.dyspd.activity.decorate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.ylean.dyspd.R;
import com.ylean.dyspd.adapter.main.ScreeningAdapter;
import com.zxdc.utils.library.base.BaseActivity;
import com.zxdc.utils.library.bean.Screening;
import com.zxdc.utils.library.http.HandlerConstant;
import com.zxdc.utils.library.http.HttpMethod;
import com.zxdc.utils.library.util.ToastUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * 筛选软装范本
 * Created by Administrator on 2019/11/20.
 */

public class ScreeningSoftActivity extends BaseActivity {

    @BindView(R.id.list_style)
    RecyclerView listStyle;
    @BindView(R.id.list_model)
    RecyclerView listModel;
    @BindView(R.id.list_area)
    RecyclerView listArea;
    @BindView(R.id.tv_style)
    TextView tvStyle;
    private ScreeningAdapter screeningAdapter;
    //风格名称，户型名称，面积名称
    private String styleName="全部",modelName="全部",areaName="全部";
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screening_soft);
        ButterKnife.bind(this);
        //获取风格数据
        getScreening("5",HandlerConstant.GET_SCREENING_STYLE);
        //获取户型数据
        getScreening("3",HandlerConstant.GET_SCREENING_MODEL);
        //获取面积数据
        getScreening("1",HandlerConstant.GET_SCREENING_AREA);
    }


    private Handler handler = new Handler(new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            Screening screening = null;
            switch (msg.what) {
                //获取风格数据
                case HandlerConstant.GET_SCREENING_STYLE:
                    screening = (Screening) msg.obj;
                    if (screening == null) {
                        break;
                    }
                    if (screening.isSussess()) {
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(ScreeningSoftActivity.this, 3);
                        listStyle.setLayoutManager(gridLayoutManager);
                        screeningAdapter = new ScreeningAdapter(ScreeningSoftActivity.this, screening.getData(), 0, new ScreeningAdapter.OnItemClickListener() {
                            public void onItemClick(Object object) {
                                final Screening.ScreeningBean screeningBean = (Screening.ScreeningBean) object;
                                if (screeningBean != null) {
                                    styleName = screeningBean.getName();
                                }

                            }
                        });
                        listStyle.setAdapter(screeningAdapter);
                    } else {
                        ToastUtil.showLong(screening.getDesc());
                    }
                    break;
                //获取户型数据
                case HandlerConstant.GET_SCREENING_MODEL:
                    screening = (Screening) msg.obj;
                    if (screening == null) {
                        break;
                    }
                    if (screening.isSussess()) {
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(ScreeningSoftActivity.this, 3);
                        listModel.setLayoutManager(gridLayoutManager);
                        screeningAdapter = new ScreeningAdapter(ScreeningSoftActivity.this, screening.getData(), 2, new ScreeningAdapter.OnItemClickListener() {
                            public void onItemClick(Object object) {
                                final Screening.ScreeningBean screeningBean = (Screening.ScreeningBean) object;
                                if (screeningBean != null) {
                                    modelName = screeningBean.getName();
                                }
                            }
                        });
                        listModel.setAdapter(screeningAdapter);
                    } else {
                        ToastUtil.showLong(screening.getDesc());
                    }
                    break;
                //获取面积数据
                case HandlerConstant.GET_SCREENING_AREA:
                    screening = (Screening) msg.obj;
                    if (screening == null) {
                        break;
                    }
                    if (screening.isSussess()) {
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(ScreeningSoftActivity.this, 3);
                        listArea.setLayoutManager(gridLayoutManager);
                        screeningAdapter = new ScreeningAdapter(ScreeningSoftActivity.this, screening.getData(), 2, new ScreeningAdapter.OnItemClickListener() {
                            public void onItemClick(Object object) {
                                final Screening.ScreeningBean screeningBean = (Screening.ScreeningBean) object;
                                if (screeningBean != null) {
                                    areaName = screeningBean.getName();
                                }
                            }
                        });
                        listArea.setAdapter(screeningAdapter);
                    } else {
                        ToastUtil.showLong(screening.getDesc());
                    }
                    break;
                case HandlerConstant.REQUST_ERROR:
                    ToastUtil.showLong(msg.obj == null ? "异常错误信息" : msg.obj.toString());
                    break;
                default:
                    break;
            }
            return false;
        }
    });


    @OnClick({R.id.lin_back,R.id.tv_style,R.id.tv_cancle, R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_style:
                if(listStyle.getVisibility()==View.VISIBLE){
                    listStyle.setVisibility(View.GONE);
                    tvStyle.setText("展开");
                }else{
                    listStyle.setVisibility(View.VISIBLE);
                    tvStyle.setText("收起");
                }
                break;
            case R.id.lin_back:
            case R.id.tv_cancle:
                finish();
                break;
            case R.id.tv_confirm:
                if (TextUtils.isEmpty(styleName)) {
                    ToastUtil.showLong("请选择风格");
                    return;
                }
                if (TextUtils.isEmpty(modelName)) {
                    ToastUtil.showLong("请选择户型");
                    return;
                }
                if (TextUtils.isEmpty(areaName)) {
                    ToastUtil.showLong("请选择面积");
                    return;
                }
                if(styleName.equals("全部")){
                    styleName="";
                }
                if(modelName.equals("全部")){
                    modelName="";
                }
                if(areaName.equals("全部")){
                    areaName="";
                }
                Intent intent=new Intent();
                intent.putExtra("styleName",styleName);
                intent.putExtra("modelName",modelName);
                intent.putExtra("areaName",areaName);
                setResult(100,intent);
                finish();
                break;
            default:
                break;
        }
    }


    /**
     * 获取风格,户型,面积数据
     */
    private void getScreening(String type,int index) {
        HttpMethod.getScreening(type, index, handler);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeHandler(handler);
    }
}
