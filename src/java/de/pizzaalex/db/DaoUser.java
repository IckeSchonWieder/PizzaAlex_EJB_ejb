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

    PreparedStatement stm;
    ResultSet rs;
    /**
     * Connection to database. Is managed by super Class (DbConnection)
     */
    Connection connec = null;

    public void readUserData(Connection con, User u) throws SQLException {
        stm = con.prepareStatement("SELECT * FROM benutzer WHERE BeNr = ?");
        stm.setInt(1, u.getUserID());
        rs = stm.executeQuery();
        rs.next();
        u.setUsername(rs.getString("BeName"));
        u.setPassword(rs.getString("Passwort"));
        u.setRole(rs.getString("Rolle"));

    }

    public void storeUser(Connection con, User u) throws SQLException {
        stm = connec.prepareStatement("INSERT INTO benutzer (BeName, passwort, rolle) "
                + "VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
        stm.setString(1, u.getUsername().trim());
        stm.setString(2, Encoder.hash(u.getPassword()));
        stm.setString(3, u.getRole().trim());
        int rows = stm.executeUpdate();
        
        rs = stm.getGeneratedKeys();
        rs.next();
        int UserID = rs.getInt(1);
        u.setUserID(UserID);
        System.out.printf("Es wurde User Nr %d hinzugefügt %n", UserID);        
    }

}
