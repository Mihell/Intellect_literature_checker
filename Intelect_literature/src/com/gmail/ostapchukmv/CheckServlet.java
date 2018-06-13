package com.gmail.ostapchukmv;


import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.ArrayList;

@MultipartConfig
public class CheckServlet extends javax.servlet.http.HttpServlet {

    private int checkRatings(int[] firstRating, int[] secondRating){
        int result = 0;
        double[] sum = new double[firstRating.length];
        for(int i = 0; i < firstRating.length; i++){
            double max = Math.max(firstRating[i], secondRating[i]);
            double dif = Math.abs(firstRating[i] - secondRating[i]);
            if(dif == 0){
                sum[i] = 100;
            } else{
                sum[i] = 100 - (dif / max * 100);
            }
            result += sum[i];
        }
        return result / sum.length;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        int maxRating = 0;
        Part newBookPart = request.getPart("newBook");
        String newBookName = UploadServlet.getSubmittedFileName(newBookPart);
        int[] newRating = UploadServlet.getRating(newBookPart);
        ArrayList<int[]> ratings = UploadServlet.getRatings();
        for (int[] r : ratings
                ) {
            if(checkRatings(newRating, r) > maxRating){
                maxRating = checkRatings(newRating, r);
            }
        }
        request.setAttribute("rating", maxRating);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/result.jsp");
        dispatcher.forward(request, response);

    }

}
