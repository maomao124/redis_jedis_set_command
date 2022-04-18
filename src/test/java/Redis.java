import org.junit.jupiter.api.*;
import redis.clients.jedis.Jedis;

/**
 * Project name(项目名称)：redis_jedis_set_command
 * Package(包名): PACKAGE_NAME
 * Class(类名): Redis
 * Author(作者）: mao
 * Author QQ：1296193245
 * GitHub：https://github.com/maomao124/
 * Date(创建日期)： 2022/4/18
 * Time(创建时间)： 18:33
 * Version(版本): 1.0
 * Description(描述)：
 * <p>
 * 命令	            说明
 * SADD	            向集合中添加一个或者多个元素，并且自动去重
 * SCARD        	返回集合中元素的个数
 * SDIFF        	求两个或对多个集合的差集
 * SDIFFSTORE	    求两个集合或多个集合的差集，并将结果保存到指定的集合(key)中
 * SINTER	        求两个或多个集合的交集
 * SINTERSTORE	    求两个或多个集合的交集，并将结果保存到指定的集合(key)中
 * SMEMBERS	        查看集合中所有元素
 * SMOVE	        将集合中的元素移动到指定的集合中
 * SPOP	            弹出指定数量的元素
 * SRANDMEMBER	    随机从集合中返回指定数量的元素，默认返回 1个
 * SREM	            删除一个或者多个元素，若元素不存在则自动忽略
 * SUNION	        求两个或者多个集合的并集
 * SUNIONSTORE	    求两个或者多个集合的并集，并将结果保存到指定的集合(key)中
 */

public class Redis
{
    private static Jedis jedis;

    @BeforeEach
    void setUp()
    {
        System.out.println("--------------------------");
    }

    @AfterEach
    void tearDown()
    {
        System.out.println("--------------------------");
    }

    @BeforeAll
    static void beforeAll()
    {
        jedis = new Jedis("127.0.0.1", 6379);
        jedis.auth("123456");
        System.out.println("启动");
    }

    @AfterAll
    static void afterAll()
    {
        if (jedis != null)
        {
            System.out.println("关闭");
            jedis.close();
        }
    }

    @Test
    void test()
    {
        System.out.println(jedis.ping());
    }

    @Test
    void sadd()
    {
       /*
        将指定的成员添加到存储在 key 的设置值。
        如果 member 已经是集合的成员，则不执行任何操作。
        如果 key 不存在，则创建一个以指定成员为唯一成员的新集合。
        如果键存在但不保存设置值，则返回错误。
        时间复杂度 O(1)
        */
        System.out.println(jedis.sadd("set1", "1", "2", "3", "2", "4", "1"));
    }

    @Test
    void scard()
    {
        //返回集合基数（元素数）。如果键不存在，则返回 0，例如空集。
        System.out.println(jedis.scard("set1"));
    }
}
