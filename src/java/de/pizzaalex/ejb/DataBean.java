
package de.pizzaalex.ejb;

import de.pizzaalex.db.DaoCustomer;
import de.pizzaalex.db.DaoOrder;
import de.pizzaalex.db.DaoPizza;
import de.pizzaalex.model.Customer;
import de.pizzaalex.model.Order;
import de.pizzaalex.model.Pizza;
import java.io.Serializable;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.ejb.Stateless;


/**
 *
 * @author AWagner
 */
@Stateful(mappedName = "ejb/DataBean")
public class DataBean implements Serializable, DataBeanRemote {
   
    
    public DataBean() {

    }
    
    
     
    @Override
    public ArrayList<Customer> getCustomers() {
        DaoCustomer dc = new DaoCustomer();
        return (ArrayList)dc.readCustomers();
    }

    @Override
    public Customer storeCustomer(Customer cus) {
        DaoCustomer dc = new DaoCustomer();
        dc.storeContact(cus);
        
        return cus;
    }
    
    
    @Override
    public ArrayList<Pizza> getMenuList() {
        DaoPizza dp = new DaoPizza();
        return (ArrayList)dp.readMenu();
    }

    @Override
    public Pizza storePizza(Pizza p) {
        DaoPizza dp = new DaoPizza();
        dp.storePizza(p);
        return p;
    }

    @Override
    public void removePizza(Pizza p) {
        DaoPizza dp = new DaoPizza();
        dp.deletePizza(p);
    }
    
    
    @Override
    public void storeOrder(Order ord) {
        DaoOrder dao = new DaoOrder();
        dao.storeOrder(ord);
    }
}
