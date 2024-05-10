$fileDir = Split-Path -Parent $MyInvocation.MyCommand.Path
cd $fileDir
java '-Dtalend.component.manager.m2.repository=%cd%/../lib' '-Xms256M' '-Xmx1024M' '-Dfile.encoding=UTF-8' -cp '.;../lib/routines.jar;../lib/log4j-slf4j-impl-2.13.2.jar;../lib/log4j-api-2.13.2.jar;../lib/log4j-core-2.13.2.jar;../lib/log4j-1.2-api-2.13.2.jar;../lib/commons-collections-3.2.2.jar;../lib/jboss-marshalling-river-2.0.12.Final.jar;../lib/jboss-marshalling-2.0.12.Final.jar;../lib/talend_file_enhanced-1.1.jar;../lib/advancedPersistentLookupLib-1.3.jar;../lib/dom4j-2.1.3.jar;../lib/slf4j-api-1.7.29.jar;../lib/trove.jar;../lib/postgresql-42.2.14.jar;../lib/talendcsv-1.0.0.jar;../lib/crypto-utils-0.31.12.jar;maestro_0_1.jar;dumpemployee_0_1.jar;dumpcustomertype_0_2.jar;customer_type_0_1.jar;dumpcountry_0_1.jar;employee_0_1.jar;colonnesselecteur_0_1.jar;dumpdate_0_1.jar;order_0_1.jar;dumpcustomergroup_0_1.jar;dumpcustomer_0_2.jar;customer_0_1.jar;dumpquarter_0_1.jar;dumporder_0_2.jar;time_0_1.jar;customer_group_0_1.jar;country_0_1.jar;orderline_0_1.jar;dumpproduct_0_1.jar;product_0_1.jar;dumpmonth_0_1.jar;dumporderline_0_2.jar;' projetsid.maestro_0_1.maestro $args
