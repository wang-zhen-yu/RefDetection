package com.wzy.paper.extract.service.impl;

import com.google.common.collect.Lists;
import com.wzy.paper.extract.core.ExtractContext;
import com.wzy.paper.extract.service.ExtractService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.tika.exception.TikaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author wzy
 * @Date 2016/11/15 14:23
 */
@Service("ExtractService")
public class ExtractServiceImpl implements ExtractService {
    @Autowired
    private ExtractContext extractContext;

    public String extractContext(String filePath) throws IOException, TikaException {
        return extractContext.extractTxt(filePath);
    }

    public List<String> extractRef(String context) {
        List<String> references = Lists.newArrayList();

        //匹配参考文献
        Pattern pattern = Pattern.compile("参.*考.*文.*献");
        Matcher matcher = pattern.matcher(context);
        if (!matcher.find()) {
            return Lists.newArrayList();
        }
        //获取最后匹配位置
        int endIndex = matcher.end();
        String refContext = context.substring(endIndex);

        //分割每条参考文献
        references = Lists.newArrayList(refContext.split("\\r|\\n"));

        if(CollectionUtils.isEmpty(references)){
            throw new RuntimeException("未提取到参考文献列表");
        }

        //去除为空的内容

        for(int i=0;i<references.size();i++){
            String reference=references.get(i);

            if(StringUtils.isEmpty(reference)){
                references.remove(i);
            }
        }

        return references;
    }

    private List<String> resolve(List<String> references){
//        List<String> modifiedReferences = Lists.newArrayList();
//
//        for (int i=0;i<references.size();i++){
//            String ref=references.get(i);
//
//            if(StringUtils.isEmpty(ref)||)
//
//        }
        return  null;
    }
}
