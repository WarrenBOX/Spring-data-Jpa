import CreateEntityManager.Customer;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaTest {

    /**实现步骤
     * 1.加载配置文件创建工厂对象
     * 2.通过实体管理器工厂获取实体管理器
     * 3.创建事务
     * 4.完成增删改查
     * 5.提交事务
     * 6.释放资源
     * **/

    @Test
    public void saveTest() {
        //1. 加载配置文件，获取实体管理器工厂对象
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("myJpa");

        //2.获取实体管理器对象
        EntityManager em = factory.createEntityManager();

        //3. 开启事务
        EntityTransaction tx  = em.getTransaction();
        tx.begin(); //漏掉了begin -》 transaction not start exception

        //4.增删改查
        Customer customer = new Customer();
        customer.setName("Bob");
        customer.setCustPhone("857-352-6687");

        //5.提交事务
        em.persist(customer);
        tx.commit();

        //6.释放资源
        em.close();
        factory.close();
    }
}

//Exception: 1. transaction not successfully started