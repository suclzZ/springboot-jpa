package com.sucl.jpa.core.view;

import java.util.List;

/**
 * @author sucl
 * @date 2019/4/9
 */
public interface ITreeNode {

    /**
     * 名称
     * @return
     */
    String getLabel();

    /**
     * 子节点
     * @return
     */
    List<ITreeNode> getChildren();

    String getId();

    /**
     * 是否禁用
     * @return
     */
    default boolean isDisabled(){
        return false;
    }

    /**
     * 是否禁止拖拽
     * @return
     */
    default boolean isFixed(){
        return true;
    }

    String getHref();

    String getCls();

    Object getObj();
}
