package org.svaltqt.Controlador;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import org.svaltqt.Modelo.Profesor;
import org.svaltqt.Validaciones.ExpresionesRegulares;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.net.URL;

public class VentanaPrincipalController implements Initializable{
    @FXML
    public Label txtNombre;
    @FXML
    public Label txtCedula;
    @FXML
    public Label txtSexo;
    @FXML
    public Label txtFacultad;
    @FXML
    public Label txtTitulo;
    @FXML
    public Label txtAsignaturas;
    @FXML
    public Label txtHoras;
    @FXML
    public Label txtFecha;
    @FXML
    public Label txtPorDocente;
    @FXML
    public Label txtPorFacultad;
    @FXML
    public Label txtPorContrato;
    @FXML
    public Label txtTipoContrato;
    @FXML
    public Label txtTotalResultado;
    @FXML
    public TextField campoCedula;
    @FXML
    public TextField campoNombre;
    @FXML
    public ChoiceBox<String> choiceSexo;
    @FXML
    public ChoiceBox<String> choiceTitulo;
    @FXML
    public ChoiceBox<String> choiceFacultad;
    @FXML
    public TextField campoFecha;
    @FXML
    public TextField campoHoras;
    @FXML
    public TextField campoAsignaturas;
    @FXML
    public ChoiceBox<String> choicePorDocente;
    @FXML
    public ChoiceBox<String> choicePorFacultad;
    @FXML
    public ChoiceBox<String> choicePorContrato;
    @FXML
    public ChoiceBox<String> choiceTipoContrato;
    @FXML
    public Button ButtonAgregar;
    @FXML
    public Button buttonBuscarDocente;
    @FXML
    public Button buttonBuscarFacultad;
    @FXML
    public Button buttonBuscarContrato;
    @FXML
    public Button ButtonImportar;
    @FXML
    public TableView<Profesor> listaPrincipal;
    @FXML
    public Label TextmensajeError;



    // ArrayLists
    private final List<Profesor> listaProfesores = new ArrayList<>();
    private final Set<Profesor> ListaProfeCatedra = new HashSet<>();
    private final Set<Profesor> ListaProfeCompleto = new HashSet<>();
    private final Set<Profesor> ListaProfeOcasional = new HashSet<>();
    private final Set<Profesor> ProfesoresUnicos = new HashSet<>();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // ChoiceBox Atributos
        choiceSexo.setItems(FXCollections.observableArrayList("Masculino", "Femenino"));
        choiceFacultad.setItems(FXCollections.observableArrayList("Ingenieria", "Deportes","Administracion",
                                                                        "Idiomas","Ciencias Basicas"));
        choiceTitulo.setItems(FXCollections.observableArrayList("Pregrado", "Especialista","Maestría","Doctorado"));
        choicePorDocente.setItems(FXCollections.observableArrayList("Tiempo Completo", "Cátedra","Ocasional",
                                                                         "Total Docentes","-","T.C - Cátedra",
                                                                         "Ocasional - Cátedra","CA-TC-OC"));
        choicePorContrato.setItems(FXCollections.observableArrayList("Total Hombres", "Total Mujeres"));
        choicePorFacultad.setItems(FXCollections.observableArrayList("Ingenieria", "Deportes","Administracion",
                                                                          "Idiomas","Ciencias Basicas"));
        choiceTipoContrato.setItems(FXCollections.observableArrayList("Tiempo Completo", "Ocasional","Cátedra"));

        // ListView Atributos

        // Configurar columnas y encabezados para la TableView
        TableColumn<Profesor, String> cedulaColumn = new TableColumn<>("Cédula");
        cedulaColumn.setCellValueFactory(new PropertyValueFactory<>("cedula"));
        cedulaColumn.setMinWidth(82);

        TableColumn<Profesor, String> nombreColumn = new TableColumn<>("Nombre Completo");
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombreCompleto"));
        nombreColumn.setMinWidth(180);

