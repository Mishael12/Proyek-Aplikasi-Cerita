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

$sql = "SELECT cerbungs.id, genres.nama as genre, users.username as author, cerbungs.title, cerbungs.desc_short, cerbungs.url_gambar, cerbungs.tgl_dibuat, cerbungs.isRestricted
FROM ((cerbungs INNER JOIN genres ON cerbungs.genres_id = genres.id) 
INNER JOIN users ON cerbungs.users_id = users.id) 
WHERE cerbungs.id = $id";
$result = $c->query($sql);
if($result->num_rows > 0){
    $obj = $result->fetch_object();
    echo json_encode(array('result' => 'OK', 'data' => $obj));
    $result->close();
    $c->close();
}else{
    echo json_encode(array(
        'result' => 'ERROR', 
        'message'=> 'No data found')
    );
    die();
}
?>