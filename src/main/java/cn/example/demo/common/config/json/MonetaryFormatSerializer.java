package cn.example.demo.common.config.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * <p>
 * JSON序列化器-货币格式
 * </P>
 * <type>BigDecimal</type>
 *
 * @author Lizuxian
 * @create 2020-12-23 16:25
 */
public class MonetaryFormatSerializer extends JsonSerializer<BigDecimal> {
    DecimalFormat decimalFormat = new DecimalFormat();

    @Override
    public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value == null ? null : decimalFormat.format(value.doubleValue()));
    }
}
