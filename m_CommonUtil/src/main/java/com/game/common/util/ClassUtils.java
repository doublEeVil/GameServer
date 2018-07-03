package com.game.common.util;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.SystemPropertyUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Class 工具类
 * 主要是实现扫描类功能
 * 参考 <a href="http://blog.51cto.com/lj3331/1724896">参考</a>
 */
public class ClassUtils {
    public static Set<String> getAllClassSet(String basePackage) {
        Set<String> set = new HashSet<>();
        final String DEFAULT_RESOURCE_PATTERN = "**/*.class";

        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resourcePatternResolver);
        try {
            String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                    org.springframework.util.ClassUtils.convertClassNameToResourcePath(SystemPropertyUtils.resolvePlaceholders(basePackage))
                    + "/" + DEFAULT_RESOURCE_PATTERN;
            Resource[] resources = resourcePatternResolver.getResources(packageSearchPath);
            for (Resource res : resources) {
                if (res.isReadable()) {
                    MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(res);
                    String clsName = metadataReader.getClassMetadata().getClassName();
                    set.add(clsName);
                } else {
                    System.err.println("res can not read ... " + res.getFilename());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return set;
    }

    public static Map<String, Class<?>> getClassMapByAnnounce(String basePackage, Class announceClass) {
        Map<String, Class<?>> map = new HashMap<>();
        Set<String> classSet = getAllClassSet(basePackage);
        for (String clsName : classSet) {
            try {
                Class<?> clazz = Class.forName(clsName);
                if (clazz.getAnnotation(announceClass) != null) {
                    map.put(clsName, clazz);
                }
            } catch (ClassNotFoundException e) {
                System.err.println("未找到该类：" + clsName);
            }
        }
        return map;
    }
}
