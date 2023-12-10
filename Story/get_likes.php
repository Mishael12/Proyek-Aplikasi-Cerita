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
$id = $_GET['id'];

if(isset($id)){
    $sql = "SELECT COUNT(*) AS qtyLike FROM users_like_paragraphs INNER JOIN paragraphs ON users_like_paragraphs.paragraphs_id = paragraphs.id WHERE paragraphs.cerbungs_id = $id";
    $result = $c->query($sql);
    if($result->num_rows > 0){
        $obj = $result->fetch_object();
        echo json_encode(array('result' => 'OK', 'data' => $obj));
    }else{
        echo json_encode(array('result'=> 'ERROR', 'message' => 'No paragraphs found!'));
    }
    $result->close();
    $c->close();
}else{
    echo json_encode(array(
        'result'=>'ERROR',
        'message'=>'no cerbung selected! (null reference)'));
    die();
}
?>