package com.example.api;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.signature.ObjectKey;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder>
{
    List<Article> articles;
    Context context;

    public Adapter(List<Article> articles, Context context) {
        this.articles = articles;
        this.context = context;
    }

    @NonNull
    @Override
    public Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {View view = LayoutInflater.from(context).inflate(R.layout.news, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.MyViewHolder holder, int position) {
        final MyViewHolder holders = holder;
        Article model = articles.get(position);
        LruCache<String, Bitmap> memCache = new LruCache<String, Bitmap>((int) (Runtime.getRuntime().maxMemory() / (1024 * 4))) {
            @Override
            protected int sizeOf(String key, Bitmap image) {
                return image.getByteCount() / 1024;
            }
        };
        Bitmap image = memCache.get("imagefile");
        if (image != null) {
            //Bitmap exists in cache.
            holders.img.setImageBitmap(image);
        } else {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL)
                    .signature(new ObjectKey(System.currentTimeMillis())).encodeQuality(70);
            requestOptions.priority(Priority.IMMEDIATE);
            requestOptions.skipMemoryCache(false);
            requestOptions.onlyRetrieveFromCache(true);
            requestOptions.priority(Priority.HIGH);
            requestOptions.isMemoryCacheable();
            requestOptions.diskCacheStrategy(DiskCacheStrategy.DATA);

            requestOptions.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
            //   requestOptions.placeholder(Utils.getRandomDrawbleColor());
            requestOptions.centerCrop();

            Glide.with(context)
                    .load(model.getUrltoImage())
                    .thumbnail(
                            Glide.with(context).load(model.getUrltoImage())
                    )
                    .apply(requestOptions)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            //  spinKitView.setVisibility(View.GONE);


                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                            return false;
                        }
                    })

                    .into(holders.img);

        }
        holders.title.setText(model.getTitle());
        holders.publish.setText(model.getPublishAt());

    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        MaterialTextView title;
        MaterialTextView publish;
        ImageView img;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.Title);
            publish=itemView.findViewById(R.id.publish);
            img=itemView.findViewById(R.id.image);
        }
    }
}