        TableColumn<Profesor, String> contratoColumn = new TableColumn<>("Contrato");
        contratoColumn.setCellValueFactory(new PropertyValueFactory<>("tipoContrato"));
        contratoColumn.setMinWidth(82);

        TableColumn<Profesor, String> sexColumn = new TableColumn<>("Sexo");
        sexColumn.setCellValueFactory(new PropertyValueFactory<>("sexo"));
        sexColumn.setMinWidth(75);

        TableColumn<Profesor, String> facultadColumn = new TableColumn<>("Facultad");
        facultadColumn.setCellValueFactory(new PropertyValueFactory<>("facultad"));
        facultadColumn.setMinWidth(98);

        TableColumn<Profesor, String> tituloColumn = new TableColumn<>("Título");
        tituloColumn.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        tituloColumn.setMinWidth(98);

        TableColumn<Profesor, Integer> asigColumn = new TableColumn<>("# Asignaturas");
        asigColumn.setCellValueFactory(new PropertyValueFactory<>("numAsignaturas"));
        asigColumn.setMinWidth(98);

        TableColumn<Profesor, Integer> numHorasColumn = new TableColumn<>("# Horas");
        numHorasColumn.setCellValueFactory(new PropertyValueFactory<>("numHoras"));
        numHorasColumn.setMinWidth(58);

        TableColumn<Profesor, String> fechaColumn = new TableColumn<>("Fecha");
        fechaColumn.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        fechaColumn.setMinWidth(98);



        List<TableColumn<Profesor, ?>> columns = new ArrayList<>();
        columns.add(cedulaColumn);
        columns.add(contratoColumn);
        columns.add(nombreColumn);
        columns.add(sexColumn);
        columns.add(facultadColumn);
        columns.add(tituloColumn);
        columns.add(asigColumn);
        columns.add(numHorasColumn);
        columns.add(fechaColumn);

