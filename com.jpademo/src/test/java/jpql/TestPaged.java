package jpql;

import Utils.JpaUtils;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

public class TestPaged {
    /**分页查询
     * sql: SELECT * FROM table limit ?,?
     * jpql: from Customer
     * **/
    @Test
    public void test() {
        EntityManager em = JpaUtils.getEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        //增删改查 :
        //i: 根据jpql语句创建query查询
        String jpql = "from Customer";
        Query query = em.createQuery(jpql);

        // ii: 对参数赋值，分页参数: 从第0页开始查，每页查询两条
        //起始索引
        query.setFirstResult(0);
        //每页查询的条数
        query.setMaxResults(2);

        //处理result
        List list = query.getResultList();
        for (Object obj: list) {
            System.out.println(obj);
        }


        tx.commit();
        em.close();
    }
}
