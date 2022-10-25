package vn.longvan.tranning.spring.user.lazy;

import org.primefaces.model.SortOrder;
import vn.longvan.tranning.spring.user.model.User;

import java.util.Comparator;

public class LazySorter implements Comparator<User> {

    private String sortField;
    private SortOrder sortOrder;

    public LazySorter(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }

    @Override
    public int compare(User customer1, User customer2) {
        try {
            Object value1 = ShowcaseUtil.getPropertyValueViaReflection(customer1, sortField);
            Object value2 = ShowcaseUtil.getPropertyValueViaReflection(customer2, sortField);

            int value = ((Comparable) value1).compareTo(value2);

            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}