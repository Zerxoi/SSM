package xyz.zerxoi.pojo;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MyIterable {
    private Dept[] array;
    private List<Dept> list;
    private Map<String, Dept> map;
    private Set<Dept> set;

    public void setArray(Dept[] array) {
        this.array = array;
    }

    public void setList(List<Dept> list) {
        this.list = list;
    }

    public void setMap(Map<String, Dept> map) {
        this.map = map;
    }

    public void setSet(Set<Dept> set) {
        this.set = set;
    }
    
    @Override
    public String toString() {
        return "Iterable [array=" + Arrays.toString(array) + ", list=" + list + ", map=" + map + ", set=" + set + "]";
    }

}
