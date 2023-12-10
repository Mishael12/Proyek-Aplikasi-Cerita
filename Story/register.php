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
    $sqlChecker = "SELECT * FROM users WHERE username=?";
    $stmtChecker = $c->prepare($sqlChecker);
    $stmtChecker->bind_param("s",$username);
    $stmtChecker->execute();
    $resultChecker = $stmtChecker->get_result();
    if($resultChecker->num_rows > 0) {
        $obj = $resultChecker->fetch_object();
        echo json_encode(array(
            'result' => 'ERROR', 
            'message' => 'Username already exists!',
            'data'=>$obj));
        die();
    }else{
        $sql = "INSERT INTO users(username,password) VALUES(?,?)";
        $stmt = $c->prepare($sql);
        $stmt->bind_param("ss",$username,$password);
        $stmt->execute();
        echo json_encode(array(
            'result'=>'OK',
            'message'=>'data added successfully!'));
        $stmt->close();
        $c->close();  
    }   
}else{
    echo json_encode(array(
        'result'=>'ERROR',
        'message'=>'input username, profile url, and password!'));
    die();
}
?>