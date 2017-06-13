package org.launchcode.controllers;

import org.launchcode.models.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "list")
public class ListController {

    static HashMap<String, String> columnChoices = new HashMap<>();
    //This is a constructor. It allows for data to be initialized, populates columns with values?
    //HashMap provides central collection of different list and search options

    public ListController () {
        //This is a constructor. It allows for data to be initialized, populates columns with values
        columnChoices.put("core competency", "Skill");
        columnChoices.put("employer", "Employer");
        columnChoices.put("location", "Location");
        columnChoices.put("position type", "Position Type");
        columnChoices.put("all", "All");
    }

    @RequestMapping(value = "")
    public String list(Model model) {
        //Displays the different types of lists for view
        model.addAttribute("columns", columnChoices);

        return "list";
    }

    @RequestMapping(value = "values")
    public String listColumnValues(Model model, @RequestParam String column) {
        //Method uses query parameter passed in (column) to determine the values to fetch from JobData
        //Displays actual data obtained from JobData
        if (column.equals("all")) {
            ArrayList<HashMap<String, String>> jobs = JobData.findAll();
            //Fetches all job data. Passes data to list-jobs.html.

            model.addAttribute("title", "All Jobs");
            model.addAttribute("jobs", jobs);
            return "list-jobs";
        } else {
            ArrayList<String> items = JobData.findAll(column);
            //Fetches only for requested column. Passes values to list-column.html
            model.addAttribute("title", "All " + columnChoices.get(column) + " Values");
            model.addAttribute("column", column);
            model.addAttribute("items", items);
            return "list-column";
        }

    }

    @RequestMapping(value = "jobs")
    public String listJobsByColumnAndValue(Model model,
                                           @RequestParam String column, @RequestParam String value) {
        //Method takes in 2 query parameters: column and value.
        // Search is for specific value in a specific column & displays jobs that match.
        //displays actual jobs matching specific value in a specific column
        //User doesn't submit form. User clicks on link.
        ArrayList<HashMap<String, String>> jobs = JobData.findByColumnAndValue(column, value);
        model.addAttribute("title", "Jobs with " + columnChoices.get(column) + ": " + value);
        model.addAttribute("jobs", jobs);

        return "list-jobs";
    }
}
