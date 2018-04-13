

package de.pizzaalex.db;

import de.pizzaalex.model.Order;
import de.pizzaalex.model.OrderedItem;
import de.pizzaalex.model.Pizza;
import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Class which manages the data access between an Order object and the 
 * connected database
 * 
 * @author AWagner
 */
public class DaoOrder extends DbConnection {
    Connection connec;
   
    /**
     * Method for storing a Order. Order's attributes are inserted into table 
     * "Bestellung" of database "PizzaDB". The new order number "BeNr" is set
     * automatically by table (primary key auto_inc).
     * 
     * @param ord Order, which will be added to database.
     * @return True, if no exception. Otherwise returns false.
     */
    public boolean storeOrder(Order ord) {
        
        try {
            connec = getConnection();
            
            if (connec == null) {
                return false;
            }
            
            int OrNr = 0;
            
            PreparedStatement stm = connec.prepareStatement("INSERT INTO Bestellung (KdNr, IPAddr, SessID, Preis) "
                                        + "VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS );
            stm.setInt(1, ord.getCus().getId());
            stm.setString(2, ord.getIpAddr().trim());
            stm.setString(3, ord.getSessId().trim());
            stm.setDouble(4, ord.getTotal());
            
            
            int rows = stm.executeUpdate();
            ResultSet rs = stm.getGeneratedKeys();
            rs.next();
            OrNr = rs.getInt(1);
            ord.setId(OrNr);
            // BeNr is automatically set by database (primary key)
            
            System.out.printf("Es wurde Bestellung Nr %d hinzugefügt %n", OrNr);
            
            for (OrderedItem item:ord.getItems()) {
                storePizza(item.getPizza(), OrNr, item.getCount());
            }
            
            
            
            return true;
        
        } catch (SQLException ex) {
            Logger.getLogger(Order.class.getName()).log(Level.SEVERE, null, ex);
        
        //  Close connection in any case!
        } finally  {
            if (connec != null)
                try {connec.close();} catch (SQLException e) {e.printStackTrace();}
        }
        
        return false;
    }
    
    
    public void storePizza(Pizza p, int OrNr, int count) throws SQLException  {
       // int PoNr = 0;
            
        PreparedStatement stm = connec.prepareStatement("INSERT INTO Position (BeNr, PiNr, Anzahl) "
                                    + "VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS );
        stm.setInt(1, OrNr);
        stm.setInt(2, p.getId());
        stm.setInt(3, count);
            
        stm.executeUpdate();
        
    }
    
    
    
    /*
       -----  unused methods. preparation for later use.  -----
    */
    
    
    
    
    /**
     * Method for deleting a order from the table "Bestellung" in database 
     * "PizzaDB". Order is identified by its order number "BeNr" which is 
     * stored in the Order attribute "ID".
     * 
     * @param ord Order which shall be deleted from database.
     * @return True, if no exception. Otherwise returns false. 
     
    public boolean deleteOrder(Order ord) {
        try {

            connec = getConnection();
            if (connec == null) {
                return false;
            }
            
            PreparedStatement stm = connec.prepareStatement("DELETE FROM Bestellung WHERE BeNr = ?");
            stm.setInt(1, ord.getId());
            
            int rows = stm.executeUpdate();
            
            System.out.printf("Es wurde(n) %d Reihe(n) entfernt! %n", rows);
            
            return true;
        
        } catch (SQLException ex) {
            Logger.getLogger(Order.class.getName()).log(Level.SEVERE, null, ex);
        
        // Close connection in any case!
        } finally  {
            if (connec != null)
                try {connec.close();} catch (SQLException e) {e.printStackTrace();}
        }
        
        return false;
    }
    */
    
    
    
    /**
     * Method for reading all the orders in database in table "Bestellung"
     * 
     * @return A list (collection object) of orders read from database.
      
    public List<Order> readOrders() {
        List<Order> orders = new ArrayList<>();
                
        try {
            connec = getConnection();
            PreparedStatement stm = connec.prepareStatement("SELECT * FROM Bestellung");
            ResultSet rs = stm.executeQuery();
           
            
            while(rs.next()){
                Order ord = new Order();
                ord.setId(rs.getInt("BeNr"));
                ord.setCusID(rs.getInt("KdNr"));
                ord.setIpAddr(rs.getString("IPAddr"));
                ord.setSessId(rs.getString("SessID"));
                ord.setSubtotal(rs.getDouble("Preis"));
               
                orders.add(ord);
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Order.class.getName()).log(Level.SEVERE, null, ex);
        
        // Close connection in any case!
        } finally  {
            if (connec != null)
                try {connec.close();} catch (SQLException e) {e.printStackTrace();}
        }
        
        return orders;
        
    }
    
    
    */
}
