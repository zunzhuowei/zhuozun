package com.qs.game.swagger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zun.wei on 2018/8/19.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
@Primary
@Component
public class DocumentationConfig implements SwaggerResourcesProvider {

    private static final String VERSION = "2.0";
    private static final String SUFIX = "/v2/api-docs";

    @Value("${zuul.prefix}")
    public Object prefix;


    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        Api[] apis = Api.values();
        Arrays.asList(apis).forEach(e ->
                resources.add(swaggerResource(e.NAME, prefix + e.API + SUFIX, VERSION)));
        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location, String version) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }

}
