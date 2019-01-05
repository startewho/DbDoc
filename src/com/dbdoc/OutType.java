package com.dbdoc;

/**
 * 导出格式
 * 
 * @author hoolo
 *
 */


public enum OutType {
	WORD("word",".docx" ,1), EXCEL("excel",".xlsx" ,2), MARKDOWN("markdown",".md", 3);
    // 成员变量
    private String name;
    private String extension;
    private int index;

    // 构造方法
    private OutType(String name,String extension,int index) {
        this.name = name;
        this.extension=extension;
        this.index = index;
    }

    
    public String getExtension() {
		return this.extension;
	}
    
    public String getName() {
		return this.name;
	}
    
    public int getIndex() {
		return this.index;
	}
    
 // 覆盖方法
    @Override
    public String toString() {
        return this.name;
    }
    
}
    
