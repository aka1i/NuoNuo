package com.example.nuonuo.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.nuonuo.R;
import com.example.nuonuo.utils.ImgDetailUtil;
import com.example.nuonuo.utils.SizeUtils;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class EasyImgAdapter extends RecyclerView.Adapter<EasyImgAdapter.ViewHolder>{
    private List<String> strings;
    private Context context;
    public EasyImgAdapter(Context context, List<String> strings){
        this.strings=strings;
        this.context=context;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView view;

        private ViewHolder(View view){
            super(view);
            this.view = view.findViewById(R.id.linear_body_img);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        String s = strings.get(position);
        RequestOptions options=new RequestOptions()
                .error(R.drawable.login_bg)
                .placeholder(R.color.grey_1)
                .transform(new MultiTransformation(
                        new CenterCrop(),
                        new RoundedCornersTransformation(SizeUtils.dip2px(context,15), 0, RoundedCornersTransformation.CornerType.ALL)));


        Glide.with(context).load(s)
                .apply(options)
                .into(holder.view);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImgDetailUtil.startImgDetail(context,strings,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return strings.size();
    }

    @NonNull
    @Override
    public EasyImgAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view= LayoutInflater.from(context)
                .inflate(R.layout.item_linear_imgview,parent,false);
        return new EasyImgAdapter.ViewHolder(view);
    }


}
