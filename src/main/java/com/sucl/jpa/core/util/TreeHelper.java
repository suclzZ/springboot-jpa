package com.sucl.jpa.core.util;

import com.sucl.jpa.core.view.ITreeNode;
import com.sucl.jpa.core.view.TreeNode;
import com.sucl.jpa.core.view.ITreeNode;
import com.sucl.jpa.core.view.TreeNode;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author sucl
 * @date 2019/4/19
 */
public class TreeHelper<T> {

    /**
     *
     * @param types
     * @param parentCoder 获取子与父关联的值
     * @param menuNodeBuild 转换成treeNode
     * @return
     */
    public static <T> List<ITreeNode> buildMenuNode(List<T> types, Function<T,String> parentCoder, Function<T,TreeNode> menuNodeBuild){
        if(types!=null){
            return types.stream().filter(m -> {
                return parentCoder!=null ? StringUtils.isEmpty(parentCoder.apply(m)):true;
            }).map(m->{
                TreeNode node = menuNodeBuild.apply(m);
                findChildren(node,types,parentCoder,menuNodeBuild);
                return node;
            }).collect(Collectors.toList());
        }
        return null;
    }

    private static <T> void findChildren(TreeNode type, List<T> types, Function<T, String> parentCoder, Function<T, TreeNode> menuNodeBuild) {
        types.stream().forEach(m->{
            if(parentCoder!=null){
                if(type.getId().equals(parentCoder.apply(m))){
                    TreeNode node = menuNodeBuild.apply(m);
                    type.add(node);
                    findChildren(node,types,parentCoder, menuNodeBuild);
                }
            }
        });
    }

}
