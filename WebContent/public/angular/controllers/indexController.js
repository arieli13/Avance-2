app.controller ('indexController', ['$scope', '$http', function ($scope, $http) {
	$scope.images = [];
	$scope.processes = [];

		
	$scope.imageUpload = function(event){
		$scope.images = [];
		var files = event.target.files; //FileList object

		for (var i = 0; i < files.length; i++) {
			var x = files[i];
			var reader = new FileReader();
			reader.onload = $scope.imageIsUpload;

			reader.readAsDataURL(x);
		}
	}
	
	$scope.imageIsUpload = function(e){
		$scope.$apply(function() {
			$scope.images.push({src: e.target.result, title:$scope.images.length+".png"});
			//console.log(x.name);
		});
	}
	
	$scope.uploadExecute = function () {
		if ($scope.images.length == 0){
			alert ('You have to select at least one image to process');
			return;
		}else {
		    
		    var string = "";
		    var arreglo = [];
		    var tamannio = 0;
		    var tamannioImages = $scope.images.length;
		    for(var i = 0; i<$scope.images.length;i++){
		    	string=$scope.images[i]['src']+"<"+$scope.images[i]['title'];
		    	$http({
		            method : 'POST',
		            url : 'Conexion',
		            contentType: 'application/json',
		            data : string,
		        }).success(function(data) {
		        	var s = data.split("<");
		        	arreglo.push ({src: s[0], csv: s[1], title: s[2]});
		        	tamannio++;
		        	if(tamannio == tamannioImages){
		        		alert("Done!");
		        	}
		        })
		        
		    }
		    $scope.processes.push(arreglo);
		    $scope.images = [];
		}
		
	}

	$scope.showProcess = function (index) {
		$scope.images = $scope.processes[index];
	}
	$scope.downloadProcess = function(index){
		var zip = new JSZip();
		for(var i = 0; i<$scope.processes[index].length;i++){
			//console.log($scope.processes[index][i]);
			zip.file($scope.processes[index][i]['title'], $scope.processes[index][i]['src'].split(",")[1], {base64: true});
			var nombre = $scope.processes[index][i]['title'].split(".")[0]+".csv";
			zip.file(nombre, $scope.processes[index][i]['csv']);
			
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