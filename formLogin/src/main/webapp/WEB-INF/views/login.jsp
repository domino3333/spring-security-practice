<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>login</title>
</head>
<body>
    <form action="/loginProc" method="post">
        <input name="username">
        <input name="password" type="password">
        <button type="submit">login</button>
    </form>
</body>
</html>
