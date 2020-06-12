


<%@page import="java.util.logging.Logger"%>
<%@page import="models.Calendar"%>
<%@page import="models.Event"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Data.SQLContext"%>
<%@page contentType="text/html" pageEncoding="windows-1256"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
        <title>JSP Page</title>
    </head>
    <body>
        <h4>No to events at same day</h4>


        <form action="CalendarController"  method="POST">

            <%!
                static SQLContext s1 = new SQLContext();

            %>

            <%

                String str = (String) session.getAttribute("myUser"); %>
            <p >Hi <% out.print(str); %> </p>

            Event Title:<input type='text' name='title'>




            <br>
            <br>

            List of attendees: 
            <br>
            <p>Note: Hold down Ctrl to select multiple options</p>

            <%
                try {
                    ArrayList<HashMap<String, String>> userList = s1.GetUsers();

            %>
            <select name="attend" multiple>
                <%                    for (int i = 0; i < userList.size(); i++) {
                %>

                <option value="l"><% out.print(userList.get(i).get("user"));
                    }

                    %></option>

                <%} catch (Exception e) {


                %>


                <%  }%>

            </select>


            <br>
            <br>

            Day of the week <select name="Days">
                <option>Days</option>
                <option value="1">Sunday</option>
                <option value="2">Monday</option>
                <option value="3">Tuesday</option>
                <option value="4">Wednesday</option>
                <option value="5">Thursday</option>
                <option value="6">Friday</option>
                <option value="7">Saturday</option>
            </select>

            <br>
            <br>
            Time range:  <select name="Time">
                <option value="">Begin Time - End Time</option>
                <option value="1">8:00-9:00</option>
                <option value="2">9:00-10:00</option>
                <option value="3">10:00-11:00</option>
                <option value="4">11:00-12:00</option>
                <option value="5">13:00-14:00</option>
                <option value="6">14:00-15:00</option>
                <option value="7">15:00-16:00</option>
                <option value="8">16:00-17:00</option>
            </select>


            <br>
            <br>
            <button type="submit">Enter</button>

            <%
                try {%>
            <jsp:useBean id = "calendar" type ="models.Calendar" scope="application"/>
            <%
                ArrayList<HashMap<String, String>> CaleanderList = s1.GetCalendar();
                ArrayList<Integer> CheckColum = new ArrayList();

                String htmlTable = null;
                String[] rowsName = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
                String[] ColumnsName = {"8:00-9:00", "9:00-10:00", "10:00-11:00", "11:00-12:00", "13:00-14:00", "14:00-15:00", "15:00-16:00", "16:00-17:00"};
                if (null != calendar) {
                    htmlTable = "<table width=\"1000\" border=\"1\" bordercolor=\"black\">";

                    for (int i = 0; i < 9; i++) {
                        if (i == 0) {
                            htmlTable += "<tr>";
                        } else if (i > 0) {
                            htmlTable += "<tr><th>" + ColumnsName[i - 1] + "</th>";
                        }
                        for (int j = 0; j < 8; j++) {

                            if (j == 0 && i == 0) {
                                htmlTable += "<td height=\"30\" value=\"" + "\">" + "</td>";

                            } else if (j > 0 && i == 0) {
                                htmlTable += "<td height=\"30\" value=\"" + "\">" + rowsName[j - 1] + "</td>";
                            } else if (j > 0 && i > 0) {

                                for (int w = 0; w < CaleanderList.size(); w++) {
                                    CheckColum.add(Integer.parseInt(CaleanderList.get(w).get("di")));
                                }

                                String title = " ";
                                for (int a = 0; a < CaleanderList.size(); a++) {
                                    if (Integer.parseInt(CaleanderList.get(a).get("ti")) == i && Integer.parseInt(CaleanderList.get(a).get("di")) == j) {

                                        title += CaleanderList.get(a).get("ei") + " , ";
                                    }
                                }

                                htmlTable += "<td height=\"30\" value=\"" + "\">" + title + "</td>";
                            }

                        }
                        htmlTable += "</tr>";

                    }
                    htmlTable += "</table>";
                }
            %>
            <p> <% out.print(htmlTable);
                } catch (Exception e) {
                }


                %></p>






            <br><br>

        </form>

    </body>
</html>
