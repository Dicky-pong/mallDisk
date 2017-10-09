package com.pwx.mall.disk.common.pagination;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * <分页查询封装公共类>
 * 
 * @version 
 * <pre>
 * Author	Version		Date		Changes
 * tanchao 	1.0  		2015年7月14日 	Created
 *
 * </pre>
 * @since 1.
 */
public class Page implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 一页显示大小
     */
    private Integer pageSize;
    
    /**
     * 当前页
     */
    private Integer pageIndex;
    
    /**
     * 总页数
     */
    private Integer pageCount;
    
    private List<?> data;

    private List<?> qrcodeDate;
    
	public List<?> getQrcodeDate() {
		return qrcodeDate;
	}


	public void setQrcodeDate(List<?> qrcodeDate) {
		this.qrcodeDate = qrcodeDate;
	}


	public Integer getPageSize() {
        return pageSize;
    }

    
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    
    public Integer getPageIndex() {
        return pageIndex;
    }

    
    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    
    public Integer getPageCount() {
        return pageCount;
    }

    
    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }


    
    public List<?> getData() {
        return data;
    }


    
    public void setData(List<?> data) {
        this.data = data;
    }

    
}
