package com.xq.bayesian;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.LinkedHashMap;
import java.util.Map;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

/**
 * 
 * <p>Title:ChineseTokenizer</p>
 * <p>Description: 中文分词,采用es-ik实现
 * </p>
 * @createDate：2013-8-30
 * @author xq
 * @version 1.0
 */
public class ChineseTokenizer {
    /**
     * 
    * @Title: segStr
    * @Description: 返回LinkedHashMap的分词
    * @param @param content
    * @param @return    
    * @return Map<String,Integer>   
    * @throws
     */
    public static Map<String, Long> segStr(String content){
        // 分词
        Reader input = new StringReader(content);
        // 智能分词关闭（对分词的精度影响很大）
        IKSegmenter iks = new IKSegmenter(input, true);
        Lexeme lexeme = null;
        Map<String, Long> words = new LinkedHashMap<String, Long>();
        try {
            while ((lexeme = iks.next()) != null) {
                if (words.containsKey(lexeme.getLexemeText())) {
                    words.put(lexeme.getLexemeText(), words.get(lexeme.getLexemeText()) + 1);
                } else {
                    words.put(lexeme.getLexemeText(), 1L);
                }
            }
        }catch(IOException e) {
            e.printStackTrace();
        }
        return words;
    }
}
