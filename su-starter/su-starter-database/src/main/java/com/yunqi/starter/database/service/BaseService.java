package com.yunqi.starter.database.service;

import com.yunqi.starter.common.page.Pagination;
import org.nutz.dao.Chain;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.dao.FieldFilter;
import org.nutz.dao.entity.Record;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Sql;
import org.nutz.lang.util.NutMap;

import java.util.List;
import java.util.Map;

/**
 * Service接口
 * Created by @author JsckChin on 2022/1/29
 */
public interface BaseService<T>{

    /**
     * 获取dao实例
     * @return dao实例
     */
    Dao dao();

    /**
     * 获取泛型类型class
     *
     * @return 泛型class
     */
    Class<T> getEntityClass();

    /**
     * 统计对象表条数
     *
     * @return  数量
     */
    int count();

    /**
     * 统计符合条件的对象表条数
     *
     * @param cnd WHERE 条件
     * @return    数量
     */
    int count(Condition cnd);

    /**
     * 统计表记录条数
     *
     * @param tableName     表名
     * @return              数量
     */
    int count(String tableName);


    /**
     * 统计符合条件的记录条数
     *
     * @param tableName     表名
     * @param cnd           WHERE 条件
     * @return              数量
     */
    int count(String tableName, Condition cnd);


    /**
     * 自定义SQL统计
     *
     * @param sql   自定义SQL对象
     * @return      将结果对象作为 int 返回
     */
    int count(Sql sql);

    /**
     * 通过数字型主键查询对象
     *
     * @param id    对象 ID
     * @return      对象
     */
    T fetch(long id);

    /**
     * 通过字符型主键查询对象
     *
     * @param id    对象 ID
     * @return      对象
     */
    T fetch(String id);

    /**
     * 查出符合条件的第一条记录
     *
     * @param cnd 查询条件
     * @return 实体, 如不存在则为null
     */
    T fetch(Condition cnd);

    /**
     * 查询关联表
     *
     * @param obj   数据对象,可以是普通对象或集合,但不是类
     * @param regex 为null查询全部,支持通配符 ^(a|b)$
     * @return      更新后的数据对象本身
     */
    <T> T fetchLinks(T obj, String regex);

    /**
     * 查询关联表
     *
     * @param obj   数据对象,可以是普通对象或集合,但不是类
     * @param regex 为null查询全部,支持通配符 ^(a|b)$
     * @param cnd   关联字段的过滤(排序,条件语句,分页等)
     * @return      传入的数据对象
     */
    <T> T fetchLinks(T obj, String regex, Condition cnd);


    /**
     * 复合主键专用
     *
     * @param pks 键值
     * @return 对象 T
     */
    T fetchx(Object... pks);

    /**
     * 复合主键专用
     *
     * @param pks 键值
     * @return 对象 T
     */
    boolean exists(Object... pks);

    /**
     * 将一个对象插入到一个数据库
     *
     * @param obj 要被插入的对象
     *            它可以是：
     *            普通 POJO
     *            集合
     *            数组
     *            Map
     *            注意：如果是集合，数组或者 Map，所有的对象必须类型相同，否则可能会出错
     * @return 插入后的对象
     */
    <T> T insert(T obj);

    /**
     * 将一个对象按FieldFilter过滤后,插入到一个数据源。
     * <p>
     * <code>dao.insert(pet, FieldFilter.create(Pet.class, FieldMatcher.create(false)));</code>
     *
     * @param obj    要被插入的对象
     * @param filter 字段过滤器, 其中FieldMatcher.isIgnoreId生效
     * @return 插入后的对象
     * @see Dao#insert(Object)
     */
    <T> T insert(T obj, FieldFilter filter);

    /**
     * 自由的向一个数据表插入一条数据
     *
     * @param tableName 表名
     * @param chain     数据名值链
     */
    void insert(String tableName, Chain chain);

    /**
     * 根据对象的主键(@Id/@Name/@Pk)先查询, 如果存在就更新, 不存在就插入
     *
     * @param obj 对象
     * @return 原对象
     */
    <T> T insertOrUpdate(T obj);

    /**
     * 根据对象的主键(@Id/@Name/@Pk)先查询, 如果存在就更新, 不存在就插入
     *
     * @param obj               对象
     * @param insertFieldFilter 插入时的字段过滤, 可以是null
     * @param updateFieldFilter 更新时的字段过滤,可以是null
     * @return 原对象
     */
    <T> T insertOrUpdate(T obj, FieldFilter insertFieldFilter, FieldFilter updateFieldFilter);


