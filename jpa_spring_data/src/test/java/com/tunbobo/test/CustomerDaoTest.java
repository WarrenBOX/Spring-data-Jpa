package com.tunbobo.test;

import com.tunbobo.dao.CustomerDao;
import com.tunbobo.domain.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class) //声明spring提供的单元测试环境
@ContextConfiguration(locations = "classpath:applicationContext.xml")//指定spring容器的信息


public class CustomerDaoTest {
    @Autowired
    private CustomerDao customerDao;
    @Test
    public void test() {
        Customer customer = customerDao.findJpql("Men");
        System.out.println(customer);
    }

    @Test
    public void testFindCustomerNameAndId() {
        Customer customer = customerDao.findCustNameAndId("Men",10);
        System.out.println(customer);
    }


    //测试jpql更新操作
    /**
     * spring data jpa 中使用jpql完成 更新/删除操作
     *  - 需要手动添加事务的支持
     *  - 默认执行成功后，回滚事务**/
    @Test
    @Transactional //添加事务的支持 or TransactionRequiredException
    @Rollback(value = false) //设置是否回滚
    public void testUpdate() {
        customerDao.updateCustomer("maxmen",7);
    }

    //测试sql查询
    @Test
    public void testFindSql() {
        //返回值是对象的数组的list： 每个row都是一个对象
        List<Object[]> list =  customerDao.findAllSql();
        for (Object[] obj: list) {
            //把array用string的方式打印出来
            System.out.println(Arrays.toString(obj));
        }
    }

    @Test
    public void testFindSqlCon() {
        //查询结果没有区分大小写
        List<Object[]> list = customerDao.findCondition("Me%");
        for (Object[] obj : list) {
            System.out.println(Arrays.toString(obj));
        }
    }

    //测试方法命名规则的查询
    @Test
    //按命名规则定义方法名称， 不需要写jpql语句了
    public void testNaming() {
        Customer customer = customerDao.findByName("Men");
        System.out.println(customer);
    }

    //命名规则方法模糊查询
    @Test
    public void testNamingLike() {
        List<Customer> list = customerDao.findByNameLike("M%");
       //iter 循环快捷键
        for (Customer customer: list) {
            System.out.println(customer);
        }
    }

    //命名方法多条件查询
    @Test
    public void testMultiConNaming() {
        Customer customer = customerDao.findByNameLikeAndCustPhoneLike("M%","%8");
        System.out.println(customer);
    }
}
