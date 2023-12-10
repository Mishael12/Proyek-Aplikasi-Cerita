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

$isi = $_POST['content'];
$id_cerbung = (int)$_POST['id_cerbung'];
$id_author = (int)$_POST['id_author'];

if($isi != "" && $id_cerbung != "" && $id_author != ""){
        $sql = "INSERT INTO paragraphs(isi,cerbungs_id,users_id) VALUES(?,?,?)";
        $stmt = $c->prepare($sql);
        $stmt->bind_param("sii",$isi,$id_cerbung,$id_author);
        $stmt->execute();
        echo json_encode(array(
            'result'=>'OK',
            'message'=>'Insert paragraph success! addparagrap'));
        $stmt->close();
        $c->close();  
}else{
    echo json_encode(array(
        'result'=>'ERROR',
        'message'=>'null reference, please input data! addparagrap'));
    die();
}
?>