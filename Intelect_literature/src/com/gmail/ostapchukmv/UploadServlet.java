package com.gmail.ostapchukmv;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@MultipartConfig
public class UploadServlet extends javax.servlet.http.HttpServlet {


    private static final String[] fantasy = initialize(new File("D:/IdeaProjects/Intelect_literature/web/resources/fantasy.txt"));
    private static final String[] romance = initialize(new File("D:/IdeaProjects/Intelect_literature/web/resources/romance.txt"));
    private static final String[] detective = initialize(new File("D:/IdeaProjects/Intelect_literature/web/resources/detective.txt"));
    private static final String[] fantastic = initialize(new File("D:/IdeaProjects/Intelect_literature/web/resources/fantastic.txt"));
    private static final String[][] genres = {fantasy, romance, detective, fantastic};
    private static ArrayList<int[]> ratings = new ArrayList<>();
    private List<String> books = new ArrayList<>();

    public static ArrayList<int[]> getRatings() {
        return ratings;
    }

    public static String[] initialize(File file){
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"))) {
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString().split(",");
    }

    public static String getSubmittedFileName(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String fileName = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return fileName.substring(fileName.lastIndexOf('/') + 1).substring(fileName.lastIndexOf('\\') + 1);
            }
        }
        return null;
    }

    public static int[] getRating(Part bookPart) throws IOException {
        int[] rating = new int[genres.length];
        try (BufferedReader br = new BufferedReader(new InputStreamReader(bookPart.getInputStream(), "UTF-8"))) {
            String line = "";
            while ((line = br.readLine()) != null) {
                for (String word : line.toLowerCase().split(" ")
                        ) {
                    if (word.contains(".") || word.contains(",") || word.contains("!")) {
                        word = word.substring(0, word.length() - 1);
                    }
                    for (int i = 0; i < genres.length; i++) {
                        for (String w : genres[i]
                                ) {
                            if (word.equals(w)) {
                                rating[i]++;
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        int length = (int) bookPart.getSize();
        for (int i = 0; i < rating.length; i++) {
            rating[i] = 10000000 / length * rating[i];
        }


        return rating;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        Part bookPart = request.getPart("book");
        String bookName = getSubmittedFileName(bookPart);
        String bookListName = bookName.substring(0, bookName.lastIndexOf("."));
        books.add(bookListName);
        request.setAttribute("books", books);
        ratings.add(getRating(bookPart));
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getParameter("clear") != null){
            books.clear();
            ratings.clear();
        }
        request.setAttribute("books", books);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
        dispatcher.forward(request, response);
    }
}
