package com.example.brmm;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserAuth;
import com.jcraft.jsch.UserInfo;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Struct;
import java.util.ArrayList;

public class DatabaseConnection extends Thread {

    private int portNum;
    private final String SSH_Host = "10.110.21.210";
    private final String SSH_User = "vmuser";
    private final String SSH_Pass = "brmmproject4!";
    private final String MySQL_Host = "127.0.0.1";
    private final String MySQL_User = "vmuser";
    private final String MySQL_Pass = "brmmproject4!";
    private Session session = null;
    private Connection conn = null;

    //gets connection
    public void run() {
        conn = dbGetConnection();

    }

    //Adusts ports and attempts to get a connection to the vm
    private Session getServerConnection() {
        //Session serverSession = null;
        Session serverSession = null;
        try {
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            JSch jsch = new JSch();
            serverSession = jsch.getSession(SSH_User, SSH_Host, 22);
            serverSession.setPassword(MySQL_Pass);
            serverSession.setConfig(config);
            System.out.println("Starting SSH Connection");
            System.out.println(serverSession.getHost() + "\n" + serverSession.getUserName() + "\n" + serverSession.getPort());
            System.out.println("Connection Established");
            serverSession.connect();
            System.out.println("Connected = " + serverSession.isConnected());

            //Port Forwarding stuff
            int assigned_port = serverSession.setPortForwardingL(5656, MySQL_Host, 3306);
            System.out.println("localhost:" + assigned_port + "->" + MySQL_Host + ":" + 3306);
            System.out.println("Port Forwarded");
        } catch (Exception e) {
            e.printStackTrace();
        }
        session = serverSession;
        return serverSession;
    }

    //Gets a connection to the database
    public Connection dbGetConnection() {
        Session ses = null;
        try {
            ses = getServerConnection();
        } catch (Exception v) {
            System.out.println("Part 2 fail...................");
        }

        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:5656/BRMM";
            conn = DriverManager.getConnection(url, "vmuser", "brmmproject4!");
            System.out.println("Connection Complete");

        } catch (Exception t) {
            System.out.println("Part 3 fail...............");
            t.printStackTrace();
        }
        return conn;
    }

    //Returns the connection so other classes can use it
    public Connection getConnection(){
        return conn;
    }

    //Returns the SSH Host string
    //public String getSSH_Host() {
     //   return SSH_Host;
    //}

    //public String getMySQL_Host() {
      //  return MySQL_Host;
    //}

    //public String getMySQL_User() {
     //   return MySQL_User;
    //}

    //public int getPortNum() {
     //   return portNum;
    //}

    //public void setPortNum(int portNum) {
     //   this.portNum = portNum;
    //}
}

