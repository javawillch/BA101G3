function doFirst(){
	document.getElementById('myFile').onchange = fileChange;
}
function fileChange() {
	var files = document.getElementById('myFile').files;	//files是陣列
	var message = '';

	for (var i = 0; i < files.length; i++) {
		message += 'File Name: '+files[i].name+'\n';
		message += 'File Type: '+files[i].type+'\n';
		message += 'File Size: '+files[i].size+' byte(s)\n';
		message += 'Last modified: '+files[i].lastModifiedDate+'\n';
		message += '======================\n';

		document.getElementById('fileInfo').innerHTML = message;
	}
}
window.addEventListener('load',doFirst,false);