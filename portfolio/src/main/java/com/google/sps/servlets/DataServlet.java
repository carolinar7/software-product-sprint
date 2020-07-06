// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;

import java.io.IOException;
import com.google.gson.Gson;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

@WebServlet("/data")
public class DataServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    List<Map<String,String>> comments = new ArrayList<Map<String,String>>();
    Query query = new Query("Comments");

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);

    for (Entity entity : results.asIterable()) {
      String firstName = (String) entity.getProperty("fname");
      String lastName = (String) entity.getProperty("lname");
      String email = (String) entity.getProperty("email");
      String message = (String) entity.getProperty("textarea");

      Map<String, String> comment = new HashMap<String, String>();
      comment.put("fname",firstName);
      comment.put("lname",lastName);
      comment.put("email",email);
      comment.put("textarea",message);
      comments.add(comment);
    }
    response.setContentType("application/json");
    String json = new Gson().toJson(comments);
    response.getWriter().println(json);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String firstName = request.getParameter("fname");
    String lastName = request.getParameter("lname");
    String email = request.getParameter("email");
    String message = request.getParameter("textarea");

    Entity taskEntity = new Entity("Comments");
    taskEntity.setProperty("fname",firstName);
    taskEntity.setProperty("lname",lastName);
    taskEntity.setProperty("email",email);
    taskEntity.setProperty("textarea",message);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(taskEntity);

    response.sendRedirect("/pages/contact.html");
  }
}
