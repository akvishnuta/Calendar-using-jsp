/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import backends.Months;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author akhil
 */
@WebServlet(urlPatterns = {"/UpdateCalendar"},asyncSupported=true)
public class UpdateCalendar extends HttpServlet {
    private Month monthReq;
    private String yearReq;
    LocalDate date;
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
           monthReq = Month.valueOf(request.getParameter("month"));
           yearReq = request.getParameter("year");
           final AsyncContext ac = request.startAsync();
           ac.start(new Runnable(){
               public void run(){
               HttpServletRequest request = (HttpServletRequest)ac.getRequest();
               HttpServletResponse response = (HttpServletResponse)ac.getResponse();
               PrintWriter pw=null;
               try{
                   pw=response.getWriter();
                   pw.write(makeMonth());
               }catch(Exception ex){
                   pw.append(ex.getMessage());
               }
               pw.close();
               ac.complete();
               }  
           });
        
    }
    
    public String makeMonth(){
        date =LocalDate.of(Integer.parseInt(yearReq), monthReq, 1);
        Calendar cal = new GregorianCalendar(date.getYear(),
                        date.getMonthValue()-1,
                        date.getDayOfMonth());
        int maxDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        int offset = date.getDayOfWeek().getValue();
        StringBuilder sb= new StringBuilder("");
        //sb.append("Month = "+date.getMonthValue()+" Year = "+date.getYear()+" maxDays ="+maxDays);
        sb.append("<table class='table table-striped'>");
        
        sb.append("<thead><tr>");//heading row begins
        sb.append("<td id='rc0_0' style='color:#d9534f'><b>SUN</b></td>");
        sb.append("<td id='rc0_1'><b>MON</b></td>");
        sb.append("<td id='rc0_2'><b>TUE</b></td>");
        sb.append("<td id='rc0_3'><b>WED</b></td>");
        sb.append("<td id='rc0_4'><b>THU</b></td>");
        sb.append("<td id='rc0_5'><b>FRI</b></td>");
        sb.append("<td id='rc0_6'><b>SAT</b></td>");
        sb.append("</tr><thead><tbody>");//end of heading row
        
        
        for(int day=1,row=1;day<=maxDays;row++){
            sb.append("<tr>");//inner row begin
            int col=0;
            if(row==1){
                for(int k=0;k<offset;k++){
                sb.append("<td id='rc"+row+"_"+col+"'></td>");//insert blank cell
                col++;
                }
            }
            
            for(int j=0;((((j+offset)<7)&&row==1)||((j<7)&&row>1)) &&day<=maxDays;j++){
                if(col==0){
                    if(date.getYear()==LocalDate.now().getYear() && date.getMonthValue()==LocalDate.now().getMonthValue()&&day==LocalDate.now().getDayOfMonth()){
                            sb.append("<td id='rc"+row+"_"+col+"' style='color:#d9534f'><b>").append(day+"").append("</b></td>");
                    }else
                        sb.append("<td id='rc"+row+"_"+col+"' style='color:#d9534f'>").append(day+"").append("</td>");
                }else{
                    if(date.getYear()==LocalDate.now().getYear() && date.getMonthValue()==LocalDate.now().getMonthValue() && day==LocalDate.now().getDayOfMonth()){
                            sb.append("<td id='rc"+row+"_"+col+"'><b>").append(day+"").append("</b></td>");
                    }else
                        sb.append("<td id='rc"+row+"_"+col+"'>").append(day+"").append("</td>");
                 }
                
               
                        
                day++;
                col++;
            }
            sb.append("</tr>");//end of inner row
        }
        
        sb.append("</tbody></table>");//end of table
        return sb.toString();
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
