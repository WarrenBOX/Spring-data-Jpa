package jpql;

import Utils.JpaUtils;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

public class TestCondition {
    /**条件查询
     * sql:案例：查询客户名称，以“特定电话号码开头的顾客”  SELECT * FROM table WHERE cust_phone LIKE "77%"
     * jpql:from Customer where custPhone like ?
     * **/
    @Test
    public void test() {
        EntityManager em = JpaUtils.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        //增删改查
        //i: 根据jpql语句创建query查询对象
        String jpql = "from Customer where custPhone like ?1";   //占位符后必须跟一个下标
        Query query = em.createQuery(jpql);

        //ii: 对参数赋值 --占位符参数
        //第一个参数，占位符索引的位置(从一开始）， 第二个参数，取值
        query.setParameter(1,"77%");

        //iii:发送查询，并封装结果
        List list = query.getResultList();
        for (Object obj: list) {
            System.out.println(list);
        }

        tx.commit();
        em.close();
    }
}
