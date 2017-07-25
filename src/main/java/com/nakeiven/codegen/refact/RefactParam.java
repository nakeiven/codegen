/*
 * 文件名称: RefactParam.java
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

/**
 * 重构参数
 * @author zhangyz created on 2015-2-4
 */
public class RefactParam {
    private String dirRoot;//根目录
    private String[] suffixs;//允许的扩展名
    
    private String origionCode = "UTF-8";//原来的编码
    private String destCode = "UTF-8";//转换后的编码
    private String[] origionPackName;//原来的包名
    private String[] destPackName;//后来的包名
    private int minLen = 0;
    
    public RefactParam(String dirRoot, String[] origionPackName, String[] destPackName) {
        super();
        this.dirRoot = dirRoot;
        this.origionPackName = origionPackName;
        this.destPackName = destPackName;
        initMinLen();
    }

    public RefactParam(String dirRoot, String origionCode, 
            String destCode, String[] origionPackName,
            String[] destPackName) {
        super();
        this.dirRoot = dirRoot;
        this.origionCode = origionCode;
        this.destCode = destCode;
        this.origionPackName = origionPackName;
        this.destPackName = destPackName;
        initMinLen();
    }

    private void initMinLen() {
        if (origionPackName == null)
            return;
        //计算被替换字符的最小长度，加快速度。
        for (String item : origionPackName) {
            if (item.length() > minLen)
                minLen = item.length();
        }
    }

    public String[] getSuffixs() {
        return suffixs;
    }
    
    public void setSuffixs(String[] suffixs) {
        this.suffixs = suffixs;
    }
    
    /**
     * 判断是否允许指定的扩展名
     * @param suffixName
     * @return
     * @author zhangyz created on 2015-2-4
     */
    public boolean allowSuffix(String suffixName) {
        if (suffixs == null)
            return true;
        for (String s : suffixs) {
            if (s.equals(suffixName))
                return true;
        }
        return false;
    }

    public String getDirRoot() {
        return dirRoot;
    }
    
    public void setDirRoot(String dirRoot) {
        this.dirRoot = dirRoot;
    }
    
    public String getOrigionCode() {
        return origionCode;
    }
    
    public void setOrigionCode(String origionCode) {
        this.origionCode = origionCode;
    }
    
    public String getDestCode() {
        return destCode;
    }
    
    public void setDestCode(String destCode) {
        this.destCode = destCode;
    }
    
    public String[] getOrigionPackName() {
        return origionPackName;
    }
    
    public void setOrigionPackName(String[] origionPackName) {
        this.origionPackName = origionPackName;
    }
    
    public String[] getDestPackName() {
        return destPackName;
    }
    
    public void setDestPackName(String[] destPackName) {
        this.destPackName = destPackName;
    }

    public int getMinLen() {
        return minLen;
    }

}
