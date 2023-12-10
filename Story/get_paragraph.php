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
    $sql = "SELECT paragraphs.id, paragraphs.isi as content, paragraphs.cerbungs_id, users.username as author 
FROM paragraphs INNER JOIN users ON paragraphs.users_id = users.id 
WHERE paragraphs.cerbungs_id = $id";
$result = $c->query($sql);
$paragraphs = array();

if($result->num_rows > 0){
    while($obj = $result->fetch_object()){
        $paragraphs[] = $obj;
    }
    echo json_encode(array('result' => 'OK', 'data' => $paragraphs));
    $result->close();
    $c->close();
}
}else{
    echo json_encode(array(
        'result' => 'ERROR', 
        'message'=> 'No cerbung seleced (null reference) getParag')
    );
    die();
}
?>
