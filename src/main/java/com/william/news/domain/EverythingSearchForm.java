package com.william.news.domain;

import com.google.common.base.Joiner;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Data
@NoArgsConstructor
public class EverythingSearchForm {

    private String keyword;
    private String sources;
    private int timeRange;
    private String from;
    private String to;
    private String sortBy;


    public void setTimeRange(int timeRange) {
        LocalDate from = LocalDate.now().minusDays(timeRange);
        LocalDate to = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd");
        this.from = from.format(formatter);
        this.to =  to.format(formatter);
    }

    public void setSources(ArrayList<String> sources) {
        if (sources != null) {
            this.sources = Joiner.on(",").join(sources);
        }
    }
}
