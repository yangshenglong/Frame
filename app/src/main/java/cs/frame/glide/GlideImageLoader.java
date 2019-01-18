package cs.frame.glide;

import android.content.Context;
import android.widget.ImageView;

import com.youth.banner.loader.ImageLoader;

/**
 * Created by yangshenglong on 16/11/24.
 */
//轮播图
public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
//        Glide.with(context).load(path).into(imageView);
        GlideUtil.showImg(context,path.toString(),imageView);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
    }
}
