package com.example.android.guardiannewsappstg202;

/**
 * An {@link NewsInformation} object contains information related to a single Article.
 */

public class NewsInformation {
    private String title, section, date, newsUrl, author;


    public NewsInformation(String newsTitle, String newsSection, String newsDate, String newsURL, String newsAuthor) {
        title = newsTitle;
        section = newsSection;
        date = newsDate;
        newsUrl = newsURL;
        author = newsAuthor;
    }

    /**
     * Returns the Article Title of the News.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the Section of the article news.
     */
    public String getSection() {
        return section;
    }

    /**
     * Returns the Date when the article was published.
     */
    public String getDate() {
        return date;
    }

    /**
     * Returns the website of the News.
     */
    public String getUrl() {
        return newsUrl;
    }

    /**
     * Returns the author of the article of the News.
     */
    public String getAuthor() {
        return author;
    }

}
