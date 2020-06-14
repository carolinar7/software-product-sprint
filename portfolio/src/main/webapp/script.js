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

function getServerQuotes() {
  console.log("Enter function");
  fetch("/data").then(response => response.json()).then((stats) => { 
    console.log("fetch");
    let statsListElement = document.getElementById('quote-container');
    statsListElement.innerHTML = '';
    statsListElement.appendChild(
        createListElement('Quote 1: ' + stats.quote1));
    statsListElement.appendChild(
        createListElement('Quote 2: ' + stats.quote2));
    statsListElement.appendChild(
        createListElement('Quote 3: ' + stats.quote3));
    statsListElement.appendChild(
        createListElement('Quote 4: ' + stats.quote4));
  });
}

function createListElement(text) {
  const liElement = document.createElement('li');
  liElement.innerText = text;
  return liElement;
}