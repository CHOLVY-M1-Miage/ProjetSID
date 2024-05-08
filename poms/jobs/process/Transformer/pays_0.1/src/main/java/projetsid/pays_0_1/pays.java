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

package projetsid.pays_0_1;

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
 * Job: pays Purpose: extraire la table pays du csv<br>
 * Description: <br>
 * 
 * @author user@talend.com
 * @version 8.0.1.20211109_1610
 * @status
 */
public class pays implements TalendJob {

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
	private final String jobName = "pays";
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
					pays.this.exception = e;
				}
			}
			if (!(e instanceof TalendException)) {
				try {
					for (java.lang.reflect.Method m : this.getClass().getEnclosingClass().getMethods()) {
						if (m.getName().compareTo(currentComponent + "_error") == 0) {
							m.invoke(pays.this, new Object[] { e, currentComponent, globalMap });
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

	public void tFileInputDelimited_2_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_2_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tFilterColumns_2_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_2_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tUniqRow_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_2_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tLogRow_3_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_2_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tFileOutputDelimited_2_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tFileInputDelimited_2_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tFileInputDelimited_2_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public static class row8Struct implements routines.system.IPersistableRow<row8Struct> {
		final static byte[] commonByteArrayLock_PROJETSID_pays = new byte[0];
		static byte[] commonByteArray_PROJETSID_pays = new byte[0];
		protected static final int DEFAULT_HASHCODE = 1;
		protected static final int PRIME = 31;
		protected int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		public String loopKey;

		public String Country;

		public String getCountry() {
			return this.Country;
		}

		public String Country_Name;

		public String getCountry_Name() {
			return this.Country_Name;
		}

		public String Country_ID;

		public String getCountry_ID() {
			return this.Country_ID;
		}

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime * result + ((this.Country == null) ? 0 : this.Country.hashCode());

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

			if (this.Country == null) {
				if (other.Country != null)
					return false;

			} else if (!this.Country.equals(other.Country))

				return false;

			return true;
		}

		public void copyDataTo(row8Struct other) {

			other.Country = this.Country;
			other.Country_Name = this.Country_Name;
			other.Country_ID = this.Country_ID;

		}

		public void copyKeysDataTo(row8Struct other) {

			other.Country = this.Country;

		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJETSID_pays.length) {
					if (length < 1024 && commonByteArray_PROJETSID_pays.length == 0) {
						commonByteArray_PROJETSID_pays = new byte[1024];
					} else {
						commonByteArray_PROJETSID_pays = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_PROJETSID_pays, 0, length);
				strReturn = new String(commonByteArray_PROJETSID_pays, 0, length, utf8Charset);
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
				if (length > commonByteArray_PROJETSID_pays.length) {
					if (length < 1024 && commonByteArray_PROJETSID_pays.length == 0) {
						commonByteArray_PROJETSID_pays = new byte[1024];
					} else {
						commonByteArray_PROJETSID_pays = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_PROJETSID_pays, 0, length);
				strReturn = new String(commonByteArray_PROJETSID_pays, 0, length, utf8Charset);
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

			synchronized (commonByteArrayLock_PROJETSID_pays) {

				try {

					int length = 0;

					this.Country = readString(dis);

					this.Country_Name = readString(dis);

					this.Country_ID = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_PROJETSID_pays) {

				try {

					int length = 0;

					this.Country = readString(dis);

					this.Country_Name = readString(dis);

					this.Country_ID = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// String

				writeString(this.Country, dos);

				// String

				writeString(this.Country_Name, dos);

				// String

				writeString(this.Country_ID, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// String

				writeString(this.Country, dos);

				// String

				writeString(this.Country_Name, dos);

				// String

				writeString(this.Country_ID, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("Country=" + Country);
			sb.append(",Country_Name=" + Country_Name);
			sb.append(",Country_ID=" + Country_ID);
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row8Struct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.Country, other.Country);
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
		final static byte[] commonByteArrayLock_PROJETSID_pays = new byte[0];
		static byte[] commonByteArray_PROJETSID_pays = new byte[0];
		protected static final int DEFAULT_HASHCODE = 1;
		protected static final int PRIME = 31;
		protected int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		public String loopKey;

		public String Country;

		public String getCountry() {
			return this.Country;
		}

		public String Country_Name;

		public String getCountry_Name() {
			return this.Country_Name;
		}

		public String Country_ID;

		public String getCountry_ID() {
			return this.Country_ID;
		}

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime * result + ((this.Country == null) ? 0 : this.Country.hashCode());

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

			if (this.Country == null) {
				if (other.Country != null)
					return false;

			} else if (!this.Country.equals(other.Country))

				return false;

			return true;
		}

		public void copyDataTo(row7Struct other) {

			other.Country = this.Country;
			other.Country_Name = this.Country_Name;
			other.Country_ID = this.Country_ID;

		}

		public void copyKeysDataTo(row7Struct other) {

			other.Country = this.Country;

		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJETSID_pays.length) {
					if (length < 1024 && commonByteArray_PROJETSID_pays.length == 0) {
						commonByteArray_PROJETSID_pays = new byte[1024];
					} else {
						commonByteArray_PROJETSID_pays = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_PROJETSID_pays, 0, length);
				strReturn = new String(commonByteArray_PROJETSID_pays, 0, length, utf8Charset);
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
				if (length > commonByteArray_PROJETSID_pays.length) {
					if (length < 1024 && commonByteArray_PROJETSID_pays.length == 0) {
						commonByteArray_PROJETSID_pays = new byte[1024];
					} else {
						commonByteArray_PROJETSID_pays = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_PROJETSID_pays, 0, length);
				strReturn = new String(commonByteArray_PROJETSID_pays, 0, length, utf8Charset);
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

			synchronized (commonByteArrayLock_PROJETSID_pays) {

				try {

					int length = 0;

					this.Country = readString(dis);

					this.Country_Name = readString(dis);

					this.Country_ID = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_PROJETSID_pays) {

				try {

					int length = 0;

					this.Country = readString(dis);

					this.Country_Name = readString(dis);

					this.Country_ID = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// String

				writeString(this.Country, dos);

				// String

				writeString(this.Country_Name, dos);

				// String

				writeString(this.Country_ID, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// String

				writeString(this.Country, dos);

				// String

				writeString(this.Country_Name, dos);

				// String

				writeString(this.Country_ID, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("Country=" + Country);
			sb.append(",Country_Name=" + Country_Name);
			sb.append(",Country_ID=" + Country_ID);
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row7Struct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.Country, other.Country);
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
		final static byte[] commonByteArrayLock_PROJETSID_pays = new byte[0];
		static byte[] commonByteArray_PROJETSID_pays = new byte[0];
		protected static final int DEFAULT_HASHCODE = 1;
		protected static final int PRIME = 31;
		protected int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		public String loopKey;

		public String Country;

		public String getCountry() {
			return this.Country;
		}

		public String Country_Name;

		public String getCountry_Name() {
			return this.Country_Name;
		}

		public String Country_ID;

		public String getCountry_ID() {
			return this.Country_ID;
		}

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime * result + ((this.Country == null) ? 0 : this.Country.hashCode());

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

			if (this.Country == null) {
				if (other.Country != null)
					return false;

			} else if (!this.Country.equals(other.Country))

				return false;

			return true;
		}

		public void copyDataTo(row6Struct other) {

			other.Country = this.Country;
			other.Country_Name = this.Country_Name;
			other.Country_ID = this.Country_ID;

		}

		public void copyKeysDataTo(row6Struct other) {

			other.Country = this.Country;

		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJETSID_pays.length) {
					if (length < 1024 && commonByteArray_PROJETSID_pays.length == 0) {
						commonByteArray_PROJETSID_pays = new byte[1024];
					} else {
						commonByteArray_PROJETSID_pays = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_PROJETSID_pays, 0, length);
				strReturn = new String(commonByteArray_PROJETSID_pays, 0, length, utf8Charset);
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
				if (length > commonByteArray_PROJETSID_pays.length) {
					if (length < 1024 && commonByteArray_PROJETSID_pays.length == 0) {
						commonByteArray_PROJETSID_pays = new byte[1024];
					} else {
						commonByteArray_PROJETSID_pays = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_PROJETSID_pays, 0, length);
				strReturn = new String(commonByteArray_PROJETSID_pays, 0, length, utf8Charset);
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

			synchronized (commonByteArrayLock_PROJETSID_pays) {

				try {

					int length = 0;

					this.Country = readString(dis);

					this.Country_Name = readString(dis);

					this.Country_ID = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_PROJETSID_pays) {

				try {

					int length = 0;

					this.Country = readString(dis);

					this.Country_Name = readString(dis);

					this.Country_ID = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// String

				writeString(this.Country, dos);

				// String

				writeString(this.Country_Name, dos);

				// String

				writeString(this.Country_ID, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// String

				writeString(this.Country, dos);

				// String

				writeString(this.Country_Name, dos);

				// String

				writeString(this.Country_ID, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("Country=" + Country);
			sb.append(",Country_Name=" + Country_Name);
			sb.append(",Country_ID=" + Country_ID);
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row6Struct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.Country, other.Country);
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
		final static byte[] commonByteArrayLock_PROJETSID_pays = new byte[0];
		static byte[] commonByteArray_PROJETSID_pays = new byte[0];

		public String Order_ID;

		public String getOrder_ID() {
			return this.Order_ID;
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

		public String Order_Item_Num;

		public String getOrder_Item_Num() {
			return this.Order_Item_Num;
		}

		public Integer Quantity;

		public Integer getQuantity() {
			return this.Quantity;
		}

		public String Total_Retail_Price;

		public String getTotal_Retail_Price() {
			return this.Total_Retail_Price;
		}

		public String CostPrice_Per_Unit;

		public String getCostPrice_Per_Unit() {
			return this.CostPrice_Per_Unit;
		}

		public String Product_ID;

		public String getProduct_ID() {
			return this.Product_ID;
		}

		public String Product_Name;

		public String getProduct_Name() {
			return this.Product_Name;
		}

		public String Product_Level;

		public String getProduct_Level() {
			return this.Product_Level;
		}

		public String Product_Level_Name;

		public String getProduct_Level_Name() {
			return this.Product_Level_Name;
		}

		public String Product_Ref_ID;

		public String getProduct_Ref_ID() {
			return this.Product_Ref_ID;
		}

		public List Customer_ID;

		public List getCustomer_ID() {
			return this.Customer_ID;
		}

		public Character Gender;

		public Character getGender() {
			return this.Gender;
		}

		public String Birth_Date;

		public String getBirth_Date() {
			return this.Birth_Date;
		}

		public String Customer_Type_ID;

		public String getCustomer_Type_ID() {
			return this.Customer_Type_ID;
		}

		public String Customer_Type;

		public String getCustomer_Type() {
			return this.Customer_Type;
		}

		public String Customer_Group_ID;

		public String getCustomer_Group_ID() {
			return this.Customer_Group_ID;
		}

		public String Customer_Group;

		public String getCustomer_Group() {
			return this.Customer_Group;
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

		public String Emp_Hire_Date;

		public String getEmp_Hire_Date() {
			return this.Emp_Hire_Date;
		}

		public String Employee_Country;

		public String getEmployee_Country() {
			return this.Employee_Country;
		}

		public String Employee_Org_Level;

		public String getEmployee_Org_Level() {
			return this.Employee_Org_Level;
		}

		public String Org_Level_Name;

		public String getOrg_Level_Name() {
			return this.Org_Level_Name;
		}

		public String Country;

		public String getCountry() {
			return this.Country;
		}

		public String Country_Name;

		public String getCountry_Name() {
			return this.Country_Name;
		}

		public String Country_ID;

		public String getCountry_ID() {
			return this.Country_ID;
		}

		public String Country_FormerName;

		public String getCountry_FormerName() {
			return this.Country_FormerName;
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJETSID_pays.length) {
					if (length < 1024 && commonByteArray_PROJETSID_pays.length == 0) {
						commonByteArray_PROJETSID_pays = new byte[1024];
					} else {
						commonByteArray_PROJETSID_pays = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_PROJETSID_pays, 0, length);
				strReturn = new String(commonByteArray_PROJETSID_pays, 0, length, utf8Charset);
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
				if (length > commonByteArray_PROJETSID_pays.length) {
					if (length < 1024 && commonByteArray_PROJETSID_pays.length == 0) {
						commonByteArray_PROJETSID_pays = new byte[1024];
					} else {
						commonByteArray_PROJETSID_pays = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_PROJETSID_pays, 0, length);
				strReturn = new String(commonByteArray_PROJETSID_pays, 0, length, utf8Charset);
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

			synchronized (commonByteArrayLock_PROJETSID_pays) {

				try {

					int length = 0;

					this.Order_ID = readString(dis);

					this.Order_Type = readString(dis);

					this.Order_Date = readString(dis);

					this.Delivery_Date = readString(dis);

					this.Order_Item_Num = readString(dis);

					this.Quantity = readInteger(dis);

					this.Total_Retail_Price = readString(dis);

					this.CostPrice_Per_Unit = readString(dis);

					this.Product_ID = readString(dis);

					this.Product_Name = readString(dis);

					this.Product_Level = readString(dis);

					this.Product_Level_Name = readString(dis);

					this.Product_Ref_ID = readString(dis);

					this.Customer_ID = (List) dis.readObject();

					length = dis.readByte();
					if (length == -1) {
						this.Gender = null;
					} else {
						this.Gender = dis.readChar();
					}

					this.Birth_Date = readString(dis);

					this.Customer_Type_ID = readString(dis);

					this.Customer_Type = readString(dis);

					this.Customer_Group_ID = readString(dis);

					this.Customer_Group = readString(dis);

					this.Employee_ID = readString(dis);

					this.Employee_Job_Title = readString(dis);

					this.Employee_Salary = readInteger(dis);

					this.Employee_Gender = readString(dis);

					this.Emp_Hire_Date = readString(dis);

					this.Employee_Country = readString(dis);

					this.Employee_Org_Level = readString(dis);

					this.Org_Level_Name = readString(dis);

					this.Country = readString(dis);

					this.Country_Name = readString(dis);

					this.Country_ID = readString(dis);

					this.Country_FormerName = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				} catch (ClassNotFoundException eCNFE) {
					throw new RuntimeException(eCNFE);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_PROJETSID_pays) {

				try {

					int length = 0;

					this.Order_ID = readString(dis);

					this.Order_Type = readString(dis);

					this.Order_Date = readString(dis);

					this.Delivery_Date = readString(dis);

					this.Order_Item_Num = readString(dis);

					this.Quantity = readInteger(dis);

					this.Total_Retail_Price = readString(dis);

					this.CostPrice_Per_Unit = readString(dis);

					this.Product_ID = readString(dis);

					this.Product_Name = readString(dis);

					this.Product_Level = readString(dis);

					this.Product_Level_Name = readString(dis);

					this.Product_Ref_ID = readString(dis);

					this.Customer_ID = (List) dis.readObject();

					length = dis.readByte();
					if (length == -1) {
						this.Gender = null;
					} else {
						this.Gender = dis.readChar();
					}

					this.Birth_Date = readString(dis);

					this.Customer_Type_ID = readString(dis);

					this.Customer_Type = readString(dis);

					this.Customer_Group_ID = readString(dis);

					this.Customer_Group = readString(dis);

					this.Employee_ID = readString(dis);

					this.Employee_Job_Title = readString(dis);

					this.Employee_Salary = readInteger(dis);

					this.Employee_Gender = readString(dis);

					this.Emp_Hire_Date = readString(dis);

					this.Employee_Country = readString(dis);

					this.Employee_Org_Level = readString(dis);

					this.Org_Level_Name = readString(dis);

					this.Country = readString(dis);

					this.Country_Name = readString(dis);

					this.Country_ID = readString(dis);

					this.Country_FormerName = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				} catch (ClassNotFoundException eCNFE) {
					throw new RuntimeException(eCNFE);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// String

				writeString(this.Order_ID, dos);

				// String

				writeString(this.Order_Type, dos);

				// String

				writeString(this.Order_Date, dos);

				// String

				writeString(this.Delivery_Date, dos);

				// String

				writeString(this.Order_Item_Num, dos);

				// Integer

				writeInteger(this.Quantity, dos);

				// String

				writeString(this.Total_Retail_Price, dos);

				// String

				writeString(this.CostPrice_Per_Unit, dos);

				// String

				writeString(this.Product_ID, dos);

				// String

				writeString(this.Product_Name, dos);

				// String

				writeString(this.Product_Level, dos);

				// String

				writeString(this.Product_Level_Name, dos);

				// String

				writeString(this.Product_Ref_ID, dos);

				// List

				dos.writeObject(this.Customer_ID);

				// Character

				if (this.Gender == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeChar(this.Gender);
				}

				// String

				writeString(this.Birth_Date, dos);

				// String

				writeString(this.Customer_Type_ID, dos);

				// String

				writeString(this.Customer_Type, dos);

				// String

				writeString(this.Customer_Group_ID, dos);

				// String

				writeString(this.Customer_Group, dos);

				// String

				writeString(this.Employee_ID, dos);

				// String

				writeString(this.Employee_Job_Title, dos);

				// Integer

				writeInteger(this.Employee_Salary, dos);

				// String

				writeString(this.Employee_Gender, dos);

				// String

				writeString(this.Emp_Hire_Date, dos);

				// String

				writeString(this.Employee_Country, dos);

				// String

				writeString(this.Employee_Org_Level, dos);

				// String

				writeString(this.Org_Level_Name, dos);

				// String

				writeString(this.Country, dos);

				// String

				writeString(this.Country_Name, dos);

				// String

				writeString(this.Country_ID, dos);

				// String

				writeString(this.Country_FormerName, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// String

				writeString(this.Order_ID, dos);

				// String

				writeString(this.Order_Type, dos);

				// String

				writeString(this.Order_Date, dos);

				// String

				writeString(this.Delivery_Date, dos);

				// String

				writeString(this.Order_Item_Num, dos);

				// Integer

				writeInteger(this.Quantity, dos);

				// String

				writeString(this.Total_Retail_Price, dos);

				// String

				writeString(this.CostPrice_Per_Unit, dos);

				// String

				writeString(this.Product_ID, dos);

				// String

				writeString(this.Product_Name, dos);

				// String

				writeString(this.Product_Level, dos);

				// String

				writeString(this.Product_Level_Name, dos);

				// String

				writeString(this.Product_Ref_ID, dos);

				// List

				dos.writeObject(this.Customer_ID);

				// Character

				if (this.Gender == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeChar(this.Gender);
				}

				// String

				writeString(this.Birth_Date, dos);

				// String

				writeString(this.Customer_Type_ID, dos);

				// String

				writeString(this.Customer_Type, dos);

				// String

				writeString(this.Customer_Group_ID, dos);

				// String

				writeString(this.Customer_Group, dos);

				// String

				writeString(this.Employee_ID, dos);

				// String

				writeString(this.Employee_Job_Title, dos);

				// Integer

				writeInteger(this.Employee_Salary, dos);

				// String

				writeString(this.Employee_Gender, dos);

				// String

				writeString(this.Emp_Hire_Date, dos);

				// String

				writeString(this.Employee_Country, dos);

				// String

				writeString(this.Employee_Org_Level, dos);

				// String

				writeString(this.Org_Level_Name, dos);

				// String

				writeString(this.Country, dos);

				// String

				writeString(this.Country_Name, dos);

				// String

				writeString(this.Country_ID, dos);

				// String

				writeString(this.Country_FormerName, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("Order_ID=" + Order_ID);
			sb.append(",Order_Type=" + Order_Type);
			sb.append(",Order_Date=" + Order_Date);
			sb.append(",Delivery_Date=" + Delivery_Date);
			sb.append(",Order_Item_Num=" + Order_Item_Num);
			sb.append(",Quantity=" + String.valueOf(Quantity));
			sb.append(",Total_Retail_Price=" + Total_Retail_Price);
			sb.append(",CostPrice_Per_Unit=" + CostPrice_Per_Unit);
			sb.append(",Product_ID=" + Product_ID);
			sb.append(",Product_Name=" + Product_Name);
			sb.append(",Product_Level=" + Product_Level);
			sb.append(",Product_Level_Name=" + Product_Level_Name);
			sb.append(",Product_Ref_ID=" + Product_Ref_ID);
			sb.append(",Customer_ID=" + String.valueOf(Customer_ID));
			sb.append(",Gender=" + String.valueOf(Gender));
			sb.append(",Birth_Date=" + Birth_Date);
			sb.append(",Customer_Type_ID=" + Customer_Type_ID);
			sb.append(",Customer_Type=" + Customer_Type);
			sb.append(",Customer_Group_ID=" + Customer_Group_ID);
			sb.append(",Customer_Group=" + Customer_Group);
			sb.append(",Employee_ID=" + Employee_ID);
			sb.append(",Employee_Job_Title=" + Employee_Job_Title);
			sb.append(",Employee_Salary=" + String.valueOf(Employee_Salary));
			sb.append(",Employee_Gender=" + Employee_Gender);
			sb.append(",Emp_Hire_Date=" + Emp_Hire_Date);
			sb.append(",Employee_Country=" + Employee_Country);
			sb.append(",Employee_Org_Level=" + Employee_Org_Level);
			sb.append(",Org_Level_Name=" + Org_Level_Name);
			sb.append(",Country=" + Country);
			sb.append(",Country_Name=" + Country_Name);
			sb.append(",Country_ID=" + Country_ID);
			sb.append(",Country_FormerName=" + Country_FormerName);
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

	public void tFileInputDelimited_2Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tFileInputDelimited_2_SUBPROCESS_STATE", 0);

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

				row5Struct row5 = new row5Struct();
				row6Struct row6 = new row6Struct();
				row7Struct row7 = new row7Struct();
				row7Struct row8 = row7;

				/**
				 * [tFileOutputDelimited_2 begin ] start
				 */

				ok_Hash.put("tFileOutputDelimited_2", false);
				start_Hash.put("tFileOutputDelimited_2", System.currentTimeMillis());

				currentComponent = "tFileOutputDelimited_2";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "row8");
				}

				int tos_count_tFileOutputDelimited_2 = 0;

				String fileName_tFileOutputDelimited_2 = "";
				fileName_tFileOutputDelimited_2 = (new java.io.File(
						context.folderName + "/files/TablesToDump/Country.csv")).getAbsolutePath().replace("\\", "/");
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
					outtFileOutputDelimited_2.write("Country");
					outtFileOutputDelimited_2.write(OUT_DELIM_tFileOutputDelimited_2);
					outtFileOutputDelimited_2.write("Country_Name");
					outtFileOutputDelimited_2.write(OUT_DELIM_tFileOutputDelimited_2);
					outtFileOutputDelimited_2.write("Country_ID");
					outtFileOutputDelimited_2.write(OUT_DELIM_ROWSEP_tFileOutputDelimited_2);
					outtFileOutputDelimited_2.flush();
				}

				resourceMap.put("out_tFileOutputDelimited_2", outtFileOutputDelimited_2);
				resourceMap.put("nb_line_tFileOutputDelimited_2", nb_line_tFileOutputDelimited_2);

				/**
				 * [tFileOutputDelimited_2 begin ] stop
				 */

				/**
				 * [tLogRow_3 begin ] start
				 */

				ok_Hash.put("tLogRow_3", false);
				start_Hash.put("tLogRow_3", System.currentTimeMillis());

				currentComponent = "tLogRow_3";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "row7");
				}

				int tos_count_tLogRow_3 = 0;

				///////////////////////

				final String OUTPUT_FIELD_SEPARATOR_tLogRow_3 = "|";
				java.io.PrintStream consoleOut_tLogRow_3 = null;

				StringBuilder strBuffer_tLogRow_3 = null;
				int nb_line_tLogRow_3 = 0;
///////////////////////    			

				/**
				 * [tLogRow_3 begin ] stop
				 */

				/**
				 * [tUniqRow_1 begin ] start
				 */

				ok_Hash.put("tUniqRow_1", false);
				start_Hash.put("tUniqRow_1", System.currentTimeMillis());

				currentComponent = "tUniqRow_1";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "row6");
				}

				int tos_count_tUniqRow_1 = 0;

				class KeyStruct_tUniqRow_1 {

					private static final int DEFAULT_HASHCODE = 1;
					private static final int PRIME = 31;
					private int hashCode = DEFAULT_HASHCODE;
					public boolean hashCodeDirty = true;

					String Country_ID;

					@Override
					public int hashCode() {
						if (this.hashCodeDirty) {
							final int prime = PRIME;
							int result = DEFAULT_HASHCODE;

							result = prime * result + ((this.Country_ID == null) ? 0 : this.Country_ID.hashCode());

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
						final KeyStruct_tUniqRow_1 other = (KeyStruct_tUniqRow_1) obj;

						if (this.Country_ID == null) {
							if (other.Country_ID != null)
								return false;

						} else if (!this.Country_ID.equals(other.Country_ID))

							return false;

						return true;
					}

				}

				int nb_uniques_tUniqRow_1 = 0;
				int nb_duplicates_tUniqRow_1 = 0;
				KeyStruct_tUniqRow_1 finder_tUniqRow_1 = new KeyStruct_tUniqRow_1();
				java.util.Set<KeyStruct_tUniqRow_1> keystUniqRow_1 = new java.util.HashSet<KeyStruct_tUniqRow_1>();

				/**
				 * [tUniqRow_1 begin ] stop
				 */

				/**
				 * [tFilterColumns_2 begin ] start
				 */

				ok_Hash.put("tFilterColumns_2", false);
				start_Hash.put("tFilterColumns_2", System.currentTimeMillis());

				currentComponent = "tFilterColumns_2";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "row5");
				}

				int tos_count_tFilterColumns_2 = 0;

				int nb_line_tFilterColumns_2 = 0;

				/**
				 * [tFilterColumns_2 begin ] stop
				 */

				/**
				 * [tFileInputDelimited_2 begin ] start
				 */

				ok_Hash.put("tFileInputDelimited_2", false);
				start_Hash.put("tFileInputDelimited_2", System.currentTimeMillis());

				currentComponent = "tFileInputDelimited_2";

				int tos_count_tFileInputDelimited_2 = 0;

				final routines.system.RowState rowstate_tFileInputDelimited_2 = new routines.system.RowState();

				int nb_line_tFileInputDelimited_2 = 0;
				int footer_tFileInputDelimited_2 = 1;
				int totalLinetFileInputDelimited_2 = 0;
				int limittFileInputDelimited_2 = -1;
				int lastLinetFileInputDelimited_2 = -1;

				char fieldSeparator_tFileInputDelimited_2[] = null;

				// support passing value (property: Field Separator) by 'context.fs' or
				// 'globalMap.get("fs")'.
				if (((String) ";").length() > 0) {
					fieldSeparator_tFileInputDelimited_2 = ((String) ";").toCharArray();
				} else {
					throw new IllegalArgumentException("Field Separator must be assigned a char.");
				}

				char rowSeparator_tFileInputDelimited_2[] = null;

				// support passing value (property: Row Separator) by 'context.rs' or
				// 'globalMap.get("rs")'.
				if (((String) "\n").length() > 0) {
					rowSeparator_tFileInputDelimited_2 = ((String) "\n").toCharArray();
				} else {
					throw new IllegalArgumentException("Row Separator must be assigned a char.");
				}

				Object filename_tFileInputDelimited_2 = /** Start field tFileInputDelimited_2:FILENAME */
						context.folderName + "/files/BDD_CASIOP.csv"/** End field tFileInputDelimited_2:FILENAME */
				;
				com.talend.csv.CSVReader csvReadertFileInputDelimited_2 = null;

				try {

					String[] rowtFileInputDelimited_2 = null;
					int currentLinetFileInputDelimited_2 = 0;
					int outputLinetFileInputDelimited_2 = 0;
					try {// TD110 begin
						if (filename_tFileInputDelimited_2 instanceof java.io.InputStream) {

							int footer_value_tFileInputDelimited_2 = 1;
							if (footer_value_tFileInputDelimited_2 > 0) {
								throw new java.lang.Exception(
										"When the input source is a stream,footer shouldn't be bigger than 0.");
							}

							csvReadertFileInputDelimited_2 = new com.talend.csv.CSVReader(
									(java.io.InputStream) filename_tFileInputDelimited_2,
									fieldSeparator_tFileInputDelimited_2[0], "ISO-8859-15");
						} else {
							csvReadertFileInputDelimited_2 = new com.talend.csv.CSVReader(
									String.valueOf(filename_tFileInputDelimited_2),
									fieldSeparator_tFileInputDelimited_2[0], "ISO-8859-15");
						}

						csvReadertFileInputDelimited_2.setTrimWhitespace(false);
						if ((rowSeparator_tFileInputDelimited_2[0] != '\n')
								&& (rowSeparator_tFileInputDelimited_2[0] != '\r'))
							csvReadertFileInputDelimited_2.setLineEnd("" + rowSeparator_tFileInputDelimited_2[0]);

						csvReadertFileInputDelimited_2.setQuoteChar('"');

						csvReadertFileInputDelimited_2.setEscapeChar(csvReadertFileInputDelimited_2.getQuoteChar());

						if (footer_tFileInputDelimited_2 > 0) {
							for (totalLinetFileInputDelimited_2 = 0; totalLinetFileInputDelimited_2 < 1; totalLinetFileInputDelimited_2++) {
								csvReadertFileInputDelimited_2.readNext();
							}
							csvReadertFileInputDelimited_2.setSkipEmptyRecords(true);
							while (csvReadertFileInputDelimited_2.readNext()) {

								rowtFileInputDelimited_2 = csvReadertFileInputDelimited_2.getValues();
								if (!(rowtFileInputDelimited_2.length == 1
										&& ("\015").equals(rowtFileInputDelimited_2[0]))) {// empty line when row
																							// separator is '\n'

									totalLinetFileInputDelimited_2++;

								}

							}
							int lastLineTemptFileInputDelimited_2 = totalLinetFileInputDelimited_2
									- footer_tFileInputDelimited_2 < 0 ? 0
											: totalLinetFileInputDelimited_2 - footer_tFileInputDelimited_2;
							if (lastLinetFileInputDelimited_2 > 0) {
								lastLinetFileInputDelimited_2 = lastLinetFileInputDelimited_2 < lastLineTemptFileInputDelimited_2
										? lastLinetFileInputDelimited_2
										: lastLineTemptFileInputDelimited_2;
							} else {
								lastLinetFileInputDelimited_2 = lastLineTemptFileInputDelimited_2;
							}

							csvReadertFileInputDelimited_2.close();
							if (filename_tFileInputDelimited_2 instanceof java.io.InputStream) {
								csvReadertFileInputDelimited_2 = new com.talend.csv.CSVReader(
										(java.io.InputStream) filename_tFileInputDelimited_2,
										fieldSeparator_tFileInputDelimited_2[0], "ISO-8859-15");
							} else {
								csvReadertFileInputDelimited_2 = new com.talend.csv.CSVReader(
										String.valueOf(filename_tFileInputDelimited_2),
										fieldSeparator_tFileInputDelimited_2[0], "ISO-8859-15");
							}
							csvReadertFileInputDelimited_2.setTrimWhitespace(false);
							if ((rowSeparator_tFileInputDelimited_2[0] != '\n')
									&& (rowSeparator_tFileInputDelimited_2[0] != '\r'))
								csvReadertFileInputDelimited_2.setLineEnd("" + rowSeparator_tFileInputDelimited_2[0]);

							csvReadertFileInputDelimited_2.setQuoteChar('"');

							csvReadertFileInputDelimited_2.setEscapeChar(csvReadertFileInputDelimited_2.getQuoteChar());

						}

						if (limittFileInputDelimited_2 != 0) {
							for (currentLinetFileInputDelimited_2 = 0; currentLinetFileInputDelimited_2 < 1; currentLinetFileInputDelimited_2++) {
								csvReadertFileInputDelimited_2.readNext();
							}
						}
						csvReadertFileInputDelimited_2.setSkipEmptyRecords(true);

					} catch (java.lang.Exception e) {
						globalMap.put("tFileInputDelimited_2_ERROR_MESSAGE", e.getMessage());

						System.err.println(e.getMessage());

					} // TD110 end

					while (limittFileInputDelimited_2 != 0 && csvReadertFileInputDelimited_2 != null
							&& csvReadertFileInputDelimited_2.readNext()) {
						rowstate_tFileInputDelimited_2.reset();

						rowtFileInputDelimited_2 = csvReadertFileInputDelimited_2.getValues();

						if (rowtFileInputDelimited_2.length == 1 && ("\015").equals(rowtFileInputDelimited_2[0])) {// empty
																													// line
																													// when
																													// row
																													// separator
																													// is
																													// '\n'
							continue;
						}

						currentLinetFileInputDelimited_2++;

						if (lastLinetFileInputDelimited_2 > -1
								&& currentLinetFileInputDelimited_2 > lastLinetFileInputDelimited_2) {
							break;
						}
						outputLinetFileInputDelimited_2++;
						if (limittFileInputDelimited_2 > 0
								&& outputLinetFileInputDelimited_2 > limittFileInputDelimited_2) {
							break;
						}

						row5 = null;

						boolean whetherReject_tFileInputDelimited_2 = false;
						row5 = new row5Struct();
						try {

							char fieldSeparator_tFileInputDelimited_2_ListType[] = null;
							// support passing value (property: Field Separator) by 'context.fs' or
							// 'globalMap.get("fs")'.
							if (((String) ";").length() > 0) {
								fieldSeparator_tFileInputDelimited_2_ListType = ((String) ";").toCharArray();
							} else {
								throw new IllegalArgumentException("Field Separator must be assigned a char.");
							}
							if (rowtFileInputDelimited_2.length == 1 && ("\015").equals(rowtFileInputDelimited_2[0])) {// empty
																														// line
																														// when
																														// row
																														// separator
																														// is
																														// '\n'

								row5.Order_ID = null;

								row5.Order_Type = null;

								row5.Order_Date = null;

								row5.Delivery_Date = null;

								row5.Order_Item_Num = null;

								row5.Quantity = null;

								row5.Total_Retail_Price = null;

								row5.CostPrice_Per_Unit = null;

								row5.Product_ID = null;

								row5.Product_Name = null;

								row5.Product_Level = null;

								row5.Product_Level_Name = null;

								row5.Product_Ref_ID = null;

								row5.Customer_ID = null;

								row5.Gender = null;

								row5.Birth_Date = null;

								row5.Customer_Type_ID = null;

								row5.Customer_Type = null;

								row5.Customer_Group_ID = null;

								row5.Customer_Group = null;

								row5.Employee_ID = null;

								row5.Employee_Job_Title = null;

								row5.Employee_Salary = null;

								row5.Employee_Gender = null;

								row5.Emp_Hire_Date = null;

								row5.Employee_Country = null;

								row5.Employee_Org_Level = null;

								row5.Org_Level_Name = null;

								row5.Country = null;

								row5.Country_Name = null;

								row5.Country_ID = null;

								row5.Country_FormerName = null;

							} else {

								int columnIndexWithD_tFileInputDelimited_2 = 0; // Column Index

								columnIndexWithD_tFileInputDelimited_2 = 0;

								if (columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length) {

									row5.Order_ID = rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2];

								} else {

									row5.Order_ID = null;

								}

								columnIndexWithD_tFileInputDelimited_2 = 1;

								if (columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length) {

									row5.Order_Type = rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2];

								} else {

									row5.Order_Type = null;

								}

								columnIndexWithD_tFileInputDelimited_2 = 2;

								if (columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length) {

									row5.Order_Date = rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2];

								} else {

									row5.Order_Date = null;

								}

								columnIndexWithD_tFileInputDelimited_2 = 3;

								if (columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length) {

									row5.Delivery_Date = rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2];

								} else {

									row5.Delivery_Date = null;

								}

								columnIndexWithD_tFileInputDelimited_2 = 4;

								if (columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length) {

									row5.Order_Item_Num = rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2];

								} else {

									row5.Order_Item_Num = null;

								}

								columnIndexWithD_tFileInputDelimited_2 = 5;

								if (columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length) {

									if (rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2].length() > 0) {
										try {

											row5.Quantity = ParserUtils.parseTo_Integer(
													rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2]);

										} catch (java.lang.Exception ex_tFileInputDelimited_2) {
											globalMap.put("tFileInputDelimited_2_ERROR_MESSAGE",
													ex_tFileInputDelimited_2.getMessage());
											rowstate_tFileInputDelimited_2.setException(new RuntimeException(String
													.format("Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
															"Quantity", "row5",
															rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2],
															ex_tFileInputDelimited_2),
													ex_tFileInputDelimited_2));
										}
									} else {

										row5.Quantity = null;

									}

								} else {

									row5.Quantity = null;

								}

								columnIndexWithD_tFileInputDelimited_2 = 6;

								if (columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length) {

									row5.Total_Retail_Price = rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2];

								} else {

									row5.Total_Retail_Price = null;

								}

								columnIndexWithD_tFileInputDelimited_2 = 7;

								if (columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length) {

									row5.CostPrice_Per_Unit = rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2];

								} else {

									row5.CostPrice_Per_Unit = null;

								}

								columnIndexWithD_tFileInputDelimited_2 = 8;

								if (columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length) {

									row5.Product_ID = rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2];

								} else {

									row5.Product_ID = null;

								}

								columnIndexWithD_tFileInputDelimited_2 = 9;

								if (columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length) {

									row5.Product_Name = rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2];

								} else {

									row5.Product_Name = null;

								}

								columnIndexWithD_tFileInputDelimited_2 = 10;

								if (columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length) {

									row5.Product_Level = rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2];

								} else {

									row5.Product_Level = null;

								}

								columnIndexWithD_tFileInputDelimited_2 = 11;

								if (columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length) {

									row5.Product_Level_Name = rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2];

								} else {

									row5.Product_Level_Name = null;

								}

								columnIndexWithD_tFileInputDelimited_2 = 12;

								if (columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length) {

									row5.Product_Ref_ID = rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2];

								} else {

									row5.Product_Ref_ID = null;

								}

								columnIndexWithD_tFileInputDelimited_2 = 13;

								if (columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length) {

									if (rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2].length() > 0) {
										try {

											row5.Customer_ID = ParserUtils.parseTo_List(
													rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2],
													String.valueOf(fieldSeparator_tFileInputDelimited_2_ListType[0]));

										} catch (java.lang.Exception ex_tFileInputDelimited_2) {
											globalMap.put("tFileInputDelimited_2_ERROR_MESSAGE",
													ex_tFileInputDelimited_2.getMessage());
											rowstate_tFileInputDelimited_2.setException(new RuntimeException(String
													.format("Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
															"Customer_ID", "row5",
															rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2],
															ex_tFileInputDelimited_2),
													ex_tFileInputDelimited_2));
										}
									} else {

										row5.Customer_ID = null;

									}

								} else {

									row5.Customer_ID = null;

								}

								columnIndexWithD_tFileInputDelimited_2 = 14;

								if (columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length) {

									if (rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2].length() > 0) {
										try {

											row5.Gender = ParserUtils.parseTo_Character(
													rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2]);

										} catch (java.lang.Exception ex_tFileInputDelimited_2) {
											globalMap.put("tFileInputDelimited_2_ERROR_MESSAGE",
													ex_tFileInputDelimited_2.getMessage());
											rowstate_tFileInputDelimited_2.setException(new RuntimeException(String
													.format("Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
															"Gender", "row5",
															rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2],
															ex_tFileInputDelimited_2),
													ex_tFileInputDelimited_2));
										}
									} else {

										row5.Gender = null;

									}

								} else {

									row5.Gender = null;

								}

								columnIndexWithD_tFileInputDelimited_2 = 15;

								if (columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length) {

									row5.Birth_Date = rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2];

								} else {

									row5.Birth_Date = null;

								}

								columnIndexWithD_tFileInputDelimited_2 = 16;

								if (columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length) {

									row5.Customer_Type_ID = rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2];

								} else {

									row5.Customer_Type_ID = null;

								}

								columnIndexWithD_tFileInputDelimited_2 = 17;

								if (columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length) {

									row5.Customer_Type = rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2];

								} else {

									row5.Customer_Type = null;

								}

								columnIndexWithD_tFileInputDelimited_2 = 18;

								if (columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length) {

									row5.Customer_Group_ID = rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2];

								} else {

									row5.Customer_Group_ID = null;

								}

								columnIndexWithD_tFileInputDelimited_2 = 19;

								if (columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length) {

									row5.Customer_Group = rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2];

								} else {

									row5.Customer_Group = null;

								}

								columnIndexWithD_tFileInputDelimited_2 = 20;

								if (columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length) {

									row5.Employee_ID = rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2];

								} else {

									row5.Employee_ID = null;

								}

								columnIndexWithD_tFileInputDelimited_2 = 21;

								if (columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length) {

									row5.Employee_Job_Title = rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2];

								} else {

									row5.Employee_Job_Title = null;

								}

								columnIndexWithD_tFileInputDelimited_2 = 22;

								if (columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length) {

									if (rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2].length() > 0) {
										try {

											row5.Employee_Salary = ParserUtils.parseTo_Integer(
													rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2]);

										} catch (java.lang.Exception ex_tFileInputDelimited_2) {
											globalMap.put("tFileInputDelimited_2_ERROR_MESSAGE",
													ex_tFileInputDelimited_2.getMessage());
											rowstate_tFileInputDelimited_2.setException(new RuntimeException(String
													.format("Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
															"Employee_Salary", "row5",
															rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2],
															ex_tFileInputDelimited_2),
													ex_tFileInputDelimited_2));
										}
									} else {

										row5.Employee_Salary = null;

									}

								} else {

									row5.Employee_Salary = null;

								}

								columnIndexWithD_tFileInputDelimited_2 = 23;

								if (columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length) {

									row5.Employee_Gender = rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2];

								} else {

									row5.Employee_Gender = null;

								}

								columnIndexWithD_tFileInputDelimited_2 = 24;

								if (columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length) {

									row5.Emp_Hire_Date = rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2];

								} else {

									row5.Emp_Hire_Date = null;

								}

								columnIndexWithD_tFileInputDelimited_2 = 25;

								if (columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length) {

									row5.Employee_Country = rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2];

								} else {

									row5.Employee_Country = null;

								}

								columnIndexWithD_tFileInputDelimited_2 = 26;

								if (columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length) {

									row5.Employee_Org_Level = rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2];

								} else {

									row5.Employee_Org_Level = null;

								}

								columnIndexWithD_tFileInputDelimited_2 = 27;

								if (columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length) {

									row5.Org_Level_Name = rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2];

								} else {

									row5.Org_Level_Name = null;

								}

								columnIndexWithD_tFileInputDelimited_2 = 28;

								if (columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length) {

									row5.Country = rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2];

								} else {

									row5.Country = null;

								}

								columnIndexWithD_tFileInputDelimited_2 = 29;

								if (columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length) {

									row5.Country_Name = rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2];

								} else {

									row5.Country_Name = null;

								}

								columnIndexWithD_tFileInputDelimited_2 = 30;

								if (columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length) {

									row5.Country_ID = rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2];

								} else {

									row5.Country_ID = null;

								}

								columnIndexWithD_tFileInputDelimited_2 = 31;

								if (columnIndexWithD_tFileInputDelimited_2 < rowtFileInputDelimited_2.length) {

									row5.Country_FormerName = rowtFileInputDelimited_2[columnIndexWithD_tFileInputDelimited_2];

								} else {

									row5.Country_FormerName = null;

								}

							}

							if (rowstate_tFileInputDelimited_2.getException() != null) {
								throw rowstate_tFileInputDelimited_2.getException();
							}

						} catch (java.lang.Exception e) {
							globalMap.put("tFileInputDelimited_2_ERROR_MESSAGE", e.getMessage());
							whetherReject_tFileInputDelimited_2 = true;

							System.err.println(e.getMessage());
							row5 = null;

							globalMap.put("tFileInputDelimited_2_ERROR_MESSAGE", e.getMessage());

						}

						/**
						 * [tFileInputDelimited_2 begin ] stop
						 */

						/**
						 * [tFileInputDelimited_2 main ] start
						 */

						currentComponent = "tFileInputDelimited_2";

						tos_count_tFileInputDelimited_2++;

						/**
						 * [tFileInputDelimited_2 main ] stop
						 */

						/**
						 * [tFileInputDelimited_2 process_data_begin ] start
						 */

						currentComponent = "tFileInputDelimited_2";

						/**
						 * [tFileInputDelimited_2 process_data_begin ] stop
						 */
