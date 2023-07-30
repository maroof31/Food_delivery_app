# Restaurant Food Delivery App - Readme

Welcome to the Restaurant Food Delivery App, an open-source project built with Kotlin, XML for the user interface, MVVM architecture, Firebase Firestore as the database, and Razorpay as the payment gateway.

## Table of Contents

1. [Introduction](#introduction)
2. [Features](#features)
3. [Installation](#installation)
4. [Configuration](#configuration)
5. [Usage](#usage)
6. [Contributing](#contributing)
7. [License](#license)

## Introduction

The Restaurant Food Delivery App is a mobile application that allows users to browse through a restaurant's menu, place orders, and have food delivered to their location. The app is built using Kotlin, making it easy to understand, modify, and extend. The MVVM (Model-View-ViewModel) architecture is employed to separate concerns and provide a clean and maintainable codebase. Firebase Firestore is used as the backend database, offering real-time updates and reliable data storage. To facilitate secure payments, the app integrates Razorpay as the payment gateway.

## Features

The app comes with several features to provide a seamless and delightful food ordering experience:

- User registration and login.

-  ## Onboarding Screens
 <img src="https://github.com/maroof31/Food_delivery_app/blob/master/images/splash.PNG" alt="Splash Screen" width="400">
 
  
  
<p float="left">
<img src="https://github.com/maroof31/Food_delivery_app/blob/master/images/login.PNG" alt="Splash Screen" width="400">
<img src="https://github.com/maroof31/Food_delivery_app/blob/master/images/register.PNG" alt="Login Screen" width="400">
</p>


- Browse through a restaurant's menu with detailed item descriptions and prices.

- ## Home and Profile Screen

<p float="left">
   <img src="https://github.com/maroof31/Food_delivery_app/blob/master/images/home.PNG" alt="Home Screen" width="400">
<img src="https://github.com/maroof31/Food_delivery_app/blob/master/images/profile.PNG" alt="profile Screen" width="400">
 
</p>
- Add items to the cart and manage the cart contents.
<p float="left">
<img src="https://github.com/maroof31/Food_delivery_app/blob/master/images/allproducts.PNG" alt="all products Screen" width="350">

<img src="https://github.com/maroof31/Food_delivery_app/blob/master/images/productDetail.PNG" alt="product detail Screen" width="350">

<img src="https://github.com/maroof31/Food_delivery_app/blob/master/images/cart.PNG" alt="cart Screen" width="350">

</p>
- Place orders and track order status.
## Checkout and Payment Screen

<p float="left">
<img src="https://github.com/maroof31/Food_delivery_app/blob/master/images/checkout.PNG" alt="all products Screen" width="350">
<img src="https://github.com/maroof31/Food_delivery_app/blob/master/images/payment.PNG" alt="all products Screen" width="350">
</p>


- Real-time updates for order status and delivery.
- Integration with Razorpay for secure and efficient payments.
- User profile management and order history.
- Integration with Firebase Firestore for storing and retrieving data.
- Easy-to-use and intuitive user interface with smooth navigation.

## Installation

To install and run the app, follow these steps:

1. Clone the repository to your local machine:

```bash
git clone https://github.com/your-username/restaurant-food-delivery-app.git
```

2. Open the project in Android Studio.

3. Connect your Android device or use an emulator with Android API level XX or above.

4. Build and run the app.

## Configuration

Before running the app, you need to configure Firebase Firestore and Razorpay with your credentials:

### Firebase Firestore Configuration

1. Go to the [Firebase Console](https://console.firebase.google.com/) and create a new project (if you haven't already).

2. Enable Firestore in the project from the Firebase Console.

3. Create a new collection to store the restaurant menu items and order details.

4. Obtain your Firebase project's `google-services.json` file and place it in the `app` directory of the project.

### Razorpay Configuration

1. Sign up for a Razorpay account at [https://razorpay.com/](https://razorpay.com/) if you don't have one.

2. Retrieve your Razorpay API Key and API Secret from your Razorpay Dashboard.

3. In the app's code, navigate to the payment section and replace the placeholder with your Razorpay API Key.

## Usage

The app is designed to be user-friendly and self-explanatory. Users can create an account or log in with existing credentials. After logging in, they can browse the restaurant's menu, add items to their cart, and proceed to checkout. During checkout, the user can choose to pay via Razorpay.

You can explore the app's codebase to understand the MVVM architecture, how Firebase Firestore is integrated for database functionality, and how Razorpay is integrated for payment processing.

## Contributing

We welcome contributions to improve the Restaurant Food Delivery App. If you find any bugs, have suggestions for enhancements, or want to add new features, feel free to create issues and submit pull requests. Please follow the existing code style and guidelines when contributing.
