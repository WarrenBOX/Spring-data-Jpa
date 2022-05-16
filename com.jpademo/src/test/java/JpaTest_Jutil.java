import CreateEntityManager.Customer;
import Utils.JpaUtils;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**根据id查询客户**/
public class JpaTest_Jutil {


    //通过工具类获取em对象
    @Test
    public void test() {
        //1. 通过工具类创建实体对象管理器
        EntityManager em = JpaUtils.getEntityManager();

        //2. 开启事务
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        //3. 增删改查
        /**
         * 使用find方法查询：查询对象就是当前客户本身**/
        // find by id -> para：
        //class:包装的实体类类型的字节码
        //id 查询主键的取值 -> 数字后接l -> 表示long类型
        Customer customer = em.find(Customer.class,1l);
        System.out.println(customer);

        //4. 提交事务
        tx.commit();

        //5.释放资源
        em.close();
    }

}
