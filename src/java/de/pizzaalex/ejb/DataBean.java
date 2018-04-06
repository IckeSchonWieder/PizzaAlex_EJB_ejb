
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
    private ArrayList<Pizza> menuList;
    private ArrayList<Customer> customers;

    
    public DataBean() {
        menuList = new ArrayList();
        customers = new ArrayList();
    }
    
    
    @PostConstruct
    public void readLists(){
        DaoCustomer dc = new DaoCustomer();
        customers.addAll(dc.readCustomers());
        
        DaoPizza dp = new DaoPizza();
        menuList.addAll(dp.readMenu());
    }
    
        
    @Override
    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    @Override
    public void storeCustomer(Customer cus) {
        DaoCustomer dc = new DaoCustomer();
        dc.storeContact(cus);
        customers.add(cus);
    }
    
    
    @Override
    public ArrayList<Pizza> getMenuList() {
        return menuList;
    }

    @Override
    public void storePizza(Pizza p) {
        DaoPizza dp = new DaoPizza();
        dp.storePizza(p);
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
