package com.yunqi.starter.common.model;

import com.yunqi.starter.common.lang.util.NutMap;
import com.yunqi.starter.common.page.Pagination;

/**
 * 分页查询接收参数模型,配合@RequestBody使用
 * Created by @author CHQ on 2022/8/6
 */
public class QueryBody extends NutMap {

    private static final long serialVersionUID = -1;

    /**
     * 获取页码
     * @return 当前页码
     */
    public int page() {
        return getInt("page", 0);
    }

    /**
     * 获取每页几条数据
     * @return 每页几条数据
     */
    public int pageSize() {
        return getInt("pageSize", Pagination.DEFAULT_PAGE_SIZE);
    }


}
