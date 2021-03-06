<%--
  Created by IntelliJ IDEA.
  User: fabricio
  Date: 9/24/13
  Time: 2:57 PM
--%>

<!DOCTYPE html>
<html>
<head>

    %{--<meta name="layout" content="main">--}%

    <title>Manual de Usuario - Reportes</title>
    <style>

    div {
        margin : auto;
        max-width: 800px;
    }

    #header2 {
        z-index          : 1;
        position         : relative;
        width            : 97.5%;
        height           : 60px;
        text-align       : center;
        background-color : #ffffff;
        margin-top       : 0px;
    }

    .centrado {
        text-align : center;
    }

    #indice {
        width            : 650px;
        position         : relative;
        background-color : #c8cac9;
        font-family      : Verdana, sans-serif;
        font-size        : 14px;
        color            : #000000;
        margin           : auto;
        text-align       : justify;
    }

    p {
        text-align : justify;
    }

    .cuadro {
        width  : 600px;
        margin : auto;
    }

    a {
        text-decoration : none;
        color           : #000000;
    }

    a:hover {
        font-weight : bold;
    }

    .cursiva {
        font-style : italic;
    }

    .regresa {
        float            : right;
        background-color : #c8cac9;
        width            : 120px;
        border           : 2px solid black;
        border-radius    : 10px;
        margin-right     : 40px;
    }

    .regresa p {
        text-align : center;
    }

    .izquierda {
        text-align : left;
    }

    .boton {
        position : relative;
        top      : 12px;
    }

    table tr {
        text-align : justify;
    }

    </style>
</head>

<body>
<div id="header2">
    <img src="${resource(dir: 'images/imagenesManuales/imagenesReportes', file: 'encabezado.png')}"/>
</div>

<div class="centrado">
    <br><br><br><br>
    <img src="${resource(dir: 'images/imagenesManuales/imagenesReportes', file: 'logo.png')}"/>
    <br><br>

    <h3>Sistema Integrado de Gesti&oacuten de Proyectos, Fiscalizaci&oacuten,
    Contrataci&oacuten y Ejecuci&oacuten de Obras</h3>
    <br><a id="volverIndice"></a>

    <h2>Manual del Usuario</h2>
</div>

<div id="indice">
    <h3>&Iacutendice de contenido</h3>

    <p><a href="#SistInt">Sistema Integrado de Gesti&oacuten de Proyectos,
    Fiscalizaci&oacuten, Contrataci&oacuten y Ejecuci&oacuten de Obras
    </a></p>
    <ol>
        <li><a href="#reportes">Reportes del Sistema</a></li>
        <ul>
            <li><a href="#reportes1">Reportes</a></li>
            <li><a href="#ingresadas">Obras Ingresadas</a></li>
            <ul>
                <li><a href="#registro">Ir al registro de la obra</a></li>
                <li><a href="#subpresupuesto">Imprimir Subpresupuesto</a></li>
            </ul>

            <li><a href="#presupuestadas">Obras Presupuestadas</a></li>
            <li><a href="#procesos">Procesos de Contrataci&oacute;n</a></li>
            <li><a href="#contratadas">Obras Contratadas</a></li>
            <li><a href="#contratos">Contratos</a></li>
            <li><a href="#contratistas">Contratistas</a></li>
            <li><a href="#aseguradoras">Aseguradoras</a></li>
            <li><a href="#garantias">Garant??as</a></li>
            <li><a href="#transferencias">Transferencias y/o cheques pagados</a></li>
            <li><a href="#avance">Avance de Obras</a></li>
            <li><a href="#finalizadas">Obras Finalizadas</a></li>

        </ul>

    </ol>
</div>

<br> <a id="reportes"></a>

<div class="regresa">
    <p><a href="#volverIndice">Volver al &Iacutendice</a></p>
</div> <br>

