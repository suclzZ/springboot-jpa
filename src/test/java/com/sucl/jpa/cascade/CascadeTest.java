package com.sucl.jpa.cascade;

import com.sucl.jpa.AbstractTest;
import com.sucl.jpa.sys.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 默认情况下：有配置的一方维护关系，否则需要mappedBy指定
 * CascadeType.PERSIST 级联新增，新增父，包含子不存在则新增，存在则异常；修改父不影响，但子设置null，关系删除；删除父，则父、关系删除
 * CascadeType.MERGE   级联修改，新增父，包含子不存在则异常，存在则新增；修改父也能影响子，子设置null，关系删除；删除父，则父、关系删除
 * CascadeType.REMOVE  级联删除，新增父，包含子不存在则异常，存在则新增；修改父不影响，但子设置null，关系删除；删除父，则父、子、关系删除
 * CascadeType.REFRESH 级联刷新，新增父，包含子不存在则异常，存在则新增；修改父不影响，但子设置null，关系删除；删除父，则父、关系删除
 * CascadeType.DETACH  同REFRESH
 * CascadeType.ALL     包含上述所有操作
 * 总结：
 *  维护方始终维护关系，
 *      PERSIST可以由父新增子，但子不能存在；
 *      MERGE可以由父修改子，但子必须存在；
 *      REFRESH仅仅由父维护关系
 *      REMOVE删除父时，子也删除。
 * 因此mappedBy决定了关系由谁维护，CascadeType则是父子的相互影响
 * @author sucl
 * @date 2019/6/14
 */
public class CascadeTest extends AbstractTest {
    @Autowired
    private UserService userService;

    /**
     *  新增用户：角色不存在会添加；角色已存在则异常(Duplicate entry '001' for key 'PRIMARY')
     *  修改用户：修改角色对象不会影响；但是把用户中的角色设置为null，关联删除
     *  删除用户：用户和关系删除
     */
//    @Test
    public void PERSIST(){
        test();
    }

    /**
     * 新增：如果角色不存在，报错
     * 修改：修改角色对象不会影响；但是把用户中的角色设置为null，关联删除
     * 删除：用户和关系删除
     */
//    @Test
    public void REFRESH(){
       test();
    }

    /**
     * 新增：如果角色不存在，报错
     * 修改：修改角色对象不会影响；但是把用户中的角色设置为null，关联删除
     * 删除：用户和关系删除
     */
//    @Test
    public void DETACH(){
        test();
    }

    /**
     * 新增：如果角色不存在，报错
     * 修改：修改角色对象会影响；但是把用户中的角色设置为null，关联删除
     * 删除：用户和关系删除
     */
//    @Test
    public void MERGE(){
        test();
    }

    /**
     * 新增：如果角色不存在，报错
     * 修改：修改角色对象会影响；但是把用户中的角色设置为null，关联删除
     * 删除：用户、角色和关系都删除
     */
    @Test
    public void REMOVE(){
        test();
    }

    public void test(){
        //新增
//        User user = new User();
//        user.setUsername("abc");
//        List<Role> roles = new ArrayList<>();
//        Role role = new Role();
//        role.setRoleCode("ROLE_1");role.setRoleName("003");
//        roles.add(role);
//        user.setRoles(roles);
//        userService.save(user);
        //修改
//        User user = userService.getById("4");
//        user.setUsername("测试2");
//        List<Role> roles = user.getRoles();
//        if(roles!=null && roles.size()>0){
//            for(Role role:roles){
//                role.setDescription("111");
//            }
//        }
//        user.setRoles(null);//
//        userService.save(user);
        //删除
        userService.deleteById("4");
    }

}
