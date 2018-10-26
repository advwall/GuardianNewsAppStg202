package com.example.android.guardiannewsappstg202;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * An {@link NewsAdapter} knows how to create a list item layout for each news(articles)
 * in the data source (a list of {@link NewsItem} objects).
 *
 * These list item layouts will be provided to an adapter view like ListView
 * to be displayed to the user.
 */
public class NewsAdapter extends ArrayAdapter<NewsItem> {

    public NewsAdapter(Context context, ArrayList<NewsItem> news) {
        super(context, 0, news);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.card_view, parent, false);
        }

        // Find the news at the given position in the list of news
        NewsItem currentNews = getItem(position);

        // Find the TextView with view ID of the title article
        TextView newsTitleTextView = listItemView.findViewById(R.id.title_article);
        String titleNews = currentNews.getTitleArticle();
        newsTitleTextView.setText(titleNews);

        // Find the TextView with view ID of the section article
        TextView newsSectionTextView = listItemView.findViewById(R.id.section_name);
        String newsSection = currentNews.getSection();
        newsSectionTextView.setText(newsSection);

        // Find the TextView with view ID of the date article was published
        TextView newsDateTextView = listItemView.findViewById(R.id.date_published);
        String datePublished = currentNews.getDate();
        newsDateTextView.setText(datePublished);

        // Find the TextView with view ID of the article s author
        TextView newsAuthorTextView = listItemView.findViewById(R.id.author_name);
        String authorName = currentNews.getAuthor();
        newsAuthorTextView.setText(authorName);

        return listItemView;
    }
}
