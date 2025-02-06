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

