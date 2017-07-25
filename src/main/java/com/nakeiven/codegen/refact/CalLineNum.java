/*
 * 文件名称: CalLineNum.java
 * 版权信息: Copyright 2013-2015 chunchen technology Co., LTD. All right reserved.
 * ----------------------------------------------------------------------------------------------
 * 修改历史:
 * ----------------------------------------------------------------------------------------------
 * 修改原因: 新增
 * 修改人员: zhangyz
 * 修改日期: 2015-2-4
 * 修改内容: 
 */
package com.nakeiven.codegen.refact;

import java.io.BufferedReader;
import java.io.File;

/**
 * 统计代码行
 * @author zhangyz created on 2015-2-4
 */
public class CalLineNum implements ProcessPlugIn{
    private int lastLine = 0;
    private int totalLine = 0;//总行数
    private int fileNum = 0;//总文件数
    private int maxLine = 0;//最大的文件大小
    private String maxFileName = null; //最大文件名    
    
    private String[] fileNameAvoidTokens = new String[] {"\\classes\\", "\\test\\"};//文件名和目录中出现该字符则跳过  
    //, "\\build\\", "\\.idea\\"
    private String[] lineAvoidTokens = new String[] {"import", "/*", "*/"};//代码行中出现该字符跳过
 
    public void process(File origionFile, BufferedReader reader, RefactParam param) throws Exception {
        lastLine = 0;
        
        //先判断该文件是否符合条件
        String fileName = origionFile.getAbsolutePath();
        for (String ft : fileNameAvoidTokens) {
            if (fileName.indexOf(ft) > 0)
                return;
        }
        
        do {
            String line = reader.readLine();   
            if (line == null)
                break;
            
            line = line.trim();
            if (line.length() == 0)
                continue;
            
            boolean lindValid = true;
            for (String lt : lineAvoidTokens) {
                if (line.startsWith(lt)) {
                    lindValid = false;
                    break;
                }
            }
            if (!lindValid)
                continue;
            
            //有效代码行，加一
            lastLine++;
        }
        while (true);
        if (lastLine > 0) {
            fileNum ++;
            totalLine += lastLine;
            if (maxLine < lastLine) {
                maxLine = lastLine;
                maxFileName = fileName;
            }
        }
    }

    public void processAfter(File origionFile, RefactParam param) throws Exception {
        if (lastLine > 0)
            System.out.println(origionFile.getAbsolutePath() + "行数:" + lastLine);        
    }

    public void processAllAfter(RefactParam param) {
        System.out.println("总行数:" + totalLine);
        System.out.println("总文件数:" + fileNum);
        System.out.println("文件最大行数:" + maxLine);
        System.out.println("最大文件名:" + maxFileName);
    }
}
