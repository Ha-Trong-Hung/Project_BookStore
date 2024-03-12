/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.admin;

import dal.CategoryDAO;
import dal.ProductDAO;
import entity.Product;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;

@MultipartConfig
/**
 *
 * @author hatro
 */
public class AddProductController extends HttpServlet {

    ProductDAO productDao = new ProductDAO();
    CategoryDAO categoryDao = new CategoryDAO();

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
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; UTF-8");
        //get sesstion
        HttpSession session = request.getSession();
        //get action
        String action = request.getParameter("action") == null
                ? ""
                : request.getParameter("action");
        switch (action) {
            case "add":
                addProduct(request);
                break;
            case "delete":
                deleteProduct(request);
                break;
            case "edit":
                editProduct(request);
            default:
        }
        response.sendRedirect("dashboard");
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

    private void addProduct(HttpServletRequest request) {

        // get name
        String name = request.getParameter("name");
        //get price
        float price = Float.parseFloat(request.getParameter("price"));
        // get quantity
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        // get description
        String description = request.getParameter("description");
        // get categoryID
        int categoryId = Integer.parseInt(request.getParameter("category"));
        // image
        String image = request.getParameter("image");
        Product product = new Product(name, image, quantity, price, description, categoryId);
        productDao.addProductInDB(product);
    }

    private void deleteProduct(HttpServletRequest request) {
        // get về  id cần xóa
        int id = Integer.parseInt(request.getParameter("id"));
        productDao.deleteProductById(id);
    }

    private void editProduct(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        // get name
        String name = request.getParameter("name");
        //get price
        float price = Float.parseFloat(request.getParameter("price"));
        // get quantity
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        // get description
        String description = request.getParameter("description");
        // get categoryID
        int categoryId = Integer.parseInt(request.getParameter("category"));
        // image
        String image = request.getParameter("image");
        Product product = new  Product(id, name, image, quantity, price, description, categoryId);
        productDao.editProduct(product);
    }

}
