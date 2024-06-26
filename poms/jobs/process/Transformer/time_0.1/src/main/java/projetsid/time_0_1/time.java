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

package projetsid.time_0_1;

import routines.Numeric;
import routines.DataOperation;
import routines.TalendDataGenerator;
import routines.TalendStringUtil;
import routines.TalendString;
import routines.StringHandling;
import routines.Relational;
import routines.TalendDate;
import routines.fonctionsPerso;
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
 * Job: time Purpose: extraire la table pays du csv<br>
 * Description: <br>
 * 
 * @author user@talend.com
 * @version 8.0.1.20211109_1610
 * @status
 */
public class time implements TalendJob {

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
	private final String jobName = "time";
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
					time.this.exception = e;
				}
			}
			if (!(e instanceof TalendException)) {
				try {
					for (java.lang.reflect.Method m : this.getClass().getEnclosingClass().getMethods()) {
						if (m.getName().compareTo(currentComponent + "_error") == 0) {
							m.invoke(time.this, new Object[] { e, currentComponent, globalMap });
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

	public void tFileInputDelimited_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tFilterColumns_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tLogRow_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tSplitRow_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tUniqRow_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tMap_1_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tLogRow_2_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tUniqRow_2_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tFileOutputDelimited_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tLogRow_3_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tUniqRow_3_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tFileOutputDelimited_2_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tLogRow_4_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tUniqRow_4_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tFileOutputDelimited_3_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tFileInputDelimited_1_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public static class row11Struct implements routines.system.IPersistableRow<row11Struct> {
		final static byte[] commonByteArrayLock_PROJETSID_time = new byte[0];
		static byte[] commonByteArray_PROJETSID_time = new byte[0];
		protected static final int DEFAULT_HASHCODE = 1;
		protected static final int PRIME = 31;
		protected int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		public String loopKey;

		public String id;

		public String getId() {
			return this.id;
		}

		public String quarterName;

		public String getQuarterName() {
			return this.quarterName;
		}

		public Integer year;

		public Integer getYear() {
			return this.year;
		}

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());

				this.hashCode = result;
				this.hashCodeDirty = false;
			}
			return this.hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final row11Struct other = (row11Struct) obj;

			if (this.id == null) {
				if (other.id != null)
					return false;

			} else if (!this.id.equals(other.id))

				return false;

			return true;
		}

		public void copyDataTo(row11Struct other) {

			other.id = this.id;
			other.quarterName = this.quarterName;
			other.year = this.year;

		}

		public void copyKeysDataTo(row11Struct other) {

			other.id = this.id;

		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJETSID_time.length) {
					if (length < 1024 && commonByteArray_PROJETSID_time.length == 0) {
						commonByteArray_PROJETSID_time = new byte[1024];
					} else {
						commonByteArray_PROJETSID_time = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_PROJETSID_time, 0, length);
				strReturn = new String(commonByteArray_PROJETSID_time, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJETSID_time.length) {
					if (length < 1024 && commonByteArray_PROJETSID_time.length == 0) {
						commonByteArray_PROJETSID_time = new byte[1024];
					} else {
						commonByteArray_PROJETSID_time = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_PROJETSID_time, 0, length);
				strReturn = new String(commonByteArray_PROJETSID_time, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_PROJETSID_time) {

				try {

					int length = 0;

					this.id = readString(dis);

					this.quarterName = readString(dis);

					this.year = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_PROJETSID_time) {

				try {

					int length = 0;

					this.id = readString(dis);

					this.quarterName = readString(dis);

					this.year = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// String

				writeString(this.id, dos);

				// String

				writeString(this.quarterName, dos);

				// Integer

				writeInteger(this.year, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// String

				writeString(this.id, dos);

				// String

				writeString(this.quarterName, dos);

				// Integer

				writeInteger(this.year, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("id=" + id);
			sb.append(",quarterName=" + quarterName);
			sb.append(",year=" + String.valueOf(year));
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row11Struct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.id, other.id);
			if (returnValue != 0) {
				return returnValue;
			}

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class row7Struct implements routines.system.IPersistableRow<row7Struct> {
		final static byte[] commonByteArrayLock_PROJETSID_time = new byte[0];
		static byte[] commonByteArray_PROJETSID_time = new byte[0];
		protected static final int DEFAULT_HASHCODE = 1;
		protected static final int PRIME = 31;
		protected int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		public String loopKey;

		public String id;

		public String getId() {
			return this.id;
		}

		public String quarterName;

		public String getQuarterName() {
			return this.quarterName;
		}

		public Integer year;

		public Integer getYear() {
			return this.year;
		}

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());

				this.hashCode = result;
				this.hashCodeDirty = false;
			}
			return this.hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final row7Struct other = (row7Struct) obj;

			if (this.id == null) {
				if (other.id != null)
					return false;

			} else if (!this.id.equals(other.id))

				return false;

			return true;
		}

		public void copyDataTo(row7Struct other) {

			other.id = this.id;
			other.quarterName = this.quarterName;
			other.year = this.year;

		}

		public void copyKeysDataTo(row7Struct other) {

			other.id = this.id;

		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJETSID_time.length) {
					if (length < 1024 && commonByteArray_PROJETSID_time.length == 0) {
						commonByteArray_PROJETSID_time = new byte[1024];
					} else {
						commonByteArray_PROJETSID_time = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_PROJETSID_time, 0, length);
				strReturn = new String(commonByteArray_PROJETSID_time, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJETSID_time.length) {
					if (length < 1024 && commonByteArray_PROJETSID_time.length == 0) {
						commonByteArray_PROJETSID_time = new byte[1024];
					} else {
						commonByteArray_PROJETSID_time = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_PROJETSID_time, 0, length);
				strReturn = new String(commonByteArray_PROJETSID_time, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_PROJETSID_time) {

				try {

					int length = 0;

					this.id = readString(dis);

					this.quarterName = readString(dis);

					this.year = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_PROJETSID_time) {

				try {

					int length = 0;

					this.id = readString(dis);

					this.quarterName = readString(dis);

					this.year = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// String

				writeString(this.id, dos);

				// String

				writeString(this.quarterName, dos);

				// Integer

				writeInteger(this.year, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// String

				writeString(this.id, dos);

				// String

				writeString(this.quarterName, dos);

				// Integer

				writeInteger(this.year, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("id=" + id);
			sb.append(",quarterName=" + quarterName);
			sb.append(",year=" + String.valueOf(year));
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row7Struct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.id, other.id);
			if (returnValue != 0) {
				return returnValue;
			}

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class row10Struct implements routines.system.IPersistableRow<row10Struct> {
		final static byte[] commonByteArrayLock_PROJETSID_time = new byte[0];
		static byte[] commonByteArray_PROJETSID_time = new byte[0];
		protected static final int DEFAULT_HASHCODE = 1;
		protected static final int PRIME = 31;
		protected int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		public String loopKey;

		public String id;

		public String getId() {
			return this.id;
		}

		public Integer monthNb;

		public Integer getMonthNb() {
			return this.monthNb;
		}

		public String monthName;

		public String getMonthName() {
			return this.monthName;
		}

		public String quarterId;

		public String getQuarterId() {
			return this.quarterId;
		}

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());

				this.hashCode = result;
				this.hashCodeDirty = false;
			}
			return this.hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final row10Struct other = (row10Struct) obj;

			if (this.id == null) {
				if (other.id != null)
					return false;

			} else if (!this.id.equals(other.id))

				return false;

			return true;
		}

		public void copyDataTo(row10Struct other) {

			other.id = this.id;
			other.monthNb = this.monthNb;
			other.monthName = this.monthName;
			other.quarterId = this.quarterId;

		}

		public void copyKeysDataTo(row10Struct other) {

			other.id = this.id;

		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJETSID_time.length) {
					if (length < 1024 && commonByteArray_PROJETSID_time.length == 0) {
						commonByteArray_PROJETSID_time = new byte[1024];
					} else {
						commonByteArray_PROJETSID_time = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_PROJETSID_time, 0, length);
				strReturn = new String(commonByteArray_PROJETSID_time, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJETSID_time.length) {
					if (length < 1024 && commonByteArray_PROJETSID_time.length == 0) {
						commonByteArray_PROJETSID_time = new byte[1024];
					} else {
						commonByteArray_PROJETSID_time = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_PROJETSID_time, 0, length);
				strReturn = new String(commonByteArray_PROJETSID_time, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_PROJETSID_time) {

				try {

					int length = 0;

					this.id = readString(dis);

					this.monthNb = readInteger(dis);

					this.monthName = readString(dis);

					this.quarterId = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_PROJETSID_time) {

				try {

					int length = 0;

					this.id = readString(dis);

					this.monthNb = readInteger(dis);

					this.monthName = readString(dis);

					this.quarterId = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// String

				writeString(this.id, dos);

				// Integer

				writeInteger(this.monthNb, dos);

				// String

				writeString(this.monthName, dos);

				// String

				writeString(this.quarterId, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// String

				writeString(this.id, dos);

				// Integer

				writeInteger(this.monthNb, dos);

				// String

				writeString(this.monthName, dos);

				// String

				writeString(this.quarterId, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("id=" + id);
			sb.append(",monthNb=" + String.valueOf(monthNb));
			sb.append(",monthName=" + monthName);
			sb.append(",quarterId=" + quarterId);
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row10Struct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.id, other.id);
			if (returnValue != 0) {
				return returnValue;
			}

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class row8Struct implements routines.system.IPersistableRow<row8Struct> {
		final static byte[] commonByteArrayLock_PROJETSID_time = new byte[0];
		static byte[] commonByteArray_PROJETSID_time = new byte[0];
		protected static final int DEFAULT_HASHCODE = 1;
		protected static final int PRIME = 31;
		protected int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		public String loopKey;

		public String id;

		public String getId() {
			return this.id;
		}

		public Integer monthNb;

		public Integer getMonthNb() {
			return this.monthNb;
		}

		public String monthName;

		public String getMonthName() {
			return this.monthName;
		}

		public String quarterId;

		public String getQuarterId() {
			return this.quarterId;
		}

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());

				this.hashCode = result;
				this.hashCodeDirty = false;
			}
			return this.hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final row8Struct other = (row8Struct) obj;

			if (this.id == null) {
				if (other.id != null)
					return false;

			} else if (!this.id.equals(other.id))

				return false;

			return true;
		}

		public void copyDataTo(row8Struct other) {

			other.id = this.id;
			other.monthNb = this.monthNb;
			other.monthName = this.monthName;
			other.quarterId = this.quarterId;

		}

		public void copyKeysDataTo(row8Struct other) {

			other.id = this.id;

		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJETSID_time.length) {
					if (length < 1024 && commonByteArray_PROJETSID_time.length == 0) {
						commonByteArray_PROJETSID_time = new byte[1024];
					} else {
						commonByteArray_PROJETSID_time = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_PROJETSID_time, 0, length);
				strReturn = new String(commonByteArray_PROJETSID_time, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJETSID_time.length) {
					if (length < 1024 && commonByteArray_PROJETSID_time.length == 0) {
						commonByteArray_PROJETSID_time = new byte[1024];
					} else {
						commonByteArray_PROJETSID_time = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_PROJETSID_time, 0, length);
				strReturn = new String(commonByteArray_PROJETSID_time, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_PROJETSID_time) {

				try {

					int length = 0;

					this.id = readString(dis);

					this.monthNb = readInteger(dis);

					this.monthName = readString(dis);

					this.quarterId = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_PROJETSID_time) {

				try {

					int length = 0;

					this.id = readString(dis);

					this.monthNb = readInteger(dis);

					this.monthName = readString(dis);

					this.quarterId = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// String

				writeString(this.id, dos);

				// Integer

				writeInteger(this.monthNb, dos);

				// String

				writeString(this.monthName, dos);

				// String

				writeString(this.quarterId, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// String

				writeString(this.id, dos);

				// Integer

				writeInteger(this.monthNb, dos);

				// String

				writeString(this.monthName, dos);

				// String

				writeString(this.quarterId, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("id=" + id);
			sb.append(",monthNb=" + String.valueOf(monthNb));
			sb.append(",monthName=" + monthName);
			sb.append(",quarterId=" + quarterId);
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row8Struct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.id, other.id);
			if (returnValue != 0) {
				return returnValue;
			}

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class row9Struct implements routines.system.IPersistableRow<row9Struct> {
		final static byte[] commonByteArrayLock_PROJETSID_time = new byte[0];
		static byte[] commonByteArray_PROJETSID_time = new byte[0];
		protected static final int DEFAULT_HASHCODE = 1;
		protected static final int PRIME = 31;
		protected int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		public String loopKey;

		public java.util.Date date;

		public java.util.Date getDate() {
			return this.date;
		}

		public String dayWeekName;

		public String getDayWeekName() {
			return this.dayWeekName;
		}

		public String monthId;

		public String getMonthId() {
			return this.monthId;
		}

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime * result + ((this.date == null) ? 0 : this.date.hashCode());

				this.hashCode = result;
				this.hashCodeDirty = false;
			}
			return this.hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final row9Struct other = (row9Struct) obj;

			if (this.date == null) {
				if (other.date != null)
					return false;

			} else if (!this.date.equals(other.date))

				return false;

			return true;
		}

		public void copyDataTo(row9Struct other) {

			other.date = this.date;
			other.dayWeekName = this.dayWeekName;
			other.monthId = this.monthId;

		}

		public void copyKeysDataTo(row9Struct other) {

			other.date = this.date;

		}

		private java.util.Date readDate(ObjectInputStream dis) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(dis.readLong());
			}
			return dateReturn;
		}

		private java.util.Date readDate(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(unmarshaller.readLong());
			}
			return dateReturn;
		}

		private void writeDate(java.util.Date date1, ObjectOutputStream dos) throws IOException {
			if (date1 == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeLong(date1.getTime());
			}
		}

		private void writeDate(java.util.Date date1, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (date1 == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeLong(date1.getTime());
			}
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJETSID_time.length) {
					if (length < 1024 && commonByteArray_PROJETSID_time.length == 0) {
						commonByteArray_PROJETSID_time = new byte[1024];
					} else {
						commonByteArray_PROJETSID_time = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_PROJETSID_time, 0, length);
				strReturn = new String(commonByteArray_PROJETSID_time, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJETSID_time.length) {
					if (length < 1024 && commonByteArray_PROJETSID_time.length == 0) {
						commonByteArray_PROJETSID_time = new byte[1024];
					} else {
						commonByteArray_PROJETSID_time = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_PROJETSID_time, 0, length);
				strReturn = new String(commonByteArray_PROJETSID_time, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_PROJETSID_time) {

				try {

					int length = 0;

					this.date = readDate(dis);

					this.dayWeekName = readString(dis);

					this.monthId = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_PROJETSID_time) {

				try {

					int length = 0;

					this.date = readDate(dis);

					this.dayWeekName = readString(dis);

					this.monthId = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// java.util.Date

				writeDate(this.date, dos);

				// String

				writeString(this.dayWeekName, dos);

				// String

				writeString(this.monthId, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// java.util.Date

				writeDate(this.date, dos);

				// String

				writeString(this.dayWeekName, dos);

				// String

				writeString(this.monthId, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("date=" + String.valueOf(date));
			sb.append(",dayWeekName=" + dayWeekName);
			sb.append(",monthId=" + monthId);
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row9Struct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.date, other.date);
			if (returnValue != 0) {
				return returnValue;
			}

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class row6Struct implements routines.system.IPersistableRow<row6Struct> {
		final static byte[] commonByteArrayLock_PROJETSID_time = new byte[0];
		static byte[] commonByteArray_PROJETSID_time = new byte[0];
		protected static final int DEFAULT_HASHCODE = 1;
		protected static final int PRIME = 31;
		protected int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		public String loopKey;

		public java.util.Date date;

		public java.util.Date getDate() {
			return this.date;
		}

		public String dayWeekName;

		public String getDayWeekName() {
			return this.dayWeekName;
		}

		public String monthId;

		public String getMonthId() {
			return this.monthId;
		}

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime * result + ((this.date == null) ? 0 : this.date.hashCode());

				this.hashCode = result;
				this.hashCodeDirty = false;
			}
			return this.hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final row6Struct other = (row6Struct) obj;

			if (this.date == null) {
				if (other.date != null)
					return false;

			} else if (!this.date.equals(other.date))

				return false;

			return true;
		}

		public void copyDataTo(row6Struct other) {

			other.date = this.date;
			other.dayWeekName = this.dayWeekName;
			other.monthId = this.monthId;

		}

		public void copyKeysDataTo(row6Struct other) {

			other.date = this.date;

		}

		private java.util.Date readDate(ObjectInputStream dis) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(dis.readLong());
			}
			return dateReturn;
		}

		private java.util.Date readDate(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(unmarshaller.readLong());
			}
			return dateReturn;
		}

		private void writeDate(java.util.Date date1, ObjectOutputStream dos) throws IOException {
			if (date1 == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeLong(date1.getTime());
			}
		}

		private void writeDate(java.util.Date date1, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (date1 == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeLong(date1.getTime());
			}
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJETSID_time.length) {
					if (length < 1024 && commonByteArray_PROJETSID_time.length == 0) {
						commonByteArray_PROJETSID_time = new byte[1024];
					} else {
						commonByteArray_PROJETSID_time = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_PROJETSID_time, 0, length);
				strReturn = new String(commonByteArray_PROJETSID_time, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJETSID_time.length) {
					if (length < 1024 && commonByteArray_PROJETSID_time.length == 0) {
						commonByteArray_PROJETSID_time = new byte[1024];
					} else {
						commonByteArray_PROJETSID_time = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_PROJETSID_time, 0, length);
				strReturn = new String(commonByteArray_PROJETSID_time, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_PROJETSID_time) {

				try {

					int length = 0;

					this.date = readDate(dis);

					this.dayWeekName = readString(dis);

					this.monthId = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_PROJETSID_time) {

				try {

					int length = 0;

					this.date = readDate(dis);

					this.dayWeekName = readString(dis);

					this.monthId = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// java.util.Date

				writeDate(this.date, dos);

				// String

				writeString(this.dayWeekName, dos);

				// String

				writeString(this.monthId, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// java.util.Date

				writeDate(this.date, dos);

				// String

				writeString(this.dayWeekName, dos);

				// String

				writeString(this.monthId, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("date=" + String.valueOf(date));
			sb.append(",dayWeekName=" + dayWeekName);
			sb.append(",monthId=" + monthId);
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row6Struct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.date, other.date);
			if (returnValue != 0) {
				return returnValue;
			}

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class dateStruct implements routines.system.IPersistableRow<dateStruct> {
		final static byte[] commonByteArrayLock_PROJETSID_time = new byte[0];
		static byte[] commonByteArray_PROJETSID_time = new byte[0];
		protected static final int DEFAULT_HASHCODE = 1;
		protected static final int PRIME = 31;
		protected int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		public String loopKey;

		public java.util.Date date;

		public java.util.Date getDate() {
			return this.date;
		}

		public String dayWeekName;

		public String getDayWeekName() {
			return this.dayWeekName;
		}

		public String monthId;

		public String getMonthId() {
			return this.monthId;
		}

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime * result + ((this.date == null) ? 0 : this.date.hashCode());

				this.hashCode = result;
				this.hashCodeDirty = false;
			}
			return this.hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final dateStruct other = (dateStruct) obj;

			if (this.date == null) {
				if (other.date != null)
					return false;

			} else if (!this.date.equals(other.date))

				return false;

			return true;
		}

		public void copyDataTo(dateStruct other) {

			other.date = this.date;
			other.dayWeekName = this.dayWeekName;
			other.monthId = this.monthId;

		}

		public void copyKeysDataTo(dateStruct other) {

			other.date = this.date;

		}

		private java.util.Date readDate(ObjectInputStream dis) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(dis.readLong());
			}
			return dateReturn;
		}

		private java.util.Date readDate(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(unmarshaller.readLong());
			}
			return dateReturn;
		}

		private void writeDate(java.util.Date date1, ObjectOutputStream dos) throws IOException {
			if (date1 == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeLong(date1.getTime());
			}
		}

		private void writeDate(java.util.Date date1, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (date1 == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeLong(date1.getTime());
			}
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJETSID_time.length) {
					if (length < 1024 && commonByteArray_PROJETSID_time.length == 0) {
						commonByteArray_PROJETSID_time = new byte[1024];
					} else {
						commonByteArray_PROJETSID_time = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_PROJETSID_time, 0, length);
				strReturn = new String(commonByteArray_PROJETSID_time, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJETSID_time.length) {
					if (length < 1024 && commonByteArray_PROJETSID_time.length == 0) {
						commonByteArray_PROJETSID_time = new byte[1024];
					} else {
						commonByteArray_PROJETSID_time = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_PROJETSID_time, 0, length);
				strReturn = new String(commonByteArray_PROJETSID_time, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_PROJETSID_time) {

				try {

					int length = 0;

					this.date = readDate(dis);

					this.dayWeekName = readString(dis);

					this.monthId = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_PROJETSID_time) {

				try {

					int length = 0;

					this.date = readDate(dis);

					this.dayWeekName = readString(dis);

					this.monthId = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// java.util.Date

				writeDate(this.date, dos);

				// String

				writeString(this.dayWeekName, dos);

				// String

				writeString(this.monthId, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// java.util.Date

				writeDate(this.date, dos);

				// String

				writeString(this.dayWeekName, dos);

				// String

				writeString(this.monthId, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("date=" + String.valueOf(date));
			sb.append(",dayWeekName=" + dayWeekName);
			sb.append(",monthId=" + monthId);
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(dateStruct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.date, other.date);
			if (returnValue != 0) {
				return returnValue;
			}

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class monthMapStruct implements routines.system.IPersistableRow<monthMapStruct> {
		final static byte[] commonByteArrayLock_PROJETSID_time = new byte[0];
		static byte[] commonByteArray_PROJETSID_time = new byte[0];
		protected static final int DEFAULT_HASHCODE = 1;
		protected static final int PRIME = 31;
		protected int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		public String loopKey;

		public String id;

		public String getId() {
			return this.id;
		}

		public Integer monthNb;

		public Integer getMonthNb() {
			return this.monthNb;
		}

		public String monthName;

		public String getMonthName() {
			return this.monthName;
		}

		public String quarterId;

		public String getQuarterId() {
			return this.quarterId;
		}

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());

				this.hashCode = result;
				this.hashCodeDirty = false;
			}
			return this.hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final monthMapStruct other = (monthMapStruct) obj;

			if (this.id == null) {
				if (other.id != null)
					return false;

			} else if (!this.id.equals(other.id))

				return false;

			return true;
		}

		public void copyDataTo(monthMapStruct other) {

			other.id = this.id;
			other.monthNb = this.monthNb;
			other.monthName = this.monthName;
			other.quarterId = this.quarterId;

		}

		public void copyKeysDataTo(monthMapStruct other) {

			other.id = this.id;

		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJETSID_time.length) {
					if (length < 1024 && commonByteArray_PROJETSID_time.length == 0) {
						commonByteArray_PROJETSID_time = new byte[1024];
					} else {
						commonByteArray_PROJETSID_time = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_PROJETSID_time, 0, length);
				strReturn = new String(commonByteArray_PROJETSID_time, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJETSID_time.length) {
					if (length < 1024 && commonByteArray_PROJETSID_time.length == 0) {
						commonByteArray_PROJETSID_time = new byte[1024];
					} else {
						commonByteArray_PROJETSID_time = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_PROJETSID_time, 0, length);
				strReturn = new String(commonByteArray_PROJETSID_time, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_PROJETSID_time) {

				try {

					int length = 0;

					this.id = readString(dis);

					this.monthNb = readInteger(dis);

					this.monthName = readString(dis);

					this.quarterId = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_PROJETSID_time) {

				try {

					int length = 0;

					this.id = readString(dis);

					this.monthNb = readInteger(dis);

					this.monthName = readString(dis);

					this.quarterId = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// String

				writeString(this.id, dos);

				// Integer

				writeInteger(this.monthNb, dos);

				// String

				writeString(this.monthName, dos);

				// String

				writeString(this.quarterId, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// String

				writeString(this.id, dos);

				// Integer

				writeInteger(this.monthNb, dos);

				// String

				writeString(this.monthName, dos);

				// String

				writeString(this.quarterId, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("id=" + id);
			sb.append(",monthNb=" + String.valueOf(monthNb));
			sb.append(",monthName=" + monthName);
			sb.append(",quarterId=" + quarterId);
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(monthMapStruct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.id, other.id);
			if (returnValue != 0) {
				return returnValue;
			}

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class quarterStruct implements routines.system.IPersistableRow<quarterStruct> {
		final static byte[] commonByteArrayLock_PROJETSID_time = new byte[0];
		static byte[] commonByteArray_PROJETSID_time = new byte[0];
		protected static final int DEFAULT_HASHCODE = 1;
		protected static final int PRIME = 31;
		protected int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		public String loopKey;

		public String id;

		public String getId() {
			return this.id;
		}

		public String quarterName;

		public String getQuarterName() {
			return this.quarterName;
		}

		public Integer year;

		public Integer getYear() {
			return this.year;
		}

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());

				this.hashCode = result;
				this.hashCodeDirty = false;
			}
			return this.hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final quarterStruct other = (quarterStruct) obj;

			if (this.id == null) {
				if (other.id != null)
					return false;

			} else if (!this.id.equals(other.id))

				return false;

			return true;
		}

		public void copyDataTo(quarterStruct other) {

			other.id = this.id;
			other.quarterName = this.quarterName;
			other.year = this.year;

		}

		public void copyKeysDataTo(quarterStruct other) {

			other.id = this.id;

		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJETSID_time.length) {
					if (length < 1024 && commonByteArray_PROJETSID_time.length == 0) {
						commonByteArray_PROJETSID_time = new byte[1024];
					} else {
						commonByteArray_PROJETSID_time = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_PROJETSID_time, 0, length);
				strReturn = new String(commonByteArray_PROJETSID_time, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJETSID_time.length) {
					if (length < 1024 && commonByteArray_PROJETSID_time.length == 0) {
						commonByteArray_PROJETSID_time = new byte[1024];
					} else {
						commonByteArray_PROJETSID_time = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_PROJETSID_time, 0, length);
				strReturn = new String(commonByteArray_PROJETSID_time, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_PROJETSID_time) {

				try {

					int length = 0;

					this.id = readString(dis);

					this.quarterName = readString(dis);

					this.year = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_PROJETSID_time) {

				try {

					int length = 0;

					this.id = readString(dis);

					this.quarterName = readString(dis);

					this.year = readInteger(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// String

				writeString(this.id, dos);

				// String

				writeString(this.quarterName, dos);

				// Integer

				writeInteger(this.year, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// String

				writeString(this.id, dos);

				// String

				writeString(this.quarterName, dos);

				// Integer

				writeInteger(this.year, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("id=" + id);
			sb.append(",quarterName=" + quarterName);
			sb.append(",year=" + String.valueOf(year));
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(quarterStruct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.id, other.id);
			if (returnValue != 0) {
				return returnValue;
			}

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class row5Struct implements routines.system.IPersistableRow<row5Struct> {
		final static byte[] commonByteArrayLock_PROJETSID_time = new byte[0];
		static byte[] commonByteArray_PROJETSID_time = new byte[0];

		public String date;

		public String getDate() {
			return this.date;
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJETSID_time.length) {
					if (length < 1024 && commonByteArray_PROJETSID_time.length == 0) {
						commonByteArray_PROJETSID_time = new byte[1024];
					} else {
						commonByteArray_PROJETSID_time = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_PROJETSID_time, 0, length);
				strReturn = new String(commonByteArray_PROJETSID_time, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJETSID_time.length) {
					if (length < 1024 && commonByteArray_PROJETSID_time.length == 0) {
						commonByteArray_PROJETSID_time = new byte[1024];
					} else {
						commonByteArray_PROJETSID_time = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_PROJETSID_time, 0, length);
				strReturn = new String(commonByteArray_PROJETSID_time, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_PROJETSID_time) {

				try {

					int length = 0;

					this.date = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_PROJETSID_time) {

				try {

					int length = 0;

					this.date = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// String

				writeString(this.date, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// String

				writeString(this.date, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("date=" + date);
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row5Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class row4Struct implements routines.system.IPersistableRow<row4Struct> {
		final static byte[] commonByteArrayLock_PROJETSID_time = new byte[0];
		static byte[] commonByteArray_PROJETSID_time = new byte[0];

		public String date;

		public String getDate() {
			return this.date;
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJETSID_time.length) {
					if (length < 1024 && commonByteArray_PROJETSID_time.length == 0) {
						commonByteArray_PROJETSID_time = new byte[1024];
					} else {
						commonByteArray_PROJETSID_time = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_PROJETSID_time, 0, length);
				strReturn = new String(commonByteArray_PROJETSID_time, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJETSID_time.length) {
					if (length < 1024 && commonByteArray_PROJETSID_time.length == 0) {
						commonByteArray_PROJETSID_time = new byte[1024];
					} else {
						commonByteArray_PROJETSID_time = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_PROJETSID_time, 0, length);
				strReturn = new String(commonByteArray_PROJETSID_time, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_PROJETSID_time) {

				try {

					int length = 0;

					this.date = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_PROJETSID_time) {

				try {

					int length = 0;

					this.date = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// String

				writeString(this.date, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// String

				writeString(this.date, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("date=" + date);
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row4Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class row3Struct implements routines.system.IPersistableRow<row3Struct> {
		final static byte[] commonByteArrayLock_PROJETSID_time = new byte[0];
		static byte[] commonByteArray_PROJETSID_time = new byte[0];

		public String Order_Date;

		public String getOrder_Date() {
			return this.Order_Date;
		}

		public String Delivery_Date;

		public String getDelivery_Date() {
			return this.Delivery_Date;
		}

		public String Emp_Hire_Date;

		public String getEmp_Hire_Date() {
			return this.Emp_Hire_Date;
		}

		public String Birth_Date;

		public String getBirth_Date() {
			return this.Birth_Date;
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJETSID_time.length) {
					if (length < 1024 && commonByteArray_PROJETSID_time.length == 0) {
						commonByteArray_PROJETSID_time = new byte[1024];
					} else {
						commonByteArray_PROJETSID_time = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_PROJETSID_time, 0, length);
				strReturn = new String(commonByteArray_PROJETSID_time, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJETSID_time.length) {
					if (length < 1024 && commonByteArray_PROJETSID_time.length == 0) {
						commonByteArray_PROJETSID_time = new byte[1024];
					} else {
						commonByteArray_PROJETSID_time = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_PROJETSID_time, 0, length);
				strReturn = new String(commonByteArray_PROJETSID_time, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_PROJETSID_time) {

				try {

					int length = 0;

					this.Order_Date = readString(dis);

					this.Delivery_Date = readString(dis);

					this.Emp_Hire_Date = readString(dis);

					this.Birth_Date = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_PROJETSID_time) {

				try {

					int length = 0;

					this.Order_Date = readString(dis);

					this.Delivery_Date = readString(dis);

					this.Emp_Hire_Date = readString(dis);

					this.Birth_Date = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// String

				writeString(this.Order_Date, dos);

				// String

				writeString(this.Delivery_Date, dos);

				// String

				writeString(this.Emp_Hire_Date, dos);

				// String

				writeString(this.Birth_Date, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// String

				writeString(this.Order_Date, dos);

				// String

				writeString(this.Delivery_Date, dos);

				// String

				writeString(this.Emp_Hire_Date, dos);

				// String

				writeString(this.Birth_Date, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("Order_Date=" + Order_Date);
			sb.append(",Delivery_Date=" + Delivery_Date);
			sb.append(",Emp_Hire_Date=" + Emp_Hire_Date);
			sb.append(",Birth_Date=" + Birth_Date);
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row3Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class row2Struct implements routines.system.IPersistableRow<row2Struct> {
		final static byte[] commonByteArrayLock_PROJETSID_time = new byte[0];
		static byte[] commonByteArray_PROJETSID_time = new byte[0];

		public String Order_Date;

		public String getOrder_Date() {
			return this.Order_Date;
		}

		public String Delivery_Date;

		public String getDelivery_Date() {
			return this.Delivery_Date;
		}

		public String Emp_Hire_Date;

		public String getEmp_Hire_Date() {
			return this.Emp_Hire_Date;
		}

		public String Birth_Date;

		public String getBirth_Date() {
			return this.Birth_Date;
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJETSID_time.length) {
					if (length < 1024 && commonByteArray_PROJETSID_time.length == 0) {
						commonByteArray_PROJETSID_time = new byte[1024];
					} else {
						commonByteArray_PROJETSID_time = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_PROJETSID_time, 0, length);
				strReturn = new String(commonByteArray_PROJETSID_time, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJETSID_time.length) {
					if (length < 1024 && commonByteArray_PROJETSID_time.length == 0) {
						commonByteArray_PROJETSID_time = new byte[1024];
					} else {
						commonByteArray_PROJETSID_time = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_PROJETSID_time, 0, length);
				strReturn = new String(commonByteArray_PROJETSID_time, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_PROJETSID_time) {

				try {

					int length = 0;

					this.Order_Date = readString(dis);

					this.Delivery_Date = readString(dis);

					this.Emp_Hire_Date = readString(dis);

					this.Birth_Date = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_PROJETSID_time) {

				try {

					int length = 0;

					this.Order_Date = readString(dis);

					this.Delivery_Date = readString(dis);

					this.Emp_Hire_Date = readString(dis);

					this.Birth_Date = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// String

				writeString(this.Order_Date, dos);

				// String

				writeString(this.Delivery_Date, dos);

				// String

				writeString(this.Emp_Hire_Date, dos);

				// String

				writeString(this.Birth_Date, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// String

				writeString(this.Order_Date, dos);

				// String

				writeString(this.Delivery_Date, dos);

				// String

				writeString(this.Emp_Hire_Date, dos);

				// String

				writeString(this.Birth_Date, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("Order_Date=" + Order_Date);
			sb.append(",Delivery_Date=" + Delivery_Date);
			sb.append(",Emp_Hire_Date=" + Emp_Hire_Date);
			sb.append(",Birth_Date=" + Birth_Date);
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row2Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class row1Struct implements routines.system.IPersistableRow<row1Struct> {
		final static byte[] commonByteArrayLock_PROJETSID_time = new byte[0];
		static byte[] commonByteArray_PROJETSID_time = new byte[0];

		public String Customer_Group_ID;

		public String getCustomer_Group_ID() {
			return this.Customer_Group_ID;
		}

		public String Customer_Group;

		public String getCustomer_Group() {
			return this.Customer_Group;
		}

		public String Customer_Type_ID;

		public String getCustomer_Type_ID() {
			return this.Customer_Type_ID;
		}

		public String Customer_Type;

		public String getCustomer_Type() {
			return this.Customer_Type;
		}

		public String Birth_Date;

		public String getBirth_Date() {
			return this.Birth_Date;
		}

		public Character Gender;

		public Character getGender() {
			return this.Gender;
		}

		public String Customer_ID;

		public String getCustomer_ID() {
			return this.Customer_ID;
		}

		public String Country;

		public String getCountry() {
			return this.Country;
		}

		public String Country_Name;

		public String getCountry_Name() {
			return this.Country_Name;
		}

		public String Employee_ID;

		public String getEmployee_ID() {
			return this.Employee_ID;
		}

		public String Employee_Job_Title;

		public String getEmployee_Job_Title() {
			return this.Employee_Job_Title;
		}

		public Integer Employee_Salary;

		public Integer getEmployee_Salary() {
			return this.Employee_Salary;
		}

		public String Employee_Gender;

		public String getEmployee_Gender() {
			return this.Employee_Gender;
		}

		public String Employee_Country;

		public String getEmployee_Country() {
			return this.Employee_Country;
		}

		public String Order_Type;

		public String getOrder_Type() {
			return this.Order_Type;
		}

		public String Order_Date;

		public String getOrder_Date() {
			return this.Order_Date;
		}

		public String Delivery_Date;

		public String getDelivery_Date() {
			return this.Delivery_Date;
		}

		public String Product_ID;

		public String getProduct_ID() {
			return this.Product_ID;
		}

		public String Product_Name;

		public String getProduct_Name() {
			return this.Product_Name;
		}

		public String Product_Ref_ID;

		public String getProduct_Ref_ID() {
			return this.Product_Ref_ID;
		}

		public Integer Quantity;

		public Integer getQuantity() {
			return this.Quantity;
		}

		public String Order_ID;

		public String getOrder_ID() {
			return this.Order_ID;
		}

		public String Order_Item_Num;

		public String getOrder_Item_Num() {
			return this.Order_Item_Num;
		}

		public String CostPrice_Per_Unit;

		public String getCostPrice_Per_Unit() {
			return this.CostPrice_Per_Unit;
		}

		public String Total_Retail_Price;

		public String getTotal_Retail_Price() {
			return this.Total_Retail_Price;
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJETSID_time.length) {
					if (length < 1024 && commonByteArray_PROJETSID_time.length == 0) {
						commonByteArray_PROJETSID_time = new byte[1024];
					} else {
						commonByteArray_PROJETSID_time = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_PROJETSID_time, 0, length);
				strReturn = new String(commonByteArray_PROJETSID_time, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJETSID_time.length) {
					if (length < 1024 && commonByteArray_PROJETSID_time.length == 0) {
						commonByteArray_PROJETSID_time = new byte[1024];
					} else {
						commonByteArray_PROJETSID_time = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_PROJETSID_time, 0, length);
				strReturn = new String(commonByteArray_PROJETSID_time, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_PROJETSID_time) {

				try {

					int length = 0;

					this.Customer_Group_ID = readString(dis);

					this.Customer_Group = readString(dis);

					this.Customer_Type_ID = readString(dis);

					this.Customer_Type = readString(dis);

					this.Birth_Date = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Gender = null;
					} else {
						this.Gender = dis.readChar();
					}

					this.Customer_ID = readString(dis);

					this.Country = readString(dis);

					this.Country_Name = readString(dis);

					this.Employee_ID = readString(dis);

					this.Employee_Job_Title = readString(dis);

					this.Employee_Salary = readInteger(dis);

					this.Employee_Gender = readString(dis);

					this.Employee_Country = readString(dis);

					this.Order_Type = readString(dis);

					this.Order_Date = readString(dis);

					this.Delivery_Date = readString(dis);

					this.Product_ID = readString(dis);

					this.Product_Name = readString(dis);

					this.Product_Ref_ID = readString(dis);

					this.Quantity = readInteger(dis);

					this.Order_ID = readString(dis);

					this.Order_Item_Num = readString(dis);

					this.CostPrice_Per_Unit = readString(dis);

					this.Total_Retail_Price = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_PROJETSID_time) {

				try {

					int length = 0;

					this.Customer_Group_ID = readString(dis);

					this.Customer_Group = readString(dis);

					this.Customer_Type_ID = readString(dis);

					this.Customer_Type = readString(dis);

					this.Birth_Date = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.Gender = null;
					} else {
						this.Gender = dis.readChar();
					}

					this.Customer_ID = readString(dis);

					this.Country = readString(dis);

					this.Country_Name = readString(dis);

					this.Employee_ID = readString(dis);

					this.Employee_Job_Title = readString(dis);

					this.Employee_Salary = readInteger(dis);

					this.Employee_Gender = readString(dis);

					this.Employee_Country = readString(dis);

					this.Order_Type = readString(dis);

					this.Order_Date = readString(dis);

					this.Delivery_Date = readString(dis);

					this.Product_ID = readString(dis);

					this.Product_Name = readString(dis);

					this.Product_Ref_ID = readString(dis);

					this.Quantity = readInteger(dis);

					this.Order_ID = readString(dis);

					this.Order_Item_Num = readString(dis);

					this.CostPrice_Per_Unit = readString(dis);

					this.Total_Retail_Price = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// String

				writeString(this.Customer_Group_ID, dos);

				// String

				writeString(this.Customer_Group, dos);

				// String

				writeString(this.Customer_Type_ID, dos);

				// String

				writeString(this.Customer_Type, dos);

				// String

				writeString(this.Birth_Date, dos);

				// Character

				if (this.Gender == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeChar(this.Gender);
				}

				// String

				writeString(this.Customer_ID, dos);

				// String

				writeString(this.Country, dos);

				// String

				writeString(this.Country_Name, dos);

				// String

				writeString(this.Employee_ID, dos);

				// String

				writeString(this.Employee_Job_Title, dos);

				// Integer

				writeInteger(this.Employee_Salary, dos);

				// String

				writeString(this.Employee_Gender, dos);

				// String

				writeString(this.Employee_Country, dos);

				// String

				writeString(this.Order_Type, dos);

				// String

				writeString(this.Order_Date, dos);

				// String

				writeString(this.Delivery_Date, dos);

				// String

				writeString(this.Product_ID, dos);

				// String

				writeString(this.Product_Name, dos);

				// String

				writeString(this.Product_Ref_ID, dos);

				// Integer

				writeInteger(this.Quantity, dos);

				// String

				writeString(this.Order_ID, dos);

				// String

				writeString(this.Order_Item_Num, dos);

				// String

				writeString(this.CostPrice_Per_Unit, dos);

				// String

				writeString(this.Total_Retail_Price, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// String

				writeString(this.Customer_Group_ID, dos);

				// String

				writeString(this.Customer_Group, dos);

				// String

				writeString(this.Customer_Type_ID, dos);

				// String

				writeString(this.Customer_Type, dos);

				// String

				writeString(this.Birth_Date, dos);

				// Character

				if (this.Gender == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeChar(this.Gender);
				}

				// String

				writeString(this.Customer_ID, dos);

				// String

				writeString(this.Country, dos);

				// String

				writeString(this.Country_Name, dos);

				// String

				writeString(this.Employee_ID, dos);

				// String

				writeString(this.Employee_Job_Title, dos);

				// Integer

				writeInteger(this.Employee_Salary, dos);

				// String

				writeString(this.Employee_Gender, dos);

				// String

				writeString(this.Employee_Country, dos);

				// String

				writeString(this.Order_Type, dos);

				// String

				writeString(this.Order_Date, dos);

				// String

				writeString(this.Delivery_Date, dos);

				// String

				writeString(this.Product_ID, dos);

				// String

				writeString(this.Product_Name, dos);

				// String

				writeString(this.Product_Ref_ID, dos);

				// Integer

				writeInteger(this.Quantity, dos);

				// String

				writeString(this.Order_ID, dos);

				// String

				writeString(this.Order_Item_Num, dos);

				// String

				writeString(this.CostPrice_Per_Unit, dos);

				// String

				writeString(this.Total_Retail_Price, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("Customer_Group_ID=" + Customer_Group_ID);
			sb.append(",Customer_Group=" + Customer_Group);
			sb.append(",Customer_Type_ID=" + Customer_Type_ID);
			sb.append(",Customer_Type=" + Customer_Type);
			sb.append(",Birth_Date=" + Birth_Date);
			sb.append(",Gender=" + String.valueOf(Gender));
			sb.append(",Customer_ID=" + Customer_ID);
			sb.append(",Country=" + Country);
			sb.append(",Country_Name=" + Country_Name);
			sb.append(",Employee_ID=" + Employee_ID);
			sb.append(",Employee_Job_Title=" + Employee_Job_Title);
			sb.append(",Employee_Salary=" + String.valueOf(Employee_Salary));
			sb.append(",Employee_Gender=" + Employee_Gender);
			sb.append(",Employee_Country=" + Employee_Country);
			sb.append(",Order_Type=" + Order_Type);
			sb.append(",Order_Date=" + Order_Date);
			sb.append(",Delivery_Date=" + Delivery_Date);
			sb.append(",Product_ID=" + Product_ID);
			sb.append(",Product_Name=" + Product_Name);
			sb.append(",Product_Ref_ID=" + Product_Ref_ID);
			sb.append(",Quantity=" + String.valueOf(Quantity));
			sb.append(",Order_ID=" + Order_ID);
			sb.append(",Order_Item_Num=" + Order_Item_Num);
			sb.append(",CostPrice_Per_Unit=" + CostPrice_Per_Unit);
			sb.append(",Total_Retail_Price=" + Total_Retail_Price);
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row1Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public void tFileInputDelimited_1Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tFileInputDelimited_1_SUBPROCESS_STATE", 0);

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

				row1Struct row1 = new row1Struct();
				row2Struct row2 = new row2Struct();
				row2Struct row3 = row2;
				row4Struct row4 = new row4Struct();
				row5Struct row5 = new row5Struct();
				dateStruct date = new dateStruct();
				dateStruct row6 = date;
				row9Struct row9 = new row9Struct();
				monthMapStruct monthMap = new monthMapStruct();
				monthMapStruct row8 = monthMap;
				row10Struct row10 = new row10Struct();
				quarterStruct quarter = new quarterStruct();
				quarterStruct row7 = quarter;
				row11Struct row11 = new row11Struct();

				/**
				 * [tFileOutputDelimited_1 begin ] start
				 */

				ok_Hash.put("tFileOutputDelimited_1", false);
				start_Hash.put("tFileOutputDelimited_1", System.currentTimeMillis());

				currentComponent = "tFileOutputDelimited_1";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "row9");
				}

				int tos_count_tFileOutputDelimited_1 = 0;

				String fileName_tFileOutputDelimited_1 = "";
				fileName_tFileOutputDelimited_1 = (new java.io.File(
						context.folderName + "/files/TablesToDump/Date.csv")).getAbsolutePath().replace("\\", "/");
				String fullName_tFileOutputDelimited_1 = null;
				String extension_tFileOutputDelimited_1 = null;
				String directory_tFileOutputDelimited_1 = null;
				if ((fileName_tFileOutputDelimited_1.indexOf("/") != -1)) {
					if (fileName_tFileOutputDelimited_1.lastIndexOf(".") < fileName_tFileOutputDelimited_1
							.lastIndexOf("/")) {
						fullName_tFileOutputDelimited_1 = fileName_tFileOutputDelimited_1;
						extension_tFileOutputDelimited_1 = "";
					} else {
						fullName_tFileOutputDelimited_1 = fileName_tFileOutputDelimited_1.substring(0,
								fileName_tFileOutputDelimited_1.lastIndexOf("."));
						extension_tFileOutputDelimited_1 = fileName_tFileOutputDelimited_1
								.substring(fileName_tFileOutputDelimited_1.lastIndexOf("."));
					}
					directory_tFileOutputDelimited_1 = fileName_tFileOutputDelimited_1.substring(0,
							fileName_tFileOutputDelimited_1.lastIndexOf("/"));
				} else {
					if (fileName_tFileOutputDelimited_1.lastIndexOf(".") != -1) {
						fullName_tFileOutputDelimited_1 = fileName_tFileOutputDelimited_1.substring(0,
								fileName_tFileOutputDelimited_1.lastIndexOf("."));
						extension_tFileOutputDelimited_1 = fileName_tFileOutputDelimited_1
								.substring(fileName_tFileOutputDelimited_1.lastIndexOf("."));
					} else {
						fullName_tFileOutputDelimited_1 = fileName_tFileOutputDelimited_1;
						extension_tFileOutputDelimited_1 = "";
					}
					directory_tFileOutputDelimited_1 = "";
				}
				boolean isFileGenerated_tFileOutputDelimited_1 = true;
				java.io.File filetFileOutputDelimited_1 = new java.io.File(fileName_tFileOutputDelimited_1);
				globalMap.put("tFileOutputDelimited_1_FILE_NAME", fileName_tFileOutputDelimited_1);
				int nb_line_tFileOutputDelimited_1 = 0;
				int splitedFileNo_tFileOutputDelimited_1 = 0;
				int currentRow_tFileOutputDelimited_1 = 0;

				final String OUT_DELIM_tFileOutputDelimited_1 = /** Start field tFileOutputDelimited_1:FIELDSEPARATOR */
						";"/** End field tFileOutputDelimited_1:FIELDSEPARATOR */
				;

				final String OUT_DELIM_ROWSEP_tFileOutputDelimited_1 = /**
																		 * Start field
																		 * tFileOutputDelimited_1:ROWSEPARATOR
																		 */
						"\n"/** End field tFileOutputDelimited_1:ROWSEPARATOR */
				;

				// create directory only if not exists
				if (directory_tFileOutputDelimited_1 != null && directory_tFileOutputDelimited_1.trim().length() != 0) {
					java.io.File dir_tFileOutputDelimited_1 = new java.io.File(directory_tFileOutputDelimited_1);
					if (!dir_tFileOutputDelimited_1.exists()) {
						dir_tFileOutputDelimited_1.mkdirs();
					}
				}

				// routines.system.Row
				java.io.Writer outtFileOutputDelimited_1 = null;

				java.io.File fileToDelete_tFileOutputDelimited_1 = new java.io.File(fileName_tFileOutputDelimited_1);
				if (fileToDelete_tFileOutputDelimited_1.exists()) {
					fileToDelete_tFileOutputDelimited_1.delete();
				}
				outtFileOutputDelimited_1 = new java.io.BufferedWriter(new java.io.OutputStreamWriter(
						new java.io.FileOutputStream(fileName_tFileOutputDelimited_1, false), "UTF-8"));
				if (filetFileOutputDelimited_1.length() == 0) {
					outtFileOutputDelimited_1.write("date");
					outtFileOutputDelimited_1.write(OUT_DELIM_tFileOutputDelimited_1);
					outtFileOutputDelimited_1.write("dayWeekName");
					outtFileOutputDelimited_1.write(OUT_DELIM_tFileOutputDelimited_1);
					outtFileOutputDelimited_1.write("monthId");
					outtFileOutputDelimited_1.write(OUT_DELIM_ROWSEP_tFileOutputDelimited_1);
					outtFileOutputDelimited_1.flush();
				}

				resourceMap.put("out_tFileOutputDelimited_1", outtFileOutputDelimited_1);
				resourceMap.put("nb_line_tFileOutputDelimited_1", nb_line_tFileOutputDelimited_1);

				/**
				 * [tFileOutputDelimited_1 begin ] stop
				 */

				/**
				 * [tUniqRow_2 begin ] start
				 */

				ok_Hash.put("tUniqRow_2", false);
				start_Hash.put("tUniqRow_2", System.currentTimeMillis());

				currentComponent = "tUniqRow_2";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "row6");
				}

				int tos_count_tUniqRow_2 = 0;

				class KeyStruct_tUniqRow_2 {

					private static final int DEFAULT_HASHCODE = 1;
					private static final int PRIME = 31;
					private int hashCode = DEFAULT_HASHCODE;
					public boolean hashCodeDirty = true;

					java.util.Date date;
					String dayWeekName;
					String monthId;

					@Override
					public int hashCode() {
						if (this.hashCodeDirty) {
							final int prime = PRIME;
							int result = DEFAULT_HASHCODE;

							result = prime * result + ((this.date == null) ? 0 : this.date.hashCode());

							result = prime * result + ((this.dayWeekName == null) ? 0 : this.dayWeekName.hashCode());

							result = prime * result + ((this.monthId == null) ? 0 : this.monthId.hashCode());

							this.hashCode = result;
							this.hashCodeDirty = false;
						}
						return this.hashCode;
					}

					@Override
					public boolean equals(Object obj) {
						if (this == obj)
							return true;
						if (obj == null)
							return false;
						if (getClass() != obj.getClass())
							return false;
						final KeyStruct_tUniqRow_2 other = (KeyStruct_tUniqRow_2) obj;

						if (this.date == null) {
							if (other.date != null)
								return false;

						} else if (!this.date.equals(other.date))

							return false;

						if (this.dayWeekName == null) {
							if (other.dayWeekName != null)
								return false;

						} else if (!this.dayWeekName.equals(other.dayWeekName))

							return false;

						if (this.monthId == null) {
							if (other.monthId != null)
								return false;

						} else if (!this.monthId.equals(other.monthId))

							return false;

						return true;
					}

				}

				int nb_uniques_tUniqRow_2 = 0;
				int nb_duplicates_tUniqRow_2 = 0;
				KeyStruct_tUniqRow_2 finder_tUniqRow_2 = new KeyStruct_tUniqRow_2();
				java.util.Set<KeyStruct_tUniqRow_2> keystUniqRow_2 = new java.util.HashSet<KeyStruct_tUniqRow_2>();

				/**
				 * [tUniqRow_2 begin ] stop
				 */

				/**
				 * [tLogRow_2 begin ] start
				 */

				ok_Hash.put("tLogRow_2", false);
				start_Hash.put("tLogRow_2", System.currentTimeMillis());

				currentComponent = "tLogRow_2";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "date");
				}

				int tos_count_tLogRow_2 = 0;

				/**
				 * [tLogRow_2 begin ] stop
				 */

				/**
				 * [tFileOutputDelimited_2 begin ] start
				 */

				ok_Hash.put("tFileOutputDelimited_2", false);
				start_Hash.put("tFileOutputDelimited_2", System.currentTimeMillis());

				currentComponent = "tFileOutputDelimited_2";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "row10");
				}

				int tos_count_tFileOutputDelimited_2 = 0;

				String fileName_tFileOutputDelimited_2 = "";
				fileName_tFileOutputDelimited_2 = (new java.io.File(
						context.folderName + "/files/TablesToDump/Month.csv")).getAbsolutePath().replace("\\", "/");
				String fullName_tFileOutputDelimited_2 = null;
				String extension_tFileOutputDelimited_2 = null;
				String directory_tFileOutputDelimited_2 = null;
				if ((fileName_tFileOutputDelimited_2.indexOf("/") != -1)) {
					if (fileName_tFileOutputDelimited_2.lastIndexOf(".") < fileName_tFileOutputDelimited_2
							.lastIndexOf("/")) {
						fullName_tFileOutputDelimited_2 = fileName_tFileOutputDelimited_2;
						extension_tFileOutputDelimited_2 = "";
					} else {
						fullName_tFileOutputDelimited_2 = fileName_tFileOutputDelimited_2.substring(0,
								fileName_tFileOutputDelimited_2.lastIndexOf("."));
						extension_tFileOutputDelimited_2 = fileName_tFileOutputDelimited_2
								.substring(fileName_tFileOutputDelimited_2.lastIndexOf("."));
					}
					directory_tFileOutputDelimited_2 = fileName_tFileOutputDelimited_2.substring(0,
							fileName_tFileOutputDelimited_2.lastIndexOf("/"));
				} else {
					if (fileName_tFileOutputDelimited_2.lastIndexOf(".") != -1) {
						fullName_tFileOutputDelimited_2 = fileName_tFileOutputDelimited_2.substring(0,
								fileName_tFileOutputDelimited_2.lastIndexOf("."));
						extension_tFileOutputDelimited_2 = fileName_tFileOutputDelimited_2
								.substring(fileName_tFileOutputDelimited_2.lastIndexOf("."));
					} else {
						fullName_tFileOutputDelimited_2 = fileName_tFileOutputDelimited_2;
						extension_tFileOutputDelimited_2 = "";
					}
					directory_tFileOutputDelimited_2 = "";
				}
				boolean isFileGenerated_tFileOutputDelimited_2 = true;
				java.io.File filetFileOutputDelimited_2 = new java.io.File(fileName_tFileOutputDelimited_2);
				globalMap.put("tFileOutputDelimited_2_FILE_NAME", fileName_tFileOutputDelimited_2);
				int nb_line_tFileOutputDelimited_2 = 0;
				int splitedFileNo_tFileOutputDelimited_2 = 0;
				int currentRow_tFileOutputDelimited_2 = 0;

				final String OUT_DELIM_tFileOutputDelimited_2 = /** Start field tFileOutputDelimited_2:FIELDSEPARATOR */
						";"/** End field tFileOutputDelimited_2:FIELDSEPARATOR */
				;

				final String OUT_DELIM_ROWSEP_tFileOutputDelimited_2 = /**
																		 * Start field
																		 * tFileOutputDelimited_2:ROWSEPARATOR
																		 */
						"\n"/** End field tFileOutputDelimited_2:ROWSEPARATOR */
				;

				// create directory only if not exists
				if (directory_tFileOutputDelimited_2 != null && directory_tFileOutputDelimited_2.trim().length() != 0) {
					java.io.File dir_tFileOutputDelimited_2 = new java.io.File(directory_tFileOutputDelimited_2);
					if (!dir_tFileOutputDelimited_2.exists()) {
						dir_tFileOutputDelimited_2.mkdirs();
					}
				}

				// routines.system.Row
				java.io.Writer outtFileOutputDelimited_2 = null;

				java.io.File fileToDelete_tFileOutputDelimited_2 = new java.io.File(fileName_tFileOutputDelimited_2);
				if (fileToDelete_tFileOutputDelimited_2.exists()) {
					fileToDelete_tFileOutputDelimited_2.delete();
				}
				outtFileOutputDelimited_2 = new java.io.BufferedWriter(new java.io.OutputStreamWriter(
						new java.io.FileOutputStream(fileName_tFileOutputDelimited_2, false), "UTF-8"));
				if (filetFileOutputDelimited_2.length() == 0) {
					outtFileOutputDelimited_2.write("id");
					outtFileOutputDelimited_2.write(OUT_DELIM_tFileOutputDelimited_2);
					outtFileOutputDelimited_2.write("monthNb");
					outtFileOutputDelimited_2.write(OUT_DELIM_tFileOutputDelimited_2);
					outtFileOutputDelimited_2.write("monthName");
					outtFileOutputDelimited_2.write(OUT_DELIM_tFileOutputDelimited_2);
					outtFileOutputDelimited_2.write("quarterId");
					outtFileOutputDelimited_2.write(OUT_DELIM_ROWSEP_tFileOutputDelimited_2);
					outtFileOutputDelimited_2.flush();
				}

				resourceMap.put("out_tFileOutputDelimited_2", outtFileOutputDelimited_2);
				resourceMap.put("nb_line_tFileOutputDelimited_2", nb_line_tFileOutputDelimited_2);

				/**
				 * [tFileOutputDelimited_2 begin ] stop
				 */

				/**
				 * [tUniqRow_3 begin ] start
				 */

				ok_Hash.put("tUniqRow_3", false);
				start_Hash.put("tUniqRow_3", System.currentTimeMillis());

				currentComponent = "tUniqRow_3";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "row8");
				}

				int tos_count_tUniqRow_3 = 0;

				class KeyStruct_tUniqRow_3 {

					private static final int DEFAULT_HASHCODE = 1;
					private static final int PRIME = 31;
					private int hashCode = DEFAULT_HASHCODE;
					public boolean hashCodeDirty = true;

					String id;
					Integer monthNb;
					String monthName;
					String quarterId;

					@Override
					public int hashCode() {
						if (this.hashCodeDirty) {
							final int prime = PRIME;
							int result = DEFAULT_HASHCODE;

							result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());

							result = prime * result + ((this.monthNb == null) ? 0 : this.monthNb.hashCode());

							result = prime * result + ((this.monthName == null) ? 0 : this.monthName.hashCode());

							result = prime * result + ((this.quarterId == null) ? 0 : this.quarterId.hashCode());

							this.hashCode = result;
							this.hashCodeDirty = false;
						}
						return this.hashCode;
					}

					@Override
					public boolean equals(Object obj) {
						if (this == obj)
							return true;
						if (obj == null)
							return false;
						if (getClass() != obj.getClass())
							return false;
						final KeyStruct_tUniqRow_3 other = (KeyStruct_tUniqRow_3) obj;

						if (this.id == null) {
							if (other.id != null)
								return false;

						} else if (!this.id.equals(other.id))

							return false;

						if (this.monthNb == null) {
							if (other.monthNb != null)
								return false;

						} else if (!this.monthNb.equals(other.monthNb))

							return false;

						if (this.monthName == null) {
							if (other.monthName != null)
								return false;

						} else if (!this.monthName.equals(other.monthName))

							return false;

						if (this.quarterId == null) {
							if (other.quarterId != null)
								return false;

						} else if (!this.quarterId.equals(other.quarterId))

							return false;

						return true;
					}

				}

				int nb_uniques_tUniqRow_3 = 0;
				int nb_duplicates_tUniqRow_3 = 0;
				KeyStruct_tUniqRow_3 finder_tUniqRow_3 = new KeyStruct_tUniqRow_3();
				java.util.Set<KeyStruct_tUniqRow_3> keystUniqRow_3 = new java.util.HashSet<KeyStruct_tUniqRow_3>();

				/**
				 * [tUniqRow_3 begin ] stop
				 */

				/**
				 * [tLogRow_3 begin ] start
				 */

				ok_Hash.put("tLogRow_3", false);
				start_Hash.put("tLogRow_3", System.currentTimeMillis());

				currentComponent = "tLogRow_3";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "monthMap");
				}

				int tos_count_tLogRow_3 = 0;

				/**
				 * [tLogRow_3 begin ] stop
				 */

				/**
				 * [tFileOutputDelimited_3 begin ] start
				 */

				ok_Hash.put("tFileOutputDelimited_3", false);
				start_Hash.put("tFileOutputDelimited_3", System.currentTimeMillis());

				currentComponent = "tFileOutputDelimited_3";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "row11");
				}

				int tos_count_tFileOutputDelimited_3 = 0;

				String fileName_tFileOutputDelimited_3 = "";
				fileName_tFileOutputDelimited_3 = (new java.io.File(
						context.folderName + "/files/TablesToDump/Quarter.csv")).getAbsolutePath().replace("\\", "/");
				String fullName_tFileOutputDelimited_3 = null;
				String extension_tFileOutputDelimited_3 = null;
				String directory_tFileOutputDelimited_3 = null;
				if ((fileName_tFileOutputDelimited_3.indexOf("/") != -1)) {
					if (fileName_tFileOutputDelimited_3.lastIndexOf(".") < fileName_tFileOutputDelimited_3
							.lastIndexOf("/")) {
						fullName_tFileOutputDelimited_3 = fileName_tFileOutputDelimited_3;
						extension_tFileOutputDelimited_3 = "";
					} else {
						fullName_tFileOutputDelimited_3 = fileName_tFileOutputDelimited_3.substring(0,
								fileName_tFileOutputDelimited_3.lastIndexOf("."));
						extension_tFileOutputDelimited_3 = fileName_tFileOutputDelimited_3
								.substring(fileName_tFileOutputDelimited_3.lastIndexOf("."));
					}
					directory_tFileOutputDelimited_3 = fileName_tFileOutputDelimited_3.substring(0,
							fileName_tFileOutputDelimited_3.lastIndexOf("/"));
				} else {
					if (fileName_tFileOutputDelimited_3.lastIndexOf(".") != -1) {
						fullName_tFileOutputDelimited_3 = fileName_tFileOutputDelimited_3.substring(0,
								fileName_tFileOutputDelimited_3.lastIndexOf("."));
						extension_tFileOutputDelimited_3 = fileName_tFileOutputDelimited_3
								.substring(fileName_tFileOutputDelimited_3.lastIndexOf("."));
					} else {
						fullName_tFileOutputDelimited_3 = fileName_tFileOutputDelimited_3;
						extension_tFileOutputDelimited_3 = "";
					}
					directory_tFileOutputDelimited_3 = "";
				}
				boolean isFileGenerated_tFileOutputDelimited_3 = true;
				java.io.File filetFileOutputDelimited_3 = new java.io.File(fileName_tFileOutputDelimited_3);
				globalMap.put("tFileOutputDelimited_3_FILE_NAME", fileName_tFileOutputDelimited_3);
				int nb_line_tFileOutputDelimited_3 = 0;
				int splitedFileNo_tFileOutputDelimited_3 = 0;
				int currentRow_tFileOutputDelimited_3 = 0;

				final String OUT_DELIM_tFileOutputDelimited_3 = /** Start field tFileOutputDelimited_3:FIELDSEPARATOR */
						";"/** End field tFileOutputDelimited_3:FIELDSEPARATOR */
				;

				final String OUT_DELIM_ROWSEP_tFileOutputDelimited_3 = /**
																		 * Start field
																		 * tFileOutputDelimited_3:ROWSEPARATOR
																		 */
						"\n"/** End field tFileOutputDelimited_3:ROWSEPARATOR */
				;

				// create directory only if not exists
				if (directory_tFileOutputDelimited_3 != null && directory_tFileOutputDelimited_3.trim().length() != 0) {
					java.io.File dir_tFileOutputDelimited_3 = new java.io.File(directory_tFileOutputDelimited_3);
					if (!dir_tFileOutputDelimited_3.exists()) {
						dir_tFileOutputDelimited_3.mkdirs();
					}
				}

				// routines.system.Row
				java.io.Writer outtFileOutputDelimited_3 = null;

				java.io.File fileToDelete_tFileOutputDelimited_3 = new java.io.File(fileName_tFileOutputDelimited_3);
				if (fileToDelete_tFileOutputDelimited_3.exists()) {
					fileToDelete_tFileOutputDelimited_3.delete();
				}
				outtFileOutputDelimited_3 = new java.io.BufferedWriter(new java.io.OutputStreamWriter(
						new java.io.FileOutputStream(fileName_tFileOutputDelimited_3, false), "UTF-8"));
				if (filetFileOutputDelimited_3.length() == 0) {
					outtFileOutputDelimited_3.write("id");
					outtFileOutputDelimited_3.write(OUT_DELIM_tFileOutputDelimited_3);
					outtFileOutputDelimited_3.write("quarterName");
					outtFileOutputDelimited_3.write(OUT_DELIM_tFileOutputDelimited_3);
					outtFileOutputDelimited_3.write("year");
					outtFileOutputDelimited_3.write(OUT_DELIM_ROWSEP_tFileOutputDelimited_3);
					outtFileOutputDelimited_3.flush();
				}

				resourceMap.put("out_tFileOutputDelimited_3", outtFileOutputDelimited_3);
				resourceMap.put("nb_line_tFileOutputDelimited_3", nb_line_tFileOutputDelimited_3);

				/**
				 * [tFileOutputDelimited_3 begin ] stop
				 */

				/**
				 * [tUniqRow_4 begin ] start
				 */

				ok_Hash.put("tUniqRow_4", false);
				start_Hash.put("tUniqRow_4", System.currentTimeMillis());

				currentComponent = "tUniqRow_4";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "row7");
				}

				int tos_count_tUniqRow_4 = 0;

				class KeyStruct_tUniqRow_4 {

					private static final int DEFAULT_HASHCODE = 1;
					private static final int PRIME = 31;
					private int hashCode = DEFAULT_HASHCODE;
					public boolean hashCodeDirty = true;

					String id;
					String quarterName;
					Integer year;

					@Override
					public int hashCode() {
						if (this.hashCodeDirty) {
							final int prime = PRIME;
							int result = DEFAULT_HASHCODE;

							result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());

							result = prime * result + ((this.quarterName == null) ? 0 : this.quarterName.hashCode());

							result = prime * result + ((this.year == null) ? 0 : this.year.hashCode());

							this.hashCode = result;
							this.hashCodeDirty = false;
						}
						return this.hashCode;
					}

					@Override
					public boolean equals(Object obj) {
						if (this == obj)
							return true;
						if (obj == null)
							return false;
						if (getClass() != obj.getClass())
							return false;
						final KeyStruct_tUniqRow_4 other = (KeyStruct_tUniqRow_4) obj;

						if (this.id == null) {
							if (other.id != null)
								return false;

						} else if (!this.id.equals(other.id))

							return false;

						if (this.quarterName == null) {
							if (other.quarterName != null)
								return false;

						} else if (!this.quarterName.equals(other.quarterName))

							return false;

						if (this.year == null) {
							if (other.year != null)
								return false;

						} else if (!this.year.equals(other.year))

							return false;

						return true;
					}

				}

				int nb_uniques_tUniqRow_4 = 0;
				int nb_duplicates_tUniqRow_4 = 0;
				KeyStruct_tUniqRow_4 finder_tUniqRow_4 = new KeyStruct_tUniqRow_4();
				java.util.Set<KeyStruct_tUniqRow_4> keystUniqRow_4 = new java.util.HashSet<KeyStruct_tUniqRow_4>();

				/**
				 * [tUniqRow_4 begin ] stop
				 */

				/**
				 * [tLogRow_4 begin ] start
				 */

				ok_Hash.put("tLogRow_4", false);
				start_Hash.put("tLogRow_4", System.currentTimeMillis());

				currentComponent = "tLogRow_4";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "quarter");
				}

				int tos_count_tLogRow_4 = 0;

				/**
				 * [tLogRow_4 begin ] stop
				 */

				/**
				 * [tMap_1 begin ] start
				 */

				ok_Hash.put("tMap_1", false);
				start_Hash.put("tMap_1", System.currentTimeMillis());

				currentComponent = "tMap_1";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "row5");
				}

				int tos_count_tMap_1 = 0;

// ###############################
// # Lookup's keys initialization
// ###############################        

// ###############################
// # Vars initialization
				class Var__tMap_1__Struct {
					java.util.Date dateTalend;
					String dayWeekName;
					String monthId;
					String quarterId;
					int monthNb;
					String monthName;
					int years;
					String quarterName;
				}
				Var__tMap_1__Struct Var__tMap_1 = new Var__tMap_1__Struct();
// ###############################

// ###############################
// # Outputs initialization
				dateStruct date_tmp = new dateStruct();
				monthMapStruct monthMap_tmp = new monthMapStruct();
				quarterStruct quarter_tmp = new quarterStruct();
// ###############################

				/**
				 * [tMap_1 begin ] stop
				 */

				/**
				 * [tUniqRow_1 begin ] start
				 */

				ok_Hash.put("tUniqRow_1", false);
				start_Hash.put("tUniqRow_1", System.currentTimeMillis());

				currentComponent = "tUniqRow_1";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "row4");
				}

				int tos_count_tUniqRow_1 = 0;

				int nb_uniques_tUniqRow_1 = 0;
				int nb_duplicates_tUniqRow_1 = 0;

				/**
				 * [tUniqRow_1 begin ] stop
				 */

				/**
				 * [tSplitRow_1 begin ] start
				 */

				ok_Hash.put("tSplitRow_1", false);
				start_Hash.put("tSplitRow_1", System.currentTimeMillis());

				currentComponent = "tSplitRow_1";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "row3");
				}

				int tos_count_tSplitRow_1 = 0;

				int nb_line_tSplitRow_1 = 0;

				/**
				 * [tSplitRow_1 begin ] stop
				 */

				/**
				 * [tLogRow_1 begin ] start
				 */

				ok_Hash.put("tLogRow_1", false);
				start_Hash.put("tLogRow_1", System.currentTimeMillis());

				currentComponent = "tLogRow_1";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "row2");
				}

				int tos_count_tLogRow_1 = 0;

				/**
				 * [tLogRow_1 begin ] stop
				 */

				/**
				 * [tFilterColumns_1 begin ] start
				 */

				ok_Hash.put("tFilterColumns_1", false);
				start_Hash.put("tFilterColumns_1", System.currentTimeMillis());

				currentComponent = "tFilterColumns_1";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "row1");
				}

				int tos_count_tFilterColumns_1 = 0;

				int nb_line_tFilterColumns_1 = 0;

				/**
				 * [tFilterColumns_1 begin ] stop
				 */

				/**
				 * [tFileInputDelimited_1 begin ] start
				 */

				ok_Hash.put("tFileInputDelimited_1", false);
				start_Hash.put("tFileInputDelimited_1", System.currentTimeMillis());

				currentComponent = "tFileInputDelimited_1";

				int tos_count_tFileInputDelimited_1 = 0;

				final routines.system.RowState rowstate_tFileInputDelimited_1 = new routines.system.RowState();

				int nb_line_tFileInputDelimited_1 = 0;
				int footer_tFileInputDelimited_1 = 1;
				int totalLinetFileInputDelimited_1 = 0;
				int limittFileInputDelimited_1 = -1;
				int lastLinetFileInputDelimited_1 = -1;

				char fieldSeparator_tFileInputDelimited_1[] = null;

				// support passing value (property: Field Separator) by 'context.fs' or
				// 'globalMap.get("fs")'.
				if (((String) ";").length() > 0) {
					fieldSeparator_tFileInputDelimited_1 = ((String) ";").toCharArray();
				} else {
					throw new IllegalArgumentException("Field Separator must be assigned a char.");
				}

				char rowSeparator_tFileInputDelimited_1[] = null;

				// support passing value (property: Row Separator) by 'context.rs' or
				// 'globalMap.get("rs")'.
				if (((String) "\n").length() > 0) {
					rowSeparator_tFileInputDelimited_1 = ((String) "\n").toCharArray();
				} else {
					throw new IllegalArgumentException("Row Separator must be assigned a char.");
				}

				Object filename_tFileInputDelimited_1 = /** Start field tFileInputDelimited_1:FILENAME */
						context.folderName + "/files/BDD_CASIOP.csv"/** End field tFileInputDelimited_1:FILENAME */
				;
				com.talend.csv.CSVReader csvReadertFileInputDelimited_1 = null;

				try {

					String[] rowtFileInputDelimited_1 = null;
					int currentLinetFileInputDelimited_1 = 0;
					int outputLinetFileInputDelimited_1 = 0;
					try {// TD110 begin
						if (filename_tFileInputDelimited_1 instanceof java.io.InputStream) {

							int footer_value_tFileInputDelimited_1 = 1;
							if (footer_value_tFileInputDelimited_1 > 0) {
								throw new java.lang.Exception(
										"When the input source is a stream,footer shouldn't be bigger than 0.");
							}

							csvReadertFileInputDelimited_1 = new com.talend.csv.CSVReader(
									(java.io.InputStream) filename_tFileInputDelimited_1,
									fieldSeparator_tFileInputDelimited_1[0], "ISO-8859-15");
						} else {
							csvReadertFileInputDelimited_1 = new com.talend.csv.CSVReader(
									String.valueOf(filename_tFileInputDelimited_1),
									fieldSeparator_tFileInputDelimited_1[0], "ISO-8859-15");
						}

						csvReadertFileInputDelimited_1.setTrimWhitespace(false);
						if ((rowSeparator_tFileInputDelimited_1[0] != '\n')
								&& (rowSeparator_tFileInputDelimited_1[0] != '\r'))
							csvReadertFileInputDelimited_1.setLineEnd("" + rowSeparator_tFileInputDelimited_1[0]);

						csvReadertFileInputDelimited_1.setQuoteChar('"');

						csvReadertFileInputDelimited_1.setEscapeChar(csvReadertFileInputDelimited_1.getQuoteChar());

						if (footer_tFileInputDelimited_1 > 0) {
							for (totalLinetFileInputDelimited_1 = 0; totalLinetFileInputDelimited_1 < 1; totalLinetFileInputDelimited_1++) {
								csvReadertFileInputDelimited_1.readNext();
							}
							csvReadertFileInputDelimited_1.setSkipEmptyRecords(true);
							while (csvReadertFileInputDelimited_1.readNext()) {

								rowtFileInputDelimited_1 = csvReadertFileInputDelimited_1.getValues();
								if (!(rowtFileInputDelimited_1.length == 1
										&& ("\015").equals(rowtFileInputDelimited_1[0]))) {// empty line when row
																							// separator is '\n'

									totalLinetFileInputDelimited_1++;

								}

							}
							int lastLineTemptFileInputDelimited_1 = totalLinetFileInputDelimited_1
									- footer_tFileInputDelimited_1 < 0 ? 0
											: totalLinetFileInputDelimited_1 - footer_tFileInputDelimited_1;
							if (lastLinetFileInputDelimited_1 > 0) {
								lastLinetFileInputDelimited_1 = lastLinetFileInputDelimited_1 < lastLineTemptFileInputDelimited_1
										? lastLinetFileInputDelimited_1
										: lastLineTemptFileInputDelimited_1;
							} else {
								lastLinetFileInputDelimited_1 = lastLineTemptFileInputDelimited_1;
							}

							csvReadertFileInputDelimited_1.close();
							if (filename_tFileInputDelimited_1 instanceof java.io.InputStream) {
								csvReadertFileInputDelimited_1 = new com.talend.csv.CSVReader(
										(java.io.InputStream) filename_tFileInputDelimited_1,
										fieldSeparator_tFileInputDelimited_1[0], "ISO-8859-15");
							} else {
								csvReadertFileInputDelimited_1 = new com.talend.csv.CSVReader(
										String.valueOf(filename_tFileInputDelimited_1),
										fieldSeparator_tFileInputDelimited_1[0], "ISO-8859-15");
							}
							csvReadertFileInputDelimited_1.setTrimWhitespace(false);
							if ((rowSeparator_tFileInputDelimited_1[0] != '\n')
									&& (rowSeparator_tFileInputDelimited_1[0] != '\r'))
								csvReadertFileInputDelimited_1.setLineEnd("" + rowSeparator_tFileInputDelimited_1[0]);

							csvReadertFileInputDelimited_1.setQuoteChar('"');

							csvReadertFileInputDelimited_1.setEscapeChar(csvReadertFileInputDelimited_1.getQuoteChar());

						}

						if (limittFileInputDelimited_1 != 0) {
							for (currentLinetFileInputDelimited_1 = 0; currentLinetFileInputDelimited_1 < 1; currentLinetFileInputDelimited_1++) {
								csvReadertFileInputDelimited_1.readNext();
							}
						}
						csvReadertFileInputDelimited_1.setSkipEmptyRecords(true);

					} catch (java.lang.Exception e) {
						globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE", e.getMessage());

						System.err.println(e.getMessage());

					} // TD110 end

					while (limittFileInputDelimited_1 != 0 && csvReadertFileInputDelimited_1 != null
							&& csvReadertFileInputDelimited_1.readNext()) {
						rowstate_tFileInputDelimited_1.reset();

						rowtFileInputDelimited_1 = csvReadertFileInputDelimited_1.getValues();

						if (rowtFileInputDelimited_1.length == 1 && ("\015").equals(rowtFileInputDelimited_1[0])) {// empty
																													// line
																													// when
																													// row
																													// separator
																													// is
																													// '\n'
							continue;
						}

						currentLinetFileInputDelimited_1++;

						if (lastLinetFileInputDelimited_1 > -1
								&& currentLinetFileInputDelimited_1 > lastLinetFileInputDelimited_1) {
							break;
						}
						outputLinetFileInputDelimited_1++;
						if (limittFileInputDelimited_1 > 0
								&& outputLinetFileInputDelimited_1 > limittFileInputDelimited_1) {
							break;
						}

						row1 = null;

						boolean whetherReject_tFileInputDelimited_1 = false;
						row1 = new row1Struct();
						try {

							char fieldSeparator_tFileInputDelimited_1_ListType[] = null;
							// support passing value (property: Field Separator) by 'context.fs' or
							// 'globalMap.get("fs")'.
							if (((String) ";").length() > 0) {
								fieldSeparator_tFileInputDelimited_1_ListType = ((String) ";").toCharArray();
							} else {
								throw new IllegalArgumentException("Field Separator must be assigned a char.");
							}
							if (rowtFileInputDelimited_1.length == 1 && ("\015").equals(rowtFileInputDelimited_1[0])) {// empty
																														// line
																														// when
																														// row
																														// separator
																														// is
																														// '\n'

								row1.Customer_Group_ID = null;

								row1.Customer_Group = null;

								row1.Customer_Type_ID = null;

								row1.Customer_Type = null;

								row1.Birth_Date = null;

								row1.Gender = null;

								row1.Customer_ID = null;

								row1.Country = null;

								row1.Country_Name = null;

								row1.Employee_ID = null;

								row1.Employee_Job_Title = null;

								row1.Employee_Salary = null;

								row1.Employee_Gender = null;

								row1.Employee_Country = null;

								row1.Order_Type = null;

								row1.Order_Date = null;

								row1.Delivery_Date = null;

								row1.Product_ID = null;

								row1.Product_Name = null;

								row1.Product_Ref_ID = null;

								row1.Quantity = null;

								row1.Order_ID = null;

								row1.Order_Item_Num = null;

								row1.CostPrice_Per_Unit = null;

								row1.Total_Retail_Price = null;

							} else {

								int columnIndexWithD_tFileInputDelimited_1 = 0; // Column Index

								columnIndexWithD_tFileInputDelimited_1 = 0;

								if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

									row1.Customer_Group_ID = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

								} else {

									row1.Customer_Group_ID = null;

								}

								columnIndexWithD_tFileInputDelimited_1 = 1;

								if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

									row1.Customer_Group = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

								} else {

									row1.Customer_Group = null;

								}

								columnIndexWithD_tFileInputDelimited_1 = 2;

								if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

									row1.Customer_Type_ID = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

								} else {

									row1.Customer_Type_ID = null;

								}

								columnIndexWithD_tFileInputDelimited_1 = 3;

								if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

									row1.Customer_Type = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

								} else {

									row1.Customer_Type = null;

								}

								columnIndexWithD_tFileInputDelimited_1 = 4;

								if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

									row1.Birth_Date = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

								} else {

									row1.Birth_Date = null;

								}

								columnIndexWithD_tFileInputDelimited_1 = 5;

								if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

									if (rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1].length() > 0) {
										try {

											row1.Gender = ParserUtils.parseTo_Character(
													rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1]);

										} catch (java.lang.Exception ex_tFileInputDelimited_1) {
											globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",
													ex_tFileInputDelimited_1.getMessage());
											rowstate_tFileInputDelimited_1.setException(new RuntimeException(String
													.format("Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
															"Gender", "row1",
															rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1],
															ex_tFileInputDelimited_1),
													ex_tFileInputDelimited_1));
										}
									} else {

										row1.Gender = null;

									}

								} else {

									row1.Gender = null;

								}

								columnIndexWithD_tFileInputDelimited_1 = 6;

								if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

									row1.Customer_ID = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

								} else {

									row1.Customer_ID = null;

								}

								columnIndexWithD_tFileInputDelimited_1 = 7;

								if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

									row1.Country = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

								} else {

									row1.Country = null;

								}

								columnIndexWithD_tFileInputDelimited_1 = 8;

								if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

									row1.Country_Name = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

								} else {

									row1.Country_Name = null;

								}

								columnIndexWithD_tFileInputDelimited_1 = 9;

								if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

									row1.Employee_ID = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

								} else {

									row1.Employee_ID = null;

								}

								columnIndexWithD_tFileInputDelimited_1 = 10;

								if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

									row1.Employee_Job_Title = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

								} else {

									row1.Employee_Job_Title = null;

								}

								columnIndexWithD_tFileInputDelimited_1 = 11;

								if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

									if (rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1].length() > 0) {
										try {

											row1.Employee_Salary = ParserUtils.parseTo_Integer(
													rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1]);

										} catch (java.lang.Exception ex_tFileInputDelimited_1) {
											globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",
													ex_tFileInputDelimited_1.getMessage());
											rowstate_tFileInputDelimited_1.setException(new RuntimeException(String
													.format("Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
															"Employee_Salary", "row1",
															rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1],
															ex_tFileInputDelimited_1),
													ex_tFileInputDelimited_1));
										}
									} else {

										row1.Employee_Salary = null;

									}

								} else {

									row1.Employee_Salary = null;

								}

								columnIndexWithD_tFileInputDelimited_1 = 12;

								if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

									row1.Employee_Gender = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

								} else {

									row1.Employee_Gender = null;

								}

								columnIndexWithD_tFileInputDelimited_1 = 13;

								if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

									row1.Employee_Country = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

								} else {

									row1.Employee_Country = null;

								}

								columnIndexWithD_tFileInputDelimited_1 = 14;

								if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

									row1.Order_Type = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

								} else {

									row1.Order_Type = null;

								}

								columnIndexWithD_tFileInputDelimited_1 = 15;

								if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

									row1.Order_Date = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

								} else {

									row1.Order_Date = null;

								}

								columnIndexWithD_tFileInputDelimited_1 = 16;

								if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

									row1.Delivery_Date = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

								} else {

									row1.Delivery_Date = null;

								}

								columnIndexWithD_tFileInputDelimited_1 = 17;

								if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

									row1.Product_ID = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

								} else {

									row1.Product_ID = null;

								}

								columnIndexWithD_tFileInputDelimited_1 = 18;

								if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

									row1.Product_Name = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

								} else {

									row1.Product_Name = null;

								}

								columnIndexWithD_tFileInputDelimited_1 = 19;

								if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

									row1.Product_Ref_ID = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

								} else {

									row1.Product_Ref_ID = null;

								}

								columnIndexWithD_tFileInputDelimited_1 = 20;

								if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

									if (rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1].length() > 0) {
										try {

											row1.Quantity = ParserUtils.parseTo_Integer(
													rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1]);

										} catch (java.lang.Exception ex_tFileInputDelimited_1) {
											globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",
													ex_tFileInputDelimited_1.getMessage());
											rowstate_tFileInputDelimited_1.setException(new RuntimeException(String
													.format("Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
															"Quantity", "row1",
															rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1],
															ex_tFileInputDelimited_1),
													ex_tFileInputDelimited_1));
										}
									} else {

										row1.Quantity = null;

									}

								} else {

									row1.Quantity = null;

								}

								columnIndexWithD_tFileInputDelimited_1 = 21;

								if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

									row1.Order_ID = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

								} else {

									row1.Order_ID = null;

								}

								columnIndexWithD_tFileInputDelimited_1 = 22;

								if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

									row1.Order_Item_Num = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

								} else {

									row1.Order_Item_Num = null;

								}

								columnIndexWithD_tFileInputDelimited_1 = 23;

								if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

									row1.CostPrice_Per_Unit = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

								} else {

									row1.CostPrice_Per_Unit = null;

								}

								columnIndexWithD_tFileInputDelimited_1 = 24;

								if (columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length) {

									row1.Total_Retail_Price = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];

								} else {

									row1.Total_Retail_Price = null;

								}

							}

							if (rowstate_tFileInputDelimited_1.getException() != null) {
								throw rowstate_tFileInputDelimited_1.getException();
							}

						} catch (java.lang.Exception e) {
							globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE", e.getMessage());
							whetherReject_tFileInputDelimited_1 = true;

							System.err.println(e.getMessage());
							row1 = null;

							globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE", e.getMessage());

						}

						/**
						 * [tFileInputDelimited_1 begin ] stop
						 */

						/**
						 * [tFileInputDelimited_1 main ] start
						 */

						currentComponent = "tFileInputDelimited_1";

						tos_count_tFileInputDelimited_1++;

						/**
						 * [tFileInputDelimited_1 main ] stop
						 */

						/**
						 * [tFileInputDelimited_1 process_data_begin ] start
						 */

						currentComponent = "tFileInputDelimited_1";

						/**
						 * [tFileInputDelimited_1 process_data_begin ] stop
						 */
// Start of branch "row1"
						if (row1 != null) {

							/**
							 * [tFilterColumns_1 main ] start
							 */

							currentComponent = "tFilterColumns_1";

							if (execStat) {
								runStat.updateStatOnConnection(iterateId, 1, 1

										, "row1"

								);
							}

							row2.Order_Date = row1.Order_Date;

							row2.Delivery_Date = row1.Delivery_Date;

							row2.Birth_Date = row1.Birth_Date;

							nb_line_tFilterColumns_1++;

							tos_count_tFilterColumns_1++;

							/**
							 * [tFilterColumns_1 main ] stop
							 */

							/**
							 * [tFilterColumns_1 process_data_begin ] start
							 */

							currentComponent = "tFilterColumns_1";

							/**
							 * [tFilterColumns_1 process_data_begin ] stop
							 */

							/**
							 * [tLogRow_1 main ] start
							 */

							currentComponent = "tLogRow_1";

							if (execStat) {
								runStat.updateStatOnConnection(iterateId, 1, 1

										, "row2"

								);
							}

							row3 = row2;

							tos_count_tLogRow_1++;

							/**
							 * [tLogRow_1 main ] stop
							 */

							/**
							 * [tLogRow_1 process_data_begin ] start
							 */

							currentComponent = "tLogRow_1";

							/**
							 * [tLogRow_1 process_data_begin ] stop
							 */

							/**
							 * [tSplitRow_1 main ] start
							 */

							currentComponent = "tSplitRow_1";

							if (execStat) {
								runStat.updateStatOnConnection(iterateId, 1, 1

										, "row3"

								);
							}

							java.util.List<row4Struct> rows_tSplitRow_1 = new java.util.ArrayList<row4Struct>(3);
							row4Struct rowTmp_tSplitRow_1 = null;

							// cache output rows for the loop
							rowTmp_tSplitRow_1 = new row4Struct();

							rowTmp_tSplitRow_1.date = row3.Order_Date;
							rows_tSplitRow_1.add(rowTmp_tSplitRow_1);
							nb_line_tSplitRow_1++;
							rowTmp_tSplitRow_1 = new row4Struct();

							rowTmp_tSplitRow_1.date = row3.Delivery_Date;
							rows_tSplitRow_1.add(rowTmp_tSplitRow_1);
							nb_line_tSplitRow_1++;
							rowTmp_tSplitRow_1 = new row4Struct();

							rowTmp_tSplitRow_1.date = row3.Birth_Date;
							rows_tSplitRow_1.add(rowTmp_tSplitRow_1);
							nb_line_tSplitRow_1++;

							for (row4Struct row_tSplitRow_1 : rows_tSplitRow_1) {// C_01
								row4 = row_tSplitRow_1;

								tos_count_tSplitRow_1++;

								/**
								 * [tSplitRow_1 main ] stop
								 */

								/**
								 * [tSplitRow_1 process_data_begin ] start
								 */

								currentComponent = "tSplitRow_1";

								/**
								 * [tSplitRow_1 process_data_begin ] stop
								 */
// Start of branch "row4"
								if (row4 != null) {

									/**
									 * [tUniqRow_1 main ] start
									 */

									currentComponent = "tUniqRow_1";

									if (execStat) {
										runStat.updateStatOnConnection(iterateId, 1, 1

												, "row4"

										);
									}

									row5.date = row4.date;

									tos_count_tUniqRow_1++;

									/**
									 * [tUniqRow_1 main ] stop
									 */

									/**
									 * [tUniqRow_1 process_data_begin ] start
									 */

									currentComponent = "tUniqRow_1";

									/**
									 * [tUniqRow_1 process_data_begin ] stop
									 */
// Start of branch "row5"
									if (row5 != null) {

										/**
										 * [tMap_1 main ] start
										 */

										currentComponent = "tMap_1";

										if (execStat) {
											runStat.updateStatOnConnection(iterateId, 1, 1

													, "row5"

											);
										}

										boolean hasCasePrimitiveKeyWithNull_tMap_1 = false;

										// ###############################
										// # Input tables (lookups)
										boolean rejectedInnerJoin_tMap_1 = false;
										boolean mainRowRejected_tMap_1 = false;

										// ###############################
										{ // start of Var scope

											// ###############################
											// # Vars tables

											Var__tMap_1__Struct Var = Var__tMap_1;
											Var.dateTalend = TalendDate.parseDate("yyyy/MM/dd",
													row5.date.split(" ")[5] + "/"
															+ fonctionsPerso.getMonthNumber(row5.date.split(" ")[1])
															+ "/" + row5.date.split(" ")[2]);
											;
											Var.dayWeekName = fonctionsPerso.getFullWeekName(row5.date.split(" ")[0]);
											Var.monthId = fonctionsPerso.getMonthNumber(row5.date.split(" ")[1]) + '-'
													+ row5.date.split(" ")[5];
											Var.quarterId = fonctionsPerso.getQuarterName(
													fonctionsPerso.getMonthNumber(row5.date.split(" ")[1])) + '-'
													+ row5.date.split(" ")[5];
											Var.monthNb = Integer
													.parseInt(fonctionsPerso.getMonthNumber(row5.date.split(" ")[1]));
											Var.monthName = fonctionsPerso.getFullMonthName(row5.date.split(" ")[1]);
											Var.years = Integer.parseInt(row5.date.split(" ")[5]);
											Var.quarterName = fonctionsPerso.getQuarterName(
													fonctionsPerso.getMonthNumber(row5.date.split(" ")[1]));// ###############################
											// ###############################
											// # Output tables

											date = null;
											monthMap = null;
											quarter = null;

// # Output table : 'date'
											date_tmp.date = Var.dateTalend;
											date_tmp.dayWeekName = Var.dayWeekName;
											date_tmp.monthId = Var.monthId;
											date = date_tmp;

// # Output table : 'monthMap'
											monthMap_tmp.id = Var.monthId;
											monthMap_tmp.monthNb = Var.monthNb;
											monthMap_tmp.monthName = Var.monthName;
											monthMap_tmp.quarterId = Var.quarterId;
											monthMap = monthMap_tmp;

// # Output table : 'quarter'
											quarter_tmp.id = Var.quarterId;
											quarter_tmp.quarterName = Var.quarterName;
											quarter_tmp.year = Var.years;
											quarter = quarter_tmp;
// ###############################

										} // end of Var scope

										rejectedInnerJoin_tMap_1 = false;

										tos_count_tMap_1++;

										/**
										 * [tMap_1 main ] stop
										 */

										/**
										 * [tMap_1 process_data_begin ] start
										 */

										currentComponent = "tMap_1";

										/**
										 * [tMap_1 process_data_begin ] stop
										 */
// Start of branch "date"
										if (date != null) {

											/**
											 * [tLogRow_2 main ] start
											 */

											currentComponent = "tLogRow_2";

											if (execStat) {
												runStat.updateStatOnConnection(iterateId, 1, 1

														, "date"

												);
											}

											row6 = date;

											tos_count_tLogRow_2++;

											/**
											 * [tLogRow_2 main ] stop
											 */

											/**
											 * [tLogRow_2 process_data_begin ] start
											 */

											currentComponent = "tLogRow_2";

											/**
											 * [tLogRow_2 process_data_begin ] stop
											 */

											/**
											 * [tUniqRow_2 main ] start
											 */

											currentComponent = "tUniqRow_2";

											if (execStat) {
												runStat.updateStatOnConnection(iterateId, 1, 1

														, "row6"

												);
											}

											row9 = null;
											finder_tUniqRow_2.date = row6.date;
											if (row6.dayWeekName == null) {
												finder_tUniqRow_2.dayWeekName = null;
											} else {
												finder_tUniqRow_2.dayWeekName = row6.dayWeekName.toLowerCase();
											}
											if (row6.monthId == null) {
												finder_tUniqRow_2.monthId = null;
											} else {
												finder_tUniqRow_2.monthId = row6.monthId.toLowerCase();
											}
											finder_tUniqRow_2.hashCodeDirty = true;
											if (!keystUniqRow_2.contains(finder_tUniqRow_2)) {
												KeyStruct_tUniqRow_2 new_tUniqRow_2 = new KeyStruct_tUniqRow_2();

												new_tUniqRow_2.date = row6.date;
												if (row6.dayWeekName == null) {
													new_tUniqRow_2.dayWeekName = null;
												} else {
													new_tUniqRow_2.dayWeekName = row6.dayWeekName.toLowerCase();
												}
												if (row6.monthId == null) {
													new_tUniqRow_2.monthId = null;
												} else {
													new_tUniqRow_2.monthId = row6.monthId.toLowerCase();
												}

												keystUniqRow_2.add(new_tUniqRow_2);
												if (row9 == null) {

													row9 = new row9Struct();
												}
												row9.date = row6.date;
												row9.dayWeekName = row6.dayWeekName;
												row9.monthId = row6.monthId;
												nb_uniques_tUniqRow_2++;
											} else {
												nb_duplicates_tUniqRow_2++;
											}

											tos_count_tUniqRow_2++;

											/**
											 * [tUniqRow_2 main ] stop
											 */

											/**
											 * [tUniqRow_2 process_data_begin ] start
											 */

											currentComponent = "tUniqRow_2";

											/**
											 * [tUniqRow_2 process_data_begin ] stop
											 */
// Start of branch "row9"
											if (row9 != null) {

												/**
												 * [tFileOutputDelimited_1 main ] start
												 */

												currentComponent = "tFileOutputDelimited_1";

												if (execStat) {
													runStat.updateStatOnConnection(iterateId, 1, 1

															, "row9"

													);
												}

												StringBuilder sb_tFileOutputDelimited_1 = new StringBuilder();
												if (row9.date != null) {
													sb_tFileOutputDelimited_1.append(
															FormatterUtils.format_Date(row9.date, "dd-MM-yyyy"));
												}
												sb_tFileOutputDelimited_1.append(OUT_DELIM_tFileOutputDelimited_1);
												if (row9.dayWeekName != null) {
													sb_tFileOutputDelimited_1.append(row9.dayWeekName);
												}
												sb_tFileOutputDelimited_1.append(OUT_DELIM_tFileOutputDelimited_1);
												if (row9.monthId != null) {
													sb_tFileOutputDelimited_1.append(row9.monthId);
												}
												sb_tFileOutputDelimited_1
														.append(OUT_DELIM_ROWSEP_tFileOutputDelimited_1);

												nb_line_tFileOutputDelimited_1++;
												resourceMap.put("nb_line_tFileOutputDelimited_1",
														nb_line_tFileOutputDelimited_1);

												outtFileOutputDelimited_1.write(sb_tFileOutputDelimited_1.toString());

												tos_count_tFileOutputDelimited_1++;

												/**
												 * [tFileOutputDelimited_1 main ] stop
												 */

												/**
												 * [tFileOutputDelimited_1 process_data_begin ] start
												 */

												currentComponent = "tFileOutputDelimited_1";

												/**
												 * [tFileOutputDelimited_1 process_data_begin ] stop
												 */

												/**
												 * [tFileOutputDelimited_1 process_data_end ] start
												 */

												currentComponent = "tFileOutputDelimited_1";

												/**
												 * [tFileOutputDelimited_1 process_data_end ] stop
												 */

											} // End of branch "row9"

											/**
											 * [tUniqRow_2 process_data_end ] start
											 */

											currentComponent = "tUniqRow_2";

											/**
											 * [tUniqRow_2 process_data_end ] stop
											 */

											/**
											 * [tLogRow_2 process_data_end ] start
											 */

											currentComponent = "tLogRow_2";

											/**
											 * [tLogRow_2 process_data_end ] stop
											 */

										} // End of branch "date"

// Start of branch "monthMap"
										if (monthMap != null) {

											/**
											 * [tLogRow_3 main ] start
											 */

											currentComponent = "tLogRow_3";

											if (execStat) {
												runStat.updateStatOnConnection(iterateId, 1, 1

														, "monthMap"

												);
											}

											row8 = monthMap;

											tos_count_tLogRow_3++;

											/**
											 * [tLogRow_3 main ] stop
											 */

											/**
											 * [tLogRow_3 process_data_begin ] start
											 */

											currentComponent = "tLogRow_3";

											/**
											 * [tLogRow_3 process_data_begin ] stop
											 */

											/**
											 * [tUniqRow_3 main ] start
											 */

											currentComponent = "tUniqRow_3";

											if (execStat) {
												runStat.updateStatOnConnection(iterateId, 1, 1

														, "row8"

												);
											}

											row10 = null;
											if (row8.id == null) {
												finder_tUniqRow_3.id = null;
											} else {
												finder_tUniqRow_3.id = row8.id.toLowerCase();
											}
											finder_tUniqRow_3.monthNb = row8.monthNb;
											if (row8.monthName == null) {
												finder_tUniqRow_3.monthName = null;
											} else {
												finder_tUniqRow_3.monthName = row8.monthName.toLowerCase();
											}
											if (row8.quarterId == null) {
												finder_tUniqRow_3.quarterId = null;
											} else {
												finder_tUniqRow_3.quarterId = row8.quarterId.toLowerCase();
											}
											finder_tUniqRow_3.hashCodeDirty = true;
											if (!keystUniqRow_3.contains(finder_tUniqRow_3)) {
												KeyStruct_tUniqRow_3 new_tUniqRow_3 = new KeyStruct_tUniqRow_3();

												if (row8.id == null) {
													new_tUniqRow_3.id = null;
												} else {
													new_tUniqRow_3.id = row8.id.toLowerCase();
												}
												new_tUniqRow_3.monthNb = row8.monthNb;
												if (row8.monthName == null) {
													new_tUniqRow_3.monthName = null;
												} else {
													new_tUniqRow_3.monthName = row8.monthName.toLowerCase();
												}
												if (row8.quarterId == null) {
													new_tUniqRow_3.quarterId = null;
												} else {
													new_tUniqRow_3.quarterId = row8.quarterId.toLowerCase();
												}

												keystUniqRow_3.add(new_tUniqRow_3);
												if (row10 == null) {

													row10 = new row10Struct();
												}
												row10.id = row8.id;
												row10.monthNb = row8.monthNb;
												row10.monthName = row8.monthName;
												row10.quarterId = row8.quarterId;
												nb_uniques_tUniqRow_3++;
											} else {
												nb_duplicates_tUniqRow_3++;
											}

											tos_count_tUniqRow_3++;

											/**
											 * [tUniqRow_3 main ] stop
											 */

											/**
											 * [tUniqRow_3 process_data_begin ] start
											 */

											currentComponent = "tUniqRow_3";

											/**
											 * [tUniqRow_3 process_data_begin ] stop
											 */
// Start of branch "row10"
											if (row10 != null) {

												/**
												 * [tFileOutputDelimited_2 main ] start
												 */

												currentComponent = "tFileOutputDelimited_2";

												if (execStat) {
													runStat.updateStatOnConnection(iterateId, 1, 1

															, "row10"

													);
												}

												StringBuilder sb_tFileOutputDelimited_2 = new StringBuilder();
												if (row10.id != null) {
													sb_tFileOutputDelimited_2.append(row10.id);
												}
												sb_tFileOutputDelimited_2.append(OUT_DELIM_tFileOutputDelimited_2);
												if (row10.monthNb != null) {
													sb_tFileOutputDelimited_2.append(row10.monthNb);
												}
												sb_tFileOutputDelimited_2.append(OUT_DELIM_tFileOutputDelimited_2);
												if (row10.monthName != null) {
													sb_tFileOutputDelimited_2.append(row10.monthName);
												}
												sb_tFileOutputDelimited_2.append(OUT_DELIM_tFileOutputDelimited_2);
												if (row10.quarterId != null) {
													sb_tFileOutputDelimited_2.append(row10.quarterId);
												}
												sb_tFileOutputDelimited_2
														.append(OUT_DELIM_ROWSEP_tFileOutputDelimited_2);

												nb_line_tFileOutputDelimited_2++;
												resourceMap.put("nb_line_tFileOutputDelimited_2",
														nb_line_tFileOutputDelimited_2);

												outtFileOutputDelimited_2.write(sb_tFileOutputDelimited_2.toString());

												tos_count_tFileOutputDelimited_2++;

												/**
												 * [tFileOutputDelimited_2 main ] stop
												 */

												/**
												 * [tFileOutputDelimited_2 process_data_begin ] start
												 */

												currentComponent = "tFileOutputDelimited_2";

												/**
												 * [tFileOutputDelimited_2 process_data_begin ] stop
												 */

												/**
												 * [tFileOutputDelimited_2 process_data_end ] start
												 */

												currentComponent = "tFileOutputDelimited_2";

												/**
												 * [tFileOutputDelimited_2 process_data_end ] stop
												 */

											} // End of branch "row10"

											/**
											 * [tUniqRow_3 process_data_end ] start
											 */

											currentComponent = "tUniqRow_3";

											/**
											 * [tUniqRow_3 process_data_end ] stop
											 */

											/**
											 * [tLogRow_3 process_data_end ] start
											 */

											currentComponent = "tLogRow_3";

											/**
											 * [tLogRow_3 process_data_end ] stop
											 */

										} // End of branch "monthMap"

// Start of branch "quarter"
										if (quarter != null) {

											/**
											 * [tLogRow_4 main ] start
											 */

											currentComponent = "tLogRow_4";

											if (execStat) {
												runStat.updateStatOnConnection(iterateId, 1, 1

														, "quarter"

												);
											}

											row7 = quarter;

											tos_count_tLogRow_4++;

											/**
											 * [tLogRow_4 main ] stop
											 */

											/**
											 * [tLogRow_4 process_data_begin ] start
											 */

											currentComponent = "tLogRow_4";

											/**
											 * [tLogRow_4 process_data_begin ] stop
											 */

											/**
											 * [tUniqRow_4 main ] start
											 */

											currentComponent = "tUniqRow_4";

											if (execStat) {
												runStat.updateStatOnConnection(iterateId, 1, 1

														, "row7"

												);
											}

											row11 = null;
											if (row7.id == null) {
												finder_tUniqRow_4.id = null;
											} else {
												finder_tUniqRow_4.id = row7.id.toLowerCase();
											}
											if (row7.quarterName == null) {
												finder_tUniqRow_4.quarterName = null;
											} else {
												finder_tUniqRow_4.quarterName = row7.quarterName.toLowerCase();
											}
											finder_tUniqRow_4.year = row7.year;
											finder_tUniqRow_4.hashCodeDirty = true;
											if (!keystUniqRow_4.contains(finder_tUniqRow_4)) {
												KeyStruct_tUniqRow_4 new_tUniqRow_4 = new KeyStruct_tUniqRow_4();

												if (row7.id == null) {
													new_tUniqRow_4.id = null;
												} else {
													new_tUniqRow_4.id = row7.id.toLowerCase();
												}
												if (row7.quarterName == null) {
													new_tUniqRow_4.quarterName = null;
												} else {
													new_tUniqRow_4.quarterName = row7.quarterName.toLowerCase();
												}
												new_tUniqRow_4.year = row7.year;

												keystUniqRow_4.add(new_tUniqRow_4);
												if (row11 == null) {

													row11 = new row11Struct();
												}
												row11.id = row7.id;
												row11.quarterName = row7.quarterName;
												row11.year = row7.year;
												nb_uniques_tUniqRow_4++;
											} else {
												nb_duplicates_tUniqRow_4++;
											}

											tos_count_tUniqRow_4++;

											/**
											 * [tUniqRow_4 main ] stop
											 */

											/**
											 * [tUniqRow_4 process_data_begin ] start
											 */

											currentComponent = "tUniqRow_4";

											/**
											 * [tUniqRow_4 process_data_begin ] stop
											 */
// Start of branch "row11"
											if (row11 != null) {

												/**
												 * [tFileOutputDelimited_3 main ] start
												 */

												currentComponent = "tFileOutputDelimited_3";

												if (execStat) {
													runStat.updateStatOnConnection(iterateId, 1, 1

															, "row11"

													);
												}

												StringBuilder sb_tFileOutputDelimited_3 = new StringBuilder();
												if (row11.id != null) {
													sb_tFileOutputDelimited_3.append(row11.id);
												}
												sb_tFileOutputDelimited_3.append(OUT_DELIM_tFileOutputDelimited_3);
												if (row11.quarterName != null) {
													sb_tFileOutputDelimited_3.append(row11.quarterName);
												}
												sb_tFileOutputDelimited_3.append(OUT_DELIM_tFileOutputDelimited_3);
												if (row11.year != null) {
													sb_tFileOutputDelimited_3.append(row11.year);
												}
												sb_tFileOutputDelimited_3
														.append(OUT_DELIM_ROWSEP_tFileOutputDelimited_3);

												nb_line_tFileOutputDelimited_3++;
												resourceMap.put("nb_line_tFileOutputDelimited_3",
														nb_line_tFileOutputDelimited_3);

												outtFileOutputDelimited_3.write(sb_tFileOutputDelimited_3.toString());

												tos_count_tFileOutputDelimited_3++;

												/**
												 * [tFileOutputDelimited_3 main ] stop
												 */

												/**
												 * [tFileOutputDelimited_3 process_data_begin ] start
												 */

												currentComponent = "tFileOutputDelimited_3";

												/**
												 * [tFileOutputDelimited_3 process_data_begin ] stop
												 */

												/**
												 * [tFileOutputDelimited_3 process_data_end ] start
												 */

												currentComponent = "tFileOutputDelimited_3";

												/**
												 * [tFileOutputDelimited_3 process_data_end ] stop
												 */

											} // End of branch "row11"

											/**
											 * [tUniqRow_4 process_data_end ] start
											 */

											currentComponent = "tUniqRow_4";

											/**
											 * [tUniqRow_4 process_data_end ] stop
											 */

											/**
											 * [tLogRow_4 process_data_end ] start
											 */

											currentComponent = "tLogRow_4";

											/**
											 * [tLogRow_4 process_data_end ] stop
											 */

										} // End of branch "quarter"

										/**
										 * [tMap_1 process_data_end ] start
										 */

										currentComponent = "tMap_1";

										/**
										 * [tMap_1 process_data_end ] stop
										 */

									} // End of branch "row5"

									/**
									 * [tUniqRow_1 process_data_end ] start
									 */

									currentComponent = "tUniqRow_1";

									/**
									 * [tUniqRow_1 process_data_end ] stop
									 */

								} // End of branch "row4"

							} // C_01

							/**
							 * [tSplitRow_1 process_data_end ] start
							 */

							currentComponent = "tSplitRow_1";

							/**
							 * [tSplitRow_1 process_data_end ] stop
							 */

							/**
							 * [tLogRow_1 process_data_end ] start
							 */

							currentComponent = "tLogRow_1";

							/**
							 * [tLogRow_1 process_data_end ] stop
							 */

							/**
							 * [tFilterColumns_1 process_data_end ] start
							 */

							currentComponent = "tFilterColumns_1";

							/**
							 * [tFilterColumns_1 process_data_end ] stop
							 */

						} // End of branch "row1"

						/**
						 * [tFileInputDelimited_1 process_data_end ] start
						 */

						currentComponent = "tFileInputDelimited_1";

						/**
						 * [tFileInputDelimited_1 process_data_end ] stop
						 */

						/**
						 * [tFileInputDelimited_1 end ] start
						 */

						currentComponent = "tFileInputDelimited_1";

						nb_line_tFileInputDelimited_1++;
					}

				} finally {
					if (!(filename_tFileInputDelimited_1 instanceof java.io.InputStream)) {
						if (csvReadertFileInputDelimited_1 != null) {
							csvReadertFileInputDelimited_1.close();
						}
					}
					if (csvReadertFileInputDelimited_1 != null) {
						globalMap.put("tFileInputDelimited_1_NB_LINE", nb_line_tFileInputDelimited_1);
					}

				}

				ok_Hash.put("tFileInputDelimited_1", true);
				end_Hash.put("tFileInputDelimited_1", System.currentTimeMillis());

				/**
				 * [tFileInputDelimited_1 end ] stop
				 */

				/**
				 * [tFilterColumns_1 end ] start
				 */

				currentComponent = "tFilterColumns_1";

				globalMap.put("tFilterColumns_1_NB_LINE", nb_line_tFilterColumns_1);
				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "row1");
				}

				ok_Hash.put("tFilterColumns_1", true);
				end_Hash.put("tFilterColumns_1", System.currentTimeMillis());

				/**
				 * [tFilterColumns_1 end ] stop
				 */

				/**
				 * [tLogRow_1 end ] start
				 */

				currentComponent = "tLogRow_1";

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "row2");
				}

				ok_Hash.put("tLogRow_1", true);
				end_Hash.put("tLogRow_1", System.currentTimeMillis());

				/**
				 * [tLogRow_1 end ] stop
				 */

				/**
				 * [tSplitRow_1 end ] start
				 */

				currentComponent = "tSplitRow_1";

				globalMap.put("tSplitRow_1_NB_LINE", nb_line_tSplitRow_1);
				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "row3");
				}

				ok_Hash.put("tSplitRow_1", true);
				end_Hash.put("tSplitRow_1", System.currentTimeMillis());

				/**
				 * [tSplitRow_1 end ] stop
				 */

				/**
				 * [tUniqRow_1 end ] start
				 */

				currentComponent = "tUniqRow_1";

				globalMap.put("tUniqRow_1_NB_UNIQUES", nb_uniques_tUniqRow_1);
				globalMap.put("tUniqRow_1_NB_DUPLICATES", nb_duplicates_tUniqRow_1);

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "row4");
				}

				ok_Hash.put("tUniqRow_1", true);
				end_Hash.put("tUniqRow_1", System.currentTimeMillis());

				/**
				 * [tUniqRow_1 end ] stop
				 */

				/**
				 * [tMap_1 end ] start
				 */

				currentComponent = "tMap_1";

// ###############################
// # Lookup hashes releasing
// ###############################      

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "row5");
				}

				ok_Hash.put("tMap_1", true);
				end_Hash.put("tMap_1", System.currentTimeMillis());

				/**
				 * [tMap_1 end ] stop
				 */

				/**
				 * [tLogRow_2 end ] start
				 */

				currentComponent = "tLogRow_2";

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "date");
				}

				ok_Hash.put("tLogRow_2", true);
				end_Hash.put("tLogRow_2", System.currentTimeMillis());

				/**
				 * [tLogRow_2 end ] stop
				 */

				/**
				 * [tUniqRow_2 end ] start
				 */

				currentComponent = "tUniqRow_2";

				globalMap.put("tUniqRow_2_NB_UNIQUES", nb_uniques_tUniqRow_2);
				globalMap.put("tUniqRow_2_NB_DUPLICATES", nb_duplicates_tUniqRow_2);

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "row6");
				}

				ok_Hash.put("tUniqRow_2", true);
				end_Hash.put("tUniqRow_2", System.currentTimeMillis());

				/**
				 * [tUniqRow_2 end ] stop
				 */

				/**
				 * [tFileOutputDelimited_1 end ] start
				 */

				currentComponent = "tFileOutputDelimited_1";

				if (outtFileOutputDelimited_1 != null) {
					outtFileOutputDelimited_1.flush();
					outtFileOutputDelimited_1.close();
				}

				globalMap.put("tFileOutputDelimited_1_NB_LINE", nb_line_tFileOutputDelimited_1);
				globalMap.put("tFileOutputDelimited_1_FILE_NAME", fileName_tFileOutputDelimited_1);

				resourceMap.put("finish_tFileOutputDelimited_1", true);

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "row9");
				}

				ok_Hash.put("tFileOutputDelimited_1", true);
				end_Hash.put("tFileOutputDelimited_1", System.currentTimeMillis());

				/**
				 * [tFileOutputDelimited_1 end ] stop
				 */

				/**
				 * [tLogRow_3 end ] start
				 */

				currentComponent = "tLogRow_3";

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "monthMap");
				}

				ok_Hash.put("tLogRow_3", true);
				end_Hash.put("tLogRow_3", System.currentTimeMillis());

				/**
				 * [tLogRow_3 end ] stop
				 */

				/**
				 * [tUniqRow_3 end ] start
				 */

				currentComponent = "tUniqRow_3";

				globalMap.put("tUniqRow_3_NB_UNIQUES", nb_uniques_tUniqRow_3);
				globalMap.put("tUniqRow_3_NB_DUPLICATES", nb_duplicates_tUniqRow_3);

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "row8");
				}

				ok_Hash.put("tUniqRow_3", true);
				end_Hash.put("tUniqRow_3", System.currentTimeMillis());

				/**
				 * [tUniqRow_3 end ] stop
				 */

				/**
				 * [tFileOutputDelimited_2 end ] start
				 */

				currentComponent = "tFileOutputDelimited_2";

				if (outtFileOutputDelimited_2 != null) {
					outtFileOutputDelimited_2.flush();
					outtFileOutputDelimited_2.close();
				}

				globalMap.put("tFileOutputDelimited_2_NB_LINE", nb_line_tFileOutputDelimited_2);
				globalMap.put("tFileOutputDelimited_2_FILE_NAME", fileName_tFileOutputDelimited_2);

				resourceMap.put("finish_tFileOutputDelimited_2", true);

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "row10");
				}

				ok_Hash.put("tFileOutputDelimited_2", true);
				end_Hash.put("tFileOutputDelimited_2", System.currentTimeMillis());

				/**
				 * [tFileOutputDelimited_2 end ] stop
				 */

				/**
				 * [tLogRow_4 end ] start
				 */

				currentComponent = "tLogRow_4";

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "quarter");
				}

				ok_Hash.put("tLogRow_4", true);
				end_Hash.put("tLogRow_4", System.currentTimeMillis());

				/**
				 * [tLogRow_4 end ] stop
				 */

				/**
				 * [tUniqRow_4 end ] start
				 */

				currentComponent = "tUniqRow_4";

				globalMap.put("tUniqRow_4_NB_UNIQUES", nb_uniques_tUniqRow_4);
				globalMap.put("tUniqRow_4_NB_DUPLICATES", nb_duplicates_tUniqRow_4);

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "row7");
				}

				ok_Hash.put("tUniqRow_4", true);
				end_Hash.put("tUniqRow_4", System.currentTimeMillis());

				/**
				 * [tUniqRow_4 end ] stop
				 */

				/**
				 * [tFileOutputDelimited_3 end ] start
				 */

				currentComponent = "tFileOutputDelimited_3";

				if (outtFileOutputDelimited_3 != null) {
					outtFileOutputDelimited_3.flush();
					outtFileOutputDelimited_3.close();
				}

				globalMap.put("tFileOutputDelimited_3_NB_LINE", nb_line_tFileOutputDelimited_3);
				globalMap.put("tFileOutputDelimited_3_FILE_NAME", fileName_tFileOutputDelimited_3);

				resourceMap.put("finish_tFileOutputDelimited_3", true);

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "row11");
				}

				ok_Hash.put("tFileOutputDelimited_3", true);
				end_Hash.put("tFileOutputDelimited_3", System.currentTimeMillis());

				/**
				 * [tFileOutputDelimited_3 end ] stop
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
				 * [tFileInputDelimited_1 finally ] start
				 */

				currentComponent = "tFileInputDelimited_1";

				/**
				 * [tFileInputDelimited_1 finally ] stop
				 */

				/**
				 * [tFilterColumns_1 finally ] start
				 */

				currentComponent = "tFilterColumns_1";

				/**
				 * [tFilterColumns_1 finally ] stop
				 */

				/**
				 * [tLogRow_1 finally ] start
				 */

				currentComponent = "tLogRow_1";

				/**
				 * [tLogRow_1 finally ] stop
				 */

				/**
				 * [tSplitRow_1 finally ] start
				 */

				currentComponent = "tSplitRow_1";

				/**
				 * [tSplitRow_1 finally ] stop
				 */

				/**
				 * [tUniqRow_1 finally ] start
				 */

				currentComponent = "tUniqRow_1";

				/**
				 * [tUniqRow_1 finally ] stop
				 */

				/**
				 * [tMap_1 finally ] start
				 */

				currentComponent = "tMap_1";

				/**
				 * [tMap_1 finally ] stop
				 */

				/**
				 * [tLogRow_2 finally ] start
				 */

				currentComponent = "tLogRow_2";

				/**
				 * [tLogRow_2 finally ] stop
				 */

				/**
				 * [tUniqRow_2 finally ] start
				 */

				currentComponent = "tUniqRow_2";

				/**
				 * [tUniqRow_2 finally ] stop
				 */

				/**
				 * [tFileOutputDelimited_1 finally ] start
				 */

				currentComponent = "tFileOutputDelimited_1";

				if (resourceMap.get("finish_tFileOutputDelimited_1") == null) {

					java.io.Writer outtFileOutputDelimited_1 = (java.io.Writer) resourceMap
							.get("out_tFileOutputDelimited_1");
					if (outtFileOutputDelimited_1 != null) {
						outtFileOutputDelimited_1.flush();
						outtFileOutputDelimited_1.close();
					}

				}

				/**
				 * [tFileOutputDelimited_1 finally ] stop
				 */

				/**
				 * [tLogRow_3 finally ] start
				 */

				currentComponent = "tLogRow_3";

				/**
				 * [tLogRow_3 finally ] stop
				 */

				/**
				 * [tUniqRow_3 finally ] start
				 */

				currentComponent = "tUniqRow_3";

				/**
				 * [tUniqRow_3 finally ] stop
				 */

				/**
				 * [tFileOutputDelimited_2 finally ] start
				 */

				currentComponent = "tFileOutputDelimited_2";

				if (resourceMap.get("finish_tFileOutputDelimited_2") == null) {

					java.io.Writer outtFileOutputDelimited_2 = (java.io.Writer) resourceMap
							.get("out_tFileOutputDelimited_2");
					if (outtFileOutputDelimited_2 != null) {
						outtFileOutputDelimited_2.flush();
						outtFileOutputDelimited_2.close();
					}

				}

				/**
				 * [tFileOutputDelimited_2 finally ] stop
				 */

				/**
				 * [tLogRow_4 finally ] start
				 */

				currentComponent = "tLogRow_4";

				/**
				 * [tLogRow_4 finally ] stop
				 */

				/**
				 * [tUniqRow_4 finally ] start
				 */

				currentComponent = "tUniqRow_4";

				/**
				 * [tUniqRow_4 finally ] stop
				 */

				/**
				 * [tFileOutputDelimited_3 finally ] start
				 */

				currentComponent = "tFileOutputDelimited_3";

				if (resourceMap.get("finish_tFileOutputDelimited_3") == null) {

					java.io.Writer outtFileOutputDelimited_3 = (java.io.Writer) resourceMap
							.get("out_tFileOutputDelimited_3");
					if (outtFileOutputDelimited_3 != null) {
						outtFileOutputDelimited_3.flush();
						outtFileOutputDelimited_3.close();
					}

				}

				/**
				 * [tFileOutputDelimited_3 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tFileInputDelimited_1_SUBPROCESS_STATE", 1);
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
		final time timeClass = new time();

		int exitCode = timeClass.runJobInTOS(args);

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
			java.io.InputStream inContext = time.class.getClassLoader()
					.getResourceAsStream("projetsid/time_0_1/contexts/" + contextStr + ".properties");
			if (inContext == null) {
				inContext = time.class.getClassLoader()
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

		this.globalResumeTicket = false;// to run others jobs

		try {
			errorCode = null;
			tFileInputDelimited_1Process(globalMap);
			if (!"failure".equals(status)) {
				status = "end";
			}
		} catch (TalendException e_tFileInputDelimited_1) {
			globalMap.put("tFileInputDelimited_1_SUBPROCESS_STATE", -1);

			e_tFileInputDelimited_1.printStackTrace();

		}

		this.globalResumeTicket = true;// to run tPostJob

		end = System.currentTimeMillis();

		if (watch) {
			System.out.println((end - startTime) + " milliseconds");
		}

		endUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		if (false) {
			System.out.println((endUsedMemory - startUsedMemory) + " bytes memory increase when running : time");
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
 * 215175 characters generated by Talend Open Studio for Data Integration on the
 * 9 mai 2024 à 20:45:44 CEST
 ************************************************************************************************/