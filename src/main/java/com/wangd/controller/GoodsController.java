package com.wangd.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wangd.pojo.Goods;
import com.wangd.pojo.GoodsAttribute;
import com.wangd.pojo.GoodsCategory;
import com.wangd.service.GoodsService;
import com.wangd.utils.GoodsResultTemplate;
import com.wangd.utils.MapUtils;
import com.wangd.utils.RequestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpRequest;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpSession;

/**
 * @author wangd
 */
@Controller
@CrossOrigin
@ResponseBody
@Scope("prototype")
public class GoodsController {


    @Autowired
    private GoodsService goodsService;

    private RequestResult requestResult = new RequestResult();

    @GetMapping("/goods")
    public String getGoods(@RequestParam("query") String query, @RequestParam("pagenum") Integer pageNumber, @RequestParam("pagesize") Integer pageSize){
        System.out.println("query = " + query);
        System.out.println("pageNumber = " + pageNumber);
        System.out.println("pageSize = " + pageSize);
        List<Goods> goodsList = goodsService.getGoodsList(query, pageNumber, pageSize);
        JSONObject resultObject = new JSONObject();
        resultObject.put("total", goodsService.getGoodsTotal());
        resultObject.put("pagenum", pageNumber);
        JSONArray jsonArray = new JSONArray();
        goodsList.forEach(goods -> {
            jsonArray.add(GoodsResultTemplate.goodsApiMapping(goods));
        });
        resultObject.put("goods", jsonArray);
        requestResult.data = resultObject;
        requestResult.msg = "获取成功";
        return requestResult.getResult();
    }

    @GetMapping("/categories")
    public String getCategories(@Nullable @RequestParam Map<String, String> requestParams){


        if (requestParams != null && !requestParams.keySet().isEmpty()){
            String type = requestParams.get("type");

            System.out.println("type = " + type);

            Map<Integer, GoodsCategory> integerGoodsCategoryMap = null;
            if (requestParams.get("pagenum") != null && requestParams.get("pagesize") != null){
                JSONObject jsonObject = new JSONObject();
                Integer pageNumber = Integer.parseInt(requestParams.get("pagenum"));
                Integer pageSize = Integer.parseInt(requestParams.get("pagesize"));
                Integer currPageIndex = pageNumber == 1 ? 0 : pageNumber * pageSize - pageSize + 1;
                Map<Integer, GoodsCategory> goodsCategory = goodsService.getGoodsCategory(type);

                integerGoodsCategoryMap = MapUtils.subMap(goodsCategory, currPageIndex, pageSize);
                JSONArray jsonArray = GoodsResultTemplate.goodsCategoryToJsonArray(integerGoodsCategoryMap, "");

                jsonObject.put("pagenum", pageNumber);
                jsonObject.put("pagesize", pageSize);
                jsonObject.put("total", goodsCategory.size());
                jsonObject.put("result", jsonArray);
                requestResult.data = jsonObject;
                return requestResult.getResult();
            }
            Map<Integer, GoodsCategory> goodsCategory = goodsService.getGoodsCategory(type);
            JSONArray jsonArray = GoodsResultTemplate.goodsCategoryToJsonArray(goodsCategory, "2");
            requestResult.data = jsonArray;
            return requestResult.getResult();

        }
        Map<Integer, GoodsCategory> goodsCategory = goodsService.getGoodsCategory(null);
        JSONArray jsonArray = GoodsResultTemplate.goodsCategoryToJsonArray(goodsCategory, "");
        requestResult.data = jsonArray;
        return requestResult.getResult();
    }

    @GetMapping("/categories/{categoriesId}")
    public String getOneCategories(@PathVariable("categoriesId") Integer categoriesId){
        GoodsCategory goodsOneCategory = goodsService.getGoodsOneCategory(categoriesId);
        requestResult.data = GoodsResultTemplate.goodsCategoryAPIMapping(goodsOneCategory);
        return requestResult.getResult();
    }

