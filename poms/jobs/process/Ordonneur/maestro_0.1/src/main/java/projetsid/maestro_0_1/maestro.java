// ============================================================================
//
// Copyright (c) 2006-2015, Talend SA
//
// Ce code source a été automatiquement généré par_Talend Open Studio for Data Integration
// / Soumis à la Licence Apache, Version 2.0 (la "Licence") ;
// votre utilisation de ce fichier doit respecter les termes de la Licence.
// Vous pouvez obtenir une copie de la Licence sur
// http://www.apache.org/licenses/LICENSE-2.0
// 
// Sauf lorsqu'explicitement prévu par la loi en vigueur ou accepté par écrit, le logiciel
// distribué sous la Licence est distribué "TEL QUEL",
// SANS GARANTIE OU CONDITION D'AUCUNE SORTE, expresse ou implicite.
// Consultez la Licence pour connaître la terminologie spécifique régissant les autorisations et
// les limites prévues par la Licence.

package projetsid.maestro_0_1;

import routines.Numeric;
import routines.DataOperation;
import routines.TalendDataGenerator;
import routines.TalendStringUtil;
import routines.TalendString;
import routines.StringHandling;
import routines.Relational;
import routines.TalendDate;
import routines.Mathematical;
import routines.system.*;
import routines.system.api.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.math.BigDecimal;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.util.Comparator;

@SuppressWarnings("unused")

/**
 * Job: maestro Purpose: Ordonneur des jobs<br>
 * Description: <br>
 * 
 * @author user@talend.com
 * @version 8.0.1.20211109_1610
 * @status
 */
public class maestro implements TalendJob {

	protected static void logIgnoredError(String message, Throwable cause) {
		System.err.println(message);
		if (cause != null) {
			cause.printStackTrace();
		}

	}

	public final Object obj = new Object();

	// for transmiting parameters purpose
	private Object valueObject = null;

	public Object getValueObject() {
		return this.valueObject;
	}

	public void setValueObject(Object valueObject) {
		this.valueObject = valueObject;
	}

	private final static String defaultCharset = java.nio.charset.Charset.defaultCharset().name();

	private final static String utf8Charset = "UTF-8";

	// contains type for every context property
	public class PropertiesWithType extends java.util.Properties {
		private static final long serialVersionUID = 1L;
		private java.util.Map<String, String> propertyTypes = new java.util.HashMap<>();

		public PropertiesWithType(java.util.Properties properties) {
			super(properties);
		}

		public PropertiesWithType() {
			super();
		}

		public void setContextType(String key, String type) {
			propertyTypes.put(key, type);
		}

		public String getContextType(String key) {
			return propertyTypes.get(key);
		}
	}

	// create and load default properties
	private java.util.Properties defaultProps = new java.util.Properties();

	// create application properties with default
	public class ContextProperties extends PropertiesWithType {

		private static final long serialVersionUID = 1L;

		public ContextProperties(java.util.Properties properties) {
			super(properties);
		}

		public ContextProperties() {
			super();
		}

		public void synchronizeContext() {

			if (folderName != null) {

				this.setProperty("folderName", folderName.toString());

			}

		}

		// if the stored or passed value is "<TALEND_NULL>" string, it mean null
		public String getStringValue(String key) {
			String origin_value = this.getProperty(key);
			if (NULL_VALUE_EXPRESSION_IN_COMMAND_STRING_FOR_CHILD_JOB_ONLY.equals(origin_value)) {
				return null;
			}
			return origin_value;
		}

		public String folderName;

		public String getFolderName() {
			return this.folderName;
		}
	}

	protected ContextProperties context = new ContextProperties(); // will be instanciated by MS.

	public ContextProperties getContext() {
		return this.context;
	}

	private final String jobVersion = "0.1";
	private final String jobName = "maestro";
	private final String projectName = "PROJETSID";
	public Integer errorCode = null;
	private String currentComponent = "";

	private final java.util.Map<String, Object> globalMap = new java.util.HashMap<String, Object>();
	private final static java.util.Map<String, Object> junitGlobalMap = new java.util.HashMap<String, Object>();

	private final java.util.Map<String, Long> start_Hash = new java.util.HashMap<String, Long>();
	private final java.util.Map<String, Long> end_Hash = new java.util.HashMap<String, Long>();
	private final java.util.Map<String, Boolean> ok_Hash = new java.util.HashMap<String, Boolean>();
	public final java.util.List<String[]> globalBuffer = new java.util.ArrayList<String[]>();

	private RunStat runStat = new RunStat();

	// OSGi DataSource
	private final static String KEY_DB_DATASOURCES = "KEY_DB_DATASOURCES";

	private final static String KEY_DB_DATASOURCES_RAW = "KEY_DB_DATASOURCES_RAW";

	public void setDataSources(java.util.Map<String, javax.sql.DataSource> dataSources) {
		java.util.Map<String, routines.system.TalendDataSource> talendDataSources = new java.util.HashMap<String, routines.system.TalendDataSource>();
		for (java.util.Map.Entry<String, javax.sql.DataSource> dataSourceEntry : dataSources.entrySet()) {
			talendDataSources.put(dataSourceEntry.getKey(),
					new routines.system.TalendDataSource(dataSourceEntry.getValue()));
		}
		globalMap.put(KEY_DB_DATASOURCES, talendDataSources);
		globalMap.put(KEY_DB_DATASOURCES_RAW, new java.util.HashMap<String, javax.sql.DataSource>(dataSources));
	}

	public void setDataSourceReferences(List serviceReferences) throws Exception {

		java.util.Map<String, routines.system.TalendDataSource> talendDataSources = new java.util.HashMap<String, routines.system.TalendDataSource>();
		java.util.Map<String, javax.sql.DataSource> dataSources = new java.util.HashMap<String, javax.sql.DataSource>();

		for (java.util.Map.Entry<String, javax.sql.DataSource> entry : BundleUtils
				.getServices(serviceReferences, javax.sql.DataSource.class).entrySet()) {
			dataSources.put(entry.getKey(), entry.getValue());
			talendDataSources.put(entry.getKey(), new routines.system.TalendDataSource(entry.getValue()));
		}

		globalMap.put(KEY_DB_DATASOURCES, talendDataSources);
		globalMap.put(KEY_DB_DATASOURCES_RAW, new java.util.HashMap<String, javax.sql.DataSource>(dataSources));
	}

	private final java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
	private final java.io.PrintStream errorMessagePS = new java.io.PrintStream(new java.io.BufferedOutputStream(baos));

	public String getExceptionStackTrace() {
		if ("failure".equals(this.getStatus())) {
			errorMessagePS.flush();
			return baos.toString();
		}
		return null;
	}

	private Exception exception;

	public Exception getException() {
		if ("failure".equals(this.getStatus())) {
			return this.exception;
		}
		return null;
	}

	private class TalendException extends Exception {

		private static final long serialVersionUID = 1L;

		private java.util.Map<String, Object> globalMap = null;
		private Exception e = null;
		private String currentComponent = null;
		private String virtualComponentName = null;

		public void setVirtualComponentName(String virtualComponentName) {
			this.virtualComponentName = virtualComponentName;
		}

		private TalendException(Exception e, String errorComponent, final java.util.Map<String, Object> globalMap) {
			this.currentComponent = errorComponent;
			this.globalMap = globalMap;
			this.e = e;
		}

		public Exception getException() {
			return this.e;
		}

		public String getCurrentComponent() {
			return this.currentComponent;
		}

		public String getExceptionCauseMessage(Exception e) {
			Throwable cause = e;
			String message = null;
			int i = 10;
			while (null != cause && 0 < i--) {
				message = cause.getMessage();
				if (null == message) {
					cause = cause.getCause();
				} else {
					break;
				}
			}
			if (null == message) {
				message = e.getClass().getName();
			}
			return message;
		}

		@Override
		public void printStackTrace() {
			if (!(e instanceof TalendException || e instanceof TDieException)) {
				if (virtualComponentName != null && currentComponent.indexOf(virtualComponentName + "_") == 0) {
					globalMap.put(virtualComponentName + "_ERROR_MESSAGE", getExceptionCauseMessage(e));
				}
				globalMap.put(currentComponent + "_ERROR_MESSAGE", getExceptionCauseMessage(e));
				System.err.println("Exception in component " + currentComponent + " (" + jobName + ")");
			}
			if (!(e instanceof TDieException)) {
				if (e instanceof TalendException) {
					e.printStackTrace();
				} else {
					e.printStackTrace();
					e.printStackTrace(errorMessagePS);
					maestro.this.exception = e;
				}
			}
			if (!(e instanceof TalendException)) {
				try {
					for (java.lang.reflect.Method m : this.getClass().getEnclosingClass().getMethods()) {
						if (m.getName().compareTo(currentComponent + "_error") == 0) {
							m.invoke(maestro.this, new Object[] { e, currentComponent, globalMap });
							break;
						}
					}

					if (!(e instanceof TDieException)) {
					}
				} catch (Exception e) {
					this.e.printStackTrace();
				}
			}
		}
	}

	public void tPostjob_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tPostjob_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tChronometerStop_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tChronometerStop_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tPrejob_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tPrejob_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tChronometerStart_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tChronometerStart_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tRunJob_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tRunJob_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tChronometerStop_2_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tChronometerStop_2_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tChronometerStart_2_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tChronometerStart_2_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tRunJob_2_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tRunJob_2_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tRunJob_3_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tRunJob_3_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tChronometerStop_3_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tChronometerStop_3_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tChronometerStart_3_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tChronometerStart_3_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tRunJob_4_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tRunJob_4_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tRunJob_5_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tRunJob_5_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tChronometerStop_4_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tChronometerStop_4_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tChronometerStart_4_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tChronometerStart_4_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tRunJob_6_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tRunJob_6_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tRunJob_7_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tRunJob_7_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tChronometerStop_5_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tChronometerStop_5_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tChronometerStart_5_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tChronometerStart_5_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tRunJob_8_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tRunJob_8_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tRunJob_9_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tRunJob_9_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tChronometerStop_6_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tChronometerStop_6_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tChronometerStart_6_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tChronometerStart_6_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tRunJob_10_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tRunJob_10_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tRunJob_11_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tRunJob_11_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tChronometerStop_7_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tChronometerStop_7_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tChronometerStart_7_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tChronometerStart_7_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tRunJob_13_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tRunJob_13_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tChronometerStop_8_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tChronometerStop_8_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tChronometerStart_8_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tChronometerStart_8_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tRunJob_15_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tRunJob_15_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tChronometerStop_9_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tChronometerStop_9_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tChronometerStart_9_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tChronometerStart_9_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tRunJob_16_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tRunJob_16_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tRunJob_17_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tRunJob_17_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tChronometerStop_10_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tChronometerStop_10_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tChronometerStart_10_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tChronometerStart_10_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tRunJob_18_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tRunJob_18_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tRunJob_19_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tRunJob_19_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tChronometerStop_11_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tChronometerStop_11_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tChronometerStart_11_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tChronometerStart_11_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tRunJob_20_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tRunJob_20_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tRunJob_21_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tRunJob_21_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tChronometerStop_12_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tChronometerStop_12_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tChronometerStart_12_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tChronometerStart_12_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tRunJob_22_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tRunJob_22_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tRunJob_23_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tRunJob_23_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tChronometerStop_13_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tChronometerStop_13_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tPostjob_1_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tChronometerStop_1_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tPrejob_1_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tChronometerStart_1_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tRunJob_1_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tChronometerStop_2_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tChronometerStart_2_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tRunJob_2_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tRunJob_3_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tChronometerStop_3_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tChronometerStart_3_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tRunJob_4_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tRunJob_5_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tChronometerStop_4_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tChronometerStart_4_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tRunJob_6_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tRunJob_7_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tChronometerStop_5_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tChronometerStart_5_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tRunJob_8_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tRunJob_9_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tChronometerStop_6_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tChronometerStart_6_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tRunJob_10_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tRunJob_11_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tChronometerStop_7_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tChronometerStart_7_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tRunJob_13_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tChronometerStop_8_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tChronometerStart_8_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tRunJob_15_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tChronometerStop_9_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tChronometerStart_9_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tRunJob_16_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tRunJob_17_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tChronometerStop_10_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tChronometerStart_10_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tRunJob_18_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tRunJob_19_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tChronometerStop_11_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tChronometerStart_11_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tRunJob_20_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tRunJob_21_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tChronometerStop_12_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tChronometerStart_12_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tRunJob_22_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tRunJob_23_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tChronometerStop_13_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public void tPostjob_1Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tPostjob_1_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [tPostjob_1 begin ] start
				 */

				ok_Hash.put("tPostjob_1", false);
				start_Hash.put("tPostjob_1", System.currentTimeMillis());

				currentComponent = "tPostjob_1";

				int tos_count_tPostjob_1 = 0;

				/**
				 * [tPostjob_1 begin ] stop
				 */

				/**
				 * [tPostjob_1 main ] start
				 */

				currentComponent = "tPostjob_1";

				tos_count_tPostjob_1++;

				/**
				 * [tPostjob_1 main ] stop
				 */

				/**
				 * [tPostjob_1 process_data_begin ] start
				 */

				currentComponent = "tPostjob_1";

				/**
				 * [tPostjob_1 process_data_begin ] stop
				 */

				/**
				 * [tPostjob_1 process_data_end ] start
				 */

				currentComponent = "tPostjob_1";

				/**
				 * [tPostjob_1 process_data_end ] stop
				 */

				/**
				 * [tPostjob_1 end ] start
				 */

				currentComponent = "tPostjob_1";

				ok_Hash.put("tPostjob_1", true);
				end_Hash.put("tPostjob_1", System.currentTimeMillis());

				if (execStat) {
					runStat.updateStatOnConnection("OnComponentOk2", 0, "ok");
				}
				tChronometerStop_1Process(globalMap);

