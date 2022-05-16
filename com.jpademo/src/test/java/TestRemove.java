import CreateEntityManager.Customer;
import Utils.JpaUtils;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class TestRemove {
    @Test
    public void test() {
        EntityManager em = JpaUtils.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        //增删改查
        /**删除客户
         * i:根据id查询客户
         * ii: 调用remove方法完成删除操作
         * **/
        Customer customer = em.find(Customer.class,1l);
        em.remove(customer);

        tx.commit();

        em.close();
    }

}

