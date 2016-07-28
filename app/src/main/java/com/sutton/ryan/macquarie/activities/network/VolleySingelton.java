package com.sutton.ryan.macquarie.activities.network;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.sutton.ryan.macquarie.activities.activities.MyApplication;

/**
 * Created by ryank on 29/06/2016.
 */
public class VolleySingelton {
    private static VolleySingelton sInstance = null;
    private RequestQueue nRequestQueue;
    private ImageLoader nImageLoader;
    private VolleySingelton(){
        nRequestQueue = Volley.newRequestQueue(MyApplication.getAppContext());
        nImageLoader = new ImageLoader(nRequestQueue, new ImageLoader.ImageCache(){
            private LruCache<String, Bitmap> cache = new LruCache<>((int)Runtime.getRuntime().maxMemory()/1024/8);

            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });
    }

    public static VolleySingelton getsInstance() {
        if(sInstance == null){
            sInstance = new VolleySingelton();
        }
        return sInstance;
    }

    public RequestQueue getnRequestQueue(){
        return nRequestQueue;
    }

    public ImageLoader getImageLoader(){
        return nImageLoader;
    }

}
