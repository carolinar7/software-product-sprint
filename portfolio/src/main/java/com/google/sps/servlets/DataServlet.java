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
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List; // import just the List interface
import java.util.ArrayList; // import just the ArrayList class

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {

    List<String> quotes;

  @Override
  public void init() {
    quotes = new ArrayList<>();
    quotes.add("Live as if you were to die tomorrow. Learn as if you were to live forever. - Mahatma Gandhi");
    quotes.add("That which does not kill us makes us stronger. - Friedrich Nietzsche");
    quotes.add("Be who you are and say what you feel, because those who mind don’t matter and those who matter don’t mind. - Bernard M. Baruch");
    quotes.add("We must not allow other people’s limited perceptions to define us. - Virginia Satir");
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String quote = quotes.get((int) (Math.random()*quotes.size()));

    response.setContentType("text/html;");
    response.getWriter().println(quote);
  }
}
