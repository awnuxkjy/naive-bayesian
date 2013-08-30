package com.xq.bayesian;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

/**
 * 
 * <p>Title:NaiveBayesMain</p>
 * <p>Description: 主函数类
 * </p>
 * @createDate：2013-8-30
 * @author xq
 * @version 1.0
 */
public class NaiveBayesMain {
	/**
	 * 默认目录
	 */
	public static final String DEFAULT_DIR="e:/recommend/";
	
	public static void main(String[] args){
		TrainSampleDataManager.process();
		//String s="据叙利亚国家电视台报道，针对西方国家即将发动的军事行动，叙利亚总统巴沙尔·阿萨德29日说，威胁发动敌对行动会让叙利亚更加坚持其原则和决定，如果遭受任何侵略，叙利亚将进行自卫。叙利亚安全部门官员29日称，该国军队已为最坏情况做好准备，称“将采取措施保卫国家，以及该以何种方式回应”。";
		//String s="两名要求匿名的消息人士称，两家公司的谈判已进入后期。另有一名消息人士称，Foursquare也在和其他公司协商投资事宜，而且这家公司未必与微软达成交易。";
		//String s="习近平来到沈阳机床集团。听说企业连续两年经营规模世界第一、职工75%以上是80后，总书记高兴地同“飞阳”团队年轻人攀谈起来";
		//String s="微软 盖茨称：作为接班计划委员会的一名成员，我将紧密与其他成员合作，从而挖掘出一名伟大的新任CEO。在新任CEO上任前，我们很幸运能够看到史蒂夫将继续行使其职责。";
		String s="微软公司提出以446亿美元的价格收购雅虎中国网2月1日报道 美联社消息，微软公司提出以446亿美元现金加股票的价格收购搜索网站雅虎公司。微软提出以每股31美元的价格收购雅虎。微软的收购报价较雅虎1月31日的收盘价19.18美元溢价62%。微软公司称雅虎公司的股东可以选择以现金或股票进行交易。微软和雅虎公司在2006年底和2007年初已在寻求双方合作。而近两年，雅虎一直处于困境：市场份额下滑、运营业绩不佳、股价大幅下跌。对于力图在互联网市场有所作为的微软来说，收购雅虎无疑是一条捷径，因为双方具有非常强的互补性。(小桥)";
		//String s="谷歌之所以向Uber投资2.58亿美元，是为了从该公司的经验中吸取价值，而加盟董事会则是为了让Uber继续专注于无人驾驶出租车，而不要进军送货上门等其他领域。";
		//String s="谷歌，微软是很好的公司，百度和 淘宝也不错";
		Set<String> words=ChineseTokenizer.segStr(s).keySet();
		
		Map<String,BigDecimal> resultMap=MultinomialModelNaiveBayes.classifyResult(DefaultStopWordsHandler.dropStopWords(words));
		Set<String> set=resultMap.keySet();
		for(String str: set){
			System.out.println("classifer:"+str+"     probability:"+resultMap.get(str));
		}
		System.out.println("The final result:"+MultinomialModelNaiveBayes.getClassifyResultName());
	}
}
