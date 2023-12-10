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

$sql = "SELECT * FROM genres";
$result = $c->query($sql);
$genres = array();

if($result->num_rows > 0){
    while($obj = $result->fetch_object()){
        $genres[] = $obj;
    }
    echo json_encode(array('result' => 'OK', 'data' => $genres));
    $c->close();
}
else{
    echo json_encode(array(
        'result' => 'ERROR', 
        'message'=> 'No data found')
    );
    die();
}
?>
