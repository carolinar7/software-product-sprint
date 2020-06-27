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
  console.log("Getting quotes from server");
  fetch("/data").then(response => response.json()).then((comments) => {
    let info = document.getElementById("comments");
    info.innerHTML = '';
    comments.forEach(element => {
      info.appendChild(createListElement(
        "Name: " + element.fname
        + " " + element.lname
        + " | Email: " + element.email
        + " | Message: " + element.textarea 
      ));
    });
  });
}

function createListElement(text) {
  const liElement = document.createElement('li');
  liElement.innerText = text;
  return liElement;
}

function createMap() {
  var locations = [
    ["Beijing", 39.9042, 116.4074],
    ["Giza", 30.0131, 31.2089],
    ["Cape Town", -33.9249, 18.4241],
    ["Tokyo", 35.6762, 139.6503],
    ["Rio de Janeiro", 36.3932, 25.4615],
    ["Santorini", 36.3932, 25.4615],
    ["San Francisco", 37.7749, -122.4194],
    ["Dubai", 25.2048, 55.2708],
    ["Paris", 48.8566, 2.3522],
    ["Rome", 41.9028, 12.4964],
    ["London", 51.5074, -0.1278],
    ["Bora Bora", -16.5004, -151.7415],
    ["Madrid", 40.4168, -3.7038]
  ];

  var map = new google.maps.Map(
      document.getElementById("map"),
      {center: new google.maps.LatLng(0, 0), zoom: 1});

  var infowindow = new google.maps.InfoWindow();

  var marker, i;

  for (i = 0; i < locations.length; i++) {  
      marker = new google.maps.Marker({
        position: new google.maps.LatLng(locations[i][1], locations[i][2]),
        map: map
      });
      google.maps.event.addListener(marker, "click", (function(marker, i) {
        return function() {
          infowindow.setContent(locations[i][0]);
          infowindow.open(map, marker);
        }
      })(marker, i));
  }
}