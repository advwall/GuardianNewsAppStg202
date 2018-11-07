package com.example.android.guardiannewsappstg202;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Loads a list of News by using an AsyncTask to perform the
 * network request to the given URL.
 */
public class NewsLoader extends AsyncTaskLoader<List<NewsInformation>> {

    /** Tag for log messages */
    private static final String LOG_TAG = NewsLoader.class.getName();

    /** Query URL */
    private String mUrl;

    /**
     * Constructs a new {@link NewsLoader}.
     *
     * @param context of the activity
     */
    public NewsLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<NewsInformation> loadInBackground() {
        // If there is no URL, return null.
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of news stories.
        List<NewsInformation> newsInformations = QueryUtils.fetchNewsData(mUrl);
        return newsInformations;
    }
}
