package com.example.android.guardiannewsappstg202;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Loads a list of News by using an AsyncTask to perform the
 * network request to the given URL.
 */
public class NewsLoader extends AsyncTaskLoader<List<NewsInformation>> {

    /** Query URL */
    private String mUrl;

    /**
     * Constructs a new {@link NewsLoader}.
     */
    public NewsLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<NewsInformation> loadInBackground() {
        // If there is no URL, return null.
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of news stories.
        return QueryUtils.fetchNewsData(mUrl);
    }
}
