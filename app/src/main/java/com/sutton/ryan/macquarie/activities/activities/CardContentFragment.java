/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sutton.ryan.macquarie.activities.activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.sutton.ryan.macquarie.R;
import com.sutton.ryan.macquarie.activities.adapters.CardContentAdapter;
import com.sutton.ryan.macquarie.activities.network.VolleySingelton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.*;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

/**
 * Provides UI for the view with Cards..
 */
public class CardContentFragment extends Fragment {

    private static final String TAG = "CardContent";
    private CardContentAdapter cardsAdapter;
    public String stringUrl = "https://www.google.com/";
    public ArrayList<ArrayList<String>> titleListPub = new ArrayList<>();
    public ArrayList<NewsArticle> articleList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ArrayAdapter mNewsAdapter = new ArrayAdapter<String>(getActivity(), R.layout.item_card, R.id.card_title, new ArrayList<String>());
//        ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
//        if (networkInfo != null && networkInfo.isConnected()) {
//            new DownloadNewsData().execute(stringUrl);
//        } else {
//            Log.v("NetworkCard" , "no network available");
//        }



        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);
        cardsAdapter = new CardContentAdapter(getActivity());
        if(cardsAdapter.getItemCount()<=0){
            new DownloadNewsData().execute(stringUrl);
        }

//        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        cardsAdapter = new CardContentAdapter(getActivity());
        recyclerView.setAdapter(cardsAdapter);
        return recyclerView;
    }

    private class DownloadNewsData extends AsyncTask<String, Void, ArrayList<NewsArticle>>{
        RequestQueue requestQueue = VolleySingelton.getsInstance().getnRequestQueue();

        @Override
        protected ArrayList<NewsArticle> doInBackground(String... urls){
            ArrayList<String> titleListFeat = new ArrayList<>();
            NewsArticle currentArticle = new NewsArticle();
            if(articleList.size()>0){
                setUI();
                return null;
            }
            try {
                String url = "https://www.mq.edu.au/newsroom/";
                Document doc = Jsoup.connect(url).get();
                Elements featureStory = doc.getElementsByClass("feature-story-text");
                Elements feature = doc.getElementsByClass("feature-story");
                Element featureLink = featureStory.select("a").first();
                String featureTitle = featureLink.text();
                String featureImage = feature.select("a > img").first().attr("src");
                Elements featurePar = featureStory.select("p");
                String featureDate = featurePar.get(0).text();
                String featureBrief = featurePar.get(1).text();
                Elements NewsPosts = doc.getElementsByClass("post");
                titleListFeat.add(featureTitle);
                titleListFeat.add(featureDate);
                titleListFeat.add(featureImage);
                titleListFeat.add(featureBrief);
                titleListPub.add(titleListFeat);
                currentArticle.setTitle(featureTitle);
                currentArticle.setDate(featureDate);
                currentArticle.setBrief(featureBrief);
                currentArticle.setImageURL(featureImage);
                articleList.add(currentArticle);
                for (Element nP : NewsPosts) {
                    currentArticle = new NewsArticle();
                    ArrayList<String> titleList = new ArrayList<>();
                    String postTitle = nP.select("h3").first().text();
                    String postImage = nP.select("a > img").first().attr("src");
                    Elements postPar = nP.select("p");
                    String postDate = postPar.get(0).text();
                    String postBrief = postPar.get(2).text();
                    titleList.add(postTitle);
                    titleList.add(postDate);
                    titleList.add(postImage);
                    titleList.add(postBrief);
                    currentArticle.setTitle(postTitle);
                    currentArticle.setDate(postDate);
                    currentArticle.setBrief(postBrief);
                    currentArticle.setImageURL(postImage);
                    articleList.add(currentArticle);
                    Log.v(TAG, "Title: " + postDate + " " + postTitle + " " + postImage);
                    titleListPub.add(titleList);
                    setUI();
                }
            }
            catch(IOException ex){
                Logger.getLogger(CardContentFragment.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
            return null;
        }

        private void setUI(){
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    cardsAdapter.setNewsArticleList(articleList);
                }
            });
        }


//        @Override
        protected void onPostExecute(ArrayList<String> result){
//            Toast.makeText(getContext(), titleListPub.get(1), Toast.LENGTH_LONG).show();
            cardsAdapter.setNewsArticleList(articleList);

            for(ArrayList<String> curTitle : titleListPub){
//                Toast.makeText(getContext(), curTitle, Toast.LENGTH_LONG).show();
//                TextView cardTitle = (TextView) findViewById(R.id.card_title);
            }

            for(ArrayList<String> curNewsArt : titleListPub){
                String curTitle = curNewsArt.get(0);
//                    TextView cardTitle = (TextView) findViewById(R.id.card_title);
//                    cardTitle.setText(curTitle);
                Toast.makeText(getContext(), curTitle, Toast.LENGTH_LONG).show();
                Log.v(TAG, "Array Loop: " + curTitle);
            }
            if(result != null){
                for(ArrayList<String> curNewsArt : titleListPub){
                    String curTitle = curNewsArt.get(0);
//                    TextView cardTitle = (TextView) findViewById(R.id.card_title);
//                    cardTitle.setText(curTitle);
                    Toast.makeText(getContext(), curTitle, Toast.LENGTH_LONG).show();
                    Log.v(TAG, "Array Loop: " + curTitle);
                }
            }
        }
    }

//    public ArrayList<String> newsLoader{
//        ArrayList<String> titleList = new ArrayList<String>();
//        try {
//            String url = "https://www.mq.edu.au/newsroom/";
//            Document doc = Jsoup.connect(url).get();
//            Elements featureStory = doc.getElementsByClass("feature-story-text");
//            Elements feature = doc.getElementsByClass("feature-story");
//            Element featureLink = featureStory.select("a").first();
//            String featureTitle = featureLink.text();
//            String featureImage = feature.select("a > img").first().attr("src").replace("-221x146", "");
//            Elements featurePar = featureStory.select("p");
//            String featureDate = featurePar.get(0).text();
//            String featureBrief = featurePar.get(1).text();
//            Elements NewsPosts = doc.getElementsByClass("post");
//            titleList.add(featureTitle);
//            for (Element nP : NewsPosts) {
//                String postTitle = nP.select("h3").first().text();
//                String postImage = nP.select("a > img").first().attr("src").replace("-130x86", "");
//                Elements postPar = nP.select("p");
//                String postDate = postPar.get(0).text();
//                String postBrief = postPar.get(2).text();
//                titleList.add(postTitle);
//                Log.v(TAG, "Title: " + postTitle);
//            }
//        }
//        catch(IOException ex){
//            Logger.getLogger(CardContentFragment.class.getName())
//                    .log(Level.SEVERE, null, ex);
//        }
//        return null;
//    };

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_card, parent, false));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, DetailActivity.class);
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

//    /**
//     * Adapter to display recycler view.
//     */
//    public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
//        // Set numbers of Card in RecyclerView.
//        private static final int LENGTH = 18;
//        private Context context;
//        ArrayList<String> titleListPub;
//
//
//
//        @Override
//        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
//        }
//
//        @Override
//        public void onBindViewHolder(ViewHolder holder, int position) {
//            // no-op
//        }
//
//        @Override
//        public int getItemCount() {
//            return LENGTH;
//        }
//
//    }
}
