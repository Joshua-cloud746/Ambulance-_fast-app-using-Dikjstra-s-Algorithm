function initMap() {
  // Create a new map object centered on Kakamega
  const kakamega = { lat: -0.28119, lng: 34.75217 };
  const map = new google.maps.Map(document.getElementById("map"), {
    center: kakamega,
    zoom: 12,
  });

  // Add a marker at Kakamega
  new google.maps.Marker({
    position: kakamega,
    map: map,
    title: "Kakamega",
  });
}
