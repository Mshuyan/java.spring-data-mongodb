package com.shuyan.demo1;

import com.mongodb.client.result.DeleteResult;
import com.shuyan.demo1.domain.Dog;
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

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Demo1ApplicationTests {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void contextLoads() {
        UserUser user = new UserUser("ccc");
        mongoTemplate.insert(user, "user1");
    }

    @Test
    public void delete() {

        DeleteResult deleteResult = mongoTemplate.remove(new Query(Criteria.where("username").is("shuyan1")), UserUser.class);
        System.out.println(deleteResult);
    }

    @Test
    public void modify() {
        Query query = new Query(Criteria.where("username").is("ccc"));
        Update update = new Update().set("username", "bbb");
        FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true);
        mongoTemplate.findAndModify(query, update, options, UserUser.class, "user1");
    }

    @Test
    public void find1() {
        //根据查询条件返回所有匹配的记录
        //映射的表为指定的类名的首字母小写形式
        Query query = new Query(Criteria.where("username").is("ccc"));
        List<UserUser> list = mongoTemplate.find(query, UserUser.class);
        for (UserUser user : list) {
            System.out.println(user);
        }
    }
    @Test
    public void find2() {
        //指定表名根据查询条件返回所有匹配的记录
        Query query = new Query(Criteria.where("username").is("ccc"));
        List<UserUser> list = mongoTemplate.find(query, UserUser.class,"user1");
        for (UserUser user : list) {
            System.out.println(user);
        }
    }
    @Test
    public void findAll1() {
        //查询表中所有记录
        //映射的表为指定的类名的首字母小写形式
        List<UserUser> list = mongoTemplate.findAll(UserUser.class);
        for (UserUser user : list) {
            System.out.println(user);
        }
    }
    @Test
    public void findAll2() {
        //指定表名查询表中所有记录
        List<UserUser> list = mongoTemplate.findAll(UserUser.class,"user1");
        for (UserUser user : list) {
            System.out.println(user);
        }
    }
    @Test
    public void findById1() {
        //根据ID查询表中记录
        //映射的表为指定的类名的首字母小写形式
        UserUser user = mongoTemplate.findById("5af10d5631be9169049c885e",UserUser.class);
        System.out.println(user);
    }
    @Test
    public void findById2() {
        //指定表名根据ID查询表中记录
        UserUser user = mongoTemplate.findById("5af1169831be917bd6b9963c",UserUser.class,"user1");
        System.out.println(user);
    }
    @Test
    public void findOne1() {
        //根据查询条件查询表中的1条记录
        //映射的表为指定的类名的首字母小写形式
        Query query = new Query(Criteria.where("username").is("ccc"));
        UserUser user = mongoTemplate.findOne(query,UserUser.class);
        System.out.println(user);
    }
    @Test
    public void findOne2() {
        //指定表名根据查询条件查询表中的1条记录
        Query query = new Query(Criteria.where("username").is("bbb"));
        UserUser user = mongoTemplate.findOne(query,UserUser.class,"user1");
        System.out.println(user);
    }
}
