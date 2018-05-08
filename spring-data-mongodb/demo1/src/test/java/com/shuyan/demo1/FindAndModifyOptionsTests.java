package com.shuyan.demo1;

import com.shuyan.demo1.domain.UserUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FindAndModifyOptionsTests {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Test
    public void returnNew(){
        //没有使用FindAndModifyOptions().returnNew(true)时，返回旧数据：
        //UserUser{id='5af10d5631be9169049c885e', username='ccc'}
        Query query1 = new Query(Criteria.where("username").is("ccc"));
        Update update1 = new Update().set("username", "ddd");
        UserUser user = mongoTemplate.findAndModify(query1, update1, UserUser.class);
        System.out.println(user);

        //使用FindAndModifyOptions().returnNew(true)时，返回新数据：
        //UserUser{id='5af10d5631be9169049c885e', username='eee'}
        Query query2 = new Query(Criteria.where("username").is("ddd"));
        Update update2 = new Update().set("username", "eee");
        FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true);
        user = mongoTemplate.findAndModify(query2, update2, options, UserUser.class);
        System.out.println(user);
    }
    @Test
    public void upsert(){
        //没有使用FindAndModifyOptions().upsert(true)时，没有查询到数据不执行任何动作：
        //null
        Query query1 = new Query(Criteria.where("username").is("ccc"));
        Update update1 = new Update().set("username", "ddd");
        FindAndModifyOptions options1 = new FindAndModifyOptions().returnNew(true);
        UserUser user = mongoTemplate.findAndModify(query1, update1,options1, UserUser.class);
        System.out.println(user);

        //使用FindAndModifyOptions().upsert(true)时，没有查询到任何数据时执行插入：
        //UserUser{id='5af1460b172494f33b9b8575', username='ddd'}
        Query query2 = new Query(Criteria.where("username").is("ccc"));
        Update update2 = new Update().set("username", "ddd");
        FindAndModifyOptions options2 = new FindAndModifyOptions().returnNew(true).upsert(true);
        user = mongoTemplate.findAndModify(query2, update2, options2, UserUser.class);
        System.out.println(user);
    }
}
