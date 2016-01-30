package org.noorganization.instalist.server.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by damihe on 30.01.16.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppConfiguration {

    @JsonProperty("db_version")
    public int    mDatabaseVersion;

    @JsonProperty("db_jdbc_url")
    public String mDatabaseUrl;
}
