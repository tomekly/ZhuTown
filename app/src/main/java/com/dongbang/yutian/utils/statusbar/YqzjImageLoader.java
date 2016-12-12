package com.dongbang.yutian.utils.statusbar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.dongbang.yutian.R;
import com.dongbang.yutian.view.YqzjApplication;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.MemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions.Builder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.utils.StorageUtils;
import java.io.File;
import java.util.Collection;
import java.util.logging.Logger;

/**
 * 图片加载帮助类
 */
public class YqzjImageLoader {

    protected static final String TAG = YqzjImageLoader.class.getSimpleName();
    private static final ColorDrawable TRANSPARENT_DRAWABLE = new ColorDrawable(android.R.color.transparent);
    private static final String DISK_CACHE_PATH_BITMAP_UTILS = YqzjConfig.ConstantCode.DISK_IMAGE_CACHE_PATH_2;
    private static long maxMemory = Runtime.getRuntime().maxMemory();
    // 缓存内存大小为当前可用内存的1/4
    private static final int MEMEORY_CACHE_SIZE = (int) (maxMemory / 1024) / 5;
    // private static final int MEMEORY_CACHE_SIZE = 10 * 1024 * 1024;
    private static final int DISK_CACHE_SIZE = 1024 * 1024 * 200;

    private static YqzjImageLoader oochImageLoader;
    private static Context context;


    private static ImageLoader uilImageLoader = ImageLoader.getInstance();

    public enum ImageUtilType {
        UIL
    }

    private ImageUtilType imageType = ImageUtilType.UIL;
    private boolean showAnim = true;

    private YqzjImageLoader() {
    }

    /**
     * 使用方法：</br> ImageLoader imageLoader =
     * XYApplication.getInstance().getImageLoader();
     * imageLoader.displayImage(imageView,url,size);
     *
     * @return
     */
    public static YqzjImageLoader getInstance() {
        if (oochImageLoader == null) {
            oochImageLoader = new YqzjImageLoader();
            context = YqzjApplication.mContext;
            initImageLoader();
        }
        return oochImageLoader;
    }

    public DisplayImageOptions initImageLoaderOption(boolean showAnim) {
        DisplayImageOptions options = createImageOptions(showAnim, false);
        return options;
    }

    public DisplayImageOptions initImageLoaderOption(boolean showAnim, boolean considerExif) {
        DisplayImageOptions options = createImageOptions(showAnim, considerExif);
        return options;
    }

    private DisplayImageOptions createImageOptions(boolean showAnim, boolean considerExif) {
        Builder builder = new Builder();

        builder.showImageOnLoading(R.drawable.background_photo);
        builder.showImageForEmptyUri(R.drawable.background_photo);
        builder.showImageOnFail(R.drawable.background_photo);
        builder.cacheInMemory(true);
        builder.cacheOnDisk(true);
        builder.considerExifParams(considerExif);
        builder.bitmapConfig(Bitmap.Config.RGB_565);
        builder.imageScaleType(ImageScaleType.EXACTLY);
        builder.resetViewBeforeLoading(true);
        if (showAnim) {
            builder.displayer(new FadeInBitmapDisplayer(500, true, false, false));
//			 builder.displayer(new FadeInBitmapDisplayer(300));//是否图片加载好后渐入的动画时间
        }
        DisplayImageOptions options = builder.build();
        return options;
    }


