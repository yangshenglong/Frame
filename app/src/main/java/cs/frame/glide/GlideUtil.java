package cs.frame.glide;


import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import cs.cs.com.frame.R;


public class GlideUtil  {

    public static void showImg(Context context, String imgUrl, ImageView imgView) {
        Glide.with(context)
                .load(imgUrl)
                .centerCrop()
                .error(R.mipmap.u0)
                .placeholder(R.mipmap.u0)
                .crossFade()
                .into(imgView);
    }
}

