package xyz.zerxoi.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UpDownloadController {
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String upload(@RequestParam("file") MultipartFile file, @RequestParam("desc") String desc,
            HttpServletRequest request) throws Exception {
        System.out.println(desc);
        ServletContext sc = request.getServletContext();
        // 获取给定路径在文件系统中的真实路径
        String originalFilename = file.getOriginalFilename();
        String path = sc.getRealPath("/upload");
        String ext = originalFilename.substring(originalFilename.lastIndexOf('.'));
        String name = UUID.randomUUID() + ext;
        File uploadFile = new File(path, name);
        // 如果不存在父目录创建父目录
        if (!uploadFile.getParentFile().exists()) {
            uploadFile.getParentFile().mkdirs();
        }
        file.transferTo(uploadFile);
        return "SUCCESS";
    }

    @RequestMapping(value = "/multiUpload", method = RequestMethod.POST)
    public String multiUpload(@RequestParam("file") MultipartFile[] files, @RequestParam("desc") String desc,
            HttpServletRequest request) throws Exception {
        System.out.println(desc);
        ServletContext sc = request.getServletContext();
        for (MultipartFile file : files) {
            String originalFilename = file.getOriginalFilename();
            String path = sc.getRealPath("/upload");
            String name = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf('.'));
            File upload = new File(path, name);
            // 如果不存在父目录创建父目录
            if (!upload.getParentFile().exists()) {
                upload.getParentFile().mkdirs();
            }
            file.transferTo(upload);
        }
        return "SUCCESS";
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public ResponseEntity<byte[]> download() throws IOException {
        Resource resource = new ClassPathResource("conf/mvc-servlet.xml");
        InputStream is = resource.getInputStream();
        byte[] b = is.readAllBytes();
        is.close();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment;filename=mvc-servlet.xml");
        return new ResponseEntity<>(b, headers, HttpStatus.OK);
    }
}
