(function($) {
	$('.spinner .btn:first-of-type').on(
			'click',
			function(e) {
				const input = $(this).parent().parent().find("input");

				input.val(parseInt($(input).val(), 10) + 1);

				let grantTotal = "";
				grantTotal = $(this).parent().parent().parent().parent()
						.parent().find(".grantTotal").text();
				console.log(grantTotal);

				
				
				let precios = "";
				precios = $(this).parent().parent().parent().parent().find(
						".precioProducto");
				const arrayPrecios=[];
				$.each(precios, function() {
					
					
					arrayPrecios.push($(this).text());
					
				});
				console.log(arrayPrecios);
				
				
				let cantidades = "";
				cantidades = $(this).parent().parent().parent().parent().find(
						".cantidadProducto");
				const arrayCantidades=[];
				$.each(cantidades, function() {
					arrayCantidades.push($(this).val());
					
				});
				
				console.log(arrayCantidades);

				let total=0;
				for(let i=0;i<arrayPrecios.length;i++){
					console.log("multiplicar "+arrayPrecios[i]+"*"+arrayCantidades[i]+" = "+(arrayPrecios[i]*arrayCantidades[i]));
					let multiplicacion=arrayPrecios[i]*arrayCantidades[i];
					total+=multiplicacion;
				}
				
				console.log("total : "+total);
				
				 $(this).parent().parent().parent().parent().parent().find(".grantTotal").text(total);
				
				
			});

	$('.spinner .btn:last-of-type').on('click', function() {
		const input = $(this).parent().parent().find("input");
		$(input).val(parseInt($(input).val(), 10) - 1);
		
		
		
		let grantTotal = "";
		grantTotal = $(this).parent().parent().parent().parent()
				.parent().find(".grantTotal").text();
		console.log(grantTotal);

		
		
		let precios = "";
		precios = $(this).parent().parent().parent().parent().find(
				".precioProducto");
		const arrayPrecios=[];
		$.each(precios, function() {
			
			
			arrayPrecios.push($(this).text());
			
		});
		console.log(arrayPrecios);
		
		
		let cantidades = "";
		cantidades = $(this).parent().parent().parent().parent().find(
				".cantidadProducto");
		const arrayCantidades=[];
		$.each(cantidades, function() {
			arrayCantidades.push($(this).val());
			
		});
		
		console.log(arrayCantidades);

		let total=0;
		for(let i=0;i<arrayPrecios.length;i++){
			console.log("multiplicaraasdasdasdas "+arrayPrecios[i]+"*"+arrayCantidades[i]+" = "+(arrayPrecios[i]*arrayCantidades[i]));
			let multiplicacion=arrayPrecios[i]*arrayCantidades[i];
			total=multiplicacion+total;
		}
		
		
		console.log("total : "+total);
		
		 $(this).parent().parent().parent().parent().parent().find(".grantTotal").text(total);
		
		
		
		
		
	});

	

})(jQuery);