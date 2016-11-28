package com.wzy.paper.extract;

import com.wzy.paper.BaseJunit4Test;
import com.wzy.paper.extract.service.ExtractService;
import org.apache.tika.exception.TikaException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

/**
 * @Author wzy
 * @Date 2016/11/25 16:44
 */
public class ExtractServiceTest extends BaseJunit4Test {
    @Autowired
    private ExtractService extractService;

    @Test
    public void testExtractContext() {
        String filePath = "F:\\研究生\\论文\\基于支持向量机与无监督聚类相结合的中文网页分类器.pdf";
        String context = null;
        List<String> refString = null;

        try {
            context = extractService.extractContext(filePath);
//            System.out.println(context);
            refString = extractService.extractRef(context);
            System.out.println(refString);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TikaException e) {
            e.printStackTrace();
        }
    }
}
