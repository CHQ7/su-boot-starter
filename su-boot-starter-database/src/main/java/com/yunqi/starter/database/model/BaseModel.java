package com.yunqi.starter.database.model;

import com.yunqi.starter.common.lang.Strings;
import com.yunqi.starter.security.utils.SecuritySessionUtil;
import lombok.Data;
import org.nutz.dao.entity.annotation.*;
import org.nutz.dao.interceptor.annotation.PrevInsert;
import org.nutz.dao.interceptor.annotation.PrevUpdate;

import java.io.Serializable;

/**
 * 基础模型
 * Created by @author CHQ on 2022/1/28
 */
@Data
public abstract class BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column
    @Comment("创建ID")
    @PrevInsert(els = @EL("$me.createdByUid()"))
    @ColDefine(type = ColType.VARCHAR, width = 32)
    private String createdById;

    @Column
    @Comment("创建人")
    @PrevInsert(els = @EL("$me.createdBy()"))
    @ColDefine(type = ColType.VARCHAR, width = 100)
    private String createdBy;

    @Column
    @Comment("创建时间")
    @PrevInsert(now = true)
    private Long  createdAt;

    @Column
    @Comment("修改人ID")
    @PrevInsert(els = @EL("$me.updatedByUid()"))
    @PrevUpdate(els = @EL("$me.updatedByUid()"))
    @ColDefine(type = ColType.VARCHAR, width = 32)
    private String updatedById;

    @Column
    @Comment("修改人")
    @PrevInsert(els = @EL("$me.updatedBy()"))
    @PrevUpdate(els = @EL("$me.updatedBy()"))
    @ColDefine(type = ColType.VARCHAR, width = 100)
    private String updatedBy;

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
        return this.toString();
    }

    public Boolean flag() {
        return false;
    }

    /**
     * 获取创建人ID
     */
    public String createdByUid() {
        return getUidString(getCreatedById());
    }

    /**
     * 获取创建人
     */
    public String createdBy() {
        return getUserNickname(getCreatedBy());
    }

    /**
     * 获取修改ID
     */
    public String updatedByUid() {
        return getUidString(getUpdatedById());
    }

    /**
     * 获取修改人
     */
    public String updatedBy() {
        return getUserNickname(getUpdatedBy());
    }


    /**
     * 获取操作用户ID
     * @param uid       用户ID
     * @return          用户ID
     */
    private String getUidString(String uid) {
        // 获取Session中的用户ID
        if (Strings.isNotBlank(SecuritySessionUtil.getUserId())) {
            return SecuritySessionUtil.getUserId();
        }

        // 如果Session中的用户ID不存在,则验证记录是否存在用户ID
        if (Strings.isNotBlank(uid)) {
            return uid;
        }
        return "";
    }

    /**
     * 获取操作用户名
     * @param nickname  用户名
     * @return          用户名
     */
    private String getUserNickname(String nickname) {
        // 获取Session中的用户名
        if (Strings.isNotBlank(SecuritySessionUtil.getUserNickname())) {
            return SecuritySessionUtil.getUserNickname();
        }

        // 如果Session中的用户名不存在,则验证记录是否存在用户名
        if (Strings.isNotBlank(nickname)) {
            return nickname;
        }
        return "";
    }

}
