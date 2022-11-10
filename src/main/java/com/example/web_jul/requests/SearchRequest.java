package com.example.web_jul.requests;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Created on 13.07.2022. by Andrija inside package com.example.web_jul.requests.
 */
public class SearchRequest {
    @NotNull(message = "Search is required")
    @NotEmpty(message = "Search is required")
    private String search;

    public SearchRequest() {
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
