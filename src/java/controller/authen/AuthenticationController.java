/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.authen;

import dal.AccountDAO;
import entity.Account;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author hatro
 */
public class AuthenticationController extends HttpServlet {

    AccountDAO accountDAO = new AccountDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action") == null
                ? ""
                : request.getParameter("action");

        // set URL can chuyen den
        String url;
        switch (action) {
            case "login":
                url = "view/Authen/Login.jsp";
                break;
            case "log-out":
                url = loginOutDoGet(request, response);
                break;
            case "sign-up":
                url = "view/Authen/SignUp.jsp";
                break;
            default:
                url = "home";
        }
        request.getRequestDispatcher(url).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action") == null
                ? ""
                : request.getParameter("action");
        // dựa theo action sử lý request
        String url;
        switch (action) {
            case "login":
                url = loginDoPost(request, response);
                break;
            case "sign-up":
                url = SignUpDoPost(request, response);
                break;
            default:
                url = "home";
        }
        request.getRequestDispatcher(url).forward(request, response);
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

    private String loginDoPost(HttpServletRequest request, HttpServletResponse response) {
        String url = null;
        HttpSession session = request.getSession();
        // get về thông tin người dùng nhậps
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        session.setAttribute("usernameLogin", username);
        session.setAttribute("passwordLogin", password);
        // kiểm tra thông tin có tồn tại trong db hay không
        Account accout = new Account(username, password);
        Account accountFoundByNamePass = accountDAO.findByUserNameAndPass(accout);
        if (accountFoundByNamePass != null) {
            session.setAttribute("account", accountFoundByNamePass);
            url = "home";
        } else {
            session.setAttribute("error2", "Username or Password incorrect !!");
            url = "view/Authen/Login.jsp";
        }
        // có => chuyển về trang home
        return url;
    }

    private String loginOutDoGet(HttpServletRequest request, HttpServletResponse response) {
        String url;
        HttpSession session = request.getSession();
        session.removeAttribute("account");
        return "home";
    }

    private String SignUpDoPost(HttpServletRequest request, HttpServletResponse response) {
        String url;
        HttpSession session = request.getSession();
        // get về thông tin người dùng nhập
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String address = request.getParameter("address");
        
        session.setAttribute("usernameSignUp", username);
        session.setAttribute("addressSignUp", address);
        // kiểm tra xem người dùng đã nhập đúng format chưa
        if (!username.matches("[a-zA-Z0-9]+@([a-zA-Z]+.){1,2}[a-zA-Z]+")) {
            request.setAttribute("error", "Please Enter UserName matches with fomat A@gmail.com");
            url = "view/Authen/SignUp.jsp";
            
        } else {
            // kiểm tra xem username đã tồn tại trong db chưa
            Account account = new Account(username, password, address);
            // nếu rồi thì hiển thị lỗi quay trở lại trang sign up
            Account accountFoundByNamePass = accountDAO.checkIsExit(account);
            if (accountFoundByNamePass != null) {
                request.setAttribute("error1", "Account is exit !!!");
                url = "view/Authen/SignUp.jsp";
              
            } else {
                // nếu chưa thì chuyển dến trang login, inser vào trong db
                accountDAO.insertAccount(account);
                url = "view/Authen/Login.jsp";
            }
        }
        return url;
    }

}
