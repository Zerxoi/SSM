package xyz.zerxoi.pojo;

public class LifeCycle {
    private String foo;

    public LifeCycle() {
        System.out.println("1. 调用无参构造创建 Bean 实例");
    }

    public String getFoo() {
        return foo;
    }

    public void setFoo(String foo) {
        System.out.println("2. 调用 set 方法设置对象属性");
        this.foo = foo;
    }

    public void initMethod() {
        System.out.println("3. 调用初始化方法");
    }

    public void destroyMethod() {
        System.out.println("5. 调用销毁方法");
    }

}
