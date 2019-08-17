package com.g2.api.controller;

import com.g2.api.model.ExecuteDao;
import com.g2.api.model.RestRequest;
import com.g2.api.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/service")
public class ServiceController {

    @Autowired
    private ExecuteDao executeDao;

    @RequestMapping(value = "/get",method = RequestMethod.GET)
    public String list(RestRequest restRequest) {
        return executeDao.executeProcedure(restRequest);
    }

    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public String create(@RequestBody RestRequest restRequest) {
        return executeDao.executeProcedure(restRequest);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public String update(@RequestBody RestRequest restRequest) {
        return executeDao.executeProcedure(restRequest);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public void delete(@RequestBody RestRequest restRequest) {
        executeDao.executeProcedure(restRequest);
    }

    @PostMapping("/auth")
    public ResponseEntity currentUser(@RequestBody User user){
        Map<Object, Object> model = new HashMap<>();
        model.put("username", user.getUsername());

        return ResponseEntity.ok(model);
    }

}
