package com.quqi.impl;

import java.io.File;

import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import com.quqi.impl.entity.wifi.User;
import com.quqi.impl.entity.wifi.UserAuthCode;
import com.quqi.impl.entity.wifi.UserTracking;

public class GenerateDDL {

	public static void main(String[] args) {
		try {
			Configuration cfg = new Configuration();
			cfg.setProperty(AvailableSettings.DIALECT, "org.hibernate.dialect.PostgreSQL9Dialect");
			cfg.setProperty(AvailableSettings.DRIVER, "org.postgresql.Driver");
			cfg.setProperty(AvailableSettings.URL, "jdbc:postgresql://localhost:5432/yoda");
			cfg.setProperty(AvailableSettings.USER, "admin");
			cfg.setProperty(AvailableSettings.PASS, "admin");
			cfg.addAnnotatedClass(User.class);
			cfg.addAnnotatedClass(UserTracking.class);
			cfg.addAnnotatedClass(UserAuthCode.class);
			SchemaExport export = new SchemaExport(cfg);
			
			export.setFormat(true);
			export.setDelimiter(";");
			export.setOutputFile(
					new File("/Users/runiu/workspaces/workspace_personal/quqi/impl/src/main/resources/ddl.sql")
							.getAbsolutePath());
			export.create(true, false);
		} finally {
			System.exit(0);
		}
	}
}
