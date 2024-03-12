/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.homepage;

import constant.CommonConst;
import dal.CategoryDAO;
import dal.ProductDAO;
import entity.Category;
import entity.PageControl;
import entity.Product;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 *
 * @author hatro
 */
public class HomeController extends HttpServlet {

    ProductDAO productDAO = new ProductDAO();
    CategoryDAO categoryDAO = new CategoryDAO();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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
        PageControl pageControl = new PageControl();
        ArrayList<Product> listProduct = findProductDoGet(request, pageControl);
        // get list category dao
        ArrayList<Category> listCategory = categoryDAO.findAll();

        // set listProduct, listCategory to session
        HttpSession session = request.getSession();
        session.setAttribute("listProduct", listProduct);
        session.setAttribute("listCategory", listCategory);
        request.setAttribute("pageControl", pageControl);
        request.getRequestDispatcher("view/Homepage/home.jsp").forward(request, response);
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

    private ArrayList<Product> findProductDoGet(HttpServletRequest request, PageControl pageControl) {
        //get về pagae
        String pageRaw = request.getParameter("page");
        int page;
        try {
            page = Integer.parseInt(pageRaw);
            if (page <= 0) {
                page = 1;
            }
        } catch (NumberFormatException e) {
            page = 1;
        }

        // get về search
        String actionSearch = request.getParameter("search") == null
                ? "default"
                : request.getParameter("search");
        ArrayList<Product> listProduct;
        // get request URL
        String requestURL = request.getRequestURL().toString();
        int totalRecord= 0;
        switch (actionSearch) {
            case "category":
                 
                String categoryIdSearch = request.getParameter("categoryId");
                totalRecord = productDAO.findTotalRecordByCategory(categoryIdSearch);
                listProduct = productDAO.findByCategory(categoryIdSearch, page);
                pageControl.setUrlPattern(requestURL + "?search=category&categoryId="
                        + categoryIdSearch + "&");
                break;

            case "searchByName":
                 
                String keyword = request.getParameter("keyword");
                totalRecord = productDAO.findTotalRecordByName(keyword);
                listProduct = productDAO.findByName(keyword, page);
                pageControl.setUrlPattern(requestURL + "?search=searchByName&keyword="
                        + keyword + "&");
                break;

            default:
                listProduct = productDAO.findAll();
                totalRecord = productDAO.findTotalRecord();
                listProduct = productDAO.findByPage(page);
                pageControl.setUrlPattern(requestURL + "?");
        }
        
        
        // total recod
        // total page
        int totalPage = (totalRecord % CommonConst.RECORD_PER_PAGE) == 0
                ? (totalRecord / CommonConst.RECORD_PER_PAGE)
                : (totalRecord / CommonConst.RECORD_PER_PAGE) + 1;
        //set total record, total page, page người dùng muốn chuyển tới là bao nhiêu vào pageControl
        pageControl.setPage(page);
        pageControl.setTotalPage(totalPage);
        pageControl.setTotalRecord(totalRecord);
        return listProduct;
    }

}
