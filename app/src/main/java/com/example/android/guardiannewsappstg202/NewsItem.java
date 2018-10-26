package com.example.android.guardiannewsappstg202;

/**
 * An {@link NewsItem} object contains information related to a single Article.
 */

public class NewsItem {
    /** Title of the article */
    private String title;

    /** Section of the News */
    private String section;

    /** Date of the article published */
    private String date;

    /** Website URL of the article */
    private String url;

    /** Author of the article */
    private String author;

    public NewsItem(String newsTitle, String newsSection, String newsDate, String newsURL, String newsAuthor) {
        this.title = newsTitle;
        this.section = newsSection;
        this.date = newsDate;
        this.url = newsURL;
        this.author = newsAuthor;
    }

    /**
     * Returns the Article Title of the News.
     */
    public String getTitleArticle() {
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
        return url;
    }

    /**
     * Returns the author of the article of the News.
     */
    public String getAuthor() {
        return author;
    }
}
