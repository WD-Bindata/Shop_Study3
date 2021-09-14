package com.wangd.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import jdk.nashorn.internal.ir.debug.JSONWriter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wangd
 */
public class RequestResult {
    public Object data;
    public Integer status = 200;
    public String msg = "成功";

    public String getResult(){
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> meta = new HashMap<>();
        meta.put("status", status);
        meta.put("msg", msg);
        result.put("data", this.data);

        result.put("meta", meta);
        return JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect);
    }


}
