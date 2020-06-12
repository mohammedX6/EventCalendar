/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Filters;

import Data.SQLContext;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 *
 * @author moham
 */
public class Auth implements Filter {

    SQLContext s1;
    int count = 0;

    public void init() throws SQLException {

        s1 = new SQLContext();

        count++;

        if (s1.tableExist("CALENDAR") == false) {

            s1.Create_Table_Calendar();

        }

    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        try {

            s1 = new SQLContext();
            if (s1.tableExist("ATTENDEES") == false) {

                s1.Create_Table_Attendees();
            }
            if (s1.tableExist("CALENDAR") == false) {

                s1.Create_Table_Calendar();

            }

            PrintWriter out = response.getWriter();

            String UserName = request.getParameter("name");

            String check = s1.CheckUser(UserName);
            if (!UserName.equals("")) {
                if (check.equals(UserName)) {

                    chain.doFilter(request, response);

                } else {
                    out.print("<font color=\"red\">  You are not authorized .........!</font>");
                }
            } else {
                out.print("<font color=\"red\">  please enter your name  .........!</font>");
            }

        } catch (SQLException ex) {
            Logger.getLogger(Auth.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Return the filter configuration object for this filter.
     */
}
