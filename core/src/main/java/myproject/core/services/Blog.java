package myproject.core.services;

import java.util.Date;

public class Blog {
    private String title;
    private String content;
    private Date date;


    public Blog(String title, String content, Date date){
        this.title = title;
        this.content = content;
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public Date getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Blogs{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", date=" + date +
                '}';
    }

}
