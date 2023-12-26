package com.TravelApp;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.net.URISyntaxException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;


public class SQLExecutor {
	static Connection connection;
	private static void printMetaData(Connection connection)
            throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        String format = "\nDatabase metadata\n"
                + "Database name : %s\n"
                + "Database version : %s\n"
                + "Database driver name : %s\n"
                + "Database driver version : %s\n\n";
        System.out.printf(format,
                metaData.getDatabaseProductName(),
                metaData.getDatabaseProductVersion(),
                metaData.getDriverName(),
                metaData.getDriverVersion());
    }

	public static void executeFunctionFile(String path) throws URISyntaxException {
    	File resource = new File(path);
        try (FileReader reader = new FileReader(resource);
                BufferedReader bufferedReader = new BufferedReader(reader);
                Statement statement = connection.createStatement();) {

            printMetaData(connection);
            System.out.println("Executing commands at : "
                    + path);

            StringBuilder builder = new StringBuilder();

            String line;
            int lineNumber = 0;
            int count = 0;
            while ((line = bufferedReader.readLine()) != null) {
                lineNumber += 1;
                line = line.trim();
                if (line.isEmpty() || line.startsWith("--"))
                    continue;

                builder.append(line);//                
            }
            try {
            	System.out.println(builder.toString());
                statement.execute(builder.toString());
                System.out.println(
                        ++count
                                + " Command successfully executed : "
                                + builder.substring(
                                        0,
                                        Math.min(builder.length(), 3000))
                                + "...");
                builder.setLength(0);
            } catch (SQLException e) {
                System.err.println(
                        "At line " + lineNumber + " : "
                                + e.getMessage() + "\n");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void executeFile(String path) throws URISyntaxException {
    	File resource = new File(path);
        try (FileReader reader = new FileReader(resource);
                // Wrap the FileReader in a BufferedReader for
                // efficient reading.
                BufferedReader bufferedReader = new BufferedReader(reader);
                // Establish a connection to the database.
                // Create a statement object to execute SQL
                // commands.
                Statement statement = connection.createStatement();) {

            printMetaData(connection);
            System.out.println("Executing commands at : "
                    + path);

            StringBuilder builder = new StringBuilder();

            String line;
            int lineNumber = 0;
            int count = 0;

            // Read lines from the SQL file until the end of the
            // file is reached.
            while ((line = bufferedReader.readLine()) != null) {
                lineNumber += 1;
                line = line.trim();

                // Skip empty lines and single-line comments.
                if (line.isEmpty() || line.startsWith("--"))
                    continue;

                builder.append(line);
//                
                if (line.endsWith(";"))
                    try {
                    	System.out.println(builder.toString());
                        statement.execute(builder.toString());
                        System.out.println(
                                ++count
                                        + " Command successfully executed : "
                                        + builder.substring(
                                                0,
                                                Math.min(builder.length(), 3000))
                                        + "...");
                        builder.setLength(0);
                    } catch (SQLException e) {
                        System.err.println(
                                "At line " + lineNumber + " : "
                                        + e.getMessage() + "\n");
                        return;
                    }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws ParseException {
        try {
        	connection= DriverManager.getConnection("jdbc:postgresql://localhost:5433/", "postgres", "Simran");
            System.out.println("Connected With the database successfully");
            printMetaData(connection);
            executeFile("C:\\Users\\simra\\eclipse-workspace\\TravelApp\\src\\com\\TravelApp\\creations.txt");
            executeFile("C:\\Users\\simra\\eclipse-workspace\\TravelApp\\src\\com\\TravelApp\\insertions.txt");
            executeFunctionFile("C:\\Users\\simra\\eclipse-workspace\\TravelApp\\src\\com\\TravelApp\\fxn.txt");
            executeFunctionFile("C:\\Users\\simra\\eclipse-workspace\\TravelApp\\src\\com\\TravelApp\\fxn2.txt");
            executeFunctionFile("C:\\Users\\simra\\eclipse-workspace\\TravelApp\\src\\com\\TravelApp\\fxn3.txt");
            executeFunctionFile("C:\\Users\\simra\\eclipse-workspace\\TravelApp\\src\\com\\TravelApp\\fxn4.txt");
            executeFunctionFile("C:\\Users\\simra\\eclipse-workspace\\TravelApp\\src\\com\\TravelApp\\fxn5.txt");
            executeFunctionFile("C:\\Users\\simra\\eclipse-workspace\\TravelApp\\src\\com\\TravelApp\\fxn7.txt");
            executeFunctionFile("C:\\Users\\simra\\eclipse-workspace\\TravelApp\\src\\com\\TravelApp\\fxn8.txt");
            executeFunctionFile("C:\\Users\\simra\\eclipse-workspace\\TravelApp\\src\\com\\TravelApp\\fxn9.txt");
            executeFunctionFile("C:\\Users\\simra\\eclipse-workspace\\TravelApp\\src\\com\\TravelApp\\fxntrig1");
            executeFunctionFile("C:\\Users\\simra\\eclipse-workspace\\TravelApp\\src\\com\\TravelApp\\fxntrig2.txt");
            executeFunctionFile("C:\\Users\\simra\\eclipse-workspace\\TravelApp\\src\\com\\TravelApp\\fxntrig3.txt");
            executeFunctionFile("C:\\Users\\simra\\eclipse-workspace\\TravelApp\\src\\com\\TravelApp\\trig1.txt");
            executeFunctionFile("C:\\Users\\simra\\eclipse-workspace\\TravelApp\\src\\com\\TravelApp\\fxntrig4.txt");
            executeFunctionFile("C:\\Users\\simra\\eclipse-workspace\\TravelApp\\src\\com\\TravelApp\\trig2.txt");
            executeFunctionFile("C:\\Users\\simra\\eclipse-workspace\\TravelApp\\src\\com\\TravelApp\\fxntrig5.txt");
            executeFunctionFile("C:\\Users\\simra\\eclipse-workspace\\TravelApp\\src\\com\\TravelApp\\trig3.txt");
            executeFunctionFile("C:\\Users\\simra\\eclipse-workspace\\TravelApp\\src\\com\\TravelApp\\trig4.txt");
            //executeFile("C:\\Users\\simra\\eclipse-workspace\\TravelApp\\src\\com\\TravelApp\\queries.txt");
            Scanner sc= new Scanner(System.in);
            String userName="";
            String password="";
            int authenticated= -1;  
            int checkUser=0;
            while(authenticated==-1) {
            	System.out.print("Enter username: ");            
                userName= sc.nextLine();
                System.out.print("Enter password: ");            
                password= sc.nextLine();
                
                CallableStatement cstmt = connection.prepareCall("{? = call authenticate(?, ?)}");

                cstmt.registerOutParameter(1, Types.INTEGER);
                cstmt.setString(2, userName);
                cstmt.setString(3, password);
                cstmt.execute();
                
                authenticated= cstmt.getInt(1);
                System.out.println(authenticated);
                if(authenticated==-1) {
                	System.out.println("SORRY!! WRONG USERNAME OR PASSWORD!\nTRY AGAIN");
                }
            }
            checkUser= authenticated;
            System.out.println("****************   WELCOME "+ userName+ "   ********************");
            //superadmin
            if(checkUser==3) {
            	//add hotel
            	//add plane
            	//add hotel admin
            	//add add plane admin
            	int operation=-5;
            	while(operation!=-1) {
                	System.out.print("PRESS 1 TO ADD HOTEL\nPRESS 2 TO ADD HOTEL ADMIN\nPRESS 3 TO ADD PLANE\nPRESS 4 TO ADD PLANE ADMIN\nPRESS 5 TO DELETE HOTEL ADMIN\nPRESS 6 TO DELETE PLANE ADMIN\nPRESS 7 TO UPDATE DATE\nPRESS -1 TO QUIT");  
                	operation= sc.nextInt();
                	if(operation==1) {
                		int phoneNo;
                		String hotelName;
                		int cityID;
                		int roomsLeft;
                		int roomCost;
                		int rating=3;
                		System.out.println("Enter hostel name: ");
                		hotelName=sc.nextLine();
                		hotelName=sc.nextLine();
                		System.out.println("Enter city ID(Choose from the list below): ");
                		Statement stmt = connection.createStatement();
                        String query = "select * from cities";
                        ResultSet rs = stmt.executeQuery(query);
                        while (rs.next()) {
                        	System.out.println(rs.getString(1) + " "+ rs.getString(2)); //Print one element of a row
                        }
                        cityID=sc.nextInt();
                		System.out.println("Enter number of rooms: ");
                		roomsLeft=sc.nextInt();
                		System.out.println("Enter cost of a room: ");
                		roomCost=sc.nextInt();
                		//System.out.println("Enter contact number of hotel: ");
                		phoneNo=(Integer) 0;
                		
                        Statement stmt1 = connection.createStatement();
                        String query1 = "select count(*) from hotel";
                        ResultSet rs1 = stmt1.executeQuery(query1);
                        rs1.next();
                        int hotelID = rs1.getInt(1)+1;
                        CallableStatement cstmt = connection.prepareCall("INSERT INTO hotel VALUES (?,?,?,?,?,?,?);");
                        cstmt.setInt(1, hotelID);
                        cstmt.setInt(2, phoneNo);
                        cstmt.setString(3, hotelName);
                        cstmt.setInt(4, cityID);
                        cstmt.setInt(5, roomsLeft);
                        cstmt.setInt(6, roomCost);
                        cstmt.setInt(7, rating);
                        cstmt.execute(); 
//                        Statement stmt2 = connection.createStatement();
//                        String query2 = "select * from hotel";
//                        ResultSet rs2 = stmt2.executeQuery(query2);
//                        while (rs2.next()) {
//                        	System.out.println(rs2.getInt(1) + " "+ rs2.getString(3)); //Print one element of a row
//                        }
                        
                        System.out.println("Please also, add a hotel admin\n");
                        
                    	String perName;
                    	System.out.print("Enter name of person: ");
                        perName= sc.nextLine();
                        perName= sc.nextLine();
                        int phNo;
                        System.out.print("Enter phone number of person: ");
                        phNo= sc.nextInt();
                        int perAge;
                        System.out.print("Enter age of person: ");
                        perAge= sc.nextInt();
                        String perSex="L";
                        int check;
                        System.out.print("Enter 1 for Male and 2 for Female: ");
                        check= sc.nextInt();
                        if(check==1) {
                        	perSex="M";
                        }
                        else if(check==2) {
                        	perSex="F";
                        }
                        else {
                        	while(check!= 1 && check!=2) {
                        		System.out.print("Please give correct gender. ");
                        		System.out.print("Enter 1 for Male and 2 for Female: ");
                                check= sc.nextInt();
                                if(check==1) {
                                	perSex="M";
                                }
                                else if(check==2) {
                                	perSex="F";
                                }
                        	}
                        }
                        int perCode;
                        System.out.println("Enter pincode of the location person lives in: ");
                        perCode= sc.nextInt();
                        System.out.print("Enter the date of birth (yyyy-MM-dd): ");
                        String userInput = sc.nextLine();
                        userInput = sc.nextLine();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date d1 = dateFormat.parse(userInput);
                        
                        String username;
                        String pass;
                        System.out.println("Add username: ");
                        username= sc.nextLine();
                        username= sc.nextLine();
                        System.out.println("Add password: ");
                        pass= sc.nextLine();
                        pass= sc.nextLine();
                        Statement stmt2 = connection.createStatement();
                        String query2 = "select count(*) from person";
                        ResultSet rs2 = stmt2.executeQuery(query2);
                        rs2.next();
                        int perID = rs2.getInt(1)+1;
                        
                    	CallableStatement cstmt2 = connection.prepareCall("INSERT INTO person VALUES (?,?,?,?,?,?,?);");
                        cstmt2.setString(1, perName);
                        cstmt2.setInt(2, phNo);
                        cstmt2.setInt(3, perAge);
                        cstmt2.setString(4, perSex);
                        cstmt2.setInt(5, perCode);
                        cstmt2.setDate(6, new java.sql.Date(d1.getTime()));
                        cstmt2.setInt(7, perID);
                        cstmt2.execute();
                        
                        CallableStatement cstmt3 = connection.prepareCall("INSERT INTO hotelAdmin VALUES (?,?,?,?);");
                        cstmt3.setInt(1, perID);
                        cstmt3.setInt(2, hotelID);
                        cstmt3.setString(3, username);
                        cstmt3.setString(4, pass);
                        cstmt3.execute();
                	}
                	else if(operation==2) {
                		System.out.println("Please add a hotel admin\n");
                		System.out.println("Please enter the hotel ID for the hotel you want to add admin for(List of existing hotels for reference): ");
                		Statement stmt = connection.createStatement();
                		String query = "select * from hotel";
                		ResultSet rs = stmt.executeQuery(query);
                		while (rs.next()) {
                			System.out.println(rs.getInt(1) + " "+ rs.getString(3)); //Print one element of a row
                		}
                		int hotelID= sc.nextInt();
                    	String perName;
                    	System.out.print("Enter name of person: ");
                        perName= sc.nextLine();
                        perName= sc.nextLine();
                        int phNo;
                        System.out.print("Enter phone number of person: ");
                        phNo= sc.nextInt();
                        int perAge;
                        System.out.print("Enter age of person: ");
                        perAge= sc.nextInt();
                        String perSex="L";
                        int check;
                        System.out.print("Enter 1 for Male and 2 for Female: ");
                        check= sc.nextInt();
                        if(check==1) {
                        	perSex="M";
                        }
                        else if(check==2) {
                        	perSex="F";
                        }
                        else {
                        	while(check!= 1 && check!=2) {
                        		System.out.print("Please give correct gender. ");
                        		System.out.print("Enter 1 for Male and 2 for Female: ");
                                check= sc.nextInt();
                                if(check==1) {
                                	perSex="M";
                                }
                                else if(check==2) {
                                	perSex="F";
                                }
                        	}
                        }
                        int perCode;
                        System.out.println("Enter pincode of the location person lives in: ");
                        perCode= sc.nextInt();
                        int date, month, year;
//                        System.out.println("Enter year of birth of the person(only YYYY): ");
//                        year= sc.nextInt();
//                        System.out.println("Enter month of birth of the person(only MM): ");
//                        month= sc.nextInt();
//                        System.out.println("Enter date of birth of the person(only DD): ");
//                        date= sc.nextInt();
//                        System.out.println(date+" "+ month+" "+ year);
//                        //@SuppressWarnings("deprecation")
//						Date d1= new Date(year, month, date);
                        System.out.print("Enter the date of birth (yyyy-MM-dd): ");
                        String userInput = sc.nextLine();
                        userInput = sc.nextLine();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date d1 = dateFormat.parse(userInput);
                        Statement stmt2 = connection.createStatement();
                        String query2 = "select count(*) from person";
                        ResultSet rs2 = stmt2.executeQuery(query2);
                        rs2.next();
                        int perID = rs2.getInt(1)+1;
                        
                    	CallableStatement cstmt2 = connection.prepareCall("INSERT INTO person VALUES (?,?,?,?,?,?,?);");
                        cstmt2.setString(1, perName);
                        cstmt2.setInt(2, phNo);
                        cstmt2.setInt(3, perAge);
                        cstmt2.setString(4, perSex);
                        cstmt2.setInt(5, perCode);
                        cstmt2.setDate(6, new java.sql.Date(d1.getTime()));
                        cstmt2.setInt(7, perID);
                        cstmt2.execute();
                        String username;
                        String pass;
                        System.out.println("Add username: ");
                        username= sc.nextLine();
                        System.out.println("Add password: ");
                        pass= sc.nextLine();
                        CallableStatement cstmt3 = connection.prepareCall("INSERT INTO hotelAdmin VALUES (?,?,?,?);");
                        cstmt3.setInt(1, perID);
                        cstmt3.setInt(2, hotelID);
                        cstmt3.setString(3, username);
                        cstmt3.setString(4, pass);
                        cstmt3.execute();
                	}
                	else if(operation==3) {
                		int numPlanesOwned;
                		String compName;
                		int rating=3;
                		System.out.println("Enter plane company name: ");
                		compName=sc.nextLine();
                		compName=sc.nextLine();
//                		System.out.println("Enter city ID(Choose from the list below): ");
//                		Statement stmt = connection.createStatement();
//                        String query = "select * from cities";
//                        ResultSet rs = stmt.executeQuery(query);
//                        while (rs.next()) {
//                        	System.out.println(rs.getString(1) + " "+ rs.getString(2)); //Print one element of a row
//                        }
//                        cityID=sc.nextInt();
                		System.out.println("Enter number of planes owned: ");
                		numPlanesOwned=sc.nextInt();
                        Statement stmt1 = connection.createStatement();
                        String query1 = "select count(*) from planeCompany";
                        ResultSet rs1 = stmt1.executeQuery(query1);
                        rs1.next();
                        int compID = rs1.getInt(1)+1;
                        CallableStatement cstmt = connection.prepareCall("INSERT INTO planeCompany VALUES (?,?,?,?);");
                        cstmt.setInt(1, numPlanesOwned);
                        cstmt.setInt(2, compID);
                        cstmt.setString(3, compName);
                        cstmt.setInt(4, rating);
                        cstmt.execute(); 
//                        Statement stmt2 = connection.createStatement();
//                        String query2 = "select * from hotel";
//                        ResultSet rs2 = stmt2.executeQuery(query2);
//                        while (rs2.next()) {
//                        	System.out.println(rs2.getInt(1) + " "+ rs2.getString(3)); //Print one element of a row
//                        }
                        
                        System.out.println("Please also, add a company admin\n");
                        
                    	String perName;
                    	System.out.print("Enter name of person: ");
                        perName= sc.nextLine();
                        perName= sc.nextLine();
                        int phNo;
                        System.out.print("Enter phone number of person: ");
                        phNo= sc.nextInt();
                        int perAge;
                        System.out.print("Enter age of person: ");
                        perAge= sc.nextInt();
                        String perSex="L";
                        int check;
                        System.out.print("Enter 1 for Male and 2 for Female: ");
                        check= sc.nextInt();
                        if(check==1) {
                        	perSex="M";
                        }
                        else if(check==2) {
                        	perSex="F";
                        }
                        else {
                        	while(check!= 1 && check!=2) {
                        		System.out.print("Please give correct gender. ");
                        		System.out.print("Enter 1 for Male and 2 for Female: ");
                                check= sc.nextInt();
                                if(check==1) {
                                	perSex="M";
                                }
                                else if(check==2) {
                                	perSex="F";
                                }
                        	}
                        }
                        int perCode;
                        System.out.println("Enter pincode of the location person lives in: ");
                        perCode= sc.nextInt();
                        System.out.print("Enter the date of birth (yyyy-MM-dd): ");
                        String userInput = sc.nextLine();
                        userInput = sc.nextLine();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date d1 = dateFormat.parse(userInput);
                        
                        Statement stmt2 = connection.createStatement();
                        String query2 = "select count(*) from person";
                        ResultSet rs2 = stmt2.executeQuery(query2);
                        rs2.next();
                        int perID = rs2.getInt(1)+1;
                        
                    	CallableStatement cstmt2 = connection.prepareCall("INSERT INTO person VALUES (?,?,?,?,?,?,?);");
                        cstmt2.setString(1, perName);
                        cstmt2.setInt(2, phNo);
                        cstmt2.setInt(3, perAge);
                        cstmt2.setString(4, perSex);
                        cstmt2.setInt(5, perCode);
                        cstmt2.setDate(6, new java.sql.Date(d1.getTime()));
                        cstmt2.setInt(7, perID);
                        cstmt2.execute();
                        String username;
                        String pass;
                        System.out.println("Add username: ");
                        username= sc.nextLine();
                        username= sc.nextLine();
                        System.out.println("Add password: ");
                        pass= sc.nextLine();
                        pass= sc.nextLine();
                        CallableStatement cstmt3 = connection.prepareCall("INSERT INTO planeCompanyAdmin VALUES (?,?,?,?);");
                        cstmt3.setInt(1, perID);
                        cstmt3.setInt(2, compID);
                        cstmt3.setString(3, username);
                        cstmt3.setString(4, pass);
                        cstmt3.execute();
                	}
                	else if(operation==4) {
                		System.out.println("Please add a hotel admin\n");
                		System.out.println("Please enter the Plane Company ID for the hotel you want to add admin for(List of existing hotels for reference): ");
                		Statement stmt = connection.createStatement();
                		String query = "select * from planeCompany";
                		ResultSet rs = stmt.executeQuery(query);
                		while (rs.next()) {
                			System.out.println(rs.getInt(2) + " "+ rs.getString(3)); //Print one element of a row
                		}
                		int compID= sc.nextInt();
                    	String perName;
                    	System.out.print("Enter name of person: ");
                        perName= sc.nextLine();
                        perName= sc.nextLine();
                        int phNo;
                        System.out.print("Enter phone number of person: ");
                        phNo= sc.nextInt();
                        int perAge;
                        System.out.print("Enter age of person: ");
                        perAge= sc.nextInt();
                        String perSex="L";
                        int check;
                        System.out.print("Enter 1 for Male and 2 for Female: ");
                        check= sc.nextInt();
                        if(check==1) {
                        	perSex="M";
                        }
                        else if(check==2) {
                        	perSex="F";
                        }
                        else {
                        	while(check!= 1 && check!=2) {
                        		System.out.print("Please give correct gender. ");
                        		System.out.print("Enter 1 for Male and 2 for Female: ");
                                check= sc.nextInt();
                                if(check==1) {
                                	perSex="M";
                                }
                                else if(check==2) {
                                	perSex="F";
                                }
                        	}
                        }
                        int perCode;
                        System.out.println("Enter pincode of the location person lives in: ");
                        perCode= sc.nextInt();
                        System.out.print("Enter the date of birth (yyyy-MM-dd): ");
                        String userInput = sc.nextLine();
                        userInput = sc.nextLine();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date d1 = dateFormat.parse(userInput);
                        
                        Statement stmt2 = connection.createStatement();
                        String query2 = "select count(*) from person";
                        ResultSet rs2 = stmt2.executeQuery(query2);
                        rs2.next();
                        int perID = rs2.getInt(1)+1;
                        
                    	CallableStatement cstmt2 = connection.prepareCall("INSERT INTO person VALUES (?,?,?,?,?,?,?);");
                        cstmt2.setString(1, perName);
                        cstmt2.setInt(2, phNo);
                        cstmt2.setInt(3, perAge);
                        cstmt2.setString(4, perSex);
                        cstmt2.setInt(5, perCode);
                        cstmt2.setDate(6, new java.sql.Date(d1.getTime()));
                        cstmt2.setInt(7, perID);
                        cstmt2.execute();
                        
                        String username;
                        String pass;
                        System.out.println("Add username: ");
                        username= sc.nextLine();
                        username= sc.nextLine();
                        System.out.println("Add password: ");
                        pass= sc.nextLine();
                        pass= sc.nextLine();
                        CallableStatement cstmt3 = connection.prepareCall("INSERT INTO planeCompanyAdmin VALUES (?,?,?,?);");
                        cstmt3.setInt(1, perID);
                        cstmt3.setInt(2, compID);
                        cstmt3.setString(3, username);
                        cstmt3.setString(4, pass);
                        cstmt3.execute();
                	}
                	else if(operation==5) {
                		System.out.println("Here is the list of all admins: ");
                		String query = "select * from hotelAdmin";
                		Statement stmt = connection.createStatement();
                		ResultSet rs = stmt.executeQuery(query);
                		while (rs.next()) {
                			CallableStatement cstmt = connection.prepareCall("{call get_values(?,?)}");
                    		cstmt.setInt(1, rs.getInt(1));
                    		cstmt.setInt(2, rs.getInt(2));
                    		boolean hasResults = cstmt.execute(); // Execute query
                    		if (hasResults) {
                    		    try (ResultSet rs2 = cstmt.getResultSet()) {        		    	
                    		        while (rs2.next()) {
                    		            System.out.println("Person ID: "+rs.getInt(1) + "\nPerson Name: " + rs2.getString("value1") + "\nHotel ID: "+rs.getInt(2) + "\nHotel Name: " + rs2.getString("value2"));
                    		            System.out.println();
                    		            System.out.println();
                    		        }
                    		    }
                    		}
                		}
                		System.out.println("Enter person ID you want to delete: ");
                		int personID= sc.nextInt();
                		System.out.println("Enter hotel ID you want to delete the person from: ");
                		int hotelID= sc.nextInt();
                		CallableStatement cstmt3 = connection.prepareCall("DELETE FROM hotelAdmin WHERE personID = ? AND hotelID = ?;");
                        cstmt3.setInt(1, personID);
                        cstmt3.setInt(2, hotelID);	
                        cstmt3.execute();
                        System.out.println("Deleted");
                	}
                	else if(operation==6) {
                		System.out.println("Here is the list of all admins: ");
                		String query = "select * from planeCompanyAdmin";
                		Statement stmt = connection.createStatement();
                		ResultSet rs = stmt.executeQuery(query);
                		while (rs.next()) {
                			CallableStatement cstmt = connection.prepareCall("{call get_plane_values(?,?)}");
                    		cstmt.setInt(1, rs.getInt(1));
                    		cstmt.setInt(2, rs.getInt(2));
                    		boolean hasResults = cstmt.execute(); // Execute query
                    		if (hasResults) {
                    		    try (ResultSet rs2 = cstmt.getResultSet()) {        		    	
                    		        while (rs2.next()) {
                    		            System.out.println("Person ID: "+rs.getInt(1) + "\nPerson Name: " + rs2.getString("value1") + "\nCompany ID: "+rs.getInt(2) + "\nCompany Name: " + rs2.getString("value2"));
                    		            System.out.println();
                    		            System.out.println();
                    		        }
                    		    }
                    		}
                		}
                		System.out.println("Enter person ID you want to delete: ");
                		int personID= sc.nextInt();
                		System.out.println("Enter company ID you want to delete the person from: ");
                		int hotelID= sc.nextInt();
                		CallableStatement cstmt3 = connection.prepareCall("DELETE FROM planeCompanyAdmin WHERE personID = ? AND companyID = ?;");
                        cstmt3.setInt(1, personID);
                        cstmt3.setInt(2, hotelID);	
                        cstmt3.execute();
                        System.out.println("Deleted");
                	}
                	else if(operation==7) {
                		java.util.Date currentDate = new java.util.Date(2023-1900,06,22);
                		//2024-1900,02,02
                		// Print the current date and time (optional)
                		System.out.println(currentDate.toString());

                		try {
                			CallableStatement cstmt = connection.prepareCall("{call updatedate(?)}");
                		    cstmt.setDate(1, new java.sql.Date(currentDate.getTime()));
                		    cstmt.execute();
                		    cstmt.close();
                		} catch (SQLException e) {
                		    e.printStackTrace();
                		}
                	}
                	
            	}
            }
            //hoteladmin
            else if(checkUser==2) {
            	int operation=-8;
            	while(operation!=-1) {
            		System.out.println("PRESS 1 TO VIEW ALL BOOKINGS OF HOTEL\nPRESS -1 TO QUIT");
            		operation= sc.nextInt();
            		if(operation==1) {
            			CallableStatement cstmt2 = connection.prepareCall("select * from hoteladmin where username= ? and passkey= ?");
                		cstmt2.setString(1, userName);
                		cstmt2.setString(2, password);
                		
                		int hID=-3;
                		boolean hasResult = cstmt2.execute(); // Execute query
                		if (hasResult) {
                		    try (ResultSet rs2 = cstmt2.getResultSet()) {        		    	
                		    	rs2.next();
                		    	hID= rs2.getInt(2);
                		    }
                		    catch(Exception e){
                		    	System.out.println("Sorry");
                		    }
                		}
            			CallableStatement cstmt = connection.prepareCall("{call getentries(?)}");
                		cstmt.setInt(1, hID);
                		boolean hasResults = cstmt.execute(); // Execute query
                		System.out.println("All present and past bookings in your hotel are:\n");
                		if (hasResults) {
                		    try (ResultSet rs2 = cstmt.getResultSet()) {        		    	
                		        while (rs2.next()) {
                		            System.out.println("Person ID: "+rs2.getInt("personid") + "\nCheckIn Date: " + rs2.getDate("checkin").toString() + "\nCheckOut Date: " + rs2.getDate("checkout").toString());
                		            System.out.println();
                		            System.out.println();
                		        }
                		    }
                		}
            		}
            	}
            	
            }
            //plane admin
            else if(checkUser==4) {
            	int operation=-8;
            	
            	while(operation!=-1) {
            		System.out.println("PRESS 1 TO VIEW ALL FLIGHTS OF YOUR COMPANY\nPRESS 2 TO ADD NEW PLANE\nPRESS 3 TO ADD NEW FLIGHT\nPRESS -1 TO QUIT");
            		operation= sc.nextInt();
            		if(operation==1) {
            			CallableStatement cstmt2 = connection.prepareCall("select * from planecompanyadmin where username= ? and passkey= ?");
                		cstmt2.setString(1, userName);
                		cstmt2.setString(2, password);
                		
                		int hID=-3;
                		boolean hasResult = cstmt2.execute(); // Execute query
                		if (hasResult) {
                		    try (ResultSet rs2 = cstmt2.getResultSet()) {        		    	
                		    	rs2.next();
                		    	hID= rs2.getInt(2);
                		    }
                		    catch(Exception e){
                		    	System.out.println("Sorry");
                		    }
                		}
                		System.out.println(hID);
            			CallableStatement cstmt = connection.prepareCall("{call getflights(?)}");
                		cstmt.setInt(1, hID);
                		boolean hasResults = cstmt.execute(); // Execute query
                		System.out.println("All flights of your company are:\n");
                		if (hasResults) {
                		    try (ResultSet rs2 = cstmt.getResultSet()) {        		    	
                		        while (rs2.next()) {
                		            System.out.println("Flight Number: "+rs2.getString("flightNumber") + "\nPlaneID: " + rs2.getInt("planeID") + "\nCompany ID: " + rs2.getInt("companyID")+ "\nSource: " + rs2.getString("sources")+ "\nDestination: " + rs2.getString("destination")+ "\nTravel Date: " + rs2.getDate("travelDate").toString());
                		            System.out.println();
                		            System.out.println();
                		        }
                		    }
                		}
            		}
            		if(operation==2) {
            			CallableStatement cstmt2 = connection.prepareCall("select * from planecompanyadmin where username= ? and passkey= ?");
                		cstmt2.setString(1, userName);
                		cstmt2.setString(2, password);
                		
                		int hID=-3;
                		boolean hasResult = cstmt2.execute(); // Execute query
                		if (hasResult) {
                		    try (ResultSet rs2 = cstmt2.getResultSet()) {        		    	
                		    	rs2.next();
                		    	hID= rs2.getInt(2);
                		    }
                		    catch(Exception e){
                		    	System.out.println("Sorry");
                		    }
                		}
                		System.out.println(hID);
            			System.out.println("Enter unique plane ID for your company");
            			int plID= sc.nextInt();
            			System.out.println("Enter the capacity of plane: ");
            			int cap= sc.nextInt();
            			CallableStatement cstmt = connection.prepareCall("INSERT INTO plane VALUES (?,?,?);");
                        cstmt.setInt(1, cap);
                        cstmt.setInt(2, plID);
                        cstmt.setInt(3, hID);
                        cstmt.execute();  
            			
            		}
            		//add flight
            		if(operation==3) {
            			CallableStatement cstmt2 = connection.prepareCall("select * from planecompanyadmin where username= ? and passkey= ?");
                		cstmt2.setString(1, userName);
                		cstmt2.setString(2, password);
                		
                		int hID=-3;
                		boolean hasResult = cstmt2.execute(); // Execute query
                		if (hasResult) {
                		    try (ResultSet rs2 = cstmt2.getResultSet()) {        		    	
                		    	rs2.next();
                		    	hID= rs2.getInt(2);
                		    }
                		    catch(Exception e){
                		    	System.out.println("Sorry");
                		    }
                		}
                		
                		System.out.println(hID);
            			System.out.println("Enter plane ID to add flight for");
            			int plID= sc.nextInt();
            			System.out.println("Enter the seats left in flight: ");
            			int cap= sc.nextInt();
            			System.out.println("Enter the unique flight number: ");
            			String flno= sc.nextLine();
            			flno= sc.nextLine();
            			System.out.println("Enter the source of flight: ");
            			String source= sc.nextLine();
            			flno= sc.nextLine();
            			System.out.println("Enter the destination of flight: ");
            			String dest= sc.nextLine();
            			flno= sc.nextLine();
            			System.out.println("Enter the travel date(yyyy-mm-dd): ");
            			String userInput= sc.nextLine();
            			userInput= sc.nextLine();
            			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date d1 = dateFormat.parse(userInput);
                        System.out.println("Enter the cost of a ticket: ");
                        int cst= sc.nextInt();
                        
            			CallableStatement cstmt = connection.prepareCall("INSERT INTO flight VALUES (?,?,?,?,?,?,?,?);");
                        cstmt.setInt(1, cap);
                        cstmt.setString(2, flno);
                        cstmt.setInt(3, plID);
                        cstmt.setInt(4, hID);
                        cstmt.setString(5, source);
                        cstmt.setString(6, dest);
                        cstmt.setDate(7, new java.sql.Date(d1.getTime()));
                        cstmt.setInt(8, cst);
                        cstmt.execute();  
            			
            		}
            	}
            }
            //customer
            else if(checkUser==1) {
            	int operation=-7;
            	int seatNo=0;
            	while(operation!=-1) {            		
            		System.out.println("PRESS 1 IF YOU WANT TO BOOK HOTEL\nPRESS 2 IF YOU WANT TO CANCEL BOOKING\\nPRESS 3 TO BOOK FLIGHT\nPRESS 4 TO CANCEL FLIGHT\nPRESS 5 TO RATE A HOTEL\nPRESS 6 TO RATE A PLANE COMPANY");
            		operation= sc.nextInt();
            		if(operation==1) {
            			System.out.println("Please enter the city you want to visit(List of cities for reference): ");
                		Statement stmt = connection.createStatement();
                		String query = "select * from cities";
                		ResultSet rs = stmt.executeQuery(query);
                		while (rs.next()) {
                			System.out.println(rs.getInt(1) + " "+ rs.getString(2)); //Print one element of a row
                		}
                		String city= sc.nextLine();
                		city= sc.nextLine();
                		System.out.println();
                		CallableStatement cstmt = connection.prepareCall("{call gethotels(?)}");
                		cstmt.setString(1, city);
                		boolean hasResults = cstmt.execute(); // Execute query
                		System.out.println("The available Hotels in "+city+" are:\n");
                		if (hasResults) {
                		    try (ResultSet rs2 = cstmt.getResultSet()) {        		    	
                		        while (rs2.next()) {
                		            System.out.println("Hotel Name: "+rs2.getString("hotelName") + "\nHotel Rating(Out of 5): " + rs2.getInt("rating") + "\nRoom Cost(in Rupees): " + rs2.getInt("roomcost"));
                		            System.out.println();
                		            System.out.println();
                		        }
                		    }
                		}
                		System.out.println("Enter the hotel you want to book: ");
                		String hotName=sc.nextLine();
                		CallableStatement cstmt3 = connection.prepareCall("{? = call booking(?, ?,?)}");
                		System.out.println("Enter the date you want to check-in(YYYY-MM-DD): ");
                		java.util.Date date = null;
                		boolean inch=false;
                		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                		while(!inch) {
                			String userInput = sc.nextLine();
                			try {
                                date = dateFormat.parse(userInput);
                                System.out.println("Entered date: " + date);
                                inch=true;
                            } catch (ParseException e) {
                                System.err.println("Invalid date format. Please enter date in the format yyyy-MM-dd.");
                                e.printStackTrace();  // You might want to handle this exception more gracefully in a real application
                            }
                		}
                        
                		System.out.println("Enter the date you want to check-out: ");
                		java.util.Date date2 = null;
                		boolean inch2=false;
                		SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
                		while(!inch2) {
                			String userInput = sc.nextLine();
                			try {
                                date2 = dateFormat2.parse(userInput);
                                System.out.println("Entered date: " + date2);
                                inch2=true;
                            } catch (ParseException e) {
                                System.err.println("Invalid date format. Please enter date in the format yyyy-MM-dd.");
                                e.printStackTrace();  // You might want to handle this exception more gracefully in a real application
                            }
                		}
                		cstmt3.registerOutParameter(1, Types.BOOLEAN);
                		cstmt3.setString(4, hotName);
                		cstmt3.setDate(2, new java.sql.Date(date.getTime()));
                		cstmt3.setDate(3, new java.sql.Date(date2.getTime()));
                		cstmt3.execute();
                        System.out.println();
                        boolean canBook= cstmt3.getBoolean(1);
                        //System.out.println(canBook);
                        if(canBook) {
                        	CallableStatement cstmt2 = connection.prepareCall("select * from customer where username= ? and passkey= ?");
                    		cstmt2.setString(1, userName);
                    		cstmt2.setString(2, password);
                    		
                    		int pID=-3;
                    		boolean hasResult = cstmt2.execute(); // Execute query
                    		if (hasResult) {
                    		    try (ResultSet rs2 = cstmt2.getResultSet()) {        		    	
                    		    	rs2.next();
                    		    	pID= rs2.getInt(1);
                    		    }
                    		    catch(Exception e){
                    		    	System.out.println("Sorry");
                    		    }
                    		}
                    		//System.out.println("Executing");
                    		CallableStatement cstmt4 = connection.prepareCall("{? = call createbooking(?, ?,?,?)}");
                    		cstmt4.registerOutParameter(1, Types.INTEGER);
                    		cstmt4.setString(4, hotName);
                    		cstmt4.setDate(2, new java.sql.Date(date.getTime()));
                    		cstmt4.setDate(3, new java.sql.Date(date2.getTime()));
                    		cstmt4.setInt(5, pID);
                    		cstmt4.execute();
                    		//System.out.println("Complete");
                    		int roomNum= cstmt4.getInt(1);
                    		System.out.println("Congratulations!! Your Alloted Room number: "+roomNum);
                    		System.out.println();
                        }
                        else {
                        	System.out.println("SORRY NO ROOMS ARE AVAILABLE FOR THIS HOSTEL IN THE DATES SPECIFIED. PLEASE SELECT SOME OTHER HOTEL");
                        }
            		}
            		else if(operation==2) {
            			System.out.println("SORRY! NO CANCELLATION POLICY AVAILABLE");
            		}
            		
					else if(operation==3) {
						System.out.println("The available flights are: ");
                		Statement stmt = connection.createStatement();
                		String query = "select * from flight";
                		ResultSet rs = stmt.executeQuery(query);
                		while (rs.next()) {
                			System.out.println("Company ID: "+rs.getInt(4) + "\nFlight Number:"+ rs.getString(2)+"\nSource:"+rs.getString(5)+"\nDestination: "+rs.getString(6)+"\nTravel Date: "+rs.getDate(7).toString()+"\nTicket Cost: "+rs.getInt(8)); //Print one element of a row
                			System.out.println();
                		}
                		System.out.println("Enter Flight Number you want to book: ");
                		String flNum= sc.nextLine();
                		flNum= sc.nextLine();
                		CallableStatement cstmt2 = connection.prepareCall("select * from customer where username= ? and passkey= ?");
                		cstmt2.setString(1, userName);
                		cstmt2.setString(2, password);
                		
                		int pID=-3;
                		boolean hasResult = cstmt2.execute(); // Execute query
                		if (hasResult) {
                		    try (ResultSet rs2 = cstmt2.getResultSet()) {        		    	
                		    	rs2.next();
                		    	pID= rs2.getInt(1);
                		    }
                		    catch(Exception e){
                		    	System.out.println("Sorry");
                		    }
                		}
                		Statement stmt1 = connection.createStatement();
                		String query1 = "select count(*) from bookFlight";
                        ResultSet rs1 = stmt1.executeQuery(query1);
                        rs1.next();
                        int bookID = rs1.getInt(1)+1+100;
                        System.out.println("Enter the seat Number you want: ");
                        int stno= sc.nextInt();
                        CallableStatement cstmt = connection.prepareCall("INSERT INTO bookFlight VALUES (?,?,?,?);");
                        cstmt.setInt(1, stno);
                        cstmt.setInt(2, bookID);
                        cstmt.setInt(3, pID);
                        cstmt.setString(4, flNum);
                        cstmt.execute();   
                        System.out.println("Your Booking ID is: "+bookID);
                        System.out.println();
                        System.out.println();
                        
					}
					else if(operation==4) {
						System.out.println("Enter your booking ID:");
						int bookID= sc.nextInt();
						CallableStatement cstmt = connection.prepareCall("delete from bookFlight where bookingID=?;");
                        cstmt.setInt(1, bookID);
                        cstmt.execute();  
					}
					else if(operation==5) {
						System.out.println("List of existing hotels for reference: ");
                		Statement stmt = connection.createStatement();
                		String query = "select * from hotel";
                		ResultSet rs = stmt.executeQuery(query);
                		while (rs.next()) {
                			System.out.println("Hotel ID: "+rs.getInt(1) + "\nHotel Name: "+ rs.getString(3)+"\nRating: "+rs.getInt(7)); //Print one element of a row
                			System.out.println();
                		}
                		System.out.println("Enter the Hotel ID you want to rate: ");
                		int hotelID= sc.nextInt();
                		
                		System.out.println("Enter rating out of 5(Integer Ratings only): ");
                		int rating= sc.nextInt();
                		int prevRating=3;
                		CallableStatement cstmt = connection.prepareCall("select * from hotel where hotelid= ?");
                		cstmt.setInt(1, hotelID);
                		boolean hasResults = cstmt.execute(); // Execute query
                		if (hasResults) {
                		    try (ResultSet rs2 = cstmt.getResultSet()) {        		    	
                		        rs2.next();
                		        prevRating= rs2.getInt(7);
                		    }
                		}
                		System.out.println(prevRating);
                		
                		int newRating= (prevRating+rating)/2;
                		System.out.println(newRating);
                		String updateQuery = "UPDATE hotel SET rating = ? WHERE hotelID = ?";
                		try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                		    preparedStatement.setInt(1, newRating);
                		    preparedStatement.setInt(2, hotelID);
                		    
                		    int rowsAffected = preparedStatement.executeUpdate();
                		    if (rowsAffected > 0) {
                		        System.out.println("Rating updated successfully for hotel ID: " + hotelID);
                		    } else {
                		        System.out.println("No hotel found with ID: " + hotelID+ " to "+newRating);
                		    }
                		} catch (SQLException e) {
                		    e.printStackTrace();
                		}
                		
					}
					else if(operation==6) {
						System.out.println("List of existing plane companies for reference: ");
                		Statement stmt = connection.createStatement();
                		String query = "select * from planecompany";
                		ResultSet rs = stmt.executeQuery(query);
                		while (rs.next()) {
                			System.out.println("Plane Company ID: "+rs.getInt(2) + "\nPlane Company Name: "+ rs.getString(3)+"\nRating: "+rs.getInt(4)); //Print one element of a row
                			System.out.println();
                		}
                		System.out.println("Enter the Plane Company ID you want to rate: ");
                		int hotelID= sc.nextInt();
                		
                		System.out.println("Enter rating out of 5(Integer Ratings only): ");
                		int rating= sc.nextInt();
                		int prevRating=3;
                		CallableStatement cstmt = connection.prepareCall("select * from planecompany where companyid= ?");
                		cstmt.setInt(1, hotelID);
                		boolean hasResults = cstmt.execute(); // Execute query
                		if (hasResults) {
                		    try (ResultSet rs2 = cstmt.getResultSet()) {        		    	
                		        rs2.next();
                		        prevRating= rs2.getInt(4);
                		    }
                		}
                		System.out.println(prevRating);
                		
                		int newRating= (prevRating+rating)/2;
                		System.out.println(newRating);
                		String updateQuery = "UPDATE planecompany SET rating = ? WHERE companyid = ?";
                		try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                		    preparedStatement.setInt(1, newRating);
                		    preparedStatement.setInt(2, hotelID);
                		    
                		    int rowsAffected = preparedStatement.executeUpdate();
                		    if (rowsAffected > 0) {
                		        System.out.println("Rating updated successfully for plane company with ID: " + hotelID+ " to "+newRating);
                		    } else {
                		        System.out.println("No hotel found with ID: " + hotelID);
                		    }
                		} catch (SQLException e) {
                		    e.printStackTrace();
                		}
                		
					}
            	}
            	
            }            
        } catch (SQLException e) {
            System.out.println("Error while connecting to the database: " + e.getMessage());
        } catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }
}