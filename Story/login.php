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
$username = (string)$_POST['username'];
$password = (string)$_POST['password'];
if($username != "" && $password != ""){
    $sql = "SELECT * FROM users WHERE username = ? AND password = ?";
    $stmt = $c->prepare($sql);
    $stmt->bind_param("ss",$username,$password);
    $stmt->execute();
    $result = $stmt->get_result();
    if($result->num_rows > 0) {
        $obj = $result->fetch_object();
        echo json_encode(array('result' => 'OK', 'data' => $obj));
    }else{
        echo json_encode(array('result'=> 'ERROR', 'message' => 'Username and/or password not found!'));
    }
    $stmt->close();
    $c->close();
}else{
    echo json_encode(array(
        'result'=>'ERROR',
        'message'=>'input username and/or password!'));
    die();
}
?>
