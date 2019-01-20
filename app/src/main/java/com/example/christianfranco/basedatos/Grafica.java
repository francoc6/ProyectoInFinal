package com.example.christianfranco.basedatos;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.formatter.SimpleAxisValueFormatter;
import lecho.lib.hellocharts.formatter.SimpleLineChartValueFormatter;
import lecho.lib.hellocharts.listener.LineChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

public class Grafica extends AppCompatActivity {

    LineChartView lineChartView;
    ArrayList<String> Datos= new ArrayList<String>();
    ArrayList<String> axisData= new ArrayList<String>();
    ArrayList<Float> yAxisData= new ArrayList<Float>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafica);

        lineChartView = findViewById(R.id.chart);
        lineChartView.getChartData();


        Datos=Consulta.datos;//obtengo los datos de la clase consulta de la base

        if(Datos.size()<=10) {//si solo hay 10 o menos datos se los carga
            for (int i = 0; i < Datos.size(); i++) {
                String[] parts = Datos.get(i).split("     ");
                axisData.add(parts[0]);
                yAxisData.add(Float.valueOf(parts[1]));
            }
        }else {//si hay mas de 10, toma los ultomos 10 agregados
            for (int i =Datos.size()-10; i < Datos.size(); i++) {
                String[] parts = Datos.get(i).split("     ");
                axisData.add(parts[0]);
                yAxisData.add(Float.valueOf(parts[1]));
            }
        }

        List yAxisValues = new ArrayList();
        List axisValues = new ArrayList();

        Line line = new Line(yAxisValues).setColor(Color.parseColor("#ffca1c1f"));//ROJO---color de la linea con los puntos


        for(int i = 0; i < axisData.size(); i++){
            axisValues.add(i, new AxisValue(i).setLabel(axisData.get(i)));
        }

        for (int i = 0; i < yAxisData.size(); i++){
            yAxisValues.add(new PointValue(i, yAxisData.get(i)));
        }

        List lines = new ArrayList();
        lines.add(line);
        line.setFormatter(new SimpleLineChartValueFormatter(2));
        //  line.setHasLabelsOnlyForSelected(true);
        line.setHasLabels(true);


        LineChartData data = new LineChartData();
        data.setLines(lines);

        //parte x
        Axis Xaxis = new Axis();
        Xaxis.setValues(axisValues);
        data.setAxisXBottom(Xaxis);
        Xaxis.setTextSize(16);
        Xaxis.setName("Fecha");
        Xaxis.setTextColor(Color.parseColor("#03A9F4"));
        //Xaxis.setTextColor(Color.parseColor("#ffca1c1f"));//rojo tanto la raya del eje como los valores y titulo

        //parte y
        Axis yAxis = new Axis();
        data.setAxisYLeft(yAxis);

        yAxis.setTextColor(Color.parseColor("#03A9F4"));
        // yAxis.setTextColor(R.color.ROJO);
        yAxis.setTextSize(16);
        yAxis.setName("Valores");
        yAxis.setFormatter(new SimpleAxisValueFormatter(2));
        yAxis.setMaxLabelChars(6);

        Viewport viewport = new Viewport(lineChartView.getMaximumViewport());
        viewport.top =110;
        lineChartView.setMaximumViewport(viewport);
        lineChartView.setCurrentViewport(viewport);


        lineChartView.setLineChartData(data);

        lineChartView.setOnValueTouchListener(new ValueTouchListener());

    }

    private class ValueTouchListener implements LineChartOnValueSelectListener {
        @Override
        public void onValueSelected(int lineIndex, int pointIndex, PointValue value) {


        }

        @Override
        public void onValueDeselected() {

        }
    }

}