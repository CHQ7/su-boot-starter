package com.yunqi.starter.database.configuration;

import org.nutz.resource.NutResource;
import org.nutz.resource.impl.ResourceLocation;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 资源定位
 * Created by @author JsckChin on 2022/1/29
 */
public class SpringResourceLoaction extends ResourceLocation implements ApplicationContextAware {

    protected ApplicationContext applicationContext;

    @Override
    public String id() {
        return "spring";
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public void scan(String base, Pattern pattern, List<NutResource> list) {
        base = pattern.matcher(base).find() ? "classpath*:" + base : "classpath*:" + base + "/**";
        try {
            Resource[] tmp = applicationContext.getResources(base);
            for (Resource resource : tmp) {
                if (resource.getFilename() == null || !pattern.matcher(resource.getFilename()).find())
                    continue;
                SpringResource sr = new SpringResource();
                sr.resource = resource;
                sr.setName(resource.getFilename());
                sr.setSource("spring");
                list.add(sr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    public class SpringResource extends NutResource {

        protected Resource resource;

        @Override
        public InputStream getInputStream() throws IOException {
            return resource.getInputStream();
        }

        /* (non-Javadoc)
         * @see org.nutz.resource.NutResource#equals(java.lang.Object)
         */
        @Override
        public boolean equals(Object obj) {
            return super.equals(obj);
        }

        /* (non-Javadoc)
         * @see org.nutz.resource.NutResource#hashCode()
         */
        @Override
        public int hashCode() {
            return super.hashCode();
        }

    }
}
