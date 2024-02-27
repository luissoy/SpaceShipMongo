package com.luissoy.spaceshipmongo.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;


import java.util.List;

@Getter
@Setter
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class PageResponse<T> {
    @JsonProperty("size")
    private final Long size;

    @JsonProperty("pages")
    private final Integer pages;

    @JsonProperty("collection")
    private final List<T> collection;

    public PageResponse(Page<T> page) {
        this.size = page.getTotalElements();
        this.pages = page.getTotalPages();
        this.collection = page.getContent();
    }
}
