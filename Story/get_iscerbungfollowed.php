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
$id_user = $_GET['id_user'];
$id_cerbung = $_GET['id_cerbung'];

if(isset($id_user) && isset($id_cerbung)){
    $sql = "SELECT COUNT(*) as isFollowed FROM users_following_cerbungs WHERE users_id = $id_user AND cerbungs_id = $id_cerbung ";
    $result = $c->query($sql);
    if($result->num_rows > 0){
        $obj = $result->fetch_object();
        echo json_encode(array('result' => 'OK', 'data' => $obj));
    }else{
        echo json_encode(array('result'=> 'ERROR', 'message' => 'No data found! isCerbungFollowed'));
    }
    $result->close();
    $c->close();
}else{
    echo json_encode(array(
        'result'=>'ERROR',
        'message'=>'no cerbung / user selected! (null reference) isCerbungFollowed'));
    die();
}
?>