import com.hjt.user.UserServiceApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

/**
 * @author 胡江涛
 * @version 1.0
 * @date 2021/3/15 11:59
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserServiceApplication.class)
public class RedisTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void testRedis(){
        stringRedisTemplate.opsForValue().set("key2","value2");
        String key2 = stringRedisTemplate.opsForValue().get("key2");
        System.out.println("key2="+key2);
    }

    @Test
    public void testRedis2(){
        stringRedisTemplate.opsForValue().set("key3","value3",20, TimeUnit.SECONDS);
        String key3 = stringRedisTemplate.opsForValue().get("key3");
        System.out.println("key3="+key3);
    }
}
