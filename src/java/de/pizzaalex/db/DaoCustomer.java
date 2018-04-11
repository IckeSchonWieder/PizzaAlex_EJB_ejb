

package de.pizzaalex.db;

import de.pizzaalex.model.Customer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Class which manages the data access between a Customer object and the 
 * connected database
 * 
 * @author AWagner
 */
public class DaoCustomer extends DaoUser {
    PreparedStatement stm;
    ResultSet rs;
    Connection connec = null;    
 
   
    /**
     * Method for reading all the contacts in database in table "Kunde"
     * 
     * @return A list (collection object) of customers read from database.
     */ 
    public List<Customer> readCustomers() {
        List<Customer> customers = new ArrayList<>();
        
        try {
            connec = getConnection();
            stm = connec.prepareStatement("SELECT * FROM Kunde");
            rs = stm.executeQuery();
            
            while(rs.next()){
                Customer cus = new Customer();
                cus.setId(rs.getInt("KdNr"));
                cus.setFirstname(rs.getString("Vorname"));
                cus.setLastname(rs.getString("Name"));
                cus.setStreet(rs.getString("Strasse"));
                cus.setPostalcode(rs.getString("Plz"));
                cus.setCity(rs.getString("Ort"));
                cus.setUserID(rs.getInt("BeNr"));
                 
                readUserData(connec, cus);
                
                customers.add(cus);
                
            }
           
        } catch (SQLException ex) {
            Logger.getLogger(DaoCustomer.class.getName()).log(Level.SEVERE, null, ex);
        
        // Close connection in any case!
        } finally  {
            if (connec != null)
                try {connec.close();} catch (SQLException e) {e.printStackTrace();}
        }
        return customers;
        
    }
    
    
    public Customer readSingleCustomer(int id) {
        Customer cus = new Customer();
        
        try {
            connec = getConnection();
            stm = connec.prepareStatement("SELECT * FROM Kunde WHERE KdNr = ?");
            stm.setInt(1, id);
            rs = stm.executeQuery();
            
            rs.next();
            cus.setId(rs.getInt("KdNr"));
            cus.setUserID(rs.getInt("BeNr"));
            cus.setFirstname(rs.getString("Vorname"));
            cus.setLastname(rs.getString("Name"));
            cus.setStreet(rs.getString("Strasse"));
            cus.setPostalcode(rs.getString("Plz"));
            cus.setCity(rs.getString("Ort"));

            readUserData(connec, cus);
           
        } catch (SQLException ex) {
            Logger.getLogger(DaoCustomer.class.getName()).log(Level.SEVERE, null, ex);
        
        // Close connection in any case!
        } finally  {
            if (connec != null)
                try {connec.close();} catch (SQLException e) {e.printStackTrace();}
        }
        return cus;
        
    }
   
    /**
     * Method for storing a Customer. Customer's attributes are inserted into table 
     * "Kunde" of database "PizzaDB". The new customer number "KdNr" is set
     * automatically by table (primary key auto_inc).
     * 
     * @param cus Customer, which will be added to database.
     * @return True, if no exception. Otherwise returns false.
     */
    public boolean storeContact(Customer cus) {
        try {
            System.out.println("DAOCust: " + cus.hashCode());
            
            connec = getConnection();
            if (connec == null) {
                return false;
            }
            
            
            storeUser(connec, cus);
                        
            stm = connec.prepareStatement("INSERT INTO Kunde (Vorname, Name, Strasse, Plz, Ort, BeNr) "
                                        + "VALUES (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS );
            stm.setString(1, cus.getFirstname().trim());
            stm.setString(2, cus.getLastname().trim());
            stm.setString(3, cus.getStreet().trim());
            stm.setString(4, cus.getPostalcode().trim());
            stm.setString(5, cus.getCity().trim());
            // UserID=BeNr was automatically set by database, when storing user
            stm.setInt(6, cus.getUserID());
            
            int rows = stm.executeUpdate();
            
            // KdNr is automatically set by database (primary key)
            rs = stm.getGeneratedKeys();
            rs.next();
            int KdNr = rs.getInt(1);
            cus.setId(KdNr);
            System.out.printf("Es wurde Kunde Nr %d hinzugefügt %n", KdNr);
           
            return true;
        
        } catch (SQLException ex) {
            Logger.getLogger(DaoCustomer.class.getName()).log(Level.SEVERE, null, ex);
        
        //  Close connection in any case!
        } finally  {
            if (connec != null)
                try {connec.close();} catch (SQLException e) {e.printStackTrace();}
        }
        
        return false;
    }
    
    
    /**
     * Method for deleting a customer from the table "Kunde" in database 
     * "PizzaDB". Customer is identified by its customer number "KdNr" which is 
     * stored in the Customer attribute "ID".
     * 
     * @param cus Customer which shall be deleted from database.
     * @return True, if no exception. Otherwise returns false. 
     
    public boolean deleteContact(Customer cus) {
        try {

            connec = getConnection();
            if (connec == null) {
                return false;
            }
            
            stm = connec.prepareStatement("DELETE FROM Kunde WHERE KdNr = ?");
            stm.setInt(1, cus.getId());
            
            int rows = stm.executeUpdate();
            
            System.out.printf("Es wurde(n) %d Reihe(n) entfernt! %n", rows);
            
            return true;
        
        } catch (SQLException ex) {
            Logger.getLogger(DaoCustomer.class.getName()).log(Level.SEVERE, null, ex);
        
        // Close connection in any case!
        } finally  {
            if (connec != null)
                try {connec.close();} catch (SQLException e) {e.printStackTrace();}
        }
        
        return false;
    }
    */
}
