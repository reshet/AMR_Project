/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mplatforma.amr.service;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.jws.WebService;


/**
 *
 * @author reshet
 */

@Stateless
@LocalBean
@WebService
public class AmrBean {
    @EJB AmrEAOLocal eao;
    public AmrBean()
    {
        //eao.createAccount();
    }
    public long getBookCount()
    {
        eao.createAccount();
        return 22;
    }
    
}
