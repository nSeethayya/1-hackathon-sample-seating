$(document).ready(function() {
	if (window.location.href.indexOf("ticketBooked") != -1) {
		document.getElementById('message').innerHTML = 'Successfully Booked ticket!';
	}
	loadSeats();
	$("#sections").change(function() {
		loadSeats();
	});
	
});
function loadSeats() {
	var selectedSections = $("#sections option:selected").text();
	var selectedSectionsVal = $("#sections option:selected").val();
	console.log("Handler for .change() called." + selectedSections + ":"
			+ selectedSectionsVal);

	if (selectedSectionsVal != null) {
		$.get("/findSeats/" + selectedSectionsVal, {}, function(response) {
			$('#seatContainer').empty();
			var len = response.length;
			var html = '';
			for (var i = 0; i < len; i++) {
				if (response[i].available) {
					html += '<option value="' + response[i].id + '">'
							+ response[i].name + '</option>';
				}
			}
			$('#seatContainer').append(html);
			// $('#seatContainer').html(responseText);
		});
	}

}
