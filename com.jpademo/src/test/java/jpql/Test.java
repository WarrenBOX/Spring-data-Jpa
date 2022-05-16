package jpql;

/**
 * CURD基本案例
 * persist()-> 保存
 * merge()->更新
 * remove（） -》 删除
 * find/getReference(): 根据id查询
 * **/

import Utils.JpaUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

/**JPQL： java persistence query language
 * 以面对对象的语言表达式，将sql语法和简单查询语义绑在一起
 * 通过类名和属性访问，而不是表名和字段
 *
 *  1. 查询全部
 *  2. 分页查询
 *  3. 统计查询
 *  4. 条件查询
 *  5. 排序
 * **/

public class Test {
    //测试jpql查询

    /**查询全部
     * jpql: from CreateEntityManager.Customer
     * sql: SELECT * FROM table;
     * **/
    @org.junit.Test
    public void test() {
        //1.获取entityManager对象
        EntityManager em = JpaUtils.getEntityManager();

        //2.开启事务
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        //3. 增删改查
        String jpql = "from CreateEntityManager.Customer";
        Query query = em.createQuery(jpql);//创建Query查询对象，query对象才是执行jpql的对象

        //发送查询，并发送结果集
        List list = query.getResultList();
        for (Object obj: list) {
            System.out.println(obj);
        }
        //4.提交事务
        tx.commit();

        //5. 释放资源
        em.close();
    }
}
