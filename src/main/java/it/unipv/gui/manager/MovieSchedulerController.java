package it.unipv.gui.manager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.unipv.conversion.CSVToMovieScheduleList;
import it.unipv.conversion.MovieScheduleToCSV;
import it.unipv.gui.common.GUIUtils;
import it.unipv.gui.common.IPane;
import it.unipv.gui.common.Movie;
import it.unipv.gui.common.MovieSchedule;
import it.unipv.utils.ApplicationException;
import it.unipv.utils.DataReferences;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import javax.swing.*;

public class MovieSchedulerController implements IPane {

    @FXML Label nuovaProgrammazioneButton;
    @FXML ScrollPane schedulerPanel;
    private Stage movieSchedulerEditorStage;
    private GridPane grigliaProgrammazione = new GridPane();
    private static int rowCount = 0;
    private static int columnCount = 0;
    private List<MovieSchedule> movieSchedules = new ArrayList<>();
    private List<MovieSchedule> actualMovieSchedules = new ArrayList<>();
    private Movie movie;

    void init(Movie movie) {
        grigliaProgrammazione.getChildren().clear();

        this.movie = movie;
        initMovieSchedulesList();

        for (MovieSchedule schedule : actualMovieSchedules) {
            createViewFromMovieSchedulesList(schedule);
        }

        GUIUtils.setScaleTransitionOnControl(nuovaProgrammazioneButton);

        rowCount = 0;
        columnCount = 0;
    }

    private boolean isMovieSchedulerEditorAlreadyOpened = false;
    @FXML public void nuovaProgrammazioneButtonListener() {
        if(!isMovieSchedulerEditorAlreadyOpened) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/manager/MovieScheduleEditor.fxml"));
                Parent p = loader.load();
                MovieScheduleEditorController msec = loader.getController();
                msec.init(this, movie);
                movieSchedulerEditorStage = new Stage();
                movieSchedulerEditorStage.setScene(new Scene(p));
                movieSchedulerEditorStage.setTitle("Nuova programmazione per " + movie.getTitolo());
                movieSchedulerEditorStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/GoldenMovieStudioIcon.png")));
                movieSchedulerEditorStage.setOnCloseRequest(event -> isMovieSchedulerEditorAlreadyOpened = false);
                movieSchedulerEditorStage.show();
                isMovieSchedulerEditorAlreadyOpened = true;
            } catch (IOException ex) {
                throw new ApplicationException(ex);
            }
        }
    }

    private void createViewFromMovieSchedulesList(MovieSchedule schedule) {
        Label scheduleLabel = new Label(schedule.getDate() + "   " +  schedule.getTime() + "   " + schedule.getHallName());
        scheduleLabel.setFont(Font.font("system", FontWeight.NORMAL, FontPosture.REGULAR, 15));
        scheduleLabel.setTextFill(Color.WHITE);

        grigliaProgrammazione.setHgap(15);
        grigliaProgrammazione.setVgap(15);

        Label deleteIcon = new Label();
        deleteIcon.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        deleteIcon.setGraphic(GUIUtils.getIconView(getClass().getResourceAsStream("/images/Bin.png")));
        deleteIcon.setTooltip(new Tooltip("Elimina"));
        GUIUtils.setFadeInOutOnControl(deleteIcon);

        AnchorPane pane = new AnchorPane();
        if(columnCount==1) {
            columnCount=0;
            rowCount++;
        }
        grigliaProgrammazione.add(pane, columnCount, rowCount);
        columnCount++;

        schedulerPanel.setContent(grigliaProgrammazione);
        GridPane.setMargin(pane, new Insets(5,5,5,5));

        deleteIcon.setLayoutY(scheduleLabel.getLayoutY());
        deleteIcon.setLayoutX(scheduleLabel.getLayoutX()+200);
        deleteIcon.setOnMouseClicked(e -> {
            int reply = JOptionPane.showConfirmDialog(null, "Sei sicuro di voler eliminare dalla lista questa programmazione?");
            if(reply == JOptionPane.YES_OPTION) {
                movieSchedules.remove(schedule);
                MovieScheduleToCSV.createCSVFromMovieScheduleList(movieSchedules, DataReferences.MOVIESCHEDULEFILEPATH, false);
                refreshUI();
            }
        });

        pane.getChildren().addAll(scheduleLabel, deleteIcon);
    }

    private void initMovieSchedulesList() {
        movieSchedules.clear();
        actualMovieSchedules.clear();
        movieSchedules = CSVToMovieScheduleList.getMovieScheduleListFromCSV(DataReferences.MOVIESCHEDULEFILEPATH);
        Collections.sort(movieSchedules);
        for(MovieSchedule ms : movieSchedules) {
            if(ms.getMovieCode().equalsIgnoreCase(movie.getCodice())) {
                actualMovieSchedules.add(ms);
            }
        }
    }

    void triggerNewScheduleEvent() { refreshUI(); }

    private void refreshUI() {
        grigliaProgrammazione.getChildren().clear();
        init(movie);
    }

    @Override
    public void closeAllSubWindows() {
        if(movieSchedulerEditorStage != null) {
            if(movieSchedulerEditorStage.isShowing()) {
                isMovieSchedulerEditorAlreadyOpened = false;
                movieSchedulerEditorStage.close();
            }
        }
    }
}
