# scientific-calculator
Below is an example of a simple scientific calculator built with Spring Boot on the backend and HTML/CSS/JavaScript on the frontend. In this example, the Spring Boot application exposes a REST endpoint (`/api/calculate`) that accepts a calculation request (via JSON) and returns the result. The frontend (an HTML page styled with CSS) presents input fields, a dropdown for selecting the operation, and a button that calls the API using JavaScript’s Fetch API.

You can use this example as a starting point and extend it as needed.

---

## Project Structure

A sample Maven project structure might look like this:

```
scientific-calculator/
├── src
│   └── main
│       ├── java
│       │   └── com
│       │       └── example
│       │           └── calculator
│       │               ├── CalculatorApplication.java
│       │               ├── controller
│       │               │    └── CalculatorController.java
│       │               └── model
│       │                    ├── CalculationRequest.java
│       │                    └── CalculationResponse.java
│       └── resources
│           ├── static
│           │    ├── css
│           │    │    └── style.css
│           │    └── js
│           │         └── script.js
│           └── templates
│                └── index.html
└── pom.xml
```

---

## 1. Maven `pom.xml`

Make sure your `pom.xml` includes Spring Boot dependencies. For example:

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" 
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
                             http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <groupId>com.example</groupId>
  <artifactId>scientific-calculator</artifactId>
  <version>0.0.1-SNAPSHOT</version>
  <packaging>jar</packaging>

  <parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.0.0</version>
  </parent>

  <dependencies>
    <!-- Spring Boot Web Starter -->
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <!-- (Optional) If you want to use Thymeleaf instead of static HTML -->
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-thymeleaf</artifactId>
    </dependency>
  </dependencies>

  <build>
    <plugins>
      <!-- Spring Boot Maven Plugin -->
      <plugin>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-maven-plugin</artifactId>
      </plugin>
    </plugins>
  </build>
</project>
```

---

## 2. Spring Boot Application Code

### **CalculatorApplication.java**

```java
package com.example.calculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CalculatorApplication {
  public static void main(String[] args) {
    SpringApplication.run(CalculatorApplication.class, args);
  }
}
```

### **CalculationRequest.java**

```java
package com.example.calculator.model;

public class CalculationRequest {
    private String operation;
    private double operand1;
    private double operand2; // For unary operations, this may be ignored

    public CalculationRequest() {}

    public String getOperation() {
        return operation;
    }
    public void setOperation(String operation) {
        this.operation = operation;
    }
    public double getOperand1() {
        return operand1;
    }
    public void setOperand1(double operand1) {
        this.operand1 = operand1;
    }
    public double getOperand2() {
        return operand2;
    }
    public void setOperand2(double operand2) {
        this.operand2 = operand2;
    }
}
```

### **CalculationResponse.java**

```java
package com.example.calculator.model;

public class CalculationResponse {
    private double result;
    private String error;

    public CalculationResponse() {}

    public CalculationResponse(double result) {
        this.result = result;
        this.error = null;
    }
    
    public CalculationResponse(double result, String error) {
        this.result = result;
        this.error = error;
    }

    public double getResult() {
        return result;
    }
    public void setResult(double result) {
        this.result = result;
    }
    public String getError() {
        return error;
    }
    public void setError(String error) {
        this.error = error;
    }
}
```

### **CalculatorController.java**

```java
package com.example.calculator.controller;

