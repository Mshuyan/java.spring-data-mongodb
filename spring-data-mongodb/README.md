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
