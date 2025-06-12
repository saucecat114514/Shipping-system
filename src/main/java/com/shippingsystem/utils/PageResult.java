package com.shippingsystem.utils;

import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.util.List;

/**
 * 分页结果封装
 */
@Data
public class PageResult<T> {
    
    /**
     * 当前页码
     */
    private Integer pageNum;
    
    /**
     * 每页大小
     */
    private Integer pageSize;
    
    /**
     * 总记录数
     */
    private Long total;
    
    /**
     * 总页数
     */
    private Integer pages;
    
    /**
     * 数据列表
     */
    private List<T> list;
    
    /**
     * 是否有下一页
     */
    private Boolean hasNextPage;
    
    /**
     * 是否有上一页
     */
    private Boolean hasPreviousPage;
    
    public PageResult() {}
    
    public PageResult(List<T> list) {
        PageInfo<T> pageInfo = new PageInfo<>(list);
        this.pageNum = pageInfo.getPageNum();
        this.pageSize = pageInfo.getPageSize();
        this.total = pageInfo.getTotal();
        this.pages = pageInfo.getPages();
        this.list = pageInfo.getList();
        this.hasNextPage = pageInfo.isHasNextPage();
        this.hasPreviousPage = pageInfo.isHasPreviousPage();
    }
    
    /**
     * 创建分页结果
     */
    public static <T> PageResult<T> of(List<T> list) {
        return new PageResult<>(list);
    }
    
    /**
     * 创建空分页结果
     */
    public static <T> PageResult<T> empty() {
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setPageNum(1);
        pageResult.setPageSize(10);
        pageResult.setTotal(0L);
        pageResult.setPages(0);
        pageResult.setList(List.of());
        pageResult.setHasNextPage(false);
        pageResult.setHasPreviousPage(false);
        return pageResult;
    }
} 