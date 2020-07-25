package com.shaadi.match.maker.featureModules.landing.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ajay
 */
public class Timezone {

    @SerializedName("offset")
    @Expose
    private String offset;
    @SerializedName("description")
    @Expose
    private String description;

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
