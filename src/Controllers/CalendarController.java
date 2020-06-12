/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Data.SQLContext;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Attendee;
import models.Calendar;
import models.Event;

/**
 *
 * @author moham
 */
public class CalendarController extends HttpServlet {

    static SQLContext s1;
    String check;

    public void init() {

        ServletContext application = getServletContext();

        Calendar calendar = new Calendar();
        application.setAttribute("calendar", calendar);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        s1 = new SQLContext();

        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();

        ServletContext application = getServletContext();
        String UserName = request.getParameter("name");
        session.setAttribute("myUser", UserName);

        RequestDispatcher req = request.getRequestDispatcher("Calendar.jsp");
        req.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            ArrayList<HashMap<String, String>> CaleanderList = s1.GetCalendar();
            int time = Integer.parseInt(request.getParameter("Time"));
            int day = Integer.parseInt(request.getParameter("Days"));

            String title = request.getParameter("title");

            if (title.isEmpty()) {
                out.println("<font color=\"red\"> Please enter title</font>");

            }

            String[] pAttendees = request.getParameterValues("attend");

            if (pAttendees.length == 0) {
                out.println("<font color=\"red\"> Please enter at least one attendee</font>");
            } else {
                boolean flag = true;
                for (int a = 0; a < CaleanderList.size(); a++) {
                    if (Integer.parseInt(CaleanderList.get(a).get("di")) == day) {
                        flag = false;
                    }

                }

                boolean flag2 = false;
                for (int a = 0; a < CaleanderList.size(); a++) {
                    if (Integer.parseInt(CaleanderList.get(a).get("ti")) == time && Integer.parseInt(CaleanderList.get(a).get("di")) == day) {
                        flag2 = true;
                    }

                }

                if (flag || flag2) {
                    s1.SetCalenader(Integer.toString(time), Integer.toString(day), title);

                    ServletContext application = getServletContext();
                    //  HttpSession session = request.getSession();

                    Calendar calendar = (Calendar) application.getAttribute("calendar");

                    Event e = calendar.getEvent(time, day);
                    if (null == e) {
                        calendar.scheduleEvent(time, day, title);
                        //  e.setTitle(title);
                        Attendee a;
                        for (String pAttendee : pAttendees) {
                            a = new Attendee();
                            a.setUserName(pAttendee);
                            calendar.events[time][day].addAttendee(a);
                        }
                    }
                } else {

                    RequestDispatcher r2 = request.getRequestDispatcher("ErrorMessage.jsp");
                    r2.forward(request, response);

                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(CalendarController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(CalendarController.class.getName()).log(Level.SEVERE, null, ex);
        }
        RequestDispatcher r = request.getRequestDispatcher("Calendar.jsp");
        r.forward(request, response);

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
