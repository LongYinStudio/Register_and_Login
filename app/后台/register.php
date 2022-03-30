<?php
$user = $_POST['user'];
$password = $_POST['password'];
$link = mysqli_connect('localhost', 'MysqlTest', 'AIDE133122#', 'test');
if (!$link) {
    $conn_failed = array('code' => -3, 'msg' => "数据库连接失败，error:" . $mysql_error());
    echo json_encode($conn_failed, JSON_UNESCAPED_UNICODE);
}
mysqli_set_charset($link, 'utf8');
if (empty($user) || empty($password)) {
    $login_empty = array('code' => -7, 'msg' => "请输入用户名和密码");
    echo json_encode($login_empty, JSON_UNESCAPED_UNICODE);
} else {
    $result = mysqli_query($link, "INSERT INTO `phptest`(`user`, `password`) VALUES ('$user','$password')");
    if ($result) {
        $registerSuccess = array('code' => 201, 'msg' => "注册成功");
        echo json_encode($registerSuccess, JSON_UNESCAPED_UNICODE);
    } else {
        $registerFailed = array('code' => -6, 'msg' => "用户已存在");
        echo json_encode($registerFailed, JSON_UNESCAPED_UNICODE);
    }
}
