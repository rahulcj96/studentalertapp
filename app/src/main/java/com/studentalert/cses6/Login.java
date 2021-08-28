package com.studentalert.cses6;
        import android.app.Activity;
        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;
public class Login extends Activity
{
    int uidnumber;
    String uid="";
    String first="u";
    String second="U";
    String numberFromUid="";
    EditText enterUid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);
        enterUid=(EditText) findViewById(R.id.editText);
    }
    public void checkAuthentication(View v) {
        uid=enterUid.getText().toString();
        if(uid!=null){
            if(uid.charAt(0)==first.charAt(0)||uid.charAt(0)==second.charAt(0)){
                numberFromUid=uid.substring(1);
                try {
                    uidnumber = Integer.parseInt(numberFromUid);
                    if (uidnumber > 1403060 && uidnumber < 1403122) {
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(this, "Invalid uid", Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception e){
                    Toast.makeText(this, "Invalid Login", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(this,"Invalid Login",Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this,"Enter a valid uid",Toast.LENGTH_SHORT).show();
        }
        }

}