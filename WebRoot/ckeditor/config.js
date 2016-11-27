/*
Copyright (c) 2003-2012, CKSource - Frederico Knabben. All rights reserved.
For licensing, see LICENSE.html or http://ckeditor.com/license
*/

CKEDITOR.editorConfig =  function (config) {
    config.filebrowserBrowseUrl =  '/webApplication/ckfinder/ckfinder.html' ;//education2表示项目根文件夹
    config.filebrowserImageBrowseUrl =  '/webApplication/ckfinder/ckfinder.html?type=Images' ;
    config.filebrowserFlashBrowseUrl =  '/webApplication/ckfinder/ckfinder.html?type=Flash' ;
    config.filebrowserUploadUrl =  '/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Files' ;
    config.filebrowserImageUploadUrl =  '/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Images' ;
    config.filebrowserFlashUploadUrl =  '/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Flash' ;
    config.filebrowserWindowWidth = '1000';
    config.filebrowserWindowHeight = '700';
    config.language =  "zh-cn" ;
}; 
