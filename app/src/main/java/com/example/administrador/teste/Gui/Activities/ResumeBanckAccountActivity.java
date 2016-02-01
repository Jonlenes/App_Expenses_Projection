package com.example.administrador.teste.Gui.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrador.teste.Gui.Dialogs.DialogOperationBankAccount;
import com.example.administrador.teste.Modelo.Bo.BankAccountBo;
import com.example.administrador.teste.Modelo.Bo.CategoriaBo;
import com.example.administrador.teste.Modelo.Bo.ModelException;
import com.example.administrador.teste.Modelo.Vo.BankAccount;
import com.example.administrador.teste.Modelo.Vo.Enum.EnumOperationBankAccount;
import com.example.administrador.teste.R;

import java.text.NumberFormat;
import java.util.Map;

public class ResumeBanckAccountActivity extends Activity {
    private Long idBankAccount;
    private BankAccount bankAccount;
    private Map<String, Double> mapPercent;

    //private RelativeLayout graphDistribuitionRelativeLayout;
    //private PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume_banck_account);

        idBankAccount = getIntent().getLongExtra("idBankAccount", -1);
        ((Button) findViewById(R.id.projetarButton)).setOnClickListener(onClickListenerProjetar);

        new BankAccountTask().execute();

        /*graphDistribuitionRelativeLayout = (RelativeLayout) findViewById(R.id.graphDistribuitionRelativeLayout);
        pieChart = new PieChart(this);

        graphDistribuitionRelativeLayout.addView(pieChart);
        graphDistribuitionRelativeLayout.setBackgroundColor(Color.LTGRAY);

        pieChart.setUsePercentValues(true);
        pieChart.setDescription("Distribuição por categoria");
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleRadius(7);
        pieChart.setTransparentCircleRadius(10);

        pieChart.setRotationAngle(0);
        pieChart.setRotationEnabled(true);

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry entry, int i, Highlight highlight) {
                if (entry == null) return;

                Toast.makeText(ResumeBanckAccountActivity.this, mapPercent.entrySet().toArray()[entry.getXIndex()].toString() + " = " + entry.getVal() + "%", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });

        Legend legend = pieChart.getLegend();
        legend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        legend.setXEntrySpace(7);
        legend.setYEntrySpace(5);*/

        //pieChart.
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_resume_bank_account, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_depositar_bank_account:
                operationBankAccount(EnumOperationBankAccount.depositar);;
                break;
            case R.id.action_sacar_bank_account:
                operationBankAccount(EnumOperationBankAccount.sacar);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private View.OnClickListener onClickListenerProjetar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Double percentual = Double.parseDouble(((EditText) findViewById(R.id.percentualProjetionEditText)).getText().toString());
            if (percentual <= 0 || percentual > 100) Toast.makeText(ResumeBanckAccountActivity.this, "Percentual inválido", Toast.LENGTH_LONG).show();
            else
                new PorjetarTask().execute(percentual / 100.0);
        }
    };

    private void operationBankAccount(EnumOperationBankAccount operationBankAccount) {
        DialogOperationBankAccount dialogOperationBankAccount =
                new DialogOperationBankAccount(ResumeBanckAccountActivity.this, idBankAccount, operationBankAccount);
        dialogOperationBankAccount.show();
        dialogOperationBankAccount.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                new BankAccountTask().execute();
            }
        });

    }

    public class BankAccountTask extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(ResumeBanckAccountActivity.this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Buscando dados...");
        }

        @Override
        protected Void doInBackground(Void... params) {
            bankAccount = new BankAccountBo().getBankAccount(idBankAccount);
            mapPercent = new CategoriaBo().getAllByBankAccout(idBankAccount);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (bankAccount != null) {
                NumberFormat numberFormat = NumberFormat.getCurrencyInstance();

                ((TextView) findViewById(R.id.nameBankAccountTextView)).setText(bankAccount.getName());
                ((TextView) findViewById(R.id.saldoCorrenteDisponivelTextView)).setText(
                        numberFormat.format(bankAccount.getSaldoCorrente() - bankAccount.getSaldoProjetado()));
                ((TextView) findViewById(R.id.saldoCorrenteTextView)).setText(numberFormat.format(bankAccount.getSaldoCorrente()));
                ((TextView) findViewById(R.id.saldoPoupancaTextView)).setText(numberFormat.format(bankAccount.getSaldoPoupanca()));
                ((TextView) findViewById(R.id.saldoProjetadoTextView)).setText(numberFormat.format(bankAccount.getSaldoProjetado()));

                String s = "";
                for (Map.Entry<String, Double> entry : mapPercent.entrySet()) {
                    s += entry.getKey() + "\t" + NumberFormat.getPercentInstance().format((int)(100 * entry.getValue()) / bankAccount.getSaldoProjetado()) + "\n";
                }
                ((TextView) findViewById(R.id.percentTextView)).setText(s);

                /*ArrayList<Entry> yValues  = new ArrayList<Entry>();
                ArrayList<String> xValues  = new ArrayList<String>();
                int i = 0;

                for (Map.Entry<String, Double> entry : mapPercent.entrySet()) {
                    yValues.add(new Entry((float) ((100 * entry.getValue()) / bankAccount.getSaldoProjetado()), i));
                    xValues.add(entry.getKey());
                    i++;
                }
                PieDataSet pieDataSet = new PieDataSet(yValues, "Categorias");
                pieDataSet.setSliceSpace(3);
                pieDataSet.setSelectionShift(5);

                ArrayList<Integer> colors = new ArrayList<Integer>();
                for (int c : ColorTemplate.VORDIPLOM_COLORS) colors.add(c);
                for (int c : ColorTemplate.JOYFUL_COLORS) colors.add(c);
                for (int c : ColorTemplate.COLORFUL_COLORS) colors.add(c);
                for (int c : ColorTemplate.LIBERTY_COLORS) colors.add(c);
                for (int c : ColorTemplate.PASTEL_COLORS) colors.add(c);
                colors.add(ColorTemplate.getHoloBlue());
                pieDataSet.setColors(colors);

                PieData pieData = new PieData(xValues, pieDataSet);
                pieData.setValueFormatter(new PercentFormatter());
                pieData.setValueTextSize(11f);
                pieData.setValueTextColor(Color.GRAY);

                pieChart.setData(pieData);
                pieChart.highlightValue(null);
                pieChart.invalidate(); */


            } else {
                Toast.makeText(ResumeBanckAccountActivity.this, "Erro", Toast.LENGTH_SHORT).show();
                finish();
            }
            progressDialog.dismiss();
        }
    }

    public class PorjetarTask extends AsyncTask<Double, Void, Void> {
        private ProgressDialog progressDialog;
        private String messageException;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(ResumeBanckAccountActivity.this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Projetando...");
        }

        @Override
        protected Void doInBackground(Double... params) {
            try {
                new BankAccountBo().projetar(idBankAccount, params[0]);
            } catch (ModelException e) {
                e.printStackTrace();
                messageException = e.getMessage();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            progressDialog.dismiss();
            if (messageException != null) {
                Toast.makeText(ResumeBanckAccountActivity.this, messageException, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(ResumeBanckAccountActivity.this, "Projeção realizada com sucesso.", Toast.LENGTH_LONG).show();
            }
            new BankAccountTask().execute();
        }
    }

}
