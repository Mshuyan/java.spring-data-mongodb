# java.spring-data-mongodb



# 1. 入门程序
## 1.1. 配置依赖
## 1.1. 配置entity类
## 1.2. 注解
# 2. MongoTemplate
## 2.1. 增删改方法

>以上内容参见javaNote笔记

## 2.2. 查
# 3. Query
    这里先不做深度研究，只会用构造方法传入Creteria即可
# 4. Update
    用法参见修改数据的例程
    该类中方法与MongoDB修改数据语句中的$xxx是对应的，参见mongoDB修改数据语法即可
# 5. FindAndModifyOptions
+ returnNew<br/>
    修改数据后返回新的数据
+ upsert<br/>
    没有查询到要修改的数据时，执行插入操作
# 6. Criteria



## 2. spring-data-mongodb

### 2.1. 入门程序

- 创建spring-boot工程，勾选NoSql中的MongoDB，或手动导入如下依赖：

  ```xml
  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-data-mongodb</artifactId>
  </dependency>
  ```

- 在application.properties中配置连接数据库的url等信息

  - 不使用用户名、密码

    格式：

    ```properties
    spring.data.mongodb.uri=mongodb://IP地址:端口号/数据库名
    ```

    如：

    ```properties
    spring.data.mongodb.uri=mongodb://localhost:27017/mydb0
    ```

  - 使用用户名、密码

    格式：

    ```properties
    spring.data.mongodb.uri=mongodb://用户名:密码@IP地址:端口/数据库名
    ```

    如：

    ```properties
    spring.data.mongodb.uri=mongodb://shuyan:943397@localhost:27017/mydb0
    ```

- 定义与数据库中字段对应的Bean类

  ```java
  //指定对应数据库中哪张表，默认不指定则使用该类名的小写形式对应数据库中表
  @Document(collection = "user1")
  public class User {
      //_id字段需要使用@Id注解
      @Id
      private String id;
      private String username;
  	//省略getter、setter
  }
  ```

- 操作数据库

  ```java
  //定义mongo模板对象
  @Autowired
  private MongoTemplate mongoTemplate;
  
  @GetMapping("/add")
  public User getUser(){
  
      User user = new User();
      user.setUsername("shuyan");
      //插入数据
      mongoTemplate.insert(user);
  	//查询数据
      user =  mongoTemplate.findOne(new Query(Criteria.where("username").is("shuyan")), User.class);
  
      return user;
  }
  ```

### 2.2. 注解

- @Id

  ​	文档的唯一标识，在mongodb中为ObjectId，它是唯一的，通过时间戳+机器标识+进程ID+自增计数器（确保同一秒内产生的Id不会冲突）构成。

- @Document

  ​	把一个java类声明为mongodb的文档，可以通过collection参数指定这个类对应的文档。

- @DBRef

  ​	声明类似于关系数据库的关联关系。ps：暂不支持级联的保存功能，当你在本实例中修改了DERef对象里面的值时，单独保存本实例并不能保存DERef引用的对象，它要另外保存.

- @Indexed

  ​	声明该字段需要索引，建索引可以大大的提高查询效率。

- @CompoundIndex

  ​	复合索引的声明，建复合索引可以有效地提高多字段的查询效率。

- @GeoSpatialIndexed

  ​	声明该字段为地理信息的索引。

- @Transient

  ​	映射忽略的字段，该字段不会保存到mongodb。

- @PersistenceConstructor

  ​	声明构造函数，作用是把从数据库取出的数据实例化为对象。该构造函数传入的值为从DBObject中取出的数据。

- 例

  ```java
  @Document(collection="person")  
  @CompoundIndexes({  
      @CompoundIndex(name = "age_idx", def = "{'lastName': 1, 'age': -1}")  
  })  
  public class Person<T extends Address> {  
    
    @Id  
    private String id;  
    @Indexed(unique = true)  
    private Integer ssn;  
    private String firstName;  
    @Indexed  
    private String lastName;  
    private Integer age;  
    @Transient  
    private Integer accountTotal;  
    @DBRef  
    private List<Account> accounts;  
    private T address;  
    
    public Person(Integer ssn) {  
      this.ssn = ssn;  
    }  
      
    @PersistenceConstructor  
    public Person(Integer ssn, String firstName, String lastName, Integer age, T address) {  
      this.ssn = ssn;  
      this.firstName = firstName;  
      this.lastName = lastName;  
      this.age = age;  
      this.address = address;  
    }  
  ```

### 2.3. mongoTemplate

#### 2.3.1. 介绍

​	该类是mongodb的1个模板类，该类中具备了对数据库操作的所有方法

#### 2.3.1. 删除记录

​	删除记录使用`remove`方法，该方法具有多个不同参数的方法：

- `DeleteResult remove(Object object);`

  ​	指定对象删除，映射的表为该对象的类名的首字母小写形式

  ​	实际是根据对象中的ID删除，所以其他字段可以为null，但是id字段不可以为null

  例：

  从`userUser`表中取出1条记录并删除它

  ```java
  UserUser user =  mongoTemplate.findOne(new Query(Criteria.where("username").is("shuyan")) , UserUser.class);
  mongoTemplate.remove(user);
  ```

- `DeleteResult remove(Object object, String collectionName)`

  ​	指定表名根据ID删除，id字段不能为null

  例：

  ```java
  UserUser user =  new UserUser();
  user.setId("5aeff64731be9167b1ba6960");
  mongoTemplate.remove(user,"user1");
  ```

- `DeleteResult remove(Query query, String collectionName)`

  ​	指定表名根据查询条件删除所有匹配的记录

  例：

  ```java
  mongoTemplate.remove(new Query(Criteria.where("username").is("shuyan")),"user1");
  ```

