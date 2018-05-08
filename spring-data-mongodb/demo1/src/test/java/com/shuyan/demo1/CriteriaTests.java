package com.shuyan.demo1;

import com.shuyan.demo1.domain.User;
import com.shuyan.demo1.domain.UserUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CriteriaTests {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Test
    public void is(){
        //db.userUser.find({username:'ddd'})
        Query query = new Query(Criteria.where("username").is("ddd"));
        List<UserUser> list = mongoTemplate.find(query,UserUser.class);
        for (UserUser user : list) {
            System.out.println(user);
        }
    }
    @Test
    public void regex(){
        //db.userUser.find({username:{$regex:'^d'}})
        Query query = new Query(Criteria.where("username").regex("^d"));
        List<UserUser> list = mongoTemplate.find(query,UserUser.class);
        for (UserUser user : list) {
            System.out.println(user);
        }
    }

    @Test
    public void insertData(){
        //向数据库插入点数据，用于测试下面的程序
        List<User> list = new ArrayList();
        for (int i=0; i<20; i++){
            User user = new User(String.valueOf((char)((int)('a')+i)),i,(char)((int)('a')+i)+"@qq.com",new Date(2018,5,i));
            list.add(user);
        }
        mongoTemplate.insert(list,"user1");
    }

    @Test
    public void compare(){
        //db.userUser.find({age:{$lt:10}})
        Query query = new Query(Criteria.where("age").lt(10));
        List<User> list = mongoTemplate.find(query,User.class,"user1");
        System.out.println("=================age lt 10 ==================");
        for (User user : list) {
            System.out.println(user);
        }
        //db.userUser.find({age:{$lte:10}})
        query = new Query(Criteria.where("age").lte(10));
        list = mongoTemplate.find(query,User.class,"user1");
        System.out.println("=================age lte 10 ==================");
        for (User user : list) {
            System.out.println(user);
        }
        //db.userUser.find({age:{$gt:10}})
        query = new Query(Criteria.where("age").gt(10));
        list = mongoTemplate.find(query,User.class,"user1");
        System.out.println("=================age gt 10 ==================");
        for (User user : list) {
            System.out.println(user);
        }
        //db.userUser.find({age:{$gte:10}})
        query = new Query(Criteria.where("age").gte(10));
        list = mongoTemplate.find(query,User.class,"user1");
        System.out.println("=================age gte 10 ==================");
        for (User user : list) {
            System.out.println(user);
        }
        //db.userUser.find({age:{$gte:10,$lt:18}})
        query = new Query(Criteria.where("age").gte(10).lt(18));
        list = mongoTemplate.find(query,User.class,"user1");
        System.out.println("================= 10 <= age < 18 ==================");
        for (User user : list) {
            System.out.println(user);
        }
    }
    @Test
    public void sort(){
        //db.userUser.find({age:{lt:10}}).sort(age:-1)
        Query query = new Query(Criteria.where("age").lt(10)).with(new Sort(Sort.Direction.DESC,"age"));
        List<User> list = mongoTemplate.find(query,User.class,"user1");
        for (User user : list) {
            System.out.println(user);
        }
    }
    @Test
    public void page(){
        //db.userUser.find({age:{lt:10}}).skip(5*(1-1)).limit(5)
        Query query = new Query(Criteria.where("age").lt(10)).with(PageRequest.of(1,5));
        List<User> list = mongoTemplate.find(query,User.class,"user1");
        for (User user : list) {
            System.out.println(user);
        }
    }
}
