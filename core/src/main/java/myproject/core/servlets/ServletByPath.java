package myproject.core.servlets;


import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

@Component(
        service = {Servlet.class},
        property = {"sling.servlet.paths="+"/bin/my-project/abhinav"}
)
public class ServletByPath extends SlingSafeMethodsServlet {
    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        JSONObject jsonobject = new JSONObject();
        try {
            jsonobject.put("Abhinav", 90);
            jsonobject.put("Dawar", 95);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        response.getWriter().write(jsonobject.toString());
    }
}
