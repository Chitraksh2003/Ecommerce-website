function forgetPass() {
	let email = $("#email").val();
	let code = $("#code").val();
	let password = $("#password").val();
	// Redirect or perform further actions as needed 
	$.ajax({
		url: '/createNewPass', // Controller endpoint
		type: 'POST',
		data: JSON.stringify({ email: email, code: code, password: password }),
		contentType: 'application/json',
		success: function(response) {
			// Handle success response from the controller
			console.error('data is sumbitted:', response);
			if (response == "successful") {
				swal("Congrats!", "Password set successful", "success");
			} else {
				swal("Opps!", response, "error");
			}

			// Redirect or show success message as needed

			window.location.href = "/login";
		},
		error: function(xhr, status, error) {
			// Handle error response
			console.error('Error submitting data:', error);
		}
	});

}




document.addEventListener("DOMContentLoaded", function() {
	document.getElementById("submitForm").addEventListener("click", function(event) {
		event.preventDefault(); // Prevent default behavior

		// Call your function here
		forgetPass();
	});
});


