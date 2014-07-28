package com.launcher.informsmanage.Model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by chen on 14-7-24.
 */
@Table(name = "Items")
public class InformData extends Model{


    public String id;


    @Column(name = "title")
    public String title;

    @Column(name = "author")
    public String author;

    @Column(name = "created_at")
    public String created_at;

    @Column(name = "updated_at")
    public String updated_at;

    @Column(name = "brief")
    public String brief;

    @Column(name = "context")
    public String context;
}
