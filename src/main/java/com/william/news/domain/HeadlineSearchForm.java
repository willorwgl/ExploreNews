package com.william.news.domain;


import com.google.common.base.Joiner;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@NoArgsConstructor
@Data
public class HeadlineSearchForm {

    private String keyword;
    private String country;
    private String category;
    private String sources;

    public void setSources(ArrayList<String> sources) {
        if (sources != null) {
            this.sources = Joiner.on(",").join(sources);
        }
    }
}
