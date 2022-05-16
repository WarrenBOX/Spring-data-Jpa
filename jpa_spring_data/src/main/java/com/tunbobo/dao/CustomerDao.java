package com.tunbobo.dao;

import com.tunbobo.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

//jpaRepository ：curd   -》 泛型<需要操作的实体类的类型, 实体类中主键属性的类型>

//jpaSpecification Executor: 复杂查询

//ctrl + f12查看当前类结构
//ctrl + 鼠标左键 ： 查看文件路径
//dao层规范： 继承两个jpa类，而且利用@query注解来声明方法
public interface CustomerDao extends JpaRepository<Customer,Long>, JpaSpecificationExecutor<Customer>{

    /**案例：根据客户名称查询客户
     * 使用jpql的形式查询
     * jqpl:from customer where custName = ?
     *
     * 配置jpql语句，使用@Query 注解
     * **/
    @Query(value = "from Customer where name = ?1")
    public Customer findJpql(String name);

    /**案例：根据客户名称和客户id查询客户
     * jpql: from customer where custName = ?1 and custId = ?2
     * 对于多个占位符：
     *    - 在赋值的时候，占位符的位置需要和方法中的参数的位置保持一致
     *    - 可以指定占位符参数的位置 ： ？1 指代方法中第一个参数
     * **/
    @Query(value = "from Customer where name = ?1 and custId = ?2")
    public Customer findCustNameAndId(String name, long id);


    /**案例：完成更新操作： 根据id更新客户的名称
     * sql:update cst_customer set cust_name = ? where cust_id = ?
     * jpql: update Customer set name where custId = ?1
     *
     * @Modify：当前执行的是一个更新操作
     * or: QueryExecutionRequestException: not supported for dml operations
     * **/
    @Query(value = "update Customer set name = ?1 where custId = ?2") //代表的是查询
    @Modifying //当前执行的是一个更新操作
    public void updateCustomer(String name, long id);

    /**使用sql形式查询
     * 案例：查询全部的顾客
     * sql: select * from cst_customer;
     * **/
    @Query(value = "select * from cst_customer", nativeQuery = true)
    public List<Object[]> findAllSql();

    /**sql条件查询**/
    @Query(value = "select * from cst_customer where cust_name like ?1",nativeQuery = true)
    public List<Object[]> findCondition(String name);

    /**
     * 方法名的约定：
     * findBy：查询
     *         对象中的属性名称（首字母大写）： 查询的条件
     *         CustName
     *         默认情况下，使用等于的方式查询
     *         特殊的查询方式
     * findByCustName --根据顾客名称查询
     * 在springDataJpa阶段，会根据方法名称进行解析 findBy from xxx(实体类)
     *                                            属性名   where CustName
     *
     * 1. findBy + 属性名称 （根据属性的名称进行完成匹配的查询）
     * 2. findBy + 属性名称 + “查询方式(Like| isnull)”
     *     -》 findByNameLike()
     * 3. 多条件查询
     *    findBy + 属性名 + “查询方式” + “多条件的连接符 （and | or）” + 属性名 + 查询方式
     * **/

    public Customer findByName(String name);

    /**方法命名模糊匹配**/
    public List<Customer> findByNameLike(String name);

    //使用客户名称模糊匹配和电话模糊匹配
    public Customer findByNameLikeAndCustPhoneLike(String name, String phone);
}
