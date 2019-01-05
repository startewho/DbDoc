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
import picocli.CommandLine.Help.Ansi.Style;
import picocli.CommandLine.Help.ColorScheme;


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
public class App {
	public static final Logger log = Logger.getLogger(App.class);
	
	public static void main(String args[]) throws IOException {
		
		CommandArgs command = CommandLine.populateCommand(new CommandArgs(), args);
		
		if (command.help) {
			ColorScheme colorScheme = new ColorScheme()
			        .commands    (Style.bold, Style.underline)    // combine multiple styles
			        .options     (Style.fg_yellow)                // yellow foreground color
			        .parameters  (Style.fg_yellow)
			        .optionParams(Style.italic);

			   CommandLine.usage(new CommandArgs(), System.out,colorScheme);
			   return;
		}
		if (command.version) {
			System.out.print("Version:0.0.1");
			return;
		}
		
		try {
			
			genOutput(command);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("生成完毕!!!");
		// 打开生成的文件
		//Runtime.getRuntime().exec("cmd.exe /c start " + out_dir);
	}
	
	
	public static void genOutput(CommandArgs args) throws SQLException {
		DocOutput output=null;
		
		List<Table> tables = TableProvider.getInstance().getAllTables();
		
		switch (args.outType) {
		case WORD:
			output=new WordOutput();
			break;
		case EXCEL:
			output=new ExcelOutput();
			break;
		case MARKDOWN:
			output=new MarkdownOutput();
			break;
		
		default:
			break;
		}
		
		if (output!=null) {
			output.genDoc(args.getOutPath(), tables);
		}
	}
	
	
	
	
}

