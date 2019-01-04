/**
 * 
 */
package com.dbdoc;

import org.omg.CORBA.PRIVATE_MEMBER;

import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

/**
 * @author hoolo 命令类
 *
 */
public class CommandArgs {

	
	@Option(names = {"-V", "--version"}, versionHelp = true, description = "关于版本")
	public	boolean version;
	
	@Option(names = {"-h", "--help"}, usageHelp = true, description = "帮助信息")
	public boolean help;

	
	@Option(names= {"-o","--out"},defaultValue="./out.docx",description="输出文档路径")
	public String outPath="./";
	
	
	@Option(names= {"-t","--type"},defaultValue="1",description="1-Word(docx)\\r\\n2-Excel(xlsx)")
	public int outType=1;
}