// Start of branch "row5"
						if (row5 != null) {

							/**
							 * [tFilterColumns_2 main ] start
							 */

							currentComponent = "tFilterColumns_2";

							if (execStat) {
								runStat.updateStatOnConnection(iterateId, 1, 1

										, "row5"

								);
							}

							row6.Country = row5.Country;

							row6.Country_Name = row5.Country_Name;

							row6.Country_ID = row5.Country_ID;

							nb_line_tFilterColumns_2++;

							tos_count_tFilterColumns_2++;

							/**
							 * [tFilterColumns_2 main ] stop
							 */

							/**
							 * [tFilterColumns_2 process_data_begin ] start
							 */

							currentComponent = "tFilterColumns_2";

							/**
							 * [tFilterColumns_2 process_data_begin ] stop
							 */

							/**
							 * [tUniqRow_1 main ] start
							 */

							currentComponent = "tUniqRow_1";

							if (execStat) {
								runStat.updateStatOnConnection(iterateId, 1, 1

										, "row6"

								);
							}

							row7 = null;
							if (row6.Country_ID == null) {
								finder_tUniqRow_1.Country_ID = null;
							} else {
								finder_tUniqRow_1.Country_ID = row6.Country_ID.toLowerCase();
							}
							finder_tUniqRow_1.hashCodeDirty = true;
							if (!keystUniqRow_1.contains(finder_tUniqRow_1)) {
								KeyStruct_tUniqRow_1 new_tUniqRow_1 = new KeyStruct_tUniqRow_1();

								if (row6.Country_ID == null) {
									new_tUniqRow_1.Country_ID = null;
								} else {
									new_tUniqRow_1.Country_ID = row6.Country_ID.toLowerCase();
								}

								keystUniqRow_1.add(new_tUniqRow_1);
								if (row7 == null) {

									row7 = new row7Struct();
								}
								row7.Country = row6.Country;
								row7.Country_Name = row6.Country_Name;
								row7.Country_ID = row6.Country_ID;
								nb_uniques_tUniqRow_1++;
							} else {
								nb_duplicates_tUniqRow_1++;
							}

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
// Start of branch "row7"
							if (row7 != null) {

								/**
								 * [tLogRow_3 main ] start
								 */

								currentComponent = "tLogRow_3";

								if (execStat) {
									runStat.updateStatOnConnection(iterateId, 1, 1

											, "row7"

									);
								}

///////////////////////		

								strBuffer_tLogRow_3 = new StringBuilder();

								if (row7.Country != null) { //

									strBuffer_tLogRow_3.append(String.valueOf(row7.Country));

								} //

								strBuffer_tLogRow_3.append("|");

								if (row7.Country_Name != null) { //

									strBuffer_tLogRow_3.append(String.valueOf(row7.Country_Name));

								} //

								strBuffer_tLogRow_3.append("|");

								if (row7.Country_ID != null) { //

									strBuffer_tLogRow_3.append(String.valueOf(row7.Country_ID));

								} //

								if (globalMap.get("tLogRow_CONSOLE") != null) {
									consoleOut_tLogRow_3 = (java.io.PrintStream) globalMap.get("tLogRow_CONSOLE");
								} else {
									consoleOut_tLogRow_3 = new java.io.PrintStream(
											new java.io.BufferedOutputStream(System.out));
									globalMap.put("tLogRow_CONSOLE", consoleOut_tLogRow_3);
								}
								consoleOut_tLogRow_3.println(strBuffer_tLogRow_3.toString());
								consoleOut_tLogRow_3.flush();
								nb_line_tLogRow_3++;
//////

//////                    

///////////////////////    			

								row8 = row7;

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
								 * [tFileOutputDelimited_2 main ] start
								 */

								currentComponent = "tFileOutputDelimited_2";

								if (execStat) {
									runStat.updateStatOnConnection(iterateId, 1, 1

											, "row8"

									);
								}

								StringBuilder sb_tFileOutputDelimited_2 = new StringBuilder();
								if (row8.Country != null) {
									sb_tFileOutputDelimited_2.append(row8.Country);
								}
								sb_tFileOutputDelimited_2.append(OUT_DELIM_tFileOutputDelimited_2);
								if (row8.Country_Name != null) {
									sb_tFileOutputDelimited_2.append(row8.Country_Name);
								}
								sb_tFileOutputDelimited_2.append(OUT_DELIM_tFileOutputDelimited_2);
								if (row8.Country_ID != null) {
									sb_tFileOutputDelimited_2.append(row8.Country_ID);
								}
								sb_tFileOutputDelimited_2.append(OUT_DELIM_ROWSEP_tFileOutputDelimited_2);

								nb_line_tFileOutputDelimited_2++;
								resourceMap.put("nb_line_tFileOutputDelimited_2", nb_line_tFileOutputDelimited_2);

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

								/**
								 * [tLogRow_3 process_data_end ] start
								 */

								currentComponent = "tLogRow_3";

								/**
								 * [tLogRow_3 process_data_end ] stop
								 */

							} // End of branch "row7"

							/**
							 * [tUniqRow_1 process_data_end ] start
							 */

							currentComponent = "tUniqRow_1";

							/**
							 * [tUniqRow_1 process_data_end ] stop
							 */

							/**
							 * [tFilterColumns_2 process_data_end ] start
							 */

							currentComponent = "tFilterColumns_2";

							/**
							 * [tFilterColumns_2 process_data_end ] stop
							 */

						} // End of branch "row5"

						/**
						 * [tFileInputDelimited_2 process_data_end ] start
						 */

						currentComponent = "tFileInputDelimited_2";

						/**
						 * [tFileInputDelimited_2 process_data_end ] stop
						 */

						/**
						 * [tFileInputDelimited_2 end ] start
						 */

						currentComponent = "tFileInputDelimited_2";

						nb_line_tFileInputDelimited_2++;
					}

				} finally {
					if (!(filename_tFileInputDelimited_2 instanceof java.io.InputStream)) {
						if (csvReadertFileInputDelimited_2 != null) {
							csvReadertFileInputDelimited_2.close();
						}
					}
					if (csvReadertFileInputDelimited_2 != null) {
						globalMap.put("tFileInputDelimited_2_NB_LINE", nb_line_tFileInputDelimited_2);
					}

				}

				ok_Hash.put("tFileInputDelimited_2", true);
				end_Hash.put("tFileInputDelimited_2", System.currentTimeMillis());

				/**
				 * [tFileInputDelimited_2 end ] stop
				 */

				/**
				 * [tFilterColumns_2 end ] start
				 */

				currentComponent = "tFilterColumns_2";

				globalMap.put("tFilterColumns_2_NB_LINE", nb_line_tFilterColumns_2);
				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "row5");
				}

				ok_Hash.put("tFilterColumns_2", true);
				end_Hash.put("tFilterColumns_2", System.currentTimeMillis());

				/**
				 * [tFilterColumns_2 end ] stop
				 */

				/**
				 * [tUniqRow_1 end ] start
				 */

				currentComponent = "tUniqRow_1";

				globalMap.put("tUniqRow_1_NB_UNIQUES", nb_uniques_tUniqRow_1);
				globalMap.put("tUniqRow_1_NB_DUPLICATES", nb_duplicates_tUniqRow_1);

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "row6");
				}

				ok_Hash.put("tUniqRow_1", true);
				end_Hash.put("tUniqRow_1", System.currentTimeMillis());

				/**
				 * [tUniqRow_1 end ] stop
				 */

				/**
				 * [tLogRow_3 end ] start
				 */

				currentComponent = "tLogRow_3";

