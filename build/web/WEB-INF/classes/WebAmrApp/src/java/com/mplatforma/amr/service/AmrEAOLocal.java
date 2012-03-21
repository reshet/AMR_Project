/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mplatforma.amr.service;

import javax.ejb.Local;

/**
 *
 * @author reshet
 */
@Local
public interface AmrEAOLocal {
    public long getBooks();
    public void createAccount();
}
