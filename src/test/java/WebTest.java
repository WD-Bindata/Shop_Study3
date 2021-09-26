import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.wangd.controller.AuthorityManager;
import com.wangd.controller.ManagerController;
import com.wangd.controller.UserController;
import com.wangd.dao.ManagerDAO;
import com.wangd.dao.MenusDAO;
import com.wangd.dao.RoleDAO;
import com.wangd.pojo.Manager;
import com.wangd.pojo.Menus;
import com.wangd.pojo.Role;
import com.wangd.service.ManagerService;
import com.wangd.service.RoleService;
import com.wangd.service.UserService;
import com.wangd.utils.MenusJson;
import com.wangd.utils.TokenUtils;
import org.junit.Test;
import org.junit.platform.commons.util.StringUtils;
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
        String users = managerController.getUsers(null, 2, 10);
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

    /**
     * 用于测试：更新账户状态
     */
    @Test
    public void test05(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("/applicationContext.xml");
        ManagerDAO managerDAO = ctx.getBean(ManagerDAO.class);
        int manager = managerDAO.updateState(519, 1);
        System.out.println("manager = " + manager);
    }


    /**
     * 用于测试：菜单栏
     */
    @Test
    public void test06(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("/applicationContext.xml");
        UserController userController = ctx.getBean(UserController.class);
        String menus = userController.getMenus();
        System.out.println("menus = " + menus);
    }

    /**
     * 用于测试：role
     */
    @Test
    public void test07(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("/applicationContext.xml");
        RoleDAO roleDAO = ctx.getBean(RoleDAO.class);
        List<Role> roleList = roleDAO.queryAllRole();
        roleList.forEach(role -> {
            System.out.println("role = " + role);
        });

    }

    /**
     * 用于测试：获取Manager和role
     */
    @Test
    public void test08(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("/applicationContext.xml");
        UserService bean = ctx.getBean(UserService.class);
        RoleService roleService = ctx.getBean(RoleService.class);
        MenusDAO menusDAO = ctx.getBean(MenusDAO.class);
        List<Menus> menusList = menusDAO.queryAllMenus();
        menusList.sort((o1, o2) -> {
            if (o1.getLevel() < o2.getLevel()){
                return -1;
            } else {
                return 0;
            }

        });
        Map<Integer, Menus> oneMenus = new HashMap<>();
        menusList.forEach(menus -> {
            if (menus.getLevel() == 0){
                oneMenus.put(menus.getMenuId(), menus);
            } else if (menus.getLevel() == 1){
                Menus menus2 = oneMenus.get(menus.getFatherMenuId());
                List<Menus> children = menus2.getChildren();
                children.add(menus2);
            }else if (menus.getLevel() == 2){
                Menus menus2 = oneMenus.get(menus.getFatherMenuId());
                if (menus2 != null){
                    List<Menus> children = menus2.getChildren();
                    children.add(menus2);
                }
            }
        });

        System.out.println("oneMenus = " + oneMenus);

    }



    /**
     * 用于测试：处理三级权限
     */
    @Test
    public void test09(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("/applicationContext.xml");
        MenusDAO menusDAO = ctx.getBean(MenusDAO.class);

        List<Integer> ids = Arrays.asList(101, 104, 105, 116, 117, 150, 151, 152, 153, 115, 142, 143, 144, 121, 122, 123, 149, 103, 111, 129, 130, 134, 135, 138, 139, 140, 141, 112, 147, 125, 110, 131, 132, 133, 136, 137, 159, 146);

        List<Menus> menusList = menusDAO.queryAllMenus();

        menusList.sort((o1, o2) -> {
            if (o1.getLevel() < o2.getLevel()){
                return -1;
            } else {
                return 0;
            }

        });

        Map<Integer, Menus> result = new HashMap<>();
        Map<Integer, Menus> jurisdiction2 = new HashMap<>();
        Map<Integer, Menus> jurisdiction3 = new HashMap<>();
        for (Menus menu : menusList) {
            if (menu.getLevel() == 0){
                result.put(menu.getMenuId(), menu);
            } else if (menu.getLevel() == 1){
                Menus menus2 = result.get(menu.getFatherMenuId());
                List<Menus> children = menus2.getChildren();
                children.add(menu);
                jurisdiction2.put(menu.getMenuId(), menu);
            }
        }

        for (Menus menus : menusList) {
            if (menus.getLevel() == 2 && jurisdiction2.containsKey(menus.getFatherMenuId()) && ids.contains(menus.getFatherMenuId())){
                Menus menus2 = jurisdiction2.get(menus.getFatherMenuId()); // 获得二级标签
                Menus menus1 = result.get(menus2.getFatherMenuId());  // 获得一级标签

                List<Menus> children = menus2.getChildren();
                children.add(menus);
                List<Menus> children1 = menus1.getChildren();
                children1.add(menus2);
                jurisdiction3.put(menus.getMenuId(), menus);
            }
        }
        System.out.println("result = " + result);


    }

    /**
     * 用于测试：roleService.getPermissions()
     */
    @Test
    public void test10(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("/applicationContext.xml");
        RoleService roleService = ctx.getBean(RoleService.class);
        List<Role> roleList = roleService.getRoles();
        for (Role role : roleList) {
            String[] roleIds = role.getRoleIds().split(",");
            Map<Integer, Menus> integerMenusMap = roleService.screenPermissions(Arrays.asList(roleIds));
            System.out.println("JSON.toJSONString(integerMenusMap, true) = " + JSON.toJSONString(integerMenusMap, true));
        }

    }

    /**
     * 用于测试：RoleController.getRoles
     */
    @Test
    public void test11(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("/applicationContext.xml");
        AuthorityManager authorityManager = ctx.getBean(AuthorityManager.class);
        String roles = authorityManager.getRoles();
        System.out.println("roles = " + roles);
    }

    /**
     * 用于测试：AuthorityManager.getRightsTree
     */
    @Test
    public void test12(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("/applicationContext.xml");
        AuthorityManager authorityManager = ctx.getBean(AuthorityManager.class);
        String rightsTree = authorityManager.getRightsTree();
        System.out.println("rightsTree = " + rightsTree);
    }

    /**
     * 用于测试：RoleService.addRole
     */
    @Test
    public void test13(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("/applicationContext.xml");
        RoleService roleService = ctx.getBean(RoleService.class);
        Role role = new Role();
        role.setRoleName("test1");
        role.setRoleDesc("测设角色");
        int i = roleService.addRole(role);
        System.out.println("role = " + role);
    }

    /**
     * 用于测试：删除权限
     */
    @Test
    public void test14(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("/applicationContext.xml");
        AuthorityManager authorityManager = ctx.getBean(AuthorityManager.class);
        String deleteAppointRights = authorityManager.deleteAppointRights(30, 153);
        System.out.println("deleteAppointRights = " + deleteAppointRights);
    }

    /**
     * 用于测试：修改权限
     */
    @Test
    public void test15(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("/applicationContext.xml");
        AuthorityManager authorityManager = ctx.getBean(AuthorityManager.class);
        HashMap<String, String> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("rids", "105,116,117,115,142,143,144,121,122,123,149,129,134,138,112,147,125,110,131,132,133,136,137,159");
        String authorizationRoles = authorityManager.authorizationRoles(31, objectObjectHashMap);
        System.out.println("authorizationRoles = " + authorizationRoles);
    }

}
