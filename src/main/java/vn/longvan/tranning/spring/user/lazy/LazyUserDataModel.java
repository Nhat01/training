package vn.longvan.tranning.spring.user.lazy;

import org.apache.commons.collections4.ComparatorUtils;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.filter.FilterConstraint;
import org.primefaces.util.LocaleUtils;
import vn.longvan.tranning.spring.user.model.User;

import javax.faces.context.FacesContext;
import java.beans.IntrospectionException;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LazyUserDataModel extends LazyDataModel<User> {
    private static final long serialVersionUID = 1L;

    private List<User> datasource;

    public LazyUserDataModel(List<User> datasource) {
        this.datasource = datasource;
    }

    @Override
    public User getRowData(String rowKey) {
        for (User user : datasource) {
            if (user.getId() == rowKey) {
                return user;
            }
        }

        return null;
    }

    @Override
    public String getRowKey(User user) {
        return String.valueOf(user.getId());
    }

    @Override
    public int count(Map<String, FilterMeta> filterBy) {
        return (int) datasource.stream()
                .filter(o -> filter(FacesContext.getCurrentInstance(), filterBy.values(), o))
                .count();
    }

    @Override
    public List<User> load(int offset, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
        // apply offset & filters
        List<User> users = datasource.stream()
                .skip(offset)
                .filter(o -> filter(FacesContext.getCurrentInstance(), filterBy.values(), o))
                .limit(pageSize)
                .collect(Collectors.toList());

//        // sort
//        if (!sortBy.isEmpty()) {
//            List<Comparator<User>> comparators = sortBy.values().stream()
//                    .map(o -> new LazySorter(o.getField(), o.getOrder()))
//                    .collect(Collectors.toList());
//            Comparator<User> cp = ComparatorUtils.chainedComparator(comparators); // from apache
//            users.sort(cp);
//        }

        return users;
    }

    private boolean filter(FacesContext context, Collection<FilterMeta> filterBy, Object o) {
        boolean matching = true;

        for (FilterMeta filter : filterBy) {
            FilterConstraint constraint = filter.getConstraint();
            Object filterValue = filter.getFilterValue();

            try {
                Object columnValue = String.valueOf(ShowcaseUtil.getPropertyValueViaReflection(o, filter.getField()));
                matching = constraint.isMatching(context, columnValue, filterValue, LocaleUtils.getCurrentLocale());
            }
            catch (ReflectiveOperationException | IntrospectionException e) {
                matching = false;
            }

            if (!matching) {
                break;
            }
        }

        return matching;
    }
}
