<p align="center">
    This project is a mobile application for tracking the International Space Station (ISS), built with Kotlin for Android.  
</p>

## 📌 About The Project  

This project tracks the ISS, displays its current location from the [ISSLocation Api](https://api.wheretheiss.at/v1/satellites/25544) and, with user permission, calculates its distance from the user's position.

To enhance the user experience, the app incorporates Lottie animations and utilizes Dexter for permission handling. 
For astronaut details, the app retrieves data from the 
[People in Space Api](https://corquaid.github.io/international-space-station-APIs/JSON/people-in-space.json) and enriches it with additional information scraped from Wikipedia using Jsoup.

The application efficiently handles images using Picasso and to provide offline access, it employs Room Database, allowing users to store and retrieve astronaut and ISS data even without an internet connection. 

## 🚀 Features  
- **ISS Location** – Tracks the ISS position in real-time using [ISSLocation Api](https://api.wheretheiss.at/v1/satellites/25544)
- **Google Maps Integration** – Displays the ISS location on a map
- **Permission Management** – Ensures proper user location access to calculate the distance between the user's location and the ISS
- **Astronaut Details** – Fetches data from [People in Space Api](https://corquaid.github.io/international-space-station-APIs/JSON/people-in-space.json) and Wikipedia (via web scraping)
- **Animations** – Enhances user experience with smooth animations
- **Offline Storage with Room Database** – Stores astronaut and ISS data for offline viewing

## 🛠 Built With  

### **Languages & Frameworks**  
![Kotlin](https://img.shields.io/badge/Kotlin-%230095D5.svg?style=for-the-badge&logo=kotlin&logoColor=white)  

### **Libraries & Tools**
![Lottie](https://img.shields.io/badge/Lottie-Animations-orange?style=for-the-badge) 
![Gson](https://img.shields.io/badge/Gson-JSON%20Parsing-green?style=for-the-badge) 
![Jsoup](https://img.shields.io/badge/Jsoup-Web%20Scraping-blue?style=for-the-badge) 
![Dexter](https://img.shields.io/badge/Dexter-Permissions-red?style=for-the-badge) 
![Picasso](https://img.shields.io/badge/Picasso-Image%20Loader-pink?style=for-the-badge) 

### **Storage**  
![Room](https://img.shields.io/badge/Room-%23FF6F00.svg?style=for-the-badge&logo=android&logoColor=white)