    /**
     * 快速插入一个对象,对象的 '@Prev' 以及 '@Next' 在这个函数里不起作用
     *
     * @param obj
     * @return
     */
    <T> T fastInsert(T obj);

    /**
     * 将对象插入数据库同时，也将符合一个正则表达式的所有关联字段关联的对象统统插入相应的数据库
     * <p>
     * 关于关联字段更多信息，请参看 '@One' | '@Many' | '@ManyMany' 更多的描述
     *
     * @param obj   数据对象
     * @param regex 正则表达式，描述了什么样的关联字段将被关注。如果为 null，则表示全部的关联字段都会被插入
     * @return 数据对象本身
     * @see org.nutz.dao.entity.annotation.One
     * @see org.nutz.dao.entity.annotation.Many
     * @see org.nutz.dao.entity.annotation.ManyMany
     */
    <T> T insertWith(T obj, String regex);

    /**
     * 根据一个正则表达式，仅将对象所有的关联字段插入到数据库中，并不包括对象本身
     *
     * @param obj   数据对象
     * @param regex 正则表达式，描述了什么样的关联字段将被关注。如果为 null，则表示全部的关联字段都会被插入
     * @return 数据对象本身
     * @see org.nutz.dao.entity.annotation.One
     * @see org.nutz.dao.entity.annotation.Many
     * @see org.nutz.dao.entity.annotation.ManyMany
     */
    <T> T insertLinks(T obj, String regex);

    /**
     * 将对象的一个或者多个，多对多的关联信息，插入数据表
     *
     * @param obj   对象
     * @param regex 正则表达式，描述了那种多对多关联字段将被执行该操作
     * @return 对象自身
     * @see org.nutz.dao.entity.annotation.ManyMany
     */
    <T> T insertRelation(T obj, String regex);

    /**
     * 更新数据
     *
     * @param obj   要被更新的对象
     * @return      返回实际被更新的记录条数，一般的情况下，如果更新成功，返回 1，否则，返回 0
     */
    int update(Object obj);

    /**
     * 更新数据忽略值为null的字段
     *
     * @param obj   要被更新的对象
     * @return      返回实际被更新的记录条数，一般的情况下，如果是单一Pojo,更新成功，返回 1，否则，返回 0
     */
    int updateIgnoreNull(Object obj);

    /**
     * 部分更新实体表
     *
     * @param chain     数据名值链
     * @param cnd       WHERE 条件
     * @return          有多少条记录被更新了
     */
    int update(Chain chain, Condition cnd);

    /**
     * 部分更新表
     *
     * @param tableName 数据表名
     * @param chain     数据名值链
     * @param cnd       WHERE 条件
     * @return          有多少条记录被更新了
     */
    int update(String tableName, Chain chain, Condition cnd);

    /**
     * 将对象更新的同时，也将符合一个正则表达式的所有关联字段关联的对象统统更新
     * <p>
     * 关于关联字段更多信息，请参看 '@One' | '@Many' | '@ManyMany' 更多的描述
     *
     * @param obj   数据对象
     * @param regex 正则表达式，描述了什么样的关联字段将被关注。如果为 null，则表示全部的关联字段都会被更新
     * @return 数据对象本身
     * @see org.nutz.dao.entity.annotation.One
     * @see org.nutz.dao.entity.annotation.Many
     * @see org.nutz.dao.entity.annotation.ManyMany
     */
    <T> T updateWith(T obj, String regex);

    /**
     * 根据一个正则表达式，仅更新对象所有的关联字段，并不包括对象本身
     *
     * @param obj   数据对象
     * @param regex 正则表达式，描述了什么样的关联字段将被关注。如果为 null，则表示全部的关联字段都会被更新
     * @return 数据对象本身
     * @see org.nutz.dao.entity.annotation.One
     * @see org.nutz.dao.entity.annotation.Many
     * @see org.nutz.dao.entity.annotation.ManyMany
     */
    <T> T updateLinks(T obj, String regex);

