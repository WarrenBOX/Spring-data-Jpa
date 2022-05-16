package jpql;

import Utils.JpaUtils;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

public class TestCount {
    /**jpql统计查询: 统计顾客的总数
     * sql:SELECT COUNT(id) FROM table
     * jpql:
     * **/

    @Test
    public void test() {
        //通过工具类获取实体管理器对象
        EntityManager em = JpaUtils.getEntityManager();

        //创建并开启事务
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        //增删改查
        //i: 根据jpql语句创建query查询对象
        //ii: 对参数赋值
        //iii：发送查询，并封装结果
        String jpql = "SELECT COUNT (custId) FROM Customer";
        Query query = em.createQuery(jpql);

        //发送查询并封装结果:
        /**
         * getResultList -> 直接将对象封装为list集合
         * getSingleResult: -> 得到唯一的结果集
         * **/
//        List list = query.getResultList();
        Object result = query.getSingleResult();
        System.out.println(result);

        //提交事务
        tx.commit();

        //释放资源
        em.close();

    }
}
