package com.yunqi.starter.log.enums;

/**
 * 日志类型
 * Created by @author JsckChin on 2022/2/16
 */
public enum LogType {
    OTHER("OTHER", "其它"),
    INSERT("INSERT","新增"),
    UPDATE("UPDATE", "修改"),
    DELETE("DELETE", "删除"),
    SORT("SORT", "排序"),
    CLEAN("CLEAN", "清空数据"),
    STATUS("STATUS", "状态"),
    EXECUTE("EXECUTE", "执行"),
    EXPORT("EXPORT", "导出"),
    IMPORT("IMPORT", "导入"),
    FORCE("FORCE", "强退"),
    GENCODE("GENCODE", "生成代码"),
    ;

    private String value;
    private String label;


    LogType(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public static LogType from(String value) {
        for (LogType t : values()) {
            if (t.value.equals(value)) {
                return t;
            }
        }
        throw new IllegalArgumentException("unknown LogType: " + value);
    }
}
