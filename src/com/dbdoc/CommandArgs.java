/**
 * 
 */
package com.dbdoc;



import picocli.CommandLine.Command;
import picocli.CommandLine.Option;


/**
 * @author hoolo 命令类
 *
 */
@Command(name = "java -jar dbdoc.jar", footer = "Copyright(c) 2019",
description = "A database to doc tool")
public class CommandArgs {

	
	@Option(names = {"-V", "--version"}, versionHelp = true, description = "版本信息")
	public	boolean version;

	@Option(names = {"-h", "--help"}, usageHelp = true, description = "帮助信息")
	public boolean help;

	
	@Option(names= {"-o","--out"},defaultValue="./outdoc",description="输出文档路径")
	private String outPath="./";
	
	
	@Option(names= {"-t","--type"},description="导出格式为:${COMPLETION-CANDIDATES}")
	public OutType outType=OutType.MARKDOWN;
	
	
	/**
	 * 获取输出路径
	 * @return
	 */
	public String getOutPath() {
		
		if (outPath.lastIndexOf('.')<=0) {
			return outPath+outType.getExtension();
		}
		return outPath;
		
	}
}
