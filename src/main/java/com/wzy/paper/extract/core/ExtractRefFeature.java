package com.wzy.paper.extract.core;

import com.google.common.collect.Lists;
import com.wzy.paper.util.StringUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * 提取参考文献特征类
 *
 * @author wzy
 * @version 1.0, 2016.12.16 at 03:27:49 CST
 */
@Component
public class ExtractRefFeature {

    /** 参考文献长度分成段数 */
    private final int k = 5;

    /**
     * Method description
     *
     * @param refs
     * @param filePath
     * @throws IOException
     */
    public void write(List<String> refs, String filePath) throws IOException {

        // 1 输出文件不存在，则创建
        File file = new File(filePath);

        if (!file.exists()) {
            file.createNewFile();
        }

        // 2 参考文献集合为空，返回
        if (CollectionUtils.isEmpty(refs)) {
            return;
        }

        // 3 遍历每个参考文献
        StringBuilder sb=new StringBuilder();
        for (int i = 0; i < refs.size(); i++) {
            String ref = StringUtil.subForeSen(refs.get(i));

            // 如果是中文
            if (StringUtil.isChineseString(ref)) {
                System.out.println(ref);

                // 4 获得参考文献特征
                List<String>  characters = getCharacter(ref);
                List<Integer> positions  = getPosition(ref, k);

                for (int j = 0; j < characters.size(); j++) {
                    String line = characters.get(j) + " " + positions.get(j) + " \n";
                    sb.append(line);
                }
            }
        }

        FileUtils.write(file,sb.toString(),"gbk");
    }

    /**
     * 提取字符串中字符
     *
     * @param ref
     * @return
     */
    private List<String> getCharacter(String ref) {
        List<String> characters = Lists.newArrayList();

        if (StringUtils.isEmpty(ref)) {
            return characters;
        }

        for (int i = 0; i < ref.length(); i++) {
            characters.add(ref.charAt(i) + "");
        }

        return characters;
    }

    /**
     * 获取每个字符在文中位置，共k段
     *
     * @param ref
     * @param k 总共分成k段
     * @return
     */
    private List<Integer> getPosition(String ref, int k) {
        List<Integer> positions = Lists.newArrayList();

        if (StringUtils.isEmpty(ref)) {
            return positions;
        }

        int length = ref.length();

        for (int i = 0; i < ref.length(); i++) {
            positions.add(((int) ((double) i / length * k)));
        }

        return positions;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
