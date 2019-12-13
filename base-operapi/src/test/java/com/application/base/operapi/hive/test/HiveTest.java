package com.application.base.operapi.hive.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class HiveTest {

    public static void main(String[] args) throws Exception {
        Class.forName("org.apache.hive.jdbc.HiveDriver");
        Connection con = DriverManager.getConnection("jdbc:hive2://192.168.10.185:10000/default", "", "");
        Statement st = con.createStatement();

        st.execute("load data inpath 'hdfs:/tmp/data.txt' into table test_partition ");

        st.close();
        con.close();
    }

}
