package com.example.redis;

import com.example.redis.model.RedisModel;
import com.example.redis.service.RedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisApplicationTests {

	@Autowired
	private RedisService redisService;

	@Test
	public void contextLoads() {
		System.out.println("2");
		System.out.println(redisService.get("1111l"));

		System.out.println(redisService.get("1213"));
	}

}
