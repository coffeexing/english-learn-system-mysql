package cn.example.demo.common.config.json;

import cn.example.demo.common.tools.obj.DateAgeUtils;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * Json 序列化与反序列化自定义策略配置类
 * </p>
 *
 * @author Lizuxian
 * @create 2020/7/9 10:10
 */
@Configuration
public class JsonConverterConfig {
    /**
     * <p>
     * 日期类型序列化，定义格式与时区。（重写@RequsetBody 的默认策略）
     * </P>
     *
     * @return
     */
    @Bean
    public ObjectMapper dateTypeSerializerObjectMapper() {
        ObjectMapper om = new ObjectMapper();
        SimpleModule simpleModule = new SimpleModule();

        simpleModule.addSerializer(Date.class, new JsonSerializer<Date>() {
            @Override
            public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                gen.writeString(DateAgeUtils.dateToString(value, "yyyy-MM-dd HH:mm:ss"));
            }
        });
        simpleModule.addDeserializer(Date.class, new JsonDeserializer<Date>() {
            @Override
            public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
                return DateAgeUtils.stringToDate(jsonParser.getValueAsString());
            }
        });

        om.registerModule(simpleModule);
        return om;
    }

    /**
     * <p>
     * JSON序列化器-货币格式
     * </P>
     * <type>BigDecimal</type>
     *
     * @return
     */
    @Bean
    public JsonSerializer<BigDecimal> bigDecimalJsonSerializer() {
        return new MonetaryFormatSerializer();
    }
}
