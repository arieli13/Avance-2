<!DOCTYPE html>
<html ng-app = 'imageAnalyzer'>
	<head>
		<meta name = 'viewport' content = 'width =  device-width, initial-scale = 1, user-scalable = no'>
		<meta name = 'theme-color' content = '#000000'>
		<meta charset = 'UTF-8'>
		<title>Image Analyzer</title>
		<link rel = 'stylesheet' href = 'public/stylesheets/bootstrap.min.css'>
		<link rel = 'stylesheet' href = 'public/stylesheets/style.css'>

		<script src = 'public/javascripts/angular.min.js'></script>
	</head>

	<body ng-controller = 'indexController'>
		<div class = 'navbar'>
			<div class = 'container'>
				<h4 class = 'navbar-title'>Image Analyzer</h4>
				<a class = 'navbar-button pull-right' ng-click = 'clearImages()'>Clear images</a>
			</div>
		</div>

		<div class = 'body col-md-12'>
			<div class = 'col-md-3 col-sm-4 col-xs-4 sidebar'>
				<h4 class = 'sidebar-title'>Browse images</h4>
				<hr class = 'divider'/>
				<div class = 'image-upload'>
					<span>Select Images</span>
					<input class = 'upload' type = 'file' accept = '.jpg, .jpeg, .png, .bmp' multiple onchange = 'angular.element (this).scope ().imageUpload (event)'/>
				</div>
				<a class = 'sidebar-button' ng-click = 'uploadExecute ()'>Upload and execute</a>
				<a class = 'sidebar-button' ng-click = 'diceAlgorithm ()'>Dice Algorithm</a>

				Min area: <input type = 'number' min = '0' ng-model = 'minArea' value = "50"/>
				<br>
				<br>
				Etiquetar al segmentar: <input type="checkbox" ng-model="segmentar"
           										ng-true-value="1" ng-false-value="0" checked>
           		<br>
           		<br>
           		<div style = "display:none" id = "errorDiv">
           			Error: {{error}}
           		</div>
           		<br>
           		<br>
           		<div style = "color:green">
           			{{ejecutando}}
           		</div>

				<h4 class = 'sidebar-title'>History</h4>
				<hr class = 'divider'/>
				<div class = 'processes'>
					<a class = 'process-button' ng-repeat = 'process in processes' ng-dblclick = 'downloadProcess($index)' ng-click = 'showProcess ( $index )'>Process {{ $index }}
					<span>Time: {{process.time}} ms</span>
					</a>
				</div>
			</div>

			<div id = 'list' class = 'col-md-offset-3 col-md-9 col-sm-offset-4 col-sm-8 col-xs-offset-4 col-xs-8 content'>
				<div class = 'thumb-container' ng-repeat = 'image in images'>
					<img class = 'thumb' ng-src = '{{ image.src }}'>
  					<span class = 'tooltiptext' ng-dblclick = 'downloadImage($index)'>
  						<span>Name: {{ image.title }}</span>
  						<br/>
  						<span ng-show = 'image.time'>Time: {{ image.time }} ms</span>
  					</span>
				</div>
			</div>
		</div>

		<script src = 'public/angular/imageAnalyzer.js'></script>
		<script src = 'public/angular/controllers/indexController.js'></script>
		<script src = 'public/angular/controllers/download.js'></script>
		<script src = 'jszip/dist/jszip.js'></script>
		<script src = 'FileSaver/FileSaver.js'></script>
	</body>
</html>