    public static void initImageLoader() {
        // This configuration tuning is custom. You can tune every option, you
        // may tune some of them,
        // or you can create default configuration by
        // ImageLoaderConfiguration.createDefault(this);
        // method.
        // File cacheDir = StorageUtils.getOwnCacheDirectory(context,
        // "xy/img/Cache");
        File cacheDir = StorageUtils.getOwnCacheDirectory(context, YqzjConfig.ConstantCode.UIL_BASE_PATH);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.FIFO).threadPoolSize(5)
                        // 线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory().memoryCacheExtraOptions(480, 800)
                .memoryCache(new LruMemoryCache(MEMEORY_CACHE_SIZE)).memoryCacheSize(MEMEORY_CACHE_SIZE).diskCacheSize(DISK_CACHE_SIZE)// 磁盘缓存大小
                .diskCache(new UnlimitedDiscCache(cacheDir))// 自定义缓存路径
                        // .writeDebugLogs() // Remove for release app
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }


    public ImageLoader getImageLoader() {
        return uilImageLoader;
    }


    public void clearDiskCache() {
        uilImageLoader.clearDiskCache();
    }


    public double clearTotalCache() {
        File diskCacheFile = ImageLoader.getInstance().getDiskCache().getDirectory();
        double diskCacheSize = getDirSize(diskCacheFile);
        MemoryCache memoryCache = ImageLoader.getInstance().getMemoryCache();
        Collection<String> keys = memoryCache.keys();
        double sizes = 0;
        for (String str : keys) {
            double byte_size = memoryCache.get(str).getByteCount() / 1024;
            sizes = sizes + byte_size;
        }
        File file = new File(DISK_CACHE_PATH_BITMAP_UTILS);
        double bitmapCacheSize = getDirSize(file);
        double totalSize = diskCacheSize + sizes + bitmapCacheSize;

        ImageLoader.getInstance().clearDiskCache();
        ImageLoader.getInstance().clearMemoryCache();
        return totalSize;

    }

    public static double getDirSize(File file) {
        //判断文件是否存在     
        if (file.exists()) {
            //如果是目录则递归计算其内容的总大小    
            if (file.isDirectory()) {
                File[] children = file.listFiles();
                double size = 0;
                for (File f : children)
                    size += getDirSize(f);
                return size;
            } else {//如果是文件则直接返回其大小,以“K”为单位   
                double size = (double) file.length() / 1024;
                return size;
            }
        } else {
            return 0.0;
        }
    }

    /**
     * @param who plz tell me who are you
     */
    public void clearMemoryCache(String who) {
        if (TextUtils.isEmpty(who)) {
            throw new RuntimeException("plz tell me who are you ?");
        }
//        Logger.e(TAG, who + " clear memory image cache");
        uilImageLoader.clearMemoryCache();
    }

    /**
     * 加载手机本地图片，加载本地图片只使用UIL或者BitmapUtils
     *
     * @param view
     * @param url
     */
    public void displayImageLocal(View view, String url) {
        privateDisplayLocal(view, url, imageType, showAnim);
    }

    public void displayImageLocal(View view, String url, boolean showAnim) {
        privateDisplayLocal(view, url, imageType, showAnim);
    }

    public void displayImageLocal(View view, String url, ImageUtilType utilType) {
//		displayLocal(view, url, utilType, showAnim);
        privateDisplayLocal(view, url, utilType, showAnim);
    }

    public void displayImageLocal(View view, String url, ImageUtilType utilType, boolean showAnim) {
//		displayLocal(view, url, utilType, showAnim);
        privateDisplayLocal(view, url, utilType, showAnim);
    }

    private void privateDisplayLocal(View view, String url, ImageUtilType utilType, boolean showAnim) {
        displayLocal(view, url, utilType, showAnim);
    }

    private void displayLocal(View view, String url, ImageUtilType utilType, boolean showAnim) {
        try {
            if (TextUtils.isEmpty(url)) {
                return;
            }
            DisplayImageOptions option = initImageLoaderOption(showAnim, true);
            String path = Uri.fromFile(new File(url)).toString();
            String path2 = "file://" + url;
//            Logger.d(TAG, "path:" + path);
//            Logger.d(TAG, "path2:" + path2);
            getImageLoader().displayImage(path2, (ImageView) view, option, null);

//            Logger.d(TAG, "加载本地图片");
        } catch (Exception e) {
//            Logger.e(TAG, "displayLocal imageType:" + imageType, e);
        }

    }

    /**
     * 加载需要进度条的图片
     *
     * @param view
     * @param url
     * @param progressBar
     * @param ivPlaceholder
     */
    public void displayImageProgress(View view, final String url, final ProgressBar progressBar, final ImageView ivPlaceholder) {
        switch (imageType) {
            case UIL:
//                Logger.d(TAG, "加载大图图片使用 UIL");
                DisplayImageOptions option = initImageLoaderOption(showAnim);
                ivPlaceholder.setTag(url);
                getImageLoader().displayImage(url, (ImageView) view, option, new MySimpleImageLoadingListener(ivPlaceholder, progressBar, url));
                break;
        }
    }

    /**
     * @param view
     * @param url
     */
    public void displayImage(View view, String url) {
        getBitmap(view, url, showAnim);
    }

    public void displayImage(View view, String url, boolean showAnim) {
        getBitmap(view, url, showAnim);
    }


    public void displayImage(View view, String url, ImageUtilType type, boolean showAnim) {
        privateGetBitmap(view, url, type, showAnim);
    }

    public void displayImage(View view, String url, ImageUtilType type) {
        privateGetBitmap(view, url, type, showAnim);
    }

    /**
     * 加载图片，最终调用的方法，便于切换不同的图片加载工具
     *
     * @param view
     * @param url
     * @param
     */
    private void getBitmap(final View view, String url, boolean showAnim) {
        try {
            privateGetBitmap(view, url, imageType, showAnim);
        } catch (Exception e) {
//            Logger.e(TAG, "getBitmap imageType:" + imageType, e);
        }
    }

    private void privateGetBitmap(final View view, String url, ImageUtilType type, boolean showAnim) {
        switch (type) {
            case UIL:
                if (!url.startsWith(YqzjConfig.ConstantCode.HTTP_PREFIX)) {
                    url = "file://" + url;
                }
                DisplayImageOptions option = initImageLoaderOption(showAnim);
                getImageLoader().displayImage(url, (ImageView) view, option, null);
                break;
            default:
                break;
        }
    }


    private class MySimpleImageLoadingListener extends SimpleImageLoadingListener {

        private View ivPlaceholder;
        private ProgressBar progressBar;
        private String url;

        public MySimpleImageLoadingListener(View ivPlaceholder, ProgressBar progressBar, String url) {
            this.ivPlaceholder = ivPlaceholder;
            this.progressBar = progressBar;
            this.url = url;
        }

        @Override
        public void onLoadingStarted(String imageUri, View view) {
            if (ivPlaceholder != null) {
                ivPlaceholder.setVisibility(View.VISIBLE);
            }

            if (progressBar != null) {
                progressBar.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
            if (ivPlaceholder != null) {
                ivPlaceholder.setVisibility(View.GONE);
            }
            view.setVisibility(View.VISIBLE);
            String message = null;
            switch (failReason.getType()) {
                case IO_ERROR:
                    message = "Input/Output error";
                    break;
                case DECODING_ERROR:
                    message = "Image can't be decoded";
                    break;
                case NETWORK_DENIED:
                    message = "Downloads are denied";
                    break;
                case OUT_OF_MEMORY:
                    message = "Out Of Memory error";
                    break;
                case UNKNOWN:
                    message = "Unknown error";
                    break;
            }
//            Logger.e(TAG, "加载图片失败：" + message);
        }

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            view.setVisibility(View.VISIBLE);
            if (ivPlaceholder != null) {
                ivPlaceholder.setVisibility(View.GONE);
            }
            if (progressBar != null) {
                progressBar.setVisibility(View.GONE);
            }

//            Logger.d(TAG, "加载大图url:" + this.url);
        }
    }


    /**
     * 加载图片时默认显示的动画
     *
     * @param imageView
     * @param bitmap
     */
    public static void fadeInDisplay(ImageView imageView, Bitmap bitmap) {
        final TransitionDrawable transitionDrawable = new TransitionDrawable(new Drawable[]{TRANSPARENT_DRAWABLE,
                new BitmapDrawable(imageView.getResources(), bitmap)});
        imageView.setImageDrawable(transitionDrawable);
        transitionDrawable.startTransition(100);
    }


    public void loadImage(final ImageView view, String url) {
        loadImage(view, url, null, null);
    }

    public void loadImage(final ImageView view, String url, final Object mAttacher, final ProgressBar progressBar) {
        ImageLoader.getInstance().loadImage(url, createImageOptions(true, false), new ImageLoadingListener() {

            @Override
            public void onLoadingStarted(String arg0, View arg1) {
                if(progressBar != null){
                    progressBar.setVisibility(View.VISIBLE);
                }
                view.setImageResource(R.drawable.background_photo);
            }

            @Override
            public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
                if(progressBar != null){
                    progressBar.setVisibility(View.GONE);
                }
                view.setImageResource(R.drawable.background_photo);
            }

            @Override
            public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
                view.setImageBitmap(arg2);
                if(mAttacher != null){
//                    mAttacher.update();
                }

                if(progressBar != null){
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onLoadingCancelled(String arg0, View arg1) {
                if(progressBar != null){
                    progressBar.setVisibility(View.GONE);
                }
                view.setImageResource(R.drawable.background_photo);
            }
        });
    }

}
