<?php
error_reporting(E_ERROR | E_PARSE);
$c = new mysqli("localhost","root","","cerbungdb");
if($c->connect_errno){
    echo json_encode(array(
        'result' => 'ERROR', 
        'message'=> 'Failed to connect DB, Error Message'.$c->connect_error)
    );
    die();
}
//If blank page uncomment below
//$c->set_charset("UTF8");

$author = (int)$_POST['id_user'];
$title = (string)$_POST['title'];
$desc_short = (string)$_POST['desc_short'];
$url_gambar = (string)$_POST['url_gambar'];

if(isset($author) && isset($title) && isset($desc_short) && isset($url_gambar)){
    $sql = "INSERT INTO cerbungs(genres_id,users_id,title,desc_short,url_gambar,tgl_dibuat,isRestricted) VALUES(?,?,?,?,?,?,?)";
    $stmt = $c->prepare($sql);
    $currentDate = date('d/m/Y');
    $genre = 2;
    $isRestricted = 1;
    $stmt->bind_param("iissssi",$genre,$author,$title,$desc_short,$url_gambar,$currentDate,$isRestricted);
    $stmt->execute();
    echo json_encode(array(
        'result'=>'OK',
        'message'=>'Data added successfully!'));
    $stmt->close();
    $c->close();
}else{
    echo json_encode(array(
        'result'=>'ERROR',
        'message'=>'input all required field!'));
    die();
}
?> 