<div>
    <br><br>

    <h2 class="cursiva">Reportes</h2>

    <p>
        Para generar los reportes de tr??mites se usan los botones que aparecen en la pantalla de lista de tr??mites.<br>

        El reporte de tr??mite en proceso no requiere de datos adicionales para su generaci??n y simplemente aparecer?? la pantalla de descarga del archivo generado del reporte.

    </p>

    <div class="centrado"><!-- pg 6 -->
        <img src="${resource(dir: 'images/imagenesManuales/imagenesReportes', file: 'img001.png')}"/>
    </div> <br>

    <p>
        Para el reporte de tr??mite por obra, es necesario primero seleccionar la obra a reportar.
    </p>

    <div class="centrado">
        <img src="${resource(dir: 'images/imagenesManuales/imagenesReportes', file: 'img002.png')}"/>
    </div> <br>

    <p></p>

    <p>
        Una vez seleccionada la obra presione Aceptar para que el reporte se genere.
        El combo muestra s??lo la lista de obras que presentan tr??mites.
    </p>

    <div class="centrado"><!-- pg 7 -->
        <img src="${resource(dir: 'images/imagenesManuales/imagenesReportes', file: 'img003.png')}"/>
    </div> <br>

    <p>
        Para ingresar en la secci??n de reportes del Sistema, clic en ???OBRAS??? se desplegar?? un submen??; a continuaci??n clic en ???Reportes???.
    </p>


    <div class="centrado"><!-- pg 7 -->
        <img src="${resource(dir: 'images/imagenesManuales/imagenesReportes', file: 'img004.png')}" style="width: 800px"/>
    </div> <br>

</div>

<a id="ingresadas"></a>

<div class="regresa">
    <p><a href="#volverIndice">Volver al &Iacutendice</a></p>
</div>

<div>
    <h2>Obras Ingresadas</h2>
</div>

<div class="centrado">
    <p>Listado de obras que se hallan en el sistema,
    estas obras est??n en la fase inicial de estructuraci??n de presupuestos
    y de documentos precontractuales. Estado = 'N' (No registrada)</p>

    <img src="${resource(dir: 'images/imagenesManuales/imagenesReportes', file: 'img005.png')}" style="width: 800px"/>

    <p>Mediante esta pantalla podemos buscar las obras que se hallan como no registradas en el sistema.</p>

    <p>El combo ???Buscar Por??? posee un conjunto de par??metros de b??squeda como son: C??digo, Nombre, Tipo,
    Cant??n, Parroquia, Comunidad, Inspector, Revisor, Oficio de Ingreso, Oficio de Salida,
    Memorando de Salida, y F??rmula Polin??mica.</p>

    <p>Escribimos el criterio a buscar y damos clic en el bot??n ???Buscar???.</p>

    <img src="${resource(dir: 'images/imagenesManuales/imagenesReportes', file: 'img006.png')}"style="width: 800px"/>


    <p>
        Adicionalmente al dar clic derecho sobre la fila de cualquier obra,
        se desplegar?? un men?? que consta de las siguientes dos acciones:
    </p>

    <img src="${resource(dir: 'images/imagenesManuales/imagenesReportes', file: 'img007.png')}" style="width: 800px"/>

</div>


<a id="registro"></a>

<div class="regresa">
    <p><a href="#volverIndice">Volver al &Iacutendice</a></p>
</div>

<div>
    <h2>Ir al Registro de la Obra</h2>
</div>

<div class="centrado">
    <p>Nos permite ir a la pantalla de registro de la obra seleccionada.</p>

    <img src="${resource(dir: 'images/imagenesManuales/imagenesReportes', file: 'img008.png')}" style="width: 800px"/>

</div>

<a id="subpresupuesto"></a>

<div>
    <h2>Imprimir Subpresupuesto</h2>
</div>

<div class="centrado">
    <p>Imprime los subpresupuestos pertenecientes a la obra seleccionada.</p>

    <img src="${resource(dir: 'images/imagenesManuales/imagenesReportes', file: 'img009.png')}" style="width: 800px"/>

</div>

<a id="presupuestadas"></a>

<div class="regresa">
    <p><a href="#volverIndice">Volver al &Iacutendice</a></p>
</div>

<div>
    <h2>Obras Presupuestadas</h2>
</div>

<div class="centrado">
    <p>
        Listado de obras que ya poseen un presupuesto elaborado y se hallan listas
        para entrar en el proceso de contrataci??n. Estado = 'R'.
    </p>

    <img src="${resource(dir: 'images/imagenesManuales/imagenesReportes', file: 'img010.png')}" style="width: 800px"/>

    <p> El reporte nos presenta el conjunto de obras registradas en el sistema.</p>

    <img src="${resource(dir: 'images/imagenesManuales/imagenesReportes', file: 'img011.png')}" style="width: 800px"/>

