
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FinalPractical {
    // Values needed during database connecitons

    private String username = "root";
    private String password = "student";
    private String database = "serenity";

    private String driver = "com.mysql.jdbc.Driver";
    private String uri = "jdbc:mysql://localhost/" + database + "?autoReconnect=true&useSSL=false";

    // Connection object created in method1, used for all other work
    private Connection conn;

    /**
     * Main driver to test the 4 methods
     */
    public static void main(String[] args) {
        FinalPractical exam = new FinalPractical();
    }

    public FinalPractical() {

        System.out.println("1-Database connection is " + method1());
        System.out.println("2-Records changed by update: " + method2("st233",872));  // ADD PARAMETERS
        System.out.println("3-Records changed by delete: " + method3(21));    // ADD PARAMETERS
        System.out.println("4-Database closed status is " + method4());
    }

    /**
     * Connect to the database
     */
    public boolean method1() {
        boolean status = false;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(uri, username, password);
            status = true;
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return status;
    } // end method1()

    /**
     * Input parameters are the LabelLot and the Song Length Updates the
     * 'titles' table to have year be 2016
     *
     * @return Count of records updated, or on error -1
     */
    public int method2(String sLabelLot, int iLength) {
        try {
            //Transaction is handled manually from here onwards
            conn.setAutoCommit(false);
            String sql = "UPDATE titles SET Year=? WHERE (LabelLot=? and Length=?)";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, "2016");
            statement.setString(2, sLabelLot);
            statement.setInt(3, iLength);

            int rowsUpdated = statement.executeUpdate();
            
            //Commit the transaction
            conn.commit();
            
            //Return the number of rows(records) updated
            return rowsUpdated;
        } catch (SQLException ex) {
            try {
                //roll back to previous state
                conn.rollback();
            } catch (SQLException ex1) {
                ex1.printStackTrace();
            }
            return -1;
        }
        // end method2()
    }

    /**
     * Input parameter is the titleid Deletes all titleids in these 5 tables:
     * titles, writers, publishers, attributes, history
     *
     * @return count (sum) of all records deleted
     */
    public int method3(int iTitleID) {

        try {
            /*
            //Transaction is handled manually from here onwards
            conn.setAutoCommit(false);
            
            String titlesSql = "DELETE FROM titles WHERE TitleID=?";            
            PreparedStatement titlesStatement = conn.prepareStatement(titlesSql);
            titlesStatement.setInt(1, iTitleID);            
            int rowsDeleted = titlesStatement.executeUpdate();
            
            String writersSql = "DELETE FROM writers WHERE TitleID=?";            
            PreparedStatement writersStatement = conn.prepareStatement(writersSql);
            writersStatement.setInt(1, iTitleID);            
            rowsDeleted += writersStatement.executeUpdate();
            
            String publishersSql = "DELETE FROM publishers WHERE TitleID=?";            
            PreparedStatement publishersStatement = conn.prepareStatement(publishersSql);
            publishersStatement.setInt(1, iTitleID);            
            rowsDeleted += publishersStatement.executeUpdate();
            
            String attributesSql = "DELETE FROM attributes WHERE TitleID=?";            
            PreparedStatement attributesStatement = conn.prepareStatement(attributesSql);
            attributesStatement.setInt(1, iTitleID);            
            rowsDeleted += attributesStatement.executeUpdate();
            
            String historySql = "DELETE FROM history WHERE TitleID=?";            
            PreparedStatement historyStatement = conn.prepareStatement(historySql);
            historyStatement.setInt(1, iTitleID);            
            rowsDeleted += historyStatement.executeUpdate();
            
            //Commit the transaction
            conn.commit();
            */
            
            String titlesSql = "SELECT * FROM titles WHERE TitleID=?";            
            PreparedStatement titlesStatement = conn.prepareStatement(titlesSql);
            titlesStatement.setInt(1, iTitleID);            
            ResultSet titlesRs=titlesStatement.executeQuery();  
            ResultSetMetaData titlesRsmd=titlesRs.getMetaData(); 
            //Print metadata infos
            printMetadataInfos(titlesRsmd);            
            titlesRs.last();
            int rowsSelected = titlesRs.getRow();
            
            String writersSql = "SELECT * FROM writers WHERE TitleID=?";            
            PreparedStatement writersStatement = conn.prepareStatement(writersSql);
            writersStatement.setInt(1, iTitleID);            
            ResultSet writersRs=writersStatement.executeQuery();  
            ResultSetMetaData writersRsmd=writersRs.getMetaData(); 
            //Print metadata infos
            printMetadataInfos(writersRsmd);
            writersRs.last();
            rowsSelected+=writersRs.getRow();
            
            String publishersSql = "SELECT * FROM publishers WHERE TitleID=?";            
            PreparedStatement publishersStatement = conn.prepareStatement(publishersSql);
            publishersStatement.setInt(1, iTitleID);            
            ResultSet publishersRs=publishersStatement.executeQuery();  
            ResultSetMetaData publishersRsmd=publishersRs.getMetaData(); 
            //Print metadata infos
            printMetadataInfos(publishersRsmd);
            publishersRs.last();
            rowsSelected+=publishersRs.getRow();
            
            String attributesSql = "SELECT * FROM attributes WHERE TitleID=?";            
            PreparedStatement attributesStatement = conn.prepareStatement(attributesSql);
            attributesStatement.setInt(1, iTitleID);            
            ResultSet attributesRs=attributesStatement.executeQuery();  
            ResultSetMetaData attributesRsmd=attributesRs.getMetaData(); 
            //Print metadata infos
            printMetadataInfos(attributesRsmd);
            attributesRs.last();
            rowsSelected+=attributesRs.getRow();
            
            String historySql = "SELECT * FROM history WHERE TitleID=?";            
            PreparedStatement historyStatement = conn.prepareStatement(historySql);
            historyStatement.setInt(1, iTitleID);            
            ResultSet historyRs=historyStatement.executeQuery();  
            ResultSetMetaData historyRsmd=historyRs.getMetaData(); 
            //Print metadata infos
            printMetadataInfos(historyRsmd);
            historyRs.last();
            rowsSelected+=historyRs.getRow();
            
            return rowsSelected;
        } // end method3()
        catch (SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
            /*try {
                //roll back to previous state
                conn.rollback();
            } catch (SQLException ex1) {
                ex1.printStackTrace();
            }*/
            
            return -1;
        }

    }
    
    private void printMetadataInfos(ResultSetMetaData rsmd) throws SQLException{
        if(rsmd.getColumnCount()>0){
            System.out.println("Table Name : "+rsmd.getTableName(1));
        }
        System.out.println("Total No of Columns : "+rsmd.getColumnCount());
        for(int i=1;i<=rsmd.getColumnCount();i++){
            System.out.println("Column Name of Column Index : "+i+" is :'"+rsmd.getColumnName(i)+"' and the Column Data Type is :"+rsmd.getColumnTypeName(i));
        }
    }

    /**
     * Close the database
     */
    public boolean method4() {
        boolean status = false;
        try {
            conn.close();
            status = true;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } catch (Exception e) {
            System.out.println("How did I get here in method4()?");
            e.printStackTrace();
        }
        return status;
    } // end method4()

}
