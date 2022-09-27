
import java.util.*;                       
import java.sql.*;                        
                                          


                                    
public class FinalPractical {
	// Values needed during database connecitons
   private String username = "root";
   private String password = "student";
   private String database = "serenity";


   private String driver = "com.mysql.jdbc.Driver";
   private String uri = "jdbc:mysql://localhost/"+database+"?autoReconnect=true&useSSL=false";

   // Connection object created in method1, used for all other work
   private Connection conn;                                       
   
	/** Main driver to test the 4 methods */
   public static void main(String[] args) {
      FinalPractical exam = new FinalPractical();
   }
   
   public FinalPractical(){
   
      System.out.println("1-Database connection is "+ method1());
      System.out.println("2-Records changed by update: " + method2( , ));  // ADD PARAMETERS
      System.out.println("3-Records changed by delete: " + method3());    // ADD PARAMETERS
      System.out.println("4-Database closed status is "+ method4() );
   }
		
	/** Connect to the database */
   public boolean method1() {
      boolean status = false;
      try {
         Class.forName(driver);
         conn = DriverManager.getConnection(uri, username, password);
         status = true;
      }
      catch(ClassNotFoundException cnfe) {
         cnfe.printStackTrace();
      }
      catch(SQLException sqle) {
         sqle.printStackTrace();
      }
      return status;
   } // end method1()
	
	/**
      Input parameters are the LabelLot and the Song Length 
      Updates the 'titles' table to have year be 2016 
      @return Count of records updated, or on error -1	 */
   public int method2( ) {
       

   } // end method2()
	
   /**
      Input parameter is the titleid
      Deletes all titleids in these 5 tables:
            titles, writers, publishers, attributes, history
      @return count (sum) of all records deleted
   */
   public int method3(  ) {
       
       

   
   } // end method3()
	
   
   /** Close the database */
   public boolean method4() {
      boolean status = false;
      try {
         conn.close();
         status = true;
      }
      catch( SQLException sqle ){
         sqle.printStackTrace();
      }
      catch( Exception e){
         System.out.println("How did I get here in method4()?");
         e.printStackTrace();
      }
      return status;
   } // end method4()
	
}
