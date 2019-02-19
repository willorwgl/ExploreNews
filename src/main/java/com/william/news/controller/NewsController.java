package com.william.news.controller;

import com.william.news.domain.EverythingSearchForm;
import com.william.news.domain.HeadlineSearchForm;
import com.william.news.restclient.NewsApiClient;
import com.william.news.restclient.NewsApiException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/news")
public class NewsController {

    private NewsApiClient newsApiClient;

    public NewsController(NewsApiClient newsApiClient) {
        this.newsApiClient = newsApiClient;
    }
    @GetMapping
    public String main() {
        return "main";
    }


    @GetMapping("/everythingsearch")
    public String search(Model model) {
        model.addAttribute("searchForm", new EverythingSearchForm());
        return "everythingSearchForm";
    }


    @PostMapping("/everythingsearch/result")
    public String searchEverything(@ModelAttribute("searchForm") EverythingSearchForm searchForm, RedirectAttributes redirectAttributes, Model model) throws NewsApiException {
        if (searchForm.getKeyword() == null) {
            redirectAttributes.addFlashAttribute("error", "Keyword is required");
            return "redirect:/news/everythingsearch";
        }
        model.addAttribute("articles",  newsApiClient.searchFromEverything(searchForm));
        return "search-result";
    }

    @GetMapping("/headlinesearch")
    public String searchHeadline(Model model) {
        model.addAttribute("searchForm", new HeadlineSearchForm());
        return "headlineSearchForm";
    }

    @PostMapping("/headlinesearch/result")
    public String searchHeadline(@ModelAttribute("searchForm") HeadlineSearchForm searchForm, RedirectAttributes redirectAttributes, Model model) throws NewsApiException {
        if ((searchForm.getCountry() != null || searchForm.getCategory() != null) && searchForm.getSources() != null) {
            redirectAttributes.addFlashAttribute("paramError", "You cannot select category or country with sources");
            return "redirect:/news/headlinesearch";
        }
        if (searchForm.getKeyword() == null) {
            redirectAttributes.addFlashAttribute("error", "Keyword is required");
            return "redirect:/news/headlinesearch";
        }
        model.addAttribute("articles",  newsApiClient.searchFromHeadlines(searchForm));
        return "search-result";
    }

    @GetMapping("/everythingsearch/result")
    public String everythingResultRefresh() {
        return "redirect:/news/everythingsearch";
    }

    @GetMapping("/headlinesearch/result")
    public String headlineResultRefresh() {
        return "redirect:/news/headlinesearch";
    }
}
