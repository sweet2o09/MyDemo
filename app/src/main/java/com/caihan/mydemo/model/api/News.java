package com.caihan.mydemo.model.api;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by caihan on 2016/12/1.
 * 加法资讯,新闻主体
 * {
 * "total":1,
 * "rows":[
 * {
 * "id":1,//主键编号
 * "type":"1000",//类型
 * "title":"中青年抗癌计划正式启动，保障之路与你 同行！",//标题
 * "subtitle":"分享的文字",//分享的文字
 * "author":"111111",//作者
 * "time":"2016-11-29 00:00:00.0",//时间
 * "content":null,//内容，列表中不显示
 * "url":null,//连接地址，目前不需要
 * "commentCount":1024,//回复量
 * "thumbCount":1024//点赞量
 * }
 * ]
 * }
 */
public class News implements Serializable {
    private static final String TAG = "News";
    private static final long serialVersionUID = 2191299001396876017L;

    /**
     * total : 1
     * rows : [{"id":1,"type":"1000","level":"","title":"中青年抗癌计划正式启动，保障之路与你 同行！","subtitle":"分享的文字","author":"111111","showTime":"","time":"2016-11-29 00:00:00.0","content":"","userID":10089,"userName":"","userTime":"","status":"","imageurl":"http://dev.jiafahuzhu.com/resources/images/jfhz/upload/2016-12-01/info.png","commentCount":1024,"thumbCount":1024}]
     */
    public News() {
        this.rows = new ArrayList<NewsBean>();
    }

    private int total;
    private ArrayList<NewsBean> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ArrayList<NewsBean> getRows() {
        return rows;
    }

    public void setRows(ArrayList<NewsBean> rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "News{" +
                "total=" + total +
                ", rows=" + rows +
                '}';
    }

    public static class NewsBean implements Serializable {
        private static final long serialVersionUID = 4469134592517799924L;
        /**
         * id : 1
         * type : 1000
         * level :
         * title : 中青年抗癌计划正式启动，保障之路与你 同行！
         * subtitle : 分享的文字
         * author : 111111
         * showTime :
         * time : 2016-11-29 00:00:00.0
         * content :
         * userID : 10089
         * userName :
         * userTime :
         * status :
         * imageurl : http://dev.jiafahuzhu.com/resources/images/jfhz/upload/2016-12-01/info.png
         * commentCount : 1024
         * thumbCount : 1024
         */

        private int id = 0;
        private String type;
        private String level;
        private String title;
        private String subtitle;
        private String author;
        private String showTime;
        private String time;
        private String content;
        private int userID;
        private String userName;
        private String userTime;
        private String status;
        private String imageurl;
        private int commentCount;
        private int thumbCount;
        /**
         * news_url : http://dev.jiafahuzhu.com/api/news/newsList
         * news_title : (和谐)不得不说的事情
         * news_sub_title : (和谐)不得不说的事情
         * thumbnail_url : http://dev.jiafahuzhu.com/resources/images/jfhz/upload/2016-12-03/44b055e4-b32b-45ee-bd0b-6f7040d05940.jpg
         */

        private String news_url;
        private String news_title;
        private String news_sub_title;
        private String thumbnail_url;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getShowTime() {
            return showTime;
        }

        public void setShowTime(String showTime) {
            this.showTime = showTime;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getUserID() {
            return userID;
        }

        public void setUserID(int userID) {
            this.userID = userID;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserTime() {
            return userTime;
        }

        public void setUserTime(String userTime) {
            this.userTime = userTime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getImageurl() {
            return imageurl;
        }

        public void setImageurl(String imageurl) {
            this.imageurl = imageurl;
        }

        public int getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(int commentCount) {
            this.commentCount = commentCount;
        }

        public int getThumbCount() {
            return thumbCount;
        }

        public void setThumbCount(int thumbCount) {
            this.thumbCount = thumbCount;
        }

        @Override
        public String toString() {
            return "NewsBean{" +
                    "id=" + id +
                    ", type='" + type + '\'' +
                    ", level='" + level + '\'' +
                    ", title='" + title + '\'' +
                    ", subtitle='" + subtitle + '\'' +
                    ", author='" + author + '\'' +
                    ", showTime='" + showTime + '\'' +
                    ", time='" + time + '\'' +
                    ", content='" + content + '\'' +
                    ", userID=" + userID +
                    ", userName='" + userName + '\'' +
                    ", userTime='" + userTime + '\'' +
                    ", status='" + status + '\'' +
                    ", imageurl='" + imageurl + '\'' +
                    ", commentCount=" + commentCount +
                    ", thumbCount=" + thumbCount +
                    ", news_url='" + news_url + '\'' +
                    ", news_title='" + news_title + '\'' +
                    ", news_sub_title='" + news_sub_title + '\'' +
                    ", thumbnail_url='" + thumbnail_url + '\'' +
                    '}';
        }

        public String getNews_url() {
            return news_url;
        }

        public void setNews_url(String news_url) {
            this.news_url = news_url;
        }

        public String getNews_title() {
            return news_title;
        }

        public void setNews_title(String news_title) {
            this.news_title = news_title;
        }

        public String getNews_sub_title() {
            return news_sub_title;
        }

        public void setNews_sub_title(String news_sub_title) {
            this.news_sub_title = news_sub_title;
        }

        public String getThumbnail_url() {
            return thumbnail_url;
        }

        public void setThumbnail_url(String thumbnail_url) {
            this.thumbnail_url = thumbnail_url;
        }
    }
}
