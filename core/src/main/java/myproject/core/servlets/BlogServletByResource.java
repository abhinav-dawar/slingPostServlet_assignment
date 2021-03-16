package myproject.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.servlet.Servlet;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

//http://localhost:4502/content/myproject/us/en/jcr:content/root/container/blogs.hello.html

@Component(
        service = {Servlet.class}
)
@SlingServletResourceTypes(
        resourceTypes="myproject/components/blogs",
        methods= "GET",
        extensions="html",
        selectors="hello")
public class BlogServletByResource extends SlingSafeMethodsServlet {

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {

        response.setContentType("text/plain");

        Resource resource=request.getResource();

        Iterable<Resource> resourceChildrens = resource.getChildren();
        HashMap<String, String> hashMap=new HashMap<String,String>();
        String title="";
        String date="";
        for(Resource children : resourceChildrens)
        {
            Node node=children.adaptTo(Node.class);
            try {
                title=node.getProperty("title").getValue().toString();
                date=node.getProperty("date").getValue().toString();
            } catch (RepositoryException e) {
                e.printStackTrace();
            }
            hashMap.put(title,date);
        }
        response.getWriter().println(" Initially hashmap  :");
        for (Map.Entry<String,String> entry : hashMap.entrySet())
        {

            response.getWriter().println("Blog: "+entry.getKey()+"  Date: "+entry.getValue());

        }

        Map<String,String> newmap=sortByValueAscending(hashMap);
        response.getWriter().println(" Sorting in Ascending  :");
        for (Map.Entry<String,String> entry : newmap.entrySet())
        {
            response.getWriter().println("Blog: "+entry.getKey()+"   Date: "+entry.getValue());

        }
    }


    public static HashMap<String, String> sortByValueAscending(HashMap<String,String> hashmap)
    {

        List<Map.Entry<String, String> > list =
                new LinkedList<Map.Entry<String, String> >(hashmap.entrySet());


        Collections.sort(list, new SortByDateAscending());


        HashMap<String, String> temp = new LinkedHashMap<String, String>();
        for (Map.Entry<String, String> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }
}
class SortByDateAscending implements Comparator<Map.Entry<String,String>>
{
    @Override
    public int compare(Map.Entry <String,String> val1, Map.Entry <String,String> val2) {
        Date date1 = null;
        Date date2= null;
        try {
            date1=new SimpleDateFormat("dd-MM-yyyy").parse(val1.getValue());
            date2=new SimpleDateFormat("dd-MM-yyyy").parse(val2.getValue());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date1.compareTo(date2);
    }
}
