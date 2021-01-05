package xyz.zerxoi.pojo;

import org.springframework.beans.factory.FactoryBean;

public class MyBean implements  FactoryBean<Dept> {

    @Override
    public Dept getObject() throws Exception {
        Dept dept = new Dept();
        dept.setName("sales");
        return dept;
    }

    @Override
    public Class<?> getObjectType() {
        return Dept.class;
    }
}
