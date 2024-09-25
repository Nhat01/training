package vn.longvan.tranning.spring.user.bean;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import vn.longvan.tranning.spring.user.controller.UserController;
import vn.longvan.tranning.spring.user.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LazyUserDataModel extends LazyDataModel<User> {
    private List<User> datasource;
    private UserController userController;

    public LazyUserDataModel(UserController userController){
        this.userController = userController;
    }

    @Override
    public String getRowKey(User user) {
        return String.valueOf(user.getId());
    }

    @Override
    public User getRowData(String rowKey) { return null; }

    @Override
    public int count(Map<String, FilterMeta> filterBy) {
        return userController.countUsers(filterBy);
    }

    @Override
    public List<User> load(int offset, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
        datasource = userController.getUsers(offset, pageSize, filterBy, sortBy);
        return datasource;
    }
}
