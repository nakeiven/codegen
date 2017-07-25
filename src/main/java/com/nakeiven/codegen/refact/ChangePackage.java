/*
 * 文件名称: ChangePackage.java
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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import org.apache.commons.lang3.StringUtils;

/**
 * 包重构工具插件
 * @author zhangyz created on 2015-2-4
 */
public class ChangePackage implements ProcessPlugIn {

    public void process(File origionFile, BufferedReader reader, RefactParam param) throws Exception {
        File destFile = new File(origionFile.getParentFile(), origionFile.getName() + ".tmp"); 
        OutputStreamWriter ws = new OutputStreamWriter(new FileOutputStream(destFile), param.getDestCode());
        BufferedWriter writer = new BufferedWriter(ws);            
        do {
            String line = reader.readLine();   
            if (line == null)
                break;
            if (line.length() >= param.getMinLen() && param.getOrigionPackName() != null 
                    && param.getOrigionPackName().length > 0) {
                line = StringUtils.replaceEach(line, 
                        param.getOrigionPackName(), param.getDestPackName());
            }
            writer.write(line);
            writer.write("\r\n");
        }
        while (true);            
        writer.close();
        destFile.renameTo(origionFile);
        System.out.println(origionFile.getName() + "处理成功!");        
    }

    public void processAfter(File origionFile, RefactParam param)  throws Exception {
        //改名
        origionFile.delete();
    }

    public void processAllAfter(RefactParam param) {
        System.out.println("全部处理结束!请修改包目录路径名...................");
    }    

}
