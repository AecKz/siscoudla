<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <!-- Meta, title, CSS, favicons, etc. -->  
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">

  <title>SISCOUDLA</title>

  <!-- Bootstrap core CSS -->

  <link href="css/bootstrap.min.css" rel="stylesheet">

  <link href="fonts/css/font-awesome.min.css" rel="stylesheet">
  <link href="css/animate.min.css" rel="stylesheet">

  <!-- Custom styling plus plugins -->
  <link href="css/custom.css" rel="stylesheet">
  <script src="js/jquery.min.js"></script>
  <script src="js/controladores/nuevoUsuario.js"></script>
  <script src="js/bootstrap.min.js"></script>

  <!-- bootstrap progress js -->
  <script src="js/progressbar/bootstrap-progressbar.min.js"></script>
  <script src="js/nicescroll/jquery.nicescroll.min.js"></script>
  <script src="js/custom.js"></script>
  <!-- pace -->
  <script src="js/pace/pace.min.js"></script> 
</head>


<body class="nav-md">

  <div class="container body">

    <div class="main_container">

      <!-- page content -->
      <div class="col-md-12">
        <div class="col-middle">
          <div class="text-center">
            <h1>Usuario Nuevo</h1>
            <h2>Estimada(o) usuario, por favor complete la siguiente informaci&oacute;n para continuar.</h2>
            <h2>Seleccione Rol:</h2>
            <div class="mid_center">
              <h3>Roles</h3>
              <form>
                <div class="col-xs-12 form-group pull-right top_search">
                  <input type="button" id="btnEstudiante" class="btn btn-info" value="Estudiante">
                  <input type="button" id="btnCoordinador" class="btn btn-info" value="Coordinador">
                  <div id="infoCoordinador">
                  	<p>Estimada(o) Usuario, para acceder con el rol de Coordinador por favor comun&iacute;quese con el Admiminstrador del Sistema</p>
                  </div>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
      <!-- /page content -->

    </div>
    <!-- footer content -->
  </div>

  <div id="custom_notifications" class="custom-notifications dsp_none">
    <ul class="list-unstyled notifications clearfix" data-tabbed_notifications="notif-group">
    </ul>
    <div class="clearfix"></div>
    <div id="notif-group" class="tabbed_notifications"></div>
  </div>
  <!-- /footer content -->
</body>
</html>