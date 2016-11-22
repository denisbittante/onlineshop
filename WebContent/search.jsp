<%@ include file="head.jspf"%>

<article>
	<section>
		<form action="search" method="post">
			<fieldset>
				<legend>Suchen</legend>
				<table>
					<tbody>
						<tr>
							<th><label for="search">Suche:</label></th>
							<td><input type="text" name="search" size="40"
								maxlength="40" title="Suchtext" placeholder="Suchtext eingeben">
							</td>
							<td><input type="submit"> <input type="reset">
							</td>
						</tr>
					</tbody>
				</table>
			</fieldset>
		</form>
	</section>
</article>
<c:forEach var="item" items="${items}">
	<c:choose>
		<c:when test="${empty item.sold}">
			<article>
		</c:when>
		<c:otherwise>
			<article class="soldout">
		</c:otherwise>
	</c:choose>
	<section id=${item.id}>
		<form action="buy" method="post">
			<fieldset>
				<legend>ID: ${item.id}</legend>

				<div id="log${item.id}"></div>
				<h2>${item.title}</h2>
				<p>${item.description}</p>
				<p>Preis: ${item.price} Euro</p>
				<c:if test="${not empty customer}">
					<c:choose>
						<c:when test="${empty item.sold}">
							<input type="hidden" name="item_id" value="${item.id}">
							<input type="submit" value="Kaufen" />
							<br>
						</c:when>
						<c:otherwise>
							<b>Verkauft am <fmt:formatDate type="both" dateStyle="long"
									timeStyle="medium" value="${item.sold}" /> an ${item.buyer}
							</b>
						</c:otherwise>
					</c:choose>
				</c:if>
				<div class="rate-result-cnt">
					<div class="rate-bg"
						style="width:<c:out value=" ${20 * item.avgStar}"/>%"></div>
					<div class="rate-stars"></div>
				</div>
				${item.cntReview} Bewertungen


				<aside>
					<p>
						<img width="200px" src="foto?id=${item.id}">
				</aside>
		</form>
		<c:if test="${not empty customer}">

			<aside>

				<div id="review${item.id}">
					<hr width="80%">
					<h3>Eine neue Produktbewertung hinzufügen</h3>

					<p>Gesamtbewertung</p>
					<div class="rate-ex2-cnt">
						<div id="1" class="rate-btn-1 rate-btn"></div>
						<div id="2" class="rate-btn-2 rate-btn"></div>
						<div id="3" class="rate-btn-3 rate-btn"></div>
						<div id="4" class="rate-btn-4 rate-btn"></div>
						<div id="5" class="rate-btn-5 rate-btn"></div>
					</div>

					<p>Kommentar</p>

					<textarea cols="10" id="comment" maxlength="1000" name="comment"
						rows="5" style="width: 80%;"></textarea>
					<br> <a class="submit_review button" id="${item.id}">Review
						abgeben</a>
				</div>
				<a class="reviews-button" id=${item.id} >Bewertungen</a>
				<table id="bewertungen${item.id}" style="display: none;">
				 <tbody>
				 </tbody>
				</table>

			</aside>

		</c:if>
		</fieldset>

	</section>
	</article>
</c:forEach>


<script>
	// rating script
	$(function() {
		$('.rate-btn').hover(function() {
			$('.rate-btn').removeClass('rate-btn-hover');
			var therate = $(this).attr('id');
			for (var i = therate; i >= 0; i--) {
				$('.rate-btn-' + i).addClass('rate-btn-hover');
			}
			;
		});

		$('.rate-btn').click(function() {
			var therate = $(this).attr('id');

			$('.rate-btn').removeClass('selected');
			$('.rate-btn-' + therate).addClass('selected');

			$('.rate-btn').removeClass('rate-btn-active');
			for (var i = therate; i >= 0; i--) {
				$('.rate-btn-' + i).addClass('rate-btn-active');
			}
			;
		});

		$('.submit_review')
				.click(
						function() {
							var product_id = $(this).attr('id');
							var rate = $(
									'#review' + product_id
											+ ' div.rate-ex2-cnt div.selected')
									.attr('id');
							var comment = $(
									'#review' + product_id
											+ ' textarea#comment').val();
							console.log('rate_:' + rate);
							console.log('comment_:' + comment);
							var dataRate = 'product_id=' + product_id
									+ '&rate=' + rate + '&comment=' + comment; //
							$.ajax({
										type : "POST",
										url : "http://localhost:8081/onlineshop-war/review",
										data : dataRate,
										success : function() {
											$('#review' + product_id).hide()
											$('#log' + product_id)
													.html(
															'<div class="info"> Vielen Dank für Ihre Bewertung</div>')
										},
										error : function() {
											$('#review' + product_id).hide()
											$('#log' + product_id)
													.html(
															'<div class="error"> Ach, nee irgendwas ist in die Hose... )</div>')
										}
									});
						});

	});

	$(document).ready(function() {
		
		$(".reviews-button").click(function() {
			var product_id = $(this).attr('id');
	    	  $("#bewertungen"+ product_id).empty();

				var flickerAPI =  "http://localhost:8081/onlineshop-war/comments?productId="+product_id;
					  $.getJSON( flickerAPI, function() {
						  console.log( "success" );
						 
					  } )
					    .done(function( data ) {
					      $.each( data.items, function( i, item ) {
   	  
					    	  var tr = '<tr>' ;
					    	   tr += '<td><img width="50px" src="../onlineshop-war/img/user.png"/></td>';
					    	   
					    	   
					    	   var star = '<div class="rate-result-cnt">';
					    	   star += '<div class="rate-bg"';
					    	   star += 'style="width:'+ 20 * item.stars + '%"></div>';
					    	   star += '<div class="rate-stars"></div>';
					    	   star += '</div>';
					    	   
					    	   tr += '<td>' + star +'<p>' + item.comment  +'</p></td>';
					    	   tr += '</tr><tr>';
					    	   tr += '<td></td>';
					    	   tr += '<td>' + item.time + " - " + item.userId  + '</td>';
					    	   tr += '</tr>';
	      
					    	
					    	   
					      		$( "#bewertungen"+ product_id ).append( tr);
				console.log(tr);
					      });
					    });
				$("#bewertungen"+ product_id).toggle("fast");
			});
		
		doPoll();
	});
	
	
	
	
	
	
	function doPoll(){
		
		var prodids = '' ;
		$('section').each(function(i, obj) {
			console.log(obj);
			var prodid = $(obj).attr('id');
			
			if (prodid ){
					prodids = prodids + "," + prodid;
			}
			
			
		});
			
		console.log(prodids);
		
	       // alert("Helllo ");  // process results here
	        setTimeout(doPoll,5000);
	}

	

</script>


<%@ include file="footer.jspf"%>