    /**
     * 多对多关联是通过一个中间表将两条数据表记录关联起来。
     * <p>
     * 而这个中间表可能还有其他的字段，比如描述关联的权重等
     * <p>
     * 这个操作可以让你一次更新某一个对象中多个多对多关联的数据
     *
     * @param classOfT 对象类型
     * @param regex    正则表达式，描述了那种多对多关联字段将被执行该操作
     * @param chain    针对中间关联表的名值链。
     * @param cnd      针对中间关联表的 WHERE 条件
     * @return 共有多少条数据被更新
     * @see org.nutz.dao.entity.annotation.ManyMany
     */
    int updateRelation(Class<?> classOfT, String regex, Chain chain, Condition cnd);

    /**
     * 基于版本的更新，版本不一样无法更新到数据
     *
     * @param obj 需要更新的对象, 必须有version属性
     * @return 若更新成功, 大于0, 否则小于0
     */
    int updateWithVersion(Object obj);

    /**
     * 基于版本的更新，版本不一样无法更新到数据
     *
     * @param obj    需要更新的对象, 必须有version属性
     * @param filter 需要过滤的字段设置
     * @return 若更新成功, 大于0, 否则小于0
     */
    int updateWithVersion(Object obj, FieldFilter filter);

    /**
     * 乐观锁, 以特定字段的值作为限制条件,更新对象,并自增该字段.
     * <p>
     * 执行的sql如下:
     * <p>
     * <code>update t_user set age=30, city="广州", version=version+1 where name="wendal" and version=124;</code>
     *
     * @param obj         需要更新的对象, 必须带@Id/@Name/@Pk中的其中一种.
     * @param fieldFilter 需要过滤的属性. 若设置了哪些字段不更新,那务必确保过滤掉fieldName的字段
     * @param fieldName   参考字段的Java属性名.默认是"version",可以是任意数值字段
     * @return 若更新成功, 返回值大于0, 否则小于等于0
     */
    int updateAndIncrIfMatch(Object obj, FieldFilter fieldFilter, String fieldName);


    /**
     * 获取某个对象,最大的 ID 值,这个对象必须声明了 '@Id'
     *
     * @return 最大 ID 值
     */
    int getMaxId();

    /**
     * 通过long主键删除数据
     *
     * @param id    对象 ID
     * @return      影响的行数
     */
    int delete(long id);

    /**
     * 通过int主键删除数据
     *
     * @param id    对象 ID
     * @return      影响的行数
     */
    int delete(int id);

    /**
     * 通过string主键删除数据
     *
     * @param id    对象 ID
     * @return      影响的行数
     */
    int delete(String id);

    /**
     * 批量删除
     *
     * @param ids ID数组
     */
    void delete(Integer[] ids);

    /**
     * 批量删除
     *
     * @param ids ID数组
     */
    void delete(Long[] ids);

    /**
     * 批量删除
     *
     * @param ids ID数组
     */
    void delete(String[] ids);

    /**
     * 批量删除
     *
     * @param ids ID数组
     */
    void delete(List<String> ids);

    /**
     * 清空表
     *
     * @return 影响的行数
     */
    int clear();

    /**
     * 清空表
     * @param tableName 表名
     * @return          影响的行数
     */
    int clear(String tableName);

    /**
     * 按条件清除一组数据
     * @param cnd       查询条件
     * @return          影响的行数
     */
    int clear(Condition cnd);

    /**
     * 按条件清除一组数据
     * @param tableName 表名
     * @param cnd       查询条件
     * @return          影响的行数
     */
    int clear(String tableName, Condition cnd);


    /**
     * 伪删除
     *
     * @param id 对象ID
     * @return   影响的行数
     */
    int vDelete(String id);

    /**
     * 批量伪删除
     *
     * @param ids   对象ID数组
     * @return      影响的行数
     */
    int vDelete(String[] ids);

    /**
     * 批量伪删除
     *
     * @param ids   对象ID数组
     * @return      影响的行数
     */
    int vDelete(List<String> ids);

    /**
     * 根据条件进行伪删除
     *
     * @param cnd   WHERE 条件
     * @return      影响的行数
     */
    int vDelete(Condition cnd);

    /**
     * 根据条件进行伪删除
     * @param tableName 表名
     * @param cnd   WHERE 条件
     * @return      影响的行数
     */
    int vDelete(String tableName, Condition cnd);



    /**
     * 通过LONG主键获取部分字段值
     *
     * @param fieldName 获取部分字段值,支持通配符 ^(a|b)$
     * @param id        对象ID
     * @return          对象
     */
    T getField(String fieldName, long id);

