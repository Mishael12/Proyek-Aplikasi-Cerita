<?php
    error_reporting(E_ERROR|E_PARSE);
    $c = new mysqli("localhost", "root", "", "cerbungdb");

    if($c -> connect_errno){
        $arrayerror = array(
                            'result' => 'ERROR',
                            'message'    => 'Failed to connect DB'
                        );
        echo json_encode($arrayerror);
        die();
    }
    $id = $_GET['id'];
    if(isset($id)){
        $sql = "SELECT cerbungs.id, genres.nama as genre, users.username as author, cerbungs.title, cerbungs.desc_short, cerbungs.url_gambar, cerbungs.tgl_dibuat, cerbungs.isRestricted 
        FROM users_following_cerbungs 
        INNER JOIN cerbungs ON users_following_cerbungs.cerbungs_id = cerbungs.id 
        INNER JOIN genres ON genres.id = cerbungs.genres_id 
        INNER JOIN users ON users.id = cerbungs.users_id WHERE users_following_cerbungs.users_id = $id";
        $result = $c->query($sql);
        $cerbungs = array();
        if($result->num_rows > 0){
            while($obj = $result->fetch_object()){
                $cerbungs[] = $obj;        }
            echo json_encode(array('result' => 'OK', 'data' => $cerbungs));
        }else{
            echo json_encode(array('result'=> 'ERROR', 'message' => 'No cerbung found! (maybe user dont follow any)'));
        }
        $result->close();
        $c->close();
    }else{
        echo json_encode(array(
            'result'=>'ERROR',
            'message'=>'no user selected! (null reference)'));
        die();
    }
    
?>