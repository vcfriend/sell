package com.imooc.sell;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author 向亚林
 * 2018/3/24 18:36
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class LoggerTest {
    private final Logger logger = LoggerFactory.getLogger(LoggerTest.class);

    @Test
    public void test1() {
        logger.info("info...");
        logger.debug("debug...");
        logger.error("error...");
    }

    @Test
    public void test2() {
        String name = "imooc";
        String password = "123456";
        log.info("name: " + name + " password: " + password);
        log.info("name: {}, password: {}", name, password);

        log.debug("debug...");
        log.error("error...");
    }
}
