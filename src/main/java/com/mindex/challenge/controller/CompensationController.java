package com.mindex.challenge.controller;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CompensationController {
    private static final Logger LOG = LoggerFactory.getLogger(CompensationController.class);

    @Autowired
    private CompensationService compensationService;

    /*
    Set up similar to the update for EmployeeController update

    Misc Thoughts -
    Originally I wanted to have it be used in a way where it could take two arguments:
        - employee id
        - new compensation object
     as that is technically all that is needed to actually update an employee, which is great to save on request size
     and overall more elegant and expandable in my opinion, as then we could create overloaded calls to update whatever
     we want individually.

     I could not get it to work in testing and had to default back to something similar that the test structure had in
     place - in a case like this I would very much want to do it the other way, but went on a rather long coding deep
     dive into the Spring API to see how I could do such a thing. But decided it was better to get it working and
     shipped.

     Given more time, or perhaps a senior dev with a bit more experience I would love to ask or use them as a resource
     to go about accomplishing this.
     */
    @PutMapping("/compensation/{id}")
    public Compensation update(@PathVariable String id, @RequestBody Compensation compensation) {
        LOG.debug("Received compensation update request for employee [{}]", compensation);
        Employee employee = new Employee();
        employee.setEmployeeId(id);
        employee.setCompensation(compensation);
        return compensationService.update(employee);
    }

    /*
    Simple pull from the mongodb to read given an id.
     */
    @GetMapping("/compensation/{id}")
    public Compensation read(@PathVariable String id) {
        LOG.debug("Received compensation read request for id [{}]", id);

        return compensationService.read(id);
    }
}
