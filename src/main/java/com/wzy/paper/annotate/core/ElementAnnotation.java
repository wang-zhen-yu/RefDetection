package com.wzy.paper.annotate.core;

import com.google.common.collect.Lists;
import com.hankcs.hanlp.corpus.tag.Nature;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;
import com.wzy.paper.annotate.Exception.AnnotationException;
import com.wzy.paper.entity.Reference;
import com.wzy.paper.util.SimilarityUtil;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author wzy
 * @Date 2016/11/21 17:08
 */
@Component("ElementAnnotation")
public class ElementAnnotation {

    /**
     * 文章标题相似度阈值
     */
    private static final double titleXslThreshold = 0.7;

    /**
     * 文章出版来源相似度阈值
     */
    private static final double sourceXslThreshold = 0.5;

    /**
     * 对参考文献原文进行标注
     *
     * @param refString 参考文献原文
     * @param evidence  网络爬取的证据文献
     * @return
     * @throws Exception
     */
    public Reference annotate(String refString, Reference evidence) throws AnnotationException {
        Reference reference = new Reference();
        String splitSymbols = ",|\\.|，|。";

        // 按标点切分
        List<String> elementList = Lists.newArrayList(refString.split(splitSymbols));

        // 可能的作者列表
        List<String> theoreticalAuthorList = Lists.newArrayList();
        List<String> theoreticalPressAndTime = Lists.newArrayList();

        //各元素索引位置，分别是作者、文章名、机构名、时间
        int[] indexs = new int[4];

        // 标注文章标题
        indexs[1] = annotateTitle(elementList, evidence.getTitle(), 0);

        // 标注发表时间
        indexs[3] = annotateTime(elementList, evidence.getTime(), indexs[1]);

        // 标注出版来源
        indexs[2] = annotateSource(elementList, evidence.getSource(), indexs[1]);

        StringBuffer authors = new StringBuffer();

        for (int i = 0; i < indexs[1]; i++) {
            //去除“等 et al”无用名称
            Pattern pattern = Pattern.compile("et/s?al|/s?等/s?");

            if (!pattern.matcher(elementList.get(i)).matches()) {
                authors.append(elementList.get(i) + ",");
            }
        }

        reference.setAuthor(authors.toString());
        reference.setTitle(elementList.get(indexs[1]));

        StringBuffer source = new StringBuffer();
        for (int i = indexs[1] + 1; i <= indexs[2]; i++) {
            source.append(elementList.get(i));
        }
        reference.setSource(source.toString());

        StringBuffer time = new StringBuffer();
        for (int i = indexs[3]; i < elementList.size(); i++) {
            time.append(elementList.get(i));
        }
        reference.setTime(time.toString());

        return reference;
    }

    /**
     * 标注机构名
     *
     * @param elementList
     * @param source
     * @param beginIndex 从该索引处开始查找
     * @return
     */
    private int annotateSource(List<String> elementList, String source, int beginIndex) throws AnnotationException {
        int length = elementList.size();

        for (int i = 1; i < elementList.size(); i++) {
            String element = elementList.get((i + beginIndex) % length);

            // 如果匹配证据库中出版来源；如果不匹配，尝试排除人名
            if ((SimilarityUtil.similarity(element, source) >= sourceXslThreshold) || (element.length() >= 5)) {
                return (i + beginIndex) % length;
            }
        }

        throw new AnnotationException("标注异常：未检测到文章出版来源");
    }

    /**
     * 标注时间
     *
     * @param elementList
     * @param time
     * @param beginIndex 从该索引处开始查找
     * @return
     */
    private int annotateTime(List<String> elementList, String time, int beginIndex) throws AnnotationException {
        int length = elementList.size();
        Pattern pattern = Pattern.compile("19\\d{2}|20\\d{2}");
        Matcher matcher = null;

        for (int i = 0; i < elementList.size(); i++) {
            String element = elementList.get((i + beginIndex) % length);

            // 匹配19++ 20++的格式
            if (pattern.matcher(element).find()) {
                return (i + beginIndex) % length;
            }
        }

        throw new AnnotationException("标注异常：未检测到发表时间");
    }

    /**
     * 标注文章标题
     *
     * @param elementList
     * @param title
     * @param beginIndex 从该索引处开始查找
     * @return
     */
    private int annotateTitle(List<String> elementList, String title, int beginIndex) throws AnnotationException {
        int length = elementList.size();

        for (int i = 0; i < elementList.size(); i++) {
            String element = elementList.get((i + beginIndex) % length);

            if (SimilarityUtil.similarity(element, title) >= titleXslThreshold) {
                return (i + beginIndex) % length;
            }
        }

        throw new AnnotationException("标注异常：未检测到文章标题");
    }

}


//~ Formatted by Jindent --- http://www.jindent.com
