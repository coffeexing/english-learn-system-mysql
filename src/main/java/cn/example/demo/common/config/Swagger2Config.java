package cn.example.demo.common.config;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * <p>
 * Swagger2 配置类
 * </p>
 *
 * @author Lizuxian
 * @create 2020/7/13 14:15
 */
@Profile(value = {"dev", "dev.oracle", "multi.source"})
@EnableSwagger2
@Configuration
public class Swagger2Config {
    /**
     * <p>
     * WebApi 摘要信息
     * </P>
     *
     * @return Docket
     */
    @Bean
    public Docket webApi() {
        Predicate<RequestHandler> selector1 = RequestHandlerSelectors.basePackage("cn.example.demo.common.secure.controller");
        Predicate<RequestHandler> selector2 = RequestHandlerSelectors.basePackage("cn.example.demo.common.retrieval.controller");
        Predicate<RequestHandler> selector3 = RequestHandlerSelectors.basePackage("cn.example.demo.modules.sys.controller");
        Predicate<RequestHandler> selector4 = RequestHandlerSelectors.basePackage("cn.example.demo.modules.english.controller");

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("标题：【后台管理系统】- WebAPI文档")
                        .description("描述：\n\t1）首页；\n\t2）查询模块；")
                        .contact(new Contact("Author", null, null))
                        .version("版本号：1.0").build())
                .select()
                .apis(Predicates.or(selector1, selector2, selector3, selector4))
                .paths(PathSelectors.any()).build();
    }
}
