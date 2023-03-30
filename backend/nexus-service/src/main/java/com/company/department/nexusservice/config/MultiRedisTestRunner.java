package com.company.department.nexusservice.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;


public class MultiRedisTestRunner implements CommandLineRunner {
    private final static Logger logger = LoggerFactory.getLogger(MultiRedisTestRunner.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void run(String... strings) throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        try {
            for (int i = 0; i < 1; i++) {
                logger.info("=====================================================================");
                logger.info("start loop " + i);
                String key = "key" + i;
                stringRedisTemplate.opsForValue().set(key, "value" + i);

                String primaryKeyValue = stringRedisTemplate.opsForValue().get(key);

                logger.info("=====================================================================");
                logger.info(String.format("read from the redis1, key %s value is %s", key, primaryKeyValue));
            }
        }
        finally {
            System.out.println("LATCH");
            latch.countDown();

            System.out.println("DOWN");
        }
    }
}
