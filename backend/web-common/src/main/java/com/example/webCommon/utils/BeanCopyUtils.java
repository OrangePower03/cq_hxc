package com.example.webCommon.utils;

import com.example.webCommon.domain.dto.BaseDto;
import com.example.webCommon.domain.entity.BaseEntity;
import com.example.webCommon.domain.vo.BaseVo;
import org.springframework.beans.BeanUtils;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 将entity对象转换为vo(value object)对象的工具类
 * 将dto对象转换为entity对象的工具类
 */
public class BeanCopyUtils {
    private BeanCopyUtils(){
    }

    public static <V extends BaseVo> V copyEntityBeanToVo(BaseEntity source, Class<V> targetClass){
        V target = null;
        try {
            target = targetClass.getConstructor().newInstance();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        BeanUtils.copyProperties(source,target);
        return target;
    }

    public static<V extends BaseEntity> V copyDtoBeanToEntity(BaseDto source, Class<V> targetClass){
        V target = null;
        try {
            target = targetClass.getConstructor().newInstance();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        BeanUtils.copyProperties(source,target);
        return target;
    }

    public static <V extends BaseVo> List<V> copyEntityBeanToVo(Collection<? extends BaseEntity> sourceList, Class<V> targetClass){

        return sourceList.stream()
                .map(baseEntity -> copyEntityBeanToVo(baseEntity, targetClass))
                .collect(Collectors.toList());
    }
}

