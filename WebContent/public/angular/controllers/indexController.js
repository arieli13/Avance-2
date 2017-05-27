app.controller ('indexController', ['$scope', '$http', function ($scope, $http) {
	$scope.images = [];
	$scope.processes = [];
	
	$scope.minArea = "";
	$scope.segmentar = "0";
	$scope.yaProcesadas = false;
	$scope.error = "";
	$scope.ejecutando = "";
	
	$scope.printError = function(){
		var div = document.getElementById('errorDiv');
		div.setAttribute("style", "display:block;color:red;");
	}
	
	$scope.dontPrintError = function(){
		var div = document.getElementById('errorDiv');
		div.setAttribute("style", "display:none");
	}

	$scope.clearImages = function () {
		$scope.yaProcesadas = false;
		$scope.images = [];
	}
	
	$scope.isInteger = function(numero){
		if(numero === undefined || numero === ""){
			return false;
		}
		for(var i = 0; i<numero.length;i++){
			if( numero[i] > 57 || numero[i]<48 ){
				return false;
			}
		}
		return true;
	}

	$scope.diceAlgorithm = function () {
		if ($scope.images.length == 0){
			$scope.error = 'You have to select at least one image to process';
			$scope.printError();
			$scope.yaProcesadas = false;
			return;
		}
		var couple = [];
		if($scope.images.length %2 !=0){
			$scope.error = "Verify that there are two images with the same name. Please reupload the images.";
			$scope.printError();
			$scope.images = [];
			$scope.yaProcesadas = false;
			return;
		}
		for (var i = 0; i < $scope.images.length; i++) {
			for (var j = 1; j < $scope.images.length; j++) {
				if ($scope.images[i].title === $scope.images[j].title && i!=j) {
					couple.push ({img1: $scope.images[i].src, img2: $scope.images[j].src, title: $scope.images[j].title});
					$scope.images.splice (i, 1);
					$scope.images.splice (j-1, 1);
					i = -1;
					break;
				}
			}		
		}

		if ($scope.images.length > 0) {
			$scope.error = "Verify that there are two images with the same name. Please reupload the images.";
			$scope.printError();
			$scope.images = [];
			$scope.yaProcesadas = false;
			return;
		}

	    var string = "";
	    var tamannio = 0;
	    var tamannioImages = couple.length;
	    var metricas = ""; //Este string se va formando con lo que retorna cada proceso de dice
	    var erroresEnProceso = "";
	    $scope.ejecutando = "Executed: 0%";
	    for(var i = 0; i<couple.length;i++){
	    	string=couple[i].img1+"<"+couple[i].img2+"<"+couple[i].title;
	    	$http({
	            method : 'POST',
	            url : 'Conexion',
	            contentType: 'application/json',
	            data : '1'+string,
	        }).success(function(data) {
	        	if(data[0] == "-"){
	        		var s = data.split("<");
	        		erroresEnProceso+="ERROR: "+s[1]+"\n";
	        	}else{
	        		metricas+= data+"\n";
	        	}
	        	tamannio++;
	        	$scope.ejecutando = "Executing: "+(tamannio*100/tamannioImages)+"%";
	        	if(tamannio == tamannioImages){
	        		$scope.ejecutando = "Executed: 100%";
	        		if(erroresEnProceso!=""){
	        			alert(erroresEnProceso);
	        		}
	        		if(metricas!=""){
	        			download(metricas, "Dice.csv");
	        		}
	        	}
	        })
	        
	    }
	    $scope.yaProcesadas = false;
	    $scope.dontPrintError();
	}
	
	$scope.imageUpload = function(event){
		var files = event.target.files; //FileList object

		for (var i = 0; i < files.length; i++) {
			var currentFile = files[i];
			var reader = new FileReader();

			reader.onload = (function (theFile){
				return function (e){
					$scope.$apply (function () {
						$scope.images.push ({src: e.target.result, title: theFile.name, time: ""});
					});
				};
			}) (currentFile);

			reader.readAsDataURL (currentFile);
		}
	}
	
	
	$scope.uploadExecute = function () {
		if($scope.yaProcesadas){
			$scope.error = "Some of the selected images are already processed. Please select new images";
			$scope.printError();
			return;
		}
		if ($scope.images.length == 0){
			$scope.error = 'You have to select at least one image to process';
			$scope.printError();
			return;
		}else {
		    if(!$scope.isInteger($scope.minArea)){
		    	$scope.error = "Please enter a valid number for the min area";
		    	$scope.printError();
		    	return;
		    }
		    
		    if(!confirm('Execute Process?')){
		    	$scope.dontPrintError();
		    	return;
		    }
		    
		    var string = "";
		    var arreglo = [];
		    var tamannio = 0;
		    var tamannioImages = $scope.images.length;
		    var inicio = performance.now ();
		    var tiempo = 0;
		    $scope.ejecutando = "Executed: 0%";
		    var erroresEnProceso = ""; //Va copiando los errores que tira cada imagen.
		    for(var i = 0; i<$scope.images.length;i++){
		    	string=$scope.images[i]['src']+"<"+$scope.images[i]['title'];
		    	$http({
		            method : 'POST',
		            url : 'Conexion',
		            contentType: 'application/json',
		            data : '0'+string+"<"+$scope.minArea+"<"+$scope.segmentar,
		        }).success(function(data) {
		        	var s = data.split("<");
		        	if(s[0] == "-"){
		        		erroresEnProceso+="ERROR: "+s[1]+"\n";
		        	}else{
		        		arreglo.push ({src: s[0], csv: s[1], title: s[2], time: s[3]});
		        	}
		        	tamannio++;
		        	$scope.ejecutando = "Executing: "+(tamannio*100/tamannioImages)+"%";
		        	
		        	if(tamannio == tamannioImages){
		        		$scope.ejecutando = "Executed: 100%";
		        		var final = performance.now ();
		        		tiempo = final - inicio;
		        		$scope.processes.push({imgs: arreglo, time: tiempo});
		    		    $scope.images = [];
		        		if(erroresEnProceso!=""){
		        			alert(erroresEnProceso);
		        		}
		        	}
		        })
		        
		    }
		}
		$scope.dontPrintError();
	}

	$scope.showProcess = function (index) {
		
		$scope.images = [];
		$scope.yaProcesadas = true;
		for(var i = 0; i<$scope.processes[index].imgs.length;i++){
			$scope.images.push( {src: $scope.processes[index].imgs[i]['src'], csv: $scope.processes[index].imgs[i]['csv'], title: $scope.processes[index].imgs[i]['title'], time: $scope.processes[index].imgs[i]['time']} );
		}
		
	}
	$scope.downloadProcess = function(index){
		var zip = new JSZip();
		for(var i = 0; i<$scope.processes[index]['imgs'].length;i++){
			zip.file($scope.processes[index]['imgs'][i]['title'], $scope.processes[index]['imgs'][i]['src'].split(",")[1], {base64: true});
			var nombre = $scope.processes[index]['imgs'][i]['title'].split(".")[0]+".csv";
			zip.file(nombre, $scope.processes[index]['imgs'][i]['csv']);
			
		}
		zip.generateAsync({type:"blob"})
		.then(function(content) {
		    // see FileSaver.js
		    saveAs(content, "Process "+index+".zip");
		});
	}
	
	$scope.downloadImage = function(index){
		download($scope.images[index].src, $scope.images[index].title);
		if($scope.images[index].csv != undefined){
			download($scope.images[index].csv, $scope.images[index].title.split(".")[0]+".csv");
		}
			
	}

}]);