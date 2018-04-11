package de.pizzaalex.db;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Class for managing the database connection.<br>
 *
 * @author AWagner
 */
public class DbConnection {

    /**
     * Intializes the connection to the database "PizzaDB". <br>
     * Database properties and connection pool is handled by glassfish server.
     * 
     * @return The connection, if no exception occured. Otherwise returns null.
     */
    protected Connection getConnection() {

        Connection connec = null;

        try {
            System.out.println("new Connection");
            
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("jdbc/PizzaPool");//
            connec = ds.getConnection();
            connec.setAutoCommit(false);
           
        } catch (SQLException ex) {
            Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return connec;
    }

}
