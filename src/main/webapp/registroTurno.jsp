<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- Meta, title, CSS, favicons, etc. -->
<meta charset="UTF-8">
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
<link href="css/bootstrap-datepicker/bootstrap-datepicker.css" rel="stylesheet">
<script src="js/controladores/registroTurno.js"></script>
<script src="js/bootstrap.min.js"></script>
<!-- bootstrap progress js -->
<script src="js/progressbar/bootstrap-progressbar.min.js"></script>
<script src="js/nicescroll/jquery.nicescroll.min.js"></script>
<!-- datepicker -->
<script type="text/javascript" src="js/bootstrap-datepicker/bootstrap-datepicker.js"></script>
<script type="text/javascript" src="js/locales/bootstrap-datepicker.es.min.js"></script>
<script src="js/custom.js"></script>
<!-- form wizard -->
<script type="text/javascript" src="js/wizard/jquery.smartWizard.js"></script>
<!-- pace -->
<script src="js/pace/pace.min.js"></script>
</head>


<body class="nav-md">
	<%
		// Permitimos el acceso si la session existe		
		if (session.getAttribute("login") == null) {
			response.sendRedirect("/index.jsp");
		}
	%>
	<div class="container body">
		<div class="main_container">
			<div class="col-md-3 left_col">
				<div class="left_col scroll-view">

					<div class="navbar nav_title" style="border: 0;">
						<a href="dashboard.jsp" class="site_title"><i
							class="fa fa-institution"></i> <span>SISCOUDLA</span></a>
					</div>
					<div class="clearfix"></div>

					<!-- menu prile quick info -->
					<div class="profile">
						<div class="profile_pic">
							<img src="images/img.jpg" alt="..."
								class="img-circle profile_img">
						</div>
						<div class="profile_info">
							<span>Bienvenida (o),</span>
							<h2 id="txtUsuarioMenu"></h2>
						</div>
					</div>
					<!-- /menu prile quick info -->

					<br />

					<!-- sidebar menu -->
					<div id="sidebar-menu"
						class="main_menu_side hidden-print main_menu">

						<div class="menu_section">
							<h3>Estudiante</h3>
							<ul class="nav side-menu">
								<li><a href="dashboard.jsp"><i class="fa fa-home"></i>Home<span
										class="fa fa-home fa-fw"></span></a></li>
								<li><a><i class="fa fa-edit"></i>Registro de Turnos<span
										class="fa fa-chevron-down"></span></a>
									<ul class="nav child_menu" style="display: none">
										<li><a href="registroTurno.jsp">Turno Normal</a></li>
										<li><a href="#">Turno Extra</a></li>
									</ul></li>
								<li><a><i class="fa fa-book"></i>Informes<span
										class="fa fa-chevron-down"></span></a>
									<ul class="nav child_menu" style="display: none">
										<li><a href="#">Turno Normal</a></li>
										<li><a href="#">Turno Extra</a></li>
									</ul></li>
							</ul>
						</div>
					</div>
					<!-- /sidebar menu -->

					<!-- /menu footer buttons -->
					<div class="sidebar-footer hidden-small">
						<a data-toggle="tooltip" data-placement="top" title="Settings">
							<span class="glyphicon glyphicon-cog" aria-hidden="true"></span>
						</a> <a data-toggle="tooltip" data-placement="top" title="FullScreen">
							<span class="glyphicon glyphicon-fullscreen" aria-hidden="true"></span>
						</a> <a data-toggle="tooltip" data-placement="top" title="Lock"> <span
							class="glyphicon glyphicon-eye-close" aria-hidden="true"></span>
						</a> <a data-toggle="tooltip" data-placement="top" title="Logout">
							<span class="glyphicon glyphicon-off" aria-hidden="true"></span>
						</a>
					</div>
					<!-- /menu footer buttons -->
				</div>
			</div>

			<!-- top navigation -->
			<div class="top_nav">

				<div class="nav_menu">
					<nav class="" role="navigation">
						<div class="nav toggle">
							<a id="menu_toggle"><i class="fa fa-bars"></i></a>
						</div>

						<ul class="nav navbar-nav navbar-right">
							<li class=""><a href="javascript:;"
								class="user-profile dropdown-toggle" data-toggle="dropdown"
								aria-expanded="false"> <img src="images/img.jpg" alt=""><span id="txtUsuarioCabecera"></span><span class=" fa fa-angle-down"></span>
							</a>
								<ul
									class="dropdown-menu dropdown-usermenu animated fadeInDown pull-right">
									<li><a href="javascript:;"> Profile</a></li>
									<li><a href="javascript:;"> <span
											class="badge bg-red pull-right">50%</span> <span>Settings</span>
									</a></li>
									<li><a href="javascript:;">Help</a></li>
									<li><a href="login.html"><i
											class="fa fa-sign-out pull-right"></i> Log Out</a></li>
								</ul></li>

							<li role="presentation" class="dropdown"><a
								href="javascript:;" class="dropdown-toggle info-number"
								data-toggle="dropdown" aria-expanded="false"> <i
									class="fa fa-envelope-o"></i> <span class="badge bg-green">6</span>
							</a>
								<ul id="menu1"
									class="dropdown-menu list-unstyled msg_list animated fadeInDown"
									role="menu">
									<li><a> <span class="image"> <img
												src="images/img.jpg" alt="Profile Image" />
										</span> <span> <span>John Smith</span> <span class="time">3
													mins ago</span>
										</span> <span class="message"> Film festivals used to be
												do-or-die moments for movie makers. They were where... </span>
									</a></li>
									<li><a> <span class="image"> <img
												src="images/img.jpg" alt="Profile Image" />
										</span> <span> <span>John Smith</span> <span class="time">3
													mins ago</span>
										</span> <span class="message"> Film festivals used to be
												do-or-die moments for movie makers. They were where... </span>
									</a></li>
									<li><a> <span class="image"> <img
												src="images/img.jpg" alt="Profile Image" />
										</span> <span> <span>John Smith</span> <span class="time">3
													mins ago</span>
										</span> <span class="message"> Film festivals used to be
												do-or-die moments for movie makers. They were where... </span>
									</a></li>
									<li><a> <span class="image"> <img
												src="images/img.jpg" alt="Profile Image" />
										</span> <span> <span>John Smith</span> <span class="time">3
													mins ago</span>
										</span> <span class="message"> Film festivals used to be
												do-or-die moments for movie makers. They were where... </span>
									</a></li>
									<li>
										<div class="text-center">
											<a> <strong>See All Alerts</strong> <i
												class="fa fa-angle-right"></i>
											</a>
										</div>
									</li>
								</ul></li>
						</ul>
					</nav>
				</div>
			</div>
			<!-- /top navigation -->

			<!-- page content -->
			<div class="right_col" role="main">

				<div class="">
					<div class="clearfix"></div>
					<div class="row">
						<div class="col-md-12 col-sm-12 col-xs-12">
							<div class="x_panel">
							<div class="row x_title">
								<div class="col-md-6">
									<h3>Registro de Turno</h3>
								</div>
							</div>
								<div class="x_content">
									<!-- Smart Wizard -->
									<p>Siga los pasos para registrar un nuevo turno:</p>
									<br>
									<div id="wizard" class="form_wizard wizard_horizontal">
										<ul class="wizard_steps">
											<li><a href="#step-1"> <span class="step_no">1</span>
													<span class="step_descr"> Paso 1<br /> <small>Ingrese
															los datos del paciente</small>
												</span>
											</a></li>
											<li><a href="#step-2"> <span class="step_no">2</span>
													<span class="step_descr"> Paso 2<br /> <small>Seleccione
															Horario</small>
												</span>
											</a></li>
											<li><a href="#step-3"> <span class="step_no">3</span>
													<span class="step_descr"> Paso 3<br /> <small>Seleccione
															tratamiento</small>
												</span>
											</a></li>
											<li><a href="#step-4"> <span class="step_no">4</span>
													<span class="step_descr"> Paso 4<br /> <small>Confirmaci&oacute;n</small>
												</span>
											</a></li>
										</ul>
										<div id="step-1">
											<h2 class="StepTitle">Ingrese los datos del paciente</h2>
											<form class="form-horizontal form-label-left">
												<div class="form-group">
													<label class="control-label col-md-3 col-sm-3 col-xs-12"
														for="txtHistoriaClinica">N° Historia
														Cl&iacute;nica:</label>
													<div class="col-md-6 col-sm-6 col-xs-12">
														<input type="text" id="txtHistoriaClinica"
															class="form-control col-md-7 col-xs-12">
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-md-3 col-sm-3 col-xs-12"
														for="txtNombresPaciente">Nombres:<span
														class="required">*</span>
													</label>
													<div class="col-md-6 col-sm-6 col-xs-12">
														<input type="text" id="txtNombresPaciente" required
															class="form-control col-md-7 col-xs-12">
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-md-3 col-sm-3 col-xs-12"
														for="txtApellidosPaciente">Apellidos:<span
														class="required">*</span>
													</label>
													<div class="col-md-6 col-sm-6 col-xs-12">
														<input type="text" id="txtApellidosPaciente" required
															class="form-control col-md-7 col-xs-12">
													</div>
												</div>
												<div class="form-group">
													<label for="txtEmail"
														class="control-label col-md-3 col-sm-3 col-xs-12">E-mail:<span
														class="required">*</span></label>
													<div class="col-md-6 col-sm-6 col-xs-12">
														<input type="email" id="txtEmailPaciente" required
															class="form-control col-md-7 col-xs-12">
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-md-3 col-sm-3 col-xs-12">G&eacute;nero:<span
														class="required">*</span></label>
													<div class="col-md-6 col-sm-6 col-xs-12">
														<div id="genero" class="btn-group" data-toggle="buttons">
															<input type="radio" name="genero" value="m">&nbsp;Masculino&nbsp;
															<input type="radio" name="genero" value="f">&nbsp;Femenino&nbsp;
														</div>
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-md-3 col-sm-3 col-xs-12">Fecha
														de Nacimiento:<span class="required">*</span>
													</label>
													<div class="col-md-6 col-sm-6 col-xs-12">
														<input type="text" id="fechaNacimientoPaciente"
															class="form-control col-md-7 col-xs-12" required>
													</div>
												</div>
											</form>
										</div>
										<div id="step-2">
											<h2 class="StepTitle">Horarios Registrados</h2>
											<p>Estimada(o) estudiante, para el presente semestre ud
												tiene registrado los siguientes horarios:</p>
											<table id="tblHorariosEstudiante" class="table">
												<thead>
													<tr>
														<th>DIA</th>
														<th>HORA INICIO</th>
														<th>HORA FIN</th>														
													</tr>
												</thead>
												<tbody id="dataTableContent">
												</tbody>
											</table>
											<p>Seleccione Fecha:</p>
											<br>		
											<div id="fechaSeleccionada" align="center"></div>									

										</div>
										<div id="step-3">
											<h2 class="StepTitle">Seleccione el tratamiento a
												realizar:</h2>
							                  <!-- start accordion -->
							                  <div class="accordion" id="accordion" role="tablist" aria-multiselectable="true">
								                    <div class="panel">
								                   	 	<div id="panelContent"></div>
								                 	 </div>							                  
											  </div>
											  <!-- end of accordion -->
										</div>
										<div id="step-4">
											<h2 class="StepTitle">Confirme su turno:</h2>
											<p>Estimada(o) estudiante, ha ingresado los siguientes datos:</p>											
						                        <div class="detalleTurno">						
						                          <h4>Nombre Paciente:</h4>
						                          <p id="lblnNombrePaciente">Deveint Inc</p>
						                          <h4 class="title">Fecha:</h4>
						                          <p id="lblnFechaTurno"></p>
						                          <h4 class="title">Horario:</h4> 
						                          <p id="lblnHorario">
						                          <h4>Tratamiento:</h4>
						                          <p id="lblnTratamiento">
						                          <h4>Especialidad:</h4>
						                          <p id="lblnEspecialidad"></p>
						                          <h4>Cub&iacute;culo asignado:</h4>
						                          <h3 id="lblCubiculoAsignado"></h3>
						                        </div>
											<button id="btnAceptar" type="button" class="btn btn-primary" onclick="reservarTurno()">ACEPTAR</button>
										</div>
									</div>
									<!-- End SmartWizard Content -->
								</div>
							</div>
						</div>

					</div>
				</div>
			</div>
			<!-- /page content -->
		</div>

	</div>	
	<!-- 	<div id="custom_notifications" class="custom-notifications dsp_none"> -->
	<!-- 		<ul class="list-unstyled notifications clearfix" -->
	<!-- 			data-tabbed_notifications="notif-group"> -->
	<!-- 		</ul> -->
	<!-- 		<div class="clearfix"></div> -->
	<!-- 		<div id="notif-group" class="tabbed_notifications"></div> -->
	<!-- 	</div> -->
</body>
</html>