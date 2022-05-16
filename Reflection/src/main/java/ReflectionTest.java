import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

public class ReflectionTest {

    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        //properties继承hashtable
        //1.加载配置文件
        //1.1 创建properties对象
        Properties pro = new Properties();

        System.out.println(pro.getClass());
        System.out.println(pro.getClass().getClassLoader()); //return null -> loaded by bootstrap classloader


        /**需求：创建任意类对象，并执行其中任意的方法
         * 实现：
         *      1.配置文件
         *      2.反射
         *
         * 步骤:
         *      1. 将需要创建的对象的全类名和需要执行的方法配置在配置文件中
         *      2. 在程序中加载读取配置文件
         *      3. 使用反射技术加载类文件进内存
         *      4. 创建对象
         *      5. 执行方法
         *
         *
         *      总结： 1. 读取配置文件内容（用二进制字节流）-》创建对象存储字节流内容
         *            2. 利用反射获取初始化好的字节码文件.class ->对字节码文件三部件（对象）进行操作
         *            3.
         * **/

        //1.2 加载配置文件，转换为一个集合
        //输入字节流来获取文件路径下的文件
        //1.2.1 获取class目录下的配置文件
        //1.2.1.1 获取当前class类的类加载器: 类加载器可以视为搜索文件的位置列表，能扫描到当前类的位置，也就扫描的到resource包下的文件
        ClassLoader classLoader = ReflectionTest.class.getClassLoader();
        //1.2.1.2 通过类加载器获取resource路径下的字节流 -》 把磁盘文件用二进制流的形式加载到内存中
        InputStream is = classLoader.getResourceAsStream("pro.properties");
        pro.load(is);


        //2.获取配置文件中定义的数据
        String className = pro.getProperty("className");
        String methodName = pro.getProperty("methodName");

        //3. 加载该类进内存
        Class clazz =  Class.forName(className);

        //4. 创建对象
        Object obj = clazz.newInstance();
        //5. 获取方法对象,带参数的
        Method method2 = clazz.getMethod(methodName,String.class);
        //6. 执行方法
        method2.invoke(obj,"rice");
    }
}
