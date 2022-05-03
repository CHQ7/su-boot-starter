package com.yunqi.starter.common.page;

/**
 * 分页接口
 * Created by @author CHQ on 2022/1/22
 */
public interface Paginable {
    /**
     * 总记录数
     *
     * @return 总记录数
     */
    public int getTotalCount();

    /**
     * 总页数
     *
     * @return 总页数
     */
    public int getTotalPage();

    /**
     * 每页记录数
     *
     * @return 每页记录数
     */
    public int getPageSize();

    /**
     * 当前页号
     *
     * @return 当前页号
     */
    public int getPage();

    /**
     * 是否第一页
     *
     * @return 是否第一页
     */
    public boolean isFirstPage();

    /**
     * 是否最后一页
     *
     * @return 是否最后一页
     */
    public boolean isLastPage();

    /**
     * 返回下页的页号
     *
     * @return 返回下页的页号
     */
    public int getNextPage();


    /**
     * 返回上页的页号
     *
     * @return 返回上页的页号
     */
    public int getPrePage();

    /**
     * 设置页码
     *
     * @param page    页码
     * @return        设置页码
     */
    Paginable setPage(int page);

    /**
     *  设置一页可以有多少条记录
     *
     * @param pageSize 每页几条数据
     * @return  设置每页几条数据
     */
    Paginable setPageSize(int pageSize);

    /**
     *  设置整个查询一共有多少条记录
     * @param recordCount 一共有多少条记录
     * @return  设置一共有多少条记录
     */
    Paginable setTotalCount(int recordCount);
}
