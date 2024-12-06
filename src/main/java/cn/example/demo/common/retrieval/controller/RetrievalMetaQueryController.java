package cn.example.demo.common.retrieval.controller;

import cn.example.demo.common.model.response.HttpDataResponseResult;
import cn.example.demo.common.model.response.HttpResponseResult;
import cn.example.demo.common.retrieval.Criteria;
import cn.example.demo.common.retrieval.CriteriaIsMust;
import cn.example.demo.common.retrieval.EntityObject;
import cn.example.demo.common.secure.authority.AuthEnable;
import cn.example.demo.common.tools.obj.reflect.FieldAlias;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 【检索条件元数据】：访问处理器
 * </p>
 *
 * @author Lizuxian
 * @create 2020/4/26 10:49
 */
@Api(tags = {"【检索条件元数据】查询接口"})
@RestController
@RequestMapping(value = "retrieval")
public class RetrievalMetaQueryController {
    @AuthEnable
    @ApiOperation(value = "检索字段列表")
    @GetMapping(value = "field/list")
    public HttpResponseResult getFieldList(@ApiParam(value = "实体对象名称") @RequestParam(defaultValue = "") String objectName) {
        if (StringUtils.isEmpty(objectName)) {
            return HttpDataResponseResult.isBadRequest("实体对象名称不能为空！", null);
        }

        if (!isExistEntityObject(objectName)) {
            return HttpDataResponseResult.isBadRequest("不存在的实体对象！", null);
        }

        EntityObject entityObject = EntityObject.valueOf(objectName);
        Class clazz = entityObject.getClazz();
        Field[] fields = clazz.getDeclaredFields();
        List<Map<String, String>> l = new ArrayList<>();
        for (Field f : fields) {
            if (f.isAnnotationPresent(FieldAlias.class)) {
                if (f.getAnnotation(FieldAlias.class).enableSearch()) {
                    String name = f.getAnnotation(FieldAlias.class).value();
                    Map<String, String> m = new HashMap<>();
                    m.put("code", f.getName());
                    m.put("name", name);
                    m.put("type", f.getType().getSimpleName());
                    m.put("entity", entityObject.toString());
                    l.add(m);
                }
            }
        }

        if (l.isEmpty()) {
            return HttpDataResponseResult.isNotFound("没有检索字段列表！", null);
        }
        return HttpDataResponseResult.isSuccess(l);
    }

    @AuthEnable
    @ApiOperation(value = "检索条件列表")
    @GetMapping(value = "criteria/list")
    public HttpDataResponseResult getRetrievalCriteria() {
        Map<String, Object> result = new HashMap<>();
        List<Map<String, String>> l1 = new ArrayList<>();
        List<Map<String, String>> l2 = new ArrayList<>();
        for (Criteria criteria : Criteria.values()) {
            if (!criteria.equals(Criteria.exists) && !criteria.equals(Criteria.notExists)) {
                Map<String, String> m = new HashMap<>();
                m.put("code", criteria.name());
                m.put("name", criteria.getName());
                l1.add(m);
            }
        }
        for (CriteriaIsMust isMust : CriteriaIsMust.values()) {
            Map<String, String> m = new HashMap<>();
            m.put("code", isMust.name());
            m.put("name", isMust.getName());
            l2.add(m);
        }
        result.put("criteria", l1);
        result.put("isMust", l2);
        return HttpDataResponseResult.isSuccess(result);
    }

    /**
     * <p>
     * 是否存在实体对象
     * </P>
     *
     * @param objectName
     * @return
     */
    protected boolean isExistEntityObject(String objectName) {
        for (EntityObject e : EntityObject.values()) {
            if (e.toString().equals(objectName)) {
                return true;
            }
        }
        return false;
    }
}