        // Agregar las columnas a la TableView
        listaPrincipal.getColumns().setAll(columns);

    }

    private void MensajeError(Label label, String mensaje) {
        label.setText(mensaje);
        label.setStyle("-fx-text-fill: red;");
    }

    private boolean cedulaYaExiste(String cedula) {
        for (Profesor p : listaProfesores) {
            if (p.getCedula().equals(cedula)) {
                return true;
            }
        }
        return false;
    }

    private void listarContarProfesoresTiempoCompleto() {
        Set<Profesor> profesoresTiempoCompleto = new HashSet<>(ListaProfeCompleto);
        // Eliminar los profesores que tienen otros contratos
        profesoresTiempoCompleto.removeAll(ListaProfeCatedra);
        profesoresTiempoCompleto.removeAll(ListaProfeOcasional);

        // Mostrar solo los profesores que tienen únicamente el contrato de tiempo completo
        ObservableList<Profesor> tiempoCompletoObservable = FXCollections.observableArrayList(profesoresTiempoCompleto);
        listaPrincipal.setItems(tiempoCompletoObservable);
        txtTotalResultado.setText("Profesores: "+ profesoresTiempoCompleto.size());
    }

    private void listarContarProfesoresCatedra() {
        Set<Profesor> profesoresCatedra = new HashSet<>(ListaProfeCatedra);
        // Eliminar los profesores que tienen otros contratos
        profesoresCatedra.removeAll(ListaProfeOcasional);
        profesoresCatedra.removeAll(ListaProfeCompleto);

        // Mostrar solo los profesores que tienen únicamente el contrato de cátedra
        ObservableList<Profesor> catedraObservable = FXCollections.observableArrayList(profesoresCatedra);
        listaPrincipal.setItems(catedraObservable);
        txtTotalResultado.setText("Profesores: "+ profesoresCatedra.size());
    }

    private void listarContarProfesoresOcasionales() {
        Set<Profesor> profesoresOcasionales = new HashSet<>(ListaProfeOcasional);
        // Eliminar los profesores que tienen otros contratos
        profesoresOcasionales.removeAll(ListaProfeCatedra);
        profesoresOcasionales.removeAll(ListaProfeCompleto);

        // Mostrar solo los profesores que tienen únicamente el contrato ocasional
        ObservableList<Profesor> ocasionalesObservable = FXCollections.observableArrayList(profesoresOcasionales);
        listaPrincipal.setItems(ocasionalesObservable);
        txtTotalResultado.setText("Profesores: " + profesoresOcasionales.size());
    }
    private void listarContarProfesoresTotales() {
        // Crear un conjunto para almacenar profesores únicos
        Set<Profesor> profesoresUnicos = new HashSet<>();

        // Agregar todos los profesores de las listas, pero sin repetidos
        profesoresUnicos.addAll(ListaProfeCompleto);
        profesoresUnicos.addAll(ListaProfeCatedra);
        profesoresUnicos.addAll(ListaProfeOcasional);

        // Convertir el conjunto a una lista observable para mostrar en la interfaz
        ObservableList<Profesor> profesoresTotalesObservable = FXCollections.observableArrayList(profesoresUnicos);
        listaPrincipal.setItems(profesoresTotalesObservable);

        txtTotalResultado.setText("Profesores: " + profesoresUnicos.size());
    }
    private void listarContarProfesoresTCyCatedra() {
        Set<Profesor> profesoresTCyCatedra = new HashSet<>();

        for (Profesor profesor : ListaProfeCompleto) {
            if (ListaProfeCatedra.contains(profesor) &&
                !ListaProfeOcasional.contains(profesor)) {
                profesoresTCyCatedra.add(profesor);
            }
        }
        ObservableList<Profesor> tcyCatedraObservable = FXCollections.observableArrayList(profesoresTCyCatedra);
        listaPrincipal.setItems(tcyCatedraObservable);
        txtTotalResultado.setText("Profesores: " + profesoresTCyCatedra.size());
    }

    private void listarContarProfesoresOcasionalCatedra() {
        Set<Profesor> profesoresOcasionalCatedra = new HashSet<>();

        for (Profesor profesor : ListaProfeOcasional) {
            if (ListaProfeCatedra.contains(profesor) &&
                !ListaProfeCompleto.contains(profesor)) {
                profesoresOcasionalCatedra.add(profesor);
            }
        }
        ObservableList<Profesor> ocasionalCatedraObservable = FXCollections.observableArrayList(profesoresOcasionalCatedra);
        listaPrincipal.setItems(ocasionalCatedraObservable);
        txtTotalResultado.setText("Profesores: " +  profesoresOcasionalCatedra.size());
    }



    private void listarContarProfesoresCombinado() {
        Set<Profesor> profesoresCombinados = new HashSet<>();

        for (Profesor profesor : ListaProfeCompleto) {
            if (ListaProfeCatedra.contains(profesor) &&
                ListaProfeOcasional.contains(profesor)) {
                profesoresCombinados.add(profesor);
            }
        }
        ObservableList<Profesor> combinadoObservable = FXCollections.observableArrayList(profesoresCombinados);
        listaPrincipal.setItems(combinadoObservable);
        txtTotalResultado.setText("Profesores: " + profesoresCombinados.size());
    }


    @FXML
    public void ButtonAgregarOnAction(ActionEvent actionEvent) {
        Profesor profesor = new Profesor();
        TextmensajeError.setText("");



        // Cédula Únicas
        /*String cedula = campoCedula.getText();
        if (!ExpresionesRegulares.cedulaPattern.matcher(cedula).matches() || cedulaYaExiste(cedula)) {
            if (!ExpresionesRegulares.cedulaPattern.matcher(cedula).matches()) {
                MensajeError(TextmensajeError, "Cédula Error");
            } else {
                MensajeError(TextmensajeError, "La cédula ya existe");
            }
            return;
        }profesor.setCedula(cedula);
        */

        if (!ExpresionesRegulares.cedulaPattern.matcher(campoCedula.getText()).matches()) {
            MensajeError(TextmensajeError, "Cédula Error");
            return;
        } else profesor.setCedula(campoCedula.getText());

        // Validar contrato
        if (choiceTipoContrato.getSelectionModel().getSelectedItem() == null){
            MensajeError(TextmensajeError, "Seleccione un Tipo de contrato");
            return;
        } else {
            String tipoContrato = choiceTipoContrato.getSelectionModel().getSelectedItem();
            profesor.setTipoContrato(tipoContrato);
        }

        // Validar el campo txtNombre
        if (!ExpresionesRegulares.nombrePattern.matcher(campoNombre.getText()).matches()) {
            MensajeError(TextmensajeError, "Nombre Error");
            return;
        } else profesor.setNombreCompleto(campoNombre.getText());

        // Validar el campo Sexo
        if (choiceSexo.getSelectionModel().getSelectedItem() == null){
            MensajeError(TextmensajeError, "Seleccione un Tipo Sexo");
            return;
        } else {
            String tipoSexo = choiceSexo.getSelectionModel().getSelectedItem();
            profesor.setSexo(tipoSexo);
        }

        // Validar el campo txtFecha
        if (!ExpresionesRegulares.fechaPattern.matcher(campoFecha.getText()).matches()) {
            MensajeError(TextmensajeError, "Verifique dd-mm-yyyy");
            return;
        } else profesor.setFecha(campoFecha.getText());

        // Validar Facultad
        if (choiceFacultad.getSelectionModel().getSelectedItem() == null){
            MensajeError(TextmensajeError, "Seleccione un Tipo de Facultad");
            return;
        } else {
            String tipoFacultad = choiceFacultad.getSelectionModel().getSelectedItem();
            profesor.setFacultad(tipoFacultad);
        }

        // Validar titulo
        if (choiceTitulo.getSelectionModel().getSelectedItem() == null){
            MensajeError(TextmensajeError, "Seleccione un Tipo Titulo");
            return;
        } else {
            String tipoTitulo = choiceTitulo.getSelectionModel().getSelectedItem();
            profesor.setTitulo(tipoTitulo);
        }

        // Validar Num Asignaturas
        if (!ExpresionesRegulares.numAsignaturasPattern.matcher(campoAsignaturas.getText()).matches()) {
            MensajeError(TextmensajeError, "Rango de asignaturas Error");
            return;
        } else profesor.setNumAsignaturas(Integer.parseInt(campoAsignaturas.getText()));


        // Validar Horas Dictadas
        if (!ExpresionesRegulares.numHorasPattern.matcher(campoHoras.getText()).matches()) {
            MensajeError(TextmensajeError, "Rango de horas Error");
            return;
        } else profesor.setNumHoras(Integer.parseInt(campoHoras.getText()));

        listaProfesores.add(profesor);

        for (Profesor tipoContrato : listaProfesores) {
            switch (tipoContrato.getTipoContrato()) {
                case "Cátedra" -> ListaProfeCatedra.add(tipoContrato);
                case "Ocasional" -> ListaProfeOcasional.add(tipoContrato);
                case "Tiempo Completo" -> ListaProfeCompleto.add(tipoContrato);
            }
        }

        // Actualizar la TableView
        ObservableList<Profesor> observableListaProfesores = FXCollections.observableArrayList(listaProfesores);
        listaPrincipal.setItems(observableListaProfesores);


    }

    @FXML
    public void buttonBuscarDocenteOnAction(ActionEvent actionEvent) {
        // Limpiar Datos de la Tabla
        listaPrincipal.getItems().clear();
        ProfesoresUnicos.clear();

        String opcionSeleccionada = choicePorDocente.getSelectionModel().getSelectedItem();
        if (opcionSeleccionada != null) {
            switch (opcionSeleccionada) {
                case "Cátedra":
                    listarContarProfesoresCatedra();
                    break;
                case "Ocasional":
                    listarContarProfesoresOcasionales();
                    break;
                case "Tiempo Completo":
                    listarContarProfesoresTiempoCompleto();
                    break;
                case "Total Docentes":
                    listarContarProfesoresTotales();
                    break;
                case "T.C - Cátedra":
                    listarContarProfesoresTCyCatedra();
                    break;
                case "Ocasional - Cátedra":
                    listarContarProfesoresOcasionalCatedra();
                    break;
                case "CA-TC-OC":
                    listarContarProfesoresCombinado();
                    break;

            }
        }
    }


    private void listarContarIngenieria() {
        Set<Profesor> profesoresIngenieria = new HashSet<>();
        for (Profesor profesor : ListaProfeCompleto) {
            if (profesor.getFacultad().equals("Ingenieria")) {
                profesoresIngenieria.add(profesor);
            }
        }
        for (Profesor profesor : ListaProfeCatedra) {
            if (profesor.getFacultad().equals("Ingenieria")) {
                profesoresIngenieria.add(profesor);
            }
        }
        for (Profesor profesor : ListaProfeOcasional) {
            if (profesor.getFacultad().equals("Ingenieria")) {
                profesoresIngenieria.add(profesor);
            }
        }
        ProfesoresUnicos.addAll(profesoresIngenieria);
        ObservableList<Profesor> ingenieriaObservable = FXCollections.observableArrayList(profesoresIngenieria);
        listaPrincipal.setItems(ingenieriaObservable);
        txtTotalResultado.setText("Profesores: " + profesoresIngenieria.size());
    }
    private void listarContarDeportes() {
        Set<Profesor> profesoresDeportes = new HashSet<>();
        for (Profesor profesor : ListaProfeCompleto) {
            if (profesor.getFacultad().equals("Deportes")) {
                profesoresDeportes.add(profesor);
            }
        }
        for (Profesor profesor : ListaProfeCatedra) {
            if (profesor.getFacultad().equals("Deportes")) {
                profesoresDeportes.add(profesor);
            }
        }
        for (Profesor profesor : ListaProfeOcasional) {
            if (profesor.getFacultad().equals("Deportes")) {
                profesoresDeportes.add(profesor);
            }
        }
        ObservableList<Profesor> deportesObservable = FXCollections.observableArrayList(profesoresDeportes);
        listaPrincipal.setItems(deportesObservable);
        txtTotalResultado.setText("Profesores: " + profesoresDeportes.size());
    }
    private void listarContarAdministracion() {
        Set<Profesor> profesoresAdministracion = new HashSet<>();
        for (Profesor profesor : ListaProfeCompleto) {
            if (profesor.getFacultad().equals("Administracion")) {
                profesoresAdministracion.add(profesor);
            }
        }
        for (Profesor profesor : ListaProfeCatedra) {
            if (profesor.getFacultad().equals("Administracion")) {
                profesoresAdministracion.add(profesor);
            }
        }
        for (Profesor profesor : ListaProfeOcasional) {
            if (profesor.getFacultad().equals("Administracion")) {
                profesoresAdministracion.add(profesor);
            }
        }
        ObservableList<Profesor> administracionObservable = FXCollections.observableArrayList(profesoresAdministracion);
        listaPrincipal.setItems(administracionObservable);
        txtTotalResultado.setText("Profesores: " + profesoresAdministracion.size());
    }
    private void listarContarIdiomas() {
        Set<Profesor> profesoresIdiomas = new HashSet<>();
        for (Profesor profesor : ListaProfeCompleto) {
            if (profesor.getFacultad().equals("Idiomas")) {
                profesoresIdiomas.add(profesor);
            }
        }
        for (Profesor profesor : ListaProfeCatedra) {
            if (profesor.getFacultad().equals("Idiomas")) {
                profesoresIdiomas.add(profesor);
            }
        }
        for (Profesor profesor : ListaProfeOcasional) {
            if (profesor.getFacultad().equals("Idiomas")) {
                profesoresIdiomas.add(profesor);
            }
        }
        ObservableList<Profesor> idiomasObservable = FXCollections.observableArrayList(profesoresIdiomas);
        listaPrincipal.setItems(idiomasObservable);
        txtTotalResultado.setText("Profesores: " + profesoresIdiomas.size());
    }
    private void listarContarCBasicas() {
        Set<Profesor> profesoresCBasicas = new HashSet<>();
        for (Profesor profesor : ListaProfeCompleto) {
            if (profesor.getFacultad().equals("Ciencias Basicas")) {
                profesoresCBasicas.add(profesor);
            }
        }
        for (Profesor profesor : ListaProfeCatedra) {
            if (profesor.getFacultad().equals("Ciencias Basicas")) {
                profesoresCBasicas.add(profesor);
            }
        }
        for (Profesor profesor : ListaProfeOcasional) {
            if (profesor.getFacultad().equals("Ciencias Basicas")) {
                profesoresCBasicas.add(profesor);
            }
        }
        ObservableList<Profesor> cBasicasObservable = FXCollections.observableArrayList(profesoresCBasicas);
        listaPrincipal.setItems(cBasicasObservable);
        txtTotalResultado.setText("Profesores: " + profesoresCBasicas.size());
    }
    @FXML
    public void buttonBuscarFacultadOnAction(ActionEvent actionEvent) {
        // Limpiar Datos de la Tabla
        listaPrincipal.getItems().clear();
        ProfesoresUnicos.clear();

        String opcionSeleccionada = choicePorDocente.getSelectionModel().getSelectedItem();
        if (opcionSeleccionada != null) {
            switch (opcionSeleccionada) {
                case "Ingenieria":
                    listarContarIngenieria();
                    break;
                case "Deportes":
                    listarContarDeportes();
                    break;
                case "Administracion":
                    listarContarAdministracion();
                    break;
                case "Idiomas":
                    listarContarIdiomas();
                    break;
                case "Ciencias Basicas":
                    listarContarCBasicas();
                    break;

            }
        }
    }
    private void listarContarHombres() {
        Set<Profesor> profesoresHombres = new HashSet<>();
        for (Profesor profesor : ListaProfeCompleto) {
            if (profesor.getSexo().equals("Masculino")) {
                profesoresHombres.add(profesor);
            }
        }
        for (Profesor profesor : ListaProfeCatedra) {
            if (profesor.getSexo().equals("Masculino")) {
                profesoresHombres.add(profesor);
            }
        }
        for (Profesor profesor : ListaProfeOcasional) {
            if (profesor.getSexo().equals("Masculino")) {
                profesoresHombres.add(profesor);
            }
        }
        ObservableList<Profesor> hombresObservable = FXCollections.observableArrayList(profesoresHombres);
        listaPrincipal.setItems(hombresObservable);
        txtTotalResultado.setText("Profesores: " + profesoresHombres.size());
    }

    private void listarContarMujeres() {
        Set<Profesor> profesoresMujeres = new HashSet<>();
        for (Profesor profesor : ListaProfeCompleto) {
            if (profesor.getSexo().equals("Femenino")) {
                profesoresMujeres.add(profesor);
            }
        }
        for (Profesor profesor : ListaProfeCatedra) {
            if (profesor.getSexo().equals("Femenino")) {
                profesoresMujeres.add(profesor);
            }
        }
        for (Profesor profesor : ListaProfeOcasional) {
            if (profesor.getSexo().equals("Femenino")) {
                profesoresMujeres.add(profesor);
            }
        }
        ObservableList<Profesor> mujeresObservable = FXCollections.observableArrayList(profesoresMujeres);
        listaPrincipal.setItems(mujeresObservable);
        txtTotalResultado.setText("Profesores: " + profesoresMujeres.size());
    }

    @FXML
    public void buttonBuscarContratoOnAction(ActionEvent actionEvent) {
        // Limpiar Datos de la Tabla
        listaPrincipal.getItems().clear();
        ProfesoresUnicos.clear();

        String opcionSeleccionada = choicePorDocente.getSelectionModel().getSelectedItem();
        if (opcionSeleccionada != null) {
            switch (opcionSeleccionada) {
                case "Total Hombres":
                    listarContarHombres();
                    break;
                case "Total Mujeres":
                    listarContarMujeres();
                    break;
            }
        }
    }

    @FXML
    public void ButtonImportarOnAction(ActionEvent actionEvent) {

        final List<String> opcionesContrato = List.of("Tiempo Completo", "Cátedra", "Ocasional");
        final List<String> opcionesSexo = List.of("Masculino", "Femenino");
        final List<String> opcionesFacultad = List.of("Ingenieria", "Deportes", "Administracion", "Idiomas", "Ciencias Basicas");
        final List<String> opcionesTitulo = List.of("Pregrado", "Maestria", "Doctorado");

        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {

                    String[] campos = line.split(",");
                    Profesor profesor = new Profesor();


                    if (!ExpresionesRegulares.cedulaPattern.matcher(campos[0]).matches()) {
                        MensajeError(TextmensajeError, "Cédula Error");
                        return;
                    } else profesor.setCedula(campos[0]);

                    if (!opcionesContrato.contains(campos[1])) {
                        MensajeError(TextmensajeError, "Seleccione un Tipo de contrato");
                        return;
                    }else profesor.setTipoContrato(campos[1]);


                    // Validar el campo txtNombre
                    if (!ExpresionesRegulares.nombrePattern.matcher(campos[2]).matches()){
                        MensajeError(TextmensajeError, "Nombre Error");
                        return;
                    } else profesor.setNombreCompleto(campos[2]);

                    if (!opcionesSexo.contains(campos[3])) {
                        MensajeError(TextmensajeError, "Seleccione un Tipo de Sexo");
                        return;
                    }else profesor.setSexo(campos[3]);


                    // Validar el campo txtFecha
                    if (!ExpresionesRegulares.fechaPattern.matcher(campos[8]).matches()) {
                        MensajeError(TextmensajeError, "Verifique dd-mm-yyyy");
                        return;
                    } else profesor.setFecha(campos[8]);

                    // Validar Facultad
                    if (!opcionesFacultad.contains(campos[4])) {
                        MensajeError(TextmensajeError, "Seleccione un Tipo de Facultad");
                        return;
                    }else profesor.setFacultad(campos[4]);

                    // Validar titulo
                    if (!opcionesTitulo.contains(campos[5])) {
                        MensajeError(TextmensajeError, "Seleccione un Tipo de Facultad");
                        return;
                    }else profesor.setTitulo(campos[5]);

                    // Validar Num Asignaturas
                    if (!ExpresionesRegulares.numAsignaturasPattern.matcher(String.valueOf(Integer.parseInt(campos[6]))).matches()) {
                        MensajeError(TextmensajeError, "Rango de asignaturas Error");
                        return;
                    } else {
                        profesor.setNumAsignaturas(Integer.parseInt(campos[6]));
                    }

                    // Validar Horas Dictadas
                    if (!ExpresionesRegulares.numHorasPattern.matcher(String.valueOf(Integer.parseInt(campos[7]))).matches()) {
                        MensajeError(TextmensajeError, "Rango de horas Error");
                        return;
                    } else {
                        profesor.setNumHoras(Integer.parseInt(campos[7]));
                    }

                    // Agregar el nuevo profesor a la lista correspondiente
                    listaProfesores.add(profesor);
                    switch (profesor.getTipoContrato()) {
                        case "Cátedra" -> ListaProfeCatedra.add(profesor);
                        case "Ocasional" -> ListaProfeOcasional.add(profesor);
                        case "Tiempo Completo" -> ListaProfeCompleto.add(profesor);
                    }
                }

                // Actualizar la TableView
                ObservableList<Profesor> observableListaProfesores = FXCollections.observableArrayList(listaProfesores);
                listaPrincipal.setItems(observableListaProfesores);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
