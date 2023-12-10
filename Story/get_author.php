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
    $sql =  "SELECT users.id, users.username 
            FROM users
            INNER JOIN cerbungs ON users.id = cerbungs.users_id";
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