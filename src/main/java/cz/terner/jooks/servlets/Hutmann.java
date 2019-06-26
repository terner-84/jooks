/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.terner.jooks.servlets;

import cz.terner.jooks.servlets.data.AbstractJsonServlet;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author hanus
 */
public class Hutmann extends AbstractJsonServlet {

    private static final Map<String, Set<String>> users = new TreeMap<>();
    
    @Override
    public void doJson(HttpServletRequest req, JsonServletResponse res) throws ServletException, IOException {

        String fce = req.getParameter("fce");
        String user = req.getParameter("user").toUpperCase();
        
        if (fce.equals("all")) {
            JSONArray ja = new JSONArray();
            for (Map.Entry<String, Set<String>> entry : users.entrySet()) {
                String userId = entry.getKey();
                Set<String> sety = entry.getValue();
                System.out.println("userId " + userId + " sety " + sety);
                JSONObject jo = new JSONObject();
                jo.put("userId", userId);
                JSONArray setyArray = new JSONArray();
                for (String set : sety) {
                    setyArray.add(set);
                }
                jo.put("sety", setyArray);
                ja.add(jo);
            }
            res.sendJson(ja);
        }
        
        
        if (fce.equals("add")) {
            
            String[] setStringArray = req.getParameter("sety").split(";");
            Set<String> setsUsers = new TreeSet<>(Arrays.asList(setStringArray));

            if (user != null) {
                users.put(user, setsUsers);
                JSONArray ja = new JSONArray();
                JSONObject jo = new JSONObject();
                jo.put("userId", user);
                JSONArray sety = new JSONArray();
                for (String set : setsUsers) {
                    sety.add(set);
                }
                jo.put("sety", sety);
                ja.add(jo);
                res.sendJson(ja);
            }
        }
        
        if (fce.equals("remove")) {
            JSONArray ja = new JSONArray();
            if (users.containsKey(user)) {
                users.remove(user);
                ja.add("User " + user + " successfully deleted. Remains: " + users.size() + " users.");
            } else {
                ja.add("Something wrong: user " + user + " doesn't exist! Remains: " + users.size() + " users.");
            }
            res.sendJson(ja);
        }

        

        /*
        int resCount = 0;
        try {
            resCount = Integer.valueOf(req.getParameter("resCount"));
            JSONArray ja = new JSONArray();
            for (int i = 0; i < resCount; i++) {
                JSONObject jo = new JSONObject();
                jo.put("ans", RandomStringUtils.random(12, true, true));
                ja.add(jo);
            }
            res.sendJson(ja);
        } catch (NumberFormatException e) {
            JSONObject jo = new JSONObject();
            jo.put("error", e.getMessage());
            res.sendJson(jo);
        }*/
    }

}
