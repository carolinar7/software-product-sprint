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
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

@WebServlet("/data")
public class DataServlet extends HttpServlet {

  private List<Map<String,String>> comments = new ArrayList<Map<String,String>>();

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("application/json");
    String json = new Gson().toJson(comments);
    response.getWriter().println(json);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Map<String, String> temp = new HashMap<String, String>();
    String firstName = request.getParameter("fname");
    String lastName = request.getParameter("lname");
    String email = request.getParameter("email");
    String message = request.getParameter("textarea");
    temp.put("fname",firstName);
    temp.put("lname",lastName);
    temp.put("email",email);
    temp.put("textarea",message);
    comments.add(temp);

    response.sendRedirect("/pages/contact.html");
  }
}
