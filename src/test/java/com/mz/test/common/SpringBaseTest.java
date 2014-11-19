package com.mz.test.common;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 * SpringBaseTest，使用Junit测试时的基类
 * 
 * @author xueyuan
 * @since 1.0
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-test-datasources.xml" })
//@ContextConfiguration(locations = { "classpath:spring-beans.xml" })
@TransactionConfiguration(transactionManager = "transactionManager")
public class SpringBaseTest {

}
