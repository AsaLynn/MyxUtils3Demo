package com.zxn.xutils3;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;
//@Table注解,对象和数据库发生关联.
//name指定表的名称
//onCreated = "sql"：当第一次创建表需要插入数据时候在此写sql语句
@Table(name = "tb_news",onCreated = "")
public class NewsEntity {

    /**
     * name = "id"：数据库表中的一个字段
     * isId = true：是否是主键
     * autoGen = true：是否自动增长
     * property = "NOT NULL"：添加约束
     */
    @Column(name = "id",isId = true,autoGen = true,property = "NOT NULL")
    private int id;
    @Column(name = "subject")
    private String subject;
    // @Column指定数据库中表字段相关属性,
    @Column(name = "summary")
    private String summary;
    @Column(name = "cover")
    private String cover;


    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
