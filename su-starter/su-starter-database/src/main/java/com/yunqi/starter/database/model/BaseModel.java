package com.yunqi.starter.database.model;

import lombok.Data;
import org.nutz.dao.entity.annotation.*;
import org.nutz.dao.interceptor.annotation.PrevInsert;
import org.nutz.dao.interceptor.annotation.PrevUpdate;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;

import java.io.Serializable;

/**
 * 基础模型
 * Created by @author JsckChin on 2022/1/28
 */
@Data
public abstract class BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column
    @Comment("创建ID")
    @ColDefine(type = ColType.VARCHAR, width = 32)
    private String createdById;

    @Column
    @Comment("创建人")
    @ColDefine(type = ColType.VARCHAR, width = 100)
    private String createdBy;

    @Column
    @Comment("创建时间")
    @PrevInsert(now = true)
    private Long  createdAt;

    @Column
    @Comment("修改ID")
    @ColDefine(type = ColType.VARCHAR, width = 32)
    protected String updatedById;

    @Column
    @Comment("修改人")
    @ColDefine(type = ColType.VARCHAR, width = 100)
    protected String updatedBy;

    @Column
    @Comment("修改时间")
    @PrevInsert(now = true)
    @PrevUpdate(now = true)
    private Long updatedAt;

    @Column
    @Comment("删除标记")
    @PrevInsert(els = @EL("$me.flag()"))
    @ColDefine(type = ColType.BOOLEAN)
    private Boolean delFlag;

    @Override
    public String toString() {
        return Json.toJson(this, JsonFormat.compact());
    }

    public Boolean flag() {
        return false;
    }

}
