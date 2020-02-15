package com.crgt.demoprotocol;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;

import com.crgt.demo.R;
import com.crgt.protocol.Protocol;


/**
 * protocol demo activity
 *
 * @author jesse.lu
 * @Date 2019/6/15
 * @mail： jesse.lu@foxmail.com
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kotlin_activity_main);
    }



    public void gotoNonexistProtocol(View view) {
        Protocol.to(this,"in://xxx_non_exist",null);

    }

    public void gotoCustomProtocol(View view) {
        Protocol.to(this,"in://fragment_activity",null);
    }

    public void gotoOtherModuleProtocol(View view) {
        Protocol.to(this,"in://module2",null);
    }

    public void gotoDefaultProtocol(View view) {
        Protocol.to(this,"in://main",null);
    }

    public void setPreprocessor(View view) {
        if (((CheckBox) view).isChecked()) {
            Protocol.setPreProcessor(new PreProcessorInterrupt());
        }else{
            Protocol.setPreProcessor(new PreProcessor());
        }
    }


}
