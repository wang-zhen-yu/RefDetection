package com.wzy.paper.extract.service;

import org.apache.tika.exception.TikaException;

import java.io.IOException;
import java.util.List;

/**
 * @Author wzy
 * @Date 2016/11/15 11:22
 */
public interface ExtractService {


    /**
     * 根据文本路径提取文章内容
     * @param filePath 文本路径
     * @return
     */
    //String extractContext(String filePath) throws IOException, TikaException;

    /**
     * 从文本中提取参考文献部分
     * @param filePath
     * @return
     */
    List<String> extractRef(String filePath) throws IOException, TikaException;
}
