package xyz.zerxoi.pojo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ConstructorAutowiredBean {
    private BeanInterface beanImplOne;
    private BeanInterface beanImplTwo;
    
    @Autowired(required = false)
    public ConstructorAutowiredBean() {}

    // @Autowired(required = false)
    // public ConstructorAutowiredBean(BeanInterface beanImplOne) {
    //     this.beanImplOne = beanImplOne;
    // }

    // @Autowired(required = false)
    // public ConstructorAutowiredBean(@Qualifier("beanImplTwo") BeanInterface beanImplOne, @Qualifier("beanImplOne") BeanInterface beanImplTwo) {
    //     this.beanImplOne = beanImplOne;
    //     this.beanImplTwo = beanImplTwo;
    // }

    @Override
    public String toString() {
        return "ConstructorAutowiredBean [beanImplOne=" + beanImplOne + ", beanImplTwo=" + beanImplTwo + "]";
    }
}
