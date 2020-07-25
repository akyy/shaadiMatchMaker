package com.shaadi.match.maker.db.tables;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.shaadi.match.maker.featureModules.landing.models.Info;
import com.shaadi.match.maker.featureModules.landing.models.Result;
import com.shaadi.match.maker.utils.DataTypeConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ajay
 */
@Entity(tableName = "matching_user_table")
public class MatchingUsersTable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "results")
    @TypeConverters(DataTypeConverter.class)
    @SerializedName("results")
    @Expose
    private List<Result> results = new ArrayList<>();

    @ColumnInfo(name = "info")
    @TypeConverters(DataTypeConverter.class)
    @SerializedName("info")
    @Expose
    private Info info;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }
}
