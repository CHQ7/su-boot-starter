package com.yunqi.starter.common.page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页模型
 * Created by @author CHQ on 2022/1/27
 */
@Slf4j
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pagination<T> implements Paginable, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 改变这个，当每页大小超过 MAX_FETCH_SIZE 时，这个将是默认的 fetchSize
     */
    public static int DEFAULT_PAGE_SIZE = 10;

    /** 页码 */
    protected int page;

    /** 每页几条数据 */
    protected int pageSize;

    /** 总页数 */
    protected int totalPage = getTotalPage();

    /** 总共几条数据 */
    protected int totalCount;

    /** 数据列表 */
    protected List<T> list = new ArrayList<>();

    public Pagination(int page) {
        if (page < 1) {
            page = 1;
        }
        this.page =  page;
        this.pageSize = DEFAULT_PAGE_SIZE;
    }

    public Pagination(int page, int pageSize) {
        if (page < 1) {
            page = 1;
        }
        if (pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        this.page = page;
        this.pageSize = pageSize;
    }

    public static <T> Pagination<T> build() {
        return Pagination.<T> builder()
                .build();
    }

    public static <T> Pagination<T> build(int page, int pageSize) {
        return Pagination.<T> build()
                .page(page)
                .size(pageSize);
    }

    public static <T> Pagination<T> build(List<T> list) {
        return Pagination.<T> build()
                .list(list);
    }

    public Pagination<T> list(List<T> list) {
        setList(list);
        return this;
    }

    public Pagination<T> page(int page) {
        setPage(page);
        return this;
    }

    public Pagination<T> size(int size) {
        setPageSize(size);
        return this;
    }

    public Pagination<T> totalCount(int totalCount) {
        setTotalCount(totalCount);
        return this;
    }


    /**
     * 获取总记录数
     */
    @Override
    public int getTotalCount() {
        return totalCount;
    }


    /**
     * 获取总页数
     */
    @Override
    public int getTotalPage() {
        if (totalPage < 0) {
            totalPage = (int) Math.ceil((double) totalCount / pageSize);
        }
        return totalPage;
    }

    /**
     * 每页记录数
     */
    @Override
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 当前页号， 从 1 开始
     */
    @Override
    public int getPage() {
        return page;
    }

    /**
     * 是否第一页
     */
    @Override
    public boolean isFirstPage() {
        return page == 1;
    }

    /**
     * 是否最后一页
     */
    @Override
    public boolean isLastPage() {
        if (totalPage == 0) {
            return true;
        }
        return page == totalPage;
    }

    /**
     * 返回下页的页号
     */
    @Override
    public int getNextPage() {
        if (isLastPage()) {
            return page;
        } else {
            return page + 1;
        }
    }

    /**
     * 返回上页的页号
     */
    @Override
    public int getPrePage() {
        if (isFirstPage()) {
            return page;
        } else {
            return page - 1;
        }
    }

    @Override
    public Paginable setPage(int page) {
        if (1 > page && log.isInfoEnabled()) {
            log.info("PageNumber shall start at 1, but input is {}, that mean pager is disable", page);
        }
        this.page = page;
        return this;
    }

    /**
     * 设置每页几条数据
     */
    @Override
    public Paginable setPageSize(int pageSize) {
        this.pageSize = (pageSize > 0 ? pageSize : DEFAULT_PAGE_SIZE);
        return this;
    }

    /**
     * 设置总页数
     */
    @Override
    public Paginable setTotalCount(int totalCount) {
        this.totalCount = Math.max(totalCount, 0);
        this.totalPage = (int) Math.ceil((double) totalCount / pageSize);
        return this;
    }




}
