import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONWriter;
import com.wangd.controller.ManagerController;
import com.wangd.controller.UserController;
import com.wangd.dao.ManagerDAO;
import com.wangd.pojo.Manager;
import com.wangd.pojo.Menus;
import com.wangd.service.ManagerService;
import com.wangd.service.UserService;
import com.wangd.utils.MenusJson;
import com.wangd.utils.TokenUtils;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.*;

/**
 * @author wangd
 */
public class WebTest {


    /**
     * 用于测试：获取Token
     */
    @Test
    public void test01(){
        String wangd = TokenUtils.sign("wangd", "123455");
        System.out.println("wangd = " + wangd);
    }

    /**
     * 用于测试：
     */
    @Test
    public void test(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("/applicationContext.xml");
        UserController userController = ctx.getBean(UserController.class);
        String menus = userController.getMenus();
        System.out.println("menus = " + menus);
        String menus1 = userController.getMenus();
        System.out.println("menus1 = " + menus1);

    }

    /**
     * 用于测试：请求返回模型
     */
    @Test
    public void test02(){
        System.out.println(1 == 0);

    }

    /**
     * 用于测试：根据参数查询管理员
     */
    @Test
    public void test03(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("/applicationContext.xml");
        ManagerController managerController = ctx.getBean(ManagerController.class);
        String users = managerController.getUsers(null, 0, 10);
        System.out.println("users = " + users);


    }

    /**
     * 用于测试：
     */
    @Test
    public void test04(){
        Manager manager = new Manager();
        manager.setRegistrationTime(Math.toIntExact(System.currentTimeMillis() / 1000));
        manager.setEmail("123@qq.com");
        manager.setUsername("wang");
        manager.setPassword("1234");
        manager.setMobile("12345678910");
        manager.setState(1);
        ApplicationContext ctx = new ClassPathXmlApplicationContext("/applicationContext.xml");
        ManagerDAO managerDAO = ctx.getBean(ManagerDAO.class);
        int i = managerDAO.insertManager(manager);
        System.out.println("manager = " + manager);


    }






}
