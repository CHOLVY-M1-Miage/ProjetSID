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


package projetsid.colonnesselecteur_0_1;

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
 * Job: colonnesSelecteur Purpose: Sélectionne les colonnes utiles<br>
 * Description: Extrait d'un CSV uniquement les colonnes utiles. <br>
 * @author user@talend.com
 * @version 8.0.1.20211109_1610
 * @status 
 */
public class colonnesSelecteur implements TalendJob {

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
	//contains type for every context property
	public class PropertiesWithType extends java.util.Properties {
		private static final long serialVersionUID = 1L;
		private java.util.Map<String,String> propertyTypes = new java.util.HashMap<>();
		
		public PropertiesWithType(java.util.Properties properties){
			super(properties);
		}
		public PropertiesWithType(){
			super();
		}
		
		public void setContextType(String key, String type) {
			propertyTypes.put(key,type);
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

		public ContextProperties(java.util.Properties properties){
			super(properties);
		}
		public ContextProperties(){
			super();
		}

		public void synchronizeContext(){
			
			if(folderName != null){
				
					this.setProperty("folderName", folderName.toString());
				
			}
			
		}
		
		//if the stored or passed value is "<TALEND_NULL>" string, it mean null
		public String getStringValue(String key) {
			String origin_value = this.getProperty(key);
			if(NULL_VALUE_EXPRESSION_IN_COMMAND_STRING_FOR_CHILD_JOB_ONLY.equals(origin_value)) {
				return null;
			}
			return origin_value;
		}

public String folderName;
public String getFolderName(){
	return this.folderName;
}
	}
	protected ContextProperties context = new ContextProperties(); // will be instanciated by MS.
	public ContextProperties getContext() {
		return this.context;
	}
	private final String jobVersion = "0.1";
	private final String jobName = "colonnesSelecteur";
	private final String projectName = "PROJETSID";
	public Integer errorCode = null;
	private String currentComponent = "";
	
		private final java.util.Map<String, Object> globalMap = new java.util.HashMap<String, Object>();
        private final static java.util.Map<String, Object> junitGlobalMap = new java.util.HashMap<String, Object>();
	
		private final java.util.Map<String, Long> start_Hash = new java.util.HashMap<String, Long>();
		private final java.util.Map<String, Long> end_Hash = new java.util.HashMap<String, Long>();
		private final java.util.Map<String, Boolean> ok_Hash = new java.util.HashMap<String, Boolean>();
		public  final java.util.List<String[]> globalBuffer = new java.util.ArrayList<String[]>();
	

private RunStat runStat = new RunStat();

	// OSGi DataSource
	private final static String KEY_DB_DATASOURCES = "KEY_DB_DATASOURCES";
	
	private final static String KEY_DB_DATASOURCES_RAW = "KEY_DB_DATASOURCES_RAW";

	public void setDataSources(java.util.Map<String, javax.sql.DataSource> dataSources) {
		java.util.Map<String, routines.system.TalendDataSource> talendDataSources = new java.util.HashMap<String, routines.system.TalendDataSource>();
		for (java.util.Map.Entry<String, javax.sql.DataSource> dataSourceEntry : dataSources.entrySet()) {
			talendDataSources.put(dataSourceEntry.getKey(), new routines.system.TalendDataSource(dataSourceEntry.getValue()));
		}
		globalMap.put(KEY_DB_DATASOURCES, talendDataSources);
		globalMap.put(KEY_DB_DATASOURCES_RAW, new java.util.HashMap<String, javax.sql.DataSource>(dataSources));
	}
	
	public void setDataSourceReferences(List serviceReferences) throws Exception{
		
		java.util.Map<String, routines.system.TalendDataSource> talendDataSources = new java.util.HashMap<String, routines.system.TalendDataSource>();
		java.util.Map<String, javax.sql.DataSource> dataSources = new java.util.HashMap<String, javax.sql.DataSource>();
		
		for (java.util.Map.Entry<String, javax.sql.DataSource> entry : BundleUtils.getServices(serviceReferences,  javax.sql.DataSource.class).entrySet()) {
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
	
	public void setVirtualComponentName (String virtualComponentName){
		this.virtualComponentName = virtualComponentName;
	}

	private TalendException(Exception e, String errorComponent, final java.util.Map<String, Object> globalMap) {
		this.currentComponent= errorComponent;
		this.globalMap = globalMap;
		this.e = e;
	}

	public Exception getException() {
		return this.e;
	}

	public String getCurrentComponent() {
		return this.currentComponent;
	}

	
    public String getExceptionCauseMessage(Exception e){
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
			if(virtualComponentName!=null && currentComponent.indexOf(virtualComponentName+"_")==0){
				globalMap.put(virtualComponentName+"_ERROR_MESSAGE",getExceptionCauseMessage(e));
			}
			globalMap.put(currentComponent+"_ERROR_MESSAGE",getExceptionCauseMessage(e));
			System.err.println("Exception in component " + currentComponent + " (" + jobName + ")");
		}
		if (!(e instanceof TDieException)) {
			if(e instanceof TalendException){
				e.printStackTrace();
			} else {
				e.printStackTrace();
				e.printStackTrace(errorMessagePS);
				colonnesSelecteur.this.exception = e;
			}
		}
		if (!(e instanceof TalendException)) {
		try {
			for (java.lang.reflect.Method m : this.getClass().getEnclosingClass().getMethods()) {
				if (m.getName().compareTo(currentComponent + "_error") == 0) {
					m.invoke(colonnesSelecteur.this, new Object[] { e , currentComponent, globalMap});
					break;
				}
			}

			if(!(e instanceof TDieException)){
			}
		} catch (Exception e) {
			this.e.printStackTrace();
		}
		}
	}
}

			public void tFileInputDelimited_1_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tFileInputDelimited_1_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tFilterColumns_1_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tFileInputDelimited_1_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tFileOutputDelimited_1_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {
				
				end_Hash.put(errorComponent, System.currentTimeMillis());
				
				status = "failure";
				
					tFileInputDelimited_1_onSubJobError(exception, errorComponent, globalMap);
			}
			
			public void tFileInputDelimited_1_onSubJobError(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap) throws TalendException {

resumeUtil.addLog("SYSTEM_LOG", "NODE:"+ errorComponent, "", Thread.currentThread().getId()+ "", "FATAL", "", exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception),"");

			}
	






public static class row2Struct implements routines.system.IPersistableRow<row2Struct> {
    final static byte[] commonByteArrayLock_PROJETSID_colonnesSelecteur = new byte[0];
    static byte[] commonByteArray_PROJETSID_colonnesSelecteur = new byte[0];

	
			    public String Customer_Group_ID;

				public String getCustomer_Group_ID () {
					return this.Customer_Group_ID;
				}
				
			    public String Customer_Group;

				public String getCustomer_Group () {
					return this.Customer_Group;
				}
				
			    public String Customer_Type_ID;

				public String getCustomer_Type_ID () {
					return this.Customer_Type_ID;
				}
				
			    public String Customer_Type;

				public String getCustomer_Type () {
					return this.Customer_Type;
				}
				
			    public String Birth_Date;

				public String getBirth_Date () {
					return this.Birth_Date;
				}
				
			    public char Gender = ' ';

				public char getGender () {
					return this.Gender;
				}
				
			    public String Customer_ID;

				public String getCustomer_ID () {
					return this.Customer_ID;
				}
				
			    public String Country;

				public String getCountry () {
					return this.Country;
				}
				
			    public String Country_Name;

				public String getCountry_Name () {
					return this.Country_Name;
				}
				
			    public String Employee_ID;

				public String getEmployee_ID () {
					return this.Employee_ID;
				}
				
			    public String Employee_Job_Title;

				public String getEmployee_Job_Title () {
					return this.Employee_Job_Title;
				}
				
			    public String Employee_Salary;

				public String getEmployee_Salary () {
					return this.Employee_Salary;
				}
				
			    public String Employee_Gender;

				public String getEmployee_Gender () {
					return this.Employee_Gender;
				}
				
			    public String Employee_Country;

				public String getEmployee_Country () {
					return this.Employee_Country;
				}
				
			    public String Order_Type;

				public String getOrder_Type () {
					return this.Order_Type;
				}
				
			    public String Order_Date;

				public String getOrder_Date () {
					return this.Order_Date;
				}
				
			    public String Delivery_Date;

				public String getDelivery_Date () {
					return this.Delivery_Date;
				}
				
			    public String Product_ID;

				public String getProduct_ID () {
					return this.Product_ID;
				}
				
			    public String Product_Name;

				public String getProduct_Name () {
					return this.Product_Name;
				}
				
			    public String Product_Ref_ID;

				public String getProduct_Ref_ID () {
					return this.Product_Ref_ID;
				}
				
			    public int Quantity;

				public int getQuantity () {
					return this.Quantity;
				}
				
			    public String Order_ID;

				public String getOrder_ID () {
					return this.Order_ID;
				}
				
			    public int Order_Item_Num;

				public int getOrder_Item_Num () {
					return this.Order_Item_Num;
				}
				
			    public String CostPrice_Per_Unit;

				public String getCostPrice_Per_Unit () {
					return this.CostPrice_Per_Unit;
				}
				
			    public String Total_Retail_Price;

				public String getTotal_Retail_Price () {
					return this.Total_Retail_Price;
				}
				



	private String readString(ObjectInputStream dis) throws IOException{
		String strReturn = null;
		int length = 0;
        length = dis.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			if(length > commonByteArray_PROJETSID_colonnesSelecteur.length) {
				if(length < 1024 && commonByteArray_PROJETSID_colonnesSelecteur.length == 0) {
   					commonByteArray_PROJETSID_colonnesSelecteur = new byte[1024];
				} else {
   					commonByteArray_PROJETSID_colonnesSelecteur = new byte[2 * length];
   				}
			}
			dis.readFully(commonByteArray_PROJETSID_colonnesSelecteur, 0, length);
			strReturn = new String(commonByteArray_PROJETSID_colonnesSelecteur, 0, length, utf8Charset);
		}
		return strReturn;
	}
	
