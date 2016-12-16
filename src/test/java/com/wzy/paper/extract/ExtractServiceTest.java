package com.wzy.paper.extract;

import com.wzy.paper.BaseJunit4Test;
import com.wzy.paper.entity.Reference;
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
    @Test
    public void testExtractContext() {
        String filePath = "/Users/lqf/Documents/基于最大熵分类器的语义角色标注.pdf";
       // String filePath = "/Users/lqf/Documents/基于最大熵的依存句法分析.pdf";
       // String context = null;
        List<String> refString = null;

        try {
            //context = extractService.extractContext(filePath);
//            System.out.println(context);
            refString = extractService.extractRef(filePath);
            for (int i=0;i<refString.size();i++){
                String originRefString=refString.get(i);
                System.out.println(originRefString);

                //2 网上爬取证据参考文献列表
               List<Reference> evidenceList= null;
                try {
                    evidenceList = refSearchService.searchRef(originRefString,3);
                    if (evidenceList == null) {
                        System.out.println("evidenceList 证据为空 ");
                        continue;
                    }
                    for (int j=0;j<evidenceList.size();j++){

                        System.err.println("evidence:"+evidenceList.get(j));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SearchException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TikaException e) {
            e.printStackTrace();
        }
    }

}
