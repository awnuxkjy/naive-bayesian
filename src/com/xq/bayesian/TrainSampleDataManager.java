package com.xq.bayesian;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * <p>Title:TrainSmapleDataManager</p>
 * <p>Description: 训练样本管理器
 * </p>
 * @createDate：2013-8-30
 * @author xq
 * @version 1.0
 */
public class TrainSampleDataManager {
	/**
	 * 训练样本目录
	 */
	private static final String SAMPLE_DATA=NaiveBayesMain.DEFAULT_DIR+"sample/";
	
	/**
	 * 整个训练样本的单词总数,包含重复的单词数
	 */
	private static Long count=0L;
	
	/**
	 * 所有单词数统计Map
	 */
	private static Map<String,Map<String,Map<String,Long>> > allWordsMap=new HashMap<String,Map<String,Map<String,Long>> >();
	
	/**
	 * V是训练样本的单词表(即抽取单词，单词出现多次，只算一个)
	 */
	private static Long kindCount=0L;
	
	/**
	 * 
	* @Title: classWordCount
	* @Description: 类c下单词总数
	* @param @param classifier
	* @param @return    
	* @return Long   
	* @throws
	 */
	public static Long classWordCount(String classifier){
		Map<String,Map<String,Long>> classifierMap=allWordsMap.get(classifier);
		if(classifierMap==null){
			return 0L;
		}
		Set<String> articleSet=classifierMap.keySet();
		Long sum=0L;
		for(String article: articleSet){
			Map<String,Long> articleMap=classifierMap.get(article);
			Set<String> wordsSet=articleMap.keySet();
			for(String words: wordsSet){
				sum+=articleMap.get(words);
			}
		}
		return sum;
	}
	
	/**
	 * 
	* @Title: sampleWordCount
	* @Description: 整个训练样本的单词总数,包含重复的单词数
	* @param @return    
	* @return Long   
	* @throws
	 */
	public static Long sampleWordCount(){
		if(count!=0L){
			return count;
		}
		Set<String> classifierSet=allWordsMap.keySet();
		Long sum=0L;
		for(String classifierName: classifierSet){
			Map<String,Map<String,Long>> classifierMap=allWordsMap.get(classifierName);
			Set<String> articleSet=classifierMap.keySet();
			for(String article: articleSet){
				Map<String,Long> articleMap=classifierMap.get(article);
				Set<String> wordsSet=articleMap.keySet();
				for(String words: wordsSet){
					sum+=articleMap.get(words);
				}
			}
		}
		count=sum;
		return count;
		
	}
	
	/**
	 * 
	* @Title: wordInClassCount
	* @Description: 类c下单词tk在各个文档中出现过的次数之和
	* @param @param word
	* @param @param classifier
	* @param @return    
	* @return Long   
	* @throws
	 */
	public static Long wordInClassCount(String word,String classifier){
		Long sum=0L;
		Map<String,Map<String,Long>> classifierMap=allWordsMap.get(classifier);
		Set<String> articleSet=classifierMap.keySet();
		for(String article: articleSet){
			Map<String,Long> articleMap=classifierMap.get(article);
			Long value=articleMap.get(word);
			if(value!=null && value>0){
				sum+=articleMap.get(word);
			}
			
		}
		return sum;
	}
	
	/**
	 * 
	* @Title: sampleWordKindCount
	* @Description: V是训练样本的单词表(即抽取单词，单词出现多次，只算一个)
	* |V|则表示训练样本包含多少种单词。 
	* @param @return    
	* @return Long   
	* @throws
	 */
    public static Long sampleWordKindCount(){
    	if(kindCount!=0L){
			return kindCount;
		}
		Set<String> classifierSet=allWordsMap.keySet();
		Long sum=0L;
		for(String classifierName: classifierSet){
			Map<String,Map<String,Long>> classifierMap=allWordsMap.get(classifierName);
			Set<String> articleSet=classifierMap.keySet();
			for(String article: articleSet){
				Map<String,Long> articleMap=classifierMap.get(article);
				sum+=articleMap.size();
			}
		}
		kindCount=sum;
		return kindCount;
	}
    
	/**
	 * 
	* @Title: readDirs
	* @Description: 递归获取文件
	* @param @param filepath,fileList
	* @param @return List<String>
	* @param @throws FileNotFoundException
	* @param @throws IOException    
	* @return List<String>   
	* @throws
	 */
    public static List<String> readDirs(String filepath,List<String> fileList) throws FileNotFoundException, IOException {  
    	
        try {  
            File file = new File(filepath);  
            if (!file.isDirectory()) {  
                System.out.println("输入的参数应该为[文件夹名]");  
                System.out.println("filepath: " + file.getAbsolutePath());
                fileList.add(file.getAbsolutePath());
            } else if (file.isDirectory()) {  
                String[] filelist = file.list();  
                for (int i = 0; i < filelist.length; i++) {  
                    File readfile = new File(filepath + File.separator + filelist[i]);  
                    if (!readfile.isDirectory()) {  
                        fileList.add(readfile.getAbsolutePath());  
                    } else if (readfile.isDirectory()) {  
                        readDirs(filepath + File.separator + filelist[i],fileList);  
                    }  
                }  
            }  
  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();
        }  
        return fileList;  
    }
    
    public static List<String> readDirs(File file,List<String> fileList) throws FileNotFoundException, IOException {  
    	String filePah=file.getAbsolutePath();
    	return readDirs(filePah, fileList);
    }
    
    /**
     * 
    * @Title: readFile
    * @Description: 读取文件转化成string
    * @param @param file
    * @param @return String
    * @param @throws FileNotFoundException
    * @param @throws IOException    
    * @return String   
    * @throws
     */
    public static String readFile(String file) throws FileNotFoundException, IOException {  
        StringBuffer sb = new StringBuffer();  
        InputStreamReader is = new InputStreamReader(new FileInputStream(file), "gbk");  
        BufferedReader br = new BufferedReader(is);  
        String line = br.readLine();  
        while (line != null) {  
            sb.append(line).append("\r\n");  
            line = br.readLine();  
        }  
        br.close();  
        return sb.toString();  
    }
    
    /**
     * 
    * @Title: process
    * @Description: 对训练样本进行处理
    * @param     
    * @return void   
    * @throws
     */
    public static void process(){
    	try{
    		File sampleDataDir=new File(SAMPLE_DATA);
    		//得到样本分类目录
    		File[] fileList=sampleDataDir.listFiles();
    		if(fileList==null){
    			throw new IllegalArgumentException("Sample data is not exists!");
    		}
    		for(File file:fileList ){
    			//加载所有该分类下的所有文件名
    			List<String> classFileList=readDirs(file, new ArrayList<String>());
    			for(String article: classFileList){
    				//读取文件内容
    				String content=readFile(article);
    				//es-ik分词
    				Map<String,Long> wordsMap=ChineseTokenizer.segStr(content);
    				if(allWordsMap.containsKey(file.getName())){
    					Map<String,Map<String,Long>> classifierValue=allWordsMap.get(file.getName());
    					classifierValue.put(article, wordsMap);
    					allWordsMap.put(file.getName(), classifierValue);
    				}else{
    					Map<String,Map<String,Long>> classifierValue=new HashMap<String,Map<String,Long>>();
    					classifierValue.put(article, wordsMap);
    					allWordsMap.put(file.getName(), classifierValue);
    				}
    			}
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    /**
     * 
    * @Title: getAllClassifiers
    * @Description: 所有文本分类
    * @param @return    
    * @return Set<String>   
    * @throws
     */
    public static Set<String> getAllClassifiers(){
    	return allWordsMap.keySet();
    }
}
