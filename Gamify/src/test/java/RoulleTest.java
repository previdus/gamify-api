
//STEP 1. Import required packages
import java.sql.*;

public class RoulleTest {
   // JDBC driver name and database URL
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://localhost/r";

   //  Database credentials
   static final String USER = "root";
   static final String PASS = "root";
   
   public static void main(String[] args) throws SQLException, InterruptedException {
   Connection conn = null;
   Statement stmt = null;
   addBetTransactionOnSame(null);
//   try{
//      //STEP 2: Register JDBC driver
//      Class.forName("com.mysql.jdbc.Driver");
//
//      //STEP 3: Open a connection
//      System.out.println("Connecting to a selected database...");
//      conn = DriverManager.getConnection(DB_URL, USER, PASS);
//      System.out.println("Connected database successfully...");
//      
//      //STEP 4: Execute a query
//      System.out.println("Inserting records into the table...");
//     stmt = conn.createStatement();
//     addBetTransaction(stmt);
//     
//   }catch(SQLException se){
//      //Handle errors for JDBC
//      se.printStackTrace();
//   }catch(Exception e){
//      //Handle errors for Class.forName
//      e.printStackTrace();
//   }finally{
//      //finally block used to close resources
//      try{
//         if(stmt!=null)
//            conn.close();
//      }catch(SQLException se){
//      }// do nothing
//      try{
//         if(conn!=null)
//            conn.close();
//      }catch(SQLException se){
//         se.printStackTrace();
//      }//end finally try
//   }//end try
   System.out.println("Goodbye!");
}//end main
   
   private static void addUser(Statement stmt) throws SQLException{
	   for(int i=1; i<= 2000; i++){
	       String sql = "INSERT INTO user (name, status) " +
	                   "VALUES('test"+i+"', 25)";
	     
	      //conn.commit();
	      
	      System.out.println("Inserted : " + sql);
	      stmt.executeUpdate(sql);
	     }  
   }
   
   private static void addBetTransactionOnSame(Statement stmt) throws SQLException, InterruptedException{
	   long bet = 100;
	   long lost =0;
	   int luckNo;
	   long maxLoss = 0;
	   long maxProfit = 0;
	   for(int j=1; j<=100; j++){
		   System.out.println(lost);
		   if(lost < maxLoss)
			   maxLoss = lost;
		   if(lost > maxProfit)
			   maxProfit = lost;
		   //Thread.sleep(50);
		   luckNo = (int)(Math.random()*37);
//	       String sql = "INSERT INTO bet_transaction (user_id, game_id, bet_amount, bet_code) " +
//	                   "VALUES( 8 ,1, " + bet +  ",45 )" ;
	       if(luckNo >=1 && luckNo <=12){
	    	   lost = lost + (bet* 3);
	    	   bet = 100;
	       }
	       else{ 
	    	   lost = lost - bet;
	    	   bet = bet*2;
	       }
	     // System.out.println("Inserted : " + sql);
	     // stmt.executeUpdate(sql);
	      
	   }
	   System.out.println(maxLoss + " || " + maxProfit);
   }
   
   private static void addBetTransaction(Statement stmt) throws SQLException{
	   for(int j=8; j<=300; j++){
	   for(int i=1; i<= 22; i++){
		  
	       String sql = "INSERT INTO bet_transaction (user_id, game_id, bet_amount, bet_code) " +
	                   "VALUES(" + j + " ,1, " + 1000 +  ", "+ (int)(Math.random()*49) + " )" ;
	       
	     
	      //conn.commit();
	      
	      System.out.println("Inserted : " + sql);
	      stmt.executeUpdate(sql);
	     }  
	   }
   }
}//end JDBCExample
