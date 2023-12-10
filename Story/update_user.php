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
$id = (Int)$_POST['id'];
$username = (string)$_POST['username'];
$password = (string)$_POST['password'];
if($username != "" && $password != ""){
    $sql = "UPDATE users SET username = ?, password = ? WHERE id = ?";
    $stmt = $c->prepare($sql);
    $stmt->bind_param("ssi",$username,$password, $id);
    $stmt->execute();
   
    echo json_encode(array('result' => 'OK', 'message' => "Data Update Succesfully"));
    
    $stmt->close();
    $c->close();
}else{
    echo json_encode(array(
        'result'=>'ERROR',
        'message'=>'input username and/or password!'));
    die();
}
?>
