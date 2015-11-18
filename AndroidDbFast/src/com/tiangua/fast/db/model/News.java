
package com.tiangua.fast.db.model;

public class News {

    private int _id;
    
    private String news_id;

    private String news_title;

    private String news_content;

    private String news_author;

    public int get_id()
    {
        return _id;
    }

    public void set_id(int _id)
    {
        this._id = _id;
    }

    public String getNews_id()
    {
        return news_id;
    }

    public void setNews_id(String news_id)
    {
        this.news_id = news_id;
    }

    public String getNews_title()
    {
        return news_title;
    }

    public void setNews_title(String news_title)
    {
        this.news_title = news_title;
    }

    public String getNews_content()
    {
        return news_content;
    }

    public void setNews_content(String news_content)
    {
        this.news_content = news_content;
    }

    public String getNews_author()
    {
        return news_author;
    }

    public void setNews_author(String news_author)
    {
        this.news_author = news_author;
    }
    
    
}
