package jpql;

import Utils.JpaUtils;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

public class TestOrder {
    /**排序查询：倒序查询全部顾客(根据id）
     * sql: SELECT * FROM table ORDER BY cust_id DESC;
     * jqpl: from CreateEntityManager.customer order by custId
     * **/

    @Test
    public void test() {
        EntityManager em = JpaUtils.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        //增删改查
        String jpql = "from Customer order by custId desc";
        Query query = em.createQuery(jpql);

        List list = query.getResultList();
        for (Object obj:list) {
            System.out.println(obj);
        }

        tx.commit();
        em.close();
    }
}
