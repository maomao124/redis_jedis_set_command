import org.junit.jupiter.api.*;
import redis.clients.jedis.Jedis;

import java.util.Set;

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

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp()
    {
        System.out.println("--------------------------");
    }

    /**
     * Tear down.
     */
    @AfterEach
    void tearDown()
    {
        System.out.println("--------------------------");
    }

    /**
     * Before all.
     */
    @BeforeAll
    static void beforeAll()
    {
        jedis = new Jedis("127.0.0.1", 6379);
        jedis.auth("123456");
        System.out.println("启动");
    }

    /**
     * After all.
     */
    @AfterAll
    static void afterAll()
    {
        if (jedis != null)
        {
            System.out.println("关闭");
            jedis.close();
        }
    }

    /**
     * Test.
     */
    @Test
    void test()
    {
        System.out.println(jedis.ping());
    }

    /**
     * Sadd.
     */
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

    /**
     * Scard.
     */
    @Test
    void scard()
    {
        //返回集合基数（元素数）。如果键不存在，则返回 0，例如空集。
        System.out.println(jedis.scard("set1"));
    }

    /**
     * Sdiff.
     */
    @Test
    void sdiff()
    {
        /*
        返回存储在 key1 的 Set 与所有 Sets key2, ..., keyN 之间的差异
        例子：
        key1 = [x, a, b, c]
        key2 = [c]
        key3 = [a, d]
        SDIFF key1,key2,key3 => [x, b]
        不存在的键被视为空集。
        时间复杂度：
        O(N) 其中 N 是所有集合的元素总数
        */
        System.out.println(jedis.sadd("set2", "1", "2", "3", "8", "9"));
        Set<String> set = jedis.sdiff("set1", "set2");
        System.out.println(set);
    }

    /**
     * Sdiffstore.
     */
    @Test
    void SDIFFSTORE()
    {
        //此命令的工作方式与SDIFF完全相同，但结果集存储在 dstkey 中，而不是返回。
        long sdiffstore = jedis.sdiffstore("set3", "set1", "set2");
        System.out.println(sdiffstore);
    }

    /**
     * Sinter.
     */
    @Test
    void sinter()
    {
        /*
        返回一个集合的成员，该集合由所有集合的交集在指定键处保持。
        就像在LRANGE中一样，结果作为多批回复发送到连接（有关更多信息，请参阅协议规范）。
        如果只指定了一个键，则此命令产生与SMEMBERS相同的结果。实际上 SMEMBERS 只是 SINTER 的语法糖。
        不存在的键被视为空集，因此如果其中一个键丢失，则返回空集（因为与空集的交集始终是空集）。
        时间复杂度 O(N*M) 最坏情况，其中 N 是最小集合的基数，M 是集合的数量
        */
        Set<String> set = jedis.sinter("set1", "set2");
        System.out.println(set);
    }

    /**
     * Sinterstore.
     */
    @Test
    void sinterstore()
    {
        //此命令的工作方式与SINTER完全相同，但结果集不会被返回，而是存储为 dstkey。
        //时间复杂度 O(N*M) 最坏情况，其中 N 是最小集合的基数，M 是集合的数量
        long sinterstore = jedis.sinterstore("set4", "set1", "set2");
        System.out.println(sinterstore);
    }

    /**
     * Smembers.
     */
    @Test
    void smembers()
    {
        //返回存储在 key 的集合值的所有成员（元素）
        //时间复杂度 O(N)
        Set<String> set1 = jedis.smembers("set1");
        System.out.println(set1);
        Set<String> set2 = jedis.smembers("set2");
        System.out.println(set2);
        Set<String> set3 = jedis.smembers("set3");
        System.out.println(set3);
        Set<String> set4 = jedis.smembers("set4");
        System.out.println(set4);
    }

    /**
     * Smove.
     */
    @Test
    void smove()
    {
        /*
        将指定成员从 srckey 的集合移动到 dstkey 的集合。
        这个操作是原子的，在每个给定的时刻，元素都将出现在用于访问客户端的源或目标集中。
        如果源集合不存在或不包含指定元素，则不执行任何操作并返回零，
        否则从源集合中删除元素并添加到目标集合。
        成功时返回一个，即使该元素已经存在于目标集中。
        如果源或目标键包含非 Set 值，则会引发错误。
        时间复杂度 O(1)
        */
        long smove = jedis.smove("set3", "set4", "4");
        System.out.println(smove);
    }

    /**
     * Spop.
     */
    @Test
    void spop()
    {
        /*
        从集合中弹出一个成员。
        在此命令中，回复将包含多达 count 个成员，具体取决于集合的基数。
        SetCommands.srandmember(String)命令执行类似的工作，但返回的元素不会从 Set 中删除。
        时间复杂度 O(N)，其中 N 是传递计数的值
        */
        String set4 = jedis.spop("set4");
        System.out.println(set4);
        Set<String> set = jedis.spop("set4", 2);
        System.out.println(set);
    }

    /**
     * Srandmember.
     */
    @Test
    void srandmember()
    {
       /*
       从 Set 中返回一个随机元素，而不删除该元素。
       如果 Set 为空或 key 不存在，则返回 nil 对象。
       SPOP 命令执行类似的工作，但返回的元素从 Set 中弹出（删除）。
       时间复杂度 O(1)
       */
        String set2 = jedis.srandmember("set2");
        System.out.println(set2);
    }

    /**
     * Srem.
     */
    @Test
    void srem()
    {
        //从存储在 key 的设置值中删除指定的成员。
        //如果成员不是集合的成员，则不执行任何操作。如果 key 不包含设置值，则返回错误。
        //时间复杂度 O(1)
        System.out.println(jedis.smembers("set2"));
        long l = jedis.srem("set2", "0");
        System.out.println(l);
        long l1 = jedis.srem("set2", "2");
        System.out.println(l1);
        System.out.println(jedis.smembers("set2"));
    }

    /**
     * Sunion.
     */
    @Test
    void sunion()
    {
        /*
        返回一个集合的成员，该集合由所有集合的并集得到指定键。
        就像在LRANGE中一样，结果作为多批回复发送到连接。
        如果只指定了一个键，则此命令产生与SMEMBERS相同的结果。
        不存在的键被视为空集。
        时间复杂度 O(N) 其中 N 是所有提供的集合中的元素总数
        */
        System.out.println(jedis.smembers("set1"));
        System.out.println(jedis.smembers("set2"));
        Set<String> sunion = jedis.sunion("set1", "set2");
        System.out.println(sunion);
    }

    /**
     * Sunionstore.
     */
    @Test
    void sunionstore()
    {
        /*
        此命令的工作方式与SUNION完全相同，但结果集不会被返回，
        而是存储为 dstkey。 dstkey 中的任何现有值都将被覆盖。
        时间复杂度 O(N) 其中 N 是所有提供的集合中的元素总数
        */
        System.out.println(jedis.smembers("set1"));
        System.out.println(jedis.smembers("set2"));
        long sunionstore = jedis.sunionstore("set5", "set1", "set2");
        System.out.println(sunionstore);
        System.out.println(jedis.smembers("set5"));
    }
}
