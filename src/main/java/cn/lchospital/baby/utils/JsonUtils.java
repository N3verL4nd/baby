package cn.lchospital.baby.utils;

import cn.lchospital.baby.exception.JsonDeserializedException;
import cn.lchospital.baby.exception.JsonSerializedException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.lang3.SerializationUtils;

import java.io.IOException;

public class JsonUtils {

    private static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        // 序列化时，忽略值为null的属性
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // 序列化时，忽略空的bean(即沒有任何Field)
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        // 反序列化时，忽略在JSON字符串中存在但Java对象实际没有的属性
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        // make all member fields serializable without further annotations,
        // instead of just public fields (default setting).
        // mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
    }

    public ObjectMapper getObjectMapper() {
        return SerializationUtils.clone(mapper);
    }

    /**
     * Object可以是POJO，也可以是Collection或数组。 如果对象为Null, 返回"null". 如果集合为空集合, 返回"[]".
     */
    public static String toJson(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (IOException e) {
            throw new JsonSerializedException("Serialized Object to json string error : " + object, e);
        }
    }

    public static String toPrettyJson(Object object) {
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (IOException e) {
            throw new JsonSerializedException("Serialized Object to json string error : " + object, e);
        }
    }

    /**
     * 反序列化POJO或简单Collection如List<String>. 如果JSON字符串为Null或"null"字符串, 返回Null.
     * 如果JSON字符串为"[]", 返回空集合. 如需反序列化复杂Collection如List<MyBean>,
     * 请使用fromJson(String,JavaType)
     */
    public static <T> T fromJson(String jsonString, Class<T> clazz) {
        if (jsonString == null || "".equals(jsonString.trim())) {
            return null;
        }
        try {
            return mapper.readValue(jsonString, clazz);
        } catch (IOException e) {
            throw new JsonDeserializedException("Deserialized json string error : " + jsonString, e);
        }
    }

    public static <T> T fromJson(String jsonString, TypeReference<T> typeReference) {

        try {
            return mapper.readValue(jsonString, typeReference);
        } catch (IOException e) {
            throw new JsonDeserializedException("Deserialized json string error : " + jsonString, e);
        }
    }
}