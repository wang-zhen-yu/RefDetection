package com.wzy.paper.annotate.service;

import com.wzy.paper.annotate.Exception.AnnotationException;
import com.wzy.paper.entity.Reference;

/**
 * 标注服务类
 * @Author wzy
 * @Date 2016/11/26 20:50
 */
public interface AnnotateService {

    /**
     * 通过证据参考文献，进行参考文献原文标注，返回一个参考文献类
     * @param refString 参考文献原文
     * @param evidence 证据参考文献
     * @return
     *
     * @throws Exception
     */
    Reference annotateByEvidence(String refString, Reference evidence) throws AnnotationException;
}


//~ Formatted by Jindent --- http://www.jindent.com
