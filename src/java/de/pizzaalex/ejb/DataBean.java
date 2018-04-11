
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
    private DaoCustomer dc;
    private DaoPizza dp;
    
    public DataBean() {
        menuList = new ArrayList();
        customers = new ArrayList();
    }
    
    
    @PostConstruct
    public void readLists(){
        dc = new DaoCustomer();
        customers.addAll(dc.readCustomers());
        
        
        dp = new DaoPizza();
        menuList.addAll(dp.readMenu());
    }
    
        
    @Override
    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    @Override
    public Customer storeCustomer(Customer cus) {
        dc.storeContact(cus);
        customers.add(cus);
        
        return cus;
    }
    
    
    @Override
    public ArrayList<Pizza> getMenuList() {
        return menuList;
    }

    @Override
    public void storePizza(Pizza p) {
        dp.storePizza(p);
    }

    @Override
    public void removePizza(Pizza p) {
        dp.deletePizza(p);
    }
    
    
    @Override
    public void storeOrder(Order ord) {
        DaoOrder dao = new DaoOrder();
        dao.storeOrder(ord);
    }
}
