# consentmanager CMP SDK v2 DemoApp

Two demo applications, on in Java and one in Kotlin, showcasing the integration and functionality of the consentmanager CMP (Consent Management Platform) SDK v2 for Android. This app demonstrates how to implement and interact with various consent management features.

For more information about the Consentmanager CMP SDK, visit [consentmanager.net](https://consentmanager.net) and our [help documentation](https://help.consentmanager.net/books/cmp/chapter/integration-into-your-app).

## Overview

This demo app provides a comprehensive interface to test and validate the CMP SDK's functionality, including:
- Consent status checking
- Purpose and vendor management
- Consent string handling
- UI layer integration

## Architecture

### Key Components

- `CmpManager`: A singleton wrapper around the CMP SDK that handles:
  - SDK initialization and configuration
  - Event handling (errors, UI open/close events)
  - Callback management

- `CmpUIConfig`: Configuration class for customizing the consent UI presentation
- `CmpWebView`: WebView component that handles the consent interface rendering
- `CmpCallbackManager`: Manages all callback events from the SDK

### Features Demonstrated

1. **Basic SDK Setup**
   - SDK initialization with custom configuration
   - Event listener registration
   - Error handling

2. **Consent Management**
   - Check user consent status
   - Get/Set consent string
   - Import external consent strings
   - Accept/Reject all consents

3. **Purpose Management**
   - List all purposes
   - Check specific purpose consent
   - Enable/Disable individual purposes
   - Get enabled/disabled purposes

4. **Vendor Management**
   - List all vendors
   - Check specific vendor consent
   - Enable/Disable individual vendors
   - Get enabled/disabled vendors

5. **UI Integration**
   - Multiple UI strategies (Popup, Dialog, Activity, Fragment)
   - Customizable layouts
   - WebView integration
   - Dark mode support

## Usage

### Initialization

#### Kotlin
```kotlin
// Basic initialization
CmpManager.createInstance(
    context = context,
    codeId = "Your Code-ID goes here (13 characters)",
    serverDomain = "delivery.consentmanager.net",
    appName = "CMPSDKv2DemoApp",
    lang = "en"
)

// With callbacks
CmpManager.createInstance(
    context = context,
    config = CmpConfig.apply {
        id = "Your Code-ID"
        domain = "delivery.consentmanager.net"
        appName = "CMPSDKv2DemoApp"
        language = "en"
    },
    openListener = OnOpenCallback { /* Handle open */ },
    closeListener = OnCloseCallback { /* Handle close */ },
    errorCallback = OnErrorCallback { error, message -> /* Handle error */ }
)
```

#### Java
```java
// Basic initialization
CmpManager.createInstance(
    context,
    "Your Code-ID goes here (13 characters)",
    "delivery.consentmanager.net",
    "CMPSDKv2DemoApp",
    "en"
);

// With callbacks
CmpManager.createInstance(
    context,
    CmpConfig.INSTANCE.apply(config -> {
        config.setId("Your Code-ID");
        config.setDomain("delivery.consentmanager.net");
        config.setAppName("CMPSDKv2DemoApp");
        config.setLanguage("en");
        return Unit.INSTANCE;
    }),
    () -> { /* Handle open */ },
    () -> { /* Handle close */ },
    (error, message) -> { /* Handle error */ },
    null
);
```

### Basic Consent Operations

#### Kotlin
```kotlin
val cmpManager = CmpManager.getInstance(context)

// Check if user has made a choice
val hasConsent = cmpManager.hasConsent()

// Accept all consents
cmpManager.acceptAll { 
    // Handle completion
}

// Reject all consents
cmpManager.rejectAll { 
    // Handle completion
}

// Open consent layer
cmpManager.openConsentLayer(context)

// Check if consent is required
cmpManager.checkConsentIsRequired(
    OnCheckIsConsentRequired { required ->
        // Handle result
    }
)
```

#### Java
```java
CmpManager cmpManager = CmpManager.getInstance(context);

// Check if user has made a choice
boolean hasConsent = cmpManager.hasConsent();

// Accept all consents
cmpManager.acceptAll(() -> {
    // Handle completion
});

// Reject all consents
cmpManager.rejectAll(() -> {
    // Handle completion
});

// Open consent layer
cmpManager.openConsentLayer(context);

// Check if consent is required
cmpManager.checkConsentIsRequired(
    required -> {
        // Handle result
    }
);
```

## UI Configuration

The SDK supports multiple UI presentation strategies:
- Popup Window
- Dialog
- Full Activity
- Fragment

Configure the UI appearance using `CmpUIConfig`:

```kotlin
CmpUIConfig.apply {
    // Configure as fullscreen
    configureFullScreen()
    
    // Or as bottom sheet
    configureHalfScreenBottom(context)
    
    // Or as centered dialog
    configureCenterScreen(context)
    
    // Customize behavior
    isFocusable = true
    isOutsideTouchable = false
    windowAnimations = R.style.WindowAnimation
}
```

## Event Handling

The SDK provides comprehensive callback interfaces:
- `OnOpenCallback`: Consent layer opened
- `OnCloseCallback`: Consent layer closed
- `OnErrorCallback`: Error occurred
- `OnButtonClickedCallback`: User interaction events
- `OnNotOpenedCallback`: Layer not shown
- `CmpGoogleAnalyticsInterface`: Google consent mode updates

## Requirements

- Android SDK 21 or later
- AndroidX compatibility
- WebView support in the target device

## Note

This demo app is designed to showcase the integration patterns and available features of the CMP SDK. In a production environment, you would need to:
- Add proper error handling
- Implement persistence if required
- Add proper loading states
- Handle edge cases
- Add proper logging
- Consider configuration changes
- Handle WebView availability
