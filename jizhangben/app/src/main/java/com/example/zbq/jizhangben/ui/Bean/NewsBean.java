package com.example.zbq.jizhangben.ui.Bean;

/**
 * Created by zbq on 18-3-18.
 */

public class NewsBean {
    private String newsTitle;   //新闻标题
    private String newsUrl;     //新闻链接地址
    private String newsContent; //新闻概要
    private String newsTime;    //新闻时间
    private String nesAuthor;   //作者
    private String newsSource;  //来源
    private String newsLookNum; //浏览人数

    public NewsBean( String newsTitle, String newsUrl, String newsContent, String newsTime, String nesAuthor,String newsSource,String newslookNum){
        this.newsTitle=newsTitle;
        this.nesAuthor=nesAuthor;
        this.newsContent=newsContent;
        this.newsTime=newsTime;
        this.newsUrl=newsUrl;
        this.newsSource=newsSource;
        this.newsLookNum=newslookNum;
    }

    public void setNesAuthor(String nesAuthor) {
        this.nesAuthor = nesAuthor;
    }

    public String getNesAuthor() {
        return nesAuthor;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsTime(String newsTime) {
        this.newsTime = newsTime;
    }

    public String getNewsTime() {
        return newsTime;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsLookNum(String newsLookNum) {
        this.newsLookNum = newsLookNum;
    }

    public String getNewsLookNum() {
        return newsLookNum;
    }

    public void setNewsSource(String newsSource) {
        this.newsSource = newsSource;
    }

    public String getNewsSource() {
        return newsSource;
    }
}
