function doFirst(){
	document.getElementById('dropZone').ondragover = dragOver;
	document.getElementById('dropZone').ondrop = dropped;
}
function dragOver(e){
	e.preventDefault();
}
function dropped(e){
	e.preventDefault();

	var files = e.dataTransfer.files;
	var readFile = new FileReader();

	for(var key in files){	//files[key]
		readFile.readAsDataURL(files[key]);
		readFile.addEventListener('load',function(){
			//動態新增
			var image = document.createElement('img');
			image.src = readFile.result;

			var dropZone = document.getElementById('dropZone');
			// dropZone.appendChild(image);
			dropZone.insertBefore(image,dropZone.firstChild);
		},false);
	}
}
window.addEventListener('load',doFirst,false);