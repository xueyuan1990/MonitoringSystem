package com.mz.service;

import org.jtester.annotations.DbFit;
import org.jtester.annotations.SpringApplicationContext;
import org.jtester.annotations.SpringBeanByName;
import org.jtester.testng.JTester;
import org.junit.Assert;
import org.testng.annotations.Test;

import com.mz.entity.User;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:spring-test-datasources.xml" })
//@TransactionConfiguration(transactionManager = "transactionManager")
@SpringApplicationContext({ "spring-test-datasources.xml" })
public class UserServiceTest extends JTester {
    //注入 
    //    @Autowired
    @SpringBeanByName("userService")
    UserService userService;


    private void init() {
        db.table("user").clean();
        //        String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()).toString();
        //        db.table("user").clean().insert(new DataMap() {
        //            {
        //                this.put("username", "u1");
        //                this.put("password", "p1");
        //                this.put("createTime", "2012-01-01 00:00:00");
        //            }
        //        }).commit();
    }


    @Test
    @DbFit(when = "loginCheck.when.wiki")
    public void testLoginCheck() {
        User user = new User();
        user.setUsername("31");
        user.setPassword("p1");
        System.out.println(userService + "*********************");
        Assert.assertEquals(true, userService.loginCheck(user));
    }
}
