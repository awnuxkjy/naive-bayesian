package com.xq.bayesian;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * <p>Title:DefaultStopWordsHandler</p>
 * <p>Description: 是否为停用词检测
 * </p>
 * @createDate：2013-8-30
 * @author xq
 * @version 1.0
 */
public class DefaultStopWordsHandler {
	/**
	 * 常用停用词
	 */
	private static Set<String> stopWordsSet =new HashSet<String>();
	static {
		stopWordsSet.add("的");
		stopWordsSet.add("我们");
		stopWordsSet.add("要");
		stopWordsSet.add("自己");
		stopWordsSet.add("之");
		stopWordsSet.add("将");
		stopWordsSet.add("后");
		stopWordsSet.add("应");
		stopWordsSet.add("到");
		stopWordsSet.add("某");
		stopWordsSet.add("某");
		stopWordsSet.add("后");
		stopWordsSet.add("个");
		stopWordsSet.add("是");
		stopWordsSet.add("位");
		stopWordsSet.add("新");
		stopWordsSet.add("一");
		stopWordsSet.add("两");
		stopWordsSet.add("中");
		stopWordsSet.add("或");
		stopWordsSet.add("有");
		stopWordsSet.add("更");
		stopWordsSet.add("好");
		stopWordsSet.add(" ");
		stopWordsSet.add(",");
		stopWordsSet.add("“");
		stopWordsSet.add("”");
		stopWordsSet.add("。");
		stopWordsSet.add(".");
	}
	
	/**
	 * 
	* @Title: isStopWord
	* @Description: 检查单词是否为停用词
	* @param @param word
	* @param @return    
	* @return boolean   
	* @throws
	 */
	public static boolean isStopWord(String word)
	{
		return stopWordsSet.contains(word);
	}
	
	/**
	* 去掉停用词
	* @param text 给定的文本
	* @return 去停用词后结果
	*/
	public static Set<String> dropStopWords(Set<String> oldWords){
		Set<String> set = new HashSet<String>();
		for(String word: oldWords)		{
			if(DefaultStopWordsHandler.isStopWord(word)==false){
				//不是停用词
				set.add(word);
			}
		}
		return set;
	}
}
