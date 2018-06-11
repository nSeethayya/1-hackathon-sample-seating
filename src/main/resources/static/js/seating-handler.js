/*function myFunction() {
	alert("myFunction for .change() called.");

}*/

$(document).ready(
		function() {
			$("#sections")
					.change(
							function() {
								var selectedSections = $(
										"#sections option:selected").text();
								var selectedSectionsVal = $(
										"#sections option:selected").val();
								console.log("Handler for .change() called."
										+ selectedSections + ":"
										+ selectedSectionsVal);

								$.get("/findSeats/" + selectedSectionsVal, {

								}, function(response) {
									$('#seatContainer').empty();
									var len = response.length;
									var html = '';
									for (var i = 0; i < len; i++) {
										if (response[i].available) {
											html += '<option value="'
													+ response[i].id + '">'
													+ response[i].name
													+ '</option>';
										}
									}
									$('#seatContainer').append(html);
									// $('#seatContainer').html(responseText);
								});

							});
			/*
			 * $("#bookTickets") .click( function() { var selectedSectionsVal = $(
			 * "#sections option:selected").val(); var selectedSeatVal = $(
			 * "#seats option:selected").val(); console.log("Handler for
			 * bookTickets:" + selectedSectionsVal + ":" + selectedSeatVal); var
			 * data = "{'sectionId':" + selectedSectionsVal + ",'seatId':" +
			 * selectedSeatVal + "}"; $.ajax({ type : "POST", url :
			 * "/bookTickets", data: JSON.stringify(data), dataType: 'json',
			 * contentType : "application/json", async : false, success :
			 * function(data) { $("#response").html("Success"); }, error:
			 * function (e) { } });
			 * 
			 * });
			 */
		});
