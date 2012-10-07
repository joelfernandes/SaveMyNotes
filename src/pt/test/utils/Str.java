/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pt.test.utils;

/**
 *
 * @author Joel
 */
public class Str {

    public static boolean isEmpty(String str) {
        return (str == null|| str.isEmpty());
    }
    
    public static boolean isBlank(String str) {
        return (str == null|| str.trim().isEmpty());
    }

}
