package com.yunqi.starter.security.configuration;

import cn.dev33.satoken.config.SaTokenConfig;
import cn.dev33.satoken.spring.SaBeanRegister;
import cn.dev33.satoken.stp.StpLogic;
import com.yunqi.starter.common.json.Json;
import com.yunqi.starter.security.spi.UserSecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Created by @author CHQ on 2022/2/16
 */
@Slf4j
@Configuration
@ConditionalOnClass(SaTokenConfig.class)
@ConditionalOnExpression("${su.security.user.enabled:true}")
@EnableConfigurationProperties(UserSecurityProperties.class)
@AutoConfigureAfter({SaBeanRegister.class})
public class UserSecurityAutoConfiguration {

    // 安全属性对象
    private final UserSecurityProperties properties;
    public UserSecurityAutoConfiguration(UserSecurityProperties properties) {
        this.properties = properties;
    }

    @Bean
    public void UserSecurityUtil() {
        // 获取配置
        SaTokenConfig config = new SaTokenConfig();
        BeanUtils.copyProperties(properties, config);

        if(config.getIsLog()){
            log.info("打印 -> su.security.user 配置:\n{}", Json.toJson(config));
        }


        // 重写 stpLogic 配置获取方法
        StpLogic stpLogic =  new StpLogic(UserSecurityUtil.TYPE) {
            @Override
            public SaTokenConfig  getConfig() {
                return config;
            }
        };
        UserSecurityUtil.setStpLogic(stpLogic);
    }

}
