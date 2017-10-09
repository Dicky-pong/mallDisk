package com.pwx.mall.disk.common.baseUtil;

import java.io.Serializable;

/**
 * 
 * <公共Service类>
 * 
 * @version 
 * <pre>
 * Author	Version		Date		Changes
 * tanchao 	1.0  		2016年3月17日 	Created
 *
 * </pre>
 * @since 1.
 */
public interface BaseInterface {

    /**
     * 
     * TODO 增加.
     * @param o
     * @return
     */
    String add(Object o);
    
    /**
     * 
     * TODO 删除.
     * @param o
     */
    void delete(Object o);
    
    /**
     * 
     * TODO 修改.
     * @param o
     */
    void update(Object o);
    
    /**
     * 
     * TODO 根据id查询Object.
     * @param c
     * @param id
     * @return
     */
    Object getById(Class<?> c,Serializable id);
    
}
