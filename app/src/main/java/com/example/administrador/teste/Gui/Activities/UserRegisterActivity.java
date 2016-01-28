package com.example.administrador.teste.Gui.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrador.teste.Modelo.Bo.ModelException;
import com.example.administrador.teste.Modelo.Bo.UserBo;
import com.example.administrador.teste.Modelo.Vo.Enum.EnumOperation;
import com.example.administrador.teste.Modelo.Vo.User;
import com.example.administrador.teste.R;

public class UserRegisterActivity extends AppCompatActivity {

    private EditText userEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText repeatPasswordEditText;
    private CheckBox isConnectCheckBox;
    private Button nextButtton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        userEditText = (EditText) findViewById(R.id.userNameEditText);
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        isConnectCheckBox = (CheckBox) findViewById(R.id.isConnectedCheckBox);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        repeatPasswordEditText = (EditText) findViewById(R.id.repeatPasswordEditText);
        nextButtton = (Button) findViewById(R.id.nextButtonUser);

        nextButtton.setOnClickListener(onClickListenerNext);

    }

    private View.OnClickListener onClickListenerNext = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            boolean cancel = false;

            if (userEditText.getText().toString().isEmpty()) {
                userEditText.setError("Preencha o nome de usuário.");
                cancel = true;
            } else if (userEditText.getText().length() > 30) {
                userEditText.setError("O tamanho máximo para o nome é 30.");
                cancel = true;
            }

            if (emailEditText.getText().toString().isEmpty()) {
                emailEditText.setError("Preencha o email de usuário.");
                cancel = true;
            } else if (emailEditText.getText().length() > 50) {
                emailEditText.setError("O tamanho máximo para o email é 50.");
                cancel = true;
            }

            if (passwordEditText.getText().toString().isEmpty()) {
                passwordEditText.setError("Preencha a senha.");
                cancel = true;
            } else if (passwordEditText.getText().length() > 30) {
                passwordEditText.setError("O tamanho máximo para a senha é 50.");
                cancel = true;
            }

            if (!repeatPasswordEditText.getText().toString().equals(passwordEditText.getText().toString())) {
                repeatPasswordEditText.setError("As senhas não correspondem.");
                cancel = true;
            }


            if (!cancel) {
                new OperationUserTask().execute(new User(userEditText.getText().toString(),
                        passwordEditText.getText().toString(),
                        emailEditText.getText().toString(),
                        isConnectCheckBox.isChecked()));
            }
        }
    };

    public class OperationUserTask extends AsyncTask<User, Void, Boolean> {

        private String exceptionMessage;
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(UserRegisterActivity.this);
            progressDialog.setMessage("Inserindo usuário...");
            progressDialog.setCancelable(false);
        }

        @Override
        protected Boolean doInBackground(User... params) {
            // TODO: attempt authentication against a network service.
            try {
                new UserBo().insert(params[0]);
            } catch (ModelException e) {
                exceptionMessage = e.getMessage();
                return false;
            };

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            progressDialog.dismiss();
            if (exceptionMessage != null && !exceptionMessage.isEmpty()) {
                Toast.makeText(UserRegisterActivity.this, exceptionMessage, Toast.LENGTH_LONG).show();
            } else {
                finish();
                startActivity(new Intent(UserRegisterActivity.this, MntBankAccountActivity.class));
            }
        }
    }
}
