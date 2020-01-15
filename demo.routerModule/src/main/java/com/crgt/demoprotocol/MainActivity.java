package com.crgt.demoprotocol;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;

import com.crgt.demo.R;
import com.crgt.protocol.DefaultProtocolParser;
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
        setContentView(R.layout.activity_main);
        initProtocols();
    }

    private void initProtocols() {
        Protocol.setParser(new DefaultProtocolParser("in", DefaultProtocolParser.ComponentIdentifier.HOST));
        Protocol.setPreProcessor(new PreProcessor());
        Protocol.setDefaultProcessor(new DefaultProcessor());
    }

    public void gotoNonexistProtocol(View view) {
        Protocol.gotoProtocol(this,"in://xxx_non_exist");

    }

    public void gotoCustomProtocol(View view) {
        Protocol.gotoProtocol(this,"in://fragment_activity");
    }

    public void gotoOtherModuleProtocol(View view) {
        Protocol.gotoProtocol(this,"in://module2");
    }

    public void gotoDefaultProtocol(View view) {
        Protocol.gotoProtocol(this,"in://main");
    }

    public void setPreprocessor(View view) {
        if (((CheckBox) view).isChecked()) {
            Protocol.setPreProcessor(new PreProcessorInterrupt());
        }else{
            Protocol.setPreProcessor(new PreProcessor());
        }
    }


}
