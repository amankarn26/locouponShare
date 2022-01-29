package com.locoupon.common.dao;

import java.io.StringWriter;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.locoupon.common.entity.MailRequest;
import com.locoupon.common.model.MailContentData;

@Service
public class EmailTemplateDao {

	private Logger logger = LoggerFactory.getLogger(EmailTemplateDao.class);

	private VelocityEngine ve;

	@PostConstruct
	public void init() {
		ve = new VelocityEngine();
		Properties p = new Properties();
		p.setProperty(RuntimeConstants.RESOURCE_LOADER, "class");

		p.setProperty("class.resource.loader.class", ClasspathResourceLoader.class.getName());
		p.setProperty(RuntimeConstants.RUNTIME_LOG, System.getProperty("catalina.base") + "/logs/velocity.log");

		try {
			ve.init(p);
		} catch (Exception ex) {
			logger.debug("Exception : ", ex);
		}

	}

	public String getTemplate(String templateId, MailRequest request) throws Exception {
		if (ve != null) {
			LinkedHashSet<String> tablecols = new LinkedHashSet<String>();
			LinkedHashSet<String> costcols = new LinkedHashSet<String>();
			Template t = ve.getTemplate("/templates/" + templateId + ".vm");
			//logger.debug("Template : " + t.getName());
			VelocityContext context = new VelocityContext();
			MailContentData contentData = request.getContentData();

			if (contentData.getTable() != null) {
				List<Map> tableData = contentData.getTable();

				for (Map map : tableData) {
					// logger.debug("Map Keys : "+map.keySet());
					tablecols.addAll(map.keySet());
				}

				//logger.debug("Adding cols : " + tablecols.toString());
				context.put("cols", tablecols);
				context.put("table", tableData);
			}

			if (contentData.getCosttable() != null) {
				List<Map> costtabledata = contentData.getCosttable();
				for (Map cmap : costtabledata) {

					// logger.debug("CMap Keys : "+cmap.keySet());
					costcols.addAll(cmap.keySet());
				}

				//logger.debug("Adding costcols : " + costcols.toString());
				context.put("costcols", costcols);
				context.put("costtable", costtabledata);
			}

			if (contentData.getContent() != null) {
				Map<String, String> content = contentData.getContent();
				for (String key : content.keySet()) {
					context.put(key, content.get(key));
					//logger.debug("Adding content : " + content.get(key));
				}
			}
			/*
			 * if(request.getTableData() != null){ for(Map map: request.getTableData()){
			 * cols.addAll(map.keySet()); } context.put("cols",cols);
			 * context.put("table",request.getTableData()); } if(request.getContentMap() !=
			 * null){ Map<String,String> content = request.getContentMap(); for(String key:
			 * content.keySet()){ context.put(key,content.get(key)); } }
			 */

			StringWriter writer = new StringWriter();
			t.merge(context, writer);
			return writer.toString();
		}
		return null;
	}

}
