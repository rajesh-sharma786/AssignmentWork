package com.example.rajesh.jakewhartonrepositorylist;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class JakeWhartonDataEntity extends RealmObject implements Parcelable {


    @PrimaryKey
    private long id;

    private String name;
    private String htmlurl;
    private String description;
    private String language;
    private int watchers_count;
    private int stargazers_count;

    public JakeWhartonDataEntity()
    {

    }


    public JakeWhartonDataEntity(long id, String name, String htmlurl, String description, String language, int watchers_count, int stargazers_count)
    {
      this.id= id;
      this.name=name;
      this.htmlurl=htmlurl;
      this.description=description;
      this.language=language;
      this.watchers_count=watchers_count;
      this.stargazers_count=stargazers_count;



    }

    public JakeWhartonDataEntity(Parcel in) {
        id = in.readLong();
        name = in.readString();
        htmlurl = in.readString();
        description = in.readString();
        language = in.readString();
        watchers_count = in.readInt();
        stargazers_count = in.readInt();
    }

    public static final Creator<JakeWhartonDataEntity> CREATOR = new Creator<JakeWhartonDataEntity>() {
        @Override
        public JakeWhartonDataEntity createFromParcel(Parcel in) {
            return new JakeWhartonDataEntity(in);
        }

        @Override
        public JakeWhartonDataEntity[] newArray(int size) {
            return new JakeWhartonDataEntity[size];
        }
    };

    public void setId(long id) {
        this.id = id;
    }
    public long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setHtmlurl(String htmlurl) {
        this.htmlurl = htmlurl;
    }

    public String getHtmlurl() {
        return htmlurl;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }

    public void setWatchers_count(int watchers_count) {
        this.watchers_count = watchers_count;
    }

    public int getWatchers_count() {
        return watchers_count;
    }

    public void setStargazers_count(int stargazers_count) {
        this.stargazers_count = stargazers_count;
    }

    public int getStargazers_count() {
        return stargazers_count;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(htmlurl);
        dest.writeString(description);
        dest.writeString(language);
        dest.writeInt(watchers_count);
        dest.writeInt(stargazers_count);
    }
}
