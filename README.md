# HypeWear: Android E-commerce App for Sustainable Fashion

<div align="center">
  <img src="https://img.shields.io/badge/Kotlin-Android-blue?logo=kotlin"/>
  <img src="https://img.shields.io/badge/Jetpack%20Compose-UI-success?logo=android"/>
  <img src="https://img.shields.io/badge/Firebase-Backend-orange?logo=firebase"/>
  <img src="https://img.shields.io/badge/Status-Prototype-yellow"/>
</div>

<br>

**HypeWear** is an Android application designed for *ecological and sustainable online fashion shopping*. Built as part of a Bachelor's thesis at the University of Piraeus, it showcases modern Android development techniques while promoting sustainable consumer habits and ethical fashion.

---

## 📱 Overview

**HypeWear** is a mobile e-commerce platform that connects eco-conscious consumers with designers and sellers of sustainable apparel. Its main goal is to provide a seamless, enjoyable shopping experience while fostering environmental awareness and responsible choices.

---

## 🎯 Key Features

### 👤 For Consumers:
- **Sign Up & Authentication** – Secure login with email verification via Firebase.
- **Product Browsing & Search** – Browse clothing by category, search by keywords, and use powerful filters (category, fabric, sustainability score, etc.).
- **Favorites** – Save products for quick access later.
- **Cart Management** – Add/remove items, choose size/quantity, and view your shopping cart.
- **Order History** – View detailed past orders.
- **User Profile** – Edit profile, change profile photo, switch between light/dark theme, log out.

### 🛒 For Sellers:
- **Authentication** – Register and manage a seller profile.
- **Product Management** – Add, edit, or delete apparel from your store via a guided 5-step form.
- **Performance Analytics** – View sales stats (total earnings, best-selling items).
- **Seller Profile** – Manage shop info, change profile/logo, adjust theme.

### 🌱 Sustainable Focus:
- **Eco-Metrics** – Products display sustainability metrics (CO₂, water footprint, fabric/packaging eco-ratings).
- **Transparency** – Emphasis on ethical production, material traceability, and environmental impact.

---

## 🏗️ Tech Stack

- **Kotlin** – Main programming language.
- **Jetpack Compose** – Declarative UI toolkit for building modern interfaces.
- **Firebase** – Real-time database, authentication, and cloud storage.
- **Cloudinary SDK** – Image storage and delivery.
- **Koin** – Lightweight Dependency Injection.

#### **Architecture:**
- **Clean Architecture** (Data, Domain, Presentation Layers)
- **MVI Pattern** (Model-View-Intent)

---

## 🗂️ Database Structure (Firestore)

- **Users:** email, displayName, photoUrl, userType (consumer/seller), favorites, cart, etc.
- **Apparels:** description, brand, color, imageUrl, price, category, tags, fabric, ecoMetrics, ecoScore, stockPerSize, trigrams (for search).
- **Brands:** name, contactEmail, logoUrl.
- **Orders:** brandIds, items, total, userId.

---

## 🚀 Getting Started

> **Note:** HypeWear is currently in a prototype state and not ready for production use.

### **Prerequisites**
- Android Studio (latest stable)
- Kotlin plugin
- Emulator or Android device (API 26+ recommended)

### **Installation**
1. **Clone the Repository**
    ```bash
    git clone https://github.com/Stefanos-Ben/HypeWear.git
    cd HypeWear
    ```
2. **Open in Android Studio**  
   Import the project as an existing Android project.
3. **Firebase Setup**  
   - Create a Firebase project.
   - Download `google-services.json` and add it to the `app/` directory.
   - Set up Firestore and Authentication in Firebase Console.
4. **Cloudinary (optional)**  
   - Add your Cloudinary credentials if you wish to enable image uploads.

5. **Build & Run**  
   Run on your preferred emulator/device.

---

## ⚠️ Known Issues & Limitations

- **Payments Integration** is missing due to subscription/API limitations (e.g., PayPal, Stripe).
- **Image Uploads** may be restricted by free-tier service limits (Firebase/Cloudinary).
- **Some features are incomplete or require further backend development** (e.g., advanced seller analytics, social/community features).
- This is a *thesis demo/prototype*, not a full production-ready app.

---

## 📈 Roadmap & Future Work

Planned features and improvements include:

- Personalized recommendation system (potentially ML-powered).
- In-app payment integration & order tracking.
- Community features (ratings, product reviews, forums).
- Enhanced sustainability metrics, user feedback on eco-claims.
- Cross-platform expansion (Kotlin Multiplatform – iOS/Web).
- AI assistant for outfit suggestions and eco-advice.

---

## 💡 Project Motivation

HypeWear was created in response to the global movement for sustainable fashion and ethical consumerism. The goal is to harness technology for environmental good, making sustainable choices accessible and appealing to everyday users.

---

## 🏛️ Academic Info

- **Institution:** University of Piraeus, School of Information & Communication Technologies
- **Department:** Digital Systems
- **Thesis Author:** Stefanos Mpiniamin  
  [stefanos.mpeniamin@gmail.com](mailto:stefanos.mpeniamin@gmail.com)
- **Supervisor:** Vasiliki Koufi
- **Thesis Date:** June 2025

---

## 📚 References & Further Reading

- [Jetpack Compose Documentation](https://developer.android.com/jetpack/compose)
- [Kotlin Programming Language](https://kotlinlang.org/)
- [Firebase Documentation](https://firebase.google.com/docs)
- [Koin Documentation](https://insert-koin.io/)
- [Material Design Guidelines](https://material.io/design)
- Ellen MacArthur Foundation: [Circular Economy & Sustainable Fashion](https://www.ellenmacarthurfoundation.org/publications)
- Sustainable Apparel Coalition: [The Higg Index](https://apparelcoalition.org/the-higg-index/)

---

## 🤝 Contribution

This is a thesis project, but contributions, suggestions, and feedback are welcome! Please open an issue or pull request if you would like to help improve HypeWear.

---

## 📝 License

[MIT License](LICENSE) (or specify your own if preferred)

---