import com.example.calculator.model.CalculationRequest;
import com.example.calculator.model.CalculationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CalculatorController {

    @PostMapping("/calculate")
    public ResponseEntity<CalculationResponse> calculate(@RequestBody CalculationRequest request) {
        double result = 0;
        String op = request.getOperation();
        double operand1 = request.getOperand1();
        double operand2 = request.getOperand2();

        try {
            switch (op) {
                case "add":
                    result = operand1 + operand2;
                    break;
                case "sub":
                    result = operand1 - operand2;
                    break;
                case "mul":
                    result = operand1 * operand2;
                    break;
                case "div":
                    if (operand2 == 0) {
                        throw new ArithmeticException("Division by zero");
                    }
                    result = operand1 / operand2;
                    break;
                case "sin":
                    // Expect operand1 to be in degrees; convert to radians
                    result = Math.sin(Math.toRadians(operand1));
                    break;
                case "cos":
                    result = Math.cos(Math.toRadians(operand1));
                    break;
                case "tan":
                    result = Math.tan(Math.toRadians(operand1));
                    break;
                case "log":
                    if (operand1 <= 0) {
                        throw new ArithmeticException("Logarithm of non-positive number");
                    }
                    result = Math.log10(operand1);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid operation");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                                 .body(new CalculationResponse(Double.NaN, e.getMessage()));
        }
        return ResponseEntity.ok(new CalculationResponse(result));
    }
}
```

---

## 3. Frontend Code

Place the following files under the `src/main/resources` folder as indicated.

### **index.html** (located in `src/main/resources/templates` or `src/main/resources/static`)

If you place this file in the `templates` folder, you may use Thymeleaf. For simplicity, you can also place it in `static` so that it is served directly. Here’s a sample HTML page:

```html
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Scientific Calculator</title>
  <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
  <div class="calculator-container">
    <h1>Scientific Calculator</h1>
    <div class="calculator">
      <!-- Input fields -->
      <input type="number" id="operand1" placeholder="Operand 1">
      <input type="number" id="operand2" placeholder="Operand 2">
      
      <!-- Operation selection -->
      <select id="operation">
        <option value="add">Addition (+)</option>
        <option value="sub">Subtraction (-)</option>
        <option value="mul">Multiplication (×)</option>
        <option value="div">Division (÷)</option>
        <option value="sin">Sine (sin)</option>
        <option value="cos">Cosine (cos)</option>
        <option value="tan">Tangent (tan)</option>
        <option value="log">Logarithm (log₁₀)</option>
      </select>
      
      <!-- Button to trigger calculation -->
      <button id="calculateBtn">Calculate</button>
      
      <!-- Display result -->
      <div id="result">Result: </div>
    </div>
  </div>
  <script src="/js/script.js"></script>
</body>
</html>
```

### **style.css** (located in `src/main/resources/static/css`)

```css
body {
  background: #f0f0f0;
  font-family: Arial, sans-serif;
  margin: 0;
  padding: 0;
}

.calculator-container {
  width: 400px;
  margin: 50px auto;
  background: #fff;
  padding: 20px 30px;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.calculator-container h1 {
  text-align: center;
  margin-bottom: 20px;
}

.calculator input, 
.calculator select, 
.calculator button {
  width: 100%;
  padding: 10px;
  margin: 10px 0;
  font-size: 16px;
  box-sizing: border-box;
}

.calculator button {
  background: #007bff;
  color: #fff;
  border: none;
  cursor: pointer;
  border-radius: 4px;
}

.calculator button:hover {
  background: #0056b3;
}

#result {
  font-size: 18px;
  text-align: center;
  margin-top: 20px;
}
```

### **script.js** (located in `src/main/resources/static/js`)

This JavaScript adds a click event listener to the “Calculate” button and makes a POST request to the backend.

```javascript
document.getElementById("calculateBtn").addEventListener("click", function(){
    // Retrieve values from input fields
    let op1 = parseFloat(document.getElementById("operand1").value);
    let op2 = parseFloat(document.getElementById("operand2").value);
    let operation = document.getElementById("operation").value;
    
    // For unary operations (sin, cos, tan, log), the second operand is not needed.
    // We set it to 0 (or you could modify the API to ignore it).
    if(operation === "sin" || operation === "cos" || operation === "tan" || operation === "log"){
      op2 = 0;
    }
    
    // Build the JSON request payload
    const payload = {
      operation: operation,
      operand1: op1,
      operand2: op2
    };

    // Call the REST API using Fetch
    fetch('/api/calculate', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(payload)
    })
    .then(response => response.json())
    .then(data => {
       if(data.error) {
         document.getElementById("result").innerText = "Error: " + data.error;
       } else {
         document.getElementById("result").innerText = "Result: " + data.result;
       }
    })
    .catch(error => {
       console.error('Error:', error);
       document.getElementById("result").innerText = "Error: " + error;
    });
});
```

---

## Running the Application

1. **Build the Project:**  
   In the project’s root directory, run:
   ```bash
   mvn clean install
   ```

2. **Run the Spring Boot Application:**  
   You can run the application with:
   ```bash
   mvn spring-boot:run
   ```
   or run the generated jar file:
   ```bash
   java -jar target/scientific-calculator-0.0.1-SNAPSHOT.jar
   ```

3. **Access the Calculator:**  
   Open your web browser and navigate to [http://localhost:8080/](http://localhost:8080/) (if your `index.html` is served as the default page).

Now you have a working scientific calculator that supports basic arithmetic as well as some scientific operations (sine, cosine, tangent, logarithm). Feel free to extend the functionality (for example, by adding more functions, error handling, or a more advanced user interface) as needed.
