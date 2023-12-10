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
$id_user = $_POST['id_user'];
$id_cerbung = $_POST['id_cerbung'];

if($id_user != "" && $id_cerbung != ""){
    $sql = "DELETE FROM users_following_cerbungs 
    WHERE users_following_cerbungs.users_id = ? AND users_following_cerbungs.cerbungs_id = ?";
    $stmt = $c->prepare($sql);
    $stmt->bind_param("ii",$id_user,$id_cerbung);
    $stmt->execute();
    echo json_encode(array('result' => 'OK', 'message' => "delete success! isCerbungUnFollowed"));
    $stmt->close();
    $c->close();
}else{
    echo json_encode(array(
        'result'=>'ERROR',
        'message'=>'No user and/or cerbung selected! (null reference) isCerbungUnFollowed'));
    die();
}
?>

