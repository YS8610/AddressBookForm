package tfip.modserver.addressbook.config;


import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
// import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import static tfip.modserver.addressbook.Constants.*;

@Configuration
// @EnableConfigurationProperties(RedisConfig.class)
public class RedisConfig {

    @Value("${spring.redis.host}") 
    private String redisHost;
    
    @Value("${spring.redis.port}") 
    private Optional<String> redisPort;
    
    @Value("${spring.redis.database}") 
    private int redisDatabase;

    // @Value("${spring.redis.pw}")
    // private String pw;
    private String pw;

    @Bean("redisDB")
    @Scope("singleton")
    public RedisTemplate<String, String> createRedisTemplate(){
        Logger logger = LoggerFactory.getLogger(RedisTemplate.class);
        logger.info("redishost: " + redisHost);
        logger.info("redisPort: " + redisPort);
        
        String k = System.getenv(ENV_REDIS_PW);
        if (null!=k && !k.isBlank()){
            pw = k;
        }
        else{
            pw = "";
        }

        final RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        // config.setDatabase(redisDatabase);
        config.setHostName(redisHost);
        config.setPort(Integer.parseInt(redisPort.get()));
        config.setPassword(pw);

        final JedisClientConfiguration jedisClient = JedisClientConfiguration.builder().build();
        final JedisConnectionFactory jedisFac = new JedisConnectionFactory(config,jedisClient);
        jedisFac.afterPropertiesSet();

        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisFac);
        template.setKeySerializer(new StringRedisSerializer());
        // template.setHashKeySerializer(new StringRedisSerializer());
        // template.setHashKeySerializer(new JdkSerializationRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());

        return template;
    }
}