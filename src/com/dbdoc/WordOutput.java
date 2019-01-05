package com.dbdoc;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
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
 * word文档输出(.docx)
 * @author hoolo
 *
 */
public class WordOutput extends DocOutput {

	public WordOutput() {
		super(OutType.WORD);
		// TODO Auto-generated constructor stub
	}
	
	/**生成word
	 * @param filePath
	 * @return
	 */
	@Override
	public  void genDoc(String filePath,List<Table> dbTables)
	{
		
		
		 XWPFDocument doc = new XWPFDocument();
		 
		 for (Table table : dbTables) {
			genTable(doc, table);
			
		}
		
	        try {
	        	if (Files.exists(Paths.get(filePath),LinkOption.NOFOLLOW_LINKS)) {
					Files.delete(Paths.get(filePath));
				}
	        	
	        	FileOutputStream out;
	            out = new FileOutputStream(filePath);
	            doc.write(out);
	            out.close();
	            doc.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	}


	/**
	 * 根据数据库表生成Word中的Table
	 * @param doc 
	 * @param dbtable
	 * @return
	 */
	public  boolean genTable(XWPFDocument doc,Table dbtable) {
		boolean gened=false;
		
		//创建标题段落
		XWPFParagraph paragraph = doc.createParagraph();
		paragraph.setIndentationLeft(0);
		paragraph.setIndentationHanging(0);
		paragraph.setAlignment(ParagraphAlignment.LEFT);
		
		
		XWPFRun run = paragraph.insertNewRun(0);
		run.setText("表名:"+ dbtable.getName()+"\r\n");
		run.setFontFamily("宋体");
		
		//创建表格
		Column[] columns= dbtable.getColumns().toArray(new Column[0]);
		int row=columns.length;
		
		XWPFTable wordTable = doc.createTable(columns.length,7);
		for (int i = 0; i < row; i++) {
			 
			 List<XWPFTableCell> tableCells = wordTable.getRow(i).getTableCells();
			 
				 if(i==0) {
					 tableCells.get(0).setText("名称");
					 tableCells.get(1).setText("类型");
					 tableCells.get(2).setText("默认值");
					 tableCells.get(3).setText("可空");
					 tableCells.get(4).setText("主键");
					 tableCells.get(5).setText("外键");
					 tableCells.get(6).setText("备注");
				 }
				 else {
					 tableCells.get(0).setText(columns[i].get_sqlName());
					 tableCells.get(1).setText(columns[i].get_sqlTypeName());
					 tableCells.get(2).setText(String.valueOf(columns[i].get_defaultValue()));
					 tableCells.get(3).setText(String.valueOf(columns[i].is_isNullable()));
					 tableCells.get(4).setText(String.valueOf(columns[i].is_isPk()));
					 tableCells.get(5).setText(String.valueOf(columns[i].is_isFk()));
					 tableCells.get(6).setText(columns[i].get_remarks().replace("\r\n", ""));
					
				}
			}
			 
			return gened;
		}

}
