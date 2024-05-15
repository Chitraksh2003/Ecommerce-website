function paymentStart() {
	console.log("payment started");
	let amount = $("#total_amount").val();
	let formData = $('#billingForm').val();
	console.log(amount);
	console.log(formData);
	$.ajax({
		url: '/createOrder',
		data: JSON.stringify({ amount: amount }),
		contentType: 'application/json',
		type: 'POST',
		success: function(response_json) {
			console.log('AJAX call successful:', response_json);
			var response = JSON.parse(response_json);
			// Handle response if needed
			var options = {
				key: 'rzp_test_aVbXqGyi1NNH4l', // Your Razorpay API Key
				amount: response.amount, // Order amount in paise
				currency: 'INR',
				name: 'Cakes & Co',
				description: 'Payment for Order ID: ' + response.id,
				order_id: response.id, // The order ID obtained from your backend
				handler: function(response) {
					// Handle successful payment callback
					console.log(response);

					$.ajax({
						url: '/paymentDone',
						data: JSON.stringify({
							razorpay_order_id: response.razorpay_order_id, razorpay_payment_id: response.razorpay_payment_id, razorpay_signature: response.razorpay_signature
						}),
						contentType: 'application/json',
						type: 'POST',
						success: function(response) {
						},
						error: function(xhr, status, error) {
							console.error("Error:", error);
						}
					});
					// Redirect or perform further actions as needed window.location.href = "/payNow";
					swal("Congrats!", "Payment successful", "success");
					$.ajax({
						url: '/payNow', // Controller endpoint
						type: 'POST',
						data: formData,
						contentType: 'application/x-www-form-urlencoded',
						success: function(response) {
							// Handle success response from the controller
							console.log('Data submitted successfully:', response);
							// Redirect or show success message as needed
						},
						error: function(xhr, status, error) {
							// Handle error response
							
							console.error('Error submitting data:', error);
						}
					});
					
				},
				prefill: {
					name: "",
					email: "",
					contact: ""
				},
				notes: {
					address: 'Cakes & Co Office'
				},
				theme: {
					color: '#3399cc'
				}
			};
			var rzp = new Razorpay(options);
			rzp.on('payment.failed', function(response) {
				console.log(response.error.code);
				console.log(response.error.description);
				console.log(response.error.source);
				console.log(response.error.step);
				console.log(response.error.reason);
				console.log(response.error.metadata.order_id);
				console.log(response.error.metadata.payment_id);

				swal("Opps!", "payment failed!", "error");
			});
			rzp.open();


		},
		error: function(xhr, status, error) {
			console.error('AJAX call error:', status, error);
			// Handle error if needed
		}
	});
}



document.addEventListener("DOMContentLoaded", function() {
	document.getElementById("submitForm").addEventListener("click", function(event) {
		event.preventDefault(); // Prevent default behavior

		// Call your function here
		paymentStart();
	});
});


