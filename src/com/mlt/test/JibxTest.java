package com.mlt.test;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

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
import com.mlt.entity.ListBean;

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
    
//    @Test
    public void bean2XML() {
    	System.out.println("解析普通对象");
    	System.out.println("运行之前需要： 在 D:/work/eclipse/workspace-jee/tech38XMLJibx>下运行： java -cp bin;lib/jibx-bind.jar org.jibx.binding.Compile -v bind.xml");
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
    
    @Test
    public void listBean2XML() {
    	System.out.println("解析集合对象：在运行前，一定要将最先前运行的Account那个类的bind.xml文件的内容加入到现在这个bind.xml中，因为ListBean依赖了Account这个类。");
        try {
            ListBean listBean = new ListBean();
            List<Account> list = new ArrayList<Account>();
            list.add(bean);
            bean = new Account();
            bean.setAddress("china");
            bean.setEmail("tom@125.com");
            bean.setId(2);
            bean.setName("tom");
            Birthday day = new Birthday("2010-11-22");
            bean.setBirthday(day);
            list.add(bean);
            listBean.setList(list);
            
            writer = new StringWriter();
            factory = BindingDirectory.getFactory(ListBean.class);
            // marshal 编组
            IMarshallingContext mctx = factory.createMarshallingContext();
            mctx.setIndent(2);
            mctx.marshalDocument(listBean, "UTF-8", null, writer);
            fail(writer);
            
            reader = new StringReader(writer.toString());
            //unmarshal 解组
            IUnmarshallingContext uctx = factory.createUnmarshallingContext();
            listBean = (ListBean) uctx.unmarshalDocument(reader, null);
            
            fail(listBean.getList().get(0));
            fail(listBean.getList().get(1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}