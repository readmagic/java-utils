package fun.oop.framework.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: bin.yu
 * Date: 2018/1/13 0013
 * Time: 下午 15:47
 */
public class BeanUtil extends org.springframework.beans.BeanUtils {


    public static void copyNotNullProperties(Object source, Object target, String[] ignoreProperties) throws BeansException {
        copyNotNullProperties(source, target, null, ignoreProperties);
    }


    public static void copyNotNullProperties(Object source, Object target, Class<?> editable) throws BeansException {
        copyNotNullProperties(source, target, editable, null);
    }


    public static void copyNotNullProperties(Object source, Object target) throws BeansException {
        copyNotNullProperties(source, target, null, null);
    }


    private static void copyNotNullProperties(Object source, Object target, Class<?> editable, String[] ignoreProperties) throws BeansException {


        Assert.notNull(source, "Source must not be null");
        Assert.notNull(target, "Target must not be null");


        Class<?> actualEditable = target.getClass();
        if (editable != null) {
            if (!editable.isInstance(target)) {
                throw new IllegalArgumentException("Target class [" + target.getClass().getName() + "] not assignable to Editable class [" + editable.getName() + "]");
            }
            actualEditable = editable;
        }
        PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
        List<String> ignoreList = (ignoreProperties != null) ? Arrays.asList(ignoreProperties) : null;


        for (PropertyDescriptor targetPd : targetPds) {
            if (targetPd.getWriteMethod() != null && (ignoreProperties == null || (!ignoreList.contains(targetPd.getName())))) {
                PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
                if (sourcePd != null && sourcePd.getReadMethod() != null) {
                    try {
                        Method readMethod = sourcePd.getReadMethod();
                        if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                            readMethod.setAccessible(true);
                        }
                        Object value = readMethod.invoke(source);
                        if (value != null) {
                            boolean isEmpty = false;
                            if (value instanceof Set) {
                                Set s = (Set) value;
                                if (s == null || s.isEmpty()) {
                                    isEmpty = true;
                                }
                            } else if (value instanceof Map) {
                                Map m = (Map) value;
                                if (m == null || m.isEmpty()) {
                                    isEmpty = true;
                                }
                            } else if (value instanceof List) {
                                List l = (List) value;
                                if (l == null || l.size() < 1) {
                                    isEmpty = true;
                                }
                            } else if (value instanceof Collection) {
                                Collection c = (Collection) value;
                                if (c == null || c.size() < 1) {
                                    isEmpty = true;
                                }
                            }
                            if (!isEmpty) {
                                Method writeMethod = targetPd.getWriteMethod();
                                if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                    writeMethod.setAccessible(true);
                                }
                                writeMethod.invoke(target, value);
                            }
                        }
                    } catch (Throwable ex) {
                        throw new FatalBeanException("Could not copy properties convertFrom source to target", ex);
                    }
                }
            }
        }
    }


}