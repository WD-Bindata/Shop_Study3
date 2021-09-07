import com.wangd.dao.MenusDAO;
import com.wangd.pojo.Menus;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.*;

/**
 * @author wangd
 */
public class WebTest {
    /**
     * 用于测试：
     */
    @Test
    public void test(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("/applicationContext.xml");
        MenusDAO menusDAO = ctx.getBean(MenusDAO.class);

        List<Menus> menus = menusDAO.getMenus();


        for (Menus menu : menus) {
            List<Menus> childrenMenus = menusDAO.getChildrenMenus(menu.getMenuId());

            if (!childrenMenus.isEmpty()){
                menu.setChildren(childrenMenus);
            }
            System.out.println("menu = " + menu);

        }



//        Map<String, Object> menusMap = new HashMap<>();
//        List<Map> menusList = new ArrayList<>();
//        List<Menus> menus = bean.getMenus();
//        for (Menus menu : menus) {
//            if (menu.getFatherMenuId() == 0){
//                menusMap.put("id", menu.getMenuId());
//                menusMap.put("authName", menu.getMenuName());
//                menusMap.put("path", menu.getApiPath());
//
//            }else {
//                Map<String, Object> child = new HashMap<>();
//                child.put("id", menu.getMenuId());
//                child.put("authName", menu.getMenuName());
//                child.put("path", menu.getApiPath());
//                menusList.add(child);
//            }
//        }


    }
}
