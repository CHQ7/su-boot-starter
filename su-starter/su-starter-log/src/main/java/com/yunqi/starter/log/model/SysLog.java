package com.yunqi.starter.log.model;

import lombok.Data;
import org.nutz.dao.entity.annotation.*;

import java.io.Serializable;

/**
 * 系统日志
 * Created by @author JsckChin on 2021/12/19
 */
@Table("ims_sys_log")
@Data
public class SysLog implements Serializable{

	private static final long serialVersionUID = 1L;

	@Column
	@Name
	@ColDefine(type = ColType.VARCHAR, width = 32)
	@Prev(els = {@EL("uuid()")})
	private String id;

    @Column
    @Comment("日志标签")
    @ColDefine(type = ColType.VARCHAR, width = 500)
    private String tag;

    @Column
    @Comment("业务类型")
    @ColDefine(type = ColType.TEXT)
    private String msg;

    @Column
    @Comment("请求地址")
    @ColDefine(type = ColType.VARCHAR, width = 500)
    private String url;

    @Column
    @Comment("请求方式")
    @ColDefine(type = ColType.VARCHAR, width = 500)
    private String method;

    @Column
    @Comment("执行类")
    @ColDefine(type = ColType.VARCHAR, width = 500)
    private String src;

    @Column
    @Comment("IP地址")
    @ColDefine(type = ColType.VARCHAR, width = 255)
    private String ip;

    @Column
    @Comment("IP地址")
    @ColDefine(type = ColType.VARCHAR, width = 255)
    private String location;

    @Column
    @Comment("浏览器类型")
    @ColDefine(type = ColType.VARCHAR, width = 500)
    private String browser;

    @Column
    @Comment("操作系统")
    @ColDefine(type = ColType.VARCHAR, width = 500)
    private String os;

    @Column
    @Comment("请求参数")
    @ColDefine(type = ColType.TEXT)
    private String param;

    @Column
    @Comment("执行结果")
    @ColDefine(type = ColType.TEXT)
    private String result;

    @Column
    @Comment("操作状态 （0正常 1失败）")
    @ColDefine(type = ColType.INT)
    @Default(value = "0")
    private Integer status;

    @Column
    @Comment("处理耗时")
    @Default(value = "0")
    private Long executeTime;

    @Column
    @Comment("创建人ID")
    @ColDefine(type = ColType.VARCHAR, width = 32)
    private String createdById;

    @Column
    @Comment("创建人")
    @ColDefine(type = ColType.VARCHAR, width = 100)
    private String createdBy;

    @Column
    @Comment("创建时间")
    private Long  createdAt;

    @Column
    @Comment("修改人ID")
    @ColDefine(type = ColType.VARCHAR, width = 32)
    protected String updatedById;

    @Column
    @Comment("修改人")
    @ColDefine(type = ColType.VARCHAR, width = 100)
    protected String updatedBy;

    @Column
    @Comment("修改时间")
    private Long updatedAt;

    @Column
    @Comment("删除标记")
    @ColDefine(type = ColType.BOOLEAN)
    private Boolean delFlag;

}
