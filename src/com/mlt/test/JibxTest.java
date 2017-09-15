package com.mlt.test;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.JiBXException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mlt.entity.Account;
import com.mlt.entity.Birthday;

public class JibxTest {
    private IBindingFactory factory = null;
    
    private StringWriter writer = null;
    private StringReader reader = null;
    
    private Account bean = null;
    
    @Before
    public void init() {
        bean = new Account();
        bean.setAddress("北京");
        bean.setEmail("email");
        bean.setId(1);
        bean.setName("jack");
        Birthday day = new Birthday();
        day.setBirthday("2010-11-22");
        bean.setBirthday(day);
        
        try {
            factory = BindingDirectory.getFactory(Account.class);
        } catch (JiBXException e) {
            e.printStackTrace();
        }
    }
    
    @After
    public void destory() {
        bean = null;
        try {
            if (writer != null) {
                writer.flush();
                writer.close();
            }
            if (reader != null) {
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.gc();
    }
    
    public void fail(Object o) {
        System.out.println(o);
    }
    
    public void failRed(Object o) {
        System.err.println(o);
    }
    
    @Test
    public void bean2XML() {
        try {
            writer = new StringWriter();
            // marshal 编组
            IMarshallingContext mctx = factory.createMarshallingContext();
            mctx.setIndent(2);
            mctx.marshalDocument(bean, "UTF-8", null, writer);
            fail(writer);
            
            reader = new StringReader(writer.toString());
            //unmarshal 解组
            IUnmarshallingContext uctx = factory.createUnmarshallingContext();
            Account acc = (Account) uctx.unmarshalDocument(reader, null);
            fail(acc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}