    @PostMapping("/categories")
    public String addCategories(@RequestBody Map<String, Object> goodsCategories){
        System.out.println("goodsCategories = " + goodsCategories);
        GoodsCategory goodsCategory = new GoodsCategory();
        goodsCategory.setCategoryFatherId((Integer) goodsCategories.get("cat_pid"));
        goodsCategory.setCategoryName((String) goodsCategories.get("cat_name"));
        goodsCategory.setCategoryLevel((Integer) goodsCategories.get("cat_level"));
        int insertCount = goodsService.addGoodsCategory(goodsCategory);
        if (insertCount != 1){
            requestResult.data = null;
            requestResult.msg = "添加失败 请确认数据是否正常";
            requestResult.status = 404;
            return requestResult.getResult();
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("cat_id", goodsCategory.getId());
        jsonObject.put("cat_name", goodsCategory.getCategoryName());
        jsonObject.put("cat_pid", goodsCategory.getCategoryFatherId());
        jsonObject.put("cat_level", goodsCategory.getCategoryLevel());
        requestResult.data = jsonObject;
        requestResult.msg = "插入成功";
        return requestResult.getResult();
    }

    @PutMapping("/categories/{categoryId}")
    public String editGoodsCategories(@PathVariable("categoryId") Integer categoryId, @RequestBody Map<String, String> requestParams){
        GoodsCategory goodsCategory = new GoodsCategory();
        goodsCategory.setId(categoryId);
        goodsCategory.setCategoryName(requestParams.get("cat_name"));
        int updateCount = goodsService.editGoodsCategory(goodsCategory);
        if (updateCount != 1){
            requestResult.status = 404;
            requestResult.msg = "更新失败";
            return requestResult.getResult();
        }
        GoodsCategory goodsOneCategory = goodsService.getGoodsOneCategory(categoryId);
        requestResult.data = GoodsResultTemplate.goodsCategoryAPIMapping(goodsOneCategory);
        requestResult.msg = "更新成功";
        return requestResult.getResult();
    }

    @DeleteMapping("/categories/{categoryId}")
    public String deleteCategoryById(@PathVariable("categoryId") Integer categoryId){
        GoodsCategory goodsCategory = new GoodsCategory();
        goodsCategory.setId(categoryId);
        goodsCategory.setIsDelete(1);
        int deleteCount = goodsService.editGoodsCategory(goodsCategory);
        requestResult.data = null;
        requestResult.msg = "软删除成功";
        if (deleteCount != 1){
            requestResult.status = 404;
            requestResult.msg = "软删除失败";
        }
        return requestResult.getResult();
    }

    @GetMapping("/categories/{categoryId}/attributes")
    public String getAttributes(@PathVariable("categoryId") Integer categoryId, @RequestParam("sel") String sel){

        System.out.println("categoryId = " + categoryId);
        System.out.println("sel = " + sel);
        List<GoodsAttribute> goodsAttributes = goodsService.getGoodsAttributes(categoryId);
        JSONArray attributeJsonArrays = new JSONArray();
        goodsAttributes.forEach(attribute -> {
            if (attribute.getAttributeSelect().equals(sel)) {
                attributeJsonArrays.add(GoodsResultTemplate.goodsAttributeAPIMapping(attribute));
            }
        });
        requestResult.data = attributeJsonArrays;
        return requestResult.getResult();
    }

    @PostMapping("/categories/{categoryId}/attributes")
    public String addAttribute(@PathVariable("categoryId") Integer categoryId, @RequestBody Map<String, Object> attribute){
        GoodsAttribute goodsAttribute = new GoodsAttribute();
        goodsAttribute.setAttributeName((String) attribute.get("attr_name"));
        goodsAttribute.setAttributeSelect((String) attribute.get("attr_sel"));
        goodsAttribute.setAttributeValues(attribute.get("attr_vals") == null ? "": (String) attribute.get("attr_vals"));
        goodsAttribute.setCategoryId(categoryId);
        int addCount = goodsService.addGoodsAttribute(goodsAttribute);
        if (addCount != 1){
            requestResult.data = null;
            requestResult.status = 404;
            requestResult.msg = "添加商品参数失败";
            return requestResult.getResult();
        }
        requestResult.data = GoodsResultTemplate.goodsAttributeAPIMapping(goodsAttribute);
        requestResult.msg = "添加商品信息成功";
        requestResult.status = 201;
        return requestResult.getResult();
    }

    @DeleteMapping("/categories/{categoryId}/attributes/{attributeId}")
    public String deleteAttribute(@PathVariable("categoryId") Integer categoryId, @PathVariable("attributeId") Integer attributeId){
        int delCount = goodsService.softDeleteAttribute(categoryId, attributeId);
        requestResult.data =null;
        if (delCount < 1){
            requestResult.status = 404;
            requestResult.msg = "删除失败";
            return requestResult.getResult();
        }
        requestResult.msg = "删除成功";
        return requestResult.getResult();
    }

    @GetMapping("categories/{categoryId}/attributes/{attributeId}")
    public String getAttribute(@PathVariable("categoryId") Integer categoryId, @PathVariable("attributeId") Integer attributeId){
        GoodsAttribute goodsAttribute = goodsService.getGoodsAttribute(categoryId, attributeId);
        requestResult.data = GoodsResultTemplate.goodsAttributeAPIMapping(goodsAttribute);
        return requestResult.getResult();
    }

    @PutMapping("/categories/{categoryId}/attributes/{attributeId}")
    public String editAttributes(@PathVariable("categoryId") Integer categoryId, @PathVariable("attributeId") Integer attributeId, @RequestBody Map<String, Object> param){
        GoodsAttribute goodsAttribute = GoodsResultTemplate.goodsAttributeApiMapToEntity(param);
        goodsAttribute.setCategoryId(categoryId);
        goodsAttribute.setAttributeId(attributeId);
        System.out.println("goodsAttribute = " + goodsAttribute);
        int i = goodsService.editAttribute(goodsAttribute);
        if (i < 1){
            requestResult.status = 404;
            requestResult.msg = "更新失败";
            return requestResult.getResult();
        }
        requestResult.msg = "更新成功";
        return requestResult.getResult();
    }

    @PostMapping("/upload")
    public String fileUpload(@RequestBody MultipartFile file, HttpSession session){
        String tempPath = "project_temp_png";
        String separator = File.separator;
        String projectDirPath = System.getProperty("user.dir");
        String basePath = projectDirPath + separator + tempPath;
        String filename = "";
        File file1 = null;
        try {
            filename = DigestUtils.md5DigestAsHex(file.getBytes()) + "." + file.getOriginalFilename().split("\\.")[1];
            file1 = new File(basePath + separator + filename);

            if(!file1.exists()){
                file1.mkdirs();
            }

            file.transferTo(file1);
        } catch (IOException e) {
            e.printStackTrace();
        }


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("tmp_path", file1.getPath());
        requestResult.data = session.getServletContext().getContextPath() + "/" +tempPath + "/" + file1.getName();
        return requestResult.getResult();
    }

}
