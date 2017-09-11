/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CustomExceptions;

/**
 *
 * @author laaks
 */
public class UniqueConstraintException extends Exception {

    public UniqueConstraintException(String msg) {
        super(msg);
    }
}
