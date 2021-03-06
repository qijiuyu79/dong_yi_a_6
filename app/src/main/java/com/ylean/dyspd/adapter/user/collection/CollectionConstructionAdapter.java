package com.ylean.dyspd.adapter.user.collection;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.ylean.dyspd.R;
import com.ylean.dyspd.activity.user.collection.CollectionConstructionActivity;
import com.ylean.dyspd.activity.user.collection.CollectionGalleryActivity;
import com.ylean.dyspd.activity.web.decorate.DecorateWebView;
import com.zxdc.utils.library.bean.ConstructionList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
public class CollectionConstructionAdapter extends BaseAdapter {

    private Context context;
    private List<ConstructionList.ConstructionBean> list;

    public CollectionConstructionAdapter(Context context, List<ConstructionList.ConstructionBean> list) {
        super();
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    ViewHolder holder = null;

    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_coll_construction, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final ConstructionList.ConstructionBean constructionBean = list.get(position);
        //显示图片
        String imgUrl = constructionBean.getImg();
        holder.imgHead.setTag(R.id.imageid, imgUrl);
        if (holder.imgHead.getTag(R.id.imageid) != null && imgUrl == holder.imgHead.getTag(R.id.imageid)) {
            Glide.with(context).load(imgUrl).centerCrop().into(holder.imgHead);
        }
        holder.tvName.setText(constructionBean.getName());
        holder.tvDes.setText(constructionBean.getDistrict() + "·" + constructionBean.getLoupanname() + "·" + constructionBean.getSquare());
        holder.tvType.setText(constructionBean.getStage());

        /**
         * 取消收藏
         */
        holder.tvCancle.setTag(constructionBean.getId());
        holder.tvCancle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (v.getTag() == null) {
                    return;
                }
                ((CollectionConstructionActivity) context).cancleColl((Integer) v.getTag());
            }
        });

        /**
         *
         * 进入详情页面
         */
        holder.relContent.setTag(constructionBean);
        holder.relContent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(v.getTag()==null){
                    return;
                }
                ConstructionList.ConstructionBean constructionBean= (ConstructionList.ConstructionBean) v.getTag();
                Intent intent=new Intent(context, DecorateWebView.class);
                intent.putExtra("type",5);
                intent.putExtra("id",constructionBean.getConstrucid());
                intent.putExtra("title",constructionBean.getName());
                context.startActivity(intent);

            }
        });
        return view;
    }


    static class ViewHolder {
        @BindView(R.id.content)
        RelativeLayout relContent;
        @BindView(R.id.img_head)
        ImageView imgHead;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_des)
        TextView tvDes;
        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.tv_cancle)
        TextView tvCancle;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
