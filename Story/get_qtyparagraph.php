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
    $sql = "SELECT COUNT(paragraphs.isi) AS qtyPar FROM paragraphs INNER JOIN cerbungs ON paragraphs.cerbungs_id = cerbungs.id WHERE cerbungs.id = $id";
    $result = $c->query($sql);
    if($result->num_rows > 0){
        $obj = $result->fetch_object();
        echo json_encode(array('result' => 'OK', 'data' => $obj));
    }else{
        echo json_encode(array('result'=> 'ERROR', 'message' => 'No paragraphs found! qtyparag'));
    }
    $result->close();
    $c->close();
    
}else{
    echo json_encode(array(
        'result'=>'ERROR',
        'message'=>'no cerbung selected! (null reference) qtyparag'));
    die();
}
?>