//////
//////
				globalMap.put("tLogRow_3_NB_LINE", nb_line_tLogRow_3);

///////////////////////    			

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "row7");
				}

				ok_Hash.put("tLogRow_3", true);
				end_Hash.put("tLogRow_3", System.currentTimeMillis());

				/**
				 * [tLogRow_3 end ] stop
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
					runStat.updateStat(resourceMap, iterateId, 2, 0, "row8");
				}

				ok_Hash.put("tFileOutputDelimited_2", true);
				end_Hash.put("tFileOutputDelimited_2", System.currentTimeMillis());

				/**
				 * [tFileOutputDelimited_2 end ] stop
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
				 * [tFileInputDelimited_2 finally ] start
				 */

				currentComponent = "tFileInputDelimited_2";

				/**
				 * [tFileInputDelimited_2 finally ] stop
				 */

				/**
				 * [tFilterColumns_2 finally ] start
				 */

				currentComponent = "tFilterColumns_2";

				/**
				 * [tFilterColumns_2 finally ] stop
				 */

				/**
				 * [tUniqRow_1 finally ] start
				 */

				currentComponent = "tUniqRow_1";

				/**
				 * [tUniqRow_1 finally ] stop
				 */

				/**
				 * [tLogRow_3 finally ] start
				 */

				currentComponent = "tLogRow_3";

				/**
				 * [tLogRow_3 finally ] stop
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

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tFileInputDelimited_2_SUBPROCESS_STATE", 1);
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
	public String contextStr = "alice";
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
		final pays paysClass = new pays();

		int exitCode = paysClass.runJobInTOS(args);

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
			java.io.InputStream inContext = pays.class.getClassLoader()
					.getResourceAsStream("projetsid/pays_0_1/contexts/" + contextStr + ".properties");
			if (inContext == null) {
				inContext = pays.class.getClassLoader()
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
			tFileInputDelimited_2Process(globalMap);
			if (!"failure".equals(status)) {
				status = "end";
			}
		} catch (TalendException e_tFileInputDelimited_2) {
			globalMap.put("tFileInputDelimited_2_SUBPROCESS_STATE", -1);

			e_tFileInputDelimited_2.printStackTrace();

		}

		this.globalResumeTicket = true;// to run tPostJob

		end = System.currentTimeMillis();

		if (watch) {
			System.out.println((end - startTime) + " milliseconds");
		}

		endUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		if (false) {
			System.out.println((endUsedMemory - startUsedMemory) + " bytes memory increase when running : pays");
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
 * 109994 characters generated by Talend Open Studio for Data Integration on the
 * 8 mai 2024 à 18:55:16 CEST
 ************************************************************************************************/