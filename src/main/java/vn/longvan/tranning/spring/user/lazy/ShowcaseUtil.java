package vn.longvan.tranning.spring.user.lazy;

import org.primefaces.cache.CacheProvider;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;

public class ShowcaseUtil {

    private ShowcaseUtil() {

    }


    public static final Object getPropertyValueViaReflection(Object o, String field)
            throws ReflectiveOperationException, IllegalArgumentException, IntrospectionException {
        return new PropertyDescriptor(field, o.getClass()).getReadMethod().invoke(o);
    }

}
