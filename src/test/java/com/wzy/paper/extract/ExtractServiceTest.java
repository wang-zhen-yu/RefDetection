package com.wzy.paper.extract;

import com.google.common.collect.Lists;
import com.wzy.paper.BaseJunit4Test;
import com.wzy.paper.entity.Reference;
import com.wzy.paper.extract.core.ExtractRefFeature;
import com.wzy.paper.extract.service.ExtractService;
import com.wzy.paper.search.exception.SearchException;
import com.wzy.paper.search.service.EvidenceCrawlerService;
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
    @Autowired
    private EvidenceCrawlerService refSearchService;
    @Autowired
    private ExtractRefFeature extractRefFeature;

    @Test
    public void testExtractFingure() throws IOException, TikaException {
        String filePath = "C:\\Users\\Administrator\\Desktop\\paper\\1160010018.pdf";

        List<String> refString = Lists.newArrayList();
        refString.add("戚建明，李叶舟，袁文俊，关于某些代数微分方程亚纯解的增长性估计，数学物理学报 33A(4): 759-765");


//        refString = extractService.extractRef(filePath);

        extractRefFeature.write(refString,"C:\\Users\\Administrator\\Desktop\\CRF++\\test1.data");
    }

    @Test
    public void testExtractContext() throws IOException, TikaException {
        String filePath = "C:\\Users\\Administrator\\Desktop\\paper\\1160010018.pdf";
        // String filePath = "/Users/lqf/Documents/基于最大熵的依存句法分析.pdf";
        // String context = null;
        List<String> refString = null;

        refString = extractService.extractRef(filePath);
        for (int i = 0; i < refString.size(); i++) {
            String originRefString = refString.get(i);
            System.out.println(originRefString);
        }

//        try {
//            //context = extractService.extractContext(filePath);
////            System.out.println(context);
//            refString = extractService.extractRef(filePath);
//            for (int i = 0; i < refString.size(); i++) {
//                String originRefString = refString.get(i);
//                System.out.println(originRefString);
//
//                //2 网上爬取证据参考文献列表
//                List<Reference> evidenceList = null;
//                try {
//                    evidenceList = refSearchService.searchRef(originRefString, 3);
//                    if (evidenceList == null) {
//                        System.out.println("evidenceList 证据为空 ");
//                        continue;
//                    }
//                    for (int j = 0; j < evidenceList.size(); j++) {
//
//                        System.err.println("evidence:" + evidenceList.get(j));
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (SearchException e) {
//                    e.printStackTrace();
//                }
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (TikaException e) {
//            e.printStackTrace();
//        }
    }

}
