/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.terner.jooks.servlets.data;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 *
 * @author DZCPKAH
 */
public abstract class AbstractJsonServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonServletResponse jr = new JsonServletResponse(resp);
        doJson(req, jr);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        JsonServletResponse jr = new JsonServletResponse(res);
        doJson(req, jr);
    }

    public abstract void doJson(HttpServletRequest req, JsonServletResponse res) throws ServletException, IOException;

    public static class JsonServletResponse {

        private final HttpServletResponse res;

        public JsonServletResponse(HttpServletResponse res) {
            this.res = res;
            res.setContentType("application/json;charset=UTF-8");
        }

        public void setHeader(String name, String value) {
            res.setHeader(name, value);
        }

        public void sendError(int sc) throws IOException {
            res.sendError(sc);
        }

        public void sendError(int sc, String msg) throws IOException {
            res.sendError(sc, msg);
        }

        public void sendJson(JSONObject o) throws IOException {
            PrintWriter pw = res.getWriter();
            pw.print(o.toString());
            pw.flush();
        }

        public void sendJson(JSONArray o) throws IOException {
            PrintWriter pw = res.getWriter();
            pw.print(o.toString());
            pw.flush();
        }

        public void sendJsonError(String error) throws IOException {
            JSONObject o = new JSONObject();
            o.put("error", error);
            sendJson(o);
        }

    }
}
