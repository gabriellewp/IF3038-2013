<%@ page import="java.sql.*"%>
<%@ page import="java.io.*" %> 
<%
    //berhasil
    String kategori="PROGIN";
    ResultSet rs = null;
   try{
    String connectionURL ="jdbc:mysql://localhost:3306/progin_405_13510060";
    Connection connection = null;
    Statement statement= null;
   
    Class.forName("com.mysql.jdbc.Driver").newInstance();
    connection = DriverManager.getConnection(connectionURL,"progin","progin");
    statement = connection.createStatement();
    String QueryStr = "SELECT * FROM task WHERE (cat_task_name='"+kategori+"')";
    rs = statement.executeQuery(QueryStr);
   
   
    } catch(Exception e){
       System.out.println("Exception pas connect ");
       e.printStackTrace();
       //out.println("Unable to connect to database "+e.getMessage());
    }
     while(rs.next()){
        out.println(rs.getString("task_name")+rs.getString("assignee_name")+"<br>");
    }

     
%>
