package com.yunqi.starter.database.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by @author CHQ on 2022/1/29
 */
@Getter
@Setter
@ConfigurationProperties(prefix = DataBaseProperties.PREFIX)
public class DataBaseProperties {

    public static final String PREFIX = "su.database";

    /** 是否开启 */
    boolean enabled = true;

    /** 是否打印操作日志 */
    private Boolean log = false;

    /**
     * 运行期配置
     */
    private Runtime runtime = new Runtime();

    /**
     * sql管理器配置
     */
    private SqlManager sqlManager = new SqlManager();

    /**
     * sql 模板处理插件配置
     */
    private SqlTemplate sqlTemplate = new SqlTemplate();

    /**
     * Interceptor
     */
    private Interceptor interceptor = new Interceptor();

    /**
     * dao的全局设置
     */
    private Global global = new Global();

    @Getter
    @Setter
    public static class Global {
        /** 是否检查字段为数据库的关键字 */
        private boolean checkColumnNameKeyword = false;

        /** 是否把字段名用字符包裹来进行关键字逃逸 */
        private boolean forceWrapColumnName = false;

        /** 是否把字段名给变成大写 */
        private boolean forceUpperColumnName = false;

        private boolean forceHumpColumnName = false;

        /** varchar 字段的默认字段长度 */
        private int defaultVarcharWidth = 128;
    }

    @Getter
    @Setter
    public static class SqlTemplate {

        /** 是否启用标识 */
        boolean enable = false;

        /** 模版引擎类型 */
        Type type = Type.BEETL;

        public enum Type {
            BEETL, FREEMARKER, JETBRICK, VELOCITY
        }
    }

    @Getter
    @Setter
    public static class Interceptor {
        /**  sql 记时 */
        boolean time = true;
    }

    @Getter
    @Setter
    public static class SqlManager {
        /** 模式 */
        private Mode mode;

        /**  路径列表 */
        private String[] paths;

        public enum Mode {
            FILE, XML
        }
    }

    @Getter
    @Setter
    public static class Runtime {
        /** 自动建表 */
        private boolean create = true;

        /** 自动变更 */
        private boolean migration = true;

        /** 实体包名 */
        private String[] basepackage;

        /** 强制创建<删表重建> */
        private boolean foceCreate = false;

        /** 是否增加列  */
        private boolean addColumn = true;

        /** 是否删除列 */
        private boolean deleteColumn = false;

        /** 检查索引 */
        private boolean checkIndex = false;
    }
}
