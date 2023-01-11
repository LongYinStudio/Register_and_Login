<?php
$user = $_POST['user'];
$password = $_POST['password'];
$link = mysqli_connect('localhost', '用户名', '密码', '数据库');
if (!$link) {
    $conn_failed = array('code' => -3, 'msg' => "数据库连接失败，error:" . $mysql_error());
    echo json_encode($conn_failed, JSON_UNESCAPED_UNICODE);
}
mysqli_set_charset($link, 'utf8');
$result = mysqli_query($link, "SELECT * FROM `phptest` WHERE user='$user'");
$row = mysqli_fetch_assoc($result);

if (strlen($user) == 0) {
    $user_empty = array('code' => -4, 'msg' => "未输入用户名");
    echo json_encode($user_empty, JSON_UNESCAPED_UNICODE);
} else {
    if ($user == $row["user"]) {
        if ($password == $row["password"]) {
            $success = array('code' => 200, 'msg' => "登录成功");
            echo json_encode($success, JSON_UNESCAPED_UNICODE);
        } else {
            $fail = array('code' => -1, 'msg' => "登录失败");
            echo json_encode($fail, JSON_UNESCAPED_UNICODE);
        }
    } else {
        $failed = array('code' => -2, 'msg' => "用户不存在");
        echo json_encode($failed, JSON_UNESCAPED_UNICODE);
    }
}
