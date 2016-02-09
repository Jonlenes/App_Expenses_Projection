package com.example.administrador.teste.Gui.Activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.NumberFormat;
import java.util.Map;

public class ResumeBankAccountActivity extends AppCompatActivity {
    private Long idBankAccount;
    private BankAccount bankAccount;
    private Map<String, Double> mapPercent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume_bank_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        idBankAccount = getIntent().getLongExtra("idBankAccount", -1);
        findViewById(R.id.projetarButton).setOnClickListener(onClickListenerProjetar);

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

                Toast.makeText(ResumeBankAccountActivity.this, mapPercent.entrySet().toArray()[entry.getXIndex()].toString() + " = " + entry.getVal() + "%", Toast.LENGTH_LONG).show();
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
                operationBankAccount(EnumOperationBankAccount.depositar);
                ;
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
            if (percentual <= 0 || percentual > 100)
                Toast.makeText(ResumeBankAccountActivity.this, "Percentual inválido", Toast.LENGTH_LONG).show();
            else
                new PorjetarTask().execute(percentual / 100.0);
        }
    };

    private void operationBankAccount(final EnumOperationBankAccount operationBankAccount) {
        final EditText descricaoEditText = new EditText(ResumeBankAccountActivity.this);
        descricaoEditText.setHint("Valor");

        DialogInterface.OnClickListener onClickListenerPositive = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DialogOperationBankAccount dialogOperationBankAccount =
                        new DialogOperationBankAccount(ResumeBankAccountActivity.this, idBankAccount, operationBankAccount, descricaoEditText.getText().toString());
                dialogOperationBankAccount.show();
                dialogOperationBankAccount.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        new BankAccountTask().execute();
                    }
                });
            }
        };

        new AlertDialog.Builder(ResumeBankAccountActivity.this)
                .setTitle("Digite o valor")
                .setPositiveButton("Ok", onClickListenerPositive)
                .setNegativeButton("Cancelar", null)
                .setView(descricaoEditText)
                .create().show();
    }

    public class BankAccountTask extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(ResumeBankAccountActivity.this);
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

                try {
                    ResumeBankAccountActivity.this.getSupportActionBar().setTitle(bankAccount.getName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ((TextView) findViewById(R.id.saldoCorrenteDisponivelTextView)).setText(
                        numberFormat.format(bankAccount.getSaldoCorrente() - bankAccount.getSaldoProjetado()));
                ((TextView) findViewById(R.id.saldoCorrenteTextView)).setText(numberFormat.format(bankAccount.getSaldoCorrente()));
                ((TextView) findViewById(R.id.saldoPoupancaTextView)).setText(numberFormat.format(bankAccount.getSaldoPoupanca()));
                ((TextView) findViewById(R.id.saldoProjetadoTextView)).setText(numberFormat.format(bankAccount.getSaldoProjetado()));

                if (bankAccount.getSaldoProjetado() > 0) {
                    String s = "";
                    for (Map.Entry<String, Double> entry : mapPercent.entrySet()) {
                        s += entry.getKey() + "\t" + NumberFormat.getPercentInstance().format(entry.getValue() / bankAccount.getSaldoProjetado()) + "\n";
                    }
                    ((TextView) findViewById(R.id.percentTextView)).setText(s);
                }
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
                Toast.makeText(ResumeBankAccountActivity.this, "Erro", Toast.LENGTH_SHORT).show();
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

            progressDialog = new ProgressDialog(ResumeBankAccountActivity.this);
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
                Toast.makeText(ResumeBankAccountActivity.this, messageException, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(ResumeBankAccountActivity.this, "Projeção realizada com sucesso.", Toast.LENGTH_LONG).show();
            }
            new BankAccountTask().execute();
        }
    }
}
