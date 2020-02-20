package in.hocg.eagle.utils;

import com.google.common.collect.Lists;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.*;

/**
 * Created by hocgin on 2019/7/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@UtilityClass
public class ClassUtils {
    
    /**
     * 获取对象字段的值
     *
     * @param fieldObject
     * @param field
     * @param def
     * @return
     */
    public Object getFieldValue(Object fieldObject, Field field,
                                Object def) {
        if (Objects.isNull(fieldObject)
                || Objects.isNull(field)) {
            return def;
        }
        
        boolean accessible = field.isAccessible();
        try {
            field.setAccessible(true);
            return field.get(fieldObject);
        } catch (IllegalAccessException ignored) {
            return def;
        } finally {
            field.setAccessible(accessible);
        }
    }
    
    public static <T> T getObjectValue(Object fieldObject, Field field,
                                       Object def) {
        return ((T) getFieldValue(fieldObject, field, def));
    }
    
    /**
     * 设置对象某字段的值
     *
     * @param fieldObject
     * @param field
     * @param value
     * @return
     */
    public void setFieldValue(Object fieldObject, Field field, Object value) {
        boolean accessible = field.isAccessible();
        try {
            field.setAccessible(true);
            field.set(fieldObject, value);
        } catch (IllegalAccessException ignored) {
        } finally {
            field.setAccessible(accessible);
        }
    }
    
    /**
     * 获取所有函数
     *
     * @return
     */
    public static List<Method> getAllMethod(Class<?> clazz) {
        List<Method> result = Lists.newArrayList();
        result.addAll(Arrays.asList(clazz.getDeclaredMethods()));
        Class<?> superclass = clazz.getSuperclass();
        if (Object.class.equals(superclass)) {
            return result;
        }
        result.addAll(ClassUtils.getAllMethod(superclass));
        
        return result;
    }
    
    /**
     * 获取所有字段
     *
     * @return
     */
    public static List<Field> getAllField(Class<?> clazz) {
        ArrayList<Field> result = Lists.newArrayList();
        result.addAll(Arrays.asList(clazz.getDeclaredFields()));
        
        Class<?> superclass = clazz.getSuperclass();
        if (Object.class.equals(superclass)) {
            return result;
        }
        result.addAll(ClassUtils.getAllField(superclass));
        return result;
    }
    
    /**
     * 查找字段
     *
     * @param fieldName
     * @return
     */
    public static Optional<Field> getField(Class<?> clazz, String fieldName) {
        Field field = null;
        try {
            field = clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            Class<?> superclass = clazz.getSuperclass();
            if (!Object.class.equals(superclass)) {
                return ClassUtils.getField(clazz, fieldName);
            }
        }
        return Optional.ofNullable(field);
    }
    
    /**
     * 通过函数名称获取 Class 对应的函数
     *
     * @param clazz
     * @param methodName
     * @return
     */
    public static Method getMethod(Class<?> clazz, String methodName) {
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        
        throw new IllegalArgumentException(clazz + "未找到函数名为 " + methodName + " 的函数");
    }
    
    /**
     * 检查是否是基础类型
     * Integer => true
     * Long => true
     *
     * @param clazz
     * @return
     */
    public static boolean isPrimitive(Class<?> clazz) {
        try {
            return ((Class<?>) clazz.getField("TYPE").get(null)).isPrimitive();
        } catch (IllegalAccessException | NoSuchFieldException e) {
            return false;
        }
    }
    
    /**
     * 判断是否基础类型
     *
     * @param clazz
     * @return
     */
    public static boolean isBaseType(Class clazz) {
        return clazz.equals(Integer.class) ||
                clazz.equals(Byte.class) ||
                clazz.equals(Long.class) ||
                clazz.equals(Double.class) ||
                clazz.equals(Float.class) ||
                clazz.equals(Character.class) ||
                clazz.equals(Short.class) ||
                clazz.equals(Boolean.class);
    }
    
    /**
     * 判断是否数组
     *
     * @param clazz
     * @return
     */
    public static boolean isArray(Class clazz) {
        return clazz.isArray();
    }
    
    /**
     * 获取范型的具体类型
     *
     * @param clazz
     * @param index
     * @return
     */
    public static Class getGenericSuperclass(Class clazz, int index) {
        ParameterizedType pt = (ParameterizedType) clazz.getGenericSuperclass();
        return (Class) pt.getActualTypeArguments()[index];
    }
}
