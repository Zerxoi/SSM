package xyz.zerxoi;

import java.io.IOException;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

public class ResourceTest {
    @Test
    public void testClasspathResource() throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
        Resource resource = context.getResource("classpath:META-INF/license.txt");
        System.out.println(resource.getURL());
        context.close();
    }

    @Test
    public void testClasspathResources() throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
        Resource[] resources = context.getResources("classpath:xyz/zerxoi/dao/**/*.class");
        for (Resource resource : resources) {
            System.out.println(resource.getURL());
        }
        context.close();
    }

    @Test
    public void testFtp() throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
        UrlResource resource = (UrlResource)context.getResource("ftp://speedtest.tele2.net");
        System.out.println(resource.getInputStream());
        context.close();
    }
}