	private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException{
		String strReturn = null;
		int length = 0;
        length = unmarshaller.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			if(length > commonByteArray_PROJETSID_colonnesSelecteur.length) {
				if(length < 1024 && commonByteArray_PROJETSID_colonnesSelecteur.length == 0) {
   					commonByteArray_PROJETSID_colonnesSelecteur = new byte[1024];
				} else {
   					commonByteArray_PROJETSID_colonnesSelecteur = new byte[2 * length];
   				}
			}
			unmarshaller.readFully(commonByteArray_PROJETSID_colonnesSelecteur, 0, length);
			strReturn = new String(commonByteArray_PROJETSID_colonnesSelecteur, 0, length, utf8Charset);
		}
		return strReturn;
	}

    private void writeString(String str, ObjectOutputStream dos) throws IOException{
		if(str == null) {
            dos.writeInt(-1);
		} else {
            byte[] byteArray = str.getBytes(utf8Charset);
	    	dos.writeInt(byteArray.length);
			dos.write(byteArray);
    	}
    }
    
    private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException{
		if(str == null) {
			marshaller.writeInt(-1);
		} else {
            byte[] byteArray = str.getBytes(utf8Charset);
            marshaller.writeInt(byteArray.length);
            marshaller.write(byteArray);
    	}
    }

    public void readData(ObjectInputStream dis) {

		synchronized(commonByteArrayLock_PROJETSID_colonnesSelecteur) {

        	try {

        		int length = 0;
		
					this.Customer_Group_ID = readString(dis);
					
					this.Customer_Group = readString(dis);
					
					this.Customer_Type_ID = readString(dis);
					
					this.Customer_Type = readString(dis);
					
					this.Birth_Date = readString(dis);
					
			        this.Gender = dis.readChar();
					
					this.Customer_ID = readString(dis);
					
					this.Country = readString(dis);
					
					this.Country_Name = readString(dis);
					
					this.Employee_ID = readString(dis);
					
					this.Employee_Job_Title = readString(dis);
					
					this.Employee_Salary = readString(dis);
					
					this.Employee_Gender = readString(dis);
					
					this.Employee_Country = readString(dis);
					
					this.Order_Type = readString(dis);
					
					this.Order_Date = readString(dis);
					
					this.Delivery_Date = readString(dis);
					
					this.Product_ID = readString(dis);
					
					this.Product_Name = readString(dis);
					
					this.Product_Ref_ID = readString(dis);
					
			        this.Quantity = dis.readInt();
					
					this.Order_ID = readString(dis);
					
			        this.Order_Item_Num = dis.readInt();
					
					this.CostPrice_Per_Unit = readString(dis);
					
					this.Total_Retail_Price = readString(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }
    
    public void readData(org.jboss.marshalling.Unmarshaller dis) {

		synchronized(commonByteArrayLock_PROJETSID_colonnesSelecteur) {

        	try {

        		int length = 0;
		
					this.Customer_Group_ID = readString(dis);
					
					this.Customer_Group = readString(dis);
					
					this.Customer_Type_ID = readString(dis);
					
					this.Customer_Type = readString(dis);
					
					this.Birth_Date = readString(dis);
					
			        this.Gender = dis.readChar();
					
					this.Customer_ID = readString(dis);
					
					this.Country = readString(dis);
					
					this.Country_Name = readString(dis);
					
					this.Employee_ID = readString(dis);
					
					this.Employee_Job_Title = readString(dis);
					
					this.Employee_Salary = readString(dis);
					
					this.Employee_Gender = readString(dis);
					
					this.Employee_Country = readString(dis);
					
					this.Order_Type = readString(dis);
					
					this.Order_Date = readString(dis);
					
					this.Delivery_Date = readString(dis);
					
					this.Product_ID = readString(dis);
					
					this.Product_Name = readString(dis);
					
					this.Product_Ref_ID = readString(dis);
					
			        this.Quantity = dis.readInt();
					
					this.Order_ID = readString(dis);
					
			        this.Order_Item_Num = dis.readInt();
					
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
				
						writeString(this.Customer_Group_ID,dos);
					
					// String
				
						writeString(this.Customer_Group,dos);
					
					// String
				
						writeString(this.Customer_Type_ID,dos);
					
					// String
				
						writeString(this.Customer_Type,dos);
					
					// String
				
						writeString(this.Birth_Date,dos);
					
					// char
				
		            	dos.writeChar(this.Gender);
					
					// String
				
						writeString(this.Customer_ID,dos);
					
					// String
				
						writeString(this.Country,dos);
					
					// String
				
						writeString(this.Country_Name,dos);
					
					// String
				
						writeString(this.Employee_ID,dos);
					
					// String
				
						writeString(this.Employee_Job_Title,dos);
					
					// String
				
						writeString(this.Employee_Salary,dos);
					
					// String
				
						writeString(this.Employee_Gender,dos);
					
					// String
				
						writeString(this.Employee_Country,dos);
					
					// String
				
						writeString(this.Order_Type,dos);
					
					// String
				
						writeString(this.Order_Date,dos);
					
					// String
				
						writeString(this.Delivery_Date,dos);
					
					// String
				
						writeString(this.Product_ID,dos);
					
					// String
				
						writeString(this.Product_Name,dos);
					
					// String
				
						writeString(this.Product_Ref_ID,dos);
					
					// int
				
		            	dos.writeInt(this.Quantity);
					
					// String
				
						writeString(this.Order_ID,dos);
					
					// int
				
		            	dos.writeInt(this.Order_Item_Num);
					
					// String
				
						writeString(this.CostPrice_Per_Unit,dos);
					
					// String
				
						writeString(this.Total_Retail_Price,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }
    
    public void writeData(org.jboss.marshalling.Marshaller dos) {
        try {

		
					// String
				
						writeString(this.Customer_Group_ID,dos);
					
					// String
				
						writeString(this.Customer_Group,dos);
					
					// String
				
						writeString(this.Customer_Type_ID,dos);
					
					// String
				
						writeString(this.Customer_Type,dos);
					
					// String
				
						writeString(this.Birth_Date,dos);
					
					// char
				
		            	dos.writeChar(this.Gender);
					
					// String
				
						writeString(this.Customer_ID,dos);
					
					// String
				
						writeString(this.Country,dos);
					
					// String
				
						writeString(this.Country_Name,dos);
					
					// String
				
						writeString(this.Employee_ID,dos);
					
					// String
				
						writeString(this.Employee_Job_Title,dos);
					
					// String
				
						writeString(this.Employee_Salary,dos);
					
					// String
				
						writeString(this.Employee_Gender,dos);
					
					// String
				
						writeString(this.Employee_Country,dos);
					
					// String
				
						writeString(this.Order_Type,dos);
					
					// String
				
						writeString(this.Order_Date,dos);
					
					// String
				
						writeString(this.Delivery_Date,dos);
					
					// String
				
						writeString(this.Product_ID,dos);
					
					// String
				
						writeString(this.Product_Name,dos);
					
					// String
				
						writeString(this.Product_Ref_ID,dos);
					
					// int
				
		            	dos.writeInt(this.Quantity);
					
					// String
				
						writeString(this.Order_ID,dos);
					
					// int
				
		            	dos.writeInt(this.Order_Item_Num);
					
					// String
				
						writeString(this.CostPrice_Per_Unit,dos);
					
					// String
				
						writeString(this.Total_Retail_Price,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }


    public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("[");
		sb.append("Customer_Group_ID="+Customer_Group_ID);
		sb.append(",Customer_Group="+Customer_Group);
		sb.append(",Customer_Type_ID="+Customer_Type_ID);
		sb.append(",Customer_Type="+Customer_Type);
		sb.append(",Birth_Date="+Birth_Date);
		sb.append(",Gender="+String.valueOf(Gender));
		sb.append(",Customer_ID="+Customer_ID);
		sb.append(",Country="+Country);
		sb.append(",Country_Name="+Country_Name);
		sb.append(",Employee_ID="+Employee_ID);
		sb.append(",Employee_Job_Title="+Employee_Job_Title);
		sb.append(",Employee_Salary="+Employee_Salary);
		sb.append(",Employee_Gender="+Employee_Gender);
		sb.append(",Employee_Country="+Employee_Country);
		sb.append(",Order_Type="+Order_Type);
		sb.append(",Order_Date="+Order_Date);
		sb.append(",Delivery_Date="+Delivery_Date);
		sb.append(",Product_ID="+Product_ID);
		sb.append(",Product_Name="+Product_Name);
		sb.append(",Product_Ref_ID="+Product_Ref_ID);
		sb.append(",Quantity="+String.valueOf(Quantity));
		sb.append(",Order_ID="+Order_ID);
		sb.append(",Order_Item_Num="+String.valueOf(Order_Item_Num));
		sb.append(",CostPrice_Per_Unit="+CostPrice_Per_Unit);
		sb.append(",Total_Retail_Price="+Total_Retail_Price);
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
    final static byte[] commonByteArrayLock_PROJETSID_colonnesSelecteur = new byte[0];
    static byte[] commonByteArray_PROJETSID_colonnesSelecteur = new byte[0];

	
			    public String Order_ID;

				public String getOrder_ID () {
					return this.Order_ID;
				}
				
			    public String Order_Type;

				public String getOrder_Type () {
					return this.Order_Type;
				}
				
			    public String Order_Date;

				public String getOrder_Date () {
					return this.Order_Date;
				}
				
			    public String Delivery_Date;

				public String getDelivery_Date () {
					return this.Delivery_Date;
				}
				
			    public Integer Order_Item_Num;

				public Integer getOrder_Item_Num () {
					return this.Order_Item_Num;
				}
				
			    public Integer Quantity;

				public Integer getQuantity () {
					return this.Quantity;
				}
				
			    public String Total_Retail_Price;

				public String getTotal_Retail_Price () {
					return this.Total_Retail_Price;
				}
				
			    public String CostPrice_Per_Unit;

				public String getCostPrice_Per_Unit () {
					return this.CostPrice_Per_Unit;
				}
				
			    public String Product_ID;

				public String getProduct_ID () {
					return this.Product_ID;
				}
				
			    public String Product_Name;

				public String getProduct_Name () {
					return this.Product_Name;
				}
				
			    public String Product_Level;

				public String getProduct_Level () {
					return this.Product_Level;
				}
				
			    public String Product_Level_Name;

				public String getProduct_Level_Name () {
					return this.Product_Level_Name;
				}
				
			    public String Product_Ref_ID;

				public String getProduct_Ref_ID () {
					return this.Product_Ref_ID;
				}
				
			    public String Customer_ID;

				public String getCustomer_ID () {
					return this.Customer_ID;
				}
				
			    public Character Gender;

				public Character getGender () {
					return this.Gender;
				}
				
			    public String Personal_ID;

				public String getPersonal_ID () {
					return this.Personal_ID;
				}
				
			    public String Customer_Name;

				public String getCustomer_Name () {
					return this.Customer_Name;
				}
				
			    public String Customer_FirstName;

				public String getCustomer_FirstName () {
					return this.Customer_FirstName;
				}
				
			    public String Customer_LastName;

				public String getCustomer_LastName () {
					return this.Customer_LastName;
				}
				
			    public String Birth_Date;

				public String getBirth_Date () {
					return this.Birth_Date;
				}
				
			    public String Customer_Address;

				public String getCustomer_Address () {
					return this.Customer_Address;
				}
				
			    public String Street_ID;

				public String getStreet_ID () {
					return this.Street_ID;
				}
				
			    public String Street_Number;

				public String getStreet_Number () {
					return this.Street_Number;
				}
				
			    public String Customer_Type_ID;

				public String getCustomer_Type_ID () {
					return this.Customer_Type_ID;
				}
				
			    public String Customer_Type;

				public String getCustomer_Type () {
					return this.Customer_Type;
				}
				
			    public String Customer_Group_ID;

				public String getCustomer_Group_ID () {
					return this.Customer_Group_ID;
				}
				
			    public String Customer_Group;

				public String getCustomer_Group () {
					return this.Customer_Group;
				}
				
			    public String Employee_ID;

				public String getEmployee_ID () {
					return this.Employee_ID;
				}
				
			    public String EmployeeStart_Date;

				public String getEmployeeStart_Date () {
					return this.EmployeeStart_Date;
				}
				
			    public String Employee_End_Date;

				public String getEmployee_End_Date () {
					return this.Employee_End_Date;
				}
				
			    public String Employee_Job_Title;

				public String getEmployee_Job_Title () {
					return this.Employee_Job_Title;
				}
				
			    public String Employee_Salary;

				public String getEmployee_Salary () {
					return this.Employee_Salary;
				}
				
			    public String Employee_Gender;

				public String getEmployee_Gender () {
					return this.Employee_Gender;
				}
				
			    public String Employee_Birth_Date;

				public String getEmployee_Birth_Date () {
					return this.Employee_Birth_Date;
				}
				
			    public String Emp_Hire_Date;

				public String getEmp_Hire_Date () {
					return this.Emp_Hire_Date;
				}
				
			    public String Emp_Term_Date;

				public String getEmp_Term_Date () {
					return this.Emp_Term_Date;
				}
				
			    public String EmpLoyee_Name;

				public String getEmpLoyee_Name () {
					return this.EmpLoyee_Name;
				}
				
			    public String Employee_Country;

				public String getEmployee_Country () {
					return this.Employee_Country;
				}
				
			    public String Employee_Org_Level;

				public String getEmployee_Org_Level () {
					return this.Employee_Org_Level;
				}
				
			    public String Org_Level_Name;

				public String getOrg_Level_Name () {
					return this.Org_Level_Name;
				}
				
			    public String Country;

				public String getCountry () {
					return this.Country;
				}
				
			    public String Country_Name;

				public String getCountry_Name () {
					return this.Country_Name;
				}
				
			    public String Population;

				public String getPopulation () {
					return this.Population;
				}
				
			    public String Country_ID;

				public String getCountry_ID () {
					return this.Country_ID;
				}
				
			    public String Continent_ID;

				public String getContinent_ID () {
					return this.Continent_ID;
				}
				
			    public String Country_FormerName;

				public String getCountry_FormerName () {
					return this.Country_FormerName;
				}
				
			    public String Continent_Name;

				public String getContinent_Name () {
					return this.Continent_Name;
				}
				
			    public String Supplier_ID;

				public String getSupplier_ID () {
					return this.Supplier_ID;
				}
				
			    public String Supplier_Name;

				public String getSupplier_Name () {
					return this.Supplier_Name;
				}
				
			    public String Supplier_Address;

				public String getSupplier_Address () {
					return this.Supplier_Address;
				}
				
			    public String Supplier_Country;

				public String getSupplier_Country () {
					return this.Supplier_Country;
				}
				



	private String readString(ObjectInputStream dis) throws IOException{
		String strReturn = null;
		int length = 0;
        length = dis.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			if(length > commonByteArray_PROJETSID_colonnesSelecteur.length) {
				if(length < 1024 && commonByteArray_PROJETSID_colonnesSelecteur.length == 0) {
   					commonByteArray_PROJETSID_colonnesSelecteur = new byte[1024];
				} else {
   					commonByteArray_PROJETSID_colonnesSelecteur = new byte[2 * length];
   				}
			}
			dis.readFully(commonByteArray_PROJETSID_colonnesSelecteur, 0, length);
			strReturn = new String(commonByteArray_PROJETSID_colonnesSelecteur, 0, length, utf8Charset);
		}
		return strReturn;
	}
	
	private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException{
		String strReturn = null;
		int length = 0;
        length = unmarshaller.readInt();
		if (length == -1) {
			strReturn = null;
		} else {
			if(length > commonByteArray_PROJETSID_colonnesSelecteur.length) {
				if(length < 1024 && commonByteArray_PROJETSID_colonnesSelecteur.length == 0) {
   					commonByteArray_PROJETSID_colonnesSelecteur = new byte[1024];
				} else {
   					commonByteArray_PROJETSID_colonnesSelecteur = new byte[2 * length];
   				}
			}
			unmarshaller.readFully(commonByteArray_PROJETSID_colonnesSelecteur, 0, length);
			strReturn = new String(commonByteArray_PROJETSID_colonnesSelecteur, 0, length, utf8Charset);
		}
		return strReturn;
	}

    private void writeString(String str, ObjectOutputStream dos) throws IOException{
		if(str == null) {
            dos.writeInt(-1);
		} else {
            byte[] byteArray = str.getBytes(utf8Charset);
	    	dos.writeInt(byteArray.length);
			dos.write(byteArray);
    	}
    }
    
    private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException{
		if(str == null) {
			marshaller.writeInt(-1);
		} else {
            byte[] byteArray = str.getBytes(utf8Charset);
            marshaller.writeInt(byteArray.length);
            marshaller.write(byteArray);
    	}
    }
	private Integer readInteger(ObjectInputStream dis) throws IOException{
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
	
	private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException{
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

	private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException{
		if(intNum == null) {
            dos.writeByte(-1);
		} else {
			dos.writeByte(0);
	    	dos.writeInt(intNum);
    	}
	}
	
	private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException{
		if(intNum == null) {
			marshaller.writeByte(-1);
		} else {
			marshaller.writeByte(0);
			marshaller.writeInt(intNum);
    	}
	}

    public void readData(ObjectInputStream dis) {

		synchronized(commonByteArrayLock_PROJETSID_colonnesSelecteur) {

        	try {

        		int length = 0;
		
					this.Order_ID = readString(dis);
					
					this.Order_Type = readString(dis);
					
					this.Order_Date = readString(dis);
					
					this.Delivery_Date = readString(dis);
					
						this.Order_Item_Num = readInteger(dis);
					
						this.Quantity = readInteger(dis);
					
					this.Total_Retail_Price = readString(dis);
					
					this.CostPrice_Per_Unit = readString(dis);
					
					this.Product_ID = readString(dis);
					
					this.Product_Name = readString(dis);
					
					this.Product_Level = readString(dis);
					
					this.Product_Level_Name = readString(dis);
					
					this.Product_Ref_ID = readString(dis);
					
					this.Customer_ID = readString(dis);
					
			            length = dis.readByte();
           				if (length == -1) {
           	    			this.Gender = null;
           				} else {
           			    	this.Gender = dis.readChar();
           				}
					
					this.Personal_ID = readString(dis);
					
					this.Customer_Name = readString(dis);
					
					this.Customer_FirstName = readString(dis);
					
					this.Customer_LastName = readString(dis);
					
					this.Birth_Date = readString(dis);
					
					this.Customer_Address = readString(dis);
					
					this.Street_ID = readString(dis);
					
					this.Street_Number = readString(dis);
					
					this.Customer_Type_ID = readString(dis);
					
					this.Customer_Type = readString(dis);
					
					this.Customer_Group_ID = readString(dis);
					
					this.Customer_Group = readString(dis);
					
					this.Employee_ID = readString(dis);
					
					this.EmployeeStart_Date = readString(dis);
					
					this.Employee_End_Date = readString(dis);
					
					this.Employee_Job_Title = readString(dis);
					
					this.Employee_Salary = readString(dis);
					
					this.Employee_Gender = readString(dis);
					
					this.Employee_Birth_Date = readString(dis);
					
					this.Emp_Hire_Date = readString(dis);
					
					this.Emp_Term_Date = readString(dis);
					
					this.EmpLoyee_Name = readString(dis);
					
					this.Employee_Country = readString(dis);
					
					this.Employee_Org_Level = readString(dis);
					
					this.Org_Level_Name = readString(dis);
					
					this.Country = readString(dis);
					
					this.Country_Name = readString(dis);
					
					this.Population = readString(dis);
					
					this.Country_ID = readString(dis);
					
					this.Continent_ID = readString(dis);
					
					this.Country_FormerName = readString(dis);
					
					this.Continent_Name = readString(dis);
					
					this.Supplier_ID = readString(dis);
					
					this.Supplier_Name = readString(dis);
					
					this.Supplier_Address = readString(dis);
					
					this.Supplier_Country = readString(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }
    
    public void readData(org.jboss.marshalling.Unmarshaller dis) {

		synchronized(commonByteArrayLock_PROJETSID_colonnesSelecteur) {

        	try {

        		int length = 0;
		
					this.Order_ID = readString(dis);
					
					this.Order_Type = readString(dis);
					
					this.Order_Date = readString(dis);
					
					this.Delivery_Date = readString(dis);
					
						this.Order_Item_Num = readInteger(dis);
					
						this.Quantity = readInteger(dis);
					
					this.Total_Retail_Price = readString(dis);
					
					this.CostPrice_Per_Unit = readString(dis);
					
					this.Product_ID = readString(dis);
					
					this.Product_Name = readString(dis);
					
					this.Product_Level = readString(dis);
					
					this.Product_Level_Name = readString(dis);
					
					this.Product_Ref_ID = readString(dis);
					
					this.Customer_ID = readString(dis);
					
			            length = dis.readByte();
           				if (length == -1) {
           	    			this.Gender = null;
           				} else {
           			    	this.Gender = dis.readChar();
           				}
					
					this.Personal_ID = readString(dis);
					
					this.Customer_Name = readString(dis);
					
					this.Customer_FirstName = readString(dis);
					
					this.Customer_LastName = readString(dis);
					
					this.Birth_Date = readString(dis);
					
					this.Customer_Address = readString(dis);
					
					this.Street_ID = readString(dis);
					
					this.Street_Number = readString(dis);
					
					this.Customer_Type_ID = readString(dis);
					
					this.Customer_Type = readString(dis);
					
					this.Customer_Group_ID = readString(dis);
					
					this.Customer_Group = readString(dis);
					
					this.Employee_ID = readString(dis);
					
					this.EmployeeStart_Date = readString(dis);
					
					this.Employee_End_Date = readString(dis);
					
					this.Employee_Job_Title = readString(dis);
					
					this.Employee_Salary = readString(dis);
					
					this.Employee_Gender = readString(dis);
					
					this.Employee_Birth_Date = readString(dis);
					
					this.Emp_Hire_Date = readString(dis);
					
					this.Emp_Term_Date = readString(dis);
					
					this.EmpLoyee_Name = readString(dis);
					
					this.Employee_Country = readString(dis);
					
					this.Employee_Org_Level = readString(dis);
					
					this.Org_Level_Name = readString(dis);
					
					this.Country = readString(dis);
					
					this.Country_Name = readString(dis);
					
					this.Population = readString(dis);
					
					this.Country_ID = readString(dis);
					
					this.Continent_ID = readString(dis);
					
					this.Country_FormerName = readString(dis);
					
					this.Continent_Name = readString(dis);
					
					this.Supplier_ID = readString(dis);
					
					this.Supplier_Name = readString(dis);
					
					this.Supplier_Address = readString(dis);
					
					this.Supplier_Country = readString(dis);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);

		

        }

		

      }


    }

    public void writeData(ObjectOutputStream dos) {
        try {

		
					// String
				
						writeString(this.Order_ID,dos);
					
					// String
				
						writeString(this.Order_Type,dos);
					
					// String
				
						writeString(this.Order_Date,dos);
					
					// String
				
						writeString(this.Delivery_Date,dos);
					
					// Integer
				
						writeInteger(this.Order_Item_Num,dos);
					
					// Integer
				
						writeInteger(this.Quantity,dos);
					
					// String
				
						writeString(this.Total_Retail_Price,dos);
					
					// String
				
						writeString(this.CostPrice_Per_Unit,dos);
					
					// String
				
						writeString(this.Product_ID,dos);
					
					// String
				
						writeString(this.Product_Name,dos);
					
					// String
				
						writeString(this.Product_Level,dos);
					
					// String
				
						writeString(this.Product_Level_Name,dos);
					
					// String
				
						writeString(this.Product_Ref_ID,dos);
					
					// String
				
						writeString(this.Customer_ID,dos);
					
					// Character
				
						if(this.Gender == null) {
			                dos.writeByte(-1);
						} else {
               				dos.writeByte(0);
           			    	dos.writeChar(this.Gender);
		            	}
					
					// String
				
						writeString(this.Personal_ID,dos);
					
					// String
				
						writeString(this.Customer_Name,dos);
					
					// String
				
						writeString(this.Customer_FirstName,dos);
					
					// String
				
						writeString(this.Customer_LastName,dos);
					
					// String
				
						writeString(this.Birth_Date,dos);
					
					// String
				
						writeString(this.Customer_Address,dos);
					
					// String
				
						writeString(this.Street_ID,dos);
					
					// String
				
						writeString(this.Street_Number,dos);
					
					// String
				
						writeString(this.Customer_Type_ID,dos);
					
					// String
				
						writeString(this.Customer_Type,dos);
					
					// String
				
						writeString(this.Customer_Group_ID,dos);
					
					// String
				
						writeString(this.Customer_Group,dos);
					
					// String
				
						writeString(this.Employee_ID,dos);
					
					// String
				
						writeString(this.EmployeeStart_Date,dos);
					
					// String
				
						writeString(this.Employee_End_Date,dos);
					
					// String
				
						writeString(this.Employee_Job_Title,dos);
					
					// String
				
						writeString(this.Employee_Salary,dos);
					
					// String
				
						writeString(this.Employee_Gender,dos);
					
					// String
				
						writeString(this.Employee_Birth_Date,dos);
					
					// String
				
						writeString(this.Emp_Hire_Date,dos);
					
					// String
				
						writeString(this.Emp_Term_Date,dos);
					
					// String
				
						writeString(this.EmpLoyee_Name,dos);
					
					// String
				
						writeString(this.Employee_Country,dos);
					
					// String
				
						writeString(this.Employee_Org_Level,dos);
					
					// String
				
						writeString(this.Org_Level_Name,dos);
					
					// String
				
						writeString(this.Country,dos);
					
					// String
				
						writeString(this.Country_Name,dos);
					
					// String
				
						writeString(this.Population,dos);
					
					// String
				
						writeString(this.Country_ID,dos);
					
					// String
				
						writeString(this.Continent_ID,dos);
					
					// String
				
						writeString(this.Country_FormerName,dos);
					
					// String
				
						writeString(this.Continent_Name,dos);
					
					// String
				
						writeString(this.Supplier_ID,dos);
					
					// String
				
						writeString(this.Supplier_Name,dos);
					
					// String
				
						writeString(this.Supplier_Address,dos);
					
					// String
				
						writeString(this.Supplier_Country,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }
    
    public void writeData(org.jboss.marshalling.Marshaller dos) {
        try {

		
					// String
				
						writeString(this.Order_ID,dos);
					
					// String
				
						writeString(this.Order_Type,dos);
					
					// String
				
						writeString(this.Order_Date,dos);
					
					// String
				
						writeString(this.Delivery_Date,dos);
					
					// Integer
				
						writeInteger(this.Order_Item_Num,dos);
					
					// Integer
				
						writeInteger(this.Quantity,dos);
					
					// String
				
						writeString(this.Total_Retail_Price,dos);
					
					// String
				
						writeString(this.CostPrice_Per_Unit,dos);
					
					// String
				
						writeString(this.Product_ID,dos);
					
					// String
				
						writeString(this.Product_Name,dos);
					
					// String
				
						writeString(this.Product_Level,dos);
					
					// String
				
						writeString(this.Product_Level_Name,dos);
					
					// String
				
						writeString(this.Product_Ref_ID,dos);
					
					// String
				
						writeString(this.Customer_ID,dos);
					
					// Character
				
						if(this.Gender == null) {
			                dos.writeByte(-1);
						} else {
               				dos.writeByte(0);
           			    	dos.writeChar(this.Gender);
		            	}
					
					// String
				
						writeString(this.Personal_ID,dos);
					
					// String
				
						writeString(this.Customer_Name,dos);
					
					// String
				
						writeString(this.Customer_FirstName,dos);
					
					// String
				
						writeString(this.Customer_LastName,dos);
					
					// String
				
						writeString(this.Birth_Date,dos);
					
					// String
				
						writeString(this.Customer_Address,dos);
					
					// String
				
						writeString(this.Street_ID,dos);
					
					// String
				
						writeString(this.Street_Number,dos);
					
					// String
				
						writeString(this.Customer_Type_ID,dos);
					
					// String
				
						writeString(this.Customer_Type,dos);
					
					// String
				
						writeString(this.Customer_Group_ID,dos);
					
					// String
				
						writeString(this.Customer_Group,dos);
					
					// String
				
						writeString(this.Employee_ID,dos);
					
					// String
				
						writeString(this.EmployeeStart_Date,dos);
					
					// String
				
						writeString(this.Employee_End_Date,dos);
					
					// String
				
						writeString(this.Employee_Job_Title,dos);
					
					// String
				
						writeString(this.Employee_Salary,dos);
					
					// String
				
						writeString(this.Employee_Gender,dos);
					
					// String
				
						writeString(this.Employee_Birth_Date,dos);
					
					// String
				
						writeString(this.Emp_Hire_Date,dos);
					
					// String
				
						writeString(this.Emp_Term_Date,dos);
					
					// String
				
						writeString(this.EmpLoyee_Name,dos);
					
					// String
				
						writeString(this.Employee_Country,dos);
					
					// String
				
						writeString(this.Employee_Org_Level,dos);
					
					// String
				
						writeString(this.Org_Level_Name,dos);
					
					// String
				
						writeString(this.Country,dos);
					
					// String
				
						writeString(this.Country_Name,dos);
					
					// String
				
						writeString(this.Population,dos);
					
					// String
				
						writeString(this.Country_ID,dos);
					
					// String
				
						writeString(this.Continent_ID,dos);
					
					// String
				
						writeString(this.Country_FormerName,dos);
					
					// String
				
						writeString(this.Continent_Name,dos);
					
					// String
				
						writeString(this.Supplier_ID,dos);
					
					// String
				
						writeString(this.Supplier_Name,dos);
					
					// String
				
						writeString(this.Supplier_Address,dos);
					
					// String
				
						writeString(this.Supplier_Country,dos);
					
        	} catch (IOException e) {
	            throw new RuntimeException(e);
        }


    }


    public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append("[");
		sb.append("Order_ID="+Order_ID);
		sb.append(",Order_Type="+Order_Type);
		sb.append(",Order_Date="+Order_Date);
		sb.append(",Delivery_Date="+Delivery_Date);
		sb.append(",Order_Item_Num="+String.valueOf(Order_Item_Num));
		sb.append(",Quantity="+String.valueOf(Quantity));
		sb.append(",Total_Retail_Price="+Total_Retail_Price);
		sb.append(",CostPrice_Per_Unit="+CostPrice_Per_Unit);
		sb.append(",Product_ID="+Product_ID);
		sb.append(",Product_Name="+Product_Name);
		sb.append(",Product_Level="+Product_Level);
		sb.append(",Product_Level_Name="+Product_Level_Name);
		sb.append(",Product_Ref_ID="+Product_Ref_ID);
		sb.append(",Customer_ID="+Customer_ID);
		sb.append(",Gender="+String.valueOf(Gender));
		sb.append(",Personal_ID="+Personal_ID);
		sb.append(",Customer_Name="+Customer_Name);
		sb.append(",Customer_FirstName="+Customer_FirstName);
		sb.append(",Customer_LastName="+Customer_LastName);
		sb.append(",Birth_Date="+Birth_Date);
		sb.append(",Customer_Address="+Customer_Address);
		sb.append(",Street_ID="+Street_ID);
		sb.append(",Street_Number="+Street_Number);
		sb.append(",Customer_Type_ID="+Customer_Type_ID);
		sb.append(",Customer_Type="+Customer_Type);
		sb.append(",Customer_Group_ID="+Customer_Group_ID);
		sb.append(",Customer_Group="+Customer_Group);
		sb.append(",Employee_ID="+Employee_ID);
		sb.append(",EmployeeStart_Date="+EmployeeStart_Date);
		sb.append(",Employee_End_Date="+Employee_End_Date);
		sb.append(",Employee_Job_Title="+Employee_Job_Title);
		sb.append(",Employee_Salary="+Employee_Salary);
		sb.append(",Employee_Gender="+Employee_Gender);
		sb.append(",Employee_Birth_Date="+Employee_Birth_Date);
		sb.append(",Emp_Hire_Date="+Emp_Hire_Date);
		sb.append(",Emp_Term_Date="+Emp_Term_Date);
		sb.append(",EmpLoyee_Name="+EmpLoyee_Name);
		sb.append(",Employee_Country="+Employee_Country);
		sb.append(",Employee_Org_Level="+Employee_Org_Level);
		sb.append(",Org_Level_Name="+Org_Level_Name);
		sb.append(",Country="+Country);
		sb.append(",Country_Name="+Country_Name);
		sb.append(",Population="+Population);
		sb.append(",Country_ID="+Country_ID);
		sb.append(",Continent_ID="+Continent_ID);
		sb.append(",Country_FormerName="+Country_FormerName);
		sb.append(",Continent_Name="+Continent_Name);
		sb.append(",Supplier_ID="+Supplier_ID);
		sb.append(",Supplier_Name="+Supplier_Name);
		sb.append(",Supplier_Address="+Supplier_Address);
		sb.append(",Supplier_Country="+Supplier_Country);
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
			if (resumeIt || globalResumeTicket) { //start the resume
				globalResumeTicket = true;



		row1Struct row1 = new row1Struct();
row2Struct row2 = new row2Struct();





	
	/**
	 * [tFileOutputDelimited_1 begin ] start
	 */

	

	
		
		ok_Hash.put("tFileOutputDelimited_1", false);
		start_Hash.put("tFileOutputDelimited_1", System.currentTimeMillis());
		
	
	currentComponent="tFileOutputDelimited_1";

	
					if(execStat) {
						runStat.updateStatOnConnection(resourceMap,iterateId,0,0,"row2");
					}
				
		int tos_count_tFileOutputDelimited_1 = 0;
		

String fileName_tFileOutputDelimited_1 = "";
    fileName_tFileOutputDelimited_1 = (new java.io.File(context.folderName+"/files/BDD_CASIOP.csv")).getAbsolutePath().replace("\\","/");
    String fullName_tFileOutputDelimited_1 = null;
    String extension_tFileOutputDelimited_1 = null;
    String directory_tFileOutputDelimited_1 = null;
    if((fileName_tFileOutputDelimited_1.indexOf("/") != -1)) {
        if(fileName_tFileOutputDelimited_1.lastIndexOf(".") < fileName_tFileOutputDelimited_1.lastIndexOf("/")) {
            fullName_tFileOutputDelimited_1 = fileName_tFileOutputDelimited_1;
            extension_tFileOutputDelimited_1 = "";
        } else {
            fullName_tFileOutputDelimited_1 = fileName_tFileOutputDelimited_1.substring(0, fileName_tFileOutputDelimited_1.lastIndexOf("."));
            extension_tFileOutputDelimited_1 = fileName_tFileOutputDelimited_1.substring(fileName_tFileOutputDelimited_1.lastIndexOf("."));
        }
        directory_tFileOutputDelimited_1 = fileName_tFileOutputDelimited_1.substring(0, fileName_tFileOutputDelimited_1.lastIndexOf("/"));
    } else {
        if(fileName_tFileOutputDelimited_1.lastIndexOf(".") != -1) {
            fullName_tFileOutputDelimited_1 = fileName_tFileOutputDelimited_1.substring(0, fileName_tFileOutputDelimited_1.lastIndexOf("."));
            extension_tFileOutputDelimited_1 = fileName_tFileOutputDelimited_1.substring(fileName_tFileOutputDelimited_1.lastIndexOf("."));
        } else {
            fullName_tFileOutputDelimited_1 = fileName_tFileOutputDelimited_1;
            extension_tFileOutputDelimited_1 = "";
        }
        directory_tFileOutputDelimited_1 = "";
    }
    boolean isFileGenerated_tFileOutputDelimited_1 = true;
    java.io.File filetFileOutputDelimited_1 = new java.io.File(fileName_tFileOutputDelimited_1);
    globalMap.put("tFileOutputDelimited_1_FILE_NAME",fileName_tFileOutputDelimited_1);
            int nb_line_tFileOutputDelimited_1 = 0;
            int splitedFileNo_tFileOutputDelimited_1 = 0;
            int currentRow_tFileOutputDelimited_1 = 0;

            final String OUT_DELIM_tFileOutputDelimited_1 = /** Start field tFileOutputDelimited_1:FIELDSEPARATOR */";"/** End field tFileOutputDelimited_1:FIELDSEPARATOR */;

            final String OUT_DELIM_ROWSEP_tFileOutputDelimited_1 = /** Start field tFileOutputDelimited_1:ROWSEPARATOR */"\n"/** End field tFileOutputDelimited_1:ROWSEPARATOR */;

                    //create directory only if not exists
                    if(directory_tFileOutputDelimited_1 != null && directory_tFileOutputDelimited_1.trim().length() != 0) {
                        java.io.File dir_tFileOutputDelimited_1 = new java.io.File(directory_tFileOutputDelimited_1);
                        if(!dir_tFileOutputDelimited_1.exists()) {
                            dir_tFileOutputDelimited_1.mkdirs();
                        }
                    }

                        //routines.system.Row
                        java.io.Writer outtFileOutputDelimited_1 = null;

                        java.io.File fileToDelete_tFileOutputDelimited_1 = new java.io.File(fileName_tFileOutputDelimited_1);
                        if(fileToDelete_tFileOutputDelimited_1.exists()) {
                            fileToDelete_tFileOutputDelimited_1.delete();
                        }
                        outtFileOutputDelimited_1 = new java.io.BufferedWriter(new java.io.OutputStreamWriter(
                        new java.io.FileOutputStream(fileName_tFileOutputDelimited_1, false),"UTF-8"));
                                    if(filetFileOutputDelimited_1.length()==0){
                                        outtFileOutputDelimited_1.write("Customer_Group_ID");
                                            outtFileOutputDelimited_1.write(OUT_DELIM_tFileOutputDelimited_1);
                                        outtFileOutputDelimited_1.write("Customer_Group");
                                            outtFileOutputDelimited_1.write(OUT_DELIM_tFileOutputDelimited_1);
                                        outtFileOutputDelimited_1.write("Customer_Type_ID");
                                            outtFileOutputDelimited_1.write(OUT_DELIM_tFileOutputDelimited_1);
                                        outtFileOutputDelimited_1.write("Customer_Type");
                                            outtFileOutputDelimited_1.write(OUT_DELIM_tFileOutputDelimited_1);
                                        outtFileOutputDelimited_1.write("Birth_Date");
                                            outtFileOutputDelimited_1.write(OUT_DELIM_tFileOutputDelimited_1);
                                        outtFileOutputDelimited_1.write("Gender");
                                            outtFileOutputDelimited_1.write(OUT_DELIM_tFileOutputDelimited_1);
                                        outtFileOutputDelimited_1.write("Customer_ID");
                                            outtFileOutputDelimited_1.write(OUT_DELIM_tFileOutputDelimited_1);
                                        outtFileOutputDelimited_1.write("Country");
                                            outtFileOutputDelimited_1.write(OUT_DELIM_tFileOutputDelimited_1);
                                        outtFileOutputDelimited_1.write("Country_Name");
                                            outtFileOutputDelimited_1.write(OUT_DELIM_tFileOutputDelimited_1);
                                        outtFileOutputDelimited_1.write("Employee_ID");
                                            outtFileOutputDelimited_1.write(OUT_DELIM_tFileOutputDelimited_1);
                                        outtFileOutputDelimited_1.write("Employee_Job_Title");
                                            outtFileOutputDelimited_1.write(OUT_DELIM_tFileOutputDelimited_1);
                                        outtFileOutputDelimited_1.write("Employee_Salary");
                                            outtFileOutputDelimited_1.write(OUT_DELIM_tFileOutputDelimited_1);
                                        outtFileOutputDelimited_1.write("Employee_Gender");
                                            outtFileOutputDelimited_1.write(OUT_DELIM_tFileOutputDelimited_1);
                                        outtFileOutputDelimited_1.write("Employee_Country");
                                            outtFileOutputDelimited_1.write(OUT_DELIM_tFileOutputDelimited_1);
                                        outtFileOutputDelimited_1.write("Order_Type");
                                            outtFileOutputDelimited_1.write(OUT_DELIM_tFileOutputDelimited_1);
                                        outtFileOutputDelimited_1.write("Order_Date");
                                            outtFileOutputDelimited_1.write(OUT_DELIM_tFileOutputDelimited_1);
                                        outtFileOutputDelimited_1.write("Delivery_Date");
                                            outtFileOutputDelimited_1.write(OUT_DELIM_tFileOutputDelimited_1);
                                        outtFileOutputDelimited_1.write("Product_ID");
                                            outtFileOutputDelimited_1.write(OUT_DELIM_tFileOutputDelimited_1);
                                        outtFileOutputDelimited_1.write("Product_Name");
                                            outtFileOutputDelimited_1.write(OUT_DELIM_tFileOutputDelimited_1);
                                        outtFileOutputDelimited_1.write("Product_Ref_ID");
                                            outtFileOutputDelimited_1.write(OUT_DELIM_tFileOutputDelimited_1);
                                        outtFileOutputDelimited_1.write("Quantity");
                                            outtFileOutputDelimited_1.write(OUT_DELIM_tFileOutputDelimited_1);
                                        outtFileOutputDelimited_1.write("Order_ID");
                                            outtFileOutputDelimited_1.write(OUT_DELIM_tFileOutputDelimited_1);
                                        outtFileOutputDelimited_1.write("Order_Item_Num");
                                            outtFileOutputDelimited_1.write(OUT_DELIM_tFileOutputDelimited_1);
                                        outtFileOutputDelimited_1.write("CostPrice_Per_Unit");
                                            outtFileOutputDelimited_1.write(OUT_DELIM_tFileOutputDelimited_1);
                                        outtFileOutputDelimited_1.write("Total_Retail_Price");
                                        outtFileOutputDelimited_1.write(OUT_DELIM_ROWSEP_tFileOutputDelimited_1);
                                        outtFileOutputDelimited_1.flush();
                                    }


        resourceMap.put("out_tFileOutputDelimited_1", outtFileOutputDelimited_1);
resourceMap.put("nb_line_tFileOutputDelimited_1", nb_line_tFileOutputDelimited_1);

 



/**
 * [tFileOutputDelimited_1 begin ] stop
 */



	
	/**
	 * [tFilterColumns_1 begin ] start
	 */

	

	
		
		ok_Hash.put("tFilterColumns_1", false);
		start_Hash.put("tFilterColumns_1", System.currentTimeMillis());
		
	
	currentComponent="tFilterColumns_1";

	
					if(execStat) {
						runStat.updateStatOnConnection(resourceMap,iterateId,0,0,"row1");
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
		
	
	currentComponent="tFileInputDelimited_1";

	
		int tos_count_tFileInputDelimited_1 = 0;
		
	
	
	
 
	
	
	final routines.system.RowState rowstate_tFileInputDelimited_1 = new routines.system.RowState();
	
	
				int nb_line_tFileInputDelimited_1 = 0;
				int footer_tFileInputDelimited_1 = 1;
				int totalLinetFileInputDelimited_1 = 0;
				int limittFileInputDelimited_1 = -1;
				int lastLinetFileInputDelimited_1 = -1;	
				
				char fieldSeparator_tFileInputDelimited_1[] = null;
				
				//support passing value (property: Field Separator) by 'context.fs' or 'globalMap.get("fs")'. 
				if ( ((String)";").length() > 0 ){
					fieldSeparator_tFileInputDelimited_1 = ((String)";").toCharArray();
				}else {			
					throw new IllegalArgumentException("Field Separator must be assigned a char."); 
				}
			
				char rowSeparator_tFileInputDelimited_1[] = null;
			
				//support passing value (property: Row Separator) by 'context.rs' or 'globalMap.get("rs")'. 
				if ( ((String)"\n").length() > 0 ){
					rowSeparator_tFileInputDelimited_1 = ((String)"\n").toCharArray();
				}else {
					throw new IllegalArgumentException("Row Separator must be assigned a char."); 
				}
			
				Object filename_tFileInputDelimited_1 = /** Start field tFileInputDelimited_1:FILENAME */context.folderName+"/files/BDD_CASIOP_FULL.csv"/** End field tFileInputDelimited_1:FILENAME */;		
				com.talend.csv.CSVReader csvReadertFileInputDelimited_1 = null;
	
				try{
					
						String[] rowtFileInputDelimited_1=null;
						int currentLinetFileInputDelimited_1 = 0;
	        			int outputLinetFileInputDelimited_1 = 0;
						try {//TD110 begin
							if(filename_tFileInputDelimited_1 instanceof java.io.InputStream){
							
			int footer_value_tFileInputDelimited_1 = 1;
			if(footer_value_tFileInputDelimited_1 > 0){
				throw new java.lang.Exception("When the input source is a stream,footer shouldn't be bigger than 0.");
			}
		
								csvReadertFileInputDelimited_1=new com.talend.csv.CSVReader((java.io.InputStream)filename_tFileInputDelimited_1, fieldSeparator_tFileInputDelimited_1[0], "UTF-8");
							}else{
								csvReadertFileInputDelimited_1=new com.talend.csv.CSVReader(String.valueOf(filename_tFileInputDelimited_1),fieldSeparator_tFileInputDelimited_1[0], "UTF-8");
		        			}
					
					
					csvReadertFileInputDelimited_1.setTrimWhitespace(false);
					if ( (rowSeparator_tFileInputDelimited_1[0] != '\n') && (rowSeparator_tFileInputDelimited_1[0] != '\r') )
	        			csvReadertFileInputDelimited_1.setLineEnd(""+rowSeparator_tFileInputDelimited_1[0]);
						
	        				csvReadertFileInputDelimited_1.setQuoteChar('"');
						
	            				csvReadertFileInputDelimited_1.setEscapeChar(csvReadertFileInputDelimited_1.getQuoteChar());
							      
		
			
						if(footer_tFileInputDelimited_1 > 0){
						for(totalLinetFileInputDelimited_1=0;totalLinetFileInputDelimited_1 < 1; totalLinetFileInputDelimited_1++){
							csvReadertFileInputDelimited_1.readNext();
						}
						csvReadertFileInputDelimited_1.setSkipEmptyRecords(true);
			            while (csvReadertFileInputDelimited_1.readNext()) {
							
								rowtFileInputDelimited_1=csvReadertFileInputDelimited_1.getValues();
								if(!(rowtFileInputDelimited_1.length == 1 && ("\015").equals(rowtFileInputDelimited_1[0]))){//empty line when row separator is '\n'
							
	                
	                		totalLinetFileInputDelimited_1++;
	                
							
								}
							
	                
			            }
	            		int lastLineTemptFileInputDelimited_1 = totalLinetFileInputDelimited_1 - footer_tFileInputDelimited_1   < 0? 0 : totalLinetFileInputDelimited_1 - footer_tFileInputDelimited_1 ;
	            		if(lastLinetFileInputDelimited_1 > 0){
	                		lastLinetFileInputDelimited_1 = lastLinetFileInputDelimited_1 < lastLineTemptFileInputDelimited_1 ? lastLinetFileInputDelimited_1 : lastLineTemptFileInputDelimited_1; 
	            		}else {
	                		lastLinetFileInputDelimited_1 = lastLineTemptFileInputDelimited_1;
	            		}
	         
			          	csvReadertFileInputDelimited_1.close();
				        if(filename_tFileInputDelimited_1 instanceof java.io.InputStream){
				 			csvReadertFileInputDelimited_1=new com.talend.csv.CSVReader((java.io.InputStream)filename_tFileInputDelimited_1, fieldSeparator_tFileInputDelimited_1[0], "UTF-8");
		        		}else{
							csvReadertFileInputDelimited_1=new com.talend.csv.CSVReader(String.valueOf(filename_tFileInputDelimited_1),fieldSeparator_tFileInputDelimited_1[0], "UTF-8");
						}
						csvReadertFileInputDelimited_1.setTrimWhitespace(false);
						if ( (rowSeparator_tFileInputDelimited_1[0] != '\n') && (rowSeparator_tFileInputDelimited_1[0] != '\r') )	
	        				csvReadertFileInputDelimited_1.setLineEnd(""+rowSeparator_tFileInputDelimited_1[0]);
						
							csvReadertFileInputDelimited_1.setQuoteChar('"');
						
	        				csvReadertFileInputDelimited_1.setEscapeChar(csvReadertFileInputDelimited_1.getQuoteChar());
							  
	        		}
	        
			        if(limittFileInputDelimited_1 != 0){
			        	for(currentLinetFileInputDelimited_1=0;currentLinetFileInputDelimited_1 < 1;currentLinetFileInputDelimited_1++){
			        		csvReadertFileInputDelimited_1.readNext();
			        	}
			        }
			        csvReadertFileInputDelimited_1.setSkipEmptyRecords(true);
	        
	    		} catch(java.lang.Exception e) {
globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",e.getMessage());
					
						
						System.err.println(e.getMessage());
					
	    		}//TD110 end
	        
			    
	        	while ( limittFileInputDelimited_1 != 0 && csvReadertFileInputDelimited_1!=null && csvReadertFileInputDelimited_1.readNext() ) { 
	        		rowstate_tFileInputDelimited_1.reset();
	        
		        	rowtFileInputDelimited_1=csvReadertFileInputDelimited_1.getValues();
		        	
					
	        			if(rowtFileInputDelimited_1.length == 1 && ("\015").equals(rowtFileInputDelimited_1[0])){//empty line when row separator is '\n'
	        				continue;
	        			}
					
	        	
	        	
	        		currentLinetFileInputDelimited_1++;
	            
		            if(lastLinetFileInputDelimited_1 > -1 && currentLinetFileInputDelimited_1 > lastLinetFileInputDelimited_1) {
		                break;
	    	        }
	        	    outputLinetFileInputDelimited_1++;
	            	if (limittFileInputDelimited_1 > 0 && outputLinetFileInputDelimited_1 > limittFileInputDelimited_1) {
	                	break;
	            	}  
	                                                                      
					
	    							row1 = null;			
								
								boolean whetherReject_tFileInputDelimited_1 = false;
								row1 = new row1Struct();
								try {			
									
				char fieldSeparator_tFileInputDelimited_1_ListType[] = null;
				//support passing value (property: Field Separator) by 'context.fs' or 'globalMap.get("fs")'. 
				if ( ((String)";").length() > 0 ){
					fieldSeparator_tFileInputDelimited_1_ListType = ((String)";").toCharArray();
				}else {			
					throw new IllegalArgumentException("Field Separator must be assigned a char."); 
				}
				if(rowtFileInputDelimited_1.length == 1 && ("\015").equals(rowtFileInputDelimited_1[0])){//empty line when row separator is '\n'
					
							row1.Order_ID = null;
					
							row1.Order_Type = null;
					
							row1.Order_Date = null;
					
							row1.Delivery_Date = null;
					
							row1.Order_Item_Num = null;
					
							row1.Quantity = null;
					
							row1.Total_Retail_Price = null;
					
							row1.CostPrice_Per_Unit = null;
					
							row1.Product_ID = null;
					
							row1.Product_Name = null;
					
							row1.Product_Level = null;
					
							row1.Product_Level_Name = null;
					
							row1.Product_Ref_ID = null;
					
							row1.Customer_ID = null;
					
							row1.Gender = null;
					
							row1.Personal_ID = null;
					
							row1.Customer_Name = null;
					
							row1.Customer_FirstName = null;
					
							row1.Customer_LastName = null;
					
							row1.Birth_Date = null;
					
							row1.Customer_Address = null;
					
							row1.Street_ID = null;
					
							row1.Street_Number = null;
					
							row1.Customer_Type_ID = null;
					
							row1.Customer_Type = null;
					
							row1.Customer_Group_ID = null;
					
							row1.Customer_Group = null;
					
							row1.Employee_ID = null;
					
							row1.EmployeeStart_Date = null;
					
							row1.Employee_End_Date = null;
					
							row1.Employee_Job_Title = null;
					
							row1.Employee_Salary = null;
					
							row1.Employee_Gender = null;
					
							row1.Employee_Birth_Date = null;
					
							row1.Emp_Hire_Date = null;
					
							row1.Emp_Term_Date = null;
					
							row1.EmpLoyee_Name = null;
					
							row1.Employee_Country = null;
					
							row1.Employee_Org_Level = null;
					
							row1.Org_Level_Name = null;
					
							row1.Country = null;
					
							row1.Country_Name = null;
					
							row1.Population = null;
					
							row1.Country_ID = null;
					
							row1.Continent_ID = null;
					
							row1.Country_FormerName = null;
					
							row1.Continent_Name = null;
					
							row1.Supplier_ID = null;
					
							row1.Supplier_Name = null;
					
							row1.Supplier_Address = null;
					
							row1.Supplier_Country = null;
					
				}else{
					
	                int columnIndexWithD_tFileInputDelimited_1 = 0; //Column Index 
	                
						columnIndexWithD_tFileInputDelimited_1 = 0;
						
						
						
						if(columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length){
						
						
							
									row1.Order_ID = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];
									
							
						
						}else{
						
							
								row1.Order_ID = null;
							
						
						}
						
						
					
						columnIndexWithD_tFileInputDelimited_1 = 1;
						
						
						
						if(columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length){
						
						
							
									row1.Order_Type = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];
									
							
						
						}else{
						
							
								row1.Order_Type = null;
							
						
						}
						
						
					
						columnIndexWithD_tFileInputDelimited_1 = 2;
						
						
						
						if(columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length){
						
						
							
									row1.Order_Date = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];
									
							
						
						}else{
						
							
								row1.Order_Date = null;
							
						
						}
						
						
					
						columnIndexWithD_tFileInputDelimited_1 = 3;
						
						
						
						if(columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length){
						
						
							
									row1.Delivery_Date = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];
									
							
						
						}else{
						
							
								row1.Delivery_Date = null;
							
						
						}
						
						
					
						columnIndexWithD_tFileInputDelimited_1 = 4;
						
						
						
						if(columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length){
						
						
							
								
									if(rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1].length() > 0) {
										try {
									
										row1.Order_Item_Num = ParserUtils.parseTo_Integer(rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1]);
									
									
										} catch(java.lang.Exception ex_tFileInputDelimited_1) {
globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",ex_tFileInputDelimited_1.getMessage());
											rowstate_tFileInputDelimited_1.setException(new RuntimeException(String.format("Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
												"Order_Item_Num", "row1", rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1], ex_tFileInputDelimited_1), ex_tFileInputDelimited_1));
										}
    								}else{
    									
											
												row1.Order_Item_Num = null;
											
    									
    								}
									
									
							
						
						}else{
						
							
								row1.Order_Item_Num = null;
							
						
						}
						
						
					
						columnIndexWithD_tFileInputDelimited_1 = 5;
						
						
						
						if(columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length){
						
						
							
								
									if(rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1].length() > 0) {
										try {
									
										row1.Quantity = ParserUtils.parseTo_Integer(rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1]);
									
									
										} catch(java.lang.Exception ex_tFileInputDelimited_1) {
globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",ex_tFileInputDelimited_1.getMessage());
											rowstate_tFileInputDelimited_1.setException(new RuntimeException(String.format("Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
												"Quantity", "row1", rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1], ex_tFileInputDelimited_1), ex_tFileInputDelimited_1));
										}
    								}else{
    									
											
												row1.Quantity = null;
											
    									
    								}
									
									
							
						
						}else{
						
							
								row1.Quantity = null;
							
						
						}
						
						
					
						columnIndexWithD_tFileInputDelimited_1 = 6;
						
						
						
						if(columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length){
						
						
							
									row1.Total_Retail_Price = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];
									
							
						
						}else{
						
							
								row1.Total_Retail_Price = null;
							
						
						}
						
						
					
						columnIndexWithD_tFileInputDelimited_1 = 7;
						
						
						
						if(columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length){
						
						
							
									row1.CostPrice_Per_Unit = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];
									
							
						
						}else{
						
							
								row1.CostPrice_Per_Unit = null;
							
						
						}
						
						
					
						columnIndexWithD_tFileInputDelimited_1 = 8;
						
						
						
						if(columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length){
						
						
							
									row1.Product_ID = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];
									
							
						
						}else{
						
							
								row1.Product_ID = null;
							
						
						}
						
						
					
						columnIndexWithD_tFileInputDelimited_1 = 9;
						
						
						
						if(columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length){
						
						
							
									row1.Product_Name = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];
									
							
						
						}else{
						
							
								row1.Product_Name = null;
							
						
						}
						
						
					
						columnIndexWithD_tFileInputDelimited_1 = 10;
						
						
						
						if(columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length){
						
						
							
									row1.Product_Level = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];
									
							
						
						}else{
						
							
								row1.Product_Level = null;
							
						
						}
						
						
					
						columnIndexWithD_tFileInputDelimited_1 = 11;
						
						
						
						if(columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length){
						
						
							
									row1.Product_Level_Name = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];
									
							
						
						}else{
						
							
								row1.Product_Level_Name = null;
							
						
						}
						
						
					
						columnIndexWithD_tFileInputDelimited_1 = 12;
						
						
						
						if(columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length){
						
						
							
									row1.Product_Ref_ID = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];
									
							
						
						}else{
						
							
								row1.Product_Ref_ID = null;
							
						
						}
						
						
					
						columnIndexWithD_tFileInputDelimited_1 = 13;
						
						
						
						if(columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length){
						
						
							
									row1.Customer_ID = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];
									
							
						
						}else{
						
							
								row1.Customer_ID = null;
							
						
						}
						
						
					
						columnIndexWithD_tFileInputDelimited_1 = 14;
						
						
						
						if(columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length){
						
						
							
								
									if(rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1].length() > 0) {
										try {
									
										row1.Gender = ParserUtils.parseTo_Character(rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1]);
									
									
										} catch(java.lang.Exception ex_tFileInputDelimited_1) {
globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",ex_tFileInputDelimited_1.getMessage());
											rowstate_tFileInputDelimited_1.setException(new RuntimeException(String.format("Couldn't parse value for column '%s' in '%s', value is '%s'. Details: %s",
												"Gender", "row1", rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1], ex_tFileInputDelimited_1), ex_tFileInputDelimited_1));
										}
    								}else{
    									
											
												row1.Gender = null;
											
    									
    								}
									
									
							
						
						}else{
						
							
								row1.Gender = null;
							
						
						}
						
						
					
						columnIndexWithD_tFileInputDelimited_1 = 15;
						
						
						
						if(columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length){
						
						
							
									row1.Personal_ID = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];
									
							
						
						}else{
						
							
								row1.Personal_ID = null;
							
						
						}
						
						
					
						columnIndexWithD_tFileInputDelimited_1 = 16;
						
						
						
						if(columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length){
						
						
							
									row1.Customer_Name = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];
									
							
						
						}else{
						
							
								row1.Customer_Name = null;
							
						
						}
						
						
					
						columnIndexWithD_tFileInputDelimited_1 = 17;
						
						
						
						if(columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length){
						
						
							
									row1.Customer_FirstName = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];
									
							
						
						}else{
						
							
								row1.Customer_FirstName = null;
							
						
						}
						
						
					
						columnIndexWithD_tFileInputDelimited_1 = 18;
						
						
						
						if(columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length){
						
						
							
									row1.Customer_LastName = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];
									
							
						
						}else{
						
							
								row1.Customer_LastName = null;
							
						
						}
						
						
					
						columnIndexWithD_tFileInputDelimited_1 = 19;
						
						
						
						if(columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length){
						
						
							
									row1.Birth_Date = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];
									
							
						
						}else{
						
							
								row1.Birth_Date = null;
							
						
						}
						
						
					
						columnIndexWithD_tFileInputDelimited_1 = 20;
						
						
						
						if(columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length){
						
						
							
									row1.Customer_Address = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];
									
							
						
						}else{
						
							
								row1.Customer_Address = null;
							
						
						}
						
						
					
						columnIndexWithD_tFileInputDelimited_1 = 21;
						
						
						
						if(columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length){
						
						
							
									row1.Street_ID = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];
									
							
						
						}else{
						
							
								row1.Street_ID = null;
							
						
						}
						
						
					
						columnIndexWithD_tFileInputDelimited_1 = 22;
						
						
						
						if(columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length){
						
						
							
									row1.Street_Number = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];
									
							
						
						}else{
						
							
								row1.Street_Number = null;
							
						
						}
						
						
					
						columnIndexWithD_tFileInputDelimited_1 = 23;
						
						
						
						if(columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length){
						
						
							
									row1.Customer_Type_ID = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];
									
							
						
						}else{
						
							
								row1.Customer_Type_ID = null;
							
						
						}
						
						
					
						columnIndexWithD_tFileInputDelimited_1 = 24;
						
						
						
						if(columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length){
						
						
							
									row1.Customer_Type = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];
									
							
						
						}else{
						
							
								row1.Customer_Type = null;
							
						
						}
						
						
					
						columnIndexWithD_tFileInputDelimited_1 = 25;
						
						
						
						if(columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length){
						
						
							
									row1.Customer_Group_ID = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];
									
							
						
						}else{
						
							
								row1.Customer_Group_ID = null;
							
						
						}
						
						
					
						columnIndexWithD_tFileInputDelimited_1 = 26;
						
						
						
						if(columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length){
						
						
							
									row1.Customer_Group = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];
									
							
						
						}else{
						
							
								row1.Customer_Group = null;
							
						
						}
						
						
					
						columnIndexWithD_tFileInputDelimited_1 = 27;
						
						
						
						if(columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length){
						
						
							
									row1.Employee_ID = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];
									
							
						
						}else{
						
							
								row1.Employee_ID = null;
							
						
						}
						
						
					
						columnIndexWithD_tFileInputDelimited_1 = 28;
						
						
						
						if(columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length){
						
						
							
									row1.EmployeeStart_Date = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];
									
							
						
						}else{
						
							
								row1.EmployeeStart_Date = null;
							
						
						}
						
						
					
						columnIndexWithD_tFileInputDelimited_1 = 29;
						
						
						
						if(columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length){
						
						
							
									row1.Employee_End_Date = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];
									
							
						
						}else{
						
							
								row1.Employee_End_Date = null;
							
						
						}
						
						
					
						columnIndexWithD_tFileInputDelimited_1 = 30;
						
						
						
						if(columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length){
						
						
							
									row1.Employee_Job_Title = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];
									
							
						
						}else{
						
							
								row1.Employee_Job_Title = null;
							
						
						}
						
						
					
						columnIndexWithD_tFileInputDelimited_1 = 31;
						
						
						
						if(columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length){
						
						
							
									row1.Employee_Salary = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];
									
							
						
						}else{
						
							
								row1.Employee_Salary = null;
							
						
						}
						
						
					
						columnIndexWithD_tFileInputDelimited_1 = 32;
						
						
						
						if(columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length){
						
						
							
									row1.Employee_Gender = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];
									
							
						
						}else{
						
							
								row1.Employee_Gender = null;
							
						
						}
						
						
					
						columnIndexWithD_tFileInputDelimited_1 = 33;
						
						
						
						if(columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length){
						
						
							
									row1.Employee_Birth_Date = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];
									
							
						
						}else{
						
							
								row1.Employee_Birth_Date = null;
							
						
						}
						
						
					
						columnIndexWithD_tFileInputDelimited_1 = 34;
						
						
						
						if(columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length){
						
						
							
									row1.Emp_Hire_Date = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];
									
							
						
						}else{
						
							
								row1.Emp_Hire_Date = null;
							
						
						}
						
						
					
						columnIndexWithD_tFileInputDelimited_1 = 35;
						
						
						
						if(columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length){
						
						
							
									row1.Emp_Term_Date = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];
									
							
						
						}else{
						
							
								row1.Emp_Term_Date = null;
							
						
						}
						
						
					
						columnIndexWithD_tFileInputDelimited_1 = 36;
						
						
						
						if(columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length){
						
						
							
									row1.EmpLoyee_Name = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];
									
							
						
						}else{
						
							
								row1.EmpLoyee_Name = null;
							
						
						}
						
						
					
						columnIndexWithD_tFileInputDelimited_1 = 37;
						
						
						
						if(columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length){
						
						
							
									row1.Employee_Country = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];
									
							
						
						}else{
						
							
								row1.Employee_Country = null;
							
						
						}
						
						
					
						columnIndexWithD_tFileInputDelimited_1 = 38;
						
						
						
						if(columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length){
						
						
							
									row1.Employee_Org_Level = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];
									
							
						
						}else{
						
							
								row1.Employee_Org_Level = null;
							
						
						}
						
						
					
						columnIndexWithD_tFileInputDelimited_1 = 39;
						
						
						
						if(columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length){
						
						
							
									row1.Org_Level_Name = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];
									
							
						
						}else{
						
							
								row1.Org_Level_Name = null;
							
						
						}
						
						
					
						columnIndexWithD_tFileInputDelimited_1 = 40;
						
						
						
						if(columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length){
						
						
							
									row1.Country = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];
									
							
						
						}else{
						
							
								row1.Country = null;
							
						
						}
						
						
					
						columnIndexWithD_tFileInputDelimited_1 = 41;
						
						
						
						if(columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length){
						
						
							
									row1.Country_Name = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];
									
							
						
						}else{
						
							
								row1.Country_Name = null;
							
						
						}
						
						
					
						columnIndexWithD_tFileInputDelimited_1 = 42;
						
						
						
						if(columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length){
						
						
							
									row1.Population = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];
									
							
						
						}else{
						
							
								row1.Population = null;
							
						
						}
						
						
					
						columnIndexWithD_tFileInputDelimited_1 = 43;
						
						
						
						if(columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length){
						
						
							
									row1.Country_ID = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];
									
							
						
						}else{
						
							
								row1.Country_ID = null;
							
						
						}
						
						
					
						columnIndexWithD_tFileInputDelimited_1 = 44;
						
						
						
						if(columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length){
						
						
							
									row1.Continent_ID = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];
									
							
						
						}else{
						
							
								row1.Continent_ID = null;
							
						
						}
						
						
					
						columnIndexWithD_tFileInputDelimited_1 = 45;
						
						
						
						if(columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length){
						
						
							
									row1.Country_FormerName = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];
									
							
						
						}else{
						
							
								row1.Country_FormerName = null;
							
						
						}
						
						
					
						columnIndexWithD_tFileInputDelimited_1 = 46;
						
						
						
						if(columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length){
						
						
							
									row1.Continent_Name = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];
									
							
						
						}else{
						
							
								row1.Continent_Name = null;
							
						
						}
						
						
					
						columnIndexWithD_tFileInputDelimited_1 = 47;
						
						
						
						if(columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length){
						
						
							
									row1.Supplier_ID = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];
									
							
						
						}else{
						
							
								row1.Supplier_ID = null;
							
						
						}
						
						
					
						columnIndexWithD_tFileInputDelimited_1 = 48;
						
						
						
						if(columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length){
						
						
							
									row1.Supplier_Name = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];
									
							
						
						}else{
						
							
								row1.Supplier_Name = null;
							
						
						}
						
						
					
						columnIndexWithD_tFileInputDelimited_1 = 49;
						
						
						
						if(columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length){
						
						
							
									row1.Supplier_Address = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];
									
							
						
						}else{
						
							
								row1.Supplier_Address = null;
							
						
						}
						
						
					
						columnIndexWithD_tFileInputDelimited_1 = 50;
						
						
						
						if(columnIndexWithD_tFileInputDelimited_1 < rowtFileInputDelimited_1.length){
						
						
							
									row1.Supplier_Country = rowtFileInputDelimited_1[columnIndexWithD_tFileInputDelimited_1];
									
							
						
						}else{
						
							
								row1.Supplier_Country = null;
							
						
						}
						
						
					
				}
				
									
									if(rowstate_tFileInputDelimited_1.getException()!=null) {
										throw rowstate_tFileInputDelimited_1.getException();
									}
									
									
	    						} catch (java.lang.Exception e) {
globalMap.put("tFileInputDelimited_1_ERROR_MESSAGE",e.getMessage());
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

	

	
	
	currentComponent="tFileInputDelimited_1";

	

 


	tos_count_tFileInputDelimited_1++;

/**
 * [tFileInputDelimited_1 main ] stop
 */
	
	/**
	 * [tFileInputDelimited_1 process_data_begin ] start
	 */

	

	
	
	currentComponent="tFileInputDelimited_1";

	

 



/**
 * [tFileInputDelimited_1 process_data_begin ] stop
 */
// Start of branch "row1"
if(row1 != null) { 



	
	/**
	 * [tFilterColumns_1 main ] start
	 */

	

	
	
	currentComponent="tFilterColumns_1";

	
					if(execStat){
						runStat.updateStatOnConnection(iterateId,1,1
						
							,"row1"
						
						);
					}
					
	

	row2.Customer_Group_ID = row1.Customer_Group_ID;

	
	row2.Customer_Group = row1.Customer_Group;

	
	row2.Customer_Type_ID = row1.Customer_Type_ID;

	
	row2.Customer_Type = row1.Customer_Type;

	
	row2.Birth_Date = row1.Birth_Date;

	
	row2.Gender = row1.Gender;

	
	row2.Customer_ID = row1.Customer_ID;

	
	row2.Country = row1.Country;

	
	row2.Country_Name = row1.Country_Name;

	
	row2.Employee_ID = row1.Employee_ID;

	
	row2.Employee_Job_Title = row1.Employee_Job_Title;

	
	row2.Employee_Salary = row1.Employee_Salary;

	
	row2.Employee_Gender = row1.Employee_Gender;

	
	row2.Employee_Country = row1.Employee_Country;

	
	row2.Order_Type = row1.Order_Type;

	
	row2.Order_Date = row1.Order_Date;

	
	row2.Delivery_Date = row1.Delivery_Date;

	
	row2.Product_ID = row1.Product_ID;

	
	row2.Product_Name = row1.Product_Name;

	
	row2.Product_Ref_ID = row1.Product_Ref_ID;

	
	row2.Quantity = row1.Quantity;

	
	row2.Order_ID = row1.Order_ID;

	
	row2.Order_Item_Num = row1.Order_Item_Num;

	
	row2.CostPrice_Per_Unit = row1.CostPrice_Per_Unit;

	
	row2.Total_Retail_Price = row1.Total_Retail_Price;

	
    nb_line_tFilterColumns_1++;

 


	tos_count_tFilterColumns_1++;

/**
 * [tFilterColumns_1 main ] stop
 */
	
	/**
	 * [tFilterColumns_1 process_data_begin ] start
	 */

	

	
	
	currentComponent="tFilterColumns_1";

	

 



/**
 * [tFilterColumns_1 process_data_begin ] stop
 */

	
	/**
	 * [tFileOutputDelimited_1 main ] start
	 */

	

	
	
	currentComponent="tFileOutputDelimited_1";

	
					if(execStat){
						runStat.updateStatOnConnection(iterateId,1,1
						
							,"row2"
						
						);
					}
					


                    StringBuilder sb_tFileOutputDelimited_1 = new StringBuilder();
                            if(row2.Customer_Group_ID != null) {
                        sb_tFileOutputDelimited_1.append(
                            row2.Customer_Group_ID
                        );
                            }
                            sb_tFileOutputDelimited_1.append(OUT_DELIM_tFileOutputDelimited_1);
                            if(row2.Customer_Group != null) {
                        sb_tFileOutputDelimited_1.append(
                            row2.Customer_Group
                        );
                            }
                            sb_tFileOutputDelimited_1.append(OUT_DELIM_tFileOutputDelimited_1);
                            if(row2.Customer_Type_ID != null) {
                        sb_tFileOutputDelimited_1.append(
                            row2.Customer_Type_ID
                        );
                            }
                            sb_tFileOutputDelimited_1.append(OUT_DELIM_tFileOutputDelimited_1);
                            if(row2.Customer_Type != null) {
                        sb_tFileOutputDelimited_1.append(
                            row2.Customer_Type
                        );
                            }
                            sb_tFileOutputDelimited_1.append(OUT_DELIM_tFileOutputDelimited_1);
                            if(row2.Birth_Date != null) {
                        sb_tFileOutputDelimited_1.append(
                            row2.Birth_Date
                        );
                            }
                            sb_tFileOutputDelimited_1.append(OUT_DELIM_tFileOutputDelimited_1);
                        sb_tFileOutputDelimited_1.append(
                            row2.Gender
                        );
                            sb_tFileOutputDelimited_1.append(OUT_DELIM_tFileOutputDelimited_1);
                            if(row2.Customer_ID != null) {
                        sb_tFileOutputDelimited_1.append(
                            row2.Customer_ID
                        );
                            }
                            sb_tFileOutputDelimited_1.append(OUT_DELIM_tFileOutputDelimited_1);
                            if(row2.Country != null) {
                        sb_tFileOutputDelimited_1.append(
                            row2.Country
                        );
                            }
                            sb_tFileOutputDelimited_1.append(OUT_DELIM_tFileOutputDelimited_1);
                            if(row2.Country_Name != null) {
                        sb_tFileOutputDelimited_1.append(
                            row2.Country_Name
                        );
                            }
                            sb_tFileOutputDelimited_1.append(OUT_DELIM_tFileOutputDelimited_1);
                            if(row2.Employee_ID != null) {
                        sb_tFileOutputDelimited_1.append(
                            row2.Employee_ID
                        );
                            }
                            sb_tFileOutputDelimited_1.append(OUT_DELIM_tFileOutputDelimited_1);
                            if(row2.Employee_Job_Title != null) {
                        sb_tFileOutputDelimited_1.append(
                            row2.Employee_Job_Title
                        );
                            }
                            sb_tFileOutputDelimited_1.append(OUT_DELIM_tFileOutputDelimited_1);
                            if(row2.Employee_Salary != null) {
                        sb_tFileOutputDelimited_1.append(
                            row2.Employee_Salary
                        );
                            }
                            sb_tFileOutputDelimited_1.append(OUT_DELIM_tFileOutputDelimited_1);
                            if(row2.Employee_Gender != null) {
                        sb_tFileOutputDelimited_1.append(
                            row2.Employee_Gender
                        );
                            }
                            sb_tFileOutputDelimited_1.append(OUT_DELIM_tFileOutputDelimited_1);
                            if(row2.Employee_Country != null) {
                        sb_tFileOutputDelimited_1.append(
                            row2.Employee_Country
                        );
                            }
                            sb_tFileOutputDelimited_1.append(OUT_DELIM_tFileOutputDelimited_1);
                            if(row2.Order_Type != null) {
                        sb_tFileOutputDelimited_1.append(
                            row2.Order_Type
                        );
                            }
                            sb_tFileOutputDelimited_1.append(OUT_DELIM_tFileOutputDelimited_1);
                            if(row2.Order_Date != null) {
                        sb_tFileOutputDelimited_1.append(
                            row2.Order_Date
                        );
                            }
                            sb_tFileOutputDelimited_1.append(OUT_DELIM_tFileOutputDelimited_1);
                            if(row2.Delivery_Date != null) {
                        sb_tFileOutputDelimited_1.append(
                            row2.Delivery_Date
                        );
                            }
                            sb_tFileOutputDelimited_1.append(OUT_DELIM_tFileOutputDelimited_1);
                            if(row2.Product_ID != null) {
                        sb_tFileOutputDelimited_1.append(
                            row2.Product_ID
                        );
                            }
                            sb_tFileOutputDelimited_1.append(OUT_DELIM_tFileOutputDelimited_1);
                            if(row2.Product_Name != null) {
                        sb_tFileOutputDelimited_1.append(
                            row2.Product_Name
                        );
                            }
                            sb_tFileOutputDelimited_1.append(OUT_DELIM_tFileOutputDelimited_1);
                            if(row2.Product_Ref_ID != null) {
                        sb_tFileOutputDelimited_1.append(
                            row2.Product_Ref_ID
                        );
                            }
                            sb_tFileOutputDelimited_1.append(OUT_DELIM_tFileOutputDelimited_1);
                        sb_tFileOutputDelimited_1.append(
                            row2.Quantity
                        );
                            sb_tFileOutputDelimited_1.append(OUT_DELIM_tFileOutputDelimited_1);
                            if(row2.Order_ID != null) {
                        sb_tFileOutputDelimited_1.append(
                            row2.Order_ID
                        );
                            }
                            sb_tFileOutputDelimited_1.append(OUT_DELIM_tFileOutputDelimited_1);
                        sb_tFileOutputDelimited_1.append(
                            row2.Order_Item_Num
                        );
                            sb_tFileOutputDelimited_1.append(OUT_DELIM_tFileOutputDelimited_1);
                            if(row2.CostPrice_Per_Unit != null) {
                        sb_tFileOutputDelimited_1.append(
                            row2.CostPrice_Per_Unit
                        );
                            }
                            sb_tFileOutputDelimited_1.append(OUT_DELIM_tFileOutputDelimited_1);
                            if(row2.Total_Retail_Price != null) {
                        sb_tFileOutputDelimited_1.append(
                            row2.Total_Retail_Price
                        );
                            }
                    sb_tFileOutputDelimited_1.append(OUT_DELIM_ROWSEP_tFileOutputDelimited_1);


                    nb_line_tFileOutputDelimited_1++;
                    resourceMap.put("nb_line_tFileOutputDelimited_1", nb_line_tFileOutputDelimited_1);

                        outtFileOutputDelimited_1.write(sb_tFileOutputDelimited_1.toString());




 


	tos_count_tFileOutputDelimited_1++;

/**
 * [tFileOutputDelimited_1 main ] stop
 */
	
	/**
	 * [tFileOutputDelimited_1 process_data_begin ] start
	 */

	

	
	
	currentComponent="tFileOutputDelimited_1";

	

 



/**
 * [tFileOutputDelimited_1 process_data_begin ] stop
 */
	
	/**
	 * [tFileOutputDelimited_1 process_data_end ] start
	 */

	

	
	
	currentComponent="tFileOutputDelimited_1";

	

 



/**
 * [tFileOutputDelimited_1 process_data_end ] stop
 */



	
	/**
	 * [tFilterColumns_1 process_data_end ] start
	 */

	

	
	
	currentComponent="tFilterColumns_1";

	

 



/**
 * [tFilterColumns_1 process_data_end ] stop
 */

} // End of branch "row1"




	
	/**
	 * [tFileInputDelimited_1 process_data_end ] start
	 */

	

	
	
	currentComponent="tFileInputDelimited_1";

	

 



/**
 * [tFileInputDelimited_1 process_data_end ] stop
 */
	
	/**
	 * [tFileInputDelimited_1 end ] start
	 */

	

	
	
	currentComponent="tFileInputDelimited_1";

	


				nb_line_tFileInputDelimited_1++;
			}
			
			}finally{
    			if(!(filename_tFileInputDelimited_1 instanceof java.io.InputStream)){
    				if(csvReadertFileInputDelimited_1!=null){
    					csvReadertFileInputDelimited_1.close();
    				}
    			}
    			if(csvReadertFileInputDelimited_1!=null){
    				globalMap.put("tFileInputDelimited_1_NB_LINE",nb_line_tFileInputDelimited_1);
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

	

	
	
	currentComponent="tFilterColumns_1";

	

globalMap.put("tFilterColumns_1_NB_LINE",nb_line_tFilterColumns_1);
				if(execStat){
			  		runStat.updateStat(resourceMap,iterateId,2,0,"row1");
			  	}
			  	
 

ok_Hash.put("tFilterColumns_1", true);
end_Hash.put("tFilterColumns_1", System.currentTimeMillis());




/**
 * [tFilterColumns_1 end ] stop
 */

	
	/**
	 * [tFileOutputDelimited_1 end ] start
	 */

	

	
	
	currentComponent="tFileOutputDelimited_1";

	



		
			
					if(outtFileOutputDelimited_1!=null) {
						outtFileOutputDelimited_1.flush();
						outtFileOutputDelimited_1.close();
					}
				
				globalMap.put("tFileOutputDelimited_1_NB_LINE",nb_line_tFileOutputDelimited_1);
				globalMap.put("tFileOutputDelimited_1_FILE_NAME",fileName_tFileOutputDelimited_1);
			
		
		
		resourceMap.put("finish_tFileOutputDelimited_1", true);
	

				if(execStat){
			  		runStat.updateStat(resourceMap,iterateId,2,0,"row2");
			  	}
			  	
 

ok_Hash.put("tFileOutputDelimited_1", true);
end_Hash.put("tFileOutputDelimited_1", System.currentTimeMillis());




/**
 * [tFileOutputDelimited_1 end ] stop
 */






				}//end the resume

				



	
			}catch(java.lang.Exception e){	
				
				TalendException te = new TalendException(e, currentComponent, globalMap);
				
				throw te;
			}catch(java.lang.Error error){	
				
					runStat.stopThreadStat();
				
				throw error;
			}finally{
				
				try{
					
	
	/**
	 * [tFileInputDelimited_1 finally ] start
	 */

	

	
	
	currentComponent="tFileInputDelimited_1";

	

 



/**
 * [tFileInputDelimited_1 finally ] stop
 */

	
	/**
	 * [tFilterColumns_1 finally ] start
	 */

	

	
	
	currentComponent="tFilterColumns_1";

	

 



/**
 * [tFilterColumns_1 finally ] stop
 */

	
	/**
	 * [tFileOutputDelimited_1 finally ] start
	 */

	

	
	
	currentComponent="tFileOutputDelimited_1";

	


		if(resourceMap.get("finish_tFileOutputDelimited_1") == null){ 
			
				
						java.io.Writer outtFileOutputDelimited_1 = (java.io.Writer)resourceMap.get("out_tFileOutputDelimited_1");
						if(outtFileOutputDelimited_1!=null) {
							outtFileOutputDelimited_1.flush();
							outtFileOutputDelimited_1.close();
						}
					
				
			
		}
	

 



/**
 * [tFileOutputDelimited_1 finally ] stop
 */






				}catch(java.lang.Exception e){	
					//ignore
				}catch(java.lang.Error error){
					//ignore
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
    public String contextStr = "Default";
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
            java.util.Map<String,String> threadRunResultMap = new java.util.HashMap<String, String>();
            threadRunResultMap.put("errorCode", null);
            threadRunResultMap.put("status", "");
            return threadRunResultMap;
        };
    };


    protected PropertiesWithType context_param = new PropertiesWithType();
    public java.util.Map<String, Object> parentContextMap = new java.util.HashMap<String, Object>();

    public String status= "";
    

    public static void main(String[] args){
        final colonnesSelecteur colonnesSelecteurClass = new colonnesSelecteur();

        int exitCode = colonnesSelecteurClass.runJobInTOS(args);

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

    	
    	

        if(clientHost == null) {
            clientHost = defaultClientHost;
        }

        if(pid == null || "0".equals(pid)) {
            pid = TalendString.getAsciiRandomString(6);
        }

        if (rootPid==null) {
            rootPid = pid;
        }
        if (fatherPid==null) {
            fatherPid = pid;
        }else{
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
                contextStr = (String)jobProperties.get("context");
            }
        }

        try {
            //call job/subjob with an existing context, like: --context=production. if without this parameter, there will use the default context instead.
            java.io.InputStream inContext = colonnesSelecteur.class.getClassLoader().getResourceAsStream("projetsid/colonnesselecteur_0_1/contexts/" + contextStr + ".properties");
            if (inContext == null) {
                inContext = colonnesSelecteur.class.getClassLoader().getResourceAsStream("config/contexts/" + contextStr + ".properties");
            }
            if (inContext != null) {
                try {
                    //defaultProps is in order to keep the original context value
                    if(context != null && context.isEmpty()) {
	                defaultProps.load(inContext);
	                context = new ContextProperties(defaultProps);
                    }
                } finally {
                    inContext.close();
                }
            } else if (!isDefaultContext) {
                //print info and job continue to run, for case: context_param is not empty.
                System.err.println("Could not find the context " + contextStr);
            }

            if(!context_param.isEmpty()) {
                context.putAll(context_param);
				//set types for params from parentJobs
				for (Object key: context_param.keySet()){
					String context_key = key.toString();
					String context_type = context_param.getContextType(context_key);
					context.setContextType(context_key, context_type);

				}
            }
            class ContextProcessing {
                private void processContext_0() {
                        context.setContextType("folderName", "id_String");
                        if(context.getStringValue("folderName") == null) {
                            context.folderName = null;
                        } else {
                            context.folderName=(String) context.getProperty("folderName");
                        }
                } 
                public void processAllContext() {
                        processContext_0();
                }
            }

            new ContextProcessing().processAllContext();
        } catch (java.io.IOException ie) {
            System.err.println("Could not load context "+contextStr);
            ie.printStackTrace();
        }

        // get context value from parent directly
        if (parentContextMap != null && !parentContextMap.isEmpty()) {if (parentContextMap.containsKey("folderName")) {
                context.folderName = (String) parentContextMap.get("folderName");
            }
        }

        //Resume: init the resumeUtil
        resumeEntryMethodName = ResumeUtil.getResumeEntryMethodName(resuming_checkpoint_path);
        resumeUtil = new ResumeUtil(resuming_logs_dir_path, isChildJob, rootPid);
        resumeUtil.initCommonInfo(pid, rootPid, fatherPid, projectName, jobName, contextStr, jobVersion);

		List<String> parametersToEncrypt = new java.util.ArrayList<String>();
        //Resume: jobStart
        resumeUtil.addLog("JOB_STARTED", "JOB:" + jobName, parent_part_launcher, Thread.currentThread().getId() + "", "","","","",resumeUtil.convertToJsonText(context,parametersToEncrypt));

