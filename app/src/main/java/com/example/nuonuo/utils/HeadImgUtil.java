package com.example.nuonuo.utils;

import com.bumptech.glide.request.RequestOptions;
import com.example.nuonuo.R;


public class HeadImgUtil {

    public static RequestOptions getHeadImgOptions(String gender){
        RequestOptions options = new RequestOptions();
//        options.transform(new GlideCircleTransform());
//        if ("男".equals(gender)) {
//            options.error(R.drawable.nantouxiang);
//        } else if ("女".equals(gender)) {
//            options.error(R.drawable.nvtouxiang);
//        } else {
//            options.error(R.drawable.default_headimg);
//        }
        options.error(R.drawable.head_img);
        return options;
    }

}
