
package cipheredUser;
import java.math.BigInteger;

/**
 *
 * @author C.V
 */

//this class generates public and private keys
public class endn {
    private BigInteger e,n,d;
    
    protected endn(BigInteger p, BigInteger q, BigInteger s){
        this.n =  p.multiply(q);
        
        // φ(n) = (p-1)(q-1) 
        BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE)); 
        this.e = s;
        this.d = e.modInverse(phi);
        
    }
    
    // public key and n
    protected BigInteger gete(){
        return this.e;
    }
    
    
    protected BigInteger getn(){
        return this.n;
    }
    
    //private key (and n above)
    protected BigInteger getd(){
        return this.d;
    }
    
    
}
