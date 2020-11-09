package com.party.dolphin.controller;

import com.party.dolphin.dto.*;
import com.party.dolphin.service.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class JobController {

    @RequestMapping(method=RequestMethod.POST, value="/job/init")
    public String addJob(@RequestBody JobRender job) {
        return "Hello";
    }
}