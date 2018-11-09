package com.example.android.guardiannewsappstg202;

//This app was made as part of Udacity's Grow with Google Scholarship. The structure and content of this code
//was made with the help of Udacity's class, mentors, as well various tutorials available online.
//Other apps which influenced this code includes: Quake Report and the Musical Structure App
//"Swipe Refresh Layout: How to Use --> https://antonioleiva.com/swiperefreshlayout/
//Additionally, extensive use was made of https://developer.android.com/

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<NewsInformation>>,
        SwipeRefreshLayout.OnRefreshListener {

    /**Constant value for the news loader ID. We can choose any integer.*/
    private static final int NEWS_LOADER_ID = 1;

    /**Accesses the api key resource.*/
//    private String API_KEY = getString(R.string.api_key);

    /**Constant value for the news loader ID. We can choose any integer.*/
    private String REQUEST_URL = "https://content.guardianapis.com/search?show-tags=contributor&api-key=8a0740e0-7c36-47a1-a721-5549be39411b";

    /** Adapter for the list of News */
    private NewsAdapter adapter;

    /** TextView that is displayed when the list is empty */
    private TextView emptyStateTextView;

    /** SwipeRefreshLayout for refresh and load more new news */
    private SwipeRefreshLayout swipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the reference of SwipeRefreshLayout ID
        swipe = findViewById(R.id.swipe_refresh);
        swipe.setOnRefreshListener(this);
        swipe.setColorSchemeColors(getResources().getColor(R.color.colorAccent));

        ListView newsListView = findViewById(R.id.list);

        emptyStateTextView = findViewById(R.id.empty_view);
        newsListView.setEmptyView(emptyStateTextView);

        adapter = new NewsAdapter(this, new ArrayList<NewsInformation>());
        newsListView.setAdapter(adapter);

        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current news that was clicked on
                NewsInformation currentNews = adapter.getItem(position);
                Uri newsUri = Uri.parse(currentNews.getUrl());
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);
                startActivity(websiteIntent);
            }
        });

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);

        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            emptyStateTextView.setText(R.string.no_internet_connection);
        }
    }

    @Override
    public Loader<List<NewsInformation>> onCreateLoader(int id, Bundle args) {
        //This is where your settings preferences are applied.
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        String Search = sharedPrefs.getString(getString(R.string.settings_search_key),
                getString(R.string.settings_Search_default));
        String orderBy = sharedPrefs.getString(getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default));
        String section = sharedPrefs.getString(getString(R.string.settings_section_key),
                getString(R.string.settings_section_default));

        Uri baseUri = Uri.parse(REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        if (!Search.equals("")) {
            uriBuilder.appendQueryParameter("q", Search);
            orderBy = getString(R.string.settings_order_by_relevance_value);
        }
        if (Search.equals("") && orderBy.equals(getString(R.string.settings_order_by_relevance_value))) {
            orderBy = getString(R.string.settings_order_by_newest_value);
        }
        if (!section.equals("")) {
            if (!section.equals(getString(R.string.settings_section_default_value))) {
                uriBuilder.appendQueryParameter("section", section);
            }
        }
        return new NewsLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<NewsInformation>> loader, List<NewsInformation> newsInformations) {
        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        emptyStateTextView.setText(R.string.no_internet_connection);

        adapter.clear();

        if (newsInformations != null && !newsInformations.isEmpty()) {
            adapter.addAll(newsInformations);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<NewsInformation>> loader) {
        // Clears existing data
        adapter.clear();
    }


    @Override
    //Call .restartLoader() method to fetch news data with swipe
    public void onRefresh() {
        getLoaderManager().restartLoader(0, null, this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipe.setRefreshing(false);
            }
        }, 5000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the main options from the res/main/main.xml file.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.setting_button) {
            // Display the SettingsActivity
            Intent settingsIntent = new Intent(this, NewsSettings.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
