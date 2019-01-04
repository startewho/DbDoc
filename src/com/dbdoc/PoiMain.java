package com.dbdoc;

import java.nio.file.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import org.apache.log4j.Logger;
import org.apache.poi.xwpf.usermodel.*;
import com.dbdoc.db.model.Column;
import com.dbdoc.db.model.Table;
import com.dbdoc.db.model.provider.TableProvider;

import picocli.CommandLine;


/***
 * 
 * 数据库设计文档生成器
 * 
 * @author kevin zhang
 *
 * @date 2011-11-23
 */
/**
 * @author hoolo
 *
 */
public class PoiMain {
	public static final Logger log = Logger.getLogger(PoiMain.class);
	public static final String template_file = "template/doc.xml";
	public static final String out_dir = "c:\\doc\\dbdoc.docx";

	
	public static void main(String args[]) throws IOException {
		
		CommandArgs command = CommandLine.populateCommand(new CommandArgs(), args);
		
		if (command.help) {
			   CommandLine.usage(new CommandArgs(), System.out);
			   return;
		}
		if (command.version) {
			System.out.print("Version:0.0.1");
			return;
		}
		
		try {
			List<Table> tables = TableProvider.getInstance().getAllTables();
			genDoc(command.outPath, tables);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("生成完毕!!!");
		// 打开生成的文件
		//Runtime.getRuntime().exec("cmd.exe /c start " + out_dir);
	}
	
	
	
	/**生成word
	 * @param filePath
	 * @return
	 */
	public static void genDoc(String filePath,List<Table> dbTables)
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
	public static boolean genTable(XWPFDocument doc,Table dbtable) {
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

