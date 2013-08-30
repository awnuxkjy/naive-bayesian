package com.xq.bayesian;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 
 * <p>Title:MultinomialModelNaiveBayes</p>
 * <p>Description: 多项式模型朴素贝叶斯
 * </p>
 * @createDate：2013-8-30
 * @author xq
 * @version 1.0
 */
public class MultinomialModelNaiveBayes {
	/**
	 * 分类结果Map
	 */
	private static Map<String,BigDecimal> classifierResult=new HashMap<String,BigDecimal>();
	
	/**
	 * 放大因子
	 */
	private static BigDecimal zoomFactor = new BigDecimal(10);
	
	/**
	 * 
	* @Title: priorProbability
	* @Description: 多项式朴素贝叶斯先验概率
	* 先验概率P(c)= 类c下单词总数/整个训练样本的单词总数
	* @param classifier   
	* @return BigDecimal   
	* @throws
	 */
	public static BigDecimal priorProbability(String classifier){
		BigDecimal molecular=new BigDecimal(TrainSampleDataManager.classWordCount(classifier));
		BigDecimal denominator=new BigDecimal(TrainSampleDataManager.sampleWordCount());
		return molecular.divide(denominator,10, BigDecimal.ROUND_CEILING);
	}
	
	/**
	 * 
	* @Title: classConditionalProbability
	* @Description: 多项式朴素贝叶斯类条件概率
	* 类条件概率P(tk|c)=(类c下单词tk在各个文档中出现过的次数之和+1)/(类c下单词总数+|V|)
    * V是训练样本的单词表（即抽取单词，单词出现多次，只算一个），
    * |V|则表示训练样本包含多少种单词。 P(tk|c)可以看作是单词tk在证明d属于类c上提供了多大的证据，
    * 而P(c)则可以认为是类别c在整体上占多大比例(有多大可能性)
	* @param @return    
	* @return BigDecimal   
	* @throws
	 */
	public static BigDecimal classConditionalProbability(String classifier,String word){
		BigDecimal molecular=new BigDecimal(TrainSampleDataManager.wordInClassCount(word, classifier)+1);
		BigDecimal denominator=new BigDecimal(TrainSampleDataManager.classWordCount(classifier)+
				TrainSampleDataManager.sampleWordKindCount());
		
		return molecular.divide(denominator,10, BigDecimal.ROUND_CEILING);
	}
	
	/**
	 * 
	* @Title: classifyResult
	* @Description: 多项式朴素贝叶斯分类结果
	* P(C_i|w_1,w_2...w_n) = P(w_1,w_2...w_n|C_i) * P(C_i) / P(w_1,w_2...w_n)
    * = P(w_1|C_i) * P(w_2|C_i)...P(w_n|C_i) * P(C_i) / (P(w_1) * P(w_2) ...P(w_n))
	* @param @return    
	* @return BigDecimal   
	* @throws
	 */
	public static Map<String,BigDecimal> classifyResult(Set<String> words){
		
		Map<String,BigDecimal> resultMap=new HashMap<String,BigDecimal>();
		Set<String> classifierSet=TrainSampleDataManager.getAllClassifiers();
		for(String classifier: classifierSet){
			BigDecimal probability=new BigDecimal(1.0);
			for(String word: words){
				probability=probability.multiply(classConditionalProbability(classifier, word)).multiply(zoomFactor);
			}
			resultMap.put(classifier, probability.multiply(priorProbability(classifier)));
		}
		classifierResult=resultMap;
		return resultMap;
	}
	
	/**
	 * 
	* @Title: getClassifyResultName
	* @Description: 得到最相似的分类
	* @param     
	* @return String   
	* @throws
	 */
	public static String getClassifyResultName(){
		if(classifierResult.isEmpty()){
			return "N/A";
		}
		BigDecimal result=new BigDecimal(0);
		String classifierName="";
		Set<String> classifierSet=classifierResult.keySet();
		for(String classifier : classifierSet){
			BigDecimal classifierValue=classifierResult.get(classifier);
			if(classifierValue.compareTo(result)>=1){
				result=classifierValue;
				classifierName=classifier;
			}
		}
		return classifierName;
	}
}
