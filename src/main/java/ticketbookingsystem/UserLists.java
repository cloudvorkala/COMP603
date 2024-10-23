/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ticketbookingsystem;
import java.util.LinkedList;


/**
 *
 * @author C.V
 */
public class UserLists {
    public LinkedList <Customer>  customerList ;
    
    public UserLists(){
        LinkedList <Customer>  c = new LinkedList<>();
        
        
        this.customerList =  c;
        
        
    }
    public UserLists(Customer a){
        LinkedList <Customer>  c = new LinkedList<>();
        
        c.add(a);
        this.customerList =  c;
    }
    
    public void addUser(Customer a){
        this.customerList.add(a);
    }
    
    
    public Customer findCustomerByUsername(String name){
        for(Customer a: this.customerList)if (a.name.equals(name)) return a;
        
        return null;
    }
    
    public void deleteUser(String name){
        for(Customer a: this.customerList)if (a.name.equals(name)) this.customerList.remove(a);
    }
            
     
}