</div>

<a id="procesos"></a>

<div class="regresa">
    <p><a href="#volverIndice">Volver al &Iacutendice</a></p>
</div>

<div>
    <h2>Procesos de Contrataci??n</h2>
</div>

<div class="centrado">
    <p>
        Listado de procesos de contrataci??n para la construcci??n de obras y para consultor??as.
    </p>

    <img src="${resource(dir: 'images/imagenesManuales/imagenesReportes', file: 'img012.png')}" style="width: 800px"/>

    <p> El combo ???Buscar por??? posee un conjunto de par??metros de b??squeda como son: C??digo,  Objeto, Fecha Inicio, Presupuesto, Obra.

    El reporte nos presenta el conjunto de procesos de contrataci??n para cada obra.
    </p>

    <img src="${resource(dir: 'images/imagenesManuales/imagenesReportes', file: 'img013.png')}" style="width: 800px"/>

</div>

<a id="contratadas"></a>

<div class="regresa">
    <p><a href="#volverIndice">Volver al &Iacutendice</a></p>
</div>

<div>
    <h2>Obras Contratadas</h2>
</div>

<div class="centrado">
    <p>
        Listado de obras que se encuentran contratadas.
    </p>

    <img src="${resource(dir: 'images/imagenesManuales/imagenesReportes', file: 'img014.png')}" style="width: 800px"/>

    <p> El combo ???Buscar Por??? posee un conjunto de par??metros de b??squeda como son: C??digo, Nombre, Tipo,
    Cant??n, Parroquia, Comunidad, Inspector, Revisor, Oficio de Ingreso, Oficio de Salida, Memorando de Salida,
    y F??rmula Polin??mica.

    El reporte nos presenta todas las obras contratadas.

    </p>

    <img src="${resource(dir: 'images/imagenesManuales/imagenesReportes', file: 'img015.png')}" style="width: 800px"/>

</div>

<a id="contratos"></a>

<div class="regresa">
    <p><a href="#volverIndice">Volver al &Iacutendice</a></p>
</div>

<div>
    <h2>Contratos</h2>
</div>

<div class="centrado">
    <p>
        Listado de contratos de obras y consultor??as registrados en el sistema.
    </p>

    <img src="${resource(dir: 'images/imagenesManuales/imagenesReportes', file: 'img016.png')}" style="width: 800px"/>

    <p> El combo ???Buscar Por??? posee un conjunto de par??metros de b??squeda como son: N?? Contrato,
    Memo, Fecha Suscripci??n, Tipo de contrato, Concurso, Obra, Nombre , Cant??n Parroquia, Clase,
    Monto, Contratista, Tipo Plazo, Fecha Inicio, Fecha Fin.

    Al seleccionar Fecha Suscripci??n, Fecha Inicio o Fecha Fin, se habilitar?? el campo ???Fecha??? en donde
    podremos elegir la fecha a ser buscada.
    </p>

    <img src="${resource(dir: 'images/imagenesManuales/imagenesReportes', file: 'img017.png')}" style="width: 800px"/>

</div>

<a id="contratistas"></a>

<div class="regresa">
    <p><a href="#volverIndice">Volver al &Iacutendice</a></p>
</div>

<div>
    <h2>Contratistas</h2>
</div>

<div class="centrado">
    <p>
        Listado de contratistas que han firmado contratos de obras y consultor??a con el GADPP.
    </p>

    <img src="${resource(dir: 'images/imagenesManuales/imagenesReportes', file: 'img018.png')}" style="width: 800px"/>

    <p> El combo ???Buscar Por??? nos permite seleccionar los contratistas ya sea por Nombre, C??dula, Especialidad.
    </p>

    <img src="${resource(dir: 'images/imagenesManuales/imagenesReportes', file: 'img019.png')}" style="width: 800px"/>

</div>


<a id="aseguradoras"></a>

<div class="regresa">
    <p><a href="#volverIndice">Volver al &Iacutendice</a></p>
</div>

<div>
    <h2>Aseguradoras</h2>
