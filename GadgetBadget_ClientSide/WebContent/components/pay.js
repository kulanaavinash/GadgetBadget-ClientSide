$(document).ready(function() 
{  
	if ($("#alertSuccess").text().trim() == "")  
	{   
		$("#alertSuccess").hide();  
	} 
	$("#alertError").hide(); 
}); 

//SAVE ============================================ 
$(document).on("click", "#btnSave", function(event) 
{  
	// Clear alerts---------------------  
	$("#alertSuccess").text("");  
	$("#alertSuccess").hide();  
	$("#alertError").text("");  
	$("#alertError").hide(); 

	// Form validation-------------------  
	var status = validatePaymentForm();  
	if (status != true)  
	{   
		$("#alertError").text(status);   
		$("#alertError").show();   
		return;  
	} 

	// If valid------------------------  
	var t = ($("#hidPaymentIDSave").val() == "") ? "POST" : "PUT";
	
	$.ajax(
	{
		url : "PaymentApi",
		type : t,
		data : $("#formPayment").serialize(),
		dataType : "text",
		complete : function(response, status)
		{
			onPaymentSaveComplete(response.responseText, status);
		}
	});
}); 

function onPaymentSaveComplete(response, status){
	if(status == "success")
	{
		var resultSet = JSON.parse(response);
			
		if(resultSet.status.trim() == "success")
		{
			$("#alertSuccess").text("Successfully Saved.");
			$("#alertSuccess").show();
					
			$("#divItemsGrid").html(resultSet.data);
	
		}else if(resultSet.status.trim() == "error"){
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	}else if(status == "error"){
		$("#alertError").text("Error While Saving.");
		$("#slertError").show();
	}else{
		$("#alertError").text("Unknown Error while Saving.");
		$("#alertError").show();
	}
	$("#hidPaymentIDSave").val("");
	$("#formPayment")[0].reset();
}

//UPDATE========================================== 
$(document).on("click", ".btnUpdate", function(event) 
		{     
	$("#hidPaymentIDSave").val($(this).closest("tr").find('#hidPaymentIDUpdate').val());     
	$("#PaymentDate").val($(this).closest("tr").find('td:eq(0)').text());    
	$("#PaymentType").val($(this).closest("tr").find('td:eq(1)').text());     
	$("#Amount").val($(this).closest("tr").find('td:eq(2)').text());     
	$("#CardNumber").val($(this).closest("tr").find('td:eq(3)').text()); 
	

});


//Remove Operation
$(document).on("click", ".btnRemove", function(event){
	$.ajax(
	{
		url : "PaymentApi",
		type : "DELETE",
		data : "PaymentID=" + $(this).data("paymentid"),
		dataType : "text",
		complete : function(response, status)
		{
			onPaymentDeletedComplete(response.responseText, status);
		}
	});
});

function onPaymentDeletedComplete(response, status)
{
	if(status == "success")
	{
		var resultSet = JSON.parse(response);
			
		if(resultSet.status.trim() == "success")
		{
			$("#alertSuccess").text("Successfully Deleted.");
			$("#alertSuccess").show();
					
			$("#divItemsGrid").html(resultSet.data);
	
		}else if(resultSet.status.trim() == "error"){
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	}else if(status == "error"){
		$("#alertError").text("Error While Deleting.");
		$("#alertError").show();
	}else{
		$("#alertError").text("Unknown Error While Deleting.");
		$("#alertError").show();
	}
}

//CLIENTMODEL
function validatePaymentForm() {  
	// Date  
	if ($("#PaymentDate").val().trim() == "")  {   
		return "Insert Date.";  
		
	} 
	
	 // Type 
	if ($("#PaymentType").val().trim() == "")  {   
		return "Insert Type.";  
	} 
	
	
	// Amount  
	if ($("#Amount").val().trim() == "")  {   
		return "Insert Amount."; 
		 
	}
	 
	 // is numerical value  
	var tmpMobile = $("#CardNumber").val().trim();  
	if (!$.isNumeric(tmpMobile))  {   
		return "Insert a numerical value for CardNumber.";  
		
	}
	 
	
		

	 
	 return true; 
	 
}
/**
 * 
 */