    /**
     * 通过INT主键获取部分字段值
     *
     * @param fieldName     获取部分字段值,支持通配符 ^(a|b)$
     * @param id            对象ID
     * @return              对象
     */
    T getField(String fieldName, int id);

    /**
     * 通过NAME主键获取部分字段值
     *
     * @param fieldName 获取部分字段值,支持通配符 ^(a|b)$
     * @param name      对象 Name
     * @return          对象
     */
    T getField(String fieldName, String name);

    /**
     * 通过条件获取部分字段值
     *
     * @param fieldName 获取部分字段值,支持通配符 ^(a|b)$
     * @param cnd       WHERE 条件
     * @return          对象
     */
    T getField(String fieldName, Condition cnd);

    /**
     * 获取全部数据
     *
     * @return      对象列表
     */
    List<T> query();

    /**
     * 查询一组对象。你可以为这次查询设定条件
     *
     * @param cnd WHERE 条件。如果为 null，将获取全部数据，顺序为数据库原生顺序<br>
     *            只有在调用这个函数的时候， cnd.limit 才会生效
     * @return 对象列表
     */
    List<T> query(Condition cnd);


    /**
     * 查询获取部分字段
     *
     * @param fieldName 获取部分字段,支持通配符 ^(a|b)$
     * @param cnd       WHERE 条件
     * @return          对象列表
     */
    List<T> query(String fieldName, Condition cnd);


    /**
     * 获取表及关联表全部数据
     *
     * @param cnd       查询条件
     * @param linkName  关联字段，支持正则 ^(a|b)$
     * @return          对象列表
     */
    List<T> query(Condition cnd, String linkName);

    /**
     * 获取表及关联表全部数据(支持子查询)
     *
     * @param cnd       查询条件
     * @param linkName  关联字段，支持正则 ^(a|b)$
     * @param linkCnd   关联条件
     * @return          对象列表
     */
    List<T> query(Condition cnd, String linkName, Condition linkCnd);

    /**
     * 获取表及关联表全部数据
     *
     * @param linkName     关联字段，支持正则 ^(a|b)$
     * @return             关联条件
     */
    List<T> query(String linkName);

    /**
     * 分页关联字段查询
     *
     * @param cnd      查询条件
     * @param linkName 关联字段，支持正则 ^(a|b)$
     * @param pager    分页对象
     * @return         对象列表
     */
    List<T> query(Condition cnd, String linkName, Pager pager);

    /**
     * 分页关联字段查询(支持关联条件)
     *
     * @param cnd      查询条件
     * @param linkName 关联字段，支持正则 ^(a|b)$
     * @param linkCnd  关联条件
     * @param pager    分页对象
     * @return         对象列表
     */
    List<T> query(Condition cnd, String linkName, Condition linkCnd, Pager pager);

    /**
     * 分页查询
     *
     * @param cnd   查询条件
     * @param pager 分页对象
     * @return      对象列表
     */
    List<T> query(Condition cnd, Pager pager);

    /**
     * 查询获取NutMap对象
     *
     * @param keyColumnName   作为key的字段名
     * @param valueColumnName 作为value的字段名
     * @param cnd             查询条件
     * @return                对象列表
     */
    NutMap query(String keyColumnName, String valueColumnName, Condition cnd);

    /**
     * 查询获取NutMap对象
     *
     * @param tableName       表名
     * @param keyColumnName   作为key的字段名
     * @param valueColumnName 作为value的字段名
     * @param cnd             查询条件
     * @return                对象列表
     */
    NutMap query(String tableName, String keyColumnName, String valueColumnName, Condition cnd);

    /**
     * 执行自定义SQL
     *
     * @param sql   待执行sql
     * @return      执行后的sql对象
     */
    Sql execute(Sql sql);

    /**
     * 自定义SQL返回Record记录集，Record是个MAP但不区分大小写
     * 别返回Map对象，因为MySql和Oracle中字段名有大小写之分
     *
     * @param sql   待执行sql
     * @return      对象列表
     */
    List<Record> list(Sql sql);

    /**
     * 自定义查询,并返回当前实体类对象
     *
     * @param sql   待执行sql
     * @return      对象列表
     */
    List<T> listEntity(Sql sql);

    /**
     * 自定义sql获取map key-value
     *
     * @param sql   待执行sql
     * @return      对象
     */
    Map getMap(Sql sql);


