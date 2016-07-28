package com.sutton.ryan.macquarie.activities.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.sutton.ryan.macquarie.R;
import com.sutton.ryan.macquarie.activities.activities.DetailActivity;
import com.sutton.ryan.macquarie.activities.activities.NewsArticle;
import com.sutton.ryan.macquarie.activities.network.VolleySingelton;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by ryank on 29/06/2016.
 */
public class CardContentAdapter extends RecyclerView.Adapter<CardContentAdapter.ViewHolderCards> {

    private static ArrayList<NewsArticle> articleList = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private VolleySingelton volleySingleton;
    private ImageLoader imageLoader;
    private static RecyclerView.RecyclerListener itemListener;
    public static Bitmap thumbnail;

    public CardContentAdapter(Context context){
        layoutInflater = LayoutInflater.from(context);
        volleySingleton = VolleySingelton.getsInstance();
        imageLoader = volleySingleton.getImageLoader();
        this.itemListener = itemListener;

    }

    public void setNewsArticleList(ArrayList<NewsArticle> articleList){
        this.articleList = articleList;
        notifyItemRangeChanged(0, articleList.size());
    }

    @Override
    public ViewHolderCards onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_card, parent, false);
        ViewHolderCards viewHolder = new ViewHolderCards(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolderCards holder, int position) {
        NewsArticle currentArticle = articleList.get(position);
        holder.articleTitle.setText(currentArticle.getTitle());
        holder.articleDate.setText(currentArticle.getDate());
        holder.articleBrief.setText(currentArticle.getBrief());
        String imageURL = currentArticle.getImageURL();
        if(imageURL != null){
            imageLoader.get(imageURL, new ImageLoader.ImageListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }

                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    thumbnail = response.getBitmap();
                    holder.articleImage.setImageBitmap(response.getBitmap());
                }
            });
        }
//        holder.articleImage.setImageBitmap(R.drawable.sydney_bmp);
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    static class ViewHolderCards extends RecyclerView.ViewHolder{
        private TextView articleTitle;
        private TextView articleDate;
        private TextView articleBrief;
        private ImageView articleImage;

        public ViewHolderCards(View itemview){
            super(itemview);
            articleTitle = (TextView) itemview.findViewById(R.id.card_title);
            articleDate = (TextView) itemview.findViewById(R.id.card_date);
            articleBrief = (TextView) itemview.findViewById(R.id.card_text);
            articleImage = (ImageView) itemview.findViewById(R.id.card_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();
                    Log.v("Debug", "Selected card: " + position);
                    Context context = v.getContext();
                    Intent intent = new Intent(context, DetailActivity.class);

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    thumbnail.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();

                    intent.putExtra("thumbnail", byteArray);

                    intent.putExtra("brief", articleList.get(position).getBrief());
                    intent.putExtra("Title", articleList.get(position).getTitle());
                    context.startActivity(intent);
                }
            });

            // Adding Snackbar to Action Button inside card
            Button button = (Button)itemView.findViewById(R.id.action_button);
            button.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Snackbar.make(v, "Action is pressed",
                            Snackbar.LENGTH_LONG).show();
                }
            });

            ImageButton favoriteImageButton =
                    (ImageButton) itemView.findViewById(R.id.favorite_button);
            favoriteImageButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Snackbar.make(v, "Added to Favorite",
                            Snackbar.LENGTH_LONG).show();
                }
            });

            ImageButton shareImageButton = (ImageButton) itemView.findViewById(R.id.share_button);
            shareImageButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                     Snackbar.make(v, "Share article",
                            Snackbar.LENGTH_LONG).show();
                }
            });
        }

    }
}
