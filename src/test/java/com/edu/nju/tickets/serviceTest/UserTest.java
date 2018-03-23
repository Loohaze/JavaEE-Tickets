package com.edu.nju.tickets.serviceTest;


import com.edu.nju.tickets.exception.userException.UserExistedException;
import com.edu.nju.tickets.service.UserService;
import com.edu.nju.tickets.util.Config;
import com.edu.nju.tickets.vo.User.UserAccountVO;
import com.edu.nju.tickets.vo.User.UserInfoVO;
import com.edu.nju.tickets.vo.User.UserLevelVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {

    @Autowired
    private UserService userService;

    @Test
    public void testRegister() throws UserExistedException {
        String userName = "loohaze";
        String email = "loohaze529@gmail.com";
        String password = "huangyan529";

        userService.register(userName,email,password);

    }

    @Test
    public void testVerify(){
        String userName = "loohaze";
        String password = "huangyan529";

    }

    @Test
    public void testCancel(){
        String userName = "loohaze";
        userService.cancel(userName);
    }

    @Test
    public void testModifyInfo(){
        UserInfoVO vo = new UserInfoVO();
        vo.setUserName("loohaze");
        vo.setBirthday("1997-05-29");
        vo.setGender(Config.GENDER.MALE);
        vo.setPhoneNumber("15895870061");
        userService.modifyInfo(vo);
    }

    @Test
    public void testModifyPwd(){
        String userName = "loohaze";
        String oldPwd = "huangyan529";
        String newPwd = "111111";
        userService.modifyPwd(userName,oldPwd,newPwd);
    }

    @Test
    public void testFindInfo(){
        String userName = "loohaze";
        UserInfoVO vo = userService.findInfoByUserName(userName);
        System.out.println(vo.toString());

    }

    @Test
    public void testFindAccount(){
        String userName = "loohaze";
        UserAccountVO vo = userService.findAccountByUserName(userName);
        System.out.println(vo.toString());
    }

    @Test
    public void testFindLevel() {
        String userName = "loohaze";
        UserLevelVO vo = userService.findLevelByUserName(userName);
        System.out.println(vo.toString());
    }

    @Test
    public void testRecharge() {
        String userName = "loohaze";
        double amount = 3000.0;
        userService.recharge(userName,amount);
    }
}