if(execStat) {
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


this.globalResumeTicket = true;//to run tPreJob





this.globalResumeTicket = false;//to run others jobs

try {
errorCode = null;tFileInputDelimited_1Process(globalMap);
if(!"failure".equals(status)) { status = "end"; }
}catch (TalendException e_tFileInputDelimited_1) {
globalMap.put("tFileInputDelimited_1_SUBPROCESS_STATE", -1);

e_tFileInputDelimited_1.printStackTrace();

}

this.globalResumeTicket = true;//to run tPostJob




        end = System.currentTimeMillis();

        if (watch) {
            System.out.println((end-startTime)+" milliseconds");
        }

        endUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        if (false) {
            System.out.println((endUsedMemory - startUsedMemory) + " bytes memory increase when running : colonnesSelecteur");
        }



if (execStat) {
    runStat.updateStatOnJob(RunStat.JOBEND, fatherNode);
    runStat.stopThreadStat();
}
    int returnCode = 0;


    if(errorCode == null) {
         returnCode = status != null && status.equals("failure") ? 1 : 0;
    } else {
         returnCode = errorCode.intValue();
    }
    resumeUtil.addLog("JOB_ENDED", "JOB:" + jobName, parent_part_launcher, Thread.currentThread().getId() + "", "","" + returnCode,"","","");

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
                if (fatherPid==null) {
                    context_param.setContextType(keyValue.substring(0, index), replaceEscapeChars(keyValue.substring(index + 1)));
                } else { // the subjob won't escape the especial chars
                    context_param.setContextType(keyValue.substring(0, index), keyValue.substring(index + 1) );
                }

            }

		} else if (arg.startsWith("--context_param")) {
            String keyValue = arg.substring(16);
            int index = -1;
            if (keyValue != null && (index = keyValue.indexOf('=')) > -1) {
                if (fatherPid==null) {
                    context_param.put(keyValue.substring(0, index), replaceEscapeChars(keyValue.substring(index + 1)));
                } else { // the subjob won't escape the especial chars
                    context_param.put(keyValue.substring(0, index), keyValue.substring(index + 1) );
                }
            }
        } else if (arg.startsWith("--log4jLevel=")) {
            log4jLevel = arg.substring(13);
		} else if (arg.startsWith("--audit.enabled") && arg.contains("=")) {//for trunjob call
		    final int equal = arg.indexOf('=');
			final String key = arg.substring("--".length(), equal);
			System.setProperty(key, arg.substring(equal + 1));
		}
    }
    
    private static final String NULL_VALUE_EXPRESSION_IN_COMMAND_STRING_FOR_CHILD_JOB_ONLY = "<TALEND_NULL>";

    private final String[][] escapeChars = {
        {"\\\\","\\"},{"\\n","\n"},{"\\'","\'"},{"\\r","\r"},
        {"\\f","\f"},{"\\b","\b"},{"\\t","\t"}
        };
    private String replaceEscapeChars (String keyValue) {

		if (keyValue == null || ("").equals(keyValue.trim())) {
			return keyValue;
		}

		StringBuilder result = new StringBuilder();
		int currIndex = 0;
		while (currIndex < keyValue.length()) {
			int index = -1;
			// judege if the left string includes escape chars
			for (String[] strArray : escapeChars) {
				index = keyValue.indexOf(strArray[0],currIndex);
				if (index>=0) {

					result.append(keyValue.substring(currIndex, index + strArray[0].length()).replace(strArray[0], strArray[1]));
					currIndex = index + strArray[0].length();
					break;
				}
			}
			// if the left string doesn't include escape chars, append the left into the result
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
 *     124443 characters generated by Talend Open Studio for Data Integration 
 *     on the 9 mai 2024 à 18:44:29 CEST
 ************************************************************************************************/