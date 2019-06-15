从以下几个方面了解jpa以及其应用：
1、jpa的认识与常用接口
    JPA是java进行数据持久化的标准规范，目前其实现框架有hibernate，我们在和springboot整合时，也是在jpa协议的基础上操作hibernate的api进行curd的操作。
    用过hibernate的都知道，这个是比较完全的ORM框架，面向对象操作，将数据与对象实现了双向映射，同时能够配置实体关系实现数据关系的一致。相对来说使用比较简单，
    主要是其提供了大量API方便开发者调用，同时所有的操作都是实体对象，对数据库的操作不用依赖于sql。当然，由于hibernate的功能非常强大，学习成本相对也比较高，
    对其扩张比较复杂，主要的难点在与使用与理解。
    在JPA中，提供了一些接口，方便我们跳过hibernate的方法，来操作数据库。
    CrudRepository：实现基础的增删改查，主要根据主键操作
    PagingAndSortingRepository：对上一个接口的加强，实现分页排序
    JpaRepository：对上一个接口的加强，同时添加excmple查询
    JpaSpecificationExecutor：通过Criteria动态查询
    
2、jpa与springboot整合
    与springboot整合非常简单，引入下面的jar以及其他springboot依赖的包
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        
3、实现通用服务,构建企业开发环境
    dao：继承jpa提供的机构，加上注解@Repository。可以通过定义自己的Repository来做加强，因为每种接口的功能不同，可以同时实现多个接口
    service:可以定义公共接口，将常用的crud方法抽象出来
    web：将一般的查询，比如根据id查询，根据条件查询，分页查询，新增/修改，删除等统一处理
    扩展：
        根据上面的接口关系，我们将dao继承JpaRepository则可以实现基础的增删改查，同时可以通过分页、排序、Example进行查询，要知道Example大部分是= ！ > <相关的查询，
        但是如果要实现多种类型的查询，比如< > in bwt...时，则无法满足需求，所有同时引入JpaSpecificationExecutor，通过Criteria进行更深层次的扩展。

5、表关系mappedBy、cascade的理解
    mappedBy表示谁维护关系。
    CascadeType：PERSIST主可以新增从；MERGE主可以修改从；REMOVE主可以删除从；REFRESH主不会影响从

6、事务处理
    默认情况下，springboot会开启事务，在spring-boot-autoconfigure中，有几个关于Transaction的自动化配置
    @EnableTransactionManagement，可以选择PROXY和ASPECT两种模式，具体什么区别，需要深入
   

7、测试
    在通过entityManager,通过接口方法调用又全部正常进行测试时，一直提示没有事务(No transactional EntityManager available)，但是系统正常运行时也没问题.
    所以只能通过mock发送请求进行测试。

8、问题记录
    一般户出现这样的错误：
    1、Could not write JSON: No serializer found for class org.hibernate.proxy.pojo.javassist.JavassistLazyInitializer and no properties discovered to create BeanSerializer (to avoid exception, disable SerializationFeature.FAIL_ON_EMPTY_BEANS); nested exception is com.fasterxml.jackson.databind.JsonMappingException...
    这是由于我们查询到的对象并非其本身，而是代理后的对象，在转换成json出了问题
    在网上搜，一般给出的答案是在实体类上添加@JsonIgnoreProperties({ "handler","hibernateLazyInitializer","fieldHandler"})来忽略代理对象新加的属性，当然这样做看起来是解决了问题，
    但是你会发现，你的所有fetch都会失效，那样效率就会出问题
    正确的处理方式是添加
        <dependency>
            <groupId>com.fasterxml.jackson.datatype</groupId>
            <artifactId>jackson-datatype-hibernate4</artifactId>
            <version>2.9.8</version>
        </dependency>
    在MappingJackson2HttpMessageConverter中添加一个objectMapper HibernateAwareObjectMapper.    
