package chatapp.controller;

import chatapp.model.Status;
import chatapp.model.User;
import chatapp.model.UserService;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Set;

@org.springframework.stereotype.Controller
public class Controller {
    @Autowired
    @Qualifier("userService")
    private UserService service;

    @PostMapping("/login")
    public String login(Model model, @RequestParam("username") String name, @RequestParam("password") String password, HttpServletRequest request){
        HttpSession session = request.getSession();
        User u = service.getLoggedIn(name,password);
        if (u != null){
            u.setStatus(Status.ONLINE);
        }
        session.setAttribute("user",service.getLoggedIn(name,password));
        model.addAttribute("statuses",Status.values());
        return "home";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        return "index";
    }

    @PostMapping("/change/{status}")
    public void changeStatus(HttpServletResponse response, HttpServletRequest request, @PathVariable("status") String status){
        HttpSession session = request.getSession();
        try {
            User u = (User) session.getAttribute("user");
            Status s = Status.OTHER;
            try{
                s = Status.valueOf(status);
            }catch (IllegalArgumentException e){
                s.setName(status);
            }
            u.setStatus(s);
            response.getWriter().write(u.getName() + " - " + s.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/friends")
    public void getFriendList(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        User u = (User) session.getAttribute("user");
        if (u != null) {
            response.setContentType("application/json");
            try {
                response.getWriter().write(String.valueOf(toJson(u.getFriendlist())));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @PostMapping("/addfriend/{name}")
    public void addFriend(HttpServletRequest request, @PathVariable("name") String name, HttpServletResponse response){
        System.out.println("idk");
        User u = (User) request.getSession().getAttribute("user");
        if (u != null) {
            User friend = service.getUser(name);
            u.addFriend(friend);
            System.out.println(u.getFriendlist().size());
        }
    }

    @GetMapping("/")
    public String getHome(Model model){
        return "index";
    }

    private Object toJson(Set<User> list){
        JsonObject json = new JsonObject();
        for (User u:list){
            JsonObject user = new JsonObject();
            user.addProperty("name",u.getName());
            user.addProperty("statusname",u.getStatus().getName());
            json.add(u.getName(),user);
        }
        return json.toString();
        //        Gson json = new Gson();
//        String string = json.toJson(list);
//        System.out.println(string);
//        return string;
    }
}
