<?php
    error_reporting(E_ERROR|E_PARSE);
    $c = new mysqli("localhost", "root", "", "cerbungdb");

    if($c -> connect_errno){
        $arrayerror = array(
                            'result' => 'ERROR',
                            'msg'    => 'Failed to connect DB'
                        );
        echo json_encode($arrayerror);
        die();
    }
    $sql ="SELECT cerbungs.id, genres.nama as genre, users.username as author, cerbungs.title, cerbungs.desc_short, cerbungs.url_gambar, cerbungs.tgl_dibuat, cerbungs.isRestricted
            FROM ((cerbungs INNER JOIN genres ON cerbungs.genres_id = genres.id) 
            INNER JOIN users ON cerbungs.users_id = users.id) ";
    $result = $c->query($sql);
    $arrayresult = array();

    while ($obj = $result->fetch_object()){
        $arrayresult[] = $obj;
    }
    $arrayjson = array(
                        'result' => 'OK',
                        'msg'    => $arrayresult
                    );
    echo json_encode($arrayjson);
?>