package org.launchcode.controllers;

import org.launchcode.models.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("search")
public class SearchController {

    @RequestMapping(value = "")
    public String search(Model model) {
        model.addAttribute("columns", ListController.columnChoices);

        return "search";
    }
    // TODO #1 - Create handler to process search request and display results. DID IT!
    //My code below...

    @RequestMapping(value = "results")
    // request path: /results as seen in Spring Boot video
    //ArrayList hashmap goes below
    //If statement goes below
    public String processSearchResult(Model model, @RequestParam String searchType, @RequestParam String searchTerm) {

        if (searchType.equals("all")) {
            ArrayList<HashMap<String, String>> jobs = JobData.findByValue(searchTerm);


            model.addAttribute("title", "All Jobs");
            model.addAttribute("jobs", jobs);
            return "search";

        } else {

                ArrayList<HashMap<String, String>> jobs = JobData.findByColumnAndValue(searchType, searchTerm);

                model.addAttribute("title", "Jobs with " + searchType + ": " + searchTerm);
                model.addAttribute("jobs", jobs);
                model.addAttribute("columns", ListController.columnChoices);
                return "search";
        }
    }
}
