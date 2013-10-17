/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Exceptions;

/**
 *
 * @author Bernhard Fischer
 */
public class InvalidFormatException extends Exception{
    private static final long serialVersionUID = 1L;

    public InvalidFormatException() {
	super();
    }

    public InvalidFormatException(String s) {
	super(s);
    }
    
}
