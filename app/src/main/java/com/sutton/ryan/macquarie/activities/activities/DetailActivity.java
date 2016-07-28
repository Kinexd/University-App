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

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.sutton.ryan.macquarie.R;

/**
 * Provides UI for the Detail page with Collapsing Toolbar.
 */
public class DetailActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Set Collapsing Toolbar layout to the screen
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        // Set title of Detail page
        collapsingToolbar.setTitle(getString(R.string.item_title));
        Bundle extras = getIntent().getExtras();
        String title = "Placeholder Title";
        String brief = "Placeholder description";
        byte[] thumbByteArray = null;
        Bitmap bmp = null;
        if (extras != null) {
            brief = extras.getString("brief");
            title = extras.getString("Title");
            thumbByteArray = extras.getByteArray("thumbnail");
            bmp = BitmapFactory.decodeByteArray(thumbByteArray, 0, thumbByteArray.length);
            Log.v("Debug", "Intent Title: " + title);
        }
        collapsingToolbar.setTitle(title);
        TextView desc = (TextView) findViewById(R.id.detail_desc);
        desc.setText(brief);
        TextView subTitle = (TextView) findViewById(R.id.detail_title);
        subTitle.setText(title);
        ImageView thumbnail = (ImageView) findViewById(R.id.image);
        thumbnail.setImageBitmap(bmp);
    }

}
