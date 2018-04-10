package de.pizzaalex.db;

import de.pizzaalex.model.User;
import de.pizzaalex.util.Encoder;
import java.sql.*;

/**
 * Class which manages the data access between a Customer object and the
 * connected database
 *
 * @author AWagner
 */
public class DaoUser extends DbConnection {

    PreparedStatement stmU;
    ResultSet rsU;
    
    
    public void readUserData(Connection con, User u) throws SQLException {
        stmU = con.prepareStatement("SELECT * FROM benutzer WHERE BeNr = ?");
        stmU.setInt(1, u.getUserID());
        rsU = stmU.executeQuery();
        rsU.next();
        u.setUsername(rsU.getString("BeName").toLowerCase());
        u.setPassword(rsU.getString("Passwort"));
        u.setRole(rsU.getString("Rolle"));
    }

    public void storeUser(Connection con, User u) throws SQLException {
        stmU = con.prepareStatement("INSERT INTO benutzer (BeName, passwort, rolle) "
                + "VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
        stmU.setString(1, u.getUsername().trim());
        stmU.setString(2, Encoder.hash(u.getPassword()));
        stmU.setString(3, u.getRole().trim());
        int rows = stmU.executeUpdate();
        
        rsU = stmU.getGeneratedKeys();
        rsU.next();
        int UserID = rsU.getInt(1);
        u.setUserID(UserID);
        System.out.printf("Es wurde User Nr %d hinzugefügt %n", UserID);        
    }

}
