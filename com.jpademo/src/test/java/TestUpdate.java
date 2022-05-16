import CreateEntityManager.Customer;
import Utils.JpaUtils;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class TestUpdate {
    @Test
    public void Test(){
        EntityManager em = JpaUtils.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        //增删改查
        /**客户的更新操作
         * i； 查询客户
         * ii：更新客户
         * **/
        Customer customer = new Customer();
        customer.setName("Men");
        customer.setCustPhone("11111");
        em.persist(customer);

//        tx.commit();
//        em.close();

        /**每一个实体对象管理器只能执行一次增删改查工作？ no! 可以在关闭前执行多个增删改查**/

//        EntityManager em1 = JpaUtils.getEntityManager();
//        EntityTransaction tx1 = em1.getTransaction();
//        tx1.begin();
        Customer customer1 = em.find(Customer.class, 9l);
        System.out.println(customer1);
        //查询客户后更新客户
        customer1.setCustPhone("2222");
        em.merge(customer1);
        tx.commit();
        em.close();
    }
}