				/**
				 * [tPostjob_1 end ] stop
				 */
			} // end the resume

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tPostjob_1 finally ] start
				 */

				currentComponent = "tPostjob_1";

				/**
				 * [tPostjob_1 finally ] stop
				 */
			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tPostjob_1_SUBPROCESS_STATE", 1);
	}

	public void tChronometerStop_1Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tChronometerStop_1_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [tChronometerStop_1 begin ] start
				 */

				ok_Hash.put("tChronometerStop_1", false);
				start_Hash.put("tChronometerStop_1", System.currentTimeMillis());

				currentComponent = "tChronometerStop_1";

				int tos_count_tChronometerStop_1 = 0;

				long timetChronometerStop_1;

				timetChronometerStop_1 = System.currentTimeMillis() - startTime;

				System.out.print("[ tChronometerStop_1 ]  ");

				System.out.print("   " + timetChronometerStop_1 / 1000 + "seconds   ");

				System.out.println("Total: " + "  " + timetChronometerStop_1 + " milliseconds");

				Long currentTimetChronometerStop_1 = System.currentTimeMillis();
				globalMap.put("tChronometerStop_1", currentTimetChronometerStop_1);

				globalMap.put("tChronometerStop_1_STOPTIME", currentTimetChronometerStop_1);
				globalMap.put("tChronometerStop_1_DURATION", timetChronometerStop_1);

				/**
				 * [tChronometerStop_1 begin ] stop
				 */

				/**
				 * [tChronometerStop_1 main ] start
				 */

				currentComponent = "tChronometerStop_1";

				tos_count_tChronometerStop_1++;

				/**
				 * [tChronometerStop_1 main ] stop
				 */

				/**
				 * [tChronometerStop_1 process_data_begin ] start
				 */

				currentComponent = "tChronometerStop_1";

				/**
				 * [tChronometerStop_1 process_data_begin ] stop
				 */

				/**
				 * [tChronometerStop_1 process_data_end ] start
				 */

				currentComponent = "tChronometerStop_1";

				/**
				 * [tChronometerStop_1 process_data_end ] stop
				 */

				/**
				 * [tChronometerStop_1 end ] start
				 */

				currentComponent = "tChronometerStop_1";

				ok_Hash.put("tChronometerStop_1", true);
				end_Hash.put("tChronometerStop_1", System.currentTimeMillis());

				/**
				 * [tChronometerStop_1 end ] stop
				 */
			} // end the resume

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tChronometerStop_1 finally ] start
				 */

				currentComponent = "tChronometerStop_1";

				/**
				 * [tChronometerStop_1 finally ] stop
				 */
			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tChronometerStop_1_SUBPROCESS_STATE", 1);
	}

	public void tPrejob_1Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tPrejob_1_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [tPrejob_1 begin ] start
				 */

				ok_Hash.put("tPrejob_1", false);
				start_Hash.put("tPrejob_1", System.currentTimeMillis());

				currentComponent = "tPrejob_1";

				int tos_count_tPrejob_1 = 0;

				/**
				 * [tPrejob_1 begin ] stop
				 */

				/**
				 * [tPrejob_1 main ] start
				 */

				currentComponent = "tPrejob_1";

				tos_count_tPrejob_1++;

				/**
				 * [tPrejob_1 main ] stop
				 */

				/**
				 * [tPrejob_1 process_data_begin ] start
				 */

				currentComponent = "tPrejob_1";

				/**
				 * [tPrejob_1 process_data_begin ] stop
				 */

				/**
				 * [tPrejob_1 process_data_end ] start
				 */

				currentComponent = "tPrejob_1";

				/**
				 * [tPrejob_1 process_data_end ] stop
				 */

				/**
				 * [tPrejob_1 end ] start
				 */

				currentComponent = "tPrejob_1";

				ok_Hash.put("tPrejob_1", true);
				end_Hash.put("tPrejob_1", System.currentTimeMillis());

				if (execStat) {
					runStat.updateStatOnConnection("OnComponentOk1", 0, "ok");
				}
				tChronometerStart_1Process(globalMap);

				/**
				 * [tPrejob_1 end ] stop
				 */
			} // end the resume

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tPrejob_1 finally ] start
				 */

				currentComponent = "tPrejob_1";

				/**
				 * [tPrejob_1 finally ] stop
				 */
			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tPrejob_1_SUBPROCESS_STATE", 1);
	}

	public void tChronometerStart_1Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tChronometerStart_1_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [tChronometerStart_1 begin ] start
				 */

				ok_Hash.put("tChronometerStart_1", false);
				start_Hash.put("tChronometerStart_1", System.currentTimeMillis());

				currentComponent = "tChronometerStart_1";

				int tos_count_tChronometerStart_1 = 0;

				Long currentTimetChronometerStart_1 = System.currentTimeMillis();

				globalMap.put("tChronometerStart_1", currentTimetChronometerStart_1);
				globalMap.put("tChronometerStart_1_STARTTIME", currentTimetChronometerStart_1);

				/**
				 * [tChronometerStart_1 begin ] stop
				 */

				/**
				 * [tChronometerStart_1 main ] start
				 */

				currentComponent = "tChronometerStart_1";

				tos_count_tChronometerStart_1++;

				/**
				 * [tChronometerStart_1 main ] stop
				 */

				/**
				 * [tChronometerStart_1 process_data_begin ] start
				 */

				currentComponent = "tChronometerStart_1";

				/**
				 * [tChronometerStart_1 process_data_begin ] stop
				 */

				/**
				 * [tChronometerStart_1 process_data_end ] start
				 */

				currentComponent = "tChronometerStart_1";

				/**
				 * [tChronometerStart_1 process_data_end ] stop
				 */

				/**
				 * [tChronometerStart_1 end ] start
				 */

				currentComponent = "tChronometerStart_1";

				ok_Hash.put("tChronometerStart_1", true);
				end_Hash.put("tChronometerStart_1", System.currentTimeMillis());

				/**
				 * [tChronometerStart_1 end ] stop
				 */
			} // end the resume

			if (resumeEntryMethodName == null || globalResumeTicket) {
				resumeUtil.addLog("CHECKPOINT", "CONNECTION:SUBJOB_OK:tChronometerStart_1:OnSubjobOk", "",
						Thread.currentThread().getId() + "", "", "", "", "", "");
			}

			if (execStat) {
				runStat.updateStatOnConnection("OnSubjobOk3", 0, "ok");
			}

			tRunJob_1Process(globalMap);

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tChronometerStart_1 finally ] start
				 */

				currentComponent = "tChronometerStart_1";

				/**
				 * [tChronometerStart_1 finally ] stop
				 */
			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tChronometerStart_1_SUBPROCESS_STATE", 1);
	}

	public void tRunJob_1Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tRunJob_1_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [tRunJob_1 begin ] start
				 */

				ok_Hash.put("tRunJob_1", false);
				start_Hash.put("tRunJob_1", System.currentTimeMillis());

				currentComponent = "tRunJob_1";

				int tos_count_tRunJob_1 = 0;

				/**
				 * [tRunJob_1 begin ] stop
				 */

				/**
				 * [tRunJob_1 main ] start
				 */

				currentComponent = "tRunJob_1";

				java.util.List<String> paraList_tRunJob_1 = new java.util.ArrayList<String>();

				paraList_tRunJob_1.add("--father_pid=" + pid);

				paraList_tRunJob_1.add("--root_pid=" + rootPid);

				paraList_tRunJob_1.add("--father_node=tRunJob_1");

				paraList_tRunJob_1.add("--context=Default");

				if (enableLogStash) {
					paraList_tRunJob_1.add("--audit.enabled=" + enableLogStash);
				}

				// for feature:10589

				paraList_tRunJob_1.add("--stat_port=" + portStats);

				if (resuming_logs_dir_path != null) {
					paraList_tRunJob_1.add("--resuming_logs_dir_path=" + resuming_logs_dir_path);
				}
				String childResumePath_tRunJob_1 = ResumeUtil.getChildJobCheckPointPath(resuming_checkpoint_path);
				String tRunJobName_tRunJob_1 = ResumeUtil.getRighttRunJob(resuming_checkpoint_path);
				if ("tRunJob_1".equals(tRunJobName_tRunJob_1) && childResumePath_tRunJob_1 != null) {
					paraList_tRunJob_1.add("--resuming_checkpoint_path="
							+ ResumeUtil.getChildJobCheckPointPath(resuming_checkpoint_path));
				}
				paraList_tRunJob_1.add("--parent_part_launcher=JOB:" + jobName + "/NODE:tRunJob_1");

				java.util.Map<String, Object> parentContextMap_tRunJob_1 = new java.util.HashMap<String, Object>();

				context.synchronizeContext();
				class ContextProcessor_tRunJob_1 {
					private void transmitContext_0() {
						parentContextMap_tRunJob_1.put("folderName", context.folderName);
						paraList_tRunJob_1.add("--context_type " + "folderName" + "=" + "id_String");
					}

					public void transmitAllContext() {
						transmitContext_0();
					}
				}
				new ContextProcessor_tRunJob_1().transmitAllContext();
				java.util.Enumeration<?> propertyNames_tRunJob_1 = context.propertyNames();
				while (propertyNames_tRunJob_1.hasMoreElements()) {
					String key_tRunJob_1 = (String) propertyNames_tRunJob_1.nextElement();
					Object value_tRunJob_1 = (Object) context.get(key_tRunJob_1);
					if (value_tRunJob_1 != null) {
						paraList_tRunJob_1.add("--context_param " + key_tRunJob_1 + "=" + value_tRunJob_1);
					} else {
						paraList_tRunJob_1.add("--context_param " + key_tRunJob_1 + "="
								+ NULL_VALUE_EXPRESSION_IN_COMMAND_STRING_FOR_CHILD_JOB_ONLY);
					}

				}

				Object obj_tRunJob_1 = null;

				projetsid.colonnesselecteur_0_1.colonnesSelecteur childJob_tRunJob_1 = new projetsid.colonnesselecteur_0_1.colonnesSelecteur();
				// pass DataSources
				java.util.Map<String, routines.system.TalendDataSource> talendDataSources_tRunJob_1 = (java.util.Map<String, routines.system.TalendDataSource>) globalMap
						.get(KEY_DB_DATASOURCES);
				if (null != talendDataSources_tRunJob_1) {
					java.util.Map<String, javax.sql.DataSource> dataSources_tRunJob_1 = new java.util.HashMap<String, javax.sql.DataSource>();
					for (java.util.Map.Entry<String, routines.system.TalendDataSource> talendDataSourceEntry_tRunJob_1 : talendDataSources_tRunJob_1
							.entrySet()) {
						dataSources_tRunJob_1.put(talendDataSourceEntry_tRunJob_1.getKey(),
								talendDataSourceEntry_tRunJob_1.getValue().getRawDataSource());
					}
					childJob_tRunJob_1.setDataSources(dataSources_tRunJob_1);
				}

				childJob_tRunJob_1.parentContextMap = parentContextMap_tRunJob_1;

				String[][] childReturn_tRunJob_1 = childJob_tRunJob_1
						.runJob((String[]) paraList_tRunJob_1.toArray(new String[paraList_tRunJob_1.size()]));

				if (childJob_tRunJob_1.getErrorCode() == null) {
					globalMap.put("tRunJob_1_CHILD_RETURN_CODE",
							childJob_tRunJob_1.getStatus() != null && ("failure").equals(childJob_tRunJob_1.getStatus())
									? 1
									: 0);
				} else {
					globalMap.put("tRunJob_1_CHILD_RETURN_CODE", childJob_tRunJob_1.getErrorCode());
				}
				if (childJob_tRunJob_1.getExceptionStackTrace() != null) {
					globalMap.put("tRunJob_1_CHILD_EXCEPTION_STACKTRACE", childJob_tRunJob_1.getExceptionStackTrace());
				}
				errorCode = childJob_tRunJob_1.getErrorCode();
				if (childJob_tRunJob_1.getErrorCode() != null || ("failure").equals(childJob_tRunJob_1.getStatus())) {
					java.lang.Exception ce_tRunJob_1 = childJob_tRunJob_1.getException();
					throw new RuntimeException("Child job running failed.\n" + ((ce_tRunJob_1 != null)
							? (ce_tRunJob_1.getClass().getName() + ": " + ce_tRunJob_1.getMessage())
							: ""));
				}

				tos_count_tRunJob_1++;

				/**
				 * [tRunJob_1 main ] stop
				 */

				/**
				 * [tRunJob_1 process_data_begin ] start
				 */

				currentComponent = "tRunJob_1";

				/**
				 * [tRunJob_1 process_data_begin ] stop
				 */

				/**
				 * [tRunJob_1 process_data_end ] start
				 */

				currentComponent = "tRunJob_1";

				/**
				 * [tRunJob_1 process_data_end ] stop
				 */

				/**
				 * [tRunJob_1 end ] start
				 */

				currentComponent = "tRunJob_1";

				ok_Hash.put("tRunJob_1", true);
				end_Hash.put("tRunJob_1", System.currentTimeMillis());

				/**
				 * [tRunJob_1 end ] stop
				 */
			} // end the resume

			if (resumeEntryMethodName == null || globalResumeTicket) {
				resumeUtil.addLog("CHECKPOINT", "CONNECTION:SUBJOB_OK:tRunJob_1:OnSubjobOk", "",
						Thread.currentThread().getId() + "", "", "", "", "", "");
			}

			if (execStat) {
				runStat.updateStatOnConnection("OnSubjobOk4", 0, "ok");
			}

			tChronometerStop_2Process(globalMap);

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tRunJob_1 finally ] start
				 */

				currentComponent = "tRunJob_1";

				/**
				 * [tRunJob_1 finally ] stop
				 */
			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tRunJob_1_SUBPROCESS_STATE", 1);
	}

	public void tChronometerStop_2Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tChronometerStop_2_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [tChronometerStop_2 begin ] start
				 */

				ok_Hash.put("tChronometerStop_2", false);
				start_Hash.put("tChronometerStop_2", System.currentTimeMillis());

				currentComponent = "tChronometerStop_2";

				int tos_count_tChronometerStop_2 = 0;

				long timetChronometerStop_2;

				timetChronometerStop_2 = System.currentTimeMillis()
						- ((Long) globalMap.get("tChronometerStart_1")).longValue();

				System.out.print("[ tChronometerStop_2 ]  ");

				System.out.print("   " + timetChronometerStop_2 / 1000 + "seconds   ");

				System.out.println("Colonnes Selecteur" + "  " + timetChronometerStop_2 + " milliseconds");

				Long currentTimetChronometerStop_2 = System.currentTimeMillis();
				globalMap.put("tChronometerStop_2", currentTimetChronometerStop_2);

				globalMap.put("tChronometerStop_2_STOPTIME", currentTimetChronometerStop_2);
				globalMap.put("tChronometerStop_2_DURATION", timetChronometerStop_2);

				/**
				 * [tChronometerStop_2 begin ] stop
				 */

				/**
				 * [tChronometerStop_2 main ] start
				 */

				currentComponent = "tChronometerStop_2";

				tos_count_tChronometerStop_2++;

				/**
				 * [tChronometerStop_2 main ] stop
				 */

				/**
				 * [tChronometerStop_2 process_data_begin ] start
				 */

				currentComponent = "tChronometerStop_2";

				/**
				 * [tChronometerStop_2 process_data_begin ] stop
				 */

				/**
				 * [tChronometerStop_2 process_data_end ] start
				 */

				currentComponent = "tChronometerStop_2";

				/**
				 * [tChronometerStop_2 process_data_end ] stop
				 */

				/**
				 * [tChronometerStop_2 end ] start
				 */

				currentComponent = "tChronometerStop_2";

				ok_Hash.put("tChronometerStop_2", true);
				end_Hash.put("tChronometerStop_2", System.currentTimeMillis());

				/**
				 * [tChronometerStop_2 end ] stop
				 */
			} // end the resume

			if (resumeEntryMethodName == null || globalResumeTicket) {
				resumeUtil.addLog("CHECKPOINT", "CONNECTION:SUBJOB_OK:tChronometerStop_2:OnSubjobOk", "",
						Thread.currentThread().getId() + "", "", "", "", "", "");
			}

			if (execStat) {
				runStat.updateStatOnConnection("OnSubjobOk6", 0, "ok");
			}

			tChronometerStart_2Process(globalMap);

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tChronometerStop_2 finally ] start
				 */

				currentComponent = "tChronometerStop_2";

				/**
				 * [tChronometerStop_2 finally ] stop
				 */
			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tChronometerStop_2_SUBPROCESS_STATE", 1);
	}

	public void tChronometerStart_2Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tChronometerStart_2_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [tChronometerStart_2 begin ] start
				 */

				ok_Hash.put("tChronometerStart_2", false);
				start_Hash.put("tChronometerStart_2", System.currentTimeMillis());

				currentComponent = "tChronometerStart_2";

				int tos_count_tChronometerStart_2 = 0;

				Long currentTimetChronometerStart_2 = System.currentTimeMillis();

				globalMap.put("tChronometerStart_2", currentTimetChronometerStart_2);
				globalMap.put("tChronometerStart_2_STARTTIME", currentTimetChronometerStart_2);

				/**
				 * [tChronometerStart_2 begin ] stop
				 */

				/**
				 * [tChronometerStart_2 main ] start
				 */

				currentComponent = "tChronometerStart_2";

				tos_count_tChronometerStart_2++;

				/**
				 * [tChronometerStart_2 main ] stop
				 */

				/**
				 * [tChronometerStart_2 process_data_begin ] start
				 */

				currentComponent = "tChronometerStart_2";

				/**
				 * [tChronometerStart_2 process_data_begin ] stop
				 */

				/**
				 * [tChronometerStart_2 process_data_end ] start
				 */

				currentComponent = "tChronometerStart_2";

				/**
				 * [tChronometerStart_2 process_data_end ] stop
				 */

				/**
				 * [tChronometerStart_2 end ] start
				 */

				currentComponent = "tChronometerStart_2";

				ok_Hash.put("tChronometerStart_2", true);
				end_Hash.put("tChronometerStart_2", System.currentTimeMillis());

				/**
				 * [tChronometerStart_2 end ] stop
				 */
			} // end the resume

			if (resumeEntryMethodName == null || globalResumeTicket) {
				resumeUtil.addLog("CHECKPOINT", "CONNECTION:SUBJOB_OK:tChronometerStart_2:OnSubjobOk", "",
						Thread.currentThread().getId() + "", "", "", "", "", "");
			}

			if (execStat) {
				runStat.updateStatOnConnection("OnSubjobOk1", 0, "ok");
			}

			tRunJob_2Process(globalMap);

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tChronometerStart_2 finally ] start
				 */

				currentComponent = "tChronometerStart_2";

				/**
				 * [tChronometerStart_2 finally ] stop
				 */
			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tChronometerStart_2_SUBPROCESS_STATE", 1);
	}

	public void tRunJob_2Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tRunJob_2_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [tRunJob_2 begin ] start
				 */

				ok_Hash.put("tRunJob_2", false);
				start_Hash.put("tRunJob_2", System.currentTimeMillis());

				currentComponent = "tRunJob_2";

				int tos_count_tRunJob_2 = 0;

				/**
				 * [tRunJob_2 begin ] stop
				 */

				/**
				 * [tRunJob_2 main ] start
				 */

				currentComponent = "tRunJob_2";

				java.util.List<String> paraList_tRunJob_2 = new java.util.ArrayList<String>();

				paraList_tRunJob_2.add("--father_pid=" + pid);

				paraList_tRunJob_2.add("--root_pid=" + rootPid);

				paraList_tRunJob_2.add("--father_node=tRunJob_2");

				paraList_tRunJob_2.add("--context=Default");

				if (enableLogStash) {
					paraList_tRunJob_2.add("--audit.enabled=" + enableLogStash);
				}

				// for feature:10589

				paraList_tRunJob_2.add("--stat_port=" + portStats);

				if (resuming_logs_dir_path != null) {
					paraList_tRunJob_2.add("--resuming_logs_dir_path=" + resuming_logs_dir_path);
				}
				String childResumePath_tRunJob_2 = ResumeUtil.getChildJobCheckPointPath(resuming_checkpoint_path);
				String tRunJobName_tRunJob_2 = ResumeUtil.getRighttRunJob(resuming_checkpoint_path);
				if ("tRunJob_2".equals(tRunJobName_tRunJob_2) && childResumePath_tRunJob_2 != null) {
					paraList_tRunJob_2.add("--resuming_checkpoint_path="
							+ ResumeUtil.getChildJobCheckPointPath(resuming_checkpoint_path));
				}
				paraList_tRunJob_2.add("--parent_part_launcher=JOB:" + jobName + "/NODE:tRunJob_2");

				java.util.Map<String, Object> parentContextMap_tRunJob_2 = new java.util.HashMap<String, Object>();

				context.synchronizeContext();
				class ContextProcessor_tRunJob_2 {
					private void transmitContext_0() {
						parentContextMap_tRunJob_2.put("folderName", context.folderName);
						paraList_tRunJob_2.add("--context_type " + "folderName" + "=" + "id_String");
					}

					public void transmitAllContext() {
						transmitContext_0();
					}
				}
				new ContextProcessor_tRunJob_2().transmitAllContext();
				java.util.Enumeration<?> propertyNames_tRunJob_2 = context.propertyNames();
				while (propertyNames_tRunJob_2.hasMoreElements()) {
					String key_tRunJob_2 = (String) propertyNames_tRunJob_2.nextElement();
					Object value_tRunJob_2 = (Object) context.get(key_tRunJob_2);
					if (value_tRunJob_2 != null) {
						paraList_tRunJob_2.add("--context_param " + key_tRunJob_2 + "=" + value_tRunJob_2);
					} else {
						paraList_tRunJob_2.add("--context_param " + key_tRunJob_2 + "="
								+ NULL_VALUE_EXPRESSION_IN_COMMAND_STRING_FOR_CHILD_JOB_ONLY);
					}

				}

				Object obj_tRunJob_2 = null;

				projetsid.customer_group_0_1.customer_group childJob_tRunJob_2 = new projetsid.customer_group_0_1.customer_group();
				// pass DataSources
				java.util.Map<String, routines.system.TalendDataSource> talendDataSources_tRunJob_2 = (java.util.Map<String, routines.system.TalendDataSource>) globalMap
						.get(KEY_DB_DATASOURCES);
				if (null != talendDataSources_tRunJob_2) {
					java.util.Map<String, javax.sql.DataSource> dataSources_tRunJob_2 = new java.util.HashMap<String, javax.sql.DataSource>();
					for (java.util.Map.Entry<String, routines.system.TalendDataSource> talendDataSourceEntry_tRunJob_2 : talendDataSources_tRunJob_2
							.entrySet()) {
						dataSources_tRunJob_2.put(talendDataSourceEntry_tRunJob_2.getKey(),
								talendDataSourceEntry_tRunJob_2.getValue().getRawDataSource());
					}
					childJob_tRunJob_2.setDataSources(dataSources_tRunJob_2);
				}

				childJob_tRunJob_2.parentContextMap = parentContextMap_tRunJob_2;

				String[][] childReturn_tRunJob_2 = childJob_tRunJob_2
						.runJob((String[]) paraList_tRunJob_2.toArray(new String[paraList_tRunJob_2.size()]));

				if (childJob_tRunJob_2.getErrorCode() == null) {
					globalMap.put("tRunJob_2_CHILD_RETURN_CODE",
							childJob_tRunJob_2.getStatus() != null && ("failure").equals(childJob_tRunJob_2.getStatus())
									? 1
									: 0);
				} else {
					globalMap.put("tRunJob_2_CHILD_RETURN_CODE", childJob_tRunJob_2.getErrorCode());
				}
				if (childJob_tRunJob_2.getExceptionStackTrace() != null) {
					globalMap.put("tRunJob_2_CHILD_EXCEPTION_STACKTRACE", childJob_tRunJob_2.getExceptionStackTrace());
				}
				errorCode = childJob_tRunJob_2.getErrorCode();
				if (childJob_tRunJob_2.getErrorCode() != null || ("failure").equals(childJob_tRunJob_2.getStatus())) {
					java.lang.Exception ce_tRunJob_2 = childJob_tRunJob_2.getException();
					throw new RuntimeException("Child job running failed.\n" + ((ce_tRunJob_2 != null)
							? (ce_tRunJob_2.getClass().getName() + ": " + ce_tRunJob_2.getMessage())
							: ""));
				}

				tos_count_tRunJob_2++;

				/**
				 * [tRunJob_2 main ] stop
				 */

				/**
				 * [tRunJob_2 process_data_begin ] start
				 */

				currentComponent = "tRunJob_2";

				/**
				 * [tRunJob_2 process_data_begin ] stop
				 */

				/**
				 * [tRunJob_2 process_data_end ] start
				 */

				currentComponent = "tRunJob_2";

				/**
				 * [tRunJob_2 process_data_end ] stop
				 */

				/**
				 * [tRunJob_2 end ] start
				 */

				currentComponent = "tRunJob_2";

				ok_Hash.put("tRunJob_2", true);
				end_Hash.put("tRunJob_2", System.currentTimeMillis());

				/**
				 * [tRunJob_2 end ] stop
				 */
			} // end the resume

			if (resumeEntryMethodName == null || globalResumeTicket) {
				resumeUtil.addLog("CHECKPOINT", "CONNECTION:SUBJOB_OK:tRunJob_2:OnSubjobOk", "",
						Thread.currentThread().getId() + "", "", "", "", "", "");
			}

			if (execStat) {
				runStat.updateStatOnConnection("OnSubjobOk2", 0, "ok");
			}

			tRunJob_3Process(globalMap);

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tRunJob_2 finally ] start
				 */

				currentComponent = "tRunJob_2";

				/**
				 * [tRunJob_2 finally ] stop
				 */
			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tRunJob_2_SUBPROCESS_STATE", 1);
	}

	public void tRunJob_3Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tRunJob_3_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [tRunJob_3 begin ] start
				 */

				ok_Hash.put("tRunJob_3", false);
				start_Hash.put("tRunJob_3", System.currentTimeMillis());

				currentComponent = "tRunJob_3";

				int tos_count_tRunJob_3 = 0;

				/**
				 * [tRunJob_3 begin ] stop
				 */

				/**
				 * [tRunJob_3 main ] start
				 */

				currentComponent = "tRunJob_3";

				java.util.List<String> paraList_tRunJob_3 = new java.util.ArrayList<String>();

				paraList_tRunJob_3.add("--father_pid=" + pid);

				paraList_tRunJob_3.add("--root_pid=" + rootPid);

				paraList_tRunJob_3.add("--father_node=tRunJob_3");

				paraList_tRunJob_3.add("--context=Default");

				if (enableLogStash) {
					paraList_tRunJob_3.add("--audit.enabled=" + enableLogStash);
				}

				// for feature:10589

				paraList_tRunJob_3.add("--stat_port=" + portStats);

				if (resuming_logs_dir_path != null) {
					paraList_tRunJob_3.add("--resuming_logs_dir_path=" + resuming_logs_dir_path);
				}
				String childResumePath_tRunJob_3 = ResumeUtil.getChildJobCheckPointPath(resuming_checkpoint_path);
				String tRunJobName_tRunJob_3 = ResumeUtil.getRighttRunJob(resuming_checkpoint_path);
				if ("tRunJob_3".equals(tRunJobName_tRunJob_3) && childResumePath_tRunJob_3 != null) {
					paraList_tRunJob_3.add("--resuming_checkpoint_path="
							+ ResumeUtil.getChildJobCheckPointPath(resuming_checkpoint_path));
				}
				paraList_tRunJob_3.add("--parent_part_launcher=JOB:" + jobName + "/NODE:tRunJob_3");

				java.util.Map<String, Object> parentContextMap_tRunJob_3 = new java.util.HashMap<String, Object>();

				context.synchronizeContext();
				class ContextProcessor_tRunJob_3 {
					private void transmitContext_0() {
						parentContextMap_tRunJob_3.put("folderName", context.folderName);
						paraList_tRunJob_3.add("--context_type " + "folderName" + "=" + "id_String");
					}

					public void transmitAllContext() {
						transmitContext_0();
					}
				}
				new ContextProcessor_tRunJob_3().transmitAllContext();
				java.util.Enumeration<?> propertyNames_tRunJob_3 = context.propertyNames();
				while (propertyNames_tRunJob_3.hasMoreElements()) {
					String key_tRunJob_3 = (String) propertyNames_tRunJob_3.nextElement();
					Object value_tRunJob_3 = (Object) context.get(key_tRunJob_3);
					if (value_tRunJob_3 != null) {
						paraList_tRunJob_3.add("--context_param " + key_tRunJob_3 + "=" + value_tRunJob_3);
					} else {
						paraList_tRunJob_3.add("--context_param " + key_tRunJob_3 + "="
								+ NULL_VALUE_EXPRESSION_IN_COMMAND_STRING_FOR_CHILD_JOB_ONLY);
					}

				}

				Object obj_tRunJob_3 = null;

				projetsid.dumpcustomergroup_0_1.dumpCustomerGroup childJob_tRunJob_3 = new projetsid.dumpcustomergroup_0_1.dumpCustomerGroup();
				// pass DataSources
				java.util.Map<String, routines.system.TalendDataSource> talendDataSources_tRunJob_3 = (java.util.Map<String, routines.system.TalendDataSource>) globalMap
						.get(KEY_DB_DATASOURCES);
				if (null != talendDataSources_tRunJob_3) {
					java.util.Map<String, javax.sql.DataSource> dataSources_tRunJob_3 = new java.util.HashMap<String, javax.sql.DataSource>();
					for (java.util.Map.Entry<String, routines.system.TalendDataSource> talendDataSourceEntry_tRunJob_3 : talendDataSources_tRunJob_3
							.entrySet()) {
						dataSources_tRunJob_3.put(talendDataSourceEntry_tRunJob_3.getKey(),
								talendDataSourceEntry_tRunJob_3.getValue().getRawDataSource());
					}
					childJob_tRunJob_3.setDataSources(dataSources_tRunJob_3);
				}

				childJob_tRunJob_3.parentContextMap = parentContextMap_tRunJob_3;

				String[][] childReturn_tRunJob_3 = childJob_tRunJob_3
						.runJob((String[]) paraList_tRunJob_3.toArray(new String[paraList_tRunJob_3.size()]));

				if (childJob_tRunJob_3.getErrorCode() == null) {
					globalMap.put("tRunJob_3_CHILD_RETURN_CODE",
							childJob_tRunJob_3.getStatus() != null && ("failure").equals(childJob_tRunJob_3.getStatus())
									? 1
									: 0);
				} else {
					globalMap.put("tRunJob_3_CHILD_RETURN_CODE", childJob_tRunJob_3.getErrorCode());
				}
				if (childJob_tRunJob_3.getExceptionStackTrace() != null) {
					globalMap.put("tRunJob_3_CHILD_EXCEPTION_STACKTRACE", childJob_tRunJob_3.getExceptionStackTrace());
				}
				errorCode = childJob_tRunJob_3.getErrorCode();
				if (childJob_tRunJob_3.getErrorCode() != null || ("failure").equals(childJob_tRunJob_3.getStatus())) {
					java.lang.Exception ce_tRunJob_3 = childJob_tRunJob_3.getException();
					throw new RuntimeException("Child job running failed.\n" + ((ce_tRunJob_3 != null)
							? (ce_tRunJob_3.getClass().getName() + ": " + ce_tRunJob_3.getMessage())
							: ""));
				}

				tos_count_tRunJob_3++;

				/**
				 * [tRunJob_3 main ] stop
				 */

				/**
				 * [tRunJob_3 process_data_begin ] start
				 */

				currentComponent = "tRunJob_3";

				/**
				 * [tRunJob_3 process_data_begin ] stop
				 */

				/**
				 * [tRunJob_3 process_data_end ] start
				 */

				currentComponent = "tRunJob_3";

				/**
				 * [tRunJob_3 process_data_end ] stop
				 */

				/**
				 * [tRunJob_3 end ] start
				 */

				currentComponent = "tRunJob_3";

				ok_Hash.put("tRunJob_3", true);
				end_Hash.put("tRunJob_3", System.currentTimeMillis());

				/**
				 * [tRunJob_3 end ] stop
				 */
			} // end the resume

			if (resumeEntryMethodName == null || globalResumeTicket) {
				resumeUtil.addLog("CHECKPOINT", "CONNECTION:SUBJOB_OK:tRunJob_3:OnSubjobOk", "",
						Thread.currentThread().getId() + "", "", "", "", "", "");
			}

			if (execStat) {
				runStat.updateStatOnConnection("OnSubjobOk5", 0, "ok");
			}

			tChronometerStop_3Process(globalMap);

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tRunJob_3 finally ] start
				 */

				currentComponent = "tRunJob_3";

				/**
				 * [tRunJob_3 finally ] stop
				 */
			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tRunJob_3_SUBPROCESS_STATE", 1);
	}

	public void tChronometerStop_3Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tChronometerStop_3_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [tChronometerStop_3 begin ] start
				 */

				ok_Hash.put("tChronometerStop_3", false);
				start_Hash.put("tChronometerStop_3", System.currentTimeMillis());

				currentComponent = "tChronometerStop_3";

				int tos_count_tChronometerStop_3 = 0;

				long timetChronometerStop_3;

				timetChronometerStop_3 = System.currentTimeMillis()
						- ((Long) globalMap.get("tChronometerStart_2")).longValue();

				System.out.print("[ tChronometerStop_3 ]  ");

				System.out.print("   " + timetChronometerStop_3 / 1000 + "seconds   ");

				System.out.println("Customer Group " + "  " + timetChronometerStop_3 + " milliseconds");

				Long currentTimetChronometerStop_3 = System.currentTimeMillis();
				globalMap.put("tChronometerStop_3", currentTimetChronometerStop_3);

				globalMap.put("tChronometerStop_3_STOPTIME", currentTimetChronometerStop_3);
				globalMap.put("tChronometerStop_3_DURATION", timetChronometerStop_3);

				/**
				 * [tChronometerStop_3 begin ] stop
				 */

				/**
				 * [tChronometerStop_3 main ] start
				 */

				currentComponent = "tChronometerStop_3";

				tos_count_tChronometerStop_3++;

				/**
				 * [tChronometerStop_3 main ] stop
				 */

				/**
				 * [tChronometerStop_3 process_data_begin ] start
				 */

				currentComponent = "tChronometerStop_3";

				/**
				 * [tChronometerStop_3 process_data_begin ] stop
				 */

				/**
				 * [tChronometerStop_3 process_data_end ] start
				 */

				currentComponent = "tChronometerStop_3";

				/**
				 * [tChronometerStop_3 process_data_end ] stop
				 */

				/**
				 * [tChronometerStop_3 end ] start
				 */

				currentComponent = "tChronometerStop_3";

				ok_Hash.put("tChronometerStop_3", true);
				end_Hash.put("tChronometerStop_3", System.currentTimeMillis());

				/**
				 * [tChronometerStop_3 end ] stop
				 */
			} // end the resume

			if (resumeEntryMethodName == null || globalResumeTicket) {
				resumeUtil.addLog("CHECKPOINT", "CONNECTION:SUBJOB_OK:tChronometerStop_3:OnSubjobOk", "",
						Thread.currentThread().getId() + "", "", "", "", "", "");
			}

			if (execStat) {
				runStat.updateStatOnConnection("OnSubjobOk43", 0, "ok");
			}

			tChronometerStart_3Process(globalMap);

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tChronometerStop_3 finally ] start
				 */

				currentComponent = "tChronometerStop_3";

				/**
				 * [tChronometerStop_3 finally ] stop
				 */
			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tChronometerStop_3_SUBPROCESS_STATE", 1);
	}

	public void tChronometerStart_3Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tChronometerStart_3_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [tChronometerStart_3 begin ] start
				 */

				ok_Hash.put("tChronometerStart_3", false);
				start_Hash.put("tChronometerStart_3", System.currentTimeMillis());

				currentComponent = "tChronometerStart_3";

				int tos_count_tChronometerStart_3 = 0;

				Long currentTimetChronometerStart_3 = System.currentTimeMillis();

				globalMap.put("tChronometerStart_3", currentTimetChronometerStart_3);
				globalMap.put("tChronometerStart_3_STARTTIME", currentTimetChronometerStart_3);

				/**
				 * [tChronometerStart_3 begin ] stop
				 */

				/**
				 * [tChronometerStart_3 main ] start
				 */

				currentComponent = "tChronometerStart_3";

				tos_count_tChronometerStart_3++;

				/**
				 * [tChronometerStart_3 main ] stop
				 */

				/**
				 * [tChronometerStart_3 process_data_begin ] start
				 */

				currentComponent = "tChronometerStart_3";

				/**
				 * [tChronometerStart_3 process_data_begin ] stop
				 */

				/**
				 * [tChronometerStart_3 process_data_end ] start
				 */

				currentComponent = "tChronometerStart_3";

				/**
				 * [tChronometerStart_3 process_data_end ] stop
				 */

				/**
				 * [tChronometerStart_3 end ] start
				 */

				currentComponent = "tChronometerStart_3";

				ok_Hash.put("tChronometerStart_3", true);
				end_Hash.put("tChronometerStart_3", System.currentTimeMillis());

				/**
				 * [tChronometerStart_3 end ] stop
				 */
			} // end the resume

			if (resumeEntryMethodName == null || globalResumeTicket) {
				resumeUtil.addLog("CHECKPOINT", "CONNECTION:SUBJOB_OK:tChronometerStart_3:OnSubjobOk", "",
						Thread.currentThread().getId() + "", "", "", "", "", "");
			}

			if (execStat) {
				runStat.updateStatOnConnection("OnSubjobOk13", 0, "ok");
			}

			tRunJob_4Process(globalMap);

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tChronometerStart_3 finally ] start
				 */

				currentComponent = "tChronometerStart_3";

				/**
				 * [tChronometerStart_3 finally ] stop
				 */
			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tChronometerStart_3_SUBPROCESS_STATE", 1);
	}

	public void tRunJob_4Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tRunJob_4_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [tRunJob_4 begin ] start
				 */

				ok_Hash.put("tRunJob_4", false);
				start_Hash.put("tRunJob_4", System.currentTimeMillis());

				currentComponent = "tRunJob_4";

				int tos_count_tRunJob_4 = 0;

				/**
				 * [tRunJob_4 begin ] stop
				 */

				/**
				 * [tRunJob_4 main ] start
				 */

				currentComponent = "tRunJob_4";

				java.util.List<String> paraList_tRunJob_4 = new java.util.ArrayList<String>();

				paraList_tRunJob_4.add("--father_pid=" + pid);

				paraList_tRunJob_4.add("--root_pid=" + rootPid);

				paraList_tRunJob_4.add("--father_node=tRunJob_4");

				paraList_tRunJob_4.add("--context=Default");

				if (enableLogStash) {
					paraList_tRunJob_4.add("--audit.enabled=" + enableLogStash);
				}

				// for feature:10589

				paraList_tRunJob_4.add("--stat_port=" + portStats);

				if (resuming_logs_dir_path != null) {
					paraList_tRunJob_4.add("--resuming_logs_dir_path=" + resuming_logs_dir_path);
				}
				String childResumePath_tRunJob_4 = ResumeUtil.getChildJobCheckPointPath(resuming_checkpoint_path);
				String tRunJobName_tRunJob_4 = ResumeUtil.getRighttRunJob(resuming_checkpoint_path);
				if ("tRunJob_4".equals(tRunJobName_tRunJob_4) && childResumePath_tRunJob_4 != null) {
					paraList_tRunJob_4.add("--resuming_checkpoint_path="
							+ ResumeUtil.getChildJobCheckPointPath(resuming_checkpoint_path));
				}
				paraList_tRunJob_4.add("--parent_part_launcher=JOB:" + jobName + "/NODE:tRunJob_4");

				java.util.Map<String, Object> parentContextMap_tRunJob_4 = new java.util.HashMap<String, Object>();

				context.synchronizeContext();
				class ContextProcessor_tRunJob_4 {
					private void transmitContext_0() {
						parentContextMap_tRunJob_4.put("folderName", context.folderName);
						paraList_tRunJob_4.add("--context_type " + "folderName" + "=" + "id_String");
					}

					public void transmitAllContext() {
						transmitContext_0();
					}
				}
				new ContextProcessor_tRunJob_4().transmitAllContext();
				java.util.Enumeration<?> propertyNames_tRunJob_4 = context.propertyNames();
				while (propertyNames_tRunJob_4.hasMoreElements()) {
					String key_tRunJob_4 = (String) propertyNames_tRunJob_4.nextElement();
					Object value_tRunJob_4 = (Object) context.get(key_tRunJob_4);
					if (value_tRunJob_4 != null) {
						paraList_tRunJob_4.add("--context_param " + key_tRunJob_4 + "=" + value_tRunJob_4);
					} else {
						paraList_tRunJob_4.add("--context_param " + key_tRunJob_4 + "="
								+ NULL_VALUE_EXPRESSION_IN_COMMAND_STRING_FOR_CHILD_JOB_ONLY);
					}

				}

				Object obj_tRunJob_4 = null;

				projetsid.customer_type_0_1.customer_type childJob_tRunJob_4 = new projetsid.customer_type_0_1.customer_type();
				// pass DataSources
				java.util.Map<String, routines.system.TalendDataSource> talendDataSources_tRunJob_4 = (java.util.Map<String, routines.system.TalendDataSource>) globalMap
						.get(KEY_DB_DATASOURCES);
				if (null != talendDataSources_tRunJob_4) {
					java.util.Map<String, javax.sql.DataSource> dataSources_tRunJob_4 = new java.util.HashMap<String, javax.sql.DataSource>();
					for (java.util.Map.Entry<String, routines.system.TalendDataSource> talendDataSourceEntry_tRunJob_4 : talendDataSources_tRunJob_4
							.entrySet()) {
						dataSources_tRunJob_4.put(talendDataSourceEntry_tRunJob_4.getKey(),
								talendDataSourceEntry_tRunJob_4.getValue().getRawDataSource());
					}
					childJob_tRunJob_4.setDataSources(dataSources_tRunJob_4);
				}

				childJob_tRunJob_4.parentContextMap = parentContextMap_tRunJob_4;

				String[][] childReturn_tRunJob_4 = childJob_tRunJob_4
						.runJob((String[]) paraList_tRunJob_4.toArray(new String[paraList_tRunJob_4.size()]));

				if (childJob_tRunJob_4.getErrorCode() == null) {
					globalMap.put("tRunJob_4_CHILD_RETURN_CODE",
							childJob_tRunJob_4.getStatus() != null && ("failure").equals(childJob_tRunJob_4.getStatus())
									? 1
									: 0);
				} else {
					globalMap.put("tRunJob_4_CHILD_RETURN_CODE", childJob_tRunJob_4.getErrorCode());
				}
				if (childJob_tRunJob_4.getExceptionStackTrace() != null) {
					globalMap.put("tRunJob_4_CHILD_EXCEPTION_STACKTRACE", childJob_tRunJob_4.getExceptionStackTrace());
				}
				errorCode = childJob_tRunJob_4.getErrorCode();
				if (childJob_tRunJob_4.getErrorCode() != null || ("failure").equals(childJob_tRunJob_4.getStatus())) {
					java.lang.Exception ce_tRunJob_4 = childJob_tRunJob_4.getException();
					throw new RuntimeException("Child job running failed.\n" + ((ce_tRunJob_4 != null)
							? (ce_tRunJob_4.getClass().getName() + ": " + ce_tRunJob_4.getMessage())
							: ""));
				}

				tos_count_tRunJob_4++;

				/**
				 * [tRunJob_4 main ] stop
				 */

				/**
				 * [tRunJob_4 process_data_begin ] start
				 */

				currentComponent = "tRunJob_4";

				/**
				 * [tRunJob_4 process_data_begin ] stop
				 */

				/**
				 * [tRunJob_4 process_data_end ] start
				 */

				currentComponent = "tRunJob_4";

				/**
				 * [tRunJob_4 process_data_end ] stop
				 */

				/**
				 * [tRunJob_4 end ] start
				 */

				currentComponent = "tRunJob_4";

				ok_Hash.put("tRunJob_4", true);
				end_Hash.put("tRunJob_4", System.currentTimeMillis());

				/**
				 * [tRunJob_4 end ] stop
				 */
			} // end the resume

			if (resumeEntryMethodName == null || globalResumeTicket) {
				resumeUtil.addLog("CHECKPOINT", "CONNECTION:SUBJOB_OK:tRunJob_4:OnSubjobOk", "",
						Thread.currentThread().getId() + "", "", "", "", "", "");
			}

			if (execStat) {
				runStat.updateStatOnConnection("OnSubjobOk14", 0, "ok");
			}

			tRunJob_5Process(globalMap);

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tRunJob_4 finally ] start
				 */

				currentComponent = "tRunJob_4";

				/**
				 * [tRunJob_4 finally ] stop
				 */
			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tRunJob_4_SUBPROCESS_STATE", 1);
	}

	public void tRunJob_5Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tRunJob_5_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [tRunJob_5 begin ] start
				 */

				ok_Hash.put("tRunJob_5", false);
				start_Hash.put("tRunJob_5", System.currentTimeMillis());

				currentComponent = "tRunJob_5";

				int tos_count_tRunJob_5 = 0;

				/**
				 * [tRunJob_5 begin ] stop
				 */

				/**
				 * [tRunJob_5 main ] start
				 */

				currentComponent = "tRunJob_5";

				java.util.List<String> paraList_tRunJob_5 = new java.util.ArrayList<String>();

				paraList_tRunJob_5.add("--father_pid=" + pid);

				paraList_tRunJob_5.add("--root_pid=" + rootPid);

				paraList_tRunJob_5.add("--father_node=tRunJob_5");

				paraList_tRunJob_5.add("--context=Default");

				if (enableLogStash) {
					paraList_tRunJob_5.add("--audit.enabled=" + enableLogStash);
				}

				// for feature:10589

				paraList_tRunJob_5.add("--stat_port=" + portStats);

				if (resuming_logs_dir_path != null) {
					paraList_tRunJob_5.add("--resuming_logs_dir_path=" + resuming_logs_dir_path);
				}
				String childResumePath_tRunJob_5 = ResumeUtil.getChildJobCheckPointPath(resuming_checkpoint_path);
				String tRunJobName_tRunJob_5 = ResumeUtil.getRighttRunJob(resuming_checkpoint_path);
				if ("tRunJob_5".equals(tRunJobName_tRunJob_5) && childResumePath_tRunJob_5 != null) {
					paraList_tRunJob_5.add("--resuming_checkpoint_path="
							+ ResumeUtil.getChildJobCheckPointPath(resuming_checkpoint_path));
				}
				paraList_tRunJob_5.add("--parent_part_launcher=JOB:" + jobName + "/NODE:tRunJob_5");

				java.util.Map<String, Object> parentContextMap_tRunJob_5 = new java.util.HashMap<String, Object>();

				context.synchronizeContext();
				class ContextProcessor_tRunJob_5 {
					private void transmitContext_0() {
						parentContextMap_tRunJob_5.put("folderName", context.folderName);
						paraList_tRunJob_5.add("--context_type " + "folderName" + "=" + "id_String");
					}

					public void transmitAllContext() {
						transmitContext_0();
					}
				}
				new ContextProcessor_tRunJob_5().transmitAllContext();
				java.util.Enumeration<?> propertyNames_tRunJob_5 = context.propertyNames();
				while (propertyNames_tRunJob_5.hasMoreElements()) {
					String key_tRunJob_5 = (String) propertyNames_tRunJob_5.nextElement();
					Object value_tRunJob_5 = (Object) context.get(key_tRunJob_5);
					if (value_tRunJob_5 != null) {
						paraList_tRunJob_5.add("--context_param " + key_tRunJob_5 + "=" + value_tRunJob_5);
					} else {
						paraList_tRunJob_5.add("--context_param " + key_tRunJob_5 + "="
								+ NULL_VALUE_EXPRESSION_IN_COMMAND_STRING_FOR_CHILD_JOB_ONLY);
					}

				}

				Object obj_tRunJob_5 = null;

				projetsid.dumpcustomertype_0_2.dumpCustomerType childJob_tRunJob_5 = new projetsid.dumpcustomertype_0_2.dumpCustomerType();
				// pass DataSources
				java.util.Map<String, routines.system.TalendDataSource> talendDataSources_tRunJob_5 = (java.util.Map<String, routines.system.TalendDataSource>) globalMap
						.get(KEY_DB_DATASOURCES);
				if (null != talendDataSources_tRunJob_5) {
					java.util.Map<String, javax.sql.DataSource> dataSources_tRunJob_5 = new java.util.HashMap<String, javax.sql.DataSource>();
					for (java.util.Map.Entry<String, routines.system.TalendDataSource> talendDataSourceEntry_tRunJob_5 : talendDataSources_tRunJob_5
							.entrySet()) {
						dataSources_tRunJob_5.put(talendDataSourceEntry_tRunJob_5.getKey(),
								talendDataSourceEntry_tRunJob_5.getValue().getRawDataSource());
					}
					childJob_tRunJob_5.setDataSources(dataSources_tRunJob_5);
				}

				childJob_tRunJob_5.parentContextMap = parentContextMap_tRunJob_5;

				String[][] childReturn_tRunJob_5 = childJob_tRunJob_5
						.runJob((String[]) paraList_tRunJob_5.toArray(new String[paraList_tRunJob_5.size()]));

				if (childJob_tRunJob_5.getErrorCode() == null) {
					globalMap.put("tRunJob_5_CHILD_RETURN_CODE",
							childJob_tRunJob_5.getStatus() != null && ("failure").equals(childJob_tRunJob_5.getStatus())
									? 1
									: 0);
				} else {
					globalMap.put("tRunJob_5_CHILD_RETURN_CODE", childJob_tRunJob_5.getErrorCode());
				}
				if (childJob_tRunJob_5.getExceptionStackTrace() != null) {
					globalMap.put("tRunJob_5_CHILD_EXCEPTION_STACKTRACE", childJob_tRunJob_5.getExceptionStackTrace());
				}
				errorCode = childJob_tRunJob_5.getErrorCode();
				if (childJob_tRunJob_5.getErrorCode() != null || ("failure").equals(childJob_tRunJob_5.getStatus())) {
					java.lang.Exception ce_tRunJob_5 = childJob_tRunJob_5.getException();
					throw new RuntimeException("Child job running failed.\n" + ((ce_tRunJob_5 != null)
							? (ce_tRunJob_5.getClass().getName() + ": " + ce_tRunJob_5.getMessage())
							: ""));
				}

				tos_count_tRunJob_5++;

				/**
				 * [tRunJob_5 main ] stop
				 */

				/**
				 * [tRunJob_5 process_data_begin ] start
				 */

				currentComponent = "tRunJob_5";

				/**
				 * [tRunJob_5 process_data_begin ] stop
				 */

				/**
				 * [tRunJob_5 process_data_end ] start
				 */

				currentComponent = "tRunJob_5";

				/**
				 * [tRunJob_5 process_data_end ] stop
				 */

				/**
				 * [tRunJob_5 end ] start
				 */

				currentComponent = "tRunJob_5";

				ok_Hash.put("tRunJob_5", true);
				end_Hash.put("tRunJob_5", System.currentTimeMillis());

				/**
				 * [tRunJob_5 end ] stop
				 */
			} // end the resume

			if (resumeEntryMethodName == null || globalResumeTicket) {
				resumeUtil.addLog("CHECKPOINT", "CONNECTION:SUBJOB_OK:tRunJob_5:OnSubjobOk", "",
						Thread.currentThread().getId() + "", "", "", "", "", "");
			}

			if (execStat) {
				runStat.updateStatOnConnection("OnSubjobOk15", 0, "ok");
			}

			tChronometerStop_4Process(globalMap);

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tRunJob_5 finally ] start
				 */

				currentComponent = "tRunJob_5";

				/**
				 * [tRunJob_5 finally ] stop
				 */
			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tRunJob_5_SUBPROCESS_STATE", 1);
	}

	public void tChronometerStop_4Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tChronometerStop_4_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [tChronometerStop_4 begin ] start
				 */

				ok_Hash.put("tChronometerStop_4", false);
				start_Hash.put("tChronometerStop_4", System.currentTimeMillis());

				currentComponent = "tChronometerStop_4";

				int tos_count_tChronometerStop_4 = 0;

				long timetChronometerStop_4;

				timetChronometerStop_4 = System.currentTimeMillis()
						- ((Long) globalMap.get("tChronometerStart_3")).longValue();

				System.out.print("[ tChronometerStop_4 ]  ");

				System.out.print("   " + timetChronometerStop_4 / 1000 + "seconds   ");

				System.out.println("Customer Type " + "  " + timetChronometerStop_4 + " milliseconds");

				Long currentTimetChronometerStop_4 = System.currentTimeMillis();
				globalMap.put("tChronometerStop_4", currentTimetChronometerStop_4);

				globalMap.put("tChronometerStop_4_STOPTIME", currentTimetChronometerStop_4);
				globalMap.put("tChronometerStop_4_DURATION", timetChronometerStop_4);

				/**
				 * [tChronometerStop_4 begin ] stop
				 */

				/**
				 * [tChronometerStop_4 main ] start
				 */

				currentComponent = "tChronometerStop_4";

				tos_count_tChronometerStop_4++;

				/**
				 * [tChronometerStop_4 main ] stop
				 */

				/**
				 * [tChronometerStop_4 process_data_begin ] start
				 */

				currentComponent = "tChronometerStop_4";

				/**
				 * [tChronometerStop_4 process_data_begin ] stop
				 */

				/**
				 * [tChronometerStop_4 process_data_end ] start
				 */

				currentComponent = "tChronometerStop_4";

				/**
				 * [tChronometerStop_4 process_data_end ] stop
				 */

				/**
				 * [tChronometerStop_4 end ] start
				 */

				currentComponent = "tChronometerStop_4";

				ok_Hash.put("tChronometerStop_4", true);
				end_Hash.put("tChronometerStop_4", System.currentTimeMillis());

				/**
				 * [tChronometerStop_4 end ] stop
				 */
			} // end the resume

			if (resumeEntryMethodName == null || globalResumeTicket) {
				resumeUtil.addLog("CHECKPOINT", "CONNECTION:SUBJOB_OK:tChronometerStop_4:OnSubjobOk", "",
						Thread.currentThread().getId() + "", "", "", "", "", "");
			}

			if (execStat) {
				runStat.updateStatOnConnection("OnSubjobOk44", 0, "ok");
			}

			tChronometerStart_4Process(globalMap);

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tChronometerStop_4 finally ] start
				 */

				currentComponent = "tChronometerStop_4";

				/**
				 * [tChronometerStop_4 finally ] stop
				 */
			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tChronometerStop_4_SUBPROCESS_STATE", 1);
	}

	public void tChronometerStart_4Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tChronometerStart_4_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [tChronometerStart_4 begin ] start
				 */

				ok_Hash.put("tChronometerStart_4", false);
				start_Hash.put("tChronometerStart_4", System.currentTimeMillis());

				currentComponent = "tChronometerStart_4";

				int tos_count_tChronometerStart_4 = 0;

				Long currentTimetChronometerStart_4 = System.currentTimeMillis();

				globalMap.put("tChronometerStart_4", currentTimetChronometerStart_4);
				globalMap.put("tChronometerStart_4_STARTTIME", currentTimetChronometerStart_4);

				/**
				 * [tChronometerStart_4 begin ] stop
				 */

				/**
				 * [tChronometerStart_4 main ] start
				 */

				currentComponent = "tChronometerStart_4";

				tos_count_tChronometerStart_4++;

				/**
				 * [tChronometerStart_4 main ] stop
				 */

				/**
				 * [tChronometerStart_4 process_data_begin ] start
				 */

				currentComponent = "tChronometerStart_4";

				/**
				 * [tChronometerStart_4 process_data_begin ] stop
				 */

				/**
				 * [tChronometerStart_4 process_data_end ] start
				 */

				currentComponent = "tChronometerStart_4";

				/**
				 * [tChronometerStart_4 process_data_end ] stop
				 */

				/**
				 * [tChronometerStart_4 end ] start
				 */

				currentComponent = "tChronometerStart_4";

				ok_Hash.put("tChronometerStart_4", true);
				end_Hash.put("tChronometerStart_4", System.currentTimeMillis());

				/**
				 * [tChronometerStart_4 end ] stop
				 */
			} // end the resume

			if (resumeEntryMethodName == null || globalResumeTicket) {
				resumeUtil.addLog("CHECKPOINT", "CONNECTION:SUBJOB_OK:tChronometerStart_4:OnSubjobOk", "",
						Thread.currentThread().getId() + "", "", "", "", "", "");
			}

			if (execStat) {
				runStat.updateStatOnConnection("OnSubjobOk16", 0, "ok");
			}

			tRunJob_6Process(globalMap);

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tChronometerStart_4 finally ] start
				 */

				currentComponent = "tChronometerStart_4";

				/**
				 * [tChronometerStart_4 finally ] stop
				 */
			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tChronometerStart_4_SUBPROCESS_STATE", 1);
	}

	public void tRunJob_6Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tRunJob_6_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [tRunJob_6 begin ] start
				 */

				ok_Hash.put("tRunJob_6", false);
				start_Hash.put("tRunJob_6", System.currentTimeMillis());

				currentComponent = "tRunJob_6";

				int tos_count_tRunJob_6 = 0;

				/**
				 * [tRunJob_6 begin ] stop
				 */

				/**
				 * [tRunJob_6 main ] start
				 */

				currentComponent = "tRunJob_6";

				java.util.List<String> paraList_tRunJob_6 = new java.util.ArrayList<String>();

				paraList_tRunJob_6.add("--father_pid=" + pid);

				paraList_tRunJob_6.add("--root_pid=" + rootPid);

				paraList_tRunJob_6.add("--father_node=tRunJob_6");

				paraList_tRunJob_6.add("--context=Default");

				if (enableLogStash) {
					paraList_tRunJob_6.add("--audit.enabled=" + enableLogStash);
				}

				// for feature:10589

				paraList_tRunJob_6.add("--stat_port=" + portStats);

				if (resuming_logs_dir_path != null) {
					paraList_tRunJob_6.add("--resuming_logs_dir_path=" + resuming_logs_dir_path);
				}
				String childResumePath_tRunJob_6 = ResumeUtil.getChildJobCheckPointPath(resuming_checkpoint_path);
				String tRunJobName_tRunJob_6 = ResumeUtil.getRighttRunJob(resuming_checkpoint_path);
				if ("tRunJob_6".equals(tRunJobName_tRunJob_6) && childResumePath_tRunJob_6 != null) {
					paraList_tRunJob_6.add("--resuming_checkpoint_path="
							+ ResumeUtil.getChildJobCheckPointPath(resuming_checkpoint_path));
				}
				paraList_tRunJob_6.add("--parent_part_launcher=JOB:" + jobName + "/NODE:tRunJob_6");

				java.util.Map<String, Object> parentContextMap_tRunJob_6 = new java.util.HashMap<String, Object>();

				context.synchronizeContext();
				class ContextProcessor_tRunJob_6 {
					private void transmitContext_0() {
						parentContextMap_tRunJob_6.put("folderName", context.folderName);
						paraList_tRunJob_6.add("--context_type " + "folderName" + "=" + "id_String");
					}

					public void transmitAllContext() {
						transmitContext_0();
					}
				}
				new ContextProcessor_tRunJob_6().transmitAllContext();
				java.util.Enumeration<?> propertyNames_tRunJob_6 = context.propertyNames();
				while (propertyNames_tRunJob_6.hasMoreElements()) {
					String key_tRunJob_6 = (String) propertyNames_tRunJob_6.nextElement();
					Object value_tRunJob_6 = (Object) context.get(key_tRunJob_6);
					if (value_tRunJob_6 != null) {
						paraList_tRunJob_6.add("--context_param " + key_tRunJob_6 + "=" + value_tRunJob_6);
					} else {
						paraList_tRunJob_6.add("--context_param " + key_tRunJob_6 + "="
								+ NULL_VALUE_EXPRESSION_IN_COMMAND_STRING_FOR_CHILD_JOB_ONLY);
					}

				}

				Object obj_tRunJob_6 = null;

				projetsid.country_0_1.country childJob_tRunJob_6 = new projetsid.country_0_1.country();
				// pass DataSources
				java.util.Map<String, routines.system.TalendDataSource> talendDataSources_tRunJob_6 = (java.util.Map<String, routines.system.TalendDataSource>) globalMap
						.get(KEY_DB_DATASOURCES);
				if (null != talendDataSources_tRunJob_6) {
					java.util.Map<String, javax.sql.DataSource> dataSources_tRunJob_6 = new java.util.HashMap<String, javax.sql.DataSource>();
					for (java.util.Map.Entry<String, routines.system.TalendDataSource> talendDataSourceEntry_tRunJob_6 : talendDataSources_tRunJob_6
							.entrySet()) {
						dataSources_tRunJob_6.put(talendDataSourceEntry_tRunJob_6.getKey(),
								talendDataSourceEntry_tRunJob_6.getValue().getRawDataSource());
					}
					childJob_tRunJob_6.setDataSources(dataSources_tRunJob_6);
				}

				childJob_tRunJob_6.parentContextMap = parentContextMap_tRunJob_6;

				String[][] childReturn_tRunJob_6 = childJob_tRunJob_6
						.runJob((String[]) paraList_tRunJob_6.toArray(new String[paraList_tRunJob_6.size()]));

				if (childJob_tRunJob_6.getErrorCode() == null) {
					globalMap.put("tRunJob_6_CHILD_RETURN_CODE",
							childJob_tRunJob_6.getStatus() != null && ("failure").equals(childJob_tRunJob_6.getStatus())
									? 1
									: 0);
				} else {
					globalMap.put("tRunJob_6_CHILD_RETURN_CODE", childJob_tRunJob_6.getErrorCode());
				}
				if (childJob_tRunJob_6.getExceptionStackTrace() != null) {
					globalMap.put("tRunJob_6_CHILD_EXCEPTION_STACKTRACE", childJob_tRunJob_6.getExceptionStackTrace());
				}
				errorCode = childJob_tRunJob_6.getErrorCode();
				if (childJob_tRunJob_6.getErrorCode() != null || ("failure").equals(childJob_tRunJob_6.getStatus())) {
					java.lang.Exception ce_tRunJob_6 = childJob_tRunJob_6.getException();
					throw new RuntimeException("Child job running failed.\n" + ((ce_tRunJob_6 != null)
							? (ce_tRunJob_6.getClass().getName() + ": " + ce_tRunJob_6.getMessage())
							: ""));
				}

				tos_count_tRunJob_6++;

				/**
				 * [tRunJob_6 main ] stop
				 */

				/**
				 * [tRunJob_6 process_data_begin ] start
				 */

				currentComponent = "tRunJob_6";

				/**
				 * [tRunJob_6 process_data_begin ] stop
				 */

				/**
				 * [tRunJob_6 process_data_end ] start
				 */

				currentComponent = "tRunJob_6";

				/**
				 * [tRunJob_6 process_data_end ] stop
				 */

				/**
				 * [tRunJob_6 end ] start
				 */

				currentComponent = "tRunJob_6";

				ok_Hash.put("tRunJob_6", true);
				end_Hash.put("tRunJob_6", System.currentTimeMillis());

				/**
				 * [tRunJob_6 end ] stop
				 */
			} // end the resume

			if (resumeEntryMethodName == null || globalResumeTicket) {
				resumeUtil.addLog("CHECKPOINT", "CONNECTION:SUBJOB_OK:tRunJob_6:OnSubjobOk", "",
						Thread.currentThread().getId() + "", "", "", "", "", "");
			}

			if (execStat) {
				runStat.updateStatOnConnection("OnSubjobOk17", 0, "ok");
			}

			tRunJob_7Process(globalMap);

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tRunJob_6 finally ] start
				 */

				currentComponent = "tRunJob_6";

				/**
				 * [tRunJob_6 finally ] stop
				 */
			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tRunJob_6_SUBPROCESS_STATE", 1);
	}

	public void tRunJob_7Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tRunJob_7_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [tRunJob_7 begin ] start
				 */

				ok_Hash.put("tRunJob_7", false);
				start_Hash.put("tRunJob_7", System.currentTimeMillis());

				currentComponent = "tRunJob_7";

				int tos_count_tRunJob_7 = 0;

				/**
				 * [tRunJob_7 begin ] stop
				 */

				/**
				 * [tRunJob_7 main ] start
				 */

				currentComponent = "tRunJob_7";

				java.util.List<String> paraList_tRunJob_7 = new java.util.ArrayList<String>();

				paraList_tRunJob_7.add("--father_pid=" + pid);

				paraList_tRunJob_7.add("--root_pid=" + rootPid);

				paraList_tRunJob_7.add("--father_node=tRunJob_7");

				paraList_tRunJob_7.add("--context=Default");

				if (enableLogStash) {
					paraList_tRunJob_7.add("--audit.enabled=" + enableLogStash);
				}

				// for feature:10589

				paraList_tRunJob_7.add("--stat_port=" + portStats);

				if (resuming_logs_dir_path != null) {
					paraList_tRunJob_7.add("--resuming_logs_dir_path=" + resuming_logs_dir_path);
				}
				String childResumePath_tRunJob_7 = ResumeUtil.getChildJobCheckPointPath(resuming_checkpoint_path);
				String tRunJobName_tRunJob_7 = ResumeUtil.getRighttRunJob(resuming_checkpoint_path);
				if ("tRunJob_7".equals(tRunJobName_tRunJob_7) && childResumePath_tRunJob_7 != null) {
					paraList_tRunJob_7.add("--resuming_checkpoint_path="
							+ ResumeUtil.getChildJobCheckPointPath(resuming_checkpoint_path));
				}
				paraList_tRunJob_7.add("--parent_part_launcher=JOB:" + jobName + "/NODE:tRunJob_7");

				java.util.Map<String, Object> parentContextMap_tRunJob_7 = new java.util.HashMap<String, Object>();

				context.synchronizeContext();
				class ContextProcessor_tRunJob_7 {
					private void transmitContext_0() {
						parentContextMap_tRunJob_7.put("folderName", context.folderName);
						paraList_tRunJob_7.add("--context_type " + "folderName" + "=" + "id_String");
					}

					public void transmitAllContext() {
						transmitContext_0();
					}
				}
				new ContextProcessor_tRunJob_7().transmitAllContext();
				java.util.Enumeration<?> propertyNames_tRunJob_7 = context.propertyNames();
				while (propertyNames_tRunJob_7.hasMoreElements()) {
					String key_tRunJob_7 = (String) propertyNames_tRunJob_7.nextElement();
					Object value_tRunJob_7 = (Object) context.get(key_tRunJob_7);
					if (value_tRunJob_7 != null) {
						paraList_tRunJob_7.add("--context_param " + key_tRunJob_7 + "=" + value_tRunJob_7);
					} else {
						paraList_tRunJob_7.add("--context_param " + key_tRunJob_7 + "="
								+ NULL_VALUE_EXPRESSION_IN_COMMAND_STRING_FOR_CHILD_JOB_ONLY);
					}

				}

				Object obj_tRunJob_7 = null;

				projetsid.dumpcountry_0_1.dumpCountry childJob_tRunJob_7 = new projetsid.dumpcountry_0_1.dumpCountry();
				// pass DataSources
				java.util.Map<String, routines.system.TalendDataSource> talendDataSources_tRunJob_7 = (java.util.Map<String, routines.system.TalendDataSource>) globalMap
						.get(KEY_DB_DATASOURCES);
				if (null != talendDataSources_tRunJob_7) {
					java.util.Map<String, javax.sql.DataSource> dataSources_tRunJob_7 = new java.util.HashMap<String, javax.sql.DataSource>();
					for (java.util.Map.Entry<String, routines.system.TalendDataSource> talendDataSourceEntry_tRunJob_7 : talendDataSources_tRunJob_7
							.entrySet()) {
						dataSources_tRunJob_7.put(talendDataSourceEntry_tRunJob_7.getKey(),
								talendDataSourceEntry_tRunJob_7.getValue().getRawDataSource());
					}
					childJob_tRunJob_7.setDataSources(dataSources_tRunJob_7);
				}

				childJob_tRunJob_7.parentContextMap = parentContextMap_tRunJob_7;

				String[][] childReturn_tRunJob_7 = childJob_tRunJob_7
						.runJob((String[]) paraList_tRunJob_7.toArray(new String[paraList_tRunJob_7.size()]));

				if (childJob_tRunJob_7.getErrorCode() == null) {
					globalMap.put("tRunJob_7_CHILD_RETURN_CODE",
							childJob_tRunJob_7.getStatus() != null && ("failure").equals(childJob_tRunJob_7.getStatus())
									? 1
									: 0);
				} else {
					globalMap.put("tRunJob_7_CHILD_RETURN_CODE", childJob_tRunJob_7.getErrorCode());
				}
				if (childJob_tRunJob_7.getExceptionStackTrace() != null) {
					globalMap.put("tRunJob_7_CHILD_EXCEPTION_STACKTRACE", childJob_tRunJob_7.getExceptionStackTrace());
				}
				errorCode = childJob_tRunJob_7.getErrorCode();
				if (childJob_tRunJob_7.getErrorCode() != null || ("failure").equals(childJob_tRunJob_7.getStatus())) {
					java.lang.Exception ce_tRunJob_7 = childJob_tRunJob_7.getException();
					throw new RuntimeException("Child job running failed.\n" + ((ce_tRunJob_7 != null)
							? (ce_tRunJob_7.getClass().getName() + ": " + ce_tRunJob_7.getMessage())
							: ""));
				}

				tos_count_tRunJob_7++;

				/**
				 * [tRunJob_7 main ] stop
				 */

				/**
				 * [tRunJob_7 process_data_begin ] start
				 */

				currentComponent = "tRunJob_7";

				/**
				 * [tRunJob_7 process_data_begin ] stop
				 */

				/**
				 * [tRunJob_7 process_data_end ] start
				 */

				currentComponent = "tRunJob_7";

				/**
				 * [tRunJob_7 process_data_end ] stop
				 */

				/**
				 * [tRunJob_7 end ] start
				 */

				currentComponent = "tRunJob_7";

				ok_Hash.put("tRunJob_7", true);
				end_Hash.put("tRunJob_7", System.currentTimeMillis());

				/**
				 * [tRunJob_7 end ] stop
				 */
			} // end the resume

			if (resumeEntryMethodName == null || globalResumeTicket) {
				resumeUtil.addLog("CHECKPOINT", "CONNECTION:SUBJOB_OK:tRunJob_7:OnSubjobOk", "",
						Thread.currentThread().getId() + "", "", "", "", "", "");
			}

			if (execStat) {
				runStat.updateStatOnConnection("OnSubjobOk18", 0, "ok");
			}

			tChronometerStop_5Process(globalMap);

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tRunJob_7 finally ] start
				 */

				currentComponent = "tRunJob_7";

				/**
				 * [tRunJob_7 finally ] stop
				 */
			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tRunJob_7_SUBPROCESS_STATE", 1);
	}

	public void tChronometerStop_5Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tChronometerStop_5_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [tChronometerStop_5 begin ] start
				 */

				ok_Hash.put("tChronometerStop_5", false);
				start_Hash.put("tChronometerStop_5", System.currentTimeMillis());

				currentComponent = "tChronometerStop_5";

				int tos_count_tChronometerStop_5 = 0;

				long timetChronometerStop_5;

				timetChronometerStop_5 = System.currentTimeMillis()
						- ((Long) globalMap.get("tChronometerStart_4")).longValue();

				System.out.print("[ tChronometerStop_5 ]  ");

				System.out.print("   " + timetChronometerStop_5 / 1000 + "seconds   ");

				System.out.println("Contry: " + "  " + timetChronometerStop_5 + " milliseconds");

				Long currentTimetChronometerStop_5 = System.currentTimeMillis();
				globalMap.put("tChronometerStop_5", currentTimetChronometerStop_5);

				globalMap.put("tChronometerStop_5_STOPTIME", currentTimetChronometerStop_5);
				globalMap.put("tChronometerStop_5_DURATION", timetChronometerStop_5);

				/**
				 * [tChronometerStop_5 begin ] stop
				 */

				/**
				 * [tChronometerStop_5 main ] start
				 */

				currentComponent = "tChronometerStop_5";

				tos_count_tChronometerStop_5++;

				/**
				 * [tChronometerStop_5 main ] stop
				 */

				/**
				 * [tChronometerStop_5 process_data_begin ] start
				 */

				currentComponent = "tChronometerStop_5";

				/**
				 * [tChronometerStop_5 process_data_begin ] stop
				 */

				/**
				 * [tChronometerStop_5 process_data_end ] start
				 */

				currentComponent = "tChronometerStop_5";

				/**
				 * [tChronometerStop_5 process_data_end ] stop
				 */

				/**
				 * [tChronometerStop_5 end ] start
				 */

				currentComponent = "tChronometerStop_5";

				ok_Hash.put("tChronometerStop_5", true);
				end_Hash.put("tChronometerStop_5", System.currentTimeMillis());

				/**
				 * [tChronometerStop_5 end ] stop
				 */
			} // end the resume

			if (resumeEntryMethodName == null || globalResumeTicket) {
				resumeUtil.addLog("CHECKPOINT", "CONNECTION:SUBJOB_OK:tChronometerStop_5:OnSubjobOk", "",
						Thread.currentThread().getId() + "", "", "", "", "", "");
			}

			if (execStat) {
				runStat.updateStatOnConnection("OnSubjobOk45", 0, "ok");
			}

			tChronometerStart_5Process(globalMap);

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tChronometerStop_5 finally ] start
				 */

				currentComponent = "tChronometerStop_5";

				/**
				 * [tChronometerStop_5 finally ] stop
				 */
			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tChronometerStop_5_SUBPROCESS_STATE", 1);
	}

	public void tChronometerStart_5Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tChronometerStart_5_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [tChronometerStart_5 begin ] start
				 */

				ok_Hash.put("tChronometerStart_5", false);
				start_Hash.put("tChronometerStart_5", System.currentTimeMillis());

				currentComponent = "tChronometerStart_5";

				int tos_count_tChronometerStart_5 = 0;

				Long currentTimetChronometerStart_5 = System.currentTimeMillis();

				globalMap.put("tChronometerStart_5", currentTimetChronometerStart_5);
				globalMap.put("tChronometerStart_5_STARTTIME", currentTimetChronometerStart_5);

				/**
				 * [tChronometerStart_5 begin ] stop
				 */

				/**
				 * [tChronometerStart_5 main ] start
				 */

				currentComponent = "tChronometerStart_5";

				tos_count_tChronometerStart_5++;

				/**
				 * [tChronometerStart_5 main ] stop
				 */

				/**
				 * [tChronometerStart_5 process_data_begin ] start
				 */

				currentComponent = "tChronometerStart_5";

				/**
				 * [tChronometerStart_5 process_data_begin ] stop
				 */

				/**
				 * [tChronometerStart_5 process_data_end ] start
				 */

				currentComponent = "tChronometerStart_5";

				/**
				 * [tChronometerStart_5 process_data_end ] stop
				 */

				/**
				 * [tChronometerStart_5 end ] start
				 */

				currentComponent = "tChronometerStart_5";

				ok_Hash.put("tChronometerStart_5", true);
				end_Hash.put("tChronometerStart_5", System.currentTimeMillis());

				/**
				 * [tChronometerStart_5 end ] stop
				 */
			} // end the resume

			if (resumeEntryMethodName == null || globalResumeTicket) {
				resumeUtil.addLog("CHECKPOINT", "CONNECTION:SUBJOB_OK:tChronometerStart_5:OnSubjobOk", "",
						Thread.currentThread().getId() + "", "", "", "", "", "");
			}

			if (execStat) {
				runStat.updateStatOnConnection("OnSubjobOk19", 0, "ok");
			}

			tRunJob_8Process(globalMap);

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tChronometerStart_5 finally ] start
				 */

				currentComponent = "tChronometerStart_5";

				/**
				 * [tChronometerStart_5 finally ] stop
				 */
			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tChronometerStart_5_SUBPROCESS_STATE", 1);
	}

	public void tRunJob_8Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tRunJob_8_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [tRunJob_8 begin ] start
				 */

				ok_Hash.put("tRunJob_8", false);
				start_Hash.put("tRunJob_8", System.currentTimeMillis());

				currentComponent = "tRunJob_8";

				int tos_count_tRunJob_8 = 0;

				/**
				 * [tRunJob_8 begin ] stop
				 */

				/**
				 * [tRunJob_8 main ] start
				 */

				currentComponent = "tRunJob_8";

				java.util.List<String> paraList_tRunJob_8 = new java.util.ArrayList<String>();

				paraList_tRunJob_8.add("--father_pid=" + pid);

				paraList_tRunJob_8.add("--root_pid=" + rootPid);

				paraList_tRunJob_8.add("--father_node=tRunJob_8");

				paraList_tRunJob_8.add("--context=Default");

				if (enableLogStash) {
					paraList_tRunJob_8.add("--audit.enabled=" + enableLogStash);
				}

				// for feature:10589

				paraList_tRunJob_8.add("--stat_port=" + portStats);

				if (resuming_logs_dir_path != null) {
					paraList_tRunJob_8.add("--resuming_logs_dir_path=" + resuming_logs_dir_path);
				}
				String childResumePath_tRunJob_8 = ResumeUtil.getChildJobCheckPointPath(resuming_checkpoint_path);
				String tRunJobName_tRunJob_8 = ResumeUtil.getRighttRunJob(resuming_checkpoint_path);
				if ("tRunJob_8".equals(tRunJobName_tRunJob_8) && childResumePath_tRunJob_8 != null) {
					paraList_tRunJob_8.add("--resuming_checkpoint_path="
							+ ResumeUtil.getChildJobCheckPointPath(resuming_checkpoint_path));
				}
				paraList_tRunJob_8.add("--parent_part_launcher=JOB:" + jobName + "/NODE:tRunJob_8");

				java.util.Map<String, Object> parentContextMap_tRunJob_8 = new java.util.HashMap<String, Object>();

				context.synchronizeContext();
				class ContextProcessor_tRunJob_8 {
					private void transmitContext_0() {
						parentContextMap_tRunJob_8.put("folderName", context.folderName);
						paraList_tRunJob_8.add("--context_type " + "folderName" + "=" + "id_String");
					}

					public void transmitAllContext() {
						transmitContext_0();
					}
				}
				new ContextProcessor_tRunJob_8().transmitAllContext();
				java.util.Enumeration<?> propertyNames_tRunJob_8 = context.propertyNames();
				while (propertyNames_tRunJob_8.hasMoreElements()) {
					String key_tRunJob_8 = (String) propertyNames_tRunJob_8.nextElement();
					Object value_tRunJob_8 = (Object) context.get(key_tRunJob_8);
					if (value_tRunJob_8 != null) {
						paraList_tRunJob_8.add("--context_param " + key_tRunJob_8 + "=" + value_tRunJob_8);
					} else {
						paraList_tRunJob_8.add("--context_param " + key_tRunJob_8 + "="
								+ NULL_VALUE_EXPRESSION_IN_COMMAND_STRING_FOR_CHILD_JOB_ONLY);
					}

				}

				Object obj_tRunJob_8 = null;

				projetsid.product_0_1.product childJob_tRunJob_8 = new projetsid.product_0_1.product();
				// pass DataSources
				java.util.Map<String, routines.system.TalendDataSource> talendDataSources_tRunJob_8 = (java.util.Map<String, routines.system.TalendDataSource>) globalMap
						.get(KEY_DB_DATASOURCES);
				if (null != talendDataSources_tRunJob_8) {
					java.util.Map<String, javax.sql.DataSource> dataSources_tRunJob_8 = new java.util.HashMap<String, javax.sql.DataSource>();
					for (java.util.Map.Entry<String, routines.system.TalendDataSource> talendDataSourceEntry_tRunJob_8 : talendDataSources_tRunJob_8
							.entrySet()) {
						dataSources_tRunJob_8.put(talendDataSourceEntry_tRunJob_8.getKey(),
								talendDataSourceEntry_tRunJob_8.getValue().getRawDataSource());
					}
					childJob_tRunJob_8.setDataSources(dataSources_tRunJob_8);
				}

				childJob_tRunJob_8.parentContextMap = parentContextMap_tRunJob_8;

				String[][] childReturn_tRunJob_8 = childJob_tRunJob_8
						.runJob((String[]) paraList_tRunJob_8.toArray(new String[paraList_tRunJob_8.size()]));

				if (childJob_tRunJob_8.getErrorCode() == null) {
					globalMap.put("tRunJob_8_CHILD_RETURN_CODE",
							childJob_tRunJob_8.getStatus() != null && ("failure").equals(childJob_tRunJob_8.getStatus())
									? 1
									: 0);
				} else {
					globalMap.put("tRunJob_8_CHILD_RETURN_CODE", childJob_tRunJob_8.getErrorCode());
				}
				if (childJob_tRunJob_8.getExceptionStackTrace() != null) {
					globalMap.put("tRunJob_8_CHILD_EXCEPTION_STACKTRACE", childJob_tRunJob_8.getExceptionStackTrace());
				}
				errorCode = childJob_tRunJob_8.getErrorCode();
				if (childJob_tRunJob_8.getErrorCode() != null || ("failure").equals(childJob_tRunJob_8.getStatus())) {
					java.lang.Exception ce_tRunJob_8 = childJob_tRunJob_8.getException();
					throw new RuntimeException("Child job running failed.\n" + ((ce_tRunJob_8 != null)
							? (ce_tRunJob_8.getClass().getName() + ": " + ce_tRunJob_8.getMessage())
							: ""));
				}

				tos_count_tRunJob_8++;

				/**
				 * [tRunJob_8 main ] stop
				 */

				/**
				 * [tRunJob_8 process_data_begin ] start
				 */

				currentComponent = "tRunJob_8";

				/**
				 * [tRunJob_8 process_data_begin ] stop
				 */

				/**
				 * [tRunJob_8 process_data_end ] start
				 */

				currentComponent = "tRunJob_8";

				/**
				 * [tRunJob_8 process_data_end ] stop
				 */

				/**
				 * [tRunJob_8 end ] start
				 */

				currentComponent = "tRunJob_8";

				ok_Hash.put("tRunJob_8", true);
				end_Hash.put("tRunJob_8", System.currentTimeMillis());

				/**
				 * [tRunJob_8 end ] stop
				 */
			} // end the resume

			if (resumeEntryMethodName == null || globalResumeTicket) {
				resumeUtil.addLog("CHECKPOINT", "CONNECTION:SUBJOB_OK:tRunJob_8:OnSubjobOk", "",
						Thread.currentThread().getId() + "", "", "", "", "", "");
			}

			if (execStat) {
				runStat.updateStatOnConnection("OnSubjobOk20", 0, "ok");
			}

			tRunJob_9Process(globalMap);

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tRunJob_8 finally ] start
				 */

				currentComponent = "tRunJob_8";

				/**
				 * [tRunJob_8 finally ] stop
				 */
			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tRunJob_8_SUBPROCESS_STATE", 1);
	}

	public void tRunJob_9Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tRunJob_9_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [tRunJob_9 begin ] start
				 */

				ok_Hash.put("tRunJob_9", false);
				start_Hash.put("tRunJob_9", System.currentTimeMillis());

				currentComponent = "tRunJob_9";

				int tos_count_tRunJob_9 = 0;

				/**
				 * [tRunJob_9 begin ] stop
				 */

				/**
				 * [tRunJob_9 main ] start
				 */

				currentComponent = "tRunJob_9";

				java.util.List<String> paraList_tRunJob_9 = new java.util.ArrayList<String>();

				paraList_tRunJob_9.add("--father_pid=" + pid);

				paraList_tRunJob_9.add("--root_pid=" + rootPid);

				paraList_tRunJob_9.add("--father_node=tRunJob_9");

				paraList_tRunJob_9.add("--context=Default");

				if (enableLogStash) {
					paraList_tRunJob_9.add("--audit.enabled=" + enableLogStash);
				}

				// for feature:10589

				paraList_tRunJob_9.add("--stat_port=" + portStats);

				if (resuming_logs_dir_path != null) {
					paraList_tRunJob_9.add("--resuming_logs_dir_path=" + resuming_logs_dir_path);
				}
				String childResumePath_tRunJob_9 = ResumeUtil.getChildJobCheckPointPath(resuming_checkpoint_path);
				String tRunJobName_tRunJob_9 = ResumeUtil.getRighttRunJob(resuming_checkpoint_path);
				if ("tRunJob_9".equals(tRunJobName_tRunJob_9) && childResumePath_tRunJob_9 != null) {
					paraList_tRunJob_9.add("--resuming_checkpoint_path="
							+ ResumeUtil.getChildJobCheckPointPath(resuming_checkpoint_path));
				}
				paraList_tRunJob_9.add("--parent_part_launcher=JOB:" + jobName + "/NODE:tRunJob_9");

				java.util.Map<String, Object> parentContextMap_tRunJob_9 = new java.util.HashMap<String, Object>();

				context.synchronizeContext();
				class ContextProcessor_tRunJob_9 {
					private void transmitContext_0() {
						parentContextMap_tRunJob_9.put("folderName", context.folderName);
						paraList_tRunJob_9.add("--context_type " + "folderName" + "=" + "id_String");
					}

					public void transmitAllContext() {
						transmitContext_0();
					}
				}
				new ContextProcessor_tRunJob_9().transmitAllContext();
				java.util.Enumeration<?> propertyNames_tRunJob_9 = context.propertyNames();
				while (propertyNames_tRunJob_9.hasMoreElements()) {
					String key_tRunJob_9 = (String) propertyNames_tRunJob_9.nextElement();
					Object value_tRunJob_9 = (Object) context.get(key_tRunJob_9);
					if (value_tRunJob_9 != null) {
						paraList_tRunJob_9.add("--context_param " + key_tRunJob_9 + "=" + value_tRunJob_9);
					} else {
						paraList_tRunJob_9.add("--context_param " + key_tRunJob_9 + "="
								+ NULL_VALUE_EXPRESSION_IN_COMMAND_STRING_FOR_CHILD_JOB_ONLY);
					}

				}

				Object obj_tRunJob_9 = null;

				projetsid.dumpproduct_0_1.dumpProduct childJob_tRunJob_9 = new projetsid.dumpproduct_0_1.dumpProduct();
				// pass DataSources
				java.util.Map<String, routines.system.TalendDataSource> talendDataSources_tRunJob_9 = (java.util.Map<String, routines.system.TalendDataSource>) globalMap
						.get(KEY_DB_DATASOURCES);
				if (null != talendDataSources_tRunJob_9) {
					java.util.Map<String, javax.sql.DataSource> dataSources_tRunJob_9 = new java.util.HashMap<String, javax.sql.DataSource>();
					for (java.util.Map.Entry<String, routines.system.TalendDataSource> talendDataSourceEntry_tRunJob_9 : talendDataSources_tRunJob_9
							.entrySet()) {
						dataSources_tRunJob_9.put(talendDataSourceEntry_tRunJob_9.getKey(),
								talendDataSourceEntry_tRunJob_9.getValue().getRawDataSource());
					}
					childJob_tRunJob_9.setDataSources(dataSources_tRunJob_9);
				}

				childJob_tRunJob_9.parentContextMap = parentContextMap_tRunJob_9;

				String[][] childReturn_tRunJob_9 = childJob_tRunJob_9
						.runJob((String[]) paraList_tRunJob_9.toArray(new String[paraList_tRunJob_9.size()]));

				if (childJob_tRunJob_9.getErrorCode() == null) {
					globalMap.put("tRunJob_9_CHILD_RETURN_CODE",
							childJob_tRunJob_9.getStatus() != null && ("failure").equals(childJob_tRunJob_9.getStatus())
									? 1
									: 0);
				} else {
					globalMap.put("tRunJob_9_CHILD_RETURN_CODE", childJob_tRunJob_9.getErrorCode());
				}
				if (childJob_tRunJob_9.getExceptionStackTrace() != null) {
					globalMap.put("tRunJob_9_CHILD_EXCEPTION_STACKTRACE", childJob_tRunJob_9.getExceptionStackTrace());
				}
				errorCode = childJob_tRunJob_9.getErrorCode();
				if (childJob_tRunJob_9.getErrorCode() != null || ("failure").equals(childJob_tRunJob_9.getStatus())) {
					java.lang.Exception ce_tRunJob_9 = childJob_tRunJob_9.getException();
					throw new RuntimeException("Child job running failed.\n" + ((ce_tRunJob_9 != null)
							? (ce_tRunJob_9.getClass().getName() + ": " + ce_tRunJob_9.getMessage())
							: ""));
				}

				tos_count_tRunJob_9++;

				/**
				 * [tRunJob_9 main ] stop
				 */

				/**
				 * [tRunJob_9 process_data_begin ] start
				 */

				currentComponent = "tRunJob_9";

				/**
				 * [tRunJob_9 process_data_begin ] stop
				 */

				/**
				 * [tRunJob_9 process_data_end ] start
				 */

				currentComponent = "tRunJob_9";

				/**
				 * [tRunJob_9 process_data_end ] stop
				 */

				/**
				 * [tRunJob_9 end ] start
				 */

				currentComponent = "tRunJob_9";

				ok_Hash.put("tRunJob_9", true);
				end_Hash.put("tRunJob_9", System.currentTimeMillis());

				/**
				 * [tRunJob_9 end ] stop
				 */
			} // end the resume

			if (resumeEntryMethodName == null || globalResumeTicket) {
				resumeUtil.addLog("CHECKPOINT", "CONNECTION:SUBJOB_OK:tRunJob_9:OnSubjobOk", "",
						Thread.currentThread().getId() + "", "", "", "", "", "");
			}

			if (execStat) {
				runStat.updateStatOnConnection("OnSubjobOk21", 0, "ok");
			}

			tChronometerStop_6Process(globalMap);

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tRunJob_9 finally ] start
				 */

				currentComponent = "tRunJob_9";

				/**
				 * [tRunJob_9 finally ] stop
				 */
			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tRunJob_9_SUBPROCESS_STATE", 1);
	}

	public void tChronometerStop_6Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tChronometerStop_6_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [tChronometerStop_6 begin ] start
				 */

				ok_Hash.put("tChronometerStop_6", false);
				start_Hash.put("tChronometerStop_6", System.currentTimeMillis());

				currentComponent = "tChronometerStop_6";

				int tos_count_tChronometerStop_6 = 0;

				long timetChronometerStop_6;

				timetChronometerStop_6 = System.currentTimeMillis()
						- ((Long) globalMap.get("tChronometerStart_5")).longValue();

				System.out.print("[ tChronometerStop_6 ]  ");

				System.out.print("   " + timetChronometerStop_6 / 1000 + "seconds   ");

				System.out.println("Product " + "  " + timetChronometerStop_6 + " milliseconds");

				Long currentTimetChronometerStop_6 = System.currentTimeMillis();
				globalMap.put("tChronometerStop_6", currentTimetChronometerStop_6);

				globalMap.put("tChronometerStop_6_STOPTIME", currentTimetChronometerStop_6);
				globalMap.put("tChronometerStop_6_DURATION", timetChronometerStop_6);

				/**
				 * [tChronometerStop_6 begin ] stop
				 */

				/**
				 * [tChronometerStop_6 main ] start
				 */

				currentComponent = "tChronometerStop_6";

				tos_count_tChronometerStop_6++;

				/**
				 * [tChronometerStop_6 main ] stop
				 */

				/**
				 * [tChronometerStop_6 process_data_begin ] start
				 */

				currentComponent = "tChronometerStop_6";

				/**
				 * [tChronometerStop_6 process_data_begin ] stop
				 */

				/**
				 * [tChronometerStop_6 process_data_end ] start
				 */

				currentComponent = "tChronometerStop_6";

				/**
				 * [tChronometerStop_6 process_data_end ] stop
				 */

				/**
				 * [tChronometerStop_6 end ] start
				 */

				currentComponent = "tChronometerStop_6";

				ok_Hash.put("tChronometerStop_6", true);
				end_Hash.put("tChronometerStop_6", System.currentTimeMillis());

				/**
				 * [tChronometerStop_6 end ] stop
				 */
			} // end the resume

			if (resumeEntryMethodName == null || globalResumeTicket) {
				resumeUtil.addLog("CHECKPOINT", "CONNECTION:SUBJOB_OK:tChronometerStop_6:OnSubjobOk", "",
						Thread.currentThread().getId() + "", "", "", "", "", "");
			}

			if (execStat) {
				runStat.updateStatOnConnection("OnSubjobOk46", 0, "ok");
			}

			tChronometerStart_6Process(globalMap);

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tChronometerStop_6 finally ] start
				 */

				currentComponent = "tChronometerStop_6";

				/**
				 * [tChronometerStop_6 finally ] stop
				 */
			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tChronometerStop_6_SUBPROCESS_STATE", 1);
	}

	public void tChronometerStart_6Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tChronometerStart_6_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [tChronometerStart_6 begin ] start
				 */

				ok_Hash.put("tChronometerStart_6", false);
				start_Hash.put("tChronometerStart_6", System.currentTimeMillis());

				currentComponent = "tChronometerStart_6";

				int tos_count_tChronometerStart_6 = 0;

				Long currentTimetChronometerStart_6 = System.currentTimeMillis();

				globalMap.put("tChronometerStart_6", currentTimetChronometerStart_6);
				globalMap.put("tChronometerStart_6_STARTTIME", currentTimetChronometerStart_6);

				/**
				 * [tChronometerStart_6 begin ] stop
				 */

				/**
				 * [tChronometerStart_6 main ] start
				 */

				currentComponent = "tChronometerStart_6";

				tos_count_tChronometerStart_6++;

				/**
				 * [tChronometerStart_6 main ] stop
				 */

				/**
				 * [tChronometerStart_6 process_data_begin ] start
				 */

				currentComponent = "tChronometerStart_6";

				/**
				 * [tChronometerStart_6 process_data_begin ] stop
				 */

				/**
				 * [tChronometerStart_6 process_data_end ] start
				 */

				currentComponent = "tChronometerStart_6";

				/**
				 * [tChronometerStart_6 process_data_end ] stop
				 */

				/**
				 * [tChronometerStart_6 end ] start
				 */

				currentComponent = "tChronometerStart_6";

				ok_Hash.put("tChronometerStart_6", true);
				end_Hash.put("tChronometerStart_6", System.currentTimeMillis());

				/**
				 * [tChronometerStart_6 end ] stop
				 */
			} // end the resume

			if (resumeEntryMethodName == null || globalResumeTicket) {
				resumeUtil.addLog("CHECKPOINT", "CONNECTION:SUBJOB_OK:tChronometerStart_6:OnSubjobOk", "",
						Thread.currentThread().getId() + "", "", "", "", "", "");
			}

			if (execStat) {
				runStat.updateStatOnConnection("OnSubjobOk22", 0, "ok");
			}

			tRunJob_10Process(globalMap);

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tChronometerStart_6 finally ] start
				 */

				currentComponent = "tChronometerStart_6";

				/**
				 * [tChronometerStart_6 finally ] stop
				 */
			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tChronometerStart_6_SUBPROCESS_STATE", 1);
	}

	public void tRunJob_10Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tRunJob_10_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [tRunJob_10 begin ] start
				 */

				ok_Hash.put("tRunJob_10", false);
				start_Hash.put("tRunJob_10", System.currentTimeMillis());

				currentComponent = "tRunJob_10";

				int tos_count_tRunJob_10 = 0;

				/**
				 * [tRunJob_10 begin ] stop
				 */

				/**
				 * [tRunJob_10 main ] start
				 */

				currentComponent = "tRunJob_10";

				java.util.List<String> paraList_tRunJob_10 = new java.util.ArrayList<String>();

				paraList_tRunJob_10.add("--father_pid=" + pid);

				paraList_tRunJob_10.add("--root_pid=" + rootPid);

				paraList_tRunJob_10.add("--father_node=tRunJob_10");

				paraList_tRunJob_10.add("--context=Default");

				if (enableLogStash) {
					paraList_tRunJob_10.add("--audit.enabled=" + enableLogStash);
				}

				// for feature:10589

				paraList_tRunJob_10.add("--stat_port=" + portStats);

				if (resuming_logs_dir_path != null) {
					paraList_tRunJob_10.add("--resuming_logs_dir_path=" + resuming_logs_dir_path);
				}
				String childResumePath_tRunJob_10 = ResumeUtil.getChildJobCheckPointPath(resuming_checkpoint_path);
				String tRunJobName_tRunJob_10 = ResumeUtil.getRighttRunJob(resuming_checkpoint_path);
				if ("tRunJob_10".equals(tRunJobName_tRunJob_10) && childResumePath_tRunJob_10 != null) {
					paraList_tRunJob_10.add("--resuming_checkpoint_path="
							+ ResumeUtil.getChildJobCheckPointPath(resuming_checkpoint_path));
				}
				paraList_tRunJob_10.add("--parent_part_launcher=JOB:" + jobName + "/NODE:tRunJob_10");

				java.util.Map<String, Object> parentContextMap_tRunJob_10 = new java.util.HashMap<String, Object>();

				context.synchronizeContext();
				class ContextProcessor_tRunJob_10 {
					private void transmitContext_0() {
						parentContextMap_tRunJob_10.put("folderName", context.folderName);
						paraList_tRunJob_10.add("--context_type " + "folderName" + "=" + "id_String");
					}

					public void transmitAllContext() {
						transmitContext_0();
					}
				}
				new ContextProcessor_tRunJob_10().transmitAllContext();
				java.util.Enumeration<?> propertyNames_tRunJob_10 = context.propertyNames();
				while (propertyNames_tRunJob_10.hasMoreElements()) {
					String key_tRunJob_10 = (String) propertyNames_tRunJob_10.nextElement();
					Object value_tRunJob_10 = (Object) context.get(key_tRunJob_10);
					if (value_tRunJob_10 != null) {
						paraList_tRunJob_10.add("--context_param " + key_tRunJob_10 + "=" + value_tRunJob_10);
					} else {
						paraList_tRunJob_10.add("--context_param " + key_tRunJob_10 + "="
								+ NULL_VALUE_EXPRESSION_IN_COMMAND_STRING_FOR_CHILD_JOB_ONLY);
					}

				}

				Object obj_tRunJob_10 = null;

				projetsid.time_0_1.time childJob_tRunJob_10 = new projetsid.time_0_1.time();
				// pass DataSources
				java.util.Map<String, routines.system.TalendDataSource> talendDataSources_tRunJob_10 = (java.util.Map<String, routines.system.TalendDataSource>) globalMap
						.get(KEY_DB_DATASOURCES);
				if (null != talendDataSources_tRunJob_10) {
					java.util.Map<String, javax.sql.DataSource> dataSources_tRunJob_10 = new java.util.HashMap<String, javax.sql.DataSource>();
					for (java.util.Map.Entry<String, routines.system.TalendDataSource> talendDataSourceEntry_tRunJob_10 : talendDataSources_tRunJob_10
							.entrySet()) {
						dataSources_tRunJob_10.put(talendDataSourceEntry_tRunJob_10.getKey(),
								talendDataSourceEntry_tRunJob_10.getValue().getRawDataSource());
					}
					childJob_tRunJob_10.setDataSources(dataSources_tRunJob_10);
				}

				childJob_tRunJob_10.parentContextMap = parentContextMap_tRunJob_10;

				String[][] childReturn_tRunJob_10 = childJob_tRunJob_10
						.runJob((String[]) paraList_tRunJob_10.toArray(new String[paraList_tRunJob_10.size()]));

				if (childJob_tRunJob_10.getErrorCode() == null) {
					globalMap.put("tRunJob_10_CHILD_RETURN_CODE", childJob_tRunJob_10.getStatus() != null
							&& ("failure").equals(childJob_tRunJob_10.getStatus()) ? 1 : 0);
				} else {
					globalMap.put("tRunJob_10_CHILD_RETURN_CODE", childJob_tRunJob_10.getErrorCode());
				}
				if (childJob_tRunJob_10.getExceptionStackTrace() != null) {
					globalMap.put("tRunJob_10_CHILD_EXCEPTION_STACKTRACE",
							childJob_tRunJob_10.getExceptionStackTrace());
				}
				errorCode = childJob_tRunJob_10.getErrorCode();
				if (childJob_tRunJob_10.getErrorCode() != null || ("failure").equals(childJob_tRunJob_10.getStatus())) {
					java.lang.Exception ce_tRunJob_10 = childJob_tRunJob_10.getException();
					throw new RuntimeException("Child job running failed.\n" + ((ce_tRunJob_10 != null)
							? (ce_tRunJob_10.getClass().getName() + ": " + ce_tRunJob_10.getMessage())
							: ""));
				}

				tos_count_tRunJob_10++;

				/**
				 * [tRunJob_10 main ] stop
				 */

				/**
				 * [tRunJob_10 process_data_begin ] start
				 */

				currentComponent = "tRunJob_10";

				/**
				 * [tRunJob_10 process_data_begin ] stop
				 */

				/**
				 * [tRunJob_10 process_data_end ] start
				 */

				currentComponent = "tRunJob_10";

				/**
				 * [tRunJob_10 process_data_end ] stop
				 */

				/**
				 * [tRunJob_10 end ] start
				 */

				currentComponent = "tRunJob_10";

				ok_Hash.put("tRunJob_10", true);
				end_Hash.put("tRunJob_10", System.currentTimeMillis());

				/**
				 * [tRunJob_10 end ] stop
				 */
			} // end the resume

			if (resumeEntryMethodName == null || globalResumeTicket) {
				resumeUtil.addLog("CHECKPOINT", "CONNECTION:SUBJOB_OK:tRunJob_10:OnSubjobOk", "",
						Thread.currentThread().getId() + "", "", "", "", "", "");
			}

			if (execStat) {
				runStat.updateStatOnConnection("OnSubjobOk23", 0, "ok");
			}

			tRunJob_11Process(globalMap);

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tRunJob_10 finally ] start
				 */

				currentComponent = "tRunJob_10";

				/**
				 * [tRunJob_10 finally ] stop
				 */
			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tRunJob_10_SUBPROCESS_STATE", 1);
	}

	public void tRunJob_11Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tRunJob_11_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [tRunJob_11 begin ] start
				 */

				ok_Hash.put("tRunJob_11", false);
				start_Hash.put("tRunJob_11", System.currentTimeMillis());

				currentComponent = "tRunJob_11";

				int tos_count_tRunJob_11 = 0;

				/**
				 * [tRunJob_11 begin ] stop
				 */

				/**
				 * [tRunJob_11 main ] start
				 */

				currentComponent = "tRunJob_11";

				java.util.List<String> paraList_tRunJob_11 = new java.util.ArrayList<String>();

				paraList_tRunJob_11.add("--father_pid=" + pid);

				paraList_tRunJob_11.add("--root_pid=" + rootPid);

				paraList_tRunJob_11.add("--father_node=tRunJob_11");

				paraList_tRunJob_11.add("--context=Default");

				if (enableLogStash) {
					paraList_tRunJob_11.add("--audit.enabled=" + enableLogStash);
				}

				// for feature:10589

				paraList_tRunJob_11.add("--stat_port=" + portStats);

				if (resuming_logs_dir_path != null) {
					paraList_tRunJob_11.add("--resuming_logs_dir_path=" + resuming_logs_dir_path);
				}
				String childResumePath_tRunJob_11 = ResumeUtil.getChildJobCheckPointPath(resuming_checkpoint_path);
				String tRunJobName_tRunJob_11 = ResumeUtil.getRighttRunJob(resuming_checkpoint_path);
				if ("tRunJob_11".equals(tRunJobName_tRunJob_11) && childResumePath_tRunJob_11 != null) {
					paraList_tRunJob_11.add("--resuming_checkpoint_path="
							+ ResumeUtil.getChildJobCheckPointPath(resuming_checkpoint_path));
				}
				paraList_tRunJob_11.add("--parent_part_launcher=JOB:" + jobName + "/NODE:tRunJob_11");

				java.util.Map<String, Object> parentContextMap_tRunJob_11 = new java.util.HashMap<String, Object>();

				context.synchronizeContext();
				class ContextProcessor_tRunJob_11 {
					private void transmitContext_0() {
						parentContextMap_tRunJob_11.put("folderName", context.folderName);
						paraList_tRunJob_11.add("--context_type " + "folderName" + "=" + "id_String");
					}

					public void transmitAllContext() {
						transmitContext_0();
					}
				}
				new ContextProcessor_tRunJob_11().transmitAllContext();
				java.util.Enumeration<?> propertyNames_tRunJob_11 = context.propertyNames();
				while (propertyNames_tRunJob_11.hasMoreElements()) {
					String key_tRunJob_11 = (String) propertyNames_tRunJob_11.nextElement();
					Object value_tRunJob_11 = (Object) context.get(key_tRunJob_11);
					if (value_tRunJob_11 != null) {
						paraList_tRunJob_11.add("--context_param " + key_tRunJob_11 + "=" + value_tRunJob_11);
					} else {
						paraList_tRunJob_11.add("--context_param " + key_tRunJob_11 + "="
								+ NULL_VALUE_EXPRESSION_IN_COMMAND_STRING_FOR_CHILD_JOB_ONLY);
					}

				}

				Object obj_tRunJob_11 = null;

				projetsid.dumpquarter_0_1.dumpQuarter childJob_tRunJob_11 = new projetsid.dumpquarter_0_1.dumpQuarter();
				// pass DataSources
				java.util.Map<String, routines.system.TalendDataSource> talendDataSources_tRunJob_11 = (java.util.Map<String, routines.system.TalendDataSource>) globalMap
						.get(KEY_DB_DATASOURCES);
				if (null != talendDataSources_tRunJob_11) {
					java.util.Map<String, javax.sql.DataSource> dataSources_tRunJob_11 = new java.util.HashMap<String, javax.sql.DataSource>();
					for (java.util.Map.Entry<String, routines.system.TalendDataSource> talendDataSourceEntry_tRunJob_11 : talendDataSources_tRunJob_11
							.entrySet()) {
						dataSources_tRunJob_11.put(talendDataSourceEntry_tRunJob_11.getKey(),
								talendDataSourceEntry_tRunJob_11.getValue().getRawDataSource());
					}
					childJob_tRunJob_11.setDataSources(dataSources_tRunJob_11);
				}

				childJob_tRunJob_11.parentContextMap = parentContextMap_tRunJob_11;

				String[][] childReturn_tRunJob_11 = childJob_tRunJob_11
						.runJob((String[]) paraList_tRunJob_11.toArray(new String[paraList_tRunJob_11.size()]));

				if (childJob_tRunJob_11.getErrorCode() == null) {
					globalMap.put("tRunJob_11_CHILD_RETURN_CODE", childJob_tRunJob_11.getStatus() != null
							&& ("failure").equals(childJob_tRunJob_11.getStatus()) ? 1 : 0);
				} else {
					globalMap.put("tRunJob_11_CHILD_RETURN_CODE", childJob_tRunJob_11.getErrorCode());
				}
				if (childJob_tRunJob_11.getExceptionStackTrace() != null) {
					globalMap.put("tRunJob_11_CHILD_EXCEPTION_STACKTRACE",
							childJob_tRunJob_11.getExceptionStackTrace());
				}
				errorCode = childJob_tRunJob_11.getErrorCode();
				if (childJob_tRunJob_11.getErrorCode() != null || ("failure").equals(childJob_tRunJob_11.getStatus())) {
					java.lang.Exception ce_tRunJob_11 = childJob_tRunJob_11.getException();
					throw new RuntimeException("Child job running failed.\n" + ((ce_tRunJob_11 != null)
							? (ce_tRunJob_11.getClass().getName() + ": " + ce_tRunJob_11.getMessage())
							: ""));
				}

				tos_count_tRunJob_11++;

				/**
				 * [tRunJob_11 main ] stop
				 */

				/**
				 * [tRunJob_11 process_data_begin ] start
				 */

				currentComponent = "tRunJob_11";

				/**
				 * [tRunJob_11 process_data_begin ] stop
				 */

				/**
				 * [tRunJob_11 process_data_end ] start
				 */

				currentComponent = "tRunJob_11";

				/**
				 * [tRunJob_11 process_data_end ] stop
				 */

				/**
				 * [tRunJob_11 end ] start
				 */

				currentComponent = "tRunJob_11";

				ok_Hash.put("tRunJob_11", true);
				end_Hash.put("tRunJob_11", System.currentTimeMillis());

				/**
				 * [tRunJob_11 end ] stop
				 */
			} // end the resume

			if (resumeEntryMethodName == null || globalResumeTicket) {
				resumeUtil.addLog("CHECKPOINT", "CONNECTION:SUBJOB_OK:tRunJob_11:OnSubjobOk", "",
						Thread.currentThread().getId() + "", "", "", "", "", "");
			}

			if (execStat) {
				runStat.updateStatOnConnection("OnSubjobOk24", 0, "ok");
			}

			tChronometerStop_7Process(globalMap);

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tRunJob_11 finally ] start
				 */

				currentComponent = "tRunJob_11";

				/**
				 * [tRunJob_11 finally ] stop
				 */
			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tRunJob_11_SUBPROCESS_STATE", 1);
	}

	public void tChronometerStop_7Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tChronometerStop_7_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [tChronometerStop_7 begin ] start
				 */

				ok_Hash.put("tChronometerStop_7", false);
				start_Hash.put("tChronometerStop_7", System.currentTimeMillis());

				currentComponent = "tChronometerStop_7";

				int tos_count_tChronometerStop_7 = 0;

				long timetChronometerStop_7;

				timetChronometerStop_7 = System.currentTimeMillis()
						- ((Long) globalMap.get("tChronometerStart_6")).longValue();

				System.out.print("[ tChronometerStop_7 ]  ");

				System.out.print("   " + timetChronometerStop_7 / 1000 + "seconds   ");

				System.out.println("TimeX + Time_Quarter" + "  " + timetChronometerStop_7 + " milliseconds");

				Long currentTimetChronometerStop_7 = System.currentTimeMillis();
				globalMap.put("tChronometerStop_7", currentTimetChronometerStop_7);

				globalMap.put("tChronometerStop_7_STOPTIME", currentTimetChronometerStop_7);
				globalMap.put("tChronometerStop_7_DURATION", timetChronometerStop_7);

				/**
				 * [tChronometerStop_7 begin ] stop
				 */

				/**
				 * [tChronometerStop_7 main ] start
				 */

				currentComponent = "tChronometerStop_7";

				tos_count_tChronometerStop_7++;

				/**
				 * [tChronometerStop_7 main ] stop
				 */

				/**
				 * [tChronometerStop_7 process_data_begin ] start
				 */

				currentComponent = "tChronometerStop_7";

				/**
				 * [tChronometerStop_7 process_data_begin ] stop
				 */

				/**
				 * [tChronometerStop_7 process_data_end ] start
				 */

				currentComponent = "tChronometerStop_7";

				/**
				 * [tChronometerStop_7 process_data_end ] stop
				 */

				/**
				 * [tChronometerStop_7 end ] start
				 */

				currentComponent = "tChronometerStop_7";

				ok_Hash.put("tChronometerStop_7", true);
				end_Hash.put("tChronometerStop_7", System.currentTimeMillis());

				/**
				 * [tChronometerStop_7 end ] stop
				 */
			} // end the resume

			if (resumeEntryMethodName == null || globalResumeTicket) {
				resumeUtil.addLog("CHECKPOINT", "CONNECTION:SUBJOB_OK:tChronometerStop_7:OnSubjobOk", "",
						Thread.currentThread().getId() + "", "", "", "", "", "");
			}

			if (execStat) {
				runStat.updateStatOnConnection("OnSubjobOk47", 0, "ok");
			}

			tChronometerStart_7Process(globalMap);

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tChronometerStop_7 finally ] start
				 */

				currentComponent = "tChronometerStop_7";

				/**
				 * [tChronometerStop_7 finally ] stop
				 */
			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tChronometerStop_7_SUBPROCESS_STATE", 1);
	}

	public void tChronometerStart_7Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tChronometerStart_7_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [tChronometerStart_7 begin ] start
				 */

				ok_Hash.put("tChronometerStart_7", false);
				start_Hash.put("tChronometerStart_7", System.currentTimeMillis());

				currentComponent = "tChronometerStart_7";

				int tos_count_tChronometerStart_7 = 0;

				Long currentTimetChronometerStart_7 = System.currentTimeMillis();

				globalMap.put("tChronometerStart_7", currentTimetChronometerStart_7);
				globalMap.put("tChronometerStart_7_STARTTIME", currentTimetChronometerStart_7);

				/**
				 * [tChronometerStart_7 begin ] stop
				 */

				/**
				 * [tChronometerStart_7 main ] start
				 */

				currentComponent = "tChronometerStart_7";

				tos_count_tChronometerStart_7++;

				/**
				 * [tChronometerStart_7 main ] stop
				 */

				/**
				 * [tChronometerStart_7 process_data_begin ] start
				 */

				currentComponent = "tChronometerStart_7";

				/**
				 * [tChronometerStart_7 process_data_begin ] stop
				 */

				/**
				 * [tChronometerStart_7 process_data_end ] start
				 */

				currentComponent = "tChronometerStart_7";

				/**
				 * [tChronometerStart_7 process_data_end ] stop
				 */

				/**
				 * [tChronometerStart_7 end ] start
				 */

				currentComponent = "tChronometerStart_7";

				ok_Hash.put("tChronometerStart_7", true);
				end_Hash.put("tChronometerStart_7", System.currentTimeMillis());

				/**
				 * [tChronometerStart_7 end ] stop
				 */
			} // end the resume

			if (resumeEntryMethodName == null || globalResumeTicket) {
				resumeUtil.addLog("CHECKPOINT", "CONNECTION:SUBJOB_OK:tChronometerStart_7:OnSubjobOk", "",
						Thread.currentThread().getId() + "", "", "", "", "", "");
			}

			if (execStat) {
				runStat.updateStatOnConnection("OnSubjobOk25", 0, "ok");
			}

			tRunJob_13Process(globalMap);

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tChronometerStart_7 finally ] start
				 */

				currentComponent = "tChronometerStart_7";

				/**
				 * [tChronometerStart_7 finally ] stop
				 */
			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tChronometerStart_7_SUBPROCESS_STATE", 1);
	}

	public void tRunJob_13Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tRunJob_13_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [tRunJob_13 begin ] start
				 */

				ok_Hash.put("tRunJob_13", false);
				start_Hash.put("tRunJob_13", System.currentTimeMillis());

				currentComponent = "tRunJob_13";

				int tos_count_tRunJob_13 = 0;

				/**
				 * [tRunJob_13 begin ] stop
				 */

				/**
				 * [tRunJob_13 main ] start
				 */

				currentComponent = "tRunJob_13";

				java.util.List<String> paraList_tRunJob_13 = new java.util.ArrayList<String>();

				paraList_tRunJob_13.add("--father_pid=" + pid);

				paraList_tRunJob_13.add("--root_pid=" + rootPid);

				paraList_tRunJob_13.add("--father_node=tRunJob_13");

				paraList_tRunJob_13.add("--context=Default");

				if (enableLogStash) {
					paraList_tRunJob_13.add("--audit.enabled=" + enableLogStash);
				}

				// for feature:10589

				paraList_tRunJob_13.add("--stat_port=" + portStats);

				if (resuming_logs_dir_path != null) {
					paraList_tRunJob_13.add("--resuming_logs_dir_path=" + resuming_logs_dir_path);
				}
				String childResumePath_tRunJob_13 = ResumeUtil.getChildJobCheckPointPath(resuming_checkpoint_path);
				String tRunJobName_tRunJob_13 = ResumeUtil.getRighttRunJob(resuming_checkpoint_path);
				if ("tRunJob_13".equals(tRunJobName_tRunJob_13) && childResumePath_tRunJob_13 != null) {
					paraList_tRunJob_13.add("--resuming_checkpoint_path="
							+ ResumeUtil.getChildJobCheckPointPath(resuming_checkpoint_path));
				}
				paraList_tRunJob_13.add("--parent_part_launcher=JOB:" + jobName + "/NODE:tRunJob_13");

				java.util.Map<String, Object> parentContextMap_tRunJob_13 = new java.util.HashMap<String, Object>();

				context.synchronizeContext();
				class ContextProcessor_tRunJob_13 {
					private void transmitContext_0() {
						parentContextMap_tRunJob_13.put("folderName", context.folderName);
						paraList_tRunJob_13.add("--context_type " + "folderName" + "=" + "id_String");
					}

					public void transmitAllContext() {
						transmitContext_0();
					}
				}
				new ContextProcessor_tRunJob_13().transmitAllContext();
				java.util.Enumeration<?> propertyNames_tRunJob_13 = context.propertyNames();
				while (propertyNames_tRunJob_13.hasMoreElements()) {
					String key_tRunJob_13 = (String) propertyNames_tRunJob_13.nextElement();
					Object value_tRunJob_13 = (Object) context.get(key_tRunJob_13);
					if (value_tRunJob_13 != null) {
						paraList_tRunJob_13.add("--context_param " + key_tRunJob_13 + "=" + value_tRunJob_13);
					} else {
						paraList_tRunJob_13.add("--context_param " + key_tRunJob_13 + "="
								+ NULL_VALUE_EXPRESSION_IN_COMMAND_STRING_FOR_CHILD_JOB_ONLY);
					}

				}

				Object obj_tRunJob_13 = null;

				projetsid.dumpmonth_0_1.dumpMonth childJob_tRunJob_13 = new projetsid.dumpmonth_0_1.dumpMonth();
				// pass DataSources
				java.util.Map<String, routines.system.TalendDataSource> talendDataSources_tRunJob_13 = (java.util.Map<String, routines.system.TalendDataSource>) globalMap
						.get(KEY_DB_DATASOURCES);
				if (null != talendDataSources_tRunJob_13) {
					java.util.Map<String, javax.sql.DataSource> dataSources_tRunJob_13 = new java.util.HashMap<String, javax.sql.DataSource>();
					for (java.util.Map.Entry<String, routines.system.TalendDataSource> talendDataSourceEntry_tRunJob_13 : talendDataSources_tRunJob_13
							.entrySet()) {
						dataSources_tRunJob_13.put(talendDataSourceEntry_tRunJob_13.getKey(),
								talendDataSourceEntry_tRunJob_13.getValue().getRawDataSource());
					}
					childJob_tRunJob_13.setDataSources(dataSources_tRunJob_13);
				}

				childJob_tRunJob_13.parentContextMap = parentContextMap_tRunJob_13;

				String[][] childReturn_tRunJob_13 = childJob_tRunJob_13
						.runJob((String[]) paraList_tRunJob_13.toArray(new String[paraList_tRunJob_13.size()]));

				if (childJob_tRunJob_13.getErrorCode() == null) {
					globalMap.put("tRunJob_13_CHILD_RETURN_CODE", childJob_tRunJob_13.getStatus() != null
							&& ("failure").equals(childJob_tRunJob_13.getStatus()) ? 1 : 0);
				} else {
					globalMap.put("tRunJob_13_CHILD_RETURN_CODE", childJob_tRunJob_13.getErrorCode());
				}
				if (childJob_tRunJob_13.getExceptionStackTrace() != null) {
					globalMap.put("tRunJob_13_CHILD_EXCEPTION_STACKTRACE",
							childJob_tRunJob_13.getExceptionStackTrace());
				}
				errorCode = childJob_tRunJob_13.getErrorCode();
				if (childJob_tRunJob_13.getErrorCode() != null || ("failure").equals(childJob_tRunJob_13.getStatus())) {
					java.lang.Exception ce_tRunJob_13 = childJob_tRunJob_13.getException();
					throw new RuntimeException("Child job running failed.\n" + ((ce_tRunJob_13 != null)
							? (ce_tRunJob_13.getClass().getName() + ": " + ce_tRunJob_13.getMessage())
							: ""));
				}

				tos_count_tRunJob_13++;

				/**
				 * [tRunJob_13 main ] stop
				 */

				/**
				 * [tRunJob_13 process_data_begin ] start
				 */

				currentComponent = "tRunJob_13";

				/**
				 * [tRunJob_13 process_data_begin ] stop
				 */

				/**
				 * [tRunJob_13 process_data_end ] start
				 */

				currentComponent = "tRunJob_13";

				/**
				 * [tRunJob_13 process_data_end ] stop
				 */

				/**
				 * [tRunJob_13 end ] start
				 */

				currentComponent = "tRunJob_13";

				ok_Hash.put("tRunJob_13", true);
				end_Hash.put("tRunJob_13", System.currentTimeMillis());

				/**
				 * [tRunJob_13 end ] stop
				 */
			} // end the resume

			if (resumeEntryMethodName == null || globalResumeTicket) {
				resumeUtil.addLog("CHECKPOINT", "CONNECTION:SUBJOB_OK:tRunJob_13:OnSubjobOk", "",
						Thread.currentThread().getId() + "", "", "", "", "", "");
			}

			if (execStat) {
				runStat.updateStatOnConnection("OnSubjobOk27", 0, "ok");
			}

			tChronometerStop_8Process(globalMap);

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tRunJob_13 finally ] start
				 */

				currentComponent = "tRunJob_13";

				/**
				 * [tRunJob_13 finally ] stop
				 */
			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tRunJob_13_SUBPROCESS_STATE", 1);
	}

	public void tChronometerStop_8Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tChronometerStop_8_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [tChronometerStop_8 begin ] start
				 */

				ok_Hash.put("tChronometerStop_8", false);
				start_Hash.put("tChronometerStop_8", System.currentTimeMillis());

				currentComponent = "tChronometerStop_8";

				int tos_count_tChronometerStop_8 = 0;

				long timetChronometerStop_8;

				timetChronometerStop_8 = System.currentTimeMillis()
						- ((Long) globalMap.get("tChronometerStart_7")).longValue();

				System.out.print("[ tChronometerStop_8 ]  ");

				System.out.print("   " + timetChronometerStop_8 / 1000 + "seconds   ");

				System.out.println("Time_Month " + "  " + timetChronometerStop_8 + " milliseconds");

				Long currentTimetChronometerStop_8 = System.currentTimeMillis();
				globalMap.put("tChronometerStop_8", currentTimetChronometerStop_8);

				globalMap.put("tChronometerStop_8_STOPTIME", currentTimetChronometerStop_8);
				globalMap.put("tChronometerStop_8_DURATION", timetChronometerStop_8);

				/**
				 * [tChronometerStop_8 begin ] stop
				 */

				/**
				 * [tChronometerStop_8 main ] start
				 */

				currentComponent = "tChronometerStop_8";

				tos_count_tChronometerStop_8++;

				/**
				 * [tChronometerStop_8 main ] stop
				 */

				/**
				 * [tChronometerStop_8 process_data_begin ] start
				 */

				currentComponent = "tChronometerStop_8";

				/**
				 * [tChronometerStop_8 process_data_begin ] stop
				 */

				/**
				 * [tChronometerStop_8 process_data_end ] start
				 */

				currentComponent = "tChronometerStop_8";

				/**
				 * [tChronometerStop_8 process_data_end ] stop
				 */

				/**
				 * [tChronometerStop_8 end ] start
				 */

				currentComponent = "tChronometerStop_8";

				ok_Hash.put("tChronometerStop_8", true);
				end_Hash.put("tChronometerStop_8", System.currentTimeMillis());

				/**
				 * [tChronometerStop_8 end ] stop
				 */
			} // end the resume

			if (resumeEntryMethodName == null || globalResumeTicket) {
				resumeUtil.addLog("CHECKPOINT", "CONNECTION:SUBJOB_OK:tChronometerStop_8:OnSubjobOk", "",
						Thread.currentThread().getId() + "", "", "", "", "", "");
			}

			if (execStat) {
				runStat.updateStatOnConnection("OnSubjobOk48", 0, "ok");
			}

			tChronometerStart_8Process(globalMap);

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tChronometerStop_8 finally ] start
				 */

				currentComponent = "tChronometerStop_8";

				/**
				 * [tChronometerStop_8 finally ] stop
				 */
			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tChronometerStop_8_SUBPROCESS_STATE", 1);
	}

	public void tChronometerStart_8Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tChronometerStart_8_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [tChronometerStart_8 begin ] start
				 */

				ok_Hash.put("tChronometerStart_8", false);
				start_Hash.put("tChronometerStart_8", System.currentTimeMillis());

				currentComponent = "tChronometerStart_8";

				int tos_count_tChronometerStart_8 = 0;

				Long currentTimetChronometerStart_8 = System.currentTimeMillis();

				globalMap.put("tChronometerStart_8", currentTimetChronometerStart_8);
				globalMap.put("tChronometerStart_8_STARTTIME", currentTimetChronometerStart_8);

				/**
				 * [tChronometerStart_8 begin ] stop
				 */

				/**
				 * [tChronometerStart_8 main ] start
				 */

				currentComponent = "tChronometerStart_8";

				tos_count_tChronometerStart_8++;

				/**
				 * [tChronometerStart_8 main ] stop
				 */

				/**
				 * [tChronometerStart_8 process_data_begin ] start
				 */

				currentComponent = "tChronometerStart_8";

				/**
				 * [tChronometerStart_8 process_data_begin ] stop
				 */

				/**
				 * [tChronometerStart_8 process_data_end ] start
				 */

				currentComponent = "tChronometerStart_8";

				/**
				 * [tChronometerStart_8 process_data_end ] stop
				 */

				/**
				 * [tChronometerStart_8 end ] start
				 */

				currentComponent = "tChronometerStart_8";

				ok_Hash.put("tChronometerStart_8", true);
				end_Hash.put("tChronometerStart_8", System.currentTimeMillis());

				/**
				 * [tChronometerStart_8 end ] stop
				 */
			} // end the resume

			if (resumeEntryMethodName == null || globalResumeTicket) {
				resumeUtil.addLog("CHECKPOINT", "CONNECTION:SUBJOB_OK:tChronometerStart_8:OnSubjobOk", "",
						Thread.currentThread().getId() + "", "", "", "", "", "");
			}

			if (execStat) {
				runStat.updateStatOnConnection("OnSubjobOk26", 0, "ok");
			}

			tRunJob_15Process(globalMap);

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tChronometerStart_8 finally ] start
				 */

				currentComponent = "tChronometerStart_8";

				/**
				 * [tChronometerStart_8 finally ] stop
				 */
			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tChronometerStart_8_SUBPROCESS_STATE", 1);
	}

	public void tRunJob_15Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tRunJob_15_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [tRunJob_15 begin ] start
				 */

				ok_Hash.put("tRunJob_15", false);
				start_Hash.put("tRunJob_15", System.currentTimeMillis());

				currentComponent = "tRunJob_15";

				int tos_count_tRunJob_15 = 0;

				/**
				 * [tRunJob_15 begin ] stop
				 */

				/**
				 * [tRunJob_15 main ] start
				 */

				currentComponent = "tRunJob_15";

				java.util.List<String> paraList_tRunJob_15 = new java.util.ArrayList<String>();

				paraList_tRunJob_15.add("--father_pid=" + pid);

				paraList_tRunJob_15.add("--root_pid=" + rootPid);

				paraList_tRunJob_15.add("--father_node=tRunJob_15");

				paraList_tRunJob_15.add("--context=Default");

				if (enableLogStash) {
					paraList_tRunJob_15.add("--audit.enabled=" + enableLogStash);
				}

				// for feature:10589

				paraList_tRunJob_15.add("--stat_port=" + portStats);

				if (resuming_logs_dir_path != null) {
					paraList_tRunJob_15.add("--resuming_logs_dir_path=" + resuming_logs_dir_path);
				}
				String childResumePath_tRunJob_15 = ResumeUtil.getChildJobCheckPointPath(resuming_checkpoint_path);
				String tRunJobName_tRunJob_15 = ResumeUtil.getRighttRunJob(resuming_checkpoint_path);
				if ("tRunJob_15".equals(tRunJobName_tRunJob_15) && childResumePath_tRunJob_15 != null) {
					paraList_tRunJob_15.add("--resuming_checkpoint_path="
							+ ResumeUtil.getChildJobCheckPointPath(resuming_checkpoint_path));
				}
				paraList_tRunJob_15.add("--parent_part_launcher=JOB:" + jobName + "/NODE:tRunJob_15");

				java.util.Map<String, Object> parentContextMap_tRunJob_15 = new java.util.HashMap<String, Object>();

				context.synchronizeContext();
				class ContextProcessor_tRunJob_15 {
					private void transmitContext_0() {
						parentContextMap_tRunJob_15.put("folderName", context.folderName);
						paraList_tRunJob_15.add("--context_type " + "folderName" + "=" + "id_String");
					}

					public void transmitAllContext() {
						transmitContext_0();
					}
				}
				new ContextProcessor_tRunJob_15().transmitAllContext();
				java.util.Enumeration<?> propertyNames_tRunJob_15 = context.propertyNames();
				while (propertyNames_tRunJob_15.hasMoreElements()) {
					String key_tRunJob_15 = (String) propertyNames_tRunJob_15.nextElement();
					Object value_tRunJob_15 = (Object) context.get(key_tRunJob_15);
					if (value_tRunJob_15 != null) {
						paraList_tRunJob_15.add("--context_param " + key_tRunJob_15 + "=" + value_tRunJob_15);
					} else {
						paraList_tRunJob_15.add("--context_param " + key_tRunJob_15 + "="
								+ NULL_VALUE_EXPRESSION_IN_COMMAND_STRING_FOR_CHILD_JOB_ONLY);
					}

				}

				Object obj_tRunJob_15 = null;

				projetsid.dumpdate_0_1.dumpDate childJob_tRunJob_15 = new projetsid.dumpdate_0_1.dumpDate();
				// pass DataSources
				java.util.Map<String, routines.system.TalendDataSource> talendDataSources_tRunJob_15 = (java.util.Map<String, routines.system.TalendDataSource>) globalMap
						.get(KEY_DB_DATASOURCES);
				if (null != talendDataSources_tRunJob_15) {
					java.util.Map<String, javax.sql.DataSource> dataSources_tRunJob_15 = new java.util.HashMap<String, javax.sql.DataSource>();
					for (java.util.Map.Entry<String, routines.system.TalendDataSource> talendDataSourceEntry_tRunJob_15 : talendDataSources_tRunJob_15
							.entrySet()) {
						dataSources_tRunJob_15.put(talendDataSourceEntry_tRunJob_15.getKey(),
								talendDataSourceEntry_tRunJob_15.getValue().getRawDataSource());
					}
					childJob_tRunJob_15.setDataSources(dataSources_tRunJob_15);
				}

				childJob_tRunJob_15.parentContextMap = parentContextMap_tRunJob_15;

				String[][] childReturn_tRunJob_15 = childJob_tRunJob_15
						.runJob((String[]) paraList_tRunJob_15.toArray(new String[paraList_tRunJob_15.size()]));

				if (childJob_tRunJob_15.getErrorCode() == null) {
					globalMap.put("tRunJob_15_CHILD_RETURN_CODE", childJob_tRunJob_15.getStatus() != null
							&& ("failure").equals(childJob_tRunJob_15.getStatus()) ? 1 : 0);
				} else {
					globalMap.put("tRunJob_15_CHILD_RETURN_CODE", childJob_tRunJob_15.getErrorCode());
				}
				if (childJob_tRunJob_15.getExceptionStackTrace() != null) {
					globalMap.put("tRunJob_15_CHILD_EXCEPTION_STACKTRACE",
							childJob_tRunJob_15.getExceptionStackTrace());
				}
				errorCode = childJob_tRunJob_15.getErrorCode();
				if (childJob_tRunJob_15.getErrorCode() != null || ("failure").equals(childJob_tRunJob_15.getStatus())) {
					java.lang.Exception ce_tRunJob_15 = childJob_tRunJob_15.getException();
					throw new RuntimeException("Child job running failed.\n" + ((ce_tRunJob_15 != null)
							? (ce_tRunJob_15.getClass().getName() + ": " + ce_tRunJob_15.getMessage())
							: ""));
				}

				tos_count_tRunJob_15++;

				/**
				 * [tRunJob_15 main ] stop
				 */

				/**
				 * [tRunJob_15 process_data_begin ] start
				 */

				currentComponent = "tRunJob_15";

				/**
				 * [tRunJob_15 process_data_begin ] stop
				 */

				/**
				 * [tRunJob_15 process_data_end ] start
				 */

				currentComponent = "tRunJob_15";

				/**
				 * [tRunJob_15 process_data_end ] stop
				 */

				/**
				 * [tRunJob_15 end ] start
				 */

				currentComponent = "tRunJob_15";

				ok_Hash.put("tRunJob_15", true);
				end_Hash.put("tRunJob_15", System.currentTimeMillis());

				/**
				 * [tRunJob_15 end ] stop
				 */
			} // end the resume

			if (resumeEntryMethodName == null || globalResumeTicket) {
				resumeUtil.addLog("CHECKPOINT", "CONNECTION:SUBJOB_OK:tRunJob_15:OnSubjobOk", "",
						Thread.currentThread().getId() + "", "", "", "", "", "");
			}

			if (execStat) {
				runStat.updateStatOnConnection("OnSubjobOk30", 0, "ok");
			}

			tChronometerStop_9Process(globalMap);

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tRunJob_15 finally ] start
				 */

				currentComponent = "tRunJob_15";

				/**
				 * [tRunJob_15 finally ] stop
				 */
			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tRunJob_15_SUBPROCESS_STATE", 1);
	}

	public void tChronometerStop_9Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tChronometerStop_9_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [tChronometerStop_9 begin ] start
				 */

				ok_Hash.put("tChronometerStop_9", false);
				start_Hash.put("tChronometerStop_9", System.currentTimeMillis());

				currentComponent = "tChronometerStop_9";

				int tos_count_tChronometerStop_9 = 0;

				long timetChronometerStop_9;

				timetChronometerStop_9 = System.currentTimeMillis()
						- ((Long) globalMap.get("tChronometerStart_8")).longValue();

				System.out.print("[ tChronometerStop_9 ]  ");

				System.out.print("   " + timetChronometerStop_9 / 1000 + "seconds   ");

				System.out.println("Time_Date" + "  " + timetChronometerStop_9 + " milliseconds");

				Long currentTimetChronometerStop_9 = System.currentTimeMillis();
				globalMap.put("tChronometerStop_9", currentTimetChronometerStop_9);

				globalMap.put("tChronometerStop_9_STOPTIME", currentTimetChronometerStop_9);
				globalMap.put("tChronometerStop_9_DURATION", timetChronometerStop_9);

				/**
				 * [tChronometerStop_9 begin ] stop
				 */

				/**
				 * [tChronometerStop_9 main ] start
				 */

				currentComponent = "tChronometerStop_9";

				tos_count_tChronometerStop_9++;

				/**
				 * [tChronometerStop_9 main ] stop
				 */

				/**
				 * [tChronometerStop_9 process_data_begin ] start
				 */

				currentComponent = "tChronometerStop_9";

				/**
				 * [tChronometerStop_9 process_data_begin ] stop
				 */

				/**
				 * [tChronometerStop_9 process_data_end ] start
				 */

				currentComponent = "tChronometerStop_9";

				/**
				 * [tChronometerStop_9 process_data_end ] stop
				 */

				/**
				 * [tChronometerStop_9 end ] start
				 */

				currentComponent = "tChronometerStop_9";

				ok_Hash.put("tChronometerStop_9", true);
				end_Hash.put("tChronometerStop_9", System.currentTimeMillis());

				/**
				 * [tChronometerStop_9 end ] stop
				 */
			} // end the resume

			if (resumeEntryMethodName == null || globalResumeTicket) {
				resumeUtil.addLog("CHECKPOINT", "CONNECTION:SUBJOB_OK:tChronometerStop_9:OnSubjobOk", "",
						Thread.currentThread().getId() + "", "", "", "", "", "");
			}

			if (execStat) {
				runStat.updateStatOnConnection("OnSubjobOk49", 0, "ok");
			}

			tChronometerStart_9Process(globalMap);

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tChronometerStop_9 finally ] start
				 */

				currentComponent = "tChronometerStop_9";

				/**
				 * [tChronometerStop_9 finally ] stop
				 */
			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tChronometerStop_9_SUBPROCESS_STATE", 1);
	}

	public void tChronometerStart_9Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tChronometerStart_9_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [tChronometerStart_9 begin ] start
				 */

				ok_Hash.put("tChronometerStart_9", false);
				start_Hash.put("tChronometerStart_9", System.currentTimeMillis());

				currentComponent = "tChronometerStart_9";

				int tos_count_tChronometerStart_9 = 0;

				Long currentTimetChronometerStart_9 = System.currentTimeMillis();

				globalMap.put("tChronometerStart_9", currentTimetChronometerStart_9);
				globalMap.put("tChronometerStart_9_STARTTIME", currentTimetChronometerStart_9);

				/**
				 * [tChronometerStart_9 begin ] stop
				 */

				/**
				 * [tChronometerStart_9 main ] start
				 */

				currentComponent = "tChronometerStart_9";

				tos_count_tChronometerStart_9++;

				/**
				 * [tChronometerStart_9 main ] stop
				 */

				/**
				 * [tChronometerStart_9 process_data_begin ] start
				 */

				currentComponent = "tChronometerStart_9";

				/**
				 * [tChronometerStart_9 process_data_begin ] stop
				 */

				/**
				 * [tChronometerStart_9 process_data_end ] start
				 */

				currentComponent = "tChronometerStart_9";

				/**
				 * [tChronometerStart_9 process_data_end ] stop
				 */

				/**
				 * [tChronometerStart_9 end ] start
				 */

				currentComponent = "tChronometerStart_9";

				ok_Hash.put("tChronometerStart_9", true);
				end_Hash.put("tChronometerStart_9", System.currentTimeMillis());

				/**
				 * [tChronometerStart_9 end ] stop
				 */
			} // end the resume

			if (resumeEntryMethodName == null || globalResumeTicket) {
				resumeUtil.addLog("CHECKPOINT", "CONNECTION:SUBJOB_OK:tChronometerStart_9:OnSubjobOk", "",
						Thread.currentThread().getId() + "", "", "", "", "", "");
			}

			if (execStat) {
				runStat.updateStatOnConnection("OnSubjobOk31", 0, "ok");
			}

			tRunJob_16Process(globalMap);

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tChronometerStart_9 finally ] start
				 */

				currentComponent = "tChronometerStart_9";

				/**
				 * [tChronometerStart_9 finally ] stop
				 */
			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tChronometerStart_9_SUBPROCESS_STATE", 1);
	}

	public void tRunJob_16Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tRunJob_16_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [tRunJob_16 begin ] start
				 */

				ok_Hash.put("tRunJob_16", false);
				start_Hash.put("tRunJob_16", System.currentTimeMillis());

				currentComponent = "tRunJob_16";

				int tos_count_tRunJob_16 = 0;

				/**
				 * [tRunJob_16 begin ] stop
				 */

				/**
				 * [tRunJob_16 main ] start
				 */

				currentComponent = "tRunJob_16";

				java.util.List<String> paraList_tRunJob_16 = new java.util.ArrayList<String>();

				paraList_tRunJob_16.add("--father_pid=" + pid);

				paraList_tRunJob_16.add("--root_pid=" + rootPid);

				paraList_tRunJob_16.add("--father_node=tRunJob_16");

				paraList_tRunJob_16.add("--context=Default");

				if (enableLogStash) {
					paraList_tRunJob_16.add("--audit.enabled=" + enableLogStash);
				}

				// for feature:10589

				paraList_tRunJob_16.add("--stat_port=" + portStats);

				if (resuming_logs_dir_path != null) {
					paraList_tRunJob_16.add("--resuming_logs_dir_path=" + resuming_logs_dir_path);
				}
				String childResumePath_tRunJob_16 = ResumeUtil.getChildJobCheckPointPath(resuming_checkpoint_path);
				String tRunJobName_tRunJob_16 = ResumeUtil.getRighttRunJob(resuming_checkpoint_path);
				if ("tRunJob_16".equals(tRunJobName_tRunJob_16) && childResumePath_tRunJob_16 != null) {
					paraList_tRunJob_16.add("--resuming_checkpoint_path="
							+ ResumeUtil.getChildJobCheckPointPath(resuming_checkpoint_path));
				}
				paraList_tRunJob_16.add("--parent_part_launcher=JOB:" + jobName + "/NODE:tRunJob_16");

				java.util.Map<String, Object> parentContextMap_tRunJob_16 = new java.util.HashMap<String, Object>();

				context.synchronizeContext();
				class ContextProcessor_tRunJob_16 {
					private void transmitContext_0() {
						parentContextMap_tRunJob_16.put("folderName", context.folderName);
						paraList_tRunJob_16.add("--context_type " + "folderName" + "=" + "id_String");
					}

					public void transmitAllContext() {
						transmitContext_0();
					}
				}
				new ContextProcessor_tRunJob_16().transmitAllContext();
				java.util.Enumeration<?> propertyNames_tRunJob_16 = context.propertyNames();
				while (propertyNames_tRunJob_16.hasMoreElements()) {
					String key_tRunJob_16 = (String) propertyNames_tRunJob_16.nextElement();
					Object value_tRunJob_16 = (Object) context.get(key_tRunJob_16);
					if (value_tRunJob_16 != null) {
						paraList_tRunJob_16.add("--context_param " + key_tRunJob_16 + "=" + value_tRunJob_16);
					} else {
						paraList_tRunJob_16.add("--context_param " + key_tRunJob_16 + "="
								+ NULL_VALUE_EXPRESSION_IN_COMMAND_STRING_FOR_CHILD_JOB_ONLY);
					}

				}

				Object obj_tRunJob_16 = null;

				projetsid.employee_0_1.employee childJob_tRunJob_16 = new projetsid.employee_0_1.employee();
				// pass DataSources
				java.util.Map<String, routines.system.TalendDataSource> talendDataSources_tRunJob_16 = (java.util.Map<String, routines.system.TalendDataSource>) globalMap
						.get(KEY_DB_DATASOURCES);
				if (null != talendDataSources_tRunJob_16) {
					java.util.Map<String, javax.sql.DataSource> dataSources_tRunJob_16 = new java.util.HashMap<String, javax.sql.DataSource>();
					for (java.util.Map.Entry<String, routines.system.TalendDataSource> talendDataSourceEntry_tRunJob_16 : talendDataSources_tRunJob_16
							.entrySet()) {
						dataSources_tRunJob_16.put(talendDataSourceEntry_tRunJob_16.getKey(),
								talendDataSourceEntry_tRunJob_16.getValue().getRawDataSource());
					}
					childJob_tRunJob_16.setDataSources(dataSources_tRunJob_16);
				}

				childJob_tRunJob_16.parentContextMap = parentContextMap_tRunJob_16;

				String[][] childReturn_tRunJob_16 = childJob_tRunJob_16
						.runJob((String[]) paraList_tRunJob_16.toArray(new String[paraList_tRunJob_16.size()]));

				if (childJob_tRunJob_16.getErrorCode() == null) {
					globalMap.put("tRunJob_16_CHILD_RETURN_CODE", childJob_tRunJob_16.getStatus() != null
							&& ("failure").equals(childJob_tRunJob_16.getStatus()) ? 1 : 0);
				} else {
					globalMap.put("tRunJob_16_CHILD_RETURN_CODE", childJob_tRunJob_16.getErrorCode());
				}
				if (childJob_tRunJob_16.getExceptionStackTrace() != null) {
					globalMap.put("tRunJob_16_CHILD_EXCEPTION_STACKTRACE",
							childJob_tRunJob_16.getExceptionStackTrace());
				}
				errorCode = childJob_tRunJob_16.getErrorCode();
				if (childJob_tRunJob_16.getErrorCode() != null || ("failure").equals(childJob_tRunJob_16.getStatus())) {
					java.lang.Exception ce_tRunJob_16 = childJob_tRunJob_16.getException();
					throw new RuntimeException("Child job running failed.\n" + ((ce_tRunJob_16 != null)
							? (ce_tRunJob_16.getClass().getName() + ": " + ce_tRunJob_16.getMessage())
							: ""));
				}

				tos_count_tRunJob_16++;

				/**
				 * [tRunJob_16 main ] stop
				 */

				/**
				 * [tRunJob_16 process_data_begin ] start
				 */

				currentComponent = "tRunJob_16";

				/**
				 * [tRunJob_16 process_data_begin ] stop
				 */

				/**
				 * [tRunJob_16 process_data_end ] start
				 */

				currentComponent = "tRunJob_16";

				/**
				 * [tRunJob_16 process_data_end ] stop
				 */

				/**
				 * [tRunJob_16 end ] start
				 */

				currentComponent = "tRunJob_16";

				ok_Hash.put("tRunJob_16", true);
				end_Hash.put("tRunJob_16", System.currentTimeMillis());

				/**
				 * [tRunJob_16 end ] stop
				 */
			} // end the resume

			if (resumeEntryMethodName == null || globalResumeTicket) {
				resumeUtil.addLog("CHECKPOINT", "CONNECTION:SUBJOB_OK:tRunJob_16:OnSubjobOk", "",
						Thread.currentThread().getId() + "", "", "", "", "", "");
			}

			if (execStat) {
				runStat.updateStatOnConnection("OnSubjobOk32", 0, "ok");
			}

			tRunJob_17Process(globalMap);

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tRunJob_16 finally ] start
				 */

				currentComponent = "tRunJob_16";

				/**
				 * [tRunJob_16 finally ] stop
				 */
			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tRunJob_16_SUBPROCESS_STATE", 1);
	}

	public void tRunJob_17Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tRunJob_17_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [tRunJob_17 begin ] start
				 */

				ok_Hash.put("tRunJob_17", false);
				start_Hash.put("tRunJob_17", System.currentTimeMillis());

				currentComponent = "tRunJob_17";

				int tos_count_tRunJob_17 = 0;

				/**
				 * [tRunJob_17 begin ] stop
				 */

				/**
				 * [tRunJob_17 main ] start
				 */

				currentComponent = "tRunJob_17";

				java.util.List<String> paraList_tRunJob_17 = new java.util.ArrayList<String>();

				paraList_tRunJob_17.add("--father_pid=" + pid);

				paraList_tRunJob_17.add("--root_pid=" + rootPid);

				paraList_tRunJob_17.add("--father_node=tRunJob_17");

				paraList_tRunJob_17.add("--context=Default");

				if (enableLogStash) {
					paraList_tRunJob_17.add("--audit.enabled=" + enableLogStash);
				}

				// for feature:10589

				paraList_tRunJob_17.add("--stat_port=" + portStats);

				if (resuming_logs_dir_path != null) {
					paraList_tRunJob_17.add("--resuming_logs_dir_path=" + resuming_logs_dir_path);
				}
				String childResumePath_tRunJob_17 = ResumeUtil.getChildJobCheckPointPath(resuming_checkpoint_path);
				String tRunJobName_tRunJob_17 = ResumeUtil.getRighttRunJob(resuming_checkpoint_path);
				if ("tRunJob_17".equals(tRunJobName_tRunJob_17) && childResumePath_tRunJob_17 != null) {
					paraList_tRunJob_17.add("--resuming_checkpoint_path="
							+ ResumeUtil.getChildJobCheckPointPath(resuming_checkpoint_path));
				}
				paraList_tRunJob_17.add("--parent_part_launcher=JOB:" + jobName + "/NODE:tRunJob_17");

				java.util.Map<String, Object> parentContextMap_tRunJob_17 = new java.util.HashMap<String, Object>();

				context.synchronizeContext();
				class ContextProcessor_tRunJob_17 {
					private void transmitContext_0() {
						parentContextMap_tRunJob_17.put("folderName", context.folderName);
						paraList_tRunJob_17.add("--context_type " + "folderName" + "=" + "id_String");
					}

					public void transmitAllContext() {
						transmitContext_0();
					}
				}
				new ContextProcessor_tRunJob_17().transmitAllContext();
				java.util.Enumeration<?> propertyNames_tRunJob_17 = context.propertyNames();
				while (propertyNames_tRunJob_17.hasMoreElements()) {
					String key_tRunJob_17 = (String) propertyNames_tRunJob_17.nextElement();
					Object value_tRunJob_17 = (Object) context.get(key_tRunJob_17);
					if (value_tRunJob_17 != null) {
						paraList_tRunJob_17.add("--context_param " + key_tRunJob_17 + "=" + value_tRunJob_17);
					} else {
						paraList_tRunJob_17.add("--context_param " + key_tRunJob_17 + "="
								+ NULL_VALUE_EXPRESSION_IN_COMMAND_STRING_FOR_CHILD_JOB_ONLY);
					}

				}

				Object obj_tRunJob_17 = null;

				projetsid.dumpemployee_0_1.dumpEmployee childJob_tRunJob_17 = new projetsid.dumpemployee_0_1.dumpEmployee();
				// pass DataSources
				java.util.Map<String, routines.system.TalendDataSource> talendDataSources_tRunJob_17 = (java.util.Map<String, routines.system.TalendDataSource>) globalMap
						.get(KEY_DB_DATASOURCES);
				if (null != talendDataSources_tRunJob_17) {
					java.util.Map<String, javax.sql.DataSource> dataSources_tRunJob_17 = new java.util.HashMap<String, javax.sql.DataSource>();
					for (java.util.Map.Entry<String, routines.system.TalendDataSource> talendDataSourceEntry_tRunJob_17 : talendDataSources_tRunJob_17
							.entrySet()) {
						dataSources_tRunJob_17.put(talendDataSourceEntry_tRunJob_17.getKey(),
								talendDataSourceEntry_tRunJob_17.getValue().getRawDataSource());
					}
					childJob_tRunJob_17.setDataSources(dataSources_tRunJob_17);
				}

				childJob_tRunJob_17.parentContextMap = parentContextMap_tRunJob_17;

				String[][] childReturn_tRunJob_17 = childJob_tRunJob_17
						.runJob((String[]) paraList_tRunJob_17.toArray(new String[paraList_tRunJob_17.size()]));

				if (childJob_tRunJob_17.getErrorCode() == null) {
					globalMap.put("tRunJob_17_CHILD_RETURN_CODE", childJob_tRunJob_17.getStatus() != null
							&& ("failure").equals(childJob_tRunJob_17.getStatus()) ? 1 : 0);
				} else {
					globalMap.put("tRunJob_17_CHILD_RETURN_CODE", childJob_tRunJob_17.getErrorCode());
				}
				if (childJob_tRunJob_17.getExceptionStackTrace() != null) {
					globalMap.put("tRunJob_17_CHILD_EXCEPTION_STACKTRACE",
							childJob_tRunJob_17.getExceptionStackTrace());
				}
				errorCode = childJob_tRunJob_17.getErrorCode();
				if (childJob_tRunJob_17.getErrorCode() != null || ("failure").equals(childJob_tRunJob_17.getStatus())) {
					java.lang.Exception ce_tRunJob_17 = childJob_tRunJob_17.getException();
					throw new RuntimeException("Child job running failed.\n" + ((ce_tRunJob_17 != null)
							? (ce_tRunJob_17.getClass().getName() + ": " + ce_tRunJob_17.getMessage())
							: ""));
				}

				tos_count_tRunJob_17++;

				/**
				 * [tRunJob_17 main ] stop
				 */

				/**
				 * [tRunJob_17 process_data_begin ] start
				 */

				currentComponent = "tRunJob_17";

				/**
				 * [tRunJob_17 process_data_begin ] stop
				 */

				/**
				 * [tRunJob_17 process_data_end ] start
				 */

				currentComponent = "tRunJob_17";

				/**
				 * [tRunJob_17 process_data_end ] stop
				 */

				/**
				 * [tRunJob_17 end ] start
				 */

				currentComponent = "tRunJob_17";

				ok_Hash.put("tRunJob_17", true);
				end_Hash.put("tRunJob_17", System.currentTimeMillis());

				/**
				 * [tRunJob_17 end ] stop
				 */
			} // end the resume

			if (resumeEntryMethodName == null || globalResumeTicket) {
				resumeUtil.addLog("CHECKPOINT", "CONNECTION:SUBJOB_OK:tRunJob_17:OnSubjobOk", "",
						Thread.currentThread().getId() + "", "", "", "", "", "");
			}

			if (execStat) {
				runStat.updateStatOnConnection("OnSubjobOk33", 0, "ok");
			}

			tChronometerStop_10Process(globalMap);

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tRunJob_17 finally ] start
				 */

				currentComponent = "tRunJob_17";

				/**
				 * [tRunJob_17 finally ] stop
				 */
			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tRunJob_17_SUBPROCESS_STATE", 1);
	}

	public void tChronometerStop_10Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tChronometerStop_10_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [tChronometerStop_10 begin ] start
				 */

				ok_Hash.put("tChronometerStop_10", false);
				start_Hash.put("tChronometerStop_10", System.currentTimeMillis());

				currentComponent = "tChronometerStop_10";

				int tos_count_tChronometerStop_10 = 0;

				long timetChronometerStop_10;

				timetChronometerStop_10 = System.currentTimeMillis()
						- ((Long) globalMap.get("tChronometerStart_9")).longValue();

				System.out.print("[ tChronometerStop_10 ]  ");

				System.out.print("   " + timetChronometerStop_10 / 1000 + "seconds   ");

				System.out.println("Employee" + "  " + timetChronometerStop_10 + " milliseconds");

				Long currentTimetChronometerStop_10 = System.currentTimeMillis();
				globalMap.put("tChronometerStop_10", currentTimetChronometerStop_10);

				globalMap.put("tChronometerStop_10_STOPTIME", currentTimetChronometerStop_10);
				globalMap.put("tChronometerStop_10_DURATION", timetChronometerStop_10);

				/**
				 * [tChronometerStop_10 begin ] stop
				 */

				/**
				 * [tChronometerStop_10 main ] start
				 */

				currentComponent = "tChronometerStop_10";

				tos_count_tChronometerStop_10++;

				/**
				 * [tChronometerStop_10 main ] stop
				 */

				/**
				 * [tChronometerStop_10 process_data_begin ] start
				 */

				currentComponent = "tChronometerStop_10";

				/**
				 * [tChronometerStop_10 process_data_begin ] stop
				 */

				/**
				 * [tChronometerStop_10 process_data_end ] start
				 */

				currentComponent = "tChronometerStop_10";

				/**
				 * [tChronometerStop_10 process_data_end ] stop
				 */

				/**
				 * [tChronometerStop_10 end ] start
				 */

				currentComponent = "tChronometerStop_10";

				ok_Hash.put("tChronometerStop_10", true);
				end_Hash.put("tChronometerStop_10", System.currentTimeMillis());

				/**
				 * [tChronometerStop_10 end ] stop
				 */
			} // end the resume

			if (resumeEntryMethodName == null || globalResumeTicket) {
				resumeUtil.addLog("CHECKPOINT", "CONNECTION:SUBJOB_OK:tChronometerStop_10:OnSubjobOk", "",
						Thread.currentThread().getId() + "", "", "", "", "", "");
			}

			if (execStat) {
				runStat.updateStatOnConnection("OnSubjobOk50", 0, "ok");
			}

			tChronometerStart_10Process(globalMap);

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tChronometerStop_10 finally ] start
				 */

				currentComponent = "tChronometerStop_10";

				/**
				 * [tChronometerStop_10 finally ] stop
				 */
			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tChronometerStop_10_SUBPROCESS_STATE", 1);
	}

	public void tChronometerStart_10Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tChronometerStart_10_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [tChronometerStart_10 begin ] start
				 */

				ok_Hash.put("tChronometerStart_10", false);
				start_Hash.put("tChronometerStart_10", System.currentTimeMillis());

				currentComponent = "tChronometerStart_10";

				int tos_count_tChronometerStart_10 = 0;

				Long currentTimetChronometerStart_10 = System.currentTimeMillis();

				globalMap.put("tChronometerStart_10", currentTimetChronometerStart_10);
				globalMap.put("tChronometerStart_10_STARTTIME", currentTimetChronometerStart_10);

				/**
				 * [tChronometerStart_10 begin ] stop
				 */

				/**
				 * [tChronometerStart_10 main ] start
				 */

				currentComponent = "tChronometerStart_10";

				tos_count_tChronometerStart_10++;

				/**
				 * [tChronometerStart_10 main ] stop
				 */

				/**
				 * [tChronometerStart_10 process_data_begin ] start
				 */

				currentComponent = "tChronometerStart_10";

				/**
				 * [tChronometerStart_10 process_data_begin ] stop
				 */

				/**
				 * [tChronometerStart_10 process_data_end ] start
				 */

				currentComponent = "tChronometerStart_10";

				/**
				 * [tChronometerStart_10 process_data_end ] stop
				 */

				/**
				 * [tChronometerStart_10 end ] start
				 */

				currentComponent = "tChronometerStart_10";

				ok_Hash.put("tChronometerStart_10", true);
				end_Hash.put("tChronometerStart_10", System.currentTimeMillis());

				/**
				 * [tChronometerStart_10 end ] stop
				 */
			} // end the resume

			if (resumeEntryMethodName == null || globalResumeTicket) {
				resumeUtil.addLog("CHECKPOINT", "CONNECTION:SUBJOB_OK:tChronometerStart_10:OnSubjobOk", "",
						Thread.currentThread().getId() + "", "", "", "", "", "");
			}

			if (execStat) {
				runStat.updateStatOnConnection("OnSubjobOk34", 0, "ok");
			}

			tRunJob_18Process(globalMap);

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tChronometerStart_10 finally ] start
				 */

				currentComponent = "tChronometerStart_10";

				/**
				 * [tChronometerStart_10 finally ] stop
				 */
			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tChronometerStart_10_SUBPROCESS_STATE", 1);
	}

	public void tRunJob_18Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tRunJob_18_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [tRunJob_18 begin ] start
				 */

				ok_Hash.put("tRunJob_18", false);
				start_Hash.put("tRunJob_18", System.currentTimeMillis());

				currentComponent = "tRunJob_18";

				int tos_count_tRunJob_18 = 0;

				/**
				 * [tRunJob_18 begin ] stop
				 */

				/**
				 * [tRunJob_18 main ] start
				 */

				currentComponent = "tRunJob_18";

				java.util.List<String> paraList_tRunJob_18 = new java.util.ArrayList<String>();

				paraList_tRunJob_18.add("--father_pid=" + pid);

				paraList_tRunJob_18.add("--root_pid=" + rootPid);

				paraList_tRunJob_18.add("--father_node=tRunJob_18");

				paraList_tRunJob_18.add("--context=Default");

				if (enableLogStash) {
					paraList_tRunJob_18.add("--audit.enabled=" + enableLogStash);
				}

				// for feature:10589

				paraList_tRunJob_18.add("--stat_port=" + portStats);

				if (resuming_logs_dir_path != null) {
					paraList_tRunJob_18.add("--resuming_logs_dir_path=" + resuming_logs_dir_path);
				}
				String childResumePath_tRunJob_18 = ResumeUtil.getChildJobCheckPointPath(resuming_checkpoint_path);
				String tRunJobName_tRunJob_18 = ResumeUtil.getRighttRunJob(resuming_checkpoint_path);
				if ("tRunJob_18".equals(tRunJobName_tRunJob_18) && childResumePath_tRunJob_18 != null) {
					paraList_tRunJob_18.add("--resuming_checkpoint_path="
							+ ResumeUtil.getChildJobCheckPointPath(resuming_checkpoint_path));
				}
				paraList_tRunJob_18.add("--parent_part_launcher=JOB:" + jobName + "/NODE:tRunJob_18");

				java.util.Map<String, Object> parentContextMap_tRunJob_18 = new java.util.HashMap<String, Object>();

				context.synchronizeContext();
				class ContextProcessor_tRunJob_18 {
					private void transmitContext_0() {
						parentContextMap_tRunJob_18.put("folderName", context.folderName);
						paraList_tRunJob_18.add("--context_type " + "folderName" + "=" + "id_String");
					}

					public void transmitAllContext() {
						transmitContext_0();
					}
				}
				new ContextProcessor_tRunJob_18().transmitAllContext();
				java.util.Enumeration<?> propertyNames_tRunJob_18 = context.propertyNames();
				while (propertyNames_tRunJob_18.hasMoreElements()) {
					String key_tRunJob_18 = (String) propertyNames_tRunJob_18.nextElement();
					Object value_tRunJob_18 = (Object) context.get(key_tRunJob_18);
					if (value_tRunJob_18 != null) {
						paraList_tRunJob_18.add("--context_param " + key_tRunJob_18 + "=" + value_tRunJob_18);
					} else {
						paraList_tRunJob_18.add("--context_param " + key_tRunJob_18 + "="
								+ NULL_VALUE_EXPRESSION_IN_COMMAND_STRING_FOR_CHILD_JOB_ONLY);
					}

				}

				Object obj_tRunJob_18 = null;

				projetsid.customer_0_1.customer childJob_tRunJob_18 = new projetsid.customer_0_1.customer();
				// pass DataSources
				java.util.Map<String, routines.system.TalendDataSource> talendDataSources_tRunJob_18 = (java.util.Map<String, routines.system.TalendDataSource>) globalMap
						.get(KEY_DB_DATASOURCES);
				if (null != talendDataSources_tRunJob_18) {
					java.util.Map<String, javax.sql.DataSource> dataSources_tRunJob_18 = new java.util.HashMap<String, javax.sql.DataSource>();
					for (java.util.Map.Entry<String, routines.system.TalendDataSource> talendDataSourceEntry_tRunJob_18 : talendDataSources_tRunJob_18
							.entrySet()) {
						dataSources_tRunJob_18.put(talendDataSourceEntry_tRunJob_18.getKey(),
								talendDataSourceEntry_tRunJob_18.getValue().getRawDataSource());
					}
					childJob_tRunJob_18.setDataSources(dataSources_tRunJob_18);
				}

				childJob_tRunJob_18.parentContextMap = parentContextMap_tRunJob_18;

				String[][] childReturn_tRunJob_18 = childJob_tRunJob_18
						.runJob((String[]) paraList_tRunJob_18.toArray(new String[paraList_tRunJob_18.size()]));

				if (childJob_tRunJob_18.getErrorCode() == null) {
					globalMap.put("tRunJob_18_CHILD_RETURN_CODE", childJob_tRunJob_18.getStatus() != null
							&& ("failure").equals(childJob_tRunJob_18.getStatus()) ? 1 : 0);
				} else {
					globalMap.put("tRunJob_18_CHILD_RETURN_CODE", childJob_tRunJob_18.getErrorCode());
				}
				if (childJob_tRunJob_18.getExceptionStackTrace() != null) {
					globalMap.put("tRunJob_18_CHILD_EXCEPTION_STACKTRACE",
							childJob_tRunJob_18.getExceptionStackTrace());
				}
				errorCode = childJob_tRunJob_18.getErrorCode();
				if (childJob_tRunJob_18.getErrorCode() != null || ("failure").equals(childJob_tRunJob_18.getStatus())) {
					java.lang.Exception ce_tRunJob_18 = childJob_tRunJob_18.getException();
					throw new RuntimeException("Child job running failed.\n" + ((ce_tRunJob_18 != null)
							? (ce_tRunJob_18.getClass().getName() + ": " + ce_tRunJob_18.getMessage())
							: ""));
				}

				tos_count_tRunJob_18++;

				/**
				 * [tRunJob_18 main ] stop
				 */

				/**
				 * [tRunJob_18 process_data_begin ] start
				 */

				currentComponent = "tRunJob_18";

				/**
				 * [tRunJob_18 process_data_begin ] stop
				 */

				/**
				 * [tRunJob_18 process_data_end ] start
				 */

				currentComponent = "tRunJob_18";

				/**
				 * [tRunJob_18 process_data_end ] stop
				 */

				/**
				 * [tRunJob_18 end ] start
				 */

				currentComponent = "tRunJob_18";

				ok_Hash.put("tRunJob_18", true);
				end_Hash.put("tRunJob_18", System.currentTimeMillis());

				/**
				 * [tRunJob_18 end ] stop
				 */
			} // end the resume

			if (resumeEntryMethodName == null || globalResumeTicket) {
				resumeUtil.addLog("CHECKPOINT", "CONNECTION:SUBJOB_OK:tRunJob_18:OnSubjobOk", "",
						Thread.currentThread().getId() + "", "", "", "", "", "");
			}

			if (execStat) {
				runStat.updateStatOnConnection("OnSubjobOk35", 0, "ok");
			}

			tRunJob_19Process(globalMap);

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tRunJob_18 finally ] start
				 */

				currentComponent = "tRunJob_18";

				/**
				 * [tRunJob_18 finally ] stop
				 */
			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tRunJob_18_SUBPROCESS_STATE", 1);
	}

	public void tRunJob_19Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tRunJob_19_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [tRunJob_19 begin ] start
				 */

				ok_Hash.put("tRunJob_19", false);
				start_Hash.put("tRunJob_19", System.currentTimeMillis());

				currentComponent = "tRunJob_19";

				int tos_count_tRunJob_19 = 0;

				/**
				 * [tRunJob_19 begin ] stop
				 */

				/**
				 * [tRunJob_19 main ] start
				 */

				currentComponent = "tRunJob_19";

				java.util.List<String> paraList_tRunJob_19 = new java.util.ArrayList<String>();

				paraList_tRunJob_19.add("--father_pid=" + pid);

				paraList_tRunJob_19.add("--root_pid=" + rootPid);

				paraList_tRunJob_19.add("--father_node=tRunJob_19");

				paraList_tRunJob_19.add("--context=Default");

				if (enableLogStash) {
					paraList_tRunJob_19.add("--audit.enabled=" + enableLogStash);
				}

				// for feature:10589

				paraList_tRunJob_19.add("--stat_port=" + portStats);

				if (resuming_logs_dir_path != null) {
					paraList_tRunJob_19.add("--resuming_logs_dir_path=" + resuming_logs_dir_path);
				}
				String childResumePath_tRunJob_19 = ResumeUtil.getChildJobCheckPointPath(resuming_checkpoint_path);
				String tRunJobName_tRunJob_19 = ResumeUtil.getRighttRunJob(resuming_checkpoint_path);
				if ("tRunJob_19".equals(tRunJobName_tRunJob_19) && childResumePath_tRunJob_19 != null) {
					paraList_tRunJob_19.add("--resuming_checkpoint_path="
							+ ResumeUtil.getChildJobCheckPointPath(resuming_checkpoint_path));
				}
				paraList_tRunJob_19.add("--parent_part_launcher=JOB:" + jobName + "/NODE:tRunJob_19");

				java.util.Map<String, Object> parentContextMap_tRunJob_19 = new java.util.HashMap<String, Object>();

				context.synchronizeContext();
				class ContextProcessor_tRunJob_19 {
					private void transmitContext_0() {
						parentContextMap_tRunJob_19.put("folderName", context.folderName);
						paraList_tRunJob_19.add("--context_type " + "folderName" + "=" + "id_String");
					}

					public void transmitAllContext() {
						transmitContext_0();
					}
				}
				new ContextProcessor_tRunJob_19().transmitAllContext();
				java.util.Enumeration<?> propertyNames_tRunJob_19 = context.propertyNames();
				while (propertyNames_tRunJob_19.hasMoreElements()) {
					String key_tRunJob_19 = (String) propertyNames_tRunJob_19.nextElement();
					Object value_tRunJob_19 = (Object) context.get(key_tRunJob_19);
					if (value_tRunJob_19 != null) {
						paraList_tRunJob_19.add("--context_param " + key_tRunJob_19 + "=" + value_tRunJob_19);
					} else {
						paraList_tRunJob_19.add("--context_param " + key_tRunJob_19 + "="
								+ NULL_VALUE_EXPRESSION_IN_COMMAND_STRING_FOR_CHILD_JOB_ONLY);
					}

				}

				Object obj_tRunJob_19 = null;

				projetsid.dumpcustomer_0_2.dumpCustomer childJob_tRunJob_19 = new projetsid.dumpcustomer_0_2.dumpCustomer();
				// pass DataSources
				java.util.Map<String, routines.system.TalendDataSource> talendDataSources_tRunJob_19 = (java.util.Map<String, routines.system.TalendDataSource>) globalMap
						.get(KEY_DB_DATASOURCES);
				if (null != talendDataSources_tRunJob_19) {
					java.util.Map<String, javax.sql.DataSource> dataSources_tRunJob_19 = new java.util.HashMap<String, javax.sql.DataSource>();
					for (java.util.Map.Entry<String, routines.system.TalendDataSource> talendDataSourceEntry_tRunJob_19 : talendDataSources_tRunJob_19
							.entrySet()) {
						dataSources_tRunJob_19.put(talendDataSourceEntry_tRunJob_19.getKey(),
								talendDataSourceEntry_tRunJob_19.getValue().getRawDataSource());
					}
					childJob_tRunJob_19.setDataSources(dataSources_tRunJob_19);
				}

				childJob_tRunJob_19.parentContextMap = parentContextMap_tRunJob_19;

				String[][] childReturn_tRunJob_19 = childJob_tRunJob_19
						.runJob((String[]) paraList_tRunJob_19.toArray(new String[paraList_tRunJob_19.size()]));

				if (childJob_tRunJob_19.getErrorCode() == null) {
					globalMap.put("tRunJob_19_CHILD_RETURN_CODE", childJob_tRunJob_19.getStatus() != null
							&& ("failure").equals(childJob_tRunJob_19.getStatus()) ? 1 : 0);
				} else {
					globalMap.put("tRunJob_19_CHILD_RETURN_CODE", childJob_tRunJob_19.getErrorCode());
				}
				if (childJob_tRunJob_19.getExceptionStackTrace() != null) {
					globalMap.put("tRunJob_19_CHILD_EXCEPTION_STACKTRACE",
							childJob_tRunJob_19.getExceptionStackTrace());
				}
				errorCode = childJob_tRunJob_19.getErrorCode();
				if (childJob_tRunJob_19.getErrorCode() != null || ("failure").equals(childJob_tRunJob_19.getStatus())) {
					java.lang.Exception ce_tRunJob_19 = childJob_tRunJob_19.getException();
					throw new RuntimeException("Child job running failed.\n" + ((ce_tRunJob_19 != null)
							? (ce_tRunJob_19.getClass().getName() + ": " + ce_tRunJob_19.getMessage())
							: ""));
				}

				tos_count_tRunJob_19++;

				/**
				 * [tRunJob_19 main ] stop
				 */

				/**
				 * [tRunJob_19 process_data_begin ] start
				 */

				currentComponent = "tRunJob_19";

				/**
				 * [tRunJob_19 process_data_begin ] stop
				 */

				/**
				 * [tRunJob_19 process_data_end ] start
				 */

				currentComponent = "tRunJob_19";

				/**
				 * [tRunJob_19 process_data_end ] stop
				 */

				/**
				 * [tRunJob_19 end ] start
				 */

				currentComponent = "tRunJob_19";

				ok_Hash.put("tRunJob_19", true);
				end_Hash.put("tRunJob_19", System.currentTimeMillis());

				/**
				 * [tRunJob_19 end ] stop
				 */
			} // end the resume

			if (resumeEntryMethodName == null || globalResumeTicket) {
				resumeUtil.addLog("CHECKPOINT", "CONNECTION:SUBJOB_OK:tRunJob_19:OnSubjobOk", "",
						Thread.currentThread().getId() + "", "", "", "", "", "");
			}

			if (execStat) {
				runStat.updateStatOnConnection("OnSubjobOk36", 0, "ok");
			}

			tChronometerStop_11Process(globalMap);

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tRunJob_19 finally ] start
				 */

				currentComponent = "tRunJob_19";

				/**
				 * [tRunJob_19 finally ] stop
				 */
			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tRunJob_19_SUBPROCESS_STATE", 1);
	}

	public void tChronometerStop_11Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tChronometerStop_11_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [tChronometerStop_11 begin ] start
				 */

				ok_Hash.put("tChronometerStop_11", false);
				start_Hash.put("tChronometerStop_11", System.currentTimeMillis());

				currentComponent = "tChronometerStop_11";

				int tos_count_tChronometerStop_11 = 0;

				long timetChronometerStop_11;

				timetChronometerStop_11 = System.currentTimeMillis()
						- ((Long) globalMap.get("tChronometerStart_10")).longValue();

				System.out.print("[ tChronometerStop_11 ]  ");

				System.out.print("   " + timetChronometerStop_11 / 1000 + "seconds   ");

				System.out.println("Customer" + "  " + timetChronometerStop_11 + " milliseconds");

				Long currentTimetChronometerStop_11 = System.currentTimeMillis();
				globalMap.put("tChronometerStop_11", currentTimetChronometerStop_11);

				globalMap.put("tChronometerStop_11_STOPTIME", currentTimetChronometerStop_11);
				globalMap.put("tChronometerStop_11_DURATION", timetChronometerStop_11);

				/**
				 * [tChronometerStop_11 begin ] stop
				 */

				/**
				 * [tChronometerStop_11 main ] start
				 */

				currentComponent = "tChronometerStop_11";

				tos_count_tChronometerStop_11++;

				/**
				 * [tChronometerStop_11 main ] stop
				 */

				/**
				 * [tChronometerStop_11 process_data_begin ] start
				 */

				currentComponent = "tChronometerStop_11";

				/**
				 * [tChronometerStop_11 process_data_begin ] stop
				 */

				/**
				 * [tChronometerStop_11 process_data_end ] start
				 */

				currentComponent = "tChronometerStop_11";

				/**
				 * [tChronometerStop_11 process_data_end ] stop
				 */

				/**
				 * [tChronometerStop_11 end ] start
				 */

				currentComponent = "tChronometerStop_11";

				ok_Hash.put("tChronometerStop_11", true);
				end_Hash.put("tChronometerStop_11", System.currentTimeMillis());

				/**
				 * [tChronometerStop_11 end ] stop
				 */
			} // end the resume

			if (resumeEntryMethodName == null || globalResumeTicket) {
				resumeUtil.addLog("CHECKPOINT", "CONNECTION:SUBJOB_OK:tChronometerStop_11:OnSubjobOk", "",
						Thread.currentThread().getId() + "", "", "", "", "", "");
			}

			if (execStat) {
				runStat.updateStatOnConnection("OnSubjobOk51", 0, "ok");
			}

			tChronometerStart_11Process(globalMap);

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tChronometerStop_11 finally ] start
				 */

				currentComponent = "tChronometerStop_11";

				/**
				 * [tChronometerStop_11 finally ] stop
				 */
			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tChronometerStop_11_SUBPROCESS_STATE", 1);
	}

	public void tChronometerStart_11Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tChronometerStart_11_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [tChronometerStart_11 begin ] start
				 */

				ok_Hash.put("tChronometerStart_11", false);
				start_Hash.put("tChronometerStart_11", System.currentTimeMillis());

				currentComponent = "tChronometerStart_11";

				int tos_count_tChronometerStart_11 = 0;

				Long currentTimetChronometerStart_11 = System.currentTimeMillis();

				globalMap.put("tChronometerStart_11", currentTimetChronometerStart_11);
				globalMap.put("tChronometerStart_11_STARTTIME", currentTimetChronometerStart_11);

				/**
				 * [tChronometerStart_11 begin ] stop
				 */

				/**
				 * [tChronometerStart_11 main ] start
				 */

				currentComponent = "tChronometerStart_11";

				tos_count_tChronometerStart_11++;

				/**
				 * [tChronometerStart_11 main ] stop
				 */

				/**
				 * [tChronometerStart_11 process_data_begin ] start
				 */

				currentComponent = "tChronometerStart_11";

				/**
				 * [tChronometerStart_11 process_data_begin ] stop
				 */

				/**
				 * [tChronometerStart_11 process_data_end ] start
				 */

				currentComponent = "tChronometerStart_11";

				/**
				 * [tChronometerStart_11 process_data_end ] stop
				 */

				/**
				 * [tChronometerStart_11 end ] start
				 */

				currentComponent = "tChronometerStart_11";

				ok_Hash.put("tChronometerStart_11", true);
				end_Hash.put("tChronometerStart_11", System.currentTimeMillis());

				/**
				 * [tChronometerStart_11 end ] stop
				 */
			} // end the resume

			if (resumeEntryMethodName == null || globalResumeTicket) {
				resumeUtil.addLog("CHECKPOINT", "CONNECTION:SUBJOB_OK:tChronometerStart_11:OnSubjobOk", "",
						Thread.currentThread().getId() + "", "", "", "", "", "");
			}

			if (execStat) {
				runStat.updateStatOnConnection("OnSubjobOk37", 0, "ok");
			}

			tRunJob_20Process(globalMap);

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tChronometerStart_11 finally ] start
				 */

				currentComponent = "tChronometerStart_11";

				/**
				 * [tChronometerStart_11 finally ] stop
				 */
			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tChronometerStart_11_SUBPROCESS_STATE", 1);
	}

	public void tRunJob_20Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tRunJob_20_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [tRunJob_20 begin ] start
				 */

				ok_Hash.put("tRunJob_20", false);
				start_Hash.put("tRunJob_20", System.currentTimeMillis());

				currentComponent = "tRunJob_20";

				int tos_count_tRunJob_20 = 0;

				/**
				 * [tRunJob_20 begin ] stop
				 */

				/**
				 * [tRunJob_20 main ] start
				 */

				currentComponent = "tRunJob_20";

				java.util.List<String> paraList_tRunJob_20 = new java.util.ArrayList<String>();

				paraList_tRunJob_20.add("--father_pid=" + pid);

				paraList_tRunJob_20.add("--root_pid=" + rootPid);

				paraList_tRunJob_20.add("--father_node=tRunJob_20");

				paraList_tRunJob_20.add("--context=Default");

				if (enableLogStash) {
					paraList_tRunJob_20.add("--audit.enabled=" + enableLogStash);
				}

				// for feature:10589

				paraList_tRunJob_20.add("--stat_port=" + portStats);

				if (resuming_logs_dir_path != null) {
					paraList_tRunJob_20.add("--resuming_logs_dir_path=" + resuming_logs_dir_path);
				}
				String childResumePath_tRunJob_20 = ResumeUtil.getChildJobCheckPointPath(resuming_checkpoint_path);
				String tRunJobName_tRunJob_20 = ResumeUtil.getRighttRunJob(resuming_checkpoint_path);
				if ("tRunJob_20".equals(tRunJobName_tRunJob_20) && childResumePath_tRunJob_20 != null) {
					paraList_tRunJob_20.add("--resuming_checkpoint_path="
							+ ResumeUtil.getChildJobCheckPointPath(resuming_checkpoint_path));
				}
				paraList_tRunJob_20.add("--parent_part_launcher=JOB:" + jobName + "/NODE:tRunJob_20");

				java.util.Map<String, Object> parentContextMap_tRunJob_20 = new java.util.HashMap<String, Object>();

				context.synchronizeContext();
				class ContextProcessor_tRunJob_20 {
					private void transmitContext_0() {
						parentContextMap_tRunJob_20.put("folderName", context.folderName);
						paraList_tRunJob_20.add("--context_type " + "folderName" + "=" + "id_String");
					}

					public void transmitAllContext() {
						transmitContext_0();
					}
				}
				new ContextProcessor_tRunJob_20().transmitAllContext();
				java.util.Enumeration<?> propertyNames_tRunJob_20 = context.propertyNames();
				while (propertyNames_tRunJob_20.hasMoreElements()) {
					String key_tRunJob_20 = (String) propertyNames_tRunJob_20.nextElement();
					Object value_tRunJob_20 = (Object) context.get(key_tRunJob_20);
					if (value_tRunJob_20 != null) {
						paraList_tRunJob_20.add("--context_param " + key_tRunJob_20 + "=" + value_tRunJob_20);
					} else {
						paraList_tRunJob_20.add("--context_param " + key_tRunJob_20 + "="
								+ NULL_VALUE_EXPRESSION_IN_COMMAND_STRING_FOR_CHILD_JOB_ONLY);
					}

				}

				Object obj_tRunJob_20 = null;

				projetsid.order_0_1.order childJob_tRunJob_20 = new projetsid.order_0_1.order();
				// pass DataSources
				java.util.Map<String, routines.system.TalendDataSource> talendDataSources_tRunJob_20 = (java.util.Map<String, routines.system.TalendDataSource>) globalMap
						.get(KEY_DB_DATASOURCES);
				if (null != talendDataSources_tRunJob_20) {
					java.util.Map<String, javax.sql.DataSource> dataSources_tRunJob_20 = new java.util.HashMap<String, javax.sql.DataSource>();
					for (java.util.Map.Entry<String, routines.system.TalendDataSource> talendDataSourceEntry_tRunJob_20 : talendDataSources_tRunJob_20
							.entrySet()) {
						dataSources_tRunJob_20.put(talendDataSourceEntry_tRunJob_20.getKey(),
								talendDataSourceEntry_tRunJob_20.getValue().getRawDataSource());
					}
					childJob_tRunJob_20.setDataSources(dataSources_tRunJob_20);
				}

				childJob_tRunJob_20.parentContextMap = parentContextMap_tRunJob_20;

				String[][] childReturn_tRunJob_20 = childJob_tRunJob_20
						.runJob((String[]) paraList_tRunJob_20.toArray(new String[paraList_tRunJob_20.size()]));

				if (childJob_tRunJob_20.getErrorCode() == null) {
					globalMap.put("tRunJob_20_CHILD_RETURN_CODE", childJob_tRunJob_20.getStatus() != null
							&& ("failure").equals(childJob_tRunJob_20.getStatus()) ? 1 : 0);
				} else {
					globalMap.put("tRunJob_20_CHILD_RETURN_CODE", childJob_tRunJob_20.getErrorCode());
				}
				if (childJob_tRunJob_20.getExceptionStackTrace() != null) {
					globalMap.put("tRunJob_20_CHILD_EXCEPTION_STACKTRACE",
							childJob_tRunJob_20.getExceptionStackTrace());
				}
				errorCode = childJob_tRunJob_20.getErrorCode();
				if (childJob_tRunJob_20.getErrorCode() != null || ("failure").equals(childJob_tRunJob_20.getStatus())) {
					java.lang.Exception ce_tRunJob_20 = childJob_tRunJob_20.getException();
					throw new RuntimeException("Child job running failed.\n" + ((ce_tRunJob_20 != null)
							? (ce_tRunJob_20.getClass().getName() + ": " + ce_tRunJob_20.getMessage())
							: ""));
				}

				tos_count_tRunJob_20++;

				/**
				 * [tRunJob_20 main ] stop
				 */

				/**
				 * [tRunJob_20 process_data_begin ] start
				 */

				currentComponent = "tRunJob_20";

				/**
				 * [tRunJob_20 process_data_begin ] stop
				 */

				/**
				 * [tRunJob_20 process_data_end ] start
				 */

				currentComponent = "tRunJob_20";

				/**
				 * [tRunJob_20 process_data_end ] stop
				 */

				/**
				 * [tRunJob_20 end ] start
				 */

				currentComponent = "tRunJob_20";

				ok_Hash.put("tRunJob_20", true);
				end_Hash.put("tRunJob_20", System.currentTimeMillis());

				/**
				 * [tRunJob_20 end ] stop
				 */
			} // end the resume

			if (resumeEntryMethodName == null || globalResumeTicket) {
				resumeUtil.addLog("CHECKPOINT", "CONNECTION:SUBJOB_OK:tRunJob_20:OnSubjobOk", "",
						Thread.currentThread().getId() + "", "", "", "", "", "");
			}

			if (execStat) {
				runStat.updateStatOnConnection("OnSubjobOk38", 0, "ok");
			}

			tRunJob_21Process(globalMap);

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tRunJob_20 finally ] start
				 */

				currentComponent = "tRunJob_20";

				/**
				 * [tRunJob_20 finally ] stop
				 */
			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tRunJob_20_SUBPROCESS_STATE", 1);
	}

	public void tRunJob_21Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tRunJob_21_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [tRunJob_21 begin ] start
				 */

				ok_Hash.put("tRunJob_21", false);
				start_Hash.put("tRunJob_21", System.currentTimeMillis());

				currentComponent = "tRunJob_21";

				int tos_count_tRunJob_21 = 0;

				/**
				 * [tRunJob_21 begin ] stop
				 */

				/**
				 * [tRunJob_21 main ] start
				 */

				currentComponent = "tRunJob_21";

				java.util.List<String> paraList_tRunJob_21 = new java.util.ArrayList<String>();

				paraList_tRunJob_21.add("--father_pid=" + pid);

				paraList_tRunJob_21.add("--root_pid=" + rootPid);

				paraList_tRunJob_21.add("--father_node=tRunJob_21");

				paraList_tRunJob_21.add("--context=Default");

				if (enableLogStash) {
					paraList_tRunJob_21.add("--audit.enabled=" + enableLogStash);
				}

				// for feature:10589

				paraList_tRunJob_21.add("--stat_port=" + portStats);

				if (resuming_logs_dir_path != null) {
					paraList_tRunJob_21.add("--resuming_logs_dir_path=" + resuming_logs_dir_path);
				}
				String childResumePath_tRunJob_21 = ResumeUtil.getChildJobCheckPointPath(resuming_checkpoint_path);
				String tRunJobName_tRunJob_21 = ResumeUtil.getRighttRunJob(resuming_checkpoint_path);
				if ("tRunJob_21".equals(tRunJobName_tRunJob_21) && childResumePath_tRunJob_21 != null) {
					paraList_tRunJob_21.add("--resuming_checkpoint_path="
							+ ResumeUtil.getChildJobCheckPointPath(resuming_checkpoint_path));
				}
				paraList_tRunJob_21.add("--parent_part_launcher=JOB:" + jobName + "/NODE:tRunJob_21");

				java.util.Map<String, Object> parentContextMap_tRunJob_21 = new java.util.HashMap<String, Object>();

				context.synchronizeContext();
				class ContextProcessor_tRunJob_21 {
					private void transmitContext_0() {
						parentContextMap_tRunJob_21.put("folderName", context.folderName);
						paraList_tRunJob_21.add("--context_type " + "folderName" + "=" + "id_String");
					}

					public void transmitAllContext() {
						transmitContext_0();
					}
				}
				new ContextProcessor_tRunJob_21().transmitAllContext();
				java.util.Enumeration<?> propertyNames_tRunJob_21 = context.propertyNames();
				while (propertyNames_tRunJob_21.hasMoreElements()) {
					String key_tRunJob_21 = (String) propertyNames_tRunJob_21.nextElement();
					Object value_tRunJob_21 = (Object) context.get(key_tRunJob_21);
					if (value_tRunJob_21 != null) {
						paraList_tRunJob_21.add("--context_param " + key_tRunJob_21 + "=" + value_tRunJob_21);
					} else {
						paraList_tRunJob_21.add("--context_param " + key_tRunJob_21 + "="
								+ NULL_VALUE_EXPRESSION_IN_COMMAND_STRING_FOR_CHILD_JOB_ONLY);
					}

				}

				Object obj_tRunJob_21 = null;

				projetsid.dumporder_0_2.dumpOrder childJob_tRunJob_21 = new projetsid.dumporder_0_2.dumpOrder();
				// pass DataSources
				java.util.Map<String, routines.system.TalendDataSource> talendDataSources_tRunJob_21 = (java.util.Map<String, routines.system.TalendDataSource>) globalMap
						.get(KEY_DB_DATASOURCES);
				if (null != talendDataSources_tRunJob_21) {
					java.util.Map<String, javax.sql.DataSource> dataSources_tRunJob_21 = new java.util.HashMap<String, javax.sql.DataSource>();
					for (java.util.Map.Entry<String, routines.system.TalendDataSource> talendDataSourceEntry_tRunJob_21 : talendDataSources_tRunJob_21
							.entrySet()) {
						dataSources_tRunJob_21.put(talendDataSourceEntry_tRunJob_21.getKey(),
								talendDataSourceEntry_tRunJob_21.getValue().getRawDataSource());
					}
					childJob_tRunJob_21.setDataSources(dataSources_tRunJob_21);
				}

				childJob_tRunJob_21.parentContextMap = parentContextMap_tRunJob_21;

				String[][] childReturn_tRunJob_21 = childJob_tRunJob_21
						.runJob((String[]) paraList_tRunJob_21.toArray(new String[paraList_tRunJob_21.size()]));

				if (childJob_tRunJob_21.getErrorCode() == null) {
					globalMap.put("tRunJob_21_CHILD_RETURN_CODE", childJob_tRunJob_21.getStatus() != null
							&& ("failure").equals(childJob_tRunJob_21.getStatus()) ? 1 : 0);
				} else {
					globalMap.put("tRunJob_21_CHILD_RETURN_CODE", childJob_tRunJob_21.getErrorCode());
				}
				if (childJob_tRunJob_21.getExceptionStackTrace() != null) {
					globalMap.put("tRunJob_21_CHILD_EXCEPTION_STACKTRACE",
							childJob_tRunJob_21.getExceptionStackTrace());
				}
				errorCode = childJob_tRunJob_21.getErrorCode();
				if (childJob_tRunJob_21.getErrorCode() != null || ("failure").equals(childJob_tRunJob_21.getStatus())) {
					java.lang.Exception ce_tRunJob_21 = childJob_tRunJob_21.getException();
					throw new RuntimeException("Child job running failed.\n" + ((ce_tRunJob_21 != null)
							? (ce_tRunJob_21.getClass().getName() + ": " + ce_tRunJob_21.getMessage())
							: ""));
				}

				tos_count_tRunJob_21++;

				/**
				 * [tRunJob_21 main ] stop
				 */

				/**
				 * [tRunJob_21 process_data_begin ] start
				 */

				currentComponent = "tRunJob_21";

				/**
				 * [tRunJob_21 process_data_begin ] stop
				 */

				/**
				 * [tRunJob_21 process_data_end ] start
				 */

				currentComponent = "tRunJob_21";

				/**
				 * [tRunJob_21 process_data_end ] stop
				 */

				/**
				 * [tRunJob_21 end ] start
				 */

				currentComponent = "tRunJob_21";

				ok_Hash.put("tRunJob_21", true);
				end_Hash.put("tRunJob_21", System.currentTimeMillis());

				/**
				 * [tRunJob_21 end ] stop
				 */
			} // end the resume

			if (resumeEntryMethodName == null || globalResumeTicket) {
				resumeUtil.addLog("CHECKPOINT", "CONNECTION:SUBJOB_OK:tRunJob_21:OnSubjobOk", "",
						Thread.currentThread().getId() + "", "", "", "", "", "");
			}

			if (execStat) {
				runStat.updateStatOnConnection("OnSubjobOk39", 0, "ok");
			}

			tChronometerStop_12Process(globalMap);

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tRunJob_21 finally ] start
				 */

				currentComponent = "tRunJob_21";

				/**
				 * [tRunJob_21 finally ] stop
				 */
			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tRunJob_21_SUBPROCESS_STATE", 1);
	}

	public void tChronometerStop_12Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tChronometerStop_12_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [tChronometerStop_12 begin ] start
				 */

				ok_Hash.put("tChronometerStop_12", false);
				start_Hash.put("tChronometerStop_12", System.currentTimeMillis());

				currentComponent = "tChronometerStop_12";

				int tos_count_tChronometerStop_12 = 0;

				long timetChronometerStop_12;

				timetChronometerStop_12 = System.currentTimeMillis()
						- ((Long) globalMap.get("tChronometerStart_11")).longValue();

				System.out.print("[ tChronometerStop_12 ]  ");

				System.out.print("   " + timetChronometerStop_12 / 1000 + "seconds   ");

				System.out.println("Order" + "  " + timetChronometerStop_12 + " milliseconds");

				Long currentTimetChronometerStop_12 = System.currentTimeMillis();
				globalMap.put("tChronometerStop_12", currentTimetChronometerStop_12);

				globalMap.put("tChronometerStop_12_STOPTIME", currentTimetChronometerStop_12);
				globalMap.put("tChronometerStop_12_DURATION", timetChronometerStop_12);

				/**
				 * [tChronometerStop_12 begin ] stop
				 */

				/**
				 * [tChronometerStop_12 main ] start
				 */

				currentComponent = "tChronometerStop_12";

				tos_count_tChronometerStop_12++;

				/**
				 * [tChronometerStop_12 main ] stop
				 */

				/**
				 * [tChronometerStop_12 process_data_begin ] start
				 */

				currentComponent = "tChronometerStop_12";

				/**
				 * [tChronometerStop_12 process_data_begin ] stop
				 */

				/**
				 * [tChronometerStop_12 process_data_end ] start
				 */

				currentComponent = "tChronometerStop_12";

				/**
				 * [tChronometerStop_12 process_data_end ] stop
				 */

				/**
				 * [tChronometerStop_12 end ] start
				 */

				currentComponent = "tChronometerStop_12";

				ok_Hash.put("tChronometerStop_12", true);
				end_Hash.put("tChronometerStop_12", System.currentTimeMillis());

				/**
				 * [tChronometerStop_12 end ] stop
				 */
			} // end the resume

			if (resumeEntryMethodName == null || globalResumeTicket) {
				resumeUtil.addLog("CHECKPOINT", "CONNECTION:SUBJOB_OK:tChronometerStop_12:OnSubjobOk", "",
						Thread.currentThread().getId() + "", "", "", "", "", "");
			}

			if (execStat) {
				runStat.updateStatOnConnection("OnSubjobOk52", 0, "ok");
			}

			tChronometerStart_12Process(globalMap);

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tChronometerStop_12 finally ] start
				 */

				currentComponent = "tChronometerStop_12";

				/**
				 * [tChronometerStop_12 finally ] stop
				 */
			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tChronometerStop_12_SUBPROCESS_STATE", 1);
	}

	public void tChronometerStart_12Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tChronometerStart_12_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [tChronometerStart_12 begin ] start
				 */

				ok_Hash.put("tChronometerStart_12", false);
				start_Hash.put("tChronometerStart_12", System.currentTimeMillis());

				currentComponent = "tChronometerStart_12";

				int tos_count_tChronometerStart_12 = 0;

				Long currentTimetChronometerStart_12 = System.currentTimeMillis();

				globalMap.put("tChronometerStart_12", currentTimetChronometerStart_12);
				globalMap.put("tChronometerStart_12_STARTTIME", currentTimetChronometerStart_12);

				/**
				 * [tChronometerStart_12 begin ] stop
				 */

				/**
				 * [tChronometerStart_12 main ] start
				 */

				currentComponent = "tChronometerStart_12";

				tos_count_tChronometerStart_12++;

				/**
				 * [tChronometerStart_12 main ] stop
				 */

				/**
				 * [tChronometerStart_12 process_data_begin ] start
				 */

				currentComponent = "tChronometerStart_12";

				/**
				 * [tChronometerStart_12 process_data_begin ] stop
				 */

				/**
				 * [tChronometerStart_12 process_data_end ] start
				 */

				currentComponent = "tChronometerStart_12";

				/**
				 * [tChronometerStart_12 process_data_end ] stop
				 */

				/**
				 * [tChronometerStart_12 end ] start
				 */

				currentComponent = "tChronometerStart_12";

				ok_Hash.put("tChronometerStart_12", true);
				end_Hash.put("tChronometerStart_12", System.currentTimeMillis());

				/**
				 * [tChronometerStart_12 end ] stop
				 */
			} // end the resume

			if (resumeEntryMethodName == null || globalResumeTicket) {
				resumeUtil.addLog("CHECKPOINT", "CONNECTION:SUBJOB_OK:tChronometerStart_12:OnSubjobOk", "",
						Thread.currentThread().getId() + "", "", "", "", "", "");
			}

			if (execStat) {
				runStat.updateStatOnConnection("OnSubjobOk40", 0, "ok");
			}

			tRunJob_22Process(globalMap);

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tChronometerStart_12 finally ] start
				 */

				currentComponent = "tChronometerStart_12";

				/**
				 * [tChronometerStart_12 finally ] stop
				 */
			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tChronometerStart_12_SUBPROCESS_STATE", 1);
	}

	public void tRunJob_22Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tRunJob_22_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [tRunJob_22 begin ] start
				 */

				ok_Hash.put("tRunJob_22", false);
				start_Hash.put("tRunJob_22", System.currentTimeMillis());

				currentComponent = "tRunJob_22";

				int tos_count_tRunJob_22 = 0;

				/**
				 * [tRunJob_22 begin ] stop
				 */

				/**
				 * [tRunJob_22 main ] start
				 */

				currentComponent = "tRunJob_22";

				java.util.List<String> paraList_tRunJob_22 = new java.util.ArrayList<String>();

				paraList_tRunJob_22.add("--father_pid=" + pid);

				paraList_tRunJob_22.add("--root_pid=" + rootPid);

				paraList_tRunJob_22.add("--father_node=tRunJob_22");

				paraList_tRunJob_22.add("--context=Default");

				if (enableLogStash) {
					paraList_tRunJob_22.add("--audit.enabled=" + enableLogStash);
				}

				// for feature:10589

				paraList_tRunJob_22.add("--stat_port=" + portStats);

				if (resuming_logs_dir_path != null) {
					paraList_tRunJob_22.add("--resuming_logs_dir_path=" + resuming_logs_dir_path);
				}
				String childResumePath_tRunJob_22 = ResumeUtil.getChildJobCheckPointPath(resuming_checkpoint_path);
				String tRunJobName_tRunJob_22 = ResumeUtil.getRighttRunJob(resuming_checkpoint_path);
				if ("tRunJob_22".equals(tRunJobName_tRunJob_22) && childResumePath_tRunJob_22 != null) {
					paraList_tRunJob_22.add("--resuming_checkpoint_path="
							+ ResumeUtil.getChildJobCheckPointPath(resuming_checkpoint_path));
				}
				paraList_tRunJob_22.add("--parent_part_launcher=JOB:" + jobName + "/NODE:tRunJob_22");

				java.util.Map<String, Object> parentContextMap_tRunJob_22 = new java.util.HashMap<String, Object>();

				context.synchronizeContext();
				class ContextProcessor_tRunJob_22 {
					private void transmitContext_0() {
						parentContextMap_tRunJob_22.put("folderName", context.folderName);
						paraList_tRunJob_22.add("--context_type " + "folderName" + "=" + "id_String");
					}

					public void transmitAllContext() {
						transmitContext_0();
					}
				}
				new ContextProcessor_tRunJob_22().transmitAllContext();
				java.util.Enumeration<?> propertyNames_tRunJob_22 = context.propertyNames();
				while (propertyNames_tRunJob_22.hasMoreElements()) {
					String key_tRunJob_22 = (String) propertyNames_tRunJob_22.nextElement();
					Object value_tRunJob_22 = (Object) context.get(key_tRunJob_22);
					if (value_tRunJob_22 != null) {
						paraList_tRunJob_22.add("--context_param " + key_tRunJob_22 + "=" + value_tRunJob_22);
					} else {
						paraList_tRunJob_22.add("--context_param " + key_tRunJob_22 + "="
								+ NULL_VALUE_EXPRESSION_IN_COMMAND_STRING_FOR_CHILD_JOB_ONLY);
					}

				}

				Object obj_tRunJob_22 = null;

				projetsid.orderline_0_1.orderLine childJob_tRunJob_22 = new projetsid.orderline_0_1.orderLine();
				// pass DataSources
				java.util.Map<String, routines.system.TalendDataSource> talendDataSources_tRunJob_22 = (java.util.Map<String, routines.system.TalendDataSource>) globalMap
						.get(KEY_DB_DATASOURCES);
				if (null != talendDataSources_tRunJob_22) {
					java.util.Map<String, javax.sql.DataSource> dataSources_tRunJob_22 = new java.util.HashMap<String, javax.sql.DataSource>();
					for (java.util.Map.Entry<String, routines.system.TalendDataSource> talendDataSourceEntry_tRunJob_22 : talendDataSources_tRunJob_22
							.entrySet()) {
						dataSources_tRunJob_22.put(talendDataSourceEntry_tRunJob_22.getKey(),
								talendDataSourceEntry_tRunJob_22.getValue().getRawDataSource());
					}
					childJob_tRunJob_22.setDataSources(dataSources_tRunJob_22);
				}

				childJob_tRunJob_22.parentContextMap = parentContextMap_tRunJob_22;

				String[][] childReturn_tRunJob_22 = childJob_tRunJob_22
						.runJob((String[]) paraList_tRunJob_22.toArray(new String[paraList_tRunJob_22.size()]));

				if (childJob_tRunJob_22.getErrorCode() == null) {
					globalMap.put("tRunJob_22_CHILD_RETURN_CODE", childJob_tRunJob_22.getStatus() != null
							&& ("failure").equals(childJob_tRunJob_22.getStatus()) ? 1 : 0);
				} else {
					globalMap.put("tRunJob_22_CHILD_RETURN_CODE", childJob_tRunJob_22.getErrorCode());
				}
				if (childJob_tRunJob_22.getExceptionStackTrace() != null) {
					globalMap.put("tRunJob_22_CHILD_EXCEPTION_STACKTRACE",
							childJob_tRunJob_22.getExceptionStackTrace());
				}
				errorCode = childJob_tRunJob_22.getErrorCode();
				if (childJob_tRunJob_22.getErrorCode() != null || ("failure").equals(childJob_tRunJob_22.getStatus())) {
					java.lang.Exception ce_tRunJob_22 = childJob_tRunJob_22.getException();
					throw new RuntimeException("Child job running failed.\n" + ((ce_tRunJob_22 != null)
							? (ce_tRunJob_22.getClass().getName() + ": " + ce_tRunJob_22.getMessage())
							: ""));
				}

				tos_count_tRunJob_22++;

				/**
				 * [tRunJob_22 main ] stop
				 */

				/**
				 * [tRunJob_22 process_data_begin ] start
				 */

				currentComponent = "tRunJob_22";

				/**
				 * [tRunJob_22 process_data_begin ] stop
				 */

				/**
				 * [tRunJob_22 process_data_end ] start
				 */

				currentComponent = "tRunJob_22";

				/**
				 * [tRunJob_22 process_data_end ] stop
				 */

				/**
				 * [tRunJob_22 end ] start
				 */

				currentComponent = "tRunJob_22";

				ok_Hash.put("tRunJob_22", true);
				end_Hash.put("tRunJob_22", System.currentTimeMillis());

				/**
				 * [tRunJob_22 end ] stop
				 */
			} // end the resume

			if (resumeEntryMethodName == null || globalResumeTicket) {
				resumeUtil.addLog("CHECKPOINT", "CONNECTION:SUBJOB_OK:tRunJob_22:OnSubjobOk", "",
						Thread.currentThread().getId() + "", "", "", "", "", "");
			}

			if (execStat) {
				runStat.updateStatOnConnection("OnSubjobOk41", 0, "ok");
			}

			tRunJob_23Process(globalMap);

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tRunJob_22 finally ] start
				 */

				currentComponent = "tRunJob_22";

				/**
				 * [tRunJob_22 finally ] stop
				 */
			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tRunJob_22_SUBPROCESS_STATE", 1);
	}

	public void tRunJob_23Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tRunJob_23_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [tRunJob_23 begin ] start
				 */

				ok_Hash.put("tRunJob_23", false);
				start_Hash.put("tRunJob_23", System.currentTimeMillis());

				currentComponent = "tRunJob_23";

				int tos_count_tRunJob_23 = 0;

				/**
				 * [tRunJob_23 begin ] stop
				 */

				/**
				 * [tRunJob_23 main ] start
				 */

				currentComponent = "tRunJob_23";

				java.util.List<String> paraList_tRunJob_23 = new java.util.ArrayList<String>();

				paraList_tRunJob_23.add("--father_pid=" + pid);

				paraList_tRunJob_23.add("--root_pid=" + rootPid);

				paraList_tRunJob_23.add("--father_node=tRunJob_23");

				paraList_tRunJob_23.add("--context=Default");

				if (enableLogStash) {
					paraList_tRunJob_23.add("--audit.enabled=" + enableLogStash);
				}

				// for feature:10589

				paraList_tRunJob_23.add("--stat_port=" + portStats);

				if (resuming_logs_dir_path != null) {
					paraList_tRunJob_23.add("--resuming_logs_dir_path=" + resuming_logs_dir_path);
				}
				String childResumePath_tRunJob_23 = ResumeUtil.getChildJobCheckPointPath(resuming_checkpoint_path);
				String tRunJobName_tRunJob_23 = ResumeUtil.getRighttRunJob(resuming_checkpoint_path);
				if ("tRunJob_23".equals(tRunJobName_tRunJob_23) && childResumePath_tRunJob_23 != null) {
					paraList_tRunJob_23.add("--resuming_checkpoint_path="
							+ ResumeUtil.getChildJobCheckPointPath(resuming_checkpoint_path));
				}
				paraList_tRunJob_23.add("--parent_part_launcher=JOB:" + jobName + "/NODE:tRunJob_23");

				java.util.Map<String, Object> parentContextMap_tRunJob_23 = new java.util.HashMap<String, Object>();

				context.synchronizeContext();
				class ContextProcessor_tRunJob_23 {
					private void transmitContext_0() {
						parentContextMap_tRunJob_23.put("folderName", context.folderName);
						paraList_tRunJob_23.add("--context_type " + "folderName" + "=" + "id_String");
					}

					public void transmitAllContext() {
						transmitContext_0();
					}
				}
				new ContextProcessor_tRunJob_23().transmitAllContext();
				java.util.Enumeration<?> propertyNames_tRunJob_23 = context.propertyNames();
				while (propertyNames_tRunJob_23.hasMoreElements()) {
					String key_tRunJob_23 = (String) propertyNames_tRunJob_23.nextElement();
					Object value_tRunJob_23 = (Object) context.get(key_tRunJob_23);
					if (value_tRunJob_23 != null) {
						paraList_tRunJob_23.add("--context_param " + key_tRunJob_23 + "=" + value_tRunJob_23);
					} else {
						paraList_tRunJob_23.add("--context_param " + key_tRunJob_23 + "="
								+ NULL_VALUE_EXPRESSION_IN_COMMAND_STRING_FOR_CHILD_JOB_ONLY);
					}

				}

				Object obj_tRunJob_23 = null;

				projetsid.dumporderline_0_2.dumpOrderLine childJob_tRunJob_23 = new projetsid.dumporderline_0_2.dumpOrderLine();
				// pass DataSources
				java.util.Map<String, routines.system.TalendDataSource> talendDataSources_tRunJob_23 = (java.util.Map<String, routines.system.TalendDataSource>) globalMap
						.get(KEY_DB_DATASOURCES);
				if (null != talendDataSources_tRunJob_23) {
					java.util.Map<String, javax.sql.DataSource> dataSources_tRunJob_23 = new java.util.HashMap<String, javax.sql.DataSource>();
					for (java.util.Map.Entry<String, routines.system.TalendDataSource> talendDataSourceEntry_tRunJob_23 : talendDataSources_tRunJob_23
							.entrySet()) {
						dataSources_tRunJob_23.put(talendDataSourceEntry_tRunJob_23.getKey(),
								talendDataSourceEntry_tRunJob_23.getValue().getRawDataSource());
					}
					childJob_tRunJob_23.setDataSources(dataSources_tRunJob_23);
				}

				childJob_tRunJob_23.parentContextMap = parentContextMap_tRunJob_23;

				String[][] childReturn_tRunJob_23 = childJob_tRunJob_23
						.runJob((String[]) paraList_tRunJob_23.toArray(new String[paraList_tRunJob_23.size()]));

				if (childJob_tRunJob_23.getErrorCode() == null) {
					globalMap.put("tRunJob_23_CHILD_RETURN_CODE", childJob_tRunJob_23.getStatus() != null
							&& ("failure").equals(childJob_tRunJob_23.getStatus()) ? 1 : 0);
				} else {
					globalMap.put("tRunJob_23_CHILD_RETURN_CODE", childJob_tRunJob_23.getErrorCode());
				}
				if (childJob_tRunJob_23.getExceptionStackTrace() != null) {
					globalMap.put("tRunJob_23_CHILD_EXCEPTION_STACKTRACE",
							childJob_tRunJob_23.getExceptionStackTrace());
				}
				errorCode = childJob_tRunJob_23.getErrorCode();
				if (childJob_tRunJob_23.getErrorCode() != null || ("failure").equals(childJob_tRunJob_23.getStatus())) {
					java.lang.Exception ce_tRunJob_23 = childJob_tRunJob_23.getException();
					throw new RuntimeException("Child job running failed.\n" + ((ce_tRunJob_23 != null)
							? (ce_tRunJob_23.getClass().getName() + ": " + ce_tRunJob_23.getMessage())
							: ""));
				}

				tos_count_tRunJob_23++;

				/**
				 * [tRunJob_23 main ] stop
				 */

				/**
				 * [tRunJob_23 process_data_begin ] start
				 */

				currentComponent = "tRunJob_23";

				/**
				 * [tRunJob_23 process_data_begin ] stop
				 */

				/**
				 * [tRunJob_23 process_data_end ] start
				 */

				currentComponent = "tRunJob_23";

				/**
				 * [tRunJob_23 process_data_end ] stop
				 */

				/**
				 * [tRunJob_23 end ] start
				 */

				currentComponent = "tRunJob_23";

				ok_Hash.put("tRunJob_23", true);
				end_Hash.put("tRunJob_23", System.currentTimeMillis());

				/**
				 * [tRunJob_23 end ] stop
				 */
			} // end the resume

			if (resumeEntryMethodName == null || globalResumeTicket) {
				resumeUtil.addLog("CHECKPOINT", "CONNECTION:SUBJOB_OK:tRunJob_23:OnSubjobOk", "",
						Thread.currentThread().getId() + "", "", "", "", "", "");
			}

			if (execStat) {
				runStat.updateStatOnConnection("OnSubjobOk42", 0, "ok");
			}

			tChronometerStop_13Process(globalMap);

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tRunJob_23 finally ] start
				 */

				currentComponent = "tRunJob_23";

				/**
				 * [tRunJob_23 finally ] stop
				 */
			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tRunJob_23_SUBPROCESS_STATE", 1);
	}

	public void tChronometerStop_13Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tChronometerStop_13_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				/**
				 * [tChronometerStop_13 begin ] start
				 */

				ok_Hash.put("tChronometerStop_13", false);
				start_Hash.put("tChronometerStop_13", System.currentTimeMillis());

				currentComponent = "tChronometerStop_13";

				int tos_count_tChronometerStop_13 = 0;

				long timetChronometerStop_13;

				timetChronometerStop_13 = System.currentTimeMillis()
						- ((Long) globalMap.get("tChronometerStart_12")).longValue();

				System.out.print("[ tChronometerStop_13 ]  ");

				System.out.print("   " + timetChronometerStop_13 / 1000 + "seconds   ");

				System.out.println("OrderLine" + "  " + timetChronometerStop_13 + " milliseconds");

				Long currentTimetChronometerStop_13 = System.currentTimeMillis();
				globalMap.put("tChronometerStop_13", currentTimetChronometerStop_13);

				globalMap.put("tChronometerStop_13_STOPTIME", currentTimetChronometerStop_13);
				globalMap.put("tChronometerStop_13_DURATION", timetChronometerStop_13);

				/**
				 * [tChronometerStop_13 begin ] stop
				 */

				/**
				 * [tChronometerStop_13 main ] start
				 */

				currentComponent = "tChronometerStop_13";

				tos_count_tChronometerStop_13++;

				/**
				 * [tChronometerStop_13 main ] stop
				 */

				/**
				 * [tChronometerStop_13 process_data_begin ] start
				 */

				currentComponent = "tChronometerStop_13";

				/**
				 * [tChronometerStop_13 process_data_begin ] stop
				 */

				/**
				 * [tChronometerStop_13 process_data_end ] start
				 */

				currentComponent = "tChronometerStop_13";

				/**
				 * [tChronometerStop_13 process_data_end ] stop
				 */

				/**
				 * [tChronometerStop_13 end ] start
				 */

				currentComponent = "tChronometerStop_13";

				ok_Hash.put("tChronometerStop_13", true);
				end_Hash.put("tChronometerStop_13", System.currentTimeMillis());

				/**
				 * [tChronometerStop_13 end ] stop
				 */
			} // end the resume

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tChronometerStop_13 finally ] start
				 */

				currentComponent = "tChronometerStop_13";

				/**
				 * [tChronometerStop_13 finally ] stop
				 */
			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tChronometerStop_13_SUBPROCESS_STATE", 1);
	}

	public String resuming_logs_dir_path = null;
	public String resuming_checkpoint_path = null;
	public String parent_part_launcher = null;
	private String resumeEntryMethodName = null;
	private boolean globalResumeTicket = false;

	public boolean watch = false;
	// portStats is null, it means don't execute the statistics
	public Integer portStats = null;
	public int portTraces = 4334;
	public String clientHost;
	public String defaultClientHost = "localhost";
	public String contextStr = "jordan";
	public boolean isDefaultContext = true;
	public String pid = "0";
	public String rootPid = null;
	public String fatherPid = null;
	public String fatherNode = null;
	public long startTime = 0;
	public boolean isChildJob = false;
	public String log4jLevel = "";

	private boolean enableLogStash;

	private boolean execStat = true;

	private ThreadLocal<java.util.Map<String, String>> threadLocal = new ThreadLocal<java.util.Map<String, String>>() {
		protected java.util.Map<String, String> initialValue() {
			java.util.Map<String, String> threadRunResultMap = new java.util.HashMap<String, String>();
			threadRunResultMap.put("errorCode", null);
			threadRunResultMap.put("status", "");
			return threadRunResultMap;
		};
	};

	protected PropertiesWithType context_param = new PropertiesWithType();
	public java.util.Map<String, Object> parentContextMap = new java.util.HashMap<String, Object>();

	public String status = "";

	public static void main(String[] args) {
		final maestro maestroClass = new maestro();

		int exitCode = maestroClass.runJobInTOS(args);

		System.exit(exitCode);
	}

	public String[][] runJob(String[] args) {

		int exitCode = runJobInTOS(args);
		String[][] bufferValue = new String[][] { { Integer.toString(exitCode) } };

		return bufferValue;
	}

	public boolean hastBufferOutputComponent() {
		boolean hastBufferOutput = false;

		return hastBufferOutput;
	}

	public int runJobInTOS(String[] args) {
		// reset status
		status = "";

		String lastStr = "";
		for (String arg : args) {
			if (arg.equalsIgnoreCase("--context_param")) {
				lastStr = arg;
			} else if (lastStr.equals("")) {
				evalParam(arg);
			} else {
				evalParam(lastStr + " " + arg);
				lastStr = "";
			}
		}
		enableLogStash = "true".equalsIgnoreCase(System.getProperty("audit.enabled"));

		if (clientHost == null) {
			clientHost = defaultClientHost;
		}

		if (pid == null || "0".equals(pid)) {
			pid = TalendString.getAsciiRandomString(6);
		}

		if (rootPid == null) {
			rootPid = pid;
		}
		if (fatherPid == null) {
			fatherPid = pid;
		} else {
			isChildJob = true;
		}

		if (portStats != null) {
			// portStats = -1; //for testing
			if (portStats < 0 || portStats > 65535) {
				// issue:10869, the portStats is invalid, so this client socket can't open
				System.err.println("The statistics socket port " + portStats + " is invalid.");
				execStat = false;
			}
		} else {
			execStat = false;
		}
		boolean inOSGi = routines.system.BundleUtils.inOSGi();

		if (inOSGi) {
			java.util.Dictionary<String, Object> jobProperties = routines.system.BundleUtils.getJobProperties(jobName);

			if (jobProperties != null && jobProperties.get("context") != null) {
				contextStr = (String) jobProperties.get("context");
			}
		}

		try {
			// call job/subjob with an existing context, like: --context=production. if
			// without this parameter, there will use the default context instead.
			java.io.InputStream inContext = maestro.class.getClassLoader()
					.getResourceAsStream("projetsid/maestro_0_1/contexts/" + contextStr + ".properties");
			if (inContext == null) {
				inContext = maestro.class.getClassLoader()
						.getResourceAsStream("config/contexts/" + contextStr + ".properties");
			}
			if (inContext != null) {
				try {
					// defaultProps is in order to keep the original context value
					if (context != null && context.isEmpty()) {
						defaultProps.load(inContext);
						context = new ContextProperties(defaultProps);
					}
				} finally {
					inContext.close();
				}
			} else if (!isDefaultContext) {
				// print info and job continue to run, for case: context_param is not empty.
				System.err.println("Could not find the context " + contextStr);
			}

			if (!context_param.isEmpty()) {
				context.putAll(context_param);
				// set types for params from parentJobs
				for (Object key : context_param.keySet()) {
					String context_key = key.toString();
					String context_type = context_param.getContextType(context_key);
					context.setContextType(context_key, context_type);

				}
			}
			class ContextProcessing {
				private void processContext_0() {
					context.setContextType("folderName", "id_String");
					if (context.getStringValue("folderName") == null) {
						context.folderName = null;
					} else {
						context.folderName = (String) context.getProperty("folderName");
					}
				}

				public void processAllContext() {
					processContext_0();
				}
			}

			new ContextProcessing().processAllContext();
		} catch (java.io.IOException ie) {
			System.err.println("Could not load context " + contextStr);
			ie.printStackTrace();
		}

		// get context value from parent directly
		if (parentContextMap != null && !parentContextMap.isEmpty()) {
			if (parentContextMap.containsKey("folderName")) {
				context.folderName = (String) parentContextMap.get("folderName");
			}
		}

		// Resume: init the resumeUtil
		resumeEntryMethodName = ResumeUtil.getResumeEntryMethodName(resuming_checkpoint_path);
		resumeUtil = new ResumeUtil(resuming_logs_dir_path, isChildJob, rootPid);
		resumeUtil.initCommonInfo(pid, rootPid, fatherPid, projectName, jobName, contextStr, jobVersion);

		List<String> parametersToEncrypt = new java.util.ArrayList<String>();
		// Resume: jobStart
		resumeUtil.addLog("JOB_STARTED", "JOB:" + jobName, parent_part_launcher, Thread.currentThread().getId() + "",
				"", "", "", "", resumeUtil.convertToJsonText(context, parametersToEncrypt));

		if (execStat) {
			try {
				runStat.openSocket(!isChildJob);
				runStat.setAllPID(rootPid, fatherPid, pid, jobName);
				runStat.startThreadStat(clientHost, portStats);
				runStat.updateStatOnJob(RunStat.JOBSTART, fatherNode);
			} catch (java.io.IOException ioException) {
				ioException.printStackTrace();
			}
		}

		java.util.concurrent.ConcurrentHashMap<Object, Object> concurrentHashMap = new java.util.concurrent.ConcurrentHashMap<Object, Object>();
		globalMap.put("concurrentHashMap", concurrentHashMap);

		long startUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		long endUsedMemory = 0;
		long end = 0;

		startTime = System.currentTimeMillis();

		this.globalResumeTicket = true;// to run tPreJob

		try {
			errorCode = null;
			tPrejob_1Process(globalMap);
			if (!"failure".equals(status)) {
				status = "end";
			}
		} catch (TalendException e_tPrejob_1) {
			globalMap.put("tPrejob_1_SUBPROCESS_STATE", -1);

			e_tPrejob_1.printStackTrace();

		}

		this.globalResumeTicket = false;// to run others jobs

		this.globalResumeTicket = true;// to run tPostJob

		try {
			errorCode = null;
			tPostjob_1Process(globalMap);
			if (!"failure".equals(status)) {
				status = "end";
			}
		} catch (TalendException e_tPostjob_1) {
			globalMap.put("tPostjob_1_SUBPROCESS_STATE", -1);

			e_tPostjob_1.printStackTrace();

		}

		end = System.currentTimeMillis();

		if (watch) {
			System.out.println((end - startTime) + " milliseconds");
		}

		endUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		if (false) {
			System.out.println((endUsedMemory - startUsedMemory) + " bytes memory increase when running : maestro");
		}

		if (execStat) {
			runStat.updateStatOnJob(RunStat.JOBEND, fatherNode);
			runStat.stopThreadStat();
		}
		int returnCode = 0;

		if (errorCode == null) {
			returnCode = status != null && status.equals("failure") ? 1 : 0;
		} else {
			returnCode = errorCode.intValue();
		}
		resumeUtil.addLog("JOB_ENDED", "JOB:" + jobName, parent_part_launcher, Thread.currentThread().getId() + "", "",
				"" + returnCode, "", "", "");

		return returnCode;

	}

	// only for OSGi env
	public void destroy() {

	}

	private java.util.Map<String, Object> getSharedConnections4REST() {
		java.util.Map<String, Object> connections = new java.util.HashMap<String, Object>();

		return connections;
	}

	private void evalParam(String arg) {
		if (arg.startsWith("--resuming_logs_dir_path")) {
			resuming_logs_dir_path = arg.substring(25);
		} else if (arg.startsWith("--resuming_checkpoint_path")) {
			resuming_checkpoint_path = arg.substring(27);
		} else if (arg.startsWith("--parent_part_launcher")) {
			parent_part_launcher = arg.substring(23);
		} else if (arg.startsWith("--watch")) {
			watch = true;
		} else if (arg.startsWith("--stat_port=")) {
			String portStatsStr = arg.substring(12);
			if (portStatsStr != null && !portStatsStr.equals("null")) {
				portStats = Integer.parseInt(portStatsStr);
			}
		} else if (arg.startsWith("--trace_port=")) {
			portTraces = Integer.parseInt(arg.substring(13));
		} else if (arg.startsWith("--client_host=")) {
			clientHost = arg.substring(14);
		} else if (arg.startsWith("--context=")) {
			contextStr = arg.substring(10);
			isDefaultContext = false;
		} else if (arg.startsWith("--father_pid=")) {
			fatherPid = arg.substring(13);
		} else if (arg.startsWith("--root_pid=")) {
			rootPid = arg.substring(11);
		} else if (arg.startsWith("--father_node=")) {
			fatherNode = arg.substring(14);
		} else if (arg.startsWith("--pid=")) {
			pid = arg.substring(6);
		} else if (arg.startsWith("--context_type")) {
			String keyValue = arg.substring(15);
			int index = -1;
			if (keyValue != null && (index = keyValue.indexOf('=')) > -1) {
				if (fatherPid == null) {
					context_param.setContextType(keyValue.substring(0, index),
							replaceEscapeChars(keyValue.substring(index + 1)));
				} else { // the subjob won't escape the especial chars
					context_param.setContextType(keyValue.substring(0, index), keyValue.substring(index + 1));
				}

			}

		} else if (arg.startsWith("--context_param")) {
			String keyValue = arg.substring(16);
			int index = -1;
			if (keyValue != null && (index = keyValue.indexOf('=')) > -1) {
				if (fatherPid == null) {
					context_param.put(keyValue.substring(0, index), replaceEscapeChars(keyValue.substring(index + 1)));
				} else { // the subjob won't escape the especial chars
					context_param.put(keyValue.substring(0, index), keyValue.substring(index + 1));
				}
			}
		} else if (arg.startsWith("--log4jLevel=")) {
			log4jLevel = arg.substring(13);
		} else if (arg.startsWith("--audit.enabled") && arg.contains("=")) {// for trunjob call
			final int equal = arg.indexOf('=');
			final String key = arg.substring("--".length(), equal);
			System.setProperty(key, arg.substring(equal + 1));
		}
	}

	private static final String NULL_VALUE_EXPRESSION_IN_COMMAND_STRING_FOR_CHILD_JOB_ONLY = "<TALEND_NULL>";

	private final String[][] escapeChars = { { "\\\\", "\\" }, { "\\n", "\n" }, { "\\'", "\'" }, { "\\r", "\r" },
			{ "\\f", "\f" }, { "\\b", "\b" }, { "\\t", "\t" } };

	private String replaceEscapeChars(String keyValue) {

		if (keyValue == null || ("").equals(keyValue.trim())) {
			return keyValue;
		}

		StringBuilder result = new StringBuilder();
		int currIndex = 0;
		while (currIndex < keyValue.length()) {
			int index = -1;
			// judege if the left string includes escape chars
			for (String[] strArray : escapeChars) {
				index = keyValue.indexOf(strArray[0], currIndex);
				if (index >= 0) {

					result.append(keyValue.substring(currIndex, index + strArray[0].length()).replace(strArray[0],
							strArray[1]));
					currIndex = index + strArray[0].length();
					break;
				}
			}
			// if the left string doesn't include escape chars, append the left into the
			// result
			if (index < 0) {
				result.append(keyValue.substring(currIndex));
				currIndex = currIndex + keyValue.length();
			}
		}

		return result.toString();
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public String getStatus() {
		return status;
	}

	ResumeUtil resumeUtil = null;
}
/************************************************************************************************
 * 300007 characters generated by Talend Open Studio for Data Integration on the
 * 9 mai 2024 à 19:10:24 CEST
 ************************************************************************************************/