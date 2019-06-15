package com.sucl.jpa.sys.web;

import com.sucl.jpa.core.orm.jpa.JpaSort;
import com.sucl.jpa.core.util.TreeHelper;
import com.sucl.jpa.core.view.ITreeNode;
import com.sucl.jpa.core.view.TreeNode;
import com.sucl.jpa.core.web.BaseController;
import com.sucl.jpa.sys.entity.Menu;
import com.sucl.jpa.sys.service.MenuService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sucl
 * @date 2019/4/3
 */
@RestController
@RequestMapping("/menu")
public class MenuController extends BaseController<MenuService,Menu> {

    @GetMapping("/tree")
    public List<ITreeNode> menuTree(){
        List<Menu> menus = service.getAllByCondition(null,new JpaSort("menuCode"));
        return TreeHelper.buildMenuNode(menus,Menu::getParentMenuCode,(m)->{return menuToNode(m);});
    }

    private TreeNode menuToNode(Menu menu){
        TreeNode treeNode = new TreeNode();
        treeNode.setId(menu.getMenuCode());
        treeNode.setLabel(menu.getMenuName());
        treeNode.setCls(menu.getStyle());
        treeNode.setHref(menu.getPath());
        treeNode.setLeaf("1".equals(menu.getLeaf()));
        if("0".equals(menu.getLeaf())){
            treeNode.setChildren(new ArrayList<>());
        }
        return treeNode;
    }
}
