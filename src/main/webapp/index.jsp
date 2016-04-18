<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <!-- Meta, title, CSS, favicons, etc. -->
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">

  <title>SISCOUDLA</title>

  <!-- Bootstrap core CSS -->

  <link href="css/bootstrap.min.css" rel="stylesheet">

  <link href="fonts/css/font-awesome.min.css" rel="stylesheet">
  <link href="css/animate.min.css" rel="stylesheet">

  <!-- Custom styling plus plugins -->
  <link href="css/custom.css" rel="stylesheet">
  <link href="css/icheck/flat/green.css" rel="stylesheet">
  <script src="js/jquery.min.js"></script>

</head>

<body style="background:#F7F7F7;">

  <div class="">
    <a class="hiddenanchor" id="toregister"></a>
    <a class="hiddenanchor" id="tologin"></a>

    <div id="wrapper">
      <div id="login" class="animate form">
        <section class="login_content">
          <form>
            <h1>Login</h1>
            <div>
              <input type="text" class="form-control" placeholder="Usuario" required="" />
            </div>
            <div>
              <input type="password" class="form-control" placeholder="Contrase&ntilde;a" required="" />
            </div>
            <div>
              <a class="btn btn-default submit" href="index.html">Ingresar</a>
              <a class="reset_pass" href="#">Recuperar Contrase&ntilde;a</a>
            </div>
            <div class="clearfix"></div>
            <div class="separator">

              <p class="change_link"><a href="#toregister" class="to_register"> Crear cuenta</a>
              </p>
              <div class="clearfix"></div>
              <br />
              <div>
                <h1><i class="fa fa-institution" style="font-size: 26px;"></i> SISCOUDLA</h1>

                <p> ©2016 Todos los derechos reservados</p>
              </div>
            </div>
          </form>
          <!-- form -->
        </section>
        <!-- content -->
      </div>
      <div id="register" class="animate form">
        <section class="login_content">
          <form>
            <h1>Crear cuenta</h1>
            <div>
              <input type="text" class="form-control" placeholder="Usuario" required="" />
            </div>
            <div>
              <input type="email" class="form-control" placeholder="Email" required="" />
            </div>
            <div>
              <input type="password" class="form-control" placeholder="Contrase&ntilde;a" required="" />
            </div>
            <div>
              <a class="btn btn-default submit" href="index.html">Enviar</a>
            </div>
            <div class="clearfix"></div>
            <div class="separator">

              <p class="change_link">Ya est&aacute; registrado?
                <a href="#tologin" class="to_register"> Log in </a>
              </p>
              <div class="clearfix"></div>
              <br />
              <div>
                <h1><i class="fa fa-institution" style="font-size: 26px;"></i> SISCOUDLA</h1>

                <p> ©2016 Todos los derechos reservados</p>
              </div>
            </div>
          </form>
          <!-- form -->
        </section>
        <!-- content -->
      </div>
    </div>
  </div>

</body>
</html>