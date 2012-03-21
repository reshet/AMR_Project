/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ejb.Stateless;

/**
 *
 * @author reshet
 */
@WebService(serviceName = "GateWebService")
@Stateless()
public class GateWebService {
    @EJB
    private GateBeanLocal ejbRef;// Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Web Service Operation")

    /**
     * Операция веб-службы
     */
    @WebMethod(operationName = "toLogin")
    public String toLogin() {
        return "You are welocme";
    }
    
}
