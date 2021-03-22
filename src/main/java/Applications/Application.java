package Applications;

import org.apache.catalina.connector.Connector;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
@EnableScheduling//定时任务
@MapperScan(value="com.vue.dao")
@ComponentScan("com.vue")
@ComponentScan("com.vue.config")
@ComponentScan("com.vue.interceptor")
@ComponentScan("com.vue.result")

public class Application {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(Application.class,args);
		//允许特殊符号,本例是 | { } 做入参，也可追加其他符号
		System.out.println("6666");
        System.setProperty("tomcat.util.http.parser.HttpParser.requestTargetAllow","|{}[]");
	}
	//手动设置tomcat工厂，允许特殊符号,本例是 | { } 做入参，也可追加其他符号
	@Bean
    public TomcatEmbeddedServletContainerFactory tomcatFactory() {
		TomcatEmbeddedServletContainerFactory tomcatFactory = new TomcatEmbeddedServletContainerFactory();
		tomcatFactory.addConnectorCustomizers(new TomcatConnectorCustomizer() {
            @Override
            public void customize(Connector connector) {
                connector.setProperty("relaxedQueryChars", "|{}[]");
            }
        });
        return tomcatFactory;
    }
	/**
	 * 设置 redisTemplate 的序列化设置
	 * @param redisConnectionFactory
	 * @return
	 */
	@Bean
	public RedisTemplate<Object,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
		//创建redistenplate模板
		RedisTemplate<Object,Object> redisTemplate=new RedisTemplate<>();
		//关联redisConnectionFactory
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		//创建序列化类
		GenericToStringSerializer genericToStringSerializer = new GenericToStringSerializer(Object.class);
		// 序列化类，对象映射设置
	    // 设置 value 的转化格式和 key 的转化格式
		redisTemplate.setValueSerializer(genericToStringSerializer);
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}
	
}