</div>

<div class="centrado">
    <p>
        Listado de aseguradoras que se hallan registradas en el sistema que han emitido garant??as.
    </p>

    <img src="${resource(dir: 'images/imagenesManuales/imagenesReportes', file: 'img020.png')}" style="width: 800px"/>

    <p> Es posible filtrar los resultados ya sea por Nombre, Tipo, Tel??fono, Fax, Contacto, Direcci??n; usando el combo ???Buscar Por???.

    </p>

    <img src="${resource(dir: 'images/imagenesManuales/imagenesReportes', file: 'img021.png')}" style="width: 800px"/>

</div>

<a id="garantias"></a>

<div class="regresa">
    <p><a href="#volverIndice">Volver al &Iacutendice</a></p>
</div>

<div>
    <h2>Garant??as</h2>
</div>

<div class="centrado">
    <p>

        Garant??as registradas de los distintos contratos para obras y cosultor??a; detalladas por contratos.
    </p>

    <img src="${resource(dir: 'images/imagenesManuales/imagenesReportes', file: 'img022.png')}" style="width: 800px"/>

    <p> El combo ???Buscar Por??? nos permite filtrar los resultados que se muestren en pantalla, el combo posee los siguientes
    par??metros de b??squeda: N?? Contrato, Garant??a, Renovaci??n, Tipo de Garant??a, Documento, Aseguradora, Contratista,
    Estado, Monto, Moneda, Emisi??n, Vencimiento, D??as.
    </p><br>
    <p>
        Si se desea buscar por fecha elegimos en ???Buscar Por???  los filtros: Emisi??n o Vencimiento,
        y se habilitar?? ???Fecha??? la cual nos permitir?? seleccionar una fecha para realizar la b??squeda.
    </p>

    <img src="${resource(dir: 'images/imagenesManuales/imagenesReportes', file: 'img023.png')}" style="width: 800px"/>

</div>

<a id="transferencias"></a>

<div class="regresa">
    <p><a href="#volverIndice">Volver al &Iacutendice</a></p>
</div>

<div>
    <h2>Transferencias y/o cheques pagados</h2>
</div>

<div class="centrado">
    <p>

        Listado de pagos realizados a partir de la solicitud de pagos relativos a las obras.
    </p>

    <img src="${resource(dir: 'images/imagenesManuales/imagenesReportes', file: 'img024.png')}" style="width: 800px"/>

    <img src="${resource(dir: 'images/imagenesManuales/imagenesReportes', file: 'img025.png')}" style="width: 800px"/>

</div>


<a id="avance"></a>

<div class="regresa">
    <p><a href="#volverIndice">Volver al &Iacutendice</a></p>
</div>

<div>
    <h2>Avance de Obras</h2>
</div>

<div class="centrado">
    <p>

        Listado de obras con el respectivo porcentaje de avance.
    </p>

    <img src="${resource(dir: 'images/imagenesManuales/imagenesReportes', file: 'img026.png')}" style="width: 800px"/>

    <p>
        Los filtros de b??squeda en ???Buscar Por???  son: C??digo, Nombre, Tipo, Cant??n, Parroquia,  Comunidad, N??mero de Contrato, Contratista.
    </p><br>

    <img src="${resource(dir: 'images/imagenesManuales/imagenesReportes', file: 'img027.png')}" style="width: 800px"/>

</div>

<a id="finalizadas"></a>

<div class="regresa">
    <p><a href="#volverIndice">Volver al &Iacutendice</a></p>
</div>

<div>
    <h2>Obras Finalizadas</h2>
</div>

<div class="centrado">
    <p>

        Listado de obras finalizadas.
    </p>

    <img src="${resource(dir: 'images/imagenesManuales/imagenesReportes', file: 'img028.png')}" style="width: 800px"/>

    <p>
        Los filtros de b??squeda nos permiten buscar por C??digo, Nombre, Descripci??n,
        Memo Ingreso, Memo Salida, Sitio, Plazo, Parroquia, Comunidad, Direcci??n, Fecha.

    </p><br>

    <img src="${resource(dir: 'images/imagenesManuales/imagenesReportes', file: 'img029.png')}" style="width: 800px"/>

</div>












</body>
</html>
