package Utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

//Persistence是静态方法， entitymanagerFactory比较耗时且维护了很多内容，而且线程安全 -》 用工具类解决创建实体管理器工厂时浪费资源
//通过静态代码块形式，当程序第一次访问此工具类时候，创建一个共给的实体管理器对象


/**第一次访问getEntityManager方法，classload至内存，准备阶段给factory赋值为null， 初始化阶段运行静态代码块创建factory对象， 并且赋值给factory**/
/**第二次访问getEntityManager方法，直接通过创建好的factory对象，创建em对象**/
public class JpaUtils {

    private static EntityManagerFactory factory;

    static {
        //加载配置文件，创建entityManagerFactory -> 根据持久化单元名称
       factory =  Persistence.createEntityManagerFactory("myJpa");
    }


    /**
     * 用工具类获取entityManager实体对象**/
    public static EntityManager getEntityManager() {
        return factory.createEntityManager();
    }
}
