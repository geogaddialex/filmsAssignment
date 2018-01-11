$(document).ready(function() { 
	
	var hideAllResults = function(){
		$( "#addResultsTableContainer" ).hide();
		$( "#searchResultsTableContainer" ).hide();
		$( "#allResultsTableContainer" ).hide();
		$( "#addResults" ).hide();
		$( "#searchResults" ).hide();
		$( "#allResults" ).hide();
	}
	
	$( "#add" ).hide();
	$( "#search" ).hide();
	$( "#all" ).hide();
	
	hideAllResults();
	
	$( "#showAllButton" ).click(function() {
		
		$( this ).addClass( "selected" );
		$( "#searchButton" ).removeClass("selected");
		$( "#addButton" ).removeClass("selected");
		$( "#all" ).show();
		$( "#search" ).hide();
		$( "#add" ).hide();
	});
	
	$( "#searchButton" ).click(function() {
		$( this ).addClass( "selected" );
		$( "#showAllButton" ).removeClass("selected");
		$( "#addButton" ).removeClass("selected");
		$( "#search" ).show();
		$( "#all" ).hide();
		$( "#add" ).hide();
	});
	
	$( "#addButton" ).click(function() {
		$( this ).addClass( "selected" );
		$( "#searchButton" ).removeClass("selected");
		$( "#showAllButton" ).removeClass("selected");
		$( "#add" ).show();
		$( "#search" ).hide();
		$( "#all" ).hide();
	});

	
	
	$('#allInput').submit(function(e) {
		e.preventDefault();
		hideAllResults();
					
		var formData = JSON.parse( JSON.stringify( $(this).serializeArray() ))
		var format = formData[0].value;
		var display = formData[1].value;
		
		var url = "http://localhost:8080/Assignment/Films?action=getAllFilms&format="+format
				
		$.ajax({
			url: url,
			success: function( films ){
				if( display=="raw" ){
					
					$( "#allResults" ).show();
					
					if( format=="xml" ){
						var xml = films.xml ? films.xml : (new XMLSerializer()).serializeToString(films);
						$( "#allResults" ).text( xml );
					}else{
						$( "#allResults" ).html( films );
					}
					
					
				}else{
					
					$( "#allResultsTableContainer" ).show();

					if( format=="json" ){
				
						$('#allResultsTable').dynatable({
						  dataset: {
						    records: $.parseJSON(films)
						  }
						});
					
					}else if( format=="xml" ){
						
						$(films).find('item').each(function(){
						    var id = $(this).find('ID').text();
						    var title = $(this).find('Title').text();
						    var year = $(this).find('Year').text();
						    var director = $(this).find('Director').text();
						    var stars = $(this).find('Stars').text();
						    var review = $(this).find('Review').text();
						    $('<tr></tr>').html('<td>'+id+'</td><td>'+title+'</td><td>'+year+'</td><td>'+director+'</td><td>'+stars+'</td><td>'+review+'</td>').appendTo('#resultsTable');
						});
						
					}else{
						
						$( "#allResults" ).show();
						$( "#allResults" ).html( "Can't parse plain text, select another data format to see formatted results" );
					}	
				}
			}
		});

	})
	
	$('#searchInput').submit(function(e) {
		e.preventDefault();
		
		hideAllResults();
					
		var formData = JSON.parse( JSON.stringify( $(this).serializeArray() ))
		var search = formData[0].value;
		var format = formData[1].value;
		var display = formData[2].value;
		
		var url = "http://localhost:8080/Assignment/Films?action=getFilm&search="+search+"&format="+format
				
		$.ajax({
			url: url,
			success: function( films ){
				if( display=="raw" ){
					
					$( "#searchResults" ).show();
					
					if( format=="xml" ){
						var xml = films.xml ? films.xml : (new XMLSerializer()).serializeToString(films);
						$( "#searchResults" ).text( xml );
					}else{
						$( "#searchResults" ).html( films );
					}
					
					
				}else{
					
					$( "#searchResultsTableContainer" ).show();

					if( format=="json" ){
				
						$('#searchResultsTable').dynatable({
						  dataset: {
						    records: $.parseJSON(films)
						  }
						});
					
					}else if( format=="xml" ){
						
						$(films).find('item').each(function(){
						    var id = $(this).find('ID').text();
						    var title = $(this).find('Title').text();
						    var year = $(this).find('Year').text();
						    var director = $(this).find('Director').text();
						    var stars = $(this).find('Stars').text();
						    var review = $(this).find('Review').text();
						    $('<tr></tr>').html('<td>'+id+'</td><td>'+title+'</td><td>'+year+'</td><td>'+director+'</td><td>'+stars+'</td><td>'+review+'</td>').appendTo('#resultsTable');
						});
						
					}else{
						
						$( "#searchResults" ).show();
						$( "#searchResults" ).html( "Can't parse plain text, select another data format to see formatted results" );
					}	
				}
			}
		});

	})
	
	$('#addInput').submit(function(e) {
		e.preventDefault();
		
		var formData = JSON.parse( JSON.stringify( $(this).serializeArray() ));
		var format = formData[5].value;
		var display = formData[6].value;
				
		var title = formData[0].value;
		var year = formData[1].value;
		var director = formData[2].value;
		var stars = formData[3].value;
		var review = formData[4].value;
		
		if( format=="json" ){
			
			var jsonData = _.object(_.map($(this).serializeArray(), _.values)); //underscore js

			$.ajax({
			    type: "POST",
			    url: "http://localhost:8080/Assignment/Films",
			    contentType: "application/json",
			    data: JSON.stringify( jsonData ),
			    success: function(response) {
			        
			    }
			});
		
		}else if( format=="xml" ){
			
			var xmlData = "<Film><Title>"+title+"</Title><Year>+"+year+"</Year><Director>"+director+"</Director><Stars>"+stars+"</Stars><Review>"+review+"</Review></Film>"
			
			$.ajax({
			    type: "POST",
			    url: "http://localhost:8080/Assignment/Films",
			    contentType: "text/xml",
			    data: xmlData,
			    success: function(response) {
			        
			    }
			});
			
		}else{
			
			$.post($(this).attr("action"), $(this).serialize(), function(response) {
		        console.log(response);
		    });
		}
						

//				if( display=="raw" ){
//					
//					$( "#addResults" ).show();
//					
//					if( format=="xml" ){
//						var xml = films.xml ? films.xml : (new XMLSerializer()).serializeToString(films);
//						$( "#results" ).text( xml );
//					}else{
//						$( "#results" ).html( films );
//					}
//					
//					
//				}else{
//					
//					$( "#addResultsTableContainer" ).show();
//
//					if( format=="json" ){
//				
//						$('#addResultsTable').dynatable({
//						  dataset: {
//						    records: $.parseJSON(films)
//						  }
//						});
//					
//					}else if( format=="xml" ){
//						
//						$(films).find('item').each(function(){
//						    var id = $(this).find('ID').text();
//						    var title = $(this).find('Title').text();
//						    var year = $(this).find('Year').text();
//						    var director = $(this).find('Director').text();
//						    var stars = $(this).find('Stars').text();
//						    var review = $(this).find('Review').text();
//						    $('<tr></tr>').html('<td>'+id+'</td><td>'+title+'</td><td>'+year+'</td><td>'+director+'</td><td>'+stars+'</td><td>'+review+'</td>').appendTo('#resultsTable');
//						});
//						
//					}else{
//						
//						$( "#addResults" ).show();
//						$( "#addResults" ).html( "Can't parse plain text, select another data format to see formatted results" );
//					}	
//				}
//			}
//		});
		
		
		
		
		//

	})

});