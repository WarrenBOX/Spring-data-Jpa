package SpecTest;


import com.tunbobo.dao.CustomerDao;
import com.tunbobo.domain.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.criteria.*;
import java.util.List;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class SpecTest {

    @Autowired
    private CustomerDao customerDao;

    /**
     * 条件查询：查询单个对象
     * - 1. 查询方式
     *          cb对象
     * - 2. 比较的属性名称
     *          root对象
     * **/
    @Test
    public void testSpec() {

        //匿名内部类
        /**
         * 自定义查询条件
         *  1. 实现specification接口（提供泛型，查询对象的类型）
         *  2. 实现toPredicate方法（构造查询条件）
         *  3. 需要借助方法参数中的两个参数
         *      - root，获取需要查询的对象属性
         *      - CriteriaBuilder： 构造查询条件的，内部封装很多查询条件，模糊匹配，精准匹配
         *
         * **/
        //匿名内部类：一个匿名实现了specification接口的类， 这个匿名类还是Specification中定义的一个类（specification依赖于predicate类）
        Specification<Customer> spec = new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                //1.获取比较的属性
                Path<Object>  name = root.get("name");

                //2.构造查询条件 select * from cst_customer where name = ' '
                /**第一个参数，需要比较的属性（Path对象）**/

                Predicate predicate = cb.equal(name, "Men"); //进行精准的匹配 (比较的属性的取值)

                return predicate;
            }
        };

        Optional<Customer> customer = customerDao.findOne(spec);
        System.out.println(customer);
    }


    /**多条件拼接查询
     *
     * 案例：根据客户名和客户电话查询
     * root:获取属性
     *       名称， 电话
     * cb：构造查询
     *      构造客户名的精准匹配查询， 构造客户电话的模糊匹配查询
     *      将以上两个查询联系起来
     *
     * **/
    @Test
    public void testMultiCon() {
        Specification<Customer> spec = new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                //通过root获取属性
                Path<Object> name = root.get("name");
                Path<Object> cust_phone = root.get("custPhone");

                //构造查询方式
                Predicate predicate = criteriaBuilder.equal(name,"Men");//第一个参数，path； 第二个参数，属性的取值
                Predicate predicate1 = criteriaBuilder.equal(cust_phone,"11111");

                //3. 将多个查询条件组合在一起 (满足条件一且满足条件二，满足条件一或条件二）
                Predicate and = criteriaBuilder.and(predicate,predicate1); //以与的形式拼接多个查询条件
//                criteriaBuilder.or(); //满足其中一个条件


                return and;
            }
        };

        Optional<Customer> customer = customerDao.findOne(spec);
        System.out.println(customer);
    }


    /**案例：根据客户名称的模糊匹配，返回客户列表
     *
     * gt, lt,ge, le, like：得到path对象，根据path对象指定比较的参数类型，再去比较
     *      指定的参数类型， path.as（类型的字节码对象）
     * **/
    @Test
    public void testSpec3() {
        Specification<Customer> spec = new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                //查询属性
                Path<Object> custPhone = root.get("custPhone");


                //查询方式
                Predicate predicate = criteriaBuilder.like(custPhone.as(String.class),"%7");

                return predicate;
            }
        };
        List<Customer> customerList = customerDao.findAll(spec);
        for (Customer customer: customerList) {
            System.out.println(customer);
        }
    }


    /**排序查询**/
    @Test
    public void testSorting() {
        
        
        Specification<Customer> spec = new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                //获取实体类属性
                Path<Object> name = root.get("name");

                //设置查询方式
                Predicate predicate = criteriaBuilder.like(name.as(String.class), "M%");

                return predicate;
            }
        };
        
        
        //创建排序的对象：需要调用构造方法进行实例化sort对象
        //第一个参数，排序的顺序（倒序，正序）
        //Sort.Direction.DESC:倒序
        //Sort.Direction.ASC: 正序

        
        Sort sort = Sort.by(Sort.Direction.DESC, "name");
        List<Customer> customerList = customerDao.findAll(spec,sort);
        for (Customer customer: customerList) {
            System.out.println(customer);
        }
    }



    /**分页查询
     *
     * Pageable: 分页参数
     *      分页参数: 查询的页码，每页查询的条数
     *      findAll(Specification, Pageable)： 带有条件的分页
     *      findAll(Pageable) 没有条件的分页
     * 返回：Page(SpringDataJpa) 为我们封装好的pageBean对象，数据列表，共条数）
     * **/
    @Test
    public void testPaging() {

        Specification<Customer> spec = new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Path<Object> name = root.get("name");

                Predicate predicate = criteriaBuilder.like(name.as(String.class), "%m");

                return predicate;
            }
        };
        //PageRequest对象是pageable接口的实现类
        //调用pagerequest过程中，调用它的构造方法传入两个参数
            //* 当前查询的页数（从0开始）
            //* 第二个参数，每页查询的数量

            //实现类的静态方法
        Pageable pageable = PageRequest.of(0,2);
        Page<Customer> customerPage = customerDao.findAll(spec=null, pageable);

        System.out.println(customerPage.getTotalElements()); //总条数
        System.out.println(customerPage.getTotalPages()); //总页数
        System.out.println(customerPage.getContent()); //集合列表

    }
}
