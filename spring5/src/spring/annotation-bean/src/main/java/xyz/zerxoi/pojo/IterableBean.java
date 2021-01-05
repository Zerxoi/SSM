package xyz.zerxoi.pojo;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IterableBean {
    @Autowired
    private List<BeanInterface> list;
    @Autowired
    private BeanInterface[] array;
    @Autowired
    private Map<String, BeanInterface> map;
    @Autowired
    private Set<BeanInterface> set;

    public List<BeanInterface> getList() {
        return list;
    }

    public BeanInterface[] getArray() {
        return array;
    }

    public Map<String, BeanInterface> getMap() {
        return map;
    }

    public Set<BeanInterface> getSet() {
        return set;
    }
}