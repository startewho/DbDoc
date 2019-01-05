package com.dbdoc;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.cert.PKIXRevocationChecker.Option;
import java.util.List;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;

import com.dbdoc.db.model.Column;
import com.dbdoc.db.model.Table;


/**
 * word文档输出(.md)
 * @author hoolo
 *
 */
public class MarkdownOutput extends DocOutput {

	public MarkdownOutput() {
		super(OutType.MARKDOWN);
		// TODO Auto-generated constructor stub
	}
	
	/**生成markdown
	 * @param filePath
	 * @return
	 */
	@Override
	public  void genDoc(String filePath,List<Table> dbTables)
	{
		
		
	        try {
	        	if (Files.exists(Paths.get(filePath),LinkOption.NOFOLLOW_LINKS)) {
					Files.delete(Paths.get(filePath));
				}
	        	
	        	
	        	StringBuilder contextBuilder=new StringBuilder("[TOC]\r\n");
	        	
	        	for (Table table : dbTables) {
	    			genTable(contextBuilder, table);
	    			
	    		}
	        	
	        	byte[] buffer=contextBuilder.toString().getBytes("UTF-8");
	        	
	            Files.write(Paths.get(filePath), buffer, StandardOpenOption.CREATE);
	            
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	}

/**
 * 
 * @param strBuilder
 * @param dbtable
 * @return
 */
	public  boolean genTable(StringBuilder strBuilder,Table dbtable) {
		boolean gened=false;
		
		
		strBuilder.append("#表名:"+ dbtable.getName()+"\r\n");
		
		
		//创建表格
		Column[] columns= dbtable.getColumns().toArray(new Column[0]);
		int row=columns.length;
		
		
		for (int i = 0; i < row; i++) {
			 
				 if(i==0) {
					 
					 strBuilder.append("|名称|类型|默认值|可空|主键|外键|备注|\r\n");
					 strBuilder.append("|--|--|--|--|--|--|--|\r\n");
					 
				 }
				 else {
					 
					 strBuilder.append(String.format("|%s|%s|%s|%s|%s|%s|%s|\r\n",
					 columns[i].get_sqlName(),
					 columns[i].get_sqlTypeName(),
					 String.valueOf(columns[i].get_defaultValue()),
					 String.valueOf(columns[i].is_isNullable()),
					 String.valueOf(columns[i].is_isPk()),
					 String.valueOf(columns[i].is_isFk()),
					 columns[i].get_remarks().replace("\r\n", "")));
					
				}
			}
			 
			return gened;
		}

}
