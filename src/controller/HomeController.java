package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Created by Team USA on 10/31/14.
 * 
 * Servlet implementation class HomeController
 * This class controls any interactions dealing with the Home page. 
 * 
 */
@WebServlet(name="home", urlPatterns={"/"})
public class HomeController extends HttpServlet {
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO check if the user is already logged in and redirect them to their dashboard page if they are logged in
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
}
