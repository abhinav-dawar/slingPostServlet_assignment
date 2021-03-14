package myproject.core.servlets;

import myproject.core.services.Blog;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

@Component(
        service = {Servlet.class},
        property = {"sling.servlet.paths="+"/bin/my-project/blogs"}
)
public class BlogServletByResource extends SlingSafeMethodsServlet {

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        ArrayList<Blog> blogList = new ArrayList<>();

        SimpleDateFormat sdfo = new SimpleDateFormat("yyyy-MM-dd");

        Blog blog1,blog2;

        {
            try {
                blog1 = new Blog("My name", "Abhinav Dawar", sdfo.parse("2018-03-31"));
                blog2 = new Blog("My College", "GTBIT", sdfo.parse("2019/03/03"));
                blogList.add(blog1);
                blogList.add(blog2);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        Collections.sort(blogList, new Comparator<Blog>(){
            public int compare (Blog b1, Blog b2){
                return b1.getDate().compareTo(b2.getDate());
            }
        });
        for(int i=0;i<blogList.size();i++){
            response.getWriter().write(blogList.get(i).getTitle());
            response.getWriter().write(blogList.get(i).getContent());
        }
    }
}
