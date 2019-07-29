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

public class SaveHero {
    static public PrintWriter savefile;

    SaveHero(){
        
    }

    static public void openfile(String name){
        try {
            savefile = new PrintWriter(name+".txt");
        } catch (Exception e) {
            System.out.println("File conflict error: "+ e.getMessage());
        }
    }

    static public void closelog(){
            savefile.close();
    }

    static public void put(String log){
        savefile.println(log); 
    }
}