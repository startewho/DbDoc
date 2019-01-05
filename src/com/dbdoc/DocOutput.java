package com.dbdoc;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

import org.apache.log4j.Logger;

import com.dbdoc.db.model.Table;
import com.dbdoc.db.model.provider.TableProvider;
import com.dbdoc.utils.FreemarkerUtils;

/***
 * 
 * 文档输出
 * 
 * @author justcheng
 *
 * @date 2019-01-05
 */
public abstract class DocOutput {

	private OutType type = OutType.WORD;

	public DocOutput(OutType outType) {
			type=outType;
	}

	public OutType geType() {
		return type;

	}

	
	public abstract void genDoc(String filePath, List<Table> dbTables);
}


