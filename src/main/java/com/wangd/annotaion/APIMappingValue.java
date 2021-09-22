package com.wangd.annotaion;

import com.sun.org.glassfish.gmbal.DescriptorFields;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * @author wangd
 */
@Target(ElementType.FIELD)
public @interface APIMappingValue {
    String value();
}