- `DeleteResult remove(Query query, Class<?> entityClass)`

  ​	根据条件删除所有匹配的记录，映射的表为指定的类名的首字母小写形式

  例：

  ```java
  mongoTemplate.remove(new Query(Criteria.where("username").is("shuyan")),UserUser.class);
  ```

- `DeleteResult remove(Query query, Class<?> entityClass, String collectionName)`

  与`DeleteResult remove(Query query, Class<?> entityClass)`一点区别没有

- `<T> T findAndRemove(Query query, Class<T> entityClass)`

  ​	根据条件删除一条匹配的记录

  ​	映射的表为该对象的类名的首字母小写形式

- `<T> T findAndRemove(Query query, Class<T> entityClass, String collectionName)`

  ​	指定表名根据条件删除一条匹配的记录

- `<T> List<T> findAllAndRemove(Query query, String collectionName)`

  ​	指定表名根据条件删除所有匹配的记录

  

#### 2.3.2.添加记录

- 介绍

  有两种添加记录的方法：`insert`和`save`

  Insert：在新增文档时，如果有一个相同的_id，就会新增失败。

  save：在新增文档时，如果有一个相同_id的文档，会覆盖原来的

- `void insert(Object objectToSave)`

  ​	添加1条记录，映射的表为该对象的类名的首字母小写形式

  例：

  ```java
  UserUser user = new UserUser();
  user.setUsername("shuyan");
  mongoTemplate.insert(user);
  ```

- `void insert(Object objectToSave, String collectionName)`

  ​	指定表名添加1条记录

  例：

  ```java
  UserUser user = new UserUser();
  user.setUsername("shuyan");
  mongoTemplate.insert(user,"user1");
  ```

- `void insert(Collection<? extends Object> batchToSave, Class<?> entityClass) `

  ​	批量添加记录，映射的表为指定的类名的首字母小写形式

  ​	batchToSave是1个实现了Conllection接口的对象

  例：

  ```java
  ArrayList<UserUser> userUsers = new ArrayList<>();
  for (int i=0; i<10; i++){
      UserUser user = new UserUser();
      user.setUsername("ccc"+i);
      userUsers.add(user);
  }
  mongoTemplate.insert(userUsers,UserUser.class);
  ```

- `void insert(Collection<? extends Object> batchToSave, String collectionName)`

  ​	指定表名批量添加记录

  ​	batchToSave是1个实现了Conllection接口的对象

  例：

  ```java
  ArrayList<UserUser> userUsers = new ArrayList<>();
  for (int i=0; i<10; i++){
      UserUser user = new UserUser();
      user.setUsername("ccc"+i);
      userUsers.add(user);
  }
  mongoTemplate.insert(userUsers,"user1");
  ```

- `void insertAll(Collection<? extends Object> objectsToSave)`

  ​	批量添加记录，映射的表为指定的类名的首字母小写形式

  ​	objectsToSave是1个实现了Conllection接口的对象

  ​	集合中可以是不同类的对象，该方法会自动根据每个对象的类型自动寻找需要插入的表

  例：

  ```java
  ArrayList<Object> list = new ArrayList<>();
  UserUser user = new UserUser("ccc");
  list.add(user);
  Dog dog = new Dog("snopy");
  list.add(dog);
  mongoTemplate.insertAll(list);
  ```

- `void save(Object objectToSave)`

  ​	添加或更新1条记录，映射的表为该对象的类名的首字母小写形式

  例：

  ```java
  UserUser user = new UserUser();
  user.setUsername("ccc"+i);
  mongoTemplate.save(user);
  ```

- `void save(Object objectToSave, String collectionName)`

  ​	指定表名，添加或更新1条记录

  例：

  ```java
  UserUser user = new UserUser();
  user.setUsername("ccc"+i);
  mongoTemplate.save(user,"user1");
  ```

#### 2.3.3. 修改记录

- `<T> T findAndModify(Query query, Update update, Class<T> entityClass)`

  ​	将查询到的结果按修改条件进行修改数据

  ​	映射的表为指定的类名的首字母小写形式

  例：

  ```java
  Query query = new Query(Criteria.where("username").is("ccc"));
  Update update = new Update().set("username", "bbb");
  mongoTemplate.findAndModify(query,update ,UserUser.class);
  ```

- `<T> T findAndModify(Query query, Update update, Class<T> entityClass, String collectionName)`

  ​	指定表名将查询到的结果按修改条件进行修改数据

  例：

  ```java
  Query query = new Query(Criteria.where("username").is("ccc"));
  Update update = new Update().set("username", "bbb");
  mongoTemplate.findAndModify(query,update ,UserUser.class,"user1");
  ```

- `<T> T findAndModify(Query query, Update update, FindAndModifyOptions options, Class<T> entityClass)`

  ​	将查询到的结果按修改条件和参数进行修改数据

  ​	映射的表为指定的类名的首字母小写形式

  例：

  ```java
  Query query = new Query(Criteria.where("username").is("ccc"));
  Update update = new Update().set("username", "bbb");
  FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true);
  mongoTemplate.findAndModify(query,update ,options,UserUser.class);
  ```

- `<T> T findAndModify(Query query, Update update, FindAndModifyOptions options, Class<T> entityClass,      String collectionName)`

  ​	指定表名将查询到的结果按修改条件和参数进行修改数据

  例：

  ```java
  Query query = new Query(Criteria.where("username").is("ccc"));
  Update update = new Update().set("username", "bbb");
  FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true);
  mongoTemplate.findAndModify(query,update ,options,UserUser.class,"user1");
  ```

#### 
