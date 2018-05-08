package com.shuyan.demo1.web;

import com.shuyan.demo1.domain.UserUser;
import com.shuyan.demo1.domain.UserUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.TextScore;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testController {
    @Autowired
    private MongoTemplate mongoTemplate;

    @GetMapping("/add")
    public UserUser getUser(){

        UserUser user = new UserUser("");
        user.setUsername("shuyan");
        mongoTemplate.insert(user);

        user =  mongoTemplate.findOne(new Query(Criteria.where("username").is("shuyan")), UserUser.class);

        return user;
    }

    @GetMapping("/delete")
    public UserUser deleteUser(){

        UserUser user =  new UserUser("");
        user.setId("5aeff64731be9167b1ba6960");
        mongoTemplate.remove(new Query(Criteria.where("username").is("shuyan")),"userUser");

        return user;
    }
}
