/*
 * 文件名称: ProcessPlugIn.java
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
 * 文件处理工具插件
 * @author zhangyz created on 2015-2-4
 */
public interface ProcessPlugIn {
    /**
     * 对指定的文件进行处理
     * @param origionFile 文件对象
     * @param reader 文件流读取，由外界释放
     * @param param 处理参数
     * @author zhangyz created on 2015-2-4
     */
    public void process(File origionFile, BufferedReader reader, RefactParam param)  throws Exception;
    
    /**
     * 成功处理后某个文件执行
     * @param origionFile
     * @param param
     * @throws Exception
     * @author zhangyz created on 2015-2-4
     */
    public void processAfter(File origionFile, RefactParam param) throws Exception;
    
    /**
     * 全部处理完回调
     * @param param
     * @author zhangyz created on 2015-2-4
     */
    public void processAllAfter(RefactParam param);
}
