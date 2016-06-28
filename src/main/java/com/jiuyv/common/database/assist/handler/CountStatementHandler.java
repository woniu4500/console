package com.jiuyv.common.database.assist.handler;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.Configuration;


public class CountStatementHandler extends BaseStatementHandler {
	
	public CountStatementHandler(Configuration config, String statementId,
			String append, int position) {
		super(config, statementId, append, position);
	}

	public CountStatementHandler(Configuration config, QueryType qtype) {
		super(config, qtype);
	}

	protected void configStatementBuilder(MappedStatement mappedStatement,
			Builder builder) {
		super.configStatementBuilder(mappedStatement, builder);
		List<ResultMap> resultMaps = new ArrayList<ResultMap>();
		ResultMap.Builder rmBuilder = new ResultMap.Builder(mappedStatement.getConfiguration()
				, builder.id() + "-Inline", Long.class, new ArrayList<ResultMapping>());
		resultMaps.add(rmBuilder.build());
		builder.resultMaps(resultMaps);
	}
	
}
