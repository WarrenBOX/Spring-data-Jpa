import CreateEntityManager.Customer;
import Utils.JpaUtils;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class TestReference {
    @Test
    public void testReference(){
        EntityManager em = JpaUtils.getEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        //增删改查
        /**getReference
         * 获取的对象是一个动态代理的对象
         * 调用getReference方法不会立即发送sql语句查询数据库
         *  * 当调用查询结果对象的时候，才会发送sql语句
         *  一般使用延迟查询 -》 节约一次查询
         * **/
        //getReference: 根据id查询
        //实体类型的字节码 + 主键取值
        Customer customer = em.getReference(Customer.class,1l);
        //延迟加载：懒加载 -》 得到的动态代理对象，打印这个对象时，才调用这个对象，才开始查询
        System.out.println(customer);

        tx.commit();

        em.close();
    }
}
