package com.party.dolphin.controller;

import com.party.dolphin.service.*;

import org.springframework.web.bind.annotation.*;

@RestController
public class DatabaseController {

    @RequestMapping(value="/state/{id}", method=RequestMethod.GET)
    public StateRender getState(@PathVariable int id) {
        return databaseService.findStateById(id);
    }

    @RequestMapping(value="/state", method=RequestMethod.GET, params={"name"})
    public StateRender getStateByName(@RequestParam(value="name") String stateName) {
        return databaseService.findStateByName(id);
    }
}