    /**
     * 自定义sql获取NutMap key-value
     *
     * @param sql   待执行sql
     * @return      对象
     */
    NutMap getNutMap(Sql sql);

    /**
     * 分页查询
     *
     * @param cnd        查询条件
     * @param pagination 分页对象
     * @return           对象列表
     */
    List<T> query(Condition cnd, Pagination<T> pagination);

    /**
     * 分页查询
     *
     * @param tableName     表名
     * @param cnd           查询条件
     * @param pagination    分页对象
     * @return              分页对象列表
     */
    List<Record> query(String tableName, Condition cnd, Pagination<T> pagination);

    /**
     * 分页查询(cnd)
     *
     * @param page      页码
     * @param pageSize  页面大小
     * @param cnd       查询条件
     * @return          分页对象列表
     */
    Pagination<T> listPage(Integer page, int pageSize, Condition cnd);


    /**
     * 分页查询
     *
     * @param page      页码
     * @param cnd       查询条件
     * @return          分页对象列表
     */
    Pagination<T> listPage(Integer page, Condition cnd);

    /**
     * 分页查询
     *
     * @param page          页码
     * @param pageSize      页面大小
     * @param tableName     表名
     * @param cnd           查询条件
     * @return              分页对象列表
     */
    Pagination<Record> listPage(Integer page, int pageSize, String tableName, Condition cnd);

    /**
     * 分页关联查询
     * @param page          页码
     * @param pageSize      页面大小
     * @param cnd           查询条件
     * @param linkName      关联字段,支持通配符 ^(a|b)$
     * @param subCnd        子查询条件
     * @return              分页对象列表
     */
    Pagination<T> listPageLinks(Integer page, int pageSize, Condition cnd, String linkName, Condition subCnd);

    /**
     * 分页关联查询
     * @param page          页码
     * @param pageSize      页面大小
     * @param cnd           查询条件
     * @param linkName      关联字段,支持通配符 ^(a|b)$
     * @return              分页对象列表
     */
    Pagination<T> listPageLinks(Integer page, int pageSize, Condition cnd, String linkName);

    /**
     * 分页查询,获取部分字段
     *
     * @param page          页码
     * @param pageSize      页面大小
     * @param cnd           查询条件
     * @param fieldName     查询字段,支持通配符 ^(a|b)$
     * @return              分页对象列表
     */
    Pagination<T> listPage(Integer page, int pageSize, Condition cnd, String fieldName);

    /**
     * 分页查询(sql)
     * @param page          页码
     * @param pageSize      页面大小
     * @param sql           待执行sql
     * @return              分页对象列表
     */
    Pagination<Record> listPage(Integer page, int pageSize, Sql sql);


    /**
     * 分页查询(sql)
     * @param page          页码
     * @param sql           待执行sql
     * @return              分页对象列表
     */
    Pagination<Record> listPage(Integer page, Sql sql);

    /**
     * 分页查询(sql)
     *
     * @param page          页码
     * @param pageSize      页面大小
     * @param sql           待执行sql
     * @return              分页对象列表
     */
    Pagination<Map>  listPageMap(Integer page, int pageSize, Sql sql);

    /**
     * 分页查询(sql)
     *
     * @param page          页码
     * @param pageSize      页面大小
     * @param sql           待执行sql
     * @param countSql      统计语句
     * @return              分页对象列表
     */
    Pagination<Map> listPageMap(Integer page, int pageSize, Sql sql, Sql countSql);


    /**
     * 分页查询(sql)
     *
     * @param page          页码
     * @param sql           待执行sql
     * @return              分页对象列表
     */
    Pagination<Map>  listPageMap(Integer page,  Sql sql);

    /**
     * 分页查询(sql)
     *
     * @param page          页码
     * @param sql           待执行sql
     * @param countSql      统计语句
     * @return              分页对象列表
     */
    Pagination<Map> listPageMap(Integer page, Sql sql, Sql countSql);

    /**
     * @param page       页码
     * @param pageSize   页面大小
     * @param sql        查询语句
     * @param countSql   统计语句
     * @return           分页对象列表
     */
    Pagination<Record> listPage(Integer page, int pageSize, Sql sql, Sql countSql);

    /**
     * @param page       页码
     * @param sql        查询语句
     * @param countSql   统计语句
     * @return           分页对象列表
     */
    Pagination<Record> listPage(Integer page,  Sql sql, Sql countSql);
}
