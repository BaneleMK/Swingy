/* ************************************************************************** */
/*                                                                            */
/*                                                        :::      ::::::::   */
/*   SaveHero.java                                      :+:      :+:    :+:   */
/*                                                    +:+ +:+         +:+     */
/*   By: bmkhize <bmkhize@student.42.fr>            +#+  +:+       +#+        */
/*                                                +#+#+#+#+#+   +#+           */
/*   Created: 2019/07/05 11:44:18 by bmkhize           #+#    #+#             */
/*   Updated: 2019/07/29 15:36:01 by bmkhize          ###   ########.fr       */
/*                                                                            */
/* ************************************************************************** */

package com.swingy;

import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SaveHero {
	private static PrintWriter savefile;
    public static final Logger logger = Logger.getLogger(SaveHero.class.getName());

    SaveHero(){
        
    }

    public static void openfile(String name){
        try {
            savefile = new PrintWriter("src/main/java/com/swingy/model/heroes/"+name+".txt");
        } catch (Exception e) {
            logger.log(Level.WARNING, "File conflict error: {}", e.getMessage());
        }
    }

    public static void closelog(){
            savefile.close();
    }

    public static void put(String log){
        savefile.println(log); 
    }
}